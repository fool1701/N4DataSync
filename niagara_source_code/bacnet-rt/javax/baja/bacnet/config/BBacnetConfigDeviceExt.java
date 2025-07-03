/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.*;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.driver.BDeviceExt;
import javax.baja.driver.loadable.*;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.*;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;

import com.tridium.bacnet.job.BBacnetDiscoverConfigJob;
import com.tridium.driver.util.StringUtil;

/**
 * BBacnetConfigDeviceExt represents the configuration representation of a
 * Bacnet device.
 * <p>
 * This contains a BBacnetDeviceObject, which contains all the properties
 * of a Bacnet Device object as defined by the Bacnet specification.
 * <p>
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 04 Jan 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
/*
 deviceObject contains the Bacnet parameters for this Bacnet
 Device, such as the vendor name and ID, model name, and the
 segmentation support.
 */
@NiagaraProperty(
  name = "deviceObject",
  type = "BBacnetDeviceObject",
  defaultValue = "new BBacnetDeviceObject()"
)
/*
 Upload reads data from the physical device.
 */
@NiagaraAction(
  name = "upload",
  parameterType = "BUploadParameters",
  defaultValue = "new BUploadParameters()",
  flags = Flags.ASYNC
)
/*
 Download writes data to the physical device.
 */
@NiagaraAction(
  name = "download",
  parameterType = "BDownloadParameters",
  defaultValue = "new BDownloadParameters()",
  flags = Flags.ASYNC
)
@NiagaraAction(
  name = "submitConfigDiscoveryJob",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "getConfigTypes",
  returnType = "BString",
  flags = Flags.HIDDEN
)
public class BBacnetConfigDeviceExt
  extends BDeviceExt
  implements BILoadable,
  BacnetConst,
  BIBacnetObjectContainer,
  BIBacnetConfigFolder
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetConfigDeviceExt(3654213513)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "deviceObject"

  /**
   * Slot for the {@code deviceObject} property.
   * deviceObject contains the Bacnet parameters for this Bacnet
   * Device, such as the vendor name and ID, model name, and the
   * segmentation support.
   * @see #getDeviceObject
   * @see #setDeviceObject
   */
  public static final Property deviceObject = newProperty(0, new BBacnetDeviceObject(), null);

  /**
   * Get the {@code deviceObject} property.
   * deviceObject contains the Bacnet parameters for this Bacnet
   * Device, such as the vendor name and ID, model name, and the
   * segmentation support.
   * @see #deviceObject
   */
  public BBacnetDeviceObject getDeviceObject() { return (BBacnetDeviceObject)get(deviceObject); }

  /**
   * Set the {@code deviceObject} property.
   * deviceObject contains the Bacnet parameters for this Bacnet
   * Device, such as the vendor name and ID, model name, and the
   * segmentation support.
   * @see #deviceObject
   */
  public void setDeviceObject(BBacnetDeviceObject v) { set(deviceObject, v, null); }

  //endregion Property "deviceObject"

  //region Action "upload"

  /**
   * Slot for the {@code upload} action.
   * Upload reads data from the physical device.
   * @see #upload(BUploadParameters parameter)
   */
  public static final Action upload = newAction(Flags.ASYNC, new BUploadParameters(), null);

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
  public static final Action download = newAction(Flags.ASYNC, new BDownloadParameters(), null);

  /**
   * Invoke the {@code download} action.
   * Download writes data to the physical device.
   * @see #download
   */
  public void download(BDownloadParameters parameter) { invoke(download, parameter, null); }

  //endregion Action "download"

  //region Action "submitConfigDiscoveryJob"

  /**
   * Slot for the {@code submitConfigDiscoveryJob} action.
   * @see #submitConfigDiscoveryJob()
   */
  public static final Action submitConfigDiscoveryJob = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code submitConfigDiscoveryJob} action.
   * @see #submitConfigDiscoveryJob
   */
  public BOrd submitConfigDiscoveryJob() { return (BOrd)invoke(submitConfigDiscoveryJob, null, null); }

  //endregion Action "submitConfigDiscoveryJob"

  //region Action "getConfigTypes"

  /**
   * Slot for the {@code getConfigTypes} action.
   * @see #getConfigTypes()
   */
  public static final Action getConfigTypes = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code getConfigTypes} action.
   * @see #getConfigTypes
   */
  public BString getConfigTypes() { return (BString)invoke(getConfigTypes, null, null); }

  //endregion Action "getConfigTypes"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetConfigDeviceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  public BBacnetConfigDeviceExt()
  {
  }

  public BBacnetConfigDeviceExt(BBacnetObjectIdentifier objectId)
  {
    getDeviceObject().setObjectId(objectId);
  }


