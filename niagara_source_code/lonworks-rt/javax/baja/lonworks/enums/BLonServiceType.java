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
 * The BLonServiceType class provides enumeration LonWorks 
 * protocol service type for referencing all LonWorks transactions
 *
 * @author    Robert Adams
 * @creation  09 Nov 00
 * @version   $Revision: 4$ $Date: 9/18/01 9:49:54 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("acked"),
    @Range("unackedRpt"),
    @Range("unacked"),
    @Range("request")
  }
)
public final class BLonServiceType
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonServiceType(3899094547)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for acked. */
  public static final int ACKED = 0;
  /** Ordinal value for unackedRpt. */
  public static final int UNACKED_RPT = 1;
  /** Ordinal value for unacked. */
  public static final int UNACKED = 2;
  /** Ordinal value for request. */
  public static final int REQUEST = 3;

  /** BLonServiceType constant for acked. */
  public static final BLonServiceType acked = new BLonServiceType(ACKED);
  /** BLonServiceType constant for unackedRpt. */
  public static final BLonServiceType unackedRpt = new BLonServiceType(UNACKED_RPT);
  /** BLonServiceType constant for unacked. */
  public static final BLonServiceType unacked = new BLonServiceType(UNACKED);
  /** BLonServiceType constant for request. */
  public static final BLonServiceType request = new BLonServiceType(REQUEST);

  /** Factory method with ordinal. */
  public static BLonServiceType make(int ordinal)
  {
    return (BLonServiceType)acked.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonServiceType make(String tag)
  {
    return (BLonServiceType)acked.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonServiceType(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonServiceType DEFAULT = acked;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonServiceType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get serviceType suitable for nv writes. This will filter serviceType
   * to change BLonServiceType.request to BLonServiceType.acked.
   * @return one of BLonServiceType.acked,unacked,unackedRpt
   */
  public BLonServiceType getWriteServiceType()
  {
    if(this==request) return  acked;
    return this;
  }

}
