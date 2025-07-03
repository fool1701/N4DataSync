/*
 * Copyright 2008 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history;

/**
 * HistoryEventListener should be implemented by objects
 * that wish to receive a callback for local history events.  
 * A HistoryEventListener must first register for events with 
 * the History Database.  BEWARE, when implementers of this 
 * interface receive history event callbacks, the history 
 * database service is blocked waiting for the method to complete.
 * Thus the implementation should return quickly (any expensive
 * work should be posted to a separate worker thread). 
 *
 * @author    Scott Hoye
 * @creation  14 Nov 08
 * @version   $Revision: 2$ $Date: 1/30/09 3:08:58 PM EST$
 * @since     Niagara 3.4.47
 */
public interface HistoryEventListener
{ 

  /**
   * Handle the specified history event.
   * 
   * BEWARE, this callback causes the history database service 
   * to be blocked until the method completes.
   * Thus the implementation should return quickly (any expensive
   * work should be posted to a separate worker thread so
   * control can be safely returned).
   */
  public void historyEvent(BHistoryEvent event);
  
}