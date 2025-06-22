/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BComponentEvent encapsulates the information associated
 * with a modification to a BComponent.
 *
 * @author    Brian Frank
 * creation  21 Apr 01
 * @version   $Revision: 17$ $Date: 5/5/11 11:17:51 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public final class BComponentEvent
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BComponentEvent(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BComponentEvent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Ids
////////////////////////////////////////////////////////////////

  /**
   * This event id indicates that the specified
   * property's value has been modified.  The value
   * contains the new value of the property.
   */
  public static final int PROPERTY_CHANGED = 0;

  /**
   * This event id indicates that a new property
   * has been added to the component.  The value
   * contains the value of the new property.
   */
  public static final int PROPERTY_ADDED = 1;

  /**
   * This event id indicates that an existing
   * property was removed.  The value contains the old
   * object (in network proxies the old object is
   * not guaranteed to be fully loaded).
   */
  public static final int PROPERTY_REMOVED = 2;

  /**
   * This event id indicates that an existing
   * property was renamed.  The value is a BString
   * containing the old name.
   */
  public static final int PROPERTY_RENAMED = 3;

  /**
   * This event id indicates that the properties
   * have been reordered.
   */
  public static final int PROPERTIES_REORDERED = 4;

  /**
   * This event indicates that a topic was fired.
   */
  public static final int TOPIC_FIRED = 5;

  /**
   * This event indicates that a slot's flags were changed.
   */
  public static final int FLAGS_CHANGED = 6;

  /**
   * This event indicates that a slot's facets were changed.
   * The value is the old BFacets value.
   */
  public static final int FACETS_CHANGED = 7;

  /**
   * This event indicates that the a link knob has been
   * activated on the source component.
   */
  public static final int KNOB_ADDED = 8;

  /**
   * This event indicates that the a link knob has been
   * deactivated on the source component.
   */
  public static final int KNOB_REMOVED = 9;

  /**
   * This event indicates that the category mask has been changed.
   * The value contains the old BCategoryMask.
   */
  public static final int RECATEGORIZED = 10;

  /**
   * This event indicates that a component has been parented to
   * to a component running the station
   * @since Niagara 3.7
   */
  public static final int COMPONENT_PARENTED = 11;

  /**
   * This event indicates that a component has been unparented from
   * a component running in the station
   * @since Niagara 3.7
   */
  public static final int COMPONENT_UNPARENTED = 12;

  /**
   * This event id indicates that the component
   * was renamed in its parent.  The value is a BString
   * containing the old name.
   * @since Niagara 3.7
   */
  public static final int COMPONENT_RENAMED = 13;

  /**
   * This event id indicates that the component has
   * been reordered in its parent.
   * @since Niagara 3.7
   */
  public static final int COMPONENT_REORDERED = 14;

  /**
   * This event indicates that a components's flags were changed
   * in its parent.
   * @since Niagara 3.7
   */
  public static final int COMPONENT_FLAGS_CHANGED = 15;

  /**
   * This event indicates that a component's facets were changed
   * in its parent.  The value is the old BFacets value.
   * @since Niagara 3.7
   */
  public static final int COMPONENT_FACETS_CHANGED = 16;

  /**
   * This event indicates that the a relation knob has been
   * activated on the endpoint component.
   */
  public static final int RELATION_KNOB_ADDED = 17;

  /**
   * This event indicates that the a relation knob has been
   * deactivated on the endpoint component.
   */
  public static final int RELATION_KNOB_REMOVED = 18;

  /**
   * This event indicates that the component has been
   * started.  Note: It is only fired in the master space,
   * proxy spaces will not receive this component event.
   * @since Niagara 3.7
   */
  public static final int COMPONENT_STARTED = 19;

  /**
   * This event indicates that the component has been
   * stopped.  Note: It is only fired in the master space,
   * proxy spaces will not receive this component event.
   * @since Niagara 3.7
   */
  public static final int COMPONENT_STOPPED = 20;

  // If additional component events are added, the BComponentSpace class
  // must be updated with the new maximum component event id, so that the
  // subscribe(Type[] t, TypeSubscriber s) method can iterate through all
  // events.

  /**
   * Private String array for the id names.
   */
  // This is used by fox, so not as private as it appears!
  private static String[] ID_STRINGS =
  {
    "changed",
    "added",
    "removed",
    "renamed",
    "reordered",
    "topicFired",
    "flagsChanged",
    "facetsChanged",
    "knobAdded",
    "knobRemoved",
    "recategorized",
    "componentParented",
    "componentUnparented",
    "componentRenamed",
    "componentReordered",
    "componentFlagsChanged",
    "componentFacetsChanged",
    "componentKnobAdded",
    "componentKnobRemoved",
    "componentStarted",
    "componentStopped",
  };

  /**
   * Returns the array of ID Strings.
   *
   * @return The array of ID strings.
   * @since Niagara 4.0
   */
  public static String[] getIdStrings()
  {
    return ID_STRINGS;
  }

  /**
   * Private String array for the id names.
   */
  private static final String[] DISPLAY_STRINGS =
  {
    "property_changed",
    "property_added",
    "property_removed",
    "property_renamed",
    "property_reordered",
    "property_topic_fired",
    "property_flags_changed",
    "property_facets_changed",
    "property_knobAdded",
    "property_knobRemoved",
    "property_recategorized",
    "component_parented",
    "component_unparented",
    "component_renamed",
    "component_reordered",
    "component_flags_changed",
    "component_facets_changed",
    "component_knob_added",
    "component_knob_removed",
    "component_started",
    "component_stopped",
  };

  /**
   * Returns the array of display Strings.
   *
   * @return The array of display strings.
   * @since Niagara 4.0
   */
  public static String[] getDisplayStrings()
  {
    return DISPLAY_STRINGS;
  }

////////////////////////////////////////////////////////////////
// Creation
////////////////////////////////////////////////////////////////

  /**
   * Constructor for a local event.
   */
  public BComponentEvent(int id, BComponent component, Slot slot, BValue value)
  {
    this.id = id;
    this.component = component;

    if (slot != null)
    {
      slotName = slot.getName();
      slotFlags = component.getFlags(slot);
      this.slot = slot;
    }

    if (value != null)
    {
      this.value = value;
    }
  }

  /**
   * Constructor for a knob event.
   */
  public BComponentEvent(int id, BComponent component, Knob knob)
  {
    this.id = id;
    this.component = component;
    this.knob = knob;
    slot = knob.getSourceSlot();
    slotName = knob.getSourceSlotName();
  }

  /**
   * Constructor for a relation knob event.
   *
   * @since Niagara 4.0
   */
  public BComponentEvent(int id, BComponent component, RelationKnob rknob)
  {
    this.id = id;
    this.component = component;
    this.rknob = rknob;
  }

  /**
   * Public no arg constructor.  Framework use only!
   */
  public BComponentEvent()
  {
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the event id constant.
   */
  public int getId() { return id; }

  /**
   * Get the name of the slot for the event or
   * null if not applicable.
   */
  public String getSlotName() { return slotName; }

  /**
   * Get the flags of the slot for the event or
   * -1 if not applicable.
   */
  public int getSlotFlags() { return slotFlags; }

  /**
   * Get the BValue value of the event, or null if not
   * applicable.
   */
  public BValue getValue() { return value; }

  /**
   * Get the source component of the event.
   */
  public BComponent getSourceComponent()
  {
    return component;
  }

  /**
   * Get the slot of the event.
   */
  public Slot getSlot()
  {
    return slot;
  }

  /**
   * If the event id is KNOB_ADDED or KNOB_REMOVED, then
   * this returns the knob instance.
   */
  public RelationKnob getRelationKnob()
  {
    return rknob;
  }

  /**
   * If the event id is KNOB_ADDED or KNOB_REMOVED, then
   * this returns the knob instance.
   */
  public Knob getKnob()
  {
    return knob;
  }

  /**
   * To debug string.
   */
  @Override
  public String toString(Context cx)
  {
    String msg;
    if(knob!=null)
    {
      msg = knob.toString();
    }
    else if(rknob!=null)
    {
      msg = rknob.toString();
    }
    else
    {
      msg = '.' + slotName + "\": " + value;
    }
    return DISPLAY_STRINGS[id] + " \"" + (component!=null?component.toDebugString():"null") + msg;
  }

////////////////////////////////////////////////////////////////
//Object
////////////////////////////////////////////////////////////////

  @Override
  public Object clone()
  {
    BComponentEvent event = new BComponentEvent();
    event.id = id;
    event.slotName = slotName;
    event.slotFlags = slotFlags;
    event.value = value;
    event.component = component;
    event.slot = slot;
    event.knob = knob;
    event.rknob = rknob;
    return event;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private int id;
  private String slotName;
  private int slotFlags;
  private BValue value;
  private BComponent component;
  private Slot slot;
  private Knob knob;
  private RelationKnob rknob;

}
