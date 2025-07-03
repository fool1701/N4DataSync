/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import javax.baja.data.BIDataValue;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.sys.BFacets;
import java.util.*;
import java.util.function.Predicate;

/**
 * EntityWrapper wraps an Entity and a BFacets instance exposing both the entity's tags
 * and the facets as tags.  In the case where there is an id collision between the entity
 * and the facets, the facets get higher precedence.
 *
 * @author John Sublett
 * @creation 3/25/14
 * @since Niagara 4.0
 */
public class EntityWrapper
  implements Entity
{
  public EntityWrapper(Entity entity, BFacets facets)
  {
    this.entity = entity;
    tags = new FacetTags(entity == null ? null : entity.tags(), facets);
  }

  public Optional<BOrd> getOrdToEntity()
  {
    if (entity == null)
      return Optional.empty();
    else
      return entity.getOrdToEntity();
  }

  public Tags tags()
  {
    return tags;
  }

  public Relations relations()
  {
    return entity.relations();
  }

///////////////////////////////////////////////////////////
// Facet Tags
///////////////////////////////////////////////////////////

  private class FacetTags
    implements Tags
  {
    public FacetTags(Tags entityTags, BFacets facets)
    {
      this.entityTags = entityTags;
      this.facets = facets;
    }

    public boolean isEmpty()
    {
      return ((entityTags == null) || entityTags.isEmpty()) && facets.isEmpty();
    }

    public boolean contains(Id id)
    {
      return ((entityTags != null) && entityTags.contains(id)) ||
             (facets.get(Id.idToFacetKey(id)) != null);
    }

    public boolean isMulti(Id id)
    {
      return false;
    }

    public boolean set(Tag tag)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    public boolean set(Id id, BIDataValue value)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    public boolean setMulti(Id id, Collection<? extends BIDataValue> values)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    public boolean addMulti(Id id, Collection<? extends BIDataValue> values)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    public boolean addMulti(Tag tag)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    public boolean addMulti(Id id, BIDataValue value)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    public boolean merge(Collection<Tag> tags)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    public boolean remove(Id id, BIDataValue value)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    public boolean removeAll(Id id)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    public boolean remove(Tag tag)
    {
      throw new UnsupportedOperationException("The tag set is readonly.");
    }

    /**
     * Get the subset of tags in the collection that satisfy the given predicate.
     * <p/>
     * For example:
     * <pre>
     *   // Find all marker tags
     *   //
     *   Collection&lt;Tag> markers = myentity.tags().filter(t -> t.getValue() instanceof BMarker);
     * </pre>
     *
     * @param condition the condition to test for each tag
     * @return a {@code Collection<Tag>} containing all tags that satisfy the condition
     */
    public Collection<Tag> filter(Predicate<Tag> condition)
    {
      ArrayList<Tag> result = new ArrayList<>();
      Iterator<Tag> i = getAll().iterator();
      while (i.hasNext())
      {
        Tag t = i.next();
        if (condition.test(t))
          result.add(t);
      }

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
      Map<Id, Tag> map = new HashMap<>();

      // add all of the entity tags to the map
      if (entityTags != null)
      {
        Iterator<Tag> i = entityTags.getAll().iterator();
        while (i.hasNext())
        {
          Tag t = i.next();
          map.put(t.getId(), t);
        }
      }

      // add or overwrite all of the facets as tags
      if (!facets.isEmpty())
      {
        String[] keys = facets.list();
        for (int k = 0; k < keys.length; k++)
        {
          Id facetId = Id.facetKeyToId(keys[k]);
          map.put(Id.facetKeyToId(keys[k]), new Tag(facetId, (BIDataValue)facets.get(keys[k])));
        }
      }

      return map.values();
    }

    /**
     * Get the value of the tag with the given id if it exists.
     * <p/>
     * If the tag is multi-value the returned value is non-deterministic.
     *
     * @param id the id to search for
     * @return a Optional containing the value of the tag with the given id if it exists
     */
    @Override
    public Optional<BIDataValue> get(Id id)
    {
      if (!facets.isEmpty())
      {
        BIDataValue value = (BIDataValue)facets.get(Id.idToFacetKey(id));
        if (value != null)
          return Optional.of(value);
      }

      if (entityTags == null)
        return Optional.empty();
      else
        return entityTags.get(id);
    }

    @Override
    public Collection<BIDataValue> getValues(Id id)
    {
      throw new UnsupportedOperationException("Not implemented.");
    }

    private Tags entityTags;
    private BFacets facets;
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private Tags tags;
  private Entity entity;
}
