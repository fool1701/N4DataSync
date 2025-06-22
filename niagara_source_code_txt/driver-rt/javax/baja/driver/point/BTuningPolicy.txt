/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.point;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BTuningPolicy stores configuration to determine how 
 * and when proxy points are read and written.
 *
 * @author    Brian Frank       
 * @creation  23 Jun 04
 * @version   $Revision: 9$ $Date: 3/29/06 4:24:56 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Min amount of time between writes.  This value
 provides the ability to throttle rapidly changing
 data so that only the last value is written.  If
 the value is zero then this feature is disabled.
 */
@NiagaraProperty(
  name = "minWriteTime",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)",
  facets = @Facet("BFacets.make(BFacets.MIN,BRelTime.make(0))")
)
/*
 Max amount of time to allow before a rewrite is
 issued if nothing else has trigged a write.  If
 the value is zero then this feature is disabled.
 */
@NiagaraProperty(
  name = "maxWriteTime",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)",
  facets = @Facet("BFacets.make(BFacets.MIN,BRelTime.make(0))")
)
/*
 If true then a write is issued when the station
 reaches steady state.
 */
@NiagaraProperty(
  name = "writeOnStart",
  type = "boolean",
  defaultValue = "true"
)
/*
 If true then a write is issued when the containing
 device transitions its status from down to up.
 */
@NiagaraProperty(
  name = "writeOnUp",
  type = "boolean",
  defaultValue = "true"
)
/*
 If true then a write is issued when the point transitions
 its status from disabled to enabled.
 */
