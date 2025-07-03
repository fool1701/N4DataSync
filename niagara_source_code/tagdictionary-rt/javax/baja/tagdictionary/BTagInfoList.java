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
import javax.baja.sys.Flags;
import javax.baja.sys.IllegalNameException;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Id;
import javax.baja.tag.TagInfo;

/**
 * BTagInfoList is a list of {@code TagInfo}.
 *
 * @author John Sublett
 * @creation 2/20/14
 * @since Niagara 4.0
 */
@NiagaraType
public class BTagInfoList
  extends BInfoList
  implements Iterable<TagInfo>
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BTagInfoList(2979906276)1.0$ @*/
/* Generated Tue Jan 25 17:26:55 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTagInfoList.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Returns an iterator over the tags in this list.
   *
   * @return iterator of tags
   */
  @Override
  public Iterator<TagInfo> iterator()
  {
    return SlotCursorIterator.iterator(getProperties(), TagInfo.class);
  }

  /**
   * Test whether this list includes a tag with the specified id.
   *
   * @param id tag id to search for
   * @return {@code true} if the list contains a tag with the specified id;
   *   {@code false} otherwise.
   */
  public boolean containsTagId(Id id)
  {
    return getTag(id).isPresent();
  }

  /**
   * Get the tag for the specified id.
   *
   * @param id id of the tag
   * @return an {@code Optional} that contains the {@code TagInfo} with the
   * specified id, if it exists in the list; an empty {@code Optional}
   * otherwise.
   */
  public Optional<TagInfo> getTag(Id id)
  {
    for (SlotCursor<Property> tagInfos = getProperties(); tagInfos.next(TagInfo.class);)
    {
      TagInfo tagInfo = (TagInfo)tagInfos.get();
      if (tagInfo.getTagId().equals(id))
      {
        return Optional.of(tagInfo);
      }
    }

    return Optional.empty();
  }

  /**
   * First, checks that the child is a TagInfo. Then, disallows adding child objects when under a
   * frozen {@link BTagDictionary}.
   *
   * <p>Exception: even if the dictionary is frozen, removal is allowed if the
   * parent of this list is a {@link BTagGroupInfo}.</p>
   *
   * <p>Additionally, if the property is a {@link BTagInfo} and the dictionary
   * is not frozen, the new tag id must not match an existing tag group id.</p>
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
    // Only allow TagInfos to be added to a BTagInfoList
    if (!(value instanceof TagInfo))
    {
      handleIllegalChild(this, value, context);
    }

    if (checkContext(context))
    {
      return;
    }

    if (getParent() instanceof BTagGroupInfo)
    {
      return;
    }

    if (isDictionaryFrozen())
    {
      throw new LocalizableRuntimeException("tagdictionary", "frozenDictionary.checkAdd");
    }

    BTagDictionary td = getTagDictionary();
    if (td == null)
    {
      return;
    }

    Id newId = Id.newId(td.getNamespace(), name);

    // TODO do we also want to search the tag definitions?
    // TODO what about the tags within the tag group definitions?
    BTagGroupInfoList tagGroupInfos = td.getTagGroupDefinitions();
    if(tagGroupInfos.containsTagId(newId))
    {
      throw new IllegalNameException("tagdictionary", "tagInfo.duplicateTagId", new String[] { newId.toString() });
    }
  }

  /**
   * Disallows renaming slots of a frozen {@link BTagDictionary}.
   *
   * <p>Exception: whether the dictionary is frozen is only evaluated if the
   * property is a {@link BTagInfo}.</p>
   *
   * <p>Additionally, if the property is a {@link BTagInfo} and the dictionary
   * is not frozen, the new tag id must not match an existing tag group id.</p>
   *
   * @param property slot to be renamed
   * @param newName new name
   * @param context execution context
   */
  @Override
  public void checkRename(Property property, String newName, Context context)
  {
    if (checkContext(context))
    {
      return;
    }

    BValue bValue = get(property);
    if (bValue instanceof BTagInfo)
    {
      if (isDictionaryFrozen())
      {
        throw new IllegalNameException("tagdictionary", "frozenDictionary.checkRename", new String[] {newName});
      }

      // TODO null check the BTagDictionary?
      BTagDictionary td = getTagDictionary();

      Id newId = Id.newId(td.getNamespace(), newName);

      // TODO do we also want to search the tag definitions?
      // TODO what about the tags within the tag group definitions?
      BTagGroupInfoList tagGroupInfos = td.getTagGroupDefinitions();
      if (tagGroupInfos.containsTagId(newId))
      {
        throw new IllegalNameException("tagdictionary", "tagInfo.duplicateTagId", new String[] { newId.toString() });
      }
    }
  }

  /**
   * Disallows removing slots when under a frozen {@link BTagDictionary}.
   *
   * <p>Exception: even if the dictionary is frozen, removal is allowed if the
   * parent of this list is a {@link BTagGroupInfo}.</p>
   *
   * @param property slot to be removed
   * @param context execution context
   */
  @Override
  public void checkRemove(Property property, Context context)
  {
    if (checkContext(context))
    {
      return;
    }

    if (getParent()instanceof BTagGroupInfo)
    {
      return;
    }

    if (isDictionaryFrozen())
    {
      throw new LocalizableRuntimeException("tagdictionary", "frozenDictionary.checkRemove");
    }
  }

  /**
   * If the property is a {@link BTagInfo}, call {@link BTagInfo#tagRenamed()} on it.
   *
   * @param property property being renamed
   * @param oldName old name
   * @param context execution context
   */
  @Override
  public void renamed(Property property, String oldName, Context context)
  {
    BValue value = get(property);
    if (value instanceof BTagInfo)
    {
      ((BTagInfo)value).tagRenamed();
    }
  }
}
