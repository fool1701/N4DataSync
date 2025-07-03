/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.history;

import javax.baja.driver.util.BPollFrequency;
import javax.baja.driver.util.BPollScheduler;
import javax.baja.history.BHistoryConfig;
import javax.baja.history.BIPollableHistorySource;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BComponentSpace;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BHistoryImport defines an archive action for transferring
 * one or more histories from a remote source to the local
 * destination.
 *
 * @author    John Sublett
 * @creation  31 Mar 2003
 * @version   $Revision: 14$ $Date: 11/3/09 4:41:52 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Enables on demand polling for this history import.
 @since Niagara 3.4
 */
@NiagaraProperty(
  name = "onDemandPollEnabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 Poll frequency bucket, determines the polling rate.
 @since Niagara 3.4
 */
@NiagaraProperty(
  name = "onDemandPollFrequency",
  type = "BPollFrequency",
  defaultValue = "BPollFrequency.normal"
)
/*
 Defines the configuration of the history that
 is created at the destination as a set of
 overrides from the default.
 */
@NiagaraProperty(
  name = "configOverrides",
  type = "BComponent",
  defaultValue = "new BComponent()"
)
public abstract class BHistoryImport
  extends BArchiveDescriptor
  implements BIHistoryPollable, BIPollableHistorySource
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.history.BHistoryImport(4038767243)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "onDemandPollEnabled"

  /**
   * Slot for the {@code onDemandPollEnabled} property.
   * Enables on demand polling for this history import.
   * @since Niagara 3.4
   * @see #getOnDemandPollEnabled
   * @see #setOnDemandPollEnabled
   */
  public static final Property onDemandPollEnabled = newProperty(0, true, null);

  /**
   * Get the {@code onDemandPollEnabled} property.
   * Enables on demand polling for this history import.
   * @since Niagara 3.4
   * @see #onDemandPollEnabled
   */
  public boolean getOnDemandPollEnabled() { return getBoolean(onDemandPollEnabled); }

  /**
   * Set the {@code onDemandPollEnabled} property.
   * Enables on demand polling for this history import.
   * @since Niagara 3.4
   * @see #onDemandPollEnabled
   */
  public void setOnDemandPollEnabled(boolean v) { setBoolean(onDemandPollEnabled, v, null); }

  //endregion Property "onDemandPollEnabled"

  //region Property "onDemandPollFrequency"

  /**
   * Slot for the {@code onDemandPollFrequency} property.
   * Poll frequency bucket, determines the polling rate.
   * @since Niagara 3.4
   * @see #getOnDemandPollFrequency
   * @see #setOnDemandPollFrequency
   */
  public static final Property onDemandPollFrequency = newProperty(0, BPollFrequency.normal, null);

  /**
   * Get the {@code onDemandPollFrequency} property.
   * Poll frequency bucket, determines the polling rate.
   * @since Niagara 3.4
   * @see #onDemandPollFrequency
   */
  public BPollFrequency getOnDemandPollFrequency() { return (BPollFrequency)get(onDemandPollFrequency); }

  /**
   * Set the {@code onDemandPollFrequency} property.
   * Poll frequency bucket, determines the polling rate.
   * @since Niagara 3.4
   * @see #onDemandPollFrequency
   */
  public void setOnDemandPollFrequency(BPollFrequency v) { set(onDemandPollFrequency, v, null); }

  //endregion Property "onDemandPollFrequency"

  //region Property "configOverrides"

  /**
   * Slot for the {@code configOverrides} property.
   * Defines the configuration of the history that
   * is created at the destination as a set of
   * overrides from the default.
   * @see #getConfigOverrides
   * @see #setConfigOverrides
   */
  public static final Property configOverrides = newProperty(0, new BComponent(), null);

  /**
   * Get the {@code configOverrides} property.
   * Defines the configuration of the history that
   * is created at the destination as a set of
   * overrides from the default.
   * @see #configOverrides
   */
  public BComponent getConfigOverrides() { return (BComponent)get(configOverrides); }

  /**
   * Set the {@code configOverrides} property.
   * Defines the configuration of the history that
   * is created at the destination as a set of
   * overrides from the default.
   * @see #configOverrides
   */
  public void setConfigOverrides(BComponent v) { set(configOverrides, v, null); }

  //endregion Property "configOverrides"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryImport.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BHistoryImport()
  {
  }

  /**
   * The specified parent is only legal if it or one
   * of its ancestors is a IArchiveFolder that
   * supports import.
   */
  public boolean isParentLegal(BComponent parent)
  {
    while ((parent != null) && !(parent instanceof BIArchiveFolder))
      parent = (BComponent)parent.getParent();
    
    if (parent == null) return false;
    
    try
    {
      return ((BIArchiveFolder)parent).getImportDescriptorType() != null;
    }
    catch(IllegalStateException e) { return true; }
  }

  /**
   * Create a local history configuration based on the specified
   * remote configuration.
   */
  public BHistoryConfig makeLocalConfig(BHistoryConfig remoteConfig)
  {
    BHistoryConfig newConfig = (BHistoryConfig)remoteConfig.newCopy(true);
    BComponent configChanges = getConfigOverrides();
    Property[] props = configChanges.getPropertiesArray();
    for (int i = 0; i < props.length; i++)
    {
      Property changeProp = props[i];
      BValue changeValue = configChanges.get(changeProp).newCopy(true);
      Property configProp = newConfig.getProperty(changeProp.getName());
      if (configProp == null)
        newConfig.add(changeProp.getName(), changeValue, configChanges.getFlags(changeProp), configChanges.getSlotFacets(changeProp), null);
      else
        newConfig.set(configProp, changeValue);
    }
    
    try
    {
      BOrd ord = getSourceOrd();
      BOrdList src = newConfig.getSource();
      if (src.size() < 1)
        newConfig.setSource(BOrdList.make(ord));
      else if (!src.get(src.size()-1).equals(ord))
      {
        BOrdList newSrc = BOrdList.add(src, ord);
        newConfig.setSource(newSrc);
      }
    }
    catch (Exception e) { e.printStackTrace(); }
    
    return newConfig;
  }
  
  /**
   * Get the ord to use as the source for the history.
   */
  private BOrd getSourceOrd()
  {
    BComponentSpace cs = getComponentSpace();
    if (cs == null) return getSlotPathOrd();
    BOrd base = cs.getOrdInSession();
    if (base == null) return getSlotPathOrd();
    return BOrd.make(base, getSlotPathOrd());
  }
  
  /**
   * Unsubscribed causes unregistration from the history poll scheduler
   * 
   * @since Niagara 3.4
   */
  public void unsubscribed()
  {
    synchronized(syncObj)
    {
      historySubscribeCounter = 0;
      if (ext != null)
        ext.getOnDemandPollScheduler().unsubscribe(this);
    }    
  }

////////////////////////////////////////////////////////////////
// BIHistoryPollable
////////////////////////////////////////////////////////////////

  /**
   * Callback when the history poll scheduler says its time to
   * poll the history.
   * 
   * @since Niagara 3.4
   */
  public void poll()
  {
    if (getOnDemandPollEnabled() && (!isUnoperational()) && (historySubscribeCounter > 0))
    {
      // invoke with a context to indicate a poll request
      invoke(execute,null,asyncHistoryPoll);
    }
  }

////////////////////////////////////////////////////////////////
// BIPollableHistorySource
////////////////////////////////////////////////////////////////  
  
  /**
   * Returns true if this object supports history polling (subscription), 
   * or false if it does not (does not support subscription).
   *
   * @since Niagara 3.4
   */
  public boolean historyPollingEnabled()
  {
    if (!getOnDemandPollEnabled()) return false;
    BHistoryNetworkExt historyExt = getHistoryNetworkExt();
    if (historyExt == null) return false;
    BPollScheduler scheduler = historyExt.getOnDemandPollScheduler();
    if (!isRunning()) scheduler.lease();
    return scheduler.getPollEnabled();
  }
    
  /**
   * This callback is made to cause the history subscription counter for this
   * history import to be incremented (positive change value) or decremented 
   * (negative change value).  The returning value is the current history 
   * subscription counter value after the increment/decrement 
   * has been processed.
   *
   * @since Niagara 3.4
   */
  public int updateHistorySubscriptionCount(int change)
  {
    int result = 0;
    boolean syncExecute = false;
    synchronized(syncObj)
    {
      int oldCount = historySubscribeCounter;
      historySubscribeCounter += change;
      if (historySubscribeCounter < 0) // paranoia
        historySubscribeCounter = 0;
      else if ((oldCount == 0) && (historySubscribeCounter > 0) && isRunning())
      {  
        ext = getHistoryNetworkExt();
        if (ext != null)
        {
          BPollScheduler scheduler = ext.getOnDemandPollScheduler();
          if (getOnDemandPollEnabled() &&
              (!isUnoperational()) && 
              scheduler.getPollEnabled() && 
              (historySubscribeCounter > 0))
          {
            syncExecute = true;
          }
          scheduler.subscribe(this);
        }
      }
      
      if ((historySubscribeCounter == 0) && (ext != null))
        ext.getOnDemandPollScheduler().unsubscribe(this);
      
      result = historySubscribeCounter;
    }
    
    if (syncExecute) // invoke with a context to indicate a poll request
      invoke(execute,null,syncHistoryPoll);
    
    return result;
  }
  
////////////////////////////////////////////////////////////////
// BIPollable
////////////////////////////////////////////////////////////////

  /**
   * Get the configured poll frequency.
   *
   * @since Niagara 3.4
   */
  public BPollFrequency getPollFrequency()
  {
    return getOnDemandPollFrequency();
  }

////////////////////////////////////////////////////////////////
//Spy
////////////////////////////////////////////////////////////////

  /**
   * Overridden to be sure to include spy info about the 
   * history subscription status.
   * 
   * @since Niagara 3.4
   */
  public void spy(SpyWriter out)
  throws Exception
  {
    // First append virtual space info
    out.startProps();
    out.trTitle("HistoryImport", 2);
    out.prop("historySubscriptionCount", ""+historySubscribeCounter);
    out.endProps();
    super.spy(out);
  }  
  
  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make
    ("module://driver/com/tridium/driver/ui/history/importHistory.png");
  
  /**
   * This instance of context is passed when the execute
   * action is invoked due to an on-demand poll, and it should be
   * handled asynchronously.  In some special cases,
   * subclasses may want to handle the postExecute() differently
   * based on whether the execute() was called due to a normal archive,
   * or due to an on-demand poll.
   * 
   * @since Niagara 3.4
   */
  public static final Context asyncHistoryPoll = new BasicContext()
  {
    public boolean equals(Object obj) { return this == obj; }
    public int hashCode() { return System.identityHashCode(this); }
    public String toString() { return "Context.asyncHistoryPoll"; }
  };
  
  /**
   * This instance of context is passed when the execute
   * action is invoked due to a history subscribe, and it should be
   * handled synchronously.  In some special cases,
   * subclasses may want to handle the postExecute() differently
   * based on whether the execute() was called due to a normal archive,
   * or due to a history subscribe.
   * 
   * @since Niagara 3.4
   */
  public static final Context syncHistoryPoll = new BasicContext()
  {
    public boolean equals(Object obj) { return this == obj; }
    public int hashCode() { return System.identityHashCode(this); }
    public String toString() { return "Context.syncHistoryPoll"; }
  };
  
  BHistoryNetworkExt ext = null;
  int historySubscribeCounter = 0;
  Object syncObj = new Object();
}