////////////////////////////////////////////////////////////////
// IBacnetConfigFolder
////////////////////////////////////////////////////////////////

  /**
   * Get the parent network.
   */
  public BBacnetConfigDeviceExt getConfig()
  {
    return this;
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  // Since the BBacnetConfigDeviceExt always contains a BBacnetDeviceObject,
  // we need convenient transparent access to the device object's
  // Bacnet properties.  Or do we??

  // Getters

  public BBacnetObjectIdentifier getObjectId()
  {
    return getDeviceObject().getObjectId();
  }

  public String toString(Context context)
  {
    return getObjectId().toString(context) + " config";
  }


////////////////////////////////////////////////////////////////
// Convenience
////////////////////////////////////////////////////////////////

  /**
   * @return the BBacnetNetwork containing this BBacnetObject.
   */
  public final BBacnetNetwork network()
  {
    return (BBacnetNetwork)getNetwork();
  }

//  private static final BBacnetClientLayer client()
//  {
//    return ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient();
//  }

  public final BBacnetDevice device()
  {
    return (BBacnetDevice)getDevice();
  }

  /**
   * Get the BBacnetObject list as an array.
   */
  public synchronized BBacnetObject[] getObjectList()
  {
    BBacnetObject[] temp = new BBacnetObject[getSlotCount()];
    int count = 0;
    SlotCursor<Property> c = getProperties();
    while (c.next(BBacnetObject.class))
    {
      BObject kid = c.get();
      temp[count] = (BBacnetObject)kid;
      count++;
    }

    BBacnetObject[] result = new BBacnetObject[count];
    System.arraycopy(temp, 0, result, 0, count);
    return result;
  }


////////////////////////////////////////////////////////////////
//  BComponent overrides
////////////////////////////////////////////////////////////////

  /**
   * BBacnetConfigDeviceExt must be contained directly in a BBacnetDevice.
   */
  public final boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BBacnetDevice;
  }

  /**
   * BBacnetConfigDeviceExt may contain at most one device object.
   */
  public final boolean isChildLegal(BComponent child)
  {
    return !(child instanceof BBacnetDeviceObject);
  }


//////////////////////////////////////////////////////////////////
//   Bacnet Object management
//////////////////////////////////////////////////////////////////

  /**
   * Look up and return the Bacnet object with the given reference.
   *
   * @param objectId
   * @return a BObject with the given reference parameters, or null if
   * this container does not contain any objects with the given parameters.
   */
  public BBacnetObject lookupBacnetObject(BBacnetObjectIdentifier objectId)
  {
    try
    {
      return (BBacnetObject)lookupBacnetObject(objectId, -1, -1, null);
    }
    catch (ClassCastException e)
    {
      return null;
    }
  }

  /**
   * Look up and return the Bacnet object with the given reference.
   *
   * @param objectId
   * @param propertyId
   * @param propertyArrayIndex
   * @param domain             the realm in which to look up the object: point, schedule, history
   * @return a BObject with the given reference parameters, or null if
   * this container does not contain any objects with the given parameters.
   */
  public BObject lookupBacnetObject(BBacnetObjectIdentifier objectId,
                                    int propertyId,
                                    int propertyArrayIndex,
                                    String domain)
  {
    SlotCursor<Property> c = getProperties();
    while (c.next(BBacnetObject.class))
    {
      if (((BBacnetObject)c.get()).getObjectId().equals(objectId))
        return c.get();
    }
    return null;
  }


////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Route to postUpload or postDownload.
   */
  public IFuture post(Action action, BValue arg, Context cx)
  {
    if (action.equals(upload)) return postUpload((BUploadParameters)arg, cx);
    if (action.equals(download)) return postDownload((BDownloadParameters)arg, cx);
    return super.post(action, arg, cx);
  }

  /**
   * Post an upload Invocation.
   */
  protected IFuture postUpload(BUploadParameters params, Context cx)
  {
    return postAsync(new Invocation(this, upload, params, cx));
  }

  /**
   * Post a download Invocation.
   */
  protected IFuture postDownload(BDownloadParameters params, Context cx)
  {
    return postAsync(new Invocation(this, download, params, cx));
  }

  /**
   * Convenience for <code>getNetwork().postAsync(r)</code>.
   */
  public IFuture postAsync(Runnable r)
  {
    return network().postAsync(r);
  }

  public BOrd doSubmitConfigDiscoveryJob(Context cx)
  {
    if (device().isFatalFault()) return null;
    return new BBacnetDiscoverConfigJob(this).submit(cx);
  }

  public BString doGetConfigTypes()
  {
    TypeInfo[] types = Sys.getRegistry().getConcreteTypes(BBacnetObject.TYPE.getTypeInfo());
    String[] infos = new String[types.length];
    for (int i = 0; i < infos.length; i++)
      infos[i] = types[i].toString();
    String list = StringUtil.toString(infos, ";");
    return BString.make(list);
  }


////////////////////////////////////////////////////////////////
//  BILoadable support
////////////////////////////////////////////////////////////////

  /**
   * Callback for processing upload on async thread.
   * Default implementation is to call doUpload on all
   * children implementing the Loadable interface.
   */
  public void doUpload(BUploadParameters p, Context cx)
    throws Exception
  {
    if (p.getRecursive())
      LoadUtil.uploadChildren(this, p, cx);
    else
      getDeviceObject().doUpload(p, cx);
  }

  /**
   * Callback for processing downLoad on async thread.
   * Default implementation is to call asyncDownload on all
   * children implementing the Loadable interface.
   */
  public void doDownload(BDownloadParameters p, Context cx)
    throws Exception
  {
    if (p.getRecursive())
      LoadUtil.downloadChildren(this, p, cx);
    else
      getDeviceObject().doDownload(p, cx);
  }


////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.make("module://bacnet/com/tridium/bacnet/ui/icons/bacObject.png");


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final Logger log = Logger.getLogger("bacnet.client");


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

}
