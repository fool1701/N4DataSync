/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTypeSpec;

/**
 * BHistorySchema is the set of name-type pairs that describe a single
 * record in a history.  This can be used to guarantee that a history
 * can be read in an environment where the original record type class
 * is not available.
 *
 * Like all simples, BHistorySchema is immutable.  However, for convenience
 * its immutability does not begin until it is accessed for reading the first
 * time. So an instance can be modified using the addColumn() method until
 * any of the getters are called.  An instance also becomes immutable when the
 * BHistorySchema(String[], BTypeSpec[]) constructor is used or when it
 * is created using decode() or decodeFromString().
 *
 * @author    John Sublett
 * @creation  24 Jun 2003
 * @version   $Revision: 3$ $Date: 7/30/08 10:53:55 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BHistorySchema
  extends BSimple
{

  /**
   * Default constructor.  Creates a schema with no columns.
   */
  public BHistorySchema()
  {
    frozen = false;
  }

  /**
   * Constructor.
   *
   * @param newNames The list of column names.
   * @param newTypes The list of column types.
   */
  public BHistorySchema(String[] newNames, BTypeSpec[] newTypes)
  {
    names = new String[newNames.length];
    System.arraycopy(newNames, 0, names, 0, newNames.length);

    types = new BTypeSpec[newTypes.length];
    System.arraycopy(newTypes, 0, types, 0, newTypes.length);
    frozen = true;
  }

////////////////////////////////////////////////////////////////
// Getters
////////////////////////////////////////////////////////////////

  /**
   * Get the number of columns in the schema.
   */
  public int getColumnCount()
  {
    freeze();
    return names.length;
  }

  /**
   * Get the name of the specified column.
   */
  public String getColumnName(int colIndex)
  {
    freeze();
    return names[colIndex];
  }

  /**
   * Get the type of the specified column.
   */
  public BTypeSpec getColumnType(int colIndex)
  {
    freeze();
    return types[colIndex];
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * Hashcode implementation.
   * Added override for this method in Niagara 3.4.
   */
  public int hashCode()
  {
    freeze();

    int hash = 0;

    for (int i = 0; i < names.length; i++)
      hash = 31 * hash + names[i].hashCode();

    for (int i = 0; i < types.length; i++)
      hash = 31 * hash + types[i].hashCode();

    return hash;
  }

  /**
   * Compare two schemas for equality.  Two schemas are equal
   * if the have the same names and types for their columns
   * in the same order.
   */
  public boolean equals(Object o)
  {
    if (o instanceof BHistorySchema) // issue 11846
    {
      BHistorySchema other = (BHistorySchema)o;
      freeze();
      other.freeze();

      if (names.length != other.names.length) return false;
      if (types.length != other.types.length) return false;

      for (int i = 0; i < names.length; i++)
        if (!names[i].equals(other.names[i])) return false;

      for (int i = 0; i < types.length; i++)
        if (!types[i].equals(other.types[i])) return false;

      return true;
    }

    return false;
  }

  /**
   * Encode this instance to the specified output.
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    freeze();
    int colCount = names.length;
    out.writeInt(colCount);
    for (int i = 0; i < colCount; i++)
    {
      out.writeUTF(names[i]);
      types[i].encode(out);
    }
  }

  /**
   * Decode an instance from the specified input.
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    int colCount = in.readInt();
    String[] names = new String[colCount];
    BTypeSpec[] types = new BTypeSpec[colCount];

    for (int i = 0; i < colCount; i++)
    {
      names[i] = in.readUTF();
      types[i] = (BTypeSpec)BTypeSpec.DEFAULT.decode(in);
    }

    BHistorySchema result = new BHistorySchema();
    result.names = names;
    result.types = types;
    result.frozen = true;

    return result;
  }

  /**
   * Encode this instance into a string representation.
   */
  @Override
  public String encodeToString()
    throws IOException
  {
    StringBuilder s = new StringBuilder(64);
    freeze();
    int colCount = names.length;
    for (int i = 0; i < colCount; i++)
    {
      if (i != 0) s.append(';');
      s.append(names[i]);
      s.append(',');
      s.append(types[i].encodeToString());
    }

    return s.toString();
  }

  /**
   * Decode an instance from the specified string.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    Unfrozen incoming = new Unfrozen();
    StringTokenizer st = new StringTokenizer(s, ";");
    while (st.hasMoreTokens())
    {
      String pair = st.nextToken();
      int index = pair.indexOf(',');
      String name = pair.substring(0, index);
      String typeString = pair.substring(index+1);
      incoming.add(name, (BTypeSpec)BTypeSpec.DEFAULT.decodeFromString(typeString));
    }

    BHistorySchema result = new BHistorySchema();
    result.names = incoming.getNames();
    result.types = incoming.getTypes();
    result.frozen = true;

    return result;
  }

////////////////////////////////////////////////////////////////
// Add
////////////////////////////////////////////////////////////////

  /**
   * Add a column to the schema.
   *
   * @param name of the new column.
   * @param type of the new column.
   */
  public synchronized void addColumn(String name, BTypeSpec type)
    throws IllegalStateException
  {
    if (frozen)
      throw new IllegalStateException("This scheme has already been frozen.");

    if (pending == null)
      pending = new Unfrozen();
    pending.add(name, type);
  }

////////////////////////////////////////////////////////////////
// Freeze
////////////////////////////////////////////////////////////////

  /**
   * Freeze the schema.
   */
  private synchronized void freeze()
  {
    if (frozen) return;
    if (pending == null)
    {
      names = new String[0];
      types = new BTypeSpec[0];
    }
    else
    {
      names = pending.getNames();
      types = pending.getTypes();
    }

    frozen = true;
    pending = null;
  }

  /**
   * A convenience class for managing an unfrozen schema.
   */
  private static class Unfrozen
  {
    public Unfrozen()
    {
      nameList = new ArrayList<String>(4);
      typeList = new ArrayList<BTypeSpec>(4);
    }

    /**
     * Add a column.
     */
    public void add(String name, BTypeSpec type)
    {
      nameList.add(name);
      typeList.add(type);
    }

    /**
     * Get the name list.
     */
    public String[] getNames()
    {
      return nameList.toArray(new String[nameList.size()]);
    }

    /**
     * Get the type list.
     */
    public BTypeSpec[] getTypes()
    {
      return typeList.toArray(new BTypeSpec[typeList.size()]);
    }

    private ArrayList<String> nameList;
    private ArrayList<BTypeSpec> typeList;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final BHistorySchema DEFAULT;
  static
  {
    DEFAULT = new BHistorySchema();
    DEFAULT.names = new String[0];
    DEFAULT.types = new BTypeSpec[0];
    DEFAULT.frozen = true;
  }

  private String[] names;
  private BTypeSpec[] types;
  private boolean frozen = false;
  private Unfrozen pending = null;

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  public static final Type TYPE = Sys.loadType(BHistorySchema.class);
  @Override
  public Type getType() { return TYPE; }

}
