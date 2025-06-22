/**
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BRdbmsTimestampStorage
 * 
 * @author    JJ Frankovich
 * @creation  05 Mar 2013
 * @version   $Revision$ $Date$
 * @since     Baja 3.7
 */ 
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("dialectDefault"),
    @Range("localTimestamp"),
    @Range("utcTimestamp"),
    @Range("utcMillis")
  }
)
public final class BRdbmsTimestampStorage
  extends BFrozenEnum  
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.BRdbmsTimestampStorage(3472097080)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for dialectDefault. */
  public static final int DIALECT_DEFAULT = 0;
  /** Ordinal value for localTimestamp. */
  public static final int LOCAL_TIMESTAMP = 1;
  /** Ordinal value for utcTimestamp. */
  public static final int UTC_TIMESTAMP = 2;
  /** Ordinal value for utcMillis. */
  public static final int UTC_MILLIS = 3;

  /** BRdbmsTimestampStorage constant for dialectDefault. */
  public static final BRdbmsTimestampStorage dialectDefault = new BRdbmsTimestampStorage(DIALECT_DEFAULT);
  /** BRdbmsTimestampStorage constant for localTimestamp. */
  public static final BRdbmsTimestampStorage localTimestamp = new BRdbmsTimestampStorage(LOCAL_TIMESTAMP);
  /** BRdbmsTimestampStorage constant for utcTimestamp. */
  public static final BRdbmsTimestampStorage utcTimestamp = new BRdbmsTimestampStorage(UTC_TIMESTAMP);
  /** BRdbmsTimestampStorage constant for utcMillis. */
  public static final BRdbmsTimestampStorage utcMillis = new BRdbmsTimestampStorage(UTC_MILLIS);

  /** Factory method with ordinal. */
  public static BRdbmsTimestampStorage make(int ordinal)
  {
    return (BRdbmsTimestampStorage)dialectDefault.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BRdbmsTimestampStorage make(String tag)
  {
    return (BRdbmsTimestampStorage)dialectDefault.getRange().get(tag);
  }

  /** Private constructor. */
  private BRdbmsTimestampStorage(int ordinal)
  {
    super(ordinal);
  }

  public static final BRdbmsTimestampStorage DEFAULT = dialectDefault;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRdbmsTimestampStorage.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
