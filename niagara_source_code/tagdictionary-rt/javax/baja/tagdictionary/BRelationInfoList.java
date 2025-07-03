/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import static com.tridium.tagdictionary.util.TagDictionaryUtil.handleIllegalChild;

import java.util.Iterator;
import java.util.Optional;

import javax.baja.collection.SlotCursorIterator;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Id;
import javax.baja.tag.RelationInfo;
import javax.baja.tag.TagDictionary;

/**
 * BRelationInfoList is a list of {@code RelationInfo}s.
 *
 * @author John Sublett
 * @creation 2/28/14
 * @since Niagara 4.0
 */
@NiagaraType
public class BRelationInfoList
  extends BInfoList
  implements Iterable<RelationInfo>
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BRelationInfoList(2979906276)1.0$ @*/
/* Generated Tue Jan 25 17:26:55 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRelationInfoList.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Only components that implement {@link RelationInfo} may be added to a BRelationInfoList.
   * @since Niagara 4.4
   */
  @Override
  public void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
    // Only allow RelationInfos to be added to a BRelationInfoList
    if (!(value instanceof RelationInfo))
      handleIllegalChild(this, value, context);

    super.checkAdd(name, value, flags, facets, context);
  }

  /**
   * Get the dictionary that this list is defined in, if one exists.
   *
   * @return an {@code Optional} that contains the {@code TagDictionary}
   * for this list if the list is part of a {@code TagDictionary};
   * otherwise, an empty {@code Optional}
   */
  public Optional<TagDictionary> getDictionary()
  {
    return BTagDictionary.getParentDictionary(this);
  }

  /**
   * Test whether this list includes a relation with the specified id.
   *
   * @param id relation id to search for
   * @return {@code true} if the list contains a relation with the specified id;
   * {@code false} otherwise.
   */
  @SuppressWarnings("unused")
  public boolean containsRelationId(Id id)
  {
    return getRelation(id).isPresent();
  }

  /**
   * Gets the relation with the specified id.
   *
   * @param id id of the relation
   * @return an {@code Optional} that contains the {@code RelationInfo} with the
   * specified id, if contained in the list; otherwise, an empty
   * {@code Optional}
   */
  public Optional<RelationInfo> getRelation(Id id)
  {
    for (BRelationInfo relationInfo: getChildren(BRelationInfo.class))
    {
      if (relationInfo.getRelationId().equals(id))
      {
        return Optional.of(relationInfo);
      }
    }
    return Optional.empty();
  }

  /**
   * If the property is a {@link BRelationInfo}, call {@link BRelationInfo#relationRenamed()} on it.
   *
   * @param property property being renamed
   * @param oldName old name
   * @param context execution context
   */
  @Override
  public void renamed(Property property, String oldName, Context context)
  {
    BValue value = get(property);
    if (value instanceof BRelationInfo)
    {
      ((BRelationInfo)value).relationRenamed();
    }
  }

////////////////////////////////////////////////////////////////
// Iterable
////////////////////////////////////////////////////////////////

  /**
   * Get an iterator of the relations in this list.
   *
   * @return an iterator of {@code RelationInfo}s.
   */
  @Override
  public Iterator<RelationInfo> iterator()
  {
    return SlotCursorIterator.iterator(getProperties(), RelationInfo.class);
  }
}
