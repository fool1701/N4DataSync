/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Relations is used to store a collection of {@link Relation} objects. A Relations collection
 * is semantically equivalent to a mathematical <i>set</i>: it is an unordered collection of
 * distinct {@link Relation} objects.
 * <p>
 * The general behavioral contract of Relations is
 * <ul>
 * <li>Depending on the concrete implementation, all methods that set or add relations
 * to the collection may throw {@link IllegalArgumentException} if some property of the
 * input arguments prevents the method from succeeding. This is often the case if
 * a relation is "implied" or "frozen" by the concrete implementation of Relations.</li>
 * <li>Because of the above property, none of the removal operations are guaranteed to
 * succeed. However, they <strong><i>SHOULD</i></strong> never throw exceptions. Instead
 * they should return {@code false} in that case.</li>
 * <li>Because of the above properties it should be evident that a Relations collection
 * might never be empty.</li>
 * <li>This interface requires non-null arguments for all methods and must be enforced
 * by the implementation.</li>
 * </ul>
 * <p>
 * Unlike {@link Tags}, there is no distinction between single-value and multi-value relations. This
 * means that for any id there may be multiple relations. An implementation <strong>must</strong>
 * always allow multiple relations with the same id.
 * <p>
 * You can never assume an ordering for any of the methods that return a
 * {@code Collection<Relation>}, but it is guaranteed that all relations in the collection are
 * unique.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
public interface Relations extends Iterable<Relation>
{
  /**
 * Create inbound relations with the given id to all the given endpoints and return the newly
 * created relations. Any existing relations with the given id are overwritten.
 *
 * @param id the id for all the relations
 * @param endpoints the endpoints to create relations for
 * @return a {@code Collection<Relation>} of all the newly created relations
 */
  Collection<Relation> set(Id id, Collection<? extends Entity> endpoints);

  /**
   * Create relations of the given direction with the given id to all the given endpoints and return
   * the newly created relations. Any existing relations with the given id are overwritten.
   *
   * @param id the id for all the relations
   * @param endpoints the endpoints to create relations for
   * @param isInbound true if relation direction is from the endpoints
   * @return a {@code Collection<Relation>} for all the newly created relations
   */
  Collection<Relation> set(Id id, Collection<? extends Entity> endpoints, boolean isInbound);

