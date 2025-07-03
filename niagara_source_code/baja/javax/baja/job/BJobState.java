/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.job;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BJobState enumerates the state machine of a Job's lifecycle.
 *
 * @author    Brian Frank
 * @creation  30 Apr 03
 * @version   $Revision: 1$Date$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("unknown"),
    @Range("running"),
    @Range("canceling"),
    @Range("canceled"),
    @Range("success"),
    @Range("failed")
  }
)
public final class BJobState
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.job.BJobState(2138089960)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for unknown. */
  public static final int UNKNOWN = 0;
  /** Ordinal value for running. */
  public static final int RUNNING = 1;
  /** Ordinal value for canceling. */
  public static final int CANCELING = 2;
  /** Ordinal value for canceled. */
  public static final int CANCELED = 3;
  /** Ordinal value for success. */
  public static final int SUCCESS = 4;
  /** Ordinal value for failed. */
  public static final int FAILED = 5;

  /** BJobState constant for unknown. */
  public static final BJobState unknown = new BJobState(UNKNOWN);
  /** BJobState constant for running. */
  public static final BJobState running = new BJobState(RUNNING);
  /** BJobState constant for canceling. */
  public static final BJobState canceling = new BJobState(CANCELING);
  /** BJobState constant for canceled. */
  public static final BJobState canceled = new BJobState(CANCELED);
  /** BJobState constant for success. */
  public static final BJobState success = new BJobState(SUCCESS);
  /** BJobState constant for failed. */
  public static final BJobState failed = new BJobState(FAILED);

  /** Factory method with ordinal. */
  public static BJobState make(int ordinal)
  {
    return (BJobState)unknown.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BJobState make(String tag)
  {
    return (BJobState)unknown.getRange().get(tag);
  }

  /** Private constructor. */
  private BJobState(int ordinal)
  {
    super(ordinal);
  }

  public static final BJobState DEFAULT = unknown;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BJobState.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
    
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Is this running.
   */
  public boolean isRunning()
  {
    return this == running;
  }                        
  
  /**
   * Is this success, canceled, or failed.
   */
  public boolean isComplete()
  {
    return this == success || this == canceled || this == failed;
  }
  
}
