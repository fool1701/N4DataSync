/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import static com.tridium.tagdictionary.util.TagDictionaryUtil.handleIllegalChild;
import static com.tridium.tagdictionary.util.TagDictionaryUtil.handleIllegalParent;

import java.util.function.Predicate;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.util.BIRestrictedComponent;

import com.tridium.tagdictionary.condition.BAnd;
import com.tridium.tagdictionary.condition.BOr;

/**
 * BTagRuleCondition is a condition that must be met for a {@link TagRule} to be activated. This is
 * a {@code BComponent} implementation of {@code Predicate<Entity>}. It also include
 * {@link #testIdealMatch(Type)} to test against a {@link Type}.
 *
 * @author John Sublett
 * @creation 2/18/14
 * @since Niagara 4.0
 */
@NiagaraType
public abstract class BTagRuleCondition extends BComponent
  implements Predicate<Entity>, BIRestrictedComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BTagRuleCondition(2979906276)1.0$ @*/
/* Generated Tue Jan 25 17:26:55 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTagRuleCondition.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Test the entity against the condition.
   *
   * @param entity entity to evaluate
   * @return {@code true} if the condition is met; {@code false} otherwise.
   */
  @Override
  public abstract boolean test(Entity entity);

  /**
   * Test the type against the condition.
   *
   * @param type type to evaluate
   * @return {@code true} if the condition is met; {@code false} otherwise.
   */
  public abstract boolean testIdealMatch(Type type);

  /**
   * Most subclasses of BTagRuleCondition do not use and will not accept child BTagRuleConditions.
   * BTagRuleConditions, such as {@link BAnd} and {@link BOr}, that do accept other child
   * BTagRuleConditions should override this method.
   * @since Niagara 4.4
   */
  @Override
  public void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
    // Do not allow any children to be added to most BTagRuleConditions.
    handleIllegalChild(this, value, context);
  }

///////////////////////////////////////////////////////////
// BIRestrictedComponent
///////////////////////////////////////////////////////////

  /**
   * BTagRuleConditions may only be added to other BTagRuleConditions.  They are frozen properties
   * on {@link BTagRule}s but they should not be dropped onto other types.
   * @since Niagara 4.4
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context context)
  {
    // Do nothing for the frozen tag rule conditions.
    if (getPropertyInParent().isFrozen())
      return;

    if (!(parent instanceof BTagRuleCondition))
      handleIllegalParent(parent, this, context);
  }
}
