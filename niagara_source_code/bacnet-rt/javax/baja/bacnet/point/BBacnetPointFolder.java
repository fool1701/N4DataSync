/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.point;

import java.util.ArrayList;

import javax.baja.agent.AgentList;
import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BIBacnetObjectContainer;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.control.BControlPoint;
import javax.baja.driver.point.BPointFolder;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BBacnetPointFolder.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 30 Jun 2004
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
public class BBacnetPointFolder
  extends BPointFolder
  implements BIBacnetObjectContainer
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.point.BBacnetPointFolder(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:32 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetPointFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * BBacnetPointFolder may only be placed under a BBacnetPointDeviceExt or BBacnetPointFolder.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BBacnetPointDeviceExt || parent instanceof BBacnetPointFolder;
  }


////////////////////////////////////////////////////////////////
// BIBacnetObjectContainer
////////////////////////////////////////////////////////////////

  public BObject lookupBacnetObject(BBacnetObjectIdentifier objectId,
                                    int propertyId,
                                    int propertyArrayIndex,
                                    String domain)
  {
    return findPoint(objectId, propertyId, propertyArrayIndex);
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * @return the BBacnetDevice containing this BBacnetPointDeviceExt.
   */
  public final BBacnetDevice device()
  {
    return (BBacnetDevice)getDevice();
  }

  /**
   * Find a proxy point based on objectId, propertyId,
   * and propertyArrayIndex.
   *
   * @param objectId
   * @param propertyId
   * @param propertyArrayIndex
   * @return the BControlPoint in this point device extension that has a
   * BBacnetProxyExt with these parameters, or null if no
   * point has these parameters.
   */
  public final BControlPoint findPoint(BBacnetObjectIdentifier objectId,
                                       int propertyId,
                                       int propertyArrayIndex)
  { // FIXX: should this be recursive?  ComponentTreeCursor???
    SlotCursor<Property> c = getProperties();
    while (c.next(BControlPoint.class))
    {
      BControlPoint pt = (BControlPoint)c.get();
      Type type = pt.getProxyExt().getType();
//      if ((type == BBacnetNumericProxyExt.TYPE)
//        || (type == BBacnetBooleanProxyExt.TYPE)
//        || (type == BBacnetEnumProxyExt.TYPE)
//        || (type == BBacnetStringProxyExt.TYPE))
      if (type.is(BBacnetProxyExt.TYPE))
      {
        BBacnetProxyExt ext = (BBacnetProxyExt)pt.getProxyExt();
        if ((ext.getObjectId().equals(objectId))
          && (ext.getPropertyId().getOrdinal() == propertyId)
          && (ext.getPropertyArrayIndex() == propertyArrayIndex))
          return pt;
      }
    }
    return null;
  }

  /**
   * Find all proxy points with a given objectId.
   *
   * @param objectId
   * @return an array of BControlPoints in this point device extension that have a
   * BBacnetProxyExt with this objectId, or null if no
   * point has these parameters.
   */
  public final BControlPoint[] findPoints(BBacnetObjectIdentifier objectId)
  { // FIXX: should this be recursive?  ComponentTreeCursor???
    SlotCursor<Property> c = getProperties();
    ArrayList<BControlPoint> a = new ArrayList<>();
    while (c.next(BControlPoint.class))
    {
      BControlPoint pt = (BControlPoint)c.get();
      Type type = pt.getProxyExt().getType();
      if ((type == BBacnetNumericProxyExt.TYPE)
        || (type == BBacnetBooleanProxyExt.TYPE)
        || (type == BBacnetEnumProxyExt.TYPE)
        || (type == BBacnetStringProxyExt.TYPE))
      {
        BBacnetProxyExt ext = (BBacnetProxyExt)pt.getProxyExt();
        if (ext.getObjectId().equals(objectId))
          a.add(pt);
      }
    }
    BControlPoint[] pts = new BControlPoint[a.size()];
    return a.toArray(pts);
  }


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
    agents.toBottom("bacnetEDE:EdeBacnetPointManager");
    return agents;
  }

  public String toString(Context cx)
  {
    return "BacnetPointFolder:" + getName();
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

}
