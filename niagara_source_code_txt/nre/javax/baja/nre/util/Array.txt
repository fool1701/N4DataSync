/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nre.util;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

import com.tridium.nre.util.ArrayIterator;

/**
 * Array is a dynamic array similar to Vector or ArrayList
 * except that it stores its elements in an array of a specific
 * type.  This provides fail fast capability when adding
 * items of the wrong type and is easy to use when converting
 * back to a Java array.
 *
 * NOTE: This class was written before J2SE 5.0 was available for all Niagara platforms.
 * With J2SE 5.0 the {@link java.util.Collections} API uses generics, eliminating Array's main
 * advantage over pre-5.0 collections classes.  Programmers writing new classes are encouraged
 * to consider using {@link java.util.ArrayList} instead of this class when it suits their needs,
 * and to replace existing existing Array variables with {@link java.util.ArrayList}.
 *
 * @author    Brian Frank
 * @creation  3 Feb 04
 * @version   $Revision: 16$ $Date: 11/26/07 2:26:25 PM EST$
 * @since     Niagara 3.0
 */
public class Array <T>
  implements Iterable<T>
{             

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>Array(Object.class, 10)</code>
   *
   * @deprecated Use a constructor that has a Class or array parameter instead. This constructor
   * should definitely not be used with a parameterized (that is, not raw) Array variable.
   */
  @SuppressWarnings("unchecked")
  @Deprecated
  public Array()
  {
    this((Class<T>)Object.class, 10);
  }
    
  /**
   * Convenience for <code>Array(ofClass, 10)</code>
   */
  public Array(Class<T> ofClass)
  {
    this(ofClass, 10);
  }
  
  /**
   * Create an array of the specified type.
   */
  @SuppressWarnings("unchecked")
  public Array(Class<T> ofClass, int capacity)
  {
    this.ofClass = ofClass;
    array = (T[])java.lang.reflect.Array.newInstance(ofClass, capacity);
  }  

  /**
   * Convenience for <code>Array(array, array.length)</code>
   */
  public Array(T[] array)
  {
    this(array, array.length);
  }

  /**
   * Construct an Array to use the specified array 
   * as the initial internal array including its
   * contents.
   */
  @SuppressWarnings("unchecked")
  public Array(T[] array, int size)
  {
    this.ofClass = (Class<T>) array.getClass().getComponentType();
    this.array = array;
    this.size = size;
  }           

  /**
   * Construct an Array using <code>Collection.toArray()</code>
   */
  @SuppressWarnings("unchecked")
  public Array(Class<T> ofClass, Collection<T> c)
  {
    this.ofClass = ofClass;
    this.array = c.toArray((T[])java.lang.reflect.Array.newInstance(ofClass, c.size()));
    this.size = array.length;
  }

  /**
   * Convenience for<code>Array(Object.class, c)</code>
   *
   * @deprecated use {@link #Array(Class, Collection)}
   */
  @SuppressWarnings("unchecked")
  @Deprecated // note: no usages found, good news!
  public Array(Collection<T> c)
  {                             
    this((Class<T>)Object.class, c);
  }
  
////////////////////////////////////////////////////////////////
// Identity
////////////////////////////////////////////////////////////////
  
  /**
   * Get the Class type of the items in this array.
   */
  public final Class<T> ofClass()
  {                          
    return ofClass;
  }  
  
  /**
   * Get the item at the specified index.
   */
  public final T get(int index)
  {                                      
    if (index >= size) throw new ArrayIndexOutOfBoundsException(index);
    return array[index];
  }                     
  
  /**
   * Return the index of the specified object using equals(),
   * or -1 if not in this array.
   */
  public final int indexOf(T item)
  {             
    return indexOf(item, 0);
  }                    

  /**
   * Return the index of the specified object using equals(),
   * or -1 if not in this array, starting the search
   * at the specified index.
   */
  public final int indexOf(T item, int fromIndex)
  {
    for(int i=fromIndex; i<size; ++i)
    {
      if (item == null ? array[i] == null : item.equals(array[i]))
      {
        return i;
      }
    }
    return -1;
  }

  /**
   * Return the rightmost index of the specified object using equals(),
   * or -1 if not in this array, searching backwards starting at
   * size() - 1.
   */
  public final int lastIndexOf(T item)
  {             
    return lastIndexOf(item, size-1);
  }                    

  /**
   * Return the rightmost index of the specified object using equals(),
   * or -1 if not in this array, searching backwards starting at the
   * specified index.
   */
  public final int lastIndexOf(T item, int fromIndex)
  {             
    for(int i=fromIndex; i>=0; --i)
    {
      if (item == null ? array[i] == null : item.equals(array[i]))
      {
        return i;
      }
    }
    return -1;
  }                    

  /**
   * Return if this array contains the specified item using equals().
   */
  public final boolean contains(T item)
  {
    return indexOf(item) >= 0;
  }
  
  /**
   * Return the number of items in the array.
   */
  public final int size()
  {
    return size;
  }                  
  
  /**
   * Return if size is zero.
   */
  public final boolean isEmpty()
  {
    return size == 0;
  }                    

  /**
   * Compare another Array for equality.  Return true only if
   * both Arrays have the same size() and ofClass() and every item
   * returns true for their equals() method.
   */
  @SuppressWarnings("unchecked")
  public boolean equals(Object o)
  {                              
    if (o instanceof Array)
    {            
      Array<T> a = (Array<T>)o;
      if (size != a.size || ofClass() != a.ofClass()) return false;
      for(int i=0; i<size; ++i)
      {
        T mi = array[i];
        T ai = a.array[i];
        if (mi == null)
        {                              
          if (ai != null) return false;
        }
        else
        {
          if (ai == null) return false;
          else if (!mi.equals(ai)) return false;
        }
      }       
      return true;
    }
    return false;
  }

  @Override
  public int hashCode()
  {
    int result = Arrays.hashCode(array);
    result = 31 * result + size;
    return result;
  }

  /**
   * Return a string representation of the Array.
   */
  public String toString()
  {
    StringBuilder s = new StringBuilder();
    s.append('{');
    for(int i=0; i<size; ++i)
    {
      if (i > 0) s.append(',');
      s.append(array[i]);
    }
    s.append('}');
    return s.toString();
  }
  
////////////////////////////////////////////////////////////////
// Modifiers
////////////////////////////////////////////////////////////////

  /**
   * Add the item to the end of this array.
   */
  public final <E extends T> boolean add(E item)
  {                                      
    grow(size+1);
    array[size] = item;
    size++;
    return true;
  }
  
  /**
   * Add the item at the specified index.  All items after
   * index are shifted up one index.
   */
  public final <E extends T> void add(int index, E item)
  {                      
    if (index > size) throw new ArrayIndexOutOfBoundsException(index);
    grow(size+1);      
    System.arraycopy(array, index, array, index+1, size-index);
    array[index] = item; 
    size++;
  }            

  /**
   * Convenience for <code>add(array, array.length)</code>.
   */
  public final <E extends T> void addAll(E[] array)
  {
    addAll(array, array.length);
  }

  /**
   * Convenience for <code>add(array.array(), array.size())</code>.
   */
  public final void addAll(Array<? extends T> array)
  {
    addAll(array.array(), array.size());
  }
  
  /**
   * Add all the items from array[0] to array[size-1].
   */
  public final <E extends T> void addAll(E[] array, int size)
  {                                           
    for(int i=0; i<size; ++i) add(array[i]);
  }                          
  
  /**
   * Add all the items from the collection to this array.
   */
  public final void addAll(Collection<? extends T> c)
  {
    c.forEach(this::add);
  }
  
  /**
   * Replace the item at the specified index and return the old item.
   */
  public final <E extends T> T set(int index, E item)
  {                                                     
    if (index >= size) throw new ArrayIndexOutOfBoundsException(index);
    T old = array[index];
    array[index] = item;    
    return old;
  }
  
  /**
   * Remove the item at the specified index and return it.
   */
  public final T remove(int index)
  {                            
    if (index >= size) throw new ArrayIndexOutOfBoundsException(index);
    T item = array[index];
    if (index < array.length)
    {
      System.arraycopy(array, index + 1, array, index, array.length - index - 1);
    }
    array[size-1] = null;     
    size--;
    return item;
  }  
  
  /**
   * If the item is in this array using indexOf() then
   * remove it and return true.  If not in this array 
   * return false.
   */
  public final boolean remove(T item)
  {               
    int index = indexOf(item);
    if (index < 0) return false;
    remove(index);
    return true;
  }              

  /**
   * Return a new Array having this Array's contents,
   * with all the specified items filtered out.
   */
  public Array<T> removeAll(T[] items)
  {               
    return shrink(items, true);
  }              

  /**
   * Remove from this Array all of the items whose index is between
   * fromIndex, inclusive and toIndex, exclusive.  Shifts any succeeding
   * items to the left (reduces their index).
   * This call shortens the array by <tt>(toIndex - fromIndex)</tt> items.
   * (If <tt>toIndex==fromIndex</tt>, this operation has no effect.)
   *
   * @param fromIndex index of first item to be removed.
   * @param toIndex index after last item to be removed.
   */
  public final void removeRange(int fromIndex, int toIndex)
  {
    int numMoved = size - toIndex;
    System.arraycopy(array, toIndex, array, fromIndex, numMoved);

    int newSize = size - (toIndex-fromIndex);
    while (size != newSize)
      array[--size] = null;
  }

  /**
   * Returns a new Array that is a shallow copy of the portion of this 
   * Array that lies between <tt>fromIndex</tt>, inclusive, and <tt>toIndex</tt>, 
   * exclusive.  (If <tt>fromIndex</tt> and <tt>toIndex</tt> are equal, 
   * the returned array is empty.)
   * @param fromIndex low endpoint (inclusive) of the slice.
   * @param toIndex high endpoint (exclusive) of the slice.
   */
  @SuppressWarnings("unchecked")
  public Array<T> slice(int fromIndex, int toIndex)
  {
    int len = toIndex - fromIndex;
    T[] temp = (T[])java.lang.reflect.Array.newInstance(ofClass, len);
    System.arraycopy(array, fromIndex, temp, 0, len);
    return new Array<>(temp);
  }

  /**
   * Return a new Array that contains the intersection of
   * this Array's contents and the specified items. 
   */
  public Array<T> intersection(T[] items)
  {               
    return shrink(items, false);
  }              

  /**
   * shrink
   */
  @SuppressWarnings("unchecked")
  private Array<T> shrink(T[] items, boolean remove)
  {
    int len = remove ? size : 0;
    boolean[] keep = new boolean[size];
    for (int i = 0; i < size; i++) keep[i] = remove;

    for (T item : items)
    {
      int idx = indexOf(item);
      if (idx == -1)
      {
      }
      else
      {
        if (remove)
        {
          len--;
        }
        else
        {
          len++;
        }
        keep[idx] = !remove;
      }
    }

    T[] temp = (T[])java.lang.reflect.Array.newInstance(ofClass(), len);
    len = 0;
    for (int i = 0; i < size; i++)
      if (keep[i]) temp[len++] = array[i];

    return new Array<>(temp);
  }
    
  /**
   * Convenience for <code>add(item)</code> when working
   * with the array as a stack.
   */
  public final <E extends T> void push(E item)
  {                            
    add(item);
  }

  /**
   * Convenience for <code>remove(size()-1)</code> when 
   * working with the array as a stack.
   */
  public final T pop()
  {
    return remove(size-1);
  }

  /**
   * Convenience for <code>get(size()-1)</code> when 
   * working with the array as a stack.
   */
  public final T peek()
  {
    return get(size-1);
  }                   
  
  /**
   * Get the item at index 0 or return null if empty.
   */
  public final T first()
  {                   
    if (size == 0) return null;
    return get(0);
  }

  /**
   * Get the item at index size-1 or return null if empty.
   */
  public final T last()
  {                   
    if (size == 0) return null;
    return get(size-1);
  }
  
  /**
   * Grow the array to ensure the specified length.
   */
  @SuppressWarnings("unchecked")
  public final void grow(int length)
  {
    if (array.length < length)
    {
      int len = Math.max(array.length*2, length);
      if (len < 10) len = 10;
      T[] temp = (T[])java.lang.reflect.Array.newInstance(ofClass(), len);
      System.arraycopy(array, 0, temp, 0, size);
      array = temp;
    }
  }           
  
  /**
   * Get a direct reference to the internal array.  The
   * component type of this array is ofClass().  The length
   * of the array is not guaranteed to be equal to size().
   */
  public final T[] array()
  {                      
    return array;
  }
             
  /**
   * Trim the internal array to size() and get a direct reference 
   * to it.  The component type of this array is ofClass(). 
   */
  @SuppressWarnings("unchecked")
  public final T[] trim()
  {
    if (array.length != size)
    {
      T[] temp = (T[])java.lang.reflect.Array.newInstance(ofClass(), size);
      System.arraycopy(array, 0, temp, 0, size);
      array = temp;
    }                  
    return array;
  }                 

  /**
   * Remove all the items.
   */
  @SuppressWarnings("unchecked")
  public final void clear()
  {                                                         
    array = (T[])java.lang.reflect.Array.newInstance(ofClass(), array.length);
    size = 0;
  }                 
  
  /**
   * Return an instance of ListIterator to iterator 
   * through the items in the array.
   */
  @Override
  public final ListIterator<T> iterator()
  {             
    return new ArrayIterator<>(array, 0, size);
  }

  /**
   * Return a modifiable List which wraps this Array.
   */
  public final List<T> list()
  {
    return new AsList(this);
  }

  class AsList extends AbstractList<T>
  {
    AsList(Array<T> arr) { this.arr = arr; }

    public T get(int index) { return arr.get(index); }
    public int size() { return arr.size(); }
    public T set(int index, T element) { return arr.set(index, element); }
    public void add(int index, T element) { arr.add(index, element); }
    public T remove(int index) { return arr.remove(index); }

    Array<T> arr;
  }
  
  /**
   * Swap two items in the array.
   */
  public void swap(int i1, int i2)
  {
    T temp = get(i1);
    set(i1, get(i2));
    set(i2, temp);
  }

////////////////////////////////////////////////////////////////
// Transforms
////////////////////////////////////////////////////////////////

  /**
   * Return a shallow copy of this array.  The new Array
   * has a different internal array, but points to the 
   * same items.
   */
  @SuppressWarnings("unchecked")
  public Array<T> copy()
  {                                      
    T[] temp = (T[])java.lang.reflect.Array.newInstance(ofClass(), size);
    System.arraycopy(array, 0, temp, 0, size);
    return new Array<>(temp, size);
  }  

  /**
   * Returns a new Array that is a subsequence of this Array. 
   * The subsequence begins with the item at the specified 
   * index and extends to the end of this Array.
   */
  public Array<T> copy(int beginIndex)
  {
    return copy(beginIndex, size);
  }

  /**
   * Returns a new Array that is a subsequence of this Array. 
   * The subsequence begins at the specified beginIndex and 
   * extends to the item at index endIndex - 1. 
   */
  @SuppressWarnings("unchecked")
  public Array<T> copy(int beginIndex, int endIndex)
  {
    if (beginIndex < 0) throw new ArrayIndexOutOfBoundsException(beginIndex);
    if (endIndex > size) throw new ArrayIndexOutOfBoundsException(endIndex);

    int len = endIndex - beginIndex;
    if (len < 0) throw new ArrayIndexOutOfBoundsException(len);

    T[] temp = (T[])java.lang.reflect.Array.newInstance(ofClass(), len);

    System.arraycopy(array, beginIndex, temp, 0, len);
    return new Array<>(temp, len);
  }
  
  /**
   * Return a new Array with this Array's items which
   * return true for <code>filter.include()</code>.
   * For example to filter out Strings of length 0:
   * <pre><code>
   * a = a.filter(new IFilter()
   * {
   *   public boolean accept(Object o) { return o.toString().length() &gt; 0; }
   * });
   * </code></pre>
   */
  public Array<T> filter(IFilter filter)
  {
    Array<T> result = new Array<>(ofClass());
    for(int i=0; i<size; ++i)
    {
      if (filter.accept(array[i])) result.add(array[i]);
    }
    return result;
  }

  /**
   * Return a new Array with all the null items filtered out.
   */
  public Array<T> filterNull()
  {                                  
    return filter(obj -> obj != null);
  }          
  
  /**
   * Return a new Array where the items are the result of 
   * applying the lambda function to all this Array's items.
   * For example to convert an Array of Strings to lowercase:
   * <pre> 
   * a = a.apply(null, new ILambda() 
   *   {
   *     public Object eval(Object o) { return o.toString().toLowerCase(); }
   *   });                                     
   * </pre>          
   * The resultOf parameter specifies the ofClass of the new 
   * array. If resultOf is null then the new array of the 
   * same class as this array.
   */
  @SuppressWarnings("unchecked")
  public <R> Array<R> apply(Class<R> resultOf, ILambda lambda)
  {
    if (resultOf == null) resultOf = (Class<R>) ofClass();
    R[] temp = (R[])java.lang.reflect.Array.newInstance(resultOf, size);
    for(int i=0; i<size; ++i) temp[i] = (R)lambda.eval(array[i]);
    return new Array<>(temp, size);
  }

  /**
   * Convenience for <code>apply(null, lambda)</code>.
   */
  public Array<T> apply(ILambda lambda)
  {
    return apply(null, lambda);
  }
        
  /**
   * Return a new Array with the items sorted using specified Comparator. 
   */
  public Array<T> sort(Comparator<? super T> comparator)
  { 
    Array<T> result = copy();
    SortUtil.sort(result.array, result.array, comparator);
    return result;
  }

  /**
   * Return a new Array with the items sorted in 
   * ascending order using SortUtil.
   */
  public Array<T> sort()
  { 
    Array<T> result = copy();
    SortUtil.sort(result.array, result.array);
    return result;
  }

  /**
   * Return a new Array with the items sorted in 
   * descending order using SortUtil.
   */
  public Array<T> rsort()
  {                                                  
    Array<T> result = copy();
    SortUtil.rsort(result.array, result.array);
    return result;
  }               
  
  /**
   * Return a new Aray with the order of the items reversed.
   */
  @SuppressWarnings("unchecked")
  public Array<T> reverse()
  {                                        
    T[] temp = (T[])java.lang.reflect.Array.newInstance(ofClass(), size);
    for(int i=0; i<size; ++i)
    {
      temp[i] = array[size - i - 1];
    }
    return new Array<>(temp, size);
  }          

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  T[] array;
  int size;
  Class<T> ofClass;
}
