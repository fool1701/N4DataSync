 /*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import java.util.logging.Level;

import javax.baja.driver.point.BITunable;
import javax.baja.driver.point.BReadWriteMode;
import javax.baja.driver.point.BTuningPolicy;
import javax.baja.driver.point.Tuning;
import javax.baja.driver.util.BPollFrequency;
import javax.baja.lonworks.datatypes.BNvConfigData;
import javax.baja.lonworks.datatypes.BNvProps;
import javax.baja.lonworks.enums.BLonNvDirection;
import javax.baja.lonworks.enums.BLonServiceType;
import javax.baja.lonworks.londata.BLonData;
import javax.baja.lonworks.tuning.BLonTuningPolicy;
import javax.baja.lonworks.util.SnvtUtil;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.*;

import com.tridium.lonworks.Lon;
import com.tridium.lonworks.device.DeviceFacets;
import com.tridium.lonworks.device.DynaDev;
import com.tridium.lonworks.util.NmUtil;

 /**
 *  BNetworkVariable represents a single network variable
 *  in a LonDevice. It provides specific support for runtime
 *  updates and contains data needed to support network managment.
 * <p>
 *
 * @author    Robert Adams
 * @creation  8 Nov 00
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:44 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Properties needed to manage the nv.
 */
@NiagaraProperty(
  name = "nvProps",
  type = "BNvProps",
  defaultValue = "new BNvProps()"
)
/*
 Shadows data in the devices nv config table.
 */
@NiagaraProperty(
  name = "nvConfigData",
  type = "BNvConfigData",
  defaultValue = "new BNvConfigData()"
)
/*
 Tuning manages which TuningPolicy to  use for
 reads and writes on this point.
 */
@NiagaraProperty(
  name = "tuningPolicyName",
  type = "String",
  defaultValue = "defaultPolicy",
  facets = @Facet("TUNING_POLICY_NAME_FACETS")
)
@NiagaraAction(
  name = "nvUpdate",
  flags = Flags.HIDDEN
)
/*
 Fired in receiveUpdate after all elements updated.
 Added in 3.6.27 and 3.5.35
 */
@NiagaraTopic(
  name = "receivedUpdate"
)
public class BNetworkVariable
  extends BLonComponent
  implements BINetworkVariable, BITunable, javax.baja.driver.util.BIPollable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BNetworkVariable(1606266167)1.0$ @*/
