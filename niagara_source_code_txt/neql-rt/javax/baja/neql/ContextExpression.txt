/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import javax.baja.data.BIDataValue;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.tag.Entity;
import java.util.Collection;
import java.util.Collections;

/**
 * A ContextExpression is an expression that is evaluated against the
 * query context.  Typically the query context is the base object from
 * the ord that the query is part of.
 *
 * @author John Sublett
 * @creation 01/16/2014
 * @since Niagara 4.0
 */
public class ContextExpression
  extends Expression
{
  public ContextExpression(Expression expr)
  {
    this.expr = expr;
    key = (expr instanceof GetTagExpression) ? ((GetTagExpression) expr).getKey() : null;
  }

  /**
   * Get the node type.
   *
   * @return AST_CONTEXT_EXPR
   */
  @Override
  public int getNodeType()
  {
    return AST_CONTEXT_EXPR;
  }

  /**
   * Get the expression to evaluate against the context.
   */
  public Expression getExpression()
  {
    return expr;
  }

  @Override
  public Collection<BObject> evaluate(Entity entity, Context context)
  {
    if(context == null)
      return Collections.singletonList(BBoolean.FALSE);

    String key = ((GetTagExpression) expr).getKey();
    BObject contextValue = context.getFacets().getFacet(key);
    if(contextValue != null)
      return Collections.singletonList(contextValue);

    return Collections.emptyList();
  }

  /**
   * Returns true if the context contains a facet with the expression's key and its value is not
   * BBoolean.FALSE.
   *
   * @param entity not used
   * @param context context checked for a facet with the expression's key
   * @return true if the context contains a facet with the expression's key and its value is not
   *   BBoolean.FALSE.
   * @since Niagara 4.9
   */
  @Override
  protected boolean evalBoolean(Entity entity, Context context)
  {
    BIDataValue value = getValue(context);
    // NCCB-46488 would modify this definition of falsy
    return (value != null) && (value != BBoolean.FALSE);
  }

  /**
   * If the context is not null and it contains a facet with the expression's key, return that
   * facet's value.
   *
   * @param context context checked for a facet with the expression's key
   * @return null if the context is null or does not contain a facet with the expression's key;
   *   otherwise, return the value of that facet
   * @since Niagara 4.9
   */
  BIDataValue getValue(Context context)
  {
    if (context == null)
    {
      return null;
    }

    BObject value = context.getFacets().getFacet(key);
    return (value instanceof BIDataValue) ? (BIDataValue) value : null;
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private final Expression expr;
  private final String key;
}