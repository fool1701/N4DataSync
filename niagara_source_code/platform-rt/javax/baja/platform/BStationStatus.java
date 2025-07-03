/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

// IMPORTANT: these values must be kept in sync with platDaemon's
// Station.h header
/**
 * Status codes returned by the Niagara platform daemon
 * 
 * @author    Matt Boon       
 * @creation  03 Jun 02
 * @version   $Revision: 2$ $Date: 5/26/05 1:00:26 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("idle"),
    @Range("starting"),
    @Range("running"),
    @Range("stopping"),
    @Range("failed"),
    @Range("unknown"),
    @Range("halted")
  }
)
public final class BStationStatus
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.platform.BStationStatus(1296243372)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for idle. */
  public static final int IDLE = 0;
  /** Ordinal value for starting. */
  public static final int STARTING = 1;
  /** Ordinal value for running. */
  public static final int RUNNING = 2;
  /** Ordinal value for stopping. */
  public static final int STOPPING = 3;
  /** Ordinal value for failed. */
  public static final int FAILED = 4;
  /** Ordinal value for unknown. */
  public static final int UNKNOWN = 5;
  /** Ordinal value for halted. */
  public static final int HALTED = 6;

  /** BStationStatus constant for idle. */
  public static final BStationStatus idle = new BStationStatus(IDLE);
  /** BStationStatus constant for starting. */
  public static final BStationStatus starting = new BStationStatus(STARTING);
  /** BStationStatus constant for running. */
  public static final BStationStatus running = new BStationStatus(RUNNING);
  /** BStationStatus constant for stopping. */
  public static final BStationStatus stopping = new BStationStatus(STOPPING);
  /** BStationStatus constant for failed. */
  public static final BStationStatus failed = new BStationStatus(FAILED);
  /** BStationStatus constant for unknown. */
  public static final BStationStatus unknown = new BStationStatus(UNKNOWN);
  /** BStationStatus constant for halted. */
  public static final BStationStatus halted = new BStationStatus(HALTED);

  /** Factory method with ordinal. */
  public static BStationStatus make(int ordinal)
  {
    return (BStationStatus)idle.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BStationStatus make(String tag)
  {
    return (BStationStatus)idle.getRange().get(tag);
  }

  /** Private constructor. */
  private BStationStatus(int ordinal)
  {
    super(ordinal);
  }

  public static final BStationStatus DEFAULT = idle;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStationStatus.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
