/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import javax.baja.data.BIDataValue;
import javax.baja.sys.BBoolean;
import javax.baja.sys.Context;
import javax.baja.tag.Entity;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * LiteralExpression is an expression for a literal value of any of the supported
 *
 * @author John Sublett
 * @creation 01/26/2014
 * @since Niagara 4.0
 */
public class LiteralExpression
  extends Expression
{
  public LiteralExpression(BIDataValue value)
  {
    Objects.requireNonNull(value, "value must not be null");
    this.value = value;
  }

  @Override
  public int getNodeType()
  {
    return AST_LITERAL;
  }

  @Override
  public String getNodeDisplay()
  {
    return value.toString();
  }

  public BIDataValue getValue()
  {
    return value;
  }

  @Override
  public Collection<BIDataValue> evaluate(Entity entity, Context context)
  {
    if(evaluation == null)
    {
      evaluation = Collections.singletonList(getValue());
    }
    return evaluation;
  }

  /**
   * Returns true if the expression's value is not BBoolean.FALSE.
   *
   * @param entity not used
   * @param context not used
   * @return true if expression's value is not BBoolean.FALSE.
   * @since Niagara 4.9
   */
  @Override
  protected boolean evalBoolean(Entity entity, Context context)
  {
    // NCCB-46488 would modify this definition of falsy
    // null-check is already made in the constructor
    return value != BBoolean.FALSE;
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private final BIDataValue value;
  private Collection<BIDataValue> evaluation = null;
}
