/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.upgrade;

import javax.baja.sys.BComplex;
import javax.baja.sys.BValue;
import javax.baja.sys.Type;
import com.tridium.driver.upgrade.Upgrade;
import com.tridium.ndriver.datatypes.BNUsernameAndPassword;

/**
 * Upgrade devDriver to ndriver
 *
 * @author Robert A Adams
 * @creation May 23, 2012
 */
public class NUpgrade
  extends Upgrade
{

  /**
   * Return an array of class name Strings that upgrade() will process.
   */
  @Override
  public String[] upgradableClasses()
  {
    return new String[]
      {
        "devDriver:DdfDeviceFolder",
        "devDriver:DdfPointFolder",
        "devHttpDriver:DdfHttpUserNameAndPassword"
      };
  }

  @Override
  public BValue upgrade(BValue origObj, BValue newObj)
  {
    Type typ = origObj.getType();

    // Process devDriver classes
    if (typ.getModule().getModuleName().equals("devDriver"))
    {
      // Replace with classes from ndriver -> drop the Ddf designation
      return getInstance("ndriver", typ.getTypeName().substring(3));
    }

    // Process devHttpDriver classes
    if (typ.getModule().getModuleName().equals("devHttpDriver"))
    {
      if (typ.getTypeName().equals("DdfHttpUserNameAndPassword"))
      {
        return upgradeUserPassword((BComplex)origObj, (BComplex)newObj);
      }
    }

    return null;
  }

  public static BNUsernameAndPassword upgradeUserPassword(BComplex co, BComplex cn)
  {
    BNUsernameAndPassword v = (cn == null) ? new BNUsernameAndPassword() : (BNUsernameAndPassword)cn;

    copyChildren(co, v);

    return v;
  }
}
