/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * Action is a Slot that defines a behavior which can
 * be invoked on a BComponent.
 *
 * @author    Brian Frank
 * @creation  24 Mar 00
 * @version   $Revision: 7$ $Date: 1/8/03 3:28:40 PM EST$
 * @since     Baja 1.0
 */
public interface Action
  extends Slot
{  

  /**
   * Get the parameter type for this action.
   * If the action takes no parameters, then 
   * return null.
   */
  public Type getParameterType();

  /**
   * Get a copy of the default parameter 
   * for this action.  If the action takes
   * no parameters, then return null.
   */
  public BValue getParameterDefault();
  
  /**
   * Get the return type for this action or
   * null if the action is asynchronous or has
   * a void method return type.
   */
  public Type getReturnType();
    
}
