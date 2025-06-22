/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.loadable;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;

/**
 * BLoadable is a BComponent implementation of BILoadable.
 *
 * @author    Robert Adams
 * @creation  4 Feb 02
 * @version   $Revision: 4$ $Date: 9/12/04 1:43:26 PM EDT$
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
public abstract class BLoadable
  extends BComponent
  implements BILoadable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.loadable.BLoadable(3660287553)1.0$ @*/
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
  public static final Type TYPE = Sys.loadType(BLoadable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Return BLoadableNetwork ancestor.
   **/
  public BLoadableNetwork getNetwork()
  {
    try
    {                
      return (BLoadableNetwork)getDevice().getNetwork();
    }
    catch(Exception e)
    {
      return null;
    }
  }

  /**
   * Return BLoadableDevice ancestor.
   **/
  public BLoadableDevice getDevice()
  {
    try
    {
      BComplex p  = getParent();
      while (!(p instanceof BLoadableDevice)) p = p.getParent();
      return (BLoadableDevice)p;
    }
    catch(Exception e)
    {
      return null;
    }
  }

  /**
   * Convenience for <code>getNetwork().postAsync(r)</code>.
   */
  public IFuture postAsync(Runnable r)
  {
    return getNetwork().postAsync(r);
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  /** 
   * Return true if parent is a BILoadable
   */
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BILoadable;
  }      
  
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

}
