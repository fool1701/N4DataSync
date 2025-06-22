/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.point;

import javax.baja.control.BControlPoint;
import javax.baja.control.ext.BAbstractProxyExt;
import javax.baja.driver.BDevice;
import javax.baja.driver.BDeviceNetwork;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusString;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BComplex;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnitConversion;

import com.tridium.sys.metrics.BISubLicenseable;
import com.tridium.sys.metrics.Metrics;
import com.tridium.sys.resource.ResourceReport;
import com.tridium.sys.schema.Fw;

/**
 * BProxyExt is the point extension supported in BControlPoints 
 * which are proxies for point data in an external system.
 *
 * @author    Brian Frank
 * @creation  5 Dec 01
 * @version   $Revision: 81$ $Date: 6/12/09 10:27:08 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The status of the proxy extension.  This
 property should never be set directly.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.stale",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Provides a description if the point is in fault.
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Used to manually enable and disable this point.
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 Facets of device value being read and/or written.
 */
@NiagaraProperty(
  name = "deviceFacets",
  type = "BFacets",
  defaultValue = "BFacets.NULL"
)
/*
 Conversion controls mapping between the device value
 space and the proxy value space.
 */
@NiagaraProperty(
  name = "conversion",
  type = "BProxyConversion",
  defaultValue = "BDefaultProxyConversion.DEFAULT",
  facets = {
    @Facet("BFacets.make(BFacets.FIELD_EDITOR, \"driver:ProxyConversionFE\")"),
    @Facet("BFacets.make(BFacets.UX_FIELD_EDITOR, \"driver:ProxyConversionEditor\")")
  }
)
/*
 References the TuningPolicy component by name.
 */
@NiagaraProperty(
  name = "tuningPolicyName",
  type = "String",
  defaultValue = "defaultPolicy",
  facets = @Facet("TUNING_POLICY_NAME_FACETS")
)
/*
 The last value read from the device.
 */
