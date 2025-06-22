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
 * AndExpression is a boolean expression with two operands.  The expression must evaluate
 * to true if both operands are expressions that evaluate to true.  If the left
 * operand evaluates to false, the right operand must not be evaluated.
 *
 * @author John Sublett
 * @creation 01/26/2014
 * @since Niagara 4.0
 */
public class AndExpression
  extends LogicalExpression
{
  public AndExpression(Expression left, Expression right)
  {
    super(left, right);
  }

  @Override
  public int getNodeType()
  {
    return AST_AND;
  }

  @Override
  public final String getSqlOperatorString()
  {
    return AstNode.OPERATOR_STR_AND;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Collection<BBoolean> evaluate(Entity entity, Context context)
  {
    Collection<Object> leftVals = (Collection<Object>) getLeft().evaluate(entity, context);
    Collection<BBoolean> leftSide = NeqlUtil.searchForSingleLogicalBoolean(leftVals);
    if(leftSide.contains(BBoolean.TRUE))
    {
      Collection<Object> rightVals = (Collection<Object>) getRight().evaluate(entity, context);
      return NeqlUtil.searchForSingleLogicalBoolean(rightVals);
    }

    return SINGLE_FALSE;
  }

  /**
   * Returns true if evalBoolean for the left and right expressions both return true. If
   * evalBoolean for the left expression return false, the right expression will not be evaluated.
   *
   * @param entity entity on which to evaluate the expressions
   * @param context context to use while evaluating the expressions
   * @return true if evalBoolean for the left and right expressions both return true
   * @since Niagara 4.9
   */
  @Override
  protected boolean evalBoolean(Entity entity, Context context)
  {
    return getLeft().evalBoolean(entity, context) && getRight().evalBoolean(entity, context);
  }
}
