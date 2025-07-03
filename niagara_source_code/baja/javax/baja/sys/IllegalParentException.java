/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * The IllegalParentException is thrown when an attempt
 * is made to add a new property where the child type
 * requires a different parent type.  This exception must
 * always support localization.
 *
 * @author    Brian Frank
 * @creation  26 Feb 01
 * @version   $Revision: 2$ $Date: 7/23/02 3:19:05 PM EDT$
 * @since     Baja 1.0 
 */
public class IllegalParentException
  extends LocalizableRuntimeException
{

  /**
   * Constructor with specified lexicon input and cause.
   */
  public IllegalParentException(String lexiconModule, String lexiconKey, 
                                Object[] lexiconArgs, Throwable cause)
  {  
    super(lexiconModule, lexiconKey, lexiconArgs, cause);
  }

  /**
   * Constructor with specified lexicon input.
   */
  public IllegalParentException(String lexiconModule, String lexiconKey, 
                                Object[] lexiconArgs)
  {  
    super(lexiconModule, lexiconKey, lexiconArgs, null);
  }
  
}
