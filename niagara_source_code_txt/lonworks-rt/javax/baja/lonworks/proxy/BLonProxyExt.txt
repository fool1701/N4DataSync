/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.proxy;

import javax.baja.driver.point.BReadWriteMode;
import javax.baja.driver.point.BTuningPolicy;
import javax.baja.lonworks.BLonComponent;
import javax.baja.lonworks.BLonDevice;
import javax.baja.lonworks.BLonNetwork;
import javax.baja.lonworks.BNetworkVariable;
import javax.baja.lonworks.enums.BLonLinkType;
import javax.baja.lonworks.londata.BLonData;
import javax.baja.lonworks.londata.BLonPrimitive;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusValue;
import javax.baja.sys.*;
import javax.baja.util.Queue;
import javax.baja.util.Worker;

import com.tridium.lonworks.device.NvDev;
import com.tridium.lonworks.util.NmUtil;

/**
 * BLonProxyExt is the superclass for Lonworks
 * proxy point extentions. There is an associated lonworks
 * proxy extension for <code>BNumericPoint, BBooleanPoint
 * BDynamicEnum and BStringPoint</code>.<p>
 *
 * @author    Robert Adams
 * @creation  19 Dec 01
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:42 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Pathname for target BLonData relative to device
 */
@NiagaraProperty(
  name = "targetComp",
  type = "String",
  defaultValue = "value",
  flags = Flags.SUMMARY
)
/*
 Name of BLonPrimitive this proxy targets
 */
@NiagaraProperty(
  name = "targetName",
  type = "String",
  defaultValue = "value",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "linkType",
  type = "BLonLinkType",
  defaultValue = "BLonLinkType.standard"
)
@NiagaraProperty(
  name = "priority",
  type = "boolean",
  defaultValue = "false"
)
/*
 Override ProxyExt default tuning policy to disable all writes that are not change driven
 */
@NiagaraProperty(
  name = "tuningPolicyName",
  type = "String",
  defaultValue = "defaultProxyPolicy",
  flags = Flags.READONLY | Flags.HIDDEN,
  override = true
)
/*
 Override ProxyExt default status to clear stale state.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.READONLY | Flags.TRANSIENT,
  override = true
)
/*
 Override ProxyExt default flags to hide from user
 */
