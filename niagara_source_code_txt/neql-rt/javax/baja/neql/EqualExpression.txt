/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import javax.baja.data.BIDataValue;
import javax.baja.sys.BBoolean;

import com.tridium.neql.NeqlUtil;

/**
 * EqualExpression is a boolean expression with two operands.  The expression must evaluate
 * to true if the operands are equal to each other.
 *
 * @author John Sublett
 * @creation 01/26/2014
 * @since Niagara 4.0
 */
public class EqualExpression
  extends ComparisonExpression
{
  public EqualExpression(Expression left, Expression right)
  {
    super(left, right);
  }

  @Override
  public int getNodeType()
  {
    return AST_EQUAL;
  }

  @Override
  public final String getSqlOperatorString()
  {
    return AstNode.OPERATOR_STR_EQUAL;
  }

  @Override
  protected BBoolean compareValues(BIDataValue left, BIDataValue right)
  {
    return BBoolean.make(NeqlUtil.equals(left, right));
  }

  @Override
  public BBoolean getComparisonResult(Integer compare)
  {
    return BBoolean.make(compare == 0);
  }

  /**
   * Returns true if the single value from the left side is equal to the single value from right
   * side of the expression.
   *
   * @param leftValue single value from the left side expression
   * @param rightValue single value from the right side expression
   * @return true if the single value from the left side is equal to the single value from right
   *   side of the expression
   * @since Niagara 4.9
   */
  @Override
  protected boolean isComparisonValid(BIDataValue leftValue, BIDataValue rightValue)
  {
    return NeqlUtil.equals(leftValue, rightValue);
  }
}
