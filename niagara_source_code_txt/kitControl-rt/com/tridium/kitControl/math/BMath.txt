/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.math;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BMath is the abstract superclass of all Baja math 
 * objects.  These objects take 1-4 numeric inputs, 
 * perform a mathematical operation such as addition, 
 * averaging, sin, etc. and update the out property.
 * <p>
 * By convention, input values with invalid status 
 * (down, fault or null) bit set are ignored (not 
 * used in the math calculation).  However, the 
 * status bits set on the output are the logical 
 * OR of the status bits set on the input, with
 * one exception. If the null bit is set, any
 * other bits (status, in_alarm, etc) are ignored.
 * <p>
 * By  default, all 'in' property slots have the null 
 * bit set, therefore unlinked values are ignored. 
 * If all inputs have the null bit set, the output
 * will have the null bit set.  
 * <p>
 * Each math object requires a minimum number of 
 * inputs. If less than the minimum number of inputs 
 * are non-null, the output will have the null bit set.
 * <p>
 * Input values with the Double.NaN are NOT ignored.
 * The calculation will proceed normally, resulting
 * in a Double.NaN output.
 *
 * @author    Dan Giorgis
 * @creation  8 Nov 00
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
  defaultValue = "BStatus.ok"
)
public abstract class BMath
  extends BNumericPoint
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.math.BMath(570264766)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "propagateFlags"

  /**
   * Slot for the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #getPropagateFlags
   * @see #setPropagateFlags
   */
  public static final Property propagateFlags = newProperty(0, BStatus.ok, null);

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
  public static final Type TYPE = Sys.loadType(BMath.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private static final BFacets PROPAGATE_FACETS = 
    BFacets.make(BFacets.FIELD_EDITOR, BString.make("kitControl:PropagateFlagsFE"), BFacets.UX_FIELD_EDITOR, BString.make("kitControl:PropagateFlagsEditor"));

  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.getName().equals("propagateFlags"))
    {
      return PROPAGATE_FACETS;
    }
    else return super.getSlotFacets(slot);
  }

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
