/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.transfer;

import javax.baja.gx.*;

/**
 * DragRenderer is used to paint the effect of a drag 
 * operation.  SimpleDragRenderer provides a standard
 * implementation.
 *
 * @author    Brian Frank       
 * @creation  7 Mar 02
 * @version   $Revision: 3$ $Date: 4/1/04 5:27:13 PM EST$
 * @since     Baja 1.0
 */
public interface DragRenderer
{ 

  /**
   * Get a rectangle instance which encapsulates the region
   * painted by the drag renderer where {@code x=0, y=0} is the
   * origin of the graphics context passed to paintDragEffect.
   */
  public RectGeom getDragEffectRectGeom();
  
  /**
   * Paint the drag effect.  The origin of the 
   * graphics context is the mouse cursor location.
   */    
  public void paintDragEffect(Graphics g);
  
}
