/**
 * Copyright (c) 2014 Tridium, Inc.  All Rights Reserved.
 */
package javax.baja.neql;

/**
 * LogicalExpression is a class of expressions that represents a logical connection between
 * two subexpressions, such as intersection (AND) or union (OR).
 *
 * @author <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *         Date: 3/19/14
 *         Time: 9:27 PM
 */
public abstract class LogicalExpression
  extends BinaryExpression
{
  protected LogicalExpression(Expression left, Expression right)
  {
    super(left, right);
  }
}
