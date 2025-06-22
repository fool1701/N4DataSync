/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui;

import javax.baja.control.BControlPoint;
import javax.baja.driver.BDevice;
import javax.baja.driver.ui.device.BDeviceManager;
import javax.baja.driver.ui.device.DeviceExtsColumn;
import javax.baja.driver.ui.point.BPointManager;
import javax.baja.nre.util.Array;
import javax.baja.sys.BComponent;
import javax.baja.util.Lexicon;
import javax.baja.workbench.mgr.BAbstractManager;
import javax.baja.workbench.mgr.MgrColumn;

public final class NMgrModelUtil
{
  private NMgrModelUtil() {}

/////////////////////////////////////////////////////////////////////////
//Util
/////////////////////////////////////////////////////////////////////////

  /**
   * Introspects recursively through the properties of the database type and
   * returns an array of MgrColumn objects for all BSimples, that have the
   * MGR_INCLUDE facet, anywhere recursively under the database type.
   */
  public static MgrColumn[] makeColumns(BAbstractManager mgr)
  {
    Array<MgrColumn> mgrColumns = new Array<>(MgrColumn.class);
    BComponent defComp = NMgrUtil.getDatabaseInstanceDefault(mgr);

    // Adds required columns
    mgrColumns.add(new MgrColumn.Path(MgrColumn.UNSEEN));
    mgrColumns.add(new MgrColumn.Name());
    mgrColumns.add(new MgrColumn.Type());


    if (mgr instanceof BDeviceManager)
    {
      // Column to access device extensions 
      mgrColumns.add(new DeviceExtsColumn((BDevice)defComp));
    }

    else if (mgr instanceof BPointManager)
    {
      // Add facets column
      mgrColumns.add(new MgrColumn.Prop(BControlPoint.facets, MgrColumn.EDITABLE | MgrColumn.UNSEEN));
      // Add column of out property
      mgrColumns.add(new MgrColumn.ToString(Lexicon.make(BPointManager.class).getText("out"), 0));
    }

    // Adds MgrColumn objects for all BSimple properties with the MGR_INCLUDE facet, that are
    NMgrColumnUtil.getColumnsFor(defComp, mgrColumns);

    return mgrColumns.trim();
  }
}


















