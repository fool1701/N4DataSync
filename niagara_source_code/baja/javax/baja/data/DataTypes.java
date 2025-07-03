/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.data;

import java.io.IOException;
import javax.baja.naming.BOrd;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BDouble;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFloat;
import javax.baja.sys.BIObject;
import javax.baja.sys.BInteger;
import javax.baja.sys.BLong;
import javax.baja.sys.BMarker;
import javax.baja.sys.BNumber;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.Type;
import javax.baja.timezone.BTimeZone;
import javax.baja.units.BUnit;

/**
 * DataTypes define the types used by the Baja Data API.
 * Each DataType is a standard Type which maps to a BSimple.
 * DataTypes are additionally uniquely identified using a
 * single ASCII character (like Flags).
 *
 * @author    Brian Frank
 */
public final class DataTypes
{
  public static boolean otob(BIObject o)
  {
    return o.toDataValue().as(BBoolean.class).getBoolean();
  }

  public static int otoi(BIObject o)
  {
    return o.toDataValue().as(BNumber.class).getInt();
  }

  public static long otol(BIObject o)
  {
    return o.toDataValue().as(BNumber.class).getLong();
  }

  public static float otof(BIObject o)
  {
    return o.toDataValue().as(BNumber.class).getFloat();
  }

  public static double otod(BIObject o)
  {
    return o.toDataValue().as(BNumber.class).getDouble();
  }

  public static String otos(BIObject o)
  {
    try
    {
      return o.toDataValue().encodeToString();
    }
    catch (IOException e)
    {
      return o.toString();
    }
  }

  private DataTypes()
  {
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  //
  // To add a DataType:
  //  1) Implement a BSimple class that implements the BIDataValue interface.
  //  2) Override the toDataValue() method to return this.
  //  3) Add constant here
  //  4) Add to TYPES array here
  //  5) Add symbol to com.tridium.sys.scheme.SimpleIntrospector
  //  6) Add to test:DataTypesTest
  //

  /** Boolean: 'b' */
  public static final Type BOOLEAN = BBoolean.TYPE;
  
  /** Integer: 'i' */
  public static final Type INTEGER = BInteger.TYPE;

  /** Long: 'l' */
  public static final Type LONG = BLong.TYPE;
  
  /** Float: 'f' */
  public static final Type FLOAT = BFloat.TYPE;

  /** Double: 'd' */
  public static final Type DOUBLE = BDouble.TYPE;
  
  /** String: 's' */
  public static final Type STRING = BString.TYPE;

  /** Enum: 'e' */
  public static final Type ENUM = BDynamicEnum.TYPE;

  /** EnumRange: 'E' */
  public static final Type ENUM_RANGE = BEnumRange.TYPE;
  
  /** AbsTime: 'a' */
  public static final Type ABS_TIME = BAbsTime.TYPE;
  
  /** RelTime: 'r' */
  public static final Type REL_TIME = BRelTime.TYPE;
  
  /** Unit: 'u' */
  public static final Type UNIT = BUnit.TYPE;  

  /** Unit: 'z' */
  public static final Type TIME_ZONE = BTimeZone.TYPE;  

  /** Ord: 'o' */
  public static final Type ORD = BOrd.TYPE;

  /** Marker: 'm' */
  public static final Type MARKER = BMarker.TYPE;

  
  private static final Type[] TYPES =
  {
    BOOLEAN,
    INTEGER,
    LONG,
    FLOAT,
    DOUBLE,
    STRING,
    ENUM,
    ENUM_RANGE,
    ABS_TIME,
    REL_TIME,
    UNIT,
    TIME_ZONE,
    ORD,
    MARKER,
  };

////////////////////////////////////////////////////////////////
// Lookup
////////////////////////////////////////////////////////////////

  /**
   * Get the predefined list of Data Types.
   */
  public static Type[] getTypes()
  {
    return TYPES.clone();
  }
  
  /**
   * Get a Data Type by it symbol, or return null if not found.
   */
  public static Type getBySymbol(char symbol)
  {
    if (symbol < 128)
    {
      return bySymbol[symbol];
    }
    return null;
  }

  // lookup tables
  private static final Type[] bySymbol = new Type[128];
  static
  {
    for (Type type : TYPES)
    {
      bySymbol[type.getDataTypeSymbol()] = type;
    }
  }
  
}
