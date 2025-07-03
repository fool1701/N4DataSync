/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.virtual;

import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.point.BBacnetTuningPolicy;
import javax.baja.bacnet.point.BBacnetTuningPolicyMap;
import javax.baja.data.BIDataValue;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.*;
import javax.baja.virtual.BVirtualComponent;

import com.tridium.bacnet.job.BacnetDiscoveryUtil;

/**
 * BBacnetVirtualObject is the virtual representation of a BACnet
 * object.  It contains the objectId, tuning policy name, priority for
 * writing, and any point facets that configure the display of property
 * data.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 05 Dec 2007
 * @since NiagaraAX 3.3
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.DEFAULT",
  flags = Flags.HIDDEN | Flags.READONLY
)
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "UNINITIALIZED_FACETS"
)
/*
 References the TuningPolicy component by name.
 */
@NiagaraProperty(
  name = "tuningPolicyName",
  type = "String",
  defaultValue = "defaultPolicy",
  facets = @Facet(name = "BFacets.FIELD_EDITOR", value = "\"bacnet:VirtualTuningPolicyNameFE\"")
)
@NiagaraProperty(
  name = "writePriority",
  type = "int",
  defaultValue = "-1"
)
@NiagaraProperty(
  name = "prioritizedPoint",
  type = "boolean",
  defaultValue = "false"
)
public class BBacnetVirtualObject
  extends BVirtualComponent
  implements BacnetConst
{
  private static final BFacets UNINITIALIZED_FACETS = BFacets.make("initialized", BBoolean.FALSE);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.virtual.BBacnetVirtualObject(201583228)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.HIDDEN | Flags.READONLY, BBacnetObjectIdentifier.DEFAULT, null);

  /**
   * Get the {@code objectId} property.
   * @see #objectId
   */
  public BBacnetObjectIdentifier getObjectId() { return (BBacnetObjectIdentifier)get(objectId); }

  /**
   * Set the {@code objectId} property.
   * @see #objectId
   */
  public void setObjectId(BBacnetObjectIdentifier v) { set(objectId, v, null); }

  //endregion Property "objectId"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, UNINITIALIZED_FACETS, null);

  /**
   * Get the {@code facets} property.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "tuningPolicyName"

  /**
   * Slot for the {@code tuningPolicyName} property.
   * References the TuningPolicy component by name.
   * @see #getTuningPolicyName
   * @see #setTuningPolicyName
   */
  public static final Property tuningPolicyName = newProperty(0, "defaultPolicy", BFacets.make(BFacets.FIELD_EDITOR, "bacnet:VirtualTuningPolicyNameFE"));

  /**
   * Get the {@code tuningPolicyName} property.
   * References the TuningPolicy component by name.
   * @see #tuningPolicyName
   */
  public String getTuningPolicyName() { return getString(tuningPolicyName); }

  /**
   * Set the {@code tuningPolicyName} property.
   * References the TuningPolicy component by name.
   * @see #tuningPolicyName
   */
  public void setTuningPolicyName(String v) { setString(tuningPolicyName, v, null); }

  //endregion Property "tuningPolicyName"

  //region Property "writePriority"

  /**
   * Slot for the {@code writePriority} property.
   * @see #getWritePriority
   * @see #setWritePriority
   */
  public static final Property writePriority = newProperty(0, -1, null);

  /**
   * Get the {@code writePriority} property.
   * @see #writePriority
   */
  public int getWritePriority() { return getInt(writePriority); }

  /**
   * Set the {@code writePriority} property.
   * @see #writePriority
   */
  public void setWritePriority(int v) { setInt(writePriority, v, null); }

  //endregion Property "writePriority"

  //region Property "prioritizedPoint"

  /**
   * Slot for the {@code prioritizedPoint} property.
   * @see #getPrioritizedPoint
   * @see #setPrioritizedPoint
   */
  public static final Property prioritizedPoint = newProperty(0, false, null);

  /**
   * Get the {@code prioritizedPoint} property.
   * @see #prioritizedPoint
   */
  public boolean getPrioritizedPoint() { return getBoolean(prioritizedPoint); }

  /**
   * Set the {@code prioritizedPoint} property.
   * @see #prioritizedPoint
   */
  public void setPrioritizedPoint(boolean v) { setBoolean(prioritizedPoint, v, null); }

  //endregion Property "prioritizedPoint"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetVirtualObject.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BBacnetVirtualObject()
  {
  }

  public BBacnetVirtualObject(String virtualPathName)
  {
    try
    {
      // virtualPathName is a series of one or more tokens separated by semicolons
      // must have at least one token with objectId
      StringTokenizer st = new StringTokenizer(virtualPathName, ";");
      BBacnetObjectIdentifier id = (BBacnetObjectIdentifier)BBacnetObjectIdentifier.DEFAULT
        .decodeFromString(st.nextToken());
      setObjectId(id);

      // additionally, may have up to 1 token each for policy= and priority=
      while (st.hasMoreTokens())
      {
        String s = st.nextToken();
        if (s.startsWith(POLICY_DEF))
        {
          setTuningPolicyName(s.substring(POLICY_DEF_LEN));
        }
        else if (s.startsWith(PRIORITY_DEF))
        {
          try
          {
            int pri = Integer.parseInt(s.substring(PRIORITY_DEF_LEN));
            setWritePriority(pri);
          }
          catch (Exception e)
          {
            log.log(Level.SEVERE, "Invalid priority: " + s + " in virtualPathName for " + virtualPathName, e);
          }
        }
      }
    }
    catch (IOException e)
    {
      log.log(Level.SEVERE, "IOException occurred in BBacnetVirtualObject", e);
    }
  }


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public String toString(Context cx)
  {
    return getObjectId().toString(cx);
  }

  public void started()
    throws Exception
  {
    discoverFacets();
  }

  public boolean isChildLegal(BComponent child)
  {
    return child instanceof BBacnetVirtualProperty;
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the containing BBacnetDevice.
   * This method will not work for the "local" version.
   *
   * @return the BBacnetDevice in which this object resides.
   */
  public BBacnetDevice device()
  {
    if (getVirtualGateway() == null) return null;
    return ((BBacnetVirtualGateway)getVirtualGateway()).device();
  }

  public void updateStatus()
  {
    SlotCursor<Property> sc = getProperties();
    while (sc.next(BBacnetVirtualProperty.class))
      ((BBacnetVirtualProperty)sc.get()).updateStatus();
  }

  /**
   * Get the BTuningPolicy configured by policyName. If the policyName doesn't
   * map to a valid policy then log a warning and use the defaultPolicy.
   * <p>
   * Note that all virtual components are polled at this time.
   */
  public BBacnetTuningPolicy getPolicy()
  {
    if (cachedPolicy == null)
    {
      String tpName = getTuningPolicyName();
      BBacnetTuningPolicyMap map = getPolicyMap();
      BValue x = map.get(tpName);
      if (x instanceof BBacnetTuningPolicy)
      {
        cachedPolicy = (BBacnetTuningPolicy)x;
      }
      else
      {
        log.warning("TuningPolicy not found: " + tpName);
        cachedPolicy = (BBacnetTuningPolicy)map.getDefaultPolicy();
      }
    }
    return cachedPolicy;
  }


////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  /**
   * Discover the facets to be used for the properties that use them.
   */
  protected void discoverFacets()
  {
    network().postAsync(new Runnable()
    {
      public void run()
      {
        // Discover the facets to be used for this point.
        HashMap<String, BIDataValue> m = BacnetDiscoveryUtil.discoverFacets(getObjectId(), device());
        setFacets(BFacets.make(m));

        // Check if this is a prioritized point.
        int objectType = getObjectId().getObjectType();
        if ((objectType == BBacnetObjectType.ANALOG_OUTPUT)
          || (objectType == BBacnetObjectType.BINARY_OUTPUT)
          || (objectType == BBacnetObjectType.MULTI_STATE_OUTPUT))
          setPrioritizedPoint(true);
        else if ((objectType == BBacnetObjectType.ANALOG_VALUE)
          || (objectType == BBacnetObjectType.BINARY_VALUE)
          || (objectType == BBacnetObjectType.MULTI_STATE_VALUE))
          setPrioritizedPoint(BacnetDiscoveryUtil.checkForPriorityArray(getObjectId(), device()).getBoolean());
      }
    });
  }

  /**
   * Get the containing BBacnetNetwork.
   * This method will not work for the "local" version.
   */
  private BBacnetNetwork network()
  {
    return ((BBacnetVirtualGateway)getVirtualGateway()).network();
  }

  /**
   * Get the BTuningPolicyMap on the parent network.
   */
  private BBacnetTuningPolicyMap getPolicyMap()
  {
    BBacnetTuningPolicyMap map = (BBacnetTuningPolicyMap)
      network().get("tuningPolicies");
    if (map != null)
      return map;
    throw new IllegalStateException("Network missing tuningPolicies property");
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetVirtualObject", 2);
    out.prop("cachedPolicy", cachedPolicy);
    out.endProps();
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static Logger log = Logger.getLogger("bacnet.virtual");

  static final String POLICY_DEF = "policy=";
  static final int POLICY_DEF_LEN = 7;
  static final String PRIORITY_DEF = "priority=";
  static final int PRIORITY_DEF_LEN = 9;


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BBacnetTuningPolicy cachedPolicy = null;
}
