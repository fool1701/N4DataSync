/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import static javax.baja.sys.LinkCheck.isInvalidSlotForLink;

import java.util.logging.Logger;

import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.metrics.Metrics;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.util.LinkUtil;

/**
 * BLink defines an event propagation relationship between
 * two slots of two components.  The target side is always
 * the link's parent and source side is a component-slot
 * pair which pushes events to the target.
 * <p>
 * Links are created directly or indirectly.  A direct link
 * has a direct Java reference to its source BComponent,
 * source slot, and target slot.  An indirect link uses
 * a BOrd to identify the source slot, and String names to
 * identity the source and target slots.  Resolution of
 * an indirect link occurs at activation time.  A direct
 * link must be explicitly deleted when no longer needed.
 * An indirect link will automatically be removed if the
 * source component is unmounted while the link is activiated.
 * <p>
 * By default a link is unactive and does not propagate
 * events.  A link is put into the active state by the
 * activate() method. While a link is active it has mirrored
 * Knob installed on the source component.  The mirrored Knob
 * is removed when the link is deactivated.
 * <p>
 * There can be six types of links:
 * <ul>
 *   <li>property -&gt; property</li>
 *   <li>property -&gt; action</li>
 *   <li>action -&gt; action</li>
 *   <li>action -&gt; topic</li>
 *   <li>topic -&gt; action</li>
 *   <li>topic -&gt; topic</li>
 * </ul>
 *
 * @author    Brian Frank
 * @creation  17 Oct 00
 * @version   $Revision: 47$ $Date: 8/3/10 1:04:04 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "relationId",
  type = "String",
  defaultValue = "n:dataLink",
  flags = Flags.READONLY | Flags.HIDDEN,
  override = true
)
/*
 Facets for the relation tags
 */
@NiagaraProperty(
  name = "relationTags",
  type = "BFacets",
  defaultValue = "BFacets.NULL",
  flags = Flags.READONLY | Flags.HIDDEN,
  override = true
)
@NiagaraProperty(
  name = "inbound",
  type = "boolean",
  defaultValue = "true",
  flags = Flags.HIDDEN | Flags.READONLY,
  override = true
)
/*
 Ord of the other entity.  Named sourceOrd for backward compatibility with BLink
 */
@NiagaraProperty(
  name = "sourceOrd",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  facets = @Facet(name = "BFacets.TARGET_TYPE", value = "\"baja:Component\""),
  override = true
)
@NiagaraProperty(
  name = "sourceSlotName",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "targetSlotName",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true"
)
public class BLink
  extends BRelation
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BLink(900818299)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "relationId"

  /**
   * Slot for the {@code relationId} property.
   * @see #getRelationId
   * @see #setRelationId
   */
  public static final Property relationId = newProperty(Flags.READONLY | Flags.HIDDEN, "n:dataLink", null);

  //endregion Property "relationId"

  //region Property "relationTags"

  /**
   * Slot for the {@code relationTags} property.
   * Facets for the relation tags
   * @see #getRelationTags
   * @see #setRelationTags
   */
  public static final Property relationTags = newProperty(Flags.READONLY | Flags.HIDDEN, BFacets.NULL, null);

  //endregion Property "relationTags"

  //region Property "inbound"

  /**
   * Slot for the {@code inbound} property.
   * @see #getInbound
   * @see #setInbound
   */
  public static final Property inbound = newProperty(Flags.HIDDEN | Flags.READONLY, true, null);

  //endregion Property "inbound"

  //region Property "sourceOrd"

  /**
   * Slot for the {@code sourceOrd} property.
   * Ord of the other entity.  Named sourceOrd for backward compatibility with BLink
   * @see #getSourceOrd
   * @see #setSourceOrd
   */
  public static final Property sourceOrd = newProperty(0, BOrd.NULL, BFacets.make(BFacets.TARGET_TYPE, "baja:Component"));

  //endregion Property "sourceOrd"

  //region Property "sourceSlotName"

  /**
   * Slot for the {@code sourceSlotName} property.
   * @see #getSourceSlotName
   * @see #setSourceSlotName
   */
  public static final Property sourceSlotName = newProperty(0, "", null);

  /**
   * Get the {@code sourceSlotName} property.
   * @see #sourceSlotName
   */
  public String getSourceSlotName() { return getString(sourceSlotName); }

  /**
   * Set the {@code sourceSlotName} property.
   * @see #sourceSlotName
   */
  public void setSourceSlotName(String v) { setString(sourceSlotName, v, null); }

  //endregion Property "sourceSlotName"

  //region Property "targetSlotName"

  /**
   * Slot for the {@code targetSlotName} property.
   * @see #getTargetSlotName
   * @see #setTargetSlotName
   */
  public static final Property targetSlotName = newProperty(0, "", null);

  /**
   * Get the {@code targetSlotName} property.
   * @see #targetSlotName
   */
  public String getTargetSlotName() { return getString(targetSlotName); }

  /**
   * Set the {@code targetSlotName} property.
   * @see #targetSlotName
   */
  public void setTargetSlotName(String v) { setString(targetSlotName, v, null); }

  //endregion Property "targetSlotName"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLink.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct an indirect BLink.
   */
  public BLink(BOrd sourceOrd, String sourceSlot, String targetSlot, boolean enabled)
  {
    setSourceOrd(sourceOrd);
    setSourceSlotName(sourceSlot);
    setTargetSlotName(targetSlot);
    setEnabled(enabled);
  }

  /**
   * Construct a direct BLink.
   */
  public BLink(BComponent source, Slot sourceSlot, Slot targetSlot)
  {
    if (source.getSlot(sourceSlot.getName()) == null)
      log.warning("No Such Slot: " + source.getType() + "." + sourceSlot.getName());

    direct = source;
    setSourceSlotName(sourceSlot.getName());
    setTargetSlotName(targetSlot.getName());
    this.sourceSlot = sourceSlot;
    this.targetSlot = targetSlot;
  }

  /**
   * Default no argument
   */
  public BLink()
  {
  }

  public final boolean isEnabled() { return getBoolean(enabled); }

