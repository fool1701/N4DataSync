/*
 * copyright 2012 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nvideo.ui;

import com.tridium.ndriver.ui.device.NDeviceController;

import javax.baja.driver.ui.device.BDeviceManager;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.workbench.mgr.BMgrTable;
import javax.baja.workbench.mgr.MgrColumn;

public class VideoNestedDeviceController
    extends NDeviceController
{

  public VideoNestedDeviceController(BDeviceManager manager)
  {
    super(manager);
  }

  
  public void cellDoubleClicked(BMgrTable table, BMouseEvent event, int row, int col)
  {
    MgrColumn mgrCol = table.columnIndexToMgrColumn(col);
    if (mgrCol instanceof VideoNestedDeviceExtsColumn)
      ((VideoNestedDeviceExtsColumn)mgrCol).videoCellDoubleClicked(table, event, row, col);
    else
      super.cellDoubleClicked(table, event, row, col);
  } 
  


}