/* Generated Mon Nov 21 08:50:24 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "nvProps"

  /**
   * Slot for the {@code nvProps} property.
   * Properties needed to manage the nv.
   * @see #getNvProps
   * @see #setNvProps
   */
  public static final Property nvProps = newProperty(0, new BNvProps(), null);

  /**
   * Get the {@code nvProps} property.
   * Properties needed to manage the nv.
   * @see #nvProps
   */
  public BNvProps getNvProps() { return (BNvProps)get(nvProps); }

  /**
   * Set the {@code nvProps} property.
   * Properties needed to manage the nv.
   * @see #nvProps
   */
  public void setNvProps(BNvProps v) { set(nvProps, v, null); }

  //endregion Property "nvProps"

  //region Property "nvConfigData"

  /**
   * Slot for the {@code nvConfigData} property.
   * Shadows data in the devices nv config table.
   * @see #getNvConfigData
   * @see #setNvConfigData
   */
  public static final Property nvConfigData = newProperty(0, new BNvConfigData(), null);

  /**
   * Get the {@code nvConfigData} property.
   * Shadows data in the devices nv config table.
   * @see #nvConfigData
   */
  public BNvConfigData getNvConfigData() { return (BNvConfigData)get(nvConfigData); }

  /**
   * Set the {@code nvConfigData} property.
   * Shadows data in the devices nv config table.
   * @see #nvConfigData
   */
  public void setNvConfigData(BNvConfigData v) { set(nvConfigData, v, null); }

  //endregion Property "nvConfigData"

  //region Property "tuningPolicyName"

  /**
   * Slot for the {@code tuningPolicyName} property.
   * Tuning manages which TuningPolicy to  use for
   * reads and writes on this point.
   * @see #getTuningPolicyName
   * @see #setTuningPolicyName
   */
  public static final Property tuningPolicyName = newProperty(0, "defaultPolicy", TUNING_POLICY_NAME_FACETS);

  /**
   * Get the {@code tuningPolicyName} property.
   * Tuning manages which TuningPolicy to  use for
   * reads and writes on this point.
   * @see #tuningPolicyName
   */
  public String getTuningPolicyName() { return getString(tuningPolicyName); }

  /**
   * Set the {@code tuningPolicyName} property.
   * Tuning manages which TuningPolicy to  use for
   * reads and writes on this point.
   * @see #tuningPolicyName
   */
  public void setTuningPolicyName(String v) { setString(tuningPolicyName, v, null); }

  //endregion Property "tuningPolicyName"

  //region Action "nvUpdate"

  /**
   * Slot for the {@code nvUpdate} action.
   * @see #nvUpdate()
   */
  public static final Action nvUpdate = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code nvUpdate} action.
   * @see #nvUpdate
   */
  public void nvUpdate() { invoke(nvUpdate, null, null); }

  //endregion Action "nvUpdate"

  //region Topic "receivedUpdate"

  /**
   * Slot for the {@code receivedUpdate} topic.
   * Fired in receiveUpdate after all elements updated.
   * Added in 3.6.27 and 3.5.35
   * @see #fireReceivedUpdate
   */
  public static final Topic receivedUpdate = newTopic(0, null);

  /**
   * Fire an event for the {@code receivedUpdate} topic.
   * Fired in receiveUpdate after all elements updated.
   * Added in 3.6.27 and 3.5.35
   * @see #receivedUpdate
   */
  public void fireReceivedUpdate(BValue event) { fire(receivedUpdate, event, null); }

  //endregion Topic "receivedUpdate"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNetworkVariable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  /**
   * No arg constructor
   */
  public BNetworkVariable()
  {
  }

  /**
   *  constructor
   */
  public BNetworkVariable( int              nvIndex    ,
                           int              snvtType   ,
                           int              objectIndex,
                           int              memberIndex,
                           int 						  flags      ,
                           BLonNvDirection  direction   )
  {
  	BNvProps  nvProps = getNvProps();
  	nvProps.setNvIndex    (nvIndex    );
  	nvProps.setSnvtType   (snvtType   );
  	nvProps.setObjectIndex(objectIndex);
  	nvProps.setMemberIndex(memberIndex);

  	BNvConfigData nvCfg = getNvConfigData();
  	nvCfg.setDirection  (direction  );

  	setFlags(nvProps, nvCfg, flags);

    BLonData ld = SnvtUtil.getLonData(snvtType);
    DynaDev.setNonCritical(ld);
    setData(ld);

  }
  /**
   *  constructor
   */
  public BNetworkVariable( int              nvIndex    ,
                           BLonData         data       ,
                           int              objectIndex,
                           int              memberIndex,
                           int 						  flags      ,
                           BLonNvDirection  direction   )
  {
  	BNvProps  nvProps = getNvProps();
  	nvProps.setNvIndex    (nvIndex    );
  	nvProps.setSnvtType   (-1   );
  	nvProps.setObjectIndex(objectIndex);
  	nvProps.setMemberIndex(memberIndex);

  	BNvConfigData nvCfg = getNvConfigData();
  	nvCfg.setDirection  (direction  );

  	setFlags(nvProps, nvCfg, flags);

  	setData(data);

  }

  public static void setFlags(BNvProps  nvProps, BNvConfigData nvCfg, int flags)
  {
    if( (flags & BINvContainer.POLLED                   )>0 ) nvProps.setPolled(true);
    if( (flags & BINvContainer.AUTH_NOT_CONFIGURABLE    )>0 ) nvProps.setAuthConf(false);
    if( (flags & BINvContainer.SERVICE_NOT_CONFIGURABLE )>0 ) nvProps.setServiceConf(false);
    if( (flags & BINvContainer.PRIORITY_CONFIGURABLE    )>0 ) nvProps.setPriorityConf(true);
    if( (flags & BINvContainer.AUTHENTICATE             )>0 ) nvCfg.setAuthenticated(true);
    if( (flags & BINvContainer.PRIORITY                 )>0 ) nvCfg.setPriority(true);
    if( (flags & BINvContainer.SERVICE_ACKED            )>0 ) nvCfg.setServiceType(BLonServiceType.acked);
    if( (flags & BINvContainer.SERVICE_UNACKED_RPT      )>0 ) nvCfg.setServiceType(BLonServiceType.unackedRpt);
  }

