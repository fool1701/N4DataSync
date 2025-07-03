/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import static com.tridium.neql.NeqlUtil.hasPermission;

import java.util.ArrayList;
import java.util.Collection;
import javax.baja.sys.Context;
import javax.baja.tag.Entity;
import javax.baja.tag.Relation;

/**
 * A TraverseInExpression traverses an inbound relation on an Entity
 * and returns the source entity.
 *
 * @author Andrew Saunders
 * @creation 12/30/2015
 * @since Niagara 4.2
 */
public class TraverseInExpression extends Expression
{
  public TraverseInExpression(Expression expr)
  {
    this.expr = expr;
  }

  /**
   * Get the node type.
   *
   * @return AST_TRAVERSE_IN
   */
  @Override
  public int getNodeType()
  {
    return AST_TRAVERSE_IN;
  }

  /**
   * Get the expression the subexpression that must evaluate
   * to an Entity.
   */
  public Expression getExpression()
  {
    return expr;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Collection<Entity> evaluate(Entity entity, Context context)
  {
    Collection<Relation> relations = (Collection<Relation>) expr.evaluate(entity, context);

    Collection<Entity> entities = new ArrayList<>(relations.size());
    for (Relation r : relations)
    {
      if (r.isInbound())
      {
        Entity endpoint = r.getEndpoint();
        if (hasPermission(endpoint, context))
        {
          entities.add(endpoint);
        }
      }
    }
    return entities;
  }

  /**
   * Returns true if the entity contains an inbound relation with this expression's relation id.
   *
   * @param entity entity on which to search for an inbound relation with this expression's relation
   *   id
   * @param context context that may contain the default namespace to form the relation id
   * @return true if the entity contains an inbound relation with this expression's relation id
   * @since Niagara 4.9
   */
  @Override
  protected boolean evalBoolean(Entity entity, Context context)
  {
    return expr.evalBoolean(entity, context);
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private final Expression expr;
}
