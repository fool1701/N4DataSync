/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.point;

import java.util.Comparator;

import javax.baja.driver.BDeviceNetwork;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BTuningPolicyMap is the standard container used to store a 
 * network's list of tuning policies.  ITunables can select
 * which of these policies to use by name.  Driver developers
 * must add a frozen slot called "tuningPolicies" on their 
 * DeviceNetwork. 
 *
 * @author    Brian Frank       
 * @creation  17 Jun 04
 * @version   $Revision: 8$ $Date: 1/31/05 5:27:31 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 This is the policy used when no other policy is configured.
 */
@NiagaraProperty(
  name = "defaultPolicy",
  type = "BTuningPolicy",
  defaultValue = "new BTuningPolicy()"
)
public class BTuningPolicyMap
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.point.BTuningPolicyMap(810430779)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "defaultPolicy"

  /**
   * Slot for the {@code defaultPolicy} property.
   * This is the policy used when no other policy is configured.
   * @see #getDefaultPolicy
   * @see #setDefaultPolicy
   */
  public static final Property defaultPolicy = newProperty(0, new BTuningPolicy(), null);

  /**
   * Get the {@code defaultPolicy} property.
   * This is the policy used when no other policy is configured.
   * @see #defaultPolicy
   */
  public BTuningPolicy getDefaultPolicy() { return (BTuningPolicy)get(defaultPolicy); }

  /**
   * Set the {@code defaultPolicy} property.
   * This is the policy used when no other policy is configured.
   * @see #defaultPolicy
   */
  public void setDefaultPolicy(BTuningPolicy v) { set(defaultPolicy, v, null); }

  //endregion Property "defaultPolicy"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTuningPolicyMap.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Ge the network (it should be the direct parent).
   */
  public final BDeviceNetwork getNetwork()
  {                          
    return (BDeviceNetwork)getParent();
  }  
  
  /**
   * Get the list of child policies.
   */
  public final BTuningPolicy[] getPolicies()
  {
    return getChildren(BTuningPolicy.class);
  }
    
  /**
   * Only TuningPolicy children allowed.
   */
  public boolean isChildLegal(BComponent c)
  {
    return c instanceof BTuningPolicy;
  }                       
  
////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////
  
  /**
   * Started callback.
   */
  public void started()
    throws Exception
  {                 
    super.started();
    startThread();
  }  
  
  /**
   * Stopped callback.
   */
  public void stopped()
    throws Exception
  {                  
    super.stopped();
    stopThread();
  }                   

