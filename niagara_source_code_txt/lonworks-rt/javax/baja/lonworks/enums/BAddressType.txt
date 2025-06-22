/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BAddressType class provides enumeration of the address
 * types in the neurons address table.
 *
 * @author    Robert Adams
 * @creation  14 Jan 01
 * @version   $Revision: 2$ $Date: 9/18/01 9:49:24 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("none"),
    @Range("group"),
    @Range("subnetNode"),
    @Range("broadcast"),
    @Range("turnaround")
  }
)
public final class BAddressType
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BAddressType(33389170)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for none. */
  public static final int NONE = 0;
  /** Ordinal value for group. */
  public static final int GROUP = 1;
  /** Ordinal value for subnetNode. */
  public static final int SUBNET_NODE = 2;
  /** Ordinal value for broadcast. */
  public static final int BROADCAST = 3;
  /** Ordinal value for turnaround. */
  public static final int TURNAROUND = 4;

  /** BAddressType constant for none. */
  public static final BAddressType none = new BAddressType(NONE);
  /** BAddressType constant for group. */
  public static final BAddressType group = new BAddressType(GROUP);
  /** BAddressType constant for subnetNode. */
  public static final BAddressType subnetNode = new BAddressType(SUBNET_NODE);
  /** BAddressType constant for broadcast. */
  public static final BAddressType broadcast = new BAddressType(BROADCAST);
  /** BAddressType constant for turnaround. */
  public static final BAddressType turnaround = new BAddressType(TURNAROUND);

  /** Factory method with ordinal. */
  public static BAddressType make(int ordinal)
  {
    return (BAddressType)none.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BAddressType make(String tag)
  {
    return (BAddressType)none.getRange().get(tag);
  }

  /** Private constructor. */
  private BAddressType(int ordinal)
  {
    super(ordinal);
  }

  public static final BAddressType DEFAULT = none;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAddressType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
