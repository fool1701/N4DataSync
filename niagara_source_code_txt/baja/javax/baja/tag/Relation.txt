/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import javax.baja.naming.BOrd;

/**
 * A relation is an immutable ({@link Id}, {@link Entity}) pair.
 * <p>
 * A relation is best understood as a <i>tail-less</i>, labeled edge in a directed graph, where
 * the {@link #getId() id} is the label for the edge, and the head of the edge is the
 * {@link #getEndpoint() endpoint} entity. The fact that a relation is tail-less is a nice property
 * since it allows relations to be easily constructed independently of their endpoint (i.e. tail) Entity.
 * Further, they can be reused and shared among multiple {@link Relations}.
 * <p>
 * A relation also extends {@link Taggable} which can be useful for attaching additional
 * metadata to the relation. But note that a relation's tags are <strong>ignored</strong>
 * when testing for equality (see {@link #equals(Object) equals}).
 * <p>
 * The following example demonstrates how to relate two entities.
 * <pre>{@code
 *   Entity manager = getManagerEntity();
 *   Entity bob = getEmployeeEntity("bob");
 *   Id managesId = Id.newId("mydict:manages");
 *
 *   // Add a "manages" relation between the manager and bob
 *   //
 *   manager.relations().add(managesId, bob);
 *
 *   // Conceptually, we now have the following graph:
 *   //
 *   // [manager] -- mydict:manages --> [bob]
 *
 *   // A manager usually manages more than one employee
 *   //
 *   manager.relations().add(managesId, getEmployeeEntity("jane"));
 *
 *   // Now our graph is
 *   //
 *   //                              --> [bob]
 *   // [manager] -- mydict:manages -|
 *   //                              --> [jane]
 * }</pre>
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 * @see Entity
 * @see Relations
 */
public interface Relation extends Taggable
{
  /**
   * Get the relations' id.
   *
   * @return the relation's id
   */
  Id getId();

  /**
   * Test for inbound relation relative to entity where the
   *   relation is stored
   *
   * @return true if relation is an inbound relation
   */
  boolean isInbound();

  /**
   * Test for outbound relation relative to entity where the
   *   relation is stored
   *
   * @return true if relation is an outbound relation
   */
  boolean isOutbound();

  /**
   * Get the endpoint entity for this relation.
   *
   * @return the endpoint entity for this relation
   */
  Entity getEndpoint();

  /**
   * Get the endpointOrd to the entity for this relation.
   *
   * @return the endpoint entity for this relation
   */
  BOrd getEndpointOrd();

  /**
   * Get the endpoint entity for this relation.
   *
   * @return the endpoint entity for this relation
   */
//  public Entity getEndpoint(BObject base);

  /**
   * Get the tags for this relation.
   *
   * @return the tags for this relation
   */
  Tags tags();

  /**
   * Two relations are equal <i>iff</i> they have the same id and endpoint. (The equality of
   * BComplex subclasses, however, cannot override {@link Object#equals(Object)}). Tags are
   * <i>NOT</i> taken into account when testing for equality.
   *
   * @param o the object to test for equality with
   * @return true if this relation is equal to the given object
   */
  @Override
  boolean equals(Object o);

  @Override
  int hashCode();

  boolean INBOUND = true;
  boolean OUTBOUND = false;
}
