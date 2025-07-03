/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * The FrozenSlotException is thrown when attempts
 * are made to modify slots which are statically
 * defined by the class.
 *
 * @author    Brian Frank
 * @creation  28 Sept 00
 * @version   $Revision: 2$ $Date: 2/28/01 9:45:06 AM EST$
 * @since     Baja 1.0 
 */
public class FrozenSlotException
  extends BajaRuntimeException
{

  /**
   * Constructor with specified detailed message.
   * Usually this msg should be the name being
   * used to access the slot.
   */
  public FrozenSlotException(String msg)
  {  
    super(msg);
  }

  /**
   * Constructor with specified slot.
   */
  public FrozenSlotException(Slot slot)
  {  
    super(String.valueOf(slot));
  }
       
}
