/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.util.Objects;

/**
 * Insets stores offsets from the four sides of a rectangle.
 *
 * @author    Brian Frank       
 * @creation  30 Dec 02
 * @version   $Revision: 6$ $Date: 5/17/04 1:22:59 PM EDT$
 * @since     Baja 1.0
 */
public final class Insets
  implements IInsets
{ 

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Copy constructor.
   */
  public Insets(IInsets insets) 
  {
    set(insets.top(), insets.right(), insets.bottom(), insets.left());
  }

  /**
   * Constructor.
   */
  public Insets(double top, double right, double bottom, double left) 
  {
    set(top, right, bottom, left);
  }

  /**
   * Constructor with 0, 0, 0, 0.
   */
  public Insets() 
  {
    set(0, 0, 0, 0);
  }

////////////////////////////////////////////////////////////////
// IInsets
////////////////////////////////////////////////////////////////  

  /** Offset from top edge */
  public double top() { return top; }
  
  /** Offset from bottom edge */
  public double bottom()  { return bottom; }

  /** Offset from left edge */
  public double left()  { return left; }

  /** Offset from right edge */
  public double right()  { return right; }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Reset the insets fields.
   */
  public void set(double top, double right, double bottom, double left) 
  {
    this.top    = top;
    this.right  = right;
    this.bottom = bottom;    
    this.left   = left;
  }
  
  /**
   * Return if the specified object is an equivalent Insets.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Insets)
    {
      Insets i = (Insets)obj;
      return this.top == i.top && this.bottom == i.bottom &&
             this.left == i.left && this.right == i.right;
    }
    return false;
  }

  /**
   * Hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(top, right, bottom, left);
  }

  /**
   * String representation.
   */
  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append(top).append(" ");
    sb.append(right).append(" "); 
    sb.append(bottom).append(" ");
    sb.append(left);
    return sb.toString();         
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  /** Offset from top edge */
  public double top;
  
  /** Offset from bottom edge */
  public double bottom;

  /** Offset from left edge */
  public double left;

  /** Offset from right edge */
  public double right;
  
}
