/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import javax.baja.sys.BDouble;

/**
 * Interface for immutable BPathGeom and mutable PathGeom.
 *
 * @author    Brian Frank       
 * @creation  5 Apr 04
 * @version   $Revision: 2$ $Date: 4/7/04 3:06:45 PM EDT$
 * @since     Baja 1.0
 */
public interface IPathGeom
  extends IGeom
{ 

////////////////////////////////////////////////////////////////
// IPathGeom
////////////////////////////////////////////////////////////////
  
  /** Get a copy of the segments list. */
  public Segment[] segments();          
  
  /** Get segment at specified index. */
  public Segment segment(int index);          
  
  /** Get the number of segments. */
  public int size();          

////////////////////////////////////////////////////////////////
// Segment
////////////////////////////////////////////////////////////////
  
  /**
   * Segment is the base class for pathing primitives.
   */
  public static abstract class Segment
  { 
    /**
     * Package private
     */                                  
    Segment(boolean absolute)
    {
      this.absolute = absolute;                 
    }                                                

    /**
     * Return the command as a lower/capital letter.
     */
    public abstract char getCommand();
    
    /**
     * Return if this segment's coordinates are absolute.
     */
    public final boolean isAbsolute() { return absolute; }
    
    /**
     * Return if this segment's coordinates are 
     * relative to the last segment.
     */
    public final boolean isRelative() { return !absolute; }
        
    /**
     * Object equality.
     */
    public final boolean equals(Object obj)
    {                  
      if (getClass() == obj.getClass())
      {            
        Segment seg = (Segment)obj;
        if (absolute != seg.absolute) return false;
        return doEquals(seg);
      }
      return false;
    } 
    
    /**
     * Get the string encoding.
     */
    public final String toString()
    {                          
      StringBuffer s = new StringBuffer();
      s.append(getCommand());
      toString(s);
      return s.toString(); 
    }

    /**
     * Subclass equality.
     */
    abstract boolean doEquals(Segment seg);
    
    /**
     * Encode the segment to the string buffer.
     */
    abstract void toString(StringBuffer s);
    
    boolean absolute;  
  } 

////////////////////////////////////////////////////////////////
// ClosePath
////////////////////////////////////////////////////////////////

  /**
   * ClosePath closed the current shape.
   */
  public static class ClosePath extends Segment
  { 
    /**
     * Constructor
     */                     
    public ClosePath()
    {
      super(false);
    }

    /**
     * Return Z
     */
    public char getCommand() { return 'Z'; }
    
    /**
     * Equality
     */
    boolean doEquals(Segment seg)
    {
      return true;
    }
    
    /**
     * Encode the segment to the string buffer.
     */
    void toString(StringBuffer s)
    {
    }            
  } 

////////////////////////////////////////////////////////////////
// MoveTo
////////////////////////////////////////////////////////////////

  /**
   * MoveTo moves the pen to the specified point.
   */
  public static class MoveTo extends Segment
  { 
    /**
     * Constructor
     */                     
    public MoveTo(boolean absolute, double x, double y)
    {
      super(absolute);
      this.x = x;
      this.y = y;
    }

    /**
     * Return M or m
     */
    public char getCommand() { return absolute ? 'M' : 'm'; }

    /**
     * Get the x coordinate.
     */
    public double getX() { return x; }

    /**
     * Get the y coordinate.
     */
    public double getY() { return y; }

    /**
     * Equality
     */
    boolean doEquals(Segment seg)
    {
      MoveTo s = (MoveTo)seg;
      return x == s.x && y == s.y;
    }
        
    /**
     * Encode the segment to the string buffer.
     */
    void toString(StringBuffer s)
    {
      s.append(BDouble.encode(x));
      s.append(',');
      s.append(BDouble.encode(y));
    }            
    
    double x, y;
  } 

////////////////////////////////////////////////////////////////
// LineTo
////////////////////////////////////////////////////////////////
  
  /**
   * LineTo draws a line to the specified point.
   */
  public static class LineTo extends Segment
  {
    /**
     * Constructor
     */                     
    public LineTo(boolean absolute, double x, double y)
    {
      super(absolute);
      this.x = x;
      this.y = y;
    }

    /**
     * Return L or l
     */
    public char getCommand() { return absolute ? 'L' : 'l'; }

    /**
     * Get the x coordinate.
     */
    public double getX() { return x; }

    /**
     * Get the y coordinate.
     */
    public double getY() { return y; }

    /**
     * Equality
     */
    boolean doEquals(Segment seg)
    {
      LineTo s = (LineTo)seg;
      return x == s.x && y == s.y;
    }
            
    /**
     * Encode the segment to the string buffer.
     */
    void toString(StringBuffer s)
    {
      s.append(BDouble.encode(x))
       .append(',')
       .append(BDouble.encode(y));
    }           
    
    double x, y;          
  } 

////////////////////////////////////////////////////////////////
// HLineTo
////////////////////////////////////////////////////////////////
  
  /**
   * HLineTo draws a line a horizontal line.
   */
  public static class HLineTo extends Segment
  {
    /**
     * Constructor
     */                     
    public HLineTo(boolean absolute, double x)
    {
      super(absolute);
      this.x = x;
    }

    /**
     * Return H or h
     */
    public char getCommand() { return absolute ? 'H' : 'h'; }

    /**
     * Get the x coordinate.
     */
    public double getX() { return x; }

    /**
     * Equality
     */
    boolean doEquals(Segment seg)
    {
      HLineTo s = (HLineTo)seg;
      return x == s.x;
    }
            
    /**
     * Encode the segment to the string buffer.
     */
    void toString(StringBuffer s)
    {
      s.append(BDouble.encode(x));
    }           
    
    double x;          
  }            
  
////////////////////////////////////////////////////////////////
// VLineTo
////////////////////////////////////////////////////////////////
  
  /**
   * VLineTo draws a line a horizontal line.
   */
  public static class VLineTo extends Segment
  {
    /**
     * Constructor
     */                     
    public VLineTo(boolean absolute, double y)
    {
      super(absolute);
      this.y = y;
    }

    /**
     * Return V or v
     */
    public char getCommand() { return absolute ? 'V' : 'v'; }

    /**
     * Get the y coordinate.
     */
    public double getY() { return y; }

    /**
     * Equality
     */
    boolean doEquals(Segment seg)
    {
      VLineTo s = (VLineTo)seg;
      return y == s.y;
    }
    
    /**
     * Encode the segment to the string buffer.
     */
    void toString(StringBuffer s)
    {
      s.append(BDouble.encode(y));
    }           
    
    double y;          
  } 

////////////////////////////////////////////////////////////////
// CurveTo
////////////////////////////////////////////////////////////////
  
  /**
   * CurveTo draws a cubic Bezier curve.
   */
  public static class CurveTo extends Segment
  {
    /**
     * Constructor
     */                     
    public CurveTo(boolean absolute, double x1, double y1, 
                   double x2, double y2, double x, double y)
    {
      super(absolute);
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
      this.x = x;
      this.y = y;
    }

    /**
     * Return C or c
     */
    public char getCommand() { return absolute ? 'C' : 'c'; }

    /**
     * Get the x coordinate of first control point.
     */
    public double getX1() { return x1; }

    /**
     * Get the y coordinate of first control point.
     */
    public double getY1() { return y1; }

    /**
     * Get the x coordinate of second control point.
     */
    public double getX2() { return x2; }

    /**
     * Get the y coordinate of second control point.
     */
    public double getY2() { return y2; }

    /**
     * Get the x coordinate.
     */
    public double getX() { return x; }

    /**
     * Get the y coordinate.
     */
    public double getY() { return y; }

    /**
     * Equality
     */
    boolean doEquals(Segment seg)
    {
      CurveTo s = (CurveTo)seg;
      return x1 == s.x1 && y1 == s.y1 &&
             x2 == s.x2 && y2 == s.y2 &&
             x == s.x   && y == s.y;
    }
            
    /**
     * Encode the segment to the string buffer.
     */
    void toString(StringBuffer s)
    {
      s.append(BDouble.encode(x1))
        .append(',')
        .append(BDouble.encode(y1))
      .append(' ')
        .append(BDouble.encode(x2))
        .append(',')
        .append(BDouble.encode(y2))
      .append(' ')
        .append(BDouble.encode(x))
        .append(',')
        .append(BDouble.encode(y));
    }           
    
    double x1, y1, x2, y2, x, y;          
  } 

////////////////////////////////////////////////////////////////
// SmoothCurveTo
////////////////////////////////////////////////////////////////
  
  /**
   * SmoothCurveTo draws a smooth cubic Bezier curve.
   */
  public static class SmoothCurveTo extends Segment
  {
    /**
     * Constructor
     */                     
    public SmoothCurveTo(boolean absolute, double x2, double y2, 
                         double x, double y)
    {
      super(absolute);
      this.x2 = x2;
      this.y2 = y2;
      this.x = x;
      this.y = y;
    }

    /**
     * Return S or s
     */
    public char getCommand() { return absolute ? 'S' : 's'; }

    /**
     * Get the x coordinate of second control point.
     */
    public double getX2() { return x2; }

    /**
     * Get the y coordinate of second control point.
     */
    public double getY2() { return y2; }

    /**
     * Get the x coordinate.
     */
    public double getX() { return x; }

    /**
     * Get the y coordinate.
     */
    public double getY() { return y; }

    /**
     * Equality
     */
    boolean doEquals(Segment seg)
    {
      SmoothCurveTo s = (SmoothCurveTo)seg;
      return x2 == s.x2 && y2 == s.y2 &&
             x == s.x   && y == s.y;
    }
            
    /**
     * Encode the segment to the string buffer.
     */
    void toString(StringBuffer s)
    {
      s.append(BDouble.encode(x2))
        .append(',')
        .append(BDouble.encode(y2))
      .append(' ')
        .append(BDouble.encode(x))
        .append(',')
        .append(BDouble.encode(y));
    }           
    
    double x2, y2, x, y;          
  }            
  
////////////////////////////////////////////////////////////////
// QuadTo
////////////////////////////////////////////////////////////////
  
  /**
   * QuadTo draws a quadratic Bezier curve.
   */
  public static class QuadTo extends Segment
  {
    /**
     * Constructor
     */                     
    public QuadTo(boolean absolute, double x1, double y1, 
                  double x, double y)
    {
      super(absolute);
      this.x1 = x1;
      this.y1 = y1;
      this.x = x;
      this.y = y;
    }

    /**
     * Return Q or q
     */
    public char getCommand() { return absolute ? 'Q' : 'q'; }

    /**
     * Get the x coordinate of control point.
     */
    public double getX1() { return x1; }

    /**
     * Get the y coordinate of control point.
     */
    public double getY1() { return y1; }

    /**
     * Get the x coordinate.
     */
    public double getX() { return x; }

    /**
     * Get the y coordinate.
     */
    public double getY() { return y; }

    /**
     * Equality
     */
    boolean doEquals(Segment seg)
    {
      QuadTo s = (QuadTo)seg;
      return x1 == s.x1 && y1 == s.y1 &&
             x == s.x   && y == s.y;
    }
            
    /**
     * Encode the segment to the string buffer.
     */
    void toString(StringBuffer s)
    {
      s.append(BDouble.encode(x1))
        .append(',')
        .append(BDouble.encode(y1))
      .append(' ')
        .append(BDouble.encode(x))
        .append(',')
        .append(BDouble.encode(y));
    }           
    
    double x1, y1, x, y;          
  } 

////////////////////////////////////////////////////////////////
// SmoothQuadTo
////////////////////////////////////////////////////////////////
  
  /**
   * SmoothQuadTo draws a smooth quadratic Bezier curve.
   */
  public static class SmoothQuadTo extends Segment
  {
    /**
     * Constructor
     */                     
    public SmoothQuadTo(boolean absolute, double x, double y)
    {
      super(absolute);
      this.x = x;
      this.y = y;
    }

    /**
     * Return T or t
     */
    public char getCommand() { return absolute ? 'T' : 't'; }

    /**
     * Get the x coordinate.
     */
    public double getX() { return x; }

    /**
     * Get the y coordinate.
     */
    public double getY() { return y; }

    /**
     * Equality
     */
    boolean doEquals(Segment seg)
    {
      SmoothQuadTo s = (SmoothQuadTo)seg;
      return x == s.x   && y == s.y;
    }
            
    /**
     * Encode the segment to the string buffer.
     */
    void toString(StringBuffer s)
    {
      s.append(BDouble.encode(x))
       .append(',')
       .append(BDouble.encode(y));
    }           
    
    double x, y;          
  } 

////////////////////////////////////////////////////////////////
// ArcTo
////////////////////////////////////////////////////////////////
  
  /**
   * ArcTo draws an ellipical arc
   */
  public static class ArcTo extends Segment
  {
    /**
     * Constructor
     */                     
    public ArcTo(boolean absolute, double rx, double ry,
                 double xAxisRotation, 
                 boolean largeArcFlag, boolean sweepFlag, 
                 double x, double y)
    {
      super(absolute);
      this.rx = rx;
      this.ry = ry;
      this.xAxisRotation = xAxisRotation;
      this.largeArcFlag  = largeArcFlag;
      this.sweepFlag     = sweepFlag;
      this.x  = x;
      this.y  = y;
    }

    /**
     * Return A or a
     */
    public char getCommand() { return absolute ? 'A' : 'a'; }

    /**
     * Get the x radius.
     */
    public double getRadiusX() { return rx; }

    /**
     * Get the y radius.
     */
    public double getRadiusY() { return ry; }

    /**
     * Get the x asix rotation in degrees.
     */
    public double getXAxisRotation() { return xAxisRotation; }
    
    /**
     * Get the large arc flag used to select one
     * of the four possible arcs (true=1, false=0).
     */
    public boolean getLargeArcFlag() { return largeArcFlag; }

    /**
     * Get the sweep flag used to select one
     * of the four possible arcs (true=1, false=0).
     */
    public boolean getSweepFlag() { return sweepFlag; }

    /**
     * Get the x coordinate.
     */
    public double getX() { return x; }

    /**
     * Get the y coordinate.
     */
    public double getY() { return y; }

    /**
     * Equality
     */
    boolean doEquals(Segment seg)
    {
      ArcTo s = (ArcTo)seg;
      return rx == s.rx && ry == s.ry &&
             xAxisRotation == s.xAxisRotation &&
             largeArcFlag == s.largeArcFlag &&
             sweepFlag == s.sweepFlag &&
             x == s.x && y == s.y;
    }
            
    /**
     * Encode the segment to the string buffer.
     */
    void toString(StringBuffer s)
    {
      s.append(BDouble.encode(rx))
       .append(',')
       .append(BDouble.encode(ry))
      .append(' ')
       .append(BDouble.encode(xAxisRotation))
      .append(' ')
       .append(largeArcFlag ? '1' : '0')
       .append(',')
       .append(sweepFlag ? '1' : '0')
      .append(' ')
       .append(BDouble.encode(x))
       .append(',')
       .append(BDouble.encode(y));
    }           
    
    double rx, ry, xAxisRotation, x, y;          
    boolean largeArcFlag, sweepFlag;
  } 
  
}