@NiagaraProperty(
  name = "writeOnEnabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 This property is used to configure when point data
 becomes stale.  If set to non-zero then points become
 stale if the configured time elapses without a successful
 read (indicated by readOk).  If set to zero then the
 stale timer is disabled, and points are set to stale
 immediately when unsubscribed.
 */
@NiagaraProperty(
  name = "staleTime",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)",
  facets = @Facet("BFacets.make(BFacets.MIN,BRelTime.make(0))")
)
public class BTuningPolicy
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.point.BTuningPolicy(1239520222)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "minWriteTime"

  /**
   * Slot for the {@code minWriteTime} property.
   * Min amount of time between writes.  This value
   * provides the ability to throttle rapidly changing
   * data so that only the last value is written.  If
   * the value is zero then this feature is disabled.
   * @see #getMinWriteTime
   * @see #setMinWriteTime
   */
  public static final Property minWriteTime = newProperty(0, BRelTime.make(0), BFacets.make(BFacets.MIN,BRelTime.make(0)));

  /**
   * Get the {@code minWriteTime} property.
   * Min amount of time between writes.  This value
   * provides the ability to throttle rapidly changing
   * data so that only the last value is written.  If
   * the value is zero then this feature is disabled.
   * @see #minWriteTime
   */
  public BRelTime getMinWriteTime() { return (BRelTime)get(minWriteTime); }

  /**
   * Set the {@code minWriteTime} property.
   * Min amount of time between writes.  This value
   * provides the ability to throttle rapidly changing
   * data so that only the last value is written.  If
   * the value is zero then this feature is disabled.
   * @see #minWriteTime
   */
  public void setMinWriteTime(BRelTime v) { set(minWriteTime, v, null); }

  //endregion Property "minWriteTime"

  //region Property "maxWriteTime"

  /**
   * Slot for the {@code maxWriteTime} property.
   * Max amount of time to allow before a rewrite is
   * issued if nothing else has trigged a write.  If
   * the value is zero then this feature is disabled.
   * @see #getMaxWriteTime
   * @see #setMaxWriteTime
   */
  public static final Property maxWriteTime = newProperty(0, BRelTime.make(0), BFacets.make(BFacets.MIN,BRelTime.make(0)));

  /**
   * Get the {@code maxWriteTime} property.
   * Max amount of time to allow before a rewrite is
   * issued if nothing else has trigged a write.  If
   * the value is zero then this feature is disabled.
   * @see #maxWriteTime
   */
  public BRelTime getMaxWriteTime() { return (BRelTime)get(maxWriteTime); }

  /**
   * Set the {@code maxWriteTime} property.
   * Max amount of time to allow before a rewrite is
   * issued if nothing else has trigged a write.  If
   * the value is zero then this feature is disabled.
   * @see #maxWriteTime
   */
  public void setMaxWriteTime(BRelTime v) { set(maxWriteTime, v, null); }

  //endregion Property "maxWriteTime"

  //region Property "writeOnStart"

  /**
   * Slot for the {@code writeOnStart} property.
   * If true then a write is issued when the station
   * reaches steady state.
   * @see #getWriteOnStart
   * @see #setWriteOnStart
   */
  public static final Property writeOnStart = newProperty(0, true, null);

  /**
   * Get the {@code writeOnStart} property.
   * If true then a write is issued when the station
   * reaches steady state.
   * @see #writeOnStart
   */
  public boolean getWriteOnStart() { return getBoolean(writeOnStart); }

  /**
   * Set the {@code writeOnStart} property.
   * If true then a write is issued when the station
   * reaches steady state.
   * @see #writeOnStart
   */
  public void setWriteOnStart(boolean v) { setBoolean(writeOnStart, v, null); }

  //endregion Property "writeOnStart"

  //region Property "writeOnUp"

  /**
   * Slot for the {@code writeOnUp} property.
   * If true then a write is issued when the containing
   * device transitions its status from down to up.
   * @see #getWriteOnUp
   * @see #setWriteOnUp
   */
  public static final Property writeOnUp = newProperty(0, true, null);

  /**
   * Get the {@code writeOnUp} property.
   * If true then a write is issued when the containing
   * device transitions its status from down to up.
   * @see #writeOnUp
   */
  public boolean getWriteOnUp() { return getBoolean(writeOnUp); }

  /**
   * Set the {@code writeOnUp} property.
   * If true then a write is issued when the containing
   * device transitions its status from down to up.
   * @see #writeOnUp
   */
  public void setWriteOnUp(boolean v) { setBoolean(writeOnUp, v, null); }

  //endregion Property "writeOnUp"

  //region Property "writeOnEnabled"

  /**
   * Slot for the {@code writeOnEnabled} property.
   * If true then a write is issued when the point transitions
   * its status from disabled to enabled.
   * @see #getWriteOnEnabled
   * @see #setWriteOnEnabled
   */
  public static final Property writeOnEnabled = newProperty(0, true, null);

  /**
   * Get the {@code writeOnEnabled} property.
   * If true then a write is issued when the point transitions
   * its status from disabled to enabled.
   * @see #writeOnEnabled
   */
  public boolean getWriteOnEnabled() { return getBoolean(writeOnEnabled); }

  /**
   * Set the {@code writeOnEnabled} property.
   * If true then a write is issued when the point transitions
   * its status from disabled to enabled.
   * @see #writeOnEnabled
   */
  public void setWriteOnEnabled(boolean v) { setBoolean(writeOnEnabled, v, null); }

  //endregion Property "writeOnEnabled"

  //region Property "staleTime"

  /**
   * Slot for the {@code staleTime} property.
   * This property is used to configure when point data
   * becomes stale.  If set to non-zero then points become
   * stale if the configured time elapses without a successful
   * read (indicated by readOk).  If set to zero then the
   * stale timer is disabled, and points are set to stale
   * immediately when unsubscribed.
   * @see #getStaleTime
   * @see #setStaleTime
   */
  public static final Property staleTime = newProperty(0, BRelTime.make(0), BFacets.make(BFacets.MIN,BRelTime.make(0)));

  /**
   * Get the {@code staleTime} property.
   * This property is used to configure when point data
   * becomes stale.  If set to non-zero then points become
   * stale if the configured time elapses without a successful
   * read (indicated by readOk).  If set to zero then the
   * stale timer is disabled, and points are set to stale
   * immediately when unsubscribed.
   * @see #staleTime
   */
  public BRelTime getStaleTime() { return (BRelTime)get(staleTime); }

  /**
   * Set the {@code staleTime} property.
   * This property is used to configure when point data
   * becomes stale.  If set to non-zero then points become
   * stale if the configured time elapses without a successful
   * read (indicated by readOk).  If set to zero then the
   * stale timer is disabled, and points are set to stale
   * immediately when unsubscribed.
   * @see #staleTime
   */
  public void setStaleTime(BRelTime v) { set(staleTime, v, null); }

  //endregion Property "staleTime"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTuningPolicy.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a fully specified BTuningPolicy.
   */
  public BTuningPolicy(BRelTime minWriteTime,
                       BRelTime maxWriteTime,
                       boolean  writeOnStart,
                       boolean  writeOnUp,
                       boolean  writeOnEnabled,
                       BRelTime staleTime)
  {
    setMinWriteTime(minWriteTime);    
    setMaxWriteTime(maxWriteTime);    
    setWriteOnStart(writeOnStart);    
    setWriteOnUp(writeOnUp);    
    setWriteOnEnabled(writeOnEnabled);    
    setStaleTime(staleTime);    
  }

  /**
   * No arg constructor.
   */
  public BTuningPolicy() 
  {
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("wrench.png");  
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  /**
   * This context is passed to ITunable.write() if the write
   * is called because of elapsed maxWriteTime.
   */ 
  public static final Context maxWriteTimeContext = new BasicContext()
  {
    public boolean equals(Object obj) { return this == obj; }
    public int hashCode() { return System.identityHashCode(this); }
    public String toString() { return "TuningPolicy.maxWriteTimeContext"; }
  };

  /**
   * This context is passed to ITunable.write() if the write
   * is called because of the onStart transition.
   */ 
  public static final Context writeOnStartContext = new BasicContext()
  {
    public boolean equals(Object obj) { return this == obj; }
    public int hashCode() { return System.identityHashCode(this); }
    public String toString() { return "TuningPolicy.writeOnStartContext"; }
  };

  /**
   * This context is passed to ITunable.write() if the write
   * is called because of the onUp transition.
   */ 
  public static final Context writeOnUpContext = new BasicContext()
  {
    public boolean equals(Object obj) { return this == obj; }
    public int hashCode() { return System.identityHashCode(this); }
    public String toString() { return "TuningPolicy.writeOnUpContext"; }
  };

  /**
   * This context is passed to ITunable.write() if the write
   * is called because of the onEnabled transition.
   */ 
  public static final Context writeOnEnabledContext = new BasicContext()
  {
    public boolean equals(Object obj) { return this == obj; }
    public int hashCode() { return System.identityHashCode(this); }
    public String toString() { return "TuningPolicy.writeOnEnabledContext"; }
  };
  
  public void started()
    throws Exception
  { 
    // Make update times positive - this is needed because the new MIN facets 
    // will not allow user to edit value to >=0 if originally <0
    setMinWriteTime(getMinWriteTime().abs());
    setMaxWriteTime(getMaxWriteTime().abs());
    setStaleTime(getStaleTime().abs());
    
    super.started();

  }  
  
}
