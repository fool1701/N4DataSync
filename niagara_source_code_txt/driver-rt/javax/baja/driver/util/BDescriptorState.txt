/* 
 * Copyright 2004 Tridium, Inc.  All rights reserved.
 * 
 */

package javax.baja.driver.util;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * @author John Sublett, Aaron Hansen
 * @creation Feb 2004
 * @version $Revision: 8$ $Date: 6/3/04 1:11:45 PM EDT$
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("idle"),
    @Range("pending"),
    @Range("inProgress")
  }
)
public final class BDescriptorState 
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.util.BDescriptorState(839540194)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for idle. */
  public static final int IDLE = 0;
  /** Ordinal value for pending. */
  public static final int PENDING = 1;
  /** Ordinal value for inProgress. */
  public static final int IN_PROGRESS = 2;

  /** BDescriptorState constant for idle. */
  public static final BDescriptorState idle = new BDescriptorState(IDLE);
  /** BDescriptorState constant for pending. */
  public static final BDescriptorState pending = new BDescriptorState(PENDING);
  /** BDescriptorState constant for inProgress. */
  public static final BDescriptorState inProgress = new BDescriptorState(IN_PROGRESS);

  /** Factory method with ordinal. */
  public static BDescriptorState make(int ordinal)
  {
    return (BDescriptorState)idle.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BDescriptorState make(String tag)
  {
    return (BDescriptorState)idle.getRange().get(tag);
  }

  /** Private constructor. */
  private BDescriptorState(int ordinal)
  {
    super(ordinal);
  }

  public static final BDescriptorState DEFAULT = idle;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDescriptorState.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Default and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Private and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Inner Classes - in alphabetical order by class name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Constants - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//BDescriptorState
