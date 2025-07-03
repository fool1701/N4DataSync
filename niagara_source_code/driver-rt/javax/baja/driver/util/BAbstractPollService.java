/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.util;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BAbstractPollService is the base class for services that poll
 * points, objects, or devices in a device network.
 *
 * @author    Craig Gemmill
 * @creation  31 Oct 2003
 * @version   $Revision: 3$ $Date: 6/22/10 1:28:16 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Enable the poll engine.
 */
@NiagaraProperty(
  name = "pollEnabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 Enable polling.
 */
@NiagaraAction(
  name = "enable"
)
/*
 Disable polling.
 */
@NiagaraAction(
  name = "disable"
)
public abstract class BAbstractPollService
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.util.BAbstractPollService(660687579)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "pollEnabled"

  /**
   * Slot for the {@code pollEnabled} property.
   * Enable the poll engine.
   * @see #getPollEnabled
   * @see #setPollEnabled
   */
  public static final Property pollEnabled = newProperty(0, true, null);

  /**
   * Get the {@code pollEnabled} property.
   * Enable the poll engine.
   * @see #pollEnabled
   */
  public boolean getPollEnabled() { return getBoolean(pollEnabled); }

  /**
   * Set the {@code pollEnabled} property.
   * Enable the poll engine.
   * @see #pollEnabled
   */
  public void setPollEnabled(boolean v) { setBoolean(pollEnabled, v, null); }

  //endregion Property "pollEnabled"

  //region Action "enable"

  /**
   * Slot for the {@code enable} action.
   * Enable polling.
   * @see #enable()
   */
  public static final Action enable = newAction(0, null);

  /**
   * Invoke the {@code enable} action.
   * Enable polling.
   * @see #enable
   */
  public void enable() { invoke(enable, null, null); }

  //endregion Action "enable"

  //region Action "disable"

  /**
   * Slot for the {@code disable} action.
   * Disable polling.
   * @see #disable()
   */
  public static final Action disable = newAction(0, null);

  /**
   * Invoke the {@code disable} action.
   * Disable polling.
   * @see #disable
   */
  public void disable() { invoke(disable, null, null); }

  //endregion Action "disable"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractPollService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /** Enable polling */
  public void doEnable() { setPollEnabled(true); }

  /** Disable polling */
  public void doDisable() { setPollEnabled(false); }


////////////////////////////////////////////////////////////////
// Subscriptions
////////////////////////////////////////////////////////////////

  /**
   * Subscribe the pollable and start polling it
   * until it is unsubscrbed.
   */
  abstract public void subscribe(BIPollable p);

  /**
   * Unsubscribe the pollable and stop polling it.
   * @return true if the pollable point was subscribed,
   *          false if the point was not in any buckets.
   */
  abstract public boolean unsubscribe(BIPollable p);


////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("pollService.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

}
