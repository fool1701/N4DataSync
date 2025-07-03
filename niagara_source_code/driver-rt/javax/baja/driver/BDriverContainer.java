/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIService;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BDriverContainer is used by convention to store all BDeviceNetworks
 * in a station database.
 *
 * @author    Brian Frank
 * @creation  18 Dec 03
 * @version   $Revision: 2$ $Date: 1/5/04 5:22:27 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BDriverContainer
  extends BComponent
  implements BIService
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.BDriverContainer(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDriverContainer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public Type[] getServiceTypes() { return new Type[] { TYPE }; }
  public void serviceStarted() throws Exception {}
  public void serviceStopped() throws Exception {}
  
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("deviceNetwork.png");
}
