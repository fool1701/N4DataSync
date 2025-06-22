/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BKitNumericPoint is the abstract superclass of all kitControl
 * numeric points.
 *
 * @author    Lee Adcock
 * @creation  18 June 09
 * @version   $Revision: 17$ $Date: 3/30/2004 3:42:02 PM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 defines which input status flags will be propagated from
 input to output.
 */
@NiagaraProperty(
  name = "propagateFlags",
  type = "BStatus",
  defaultValue = "BStatus.nullStatus"
)
public abstract class BKitBooleanPoint
  extends BBooleanPoint
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.BKitBooleanPoint(3891554994)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "propagateFlags"

  /**
   * Slot for the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #getPropagateFlags
   * @see #setPropagateFlags
   */
  public static final Property propagateFlags = newProperty(0, BStatus.nullStatus, null);

  /**
   * Get the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #propagateFlags
   */
  public BStatus getPropagateFlags() { return (BStatus)get(propagateFlags); }

  /**
   * Set the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #propagateFlags
   */
  public void setPropagateFlags(BStatus v) { set(propagateFlags, v, null); }

  //endregion Property "propagateFlags"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BKitBooleanPoint.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
/*
  private static final BFacets PROPAGATE_FACETS = 
    BFacets.make(BFacets.FIELD_EDITOR, BString.make("kitControl:PropagateFlagsFE"));

  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.getName().equals("propagateFlags"))
    {
      return PROPAGATE_FACETS;
    }
    else return super.getSlotFacets(slot);
  }
*/

  /**
   * Create a new status by masking out only the standard
   * flags which should be propagated from inputs to outputs.
   * See PROPOGATE_MASK for the flags which are propagated.
   *
   * @return <code>make(s.getBits() & PROPOGATE_MASK)</code>
   */
  public BStatus propagate(BStatus s)
  {
    return BStatus.make(s.getBits() & getPropagateFlags().getBits());
  }

}