////////////////////////////////////////////////////////////////
// Api
////////////////////////////////////////////////////////////////
  /** @return always true */
  public boolean isNetworkVariable() { return true; }

  /** Get the index of this nv in the lonworks device. */
  public int getNvIndex() { return getNvProps().getNvIndex(); }

  /** Set the index of this nv in the lonworks device. */
  public void setNvIndex(int nvIndex) { getNvProps().setNvIndex(nvIndex); }

  /** Get the snvt type. If not a snvt return 0. */
  public int getSnvtType() { return getNvProps().getSnvtType(); }

  /**
   * Set nv to unbound state. Convience method to call
   * nvProps.setUnbound(), nvConfigData.setUnbound() and unbound().
   */
  public void setUnbound()
  {
    int sel = getNvConfigData().getSelector();
    getNvConfigData().setUnbound(getNvProps().getNvIndex());
    getNvProps().setUnbound();
    unbound(sel);
 }

  /** Callback during bind process when nv taken to unbound state.  */
  public void unbound(int sel)
  {
    if(getNvConfigData().isOutput()) // this flag may already be cleared && getNvProps().getBoundToLocal())
      unregisterSelector(sel);

    evaluatePollSubscribe();

    // pass call to device
    try{ getDevice().unbound(getNvProps().getNvIndex()); } catch(Throwable e){}
  }

  /** Callback during bind process when nv taken to bound state. */
  public void bound()
  {
    evaluatePollSubscribe();

    // always register if bound to local flag set
    if(getNvConfigData().isOutput() && getNvProps().getBoundToLocal())
      registerSelector();

    // pass call to device
    try{ getDevice().bound(getNvProps().getNvIndex()); } catch(Throwable e){}
  }

  public void lonComponentStarted()
  {
    // If selector still at default (-1) set to unbound
    if(getNvConfigData().getSelector()==-1)
       getNvConfigData().setUnbound(getNvProps().getNvIndex());


    getTuning().transition();
    if(getNvConfigData().isOutput() && getNvProps().getBoundToLocal())
      registerSelector();
    evaluateLinkedState();

    if(Sys.atSteadyState()) evaluatePollSubscribe();
  }

  public void lonComponentStopped()
  {
    getTuning().transition();
    if(getNvConfigData().isOutput() && getNvProps().getBoundToLocal())
      unregisterSelector(getNvConfigData().getSelector());

    lonNetwork().getPollService().unsubscribe(this);
  //  evaluatePollSubscribe();

    linksToPropagate = null;
  }

  public void atSteadyState()
    throws Exception
  {
    super.atSteadyState();

// FIXX remove - clear boundToLocal in inputs
if(getNvConfigData().isInput()) getNvProps().setBoundToLocal(false);

    // Push the value of unbound links
    if(getNvConfigData().isOutput()) propagateLinks();

    getTuning().transition();

    evaluatePollSubscribe();

  }

  /** Does this nv require a priority slot (i.e. bound, ouput nv, with priority bit set) */
  public boolean requiresPrioritySlot()
  {
    BNvConfigData nvCfg = getNvConfigData();
    if(nvCfg.getPriority() && !nvCfg.isInput() && nvCfg.isBoundNv())
       return true;

    return false;
  }

  private void registerSelector()
  {
    if(registered) return;
   // System.out.println("registerSelector " + getDevice().getDisplayName(null) +":" + getDisplayName(null) );
    lonNetwork().nvManager().registerSelector(getNvConfigData().getSelector(), this, getDevice());
    registered = true;
  }
  private void unregisterSelector(int sel)
  {
    if(!registered) return;
   //System.out.println("unregisterSelector " + getDevice().getDisplayName(null) +":" + getDisplayName(null) );
    lonNetwork().nvManager().unregisterSelector(sel, this, getDevice());
    registered = false;
  }
  private boolean registered = false;

  void reregisterSelector()
  {
    if(registered)
    {
      int sel = getNvConfigData().getSelector();
      lonNetwork().nvManager().unregisterSelector(sel, this, getDevice());
      lonNetwork().nvManager().registerSelector(sel, this, getDevice());
    }
  }
