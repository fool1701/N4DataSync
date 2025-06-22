/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui;

import javax.baja.driver.BDevice;
import javax.baja.driver.BIDeviceFolder;
import javax.baja.driver.point.BIPointFolder;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;
import javax.baja.workbench.mgr.BAbstractManager;
import com.tridium.driver.util.DrUtil;
import com.tridium.ndriver.BNNetwork;
import com.tridium.ndriver.discover.BINDiscoveryHost;
import com.tridium.ndriver.discover.BINDiscoveryLeaf;
import com.tridium.ndriver.discover.BNDiscoveryPreferences;
import com.tridium.ndriver.point.BNPointDeviceExt;

/**
 * This class holds the common functionality that exists between the point
 * manager ui classes and the device manager ui classes.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
public final class NMgrUtil
{
  private NMgrUtil() {}

/////////////////////////////////////////////////////////////////////////
// Util
/////////////////////////////////////////////////////////////////////////

  /**
   * Gets the discovery leaf type for a BNDiscoveryManager. If managed object is
   * a BINDiscoveryHost then call getDiscoveryPreferences() and then call
   * getDiscoveryLeafType() on preferences.
   */
  public static Type getDiscoveryLeafType(BAbstractManager mgr)
  {
    BINDiscoveryHost discoveryHost = findDiscoveryHost(mgr);

    if (discoveryHost != null)
    {
      // Then we get the discovery preferences
      BNDiscoveryPreferences prefs = discoveryHost.getDiscoveryPreferences();

      if (prefs != null)
      {
        return prefs.getDiscoveryLeafType();
      }
    }
    return null;
  }

  /**
   * Gets a prototype instance of the discovery leaf type for a
   * BAbstractManager.
   */
  public static BINDiscoveryLeaf getDiscoveryLeafInstance(BAbstractManager mgr)
  {
    // Gets the discovery leaf type, as defined by the mgr's discovery host's discovery preferences
    Type discoveryLeafType = getDiscoveryLeafType(mgr);

    if (discoveryLeafType != null)
    {
      // Verifies that the discoveryLeafType implements BINDiscoveryLeaf
      if (discoveryLeafType.is(BINDiscoveryLeaf.TYPE))
      {
        return (BINDiscoveryLeaf)discoveryLeafType.getInstance(); // Returns a default instance of the discovery leaf type
      }
    }

    return null;
  }

  /*
   * Get abstract managers target type
   */
  static Type getDatabaseType(BAbstractManager mgr)
  {
    BObject val = mgr.getCurrentValue();

    if (val instanceof BIPointFolder)
    {
      return ((BIPointFolder)val).getProxyExtType();
    }

    if (val instanceof BIDeviceFolder)
    {
      return ((BIDeviceFolder)val).getDeviceType();
    }

    throw new BajaRuntimeException("Not Implemented");
  }

  /**
   * Returns a default instance of the given manager's database type.
   */
  public static BComponent getDatabaseInstanceDefault(BAbstractManager mgr)
  {
    Type typ = getDatabaseType(mgr);
    if (typ.isAbstract())
    {
      throw new BajaRuntimeException("Must specify non abstract class: " + typ.getTypeClass().getName() + " is abstract.");
    }
    return (BComponent)typ.getInstance();
  }

  /**
   * Finds the network that is above or that is the component on which the
   * abstract manager is an agent.
   */
  public static BNNetwork findNNetwork(BAbstractManager mgr)
  {
    return (BNNetwork)findType(mgr, BNNetwork.TYPE);
  }

  /**
   * Finds the device that is above or that is the component on which the
   * abstract manager is an agent.
   */
  public static BDevice findNDevice(BAbstractManager mgr)
  {
    return (BDevice)findType(mgr, BDevice.TYPE);
  }

  /**
   * Finds BINDiscoveryHost - must be value of manger view or return null.
   */
  public static BINDiscoveryHost findDiscoveryHost(BAbstractManager mgr)
  {
    BComplex obj = (BComplex)mgr.getCurrentValue();
    if (obj instanceof BINDiscoveryHost)
    {
      return (BINDiscoveryHost)obj;
    }
    return null;
    //  return (BINDiscoveryHost)findType(mgr, BINDiscoveryHost.TYPE);
  }

  /**
   * Finds the pointDeviceExt that is above or that is the component on which
   * the abstract manager is an agent.
   */
  public static BNPointDeviceExt findNPointDeviceExt(BAbstractManager mgr)
  {
    return (BNPointDeviceExt)findType(mgr, BNPointDeviceExt.TYPE);
  }

  private static BComplex findType(BAbstractManager mgr, Type t)
  {
    BComplex obj = (BComplex)mgr.getCurrentValue();

    if (obj.getType().is(t))
    {
      return obj;
    }

    return DrUtil.getParent(obj, t);
  }

  public static final Lexicon LEX = Lexicon.make(NMgrUtil.class);
}
