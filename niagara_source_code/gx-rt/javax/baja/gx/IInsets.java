/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

/**
 * Interface for immutable BInsets and mutable Insets.
 *
 * @author    Brian Frank       
 * @creation  30 Dec 02
 * @version   $Revision: 2$ $Date: 3/22/04 11:11:49 AM EST$
 * @since     Baja 1.0
 */
public interface IInsets
{ 

  /** Offset from top edge */
  public double top();
  
  /** Offset from bottom edge */
  public double bottom();

  /** Offset from left edge */
  public double left();

  /** Offset from right edge */
  public double right();
  
}
