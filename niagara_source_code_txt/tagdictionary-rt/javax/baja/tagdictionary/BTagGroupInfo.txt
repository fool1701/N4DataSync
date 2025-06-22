/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import static com.tridium.tagdictionary.util.TagDictionaryUtil.generateId;
import static com.tridium.tagdictionary.util.TagDictionaryUtil.handleIllegalParent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelation;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.RelationKnob;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.BIDataPolicy;
import javax.baja.tag.DataPolicy;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.TagDictionary;
import javax.baja.tag.TagDictionaryService;
import javax.baja.tag.TagGroupInfo;
import javax.baja.tag.TagInfo;
import javax.baja.tagdictionary.data.BDataPolicy;
import javax.baja.util.BIRestrictedComponent;
import javax.baja.util.BTypeSpec;
import javax.baja.util.ICoalesceable;

import com.tridium.tagdictionary.BNiagaraTagDictionary;
import com.tridium.tagdictionary.condition.BAlways;
import com.tridium.tagdictionary.tag.BTagGroupNameTag;

/**
 * BTagGroupInfo is a {@code BComponent} implementation of {@code TagGroupInfo}.
 *
 * <p>Only one data policy slot (of a type that extends {@link BDataPolicy}) is allowed per tag.
 * Once added, the {@link #addDataPolicy} slot is hidden until the policy is removed.</p>
 *
 * @author John Sublett
 * @creation 2/21/14
 * @since Niagara 4.0
 */
@NiagaraType
/*
 Condition for determining validity within this tag group. Default value: BAlways.
 */
@NiagaraProperty(
  name = "validity",
  type = "BTagRuleCondition",
  defaultValue = "new BAlways()"
)
/*
 List of tags in this tag group.
 */
@NiagaraProperty(
  name = "tagList",
  type = "BTagInfoList",
  defaultValue = "new BTagInfoList()"
)
/*
 Add a data policy to the tag group.
 */
