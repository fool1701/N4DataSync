/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history.ext;

import java.io.IOException;

import javax.baja.control.BBooleanPoint;
import javax.baja.history.BBooleanTrendRecord;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * This is a history extension that monitors a boolean point value and
 * logs records on change of value.
 *
 * @author    John Sublett
 * @creation  19 Nov 2004
 * @version   $Revision: 2$ $Date: 12/7/04 2:48:05 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BBooleanCovHistoryExt
  extends BCovHistoryExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.ext.BBooleanCovHistoryExt(2979906276)1.0$ @*/
/* Generated Thu Jan 27 19:02:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBooleanCovHistoryExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Extensions may only be placed in BControlPoints.
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BBooleanPoint;
  }

  /**
   * This extension writes numeric trend records. (history:NumericTrendRecord)
   */
  @Override
  public Type getRecordType()
  {
    return BBooleanTrendRecord.TYPE;
  }

  @Override
  public void started()
    throws Exception
  {
    super.started();
    rec = new BBooleanTrendRecord();
  }

  /**
   * Write a record for the specified timestamp and value.
   */
  @Override
  protected void writeRecord(BAbsTime timestamp, BStatusValue out)
    throws IOException
  {
    append(rec.set(timestamp, ((BStatusBoolean)out).getValue(), out.getStatus()));
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BBooleanTrendRecord rec;


}
