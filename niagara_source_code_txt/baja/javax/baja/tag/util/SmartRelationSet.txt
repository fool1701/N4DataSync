/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag.util;

import static javax.baja.tag.util.ImpliedRelations.isMatchingDirection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javax.baja.collection.CompoundIterator;
import javax.baja.collection.FilteredIterator;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.Relation;
import javax.baja.tag.Relations;
import javax.baja.tag.Tags;

import com.tridium.tag.RelationKey;

/**
 * A SmartRelationSet is an implementation of the Relations interface. It maintains separate subsets
 * of relations for smart relations and direct relations.  Any methods that would modify the
 * relation set only operate on the direct relations.
 *
 * @author John Sublett
 * @creation 2/28/14
 * @since Niagara 4.0
 */
public class SmartRelationSet implements Relations
{
  public SmartRelationSet(Relations smart, Relations direct)
  {
    this.smart = smart;
    this.direct = direct;
  }

  /**
   * Create inbound relations with the given id to all the given endpoints and return the newly
   * created relations. Any existing relations with the given id are overwritten.
   *
   * @param id the id for all the relations
   * @param endpoints the endpoints to create relations for
   * @return a {@code Collection<Relation>} of all the newly created relations
   */
  @Override
  public Collection<Relation> set(Id id, Collection<? extends Entity> endpoints)
  {
    return direct.set(id, endpoints);
  }

  /**
   * Create relations of the given direction with the given id to all the given endpoints and return
   * the newly created relations. Any existing relations with the given id are overwritten.
   *
   * @param id the id for all the relations
   * @param endpoints the endpoints to create relations for
   * @param isInbound true if relation direction is from the endpoints
   * @return a {@code Collection<Relation>} of all the newly created relations
   */
  @Override
  public Collection<Relation> set(Id id, Collection<? extends Entity> endpoints, boolean isInbound)
  {
    return direct.set(id, endpoints, isInbound);
  }

  /**
   * Create an inbound relation with the given id and endpoint and overwrite any existing relations
   * with that id.
   *
   * @param id the id for the relation
   * @param endpoint the endpoint of the new relation
   * @return the new relation
   */
  @Override
  public Relation set(Id id, Entity endpoint)
  {
    return direct.set(id, endpoint);
  }

  /**
   * Add the given relation to the set of relations. The tags will be preserved in the
   * new relation. Implementations are not required to return the input relation as the result.
   * <p>
   * If the relation already exists, then the tags are {@link Tags#merge(Collection) merged}
   * and the existing relation is returned.
   * <p>
   * <strong>It is never valid to assume that the returned relation object is the same as the
   * input relation!</strong>
   * <pre>{@code
   *   // This is not necessarily true!
   *   //
   *   Relation relation = new BasicRelation(id, entity);
   *   assert relation == entity.add(relation);
   *
   *   // But this is always true
   *   //
   *   Relation addedRelation = entity.add(relation);
   *   assert relation.id.equals(addedRelation.id) &&
   *          relation.endpoint.equals(addedRelation.endpoint);
   * }</pre>
   *
   * @param relation the relation to add
   * @return the newly added relation
   */
  @Override
  public Relation add(Relation relation)
  {
    return direct.add(relation);
  }

  /**
   * Create a relation of the given direction with the given id for the given endpoint and add it to
   * the set of relations. If the relation already exists, the tags are merged as in {@link
   * #add(Relation)}.
   *
   * @param id the id for the relation
   * @param endpoint the endpoint of the new relation
   * @param isInbound true if relation direction is from the endpoint
   * @return the new relation
   */
  @Override
  public Relation add(Id id, Entity endpoint, boolean isInbound)
  {
    return direct.add(id, endpoint, isInbound);
  }

