/*
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BDouble;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.gx.parser.Parser;

/**
 * BLayoutDimension stores an explicit layout dimension.
 * The dimensions may be absolute, expressed as percentage
 * of the parent pane, or set to use their preferred size.
 *
 * @author    JJ Frankovich
 * @since     Niagara 4.6
 */
@NiagaraType
@NoSlotomatic
public final class BLayoutDimension
  extends BSimple
{
////////////////////////////////////////////////////////////////
// Units
////////////////////////////////////////////////////////////////

  public static final int ABS     = 0;
  public static final int PERCENT = 1;
  public static final int PREF    = 2;
  /**
   * The default layout is 0 Abs.
   */
  public static final BLayoutDimension DEFAULT = make(0, ABS);
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BLayoutDimension(2979906276)1.0$ @*/
/* Generated Thu Nov 18 12:29:49 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLayoutDimension.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Make a layout dimension using the specified dimension and
   * its unit constant
   */
  public static BLayoutDimension make(double value, int unit)
  {
    if (!isWH(unit))
      throw new IllegalArgumentException("Unit is unknown: " + unit);

    BLayoutDimension layout = new BLayoutDimension();
    layout.value = value;
    layout.unit = unit;
    return layout;
  }

  /**
   * Make a layout using the specified dimension and
   * the ABS unit constant.
   */
  public static BLayoutDimension makeAbs(double value)
  {
    return make(value, ABS);
  }

  static boolean isWH(int unit) { return unit == ABS || unit == PERCENT || unit == PREF; }

  /**
   * Make a layout dimension from its string encoding.
   */
  public static BLayoutDimension make(String s)
  {
    try
    {
      Parser parser = new Parser(s);
      BLayoutDimension layout = new BLayoutDimension();

      // width
      layout.value = parser.cur.num;
      if (parser.cur.num())
        layout.unit = ABS;
      else if (parser.cur.dimen("%"))
        layout.unit = PERCENT;
      else if (parser.cur.id("pref"))
        layout.unit = PREF;
      else
        throw new Exception();
      return layout;
    }
    catch(Exception e)
    {
      throw new IllegalArgumentException(s);
    }
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor
   */
  private BLayoutDimension()
  {
  }              
  
////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////
  
  /**
   * Get the value scalar.
   */
  public double getValue() { return value; }

  /**
   * Get the unit constant.
   */
  public int getUnit() { return unit; }
    
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////
  
  /**
   * BLayoutDimension hash code.
   */
  public int hashCode()
  {
    long hash = 23L + Double.doubleToRawLongBits(value);
    hash = (hash * 37L) + unit;
    return (int)(hash >>> 32) ^ (int)hash;
  }
  
  /**
   * Equality is based on equivalent of value and unit attributes.
   */
  public boolean equals(Object object)
  {
    if (object instanceof BLayoutDimension)
    {
      BLayoutDimension o = (BLayoutDimension)object;
      return value == o.value && unit == o.unit;
    }
    return false;
  }
  
  /**
   * BLayoutDimension is serialized writeUTF() of its string encoding.
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }
  
  /**
   * BLayoutDimension is unserialized using readUTF() of its string encoding.
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return make(in.readUTF());
  }

  /**
   * Write the primitive in String format.
   */
  public String encodeToString()
  {
    if (string == null)
    {               
      StringBuilder s = new StringBuilder();
      
      // width
      if (unit == PREF) s.append("pref");
      else
      {
        s.append(BDouble.encode(value));
        if (unit == PERCENT) s.append('%');
      }
      
      string = s.toString();    
    }
    return string;
  }

  /**
   * Read the primitive from String format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    return make(s);
  }
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  private double value;
  private int unit;
  private String string;
}