////////////////////////////////////////////////////////////////
// BIPollable
////////////////////////////////////////////////////////////////
  /** Convenience method to get poll frequency from tunning policy. */
  public BPollFrequency getPollFrequency()
  {
    return ((BLonTuningPolicy)getTuning().getPolicy()).getPollFrequency();
  }

  /**
   * Convenience method to get writeDelay from tunning policy.
   * Added 3.7.30
   */
  public int getWriteDelay()
  {
    return ((BLonTuningPolicy)getTuning().getPolicy()).getWriteDelay();
  }

  /**
   * If the device is online and nvProps.pollEnable flag set or subscribed,
   * read the data from the device.
   */
  public void pollNv()
  {
    if(getDevice().isReadyForNvUpdates())
    {
      // Don't poll if write initiated but not complete
      if(writeInProgress())  return;

      doForceRead();
    }
  }

  private void evaluatePollSubscribe()
  {
    if(!isRunning()) return;

    // Reevaluate poll subscription & setting of pollEnable flag.
    // 1) if an output isBoundToLocal and not a polled nv the local bind will
    //     provide the update mechanism. This only applies to outputs with proxies.
    // 2) if there is a knob(nv is linked output) and the nv has an unbound link
    // 3) if there is a knob and the nv is polled.
    boolean allLinksBound = evaluateBound();
    boolean isBoundToLocal = getNvProps().getBoundToLocal();
    boolean enablePoll = (  (!isBoundToLocal && knobCount>0 && !allLinksBound) ||
                            (isBoundToLocal && getNvProps().getPolled()) );

    boolean sub = (enablePoll || (!isBoundToLocal && subscribeCount>0)) &&
                  getNvProps().getPollEnable();

 // System.out.println("evaluatePollSubscribe " + getParent().getDisplayName(null) + ":" + getDisplayName(null) + " h=" + getHandle());
 // System.out.println("enablePoll=" + enablePoll + " isBoundToLocal=" + isBoundToLocal + " knobCount=" + knobCount + " allLinksBound=" + allLinksBound+ "  getPolled=" + getNvProps().getPolled());
 // System.out.println(" sub=" +sub + " subscribeCount=" + subscribeCount );
    if(sub && !pollSubscribe)
      lonNetwork().getPollService().subscribe(this);
    else if(!sub && pollSubscribe)
      lonNetwork().getPollService().unsubscribe(this);

    pollSubscribe = sub;
  }
  private boolean pollSubscribe = false;


  /** LonComponent transition from 0 to 1 subscriber */
  protected void lonComponentSubscribed()
  {
    // When first subscribed read the property.
    if(isRunning() && getDevice().isReadyForNvUpdates()) forceRead();

    evaluatePollSubscribe();
  }
  /** LonComponent transition from 1 to 0 subscriber */
  protected void lonComponentUnsubscribed() { evaluatePollSubscribe(); }


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  /** Override for changed(). */
  public void changed(Property prop, Context context)
  {
    super.changed(prop, context);
    if(prop==nvProps) evaluatePollSubscribe();
  }

  /**
   * Respond to property changes.<p> If the prop=data and
   * the context!=BLonNetwork.lonNoWrite then update the nv
   * data in the device.
   */
  protected void dataChanged(Context cx)
  {
    if(!isRunning()) return;

   // System.out.println("dataChanged() " + debugName() + " " + cx);
    if(cx==null || !BLonNetwork.lonNoPropagate.equals(cx)) propagateLinks();

    // If noWrite context then property changed by reading device.
    if(cx!=null && BLonNetwork.lonNoWrite.equals(cx)) return;

    if(wipSync==null) wipSync = new Object();
    synchronized(wipSync) { writeInProgress = true; }

    getTuning().writeDesired();
  }

  private boolean writeInProgress()
  {
    if(wipSync==null)  return false;
    synchronized(wipSync)
    {
      return writeInProgress;
    }
  }

  protected void propagateLinks()
  {
    Array<BLonLink> a = getLinksToPropagate(false);
    if(a==null) return;
    synchronized(a)
    {
      BLonLink[] lks = a.trim();
      for(int i=0 ; i<lks.length ; i++)
      {
        lks[i].propagateNv(this);
      }
    }
  }

  /**
   *  Read the data from the device.
   */
  public void doForceRead()
  {
    lonDevice().checkState();

    if(illegalLength)
      throw new BajaRuntimeException(getDisplayName(null) + " data length > maxNvLength of " + Lon.maxNvLength() + " bytes");

    try
    {
      if(Lon.d())
      {
        byte[] nvData =  NmUtil.fetchNv(lonDevice(), getNvIndex());
        getData().fromNetBytes(nvData);
      }
      // Indicate a read success to any proxy points.
      getData().readOk();
      getTuning().readOk();
    }
    catch(Throwable e)
    {
      getData().readFail(e.toString());
      getTuning().readFail();
      String errMsg = "Unable to read " + debugName();
      lonNetwork().log().log(Level.SEVERE,errMsg,e);
      throw new BajaRuntimeException(errMsg,e);
    }
  }

  // temp for test
  public void doNvUpdate()
  {
    try
    {
      BNvConfigData configData = getNvConfigData();
      if( configData.isOutput() && configData.isBoundNv())
      {
        byte[] a = NmUtil.pollNv(lonDevice(), configData);
        receiveUpdate(a);
      }
    }
    catch(Exception e)
    {

    }
  }

  /** Write data to device. */
  public void doForceWrite()
  {
    try
    {
      lonDevice().checkState();

      if(illegalLength)
        throw new BajaRuntimeException(getDisplayName(null) + " data length > maxNvLength of " + Lon.maxNvLength() + " bytes");

      BNvConfigData configData = getNvConfigData();
      try
      {
        if( configData.isInput())
        {
          if(Lon.d()) NmUtil.setNvValue( lonDevice(), configData, getData().toNetBytes());
          // Update any proxies
          getData().writeOk();
          getTuning().writeOk();
        }
      }
      catch(Throwable e)
      {
        getData().writeFail(e.toString());
        getTuning().writeFail();
        String errMsg = "Unable to write " + debugName();
        lonNetwork().log().log(Level.SEVERE,errMsg,e); 
        throw new BajaRuntimeException(errMsg,e);
      }
    }
    finally
    {
      if(wipSync!=null)
      {
        synchronized(wipSync)
        {
          writeInProgress = false;
        }
      }
    }
   // System.out.println("doForceWrite complete " + debugName());
  }

  /** Does this component represent a writable value in device.
   *  Returns true if this is an input nv. */
  public boolean isWriteable() { return getNvConfigData().isInput(); }

  /** Receive nvUpdate for this nv. */
  public void receiveUpdate(byte[] nvData)
  {
    try
    {
      getData().fromNetBytes(nvData);
      // Indicate a read success to any proxy points.
      getData().readOk();
      getTuning().readOk();
      fireReceivedUpdate(null);
    }
    catch(Throwable e)
    {
      String err = e.toString();
      getData().readFail(err);
      getTuning().readFail();
      lonNetwork().log().log(Level.SEVERE,"Could not decode nv update data " + debugName(),e); 
    }
  }

