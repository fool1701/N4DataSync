/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.util.Objects;

// hashCode method not implemented. @SuppressWarnings("overrides") added for that. Class Segment
// used in equals method does not implement hashCode, so any PathGeom hashCode that needs segments
// as parameters is worthless.

/**
 * PathGeom stores a mutable general path composed of Segments.
 *
 * @author    Brian Frank       
 * @creation  5 Apr 04
 * @version   $Revision: 4$ $Date: 3/28/05 9:47:18 AM EST$
 * @since     Baja 1.0
 */
@SuppressWarnings("overrides")
public final class PathGeom
  extends Geom
  implements IPathGeom
{ 

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Copy constructor
   */
  public PathGeom(IPathGeom path)
  {
    this.segments = path.segments();
    this.size     = path.size();
  }

  /**
   * Constructor with segments
   */
  public PathGeom(Segment[] segments, int size) 
  {
    this.segments = segments.clone();
    this.size     = size;
  }             

  /**
   * Constructor with zero segments
   */
  public PathGeom() 
  {               
    this.segments = new Segment[10];
    this.size     = 0;
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
  public Segment[] segments()
  {
    Segment[] copy = new Segment[size];
    System.arraycopy(segments, 0, copy, 0, size);
    return copy;
  }
  
  /** Get segment at specified index. */
  public Segment segment(int index)
  {
    if (index >= size) throw new ArrayIndexOutOfBoundsException(index);
    return segments[index];
  }
  
  /** Get the number of segments. */
  public int size()
  {
    return size;
  }          

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Add ClosePath segment.
   */
  public void closePath()
  {                     
    add(new ClosePath());
  }  
  
  /**
   * Add MoveTo segment.
   */
  public void moveTo(boolean abs, double x, double y)
  {                     
    add(new MoveTo(abs, x, y));
  }  

  /**
   * Add LineTo segment.
   */
  public void lineTo(boolean abs, double x, double y)
  {                     
    add(new LineTo(abs, x, y));
  }  

  /**
   * Add HLineTo segment.
   */
  public void hlineTo(boolean abs, double x)
  {                     
    add(new HLineTo(abs, x));
  }  

  /**
   * Add VLineTo segment.
   */
  public void vlineTo(boolean abs, double y)
  {                     
    add(new VLineTo(abs, y));
  }  

  /**
   * Add CurveTo segment.
   */
  public void curveTo(boolean abs, double x2, double y2, 
                      double x1, double y1, double x, double y)
  {                     
    add(new CurveTo(abs, x2, y2, x1, y1, x, y));
  }  

  /**
   * Add SmoothCurveTo segment.
   */
  public void smoothCurveTo(boolean abs, double x2, double y2, 
                     double x, double y)
  {                     
    add(new SmoothCurveTo(abs, x2, y2, x, y));
  }  

  /**
   * Add QuadTo segment.
   */
  public void quadTo(boolean abs, double x1, double y1, 
                     double x, double y)
  {                     
    add(new QuadTo(abs, x1, y1, x, y));
  }  

  /**
   * Add SmoothQuadTo segment.
   */
  public void smoothQuadTo(boolean abs, double x, double y)
  {                     
    add(new SmoothQuadTo(abs, x, y));
  }  

  /**
   * Add ArcTo segment.
   */
  public void arcTo(boolean abs, double rx, double ry, double xAxisRotation,
                    boolean largeArcFlag, boolean sweepFlag,
                    double x, double y)
  {                     
    add(new ArcTo(abs, rx, ry, xAxisRotation, largeArcFlag, sweepFlag, x, y));
  }  
  
  /**
   * Add a segment to the end of the list.
   */
  public void add(Segment segment)
  {                                       
    if (size >= segments.length)
    {
      int newSize = Math.max(size*2, 4);
      Segment[] temp = new Segment[newSize];
      System.arraycopy(segments, 0, temp, 0, size);
      segments = temp;
    }
    segments[size++] = segment; 
    dirty();
  }         
  
  /**
   * Reset the size to zero.
   */
  public void clear()
  {                         
    size = 0;
    dirty();
  }

  /**
   * Return if the specified object is an equivalent PathGeom.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof PathGeom)
    {
      PathGeom o = (PathGeom)obj;
      if (size != o.size) return false;
      return equals(segments, o.segments, size);
    }
    return false;
  }

  /**
   * Encode to string.
   */
  public String toString()
  {                   
    return toString(segments, size);          
  }

////////////////////////////////////////////////////////////////
// Shared Utils
////////////////////////////////////////////////////////////////

  static boolean equals(Segment[] a, Segment[] b, int size)
  {
    for(int i=0; i<size; ++i)
      if (!a[i].equals(b[i])) return false;
    return true;
  }

  static String toString(Segment[] segments, int size)
  {                                     
    Segment lastSeg = null;
    char lastCommand = 0;
    StringBuffer s = new StringBuffer();
    for(int i=0; i<size; ++i)
    { 
      if (i > 0) s.append(' ');
      Segment thisSeg = segments[i];
      char thisCommand = thisSeg.getCommand();
      if (lastCommand != thisCommand) 
      {
        s.append(thisCommand); 
        lastCommand = thisCommand;
      }                
      thisSeg.toString(s);
      lastSeg = thisSeg;
    }
    return s.toString();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private Segment[] segments;
  private int size;
}
