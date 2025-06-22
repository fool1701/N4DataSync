/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.points;

import javax.baja.control.BControlPoint;
import javax.baja.control.ext.BAbstractProxyExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComplex;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.BNrio34Module;
import com.tridium.nrio.BNrio34SecModule;
import com.tridium.nrio.BNrioDevice;
import com.tridium.nrio.messages.NrioMessageConst;

/**
 * BNrio34PriSecPoints is the implementation of BPointDeviceExt
 * which provides a container for points for the Nrio 34 Module.
 *
 * @author    Andy Saunders
 * @creation  09 Oct 16
 */
@NiagaraType

public class BNrio34PriSecPoints
  extends BNrio16Points
  implements NrioMessageConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.points.BNrio34PriSecPoints(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:13 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrio34PriSecPoints.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  public void setDynamicPoints()
  {
    BNrioDevice nrioDevice = getNrioDevice();
    boolean isSecondary = nrioDevice instanceof BNrio34SecModule;
    if(isSecondary)
    {
      nrioDevice = ((BNrio34SecModule)nrioDevice).getParentModule();
    }
    BControlPoint[] cps = nrioDevice.getPoints().getPoints();
    for(int i = 0; i < cps.length; i++)
    {
      if( cps[i].getPropertyInParent().isFrozen() )
        continue;
      BAbstractProxyExt proxy = cps[i].getProxyExt();
      if(proxy instanceof BUiProxyExt)
      {
        BUiProxyExt proxyExt = (BUiProxyExt)proxy;
        if(!proxyExt.getEnabled() || cps[i].isWritablePoint())
          continue;
        if(isSecondary && proxyExt.getInstance() < 9)
          continue;
        if(!isSecondary && proxyExt.getInstance() > 8)
          continue;
        proxyExt.ioValueChanged();
      }
    }
  }



}