////////////////////////////////////////////////////////////////
// poll management
////////////////////////////////////////////////////////////////

  /** A knob was added to the londevice with this nv as source. For internal use. */
  public void lonKnobAdded(Knob knob)
  {
    knobCount++;
    Array<BLonLink> a = getLinksToPropagate(true);
    synchronized(a)
    {
      a.add((BLonLink)knob.getLink());
    }

    evaluatePollSubscribe();
    if(getDevice().isRunning()) pollNv();
  }

  /** A knob was removed from the londevice which had this nv as source. For internal use. */
  public void lonKnobRemove(Knob knob)
  {
    knobCount--;

    Array<BLonLink> a = getLinksToPropagate(false);
    if(a!=null)
    {
      synchronized(a)
      {
        a.remove((BLonLink)knob.getLink());
      }
    }
    evaluatePollSubscribe();
  }

  /** A lonLink was added to the londevice with this nv as destination. For internal use. */
  public void lonLinkAdded()
  {
    evaluateLinkedState();
  }

  /** A lonLink was removed from the londevice which had this nv as destination. For internal use. */
  public void lonLinkRemoved()
  {
    evaluateLinkedState();
  }

  private int knobCount = 0;


  /** Determine if all links are bound. */
  private boolean evaluateBound()
  {
    BNvConfigData nvCfg = getNvConfigData();
    // If input check for bound selector
    if(nvCfg.isInput()) return nvCfg.isBoundNv();

    // If output walk knobs and see if all are bound
    BComponent p =(BComponent)getParent();
    Array<BLonLink> a = getLinksToPropagate(false);
    if(p!=null && a!=null)
    {
      BLonLink[] lks = a.trim();
      for(int i=0 ; i<lks.length ; i++)
      {
        if(!lks[i].isBound(this))
           return false;
      }
    }
    return true;

  }

  /** Determine if nv is linked and if so is it linked to pseudoNv */
  protected void evaluateLinkedState()
  {
    linked = false;
    pseudoLink = false;
    remoteLink = false;
    BComponent p =(BComponent)getParent();
    if(p==null) return;

    BLink[] lks = p.getLinks(getPropertyInParent());
    for(int i=0 ; i<lks.length ; ++i)
    {
      if(!(lks[i] instanceof BLonLink)) continue;
      linked = true;
      if(((BLonLink)lks[i]).getPseudoLink())
      {
        pseudoLink = true;
      }
      if(((BLonLink)lks[i]).getRemoteLink())
      {
        remoteLink = true;
      }
    }
  }
  protected boolean isLinked()
  {
    return linked;
  }

  boolean linked = false;
  boolean pseudoLink = false;
  boolean remoteLink = false;

