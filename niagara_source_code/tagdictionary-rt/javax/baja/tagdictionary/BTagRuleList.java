/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import static com.tridium.tagdictionary.util.TagDictionaryUtil.handleIllegalChild;

import java.util.Iterator;

import javax.baja.collection.SlotCursorIterator;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A TagRuleList is a collection of TagRules associated with a SmartTagDictionary.
 *
 * @author John Sublett
 * @creation 2/18/14
 * @since Niagara 4.0
 */
@NiagaraType
public class BTagRuleList
  extends BInfoList
  implements Iterable<TagRule>
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BTagRuleList(2979906276)1.0$ @*/
/* Generated Tue Jan 25 17:26:55 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTagRuleList.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Returns an iterator over the tag rules in this list.
   *
   * @return iterator of tag rules
   */
  @Override
  public Iterator<TagRule> iterator()
  {
    return SlotCursorIterator.iterator(getProperties(), TagRule.class);
  }

  /**
   * Only components that implement {@link TagRule} may be added to a BTagRuleList.
   * @since Niagara 4.4
   */
  @Override
  public void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
    // Only allow RelationInfos to be added to a BRelationInfoList
    if (!(value instanceof TagRule))
      handleIllegalChild(this, value, context);

    super.checkAdd(name, value, flags, facets, context);
  }
}
