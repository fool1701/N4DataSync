/*
 * copyright 2012 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nvideo.ui;

import com.tridium.ndriver.ui.device.BNDeviceManager;
import com.tridium.ndriver.ui.device.NDeviceModel;
import com.tridium.ndriver.ui.NMgrUtil;

import javax.baja.driver.BDevice;
import javax.baja.driver.ui.device.DeviceExtsColumn;
import javax.baja.sys.BComponent;
import javax.baja.workbench.mgr.MgrColumn;

/**
 * Customizes the NDeviceModel to allow for the device folders to
 * implement BIDeviceFolder instead of implicitly having the extend
 * BDeviceFolder.
 * 
 * See the comment for the VideoNestedDeviceExtsColumn class for
 * complete details as to why this is necessary.
 * 
 * @author   lperkins (Original ddf code)
 * @author   Robert Adams (rework for ndriver)
 * @creation  25 Jan 2012
 */
public class VideoNestedDeviceModel
  extends NDeviceModel
{
  public VideoNestedDeviceModel(BNDeviceManager manager)
  {
    super(manager);
    this.mgr = manager;
  }

  /**
   * Replaces the DeviceExtsColumn with a VideoNestedDeviceExtsColumn.
   */
  protected MgrColumn[] makeColumns()
  {
    // Gets the columns that would be displayed
    MgrColumn[] columns = super.makeColumns();
    
    // Loops through them
    for (int i=0; i<columns.length; i++)
    {
      // Seeks out the DeviceExtsColumn
      if (columns[i] instanceof DeviceExtsColumn)
      {
        // Gets a prototype device to pass to the constructor of the VideoNestedDeviceExtsColumn
        // which we are about to substitute in place of the DeviceExtsColumn
        BComponent databaseInstanceDefault = NMgrUtil.getDatabaseInstanceDefault(mgr);
        
        // Substitutes a VideoNestedDeviceExtsColumn in place of the DeviceExtsColumn. See
        // the comment for the CamereDeviceExtsColumn class for complete details about
        // why this is necessary.
        columns[i] = new VideoNestedDeviceExtsColumn( (BDevice)databaseInstanceDefault );
      }
    }
    return columns;
  }
  
  // This is the manager that is passed to the constructor of this class.
  BNDeviceManager mgr;  

}
