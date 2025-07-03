/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.tuning;

import javax.baja.driver.point.BTuningPolicy;
import javax.baja.driver.point.BTuningPolicyMap;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BLonTuningPolicyMap .
 *
 * @author    Robert Adams
 * @creation  2 July 04
 * @version   $Revision: 1$ $Date: 6/28/2004 5:26:38 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "defaultProxyPolicy",
  type = "BTuningPolicy",
  defaultValue = "new BLonTuningPolicy(BRelTime.make(0),BRelTime.make(0),true,true,true,BRelTime.make(0))",
  flags = Flags.READONLY | Flags.HIDDEN
)
@NiagaraProperty(
  name = "defaultPolicy",
  type = "BLonTuningPolicy",
  defaultValue = "new BLonTuningPolicy(BRelTime.make(0),BRelTime.make(60000),true,true,true,BRelTime.make(0))",
  override = true
)
public class BLonTuningPolicyMap
  extends BTuningPolicyMap
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.tuning.BLonTuningPolicyMap(1066882886)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "defaultProxyPolicy"

  /**
   * Slot for the {@code defaultProxyPolicy} property.
   * @see #getDefaultProxyPolicy
   * @see #setDefaultProxyPolicy
   */
  public static final Property defaultProxyPolicy = newProperty(Flags.READONLY | Flags.HIDDEN, new BLonTuningPolicy(BRelTime.make(0),BRelTime.make(0),true,true,true,BRelTime.make(0)), null);

  /**
   * Get the {@code defaultProxyPolicy} property.
   * @see #defaultProxyPolicy
   */
  public BTuningPolicy getDefaultProxyPolicy() { return (BTuningPolicy)get(defaultProxyPolicy); }

  /**
   * Set the {@code defaultProxyPolicy} property.
   * @see #defaultProxyPolicy
   */
  public void setDefaultProxyPolicy(BTuningPolicy v) { set(defaultProxyPolicy, v, null); }

  //endregion Property "defaultProxyPolicy"

  //region Property "defaultPolicy"

  /**
   * Slot for the {@code defaultPolicy} property.
   * @see #getDefaultPolicy
   * @see #setDefaultPolicy
   */
  public static final Property defaultPolicy = newProperty(0, new BLonTuningPolicy(BRelTime.make(0),BRelTime.make(60000),true,true,true,BRelTime.make(0)), null);

  //endregion Property "defaultPolicy"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonTuningPolicyMap.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  /**
   * Only LonTuningPolicy children are allowed.
   */
  public boolean isChildLegal(BComponent child)
    { return child instanceof BLonTuningPolicy; }

}
