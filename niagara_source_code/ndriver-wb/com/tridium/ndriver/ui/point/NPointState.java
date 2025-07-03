/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui.point;

import javax.baja.driver.ui.point.PointState;
import javax.baja.workbench.mgr.BAbstractManager;
import com.tridium.ndriver.ui.NMgrStateUtil;

/**
 * Preserves the NPointManager's state if the user temporarily navigates
 * away.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
public class NPointState
  extends PointState
{

////////////////////////////////////////////////////////////////
// PointState
////////////////////////////////////////////////////////////////

  @Override
  protected void restoreForType(BAbstractManager manager)
  {
    super.restoreForType(manager);

    if (manager instanceof BNPointManager)
    {
      NMgrStateUtil.restoreState((BNPointManager)manager);
    }
  }

  @Override
  protected void saveForType(BAbstractManager manager)
  {
    super.saveForType(manager);

    if (manager instanceof BNPointManager)
    {
      NMgrStateUtil.saveState((BNPointManager)manager);
    }
  }

}
