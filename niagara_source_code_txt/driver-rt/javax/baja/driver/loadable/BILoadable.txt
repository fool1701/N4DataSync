/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.loadable;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The ILoadable interface is implemented by BComponents 
 * which support async upload and download actions.
 *
 * @author    Robert Adams
 * @creation  4 Feb 02
 * @version   $Revision: 2$ $Date: 2/11/04 4:14:14 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BILoadable
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.loadable.BILoadable(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BILoadable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Invocation for async upload action.
   */                                   
  public void upload(BUploadParameters params);

  /**
   * Implementation for async upload action.
   */                                   
  public void doUpload(BUploadParameters params, Context cx)
    throws Exception;

  /**
   * Invocation for async download action.
   */                                   
  public void download(BDownloadParameters params);
  
  /**
   * Implementation for async download action.
   */                                   
  public void doDownload(BDownloadParameters params, Context cx)
    throws Exception;       
}