@NiagaraProperty(
  name = "readValue",
  type = "BStatusValue",
  defaultValue = "new BStatusString(\"-\")",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 The value current desired to be written.
 */
@NiagaraProperty(
  name = "writeValue",
  type = "BStatusValue",
  defaultValue = "new BStatusString(\"-\")",
  flags = Flags.READONLY | Flags.TRANSIENT
)
public abstract class BProxyExt
  extends BAbstractProxyExt 
  implements BIStatus, BITunable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.point.BProxyExt(2574222224)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * The status of the proxy extension.  This
   * property should never be set directly.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.READONLY | Flags.TRANSIENT, BStatus.stale, null);

  /**
   * Get the {@code status} property.
   * The status of the proxy extension.  This
   * property should never be set directly.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * The status of the proxy extension.  This
   * property should never be set directly.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * Provides a description if the point is in fault.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.READONLY | Flags.TRANSIENT, "", null);

  /**
   * Get the {@code faultCause} property.
   * Provides a description if the point is in fault.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * Provides a description if the point is in fault.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * Used to manually enable and disable this point.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * Used to manually enable and disable this point.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * Used to manually enable and disable this point.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "deviceFacets"

  /**
   * Slot for the {@code deviceFacets} property.
   * Facets of device value being read and/or written.
   * @see #getDeviceFacets
   * @see #setDeviceFacets
   */
  public static final Property deviceFacets = newProperty(0, BFacets.NULL, null);

  /**
   * Get the {@code deviceFacets} property.
   * Facets of device value being read and/or written.
   * @see #deviceFacets
   */
  public BFacets getDeviceFacets() { return (BFacets)get(deviceFacets); }

  /**
   * Set the {@code deviceFacets} property.
   * Facets of device value being read and/or written.
   * @see #deviceFacets
   */
  public void setDeviceFacets(BFacets v) { set(deviceFacets, v, null); }

  //endregion Property "deviceFacets"

  //region Property "conversion"

  /**
   * Slot for the {@code conversion} property.
   * Conversion controls mapping between the device value
   * space and the proxy value space.
   * @see #getConversion
   * @see #setConversion
   */
  public static final Property conversion = newProperty(0, BDefaultProxyConversion.DEFAULT, BFacets.make(BFacets.make(BFacets.FIELD_EDITOR, "driver:ProxyConversionFE"), BFacets.make(BFacets.UX_FIELD_EDITOR, "driver:ProxyConversionEditor")));

  /**
   * Get the {@code conversion} property.
   * Conversion controls mapping between the device value
   * space and the proxy value space.
   * @see #conversion
   */
  public BProxyConversion getConversion() { return (BProxyConversion)get(conversion); }

  /**
   * Set the {@code conversion} property.
   * Conversion controls mapping between the device value
   * space and the proxy value space.
   * @see #conversion
   */
  public void setConversion(BProxyConversion v) { set(conversion, v, null); }

  //endregion Property "conversion"

  //region Property "tuningPolicyName"

  /**
   * Slot for the {@code tuningPolicyName} property.
   * References the TuningPolicy component by name.
   * @see #getTuningPolicyName
   * @see #setTuningPolicyName
   */
  public static final Property tuningPolicyName = newProperty(0, "defaultPolicy", TUNING_POLICY_NAME_FACETS);

  /**
   * Get the {@code tuningPolicyName} property.
   * References the TuningPolicy component by name.
   * @see #tuningPolicyName
   */
  public String getTuningPolicyName() { return getString(tuningPolicyName); }

  /**
   * Set the {@code tuningPolicyName} property.
   * References the TuningPolicy component by name.
   * @see #tuningPolicyName
   */
  public void setTuningPolicyName(String v) { setString(tuningPolicyName, v, null); }

  //endregion Property "tuningPolicyName"

  //region Property "readValue"

  /**
   * Slot for the {@code readValue} property.
   * The last value read from the device.
   * @see #getReadValue
   * @see #setReadValue
   */
  public static final Property readValue = newProperty(Flags.READONLY | Flags.TRANSIENT, new BStatusString("-"), null);

  /**
   * Get the {@code readValue} property.
   * The last value read from the device.
   * @see #readValue
   */
  public BStatusValue getReadValue() { return (BStatusValue)get(readValue); }

  /**
   * Set the {@code readValue} property.
   * The last value read from the device.
   * @see #readValue
   */
  public void setReadValue(BStatusValue v) { set(readValue, v, null); }

  //endregion Property "readValue"

  //region Property "writeValue"

  /**
   * Slot for the {@code writeValue} property.
   * The value current desired to be written.
   * @see #getWriteValue
   * @see #setWriteValue
   */
  public static final Property writeValue = newProperty(Flags.READONLY | Flags.TRANSIENT, new BStatusString("-"), null);

  /**
   * Get the {@code writeValue} property.
   * The value current desired to be written.
   * @see #writeValue
   */
  public BStatusValue getWriteValue() { return (BStatusValue)get(writeValue); }

  /**
   * Set the {@code writeValue} property.
   * The value current desired to be written.
   * @see #writeValue
   */
  public void setWriteValue(BStatusValue v) { set(writeValue, v, null); }

  //endregion Property "writeValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  
  /**
   * Get the parent PointDeviceExt type this proxy 
   * extension belongs under (and by deduction which
   * device and network).
   */
  public abstract Type getDeviceExtType(); 
  
  /**
   * Return if this proxy point is readonly, readWrite or writeonly.
   */
  public abstract BReadWriteMode getMode();

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the parent network.  Throw an exception if this 
   * component is not running or has a fatal fault.
   */
  public final BDeviceNetwork getNetwork()
  {          
    return getDevice().getNetwork();
  }

  /**
   * Get the parent device.  Throw an exception if this 
   * component is not running or has a fatal fault.
   */
  public final BDevice getDevice()
  {
    if (device != null) return device;
    if (!isRunning()) throw new NotRunningException();
    throw new IllegalStateException(getFaultCause());
  }

  /**
   * Get the parent PointDeviceExt.  Throw an exception if 
   * this component is not running or has a fatal fault.
   */
  public final BPointDeviceExt getDeviceExt()
  {
    if (deviceExt != null) return deviceExt;
    if (!isRunning()) throw new NotRunningException();
    throw new IllegalStateException(getFaultCause());
  }

  /**
   * Get the facets for the specified slot.
   */
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.equals(readValue) || slot.equals(writeValue))
    {
      return BFacets.make(getDeviceFacets(), BFacets.UNIT_CONVERSION, BDynamicEnum.make(BUnitConversion.none));
    }
    return super.getSlotFacets(slot);
  }

