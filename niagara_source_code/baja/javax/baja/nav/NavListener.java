/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nav;

/**
 * NavListener is used to receive NavEvent callbacks.  Listeners
 * are registered and unregistered on BNavRoot.INSTANCE.  You may
 * use the NavMap class to map BINavNodes to other data structures
 * for efficiently handling navigation tree events.  Also see the
 * workbench:NavTree component.
 *
 * @author    Brian Frank
 * @creation  22 Jan 03
 * @version   $Revision: 1$ $Date: 1/23/03 11:17:11 AM EST$
 * @since     Baja 1.0
 */
public interface NavListener
{ 

  /**
   * Handle the specified nav event.
   */
  public void navEvent(NavEvent event);    
  
}
