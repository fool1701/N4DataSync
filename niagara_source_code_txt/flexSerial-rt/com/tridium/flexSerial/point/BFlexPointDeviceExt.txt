/*
 * Copyright 2005 Tridium, All Rights Reserved.
 */
package com.tridium.flexSerial.point;

import javax.baja.control.*;
import javax.baja.control.ext.*;
import javax.baja.driver.*;
import javax.baja.driver.point.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

import com.tridium.flexSerial.*;
import com.tridium.flexSerial.point.*;

/**
 * BFlexPointDeviceExt
 *
 * @author    Andy Saunders
 * @creation  19-Apr-05
 * @version   $Revision$ $Date: 1/26/2005 6:27:07 PM$
 */
@NiagaraType
public class BFlexPointDeviceExt
  extends BPointDeviceExt
{            
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.point.BFlexPointDeviceExt(2979906276)1.0$ @*/
/* Generated Thu Sep 30 12:51:14 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexPointDeviceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Callback when network is started.
   */
  public void started()
    throws Exception
  {
    super.started();                             
  }

  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the network cast to a BFlexSerialNetwork.
   */
  public final BFlexSerialNetwork getFlexNetwork()
  {
    return (BFlexSerialNetwork)getNetwork();
  }

  /**
   * Get the device cast to a BFlexSerialDevice.
   */
  public final BFlexSerialDevice getFlexDevice()
  {
    return (BFlexSerialDevice)getDevice();
  }

////////////////////////////////////////////////////////////////
// PointDeviceExt
////////////////////////////////////////////////////////////////
  
  /**
   * Return the Device type.
   */
  public Type getDeviceType()
  {
    return BFlexSerialDevice.TYPE;
  }

  /**
   * Return the PointFolder type.
   */
  public Type getPointFolderType()
  {
    return BFlexPointFolder.TYPE;
  }
  
  /**
   * Return the ProxyExt type.
   */
  public Type getProxyExtType()
  {
    return BFlexProxyExt.TYPE;
  }

}
