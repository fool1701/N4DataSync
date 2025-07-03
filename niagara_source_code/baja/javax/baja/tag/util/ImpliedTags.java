/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import javax.baja.collection.FilteredIterator;
import javax.baja.data.BIDataValue;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.SmartTagDictionary;
import javax.baja.tag.Tag;
import javax.baja.tag.TagDictionaryService;
import javax.baja.tag.Tags;

/**
 * ImpliedTags is a Tags implementation that is lazily loaded by delegating
 * to the TagDictionaryService and the set of SmartTagDictionaries.
 *
 * @author John Sublett
 * @creation 2/17/14
 * @since Niagara 4.0
 */
public class ImpliedTags
  implements Tags
{
  public ImpliedTags(TagDictionaryService service, Entity entity)
  {
    this.service = service;
    this.entity = entity;
  }

  /**
   * This method delegates to get(id) and checks that the result is
   * present and that the value is a collection.
   *
   * @return Returns true if the tag exists and is a collection, false otherwise.
   */
  @Override
  public boolean isMulti(Id id)
  {
    Optional<BIDataValue> o = get(id);
    return o.isPresent() && o.get() instanceof Collection;
  }

///////////////////////////////////////////////////////////
// Immutability overrides
///////////////////////////////////////////////////////////

  @Override
  public boolean set(Tag tag)
  {
    return false;
  }

  @Override
  public boolean set(Id id, BIDataValue value)
  {
    return false;
  }

  @Override
  public boolean setMulti(Id id, Collection<? extends BIDataValue> values)
  {
    return false;
  }

  @Override
  public boolean addMulti(Id id, Collection<? extends BIDataValue> values)
  {
    return false;
  }

  @Override
  public boolean addMulti(Tag tag)
  {
    return false;
  }

  @Override
  public boolean addMulti(Id id, BIDataValue value)
  {
    return false;
  }

  @Override
  public boolean merge(Collection<Tag> tags)
  {
    return false;
  }

  @Override
  public boolean remove(Id id, BIDataValue value)
  {
    return false;
  }

  @Override
  public boolean removeAll(Id id)
  {
    return false;
  }

  @Override
  public boolean remove(Tag tag)
  {
    return false;
  }

///////////////////////////////////////////////////////////
// Tag access
///////////////////////////////////////////////////////////

  @Override
  public Collection<Tag> filter(Predicate<Tag> condition)
  {
    List<Tag> result = new ArrayList<>();
    Iterator<Tag> filtered = new FilteredIterator<>(condition, iterator());
    while (filtered.hasNext())
      result.add(filtered.next());

    return result;
  }

  /**
   * Get all tags in the collection.
   *
   * @return a {@code Collection<Tag>} containing all tags in the collection
   */
  @Override
  public Collection<Tag> getAll()
  {
    List<Tag> result = new ArrayList<>();
    for (Tag tag : this) result.add(tag);
    return result;
  }

  /**
   * Get the first value for the tag with the specified id.
   *
   * @param id The id of the tag to retrieve from the collection.
   * @return Returns the first value for the tag with the specified id or null
   * if the tag is not in the collection.
   */
  @Override
  public Optional<BIDataValue> get(Id id)
  {
    // first check the tags that are already loaded for this instance
    Tag tag = loaded.get(id);
    if (tag != null)
    {
      return Optional.of(tag.getValue());
    }
    else
    {
      // if not loaded, check with the TagDictionaryService
      Optional<Tag> otag = service.getImpliedTag(id, entity);
      if (otag.isPresent())
      {
        loaded.put(id, otag.get());
        return Optional.of(otag.get().getValue());
      }
      else
      {
        return Optional.empty();
      }
    }
  }

  /**
   * Get all of the values for the specified id.
   */
  @Override
  public Collection<BIDataValue> getValues(Id id)
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Get all tags that have an {@link Id} with the given dictionary.
   *
   * @param dictionary the dictionary name of tags to search for
   * @return a {@code Collection<Tag>} that contains all tags in the given dictionary
   */
  @Override
  public Collection<Tag> getInDictionary(String dictionary)
  {
    Optional<SmartTagDictionary> otd = service.getSmartTagDictionary(dictionary);
    if (otd.isPresent())
    {
      return otd.get().getAllImpliedTags(entity);
    }
    else
      return Collections.emptyList();
  }

  /**
   * Get an iterator over all tags in the collection.
   *
   * @return an Iterator over all tags in the collection
   */
  @Override
  public Iterator<Tag> iterator()
  {
    return service.getImpliedTags(entity).iterator();
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private TagDictionaryService service;
  private Entity entity;
  private Map<Id, Tag> loaded = new HashMap<>();
}
