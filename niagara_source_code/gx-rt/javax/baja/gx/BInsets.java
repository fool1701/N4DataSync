/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
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

import com.tridium.gx.parser.Parser;

/**
 * BInsets stores offsets from the four sides of a rectangle.
 * String encoding matches CSS margin and padding shortcut
 * properties where one, two, three, or all four sides can
 * be specified:
 * <pre>
 *  1       (shortcut for 1 1 1 1)
 *  1 2     (shortcut for 1 2 1 2)
 *  1 2 3   (shortcut for 1 2 3 2)
 *  1 2 3 4 (top right bottom left)
 * </pre>
 *
 * @author    Brian Frank       
 * @creation  30 Dec 02
 * @version   $Revision: 11$ $Date: 9/30/08 5:09:01 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BInsets
  extends BSimple
  implements IInsets
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Create with each side.
   */
  public static BInsets make(double top, double right, double bottom, double left) 
  {
    return new BInsets(top, right, bottom, left);
  }

  /**
   * Create with all sides being equal.
   */
  public static BInsets make(double side) 
  {
    return new BInsets(side, side, side, side);
  }

  /**
   * Create copy from specified insets.
   */
  public static BInsets make(IInsets i) 
  {
    if (i instanceof BInsets) return (BInsets)i;
    return make(i.top(), i.right(), i.bottom(), i.left());
  }

  /**
   * Make from its string encoding.
   */
  public static BInsets make(String s)
  {                       
    Parser parser = new Parser(s);
    BInsets x = parser.parseInsets();
    if (x == null || !parser.isEnd())
      throw new IllegalArgumentException(s);
    return x;
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BInsets(double top, double right, double bottom, double left) 
  {
    this.top    = top;
    this.right  = right;
    this.bottom = bottom;    
    this.left   = left;
  }

////////////////////////////////////////////////////////////////
// IInsets
////////////////////////////////////////////////////////////////  

  /** Offset from top edge */
  public double top() { return top; }
  
  /** Offset from right edge */
  public double right()  { return right; }
  
  /** Offset from bottom edge */
  public double bottom()  { return bottom; }

  /** Offset from left edge */
  public double left()  { return left; }
  
////////////////////////////////////////////////////////////////
// Simple
////////////////////////////////////////////////////////////////  

  /**
   * Is this the null insets.
   */
  public boolean isNull()
  {
    return this == NULL;
  }
  
  /**
   * BInsets hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    long hash = 23L + Double.doubleToRawLongBits(top);
    hash = (hash * 37L) + Double.doubleToRawLongBits(bottom);
    hash = (hash * 37L) + Double.doubleToRawLongBits(left);
    hash = (hash * 37L) + Double.doubleToRawLongBits(right);
    return (int)(hash >>> 32) ^ (int)hash;
  }
  
  /**
   * Return if the specified object is an equivalent BInsets.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BInsets)
    {
      BInsets i = (BInsets)obj;
      return this.top == i.top && this.bottom == i.bottom &&
             this.left == i.left && this.right == i.right;
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
  * Encode as string
  */
  public String encodeToString()
    throws IOException
  {
    if (isNull()) return "null";
    if (top == bottom && left == right)
    {
      if (top == right)
        return BDouble.encode(top);
      else
        return BDouble.encode(top) + ' ' + BDouble.encode(right);
    }
    else
    {
      return BDouble.encode(top) + ' ' + BDouble.encode(right) + ' ' + 
             BDouble.encode(bottom) + ' ' + BDouble.encode(left);
    }
  }

  /**
   * Decode from string
   */
  public BObject decodeFromString(String s)
    throws IOException
  {               
    return make(s);
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  /**
   * The default insets are 0, 0, 0, 0.
   */
  public static final BInsets DEFAULT = new BInsets(0, 0, 0, 0);

  /**
   * The null instance.
   */
  public static final BInsets NULL = new BInsets(0, 0, 0, 0);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
  /*@ $javax.baja.gx.BInsets(2979906276)1.0$ @*/
  /* Generated Fri Nov 19 12:18:06 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BInsets.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  /** Offset from top edge */
  public final double top;
  
  /** Offset from right edge */
  public final double right;
  
  /** Offset from bottom edge */
  public final double bottom;

  /** Offset from left edge */
  public final double left;  
}
