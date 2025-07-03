/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.job;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * IJobService provides a common interface used for managing jobs
 * consistently in both a station and workbench environment.
 *
 * @author    Brian Frank       
 * @creation  30 Apr 03
 * @version   $Revision: 3$ $Date: 3/28/05 9:22:58 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIJobService
  extends BInterface  
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.job.BIJobService(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIJobService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get all the child jobs under this service.
   */
  public BJob[] getJobs();

  /**
   * Submit a job and run it!
   */
  public BOrd submit(BJob job, Context cx);
}
