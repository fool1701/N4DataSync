/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import javax.baja.lonworks.datatypes.BDeviceData;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 *  The BINvContainer interface is implemented by 
 * <code>BLonDevice</code> and <code>BLonObject</code> because both contain
 * <code>BNetworkVariables</code>.  The interface is needed to manage
 * links and bindings.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  16 Dec 05
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:44 PM$
 * @since     Niagara 3.1
 */
@NiagaraType
public interface BINvContainer
  extends BInterface
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BINvContainer(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:20 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BINvContainer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  // From BComponent
  public BComponent asComponent(); 
  public String getName();
  public String getDisplayName(Context cx);
  
 
  /**
   * Get array of NetworkVariables that are descendants of this
   * NvContainer. The nvs are indexed by nvIndex.  There will be
   * null entries if a given nv is not represented in the
   * container.
   */
  public BINetworkVariable[] getNetworkVariables();

  public BDeviceData getDeviceData();
  
  public BLonNetwork getLonNetwork();

  public BLonDevice getLonDevice();
  
  public boolean isLonObject();
  
  public void linkUpdate();
  

  public static final int NOT_POLLED                = 0;     
  public static final int POLLED                    = 0x0001;
  public static final int AUTH_NOT_CONFIGURABLE     = 0x0002;
  public static final int SERVICE_NOT_CONFIGURABLE  = 0x0004;
  public static final int PRIORITY_CONFIGURABLE     = 0x0008;
  public static final int AUTHENTICATE              = 0x0010;
  public static final int PRIORITY                  = 0x0020;
  public static final int SERVICE_ACKED             = 0x0040;
  public static final int SERVICE_UNACKED_RPT       = 0x0080;
  public static final int CONFIG_OFFLINE            = 0x0100;
                                                             
  public static final int CHANGEABLE_NV             = 0x0200;
  public static final int CHANGEABLE_NV_CONFIG      = 0x0200;
  
}
  
