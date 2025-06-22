/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * The NoSuchSlotException is used to indicate an
 * attempt to access a slot which does not exist.
 *
 * @author    Brian Frank
 * @creation  1 Feb 00
 * @version   $Revision: 4$ $Date: 5/30/01 9:23:13 AM EDT$
 * @since     Baja 1.0 
 */
public class NoSuchSlotException
  extends BajaRuntimeException
{

  /**
   * Constructor with specified detailed message.
   * Usually this msg should be the name being
   * used to access the slot.
   */
  public NoSuchSlotException(String msg)
  {  
    super(msg);
  }

  /**
   * Constructor with specified type and slot.
   */
  public NoSuchSlotException(Type type, Slot slot)
  {  
    super(type + "." + slot.getName());
  }
       
}
