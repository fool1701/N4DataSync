/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui.point;

import javax.baja.driver.ui.point.PointModel;
import javax.baja.workbench.mgr.MgrColumn;
import com.tridium.ndriver.BNDevice;
import com.tridium.ndriver.ui.NMgrModelUtil;

/**
 * This is the point model that is used for the NPointManager.
 * <p>
 * For any Niagara manager, the point model essentially defines the behavior
 * and characteristics of the database pane.
 * <p>
 * This class uses introspection and special facets to automatically generate
 * the database pane so that the developer does not have to define any UI
 * classes.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 * @see NMgrModelUtil.makeColumns
 */
public class NPointModel
  extends PointModel
{
  public NPointModel(BNPointManager manager)
  {
    super(manager);
  }

////////////////////////////////////////////////////////////////
// PointModel
////////////////////////////////////////////////////////////////

  /**
   * Automatically determines the MgrColumns for the database list.
   */
  @Override
  protected MgrColumn[] makeColumns()
  {
    return NMgrModelUtil.makeColumns(getManager());
  }


  /**
   * Override to return subscribe depth specified in BNNetwork.getPointManagerSubscribeDepth()
   *
   * @since Niagara 4.8
   */
  @Override
  public int getSubscribeDepth()
  {
    BNPointManager nPointManager = (BNPointManager)getManager();
    BNDevice nDevice = (BNDevice)nPointManager.getNDevice();

    return nDevice.getPointManagerSubscribeDepth();
  }
}
