/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history.ext;

import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BCovHistoryExt provides the basic interface needed to collect
 * history records on change of the parent point.
 *
 * @author    John Sublett
 * @creation  18 Nov 2004
 * @version   $Revision: 3$ $Date: 2/9/05 1:20:22 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BCovHistoryExt
  extends BHistoryExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.ext.BCovHistoryExt(2979906276)1.0$ @*/
/* Generated Thu Jan 27 19:02:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCovHistoryExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  public void activated(BAbsTime startTime, BAbsTime currentTime, BStatusValue value)
    throws IOException
  {
    if (lastValue == null)
      lastValue = (BStatusValue)value.newCopy(true);
    else
      lastValue.copyFrom(value);

    writeRecord(currentTime, value);
  }

  @Override
  public void deactivated(BAbsTime currentTime, BStatusValue value)
    throws IOException
  {
    if (lastValue == null)
      lastValue = (BStatusValue)value.newCopy(true);
    else
      lastValue.copyFrom(value);

    writeRecord(currentTime, value);
  }

  @Override
  public final void pointChanged(BAbsTime timestamp, BStatusValue out)
    throws IOException
  {
    if (lastValue == null)
    {
      lastValue = (BStatusValue)out.newCopy(true);
      writeRecord(timestamp, out);
    }
    else if (isChange(lastValue, out))
    {
      lastValue.copyFrom(out);
      writeRecord(timestamp, out);
    }
  }

  /**
   * Write a record for the specified timestamp and value.
   */
  protected abstract void writeRecord(BAbsTime timestamp, BStatusValue out)
    throws IOException;

  /**
   * Compare the specified value to see if they are significantly different.
   *
   * @param oldValue The old value.
   * @param newValue The new value.
   */
  protected boolean isChange(BStatusValue oldValue, BStatusValue newValue)
  {
    return !oldValue.equivalent(newValue);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BStatusValue lastValue;
}
