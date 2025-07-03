/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.baja.data.BIDataValue;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.tag.Entity;

import com.tridium.neql.EvalOnIterator;

/**
 * A LikeExpression is an expression that is evaluated by returning
 * boolean based on pattern caching.  It must evaluate to
 * true if the contained expression is false, and evaluate to
 * false if the contained expression is true.  If the contained
 * expression does not evaluate to a boolean, this expression
 * returns true.
 *
 * @author Andy Saunders
 * @creation 12/4/2015
 * @since Niagara 4.2
 */
public class LikeExpression
  extends BinaryExpression
{
  public LikeExpression(Expression left, Expression right)
  {
    super(left, right);
  }

  /**
   * Get the node type.
   *
   * @return AST_LIKE
   */
  @Override
  public int getNodeType()
  {
    return AST_LIKE;
  }
  
  @Override
  public final String getSqlOperatorString()
  {
    return AstNode.OPERATOR_STR_LIKE;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Collection<BBoolean> evaluate(Entity entity, Context context)
  {
    Collection<BIDataValue> leftValues = (Collection<BIDataValue>) getLeft().evaluate(entity, context);
    if (leftValues.isEmpty())
    {
      return SINGLE_FALSE;
    }

    Collection<BIDataValue> rightValues = (Collection<BIDataValue>) getRight().evaluate(entity, context);
    if (rightValues.isEmpty())
    {
      return SINGLE_FALSE;
    }

    BIDataValue rightValue = rightValues.iterator().next();
    if (rightValue.getType() != BString.TYPE)
    {
      return SINGLE_FALSE;
    }

    String regex = ((BString)rightValue).getString();
    try
    {
      Pattern pattern = Pattern.compile(regex);
      for (BIDataValue leftValue : leftValues)
      {
        Matcher matcher = pattern.matcher(leftValue.encodeToString());
        if (matcher.matches())
        {
          return SINGLE_TRUE;
        }
      }
    }
    catch (Exception ignore)
    {
    }

    return SINGLE_FALSE;
  }

  /**
   * Returns true if this expression's regex matches the single value or any of the values coming
   * from the left expression.
   *
   * @param entity entity on which to evaluate the left expression
   * @param context context to use while evaluating the left expression
   * @return true if this expression's regex matches the single value or any of the values coming
   *   from the left expression
   * @since Niagara 4.9
   */
  @Override
  protected final boolean evalBoolean(Entity entity, Context context)
  {
    Pattern pattern = getPattern();
    if (pattern == null)
    {
      return false;
    }

    Expression left = getLeft();
    if (left.getNodeType() == AST_EVAL_ON)
    {
      EvalOnExpression leftEvalOn = (EvalOnExpression) left;
      EvalOnIterator iterator = EvalOnIterator.make(leftEvalOn, entity, context);
      while (iterator.hasNext())
      {
        BIDataValue leftValue = getSingleValue(leftEvalOn.getExpression(), iterator.next(), context);
        try
        {
          if ((leftValue != null) && pattern.matcher(leftValue.encodeToString()).matches())
          {
            return true;
          }
        }
        catch (IOException e)
        {
          // Skip this endpoint entity
        }
      }

      return false;
    }
    else
    {
      // Left side is a single value
      BIDataValue leftValue = getSingleValue(left, entity, context);
      if (leftValue == null)
      {
        return false;
      }

      try
      {
        return pattern.matcher(leftValue.encodeToString()).matches();
      }
      catch (IOException e)
      {
        return false;
      }
    }
  }

  /**
   * If a pattern is not cached, compile the literal String on the right side and cache that.
   * @return the cached, compiled pattern
   * @since Niagara 4.9
   */
  private Pattern getPattern()
  {
    if (cachedPattern != null)
    {
      return cachedPattern;
    }

    Expression right = getRight();
    if (right instanceof LiteralExpression)
    {
      BIDataValue rightValue = ((LiteralExpression) right).getValue();
      if (rightValue.getType() == BString.TYPE)
      {
        cachedPattern = Pattern.compile(((BString) rightValue).getString());
      }
    }

    return cachedPattern;
  }

  private Pattern cachedPattern = null;
}
