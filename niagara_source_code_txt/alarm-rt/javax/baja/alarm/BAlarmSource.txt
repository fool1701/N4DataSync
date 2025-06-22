/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.alarm;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * An AlarmSource represents a source for in the Niagara alarm database.  It
 * is a wrapper for the alarm source ord list and can be used as the target
 * of agent relationships.
 * 
 * @author    John Sublett
 * @creation  05 Jan 2011
 * @version   $Revision: 2$ $Date: 1/11/11 2:32:18 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BAlarmSource
  extends BObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BAlarmSource(2979906276)1.0$ @*/
/* Generated Thu Jan 13 17:12:01 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmSource.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BAlarmSource(BOrdList sourceOrdList, BAlarmRecord currentAlarm)
  {
    this.sourceOrdList = sourceOrdList;
    this.currentAlarm = currentAlarm;
  }
  
  public BOrdList getSourceOrdList()
  {
    return sourceOrdList;
  }
  
  public BAlarmRecord getCurrentAlarm()
  {
    return currentAlarm;
  }
  
  public void setCurrentAlarm(BAlarmRecord rec)
  {
    currentAlarm = rec;
  }
  
  public int hashCode()
  {
    return sourceOrdList.hashCode();
  }
  
  public boolean equals(Object other)
  {
    if (!(other instanceof BAlarmSource))
      return false;
    
    return ((BAlarmSource)other).sourceOrdList.equals(sourceOrdList);
  }
  
  @Override
  public String toString(Context cx)
  {
    return currentAlarm.toString(cx);
  }

////////////////////////////////////////////////////////////////
// Comparator
////////////////////////////////////////////////////////////////

  public static class SourceComparator
    implements java.util.Comparator<BAlarmSource>
  {
    @Override
    public int compare(BAlarmSource o1, BAlarmSource o2)
    {
      return o1.getCurrentAlarm().getTimestamp()
        .compareTo(o2.getCurrentAlarm().getTimestamp());
    }
  }

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private BOrdList     sourceOrdList;
  private BAlarmRecord currentAlarm;
}
