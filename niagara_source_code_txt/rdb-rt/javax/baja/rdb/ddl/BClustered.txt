/*
 * Copyright 2005, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.rdb.ddl;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BClustered defines whether something is clustered, nonclustered, or unspecified.
 * 
 * @author    Mike Jarmy
 * @creation  06 Mar 10
 * @version   $Revision$ $Date: 8/4/2005 4:53:55 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("unspecified"),
    @Range("clustered"),
    @Range("nonClustered")
  }
)
public final class BClustered
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.ddl.BClustered(61595432)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for unspecified. */
  public static final int UNSPECIFIED = 0;
  /** Ordinal value for clustered. */
  public static final int CLUSTERED = 1;
  /** Ordinal value for nonClustered. */
  public static final int NON_CLUSTERED = 2;

  /** BClustered constant for unspecified. */
  public static final BClustered unspecified = new BClustered(UNSPECIFIED);
  /** BClustered constant for clustered. */
  public static final BClustered clustered = new BClustered(CLUSTERED);
  /** BClustered constant for nonClustered. */
  public static final BClustered nonClustered = new BClustered(NON_CLUSTERED);

  /** Factory method with ordinal. */
  public static BClustered make(int ordinal)
  {
    return (BClustered)unspecified.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BClustered make(String tag)
  {
    return (BClustered)unspecified.getRange().get(tag);
  }

  /** Private constructor. */
  private BClustered(int ordinal)
  {
    super(ordinal);
  }

  public static final BClustered DEFAULT = unspecified;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BClustered.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
