/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * Topic defines a Slot which indicates an event that
 * is fired on a BComponent.
 *
 * @author    Brian Frank
 * @creation  24 Mar 00
 * @version   $Revision: 4$ $Date: 1/8/03 3:29:06 PM EST$
 * @since     Baja 1.0
 */
public interface Topic
  extends Slot
{  

  /**
   * Get the type of the Topic's event.
   */
  public Type getEventType();
  
}
