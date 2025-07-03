/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
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
import com.tridium.sys.schema.Fw;

/**
 * BPen encapsulates how to stroke the outlines and paths of geometries.
 *
 * <pre> 
 *  pen   := [width] || [cap] || [join] || [dash]
 *  width := num                                (1 is default)
 *  cap   := capButt | capSquare | capRound     (butt is default
 *  join  := joinMiter | joinRound | joinBevel  (miter is default)
 *  dash  := "dash(" num ("," num)* ")"
 * </pre>
 *
 * @author    Brian Frank       
 * @creation  2 Apr 04
 * @version   $Revision: 6$ $Date: 9/30/08 5:09:01 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BPen
  extends BSimple
{

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////
  
  /** Cap option */
  public static final int CAP_BUTT   = 101;
  /** Cap option */
  public static final int CAP_SQUARE = 102;
  /** Cap option */
  public static final int CAP_ROUND  = 103;

  /** Join option */
  public static final int JOIN_MITER = 201;
  /** Join option */
  public static final int JOIN_ROUND = 202;
  /** Join option */
  public static final int JOIN_BEVEL = 203;

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////    

  /**
   * Make a solid, butt, miter pen of the specified width.
   */
  public static BPen make(double width)
  {               
    return make(width, CAP_BUTT, JOIN_MITER, null);
  }

  /**
   * Make a butt, miter pen of the specified width and dash pattern.
   */
  public static BPen make(double width, double[] dash)
  {
    return make(width, CAP_BUTT, JOIN_MITER, dash);
  }

  /**
   * Make a pen of the specified width, cap, join, and dash pattern.
   */
  public static BPen make(double width, int cap, int join, double[] dash)
  {
    return new BPen(width, cap, join, dash);
  }
 
  /**
   * Make from a string encoding.  See class header for format.
   */
  public static BPen make(String s)
  {               
    Parser parser = new Parser(s);
    BPen x = parser.parsePen();     
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
  private BPen(double width, int cap, int join, double[] dash)
  {                                 
    if (width < 0)
      throw new IllegalArgumentException("width " + width);
     
    if (cap != CAP_BUTT && cap != CAP_SQUARE && cap != CAP_ROUND)
      throw new IllegalArgumentException("cap " + cap);
      
    if (join != JOIN_MITER && join != JOIN_ROUND && join != JOIN_BEVEL)
      throw new IllegalArgumentException("join " + join);
    
    if (dash == null)
      dash = noDash;
      
    this.width = width;                                  
    this.cap   = cap;
    this.join  = join;
    this.dash  = dash;
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Return the width of the pen.
   */
  public double getWidth()
  {
    return width;
  }            
  
  /**
   * Get cap constant: CAP_BUTT, CAP_SQUARE, CAP_ROUND
   */
  public int getCap()
  {
    return cap;
  }  

  /**
   * Get join constant: JOIN_MITER, JOIN_ROUND, JOIN_BEVEL
   */
  public int getJoin()
  {
    return join;
  }                         

  /**
   * Get dash pattern as an array of opaque and 
   * transparent segment lengths.
   */
  public double[] getDash()
  { 
    if (dash.length == 0) return dash;              
    return dash.clone();
  }  
          
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  
  
  /**
   * BPen hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    int len = (dash != null)?dash.length:0; 
    long hash = 23L + len;
    for (int i = 0; i < len; i++)
      hash = (hash * 37L) + Double.doubleToRawLongBits(dash[i]);
    hash = (hash * 37L) + Double.doubleToRawLongBits(width);
    hash = (hash * 37L) + cap;
    hash = (hash * 37L) + join;
    return (int)(hash >>> 32) ^ (int)hash;
  }
  
  /**
   * Return if the specified object is an equivalent BPen.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BPen)
    {                      
      BPen x = (BPen)obj;      
      
      if (dash.length != x.dash.length) return false;
      for(int i=0; i<dash.length; ++i)
        if (dash[i] != x.dash[i]) return false;
        
      return width == x.width &&
             cap   == x.cap   &&
             join  == x.join;
    }
    return false;
  }
  
  /**
   * Serialized using writeUTF() of string encoding.
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF( encodeToString() );
  }
  
  /**
   * Unserialized using readUTF() of string encoding.
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString( in.readUTF() );
  }

  /**
   * Encode to string format.  See class header for format.
   */
  public String encodeToString()
    throws IOException
  {        
    if (string == null)
    {                     
      StringBuilder s = new StringBuilder();
      
      s.append(BDouble.encode(width));
      
      if (cap != CAP_BUTT) s.append(' ').append(getCapString());
      
      if (join != JOIN_MITER) s.append(' ').append(getJoinString());
      
      if (dash.length > 0)
      { 
        s.append(" dash(");
        for(int i=0; i<dash.length; ++i)
        {                                   
          if (i > 0) s.append(',');
          s.append(BDouble.encode(dash[i]));
        }
        s.append(')');
      }
      
      string = s.toString();
    }
    return string;     
  }

  String getCapString()
  {
    switch(cap)
    {
      case CAP_BUTT:   return "capButt";
      case CAP_SQUARE: return "capSquare";
      case CAP_ROUND:  return "capRound";
      default: throw new IllegalStateException();
    }
  }

  String getJoinString()
  {
    switch(join)
    {
      case JOIN_MITER: return "joinMiter";
      case JOIN_ROUND: return "joinRound";
      case JOIN_BEVEL: return "joinBevel";
      default: throw new IllegalStateException();
    }
  }
  
  /**
   * Encode from string format.  See class header for format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {                 
    return make(s);
  }                  
                                                                     
////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static double[] noDash = new double[0];

  /**
   * The default pen is solid width of 1.
   */
  public static final BPen DEFAULT = make(1);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
  /*@ $javax.baja.gx.BPen(2979906276)1.0$ @*/
  /* Generated Fri Nov 19 12:18:06 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPen.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Framework Support
////////////////////////////////////////////////////////////////

  /**
   * Framework use only.
   */
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {  
    switch(x)
    {      
      case Fw.GET_AWT: return awtSupport;
      case Fw.SET_AWT: awtSupport = a; return null;
    }
    return super.fw(x, a, b, c, d);      
  }     

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  private double width;   
  private int cap;
  private int join;
  private double[] dash;
  private String string;
  private Object awtSupport;
   
}
