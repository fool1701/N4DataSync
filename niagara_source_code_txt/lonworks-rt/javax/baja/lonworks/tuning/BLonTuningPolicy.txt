/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.tuning;

import javax.baja.driver.point.BTuningPolicy;
import javax.baja.driver.util.BPollFrequency;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.units.UnitDatabase;

/**
 * BLonTuningPolicy.
 *
 * @author    Robert Adams
 * @creation  03 June 2005
 * @version   $Revision$ $Date$
 * @since     Niagara 3 lonworks 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "pollFrequency",
  type = "BPollFrequency",
  defaultValue = "BPollFrequency.normal"
)
@NiagaraProperty(
  name = "writeDelay",
  type = "int",
  defaultValue = "0",
  facets = @Facet("BFacets.make(BFacets.MIN,BInteger.make(0),BFacets.MAX,BInteger.make(1000),BFacets.UNITS,UnitDatabase.getUnit(\"millisecond\"))")
)
public class BLonTuningPolicy
  extends BTuningPolicy
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.tuning.BLonTuningPolicy(1387655651)1.0$ @*/
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

  //region Property "writeDelay"

  /**
   * Slot for the {@code writeDelay} property.
   * @see #getWriteDelay
   * @see #setWriteDelay
   */
  public static final Property writeDelay = newProperty(0, 0, BFacets.make(BFacets.MIN,BInteger.make(0),BFacets.MAX,BInteger.make(1000),BFacets.UNITS,UnitDatabase.getUnit("millisecond")));

  /**
   * Get the {@code writeDelay} property.
   * @see #writeDelay
   */
  public int getWriteDelay() { return getInt(writeDelay); }

  /**
   * Set the {@code writeDelay} property.
   * @see #writeDelay
   */
  public void setWriteDelay(int v) { setInt(writeDelay, v, null); }

  //endregion Property "writeDelay"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonTuningPolicy.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BLonTuningPolicy()
  {
  }

  public BLonTuningPolicy( BRelTime minWriteTime,
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

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  /**
   * Can only go in a LonTuningPolicyMap.
   */
  public boolean isParentLegal(BComponent parent)
    { return parent instanceof BLonTuningPolicyMap; }

/*
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning()) return;
    if (p.equals(useCov))
    {
      ((BLonNetwork)((BLonTuningPolicyMap)getParent()).getNetwork())
        .tuningChanged(this, cx);
    }
  }
*/
  
}
