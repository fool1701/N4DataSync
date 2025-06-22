/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.naming.BOrd;

/**
 * RelationKnob is the mirror of a relation which may be accessed
 * on the relation endpoint component.
 *
 * @author    Andy Saunders
 * @creation  18 Apr 14
 * @since     Baja 4.0
 */
public interface RelationKnob
{ 

  /**
   * Get the mirrored relation.
   * @return may be null
   */
  public BRelation getRelation();

  /**
   * Get the relation ord.
   * @return should never be null.
   */
  public String getRelationId();

  /**
   * Get the relation ord.
   * @return should never be null.
   */
  public BOrd getRelationOrd();

  /**
   * Get the relation tags.
   * @return may be null.
   */
  public BFacets getRelationTags();

  /**
   * Get the relation component.
   * @return may be null.
   */
  public BComponent getRelationComponent();

  /**
   * Get the relation endpoint ord.
   * @return should never be null.
   */
  public BOrd getEndpointOrd();

  /**
   * Get the relation endpoint component.
   * @return should never be null.
   */
  public BComponent getEndpointComponent();

}
