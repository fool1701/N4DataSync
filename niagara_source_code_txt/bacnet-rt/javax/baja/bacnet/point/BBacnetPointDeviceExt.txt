/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.point;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.baja.agent.AgentList;
import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BIBacnetObjectContainer;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.control.BControlPoint;
import javax.baja.driver.point.BPointDeviceExt;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.LongHashMap;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.*;

import com.tridium.bacnet.job.BBacnetDiscoverPointsJob;

/**
 * BBacnetPointDeviceExt.
 *
 * @author Craig Gemmill
 * @version $Revision: 1$ $Date: 12/19/01 4:32:48 PM$
 * @creation 17 Dec 2001
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraAction(
  name = "submitPointDiscoveryJob",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
public class BBacnetPointDeviceExt
  extends BPointDeviceExt
  implements BIBacnetObjectContainer
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.point.BBacnetPointDeviceExt(1950799180)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "submitPointDiscoveryJob"

  /**
   * Slot for the {@code submitPointDiscoveryJob} action.
   * @see #submitPointDiscoveryJob()
   */
  public static final Action submitPointDiscoveryJob = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code submitPointDiscoveryJob} action.
   * @see #submitPointDiscoveryJob
   */
  public BOrd submitPointDiscoveryJob() { return (BOrd)invoke(submitPointDiscoveryJob, null, null); }

  //endregion Action "submitPointDiscoveryJob"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetPointDeviceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Get the parent device Type.
   */
  public Type getDeviceType()
  {
    return BBacnetDevice.TYPE;
  }

  /**
   * Get the Type of proxy extensions for this device.
   */
  public Type getProxyExtType()
  {
    return BBacnetProxyExt.TYPE;
  }

  /**
   * Get the Type of point folder for this device.
   */
  public Type getPointFolderType()
  {
    return BBacnetPointFolder.TYPE;
  }

  /**
   * BBacnetPointDeviceExt can only be contained in a BBacnetDevice.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BBacnetDevice;
  }


////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  public BOrd doSubmitPointDiscoveryJob(Context cx)
  {
    if (device().isFatalFault()) return null;
    return new BBacnetDiscoverPointsJob(this).submit(cx);
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

  void registerPoint(BBacnetProxyExt pExt)
  {
    synchronized (byPointHash)
    {
      byPointHash.put(hash(pExt), pExt.getParentPoint());
      Array<BControlPoint> a = byObjectId.get(pExt.getObjectId());
      if (a == null)
      {
        a = new Array<>(BControlPoint.class);
      }
      a.add(pExt.getParentPoint());
      byObjectId.put(pExt.getObjectId(), a);
    }
  }

  void unregisterPoint(BBacnetProxyExt pExt)
  {
    BControlPoint pt = pExt.getParentPoint();
    synchronized (byPointHash)
    {
      LongHashMap.Iterator it = byPointHash.iterator();
      while (it.hasNext())
      {
        BControlPoint p = (BControlPoint)it.next();
        if (p.equals(pt))
          byPointHash.remove(it.key());
      }
      Array<BControlPoint> a = byObjectId.get(pExt.getObjectId());
      if (a != null)
      {
        a.remove(pExt.getParentPoint());
        if (a.size() == 0)
          byObjectId.values().remove(a);
      }
    }
  }

  void reregisterPoint(BBacnetProxyExt pExt)
  {
    synchronized (byPointHash)
    {
      unregisterPoint(pExt);
      registerPoint(pExt);
    }
  }

  private static long hash(BBacnetProxyExt pExt)
  {
    return hash(pExt.getObjectId().hashCode(), pExt.getPropertyId().getOrdinal(), pExt.getPropertyArrayIndex());
  }

  private static long hash(int objectId, int propertyId, int propertyArrayIndex)
  {
    long hash = ((long)objectId << 32) | ((propertyId & 0xFFFF) << 16) | (propertyArrayIndex & 0xFFFF);
    return hash;
  }

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
  {
    BControlPoint pt = (BControlPoint)byPointHash.get(hash(objectId.hashCode(), propertyId, propertyArrayIndex));
    return pt;
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
  {
    Array<BControlPoint> a = byObjectId.get(objectId);
    if (a != null)
      return a.trim();

    return new BControlPoint[] {};
  }

  /**
   * Find all proxy points with a given objectId, propertyId and propertyIdx.
   *
   * @param objectId
   * @param propertyId
   * @param propertyArrayIndex
   * @return an array of BControlPoints in this point device extension that have a
   * BBacnetProxyExt with this objectId, propertyId and propertyIdx.
   * returns null if no point has these parameters.
   */
  public final BControlPoint[] findPoints(BBacnetObjectIdentifier objectId,
                                          int propertyId,
                                          int propertyArrayIndex)
  {
    Array<BControlPoint> a = byObjectId.get(objectId);
    if (a != null)
    {
      int size = a.size();
      if (size > 0)
      {
        //Most common use case: single present-value exported for object, skip array filtering.
        if (size == 1 && pointMatches(a.get(0), propertyId, propertyArrayIndex))
        {
          return a.trim();
        }
        else
        {
          Array<BControlPoint> filtered = new Array<>(BControlPoint.class);
          for (int i = 0; i < size; i++)
          {
            BControlPoint point = a.get(i);
            if (pointMatches(point, propertyId, propertyArrayIndex))
            {
              filtered.add(point);
            }
          }
          return filtered.trim();
        }
      }
    }
    return new BControlPoint[] {};
  }

  private static boolean pointMatches(BControlPoint point,
                                      int propertyId,
                                      int propertyArrayIndex)
  {
    if (point.getProxyExt() instanceof BBacnetProxyExt)
    {
      BBacnetProxyExt proxy = (BBacnetProxyExt)point.getProxyExt();
      return proxy.getPropertyId().getOrdinal() == propertyId &&
        proxy.getPropertyArrayIndex() == propertyArrayIndex;
    }

    return false;
  }

////////////////////////////////////////////////////////////////
//  Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetPointDeviceExt", 2);

    out.trTitle("byPointHash:" + byPointHash.size(), 2);
    LongHashMap.Iterator it = byPointHash.iterator();
    while (it.hasNext())
    {
      BControlPoint pt = (BControlPoint)it.next();
      out.prop("  " + Long.toHexString(it.key()), SlotPath.unescape(pt.getName()) + ":" + pt.getHandleOrd());
    }

    out.trTitle("byObjectId:" + byObjectId.size(), 2);
    Enumeration<BBacnetObjectIdentifier> e = byObjectId.keys();
    while (e.hasMoreElements())
    {
      BBacnetObjectIdentifier k = e.nextElement();
      out.prop("  " + k, byObjectId.get(k));
    }

    out.endProps();
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


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private LongHashMap byPointHash = new LongHashMap();
  private Hashtable<BBacnetObjectIdentifier, Array<BControlPoint>> byObjectId = new Hashtable<>();
}
