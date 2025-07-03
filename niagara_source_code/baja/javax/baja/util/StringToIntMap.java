/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.util.*;

/**
 * StringToIntMap is an optimized hashtable for hashing 
 * Strings to an integer.
 *
 * @author    Brian Frank
 * @creation  22 Jun 01
 * @version   $Revision: 1$ $Date: 6/22/01 12:28:28 PM EDT$
 * @since     Baja 1.0
 * @deprecated Use Map&lt;String,Integer&gt;
 */
@Deprecated
public class StringToIntMap
{
  /**
   * Default constructor.
   */
  public StringToIntMap()
  {
    this(31, 0.75f);
  }

  /**
   * Constructor with initial capacity.
   */
  public StringToIntMap(int initialCapacity)
  {
    this(initialCapacity, 0.75f);
  }

  /**
   * Constructor with capacity and load factor.
   */
  public StringToIntMap(int initialCapacity, float loadFactor)
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
   * @return an iterator for the String keys of the table.
   */
  public Iterator<String> keys()
  {
    return new StringToIntMapIterator();
  }

  /**
   * Get the int identified by the given String key.
   * 
   * @return -1 if not in table.
   */
  public int get(String key) 
  {
    return get(key, -1);
  }
  
  /**
   * Get the int identified by the given String key.
   * 
   * @return nullValue if not in table.
   */
  public synchronized int get(String key, int nullValue)
  {
    Entry tab[] = table;
    int hash = key.hashCode();
    int index = (hash & 0x7FFFFFFF) % tab.length;

    for (Entry e = tab[index] ; e != null ; e = e.next) 
    {
      if (e.hash == hash && e.key.equals(key))
        return e.value;
    }

    return nullValue;
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

        int index = (e.hash & 0x7FFFFFFF) % newCapacity;
        e.next = newTable[index];
        newTable[index] = e;
      }
    }
  }

  /**
   * Put the given object into the table keyed on the int.
   */
  public synchronized void put(String key, int value) 
  {
    // Insure the key is not already in the hashtable
    Entry tab[] = table;
    int hash = key.hashCode();
    int index = (hash & 0x7FFFFFFF) % tab.length;
    for (Entry e = tab[index] ; e != null ; e = e.next) 
    {
      if (e.hash == hash && e.key.equals(key))
      {
        e.value = value;
        return;
      }
    }

    // Insure capacity
    if (count >= threshold) 
    {
      rehash();
      put(key, value);
      return;
    } 

    // Create the new entry
    Entry e = new Entry();
    e.hash  = hash;
    e.key   = key;
    e.value = value;
    e.next  = tab[index];
    tab[index] = e;
    count++;
  }

  /**
   * Remove the vaulue identified by the key.
   */
  public synchronized void remove(String key) 
  {
    Entry tab[] = table;
    int hash = key.hashCode();
    int index = (hash & 0x7FFFFFFF) % tab.length;

    for (Entry e = tab[index], prev=null; e != null ; prev=e, e=e.next) 
    {
      if (e.hash == hash && e.key.equals(key))
      {
        if (prev != null) 
          prev.next = e.next;
        else 
          tab[index] = e.next;

        count--;
        return;
      }
    }
  }

  /**
   * Clear the hashtable of entries.
   */
  public synchronized void clear() 
  {
    Entry tab[] = table;
    for (int index = tab.length; --index >= 0; )
      tab[index] = null;
    count = 0;
  }
  
  /**
   * Return if the specified object another IntHashMap 
   * with the exact same key-value pairs.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof StringToIntMap)) return false;
    if (this == obj) return true;
    StringToIntMap o = (StringToIntMap)obj;
    for (int i=0; i<table.length; ++i)
    { 
      Entry entry = table[i];
      while (entry != null)
      {
        int tv = entry.value;
        int ov = o.get(entry.key);
        if (ov != tv)
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
  @Override
  public synchronized Object clone()
  {
    StringToIntMap c = new StringToIntMap(size()*3);
    for (int i=0; i<table.length; ++i)
    { 
      Entry entry = table[i];
      while (entry != null)
      {
        c.put(entry.key, entry.value);
        entry = entry.next;
      }
    }
    return c;
  }

////////////////////////////////////////////////////////////////
// Enumerator
////////////////////////////////////////////////////////////////

  class StringToIntMapIterator
    implements java.util.Iterator<String>
  {
  
    @Override
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

    @Override
    public String next()
    {
      if (entry == null) 
      {
        while ((index-- > 0) && ((entry = table[index]) == null));
      }

      if (entry != null) 
      {
        Entry e = entry;
        entry = e.next;
        return e.key;
      }

      throw new NoSuchElementException("IntHashtable");
    }
    
    @Override
    public void remove()
    {
      throw new UnsupportedOperationException();
    }

    private int index = table.length;
    private Entry entry;
  }

//////////////////////////////////////////////////////////////
// Entry
//////////////////////////////////////////////////////////////

  static class Entry 
  {
    @Override public int hashCode() { return hash; }

    int    hash;
    String key;
    int    value;
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
