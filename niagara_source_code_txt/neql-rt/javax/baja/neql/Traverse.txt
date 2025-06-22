/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import javax.baja.tag.Id;

/**
 * A Traverse statement is a NEQL statement for selecting a collection of Entities by
 * traversing a relation from either end of the relation.
 *
 * @author John Sublett
 * @creation 11/3/2014
 * @since Niagara 4.0
 */
public class Traverse
  extends AstNode
{
  public Traverse(Id relationId, boolean outbound, Expression predicate)
  {
    this.relationId = relationId;
    this.outbound = outbound;
    this.predicate = predicate;
  }

  /**
   * Get the AST node type constant for this node type as defined
   * in AstNode.
   *
   * @return Returns the integer node type code.
   */
  @Override
  public int getNodeType()
  {
    return AstNode.AST_TRAVERSE;
  }

  /**
   * Get the id for the relation to be traversed.
   *
   * @return Returns the Id of a relation to traverse.
   */
  public Id getRelationId()
  {
    return relationId;
  }

  /**
   * Determine whether the traversal is for an outbound relation.
   *
   * @return Returns true for an outbound traversal.  False for inbound.
   */
  public boolean isOutbound()
  {
    return outbound;
  }

  /**
   * Determine whether the traversal is for an inbound relation.
   *
   * @return Returns true for an inbound traversal.  False for outbound.
   */
  public boolean isInbound()
  {
    return !outbound;
  }

  /**
   * Get the predicate expression used to filter related objects.
   *
   * @return Returns a boolean Expression to be used as the filter.
   */
  public Expression getPredicate()
  {
    return predicate;
  }

  @Override
  protected String getNodeDisplay()
  {
    return relationId + " " + (outbound ? "outbound" : "inbound");
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private Id      relationId;
  private boolean outbound;
  private Expression predicate;

}