////////////////////////////////////////////////////////////////
// Derived Properties
////////////////////////////////////////////////////////////////


  /**
   * If this is a direct link then return the source
   * BComponent.  If this is an indirect link then
   * return the resolved value of getSourceOrd().
   *
   * @throws UnresolvedException if the link is indirect
   *    and inactive.
   */
  public final BComponent getSourceComponent()
  {
    if (direct != null) return direct;
    if (!active) throw new UnresolvedException();
    return indirect;
  }

  /**
   * The source slot being monitored on the source object.
   *
   * @throws UnresolvedException if the link is indirect
   *    and inactive.
   */
  public final Slot getSourceSlot()
  {
    if (direct != null) return sourceSlot;
    if (!active) throw new UnresolvedException();
    return sourceSlot;
  }

  /**
   * The target object is always the same as the
   * link's parent object.
   */
  public final BComponent getTargetComponent()
  {
    return (BComponent)getParent();
  }

  /**
   * Get the resolved target slot.
   *
   * @throws UnresolvedException if the link is indirect
   *    and inactive.
   */
  public final Slot getTargetSlot()
  {
    if (direct != null) return targetSlot;
    if (!active) throw new UnresolvedException();
    return targetSlot;
  }

////////////////////////////////////////////////////////////////
// Activation
////////////////////////////////////////////////////////////////

  /**
   * Return true if the link is currently activated.
   */
  public final boolean isActive()
  {
    return active;
  }

  /**
   * Activate the link:
   * <ul>
   * <li>If the link is indirect, then attempt to resolve it.</li>
   * <li>Install a Knob on the source side as this link's mirror.</li>
   * <li>If prop to prop, then do an initial propagation.</li>
   * <li>Begin propagation of events from the source slot to
   *     the target slot.</li>
   * </ul>
   *
   * @throws IllegalStateException if the component is not
   *   mounted as a property of a BComponent.
   * @throws UnresolvedException if the link is indirect
   *   and the source component, source slot, or target
   *   slot cannot be resolved.
   */
  public final void activate()
  {
    // increment metrics (but only inside station, that way
    // GUI apps will still work).
    if (runningInStation())
    {
      if (!Metrics.incrementLink())
      {
        fatalFault = true;
        log.severe("Exceeded link limit for globalCapacity");
      }
    }

    ////////////////////////////////////////////////////////////////

    // if already active short circuit
    if (active) return;

    // get target which is my parent
    BComponent target = (BComponent)getParent();
    if (target == null) throw new IllegalStateException("not mounted in component");

    // resolve source
    BComponent source = isDirect() ? direct : resolve();

    // link target or source can not be a BPassword or BPermissionsMap or the BUser "roles" property, etc.
    Slot targetSlot = target.getSlot(getTargetSlotName());
    if(targetSlot != null && isInvalidSlotForLink(target, targetSlot, BIUnlinkableTarget.TYPE, null))
    {
      String propType = targetSlot.isProperty() ? " (" + targetSlot.asProperty().getType() + ')' : "";
      log.warning("Link target is not allowed: " +
        target.toPathString() + '/' + targetSlot.getName() + propType);

      // When it's invalid, remove the link
      if (getParent().isComponent())
      {
        getParent().asComponent().remove(getPropertyInParent());
      }
      return;
    }
    Slot sourceSlot = source.getSlot(getSourceSlotName());
    if(sourceSlot != null && isInvalidSlotForLink(source, sourceSlot, BIUnlinkableSource.TYPE, null))
    {
      String propType = sourceSlot.isProperty() ? " (" + sourceSlot.asProperty().getType() + ')' : "";
      log.warning("Link source is not allowed: " +
        source.toPathString() + '/' + sourceSlot.getName() + propType);

      // When it's invalid, remove the link
      if (getParent().isComponent())
      {
        getParent().asComponent().remove(getPropertyInParent());
      }
      return;
    }

    // establish Knob source side and set active
    try
    {
      active = true; // this lets us get methods safely
      knob = ((ComponentSlotMap)source.getSlotMap()).installKnob(this);
    }
    catch(RuntimeException e)
    {
      active = false;
      throw e;
    }
    finally
    {
      if (active)
      {
        try
        { // We just activated a link to a target, so set the linkTarget flag on the target slot
          target.setFlags(targetSlot, target.getFlags(targetSlot) | Flags.LINK_TARGET, SKIP_CRITICAL_CX);
        } catch (Exception x) { x.printStackTrace(); }
      }
      else // if failed to activate, recompute the slot flags for the target slot
        recomputeSlotFlags(target, targetSlot);
    }

    // initial propagation
    if (sourceSlot.isProperty() && targetSlot.isProperty())
    {
      propagate(null);
    }
  }

  /**
   * Deactivate the link:
   * <ul>
   * <li>Terminate propagation of events from the source slot
   *     to the target slot.</li>
   * <li>Uninstall the Knob on the source side which mirror's
   *     this link.</li>
   * </ul>
   */
  public final void deactivate()
  {                   
    // if not active short circuit
    if (!active) return;

    BComponent targetComp = null;
    Slot tSlot = null;
    try
    {
      try { tSlot = getTargetSlot(); } catch(Exception e) {}
      if (active)
      {
        ((ComponentSlotMap)getSourceComponent().getSlotMap()).uninstallKnob(this);
        targetComp = getTargetComponent();
        ((ComponentSlotMap)targetComp.getSlotMap()).deactivating(this);
      }
    }
    finally
    {
      active     = false;
      knob       = null;
      if (indirect != null)
      {
        indirect   = null;
        sourceSlot = null;
        targetSlot = null;
      }

      // Whenever a link is deactivated, we need to recompute the slot
      // flags for the former target slot to check to see if the
      // linkTarget flag should be cleared.  It is possible that the
      // target slot is the target of multiple links, so that's why
      // we have to perform a full check.
      recomputeSlotFlags(targetComp, tSlot);
    }
  }

  /**
   * Convenience method to recompute the slot flags
   * for the given parent component and child slot.
   * If we detect that the slot is the target of an
   * active link, then the 'linkTarget' slot flag should
   * be set.  If not, the 'linkTarget' slot
   * flag should be removed.  If the slot is null, then
   * all of the slots for the given component will be
   * recomputed.
   *
   * @since Niagara 3.6
   */
  private void recomputeSlotFlags(BComponent comp, Slot slot)
  {
    if (comp == null) return; // nothing to do
    try
    {
      if (slot != null)
        computeLinkTargetFlag(comp, slot);
      else
      { // Check all slots on the component
        SlotCursor<Slot> c = comp.getSlots();
        while(c.nextObject())
          computeLinkTargetFlag(comp, c.slot());
      }
    }
    catch(Throwable t)
    {
      t.printStackTrace();
    }
  }

  /**
   * Convenience method to compute the linkTarget slot flag
   * for the given parent component and child slot.
   * If we detect that the slot is the target of an
   * active link, then the 'linkTarget' slot flag should
   * be set.  If not, the 'linkTarget' slot
   * flag should be removed.
   *
   * @since Niagara 3.6
   */
  private void computeLinkTargetFlag(BComponent comp, Slot slot)
  {
    try
    {
      // Look for any active links that target
      // the given slot
      int flags = comp.getFlags(slot);
      BLink[] links = comp.getLinks(slot);
      int len = (links != null)?links.length:0;
      boolean activeLinkFound = false;
      for (int i = 0; i < len; i++)
      {
        if (links[i].isActive())
        { // Found one!
          activeLinkFound = true;
          break;
        }
      }

      if (activeLinkFound)
        comp.setFlags(slot, flags | Flags.LINK_TARGET, SKIP_CRITICAL_CX); // set the linkTarget flag
      else
        comp.setFlags(slot, flags & ~Flags.LINK_TARGET, SKIP_CRITICAL_CX); // clear the linkTarget flag
    }
    catch(Throwable th)
    {
      th.printStackTrace();
    }
  }

  /**
   * Get the link's mirror Knob on the source side.
   *
   * @throws UnresolvedException if link is not active.
   */
  public Knob getKnob()
  {
    if (!active) throw new UnresolvedException();
    return knob;
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * Resolve an indirect link and return source component.
   */
  private BComponent resolve()
  {
    indirect = null;
    try
    {

      BObject obj = getSourceOrd().resolve(getParentComponent()).get();
      if(!(obj instanceof BComponent))
        throw new BajaRuntimeException("Source ord must resolve to a component");
      indirect = (BComponent)obj;
    }
    catch(UnresolvedException e)
    {
      throw new UnresolvedException("Cannot resolve source component");
    }

    sourceSlot = indirect.getSlot( getSourceSlotName() );
    if (sourceSlot == null)
      throw new UnresolvedException("Source slot does not exist");

    targetSlot = getParent().getSlot( getTargetSlotName() );
    if (targetSlot == null)
      throw new UnresolvedException("Target slot does not exist");

    return indirect;
  }

  /**
   * To string.
   */
  @Override
  public String toString(Context context)
  {
    String t = "null";
    BComponent tc = getTargetComponent();
    if (tc != null)
    {
      SlotPath tp = tc.getSlotPath();
      if (tp == null) t = tc.toString();
      else t = tp.toString();
    }

    String s;
    if (direct != null)
    {
      s = "Direct: " + direct.toDebugString();
    }
    else
    {
      s = "Indirect: " + getSourceOrd();
    }
    return LinkUtil.linkText(s + "." + getSourceSlotName(), t + "." + getTargetSlotName(), context);
  }

////////////////////////////////////////////////////////////////
// Propogation
////////////////////////////////////////////////////////////////

  /**
   * This method is called when an event occurs on the
   * source slot.  The link should propagate the change
   * across the link.  By default this routes to one of
   * the propagateXtoX() methods.
   *
   * @param arg if the source slot is a topic, this is
   *    the event passed to fire.  Otherwise this is value
   *    is null.
   */
  public void propagate(BValue arg)
  {
    if (fatalFault) return;

    // short circuit if sourceSlot and targetSlot
    // are same slot on same parent
    if (getTargetComponent() == getSourceComponent() &&
        getTargetSlot() == getSourceSlot())
    {
      System.out.println("WARNING: Infinite link: " + this);
      return;
    }

    if (sourceSlot.isAction())
    {
      if (targetSlot.isAction()) { propagateActionToAction(arg); return; }
      if (targetSlot.isTopic()) { propagateActionToTopic(arg); return; }
    }
    else if (sourceSlot.isTopic())
    {
      if (targetSlot.isAction()) { propagateTopicToAction(arg); return; }
      if (targetSlot.isTopic()) { propagateTopicToTopic(arg); return; }
    }
    else if (sourceSlot.isProperty()) // must be last
    {
      if (targetSlot.isAction()) { propagatePropertyToAction(); return; }
      if (targetSlot.isProperty()) { propagatePropertyToProperty(); return; }
    }
    throw new IllegalStateException("Invalid link config");
  }

  /**
   * Propagate a property to action link.  The default
   * implementation invokes the action.  If the action takes
   * an argument, then the current value of the source
   * property is passed.
   */
  public void propagatePropertyToAction()
  {
    if (fatalFault) return;

    Action targetAction = getTargetSlot().asAction();

    // get the argument as the new property
    // value only if needed for the action
    BValue arg = null;
    if (targetAction.getParameterType() != null)
      arg = getSourceComponent().get(getSourceSlot().asProperty());

    // invoke the action on the target
    getTargetComponent().invoke(targetAction, arg);
  }

  /**
   * Push the source property to the source property.  This
   * method is automatically called by the framework when the
   * source property is modified.  The default implementation
   * performs a copyFrom on the target property using the
   * source property.
   */
  public void propagatePropertyToProperty()
  {
    if (fatalFault) return;

    BComponent s = getSourceComponent();
    BComponent t = getTargetComponent();
    Property sProp = sourceSlot.asProperty();
    Property tProp = targetSlot.asProperty();

//System.out.println("pull: " + s + "." + sProp + " -> " + t + "." + tProp);

    switch(sProp.getTypeAccess())
    {
      case Slot.BOOLEAN_TYPE:
        t.setBoolean( tProp, s.getBoolean(sProp), null);
        break;
      case Slot.INT_TYPE:
        t.setInt( tProp, s.getInt(sProp), null);
        break;
      case Slot.LONG_TYPE:
        t.setLong( tProp, s.getLong(sProp), null);
        break;
      case Slot.FLOAT_TYPE:
        t.setFloat( tProp, s.getFloat(sProp), null);
        break;
      case Slot.DOUBLE_TYPE:
        t.setDouble( tProp, s.getDouble(sProp), null);
        break;
      case Slot.STRING_TYPE:
        t.setString( tProp, s.getString(sProp), null);
        break;
      case Slot.BOBJECT_TYPE:
        BValue value = s.get(sProp);
        if (value.isSimple())
          t.set(tProp, value, null);
        else
          t.get(tProp).asComplex().copyFrom(value.asComplex());
        break;
      default:
        throw new IllegalStateException();
    }
  }

  /**
   * Propagate a action to action link.  The default
   * implementation invokes the action.  If the action
   * takes an argument, then the arg is passed through.
   */
  public void propagateActionToAction(BValue arg)
  {
    if (fatalFault) return;

    Action targetAction = getTargetSlot().asAction();
    getTargetComponent().invoke(targetAction, arg);
  }

  /**
   * Propagate a action to topic link.  The default
   * implementation fires the target topic using
   * the event used to fire the source topic.
   */
  public void propagateActionToTopic(BValue event)
  {
    if (fatalFault) return;

    Topic targetTopic = getTargetSlot().asTopic();
    getTargetComponent().fire(targetTopic, event);
  }

  /**
   * Propagate a topic to action link.  The default
   * implementation invokes the action.  If the action
   * takes an argument, then the event is passed.
   */
  public void propagateTopicToAction(BValue event)
  {
    if (fatalFault) return;

    Action targetAction = getTargetSlot().asAction();

    BValue arg = null;
    if (targetAction.getParameterType() != null)
      arg = event;

    // invoke the action on the target
    getTargetComponent().invoke(targetAction, arg);
  }

  /**
   * Propagate a topic to topic link.  The default
   * implementation fires the target topic using
   * the event used to fire the source topic.
   */
  public void propagateTopicToTopic(BValue event)
  {
    if (fatalFault) return;

    Topic targetTopic = getTargetSlot().asTopic();
    getTargetComponent().fire(targetTopic, event);
  }

  /**
   * Return whether this link is in fatal fault.  If it
   * is, then the link will not propogate.
   */
  public boolean isFatalFault()
  {
    return fatalFault;
  }

  /**
   * Return whether or not we are running inside of a station.
   */
  private static boolean runningInStation()
  {
    return Sys.getStation() != null;
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("link.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final Logger log = Logger.getLogger("sys.link");
  private static final Context SKIP_CRITICAL_CX = com.tridium.dataRecovery.BDataRecoveryComponentRecorder.SKIP_CRITICAL_CX;

//  private BComponent direct;
//  private BComponent indirect;
  private Slot sourceSlot;
  private Slot targetSlot;
  private boolean active;
  private Knob knob;

  private boolean fatalFault = false;
}
