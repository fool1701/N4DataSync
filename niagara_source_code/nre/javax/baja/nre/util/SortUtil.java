/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nre.util;

import java.util.*;

/**
 * Sorting utilities.
 *
 * @author    Brian Frank
 * @creation  4 Aug 01
 * @version   $Revision: 9$ $Date: 4/23/08 11:54:57 AM EDT$
 * @since     Baja 1.0
 */
public class SortUtil
{               
  
  /**
   * Convenience for <code>sort(values, values, ASCENDING)</code>.
   */
  public static <T> void sort(T[] values)
  {
    sort(values, values, ASCENDING);
  }  

  /**
   * Convenience for <code>sort(values, values, DESCENDING)</code>.
   */
  public static <T> void rsort(T[] values)
  {
    sort(values, values, DESCENDING); 
  }  

  /**
   * Convenience for <code>sort(keys, values, ASCENDING)</code>.
   */
  public static <K,V> void sort(K[] keys, V[] values)
  {
    sort(keys, values, ASCENDING); 
  }  

  /**
   * Convenience for <code>sort(keys, values, DESCENDING)</code>.
   */
  public static <K,V> void rsort(K[] keys, V[] values)
  {
    sort(keys, values, DESCENDING); 
  }  

  /**
   * Convenience for <code>sort(keys, values, ascending ? ASCENDING : DESCENDING)</code>.
   */
  public static <K,V> void sort(K[] keys, V[] values, boolean ascending)
  {
    if (ascending)
    {
      sort(keys, values, ASCENDING);
    }
    else
    {
      sort(keys, values, DESCENDING);
    }
  }  
  
  /**
   * Given a an array of keys and values, perform an in place sort
   * using the given Comparator.  The indices of the keys and values 
   * array should correspond.  Both the keys and values array will 
   * be modified to reflect the results of the sort.  The keys and
   * values parameters may reference the same array.
   */
  public static <K,V> void sort(K[] keys, V[] values, Comparator<? super K> comparator)
  {
    if (keys.length != values.length)
      throw new IllegalArgumentException("keys.length != values.length");
      
    int n = keys.length;
    int incr = n / 2;
    while(incr >= 1)
    {
      for(int i=incr; i<n; i++)
      {
        K tempKey = keys[i];
        V tempValue = values[i];
        int j = i;
        while(j >= incr && comparator.compare(tempKey, keys[j-incr]) < 0)
        {
          keys[j] = keys[j-incr];
          values[j] = values[j-incr];
          j -= incr;
        }
        keys[j] = tempKey;
        values[j] = tempValue;
      }
      incr /= 2;
    }
  }
  
  /**
   * Compare two objects.  The two objects should be of the
   * same class.  If the objects implement the Comparable
   * interface then that is how they are compared.  If not
   * then the results of their toString() methods are used
   * for the comparision.  This method works with null values,
   * where null objects are less than non-null objects.
   *
   * @return a negative integer, zero, or a positive integer 
   * as v1 is less than, equal to, or greater than the v2.
   */
  @SuppressWarnings("unchecked")
  public static <T> int compare(T v1, T v2)
  { 
    // check for null
    if (v1 == null) return v2 == null ? 0 : -1;
    if (v2 == null) return 1;
    
    // comparable
    try
    {
      if (v1 instanceof Comparable)
      {
        return ((Comparable<? super T>) v1).compareTo(v2);
      }
    }
    catch(ClassCastException ignored)
    {
    }         
    
    // fall back to string comparision
    return v1.toString().compareTo(v2.toString());
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////  
  
  /**
   * Comparator that uses <code>SortUtil.compare()</code>.
   */
  public static final Comparator<? super Object> ASCENDING = SortUtil::compare;

  /**
   * Comparator that uses reverse of <code>SortUtil.compare()</code>.
   */
  public static final Comparator<? super Object> DESCENDING = ASCENDING.reversed();
}
