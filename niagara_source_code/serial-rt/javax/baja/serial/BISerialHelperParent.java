/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.serial;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BISerialHelperParent defines the callback methods required
 * by any parent of BSerialHelper.  The method reopenPort()
 * is called by BSerialHelper when the port name has changed
 * to indicate that a reinitialization of the serial port is 
 * required (i.e. close the old port, and call BSerialHelper.open()
 * to reopen the new port).  The changed() method is implemented by
 * BComponent (implementers of this interface should always extend 
 * from BComponent) and provides the callback to notify changes made
 * to any of the BSerialHelper properties.
 *
 * @author    Scott Hoye
 * @creation  6 Feb 04
 * @version   $Revision: 2$ $Date: 2/16/04 1:48:45 PM EST$  
 * @since     Baja 1.0
 */
@NiagaraType
public interface BISerialHelperParent
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.serial.BISerialHelperParent(2979906276)1.0$ @*/
/* Generated Fri Sep 17 11:17:08 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BISerialHelperParent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * This method is called by BSerialHelper when the port name has changed
   * to indicate that a reinitialization of the serial port is 
   * required (i.e. implementers should close the old port, and 
   * and then call BSerialHelper.open() to open the new port).
   */
  public void reopenPort();
  
  /**
   * This method is called by BSerialHelper when any of its properties
   * has changed.  It gives the BISerialHelperParent an opportunity to 
   * perform any actions based on a change to the SerialHelper settings.
   */
  public void changed(Property property, Context context);
  
}
