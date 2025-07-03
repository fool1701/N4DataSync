/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BNetworkExt is the abstract base class for network extensions
 * which provide sub-functionality under a BDeviceNetwork.
 *
 * @author    Brian Frank       
 * @creation  17 Jun 04
 * @version   $Revision: 1$ $Date: 6/17/04 2:01:52 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BNetworkExt
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.BNetworkExt(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNetworkExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the parent network.
   */
  public final BDeviceNetwork getNetwork()
  {
    return (BDeviceNetwork)getParent();
  }
  
////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  /**
   * This method is called when the extension should recompute
   * its status (or the status of its children).  It is called
   * whenever the status of the parent network is modified.
   */
  public void updateStatus()
  {
  }

}