////////////////////////////////////////////////////////////////
// Status
////////////////////////////////////////////////////////////////

  /**
   * Return true if the parent device is down.
   */
  public final boolean isDown()
  {
    return getStatus().isDown();
  }

  /**
   * Return true if the point is disabled.  A point is disabled if 
   * the user has manually set the network, device, or proxyExt 
   * enabled property to false.
   */
  public final boolean isDisabled()
  {            
    return getStatus().isDisabled();
  }
  
  /**
   * Return true if the point is in fault.  A point is in fault if 
   * either a fatal fault was detected, the last read failed, or the
   * last write failed.  A point is also automatically  marked in 
   * fault if the parent device is in fault.
   */
  public final boolean isFault()
  {
    return getStatus().isFault();
  }       
  
  /**
   * The point is unoperational if it is down, disabled, 
   * or in fatal fault.
   */
  public final boolean isUnoperational()
  {           
    return isDown() || isDisabled() || isFatalFault();
  }                      
            
  /**
   * Recompute the proxy extensions status. 
   */
  public final void updateStatus()
  {                                                        
    // compute new status...
    int newStatus = getStatus().getBits();                     
    BStatus device = (this.device == null) ? BStatus.ok : this.device.getStatus();
    
    // ... disabled bit
    if (!getEnabled() || device.isDisabled())
      newStatus |= BStatus.DISABLED;
    else
      newStatus &= ~BStatus.DISABLED;
      
    // ... down bit
    if (device.isDown()) 
      newStatus |= BStatus.DOWN;
    else
      newStatus &= ~BStatus.DOWN;

    // ... fault bit
    if (fatalFault() || readFault != null || writeFault  != null || device.isFault()) 
      newStatus |= BStatus.FAULT;
    else
      newStatus &= ~BStatus.FAULT;
    
    // stale bit
    if (stale())
      newStatus |= BStatus.STALE;
    else
      newStatus &= ~BStatus.STALE;   
    
    // short circuit if nothing has changed since last time
    if (oldStatus == newStatus) return;
    
    // update status
    setStatus(BStatus.make(newStatus));
    oldStatus = newStatus;   
    
    // update fault cause property
    updateFaultCause();
    
    // inform tuning
    getTuning().transition();
    
    // re-execute point
    executePoint();
  }          
  
  /**
   * Update the fault cause property by looking at various
   * internal state fields to see if something is causing us
   * to be in fault.
   */
  private void updateFaultCause()
  {
    // never update if in  fatal b/c it never changes, 
    // and we don't want to overwrite that cause message
    if (fatalFault()) return;                        
    
    String cause;                   
    if (readFault != null && writeFault != null) 
      cause = "Read fault: " + readFault + "; Write fault: " + writeFault;
    else if (readFault != null)
      cause = "Read fault: " + readFault;
    else if (writeFault != null)
      cause = "Write fault: " + writeFault;
    else if (convErrD2P() || convErrP2D())
      cause = "Conversion Error";
    else
      cause = "";
      
    setFaultCause(cause);
  }

