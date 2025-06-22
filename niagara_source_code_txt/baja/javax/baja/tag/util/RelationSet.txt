/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag.util;

import static javax.baja.tag.util.ImpliedRelations.isMatchingDirection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import javax.baja.tag.BasicRelation;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.Relation;
import javax.baja.tag.Relations;
import javax.baja.tag.Tags;

/**
 * A basic {@link Relations} implementation.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
public class RelationSet implements Relations
{
  private final Map<Id, Set<Relation>> relations = new HashMap<>();

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
    return set(id, endpoints, Relation.INBOUND);
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
    Set<Relation> rels = new HashSet<>();
    for (Entity endpoint : endpoints)
    {
      rels.add(new BasicRelation(id, endpoint, isInbound));
    }
    relations.put(id, rels);
    return rels;
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
    Objects.requireNonNull(relation);
    Id id = relation.getId();
    if (!relations.containsKey(id))
    {
      relations.put(id, new HashSet<>(Collections.singletonList(relation)));
      return relation;
    }
    final Set<Relation> current = relations.get(id);

    // If the relation is already present, merge the tags from the input into that one,
    // and then return the already present relation. Otherwise, just add the input relation.
    //
    Optional<Relation> maybeRelation = current.stream().filter(relation::equals).findFirst();
    return maybeRelation
      .map(r -> { r.tags().merge(relation.tags().getAll()); return r; })
      .orElseGet(() -> {
        current.add(relation);
        return relation;
      });
  }

  /**
   * Attempt to remove the given relation.
   *
   * @return {@code true} if the relation was removed
   */
  @Override
  public boolean remove(Relation r)
  {
    final Id id = r.getId();
    if (relations.getOrDefault(id, Collections.emptySet()).remove(r))
    {
      relations.computeIfPresent(id, (_id, current) -> current.isEmpty() ? null : current);
      return true;
    }
    return false;
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
    List<Relation> matches = new ArrayList<>();
    relations.forEach((id, rels) -> {
      rels.forEach(r -> {
        if (condition.test(r) && isMatchingDirection(r, direction))
        {
          matches.add(r);
        }
      });
    });
    return matches;
  }
}
