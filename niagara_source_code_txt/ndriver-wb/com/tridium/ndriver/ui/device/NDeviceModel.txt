/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui.device;

import javax.baja.driver.BDeviceFolder;
import javax.baja.driver.BIDeviceFolder;
import javax.baja.driver.ui.device.DeviceModel;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Type;
import javax.baja.workbench.mgr.MgrColumn;
import com.tridium.ndriver.BNDevice;
import com.tridium.ndriver.ui.NMgrModelUtil;
import com.tridium.ndriver.ui.NMgrUtil;

/**
 * The NDeviceModel uses introspection to define the columns in the database
 * type of the NDeviceManager.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
public class NDeviceModel
  extends DeviceModel
{
  public NDeviceModel(BNDeviceManager manager)
  {
    super(manager);
  }

////////////////////////////////////////////////////////////////
// MgrModel
////////////////////////////////////////////////////////////////

  /**
   * Automatically determines the MgrColumns for the database list.
   * <p>
   * Calls NMgrModelUtil.makeColumns
   *
   * @see NMgrModelUtil.makeColumns
   */
  @Override
  protected MgrColumn[] makeColumns()
  {
    return NMgrModelUtil.makeColumns(getManager());
  }

  @Override
  public void init()
  {
    // Looks at the 'current value' as a 'BIDeviceFolder'
    folder = (BIDeviceFolder)getManager().getCurrentValue();

    // Gets the device TYPE and device folder TYPE for
    // the NDeviceManager
    try
    {
      deviceType = folder.getDeviceType();
      folderType = folder.getDeviceFolderType();
    }
    catch (Exception e)
    {
      // this occurs when using offline
    }

    if (deviceType == null)
    {
      throw new BajaRuntimeException(
        NMgrUtil.LEX.getText("MustSpecifyDeviceType",
          new Object[] { folder.getType().getTypeSpec() }));
    }

    super.init();
  }

  /**
   * Overrides to return subscribe depth specified in BNNetwork.getAutoManagerSubscribeDepth()
   *
   * @since 3.7.107.
   */
  @Override
  public int getSubscribeDepth()
  {
    return ((BNDeviceManager)getManager()).getNNetwork().getDeviceManagerSubscribeDepth();
  }

  protected BIDeviceFolder folder;
  protected Type deviceType = BNDevice.TYPE;
  protected Type folderType = BDeviceFolder.TYPE;
}
