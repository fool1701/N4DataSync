/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.ddl;

import javax.baja.sys.*;
import javax.baja.rdb.*;
import javax.baja.util.*;
import com.tridium.rdb.*;
import com.tridium.rdb.jdbc.*;
import com.tridium.rdb.jdbc.trans.*;

/**
 * A Column in a database table.
 * 
 * @author    Mike Jarmy
 * @creation  06 Feb 08
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
public class Column
{
  /**
   * Make an IDENTITY column (which can also optionally be a KEY).
   */
  public static Column makeIdentity(String name, boolean isKey)
  {
    return new Column(
      name, 
      BInteger.TYPE,
      BInteger.DEFAULT,
      -1, 
      isKey ? IDENTITY | KEY : IDENTITY);
  }

  /**
   * Make a KEY column.
   */
  public static Column makeKey(String name, Type type, BValue defaultValue)
  {
    return new Column(name, type, defaultValue, -1, KEY);
  }

  /**
   * Make a KEY column.
   */
  public static Column makeKey(String name, Type type, BValue defaultValue, int width)
  {
    return new Column(name, type, defaultValue, width, KEY);
  }

  /**
   * Make a UNIQUE column.
   */
  public static Column makeUnique(String name, Type type, BValue defaultValue)
  {
    return new Column(name, type, defaultValue, -1, UNIQUE);
  }

  /**
   * Make a UNIQUE column.
   */
  public static Column makeUnique(String name, Type type, BValue defaultValue, int width)
  {
    return new Column(name, type, defaultValue, width, UNIQUE);
  }

  /**
   * Make a CLOB column
   */
  public static Column makeClob(String name)
  {
    return new Column(name, BString.TYPE, BString.DEFAULT, -1, CLOB);
  }

  /**
   * Make a column using the specified parameters.
   */
  public static Column make(String name, Type type, BValue defaultValue)
  {
    return new Column(name, type, defaultValue, -1, 0);
  }

  /**
   * Make a column using the specified parameters.
   */
  public static Column make(String name, Type type, BValue defaultValue, int width)
  {
    return new Column(name, type, defaultValue, width, 0);
  }
  
  /**
   * Make a column using the specified parameters.
   */
  public static Column make(String name, Type type, BValue defaultValue, int width, int flags)
  {
    return new Column(name, type, defaultValue, width, flags);
  }

////////////////////////////////////////////////////////////////
// constructor
////////////////////////////////////////////////////////////////

  /**
   * constructor
   */
  private Column(
    String name,
    Type   type,
    BValue defaultValue,
    int    width,
    int    flags)
  {
    this.name  = name;
    this.type  = type;
    this.defaultValue = defaultValue;
    this.width = width;
    this.flags = flags;
  }

