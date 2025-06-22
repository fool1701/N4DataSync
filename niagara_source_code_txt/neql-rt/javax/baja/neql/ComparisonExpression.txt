/*
 * Copyright (c) 2014 Tridium, Inc.  All Rights Reserved.
 */
package javax.baja.neql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.baja.data.BIDataValue;
import javax.baja.sys.BBoolean;
import javax.baja.sys.Context;
import javax.baja.tag.Entity;

import com.tridium.neql.EvalOnIterator;
import com.tridium.neql.NeqlUtil;

/**
 * ComparisonExpression is a class of expressions that compare two quantities for
 * equality, greater than, or less than.
 *
 * @author <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *         Date: 3/19/14
 *         Time: 9:30 PM
 */
public abstract class ComparisonExpression
  extends BinaryExpression
{
  protected ComparisonExpression(Expression left, Expression right)
  {
    super(left, right);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Collection<BBoolean> evaluate(Entity entity, Context context)
  {
    Collection<BIDataValue> rightSide = (Collection<BIDataValue>)getRight().evaluate(entity, context);
    int rightSideCount = rightSide.size();
    if (rightSide.isEmpty())
    {
      return SINGLE_FALSE;
    }

    Collection<BIDataValue> leftSide = (Collection<BIDataValue>)getLeft().evaluate(entity, context);
    int leftSideCount = leftSide.size();
    if (leftSide.isEmpty())
    {
      return SINGLE_FALSE;
    }

    if (rightSideCount > 1 && leftSideCount > 1)
    {
      return SINGLE_TRUE;
    }

    if (rightSideCount == 1)
    {
      BIDataValue rightValue = rightSide.iterator().next();
      Collection<BBoolean> evaluations = new LinkedList<>();
      for (BIDataValue leftValue : leftSide)
      {
        BBoolean result = compareValues(leftValue, rightValue);
        if (result != null)
        {
          evaluations.add(result);
        }
      }

      return evaluations;
    }

    // a condition isn't necessary. at this point, there must be only 1 value in the left side
    BIDataValue leftValue = leftSide.iterator().next();
    Collection<BBoolean> evaluations = new LinkedList<>();
    for (BIDataValue rightValue : rightSide)
    {
      BBoolean result = compareValues(leftValue, rightValue);
      if (result != null)
      {
        evaluations.add(result);
      }
    }

    return evaluations;
  }

  protected BBoolean compareValues(BIDataValue left, BIDataValue right)
  {
    Integer result = NeqlUtil.compareValues(left, right);
    if (result != null)
    {
      return getComparisonResult(result);
    }

    return null;
  }

  /**
   * Get the BBoolean value for this comparison operation.
   *
   * @param  comparison  An integer comparison value that is returned by a compareTo method
   * @return BBoolean A baja boolean value that indicates if this comparison is true or false
   * @since  Niagara 4.4
   */
  protected abstract BBoolean getComparisonResult(Integer comparison);

  /**
   * Returns true if this expression's isComparisonValid method returns true for any of pair of
   * values coming from the left and right expressions. The left and/or right expressions may
   * evaluate to a single value or multiple values.
   *
   * @param entity entity on which to evaluate the expression
   * @param context context to use while evaluating the expression
   * @return true if this expression's isComparisonValid method returns true for any of pair of
   *   values coming from the left and right expressions
   * @since Niagara 4.9
   */
  @Override
  protected final boolean evalBoolean(Entity entity, Context context)
  {
    Expression left = getLeft();
    Expression right = getRight();

    boolean isLeftEvalOn = left.getNodeType() == AST_EVAL_ON;
    boolean isRightEvalOn = right.getNodeType() == AST_EVAL_ON;

    if (isLeftEvalOn && isRightEvalOn)
    {
      return areAnyComparisonsValid((EvalOnExpression) left, (EvalOnExpression) right, entity, context);
    }

    if (isLeftEvalOn)
    {
      BIDataValue rightValue = getSingleValue(right, entity, context);
      return (rightValue != null) && areAnyLeftValuesValid((EvalOnExpression) left, rightValue, entity, context);
    }

    if (isRightEvalOn)
    {
      BIDataValue leftValue = getSingleValue(left, entity, context);
      return (leftValue != null) && areAnyRightValuesValid(leftValue, (EvalOnExpression) right, entity, context);
    }

    // Both sides are single values
    BIDataValue leftValue = getSingleValue(left, entity, context);
    if (leftValue == null)
    {
      return false;
    }

    BIDataValue rightValue = getSingleValue(right, entity, context);
    if (rightValue == null)
    {
      return false;
    }

    return isComparisonValid(leftValue, rightValue);
  }

  /**
   * @param left EvalOnExpression on the left
   * @param right EvalOnExpression on the right
   * @param entity entity on which to evaluate the expression
   * @param context context to use while evaluating the left and right expressions
   * @return true if isComparisonValid returns true for any pair of values from the multiple values
   *   on the left and right side of this ComparisonExpression
   * @since Niagara 4.9
   */
  private boolean areAnyComparisonsValid(EvalOnExpression left, EvalOnExpression right, Entity entity, Context context)
  {
    // Stash away the right values to compare with subsequent left values, if necessary, and avoid
    // iterating endpoints more than once.
    List<BIDataValue> rightValues = null;

    EvalOnIterator leftIterator = EvalOnIterator.make(left, entity, context);
    while (leftIterator.hasNext())
    {
      BIDataValue leftValue = BinaryExpression.getSingleValue(left.getExpression(), leftIterator.next(), context);
      if (leftValue != null)
      {
        if (rightValues == null)
        {
          // Create an empty list so if there are no non-null values on the right, we will not
          // iterate the endpoints again.
          rightValues = new ArrayList<>();

          EvalOnIterator rightIterator = EvalOnIterator.make(right, entity, context);
          while (rightIterator.hasNext())
          {
            BIDataValue rightValue = BinaryExpression.getSingleValue(right.getExpression(), rightIterator.next(), context);
            if (rightValue != null)
            {
              if (isComparisonValid(leftValue, rightValue))
              {
                return true;
              }

              rightValues.add(rightValue);
            }
          }
        }
        else
        {
          for (BIDataValue rightValue : rightValues)
          {
            // Only non-null right values are added to rightValues so no need to do a null check
            // here.
            if (isComparisonValid(leftValue, rightValue))
            {
              return true;
            }
          }
        }
      }
    }

    return false;
  }

  /**
   * @param left EvalOnExpression on the left
   * @param rightValue single value from the right side expression
   * @param entity entity on which to evaluate the left expression
   * @param context context to use while evaluating the left expression
   * @return true if isComparisonValid returns true for any of the multiple values on the left side
   *   and the single value on the right side
   * @since Niagara 4.9
   */
  private boolean areAnyLeftValuesValid(EvalOnExpression left, BIDataValue rightValue, Entity entity, Context context)
  {
    EvalOnIterator iterator = EvalOnIterator.make(left, entity, context);
    while (iterator.hasNext())
    {
      BIDataValue leftValue = BinaryExpression.getSingleValue(left.getExpression(), iterator.next(), context);
      if ((leftValue != null) && isComparisonValid(leftValue, rightValue))
      {
        return true;
      }
    }

    return false;
  }

  /**
   * @param leftValue single value from the left side expression
   * @param right EvalOnExpression on the right
   * @param entity entity on which to evaluate the right expression
   * @param context context to use while evaluating the right expression
   * @return true if isComparisonValid returns true for the single value on the left side and any
   *   of the multiple values on the right side
   * @since Niagara 4.9
   */
  private boolean areAnyRightValuesValid(BIDataValue leftValue, EvalOnExpression right, Entity entity, Context context)
  {
    EvalOnIterator iterator = EvalOnIterator.make(right, entity, context);
    while (iterator.hasNext())
    {
      BIDataValue rightValue = BinaryExpression.getSingleValue(right.getExpression(), iterator.next(), context);
      if ((rightValue != null) && isComparisonValid(leftValue, rightValue))
      {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns true if the ComparisonExpression is valid based on single values from the left and
   * right sides of the expression.
   *
   * @param leftValue single value from the left side expression
   * @param rightValue single value from the right side expression
   * @return true if the ComparisonExpression is valid based on single values from the left and
   *   right sides of the expression.
   * @since Niagara 4.9
   */
  protected abstract boolean isComparisonValid(BIDataValue leftValue, BIDataValue rightValue);
}
