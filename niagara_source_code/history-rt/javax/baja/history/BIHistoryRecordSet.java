/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTypeSpec;

/**
 * BIHistoryRecordSet is an ordered set of zero or more history records.  Each
 * record in the set must be of the same type.
 *
 * @author    John Sublett
 * @creation  28 Oct 2002
 * @version   $Revision: 4$ $Date: 3/4/10 3:08:06 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIHistoryRecordSet
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BIHistoryRecordSet(2979906276)1.0$ @*/
/* Generated Thu Jan 27 19:02:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIHistoryRecordSet.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the number of records in the set.
   */
  public int getRecordCount();

  /**
   * Get the record at the specified index.
   */
  public BHistoryRecord getRecord(int index);

  /**
   * Get an array of the records in the set.
   */
  public BHistoryRecord[] getRecords();

  /**
   * Get the last record in the set.  If the set is empty, null is returned.
   */
  public BHistoryRecord getLastRecord();

  /**
   * Get the type of the records in the set.
   */
  public BTypeSpec getRecordTypeSpec();
}
