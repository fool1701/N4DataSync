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
 * BSize stores a width and height
 *
 * @author    Brian Frank       
 * @creation  10 Jan 03
 * @version   $Revision: 9$ $Date: 9/30/08 5:09:01 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BSize
  extends BSimple
  implements ISize
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public static BSize make(double width, double height) 
  {
    return new BSize(width, height);
  }

  /**
   * Constructor.
   */
  public static BSize make(ISize d) 
  {
    if (d instanceof BSize) return (BSize)d;
    return make(d.width(), d.height());
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BSize(double width, double height) 
  {
    this.width = width;
    this.height = height;
  }

////////////////////////////////////////////////////////////////
// ISize
////////////////////////////////////////////////////////////////  

  /** The width */
  public double width() { return width; }
  
  /** The height */
  public double height() { return height; }
  
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
   * BSize hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    long hash = 23L + Double.doubleToRawLongBits(width);
    hash = (hash * 37L) + Double.doubleToRawLongBits(height);
    return (int)(hash >>> 32) ^ (int)hash;
  }
  
  /**
   * Return if the specified object is an equivalent BSize.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BSize)
    {
      BSize r = (BSize)obj;
      return this.width == r.width && this.height == r.height;
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
  * Encode as "width,height"
  */
  public String encodeToString()
    throws IOException
  {
    if (isNull()) return "null";
    StringBuilder sb = new StringBuilder();
    sb.append(width).append(',').append(height);
    return sb.toString();         
  }

  /**
   * Decode as "width,height"
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    if (s.equals("null")) return NULL;
    int c = s.indexOf(',');
    return make(BDouble.decode(s.substring(0, c)), 
                BDouble.decode(s.substring(c+1)));
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  /**
   * The default is 0, 0.
   */
  public static final BSize DEFAULT = new BSize(0, 0);

  /**
   * The null instance
   */
  public static final BSize NULL = new BSize(0, 0);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
  /*@ $javax.baja.gx.BSize(2979906276)1.0$ @*/
  /* Generated Fri Nov 19 12:18:06 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSize.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  /** The width */
  public final double width;

  /** The height */
  public final double height;
    
}
