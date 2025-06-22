/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * TypeNotFoundException indicates an attempt to 
 * load a type from a module failed because the
 * type is not listed in the "module.xml" manifest.
 *
 * @author    Brian Frank
 * @creation  25 Jul 00
 * @version   $Revision: 1$ $Date: 2/23/01 4:21:17 PM EST$
 * @since     Baja 1.0
 */
public class TypeNotFoundException
  extends TypeException
{

  /**
   * Construct a TypeNotFoundException with the given message.
   */
  public TypeNotFoundException(String msg)
  {
    super(msg);    
  }
    
}
