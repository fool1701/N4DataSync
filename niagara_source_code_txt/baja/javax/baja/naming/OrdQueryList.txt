/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import java.util.*;

/**
 * OrdQueryList is used to manipulate a list of OrdQueries.
 *
 * @author    Brian Frank
 * @creation  16 Jan 03
 * @version   $Revision: 6$ $Date: 5/19/03 11:15:15 AM EDT$
 * @since     Baja 1.0
 */
public final class OrdQueryList
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a list from the specified array and size.
   */
  public OrdQueryList(OrdQuery[] q, int size)
  {
    list.ensureCapacity(size);
    for(int i=0; i<size; ++i)
      list.add(q[i]);
  }

  /**
   * Construct a list from the specified array with 
   * a size of q.length.
   */
  public OrdQueryList(OrdQuery[] q)
  {
    this(q, q.length);
  }

  /**
   * Construct a list of size 0.
   */
  public OrdQueryList()
  {
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Get the number of queries in the list.
   */
  public int size()
  {
    return list.size();
  }
  
  /**
   * Get the query at the specified index.
   */
  public OrdQuery get(int index)
  {
    return list.get(index);
  }

  /**
   * Get the querys as an array.
   */
  public OrdQuery[] toArray()
  {
    return list.toArray(new OrdQuery[list.size()]);
  }
  
  /**
   * Get the query at index-1 or null if index is zero.
   */
  public OrdQuery prev(int index)
  {
    if (index > 0 && !list.isEmpty())
      return list.get(index-1);
    return null;
  }

  /**
   * Get the query at index+1 or null if past end of list.
   */
  public OrdQuery next(int index)
  {
    if (index+1 < list.size()) 
      return list.get(index+1);
    return null;
  }
  
  /**
   * Return if the queries at indexA and indexB
   * are the same class and same scheme identifier.
   * If indexA or indexB is out of range, then 
   * return false.
   */
  public boolean isSameScheme(int indexA, int indexB)
  {
    if (0 <= indexA && indexA < list.size() &&
        0 <= indexB && indexB < list.size()) 
    {
      OrdQuery a = list.get(indexA);
      OrdQuery b = list.get(indexB);
      return a.getClass() == b.getClass() &&
             a.getScheme().equals(b.getScheme());
    }
    return false;
  }

////////////////////////////////////////////////////////////////
// Manipulation
////////////////////////////////////////////////////////////////  

  /**
   * Add the specified query list to the end of this list.
   */
  public void add(OrdQueryList list)
  {
    modified = true;
    for(int i=0; i<list.size(); ++i)
      add(list.get(i));
  }

  /**
   * Add the specified query to the end of this list.
   */
  public void add(OrdQuery q)
  {
    modified = true;
    list.add(list.size(), q);
  }

  /**
   * Add the specified query at the given index.
   */
  public void add(int index, OrdQuery q)
  {
    modified = true;
    list.add(index, q);
  }

  /**
   * Replace the query at the specified index.
   */
  public void replace(int index, OrdQuery q)
  {
    modified = true;
    list.set(index, q);
  }

  /**
   * Remove the query at the specified index.
   */
  public void remove(int index)
  {
    modified = true;
    list.remove(index);
  }

  /**
   * Remove all the queries.
   */
  public void clear()
  {
    modified = true;
    list.clear();
  }
  
  /**
   * Remove queries from 0 to start-1 and 
   * keep queries from start to size-1.
   */
  public void trim(int start)
  {
    trim(start, size());
  }

  /**
   * Remove queries from 0 to start-1.  Remove
   * queries from end to size-1.  Keep queries 
   * from start to end-1.
   */
  public void trim(int start, int end)
  {
    if (start > 0 || end < list.size())
    {
      modified = true;
      int size = list.size();
      for(int i=end; i<size; ++i) list.remove(end);
      for(int i=0; i<start; ++i) list.remove(0);
    }
  }  
  
  /**
   * Merge the queries at index and index+1 into the 
   * specified merged query.
   */
  public void merge(int index, OrdQuery merged)
  {
    modified = true;
    list.remove(index+1);
    list.remove(index+0);
    list.add(index, merged);
  }

  /**
   * Starting from index-1 and working left to index zero, 
   * strip any queries which return false for isHost().
   * This is the effect of shifting the specified query
   * left so that it immediately follows the host query.
   * If no host is present, then it has the effect of
   * triming all the queries to the left.
   */
  public void shiftToHost(int index)
  {
    for(int i=index-1; i>=0; --i)
      if (!get(i).isHost()) remove(i);
  }

  /**
   * Starting from index-1 and working left to index zero, 
   * strip any queries which return false for isHost and 
   * isSession().   This is the effect of shifting the specified 
   * query left so that it immediately follows the session query.
   * If no session is present, then it has the effect of
   * triming all the queries to the left.
   */
  public void shiftToSession(int index)
  {
    for(int i=index-1; i>=0; --i)
    {
      OrdQuery q = get(i);
      if (!q.isHost() && !q.isSession()) remove(i);
    }
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////  

  /**
   * To string.
   */  
  public String toString()
  {
    StringBuilder s = new StringBuilder();
    for(int i=0; i<list.size(); ++i)
    {
      if (i > 0) s.append('|');
      s.append(list.get(i));
    }
    return s.toString();
  }
    
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  ArrayList<OrdQuery> list = new ArrayList<>();
  boolean modified;
  
}

