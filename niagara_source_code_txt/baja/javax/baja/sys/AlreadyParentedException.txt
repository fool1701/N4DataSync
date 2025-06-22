/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * The AlreadyParentedException is thrown when
 * an attempt is made to set or add a BComplex
 * value which is already contained by another
 * object.
 *
 * @author    Brian Frank
 * @creation  28 Sept 00
 * @version   $Revision: 2$ $Date: 2/28/01 9:44:54 AM EST$
 * @since     Baja 1.0 
 */
public class AlreadyParentedException
  extends BajaRuntimeException
{

  /**
   * Constructor with specified detailed message.
   */
  public AlreadyParentedException(String msg)
  {  
    super(msg);
  }

  /**
   * Constructor with no message.
   */
  public AlreadyParentedException()
  {  
  }
       
}
