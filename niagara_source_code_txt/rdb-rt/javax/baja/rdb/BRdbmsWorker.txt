/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.*;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BRdbmsWorker manages the queue and worker for 
 * asynchronous operations on a single database.
 *
 * @author    John Sublett
 * @creation  18 Feb 2004
 * @version   $Revision: 6$ $Date: 2/22/09 3:21:20 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Slot for the <code>maxThreads</code> property.
 Max number of concurrent threads for working.
 The default is one thread.
 Each thread uses one JDBC Connection to communicate with
 the database, so there will be as many connections created
 as there are threads.
 */
@NiagaraProperty(
  name = "maxThreads",
  type = "int",
  defaultValue = "1",
  facets = @Facet("BFacets.make(BFacets.MIN, BInteger.make(1))"),
  override = true
)
/*
 the size of the working queue
 */
@NiagaraProperty(
  name = "maxQueueSize",
  type = "int",
  defaultValue = "1000",
  facets = @Facet("BFacets.makeInt(null, 1, Integer.MAX_VALUE)")
)
public class BRdbmsWorker
  extends BThreadPoolWorker
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.BRdbmsWorker(29180449)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "maxThreads"

  /**
   * Slot for the {@code maxThreads} property.
   * Slot for the <code>maxThreads</code> property.
   * Max number of concurrent threads for working.
   * The default is one thread.
   * Each thread uses one JDBC Connection to communicate with
   * the database, so there will be as many connections created
   * as there are threads.
   * @see #getMaxThreads
   * @see #setMaxThreads
   */
  public static final Property maxThreads = newProperty(0, 1, BFacets.make(BFacets.MIN, BInteger.make(1)));

  //endregion Property "maxThreads"

  //region Property "maxQueueSize"

  /**
   * Slot for the {@code maxQueueSize} property.
   * the size of the working queue
   * @see #getMaxQueueSize
   * @see #setMaxQueueSize
   */
  public static final Property maxQueueSize = newProperty(0, 1000, BFacets.makeInt(null, 1, Integer.MAX_VALUE));

  /**
   * Get the {@code maxQueueSize} property.
   * the size of the working queue
   * @see #maxQueueSize
   */
  public int getMaxQueueSize() { return getInt(maxQueueSize); }

  /**
   * Set the {@code maxQueueSize} property.
   * the size of the working queue
   * @see #maxQueueSize
   */
  public void setMaxQueueSize(int v) { setInt(maxQueueSize, v, null); }

  //endregion Property "maxQueueSize"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRdbmsWorker.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BRdbmsWorker()
  {
    super();
  }

  public BRdbmsWorker(int maxThreads)
  {                                      
    super(maxThreads);
  }                           
  
////////////////////////////////////////////////////////////////
// BWorker
////////////////////////////////////////////////////////////////
  
  /**
   * Post an action to be run asynchronously.
   */
  public IFuture postAsync(Runnable r)
  {         
    if (!isRunning() || queue == null)
      throw new NotRunningException();
    queue.enqueue(r);
    return null;
  }

  /**
   * Start running this task.
   */
  public Worker getWorker()
  {        
    if (worker == null) 
    {
      // Issue 8925 - Expose the max queue size as a property
      // so that the user can adjust it as needed.
      queue = new CoalesceQueue(getMaxQueueSize());
      worker = new ThreadPoolWorker(queue);
    }
    return worker;
  }

  protected String getWorkerThreadName()
  {                
    return "RdbmsWorker:" + getParent().getName();
  }

////////////////////////////////////////////////////////////////
// BComponent Overrides
////////////////////////////////////////////////////////////////

  /**
   * Callback when a property (or possibly a ancestor of
   * that property) is modified on this component.
   */
  public void changed(Property property, Context context)
  {
    super.changed(property, context);
    
    // Issue 8925 - Since we now expose the max queue size as a property,
    // trap user changes to the property so that we can reinitialize the queue.
    if (isRunning() && property.equals(maxQueueSize) && (queue != null))
    {
      stopWorker();
      queue = null;
      worker = null;
      getWorker();
      startWorker();
    }
  }
  
////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out)
    throws Exception
  {
    getWorker().spy(out);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  CoalesceQueue queue;
  ThreadPoolWorker worker;
}
