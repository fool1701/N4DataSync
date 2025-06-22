/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import com.tridium.neql.NeqlUtil;

import javax.baja.sys.BBoolean;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.tag.Entity;
import java.util.Collection;

/**
 * OrExpression is a boolean expression with two operands.  The expression must evaluate
 * to true if any operands are expressions that evaluate to true.  If the left
 * operand evaluates to true, the right operand must not be evaluated.
 *
 * @author John Sublett
 * @creation 01/26/2014
 * @since Niagara 4.0
 */
public class OrExpression
  extends LogicalExpression
{
  public OrExpression(Expression left, Expression right)
  {
    super(left, right);
  }

  @Override
  public int getNodeType()
  {
    return AST_OR;
  }

  @Override
  public final String getSqlOperatorString()
  {
    return AstNode.OPERATOR_STR_OR;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Collection<BBoolean> evaluate(Entity entity, Context context)
  {
    Collection<Object> leftVals = (Collection<Object>) getLeft().evaluate(entity, context);
    Collection<BBoolean> evals = NeqlUtil.searchForSingleLogicalBoolean(leftVals);

    if(evals.contains(BBoolean.TRUE))
    {
      return evals;
    }

    Collection<Object> rightVals = (Collection<Object>) getRight().evaluate(entity, context);
    return NeqlUtil.searchForSingleLogicalBoolean(rightVals);
  }

  /**
   * Returns true if evalBoolean for either the left or the right expressions returns true. If
   * evalBoolean for the left expression return true, the right expression will not be evaluated.
   *
   * @param entity entity on which to evaluate the expressions
   * @param context context to use while evaluating the expressions
   * @return true if evalBoolean for either the left or the right expressions returns true
   * @since Niagara 4.9
   */
  @Override
  protected boolean evalBoolean(Entity entity, Context context)
  {
    return getLeft().evalBoolean(entity, context) || getRight().evalBoolean(entity, context);
  }
}