////////////////////////////////////////////////////////////////
// Fault
////////////////////////////////////////////////////////////////
  
  /**
   * Return true if the point detected a fatal fault.
   * Fatal faults cannot be recovered until the point
   * is restarted.
   */
  public final boolean isFatalFault()
  {                 
    return fatalFault();
  }  

  /**
   * Set the proxyExt into the fatal fault condition.  Unlike
   * configFail(), the fatal fault condition cannot be cleared
   * until station restart.
   */
  public final void configFatal(String cause)
  {
    fatalFault(true);
    setFaultCause(cause);
    updateStatus();
  }             

  /**
   * This method checks for fatal faults built into the framework.
   */
  private void checkFatalFault(String badGroups)
  {                         
    // check capacity
    if (badGroups != null)
    {
      configFatal("Exceeded point limit for " + badGroups);
      return;
    }
        
    BPointDeviceExt deviceExt = null;
    BDevice device = null;
    BDeviceNetwork network = null;

    // short circuit if already in fatal fault
    if (fatalFault()) return;
    
    // find device ext
    BComplex parent = getParent();
    while(parent != null)                       
    {
      if (parent instanceof BPointDeviceExt)
        { deviceExt = (BPointDeviceExt)parent; break; }
      parent = parent.getParent();
    }                   

    // check mounted in device ext 
    if (deviceExt == null)
    {
      fatalFault(true);
      setFaultCause("Not under PointDeviceExt");
      return;
    }        

    // check device ext type
    if (!deviceExt.getType().is(getDeviceExtType()))
    {
      fatalFault(true);
      setFaultCause("Parent PointDeviceExt " + deviceExt.getType() + " is not " + getDeviceExtType());
      return;
    }   
    
    // check mounted in device
    device = deviceExt.getDevice();
    if (device == null)
    {
      fatalFault(true);
      setFaultCause("Not under Device");
      return;
    }

    // check device type
    if (!device.getType().is(deviceExt.getDeviceType()))
    {
      fatalFault(true);
      setFaultCause("Parent Device " + device.getType() + " is not " + deviceExt.getDeviceType());
      return;
    }   

    // check device fatal fault 
    if (device.isFatalFault())
    {
      fatalFault(true);
      setFaultCause("Device fault: " + device.getFaultCause());
      return;
    }         
    
    // check mounted in network
    network = device.getNetwork();
    if (network == null)
    {
      fatalFault(true);
      setFaultCause("Not under DeviceNetwork");
      return;
    }                  
    
    // check network fatal fault 
    if (network.isFatalFault())
    {
      fatalFault(true);
      setFaultCause("Network fault: " + network.getFaultCause());
      return;
    }                  
    
    if (getType().is(deviceExt.getProxyExtType())) // 6/1/05 S. Hoye added this proxy ext type check
    {
      // check license
      Object licenseFault = network.fw(Fw.CHECK_LICENSE_LIMIT,
                                       BISubLicenseable.getLicenseKey(this, "point.limit"),
                                       this, null, null);
      if (licenseFault != null)
      {
        fatalFault(true);
        setFaultCause(licenseFault.toString());
        return;
      }
    }

    // no fatal faults
    this.device     = device;
    this.deviceExt  = deviceExt;
    setFaultCause("");                                       
    
    // sanity checks
    if (this.device  == null || this.deviceExt == null)
      throw new IllegalStateException();
  }             
  
////////////////////////////////////////////////////////////////
// AbstractProxyExt
////////////////////////////////////////////////////////////////

  /**
   * Check that readValue and writeValue are of the
   * correct type for the parent point.
   */
  public void checkStatusValueTypes()
  {
    BControlPoint parent = getParentPoint();
    if (parent == null) return;
    BStatusValue out = parent.getOutStatusValue();

    // fix readValue
    if (out.getType() != getReadValue().getType())
      set(readValue, (BStatusValue)out.getType().getInstance());

    // fix writeValue
    if (out.getType() != getWriteValue().getType())
      set(writeValue, (BStatusValue)out.getType().getInstance());
  }
  
  /**
   * If this point is writable, store the working value away
   * in the writeValue property to be written.  Return the current
   * readValue as the new working variable.
   */
  public final void onExecute(BStatusValue working, Context cx)
  {
    // handle potential write change
    if (getMode().isWrite())
    {
      // if working has changed since last time
      // then update our writeValue and let tuning know
      if (hasWorkingChanged(working) || forceWrite())
      {
        forceWrite(false);
        convertProxyToDevice(working, getWriteValue());
        getTuning().writeDesired();
      }
    }          
    
    // save original working status (computed from inputs)
    BStatus inStatus = working.getStatus();

    // convert readValue -> working (value and status)
    convertDeviceToProxy(getReadValue(), working);
    BStatus readStatus = working.getStatus();

    // status = input status + readValue status + my status                      
    BStatus outStatus;                                                           
    int inBits   = inStatus.getBits() & ~BStatus.NULL; // don't pass thru null
    int readBits = readStatus.getBits();
    int myBits   = getStatus().getBits();
    int bits =  inBits | readBits | myBits;
    if (readStatus.getFacets().isNull()) 
      outStatus = BStatus.make(inStatus, bits);
    else
      outStatus = BStatus.make(bits, BFacets.make(inStatus.getFacets(), readStatus.getFacets()));
    working.setStatus(outStatus);
  }

  /**
   * This callback is made when the parent point's facets are 
   * modified.  This hook is used to force a  write to the 
   * external device.
   */
  public void pointFacetsChanged()
  {
    forceWrite(true);
  }

  /**
   * Callback when any of the WritablePoint's command
   * actions are invoked: emergencyOverride, emergencyAuto,
   * override, auto, or set.  This hook is used to force a 
   * write to the external device.
   */
  public void writablePointActionInvoked()
  {             
    forceWrite(true);
  }
  
  /**
   * Return if the working value has changed since the last execute.
   */
  private boolean hasWorkingChanged(BStatusValue working)
  {                         
    // during initialization don't write the first cycle since
    // that should be handled by the atSteadyState and started 
    // transitions; however we do need to init the lastWorking                                       
    // variable as well as the writeValue property
    if (lastWorking == null)
    {            
      lastWorking = (BStatusValue)working.newCopy();
      convertProxyToDevice(working, getWriteValue());
      return false;
    }
    
    // check if the value has changed since our last pass
    if (lastWorking.equivalent(working)) return false;
    lastWorking.copyFrom(working);
    return true;
  }

