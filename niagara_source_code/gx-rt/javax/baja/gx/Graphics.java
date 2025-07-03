/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

/**
 * Graphics is used to access and paint to a graphics device.
 *
 * @author    Brian Frank       
 * @creation  30 Dec 02
 * @version   $Revision: 21$ $Date: 6/10/11 10:09:00 AM EDT$
 * @since     Baja 1.0
 */
public interface Graphics
{ 

////////////////////////////////////////////////////////////////
// Anti-aliasing
////////////////////////////////////////////////////////////////
  
  /**
   * Specify whether this context should attempt to
   * use anti-aliasing for rendering text and shapes.
   */
  public void useAntiAliasing(boolean b);

  /**
   * Specify whether this context should attempt to use fractional font metrics
   * when rendering text. This can change the actual rendered dimensions of
   * text, so if you need to measure the dimensions of the rendered text, ensure
   * that you are also measuring them with a context with fractional font
   * metrics enabled.
   * <p>
   * By default, this will do nothing. Fractional font metrics support must be
   * explicitly enabled with the {@code gx.fractionalFontMetrics} system
   * property.
   *
   * @param useFractionalFontMetrics whether to use fractional font metrics
   * @see BFont#fractionalWidth(String)
   * @since Niagara 4.12
   */
  default void useFractionalFontMetrics(boolean useFractionalFontMetrics)
  {

  }

////////////////////////////////////////////////////////////////
// Styling
////////////////////////////////////////////////////////////////

  /**
   * Gets the current brush.
   */
  public BBrush getBrush();

  /**
   * Set the current brush. 
   */
  public void setBrush(BBrush b);

  /**
   * Convenience for <code>setBrush(c.toBrush())</code>.
   */
  public void setBrush(BColor c);

  /**
   * Gets the current pen.
   */
  public BPen getPen();

  /**
   * Set the current stroke.
   */
  public void setPen(BPen p);

  /**
   * Gets the current font.
   */
  public BFont getFont();

  /**
   * Sets the current font. 
  */
  public void setFont(BFont font);

////////////////////////////////////////////////////////////////
// Transforms
////////////////////////////////////////////////////////////////

  /**
   * Translate the logical coordinate system using
   * the specified transformation.
   */
  public void transform(BTransform transform);
  
  /**
   * Translate the graphics by the specified x and y distance.
   */
  public void translate(double x, double y);

////////////////////////////////////////////////////////////////
// Clipping
////////////////////////////////////////////////////////////////
  
  /**
   * Get the clip geometry relative to the 
   * current logical coordinate space. 
   */
  public IGeom getClip();

  /**
   * Convenience for <code>getClip().bounds()</code>.
   */
  public IRectGeom getClipBounds();

  /**
   * Intersects the current clip with the specified rectangle.
   */
  public void clip(IGeom geom);

  /**
   * Convenience for <code>clip(IGeom)</code> with a rectangle.  
   * Intersect the current clip with the specified rectangle.
   */
  public void clip(double x, double y, double w, double h);

////////////////////////////////////////////////////////////////
// Drawing
////////////////////////////////////////////////////////////////

  /**
   * Strokes the outline or path of the specified 
   * geom using the current pen and current brush.
   */
  public void stroke(IGeom geom);

  /**
   * Fills the specified geom using current brush.
   */
  public void fill(IGeom geom);

  /** 
   * Draws a line between the two points using the current pen and brush.
   */
  public void strokeLine(double x1, double y1, double x2, double y2);

  /** 
   * Draws the outline of the rectangle using current pen and brush.
   */
  public void strokeRect(double x, double y, double width, double height);

  /** 
   * Fills the specified rectangle using current brush.
   */
  public void fillRect(double x, double y, double width, double height);
  
  /** 
   * Draws the text given by the specified string, using this 
   * graphics context's current font and brush. The baseline of the 
   * leftmost character is at position <code>x,y</code> in this 
   * graphics context's coordinate system. 
   */
  public void drawString(String str, double x, double y);

  /** 
   * Draws the text given by the specified character array, using this 
   * graphics context's current font and brush. The baseline of the 
   * first character is at position <code>x,y</code> in this 
   * graphics context's coordinate system. 
   */
  public void drawString(char data[], int offset, int length, double x, double y);

  /** 
   * Draws as much of the specified image as is currently available.
   * The image is drawn with its top-left corner at <code>x,y</code> 
   * in this graphics context's coordinate space. Transparent pixels 
   * in the image do not affect whatever pixels are already there. 
   */
  public void drawImage(BImage img, double x, double y);

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * Push a copy of the current graphics state onto a stack.  This 
   * method should always be called with a matching pop() within a 
   * try-finally block:
   *
   * <pre>
   *  g.push();
   *  try
   *  {
   *    // paint code
   *  }
   *  finally
   *  {
   *    g.pop();
   *  }
   * </pre> 
   */
  public void push();

  /**
   * Replace current graphics state with last pushed() state. This 
   * method should always be called with a matching push() within a 
   * try-finally block:
   *
   * <pre>
   *  g.push();
   *  try
   *  {
   *    // paint code
   *  }
   *  finally
   *  {
   *    g.pop();
   *  }
   * </pre>    
   */
  public void pop();    
  
  /**
   * Disposes of this graphics context and releases any system 
   * resources that it is using. 
   */
  public void dispose();
  
}
