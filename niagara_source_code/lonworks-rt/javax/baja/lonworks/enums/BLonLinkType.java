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
 * The BLonLinkStatus enumerates the various managed states
 * of a BLonRouter.
 *
 * @author    Robert Adams
 * @creation  30 May 02
 * @version   $Revision: 1$ $Date: 12/11/00 8:13:10 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("unknown"),
    @Range("standard"),
    @Range("reliable"),
    @Range("critical"),
    @Range("authenticated"),
    @Range("pollOnly")
  }
)
public final class BLonLinkType
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonLinkType(733896115)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for unknown. */
  public static final int UNKNOWN = 0;
  /** Ordinal value for standard. */
  public static final int STANDARD = 1;
  /** Ordinal value for reliable. */
  public static final int RELIABLE = 2;
  /** Ordinal value for critical. */
  public static final int CRITICAL = 3;
  /** Ordinal value for authenticated. */
  public static final int AUTHENTICATED = 4;
  /** Ordinal value for pollOnly. */
  public static final int POLL_ONLY = 5;

  /** BLonLinkType constant for unknown. */
  public static final BLonLinkType unknown = new BLonLinkType(UNKNOWN);
  /** BLonLinkType constant for standard. */
  public static final BLonLinkType standard = new BLonLinkType(STANDARD);
  /** BLonLinkType constant for reliable. */
  public static final BLonLinkType reliable = new BLonLinkType(RELIABLE);
  /** BLonLinkType constant for critical. */
  public static final BLonLinkType critical = new BLonLinkType(CRITICAL);
  /** BLonLinkType constant for authenticated. */
  public static final BLonLinkType authenticated = new BLonLinkType(AUTHENTICATED);
  /** BLonLinkType constant for pollOnly. */
  public static final BLonLinkType pollOnly = new BLonLinkType(POLL_ONLY);

  /** Factory method with ordinal. */
  public static BLonLinkType make(int ordinal)
  {
    return (BLonLinkType)unknown.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonLinkType make(String tag)
  {
    return (BLonLinkType)unknown.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonLinkType(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonLinkType DEFAULT = unknown;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonLinkType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
