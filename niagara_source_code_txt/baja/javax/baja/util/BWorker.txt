/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.*;
import javax.baja.sys.*;

/**
 * BWorker is a BComponent wrapper for Worker
 *
 * @author    Brian Frank
 * @creation  6 Feb 04
 * @version   $Revision: 2$ $Date: 8/25/04 8:47:34 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BWorker
  extends BComponent
{                                                      
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BWorker(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWorker.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  /**
   * If not isSteadyStateWorker() then call startWorker().
   */
  @Override
  public void started()
    throws Exception
  {
    super.started();  
    if (!isSteadyStateWorker()) startWorker();
  }

  /**
   * If isSteadyStateWorker() then call startWorker().
   */
  @Override
  public void atSteadyState()
    throws Exception
  {
    super.atSteadyState();
    if (isSteadyStateWorker()) startWorker();
  }
  
  /**
   * Call stopWorker().
   */
  @Override
  public void stopped()
    throws Exception
  {                
    super.stopped(); 
    stopWorker();
  }        
  
  /**
   * Get the Worker this BWorker wraps.
   */
  public abstract Worker getWorker(); 
  
  /**
   * Default implementation is <code>getWorker().start(toPathString())</code>.
   */
  protected void startWorker()
  {
    getWorker().start(getWorkerThreadName());
  }

  /**
   * Default implementation is <code>getWorker().stop()</code>.
   */
  protected void stopWorker()
  {              
    getWorker().stop();
  }
            
  /**
   * Return true if this worker doesn't start until steady
   * state is reached.  Return false to start immediately
   * on the <code>BComponent.started()</code> callback.  The 
   * default returns false.
   */
  protected boolean isSteadyStateWorker()
  {
    return false;
  }              
  
  /**
   * Get the thread name used to start the worker.
   */
  protected String getWorkerThreadName()
  {                                 
    return toPathString();
  }
  
////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////
  
  /**
   * Include worker's spy in diagnostics.
   */        
  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    Worker w = getWorker();
    if (w != null) w.spy(out);
    else { out.startProps("No Worker"); out.endProps(); }
    super.spy(out);
  }  

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("gears.png");     
  
}
