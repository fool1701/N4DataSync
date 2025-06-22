/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.serial;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * The BISerialService is the public API used to lookup serial ports
 *
 *
 * @author    Dan Giorgis
 * @creation  03 Feb 04
 * @version   $Revision: 5$ $Date: 3/28/05 11:40:35 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BISerialService
  extends BIService
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.serial.BISerialService(2979906276)1.0$ @*/
/* Generated Fri Sep 17 11:17:08 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BISerialService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Lazy-loads serial service properties.
   * 
   * This must be called before any getters are used, otherwise
   * they can return inaccurate results.
   */
  public void checkPropertiesLoaded();

  /**
   *  Returns array of all port names on the platform
   */
  public String[] getPortNames();  
  
  /**
   * Return the current owner for the given port
   */
  public String getCurrentOwner(String portName)
    throws PortNotFoundException;
  
  /**
   *  Open port for use by the given owner.
   *  The port may be released by calling BSerialPort.close();
   */  
  public BISerialPort openPort(String port, String owner)
    throws PortNotFoundException, PortDeniedException;
    
  /** 
   *  Get the mimimum receive timeout supported on the platform
   */
  public int getMinTimeout();
       

}
