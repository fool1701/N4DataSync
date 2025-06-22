/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import java.util.logging.Level;

import javax.baja.lonworks.datatypes.BConfigProps;
import javax.baja.lonworks.datatypes.BModifyFlags;
import javax.baja.lonworks.enums.BLonConfigScope;
import javax.baja.lonworks.enums.BLonNodeState;
import javax.baja.lonworks.londata.BLonData;
import javax.baja.lonworks.util.LonFile;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.lonworks.util.NmUtil;
import com.tridium.lonworks.util.selfdoc.SelfDocUtil;

/**
 *  BConfigParameter represents a single config parameter
 *  in a LonDevice. It provides specific support for runtime
 *  updates.
 * <p>
 *
 * @author    Robert Adams
 * @creation  8 Nov 00
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:39 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Properties needed to access config parameter in device.
 */
@NiagaraProperty(
  name = "configProps",
  type = "BConfigProps",
  defaultValue = "new BConfigProps()"
)
public class BConfigParameter
  extends BLonComponent
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BConfigParameter(3555731322)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "configProps"

  /**
   * Slot for the {@code configProps} property.
   * Properties needed to access config parameter in device.
   * @see #getConfigProps
   * @see #setConfigProps
   */
  public static final Property configProps = newProperty(0, new BConfigProps(), null);

  /**
   * Get the {@code configProps} property.
   * Properties needed to access config parameter in device.
   * @see #configProps
   */
  public BConfigProps getConfigProps() { return (BConfigProps)get(configProps); }

  /**
   * Set the {@code configProps} property.
   * Properties needed to access config parameter in device.
   * @see #configProps
   */
  public void setConfigProps(BConfigProps v) { set(configProps, v, null); }

  //endregion Property "configProps"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BConfigParameter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * No arg constructor
   */
  public BConfigParameter()
  {
  }

  /**
   * No arg constructor
   */
  public BConfigParameter(BLonData        data       ,
                          int             offset     ,
                          int             length     ,
                          BModifyFlags    modifyFlag ,
                          BLonConfigScope scope      ,
                          String          select      )
  {
  	BConfigProps cfgProps = getConfigProps();
  	cfgProps.setOffset    (offset    );
  	cfgProps.setLength    (length    );
  	cfgProps.setModifyFlag(modifyFlag);
  	cfgProps.setScope     (scope     );
  	cfgProps.setSelect    (select    );

    setData(data);
  }


  /** @return always true */
  public boolean isConfigParameter() { return true; }

  /**
   * Does this component represent data stored persistently in the device.
   * <p>
   * @return always true.
   */
  public boolean isForeignPersistent() { return true; }

  /**
   * Does this component represent a writable value in device.
   * <p>
   * @return false if the MfgOnly modifyFlag is true else returns true.
   */
  public boolean isWriteable() 
  { 
    BModifyFlags mf = getConfigProps().getModifyFlag();
    return !mf.isMfgOnly() && !mf.isConst(); 
  }
  
  /**
   * Respond to data changes.<p> If the context!=BLonNetwork.lonNoWrite
   * and the device is running then update the cp data in the device.
   */
  protected void dataChanged(Context context)
  {
    // If noWrite context then property changed by reading device.
    if(BLonNetwork.lonNoWrite.equals(context)) return;

    if(!isRunning()) return;
   //System.out.println("changed " + getDisplayName(null) + "." + prop.getName() + "  " +  Clock.millis());

    doForceWrite();
  }

  /** Write data to device. If readOnly, mfgOnly data throw runtime exception.*/
  public void doForceWrite()
  {
    // Block writes to mfgOnly cps
    BConfigProps configProps = getConfigProps();
    if(configProps.getModifyFlag().isMfgOnly())
      throw new BajaRuntimeException("Can not write mfgOnly cp " + getDisplayName(null));
    if(configProps.getModifyFlag().isConst())
      throw new BajaRuntimeException("Can not write constant cp " + getDisplayName(null));
      
    BLonDevice dev = lonDevice();
    dev.checkState();
    boolean downloading = dev.isDownLoadInProgress();
    try
    {
      // Get file.
      LonFile f = dev.getReadWriteFile();
      if(f==null) throw new LonException("Error writing " + getDisplayName(null) + ": could not access file");

      int[] sels = null;
      boolean[] objDis = null;
      boolean onlineReq = false;
      boolean failedObjDevOff = false;
      if(!downloading)
      {
        // Allow LonDevice subclasses to do setup before write 
        dev.beginConfigWrite();
   
        // Check if cp needs objects disabled.
        if(configProps.getModifyFlag().isDisabled() && 
           configProps.getScope()==BLonConfigScope.object)
        {
          sels = SelfDocUtil.selectToIntArray(configProps.getSelect());
          objDis = new boolean[sels.length];
          try 
          { 
            dev.disableObjectsForWrite(sels,objDis); 
          }
          catch(Throwable e) 
          {
            // If not able to disable object - set device offline - See LonMark Interop Guide 2.7.3.4 FB-Disabled
            lonNetwork().log().log(Level.WARNING, "Unable to disable object " + configProps.getSelect() ,e);
            failedObjDevOff=true;
          }
        }

        // Check if cp needs device offline
        if(configProps.getModifyFlag().isOffline() || failedObjDevOff)
        {
          try { NmUtil.setDeviceState(dev, BLonNodeState.configOffline); } 
          catch(LonException e){System.out.println(e);}
          onlineReq = true;
        }  
      }
        
      // Update device.
      f.write(getData().toNetBytes(), configProps.getOffset());

      if(!downloading)
      {
        f.flush();
        
        // If needed set device online
        if(onlineReq)
        {
          try { NmUtil.setDeviceState(dev, BLonNodeState.configOnline); } 
          catch(LonException e){System.out.println(e);}
        }  
        // If object disabled then reenable.
        if(sels!=null) dev.enableObjectsAfterWrite(sels,objDis);
        // If needed reset device.
        if(configProps.getModifyFlag().isReset()) dev.doReset();
    
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
  
  

  /** Read data from device. */
  public void doForceRead()
  {
    BLonDevice dev = lonDevice();
    dev.checkState();
    
    BConfigProps configProps = getConfigProps();

    //
    // Get appropriate file.
    LonFile f = (configProps.getModifyFlag().isConst()) ? dev.getReadOnlyFile() : dev.getReadWriteFile();
    if(f==null) throw new RuntimeException("Error reading " + getDisplayName(null) + ": could not access file");


    // Update value
    try
    {
      getData().fromNetBytes(f.read(configProps.getOffset(), configProps.getLength()));
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
  private static final BIcon icon = BIcon.make("module://lonworks/com/tridium/lonworks/ui/icons/cp.png");


}
