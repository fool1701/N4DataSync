/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.job;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BRunnableJob provides a simple job implementations 
 * for executing runnable tasks in the job framework.
 *
 * @author    Lee Adcock    
 * @creation  12 April 2010
 * @version   $Revision: 1$ $Date: 4/12/10 10:53:18 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BRunnableJob 
  extends BSimpleJob
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.job.BRunnableJob(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRunnableJob.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  public BRunnableJob(Runnable runnable)
  {
    this.runnable = runnable;
  }
  
  public BRunnableJob()
  {
  }

  public final void run(Context cx)
    throws Exception
  {
    if (runnable!=null)
    {
      runnable.run();
    }
  }

  private Runnable runnable = null;
}
