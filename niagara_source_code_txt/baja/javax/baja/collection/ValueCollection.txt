/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.collection;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;
import javax.baja.sys.BComplex;
import javax.baja.sys.BValue;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;

/**
 * ValueCollection represents the property values of a BComplex instance as
 * a Collection.  The BComplex instance is not modifiable through this interface.
 *
 * @author John Sublett
 * @creation 3/1/14
 * @since Niagara 4.0
 */
public class ValueCollection
  implements Collection<BValue>
{
  public ValueCollection(BComplex obj)
  {
    this.obj = obj;
  }

///////////////////////////////////////////////////////////
// Accessors
///////////////////////////////////////////////////////////

  @Override
  public boolean contains(Object o)
  {
    if (!(o instanceof BValue))
    {
      return false;
    }

    SlotCursor<Property> c = obj.getProperties();
    while (c.next())
    {
      if (c.get().equals(o))
      {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean containsAll(Collection<?> c)
  {
    Iterator<?> i = c.iterator();
    while (i.hasNext())
    {
      if (!contains(i.next()))
      {
        return false;
      }
    }

    return true;
  }

  @Override
  public boolean equals(Object o)
  {
    if (o instanceof ValueCollection)
    {
      return ((ValueCollection)o).obj == obj;
    }
    else
    {
      return false;
    }
  }

  @Override
  public int hashCode()
  {
    return obj.hashCode();
  }

  @Override
  public boolean isEmpty()
  {
    return obj.getPropertyCount() == 0;
  }

  @Override
  public int size()
  {
    return obj.getPropertyCount();
  }

  @Override
  public Object[] toArray()
  {
    BValue[] result = new BValue[size()];
    SlotCursor<Property> c = obj.getProperties();
    int index = 0;
    while (c.next())
    {
      result[index++] = c.get();
    }
    return result;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T[] toArray(T[] a)
  {
    if (BValue.class.isAssignableFrom(a.getClass().getComponentType()))
    {
      try
      {
        T[] result = size() == a.length ? a : (T[])Array.newInstance(a.getClass().getComponentType(), size());

        SlotCursor<Property> c = obj.getProperties();
        int index = 0;
        while (c.next())
        {
          // Will result in unchecked cast, but we know it's good due to call to isAssignmableFrom()
          result[index++] = (T)c.get();
        }

        return result;
      }
      catch (ClassCastException ex)
      {
        throw new ArrayStoreException();
      }
    }
    else
    {
      throw new ArrayStoreException();
    }
  }

  @Override
  public Iterator<BValue> iterator()
  {
    return SlotCursorIterator.iterator(obj.getProperties());
  }

///////////////////////////////////////////////////////////
// Unsupported operations
///////////////////////////////////////////////////////////

  @Override
  public boolean add(BValue p)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(Collection<? extends BValue> c)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear()
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(Object o)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeAll(Collection<?> c)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(Collection<?> c)
  {
    throw new UnsupportedOperationException();
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private BComplex obj;
}
