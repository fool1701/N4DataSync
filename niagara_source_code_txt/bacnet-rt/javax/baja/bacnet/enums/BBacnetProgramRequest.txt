/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BBacnetProgramRequest represents the BACnetProgramRequest
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
    @Range("ready"),
    @Range("load"),
    @Range("run"),
    @Range("halt"),
    @Range("restart"),
    @Range("unload")
  }
)
public final class BBacnetProgramRequest
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetProgramRequest(1345812197)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for ready. */
  public static final int READY = 0;
  /** Ordinal value for load. */
  public static final int LOAD = 1;
  /** Ordinal value for run. */
  public static final int RUN = 2;
  /** Ordinal value for halt. */
  public static final int HALT = 3;
  /** Ordinal value for restart. */
  public static final int RESTART = 4;
  /** Ordinal value for unload. */
  public static final int UNLOAD = 5;

  /** BBacnetProgramRequest constant for ready. */
  public static final BBacnetProgramRequest ready = new BBacnetProgramRequest(READY);
  /** BBacnetProgramRequest constant for load. */
  public static final BBacnetProgramRequest load = new BBacnetProgramRequest(LOAD);
  /** BBacnetProgramRequest constant for run. */
  public static final BBacnetProgramRequest run = new BBacnetProgramRequest(RUN);
  /** BBacnetProgramRequest constant for halt. */
  public static final BBacnetProgramRequest halt = new BBacnetProgramRequest(HALT);
  /** BBacnetProgramRequest constant for restart. */
  public static final BBacnetProgramRequest restart = new BBacnetProgramRequest(RESTART);
  /** BBacnetProgramRequest constant for unload. */
  public static final BBacnetProgramRequest unload = new BBacnetProgramRequest(UNLOAD);

  /** Factory method with ordinal. */
  public static BBacnetProgramRequest make(int ordinal)
  {
    return (BBacnetProgramRequest)ready.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetProgramRequest make(String tag)
  {
    return (BBacnetProgramRequest)ready.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetProgramRequest(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetProgramRequest DEFAULT = ready;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetProgramRequest.class);

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
