/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import javax.baja.driver.loadable.BDownloadParameters;
import javax.baja.driver.loadable.BUploadParameters;
import javax.baja.lonworks.datatypes.BDeviceData;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.*;

import com.tridium.lonworks.device.BDownloadJob;
import com.tridium.lonworks.device.BUploadJob;
import com.tridium.lonworks.device.NvDev;
import com.tridium.lonworks.device.NvDev.SaveNv;
import com.tridium.lonworks.util.NmUtil;

/**
 * BLonObject is container object to allow partitioning lonComponents
 * in a BLonDevice. It could be used to represent a profile.
 *
 * @author    Robert Adams
 * @creation  18 Oct 01
 * @version   $Revision: 1$ $Date: 10/18/01 2:56:31 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Status of the object.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE
)
/*
 Provides a short message why the object is in fault.
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
/*
 Index of object in device.  Must be unique.
 */
@NiagaraProperty(
  name = "objectId",
  type = "int",
  defaultValue = "UNASSIGNED_ID",
  flags = Flags.READONLY
)
/*
 Optional object type.
 */
@NiagaraProperty(
  name = "objectType",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY
)
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
public class BLonObject
  extends BComponent
  implements BINvContainer, BIStatus,  BILonLoadable
{ 
	public static final int UNASSIGNED_ID = -1;
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BLonObject(3603969375)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * Status of the object.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * Status of the object.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * Status of the object.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * Provides a short message why the object is in fault.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE, "", null);

  /**
   * Get the {@code faultCause} property.
   * Provides a short message why the object is in fault.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * Provides a short message why the object is in fault.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * Index of object in device.  Must be unique.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.READONLY, UNASSIGNED_ID, null);

  /**
   * Get the {@code objectId} property.
   * Index of object in device.  Must be unique.
   * @see #objectId
   */
  public int getObjectId() { return getInt(objectId); }

  /**
   * Set the {@code objectId} property.
   * Index of object in device.  Must be unique.
   * @see #objectId
   */
  public void setObjectId(int v) { setInt(objectId, v, null); }

  //endregion Property "objectId"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * Optional object type.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, "", null);

  /**
   * Get the {@code objectType} property.
   * Optional object type.
   * @see #objectType
   */
  public String getObjectType() { return getString(objectType); }

  /**
   * Set the {@code objectType} property.
   * Optional object type.
   * @see #objectType
   */
  public void setObjectType(String v) { setString(objectType, v, null); }

  //endregion Property "objectType"

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
  public static final Type TYPE = Sys.loadType(BLonObject.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////
//  Overrides
////////////////////////////////////////////////////////////
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make("module://lonworks/com/tridium/lonworks/ui/icons/object.png");

  public boolean isParentLegal(BComponent parent)
  {
    return parent.getType().is(BLonDevice.TYPE) || 
           parent.getType().is(BLonObjectFolder.TYPE);
  }


////////////////////////////////////////////////////////////
//  BINvContainer Implementation
////////////////////////////////////////////////////////////
  public BLonNetwork getLonNetwork()  { return getLonDevice().getLonNetwork(); }
  public BLonDevice  getLonDevice()   { return (BLonDevice)NmUtil.getParent(this,BLonDevice.TYPE); }

  public BDeviceData getDeviceData() { return getLonDevice().getDeviceData(); }
  public BINetworkVariable[] getNetworkVariables() { return getLonDevice().getNetworkVariables(); }

  public boolean isLonObject() { return true; }
 
////////////////////////////////////////////////////////////
//  BILonLoadable Implementation
////////////////////////////////////////////////////////////
  /** Callback for processing upLoad. */
  public final void doUpload(BUploadParameters p, Context cx)
    throws Exception
  {
    checkState();
    getLonDevice().checkState();
    checkUpload();
    new BUploadJob(this,p,cx).submit(cx);
  }
  
  /** Throw BajaRuntimeException if object is down or disabled or in fault. */
  public void checkState()
  {
    if(getStatus().isDown())
      throw new BajaRuntimeException("Object is down. Can't perform operation.");
    if(getStatus().isDisabled())
      throw new BajaRuntimeException("Object is disabled. Can't perform operation.");
    if(getStatus().isFault())
      throw new BajaRuntimeException("Object is in fault. Can't perform operation.");
  }
  
  /** Callback for processing downLoad. */
  public final void doDownload(BDownloadParameters p, Context cx)
    throws Exception
  {
    checkState();
    getLonDevice().checkState();
    checkDownload();
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
    if(!dev.isDownLoadInProgress()) dev.initDownload(true); 
  }
  /** Override point to customize download process.  This will be called after all device components have been downloaded. */ 
  public void endDownload()
  {
    BLonDevice dev = getLonDevice();
    if(!dev.isDownLoadInProgress()) dev.cleanupDownload();
  }

////////////////////////////////////////////////////////////
//  Overrides
////////////////////////////////////////////////////////////
  /**
   * Create an instance of BLink to use for a link to the specified
   * source component.  This method is used by Baja tools when users
   * create links via the "bajaui:javax.baja.ui.commands.LinkCommand".
   */
  public BLink makeLink(BComponent source, Slot sourceSlot, Slot targetSlot, Context cx)
  {
    if(!NvDev.requiresLonLink(targetSlot))
      return super.makeLink(source,sourceSlot,targetSlot, cx);
      
    return NvDev.makeLonLink(source, sourceSlot, this, targetSlot, cx);
  }
  
  protected LinkCheck doCheckLink(BComponent source, Slot sourceSlot, Slot targetSlot, Context cx)
  {
    return NvDev.doNvCheckLink(source, sourceSlot, this, targetSlot, cx);
  }

  public void linkUpdate()
  {
    if(linkUpdateDone) return;
    // Insure non-component properties are up-to-date
    getComponentSpace().update(this,1);
    getComponentSpace().update(getLonDevice().getDeviceData(),2);
    linkUpdateDone = true;
  }
  boolean linkUpdateDone = false;

  /** Override for renamed(). */
  public void renamed(Property property, String oldName, Context context)
  {
    super.renamed(property, oldName, context);
    if(property.getType().is(BLonComponent.TYPE))
    {
      BLonDevice.lonComponentRenamed(getLonDevice(), property.getName(), oldName, context);
    }  
  }
  /**
   * Called when a knob is activated.
   */
  public void knobAdded(Knob knob, Context context)
  {
    super.knobAdded(knob, context);
    NvDev.knobAdded(this, knob, context);
  }

  /**
   * Called when a knob is deactivated.
   */
  public void knobRemoved(Knob knob, Context context)
  {
    super.knobRemoved(knob, context);
    NvDev.knobRemoved(this, knob, context);
  }

  public void checkRemove(Property prop, Context context)
  {
    snv = NvDev.checkRemove(this, prop, context);
    super.checkRemove(prop, context);
  }
  
  public void removed(Property prop, BValue value, Context context)
  {
    super.removed(prop,value,context);
    NvDev.removed(this, snv, prop, value, context);
    snv = null;
  }
  SaveNv snv = null;
  
  public void added(Property prop, Context context)
  {
    super.added(prop,context);

    if(prop.getType().is(BLonLink.TYPE))
    {
      BLonLink lnk = (BLonLink)get(prop);
      if(isRunning() && !lnk.getMessageTag()) lnk.getDestinationNv().lonLinkAdded();
    }
  }
  
  BNetworkVariable saveNv = null;
  Property nvProp = null;
  
}
