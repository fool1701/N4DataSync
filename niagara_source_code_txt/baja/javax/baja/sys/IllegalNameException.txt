/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * The IllegalNameException is thrown when an attempt
 * is made to add or rename a new property with an 
 * invalid name.  Use SlotPath.escape() to escape invalid 
 * characters in a name.
 *
 * @author    Brian Frank
 * @creation  23 Oct 01
 * @version   $Revision: 3$ $Date: 4/8/03 3:48:45 PM EDT$
 * @since     Baja 1.0 
 */
public class IllegalNameException
  extends LocalizableRuntimeException
{

  /**
   * Constructor with specified lexicon input and cause.
   */
  public IllegalNameException(String lexiconModule, String lexiconKey, 
                                Object[] lexiconArgs, Throwable cause)
  {  
    super(lexiconModule, lexiconKey, lexiconArgs, cause);
  }

  /**
   * Constructor with specified lexicon input.
   */
  public IllegalNameException(String lexiconModule, String lexiconKey, 
                              Object[] lexiconArgs)
  {  
    super(lexiconModule, lexiconKey, lexiconArgs, null);
  }

}
