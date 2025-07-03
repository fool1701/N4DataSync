/*
 * @copyright 2016 Tridium Inc.
 */
package com.tridium.nrio.components;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComplex;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.BNrio34Module;

/**
 * BNrio16Status - This is a structure to represent the base IO status from the GpIoModule.
 *
 * @author    Andy Saunders
 * @creation  Oct 09, 2016
 */


@NiagaraType

public class BNrio34PriStatus
  extends BNrio34SecStatus
  implements BINrioIoStatus
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.components.BNrio34PriStatus(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:13 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrio34PriStatus.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  public BNrio34PriStatus()
  {
  }

  private BNrio34SecStatus secIoStatus = null;
  public void setSecIoStatus(BNrio34SecStatus ioStatus)
  {
    this.secIoStatus = ioStatus;
  }
  public BNrio34SecStatus getSecIoStatus()
  {
    if(secIoStatus != null)
      return secIoStatus;
    final BComplex parent = getParent();
    if(parent instanceof BNrio34Module)
    {
      BNrio34Module device = (BNrio34Module)parent;
      secIoStatus = (BNrio34SecStatus)device.getIo34Sec().getIoStatus();
    }
    return secIoStatus;
  }

  public void doClearTotals()
  {
    super.doClearTotals();
    getSecIoStatus().doClearTotals();

  }

  public int getMaxUiInstance()
  {
    return 16;
  };


  public boolean getDi(int instance)
  {
    if(instance > 8) // di is on secondary device
    {
      return getSecIoStatus().getDi(instance-8);
    }
    return super.getDi(instance);
  }

  public int getAi(int instance)
  {
    if(instance > 8) // di is on secondary device
    {
      return getSecIoStatus().getAi(instance-8);
    }
    return super.getAi(instance);
  }

  public long getTotalCounts(int instance)
  {
    if(instance > 8)
      return getSecIoStatus().getTotalCounts(instance-8);
    return super.getTotalCounts(instance);
  }

  public long getDiCounts(int instance)
  {
    if(instance > 8)
      return getSecIoStatus().getDiCounts(instance-8);
    return super.getDiCounts(instance);
  }


}
