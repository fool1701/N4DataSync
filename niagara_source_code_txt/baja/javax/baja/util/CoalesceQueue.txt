/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

/**
 * CoalesceQueue is a specialization of Queue designed to 
 * coalesce duplicate entries using the ICoalesceable interface.
 * Internally CoalesceQueue is a linked list with a hash index
 * to the link entries.
 *
 * @author    Brian Frank on 23 Jan 02
 * @version   $Revision: 3$ $Date: 2/7/04 4:24:43 PM EST$
 * @since     Baja 1.0
 */
public class CoalesceQueue
  extends Queue
{     

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a CoalesceQueue with the specified max size.
   */
  public CoalesceQueue(int maxSize)
  {  
    super(maxSize);         
    table = new HashEntry[ Math.max(16, Math.min(maxSize/3, 101)) ];
    threshold = (int)(table.length * loadFactor);
  }

  /**
   * Construct a CoalesceQueuewith max size of Integer.MAX_VALUE.
   */
  public CoalesceQueue()
  {                  
    this(Integer.MAX_VALUE);
  }
  
////////////////////////////////////////////////////////////////
// Methods 
////////////////////////////////////////////////////////////////
                                              
  /**
   * Find on a CoalesceQueue provides fast access using
   * <code>ICoalesceable.getCoalesceKey()</code> as the index.
   * If the value does not implement ICoalesceable then
   * return null.
   */
  @Override
  public synchronized Object find(Object value)
  {                            
    if (value instanceof ICoalesceable)
    {
      HashEntry e = get((ICoalesceable)value);
      if (e != null) return e.value;
    }
    return null;
  }

  /**
   * Read off the oldest object from the queue.
   *
   * @return oldest queue element, or null
   *    if the queue is empty.
   */
  @Override
  public synchronized Object dequeue()
  {
    Entry e = head;
    if (e == null) return null;
    super.dequeue();         
    if (e.value instanceof ICoalesceable)
    {
      remove((ICoalesceable)e.value);
    }
    return e.value;
  }

  /**
   * If <code>find(value)</code> returns null then add
   * the value to the end of the queue.  If find returns
   * a match then update the existing enqueued item using
   * <code>ICoalesceable.coalesce(value)</code>.
   *                         
   * @return true if new value was enqueued and
   *   false if it was coalesced.
   * @param value Object to append to the
   *    end of the queue.
   * @throws QueueFullException is the queue
   *    is already at max size.
   */
  @Override
  public synchronized boolean enqueue(Object value)
    throws QueueFullException
  { 
    if (value instanceof ICoalesceable)
    {
      ICoalesceable newc = (ICoalesceable)value;
      HashEntry dup = get(newc);
      if (dup != null)
      {            
        ICoalesceable oldc = (ICoalesceable)dup.value;
        dup.value = oldc.coalesce(newc);
        return false;
      }
    }
    return super.enqueue(value);  
  }
      
  @Override
  Entry newEntry(Object value)
  {                          
    if (value instanceof ICoalesceable)
      return put((ICoalesceable)value); 
    else
      return new Entry(value);
  }
  
  /**
   * Remove all the enqueued entries.
   */
  @Override
  public synchronized void clear()
  {                          
    super.clear();       
    hashSize = 0;
    table = new HashEntry[table.length];
  }           
  
////////////////////////////////////////////////////////////////
// HashMap
////////////////////////////////////////////////////////////////

  HashEntry get(ICoalesceable c) 
  {                         
    Object key = c.getCoalesceKey();
    int hash = key.hashCode();
    HashEntry tab[] = table;
    int index = (hash & 0x7FFFFFFF) % tab.length;

    for (HashEntry e = tab[index] ; e != null ; e = e.hashNext) 
    {
      if (e.hash == hash && ((ICoalesceable)e.value).getCoalesceKey().equals(key))
        return e;
    }

    return null;
  }

  void rehash() 
  {                 
    int oldCapacity = table.length;
    HashEntry oldTable[] = table;

    int newCapacity = oldCapacity * 2 + 1;
    HashEntry newTable[] = new HashEntry[newCapacity];

    threshold = (int)(newCapacity * loadFactor);
    table = newTable;

    for (int i = oldCapacity ; i-- > 0 ;) 
    {
      for (HashEntry old = oldTable[i] ; old != null ; ) 
      {
        HashEntry e = old;
        old = old.hashNext;

        int index = (e.hash & 0x7FFFFFFF) % newCapacity;
        e.hashNext = newTable[index];
        newTable[index] = e;
      }
    }
  }

  HashEntry put(ICoalesceable c) 
  {
    // ensure capacity
    if (hashSize >= threshold) 
    {
      rehash();
      return put(c);
    } 

    Object key = c.getCoalesceKey();
    int hash = key.hashCode();
    HashEntry tab[] = table;
    int index = (hash & 0x7FFFFFFF) % tab.length;

    // create the new entry
    HashEntry e = new HashEntry(c);
    e.hash     = hash;
    e.hashNext = tab[index];
    tab[index] = e;            
    hashSize++;
    return e;
  }

  Object remove(ICoalesceable c) 
  {
    Object key = c.getCoalesceKey();
    int hash = key.hashCode();
    HashEntry tab[] = table;
    int index = (hash & 0x7FFFFFFF) % tab.length;

    for (HashEntry e = tab[index], prev=null; e != null ; prev=e, e=e.hashNext) 
    {
      if (e.hash == hash && ((ICoalesceable)e.value).getCoalesceKey().equals(key))
      {
        if (prev != null) 
          prev.hashNext = e.hashNext;
        else 
          tab[index] = e.hashNext;
          
        hashSize--;
        return e.value;
      }
    }
    
    throw new IllegalStateException();
  }
  
////////////////////////////////////////////////////////////////
// HashEntry
////////////////////////////////////////////////////////////////

  static class HashEntry extends Entry
  {              
    HashEntry(Object v) { super(v); }
    
    int hash;
    HashEntry hashNext;
  }
  
////////////////////////////////////////////////////////////////
// Atributes
////////////////////////////////////////////////////////////////

  HashEntry[] table;
  int hashSize;    
  int threshold;
  float loadFactor = 0.75f;
  
} 

