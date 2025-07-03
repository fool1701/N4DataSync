/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * The DuplicateSlotException is used to indicate an
 * attempt to add a slot which already exists.
 *
 * @author    Brian Frank
 * @creation  28 Sept 00
 * @version   $Revision: 3$ $Date: 7/23/02 3:18:39 PM EDT$
 * @since     Baja 1.0 
 */
public class DuplicateSlotException
  extends LocalizableRuntimeException
{

  /**
   * Constructor with specified lexicon input and cause.
   */
  public DuplicateSlotException(String lexiconModule, String lexiconKey, 
                                Object[] lexiconArgs, Throwable cause)
  {  
    super(lexiconModule, lexiconKey, lexiconArgs, cause);
  }

  /**
   * Constructor with specified lexicon input.
   */
  public DuplicateSlotException(String lexiconModule, String lexiconKey, 
                                Object[] lexiconArgs)
  {  
    super(lexiconModule, lexiconKey, lexiconArgs, null);
  }

  /**
   * Constructor with specified slot.
   */
  public DuplicateSlotException(Slot slot)
  {  
    super("baja", "DuplicateSlotException.slot", new Object[] { slot.getName() });
  }
       
}
