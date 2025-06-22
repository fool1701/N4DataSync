/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.ui.history;

import javax.baja.driver.history.BHistoryDeviceExt;
import javax.baja.driver.history.BHistoryExport;
import javax.baja.driver.history.BIArchiveFolder;
import javax.baja.history.BHistorySpace;
import javax.baja.history.BIHistory;
import javax.baja.naming.BISession;
import javax.baja.naming.BOrd;
import javax.baja.naming.UnresolvedException;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.workbench.mgr.MgrTypeInfo;

/**
 * ExportLearn manages the learn for history exports.
 *
 * @author    John Sublett
 * @creation  13 Jan 2004
 * @version   $Revision: 10$ $Date: 9/24/09 2:31:26 PM EDT$
 * @since     Baja 1.0
 */
public class ExportLearn
  extends HistoryLearn
{
  public ExportLearn(BHistoryExportManager manager)
  {
    super(manager);
  }

  public MgrTypeInfo[] toTypes(Object discovery)
  {
    if (discovery instanceof BIHistory)
    {
      BIArchiveFolder folder = (BIArchiveFolder)getManager().getCurrentValue();
      return new MgrTypeInfo[] { MgrTypeInfo.make(folder.getExportDescriptorType()) };
    }
    else
      return super.toTypes(discovery);
  }

  public Object[] getDiscovery(BComponent target)
  {
    // check for a history space, if there is one, then the discovery
    // can use it
    BHistorySpace space = null;
    BHistoryDeviceExt ext = ((BIArchiveFolder)target).getDeviceExt();
    BISession session = ext.getSession();
    BOrd spaceOrd = BOrd.make("history:");
    try
    {
      space = (BHistorySpace)spaceOrd.resolve((BObject)session).get();
    }
    catch(UnresolvedException e)
    {
    }

    if (space == null) return new Object[0];
    
    return space.listDevices();
  }

  /**
   * Verify that the specified discovery object is a BIHistory and
   * that the component is a BHistoryExport with a matching history
   * id.
   */
  public boolean isExisting(Object discovery, BComponent component)
  {
    if (!(discovery instanceof BIHistory)) return false;
    if (!(component instanceof BHistoryExport)) return false;

    BIHistory h = (BIHistory)discovery;
    BHistoryExport exp = (BHistoryExport)component;

    return h.getId().equals(exp.getHistoryId());
  }
}