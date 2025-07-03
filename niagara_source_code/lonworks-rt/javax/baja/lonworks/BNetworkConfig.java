/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import java.util.logging.Level;

import javax.baja.lonworks.datatypes.BModifyFlags;
import javax.baja.lonworks.datatypes.BNcProps;
import javax.baja.lonworks.datatypes.BNvConfigData;
import javax.baja.lonworks.enums.BLonConfigScope;
import javax.baja.lonworks.enums.BLonNodeState;
import javax.baja.lonworks.londata.BLonData;
import javax.baja.lonworks.util.SnvtUtil;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.lonworks.Lon;
import com.tridium.lonworks.util.NmUtil;
import com.tridium.lonworks.util.selfdoc.SelfDocUtil;

/**
 *  BNetworkConfig represents a single nci in a LonDevice. It
 *  provides specific support for runtime updates and
 *  contains data needed to support network managment.
 * <p>
 *
 * @author    Robert Adams
 * @creation  8 Nov 00
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:44 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Properties needed to manage the nci.
 */
@NiagaraProperty(
  name = "ncProps",
  type = "BNcProps",
  defaultValue = "new BNcProps()"
)
/*
 Shadows data in the devices nv config table.
 */
@NiagaraProperty(
  name = "nvConfigData",
  type = "BNvConfigData",
  defaultValue = "new BNvConfigData()"
)
/*
 Fired in receiveUpdate after all elements updated.
 Added in 3.6.27 and 3.5.35
 */
@NiagaraTopic(
  name = "receivedUpdate"
)
public class BNetworkConfig
  extends BLonComponent
  implements BINetworkVariable
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BNetworkConfig(395933656)1.0$ @*/
/* Generated Mon Nov 21 08:50:24 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ncProps"

  /**
   * Slot for the {@code ncProps} property.
   * Properties needed to manage the nci.
   * @see #getNcProps
   * @see #setNcProps
   */
  public static final Property ncProps = newProperty(0, new BNcProps(), null);

  /**
   * Get the {@code ncProps} property.
   * Properties needed to manage the nci.
   * @see #ncProps
   */
  public BNcProps getNcProps() { return (BNcProps)get(ncProps); }

  /**
   * Set the {@code ncProps} property.
   * Properties needed to manage the nci.
   * @see #ncProps
   */
  public void setNcProps(BNcProps v) { set(ncProps, v, null); }

  //endregion Property "ncProps"

  //region Property "nvConfigData"

  /**
   * Slot for the {@code nvConfigData} property.
   * Shadows data in the devices nv config table.
   * @see #getNvConfigData
   * @see #setNvConfigData
   */
  public static final Property nvConfigData = newProperty(0, new BNvConfigData(), null);

  /**
   * Get the {@code nvConfigData} property.
   * Shadows data in the devices nv config table.
   * @see #nvConfigData
   */
  public BNvConfigData getNvConfigData() { return (BNvConfigData)get(nvConfigData); }

  /**
   * Set the {@code nvConfigData} property.
   * Shadows data in the devices nv config table.
   * @see #nvConfigData
   */
  public void setNvConfigData(BNvConfigData v) { set(nvConfigData, v, null); }

  //endregion Property "nvConfigData"

  //region Topic "receivedUpdate"

  /**
   * Slot for the {@code receivedUpdate} topic.
   * Fired in receiveUpdate after all elements updated.
   * Added in 3.6.27 and 3.5.35
   * @see #fireReceivedUpdate
   */
  public static final Topic receivedUpdate = newTopic(0, null);

  /**
   * Fire an event for the {@code receivedUpdate} topic.
   * Fired in receiveUpdate after all elements updated.
   * Added in 3.6.27 and 3.5.35
   * @see #receivedUpdate
   */
  public void fireReceivedUpdate(BValue event) { fire(receivedUpdate, event, null); }

  //endregion Topic "receivedUpdate"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNetworkConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * No arg constructor
   */
  public BNetworkConfig()
  {
  }
  /**
   *  constructor
   */
  public BNetworkConfig( int              nvIndex,  
                         int              snvtType, 
                         int              configIndex, 
                         BModifyFlags     modifyFlag, 
                         BLonConfigScope  scope,  
                         String           select,
                         float[]				  init  )
  {
  	this(nvIndex,snvtType, configIndex, modifyFlag, scope, select);
    initDataElements(init);
  }
  
  public BNetworkConfig( int              nvIndex,  
                         int              snvtType, 
                         int              configIndex, 
                         BModifyFlags     modifyFlag, 
                         BLonConfigScope  scope,  
                         String           select )
  {
  	BNcProps  ncProps = getNcProps();
  	ncProps.setNvIndex    (nvIndex    ); 
  	ncProps.setSnvtType   (snvtType   ); 
  	ncProps.setConfigIndex(configIndex); 
  	ncProps.setModifyFlag (modifyFlag ); 
  	ncProps.setScope      (scope      ); 
  	ncProps.setSelect     (select     );  
  	
   	setData(SnvtUtil.getLonData(snvtType ));
 	
  }
  
  public BNetworkConfig( int              nvIndex,  
                         BLonData         data, 
                         int              configIndex, 
                         BModifyFlags     modifyFlag, 
                         BLonConfigScope  scope,  
                         String           select )
  {
  	BNcProps  ncProps = getNcProps();
  	ncProps.setNvIndex    (nvIndex    ); 
  	ncProps.setConfigIndex(configIndex); 
  	ncProps.setModifyFlag (modifyFlag ); 
  	ncProps.setScope      (scope      ); 
  	ncProps.setSelect     (select     );  
  	
   	setData(data);
  }