@NiagaraAction(
  name = "addDataPolicy",
  parameterType = "BDynamicEnum",
  defaultValue = "BDynamicEnum.DEFAULT"
)
public class BTagGroupInfo extends BComponent
  implements TagGroupInfo, BIDataPolicy, BIRestrictedComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BTagGroupInfo(1333023494)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "validity"

  /**
   * Slot for the {@code validity} property.
   * Condition for determining validity within this tag group. Default value: BAlways.
   * @see #getValidity
   * @see #setValidity
   */
  public static final Property validity = newProperty(0, new BAlways(), null);

  /**
   * Get the {@code validity} property.
   * Condition for determining validity within this tag group. Default value: BAlways.
   * @see #validity
   */
  public BTagRuleCondition getValidity() { return (BTagRuleCondition)get(validity); }

  /**
   * Set the {@code validity} property.
   * Condition for determining validity within this tag group. Default value: BAlways.
   * @see #validity
   */
  public void setValidity(BTagRuleCondition v) { set(validity, v, null); }

  //endregion Property "validity"

  //region Property "tagList"

  /**
   * Slot for the {@code tagList} property.
   * List of tags in this tag group.
   * @see #getTagList
   * @see #setTagList
   */
  public static final Property tagList = newProperty(0, new BTagInfoList(), null);

  /**
   * Get the {@code tagList} property.
   * List of tags in this tag group.
   * @see #tagList
   */
  public BTagInfoList getTagList() { return (BTagInfoList)get(tagList); }

  /**
   * Set the {@code tagList} property.
   * List of tags in this tag group.
   * @see #tagList
   */
  public void setTagList(BTagInfoList v) { set(tagList, v, null); }

  //endregion Property "tagList"

  //region Action "addDataPolicy"

  /**
   * Slot for the {@code addDataPolicy} action.
   * Add a data policy to the tag group.
   * @see #addDataPolicy(BDynamicEnum parameter)
   */
  public static final Action addDataPolicy = newAction(0, BDynamicEnum.DEFAULT, null);

  /**
   * Invoke the {@code addDataPolicy} action.
   * Add a data policy to the tag group.
   * @see #addDataPolicy
   */
  public void addDataPolicy(BDynamicEnum parameter) { invoke(addDataPolicy, parameter, null); }

  //endregion Action "addDataPolicy"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTagGroupInfo.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Hides the {@link #addDataPolicy} action slot if a {@link BDataPolicy} is
   * already present (see {@link #getDataPolicy()}).
   */
  @Override
  public void started() throws Exception
  {
    updateAddDataPolicyActionFlags();
  }

  /**
   * Get the dictionary that this tag group is defined in, if one exists.
   *
   * @return an {@code Optional} that contains the {@code TagDictionary}
   * for this tag group if the tag group is part of a {@code TagDictionary};
   * otherwise, an empty {@code Optional}
   */
  @Override
  public Optional<TagDictionary> getDictionary()
  {
    return BTagDictionary.getParentDictionary(this);
  }

  /**
   * Get the id for this group: {@link BTagDictionary#namespace}:
   * {@link #getName()}.
   *
   * @return id for this group
   */
  @Override
  public Id getGroupId()
  {
    // id is cached for future calls
    if (isRunning() && groupId != null)
    {
      return groupId;
    }

    groupId = generateId(this, this::getDictionary);
    return groupId;
  }

  /**
   * Regenerates the {@link Id} of this group.
   *
   * @since Niagara 4.3
   */
  public void groupRenamed()
  {
    groupId = null;
    groupId = getGroupId();
  }

  /**
   * Test whether this tag group is an ideal match (see
   * {@link BTagRuleCondition#testIdealMatch(Type)}) for the specified type
   * using the {@link #validity} property.
   *
   * @param type {@code Type} to test
   * @return {@code true} if the tag group is an ideal match for the specified
   * type; {@code false} otherwise
   */
  @Override
  public boolean isIdealFor(Type type)
  {
    return getValidity().testIdealMatch(type);
  }

  /**
   * Test whether this tag group is valid (see
   * {@link BTagRuleCondition#test(Entity)}) for the specified entity using
   * the {@link #validity} property.
   *
   * @param entity {@code Entity} to test
   * @return {@code true} if the tag group is valid for the specified entity;
   * {@code false} otherwise
   */
  @Override
  public boolean isValidFor(Entity entity)
  {
    return getValidity().test(entity);
  }

  /**
   * Get the tags in the group.
   *
   * @return an iterator of the tags in the group
   */
  @Override
  public Iterator<TagInfo> getTags()
  {
    return getTagList().iterator();
  }

  /**
   * Find all the tag groups in the specified dictionary that are valid for the specified child
   * type.
   *
   * @param dictionary tag dictionary from which to retrieve tag groups
   * @param parent parent component
   * @param childType type against which validity (see {@link #isValidFor(Entity)}) of each tag
   * group is checked
   * @return list of tag groups in the dictionary that are valid for the child type
   */
  public static ArrayList<TagGroupInfo> getValidTagGroups(TagDictionary dictionary,
    BComponent parent, Type childType)
  {
    BObject childTypeInstance = childType.getInstance();
    ArrayList<TagGroupInfo> list = new ArrayList<>();
    if (childTypeInstance instanceof BComponent)
    {
      BComponent childComp = childTypeInstance.asComponent();
      // add a parent relationship to the manager target component
      BRelation relation = new BRelation(BNiagaraTagDictionary.PARENT, parent);
      childComp.relations().add(relation);

      Iterator<TagGroupInfo> tagGroups = dictionary.getTagGroups();
      while (tagGroups.hasNext())
      {
        TagGroupInfo tagGroup = tagGroups.next();
        //boolean idealFor = tagGroup.isIdealFor(parent.getType());
        if (tagGroup.isValidFor(childComp))
        {
          list.add(tagGroup);
        }
      }
    }
    return list;
  }

  /**
   * Allows TagGroupInfo object names to be searchable.
   *
   * @return an optional containing a BTagInfo object with this BTagGroupInfo specified as the ID,
   * instead of a BTagInfo.
   */
  @Override
  public Optional<TagInfo> getNameTagInfo()
  {
    return Optional.of(new BTagGroupNameTag(this));
  }

///////////////////////////////////////////////////////////
// BIRestrictedComponent
///////////////////////////////////////////////////////////

  /**
   * BTagGroupInfos may only be added to a {@link BTagGroupInfoList} located at a tag dictionary's
   * {@link BTagDictionary#tagGroupDefinitions} property and a tag rule's
   * {@link BTagRule#tagGroupList} property.
   * @since Niagara 4.4
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context context)
  {
    if (!(parent instanceof BTagGroupInfoList))
    {
      handleIllegalParent(parent, this, context);
    }
  }

///////////////////////////////////////////////////////////
// Data policy
///////////////////////////////////////////////////////////

  /**
   * Get the DataPolicy associated with this tag group.
   *
   * @return an {@code Optional} that contains the {@code DataPolicy} for this
   * tag group, if it exists; an empty {@code Optional} otherwise.
   */
  @Override
  public Optional<DataPolicy> getDataPolicy()
  {
    BDataPolicy[] children = getChildren(BDataPolicy.class);
    if (children == null || children.length == 0)
    {
      return Optional.empty();
    }
    return Optional.of(children[0]);
  }

  /**
   * Disallows adding a {@link BDataPolicy} if one already exists.
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
    if (Context.commit.equals(context))
    {
      return;
    }

    // TODO Check that value is actually a BDataPolicy?
    if (getDataPolicy().isPresent())
    {
      throw new LocalizableRuntimeException("tagdictionary", "tagGroupInfo.oneDataPolicy");
    }
  }

  /**
   * Hides the {@link #addDataPolicy} action slot if the property added
   * is of type {@link BDataPolicy}.
   *
   * @param p {@code Property} added
   * @param cx execution {@code Context}
   */
  @Override
  public void added(Property p, Context cx)
  {
    if (get(p).getType().is(BDataPolicy.TYPE))
	{
      updateAddDataPolicyActionFlags();
    }
  }

  /**
   * Checks that the endpoint ord of the activated tag group relation is the slot path ord of the
   * tag group and not its handle ord. This ensures that the tag group relation will be copied with
   * the components on which it is applied.
   * @since Niagara 4.4u1
   */
  @Override
  public void relationKnobAdded(RelationKnob knob, Context context)
  {
    if (isTagGroupRelationKnob(knob))
    {
      BOrd slotPathOrd = getSlotPathOrd();
      if (!doesEndpointOrdMatch(knob, slotPathOrd))
      {
        TagDictionaryService service = getTagDictionaryService();
        if (service instanceof BTagDictionaryService)
        {
          ((BTagDictionaryService) service)
            .addTask(new FixEndpointOrdTask(knob.getRelation(), slotPathOrd));
        }
      }
    }
  }

  static boolean isTagGroupRelationKnob(RelationKnob knob)
  {
    return TAG_GROUP_RELATION_STR.equals(knob.getRelationId());
  }

  private static boolean doesEndpointOrdMatch(RelationKnob knob, BOrd slotPathOrd)
  {
    return knob.getRelation().getEndpointOrd().equals(slotPathOrd);
  }

  /**
   * Restores the {@link #addDataPolicy} action slot if the property
   * removed is of type {@link BDataPolicy}.
   *
   * @param p {@code Property} removed
   * @param oldValue value of the removed property
   * @param cx execution {@code Context}
   */
  @Override
  public void removed(Property p, BValue oldValue, Context cx)
  {
    if (oldValue.getType().is(BDataPolicy.TYPE))
    {
      updateAddDataPolicyActionFlags();
    }
  }

  private void updateAddDataPolicyActionFlags()
  {
    if (getDataPolicy().isPresent())
    {
      setFlags(addDataPolicy, getFlags(addDataPolicy) | Flags.HIDDEN);
    }
    else
    {
      setFlags(addDataPolicy, getFlags(addDataPolicy) & ~Flags.HIDDEN);
    }
  }

  /**
   * For the {@link #addDataPolicy} action slot, return a {@link BDynamicEnum}
   * set to zero with a range consisting of all concrete types of
   * {@link BDataPolicy}.
   *
   * @param action action for which a default value is required
   * @return a {@code BDynamicEnum} if for the {@code addDataPolicy} action
   * slot;
   * otherwise, see {@link BComponent#getActionParameterDefault(Action)}.
   */
  @Override
  public BValue getActionParameterDefault(Action action)
  {
    if (action.equals(addDataPolicy))
    {
      TypeInfo[] concreteTypes = Sys.getRegistry().getConcreteTypes(BDataPolicy.TYPE.getTypeInfo());
      String[] tags = new String[concreteTypes.length];
      for (int i = 0; i < concreteTypes.length; ++i)
      {
        tags[i] = SlotPath.escape(concreteTypes[i].getTypeSpec().toString());
      }

      BEnumRange range = BEnumRange.make(tags);
      return BDynamicEnum.make(0, range);
    }
    else
    {
      return super.getActionParameterDefault(action);
    }
  }

  /**
   * Adds an instance of an object that extends {@link BDataPolicy} to the tag group.
   * The specific type of {@code BDataPolicy} is stored in the value of the
   * specified {@code BDynamicEnum}. The new slot will be named "dp_" + the
   * component name of the tag group.
   *
   * @param selected type that extends {@code BDataPolicy} to be added
   */
  @SuppressWarnings("unused")
  public void doAddDataPolicy(BDynamicEnum selected)
  {
    BTypeSpec typeSpec = BTypeSpec.make(SlotPath.unescape(selected.getTag()));
    add("dp_" + getName(), typeSpec.getInstance().asValue());
  }

  /**
   * Ensures that the {@link #addDataPolicy} action flags are set correctly before evaluating
   * equivalence.  These are set by {@link #started()} based on the default value of the tag and
   * whether there is a data policy already present.
   *
   * @param obj object to compare to this
   * @return true if obj is equivalent to this
   */
  @Override
  public synchronized boolean equivalent(Object obj)
  {
    if (!(obj instanceof BTagGroupInfo))
    {
      return false;
    }

    try
    {
      updateAddDataPolicyActionFlags();
      ((BTagGroupInfo)obj).updateAddDataPolicyActionFlags();
    }
    catch (Exception ignored)
    {
    }

    return super.equivalent(obj);
  }

///////////////////////////////////////////////////////////
// Icon
///////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("tag.png");

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private static final String TAG_GROUP_RELATION_STR = BNiagaraTagDictionary.TAG_GROUP_RELATION.toString();

  private Id groupId;

///////////////////////////////////////////////////////////
// Fix endpoint ord task
///////////////////////////////////////////////////////////

  /**
   * Runnable to fix the endpoint or of a relation.
   */
  private static final class FixEndpointOrdTask implements ICoalesceable, Runnable
  {
    private final BRelation relation;
    private final BOrd slotPathOrd;

    public FixEndpointOrdTask(BRelation relation, BOrd slotPathOrd)
    {
      this.relation = relation;
      this.slotPathOrd = slotPathOrd;
    }

    @Override
    public void run()
    {
      relation.setEndpointOrd(slotPathOrd);
    }

    @Override
    public Object getCoalesceKey()
    {
      return this;
    }

    @Override
    public ICoalesceable coalesce(ICoalesceable c)
    {
      return c;
    }

    /**
     * Tasks for the same relation can be coalesced because the endpoint ord set by an existing task
     * would be overwritten by a subsequent task. Therefore, the equals is based only on the
     * relation and not the slot path ord.
     */
    @Override
    public boolean equals(Object o)
    {
      if (this == o) { return true; }
      if (o == null || getClass() != o.getClass()) { return false; }
      FixEndpointOrdTask that = (FixEndpointOrdTask) o;
      return Objects.equals(relation, that.relation);
    }

    @Override
    public int hashCode()
    {
      return relation.hashCode();
    }
  }
}
