/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.driver.loadable.BDownloadParameters;
import javax.baja.driver.loadable.BILoadable;
import javax.baja.driver.loadable.BUploadParameters;
import javax.baja.driver.loadable.LoadUtil;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.BFolder;

/**
 * BBacnetConfigFolder is the standard container to use
 * under BBacnetConfigDeviceExt to organize BBacnetObjects.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 19 Nov 2004
 * @since Niagara 3 BACnet 1.0
 */
@NiagaraType
@NiagaraAction(
  name = "upload",
  parameterType = "BUploadParameters",
  defaultValue = "new BUploadParameters()",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "download",
  parameterType = "BDownloadParameters",
  defaultValue = "new BDownloadParameters()",
  flags = Flags.HIDDEN
)
public class BBacnetConfigFolder
  extends BFolder
  implements BIBacnetConfigFolder, BILoadable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetConfigFolder(517787664)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "upload"

  /**
   * Slot for the {@code upload} action.
   * @see #upload(BUploadParameters parameter)
   */
  public static final Action upload = newAction(Flags.HIDDEN, new BUploadParameters(), null);

  /**
   * Invoke the {@code upload} action.
   * @see #upload
   */
  public void upload(BUploadParameters parameter) { invoke(upload, parameter, null); }

  //endregion Action "upload"

  //region Action "download"

  /**
   * Slot for the {@code download} action.
   * @see #download(BDownloadParameters parameter)
   */
  public static final Action download = newAction(Flags.HIDDEN, new BDownloadParameters(), null);

  /**
   * Invoke the {@code download} action.
   * @see #download
   */
  public void download(BDownloadParameters parameter) { invoke(download, parameter, null); }

  //endregion Action "download"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetConfigFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// IBacnetConfigFolder
////////////////////////////////////////////////////////////////

  /**
   * Get the parent network.
   */
  public BBacnetConfigDeviceExt getConfig()
  {
    BComplex p = getParent();
    while (p != null)
    {
      if (p instanceof BBacnetConfigDeviceExt)
        return (BBacnetConfigDeviceExt)p;
      p = p.getParent();
    }
    throw new IllegalStateException();
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  @Override
  public void doUpload(BUploadParameters params, Context cx) throws Exception
  {
    if (params.getRecursive())
    {
      LoadUtil.uploadChildren(this, params, cx);
    }
  }

  @Override
  public void doDownload(BDownloadParameters params, Context cx)
    throws Exception
  {
    if (params.getRecursive())
    {
      LoadUtil.downloadChildren(this, params, cx);
    }
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  public String toString(Context cx)
  {
    return "";
  }
//
//  public AgentList getAgents(Context cx)
//  {
//    TypeInfo exportMgrTI = Sys.getRegistry().getType("bacnet:BacnetExportManager");
//    TypeInfo fileExportMgrTI = Sys.getRegistry().getType("bacnet:BacnetFileExportManager");
//    TypeInfo logExportMgrTI = Sys.getRegistry().getType("bacnet:BacnetNiagaraLogExportManager");
//    boolean exportMgrFound = false;
//    boolean fileExportMgrFound = false;
//    boolean logExportMgrFound = false;
//
//    AgentList agents = super.getAgents(cx);
//
//    for(int i=0; i<agents.size(); ++i)
//    {
//      TypeInfo ti = agents.get(i).getAgentType();
//      if (!exportMgrFound && ti.is(exportMgrTI))
//        { exportMgrFound = true; continue; }
//      if (!fileExportMgrFound && ti.is(fileExportMgrTI))
//        { fileExportMgrFound = true; continue; }
//      if (!logExportMgrFound && ti.is(logExportMgrTI))
//        { logExportMgrFound = true; continue; }
//    }
//
//    if (!logExportMgrFound) agents.add(logExportMgrTI.getAgentInfo());
//    if (!fileExportMgrFound) agents.add(fileExportMgrTI.getAgentInfo());
//    if (!exportMgrFound) agents.add(exportMgrTI.getAgentInfo());
//    return agents;
//  }

  public BIcon getIcon()
  {
    return icon;
  }

  //  private static final BIcon icon = BIcon.std("pointFolder.png");
  private static final BIcon icon = BIcon.make(BIcon.std("folder.png"),
    BIcon.make("module://bacnet/com/tridium/bacnet/ui/icons/bacnetBadge.png"));

}
