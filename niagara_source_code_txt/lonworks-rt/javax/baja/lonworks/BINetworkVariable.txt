/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import javax.baja.lonworks.datatypes.BNvConfigData;
import javax.baja.lonworks.londata.BLonData;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 *  The BINetworkVariable interface is implemented by 
 * <code>BNetworkVariable</code> and <code>BNetworkConfig</code> because both
 * are implemented as nvs in lonworks devices. Both 
 * have NvConfigData which must be managed. 
 * <p>
 *  
 * @author    Robert Adams
 * @creation  14 Feb 02
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:44 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
public interface BINetworkVariable
  extends BInterface
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BINetworkVariable(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:20 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BINetworkVariable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Get the components name.
   */
  public String getName();
  
  /**
   * Get the <code>nvConfigData</code> property.
   */
  public BNvConfigData getNvConfigData();
  
  /**
   * Set the <code>nvConfigData</code> property.
   */
  public void setNvConfigData(BNvConfigData v);


  /** Get the index of BINetworkVariable in the device. */
  public int getNvIndex();
  
  /** Set the index of BINetworkVariable in the device. */
  public void setNvIndex(int nvIndex);
 
  /** Get the snvt type. If not a snvt return 0. */
  public int getSnvtType();

  /**
   * Get the <code>data</code> property.
   */
  public BLonData getData();
  
 
  /**
   * Set nv to unbound state - this should modif nv's config data and
   * other type specific elements.
   */
  public void setUnbound();
  
  /** Receive nvUpdate for this nv. */
  public void receiveUpdate(byte[] nvData);

  /** Is this BINetworkVariable a BNetworkVariable. */
  public boolean isNetworkVariable();
  /** Is this BINetworkVariable a BNetworkConfig. */
  public boolean isNetworkConfig();
  /** Is this LonComponent a BLocalNetworkVariable */
  public boolean isLocalNv();
  /** Is this LonComponent a BLocalNetworkConfig */
  public boolean isLocalNci();
  
  
  public static final int MAX_NV_INDEX = 0x0fff;
  
}
