/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import javax.baja.agent.AgentList;
import javax.baja.driver.BDeviceFolder;
import javax.baja.driver.loadable.BDownloadParameters;
import javax.baja.driver.loadable.BUploadParameters;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.BFolder;

import com.tridium.lonworks.device.BDownloadJob;
import com.tridium.lonworks.device.BUploadJob;
import com.tridium.lonworks.device.NvDev;

/**
 * BLonDeviceFolder is the standard container to use 
 * under LonDeviceNetwork to organize Devices.
 *
 * @author    Robert Adams
 * @creation  13 Sept 04
 * @version   $Revision: 1$ $Date: 9/12/2004 1:39:32 PM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Upload reads data from the physical device.
 */
@NiagaraAction(
  name = "upload",
  parameterType = "BUploadParameters",
  defaultValue = "new BUploadParameters()"
)
/*
 Download writes data to the physical device.
 */
@NiagaraAction(
  name = "download",
  parameterType = "BDownloadParameters",
  defaultValue = "new BDownloadParameters()"
)
public class BLonObjectFolder
  extends BFolder
  implements BILonLoadable
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BLonObjectFolder(2832818901)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "upload"

  /**
   * Slot for the {@code upload} action.
   * Upload reads data from the physical device.
   * @see #upload(BUploadParameters parameter)
   */
  public static final Action upload = newAction(0, new BUploadParameters(), null);

  /**
   * Invoke the {@code upload} action.
   * Upload reads data from the physical device.
   * @see #upload
   */
  public void upload(BUploadParameters parameter) { invoke(upload, parameter, null); }

  //endregion Action "upload"

  //region Action "download"

  /**
   * Slot for the {@code download} action.
   * Download writes data to the physical device.
   * @see #download(BDownloadParameters parameter)
   */
  public static final Action download = newAction(0, new BDownloadParameters(), null);

  /**
   * Invoke the {@code download} action.
   * Download writes data to the physical device.
   * @see #download
   */
  public void download(BDownloadParameters parameter) { invoke(download, parameter, null); }

  //endregion Action "download"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonObjectFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
        
////////////////////////////////////////////////////////////////
// IDeviceFolder
////////////////////////////////////////////////////////////////

  public boolean isParentLegal(BComponent parent)
  {
    return ( parent.getType().is(BLonDevice.TYPE) ||
             parent.getType().is(BLonObjectFolder.TYPE) );
  }
  
  public boolean isChildLegal(BComponent child)
  {
    if( child.getType().is(BDeviceFolder.TYPE)/* &&
        !child.getType().is(BLonObjectFolder.TYPE)*/ )
    {
      return false;
    }    
    return true;
  }

  /** Change wire sheets */
  public AgentList getAgents(Context cx)
  {
    AgentList list = super.getAgents(cx);
    return NvDev.fixWireSheet(list,cx);
  }
  
  
  /** Don't allow second local device. 
  public void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
    if(value.getType().is(BLocalLonDevice.TYPE)) 
      throw new LocalizableRuntimeException("lonworks", "addLocalDeviceError");
  }
*/ 
  /** Let LonNetmgmt know about new devices and routers. 
  public void added(Property prop, Context context)
  {
    try
    {
      if(!isRunning()) return;

      if(prop.getType().is(BLonDevice.TYPE))
        getLonNetwork().netmgmt().deviceAdded((BLonDevice)get(prop));
      else if(prop.getType().is(BLonRouter.TYPE))
        getLonNetwork().netmgmt().routerAdded((BLonRouter)get(prop));
    }
    finally
    {
      super.added(prop,context);
    }
  }
  */  
  /**
   * Get the parent network.
   */
  public final BLonNetwork getLonNetwork()
  {            
    BComplex p = getParent();
    while(p != null)
    {
      if(p.getType().is(BLonNetwork.TYPE))
        return (BLonNetwork)p;
      p = p.getParent();
    }
    throw new IllegalStateException();
  }
  /**
   * Get the parent network.
   */
  public final BLonDevice getLonDevice()
  {            
    BComplex p = getParent();
    while(p != null)
    {
      if(p.getType().is(BLonDevice.TYPE))
        return (BLonDevice)p;
      p = p.getParent();
    }
    throw new IllegalStateException();
  }
 
////////////////////////////////////////////////////////////
//  BILonLoadable Implementation
////////////////////////////////////////////////////////////
  /** Callback for processing upLoad. */
  public final void doUpload(BUploadParameters p, Context cx)
    throws Exception
  {
    getLonDevice().checkState();
    new BUploadJob(this,p,cx).submit(cx);
  }
  
  /** Callback for processing downLoad. */
  public final void doDownload(BDownloadParameters p, Context cx)
    throws Exception
  {
    getLonDevice().checkState();
    new BDownloadJob(this,p,cx).submit(cx);
  }
  
   
  /** Override point to customize upload process.  This will be called before the upload job is initiated. 
   * If a subclass wishes to cancel the upload, then a LocalizableRuntimeException should be thrown.*/ 
  public void checkUpload(){}
  /** Override point to customize upload process.  This will be called at the beginning of an upload operation. */ //after initUpload() 
  public void beginUpload()
  {
    BLonDevice dev = getLonDevice();
    if(!dev.isUpLoadInProgress()) { dev.initUpload(true); objUploadInProgress=true; }
  }
  private boolean objUploadInProgress = false;
  /** Override point to customize upload process.  This will be called after all device components have been uploaded. */ 
  public void endUpload()
  {
    BLonDevice dev = getLonDevice();
    if(objUploadInProgress) { dev.cleanupUpload(); objUploadInProgress=false; }
  }

  /** Override point to customize download process.  This will be called before the download job is initiated. 
  If a subclass wishes to cancel the download, then a LocalizableRuntimeException should be thrown.*/ 
  public void checkDownload(){}
  /** Override point to customize download process.  This will be called at the beginning of a download operation . */ //after initDownload()
  public void beginDownload()
  {
    BLonDevice dev = getLonDevice();
    // If object download not part of device download then allow randow access
    if(!dev.isDownLoadInProgress()) { dev.initDownload(true); objDownloadInProgress=true; }
  }
  private boolean objDownloadInProgress = false;
  /** Override point to customize download process.  This will be called after all device components have been downloaded. */ 
  public void endDownload()
  {
    BLonDevice dev = getLonDevice();
    if(!dev.isDownLoadInProgress()) { dev.cleanupDownload(); objDownloadInProgress=false; }
  }

  
////////////////////////////////////////////////////////////
//  Visualization
////////////////////////////////////////////////////////////
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make("module://lonworks/com/tridium/lonworks/ui/icons/objectFolder.png");


}
