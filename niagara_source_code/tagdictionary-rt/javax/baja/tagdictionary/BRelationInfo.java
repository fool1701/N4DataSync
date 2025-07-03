/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import static com.tridium.tagdictionary.util.TagDictionaryUtil.generateId;
import static com.tridium.tagdictionary.util.TagDictionaryUtil.handleIllegalParent;

import java.util.Optional;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Id;
import javax.baja.tag.RelationInfo;
import javax.baja.tag.TagDictionary;
import javax.baja.util.BIRestrictedComponent;

/**
 * BRelationInfo is a BComponent implementation of RelationInfo.
 *
 * @author John Sublett
 * @creation 2/28/14
 * @since Niagara 4.0
 */
@NiagaraType
public class BRelationInfo extends BComponent implements RelationInfo, BIRestrictedComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BRelationInfo(2979906276)1.0$ @*/
/* Generated Tue Jan 25 17:26:55 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRelationInfo.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// RelationInfo
////////////////////////////////////////////////////////////////

  /**
   * Get the dictionary that this relation is defined in, if one exists.
   *
   * @return an {@code Optional} that contains the {@code TagDictionary}
   * for this relation if the relation is part of a {@code TagDictionary};
   * otherwise, an empty {@code Optional}
   */
  @Override
  public Optional<TagDictionary> getDictionary()
  {
    return BTagDictionary.getParentDictionary(this);
  }

  /**
   * Get the id for this relation.  The id is constructed from the
   * dictionary namespace and this component's name.
   *
   * @return an id for this relation
  */
  @Override
  public Id getRelationId()
  {
    // id is cached for future calls
    if (isRunning() && relationId != null)
      return relationId;

    relationId = generateId(this, this::getDictionary);
    return relationId;
  }

  /**
   * Regenerates the {@link Id} of this relation.
   *
   * @since Niagara 4.3
   */
  public void relationRenamed()
  {
    relationId = null;
    relationId = getRelationId();
  }

///////////////////////////////////////////////////////////
// BIRestrictedComponent
///////////////////////////////////////////////////////////

  /**
   * BRelationInfos may only be added to a {@link BRelationInfoList} located at a tag dictionary's
   * {@link BTagDictionary#relationDefinitions} property and a tag rule's
   * {@link BTagRule#relationList} property.
   * @since Niagara 4.4
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context context)
  {
    if (!(parent instanceof BRelationInfoList))
      handleIllegalParent(parent, this, context);
  }

///////////////////////////////////////////////////////////
// Icon
///////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("link.png");

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private Id relationId;
}
