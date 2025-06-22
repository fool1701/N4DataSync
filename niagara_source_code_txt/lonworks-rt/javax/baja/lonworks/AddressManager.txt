/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import com.tridium.lonworks.BLonRouter;

import javax.baja.lonworks.datatypes.BDeviceData;
import javax.baja.lonworks.datatypes.BNeuronId;
import javax.baja.lonworks.datatypes.BSubnetNode;
import javax.baja.sys.BString;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;

/**
 * AddressManager interfaces defines the api to allow
 * managing the assignment and validation of channel ids
 * and subnet/node addresses for LonDevices.
 *
 * @author    Robert Adams
 * @creation  19 Feb 02
 * @version   $Revision: 14$ $Date: 10/17/00 12:47:14 PM$
 * @since     Niagara 3.0
 */
public interface AddressManager
{ 

  /** 
   * Register the device. Called by BLonDevice when
   * started.
   */
  public void registerLonDevice(BLonDevice dev);
 
  /** 
   * Unregister the device. Called by BLonDevice when
   * stopped.
   */
  public void unregisterLonDevice(BLonDevice dev);

  /** 
   *  Callback to indicate that essential device data, such
   *  as addressing, has changed.
   */
  public void deviceDataChanged(BDeviceData dd, Context context);
  
  /**
   * Get a list of all <code>BLonDevice</code> instances that 
   * are registered with this BLonNetwork excluding the localDevice.
   */ 
  public BLonDevice[] getDeviceList(boolean includeLocal);
  
  /**
   * Return the <code>BLonDevice</code> with the specified name.
   */
  public BLonDevice getDeviceByName(String name);
  
  /**
   * Return the <code>BLonDevice</code> with the specified 
   * <code>BSubnetNode</code> address.
   */
  public BLonDevice getDeviceByAddress(BSubnetNode addr);
  
  /**
   * Return the <code>BLonDevice</code> with the specified 
   * <code>BNeuronId</code> address.
   */
  public BLonDevice getDeviceByAddress(BNeuronId addr);
  
  /**
   * Return the <code>BLonRouter</code> with the specified 
   * <code>BSubnetNode</code> address.
   */
  public BLonRouter getRouterByAddress(BSubnetNode addr);
  
  /**
   * Return the <code>BLonRouter</code> with the specified 
   * <code>BNeuronId</code> address.
   */
  public BLonRouter getRouterByAddress(BNeuronId nid);
  
  /** 
   *  Assign the a new address to the specified device.
   *  Return string describing error condition or null if 
   *  address changed.
   */
  public BString newAddress(BLonDevice dev, int chan, int subnet, int node );

  /** 
   * Convenience method to obtain access to local device object.
   */
  public BLocalLonDevice getLocalDevice();
  
  public BLonRouter[] getRouterList();
  
  /**
   *  Context to pass when changing deviceData without firing a
   *  a BLonNetwork.deviceChange.  This should be used when there 
   *  are multiple changes to make in sequence and table update 
   *  is only desired on last change. 
   *  <p>
   *  This will also block any update to the physical device.
   */
  public static final Context noDeviceChange = new BasicContext();
  
    
}
