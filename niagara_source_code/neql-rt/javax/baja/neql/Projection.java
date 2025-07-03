/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

/**
 * A Projection is a node that narrows the tags on the resulting entities.
 *
 * @author John Sublett
 * @creation 01/27/2014
 * @since Niagara 4.0
 */
public class Projection
  extends AstNode
{
  public Projection(Expression[] elements)
  {
    this.elements = elements;
  }

  @Override
  public int getNodeType()
  {
    return AstNode.AST_PROJECTION;
  }

  public int getElementCount()
  {
    return elements.length;
  }

  public Expression getElement(int index)
  {
    return elements[index];
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private Expression[] elements;
}
