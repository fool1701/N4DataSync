/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BThreadPoolWorker is a BComponent wrapper for ThreadPoolWorker
 *
 * @author    Brian Frank
 * @creation  7 Feb 04
 * @version   $Revision: 3$ $Date: 8/23/06 2:42:43 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Max number of concurrent threads for working.
 */
@NiagaraProperty(
  name = "maxThreads",
  type = "int",
  defaultValue = "4",
  facets = @Facet("BFacets.make(BFacets.MIN, BInteger.make(1))")
)
public abstract class BThreadPoolWorker
  extends BWorker
{                                                      
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BThreadPoolWorker(4048445599)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "maxThreads"

  /**
   * Slot for the {@code maxThreads} property.
   * Max number of concurrent threads for working.
   * @see #getMaxThreads
   * @see #setMaxThreads
   */
  public static final Property maxThreads = newProperty(0, 4, BFacets.make(BFacets.MIN, BInteger.make(1)));

  /**
   * Get the {@code maxThreads} property.
   * Max number of concurrent threads for working.
   * @see #maxThreads
   */
  public int getMaxThreads() { return getInt(maxThreads); }

  /**
   * Set the {@code maxThreads} property.
   * Max number of concurrent threads for working.
   * @see #maxThreads
   */
  public void setMaxThreads(int v) { setInt(maxThreads, v, null); }

  //endregion Property "maxThreads"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BThreadPoolWorker.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BThreadPoolWorker()
  {
  }

  public BThreadPoolWorker(int maxThreads)
  {                                      
    setMaxThreads(maxThreads);
  }                           
  
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  @Override
  public void started()
    throws Exception
  {
    super.started();
    updateMaxThreads();
  }

  @Override
  public void atSteadyState()
    throws Exception
  {
    super.atSteadyState();
    updateMaxThreads();
  }

  @Override
  public void changed(Property prop, Context cx)
  {     
    if (isRunning())
    {
      if (prop.equals(maxThreads)) { updateMaxThreads(); return; }
    }
    super.changed(prop, cx);
  }      
  
  void updateMaxThreads()
  {      
    ThreadPoolWorker w = (ThreadPoolWorker)getWorker();
    if (w != null) w.setMaxThreads(getMaxThreads());
  }

}
