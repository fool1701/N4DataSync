/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import static com.tridium.tagdictionary.util.TagDictionaryUtil.handleIllegalChild;

import java.util.Iterator;
import java.util.Optional;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.IllegalNameException;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Id;
import javax.baja.tag.TagGroupInfo;

/**
 * BTagGroupInfoList is a list of {@code TagGroupInfo}.
 *
 * @author John Sublett
 * @creation 2/21/14
 * @since Niagara 4.0
 */
@NiagaraType
public class BTagGroupInfoList extends BInfoList implements Iterable<TagGroupInfo>
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BTagGroupInfoList(2979906276)1.0$ @*/
/* Generated Tue Jan 25 17:26:55 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTagGroupInfoList.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Returns an iterator over the tag groups in this list. Hidden tag groups are excluded as they
   * are considered deprecated and are only retained to preserve relations to them.
   *
   * @return iterator of tag groups
   */
  @Override
  public Iterator<TagGroupInfo> iterator()
  {
    return getProperties().stream()
      .filter(p -> !Flags.isHidden(this, p) && get(p) instanceof TagGroupInfo)
      .map(p -> (TagGroupInfo)get(p))
      .iterator();
  }

  /**
   * Test whether this list includes a tag group with the specified id.  Hidden tag groups are not
   * considered; see {@link #iterator()}.
   *
   * @param id tag id to search for
   * @return {@code true} if the list contains a tag group with the specified id; {@code false}
   * otherwise.
   */
  public boolean containsTagId(Id id)
  {
    return getTagGroup(id).isPresent();
  }

  /**
   * Get the tag group for the specified id.  Hidden tag groups are not considered; see
   * {@link #iterator()}.
   *
   * @param id id of the tag
   * @return an {@code Optional} that contains the {@code TagGroupInfo} with the specified id, if it
   * exists in the list; an empty {@code Optional} otherwise.
   */
  public Optional<TagGroupInfo> getTagGroup(Id id)
  {
    for (SlotCursor<Property> c = getProperties(); c.next(TagGroupInfo.class);)
    {
      if (Flags.isHidden(this, c.property()))
        continue;

      TagGroupInfo tagGroup = (TagGroupInfo)c.get();
      if (tagGroup.getGroupId().equals(id))
        return Optional.of(tagGroup);
    }

    return Optional.empty();
  }

////////////////////////////////////////////////////////////////
// BInfoList
////////////////////////////////////////////////////////////////

  /**
   * If the property being renamed is a {@link BTagGroupInfo}, the rename is
   * not allowed if the group has any relations (see
   * {@link BTagGroupInfo#relations()}). Also, the new tag group id cannot match
   * any existing tag ids in the tag dictionary to which this list belongs.
   *
   * @param property slot to be renamed
   * @param newName new name
   * @param context execution context
   */
  @Override
  public void checkRename(Property property, String newName, Context context)
  {
    if (checkContext(context))
      return;

    BValue bValue = get(property);
    if (bValue instanceof BTagGroupInfo)
    {
      BTagDictionary td = getTagDictionary();
      if (td == null)
        return;

      Id newId = Id.newId(td.getNamespace(), newName);
      if (td.getTagDefinitions().containsTagId(newId))
        throw new IllegalNameException("tagdictionary", "tagGroupInfo.duplicateTagId", new String[] { newId.toString() });
    }
  }

  /**
   * If the property being added is a {@link BTagGroupInfo}, the new tag group id cannot match any
   * existing tag ids in the tag dictionary to which this list belongs.  Also enforce that only
   * {@link TagGroupInfo}s are added.
   *
   * @param name name of the child object being added
   * @param value child object being added
   * @param flags {@link Flags} to be added to the child object
   * @param facets {@link BFacets} to be added to the child object
   * @param context execution context
   */
  @Override
  public void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
    // Only allow TagGroupInfos to be added to a BTagGroupInfoList
    if (!(value instanceof TagGroupInfo))
      handleIllegalChild(this, value, context);

    // TODO What about if context.equals(BTagDictionary.importContext)?  Could we call checkContext?
    boolean isDecoding = context != null && context.equals(Context.decoding);
    boolean isCommit = context != null && context.equals(Context.commit);

    if (value instanceof BTagGroupInfo)
    {
      BTagDictionary td = getTagDictionary();
      if (td == null)
        return;

      Id newId = Id.newId(td.getNamespace(), name);
      if (td.getTagDefinitions().containsTagId(newId))
      {
        if (!isDecoding && !isCommit)
          throw new IllegalNameException("tagdictionary", "tagGroupInfo.duplicateTagId", new String[] { newId.toString() });
        else if ((flags & Flags.HIDDEN) == 0)
        {
          // Do not log a message for hidden tag groups- these are considered deprecated and are
          // only retained to preserve relations to them.
          BTagDictionary.logger.warning(BTagDictionary.lex.getText("tagGroupInfo.duplicateTagId", newId.toString() ));
        }
      }
    }
  }

  /**
   * No restrictions on removing child objects.
   *
   * @param property slot to be removed
   * @param context execution context
   */
  @Override
  public void checkRemove(Property property, Context context)
  {
  }

  /**
   * If the property is a {@link BTagGroupInfo}, call {@link BTagGroupInfo#groupRenamed()} on it.
   *
   * @param property property being renamed
   * @param oldName old name
   * @param context execution context
   */
  @Override
  public void renamed(Property property, String oldName, Context context)
  {
    BValue value = get(property);
    if (value instanceof BTagGroupInfo)
    {
      ((BTagGroupInfo)value).groupRenamed();
    }
  }
}
