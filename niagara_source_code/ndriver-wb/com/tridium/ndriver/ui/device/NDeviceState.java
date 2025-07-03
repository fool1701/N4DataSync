/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui.device;

import javax.baja.driver.ui.device.DeviceState;
import javax.baja.workbench.mgr.BAbstractManager;
import com.tridium.ndriver.ui.NMgrStateUtil;

/**
 * Preserves the NDeviceManager's state if the user temporarily navigates away.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
public class NDeviceState
  extends DeviceState
{

////////////////////////////////////////////////////////////////
// FolderState
////////////////////////////////////////////////////////////////

  @Override
  protected void restoreForType(BAbstractManager manager)
  {
    super.restoreForType(manager);

    if (manager instanceof BNDeviceManager)
    {
      NMgrStateUtil.restoreState((BNDeviceManager)manager);
    }
  }

  @Override
  protected void saveForType(BAbstractManager manager)
  {
    super.saveForType(manager);

    if (manager instanceof BNDeviceManager)
    {
      NMgrStateUtil.saveState((BNDeviceManager)manager);
    }
  }
}
