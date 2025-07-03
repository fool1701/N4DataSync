/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import static com.tridium.tagdictionary.util.TagDictionaryUtil.generateId;
import static com.tridium.tagdictionary.util.TagDictionaryUtil.handleIllegalParent;

import java.util.Optional;

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
import javax.baja.sys.BMarker;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.BIDataPolicy;
import javax.baja.tag.DataPolicy;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.TagDictionary;
import javax.baja.tag.TagInfo;
import javax.baja.tagdictionary.data.BDataPolicy;
import javax.baja.util.BIRestrictedComponent;
import javax.baja.util.BTypeSpec;

import com.tridium.tagdictionary.condition.BAlways;

/**
 * BTagInfo is a {@code BComponent} implementation of {@code TagInfo}.
 *
 * <p>Only one data policy slot (of a type that extends {@link BDataPolicy}) is allowed per tag.
 * Once added, the {@link #addDataPolicy} action is hidden until the policy is removed.  Tags of
 * value type {@link BMarker} may not have a {@code BDataPolicy} slot ({@code addDataPolicy} action
 * is always hidden).</p>
 *
 * @author John Sublett
 * @creation 2/13/14
 * @since Niagara 4.0
 */
@NiagaraType
/*
 Condition for determining validity within this tag.  Default value: {@link BAlways}.
 */
@NiagaraProperty(
  name = "validity",
  type = "BTagRuleCondition",
  defaultValue = "new BAlways()"
)
/*
 Add a data policy to the tag.
 */
@NiagaraAction(
  name = "addDataPolicy",
  parameterType = "BDynamicEnum",
  defaultValue = "BDynamicEnum.DEFAULT"
)
public abstract class BTagInfo extends BComponent
  implements TagInfo, BIDataPolicy, BIRestrictedComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BTagInfo(2538344184)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "validity"

  /**
   * Slot for the {@code validity} property.
   * Condition for determining validity within this tag.  Default value: {@link BAlways}.
   * @see #getValidity
   * @see #setValidity
   */
  public static final Property validity = newProperty(0, new BAlways(), null);

  /**
   * Get the {@code validity} property.
   * Condition for determining validity within this tag.  Default value: {@link BAlways}.
   * @see #validity
   */
  public BTagRuleCondition getValidity() { return (BTagRuleCondition)get(validity); }

  /**
   * Set the {@code validity} property.
   * Condition for determining validity within this tag.  Default value: {@link BAlways}.
   * @see #validity
   */
  public void setValidity(BTagRuleCondition v) { set(validity, v, null); }

  //endregion Property "validity"

  //region Action "addDataPolicy"

  /**
   * Slot for the {@code addDataPolicy} action.
   * Add a data policy to the tag.
   * @see #addDataPolicy(BDynamicEnum parameter)
   */
  public static final Action addDataPolicy = newAction(0, BDynamicEnum.DEFAULT, null);

  /**
   * Invoke the {@code addDataPolicy} action.
   * Add a data policy to the tag.
   * @see #addDataPolicy
   */
  public void addDataPolicy(BDynamicEnum parameter) { invoke(addDataPolicy, parameter, null); }

  //endregion Action "addDataPolicy"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTagInfo.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Protected constructor
   */
  protected BTagInfo()
  {
  }

  /**
   * Hides the {@link #addDataPolicy} action slot if the tag is of type {@link BMarker} or a
   * {@link BDataPolicy} is already present (see {@link #getDataPolicy()}).
   */
  @Override
  public void started()
  {
    updateAddDataPolicyActionFlags();
  }

  /**
   * Regenerates the {@link Id} of this tag.
   */
  public void tagRenamed()
  {
    tagId = null;
    tagId = getTagId();

    // TODO what should be done about the tag index?
  }

  /**
   * Get the dictionary that this tag is defined in, if one exists.
   *
   * @return an {@code Optional} that contains the {@code TagDictionary}
   * for this tag if the tag is part of a {@code TagDictionary};
   * otherwise, an empty {@code Optional}
   */
  @Override
  public Optional<TagDictionary> getDictionary()
  {
    return BTagDictionary.getParentDictionary(this);
  }

  /**
   * Get the id for this tag.
   *
   * <p>If the unescaped name of this component can be split around the id
   * dictionary separator (see {@link Id#DICT_SEP}), the unescaped name will be used to create
   * the id.  Otherwise, the namespace of this tag's {@link TagDictionary}
   * and its escaped name will be used.</p>
   *
   * @return id for this tag
   */
  @Override
  public Id getTagId()
  {
    // id is cached for future calls
    if (isRunning() && tagId != null)
      return tagId;

    tagId = generateId(this, this::getDictionary);
    return tagId;
  }

  /**
   * Test whether this tag is an ideal match (see
   * {@link BTagRuleCondition#testIdealMatch(Type)}) for the specified type
   * using the {@link #validity} property.
   *
   * @param type {@code Type} to test
   * @return {@code true} if the tag is an ideal match for the specified type;
   * {@code false} otherwise
   */
  @Override
  public boolean isIdealFor(Type type)
  {
    return getValidity().testIdealMatch(type);
  }

  /**
   * Test whether this tag is valid (see
   * {@link BTagRuleCondition#test(Entity)}) for the specified entity using
   * the {@link #validity} property.
   *
   * @param entity {@code Entity} to test
   * @return {@code true} if the tag is valid for the specified entity;
   * {@code false} otherwise
   */
  @Override
  public boolean isValidFor(Entity entity)
  {
    return getValidity().test(entity);
  }

