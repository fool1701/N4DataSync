/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nre.util;

import java.util.Arrays;
import java.util.Objects;

/**
 * LongHashMap is an optimized hashtable for hashing objects 
 * by a long key.  It removes the need to use wrapper 
 * Long as with the standard collection classes.  
 *
 * @author    Robert Adams
 * @creation  9 Mar 99
 * @version   $Revision: 5$ $Date: 1/23/03 11:21:56 AM EST$
 * @since     Baja 1.0
 */
public class LongHashMap
{

  /**
   * Default constructor.
   */
  public LongHashMap()
  {
    this(31, 0.75f);
  }

  /**
   * Constructor with initial capacity.
   */
  public LongHashMap(int initialCapacity)
  {
    this(initialCapacity, 0.75f);
  }

  /**
   * Constructor with initial capacity and load factor.
   */
  public LongHashMap(int initialCapacity, float loadFactor)
  {
    if (initialCapacity <= 0 || loadFactor <= 0.0) 
      throw new IllegalArgumentException();

     this.loadFactor = loadFactor;
     this.table = new Entry[initialCapacity];
     this.threshold = (int)(initialCapacity * loadFactor);
  }

  /**
   * @return the count of elements in the table.
   */
  public int size()
  {
    return count;
  }

  /**
   * @return if the table is empty.
   */
  public boolean isEmpty() 
  {
    return (count == 0);
  }

  /**
   * @return an iterator of the values for this table.
   */
  public Iterator iterator()
  {
    return new Iterator();
  }

  /**
   * Get the object identified by the given long key.
   * 
   * @return null if not in table.
   */
  public Object get(long key) 
  {
    Entry tab[] = table;
    int index = (hash(key) & 0x7FFFFFFF) % tab.length;

    for (Entry e = tab[index] ; e != null ; e = e.next) 
    {
      if (e.hash == key) 
        return e.value;
    }

    return null;
  }

  /**
   * Rehash contents of this table into a table
   * with a larger capacity.
   */
  private void rehash() 
  {
    int oldCapacity = table.length;
    Entry oldTable[] = table;

    int newCapacity = oldCapacity * 2 + 1;
    Entry newTable[] = new Entry[newCapacity];

    threshold = (int)(newCapacity * loadFactor);
    table = newTable;

    for (int i = oldCapacity ; i-- > 0 ;) 
    {
      for (Entry old = oldTable[i] ; old != null ; ) 
      {
        Entry e = old;
        old = old.next;

        int index = (hash(e.hash) & 0x7FFFFFFF) % newCapacity;
        e.next = newTable[index];
        newTable[index] = e;
      }
    }
  }

  /**
   * Put the given object into the table keyed on the long.
   * 
   * @return the previous value at this key,
   *    or null if it did not have one.
   */
  public Object put(long key, Object value) 
  {
    if (value == null) 
      throw new NullPointerException();

    // Insure the key is not already in the hashtable
    Entry tab[] = table;
    int index = (hash(key) & 0x7FFFFFFF) % tab.length;
    for (Entry e = tab[index] ; e != null ; e = e.next) 
    {
      if (e.hash == key)
      {
        Object old = e.value;
        e.value = value;
        return old;
      }
    }

    // Insure capacity
    if (count >= threshold) 
    {
      rehash();
      return put(key, value);
    } 

    // Create the new entry
    Entry e = new Entry();
    e.hash  = key;
    e.value = value;
    e.next  = tab[index];
    tab[index] = e;
    count++;
    return null;
  }

  /**
   * Remove the vaulue identified by the key.
   */
  public Object remove(long key) 
  {
    Entry tab[] = table;
    int index = (hash(key) & 0x7FFFFFFF) % tab.length;

    for (Entry e = tab[index], prev=null; e != null ; prev=e, e=e.next) 
    {
      if (e.hash == key) 
      {
        if (prev != null) 
          prev.next = e.next;
        else 
          tab[index] = e.next;

        count--;
        return e.value;
      }
    }

    return null;
  }

  /**
   * Clear the hashtable of entries.
   */
  public void clear() 
  {
    Entry tab[] = table;
    for (int index = tab.length; --index >= 0; )
      tab[index] = null;
    count = 0;
  }

  /**
   * Return if the specified object another LongHashMap 
   * with the exact same key-value pairs.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof LongHashMap)) return false;
    if (this == obj) return true;
    LongHashMap o = (LongHashMap)obj;
    for (int i=0; i<table.length; ++i)
    { 
      Entry entry = table[i];
      while (entry != null)
      {
        Object tv = entry.value;
        Object ov = o.get(entry.hash);
        if (ov == null || !tv.equals(ov))
          return false;
        entry = entry.next;
      }
    }
    return true;
  }

  @Override
  public int hashCode()
  {
    return Arrays.hashCode(table);
  }

  /**
   * Clone the IntHashMap into a new instance.
   */
  public Object clone()
  {
    LongHashMap c = new LongHashMap(size()*3);
    for (int i=0; i<table.length; ++i)
    { 
      Entry entry = table[i];
      while (entry != null)
      {
        c.put(entry.hash, entry.value);
        entry = entry.next;
      }
    }
    return c;
  }

  /**
   * Get an array containing all values in 
   * hash table.
   */
  public Object[] toArray(Object[] a) 
  {
    int nxtIdx = 0;
    for (int i=0; i<table.length; i++)
    { 
      Entry entry = table[i];
      while ( entry != null && nxtIdx < count )
      {
        a[nxtIdx++] = entry.value;
        entry = entry.next;
      }
    }
    return a;
  }
  
  /**
   * Hash a long.
   */
  private int hash(long x)
  {
    return (int)(x ^ (x >> 32));
  }

//////////////////////////////////////////////////////////////
// Iterator
//////////////////////////////////////////////////////////////

  public class Iterator
    implements java.util.Iterator<Object>
  {

    public boolean hasNext()
    {
      if (entry != null) return true;
      while(index-- > 0)
      {
        entry = table[index];
        if (entry != null) return true;
      }
      return false;
    }
    
    public long key()
    {
      return key;
    }

    public Object next() 
    {
      if (entry == null) 
      {
        while ((index-- > 0) && ((entry = table[index]) == null));
      }

      if (entry != null) 
      {
        Entry e = entry;
        entry = e.next;
        key = e.hash;
        return e.value;
      }

      throw new java.util.NoSuchElementException();
    }
    
    public void remove()
    {
      throw new UnsupportedOperationException();
    }

    private int index = table.length;
    private Entry entry;
    private long key;
  }

//////////////////////////////////////////////////////////////
// Entry
//////////////////////////////////////////////////////////////

  static class Entry 
  {
    @Override
    public int hashCode()
    {
      return Objects.hashCode(hash);
    }

    long   hash;
    Object value;
    Entry  next;
  }

//////////////////////////////////////////////////////////////
// Attributes
//////////////////////////////////////////////////////////////

  private Entry table[];
  private int count;
  private int threshold;
  private float loadFactor;

}
