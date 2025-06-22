/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

/**
 * ICoalesceable is implemented by objects which may 
 * coalesced into a single object.
 *
 * @author    Brian Frank
 * @creation  7 Feb 04
 * @version   $Revision: 1$ $Date: 2/7/04 10:03:14 AM EST$
 * @since     Baja 1.0
 */
public interface ICoalesceable
{     
  
  /**
   * Get the key which may be used to index this coalesceable
   * in hash maps.  This key must implement hashCode() and equals()
   * according to the coalescing semanatics.
   */
  public Object getCoalesceKey();
  
  /**
   * Coalesce this instance with the specified object and return
   * the result (typically this or c).  If using a CoalesceQueue, 
   * this object is always the first enqueued object and c is the
   * new object being enqueued.
   */
  public ICoalesceable coalesce(ICoalesceable c);
      
}
