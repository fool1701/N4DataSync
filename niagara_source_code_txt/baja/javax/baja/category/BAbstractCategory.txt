/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.category;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BAbstractCategory contains properties for all category types.
 *
 * @author    Tim Urenda
 * @creation  12 Mar 2014
 * @since     Niagara 4.0
 */

@NiagaraType
/*
 Provides status for the category. If the disabled flag is set
 the category is not yet assigned an index. The fault flag
 is set if the category has an invalid index.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.disabled",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 This is a human readable description of the node.
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.TRANSIENT
)

public abstract class BAbstractCategory
  extends BComponent
  implements BIStatus
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.category.BAbstractCategory(4224523395)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * Provides status for the category. If the disabled flag is set
   * the category is not yet assigned an index. The fault flag
   * is set if the category has an invalid index.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.READONLY | Flags.TRANSIENT, BStatus.disabled, null);

  /**
   * Get the {@code status} property.
   * Provides status for the category. If the disabled flag is set
   * the category is not yet assigned an index. The fault flag
   * is set if the category has an invalid index.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * Provides status for the category. If the disabled flag is set
   * the category is not yet assigned an index. The fault flag
   * is set if the category has an invalid index.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * This is a human readable description of the node.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.READONLY | Flags.TRANSIENT, "", null);

  /**
   * Get the {@code faultCause} property.
   * This is a human readable description of the node.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * This is a human readable description of the node.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractCategory.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * To string.
   */
  @Override
  public String toString(Context cx)
  {
    return getDisplayName(cx);
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  protected static final BIcon icon = BIcon.std("category.png");

}
