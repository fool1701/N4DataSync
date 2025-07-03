/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.gx.parser.Parser;

/**
 * BPathGeom stores a general path composed of Segments.
 * The model and string format of PathGeom maps to the SVG 
 * specification.  A path is a list of segments identified
 * with a single ASCII letter.  If the letter is uppercase
 * then the segment uses absolute coordinates, if lowercase
 * then relative coordinates.  Multiple segments with the
 * same segment command may omit the command letter. The list
 * of commands:
 *
 * <pre> 
 *   closePath     := Z|z
 *   moveTo        := M|m x y
 *   lineTo        := L|l x y
 *   hlineTo       := H|h x 
 *   vlineTo       := V|v y 
 *   curveTo       := C|c x2 y2 x1 y1 x y 
 *   smoothCurveTo := S|s x2 y2 x y 
 *   quadTo        := Q|q x1 y1 x y 
 *   smoothQuadTo  := T|t x y 
 *   arcTo         := A|a rx ry xAxisRotation largeArcFlag sweepFlag x y 
 * </pre>
 *
 * @author    Brian Frank       
 * @creation  5 Apr 04
 * @version   $Revision: 6$ $Date: 9/30/08 5:09:01 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BPathGeom
  extends BGeom
  implements IPathGeom
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public static BPathGeom make(Segment[] segments) 
  {
    return new BPathGeom(segments.clone());
  }

  /**
   * Constructor.
   */
  public static BPathGeom make(IPathGeom g) 
  {
    if (g instanceof BPathGeom) return (BPathGeom)g;
    return make(g.segments());
  }

  /**
   * Make from string encoding.
   */
  public static BPathGeom make(String s)
  {               
    Parser parser = new Parser(s, true);
    BPathGeom x = parser.parsePath();
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
  private BPathGeom(Segment[] segments) 
  {
    this.segments = segments;
  }

////////////////////////////////////////////////////////////////
// IGeom
////////////////////////////////////////////////////////////////
  
  /**
   * Return PATH.
   */
  public int getGeomCase() { return PATH; }

////////////////////////////////////////////////////////////////
// IPathGeom
////////////////////////////////////////////////////////////////

  /** Get a copy of the segments list. */
  public Segment[] segments() { return segments.clone(); }
  
  /** Get segment at specified index. */
  public Segment segment(int index) { return segments[index]; }
  
  /** Get the number of segments. */
  public int size() { return segments.length; }
  
////////////////////////////////////////////////////////////////
// Simple
////////////////////////////////////////////////////////////////  

  /**
   * A null path is one without any segments.
   */
  public boolean isNull()
  {
    return segments.length == 0;
  }
  
  /**
   * BPathGeom hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    int len = (segments != null)?segments.length:0; 
    int hash = 23 + len;
    for (int i = 0; i < len; i++)
    {
      String segStr = (segments[i] != null)?segments[i].toString():null;
      hash = (hash * 37) + ((segStr != null)?segStr.hashCode():0);
    }
    return hash;
  }
  
  /**
   * Return if the specified object is an equivalent BPathGeom.
   */                      
  public boolean equals(Object obj)
  {
    if (obj instanceof BPathGeom)
    {
      BPathGeom o = (BPathGeom)obj;
      if (segments.length != o.segments.length) return false;
      return PathGeom.equals(segments, o.segments, segments.length);
    }
    return false;
  }
   
  /**
   * Encode to string.
   */
  public String encodeToString()
    throws IOException
  {                             
    if (string == null) 
      string = PathGeom.toString(segments, segments.length);
    return string;         
  }

  /**
   * Decode from string.
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
   * The default path has no segments.
   */
  public static final BPathGeom DEFAULT = new BPathGeom(new Segment[0]);

  /**
   * The null path is the same as the default.
   */
  public static final BPathGeom NULL = DEFAULT;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
  /*@ $javax.baja.gx.BPathGeom(2979906276)1.0$ @*/
  /* Generated Fri Nov 19 12:18:06 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPathGeom.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  Segment[] segments;
  String string;  
}
