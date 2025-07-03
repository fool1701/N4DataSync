/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import static com.tridium.tagdictionary.util.TagDictionaryUtil.handleIllegalParent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;

import javax.baja.collection.FilteredIterator;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.RelationInfo;
import javax.baja.tag.TagGroupInfo;
import javax.baja.tag.TagInfo;
import javax.baja.util.BIRestrictedComponent;

import com.tridium.tagdictionary.condition.BNever;

/**
 * BTagRule is a {@code BComponent} implementation of {@code TagRule}. It is used by a
 * {@link BSmartTagDictionary} to determine implied tags and relations for an {@link Entity}.
 *
 * @author John Sublett
 * @creation 2/18/14
 * @since Niagara 4.0
 */
@NiagaraType
/*
 The condition that this rule tests for.
 */
@NiagaraProperty(
  name = "condition",
  type = "BTagRuleCondition",
  defaultValue = "new BNever()"
)
/*
 The tags associated with this rule.
 */
@NiagaraProperty(
  name = "tagList",
  type = "BTagInfoList",
  defaultValue = "new BTagInfoList()"
)
/*
 The tag groups associated with this rule.
 */
@NiagaraProperty(
  name = "tagGroupList",
  type = "BTagGroupInfoList",
  defaultValue = "new BTagGroupInfoList()"
)
/*
 The relations associated with this rule.
 */
@NiagaraProperty(
  name = "relationList",
  type = "BRelationInfoList",
  defaultValue = "new BRelationInfoList()"
)
public class BTagRule extends BComponent implements TagRule, BIRestrictedComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BTagRule(4035714068)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "condition"

  /**
   * Slot for the {@code condition} property.
   * The condition that this rule tests for.
   * @see #getCondition
   * @see #setCondition
   */
  public static final Property condition = newProperty(0, new BNever(), null);

  /**
   * Get the {@code condition} property.
   * The condition that this rule tests for.
   * @see #condition
   */
  public BTagRuleCondition getCondition() { return (BTagRuleCondition)get(condition); }

  /**
   * Set the {@code condition} property.
   * The condition that this rule tests for.
   * @see #condition
   */
  public void setCondition(BTagRuleCondition v) { set(condition, v, null); }

  //endregion Property "condition"

  //region Property "tagList"

  /**
   * Slot for the {@code tagList} property.
   * The tags associated with this rule.
   * @see #getTagList
   * @see #setTagList
   */
  public static final Property tagList = newProperty(0, new BTagInfoList(), null);

  /**
   * Get the {@code tagList} property.
   * The tags associated with this rule.
   * @see #tagList
   */
  public BTagInfoList getTagList() { return (BTagInfoList)get(tagList); }

  /**
   * Set the {@code tagList} property.
   * The tags associated with this rule.
   * @see #tagList
   */
  public void setTagList(BTagInfoList v) { set(tagList, v, null); }

  //endregion Property "tagList"

  //region Property "tagGroupList"

  /**
   * Slot for the {@code tagGroupList} property.
   * The tag groups associated with this rule.
   * @see #getTagGroupList
   * @see #setTagGroupList
   */
  public static final Property tagGroupList = newProperty(0, new BTagGroupInfoList(), null);

  /**
   * Get the {@code tagGroupList} property.
   * The tag groups associated with this rule.
   * @see #tagGroupList
   */
  public BTagGroupInfoList getTagGroupList() { return (BTagGroupInfoList)get(tagGroupList); }

  /**
   * Set the {@code tagGroupList} property.
   * The tag groups associated with this rule.
   * @see #tagGroupList
   */
  public void setTagGroupList(BTagGroupInfoList v) { set(tagGroupList, v, null); }

  //endregion Property "tagGroupList"

  //region Property "relationList"

  /**
   * Slot for the {@code relationList} property.
   * The relations associated with this rule.
   * @see #getRelationList
   * @see #setRelationList
   */
  public static final Property relationList = newProperty(0, new BRelationInfoList(), null);

  /**
   * Get the {@code relationList} property.
   * The relations associated with this rule.
   * @see #relationList
   */
  public BRelationInfoList getRelationList() { return (BRelationInfoList)get(relationList); }

  /**
   * Set the {@code relationList} property.
   * The relations associated with this rule.
   * @see #relationList
   */
  public void setRelationList(BRelationInfoList v) { set(relationList, v, null); }

  //endregion Property "relationList"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTagRule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor that sets all properties to their default values.
   */
  public BTagRule()
  {
  }

  /**
   * Initializes the {@link #condition} property.
   *
   * @param cond initial value of the {@link #condition} property
   */
  public BTagRule(BTagRuleCondition cond)
  {
    setCondition(cond);
  }

