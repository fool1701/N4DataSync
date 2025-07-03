/*
 * Copyright 2015, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;

@NiagaraType
public interface BIAlarmMessages
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.BIAlarmMessages(2979906276)1.0$ @*/
/* Generated Thu Jan 13 17:12:02 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIAlarmMessages.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  public BFormat getToOffnormalText();
  
  public BFormat getToFaultText();
  
  public BFormat getToNormalText();
}