  /**
   * Create outbound relations with the given id for each given endpoint and add them to the set of
   * relations. If the relation already exists, the tags are merged as in {@link #add(Relation)}.
   *
   * @param id the id for each relation
   * @param endpoints the endpoints to add relations for
   * @return a {@code Collection<Relation>} of all the newly created relations
   */
  @Override
  public Collection<Relation> add(Id id, Collection<? extends Entity> endpoints)
  {
    return direct.add(id, endpoints);
  }

  /**
   * Attempt to remove the given relation.
   *
   * @return {@code true} if the relation was removed
   */
  @Override
  public boolean remove(Relation relation)
  {
    return direct.remove(relation);
  }

  /**
   * Attempt to remove a relation with the given id and endpoint.
   *
   * @param id the id of the relation to remove
   * @param endpoint the endpoint of the relation to remove
   * @return {@code true} if a relation was removed
   */
  @Override
  public boolean remove(Id id, Entity endpoint)
  {
    return direct.remove(id, endpoint);
  }

  /**
   * Attempts to remove every relation with the given id.
   *
   * @param id the id to remove relations for
   * @return true if any relations were removed
   */
  @Override
  public boolean removeAll(Id id)
  {
    return direct.removeAll(id);
  }

  /**
   * Get the subset of relations in the collection that satisfy the given predicate.
   * <p>
   * For example:
   * <pre>{@code
   *   // Find all relations that are tagged
   *   //
   *   Collection<Relation> tagged = myEntity.relations().filter(r -> !r.tags().isEmpty());
   * }</pre>
   *
   * @param condition the condition to test for each relation
   * @param direction the relation direction, IN, OUT, or BOTH, to get
   * @return a {@code Collection<Relation>} where every relation satisfies the condition
   */
  @Override
  public Collection<Relation> filter(Predicate<Relation> condition, int direction)
  {
    List<Relation> result = new ArrayList<>();
    for (Relation relation : this)
    {
      if (condition.test(relation) && isMatchingDirection(relation, direction))
      {
        result.add(relation);
      }
    }
    return result;
  }

  /**
   * Get all the relations.
   *
   * @return a {@code Collection<Relation>} containing every relation
   */
  @Override
  public Collection<Relation> getAll()
  {
    return mergeRelations(direct.getAll(), smart.getAll());
  }

  /**
   * Get all the relations with the given direction.
   *
   * @param direction the relation direction, IN, OUT, or BOTH, to get
   * @return a {@code Collection<Relation>} containing every relation
   */
  @Override
  public Collection<Relation> getAll(int direction)
  {
    return mergeRelations(direct.getAll(direction), smart.getAll(direction));
  }

  /**
   * Get the first or only relation with the given id.
   *
   * @param id id of the relation to get
   * @return a relation with the given id or empty if the one is not found
   */
  @Override
  public Optional<Relation> get(Id id)
  {
    Optional<Relation> r = direct.get(id);
    return r.isPresent() ? r : smart.get(id);
  }

  /**
   * Get the first or only relation with the given id and direction.
   *
   * @param id id of the relation to get
   * @param direction the relation direction, IN, OUT, or BOTH, to get
   * @return a relation with the given id and direction or empty if the one is not found
   */
  @Override
  public Optional<Relation> get(Id id, int direction)
  {
    Optional<Relation> r = direct.get(id, direction);
    return r.isPresent() ? r : smart.get(id, direction);
  }

  /**
   * Get all relations with the given id.
   *
   * @param id the id to return relations for
   * @return a {@code Collection<Relation>} containing every relation with the given id
   */
  @Override
  public Collection<Relation> getAll(Id id)
  {
    return mergeRelations(direct.getAll(id), smart.getAll(id));
  }

  /**
   * Get all relations with the given id and direction.
   *
   * @param id the id to return relations for
   * @param direction the relation direction, IN, OUT, or BOTH, to get
   * @return a {@code Collection<Relation>} containing every relation with the given id and
   * direction
   */
  @Override
  public Collection<Relation> getAll(Id id, int direction)
  {
    return mergeRelations(direct.getAll(id, direction), smart.getAll(id, direction));
  }

