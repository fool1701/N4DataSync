/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.tool;

import javax.baja.registry.TypeInfo;

/**
 * WbServiceManager is used to manage the lifecycle and configuration 
 * of BWbServices.  Use the <code>BWbService.getManager()</code> 
 * to access.
 *
 * @author    Brian Frank       
 * @creation  14 Oct 03
 * @version   $Revision: 3$ $Date: 3/28/05 1:41:02 PM EST$
 * @since     Baja 1.0
 */
public interface WbServiceManager
{ 
  
  /**
   * Start the specified service.
   */
  public void start(TypeInfo serviceType);  

  /**
   * Start the specified service.
   */
  public void start(BWbService service);  

  /**
   * Stop the specified service.
   */
  public void stop(TypeInfo serviceType);  

  /**
   * Stop the specified service.
   */
  public void stop(BWbService service);  

  /**
   * Return if the specified service is running.
   */
  public boolean isRunning(TypeInfo serviceType);  

  /**
   * Return if the specified service is configured as auto start.
   */
  public boolean isAutoStart(TypeInfo serviceType);  

  /**
   * Set the autoStart property of the specified service.  If
   * the service is not running, this automatically starts it too.
   */
  public void setAutoStart(TypeInfo serviceType, boolean autoStart);  
   
}

