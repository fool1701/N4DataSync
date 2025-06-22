/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import javax.baja.naming.BOrd;
import javax.baja.tag.util.TagSet;
import java.util.Objects;

/**
 * A relation is an immutable ({@link Id}, {@link Entity}) pair.
 * <p>
 * A relation is best understood as a <i>tail-less</i>, labeled edge in a directed graph, where
 * the {@link #getId() id} is the label for the edge, and the head of the edge is the
 * {@link #getEndpoint()} endpoint} entity. The fact that a relation is tail-less is a nice property
 * since it allows relations to be easily constructed independently of their source (i.e. tail) Entity.
 * Further, they can be reused and shared amoung multiple {@link Relations}.
 * <p>
 * A relation also implements {@link Taggable} which can be useful for attaching additional
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
public final class BasicRelation implements Relation, Taggable
{
  private final Id id;
  private final Entity endpoint;
  private final BOrd endpointOrd;
  private final Tags tags;
  private final boolean inbound;

  /**
   * Create a relation to the endpoint entity with the given id. The relation will be inbound and will have no tags.
   *
   * @param id the id of the relation. Must not be {@code null}.
   * @param endpoint the endpoint entity for the relation. Must not be {@code null}.
   */
  public BasicRelation(Id id, Entity endpoint)
  {
    this(id, endpoint, new TagSet(), true);
  }

  /**
   * Create a relation to the endpoint entity with the given id and direction. The relation will have no tags.
   *
   * @param id the id of the relation. Must not be {@code null}.
   * @param endpoint the endpoint entity for the relation. Must not be {@code null}.
   * @param isInbound boolean true if the relation direction inbound from the endpoint entity.
   */
  public BasicRelation(Id id, Entity endpoint, boolean isInbound)
  {
    this(id, endpoint, new TagSet(), isInbound);
  }

  /**
   * Create a relation to the endpoint entity with the given id and tags.
   *
   * @param id the id of the relation. Must not be {@code null}.
   * @param endpoint the endpoint entity for the relation. Must not be {@code null}.
   * @param tags the initial tags for the relation. Must not be {@code null}.
   *             Note: the tags are not copied, so any changes to the tags will
   *             be reflected on this relation.
   * @param isInbound boolean true if the relation direction inbound from the endpoint entity.
   */
  public BasicRelation(Id id, Entity endpoint, Tags tags, boolean isInbound)
  {
    Objects.requireNonNull(id, "id");
//    Objects.requireNonNull(endpoint, "endpoint");
    Objects.requireNonNull(tags, "tags");
    this.id = id;
    this.endpoint = endpoint;
    this.endpointOrd = BOrd.NULL;
    this.tags = tags;
    this.inbound = isInbound;
  }

  /**
   * Create a relation to the endpoint entity with the given id. The relation will have no tags.
   *
   * @param id the id of the relation. Must not be {@code null}.
   * @param endpointOrd the BORd to the  endpoint entity for the relation. Must not be {@code null}.
   */
  public BasicRelation(Id id, BOrd endpointOrd)
  {
    this(id, endpointOrd, new TagSet());
  }

  /**
   * Create a relation to the endpoint entity with the given id and tags.
   *
   * @param id the id of the relation. Must not be {@code null}.
   * @param endpointOrd the BORd to the endpoint entity for the relation. Must not be {@code null}.
   * @param tags the initial tags for the relation. Must not be {@code null}.
   *             Note: the tags are not copied, so any changes to the tags will
   *             be reflected on this relation.
   */
  public BasicRelation(Id id, BOrd endpointOrd, Tags tags)
  {
    this(id, endpointOrd, tags, true);
  }

  /**
   * Create a relation to the endpoint entity with the given id and tags.
   *
   * @param id the id of the relation. Must not be {@code null}.
   * @param endpointOrd the BORd to the endpoint entity for the relation. Must not be {@code null}.
   * @param tags the initial tags for the relation. Must not be {@code null}.
   *             Note: the tags are not copied, so any changes to the tags will
   *             be reflected on this relation.
   * @param isInbound boolean true if the relation direction inbound from the endpoint entity.
   *
   * @since Niagara 4.4
   */
  public BasicRelation(Id id, BOrd endpointOrd, Tags tags, boolean isInbound)
  {
    Objects.requireNonNull(id, "id");
//    Objects.requireNonNull(endpointOrd, "endpointOrd");
    Objects.requireNonNull(tags, "tags");
    this.id = id;
    this.endpointOrd = endpointOrd;
    this.endpoint = null;
    this.tags = tags;
    this.inbound = isInbound;
  }

  /**
   * Get the relations' id.
   *
   * @return the relation's id
   */
  @Override
  public Id getId()
  {
    return id;
  }

  /**
   * Test for inbound relation relative to entity where the
   *   relation is stored
   *
   * @return true if relation is an inbound relation
   */
  @Override
  public boolean isInbound()
  {
    return inbound;
  }

  /**
   * Test for outbound relation relative to entity where the
   *   relation is stored
   *
   * @return true if relation is an outbound relation
   */
  @Override
  public boolean isOutbound()
  {
    return !inbound;
  }

  /**
   * Get the endpoint entity for this relation.
   *
   * @return the endpoint entity for this relation
   */
  @Override
  public Entity getEndpoint()
  {
    return endpoint;
  }

  /**
   * Get the endpointOrd to the entity for this relation.
   *
   * @return the endpoint entity for this relation
   */
  @Override
  public BOrd getEndpointOrd()
  {
    return endpointOrd;
  }

  /**
   * Get the tags for this relation.
   *
   * @return the tags for this relation
   */
  @Override
  public Tags tags()
  {
    return tags;
  }

  /**
   * Two relations are equal <i>iff</i> they have the same id and endpoint. (The equality of
   * BComplex subclasses, however, cannot override {@link Object#equals(Object)}). Tags are
   * <i>NOT</i> taken into account when testing for equality.
   *
   * @param o the object to test for equality with
   * @return true if this relation is equal to the given object
   */
  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }

    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    BasicRelation relation = (BasicRelation)o;

    if (!id.equals(relation.id))
    {
      return false;
    }

    if (endpoint == null)
    {
      return endpointOrd.equals(relation.endpointOrd);
    }

    try
    {
      return endpoint.equals(relation.endpoint);
    }
    catch(Exception e)
    {
      return false;
    }
  }

  @Override
  public int hashCode()
  {
    int result = id.hashCode();

    if (endpoint != null)
    {
      result = 31 * result + endpoint.hashCode();
    }
    else if (endpointOrd != null)
    {
      result = 31 * result + endpointOrd.hashCode();
    }

    return result;
  }

  @Override
  public String toString()
  {
    return "BasicRelation; id: " + id + "; inbound? " + inbound + "; endpoint: " +
      (endpoint != null ? endpoint : endpointOrd).toString();
  }
}
