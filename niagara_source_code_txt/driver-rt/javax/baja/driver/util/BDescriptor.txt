/* 
 * Copyright 2004 Tridium, Inc.  All rights reserved.
 * 
 */

package javax.baja.driver.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.driver.BDevice;
import javax.baja.driver.BDeviceNetwork;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.status.BStatus;
import javax.baja.sys.BComplex;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.metrics.BISubLicenseable;
import com.tridium.sys.schema.Fw;

/**
 * Maps an object in the local database to a remote object.  Must have
 * a BDescriptorDeviceExt ancestor.
 * <p><b>Subclasses</b>
 * <ul>
 * <li>Implement postExecute()
 * <li>Are responsible for calling the following lifecycle methods:
 *   <li>executeInProgress() - must call when doExecute() is invoked
 *   <li>executeFail()
 *   <li>executeOk()
 *  <br>
 * </ul>
 * @author John Sublett, Aaron Hansen
 * @creation Feb 2004
 * @version $Revision: 35$ $Date: 7/2/08 11:57:15 AM EDT$
 */

@NiagaraType
public abstract class BDescriptor
  extends BAbstractDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.util.BDescriptor(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

/////////////////////////////////////////////////////////////////
// Constructors
/////////////////////////////////////////////////////////////////

  public BDescriptor() {}

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

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
  public final BDescriptorDeviceExt getDeviceExt()
  {
    if (deviceExt != null) return deviceExt;
    if (!isRunning()) throw new NotRunningException();
    throw new IllegalStateException(getFaultCause());
  }

  /**
   * Get the parent network.  Throw an exception if this 
   * component is not running or has a fatal fault.
   */
  public final BDeviceNetwork getNetwork()
  {
    if (network != null) return network;
    if (!isRunning()) throw new NotRunningException();
    throw new IllegalStateException(getFaultCause());
  }

/////////////////////////////////////////////////////////////////
// Status 
/////////////////////////////////////////////////////////////////

  /**
   * If true, execute should be short circuited.
   * @return True if down, disabled, or in fatal fault
   */
  @Override
  public boolean isUnoperational()
  {
    return fatalFault || super.isUnoperational();
  }

  /**
   * Return true if the parent device is down.
   */
  @Override
  public final boolean isDown()
  {
    return super.isDown();
  }

  /**
   * Return true if in fault.  Is in fault if
   * either a fatal fault was detected, the last execute failed.
   * Also automatically  marked in fault if the parent device is in fault.
   */
  @Override
  public final boolean isFault()
  {
    return super.isFault();
  }

  /**
   * Return true if disabled.
   * Is disabled is the user has manually
   * set the network, device, or descriptor enabled
   * property to false.
   */
  @Override
  public final boolean isDisabled()
  {
    return super.isDisabled();
  }

  @Override
  public final void updateStatus()
  {
    int newStatus = getStatus().getBits();
    BStatus devStatus = (device == null) ? BStatus.ok : device.getStatus();

    // disabled bit
    if (!getEnabled() || devStatus.isDisabled())
      newStatus |= BStatus.DISABLED;
    else
      newStatus &= ~BStatus.DISABLED;

    // down bit
    if (devStatus.isDown()) 
      newStatus |= BStatus.DOWN;
    else
      newStatus &= ~BStatus.DOWN;

    // fault bit
    if (isFatalFault()
        || (!getLastFailure().isNull() && getLastFailure().isAfter(getLastSuccess()))
        || devStatus.isFault())
      newStatus |= BStatus.FAULT;
    else
      newStatus &= ~BStatus.FAULT;
    
    // short circuit if no changes
    if (newStatus == oldStatus) return;

    setStatus(BStatus.make(newStatus));
    oldStatus = newStatus;
  }

////////////////////////////////////////////////////////////////
// Fault
////////////////////////////////////////////////////////////////
  
  /**
   * Return true if the point detected a fatal fault.
   * Fatal faults cannot be recovered until the descriptor
   * is restarted.
   */
  public final boolean isFatalFault()
  {                 
    return fatalFault;
  }  
    
  /**
   * This method checks for fatal faults built into the framework.
   */
  private void checkFatalFault()
  {                         
    BDescriptorDeviceExt deviceExt = null;
    BDevice device;
    BDeviceNetwork network;
    // find device ext
    BComplex parent = getParent();
    while(parent != null)                       
    {
      if (parent instanceof BDescriptorDeviceExt)
        { deviceExt = (BDescriptorDeviceExt)parent; break; }
      parent = parent.getParent();
    }                   
    // check mounted in device ext 
    if (deviceExt == null)
    {
      fatalFault = true;
      setFaultCause("Not under DescriptorDeviceExt");
      return;
    }        
    // check mounted in device
    device = deviceExt.getDevice();
    if (device == null)
    {
      fatalFault = true;
      setFaultCause("Not under Device");
      return;
    }
    // check device fatal fault 
    if (device.isFatalFault())
    {
      fatalFault = true;
      setFaultCause("Device fault: " + device.getFaultCause());
      return;
    }         
    // check mounted in network
    network = device.getNetwork();
    if (network == null)
    {
      fatalFault = true;
      setFaultCause("Not under DeviceNetwork");
      return;
    }                  
    // check network fatal fault 
    if (network.isFatalFault())
    {
      fatalFault = true;
      setFaultCause("Network fault: " + network.getFaultCause());
      return;
    }             
    // check license                                           
    String licenseLimitKey = getLicenseLimitKey();
    if (licenseLimitKey != null)
    {
      Object licenseFault = network.fw(Fw.CHECK_LICENSE_LIMIT,
                                       BISubLicenseable.getLicenseKey(this, licenseLimitKey),
                                       this, null, null);
      if (licenseFault != null)
      {
        fatalFault = true;
        setFaultCause(licenseFault.toString());
        return;
      }
    }
    // no fatal faults
    this.network    = network;
    this.device     = device;
    this.deviceExt  = deviceExt;
    setFaultCause("");                                       
    // sanity checks
    if (this.network == null || this.device  == null || this.deviceExt == null)
      throw new IllegalStateException();
  }           
  
  /**
   * Eventually maybe this could be a protected hook, but 
   * for now just hard-code the keys.
   */
  private String getLicenseLimitKey()
  {                             
    Type t = getType();
    if (archiveType != null && t.is(archiveType))
    {
      return "history.limit";
    }
    if (schedImportType != null && t.is(schedImportType) ||
        schedExportType != null && t.is(schedExportType))
    {
      return "schedule.limit";
    }

    return null;
  }

  static TypeInfo archiveType;
  static TypeInfo schedExportType;
  static TypeInfo schedImportType;
  static
  {
    try
    {
      archiveType = Sys.getRegistry().getType("driver:ArchiveDescriptor");
    }
    catch(Exception e)
    {
      Logger.getLogger(TYPE.getModule().getModuleName()).log(Level.SEVERE,
        "Could not find type 'driver:ArchiveDescriptor'", e);
    }
    try
    {
      schedExportType = Sys.getRegistry().getType("driver:ScheduleExport");
    }
    catch(Exception e)
    {
      Logger.getLogger(TYPE.getModule().getModuleName()).log(Level.SEVERE,
        "Could not find type 'driver:ScheduleExport'", e);
    }
    try
    {
      schedImportType = Sys.getRegistry().getType("driver:ScheduleImportExt");
    }
    catch(Exception e)
    {
      Logger.getLogger(TYPE.getModule().getModuleName()).log(Level.SEVERE,
        "Could not find type 'driver:ScheduleImportExt'", e);
    }
  }

////////////////////////////////////////////////////////////////
// Framework Implementation
////////////////////////////////////////////////////////////////

  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch (x)
    {
      case Fw.STARTED: return fwStarted(a, b, c, d);
      case Fw.STOPPED: fwStopped(); break;
    }

    return super.fw(x, a, b, c, d);
  }

  private Object fwStarted(Object a, Object b, Object c, Object d)
  {
    checkFatalFault();
    super.fw(Fw.STARTED, a, b, c, d);

    if (Sys.isStationStarted())
    {
      super.fw(Fw.STATION_STARTED, a, b, c, d);
    }
    return null;
  }

  private void fwStopped()
  {
    network    = null;
    device     = null;
    deviceExt  = null;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private BDevice device;                     // cached parentage
  private BDescriptorDeviceExt deviceExt;     // cached parentage
  private boolean fatalFault;                 // locked forever once started
  private BDeviceNetwork network;             // cached parentage
  private int oldStatus = BStatus.STALE;      // last result of updateStatus

}//BDescriptor