////////////////////////////////////////////////////////////////
// public
////////////////////////////////////////////////////////////////

  /**
   * asDdl
   */
  public String getDdl(RdbmsContext context)
  {
    return getDdl(context, false);
  }
  /**
   * asDdl
   */
  public String getDdl(RdbmsContext context, boolean alteration)
  {
    RdbmsDialect dialect = (RdbmsDialect) context;

    StringBuilder sql = new StringBuilder();
    sql.append(getName()).append(" ");
    if(alteration && dialect.getAlterColumnSuffix() != null)
      sql.append(dialect.getAlterColumnSuffix()).append(" ");
    
    sql.append(makeTypeDdl(dialect));

    if (isIdentity())
    {
      if (dialect.getInsertionMode() != RdbmsDialect.INSERT_VIA_SEQUENCE && !alteration)
        sql.append(" ").append(dialect.getIdentityCreation());
      if(!alteration || dialect.getAlterColumnSupportsNotNull())
        sql.append(" NOT NULL");
    }
    else if (isKey() || isUnique())
    {
      if(!alteration || dialect.getAlterColumnSupportsNotNull())
        sql.append(" NOT NULL");
    }
    
    return sql.toString();
  }

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

  /**
   * makeTypeDdl
   */
  private String makeTypeDdl(RdbmsDialect dialect)
  {
    BColumnTranslator translator = BColumnTranslator.makeTranslator(dialect, defaultValue);

    switch (translator.getSqlType().getOrdinal())
    {
      case BSqlType.SQL_INT:    checkNoWidth(); return dialect.getIntType();
      case BSqlType.SQL_LONG:   checkNoWidth(); return dialect.getLongType();
      case BSqlType.SQL_FLOAT:  checkNoWidth(); return dialect.getFloatType();
      case BSqlType.SQL_DOUBLE: checkNoWidth(); return dialect.getDoubleType();
      case BSqlType.SQL_UUID:   checkNoWidth(); return dialect.getUuidType();
      case BSqlType.SQL_BLOB:   checkNoWidth(); return dialect.getBlobType();

      case BSqlType.SQL_BOOLEAN: 

        checkNoWidth();
        return (dialect.supportsBooleanType()) ?
          dialect.getBooleanType() :
          dialect.getCharType() + "(1)";

      case BSqlType.SQL_TIMESTAMP: 

        checkNoWidth();
        return (dialect.supportsMillisecondTimestamp()) ?
          dialect.getTimestampType() :
          dialect.getLongType();

      case BSqlType.SQL_DATE: 

        checkNoWidth();
        return (dialect.supportsDateType()) ?
          dialect.getDateType() :
          dialect.getTimestampType();

      case BSqlType.SQL_CHAR:
      {    
        if (translator instanceof BFixedWidthTranslator)
        {
          checkNoWidth();
          return dialect.getCharType() + "(" + 
            ((BFixedWidthTranslator) translator).getColumnWidth() + ")";
        }
        else
        {
          checkHasWidth();
          return dialect.getCharType() + "(" + width + ")";
        }
      }

      case BSqlType.SQL_VARCHAR: 
      {    
        if (isClob())
        {
          checkNoWidth();
          return dialect.getClobType();
        }
        else 
        {
          checkHasWidth();
          return dialect.getVarCharType() + "(" + width + ")";
        }
      }

      default: throw new IllegalStateException();
    }
  }

  /**
   * checkNoWidth
   */
  private void checkNoWidth()
  {
    if (width != -1)
      throw new BajaRuntimeException(
        "Illegal WIDTH specification '" + width  + "' for column  '" + name + "'");
  }

  /**
   * checkHasWidth
   */
  private void checkHasWidth()
  {
    if (width == -1)
      throw new BajaRuntimeException(
        "Missing WIDTH specification for column  '" + name + "'");
  }

////////////////////////////////////////////////////////////////
// utility
////////////////////////////////////////////////////////////////

  /**
   * Return whether a Column of the given Type would
   * require a width parameter.
   */
  public static boolean requiresWidth(RdbmsContext context, BValue defaultValue)
  {
    RdbmsDialect dialect = (RdbmsDialect) context;

    BColumnTranslator translator = BColumnTranslator.makeTranslator(dialect, defaultValue);

    switch (translator.getSqlType().getOrdinal())
    {
      case BSqlType.SQL_INT:       return false;
      case BSqlType.SQL_LONG:      return false;
      case BSqlType.SQL_FLOAT:     return false;
      case BSqlType.SQL_DOUBLE:    return false;
      case BSqlType.SQL_UUID:      return false;
      case BSqlType.SQL_BLOB:      return false;
      case BSqlType.SQL_BOOLEAN:   return false;
      case BSqlType.SQL_TIMESTAMP: return false;
      case BSqlType.SQL_DATE:      return false;

      case BSqlType.SQL_CHAR:
      {    
        if (translator instanceof BFixedWidthTranslator) return false;
        else return true;
      }

      case BSqlType.SQL_VARCHAR: 
      {    
        // Actually if its a clob, it would return false.
        // But lets not worry about that.
        return true;
      }

      default: throw new IllegalStateException();
    }
  }

////////////////////////////////////////////////////////////////
// access
////////////////////////////////////////////////////////////////

  private static final int IDENTITY = 0x00000001;
  private static final int KEY      = 0x00000002;
  private static final int UNIQUE   = 0x00000004;
  private static final int CLOB     = 0x00000008;

  public String getName()         { return name;         }
  public Type   getType()         { return type;         }
  public BValue getDefaultValue() { return defaultValue; }
  public int    getWidth()        { return width;        }
  public int    getFlags()        { return flags;        }

  public boolean isIdentity() { return (flags & IDENTITY) != 0; }
  public boolean isKey()      { return (flags & KEY)      != 0; }
  public boolean isUnique()   { return (flags & UNIQUE)   != 0; }
  public boolean isClob()     { return (flags & CLOB)     != 0; }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

  private final String name;
  private final Type   type;
  private final BValue defaultValue;
  private final int    width;

  private final int flags;
}
