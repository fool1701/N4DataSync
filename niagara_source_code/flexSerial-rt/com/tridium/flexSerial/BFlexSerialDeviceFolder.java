/*
 * Copyright 2005 Tridium, All Rights Reserved.
 */
package com.tridium.flexSerial;

import javax.baja.driver.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BFlexSerialDeviceFolder
 *
 * @author    Andy Saunders
 * @creation  19-Apr-05
 * @version   $Revision$ $Date: 1/26/2005 6:27:07 PM$
 */
@NiagaraType
public class BFlexSerialDeviceFolder
  extends BDeviceFolder
{                       
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.BFlexSerialDeviceFolder(2979906276)1.0$ @*/
/* Generated Thu Sep 30 12:51:14 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexSerialDeviceFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the network cast to a BFlexSerialNetwork.
   */
  public final BFlexSerialNetwork getFlexSerialNetwork()
  {
    return (BFlexSerialNetwork)getNetwork();
  }
                
}
