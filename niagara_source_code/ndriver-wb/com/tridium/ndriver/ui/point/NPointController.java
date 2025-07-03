/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui.point;

import javax.baja.driver.ui.point.PointController;
import javax.baja.nre.util.Array;
import javax.baja.sys.Context;
import javax.baja.ui.BWidget;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BEdgePane;
import com.tridium.ndriver.ui.NMgrControllerUtil;
import com.tridium.ndriver.ui.NMgrControllerUtil.NPointMgrAgentCommand;

/**
 * This is the MgrController for the BNPointManager.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
public class NPointController
  extends PointController
{
  public NPointController(BNPointManager manager)
  {
    super(manager);
  }

////////////////////////////////////////////////////////////////
// MgrController
////////////////////////////////////////////////////////////////

  /**
   * Called when the user clicks the "Discover" button. This calls
   * NMgrControllerUtil.doDiscover.
   *
   * @see NMgrControllerUtil.doDiscover
   */
  @Override
  public CommandArtifact doDiscover(Context cx)
    throws Exception
  {
    return NMgrControllerUtil.doDiscover(getManager(), cx);
  }

  /**
   * Adds IMgrCommands for each BINPointMgrAgent that is declared on the device
   * or point-device-ext.
   */
  @Override
  protected IMgrCommand[] makeCommands()
  {
    return NMgrControllerUtil.makeCommands(
      super.makeCommands(),
      (BNPointManager)getManager());
  }

  /**
   * Enables/Disables the IMgrCommands for each BINPointMgrAgent that is
   * declared on the device or point-device-ext.
   */
  @Override
  public void updateCommands()
  {
    NMgrControllerUtil.updateCommands(getManager());
    super.updateCommands();
  }

  /**
   * Overrides the default action bar to add a second row of buttons to contain
   * the developer's button agents (provided that the developer adds two or more
   * of them). If the developer only adds one button to the manager, then this
   * will still just use a single row of buttons.
   */
  @Override
  public BWidget makeActionBar()
  {
    // Gets all IMgrCommands for this device
    IMgrCommand[] mgrCommands = getCommands();

    // Allocates two array buffers, one to hold the framework commands
    // the other to hold the developer (user) commands
    Array<IMgrCommand> firstRow = new Array<>(IMgrCommand.class);
    Array<IMgrCommand> secondRow = new Array<>(IMgrCommand.class);

    // Loops through the mgrCommands and divides them between the firstRow
    // and secondRow. The firstRow contains those commands that are added
    // by the framework itself. The secondRow contains those commands that
    // are added by the developer by means of N manager agents
    for (int i = 0; i < mgrCommands.length; i++)
    {
      if (mgrCommands[i] instanceof NPointMgrAgentCommand)
      {
        secondRow.add(mgrCommands[i]);
      }
      else
      {
        firstRow.add(mgrCommands[i]);
      }
    }

    // If the secondRow would not have at least two items in it then
    // let's just use one row...calling super.makeActionBar will 
    // accomplish this.
    if (secondRow.size() < 2)
    {
      return super.makeActionBar();
    }
    else
    {
      IMgrCommand[] row1Commands = firstRow.trim();
      IMgrCommand[] row2Commands = secondRow.trim();

      BEdgePane pane = new BEdgePane();
      pane.setTop(makeActionPane(row1Commands));
      pane.setCenter(new BBorderPane(makeActionPane(row2Commands)));

      return pane;
    }
  }
}
