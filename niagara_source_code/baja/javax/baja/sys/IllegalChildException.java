/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * The IllegalChildException is thrown when an attempt
 * is made to add a new property to a component where
 * the child type is invalid.
 *
 * @author    Brian Frank
 * @creation  26 Feb 01
 * @version   $Revision: 2$ $Date: 7/23/02 3:19:00 PM EDT$
 * @since     Baja 1.0 
 */
public class IllegalChildException
  extends LocalizableRuntimeException
{

  /**
   * Constructor with specified lexicon input and cause.
   */
  public IllegalChildException(String lexiconModule, String lexiconKey, 
                                Object[] lexiconArgs, Throwable cause)
  {  
    super(lexiconModule, lexiconKey, lexiconArgs, cause);
  }

  /**
   * Constructor with specified lexicon input.
   */
  public IllegalChildException(String lexiconModule, String lexiconKey, 
                                Object[] lexiconArgs)
  {  
    super(lexiconModule, lexiconKey, lexiconArgs, null);
  }
  
}
