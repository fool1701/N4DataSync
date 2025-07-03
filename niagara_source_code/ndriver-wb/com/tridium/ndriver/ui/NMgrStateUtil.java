/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui;

import java.util.Hashtable;
import javax.baja.naming.BOrd;
import javax.baja.sys.BajaException;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.workbench.mgr.BAbstractManager;

public final class NMgrStateUtil
{
  private NMgrStateUtil() {}

/////////////////////////////////////////////////////////////////////////
// Util
/////////////////////////////////////////////////////////////////////////

  /**
   * Called from NDeviceState or NPointState when a auto manager is
   * de-activated.
   */
  public static void saveState(BAbstractManager mgr)
  {
    if (mgr == null ||
      mgr.getLearn() == null ||
      mgr.getLearn().getJob() == null ||
      mgr.getLearn().getJob().getComponentSpace() == null)
    {
      return;
    }

    saveState(NMgrUtil.findDiscoveryHost(mgr), mgr.getLearn().getJob().getNavOrd());
  }

  protected static void saveState(Object mgrHashKey, BOrd navOrdOfLastLearn)
  {
    if (mgrHashKey == null || navOrdOfLastLearn == null)
    {
      return;
    }

    jobTable.put(mgrHashKey, navOrdOfLastLearn); // The navOrdOfLastLearn will allow us to restore the job if the manager is revisited
  }

  /**
   * Called from NDeviceState or NPointState when a auto manager is
   * re-activated.
   */
  public static void restoreState(BAbstractManager mgr)
  {
    if (mgr == null || mgr.getLearn() == null)
    {
      return;
    }

    restoreState(mgr, jobTable.get(NMgrUtil.findDiscoveryHost(mgr)));
  }

  protected static void restoreState(BAbstractManager mgr, BOrd navOrdOfLastLearn)
  {
    try
    {
      if (navOrdOfLastLearn == null || mgr.getLearn() == null)
      {
        return;
      }

      mgr.getLearn().setJob(navOrdOfLastLearn);
    }
    catch (BajaException e)
    {
      // This happens if the job has been cleaned up from the Job service.
      System.out.println(NMgrUtil.LEX.get("mgrJobExpired"));
    }
    catch (BajaRuntimeException e)
    {
      // This happens if the job has been cleaned up from the Job service.
      System.out.println(NMgrUtil.LEX.get("mgrJobExpired"));
    }
    catch (Exception e)
    {
      throw new BajaRuntimeException(e);
    }
  }

/////////////////////////////////////////////////////////////////////////
// Attributes
/////////////////////////////////////////////////////////////////////////
  protected static Hashtable<Object, BOrd> jobTable = new Hashtable<>();
}