@NiagaraProperty(
  name = "deviceFacets",
  type = "BFacets",
  defaultValue = "BFacets.NULL",
  flags = Flags.READONLY,
  override = true
)
public abstract class BLonProxyExt
  extends javax.baja.driver.point.BProxyExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.proxy.BLonProxyExt(1007977610)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "targetComp"

  /**
   * Slot for the {@code targetComp} property.
   * Pathname for target BLonData relative to device
   * @see #getTargetComp
   * @see #setTargetComp
   */
  public static final Property targetComp = newProperty(Flags.SUMMARY, "value", null);

  /**
   * Get the {@code targetComp} property.
   * Pathname for target BLonData relative to device
   * @see #targetComp
   */
  public String getTargetComp() { return getString(targetComp); }

  /**
   * Set the {@code targetComp} property.
   * Pathname for target BLonData relative to device
   * @see #targetComp
   */
  public void setTargetComp(String v) { setString(targetComp, v, null); }

  //endregion Property "targetComp"

  //region Property "targetName"

  /**
   * Slot for the {@code targetName} property.
   * Name of BLonPrimitive this proxy targets
   * @see #getTargetName
   * @see #setTargetName
   */
  public static final Property targetName = newProperty(Flags.SUMMARY, "value", null);

  /**
   * Get the {@code targetName} property.
   * Name of BLonPrimitive this proxy targets
   * @see #targetName
   */
  public String getTargetName() { return getString(targetName); }

  /**
   * Set the {@code targetName} property.
   * Name of BLonPrimitive this proxy targets
   * @see #targetName
   */
  public void setTargetName(String v) { setString(targetName, v, null); }

  //endregion Property "targetName"

  //region Property "linkType"

  /**
   * Slot for the {@code linkType} property.
   * @see #getLinkType
   * @see #setLinkType
   */
  public static final Property linkType = newProperty(0, BLonLinkType.standard, null);

  /**
   * Get the {@code linkType} property.
   * @see #linkType
   */
  public BLonLinkType getLinkType() { return (BLonLinkType)get(linkType); }

  /**
   * Set the {@code linkType} property.
   * @see #linkType
   */
  public void setLinkType(BLonLinkType v) { set(linkType, v, null); }

  //endregion Property "linkType"

  //region Property "priority"

  /**
   * Slot for the {@code priority} property.
   * @see #getPriority
   * @see #setPriority
   */
  public static final Property priority = newProperty(0, false, null);

  /**
   * Get the {@code priority} property.
   * @see #priority
   */
  public boolean getPriority() { return getBoolean(priority); }

  /**
   * Set the {@code priority} property.
   * @see #priority
   */
  public void setPriority(boolean v) { setBoolean(priority, v, null); }

  //endregion Property "priority"

  //region Property "tuningPolicyName"

  /**
   * Slot for the {@code tuningPolicyName} property.
   * Override ProxyExt default tuning policy to disable all writes that are not change driven
   * @see #getTuningPolicyName
   * @see #setTuningPolicyName
   */
  public static final Property tuningPolicyName = newProperty(Flags.READONLY | Flags.HIDDEN, "defaultProxyPolicy", null);

  //endregion Property "tuningPolicyName"

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * Override ProxyExt default status to clear stale state.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.READONLY | Flags.TRANSIENT, BStatus.ok, null);

  //endregion Property "status"

  //region Property "deviceFacets"

  /**
   * Slot for the {@code deviceFacets} property.
   * Override ProxyExt default flags to hide from user
   * @see #getDeviceFacets
   * @see #setDeviceFacets
   */
  public static final Property deviceFacets = newProperty(Flags.READONLY, BFacets.NULL, null);

  //endregion Property "deviceFacets"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /** Empty constructor. */
  public BLonProxyExt(){ }

  /**
   * Create a BLonProxyExt with the specified proxyLink.
   */
  public BLonProxyExt(OrdTarget ordTgt)
  {
  //  OrdTarget ordTgt = proxyLink.resolve();
    BComponent comp = ordTgt.getComponent();
    BLonDevice dev = getLonDevice(comp);

    // Get slot path relative to dev path - remove leading '/' from result
    String tgtCmp = comp.getSlotPath().toString().substring(dev.getSlotPath().toString().length()+1);
    setTargetComp(tgtCmp);

    setTargetName(ordTgt.getSlotInComponent().getName());
  }

  private BLonDevice getLonDevice(BComponent c)
  {
    while( !(c instanceof BLonDevice)) c = (BComponent)c.getParent();
    return (BLonDevice)c;
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Get the parent PointDeviceExt type this proxy
   * extension belongs under (and by deduction which
   * device and network).
   */
  public Type getDeviceExtType() { return BLonPointDeviceExt.TYPE; }

  /**
   * Return if this proxy point is readonly, readWrite or writeonly.
   */
  public BReadWriteMode getMode()
  {
    return isDataPointWriteable ? BReadWriteMode.writeonly : BReadWriteMode.readonly;
  }

  /**
   * This callback is invoked when the point's
   * subscription count is incremented to non-zero
   * and isOutOfService() returns false.
   */
  public final void readSubscribed(Context cx)
    throws Exception
  {
    BLonData dataPoint = getDataPoint();    
  // System.out.println("BLonProxyExt.doPointSubscribed " + getParent().getDisplayName(null) + ((dataPoint==null)? "(dataPoint==null)" : dataPoint.getDisplayName(null) ));
    if(dataPoint!=null && !dataPntSubscribed) { dataPoint.readSubscribed(); dataPntSubscribed=true; }

  }
 
  /**
   * This callback is invoked when the point's subscription
   * count returns to zero and isOutOfService() returns false.
   */
  public final void readUnsubscribed(Context cx)
    throws Exception
  {
   // System.out.println("BLonProxyExt.doPointUnsubscribed " + getParent().getDisplayName(null));
    BLonData dataPoint = datPnt; // getDataPoint();    
    if(dataPoint!=null && dataPntSubscribed) {dataPoint.readUnsubscribed(); dataPntSubscribed=false; }
  }


  /**
   * This callback is made when a write is desired based on the
   * current status and tuning.  The value to write is the current
   * value of the writeValue property.  Processing is done on the
   * BLonNetwork proxyQueue worker.
   */
  public boolean write(Context cx)
    throws Exception
  {
    if(!isDataPointWriteable || isUnoperational()) return false;

    // Do all processing through the proxyQueue worker to 
    // help prevent deadlocks occurring because the write
    // call is being done synchronized on the tunning object.
    Worker w = getProxyQueue();
    if(w==null || !w.isRunning()) return false;

    
    try
    {
      ((Queue)w.getTodo()).enqueue(getRequest(cx)); 
    }
    catch(Throwable e)
    {
      // can't queue - just do it
      doProxyWrite(cx);
    } 
       
    return false;
  }
  
  /**
   * For internal use.  Push control point writeValue to the 
   * target with BLonNetwork.noWrite context.
   */
  public final void forceUpdate()
  {
    doProxyWrite(BLonNetwork.lonNoWrite);
  }
  
  // Implemetation of write done on worker thread.
  private void doProxyWrite(Context cx)
  {
    if(isUnoperational()) return;
    try
    {
      //updateDevice(getWriteValue());
      BStatusValue value = getWriteValue();

      // if value has null status do nothing.  Null status
      // indicates no active inputs in control point
      if(value.getStatus().isNull())  return ;

      // If dataPoint not resolved there is nothing to do
      BLonData dataPoint = getDataPoint();    
      if(dataPoint==null) return;
      
      BLonPrimitive prim = getPrimitiveValue(value);
      if(prim==null)
      {
        writeFail("Unable to convert status value to lonPrimitive.");
        return ;
      }

      // The set always initiates a write to the device. State driven tuning (up,on,maxTime..)
      // writes should be left to the nv's tuning
      boolean stateWrite = (cx==BTuningPolicy.maxWriteTimeContext) ||
                           (cx==BTuningPolicy.writeOnStartContext) ||
                           (cx==BTuningPolicy.writeOnUpContext) ||
                           (cx==BTuningPolicy.writeOnEnabledContext);
      if(!stateWrite) dataPoint.set(targetProp,prim,cx);
    }
    catch(Throwable e)
    {
      writeFail(e.toString());
    }
   
  }
  
  // Access the proxyQueue Worker in BLonNetwork.
  private Worker getProxyQueue()
  { 
    if(wrkr == null)
    {
      wrkr = ((BLonNetwork)((BLonDevice)getDevice()).getNetwork()).getProxyQueue();
    }
    return wrkr;
  }
  // Create a reusable runnable for processing proxy writes
  // to reduce constant object creation/garabage collection.
  private Runnable getRequest(Context cx)
  {
    if(req==null)
    {
      req = new ProxyRequest();
    }
    req.setContext(cx);
    return req;  
  }
  
  Worker wrkr = null;
  ProxyRequest req = null;
  
  private class ProxyRequest implements Runnable
  {
    public void run() { doProxyWrite(cx); }
    public void setContext(Context cx) { this.cx=cx; }
    Context cx = null;
  }

  /**
   * Resolve proxy link and register with data component if needed.
   */
  public final  void started()
    throws Exception
  {
    super.started();
    // Must set stale to clear internal flag on BProxyExt - otherwise can not set
    // to stale(true) until set to stale(false)
    setStale(false,null);
    registerProxy(getDataPoint());
    checkIfWriteable();
    extStarted();
    
    if(getLinkType() == BLonLinkType.unknown)
    {
      BLonComponent lc = getLonComponent();
      if(lc!=null && lc.isNetworkVariable())
        setLinkType(NmUtil.getLinkType( ((BNetworkVariable)lc).getNvConfigData()) );
    }
  }
  
  protected void extStarted()
  {
    // Force the readValue to start with the value in the
    // shadow lonComponent.
    setReadValue(getStatusValue());
  }
  
  /**
   *  Unregister proxy with data component if needed.
   */
  public final void stopped()
    throws Exception
  {
    try
    {
      unregisterProxy();
    }
    catch(Throwable e) {}
      
    super.stopped();
  }

  private void registerProxy(BLonData dataPoint)
  {
    // Register with dataPoint to receiveUpdate callback.
    // Only needed if the control point is not a writeable point.
    if(dataPoint!=null)
    {
      if(isSubscribedDesired() && !dataPntSubscribed) { dataPoint.readSubscribed(); dataPntSubscribed=true; }
      dataPoint.registerProxyExt(this);
    }  
  }

  private void unregisterProxy()
  {
    BLonData dataPoint = datPnt;//getDataPoint();    
    if(dataPoint!=null)
    {
      if(dataPntSubscribed) { dataPoint.readUnsubscribed(); dataPntSubscribed=false; }
      dataPoint.unregisterProxyExt(this);
    }   
  }

//  private boolean isReadonlyControlPoint()
//  {
//    return !((BControlPoint)getParent()).isWritablePoint();
//  }

  /**
   * Override to return false. This will force update of
   * deviceValue from executeValue after successful writes.
   */
  public boolean isReadWriteProxy()
  {
    return false;
  }

  /**
   * If change to proxyLink or target resolve new proxy link and
   * register with data component
   */
  public void changed(Property prop, Context context)
  {
    try
    {
      if(!isRunning())return;

      if(prop == targetName || prop == targetComp)
      {
        renew();
      }
    }
    finally
    {
      super.changed(prop,context);
    }
  }
  
////////////////////////////////////////////////////////////////
// Api
////////////////////////////////////////////////////////////////

  /**
   * Get the BLonComponent containing the data element this proxy
   * is linked to.
   */
  public BLonComponent getLonComponent()
  {
    BLonData dataPoint = datPnt; // getDataPoint();    
    if(dataPoint==null) return null;
    return dataPoint.getLonComponent();
  }

  /** Resolve proxy link. */
  public void renew()
  {
    renew(false);
  }
  
  /** */
  public void renew(boolean updateFacets)
  {
    //Unregister current link
    unregisterProxy();
    // Resolve new link
    datPnt = null;
    // Update point per any changes to proxied point
    if(updateFacets) deviceFacetsChanged();
    // Register new link
    registerProxy(getDataPoint());
  }
  
  /**
   * Override point for subclasses to set the Control Point and ProxyExt facets
   * from the device facets which may have changed.
   */
  protected void deviceFacetsChanged()
  {
  }

////////////////////////////////////////////////////////////////
// Implementation
////////////////////////////////////////////////////////////////
  public BLonData getDataPoint()
  {
    BLonData orig = datPnt;
    resolveProxyLink();
    if(orig!=null && datPnt!=null && orig!=datPnt)
    {
      System.out.println(NmUtil.timeStamp() + " resolveProxyLink found new object orig=" + 
                  orig.getHandle() + " isMounted=" + orig.isMounted() + 
        " new=" + datPnt.getHandle() + " isMounted=" + datPnt.isMounted());
      
      // Collect some statistics
      ((BLonNetwork)getLonDevice(this).getNetwork()).dataPntMismatchCount++;
      dataPntMismatchCount++;  
      
      registerProxy(datPnt);
    }
    return datPnt;


  }
  
  private void resolveProxyLink()
  {
    BComponent refPoint = null;
    try
    {
      // Find parent device
      BLonDevice dev = getLonDevice(this);
      // Get the ord which is relative to dev
      BOrd tgtOrd = BOrd.make("slot:"+ getTargetComp());
      // Resolve to ref point
      refPoint = (BComponent)tgtOrd.get(dev);
      targetProp = refPoint.getProperty(getTargetName());
    }
    catch(Throwable e)
    {
      refPoint = null;
    }
    
    // Check for successful resolve - set fault and status
    if(refPoint==null || targetProp==null) 
    {
if(datPnt!= null)
{
System.out.println("\nunable to re-resolve proxy reference for " + getDevice().getDisplayName(null) +  ":" + getParent().getDisplayName(null) + ":" + datPnt.getDisplayName(null));
System.out.println("getTargetComp()=" + getTargetComp() + " getTargetName()=" + getTargetName());
System.out.println("previous datPnt.isMounted()=" + datPnt.isMounted() + " handle=" + datPnt.getHandle());
System.out.println();
}
      datPnt = null;
      setFaultCause(RESOLVE_FAULT_MSG + toString(null) );
      setStatus(BStatus.fault);
      executePoint();
      throw new UnresolvedException("Cannot resolve proxyLink in " + getDevice().getDisplayName(null) +  ":" + getParent().getDisplayName(null));
    }

    if( getStatus().isFault() &&
        getFaultCause().indexOf(RESOLVE_FAULT_MSG)>=0)
    {
      setFaultCause("");
      setStatus(BStatus.ok);
      getTuning().writeDesired();//executePoint();
    }
  
    datPnt = (BLonData)refPoint;
  }

  /**
   * Override point for subclasses to convert BStatusValue to
   * the appropriate BLonPrimitive type.
   */
  protected abstract BLonPrimitive getPrimitiveValue(BStatusValue value);

  /**
   * Return data point value as BStatusValue.
   */
  public final BStatusValue getStatusValue()
  {
    
    BLonPrimitive target = getTarget();
    if(target==null)  return null;
    return getStatusValue(target);
  }
  
  protected final BLonPrimitive getTarget()
  {
    BLonData dataPoint = getDataPoint();    

    if(dataPoint==null || targetProp==null) return null;
    BLonPrimitive target;
    try 
    {
      target  = (BLonPrimitive)dataPoint.get(targetProp);
    }
    catch(Exception e)
    {
      System.out.println("ERROR: no property " + targetProp.getName() + " in " + dataPoint.getDisplayName(null));
      return null;
    } 
    return target;   
  }
  
  /**
   * Override point for subclasses to convert data point value to
   * the appropriate BStatusValue type.
   */
  public abstract BStatusValue getStatusValue(BLonPrimitive newValue);


  /** Override toString to return targetComp and targetName. */
  public String toString(Context c)
  {
    return getTargetComp() + "/" + getTargetName();
  }

  private void checkIfWriteable()
  {
    BLonComponent lc = getLonComponent();
    isDataPointWriteable = getParentPoint().isWritablePoint() && lc.isWriteable();
  }

////////////////////////////////////////////////////////////////
// Debug
////////////////////////////////////////////////////////////////
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    BLonData dataPoint = datPnt;    
    
    out.startProps();
    out.trTitle("LonProxyExt", 2);
    BLonComponent lc = getLonComponent();
    out.prop("lonComponent", (lc==null)? "null" : lc.getName());
    if(dataPoint==null) 
      out.prop("dataPoint", "null");
    else
    {
      out.prop("dataPoint", dataPoint.getName());
      out.prop("dataPoint.handle", dataPoint.getHandle());
      out.prop("dataPoint.isMounted", dataPoint.isMounted());
      String name = dataPoint.getName();
      spySlot_(out, name, dataPoint.getSlotPath().getBody(), getFlags(targetProp), dataPoint);
    }
    out.prop("targetProp", (targetProp==null)? "null" : targetProp.getName());
    out.prop("isDataPointWriteable", isDataPointWriteable);
    if((dataPoint!=null) && (targetProp!=null)) out.prop("primitive val", dataPoint.get(targetProp).toString(null));
    out.prop("dataPntMismatchCount", dataPntMismatchCount);
    out.prop("dataPointSubscribed", dataPntSubscribed);
    out.endProps();   

  }                       

  private void spySlot_(SpyWriter out, String name, String slotPath, int flags, Object value)
  {
    out.w("<tr><td align='left' nowrap='true'><b>").a("/nav/localhost/station" + slotPath, name).w("</b>");
    if (flags == 0) out.w("{0}");
    else out.w(" {").w(Flags.encodeToString(flags)).w("}");
    out.w("</td><td align='left' nowrap='true'>").w(value).w("</td></tr>\n");
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  private static final String RESOLVE_FAULT_MSG = "Can not resolve target: ";

  protected BLonData datPnt = null;
  protected Property targetProp = null;
  private boolean isDataPointWriteable = true;
  private int dataPntMismatchCount = 0;
  private boolean dataPntSubscribed = false;
}
