/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.discover;

import javax.baja.control.BControlPoint;
import javax.baja.driver.point.BProxyConversion;
import javax.baja.driver.point.BProxyExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BNPointDiscoveryLeaf is a base implementation of BINPointDiscoveryLeaf.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
@NiagaraType
public class BNPointDiscoveryLeaf
  extends BNDiscoveryLeaf
  implements BINPointDiscoveryLeaf
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.discover.BNPointDiscoveryLeaf(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:11 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNPointDiscoveryLeaf.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BINPointDiscoveryLeaf
////////////////////////////////////////////////////////////////

  /**
   * Your driver can extends this class and override this method to customize
   * the proxy conversion for learned points.
   */
  public BProxyConversion getDiscoveryConversion()
  {
    return null;
  }

  /**
   * Your driver can extends this class and override this method to customize
   * the device facets for learned points.
   */
  public BFacets getDiscoveryDeviceFacets()
  {
    return null;
  }

  /**
   * Your driver can extends this class and override this method to customize
   * the point facets for learned points.
   */
  public BFacets getDiscoveryPointFacets()
  {
    return null;
  }

  /**
   * Your driver can extends this class and override this method to customize
   * the tuning policy name for learned points.
   */
  public String getDiscoveryTuningPolicyName()
  {
    return null;
  }

  /**
   * Update the specified target component from BINDiscoveryLeaf values.
   */
  @Override
  public void updateTarget(BComponent target)
  {
  }

  /**
   * Do updates to specified target from default discovery leaf's values. Used
   * for framework supported updates.
   */
  @Override
  public void defaultTargetUpdate(BComponent target)
  {
    BControlPoint newCtPt = (BControlPoint)target;
    if (getDiscoveryPointFacets() != null)
    {
      newCtPt.setFacets(getDiscoveryPointFacets());
    }

    BProxyExt proxy = (BProxyExt)newCtPt.getProxyExt();
    if (getDiscoveryDeviceFacets() != null)
    {
      proxy.setDeviceFacets(getDiscoveryDeviceFacets());
    }
    if (getDiscoveryConversion() != null)
    {
      proxy.setConversion(getDiscoveryConversion());
    }
    if (getDiscoveryTuningPolicyName() != null)
    {
      proxy.setTuningPolicyName(getDiscoveryTuningPolicyName());
    }
  }
}
