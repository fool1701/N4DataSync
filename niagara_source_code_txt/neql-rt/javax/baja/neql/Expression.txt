/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import javax.baja.sys.BBoolean;
import javax.baja.sys.Context;
import javax.baja.tag.Entity;
import java.util.Collection;
import java.util.Collections;

/**
 * An Expression is an AstNode that can be evaluated to a value.
 *
 * @author John Sublett
 * @creation 01/16/2014
 * @since Niagara 4.0
 */
public abstract class Expression
  extends AstNode
{
  /**
   * @param  entity     The entity to evaluate the expression against
   * @param  context    The context to use when evaluating this expression for the given entity.
   * @return A collection of BIDataValues, Entities, Relations, or an empty list, depending on the query.
   * @since  Niagara 4.4
   */
  public abstract Collection<?> evaluate(Entity entity, Context context);

  /**
   * @since Niagara 4.4
   */
  public static final Collection<BBoolean> SINGLE_FALSE = Collections.singletonList(BBoolean.FALSE);

  /**
   *@since Niagara 4.4
   */
  public static final Collection<BBoolean> SINGLE_TRUE = Collections.singletonList(BBoolean.TRUE);

  /**
   * Returns true or false based on the specified entity and context.
   *
   * @param entity entity on which to evaluate the expression
   * @param context context to use while evaluating the expression
   * @return false by default
   * @since Niagara 4.9
   */
  protected boolean evalBoolean(Entity entity, Context context)
  {
    return false;
  }
}