////////////////////////////////////////////////////////////////
// ITunable
////////////////////////////////////////////////////////////////
  
  /**
   * Get the tuning support instance.
   */
  public final Tuning getTuning()
  {                                  
    return tuning;
  }  

  /**
   * Set or clear the point's stale status.  Stale is used to indicate
   * that the current readValue might be untrustworthy.  If the tuning
   * policy has a non-zero staleTime, then stale is automatically called
   * when staleTime elapses without any calls to readOk (or writeOk in
   * the case of a writeonly point).  If staleTime is zero, then stale 
   * must be handled by the driver.  The stale status is always cleared 
   * on readOk (or writeOk for a writeonly point).
   */
  public void setStale(boolean stale, Context cx)
  {      
    if (this.stale() != stale)
    {
      this.stale(stale);      
      updateStatus();
    }
  }  
  
  /**
   * Convenience for <code>getTuning().getPolicy()</code>.
   */
  public final BTuningPolicy getTuningPolicy()
  {                        
    return getTuning().getPolicy();
  }
  
////////////////////////////////////////////////////////////////
// Subscriptions
////////////////////////////////////////////////////////////////
  
  /**
   * Return if the parent point is subscribed.  This is 
   * independent of any status conditions which might be 
   * preventing actual subscription.
   */
  public boolean isSubscribedDesired()
  {                         
    return getParentPoint().isSubscribed();
  }
  
  /**
   * Route to tuning.  Actual subscribed taking status
   * into account is done via readSubscription().
   */
  public final void pointSubscribed()
  {                            
    getTuning().transition();
  }

  /**
   * Route to tuning.  Actual unsubscribed taking status
   * into account is done via readUnsubscribed().
   */
  public final void pointUnsubscribed()
  {
    getTuning().transition();
  }

////////////////////////////////////////////////////////////////
// Callbacks To Driver
////////////////////////////////////////////////////////////////
  
  /**
   * This callback is made when the point enters a subscribed 
   * state based on the current status and tuning.  The driver 
   * should register for changes or begin polling.  Any IO should
   * be done asynchronously on another thread - never block the
   * calling thread.  The result of reads should be to call the
   * readOk() or readFail() method.
   */
  public abstract void readSubscribed(Context cx)
    throws Exception;
  
  /**
   * This callback is made when the point exits the subscribed
   * state based on the current status and tuning.  The driver
   * should unregister for changes of cease polling.  Any IO should
   * be done asynchronously on another thread - never block the
   * calling thread.
   */
  public abstract void readUnsubscribed(Context cx)
    throws Exception;
  
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
  public abstract boolean write(Context cx)
    throws Exception;

