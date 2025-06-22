/**
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.history;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BRdbmsHistoryExportMode defines whether histories will
 * be exported into one table per history, or one table
 * per BHistoryRecord type.
 * 
 * @author    Mike Jarmy
 * @creation  28 Feb 07
 * @version   $Revision: 1$ $Date: 3/22/07 11:16:16 AM EDT$  
 * @since     Niagara 3.1     
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("byHistoryId"),
    @Range("byHistoryType")
  }
)
public final class BRdbmsHistoryExportMode
  extends BFrozenEnum  
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.history.BRdbmsHistoryExportMode(3914833935)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for byHistoryId. */
  public static final int BY_HISTORY_ID = 0;
  /** Ordinal value for byHistoryType. */
  public static final int BY_HISTORY_TYPE = 1;

  /** BRdbmsHistoryExportMode constant for byHistoryId. */
  public static final BRdbmsHistoryExportMode byHistoryId = new BRdbmsHistoryExportMode(BY_HISTORY_ID);
  /** BRdbmsHistoryExportMode constant for byHistoryType. */
  public static final BRdbmsHistoryExportMode byHistoryType = new BRdbmsHistoryExportMode(BY_HISTORY_TYPE);

  /** Factory method with ordinal. */
  public static BRdbmsHistoryExportMode make(int ordinal)
  {
    return (BRdbmsHistoryExportMode)byHistoryId.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BRdbmsHistoryExportMode make(String tag)
  {
    return (BRdbmsHistoryExportMode)byHistoryId.getRange().get(tag);
  }

  /** Private constructor. */
  private BRdbmsHistoryExportMode(int ordinal)
  {
    super(ordinal);
  }

  public static final BRdbmsHistoryExportMode DEFAULT = byHistoryId;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRdbmsHistoryExportMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
 

}
