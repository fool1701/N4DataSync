/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import javax.baja.data.BIDataValue;
import javax.baja.sys.BBoolean;

import com.tridium.neql.NeqlUtil;

/**
 * GreaterExpression is a boolean expression with two operands.  The expression must evaluate
 * to true if the left operand is greater than the right operand.
 *
 * @author John Sublett
 * @creation 01/26/2014
 * @since Niagara 4.0
 */
public class GreaterExpression
  extends ComparisonExpression
{
  public GreaterExpression(Expression left, Expression right)
  {
    super(left, right);
  }

  @Override
  public int getNodeType()
  {
    return AST_GREATER;
  }

  @Override
  public final String getSqlOperatorString()
  {
    return AstNode.OPERATOR_STR_GREATER;
  }

  @Override
  protected BBoolean getComparisonResult(Integer comparison)
  {
    return BBoolean.make(comparison > 0);
  }

  /**
   * Returns true if the single value from the left side is greater than the single value from right
   * side of the expression.
   *
   * @param leftValue single value from the left side expression
   * @param rightValue single value from the right side expression
   * @return true if the single value from the left side is greater than the single value from right
   *   side of the expression
   * @since Niagara 4.9
   */
  @Override
  protected boolean isComparisonValid(BIDataValue leftValue, BIDataValue rightValue)
  {
    Integer result = NeqlUtil.compareValues(leftValue, rightValue);
    return (result != null) && (result > 0);
  }
}
