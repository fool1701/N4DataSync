/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.sidebar;

import javax.baja.ui.BWidget;
import com.tridium.workbench.shell.WbCommands;

/**
 * IWbSideBarManager is an interface for managing BIWbSidebar instances in the
 * workbench environment
 *
 * @author Danesh Kamal on 1/19/14
 * @since Niagara 4.0
 */
public interface IWbSideBarManager
{

  /**
   * Return a list of the open sidebars
   * @return
   */
  public BIWbSideBar[] listSideBars();

  /**
   * Open a sidebar
   * @param bar
   * @return
   */
  public BIWbSideBar openSideBar(BIWbSideBar bar);

  /**
   * Open sidebars in a specified mode
   * @param mode
   */
  public void openMode(WbCommands.Mode mode);

  /**
   * Close a sidebar
   * @param bar
   */
  public void closeSideBar(BIWbSideBar bar);

  /**
   * Close all sidebars
   */
  public void closeAllSideBars();

  /**
   * Returns whether any sidebars are open
   * @return
   */
  public boolean hasOpenSideBars();

  /**
   * Returns a serialized string representation of the open sidebars
   * @return
   */
  public String serialize();

  /**
   * Reconstruct the open sidebars from a serialized input
   * @param serializedForm
   */
  public void deserialize(String serializedForm);

  /**
   * Return the manager as a widget
   * @return
   */
  public BWidget asWidget();

}
