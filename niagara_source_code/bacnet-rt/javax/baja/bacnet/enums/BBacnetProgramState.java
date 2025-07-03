/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BBacnetProgramState represents the BACnetProgramState
 * enumeration.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 17 Oct 2005
 * @since Niagara 3.1
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("idle"),
    @Range("loading"),
    @Range("running"),
    @Range("waiting"),
    @Range("halted"),
    @Range("unloading")
  }
)
public final class BBacnetProgramState
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetProgramState(1548153317)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for idle. */
  public static final int IDLE = 0;
  /** Ordinal value for loading. */
  public static final int LOADING = 1;
  /** Ordinal value for running. */
  public static final int RUNNING = 2;
  /** Ordinal value for waiting. */
  public static final int WAITING = 3;
  /** Ordinal value for halted. */
  public static final int HALTED = 4;
  /** Ordinal value for unloading. */
  public static final int UNLOADING = 5;

  /** BBacnetProgramState constant for idle. */
  public static final BBacnetProgramState idle = new BBacnetProgramState(IDLE);
  /** BBacnetProgramState constant for loading. */
  public static final BBacnetProgramState loading = new BBacnetProgramState(LOADING);
  /** BBacnetProgramState constant for running. */
  public static final BBacnetProgramState running = new BBacnetProgramState(RUNNING);
  /** BBacnetProgramState constant for waiting. */
  public static final BBacnetProgramState waiting = new BBacnetProgramState(WAITING);
  /** BBacnetProgramState constant for halted. */
  public static final BBacnetProgramState halted = new BBacnetProgramState(HALTED);
  /** BBacnetProgramState constant for unloading. */
  public static final BBacnetProgramState unloading = new BBacnetProgramState(UNLOADING);

  /** Factory method with ordinal. */
  public static BBacnetProgramState make(int ordinal)
  {
    return (BBacnetProgramState)idle.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetProgramState make(String tag)
  {
    return (BBacnetProgramState)idle.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetProgramState(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetProgramState DEFAULT = idle;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetProgramState.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Static methods
////////////////////////////////////////////////////////////////

  /**
   * Create a string tag for the given ordinal.
   *
   * @return the tag for the ordinal, if it is known,
   * or construct one using standard prefixes.
   */
  public static String tag(int id)
  {
    return DEFAULT.getRange().getTag(id);
  }
}