////////////////////////////////////////////////////////////////
// BITunable implementation
////////////////////////////////////////////////////////////////

  /**
   * Get tuning support instance.
   */
  public Tuning getTuning()
  {
    return tuning;
  }

  /**
   * Return true if the network/device/component has a
   * fatal fault which prevents normal operation.
   */
  public boolean isFatalFault()
  {
    return getDevice().isFatalFault();
  }

  /**
   * Get the status of the component.  The status should have
   * the down bit set if the network or device is down.  The
   * status should have the outOfService bit set if the
   * network/device/component is out of service.
   */
  public BStatus getStatus()
  {
    return getDevice().getStatus();
  }

  /**
   * This always returns false so that tuning will not change subscribe state.
   */
  public boolean isSubscribedDesired()
  {
    return false; // don't allow tuning to change subscribe state
  }

  /**
   * Return if the component is readonly, readWrite, or writeOnly.
   */
  public BReadWriteMode getMode()
  {
    return isWriteable() ? BReadWriteMode.writeonly : BReadWriteMode.readonly;
  }


////////////////////////////////////////////////////////////////
// Callbacks from BTuning
////////////////////////////////////////////////////////////////

  // These calls needed to implement BITuning
  public void readSubscribed(Context cx) { super.readSubscribed(); }
  public void readUnsubscribed(Context cx){ super.readUnsubscribed(); }

  /**
   * This callback is made when a write is desired based on the
   * current status and tuning. It will filter out writes that
   * are not initiated by the user i.e. due to tuning police(maxTime,startup,etc)
   * if the nv is not link or if linked and bound.
   */
  public boolean write(Context cx)
  {
   //*update debug* / System.out.println("write() " + ((cx!=null) ? cx.toString() : "") + "  " + debugName());
    boolean stateWrite = (cx==BTuningPolicy.maxWriteTimeContext) ||
                         (cx==BTuningPolicy.writeOnStartContext) ||
                         (cx==BTuningPolicy.writeOnUpContext) ||
                         (cx==BTuningPolicy.writeOnEnabledContext);

    // Must short circuit some state driven writes
    // (other writes are forced by user)
    if(stateWrite)
    {
      // Writes only apply to nvis.
      // Tuning writes apply if linked and unbound or hasWriteProxies or linked to pseudoNv of remoteLink
      if( (!linked || getNvConfigData().isBoundNv()) &&
          !getData().hasWriteProxies() && !pseudoLink && !remoteLink)
      {
        getTuning().writeOk();
        return false;
      }
    }

    // If timed write then pull values from proxy and forceWrite
    if(cx==BTuningPolicy.maxWriteTimeContext && getData().hasWriteProxies())
    {
      // Must set flag to block any polls during proxy update and write
      // Set flag here to wrap, proxy updates, any minNvUpdate delay and the write opertion.
      // Flag will be cleared when write complete
      getData().forceProxyUpdates();
    }

    // Give opportunity to insert nvUpdateInterMsgDelay.
    // If delayed write then return without writing - will be handled by delayQueue.
    if(DeviceFacets.delayNvUpdate(lonDevice(),this))
      return false;

    forceWrite();
    // Return false to indicate write not pending. If tuning thinks write pending
    // it will ignore further writes until writeOk or writeFail received.  The effect is to
    // filter out all but the first of a multi element change as writes are coalesced.
    return false;
  }

  /** Pass setStale callback to any proxies attached to this nvs data. */
  public void setStale(boolean s, Context cx)
  {
    // call setStale on proxy extensions
    if(getData().hasProxies()) getData().markStale(s, cx);
  }

