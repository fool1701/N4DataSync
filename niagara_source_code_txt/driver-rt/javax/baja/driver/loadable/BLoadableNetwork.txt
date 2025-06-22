/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.loadable;

import javax.baja.driver.BDeviceNetwork;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.UnitDatabase;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;

/**
 * BDeviceNetwork is the abstract base class for drivers
 * which model a network of BLoadableDevices.
 *
 * @author    Robert Adams
 * @creation  4 Feb 02
 * @version   $Revision: 20$ $Date: 2/13/04 11:32:24 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
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
public abstract class BLoadableNetwork
  extends BDeviceNetwork
  implements BILoadable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.loadable.BLoadableNetwork(3660287553)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLoadableNetwork.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Subclasses should use the Queue and Worker APIs to
   * support running async tasks on one or more background
   * threads.  The ILoadable APIs route all upload and download
   * Invocations to this method.
   */
  public abstract IFuture postAsync(Runnable r);

////////////////////////////////////////////////////////////////
// Action Post
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
   * Post a ping Invocation.
   */
  protected IFuture postPing()
  {
    return postAsync(new Invocation(this, ping, null, null));
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
  
////////////////////////////////////////////////////////////////
// Action Implementation
////////////////////////////////////////////////////////////////

  /**
   * Implementation for processing upload on async thread.
   * Default implementation recurses upload on children.
   */
  public void doUpload(BUploadParameters params, Context cx)
    throws Exception
  {
    if (params.getRecursive())  
      LoadUtil.uploadChildren(this, params, cx);
  }

  /**
   * Implementation for processing download on async thread.
   * Default implementation recurses download on children.
   */
  public void doDownload(BDownloadParameters params, Context cx)
    throws Exception
  {
    if (params.getRecursive())  
      LoadUtil.downloadChildren(this, params, cx);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static
  {
    // Make sure the unit database is loaded
    UnitDatabase.getDefault();
  }

}
