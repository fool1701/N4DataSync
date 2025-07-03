/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import javax.baja.data.BIDataValue;
import javax.baja.sys.BBoolean;
import javax.baja.sys.Context;
import javax.baja.tag.Entity;
import java.util.Collection;

/**
 * A NotExpression is an expression that is evaluated by returning
 * the logical not of the subexpression.  It must evaluate to
 * true if the contained expression is false, and evaluate to
 * false if the contained expression is true.  If the contained
 * expression does not evaluate to a boolean, this expression
 * returns true.
 *
 * @author John Sublett
 * @creation 01/16/2014
 * @since Niagara 4.0
 */
public class NotExpression
  extends Expression
{
  public NotExpression(Expression expr)
  {
    this.expr = expr;
  }

  /**
   * Get the node type.
   *
   * @return AST_GET_TAG
   */
  @Override
  public int getNodeType()
  {
    return AST_NOT;
  }

  /**
   * Get the expression to evaluate against the context.
   */
  public Expression getExpression()
  {
    return expr;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Collection<BBoolean> evaluate(Entity entity, Context context)
  {
    Collection<?> evaluations = expr.evaluate(entity, context);

    for(Object value : evaluations)
    {
      if(value instanceof BBoolean)
      {
        BBoolean current = (BBoolean) value;
        if(current.getBoolean())
        {
          return SINGLE_FALSE;
        }
      }
      else
      {
        return SINGLE_FALSE;
      }
    }

    return SINGLE_TRUE;
  }

  /**
   * Returns true if evalBoolean for the inner expression returns false.
   *
   * @param entity entity on which to evaluate the expression
   * @param context context to use while evaluating the expression
   * @return true if evalBoolean for the inner expression returns false
   * @since Niagara 4.9
   */
  @Override
  protected boolean evalBoolean(Entity entity, Context context)
  {
    return !expr.evalBoolean(entity, context);
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private final Expression expr;
}