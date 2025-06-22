/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.collection;

import javax.baja.sys.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * PropertyMap represents a BComplex instance as a Map of Property to BValue.
 * The BComplex instance is not modifiable through this interface.
 *
 * @author John Sublett, Peter Kronenberg
 * @creation 2/23/14, 1/19/2016
 * @since Niagara 4.0
 */
public class PropertyMap
  implements Map<Property, BValue>
{
  public PropertyMap(BComplex obj)
  {
    this.obj = obj;
  }

  @Override
  public void clear()
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsKey(Object key)
  {
    if (key instanceof Property)
    {
      Property p = (Property)key;
      if (p.isFrozen())
      {
        try
        {
          obj.get((Property)key);
          return true;
        }
        catch(NoSuchSlotException ex)
        {
          return false;
        }
      }
      else
      {
        return obj.get(p.getName()) != null;
      }
    }
    else
    {
      return false;
    }
  }

  @Override
  public boolean containsValue(Object value)
  {
    SlotCursor<Property> c = obj.getProperties();
    while (c.next())
    {
      if (c.get().equals(value))
      {
        return true;
      }
    }

    return false;
  }

  @Override
  public Set<Map.Entry<Property, BValue>> entrySet()
  {
    Set<Map.Entry<Property, BValue>> entries = new HashSet<>();
    SlotCursor<Property> c = obj.getProperties();
    while (c.next())
    {
      Property p = c.property();
      BValue v = c.get();
      entries.add(new AbstractMap.SimpleEntry<Property, BValue>(p, v));
    }

    return entries;
  }

  public boolean equals(Object o)
  {
    if (o instanceof PropertyMap)
    {
      return ((PropertyMap)o).obj.equals(obj);
    }
    else
    {
      return false;
    }
  }

  @Override
  public BValue get(Object key)
  {
    if (key instanceof Property)
    {
      Property p = (Property)key;
      if (p.isFrozen())
      {
        try
        {
          return obj.get(p);
        }
        catch(NoSuchSlotException ex)
        {
          return null;
        }
      }
      else
      {
        return obj.get(p.getName());
      }
    }
    else
    {
      return null;
    }
  }

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
  public Set<Property> keySet()
  {
    return new PropertySet(obj);
  }

  @Override
  public BValue put(Property property, BValue value)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public void putAll(Map<? extends Property, ? extends BValue> toAdd)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public BValue remove(Object property)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size()
  {
    return obj.getPropertyCount();
  }

  @Override
  public Collection<BValue> values()
  {
    return new ValueCollection(obj);
  }

///////////////////////////////////////////////////////////
// KeySet
///////////////////////////////////////////////////////////

  public static class PropertySet
    implements Set<Property>
  {
    public PropertySet(BComplex obj)
    {
      this.obj = obj;
    }

    @Override
    public boolean add(Property p) { throw new UnsupportedOperationException(); }
    @Override
    public boolean addAll(Collection<? extends Property> c) { throw new UnsupportedOperationException(); }
    @Override
    public void clear() { throw new UnsupportedOperationException(); }
    @Override
    public boolean remove(Object o) { throw new UnsupportedOperationException(); }
    @Override
    public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override
    public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }


    @Override
    public boolean contains(Object o)
    {
      if (!(o instanceof Property))
      {
        return false;
      }

      Property p = (Property)o;
      Property op = obj.getProperty(p.getName());
      return p == op;
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

    public boolean equals(Object o)
    {
      if (o instanceof PropertySet)
      {
        return ((PropertySet)o).obj == obj;
      }
      else
      {
        return false;
      }
    }

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
      return obj.getPropertiesArray();
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        T[] result;
      try
      {
        System.arraycopy(obj.getPropertiesArray(), 0, a, 0, size());
        result = a;
      } catch (IndexOutOfBoundsException e) {
        @SuppressWarnings("unchecked")
        T[] temp = (T[])Array.newInstance(a.getClass().getComponentType(), size());
        System.arraycopy(obj.getPropertiesArray(), 0, temp, 0, size());
        result = temp;
      }
      return result;
    }

    @Override
    public Iterator<Property> iterator()
    {
      return SlotCursorIterator.stream(obj.getProperties(), Property.class)
        .iterator();
    }

    private BComplex obj;
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private BComplex obj;
}
