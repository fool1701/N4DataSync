/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BDeviceExt is the abstract base class for device extensions
 * which provide feature integrations between the device's native
 * protocol and model and the framework's normalized model. 
 *
 * @author    Brian Frank       
 * @creation  17 Oct 01
 * @version   $Revision: 18$ $Date: 8/16/04 8:35:43 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BDeviceExt
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.BDeviceExt(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDeviceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the grandparent network.
   */
  public final BDeviceNetwork getNetwork()
  {
    return getDevice().getNetwork();
  }
  
  /**
   * Get the parent device.
   */
  public final BDevice getDevice()
  {
    // Starting in Niagara 4.13, this implementation was changed to look for the
    // first ancestor BDevice, which may not necessarily be the immediate parent.
    // Previously this code always assumed that the direct parent was a BDevice
    // instance (which is still true for all drivers other than NiagaraDriver).
    // Going forward, with the possibility of reachable stations in the
    // NiagaraNetwork, this implementation needed to change to allow special
    // device exts to be placed under BReachableStationInfo instances (which
    // are not themselves BDevices).  This exception should not be followed by
    // other drivers outside of NiagaraDriver.  In particular,
    // BDevice#getDeviceExts() still only looks at direct children to find the
    // available BDeviceExt instances. So even with this method's implementation
    // change, it is not designed for BDeviceExt subclasses (outside of the
    // exception mentioned for reachable stations) to live anywhere other than a
    // direct child of a BDevice.
    BComplex parent = getParent();
    while(parent != null)
    {
      if (parent instanceof BDevice)
      {
        return (BDevice)parent;
      }
      parent = parent.getParent();
    }

    return null;
  }

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  /**
   * This method is called when the extension should recompute
   * its status (or the status of its children).  It is called
   * whenever the status the parent device is modified.
   */
  public void updateStatus()
  {
  }

}
