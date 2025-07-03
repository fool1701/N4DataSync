/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio;

import javax.baja.driver.BDeviceFolder;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * GeM6 implementation of DeviceFolder
 *                      
 * @author    Andy Saunders
 * @creation  13 Sep 04
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:10 AM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BNrioDeviceFolder
  extends BDeviceFolder
{                           
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.BNrioDeviceFolder(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:13 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioDeviceFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
