/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.point;

import javax.baja.bacnet.*;
import javax.baja.driver.point.*;
import javax.baja.driver.util.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.units.*;

/**
 * BBacnetTuningPolicy.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 08 Jul 2004
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "pollFrequency",
  type = "BPollFrequency",
  defaultValue = "BPollFrequency.normal"
)
/*
 flag indicating if Niagara will use COV notification services to
 receive data about points in this device for which COV is supported.
 */
@NiagaraProperty(
  name = "useCov",
  type = "boolean",
  defaultValue = "false"
)
/*
 flag indicating if Niagara will request confirmed (true)
 or unconfirmed (false) COV notifications.
 */
@NiagaraProperty(
  name = "useConfirmedCov",
  type = "boolean",
  defaultValue = "true"
)
/*
 the lifetime, in minutes, for which Niagara will subscribe for COV
 notifications.  A value of zero means an indefinite lifetime,
 although this is not guaranteed to persist across resets of the
 server device.
 */
@NiagaraProperty(
  name = "covSubscriptionLifetime",
  type = "int",
  defaultValue = "15",
  facets = @Facet("BFacets.makeInt(UnitDatabase.getUnit(\"minute\"))")
)
/*
 flag indicating if Niagara will use COV Property notification services to
 receive data about points in this device for which COV Property is supported.
 */
@NiagaraProperty(
  name = "useCovProperty",
  type = "boolean",
  defaultValue = "false"
)
/*
 flag indicating if Niagara will request confirmed (true)
 or unconfirmed (false) COV notifications on COV Property request.
 */
@NiagaraProperty(
  name = "useConfirmedCovProperty",
  type = "boolean",
  defaultValue = "true"
)
/*
 COV notifications to send Cov Increment
 this property is be applicable for a COV Property of type numeric
 */
@NiagaraProperty(
  name = "covPropertyIncrement",
  type = "double",
  defaultValue = "1.0"
)
/*
 the lifetime, in minutes, for which Niagara will subscribe COV Property for COV
 notifications.  A value of zero means an indefinite lifetime,
 although this is not guaranteed to persist across resets of the
 server device.
 */
@NiagaraProperty(
  name = "covPropertySubscriptionLifetime",
  type = "int",
  defaultValue = "15",
  facets = @Facet("BFacets.makeInt(UnitDatabase.getUnit(\"minute\"))")
)
/*
 if true, will allow cov notifications for a polled point to update the point
 */
