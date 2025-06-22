/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * The ILonLoadable interface is implemented by Lonworks BComponents 
 * which support async upload and download actions.
 *
 * @author    Robert Adams
 * @creation  31 Jan 06
 * @version   $Revision$ $Date: 2/11/2004 4:14:14 PM$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BILonLoadable
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BILonLoadable(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:20 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BILonLoadable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /** Override point to customize upload process.  This will be called at the beginning of an upload operation. */ //after initUpload() 
  public void beginUpload();
  /** Override point to customize upload process.  This will be called after all device components have been uploaded. */ 
  public void endUpload();
  
  /** Override point to customize download process.  This will be called at the beginning of a download operation . */ //after initDownload()
  public void beginDownload();
  /** Override point to customize download process.  This will be called after all device components have been downloaded. */ 
  public void endDownload();
   
  public BLonDevice getLonDevice();
  
  // From BComponent
  public BComponent asComponent(); 
  public String getName();
  public String getDisplayName(Context cx);
}
