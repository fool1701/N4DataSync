/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import javax.baja.neql.AstNode;

/**
 * A Select statement is a NEQL statement for selecting a collection of Entities
 *
 * @author John Sublett
 * @creation 01/27/2014
 * @since Niagara 4.0
 */
public class Select
  extends AstNode
{
  public Select(Projection projection, Expression predicate)
  {
    this.projection = projection;
    this.predicate = predicate;
  }

  @Override
  public int getNodeType()
  {
    return AstNode.AST_SELECT;
  }

  public boolean hasProjection()
  {
    return projection != null;
  }

  public Projection getProjection()
  {
    return projection;
  }

  public boolean hasPredicate()
  {
    return predicate != null;
  }

  public Expression getPredicate()
  {
    return predicate;
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private Projection projection;
  private Expression predicate;

}