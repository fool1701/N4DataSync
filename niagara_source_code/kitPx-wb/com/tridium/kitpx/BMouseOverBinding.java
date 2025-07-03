/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitpx;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BFacets;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BValueBinding;
import javax.baja.ui.BWidget;
import javax.baja.ui.enums.BDegradeBehavior;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.util.BFormat;

/**
 * BMouseOverBinding
 * 
 * This binding is used to allow animating widgets on mouse enter / mouse exit events for widgets
 * 
 * @author    Danesh Kamal
 * @creation  June 15 2016
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "bajaui:Widget"
  )
)
/*
 Specifies the ord of the binding target.
 */
@NiagaraProperty(
  name = "ord",
  type = "BOrd",
  defaultValue = "BOrd.make(\"local:\")",
  flags = Flags.HIDDEN,
  override = true
)
/*
 Specifies the behavior for when the binding
 ord cannot be resolved or used due to security
 permissions.
 */
@NiagaraProperty(
  name = "degradeBehavior",
  type = "BDegradeBehavior",
  defaultValue = "BDegradeBehavior.none",
  flags = Flags.HIDDEN,
  override = true
)
/*
 If this ord is non-null then clicking inside the
 bound widget will perform a hyperlink.
 */
@NiagaraProperty(
  name = "hyperlink",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  flags = Flags.HIDDEN,
  override = true
)
/*
 This property is used to format the summary string
 which is displayed in the status bar on mouse over.
 */
@NiagaraProperty(
  name = "summary",
  type = "BFormat",
  defaultValue = "BFormat.make(\"%displayName?typeDisplayName% = %.%\")",
  flags = Flags.HIDDEN,
  override = true
)
/*
 If this property is true and this binding is bound
 to a component, a popup menu is displayed to invoke
 the actions.  If false this feature is disabled.
 */
@NiagaraProperty(
  name = "popupEnabled",
  type = "boolean",
  defaultValue = "true",
  flags = Flags.HIDDEN,
  override = true
)
/*
 Fired whenever there's an event on the associated Widget
 */
@NiagaraAction(
  name = "mouseEvent",
  parameterType = "BMouseEvent",
  defaultValue = "new BMouseEvent()"
)
public final class BMouseOverBinding
  extends BValueBinding
{


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BMouseOverBinding(1448877552)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ord"

  /**
   * Slot for the {@code ord} property.
   * Specifies the ord of the binding target.
   * @see #getOrd
   * @see #setOrd
   */
  public static final Property ord = newProperty(Flags.HIDDEN, BOrd.make("local:"), null);

  //endregion Property "ord"

  //region Property "degradeBehavior"

  /**
   * Slot for the {@code degradeBehavior} property.
   * Specifies the behavior for when the binding
   * ord cannot be resolved or used due to security
   * permissions.
   * @see #getDegradeBehavior
   * @see #setDegradeBehavior
   */
  public static final Property degradeBehavior = newProperty(Flags.HIDDEN, BDegradeBehavior.none, null);

  //endregion Property "degradeBehavior"

  //region Property "hyperlink"

  /**
   * Slot for the {@code hyperlink} property.
   * If this ord is non-null then clicking inside the
   * bound widget will perform a hyperlink.
   * @see #getHyperlink
   * @see #setHyperlink
   */
  public static final Property hyperlink = newProperty(Flags.HIDDEN, BOrd.NULL, null);

  //endregion Property "hyperlink"

  //region Property "summary"

  /**
   * Slot for the {@code summary} property.
   * This property is used to format the summary string
   * which is displayed in the status bar on mouse over.
   * @see #getSummary
   * @see #setSummary
   */
  public static final Property summary = newProperty(Flags.HIDDEN, BFormat.make("%displayName?typeDisplayName% = %.%"), null);

  //endregion Property "summary"

  //region Property "popupEnabled"

  /**
   * Slot for the {@code popupEnabled} property.
   * If this property is true and this binding is bound
   * to a component, a popup menu is displayed to invoke
   * the actions.  If false this feature is disabled.
   * @see #getPopupEnabled
   * @see #setPopupEnabled
   */
  public static final Property popupEnabled = newProperty(Flags.HIDDEN, true, null);

  //endregion Property "popupEnabled"

  //region Action "mouseEvent"

  /**
   * Slot for the {@code mouseEvent} action.
   * Fired whenever there's an event on the associated Widget
   * @see #mouseEvent(BMouseEvent parameter)
   */
  public static final Action mouseEvent = newAction(0, new BMouseEvent(), null);

  /**
   * Invoke the {@code mouseEvent} action.
   * Fired whenever there's an event on the associated Widget
   * @see #mouseEvent
   */
  public void mouseEvent(BMouseEvent parameter) { invoke(mouseEvent, parameter, null); }

  //endregion Action "mouseEvent"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMouseOverBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
// Component
////////////////////////////////////////////////////////////////

  public void started()
  {
    super.started();
    
    // Issue 15791. Receive mouse events from Widget regardless of whether other 
    // bindings have consumed them
    if (getWidget() != null)
      linkTo(getWidget(), BWidget.mouseEvent, mouseEvent);
  }
  
////////////////////////////////////////////////////////////////
// Mouse Eventing
////////////////////////////////////////////////////////////////

  public void doMouseEvent(BMouseEvent event)
  {
    if (!getWidget().isEnabled())
      return;
        
    switch(event.getId())
    {  
      case BMouseEvent.MOUSE_ENTERED:  entered(event); break;
      case BMouseEvent.MOUSE_EXITED:   exited(event); break;
    } 
  }
  
  private void entered(BMouseEvent event)
  {
    active = true;
    //ensure widget updates by calling targetChanges directly
    targetChanged();
  }
  
  private void exited(BMouseEvent event)
  {
    active = false;
    //ensure widget updates by calling targetChanges directly
    targetChanged();
  }

  @Override
  protected Context getConverterContext()
  {
    return new BasicContext(super.getConverterContext(), BFacets.make("active", active));
  }

  ////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private boolean active = false;
}