///////////////////////////////////////////////////////////
// BIRestrictedComponent
///////////////////////////////////////////////////////////

  /**
   * BTagInfos may only be added to a {@link BTagInfoList} located at a tag dictionary's
   * {@link BTagDictionary#tagDefinitions} property, a tag group info's
   * {@link BTagGroupInfo#tagList} property, and a tag rule's {@link BTagRule#tagList} property.
   * @since Niagara 4.4
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context context)
  {
    if (!(parent instanceof BTagInfoList))
      handleIllegalParent(parent, this, context);
  }

///////////////////////////////////////////////////////////
// Data policy
///////////////////////////////////////////////////////////

  /**
   * Get the DataPolicy associated with this tag.
   *
   * @return an {@code Optional} that contains the {@code DataPolicy} for this
   * tag, if it exists; an empty {@code Optional} otherwise.
   */
  @Override
  public Optional<DataPolicy> getDataPolicy()
  {
    BDataPolicy[] children = getChildren(BDataPolicy.class);
    if(children == null || children.length == 0)
      return Optional.empty();
    return Optional.of(children[0]);
  }

  /**
   * Disallows adding a {@link BDataPolicy} if one already exists or the tag is
   * of type {@link BMarker}.
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
    if (context != null && context.equals(Context.commit))
      return;

    if (value instanceof BDataPolicy)
    {
      if (getDataPolicy().isPresent())
        throw new LocalizableRuntimeException("tagdictionary", "tagGroupInfo.oneDataPolicy");

      if (getDefaultValue().getType().is(BMarker.TYPE))
        throw new LocalizableRuntimeException("tagdictionary", "tagInfo.dataPolicyNotValidOnMarkerTag");
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
      updateAddDataPolicyActionFlags();
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
      updateAddDataPolicyActionFlags();
  }

  private void updateAddDataPolicyActionFlags()
  {
    if (getDefaultValue().getType().is(BMarker.TYPE) || getDataPolicy().isPresent())
      setFlags(addDataPolicy, getFlags(addDataPolicy) | Flags.HIDDEN);
    else
      setFlags(addDataPolicy, getFlags(addDataPolicy) & ~Flags.HIDDEN);
  }

  /**
   * For the {@link #addDataPolicy} action slot, return a {@link BDynamicEnum}
   * set to zero with a range consisting of all concrete types of
   * {@link BDataPolicy}.
   *
   * @param action {@code Action} for which a default value is required
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
        tags[i] = SlotPath.escape(concreteTypes[i].getTypeSpec().toString());

      BEnumRange range = BEnumRange.make(tags);
      return BDynamicEnum.make(0, range);
    }
    else
      return super.getActionParameterDefault(action);
  }

  /**
   * Adds an instance of an object that extends {@link BDataPolicy} to the tag.
   * The specific type of {@code BDataPolicy} is stored in the value of the
   * specified {@code BDynamicEnum}. The new slot will be named "dp_" + the
   * component name of the tag.
   *
   * @param selected type that extends {@code BDataPolicy} to be added
   */
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
    if (!(obj instanceof BTagInfo))
      return false;

    try
    {
      updateAddDataPolicyActionFlags();
      ((BTagInfo)obj).updateAddDataPolicyActionFlags();
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

  private Id tagId;
}
