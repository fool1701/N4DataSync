/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.Relation;
import javax.baja.tag.Relations;
import javax.baja.tag.TagDictionaryService;

/**
 * ImpliedRelations is a Relations implementation that is lazily loaded by
 * delegating to the TagDictionaryService and the set of SmartTagDictionaries.
 *
 * @author John Sublett
 * @creation 2/28/14
 * @since Niagara 4.0
 */
public class ImpliedRelations implements Relations
{
  public ImpliedRelations(TagDictionaryService service, Entity entity)
  {
    this.service = service;
    this.entity = entity;
  }

///////////////////////////////////////////////////////////
// Immutable: Unsupported Operations
///////////////////////////////////////////////////////////

  private static RuntimeException immutable() { return new UnsupportedOperationException("Immutable Relation set"); }

  /** Unsupported operation */
  @Override
  public Collection<Relation> set(Id id, Collection <? extends Entity> endpoints) { throw immutable(); }
  /** Unsupported operation */
  @Override
  public Collection<Relation> set(Id id, Collection<? extends Entity> endpoints, boolean isInbound) { throw immutable(); }
  /** Unsupported operation */
  @Override
  public Relation set(Id id, Entity endpoint) { throw immutable(); }
  /** Unsupported operation */
  @Override
  public Relation add(Relation relation) { throw immutable(); }
  /** Unsupported operation */
  @Override
  public Relation add(Id id, Entity endpoint) { throw immutable(); }
  /** Unsupported operation */
  @Override
  public Relation add(Id id, Entity endpoint, boolean isInbound) { throw immutable(); }
  /** Unsupported operation */
  @Override
  public Collection<Relation> add(Id id, Collection<? extends Entity> endpoints) { throw immutable(); }
  /** Unsupported operation */
  @Override
  public Collection<Relation> add(Id id, Collection<? extends Entity> endpoints, boolean isInbound) { throw immutable(); }
  /** Unsupported operation */
  @Override
  public boolean remove(Relation relation) { throw immutable(); }
  /** Unsupported operation */
  @Override
  public boolean remove(Id id, Entity endpoint) { throw immutable(); }
  /** Unsupported operation */
  @Override
  public boolean removeAll(Id id) { throw immutable(); }

///////////////////////////////////////////////////////////
// Accessors
///////////////////////////////////////////////////////////

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
    Collection<Relation> result = new ArrayList<>();
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
    Collection<Relation> result = new ArrayList<>();
    service.addAllImpliedRelations(entity, result);
    return result;
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
    return service.getImpliedRelation(id, entity);
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
    for (Relation relation : getAll(id))
    {
      if (isMatchingDirection(relation, direction))
      {
        return Optional.of(relation);
      }
    }
    return Optional.empty();
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
    Collection<Relation> relations = new ArrayList<>();
    service.addImpliedRelations(id, entity, relations);
    return relations;
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
    Collection<Relation> result = new ArrayList<>();
    for (Relation relation : getAll(id))
    {
      if (isMatchingDirection(relation, direction))
      {
        result.add(relation);
      }
    }
    return result;
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
    for (Relation relation : getAll(id))
    {
      if (relation.getEndpoint().equals(endpoint))
      {
        return Optional.of(relation);
      }
    }

    return Optional.empty();
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
    for (Relation relation : getAll(id))
    {
      if (relation.getEndpoint().equals(endpoint) && isMatchingDirection(relation, direction))
      {
        return Optional.of(relation);
      }
    }

    return Optional.empty();
  }

  /**
   * Get an iterator over all relations in the collection.
   *
   * @return an iterator over all relations in the collection
   */
  @Override
  public Iterator<Relation> iterator()
  {
    return getAll().iterator();
  }

  /**
   * @return {@code true} if there are no relations in the collection
   */
  @Override
  public boolean isEmpty()
  {
    return !iterator().hasNext();
  }

///////////////////////////////////////////////////////////
// Utils
///////////////////////////////////////////////////////////

  public static boolean isMatchingDirection(Relation relation, int direction)
  {
    return direction == BOTH ||
           (direction == IN  && relation.isInbound()) ||
           (direction == OUT && relation.isOutbound());
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private final TagDictionaryService service;
  private final Entity entity;
}