  /**
   * Get the first or only relation with the given id and endpoint.
   *
   * @param id the id of the relation to get
   * @param endpoint the endpoint of the relation to get
   * @return a relation with the given id and endpoint or empty if the one is not found
   */
  @Override
  public Optional<Relation> get(Id id, Entity endpoint)
  {
    Optional<Relation> r = direct.get(id, endpoint);
    return r.isPresent() ? r : smart.get(id, endpoint);
  }

  /**
   * Get the first or only relation with the given id, endpoint, and direction.
   *
   * @param id the id of the relation to get
   * @param endpoint the endpoint of the relation to get
   * @param direction the relation direction, IN, OUT, or BOTH, to get
   * @return a relation with the given id, endpoint, and direction or empty if the one is not found
   */
  @Override
  public Optional<Relation> get(Id id, Entity endpoint, int direction)
  {
    Optional<Relation> r = direct.get(id, endpoint, direction);
    return r.isPresent() ? r : smart.get(id, endpoint, direction);
  }

  /**
   * Get an iterator over all relations in the collection.
   *
   * @return an iterator over all relations in the collection
   */
  @Override
  public Iterator<Relation> iterator()
  {
    @SuppressWarnings({"unchecked", "rawtypes"}) Iterator<Relation>[] subs = new Iterator[2];
    subs[0] = new FilteredIterator<>(new ExcludeDirectDuplicatesFilter(), smart.iterator());
    subs[1] = direct.iterator();
    return new CompoundIterator<>(subs);
  }

  /**
   * @return {@code true} if there are no relations in the collection
   */
  @Override
  public boolean isEmpty()
  {
    return smart.isEmpty() && direct.isEmpty();
  }

///////////////////////////////////////////////////////////
// Utility
///////////////////////////////////////////////////////////

  private static Collection<Relation> mergeRelations(Collection<Relation> directs, Collection<Relation> smarts)
  {
    if (directs.isEmpty()) { return smarts; }
    if (smarts.isEmpty()) { return directs; }

    int directsSize = directs.size();
    Collection<Relation> result = new ArrayList<>(directsSize + smarts.size());
    result.addAll(directs);

    // Even for small number of relations in either the direct or smart collections, using a hash
    // set was quicker then comparing each smart to each direct.
    // Need to create a composite key because the equals method of BRelation only uses reference
    // equality and does not account for id, endpoint/endpointOrd, and direction.
    HashSet<RelationKey> set = new HashSet<>(directsSize);
    for (Relation direct : directs)
    {
      set.add(new RelationKey(direct.getId(), direct.getEndpoint(), direct.getEndpointOrd(), getDirection(direct)));
    }

    RelationKey smartKey = new RelationKey();
    for (Relation smart : smarts)
    {
      smartKey.setId(smart.getId());
      smartKey.setEndpoint(smart.getEndpoint());
      smartKey.setEndpointOrd(smart.getEndpointOrd());
      smartKey.setDirection(getDirection(smart));
      if (!set.contains(smartKey))
      {
        result.add(smart);
      }
    }

    return result;
  }

  private static int getDirection(Relation relation)
  {
    if (relation.isInbound())
    {
      return relation.isOutbound() ? BOTH : IN;
    }
    else
    {
      return relation.isOutbound() ? OUT : 0;
    }
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private final Relations smart;
  private final Relations direct;

///////////////////////////////////////////////////////////
// Predicate
///////////////////////////////////////////////////////////

  private class ExcludeDirectDuplicatesFilter implements Predicate<Relation>
  {
    /**
     * Test the relation against the filter.
     *
     * @param relation Returns true if the relation is NOT in the direct relations: does not have
     * the same id, direction, and endpoint.
     * @return Returns true if the relation is not already defined in the direct tags.
     */
    @Override
    public boolean test(Relation relation)
    {
      int direction = relation.isOutbound() ? Relations.OUT : Relations.IN;
      return !direct.get(relation.getId(), relation.getEndpoint(), direction).isPresent();
    }
  }
}
