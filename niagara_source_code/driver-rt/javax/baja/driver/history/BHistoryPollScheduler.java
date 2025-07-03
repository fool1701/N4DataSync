/*
 * Copyright 2008 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.history;

import javax.baja.driver.util.BIPollable;
import javax.baja.driver.util.BPollScheduler;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BHistoryPollScheduler
 *
 * @author    Scott Hoye
 * @creation  03 Apr 08
 * @version   $Revision: 3$ $Date: 7/28/09 12:27:41 PM EDT$  
 * @since     Niagara 3.4  
 */
@NiagaraType
/*
 The frequency used to poll history import descriptors set to fast.
 */
@NiagaraProperty(
  name = "fastRate",
  type = "BRelTime",
  defaultValue = "BRelTime.make(10000)",
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.make(1))"),
  override = true
)
/*
 The frequency used to poll history import descriptors set to normal.
 */
@NiagaraProperty(
  name = "normalRate",
  type = "BRelTime",
  defaultValue = "BRelTime.make(45000)",
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.make(1))"),
  override = true
)
/*
 The frequency used to poll history import descriptors set to slow.
 */
@NiagaraProperty(
  name = "slowRate",
  type = "BRelTime",
  defaultValue = "BRelTime.make(120000)",
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.make(1))"),
  override = true
)
public class BHistoryPollScheduler
  extends BPollScheduler
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.history.BHistoryPollScheduler(1853484177)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "fastRate"

  /**
   * Slot for the {@code fastRate} property.
   * The frequency used to poll history import descriptors set to fast.
   * @see #getFastRate
   * @see #setFastRate
   */
  public static final Property fastRate = newProperty(0, BRelTime.make(10000), BFacets.make(BFacets.MIN, BRelTime.make(1)));

  //endregion Property "fastRate"

  //region Property "normalRate"

  /**
   * Slot for the {@code normalRate} property.
   * The frequency used to poll history import descriptors set to normal.
   * @see #getNormalRate
   * @see #setNormalRate
   */
  public static final Property normalRate = newProperty(0, BRelTime.make(45000), BFacets.make(BFacets.MIN, BRelTime.make(1)));

  //endregion Property "normalRate"

  //region Property "slowRate"

  /**
   * Slot for the {@code slowRate} property.
   * The frequency used to poll history import descriptors set to slow.
   * @see #getSlowRate
   * @see #setSlowRate
   */
  public static final Property slowRate = newProperty(0, BRelTime.make(120000), BFacets.make(BFacets.MIN, BRelTime.make(1)));

  //endregion Property "slowRate"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryPollScheduler.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Poll the specified BIHistoryPollable.
   */
  public void doPoll(BIPollable p)
    throws Exception
  {
    if(p instanceof BIHistoryPollable)
      ((BIHistoryPollable)p).poll();
  }

}