  /**
   * Create an inbound relation with the given id and endpoint and overwrite any existing relations
   * with that id.
   *
   * @param id the id for the relation
   * @param endpoint the endpoint of the new relation
   * @return the new relation
   */
  default Relation set(Id id, Entity endpoint)
  {
    return set(id, Collections.singletonList(endpoint), Relation.INBOUND).iterator().next();
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
  Relation add(Relation relation);

  /**
   * Create an outbound relation with the given id for the given endpoint and add it to the set of
   * relations. If the relation already exists, the tags are merged as in {@link #add(Relation)}.
   *
   * @param id the id for the relation
   * @param endpoint the endpoint of the new relation
   * @return the new relation
   */
  default Relation add(Id id, Entity endpoint)
  {
    return add(id, endpoint, Relation.OUTBOUND);
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
  default Relation add(Id id, Entity endpoint, boolean isInbound)
  {
    return add(new BasicRelation(id, endpoint, isInbound));
  }

  /**
   * Create outbound relations with the given id for each given endpoint and add them to the set of
   * relations. If the relation already exists, the tags are merged as in {@link #add(Relation)}.
   *
   * @param id the id for each relation
   * @param endpoints the endpoints to add relations for
   * @return a {@code Collection<Relation>} of all the newly created relations
   */
  default Collection<Relation> add(Id id, Collection<? extends Entity> endpoints)
  {
    Set<Relation> added = new LinkedHashSet<>();
    for (Entity endpoint : endpoints)
    {
      added.add(add(id, endpoint, Relation.OUTBOUND));
    }
    return added;
  }

  /**
   * Create relations of the given direction with the given id for each given endpoint and add them
   * to the set of relations. If the relation already exists, the tags are merged as in {@link
   * #add(Relation)}.
   *
   * @param id the id for each relation
   * @param endpoints the endpoints to add relations for
   * @param isInbound true if the relation is to be the endpoint entities to add relations for
   * @return a {@code Collection<Relation>} of all the newly created relations
   */
  default Collection<Relation> add(Id id, Collection<? extends Entity> endpoints, boolean isInbound)
  {
    Set<Relation> added = new LinkedHashSet<>();
    for (Entity endpoint : endpoints)
    {
      added.add(add(id, endpoint, isInbound));
    }
    return added;
  }

  /**
   * Attempt to remove the given relation.
   *
   * @return {@code true} if the relation was removed
   */
  boolean remove(Relation relation);

  /**
   * Attempt to remove a relation with the given id and endpoint.
   *
   * @param id the id of the relation to remove
   * @param endpoint the endpoint of the relation to remove
   * @return {@code true} if a relation was removed
   */
  default boolean remove(Id id, Entity endpoint)
  {
    return remove(new BasicRelation(id, endpoint));
  }

  /**
   * Attempts to remove every relation with the given id.
   *
   * @param id the id to remove relations for
   * @return true if any relations were removed
   */
  default boolean removeAll(Id id)
  {
    boolean changed = false;
    for (Relation relation : getAll(id))
    {
      changed |= remove(relation.getId(), relation.getEndpoint());
    }
    return changed;
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
  Collection<Relation> filter(Predicate<Relation> condition, int direction);

  /**
   * Get all the relations.
   *
   * @return a {@code Collection<Relation>} containing every relation
   */
  default Collection<Relation> getAll()
  {
    return getAll(BOTH);
  }

  /**
   * Get all the relations with the given direction.
   *
   * @param direction the relation direction, IN, OUT, or BOTH, to get
   * @return a {@code Collection<Relation>} containing every relation
   */
  default Collection<Relation> getAll(int direction)
  {
    return filter(Objects::nonNull, direction);
  }

  /**
   * Get the first or only relation with the given id.
   *
   * @param id id of the relation to get
   * @return a relation with the given id or empty if the one is not found
   */
  default Optional<Relation> get(Id id)
  {
    return filter(r -> r.getId().equals(id), BOTH).stream().findFirst();
  }

  /**
   * Get the first or only relation with the given id and direction.
   *
   * @param id id of the relation to get
   * @param direction the relation direction, IN, OUT, or BOTH, to get
   * @return a relation with the given id and direction or empty if the one is not found
   */
  default Optional<Relation> get(Id id, int direction)
  {
    return filter(r -> r.getId().equals(id), direction).stream().findFirst();
  }

  /**
   * Get all relations with the given id.
   *
   * @param id the id to return relations for
   * @return a {@code Collection<Relation>} containing every relation with the given id
   */
  default Collection<Relation> getAll(Id id)
  {
    return filter(r -> r.getId().equals(id), BOTH);
  }

  /**
   * Get all relations with the given id and direction.
   *
   * @param id the id to return relations for
   * @param direction the relation direction, IN, OUT, or BOTH, to get
   * @return a {@code Collection<Relation>} containing every relation with the given id and
   * direction
   */
  default Collection<Relation> getAll(Id id, int direction)
  {
    return filter(r -> r.getId().equals(id), direction);
  }

  /**
   * Get the first or only relation with the given id and endpoint.
   *
   * @param id the id of the relation to get
   * @param endpoint the endpoint of the relation to get
   * @return a relation with the given id and endpoint or empty if the one is not found
   */
  default Optional<Relation> get(Id id, Entity endpoint)
  {
    return getAll(id)
      .stream()
      .filter(r -> r.getEndpoint().equals(endpoint))
      .findFirst();
  }

  /**
   * Get the first or only relation with the given id, endpoint, and direction.
   *
   * @param id the id of the relation to get
   * @param endpoint the endpoint of the relation to get
   * @param direction the relation direction, IN, OUT, or BOTH, to get
   * @return a relation with the given id, endpoint, and direction or empty if the one is not found
   */
  default Optional<Relation> get(Id id, Entity endpoint, int direction)
  {
    return getAll(id, direction)
      .stream()
      .filter(r -> r.getEndpoint().equals(endpoint))
      .findFirst();
  }

  /**
   * Get an iterator over all relations in the collection.
   *
   * @return an iterator over all relations in the collection
   */
  @Override
  default Iterator<Relation> iterator()
  {
    return getAll().iterator();
  }

  /**
   * @return {@code true} if there are no relations in the collection
   */
  default boolean isEmpty()
  {
    return !iterator().hasNext();
  }

  int IN = 1;
  int OUT = 2;
  int BOTH = 3;
}
