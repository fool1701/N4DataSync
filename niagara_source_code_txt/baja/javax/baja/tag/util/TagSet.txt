/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag.util;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.baja.data.BIDataValue;
import javax.baja.tag.Id;
import javax.baja.tag.Tag;
import javax.baja.tag.Tags;

/**
 * A basic {@link Tags} implementation.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
public class TagSet implements Tags
{
  private final Map<Id, Object> tags = new HashMap<>();

  @Override
  @SuppressWarnings("unchecked")
  public Optional<BIDataValue> get(Id id)
  {
    Object val = tags.get(id);
    if (val instanceof BIDataValue)
      return Optional.of((BIDataValue)val);
    else if (val instanceof Collection)
      return Optional.of(((Collection<BIDataValue>)val).iterator().next());
    else
      return Optional.empty();
  }

  @Override
  public boolean isMulti(Id id)
  {
    return contains(id) && tags.get(id) instanceof Collection;
  }

  @Override
  public boolean setMulti(Id id, Collection<? extends BIDataValue> values)
  {
    Objects.requireNonNull(id);
    Objects.requireNonNull(values);
    tags.put(id, new HashSet<>(values));
    return true;
  }

  @Override
  public boolean set(Tag tag)
  {
    Objects.requireNonNull(tag);
    tags.put(tag.getId(), tag.getValue());
    return true;
  }

  @Override
  public boolean addMulti(Id id, Collection<? extends BIDataValue> values)
  {
    if (!tags.containsKey(id))
    {
      return setMulti(id, values);
    }
    return getAsMulti(id).addAll(values);
  }

  @SuppressWarnings("unchecked")
  private Set<BIDataValue> getAsMulti(Id id)
  {
    Object v = tags.get(id);
    Objects.requireNonNull(v);
    if (v instanceof Set)
    {
      return (Set<BIDataValue>)v;
    }
    throw new IllegalArgumentException(String.format("%s is not multi-value", id));
  }

  @Override
  public boolean remove(Id id, BIDataValue value)
  {
    if (!isMulti(id))
    {
      return tags.remove(id) != null;
    }
    Set<BIDataValue> values = getAsMulti(id);
    if (values.remove(value))
    {
      if (values.isEmpty())
      {
        tags.remove(id);
      }
      return true;
    }
    return false;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Collection<Tag> filter(Predicate<Tag> f)
  {
    List<Tag> matches = new ArrayList<>();
    for (Entry<Id, Object> entry : tags.entrySet())
    {
      final Id id = entry.getKey();
      final Object ov = entry.getValue();
      final Set<BIDataValue> values = ov instanceof BIDataValue
        ? Collections.singleton((BIDataValue)ov)
        : (Set<BIDataValue>)ov;
      for (BIDataValue dv : values)
      {
        Tag t = new Tag(id, dv);
        if (f.test(t))
        {
          matches.add(t);
        }
      }
    }
    return matches;
  }
}
