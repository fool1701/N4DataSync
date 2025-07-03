/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.util.Objects;

/**
 * Size stores a width and height.
 *
 * @author    Brian Frank       
 * @creation  30 Dec 02
 * @version   $Revision: 5$ $Date: 4/1/04 5:29:23 PM EST$
 * @since     Baja 1.0
 */
public final class Size
  implements ISize
{ 

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Copy constructor
   */
  public Size(ISize dim)
  {
    set(dim.width(), dim.height());
  }

  /**
   * Constructor for width, and height
   */
  public Size(double width, double height)
  {
    set(width, height);
  }

  /**
   * Constructor for 0, 0
   */
  public Size()
  {
    set(0, 0);
  }

////////////////////////////////////////////////////////////////
// IRectangle
////////////////////////////////////////////////////////////////

  /** The width */
  public double width() { return width; }
  
  /** The height */
  public double height() { return height; }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  
  
  /**
   * Reset the dimension fields.
   */
  public void set(double width, double height)
  {
    this.width  = width;
    this.height = height;
  }
  
  /**
   * Return if the specified object is an equivalent Size.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Size)
    {
      Size d = (Size)obj;
      return this.width == d.width && this.height == d.height;
    }
    return false;
  }

  /**
   * Hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(width, height);
  }

  /**
   * String representation.
   */
  @Override
  public String toString() 
  {
    return String.valueOf(width) + "," + String.valueOf(height);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  /** The width */
  public double width;

  /** The height */
  public double height;
  
}
