/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.algorithm;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.BIcon;
import javax.baja.sys.BStruct;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;


/**
 * Used for linking algorithm blocks together.
 *
 * <p>
 *   Only the properties of type BBlockPin will considered as input/output slots
 *   in algorithm blocks.
 * </p>
 * @author Aaron Hansen
 * @since NA 2.0
 */
@NiagaraType
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.HIDDEN
)
public class BBlockPin
  extends BStruct
  implements BIStatus
{
  


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.bajax.analytics.algorithm.BBlockPin(584830080)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.HIDDEN, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBlockPin.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BIcon getIcon()
  {
    return icon;
  }

  public String toString(Context cx)
  {
    return "";
  }


  /**
   * Called by blocks when a required input is unlinked.
   */
  protected void configFail()
  {
    if (!getStatus().isFault())
    {
      setStatus(BStatus.fault);
    }
  }

  /**
   * Called by blocks when a required input is linked.
   */
  protected void configOk()
  {
    if (!getStatus().isOk())
      setStatus(BStatus.ok);
  }


  private static BIcon icon = BIcon.std("mediaPlay.png");


}
