/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import java.util.Optional;
import javax.baja.naming.BOrd;

/**
 * An entity is an object that can be tagged and related to other entities. An entity is best
 * understood as a node in a directed graph. An "edge" can be created to another entity by
 * adding a {@link Relation} to its set of {@link #relations()}.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 * @see Relation
 * @see Relations
 */
public interface Entity extends Taggable
{
  /**
   * Get the relations for this entity.
   * <p>
   * The relations are not thread-safe unless a concrete implementation of entity
   * makes a special guarantee of thread safety.
   *
   * @return the relations for this entity.
   */
  Relations relations();

  /**
   * Get the absolute ord to the entity if it has one.
   *
   * @return an Optional which if present will contain the absolute ord to the entity.
   */
  Optional<BOrd> getOrdToEntity();
}
