/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import java.util.Iterator;

import javax.baja.collection.SlotCursorIterator;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;

/**
 * BTagRuleScopeList defines a scope in which a {@code BScopedTagRule} applies.
 * Its {@code TagRuleScope} children specify what is in scope and
 * what is out of scope.
 *
 * @author Scott Newton
 * @creation 20 Oct 16
 * @since Niagara 4.3
 */
@NiagaraType
public class BTagRuleScopeList
  extends BInfoList
  implements Iterable<TagRuleScope>
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BTagRuleScopeList(2979906276)1.0$ @*/
/* Generated Tue Jan 25 17:26:55 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTagRuleScopeList.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Allow only TagRuleScope children.
   */
  @Override
  public boolean isChildLegal(BComponent child)
  {
    return child instanceof TagRuleScope;
  }

  /**
   * Determine if a supplied entity is in this scope.
   *
   * @param entity the entity to test against this scope
   * @return {@code true} if the entity is in scope, otherwise {@code false}.
   */
  public boolean isInScope(Entity entity)
  {
    Iterator<TagRuleScope> it = iterator();
    while (it.hasNext())
    {
      TagRuleScope scope = it.next();
      if (scope.includes(entity))
      {
        return true;
      }
    }

    return false;
  }

  /**
   * Get an iterator of TagRuleScopes.
   *
   * @return An Iterator of TagRuleScope children.
   */
  public Iterator<TagRuleScope> iterator()
  {
    return SlotCursorIterator.iterator(getProperties(), TagRuleScope.class);
  }
}
