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

import com.tridium.lonworks.BLonRouter;
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
public class BLonDeviceFolder
  extends BDeviceFolder
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BLonDeviceFolder(2832818901)1.0$ @*/
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
  public static final Type TYPE = Sys.loadType(BLonDeviceFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
        
////////////////////////////////////////////////////////////////
// IDeviceFolder
////////////////////////////////////////////////////////////////

  public boolean isParentLegal(BComponent parent)
  {
    return ( parent.getType().is(BLonNetwork.TYPE) ||
             parent.getType().is(BLonDeviceFolder.TYPE) );
  }
  
  public boolean isChildLegal(BComponent child)
  {
    if( child.getType().is(BDeviceFolder.TYPE) &&
        !child.getType().is(BLonDeviceFolder.TYPE) )
    {
      return false;
    }    
    return true;
  }
  
  /** Don't allow second local device. */
  public void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
    if(value.getType().is(BLocalLonDevice.TYPE)) 
      throw new LocalizableRuntimeException("lonworks", "addLocalDeviceError");
  }

  /** Let LonNetmgmt know about new devices and routers. */
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
  
  /** Change wire sheets */
  public AgentList getAgents(Context cx)
  {
    AgentList list = super.getAgents(cx);
    return NvDev.fixWireSheet(list,cx);
  }
  
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
  

////////////////////////////////////////////////////////////////
// ILoadable
////////////////////////////////////////////////////////////////

  /**
   * Implementation for processing upload on async thread.
   * Default implementation recurses upload on children.
   */
  public void doUpload(BUploadParameters params, Context cx)
    throws Exception
  {
    new BUploadJob(this,params,cx).submit(cx);
  }

  /**
   * Implementation for processing download on async thread.
   * Default implementation recurses download on children.
   */
  public void doDownload(BDownloadParameters params, Context cx)
    throws Exception
  {
    new BDownloadJob(this,params,cx).submit(cx);
  }

}
