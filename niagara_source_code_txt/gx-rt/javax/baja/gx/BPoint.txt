/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

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

/**
 * BPoint stores an x and y coordinate.
 *
 * @author    Brian Frank       
 * @creation  10 Jan 03
 * @version   $Revision: 7$ $Date: 9/30/08 5:09:01 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BPoint
  extends BSimple
  implements IPoint
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public static BPoint make(double x, double y) 
  {
    return new BPoint(x, y);
  }

  /**
   * Constructor.
   */
  public static BPoint make(IPoint pt) 
  {
    return make(pt.x(), pt.y());
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BPoint(double x, double y) 
  {
    this.x = x;
    this.y = y;
  }

////////////////////////////////////////////////////////////////
// IPoint
////////////////////////////////////////////////////////////////  

  /** The x coordinate */
  public double x() { return x; }
  
  /** The y coordinate */
  public double y()  { return y; }
  
////////////////////////////////////////////////////////////////
// Simple
////////////////////////////////////////////////////////////////  

  /**
   * Is this the null instance.
   */
  public boolean isNull()
  {
    return this == NULL;
  }
  
  /**
   * BPoint hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    long hash = 23L + Double.doubleToRawLongBits(x);
    hash = (hash * 37L) + Double.doubleToRawLongBits(y);
    return (int)(hash >>> 32) ^ (int)hash;
  }
  
  /**
   * Return if the specified object is an equivalent BPoint.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BPoint)
    {
      BPoint p = (BPoint)obj;
      return this.x == p.x && this.y == p.y;
    }
    return false;
  }

  /**
   * Encode using writeUTF
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }
  
  /**
   * Decode using readUTF
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString(in.readUTF());
  }

  /**
  * Encode as "x,y"
  */
  public String encodeToString()
    throws IOException
  {
    if (isNull()) return "null";
    StringBuilder sb = new StringBuilder();
    sb.append(x).append(',').append(y);
    return sb.toString();         
  }

  /**
   * Decode as "x,y"
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    if (s.equals("null")) return NULL;
    int comma = s.indexOf(',');
    return make(BDouble.decode(s.substring(0, comma)), 
                BDouble.decode(s.substring(comma+1)));
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  /**
   * The default is 0, 0.
   */
  public static final BPoint DEFAULT = new BPoint(0, 0);

  /**
   * The null instance.
   */
  public static final BPoint NULL = new BPoint(0, 0);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
  /*@ $javax.baja.gx.BPoint(2979906276)1.0$ @*/
  /* Generated Fri Nov 19 12:18:06 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPoint.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  /** The x coordinate */
  public final double x;
  
  /** The y coordinate */
  public final double y;
  
}