////////////////////////////////////////////////////////////////
// BINetworkVariable
////////////////////////////////////////////////////////////////
  /** @return always true */
  public boolean isNetworkConfig()   { return true; }

  /** Get the index of this nci in the lonworks device. */
  public int getNvIndex() { return getNcProps().getNvIndex(); }

  /** Set the index of this nv in the lonworks device. */
  public void setNvIndex(int nvIndex) { getNcProps().setNvIndex(nvIndex); }

  /** Get the snvt type. If not a snvt return 0. */
  public int getSnvtType() { return getNcProps().getSnvtType(); }

  /**
   * Set nv to unbound state - this should modif nv's config data and
   * other type specific elements.
   */
  public void setUnbound()
  {
    getNcProps().setUnbound();
    getNvConfigData().setUnbound(getNvIndex());
  }

  public void receiveUpdate(byte[] nvData)
  {
    try
    {
      getData().fromNetBytes(nvData);
      getData().readOk();
      fireReceivedUpdate(null);
    }
    catch(Throwable e)
    {
      getData().readFail(e.toString());
      lonNetwork().log().log(Level.SEVERE,"Could not decode nv update data " + getParent().getDisplayName(null) + ":" + getDisplayName(null),e); 
    }
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  public final void lonComponentStarted()
  {
    // If selector still at default (-1) set to unbound
    if(getNvConfigData().getSelector()==-1)     
       getNvConfigData().setUnbound(getNcProps().getNvIndex());
  }
  
  /**
   * Does this component represent data stored persistently in the device.
   * <p>
   * @return Returns true.
   */
  public boolean isForeignPersistent() { return true; }

  /**
   * Does this component represent a writable value in device.
   * <p>
   * @return false if the MfgOnly modifyFlag is true else returns true.
   */
  public boolean isWriteable() { return !getNcProps().getModifyFlag().isMfgOnly(); }

  /**
   * Respond to property changes.<p> If the prop=data and
   * the context!=BLonNetwork.lonNoWrite then update the nci
   * data in the device.
   */
  protected void dataChanged(Context context)
  {

    // If noWrite context then property changed by reading device.
    if(BLonNetwork.lonNoWrite.equals(context)) return;

    if(!isRunning()) return;

    forceWrite();
  }

  /** Read data from device. */
  public void doForceRead()
  {
    lonDevice().checkState();
    
    if(illegalLength)
      throw new BajaRuntimeException(getDisplayName(null) + " data length > maxNvLength of " + Lon.maxNvLength() + " bytes");

    BNcProps ncProps = getNcProps();
    try
    {
      if(Lon.d())
      {
        byte[] nvData =  NmUtil.fetchNv(lonDevice(), ncProps.getNvIndex());
        getData().fromNetBytes(nvData);
      }
      getData().readOk();
    }
    catch(Throwable e)
    {
      getData().readFail(e.toString());
      String errMsg = "Unable to read " + debugName();
      lonNetwork().log().log(Level.SEVERE,errMsg,e);
      throw new BajaRuntimeException(errMsg + " " + e.getMessage(),e);
    }
  }

  /** Write data to device. */
  public void doForceWrite()
  {
    if(!Lon.d()) return;

    BNvConfigData configData = getNvConfigData();
    // Sanity check
    if(!configData.isInput()) return;
    
    // Block writes to mfgOnly ncis
    BNcProps ncProps = getNcProps();
    if(ncProps.getModifyFlag().isMfgOnly())
      throw new BajaRuntimeException("Can not write mfgOnly nci " + getDisplayName(null));
    
    BLonDevice dev = lonDevice();
    dev.checkState();

    if(illegalLength)
      throw new BajaRuntimeException(getDisplayName(null) + " data length > maxNvLength of " + Lon.maxNvLength() + " bytes");
      
    boolean downloading = dev.isDownLoadInProgress();
    try
    {
      int[] sels = null;
      boolean[] objDis = null;
      boolean onlineReq = false;
      if(!downloading)
      {
        // Allow LonDevice subclasses to do setup before write 
        dev.beginConfigWrite();
        
        // Check if nci needs device offline
        if(ncProps.getModifyFlag().isOffline())
        {
          try { NmUtil.setDeviceState(dev, BLonNodeState.configOffline); } 
          catch(LonException e){System.out.println(e);}
          onlineReq = true;
        }
          
        // Check if nci needs objects disabled.
        if(ncProps.getModifyFlag().isDisabled() && 
           ncProps.getScope()==BLonConfigScope.object)
        {
          sels = SelfDocUtil.selectToIntArray(ncProps.getSelect());
          objDis = new boolean[sels.length];
          dev.disableObjectsForWrite(sels,objDis);
        }
      }
      
      // Update device.
      NmUtil.setNvValue( lonDevice(), configData, getData().toNetBytes());
      
      if(!downloading)
      {
        // If needed set device online
        if(onlineReq) 
        {
          try { NmUtil.setDeviceState(dev, BLonNodeState.configOnline); } 
          catch(LonException e){System.out.println(e);}
        }  
        // If object disabled then reenable.
        if(sels!=null) dev.enableObjectsAfterWrite(sels,objDis);
        // If needed reset device.
        if(ncProps.getModifyFlag().isReset()) dev.doReset();

        dev.endConfigWrite();
      }
      
      // Update any proxies
      getData().writeOk();
    }
    catch(Throwable e)
    {
      getData().writeFail(e.toString());
      String errMsg = "Unable to write " + debugName();
      lonNetwork().log().log(Level.SEVERE,errMsg,e); 
      throw new BajaRuntimeException(errMsg + " " + e.getMessage(),e);
    }
  }

  /** Callback to indicate LonComponent transition from 0 to 1 subscriber.
   *  If deviceSpecific facet set call forceRead() */
  protected void lonComponentSubscribed() 
  {
    BBoolean bb = (BBoolean)getPropertyInParent().getFacets().get("deviceSpecific");
    if(bb!=null && bb.getBoolean()) forceRead();
  }


////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make("module://lonworks/com/tridium/lonworks/ui/icons/nci.png");

}
