/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.proxy;

import javax.baja.agent.AgentList;
import javax.baja.driver.point.BPointDeviceExt;
import javax.baja.driver.point.BPointFolder;
import javax.baja.lonworks.BLonDevice;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BLonPointDeviceExt is the lighweight container for LonProxyPoints.
 *
 * @author    Robert Adams
 * @creation  19 Dec 01
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:42 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
public class BLonPointDeviceExt
  extends BPointDeviceExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.proxy.BLonPointDeviceExt(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonPointDeviceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * BLonPointDeviceExt can only be contained in a BLonDevice.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BLonDevice;
  }
  
  /**
   * BLonPointDeviceExt can only contain a BPointFolder of type BLonPointFolder
   */
  public boolean isChildLegal(BComponent child)
  {
    if( child.getType().is(BPointFolder.TYPE) &&
        !child.getType().is(BLonPointFolder.TYPE) )
    {
      return false;
    }    
    return true;
  }
  
  /**
   * Get the parent device Type.
   */
  public Type getDeviceType() { return BLonDevice.TYPE; }

  /**
   * Get the Type of point folder for this device.
   */
  public Type getPointFolderType() { return BLonPointFolder.TYPE; }      
  

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Return BLonProxyExt Type for this device.
   */
  public Type getProxyExtType() { return BLonProxyExt.TYPE; }     

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Get the agent list.  Remove Device Manager and Network Summary.
   */
  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.remove("driver:PointManager");
    agents.remove("driver:PointSummary");
    return agents;
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////


}
