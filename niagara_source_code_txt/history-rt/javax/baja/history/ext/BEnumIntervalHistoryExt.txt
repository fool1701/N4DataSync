/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history.ext;

import java.io.IOException;

import javax.baja.control.BEnumPoint;
import javax.baja.history.BEnumTrendRecord;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * This is an extension for collecting enum values on a fixed interval.
 *
 * @author    John Sublett
 * @creation  23 Nov 2004
 * @version   $Revision: 2$ $Date: 12/7/04 2:48:05 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BEnumIntervalHistoryExt
  extends BIntervalHistoryExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.ext.BEnumIntervalHistoryExt(2979906276)1.0$ @*/
/* Generated Thu Jan 27 19:02:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumIntervalHistoryExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Extensions may only be placed in BControlPoints.
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BEnumPoint;
  }

  /**
   * This extension writes enum trend records. (history:EnumTrendRecord)
   */
  @Override
  public Type getRecordType()
  {
    return BEnumTrendRecord.TYPE;
  }

  /**
   * Init on start.
   */
  @Override
  public void started()
    throws Exception
  {
    super.started();
    rec = new BEnumTrendRecord();
  }

  /**
   * Write a record for the specified timestamp and value.
   */
  @Override
  protected void writeRecord(BAbsTime timestamp, BStatusValue out)
    throws IOException
  {
    append(rec.set(timestamp,
                   ((BStatusEnum)out).getValue(),
                   out.getStatus()));
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BEnumTrendRecord rec;



}
