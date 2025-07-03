/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;

/**
 * BScopedTagRule is a {@code BTagRule} with a list of scopes in which
 * the rule applies.
 *
 * @author Scott Newton
 * @creation 21 Nov 16
 * @since Niagara 4.3
 */

@NiagaraType
/*
 The scope in which this rule applies
 */
@NiagaraProperty(
  name = "scopeList",
  type = "BTagRuleScopeList",
  defaultValue = "new BTagRuleScopeList()"
)

public class BScopedTagRule extends BTagRule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BScopedTagRule(10251198)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "scopeList"

  /**
   * Slot for the {@code scopeList} property.
   * The scope in which this rule applies
   * @see #getScopeList
   * @see #setScopeList
   */
  public static final Property scopeList = newProperty(0, new BTagRuleScopeList(), null);

  /**
   * Get the {@code scopeList} property.
   * The scope in which this rule applies
   * @see #scopeList
   */
  public BTagRuleScopeList getScopeList() { return (BTagRuleScopeList)get(scopeList); }

  /**
   * Set the {@code scopeList} property.
   * The scope in which this rule applies
   * @see #scopeList
   */
  public void setScopeList(BTagRuleScopeList v) { set(scopeList, v, null); }

  //endregion Property "scopeList"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BScopedTagRule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Default constructor that sets all properties to their default values.
   */
  public BScopedTagRule()
  {
  }

  /**
   * Initializes the {@link #condition} property.
   *
   * @param cond initial value of the {@link #condition} property
   */
  public BScopedTagRule(BTagRuleCondition cond)
  {
    setCondition(cond);
  }

  /**
   * Initializes the {@link #condition} and {@link #scopeList} properties.
   *
   * @param cond initial value of the {@link #condition} property
   * @param scopeList initial value of the {@link #scopeList} property
   */
  public BScopedTagRule(BTagRuleCondition cond, BTagRuleScopeList scopeList)
  {
    setCondition(cond);
    setScopeList(scopeList);
  }

  /**
   * Determine if this rule applies to the supplied entity.
   *
   * @param entity the entity to evaluate this rule against
   * @return {@code true} if the rule applies to the supplied entity; {@code false} otherwise.
   * @since Niagara 4.3
   */
  @Override
  public boolean evaluate(Entity entity)
  {
    return getScopeList().isInScope(entity) && getCondition().test(entity);
  }
}
