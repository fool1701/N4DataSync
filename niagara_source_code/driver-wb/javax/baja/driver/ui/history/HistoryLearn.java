/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.ui.history;

import javax.baja.driver.history.BArchiveDescriptor;
import javax.baja.gx.BImage;
import javax.baja.history.BHistoryDevice;
import javax.baja.history.BIHistory;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.ui.BWidget;
import javax.baja.util.Lexicon;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrEditRow;
import javax.baja.workbench.mgr.MgrLearn;
import javax.baja.workbench.mgr.MgrTypeInfo;

/**
 * HistoryLearn manages the learn piece of the HistoryArchiveManager.
 *
 * @author    John Sublett
 * @creation  12 Jan 2004
 * @version   $Revision: 7$ $Date: 8/3/04 11:57:58 AM EDT$
 * @since     Baja 1.0
 */
public abstract class HistoryLearn
  extends MgrLearn
{
  public HistoryLearn(BArchiveManager manager)
  {
    super(manager);
  }

  /**
   * Returns null so that no job bar is visible. 
   */                    
  protected BWidget makeJobBar()
  {             
    return null;
  }
  
  /**
   * Make the columns for the discovery table.
   */
  protected MgrColumn[] makeColumns()
  {           
    return new MgrColumn[]
    {
      new NavNameColumn(histLex.getText("historyId"))
    };
  }
  
  public BImage getIcon(Object discovery)
  {
    if (discovery instanceof BIHistory)
      return historyIcon;
    else if (discovery instanceof BHistoryDevice)
      return deviceIcon;
    else
      return null;
  } 

  public boolean isDepthExpandable(int depth)
  {
    return depth < 2;
  }

  /**
   * Test if the specified discovery object is a group.
   * By default, devices are groups and histories are not.
   */
  public boolean isGroup(Object discovery)
  {
    return discovery instanceof BHistoryDevice;
  }
  
  /**
   * Test if the specified discovery object has children.  By
   * default, this method calls isGroup(discovery).
   */
  public boolean hasChildren(Object discovery)
  {                
    return isGroup(discovery);
  }
  
  public Object[] getChildren(Object discovery)
  {
    if (discovery instanceof BHistoryDevice)
    {
      return ((BHistoryDevice)discovery).getNavChildren();
    }
    else
      return super.getChildren(discovery);
  }

  public boolean isMatchable(Object discovery, BComponent database)
  {
    if (discovery instanceof BHistoryDevice)
      return false;
    else
      return (discovery instanceof BIHistory) && (database instanceof BArchiveDescriptor);
  }

  public MgrTypeInfo[] toTypes(Object discovery)
  {
    return new MgrTypeInfo[0];
  }

  public void toRow(Object discovery, MgrEditRow row)
    throws Exception
  {
    BIHistory history = (BIHistory)discovery;

    String name = history.getId().toString();
    name = name.substring(1).replace('/', '_');

    // map to row                         
    row.setDefaultName(name);
    row.setCell(((ArchiveModel)getManager().getModel()).idCol, history.getId());
  }

////////////////////////////////////////////////////////////////
// Discovery
////////////////////////////////////////////////////////////////

  /**
   * This method is only called if toTask(target) returns false.  It should
   * return the list of discovered objects.
   */
  public Object[] getDiscovery(BComponent target)
  {
    return new Object[0];
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final Lexicon histLex = Lexicon.make("history");
  private static final BImage historyIcon = BImage.make(BIcon.std("history.png"));
  private static final BImage deviceIcon = BImage.make(BIcon.std("device.png"));
}