////////////////////////////////////////////////////////////////
// Callbacks From Driver
////////////////////////////////////////////////////////////////

  /**
   * This method is called when a value is successfully
   * read from the device.  The newValue argument is the
   * value just read (including any status which was read).
   */
  public void readOk(BStatusValue newValue)
  {                                          
    setReadValue((BStatusValue)newValue.newCopy()); // use copy for safe atomic
    getTuning().readOk();
    readFault = null;   
    stale(false);
    updateStatus();    
    executePoint();
  }                  
  
  /**
   * Read reset is used to clear a readFail, but does not indicate
   * that a successful read has been accomplished.  It clears the
   * read fault and marks the value as stale.  It does not update
   * the readValue or readTime properties.
   */
  public void readReset()
  {              
    readFault = null;                            
    stale(true);
    updateStatus();    
    executePoint();
  }

  /**
   * This method is called when a read from the device
   * fails due to a configuration or fault error.
   */
  public void readFail(String cause)
  {                        
    getTuning().readFail();
    readFault = cause;    
    oldStatus = -1;
    updateStatus();
    executePoint();
  }

  /**
   * This method is called when a value is successfully
   * written to the device.  The parameter passed is the 
   * value that was written.
   */
  public void writeOk(BStatusValue writeValue)
  {
    if (getMode().isWriteonly()) 
    {
      setReadValue((BStatusValue)writeValue.newCopy()); // use copy for safe atomic
      stale(false);
    }
    getTuning().writeOk();
    writeFault = null;
    updateStatus();
    // This is needed to pull the readValue to cp.out.
    if (getMode().isWriteonly())
      executePoint();
  }

  /**
   * This method is called when a pending write to the device is canceled or short 
   * circuited.  This often happens when an attempt is made to write a value with a
   * null status bit.  This method clears the fault cause and clears the tunings write
   * pending flag.
   */
  public void writeReset()
  {
    getTuning().writeFail();
    writeFault = null;
    oldStatus = -1;
    updateStatus();
  }

  /**
   * This method is called when a write to the device
   * fails for any reason. 
   */
  public void writeFail(String cause)
  {
    getTuning().writeFail();
    writeFault = cause;
    oldStatus = -1;
    updateStatus();
  }

////////////////////////////////////////////////////////////////
// Conversion
////////////////////////////////////////////////////////////////

  /**
   * Copy the deviceValue to the proxyValue.  The default implementation 
   * routes to <code>getConversion().convertDeviceToProxy()</code>.
   */
  protected void convertDeviceToProxy(BStatusValue deviceValue, BStatusValue proxyValue)
  {     
    boolean oldErr = convErrD2P();             
    boolean newErr;
    
    try
    {                         
      getConversion().convertDeviceToProxy(this, deviceValue, proxyValue);
      newErr = false;
    }
    catch(Exception e)
    {                   
      proxyValue.setValueValue(deviceValue.getValueValue());
      proxyValue.setStatusFault(true);
      newErr = true;
    }
    
    if (oldErr != newErr) 
    {      
      convErrD2P(newErr);
      updateFaultCause();
    }
  }

  /**
   * Copy the proxyValue to the deviceValue.  The default implementation 
   * routes to <code>getConversion().convertProxyToDevice()</code>.
   */
  protected void convertProxyToDevice(BStatusValue proxyValue, BStatusValue deviceValue)
  {
    boolean oldErr = convErrP2D();             
    boolean newErr;
    
    try
    {                         
      getConversion().convertProxyToDevice(this, proxyValue, deviceValue);
      newErr = false;
    }
    catch(Exception e)
    {                                                  
      deviceValue.setValueValue(proxyValue.getValueValue());
      deviceValue.setStatusFault(true);
      newErr = true;
    }
    
    if (oldErr != newErr) 
    {      
      convErrP2D(newErr);
      updateFaultCause();
    }
  }