///////////////////////////////////////////////////////////
// BIRestrictedComponent
///////////////////////////////////////////////////////////

  /**
   * BTagRules may only be added to a {@link BTagRuleList} located at a tag dictionary's
   * {@link BSmartTagDictionary#tagRules} property.
   * @since Niagara 4.4
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context context)
  {
    if (!(parent instanceof BTagRuleList))
      handleIllegalParent(parent, this, context);
  }

////////////////////////////////////////////////////////////////
// Condition
////////////////////////////////////////////////////////////////

  /**
   * Get the predicate for this rule.
   *
   * @return the predicate to evaluate for this rule
   */
  @Override
  public Predicate<Entity> getRuleCondition()
  {
    return getCondition();
  }

////////////////////////////////////////////////////////////////
// Tags
////////////////////////////////////////////////////////////////

  /**
   * Test whether this rule includes an implied tag with the specified id.
   *
   * <p>See {@link BTagInfoList#containsTagId(Id)}.</p>
   *
   * @param id tag id to search for
   * @return {@code true} if the rule contains a tag with the specified id;
   * {@code false} otherwise.
   */
  @Override
  public boolean containsTagId(Id id)
  {
    return getTag(id).isPresent();
  }

  /**
   * Get the tag in this rule for the specified id.  Also searches each tag group in this rule for
   * a match with the tag group id or a tag within the tag group with the specified id.
   *
   * @param id id of the implied tag
   * @return an {@code Optional} that contains the {@code TagInfo} for the
   * specified id if the rule includes one; an empty {@code Optional} otherwise
   */
  @Override
  public Optional<TagInfo> getTag(Id id)
  {
    Optional<TagInfo> found = getTagList().getTag(id);
    if (found.isPresent())
      return found;

    for (SlotCursor<Property> tagGroupInfos = getTagGroupList().getProperties(); tagGroupInfos.next(TagGroupInfo.class);)
    {
      TagGroupInfo tagGroupInfo = (TagGroupInfo)tagGroupInfos.get();

      // check the tag group id
      if (tagGroupInfo.getGroupId().equals(id))
        return tagGroupInfo.getNameTagInfo();

      // check the tags within the group
      for (Iterator<TagInfo> tagInfos = tagGroupInfo.getTags(); tagInfos.hasNext();)
      {
        TagInfo tagInfo = tagInfos.next();
        if (tagInfo.getTagId().equals(id))
          return Optional.of(tagInfo);
      }
    }

    return Optional.empty();
  }

  /**
   * Get the tags associated with this rule.
   *
   * @return tags associated with this rule
   */
  @Override
  public Collection<TagInfo> getTags()
  {
    ArrayList<TagInfo> result = new ArrayList<>();
    SlotCursor<Property> c = getTagList().getProperties();
    while (c.next(TagInfo.class))
      result.add((TagInfo)c.get());
    return result;
  }

  /**
   * Get the tag groups associated with this rule.
   *
   * @return tag groups associated with this rule
   */
  @Override
  public Collection<TagGroupInfo> getTagGroups()
  {
    ArrayList<TagGroupInfo> result = new ArrayList<>();
    SlotCursor<Property> c = getTagGroupList().getProperties();
    while (c.next(TagGroupInfo.class))
      result.add((TagGroupInfo)c.get());
    return result;
  }

///////////////////////////////////////////////////////////
// Relations
///////////////////////////////////////////////////////////

  /**
   * Test whether this rule includes an implied relation with the specified id.
   *
   * @param id relation id to search for
   * @return {@code true} if the rule contains a relation with the specified id;
   * {@code false} otherwise.
   */
  @Override
  public boolean containsRelationId(Id id)
  {
    SlotCursor<Property> c = getRelationList().getProperties();
    while (c.next(RelationInfo.class))
    {
      RelationInfo rel = (RelationInfo)c.get();
      if (rel.getRelationId().equals(id))
        return true;
    }

    return false;
  }

  /**
   * Get an iterator of the relations in this rule with the specified id.
   *
   * @param id relation id to search for
   * @return an iterator of the relations in this rule with the specified id
   */
  @Override
  public Iterator<RelationInfo> getRelations(Id id)
  {
    Predicate<RelationInfo> idFilter = rel -> rel.getRelationId().equals(id);
    return new FilteredIterator<>(idFilter, getRelationList().iterator());
  }

  /**
   * Get the relations that are associated with this rule.
   *
   * @return relations associated with this rule
   */
  @Override
  public Collection<RelationInfo> getRelations()
  {
    ArrayList<RelationInfo> result = new ArrayList<>();
    SlotCursor<Property> c = getRelationList().getProperties();
    while (c.next(RelationInfo.class))
      result.add((RelationInfo)c.get());
    return result;
  }

  @Override
  public String toString(Context context)
  {
    return getDisplayName(context);
  }
}
