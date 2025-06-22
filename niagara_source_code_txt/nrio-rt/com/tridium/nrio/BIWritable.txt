/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

@NiagaraType
public interface BIWritable
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.BIWritable(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:13 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIWritable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * This callback is made when a write is desired based on the
   * current status and tuning.  The value to write is the current
   * value of the writeValue property.  Any IO should be done 
   * asynchronously on another thread - never block the calling 
   * thread.  If the write is enqueued then return true and call 
   * writeOk() or writeFail() once it has been processed.  If the 
   * write is canceled immediately for other reasons then return false. 
   *
   * @return true if a write is now pending
   */
   public void writeData(BStatusValue out);
  
  

}