@NiagaraProperty(
  name = "acceptUnsolicitedCov",
  type = "boolean",
  defaultValue = "false"
)
public class BBacnetTuningPolicy
  extends BTuningPolicy
{


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.point.BBacnetTuningPolicy(951541754)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "pollFrequency"

  /**
   * Slot for the {@code pollFrequency} property.
   * @see #getPollFrequency
   * @see #setPollFrequency
   */
  public static final Property pollFrequency = newProperty(0, BPollFrequency.normal, null);

  /**
   * Get the {@code pollFrequency} property.
   * @see #pollFrequency
   */
  public BPollFrequency getPollFrequency() { return (BPollFrequency)get(pollFrequency); }

  /**
   * Set the {@code pollFrequency} property.
   * @see #pollFrequency
   */
  public void setPollFrequency(BPollFrequency v) { set(pollFrequency, v, null); }

  //endregion Property "pollFrequency"

  //region Property "useCov"

  /**
   * Slot for the {@code useCov} property.
   * flag indicating if Niagara will use COV notification services to
   * receive data about points in this device for which COV is supported.
   * @see #getUseCov
   * @see #setUseCov
   */
  public static final Property useCov = newProperty(0, false, null);

  /**
   * Get the {@code useCov} property.
   * flag indicating if Niagara will use COV notification services to
   * receive data about points in this device for which COV is supported.
   * @see #useCov
   */
  public boolean getUseCov() { return getBoolean(useCov); }

  /**
   * Set the {@code useCov} property.
   * flag indicating if Niagara will use COV notification services to
   * receive data about points in this device for which COV is supported.
   * @see #useCov
   */
  public void setUseCov(boolean v) { setBoolean(useCov, v, null); }

  //endregion Property "useCov"

  //region Property "useConfirmedCov"

  /**
   * Slot for the {@code useConfirmedCov} property.
   * flag indicating if Niagara will request confirmed (true)
   * or unconfirmed (false) COV notifications.
   * @see #getUseConfirmedCov
   * @see #setUseConfirmedCov
   */
  public static final Property useConfirmedCov = newProperty(0, true, null);

  /**
   * Get the {@code useConfirmedCov} property.
   * flag indicating if Niagara will request confirmed (true)
   * or unconfirmed (false) COV notifications.
   * @see #useConfirmedCov
   */
  public boolean getUseConfirmedCov() { return getBoolean(useConfirmedCov); }

  /**
   * Set the {@code useConfirmedCov} property.
   * flag indicating if Niagara will request confirmed (true)
   * or unconfirmed (false) COV notifications.
   * @see #useConfirmedCov
   */
  public void setUseConfirmedCov(boolean v) { setBoolean(useConfirmedCov, v, null); }

  //endregion Property "useConfirmedCov"

  //region Property "covSubscriptionLifetime"

  /**
   * Slot for the {@code covSubscriptionLifetime} property.
   * the lifetime, in minutes, for which Niagara will subscribe for COV
   * notifications.  A value of zero means an indefinite lifetime,
   * although this is not guaranteed to persist across resets of the
   * server device.
   * @see #getCovSubscriptionLifetime
   * @see #setCovSubscriptionLifetime
   */
  public static final Property covSubscriptionLifetime = newProperty(0, 15, BFacets.makeInt(UnitDatabase.getUnit("minute")));

  /**
   * Get the {@code covSubscriptionLifetime} property.
   * the lifetime, in minutes, for which Niagara will subscribe for COV
   * notifications.  A value of zero means an indefinite lifetime,
   * although this is not guaranteed to persist across resets of the
   * server device.
   * @see #covSubscriptionLifetime
   */
  public int getCovSubscriptionLifetime() { return getInt(covSubscriptionLifetime); }

  /**
   * Set the {@code covSubscriptionLifetime} property.
   * the lifetime, in minutes, for which Niagara will subscribe for COV
   * notifications.  A value of zero means an indefinite lifetime,
   * although this is not guaranteed to persist across resets of the
   * server device.
   * @see #covSubscriptionLifetime
   */
  public void setCovSubscriptionLifetime(int v) { setInt(covSubscriptionLifetime, v, null); }

  //endregion Property "covSubscriptionLifetime"

  //region Property "useCovProperty"

  /**
   * Slot for the {@code useCovProperty} property.
   * flag indicating if Niagara will use COV Property notification services to
   * receive data about points in this device for which COV Property is supported.
   * @see #getUseCovProperty
   * @see #setUseCovProperty
   */
  public static final Property useCovProperty = newProperty(0, false, null);

  /**
   * Get the {@code useCovProperty} property.
   * flag indicating if Niagara will use COV Property notification services to
   * receive data about points in this device for which COV Property is supported.
   * @see #useCovProperty
   */
  public boolean getUseCovProperty() { return getBoolean(useCovProperty); }

  /**
   * Set the {@code useCovProperty} property.
   * flag indicating if Niagara will use COV Property notification services to
   * receive data about points in this device for which COV Property is supported.
   * @see #useCovProperty
   */
  public void setUseCovProperty(boolean v) { setBoolean(useCovProperty, v, null); }

  //endregion Property "useCovProperty"

  //region Property "useConfirmedCovProperty"

  /**
   * Slot for the {@code useConfirmedCovProperty} property.
   * flag indicating if Niagara will request confirmed (true)
   * or unconfirmed (false) COV notifications on COV Property request.
   * @see #getUseConfirmedCovProperty
   * @see #setUseConfirmedCovProperty
   */
  public static final Property useConfirmedCovProperty = newProperty(0, true, null);

  /**
   * Get the {@code useConfirmedCovProperty} property.
   * flag indicating if Niagara will request confirmed (true)
   * or unconfirmed (false) COV notifications on COV Property request.
   * @see #useConfirmedCovProperty
   */
  public boolean getUseConfirmedCovProperty() { return getBoolean(useConfirmedCovProperty); }

  /**
   * Set the {@code useConfirmedCovProperty} property.
   * flag indicating if Niagara will request confirmed (true)
   * or unconfirmed (false) COV notifications on COV Property request.
   * @see #useConfirmedCovProperty
   */
  public void setUseConfirmedCovProperty(boolean v) { setBoolean(useConfirmedCovProperty, v, null); }

  //endregion Property "useConfirmedCovProperty"

  //region Property "covPropertyIncrement"

  /**
   * Slot for the {@code covPropertyIncrement} property.
   * COV notifications to send Cov Increment
   * this property is be applicable for a COV Property of type numeric
   * @see #getCovPropertyIncrement
   * @see #setCovPropertyIncrement
   */
  public static final Property covPropertyIncrement = newProperty(0, 1.0, null);

  /**
   * Get the {@code covPropertyIncrement} property.
   * COV notifications to send Cov Increment
   * this property is be applicable for a COV Property of type numeric
   * @see #covPropertyIncrement
   */
  public double getCovPropertyIncrement() { return getDouble(covPropertyIncrement); }

  /**
   * Set the {@code covPropertyIncrement} property.
   * COV notifications to send Cov Increment
   * this property is be applicable for a COV Property of type numeric
   * @see #covPropertyIncrement
   */
  public void setCovPropertyIncrement(double v) { setDouble(covPropertyIncrement, v, null); }

  //endregion Property "covPropertyIncrement"

  //region Property "covPropertySubscriptionLifetime"

  /**
   * Slot for the {@code covPropertySubscriptionLifetime} property.
   * the lifetime, in minutes, for which Niagara will subscribe COV Property for COV
   * notifications.  A value of zero means an indefinite lifetime,
   * although this is not guaranteed to persist across resets of the
   * server device.
   * @see #getCovPropertySubscriptionLifetime
   * @see #setCovPropertySubscriptionLifetime
   */
  public static final Property covPropertySubscriptionLifetime = newProperty(0, 15, BFacets.makeInt(UnitDatabase.getUnit("minute")));

  /**
   * Get the {@code covPropertySubscriptionLifetime} property.
   * the lifetime, in minutes, for which Niagara will subscribe COV Property for COV
   * notifications.  A value of zero means an indefinite lifetime,
   * although this is not guaranteed to persist across resets of the
   * server device.
   * @see #covPropertySubscriptionLifetime
   */
  public int getCovPropertySubscriptionLifetime() { return getInt(covPropertySubscriptionLifetime); }

  /**
   * Set the {@code covPropertySubscriptionLifetime} property.
   * the lifetime, in minutes, for which Niagara will subscribe COV Property for COV
   * notifications.  A value of zero means an indefinite lifetime,
   * although this is not guaranteed to persist across resets of the
   * server device.
   * @see #covPropertySubscriptionLifetime
   */
  public void setCovPropertySubscriptionLifetime(int v) { setInt(covPropertySubscriptionLifetime, v, null); }

  //endregion Property "covPropertySubscriptionLifetime"

  //region Property "acceptUnsolicitedCov"

  /**
   * Slot for the {@code acceptUnsolicitedCov} property.
   * if true, will allow cov notifications for a polled point to update the point
   * @see #getAcceptUnsolicitedCov
   * @see #setAcceptUnsolicitedCov
   */
  public static final Property acceptUnsolicitedCov = newProperty(0, false, null);

  /**
   * Get the {@code acceptUnsolicitedCov} property.
   * if true, will allow cov notifications for a polled point to update the point
   * @see #acceptUnsolicitedCov
   */
  public boolean getAcceptUnsolicitedCov() { return getBoolean(acceptUnsolicitedCov); }

  /**
   * Set the {@code acceptUnsolicitedCov} property.
   * if true, will allow cov notifications for a polled point to update the point
   * @see #acceptUnsolicitedCov
   */
  public void setAcceptUnsolicitedCov(boolean v) { setBoolean(acceptUnsolicitedCov, v, null); }

  //endregion Property "acceptUnsolicitedCov"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetTuningPolicy.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetTuningPolicy()
  {
  }


////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  /**
   * Can only go in a BacnetTuningPolicyMap.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BBacnetTuningPolicyMap;
  }

  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning()) return;
    if (p.equals(useCov) || p.equals(useCovProperty))
    {
      ((BBacnetNetwork)((BBacnetTuningPolicyMap)getParent()).getNetwork())
        .tuningChanged(this, cx);
    }
  }
}
