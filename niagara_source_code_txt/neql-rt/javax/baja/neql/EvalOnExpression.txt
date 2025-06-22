/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import javax.baja.sys.Context;
import javax.baja.tag.Entity;
import java.util.Collection;
import java.util.LinkedList;

import com.tridium.neql.EvalOnIterator;

/**
 * AndExpression is a boolean expression with two operands.  The expression must evaluate
 * to true if both operands are expressions that evaluate to true.  If the left
 * operand evaluates to false, the right operand must not be evaluated.
 *
 * @author John Sublett
 * @creation 01/26/2014
 * @since Niagara 4.0
 */
public class EvalOnExpression
  extends Expression
{
  /**
   * Create a new EvalOnExpression.
   *
   * @param expr The expression to evaluate.
   * @param target An expression that results in an Entity to evaluate the expression on.
   */
  public EvalOnExpression(Expression expr, Expression target)
  {
    this.expr = expr;
    this.target = target;
  }

  @Override
  public int getNodeType()
  {
    return AST_EVAL_ON;
  }

  public Expression getExpression()
  {
    return expr;
  }

  public Expression getTarget()
  {
    return target;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Collection<?> evaluate(Entity entity, Context context)
  {
    Collection<Entity> targets = (Collection<Entity>) target.evaluate(entity, context);
    Collection<Object> evaluations = new LinkedList<>();
    for (Entity target : targets)
    {
      evaluations.addAll(expr.evaluate(target, context));
    }

    return evaluations;
  }

  /**
   * Returns true if evalBoolean on the inner expression return true for any related endpoints of
   * the entity.
   *
   * @param entity root entity from which related endpoints are found
   * @param context context to use while finding related endpoints and evaluating the inner
   *   expression
   * @return true if evalBoolean on the inner expression return true for any related endpoints
   * @since Niagara 4.9
   */
  @Override
  protected boolean evalBoolean(Entity entity, Context context)
  {
    EvalOnIterator iterator = EvalOnIterator.make(this, entity, context);
    while (iterator.hasNext())
    {
      if (expr.evalBoolean(iterator.next(), context))
      {
        return true;
      }
    }

    return false;
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private final Expression expr;
  private final Expression target;
}