////////////////////////////////////////////////////////////////
// Background Processing
////////////////////////////////////////////////////////////////

  /**
   * Add to list for background processing.
   */
  int register(Tuning tuning)
  {          
    synchronized(lock)
    {     
      // check for empty slot (never use 0)
      for(int i=1; i<tunings.length; ++i)
        if (tunings[i] == null)
          { tunings[i] = tuning; return i; }
      
      // expand array                                       
      int oldLength = tunings.length;
      Tuning[] temp = new Tuning[oldLength*2];
      System.arraycopy(tunings, 0, temp, 0, oldLength);
      tunings = temp;
      tunings[oldLength] = tuning;
      return oldLength;
    }
  }         
  
  /**
   * Start background thread.
   */
  void startThread()
  {
    stopThread();   
    thread = new BackgroundThread(getNetwork());
    thread.start();
  }

  /**
   * Stop background thread.
   */
  void stopThread()
  {                
    if (thread != null) thread.kill();
    thread = null;
  }             
  
  /**
   * Walk through all the BTuningPolicies to compute the quickest 
   * scan time needed.  This is half of the smallest time found in 
   * any of the child policies.  This value is capped between 20sec 
   * and 200ms.
   */
  long computeScanFrequency()
  {                 
    // always scan every at least 20sec                       
    long t = maxScan;                                  
    
    // see if we have any policies which require faster scan
    BTuningPolicy[] policies = getPolicies();
    for(int i=0; i<policies.length; ++i)
    {            
      BTuningPolicy policy = policies[i];
      long minWrite = policy.getMinWriteTime().getMillis();
      long maxWrite = policy.getMaxWriteTime().getMillis();
      long stale    = policy.getStaleTime().getMillis();
      if (minWrite > 0) t = Math.min(t, minWrite/2);
      if (maxWrite > 0) t = Math.min(t, maxWrite/2);
      if (stale    > 0) t = Math.min(t, stale/2);
    }                   
    
    // throttle to 200ms            
    t = Math.max(t, minScan);
  
    return t;
  }
  
  /**
   * The background thread is responsible for giving 
   * each registered BTuning a processing hook.
   */
  class BackgroundThread extends Thread
  {                     
    BackgroundThread(BDeviceNetwork network) 
    {
      super("Tuning:" + network.getName());
      this.network = network;
    }     
    
    void kill()
    {
      isAlive = false;
      interrupt();
    }             
    
    public void run()
    {                   
      while(isAlive)
      {
        try
        {               
          // re-compute scan frequency in case any policies have 
          // changed, and then sleep until ready
          scanFrequency = computeScanFrequency(); 
          Thread.sleep(scanFrequency); 
          
          // check if network finished starting its ancestors,
          // otherwise don't process because it just drags out
          // the station startup process 
          if (!network.isDescendantsStarted())
            continue;
          
          // process with local var
          long t1 = Clock.ticks();          
          Tuning[] temp = tunings;         
          for(int i=0; i<temp.length; ++i)
            process(temp, i);
          
          // update stats
          long t2 = Clock.ticks();      
          scanCount++;
          lastScanDuration = t2-t1;
        }
        catch(Throwable e)
        {
          if (isAlive) e.printStackTrace();
        }
      }
    }           
    
    void process(Tuning[] list, int index)
    {                   
      Tuning t = list[index];
      if (t == null) return;
      try
      {                                             
        // invoke process callback
        boolean ok = t.process();
        
        // if not ok anymore remove from processing list
        if (!ok)
        {
          synchronized(lock)
          {
            tunings[index] = null;
          }
        }
      }
      catch(Throwable e)
      {
        e.printStackTrace();
      }
    }
    
    BDeviceNetwork network;
    boolean isAlive = true;
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out)
      throws Exception
  {            
    // get offline copy of list   
    Array<Tuning> a = new Array<>(Tuning.class);
    for(int i=0; i<tunings.length; ++i)
      if (tunings[i] != null) a.add(tunings[i]);

    // performance  
    out.startProps("Tuning Performance");     
    out.prop("scanFrequency",    BRelTime.toString(scanFrequency));
    out.prop("scanCount",        ""+scanCount);
    out.prop("lastScanDuration", BRelTime.toString(lastScanDuration));
    out.prop("proxyPointCount",  ""+a.size());
    out.endProps();     
    
    // standard spy
    super.spy(out);         
    
    // it's possible to have 100,000s of points registered, 
    // which takes a really long time to sort (so skip this if
    // we aren't going to display them all)
    int max = 1000;
    if (a.size() < max)
    {
      // sort
      a = a.sort(new Comparator<Tuning>()
      {  
        public int compare(Tuning x, Tuning y)
        {                                     
          BComponent xc = (BComponent)x.getTunable();
          BComponent yc = (BComponent)y.getTunable();
          String xs = xc.toPathString();
          String ys = yc.toPathString();
          return xs.compareTo(ys);
        }
      });
    }

    // write proxy point list
    out.startTable(true);
    out.trTitle("Proxy Point List [" + a.size() + "]", 3);     
    out.w("<tr>")
      .th("Id")
      .th("Path")
      .th("Type")
      .w("</tr>\n");
    for(int i=0; i<a.size() && i<max; ++i)
    {                    
      Tuning t = a.get(i);
      BComponent p = (BComponent)t.getTunable();
      out.tr(""+t.processId(), p.toPathString(), p.getType());
    }                            
    if (a.size() >= max)
      out.tr("" + (a.size()-max) + " more points...", "");
    out.endTable();
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("wrench.png");  
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  // configurable by test code
  static long minScan = 200;
  static long maxScan = 20000;

  BackgroundThread thread;   
  Object lock = new Object();
  Tuning[] tunings = new Tuning[100];   
  long scanFrequency = 1000;
  int scanCount;
  long lastScanDuration;

}
