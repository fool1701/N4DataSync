/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.point;

import javax.baja.driver.point.BTuningPolicyMap;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BBacnetTuningPolicyMap.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 08 Jul 2004
 * @since Niagara 3 BACnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "defaultPolicy",
  type = "BBacnetTuningPolicy",
  defaultValue = "new BBacnetTuningPolicy()",
  override = true
)
public class BBacnetTuningPolicyMap
  extends BTuningPolicyMap
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.point.BBacnetTuningPolicyMap(3422687725)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "defaultPolicy"

  /**
   * Slot for the {@code defaultPolicy} property.
   * @see #getDefaultPolicy
   * @see #setDefaultPolicy
   */
  public static final Property defaultPolicy = newProperty(0, new BBacnetTuningPolicy(), null);

  //endregion Property "defaultPolicy"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetTuningPolicyMap.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Only BacnetTuningPolicy children are allowed.
   */
  public boolean isChildLegal(BComponent child)
  {
    return child instanceof BBacnetTuningPolicy;
  }
}
