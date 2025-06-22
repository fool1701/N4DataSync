/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitLon;

import javax.baja.lonworks.BLonDevice;
import javax.baja.lonworks.datatypes.BDeviceData;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.lonworks.datatypes.BCommissionParameter;
import com.tridium.lonworks.netmgmt.BLonNetmgmt;

/**
 * BLonReplace action which can be added to a BLonDevice and linked to a
 *  PxPages to invoke a BLonNetmgmt.replace of the parent device.
 *
 * @author    Robert Adams
 * @creation  27 April 2006
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraAction(
  name = "replace",
  parameterType = "BReplaceParameter",
  defaultValue = "new BReplaceParameter()"
)
public class BLonReplace
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitLon.BLonReplace(4294538174)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "replace"

  /**
   * Slot for the {@code replace} action.
   * @see #replace(BReplaceParameter parameter)
   */
  public static final Action replace = newAction(0, new BReplaceParameter(), null);

  /**
   * Invoke the {@code replace} action.
   * @see #replace
   */
  public void replace(BReplaceParameter parameter) { invoke(replace, parameter, null); }

  //endregion Action "replace"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonReplace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BLonReplace()
  {
  }
  
  public boolean isParentLegal(BComponent parent) 
  {
    return parent instanceof BLonDevice;
  }
  
  public BValue getActionParameterDefault(Action action)  
  {
    if (action == replace)
    {
      BLonDevice dev = (BLonDevice)getParent();
      getComponentSpace().update(dev,1);
      BReplaceParameter param = new BReplaceParameter();
      param.setNeuronId(dev.getDeviceData().getNeuronId());
      return param;
    }
    return super.getActionParameterDefault(action);
  }


  /**
   * Init if started after steady state has been reached.
  public void started()
    throws Exception
  {
    super.started();
  }
  
  public void stopped()
    throws Exception
  {
    super.stopped();
  }
   */

  /**
   * Reinitialize timer if updateTime changes
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
   
    if (!isRunning()) return;

  }
   */

  public void doReplace(BReplaceParameter param)
  {
    BLonDevice dev = (BLonDevice)getParent();
    BDeviceData dd = dev.getDeviceData();
    BLonNetmgmt net = dev.lonNetwork().netmgmt();

    if(param.getServicePin())
    {
      // replace
      net.replaceDevice(new BCommissionParameter(dd.getSubnetNodeId(), true));
      // provide popup for service pin
     //  getManager().attach((new ServicePinMonitor(getManager(),job)));
    }
    else
    {
      // replace
      net.replaceDevice(new BCommissionParameter(dd.getSubnetNodeId(), param.getNeuronId(), false));
    }
  }

////////////////////////////////////////////////////////////////
// Icon
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("action.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

}