////////////////////////////////////////////////////////////////
// Debug
////////////////////////////////////////////////////////////////
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);

    out.startProps();
    out.trTitle("BNetworkVariable", 2);
    out.prop("linked", linked);
    out.prop("knobCount(nvo)", knobCount);
    out.prop("allLinksBound", evaluateBound());
    out.prop("isBoundToLocal", getNvProps().getBoundToLocal());
    out.prop("hasProxies", getData().hasProxies());
    out.prop("hasWriteProxies", getData().hasWriteProxies());
    out.prop("pseudoLink", pseudoLink);
    out.prop("remoteLink", remoteLink);
    out.prop("getMode", getMode());
    out.prop("subscribeCount", subscribeCount);
    out.prop("selector registered", registered);
    out.prop("pollSubscribe", pollSubscribe);
    out.prop("writeInProgress", writeInProgress);
    out.prop("illegalLength", illegalLength);
    out.endProps();

 //   tuning.spy(out);

  }

  protected Array<BLonLink> getLinksToPropagate(boolean create)
  {
    if(create && linksToPropagate==null) linksToPropagate = new Array<>(BLonLink.class);
    return linksToPropagate;
  }

////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make("module://lonworks/com/tridium/lonworks/ui/icons/nv.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  Tuning tuning = new Tuning(this);
  Object wipSync = null;

  // Array of links from knobs on parent device for this nv.
  Array<BLonLink> linksToPropagate = null;

  boolean writeInProgress = false;
}