////////////////////////////////////////////////////////////////
// Framework Implementation
////////////////////////////////////////////////////////////////

  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.STARTED:             fwStarted(); break;
      case Fw.AT_STEADY_STATE:     fwAtSteadyState(); break;
      case Fw.STOPPED:             fwStopped(); break;
      case Fw.DESCENDANTS_STOPPED: fwDescendantsStopped(); break;
      case Fw.CHANGED:             fwChanged((Property)a); break;
      case Fw.RR:                  ((ResourceReport)a).add("proxyExt", 250); break;
    }                                    
    return super.fw(x, a, b, c, d);
  }
    
  private void fwStarted()
  {
    checkFatalFault(Metrics.incrementPoint(this));
    updateStatus();    
    getTuning().transition();
  }

  private void fwAtSteadyState()
  {
    getTuning().transition();
  }

  private void fwStopped()
  {                 
    getTuning().transition();
  }                                                    
  
  private void fwDescendantsStopped()
  {                     
    device     = null;
    deviceExt  = null;
  }

  private void fwChanged(Property prop)
  {                   
    if (!isRunning()) return;
        
    if (prop.equals(enabled))
    {
      updateStatus();   
    }
    
    else if (prop.equals(deviceFacets) || prop.equals(conversion))
    {
      forceWrite(true);                                
      executePoint();
    }
  }  

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out)
    throws Exception
  {            
    out.startProps("ProxyExt");
    out.prop("fatalFault",              ""+fatalFault());
    out.prop("stale",                   ""+stale());
    out.prop("readFault",               ""+readFault);
    out.prop("writeFault",              ""+writeFault);
    out.prop("lastWorking",             lastWorking);
    out.prop("convErrP2D",              convErrP2D());
    out.prop("convErrD2P",              convErrD2P());
    out.prop("tuning.subscribed",       ""+tuning.subscribed());
    out.prop("tuning.readTicks",        timestr(tuning.readTicks));
    out.prop("tuning.writeTicks",       timestr(tuning.writeTicks));
    out.prop("tuning.wrieDesiredTicks", timestr(tuning.writeTicks));
    out.prop("tuning.writePending",     ""+tuning.writePending());
    out.prop("tuning.state",            Tuning.toString(tuning.state()));
    out.prop("tuning.processId",        ""+tuning.processId());
    out.endProps();
    
    super.spy(out);
  }          
  
  static String timestr(long ticks)
  {
    if (ticks == 0) return "null";
    long now = Clock.ticks();
    long diff = now-ticks;
    if (diff < 1000) return ""+diff+"ms";
    return (diff/1000)+"sec";
  }

////////////////////////////////////////////////////////////////
// Flags
////////////////////////////////////////////////////////////////

  static final int FATAL_FAULT  = 0x01;  // locked forever once started
  static final int STALE        = 0x02;  // is readValue stale
  static final int FORCE_WRITE  = 0x04;  // force write on next execute
  static final int CONV_ERR_D2P = 0x08;  // device->proxy conversion error
  static final int CONV_ERR_P2D = 0x10;  // proxy->device conversion error
  
  private boolean fatalFault() { return (flags & FATAL_FAULT) != 0; }
  private void fatalFault(boolean b) { if (b) flags |= FATAL_FAULT; else flags &= ~FATAL_FAULT; }  

  private boolean stale() { return (flags & STALE) != 0; }
  private void stale(boolean b) { if (b) flags |= STALE; else flags &= ~STALE; }  

  private boolean forceWrite() { return (flags & FORCE_WRITE) != 0; }
  private void forceWrite(boolean b) { if (b) flags |= FORCE_WRITE; else flags &= ~FORCE_WRITE; }  

  private boolean convErrD2P() { return (flags & CONV_ERR_D2P) != 0; }
  private void convErrD2P(boolean b) { if (b) flags |= CONV_ERR_D2P; else flags &= ~CONV_ERR_D2P; }  

  private boolean convErrP2D() { return (flags & CONV_ERR_P2D) != 0; }
  private void convErrP2D(boolean b) { if (b) flags |= CONV_ERR_P2D; else flags &= ~CONV_ERR_P2D; }  

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private BDevice device;                // cached parentage
  private BPointDeviceExt deviceExt;     // cached parentage
  private int flags = STALE;             // bit mask flags
  private int oldStatus = BStatus.STALE; // last result of updateStatus
  private String readFault;              // cause msg or null for ok
  private String writeFault;             // cause msg or null for ok
  private BStatusValue lastWorking;      // last working variable of execute
  private Tuning tuning = new Tuning(this); // Tuning support instance

}
