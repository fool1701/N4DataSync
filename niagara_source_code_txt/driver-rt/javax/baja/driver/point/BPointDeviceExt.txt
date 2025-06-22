/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.point;

import java.util.ArrayList;

import javax.baja.agent.AgentList;
import javax.baja.control.BControlPoint;
import javax.baja.control.ext.BAbstractProxyExt;
import javax.baja.driver.BDeviceExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.util.PxUtil;
/**
 * BPointDeviceExt is the base class for mapping devices into
 * standard Baja points.
 *
 * @author    Brian Frank       
 * @creation  17 Oct 01
 * @version   $Revision: 49$ $Date: 7/29/04 6:22:07 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BPointDeviceExt
  extends BDeviceExt
  implements BIPointFolder
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.point.BPointDeviceExt(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPointDeviceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Get the parent device Type.
   */
  public abstract Type getDeviceType();      
  
  /**
   * Get the Type of proxy extensions for this device.
   */
  public abstract Type getProxyExtType();      

  /**
   * Get the Type of point folder for this device.
   */
  public abstract Type getPointFolderType();      

  /**
   * Return this.
   */
  public final BPointDeviceExt getDeviceExt()
  {
    return this;
  }
    
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get an array containing all the BControlPoints
   * children of this network component.
   */
  public BControlPoint[] getPoints()
  {
    ArrayList<BControlPoint> list = new ArrayList<>();
    getPoints(this, getProxyExtType(), list); 
    return list.toArray(new BControlPoint[list.size()]);
  }
  
  private void getPoints(BComponent comp, Type proxyExtType, ArrayList<BControlPoint> list)
  {           
    // use cursor so we don't taking out any locks                          
    SlotCursor<Property> cursor = comp.loadSlots().getProperties();
    while(cursor.nextComponent())
    {
      BComponent kid = cursor.get().asComponent();
      if (kid instanceof BControlPoint)
      {      
        if (((BControlPoint)kid).getProxyExt().getType().is(proxyExtType))               
          list.add((BControlPoint)kid);
      }
      else
      {
        getPoints(kid, proxyExtType, list);
      }
    }
  }                     
      
////////////////////////////////////////////////////////////////
// Status
////////////////////////////////////////////////////////////////

  /**
   * Update the status of all the points, by calling
   * BProxyExt.updateStatus().
   */
  public void updateStatus()
  {
    BControlPoint[] points = getPoints();
    for (int i=0; i<points.length; i++)
    {
      BAbstractProxyExt ext = points[i].getProxyExt();
      if (ext instanceof BProxyExt)
        ((BProxyExt)ext).updateStatus();
    }
  }
    
////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  public AgentList getAgents(Context cx)
  { 
    TypeInfo pointManager = Sys.getRegistry().getType("driver:PointManager");              
    AgentList agents = super.getAgents(cx);
    
    for(int i=0; i<agents.size(); ++i)
      if (agents.get(i).getAgentType().is(pointManager))
        return agents;
        
    agents.add(pointManager.getAgentInfo());
    return PxUtil.movePxViewsToTop(agents);
  }

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("navOnly/points.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////


}
