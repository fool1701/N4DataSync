/*
 * Copyright 2009 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.history;

import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.driver.BDevice;
import javax.baja.driver.BDeviceNetwork;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BComplex;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFolder;

/**
 * BArchiveFolder is the standard container to use 
 * under HistoryDeviceExt to organize history import/export descriptors.
 *
 * @author    Scott Hoye
 * @creation  15 May 09
 * @version   $Revision: 1$ $Date: 5/19/09 2:54:59 PM EDT$
 * @since     Niagara 3.5
 */
@NiagaraType
public class BArchiveFolder
  extends BFolder
  implements BIArchiveFolder
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.history.BArchiveFolder(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BArchiveFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// IArchiveFolder
////////////////////////////////////////////////////////////////

  /**
   * Get the parent network.
   */
  public final BDeviceNetwork getNetwork()
  {            
    return getDevice().getNetwork();
  }

  /**
   * Get the parent device.
   */
  public final BDevice getDevice()
  {
    return getDeviceExt().getDevice();
  }

  /**
   * Get the parent history device extension.
   */
  public final BHistoryDeviceExt getDeviceExt()
  {            
    BComplex p = getParent();
    while(p != null)
    {
      if (p instanceof BHistoryDeviceExt)
        return (BHistoryDeviceExt)p;
      p = p.getParent();
    }
    throw new IllegalStateException();
  }
  
  /**
   * Get the type of HistoryImport for this driver.
   */
  public final Type getImportDescriptorType()
  {            
    return getDeviceExt().getImportDescriptorType();
  }
  
  /**
   * Get the type of HistoryExport for this driver.
   */
  public final Type getExportDescriptorType()
  {            
    return getDeviceExt().getExportDescriptorType();
  }

  /**
   * Get the type of ArchiveFolder for this driver.
   */
  public final Type getArchiveFolderType()
  {            
    return getDeviceExt().getArchiveFolderType();
  }


////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  public String toString(Context cx)
  {
    return "";
  }

  public AgentList getAgents(Context cx)
  { 
    AgentList agents = super.getAgents(cx);
    
    BHistoryDeviceExt deviceExt = null;
    try { deviceExt = getDeviceExt(); } catch (Exception e) {}
    
    if ((deviceExt != null) && (deviceExt.supportsGenericArchiveFolder()))
    {
      AgentList devExtAgents = deviceExt.getAgents(cx);
      for(int i=devExtAgents.size()-1; i>=0; i--)
      {
        AgentInfo info = devExtAgents.get(i);
        if (agents.indexOf(info) < 0)
          agents.add(info);
      }

      return agents;
    }
    
    TypeInfo historyManager = Sys.getRegistry().getType("driver:ArchiveManager");    
    
    for(int i=0; i<agents.size(); ++i)
      if (agents.get(i).getAgentType().is(historyManager))
        return agents;
        
    try
    {
      if (getExportDescriptorType() != null)
      {        
        TypeInfo historyExportManager = Sys.getRegistry().getType("driver:HistoryExportManager");
        agents.add(historyExportManager.getAgentInfo());
      }
    }
    catch (Exception e) {}
    
    try
    {
      if (getImportDescriptorType() != null)
      {        
        TypeInfo historyImportManager = Sys.getRegistry().getType("driver:HistoryImportManager");
        agents.add(historyImportManager.getAgentInfo());
      }
    }
    catch (Exception e) {}      
        
    return agents;
  }

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("historyFolder.png");

}
