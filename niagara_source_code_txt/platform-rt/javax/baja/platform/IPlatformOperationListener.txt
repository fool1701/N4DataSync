/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform;

/**
 * Provides a simple interface between a PlatformDaemon client and a long-running 
 * task 
 * 
 * @author    Matt Boon       
 * @creation  28 Feb 05
 * @version   $Revision: 2$ $Date: 2/13/08 1:44:17 PM EST$
 * @since     Baja 1.0
 */
public interface IPlatformOperationListener
  extends ICancelHint
{
  /**
   * Return true if the platform operation should terminate.
   * 
   * Some platform operations check isCanceled() more frequently than others,
   * and operations aren't required to check at all.
   */
  boolean isCanceled();

  /**
   * Used by the platform operation to report a change in status
   * to the listener.
   */
  void notifyStatus(String status);
}
