/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import javax.baja.data.BIDataValue;
import javax.baja.sys.Context;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.tag.Entity;

/**
 * An Expression is a class of expressions that operates upon two expressions.
 *
 * @author John Sublett
 * @creation 01/16/2014
 * @since Niagara 4.0
 */
public abstract class BinaryExpression
  extends Expression
{
  protected BinaryExpression(Expression left, Expression right)
  {
    this.left = left;
    this.right = right;
  }

  public abstract String getSqlOperatorString();

  public final Expression getLeft()
  {
    return left;
  }

  public final Expression getRight()
  {
    return right;
  }

  /**
   * Return the single value to which a GetTagExpression, LiteralExpression, or ContextExpression
   * evaluates. All other expression types throw an UnsupportedOperationException.
   *
   * @param expression expression that evaluates to a single value
   * @param entity entity on which to evaluate the expression
   * @param context context to use while evaluating the expression
   * @return the single value to which the expression evaluates
   * @throws UnsupportedOperationException if not a GetTagExpression, LiteralExpression, or
   *   ContextExpression
   * @since Niagara 4.9
   */
  static BIDataValue getSingleValue(Expression expression, Entity entity, Context context)
  {
    switch (expression.getNodeType())
    {
      case AST_GET_TAG:
        return ((GetTagExpression) expression).getValue(entity, context);
      case AST_LITERAL:
        return ((LiteralExpression) expression).getValue();
      case AST_CONTEXT_EXPR:
        return ((ContextExpression) expression).getValue(context);

      case AST_AND:
      case AST_OR:
      case AST_NOT:
        throw new LocalizableRuntimeException("neql", "binaryExpression.getSingleValue.invalidLogicalExpression", new Object[] {expression.getClass().getSimpleName()});

      case AST_EQUAL:
      case AST_NOT_EQUAL:
      case AST_LESS:
      case AST_LESS_OR_EQUAL:
      case AST_GREATER:
      case AST_GREATER_OR_EQUAL:
      case AST_LIKE:
        throw new LocalizableRuntimeException("neql", "binaryExpression.getSingleValue.invalidComparisonExpression", new Object[] {expression.getClass().getSimpleName()});

      case AST_GET_RELATION:
      case AST_EVAL_ON:
      case AST_TRAVERSE_OUT:
      case AST_TRAVERSE_IN:
      default:
        throw new LocalizableRuntimeException("neql", "binaryExpression.getSingleValue.invalidExpression", new Object[] {expression.getClass().getSimpleName()});
    }
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private final Expression left;
  private final Expression right;
}
