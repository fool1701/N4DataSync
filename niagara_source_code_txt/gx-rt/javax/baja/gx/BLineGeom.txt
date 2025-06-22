/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BDouble;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.gx.parser.Parser;

/**
 * BLineGeom stores a line between two points.
 *
 * @author    Brian Frank       
 * @creation  5 Apr 04
 * @version   $Revision: 4$ $Date: 9/30/08 5:09:01 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BLineGeom
  extends BGeom
  implements ILineGeom
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public static BLineGeom make(double x1, double y1, double x2, double y2) 
  {
    return new BLineGeom(x1, y1, x2, y2);
  }

  /**
   * Constructor.
   */
  public static BLineGeom make(ILineGeom g) 
  {
    if (g instanceof BLineGeom) return (BLineGeom)g;
    return make(g.x1(), g.y1(), g.x2(), g.y2());
  }

  /**
   * Make from string encoding.
   */
  public static BLineGeom make(String s)
  {               
    Parser parser = new Parser(s);
    BLineGeom x = parser.parseLine();
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
  private BLineGeom(double x1, double y1, double x2, double y2) 
  {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

////////////////////////////////////////////////////////////////
// IGeom
////////////////////////////////////////////////////////////////
  
  /**
   * Return LINE.
   */
  public int getGeomCase() { return LINE; }

////////////////////////////////////////////////////////////////
// ILineGeom
////////////////////////////////////////////////////////////////

  /** The x coordinate of first point */
  public double x1()  { return x1; }

  /** The y coordinate of first point */
  public double y1()  { return y1; }

  /** The x coordinate of second point */
  public double x2() { return x2; }
  
  /** The y coordinate of second point */
  public double y2() { return y2; }

////////////////////////////////////////////////////////////////
// Simple
////////////////////////////////////////////////////////////////  

  /**
   * Is this the null line.
   */
  public boolean isNull()
  {
    return this == NULL;
  }
  
  /**
   * BLineGeom hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    long hash = 23L + Double.doubleToRawLongBits(x1);
    hash = (hash * 37L) + Double.doubleToRawLongBits(y1);
    hash = (hash * 37L) + Double.doubleToRawLongBits(x2);
    hash = (hash * 37L) + Double.doubleToRawLongBits(y2);
    return (int)(hash >>> 32) ^ (int)hash;
  }
  
  /**
   * Return if the specified object is an equivalent BLineGeom.
   */                      
  public boolean equals(Object obj)
  {
    if (obj instanceof BLineGeom)
    {
      BLineGeom o = (BLineGeom)obj;
      return this.x1 == o.x1 && this.y1 == o.y1 &&
             this.x2 == o.x2 && this.y2 == o.y2;
    }
    return false;
  }
   
  /**
  * Encode as "x1,y1 x2,y2"
  */
  public String encodeToString()
    throws IOException
  {
    if (isNull()) return "null";
    StringBuilder sb = new StringBuilder();
    sb.append(BDouble.encode(x1)).append(',')
      .append(BDouble.encode(y1)).append(' ')
      .append(BDouble.encode(x2)).append(',')
      .append(BDouble.encode(y2));
    return sb.toString();         
  }

  /**
   * Decode as "x1,y1 x2,y2"
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
   * The default is 0,0 0,0.
   */
  public static final BLineGeom DEFAULT = new BLineGeom(0, 0, 0, 0);

  /**
   * The null instance.
   */
  public static final BLineGeom NULL = new BLineGeom(0, 0, 0, 0);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
  /*@ $javax.baja.gx.BLineGeom(2979906276)1.0$ @*/
  /* Generated Fri Nov 19 12:18:06 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLineGeom.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  /** The x coordinate of first point */
  public final double x1;
  
  /** The y coordinate of first point */
  public final double y1;

  /** The x coordinate of second point */
  public final double x2;
  
  /** The y coordinate of second point */
  public final double y2;

    
}
