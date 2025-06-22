/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import static com.tridium.tagdictionary.util.TagDictionaryUtil.handleIllegalParent;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.util.BIRestrictedComponent;

/**
 * A BTagRuleScope is a {@code BComponent} implementation of {@code TagRuleScope}.
 *
 * @author Scott Newton
 * @creation 20 Oct 16
 * @since Niagara 4.3
 */
@NiagaraType
public abstract class BTagRuleScope extends BComponent
  implements TagRuleScope, BIRestrictedComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BTagRuleScope(2979906276)1.0$ @*/
/* Generated Tue Jan 25 17:26:55 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTagRuleScope.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Determine if the supplied entity is in this scope.
   *
   * @param entity the entity to check against this scope
   * @return {@code false}
   */
  @Override
  public boolean includes(Entity entity)
  {
    return false;
  }

///////////////////////////////////////////////////////////
// BIRestrictedComponent
///////////////////////////////////////////////////////////

  /**
   * BTagRuleScopes may only be added to a {@link BTagRuleScopeList} located at a scoped tag rule's
   * {@link BScopedTagRule#scopeList} property.
   * @since Niagara 4.4
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context context)
  {
    if (!(parent instanceof BTagRuleScopeList))
      handleIllegalParent(parent, this, context);
  }
}
