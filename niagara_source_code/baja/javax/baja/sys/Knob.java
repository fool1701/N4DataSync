/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.naming.*;

/**
 * Knob is the mirror of a link which may be accessed 
 * on the source component.
 *
 * @author    Brian Frank
 * @creation  17 Nov 01
 * @version   $Revision: 4$ $Date: 7/24/03 3:06:11 PM EDT$
 * @since     Baja 1.0
 */
public interface Knob
{ 

  /**
   * Get the mirrored link.
   * @return may be null
   */
  public BLink getLink();
  
  /**
   * Get the target ord.
   * @return should never be null.
   */
  public BOrd getTargetOrd();

  /**
   * Get the target component.
   * @return may be null.
   */
  public BComponent getTargetComponent();

  /**
   * Get the target slot name.
   * @return should never be null.
   */
  public String getTargetSlotName();

  /**
   * Get the target slot.
   * @return may be null.
   */
  public Slot getTargetSlot();
  
  /**
   * Get the source ord.
   * @return should never be null.
   */
  public BOrd getSourceOrd();

  /**
   * Get the source component.
   * @return should never be null.
   */
  public BComponent getSourceComponent();

  /**
   * Get the source slot name.
   * @return should never be null.
   */
  public String getSourceSlotName();

  /**
   * Get the source slot.
   * @return may be null.
   */
  public Slot getSourceSlot();
    
}
