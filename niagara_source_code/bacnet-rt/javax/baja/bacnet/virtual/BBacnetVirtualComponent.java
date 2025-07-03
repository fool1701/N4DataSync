/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
/**
 *
 */
package javax.baja.bacnet.virtual;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.*;
import javax.baja.bacnet.datatypes.*;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.point.BBacnetTuningPolicy;
import javax.baja.bacnet.point.BBacnetTuningPolicyMap;
import javax.baja.bacnet.util.*;
import javax.baja.data.BIDataValue;
import javax.baja.driver.util.BPollFrequency;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.spy.SpyWriter;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.virtual.BVirtualComponent;
import javax.baja.virtual.BVirtualComponentSpace;

import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.job.BacnetDiscoveryUtil;
import com.tridium.bacnet.stack.BBacnetPoll;
import com.tridium.bacnet.stack.transport.TransactionException;

/**
 * BBacnetVirtualComponent is the implementation of BVirtualComponent for the
 * BACnet driver. It includes the syntax for indicating a fixed priority for
 * potential writes, optional declaration of a different tuning policy, and
 * specification of additional sources for status information.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 12 Dec 2006
 * @since Niagara 3.2
 * @deprecated
 */
@NiagaraType
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.NULL"
)
@NiagaraAction(
  name = "subscribe",
  flags = Flags.ASYNC | Flags.HIDDEN
)
@Deprecated
public class BBacnetVirtualComponent
  extends BVirtualComponent
  implements BIBacnetPollable, BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.virtual.BBacnetVirtualComponent(3630392080)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.NULL, null);

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

  //region Action "subscribe"

  /**
   * Slot for the {@code subscribe} action.
   * @see #subscribe()
   */
  public static final Action subscribe = newAction(Flags.ASYNC | Flags.HIDDEN, null);

  /**
   * Invoke the {@code subscribe} action.
   * @see #subscribe
   */
  public void subscribe() { invoke(subscribe, null, null); }

  //endregion Action "subscribe"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetVirtualComponent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Required no-arg constructor.
   */
  public BBacnetVirtualComponent()
  {
  }

  /**
   * The syntax for creating BacnetVirtualComponents is:<p>
   * <code>object_Id/propertyId/index</code><p>
   * For example,<p>
   * <code>analogInput_1/presentValue</code>
   * <code>analogOutput_10/priorityArray/8</code><p><p>
   * Optional entries may be added separated by semicolons:<p>
   * <code>priority=<i>X</i></code> may be added after the objectId;<p>
   * <code>policy=<i>tpName</i></code> may be added after the objectId;<p>
   * <code>status=<i>type_inst_propId_ndx_dev_inst</i></code> may be added after the property name.
   */
  public BBacnetVirtualComponent(String virtualPathName)
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine("BVC ctor(): vpath=" + virtualPathName);
    }
    try
    {
      StringTokenizer st = new StringTokenizer(virtualPathName, ";");
      objectId = (BBacnetObjectIdentifier)BBacnetObjectIdentifier.DEFAULT
        .decodeFromString(st.nextToken());
      while (st.hasMoreTokens())
      {
        String s = st.nextToken();
        if (s.startsWith(POLICY_DEF))
        {
          tpName = s.substring(POLICY_DEF_LEN);
        }
        else if (s.startsWith(PRIORITY_DEF))
        {
          try
          {
            writePriority = Integer.parseInt(s.substring(PRIORITY_DEF_LEN));
          }
          catch (Exception e)
          {
            if (log.isLoggable(Level.FINE))
            {
              log.fine("Invalid priority: " + s + " in virtualPathName");
            }
          }
        }
      }
    }
    catch (IOException e)
    {
      log.log(Level.SEVERE, "IOException occurred in BBacnetVirtualComponent", e);
    }
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  public void doSubscribe()
  {
    BBacnetPoll pollService = (BBacnetPoll)network().getPollService(this);
    pollService.subscribe(this);
    isPollSubscribed = true;
  }

  /**
   * Enqueues the invocation in the network object.
   * public IFuture post(Action a, BValue arg, Context cx)
   * {
   * network().postAsync(new Invocation(this,a,arg,cx));
   * return null;
   * }
   */

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  public String toString(Context cx)
  {
    return getName() + " (V)";
  }

  /**
   * Override default behavior to allow non-virtual components.  Some
   * BACnet properties are represented in Niagara by BComponent datatypes.
   */
  public boolean isChildLegal(BComponent c)
  {
    return true;
  }

  public void stopped()
    throws Exception
  {
    super.stopped();
    BBacnetPoll pollService = (BBacnetPoll)network().getPollService(this);
    pollService.unsubscribe(this);
  }

  public void added(Property p, Context cx)
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine(this + ".added(): " + p.getName() + " [" + p + "] cx=" + cx
                 + (isSubscribed() ? " subscribed" : " unsubscribed")
                 + (ples != null ? "  ples:" + ples.length : " ples=null"));
    }
    if (!isRunning())
      return;

    if (isSubscribed())
    {
      Array<PollListEntry> a;
      if (ples == null)
        a = new Array<>(PollListEntry.class);
      else
        a = new Array<>(ples);
      addPolledProperty(p, a);
      if (a.size() > 0)
        ples = a.trim();
      if (!isPollSubscribed)
        subscribe();
    }
  }

  public void removed(Property p, BValue oldValue, Context cx)
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine(this + ".removed(): " + p.getName() + " [" + p + "] ov=" + oldValue
                 + " cx=" + cx + (isSubscribed() ? " subscribed" : " unsubscribed"));
    }
    if (!isRunning())
      return;

    if (isSubscribed())
    {
      Array<PollListEntry> a; // not sure if this is needed for remove()...
      if (ples == null)
        a = new Array<>(PollListEntry.class);
      else
        a = new Array<>(ples);
      removePolledProperty(p, oldValue, a);
      ples = a.trim();
    }
  }

  public void subscribed()
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine(this + ".subscribed(): path=" + getSlotPathOrd());
    }

    network().postAsync(new Runnable()
    {
      public void run()
      {
        HashMap<String, BIDataValue> m = BacnetDiscoveryUtil.discoverFacets(objectId, device());
        setFacets(BFacets.make(m));
      }
    });

    if (getPollListEntries() != null)
    {
      isPollSubscribed = true;
      subscribe();
    }
  }

  public void unsubscribed()
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine(this + ".unsubscribed(): path=" + getSlotPathOrd());
    }
    BBacnetPoll pollService = (BBacnetPoll)network().getPollService(this);
    pollService.unsubscribe(this);
    ples = null;
    isPollSubscribed = false;
    propsMap.clear();
  }

  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (cx == noWrite)
      return;
    if (!isRunning())
      return;
    BFacets f = p.getFacets();
    if (log.isLoggable(Level.FINE))
    {
      log.fine("changed(" + p + ", " + cx + ") on " + this + " prop facets=" + f);
    }
    BInteger pid = (BInteger)f.getFacet(PROPERTY_ID);
    if (pid != null)
    {
      BValue v = get(p);
      int index = NOT_USED;
      if (v instanceof BBacnetVirtualArray)
      {
        if (cx != null)
        {
          BInteger ndx = (BInteger)cx.getFacet(INDEX);
          if (ndx != null)
          {
            index = ndx.getInt();
            v = ((BBacnetVirtualArray)v).getElement(index);
          }
        }
      }

      byte[] encodedValue = null;
      PropertyInfo pi = device().getPropertyInfo(objectId.getObjectType(),
        pid.getInt());
      if (v instanceof BStatusValue)
      {
        BStatusValue sv = (BStatusValue)v;
        if (sv.getStatus().isNull())
          encodedValue = AsnUtil.toAsnNull();
        else
        {
          if (pi != null)
            encodedValue = AsnUtil.toAsn(pi.getAsnType(), sv.getValueValue());
          else
            encodedValue = AsnUtil.toAsn(v); // best guess
        }
      }
      else
      {
        if (pi != null)
          encodedValue = AsnUtil.toAsn(pi.getAsnType(), v);
        else
          encodedValue = AsnUtil.toAsn(v); // best guess
      }
      network().postWrite(
        new Write(pid.getInt(), index, encodedValue, writePriority));
    }
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * ObjectId is stored as an instance field in the virtual component.
   */
  protected BBacnetObjectIdentifier getObjectId()
  {
    return objectId;
  }

  /**
   * Get the priority to be used for writing the Present_Value property of this
   * object.
   *
   * @return write priority
   */
  public int getWritePriority()
  {
    return writePriority;
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

  public BFacets getSlotFacets(Slot s)
  {
    BFacets f = s.getFacets();
    BBoolean useFacets = (BBoolean)f.getFacet(USE_FACETS);
    if ((useFacets != null) && useFacets.getBoolean())
    {
      BFacets vcfac = getFacets();
      if (vcfac.equals(BFacets.NULL))
        return f;
      else
        return vcfac;
    }
    return f;
  }

  /**
   * Does this property require a status? This is based on whether the "status="
   * option exists in the virtualPathName. If true, the property will be created
   * as a BStatusValue, instead of the default type.
   *
   * @param virtualPathName
   * @return true if this property requires an associated status.
   */
  public static boolean isStatusProp(String virtualPathName)
  {
    return (virtualPathName.indexOf(STATUS_TAG) >= 0);
  }

  /**
   * Extract the source(s) of status information from the property name used to
   * create the virtual component. This inspects each modifier, separated by
   * semicolons, for the "status=" tag. Found status sources are returned as a
   * BString, separated by semicolons.
   *
   * @param propertyName
   * @return status source
   */
  public static BString getStatusSource(String propertyName)
  {
    StringBuilder sb = new StringBuilder();
    StringTokenizer st = new StringTokenizer(propertyName, ";");
    while (st.hasMoreTokens())
    {
      String s = st.nextToken();
      if ((s != null) && s.startsWith(STATUS_TAG))
      {
        sb.append(s.substring(STATUS_TAG_LEN)).append(';');
      }
    }
    return BString.make(sb.toString());
  }

  public void updateStatus()
  {
    BStatus devStatus = device().getStatus();
//    int devStatusBits = device().getStatus().getBits();
    SlotCursor<Property> sc = getProperties();

    while (sc.next(BStatusValue.class))
    {
      BStatusValue sv = (BStatusValue)sc.get();
      int oldStatus = sv.getStatus().getBits();
      int newStatus = sv.getStatus().getBits();
      if (devStatus.isDisabled())
        newStatus |= BStatus.DISABLED;
      else
        newStatus &= ~BStatus.DISABLED;
      if (devStatus.isDown())
        newStatus |= BStatus.DOWN;
      else
        newStatus &= ~BStatus.DOWN;
      if (devStatus.isFault())
        newStatus |= BStatus.FAULT;
      else
        newStatus &= ~BStatus.FAULT;
      if (oldStatus == newStatus)
        continue;
      sv.setStatus(BStatus.make(newStatus));
    }
  }

  // //////////////////////////////////////////////////////////////
  // BIBacnetPollable
  // //////////////////////////////////////////////////////////////

  public BPollFrequency getPollFrequency()
  {
    BBacnetTuningPolicy policy = getPolicy();
    if (policy != null)
      return policy.getPollFrequency();
    return BPollFrequency.normal;
  }

  /**
   * Get the containing device object which will poll this object.
   *
   * @return the containing BBacnetDevice
   */
  public BBacnetDevice device()
  {
    return (BBacnetDevice)((BVirtualComponentSpace)this.getComponentSpace())
      .getVirtualGateway().getParent();
  }

  /**
   * Get the pollable type of this object.
   *
   * @return one of the pollable types defined in BIBacnetPollable.
   */
  public int getPollableType()
  {
    return BACNET_POLLABLE_VIRTUAL;
  }

  /**
   * Poll the node.
   *
   * @return true if a poll was attempted to this node, or false if the poll
   * was skipped due to device down, out of service, etc.
   * @deprecated As of 3.2
   */
  @Deprecated
  public boolean poll()
  {
    return false;
  }

  /**
   * Indicate a failure polling this object.
   *
   * @param failureMsg
   */
  public void readFail(String failureMsg)
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine("readFail(" + failureMsg + ") on " + this);
    }
    // This does it for all status values, not just the one that failed
    // (which one failed we don't know).
    // SlotCursor sc = getProperties();
    // while (sc.next(BStatusValue.class))
    // ((BStatusValue)sc.get()).setStatusFault(true);
  }

  /**
   * Normalize the encoded data into the pollable's data structure.
   *
   * @param encodedValue
   * @param status
   * @param cx
   */
  public void fromEncodedValue(byte[] encodedValue, BStatus status, Context cx)
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine(this + ".fromEncodedValue:"
                 + ByteArrayUtil.toHexString(encodedValue) + " status=" + status
                 + "  cx=" + cx);
    }
    try
    {
      PollListEntry ple = (PollListEntry)cx;
      Context baseCx = cx.getBase();

      // Check for status/metadata information
      if (baseCx instanceof MetaDataContext)
      {
        readMetaData(encodedValue, cx, (MetaDataContext)baseCx);
        return;
      }

//      // Set the data size for the PLE.
//      ple.setDataSize(encodedValue.length);

      // Make sure we have a property in which to put the data.
      Property p = getProperty(ple.getPropertyId(), ple.getPropertyArrayIndex());
      if (p == null)
      {
        readFail("No property in virtual point for " + ple);
        ((BBacnetPoll)network().getPollService(this)).removePLE(this, ple);
        return;
      }

      // Check if the property is a BStatusValue
      BValue v = get(p);
      BStatusValue sv = null;
      if (v instanceof BStatusValue)
        sv = (BStatusValue)v.newCopy();

      synchronized (asnIn)
      {
        setProp(p, encodedValue, v, sv, ple.getPropertyArrayIndex());

        // Now for any others...
        SlotCursor<Property> c = getProperties();
        String pname = p.getName();
        while (c.next())
        {
          if (c.property().getName().startsWith(pname))
          {
            v = c.get();
            sv = null;
            if (v instanceof BStatusValue)
              sv = (BStatusValue)v.newCopy();
            setProp(c.property(), encodedValue, v, sv, ple
              .getPropertyArrayIndex());
          }
        }

      }

      // Indicate a successful read - clear any read fault
      readOk(p, ple);

    }
    catch (AsnException e)
    {
      readFail(e.toString());
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "Exception decoding value for " + this + ":" + e + " ev="
          + ByteArrayUtil.toHexString(encodedValue), e);
      }
    }
  }

  /**
   * Get the list of poll list entries for this pollable. The first entry for
   * points must be the configured property.
   *
   * @return the list of poll list entries.
   */
  public PollListEntry[] getPollListEntries()
  {
    if (!isRunning())
      return null;
    if (ples == null)
    {
      Array<PollListEntry> a = new Array<>(PollListEntry.class);
      SlotCursor<Property> sc = getProperties();
      while (sc.next())
      {
        addPolledProperty(sc.property(), a);
      }

      if (a.size() > 0)
        ples = a.trim();
    }
    return ples;
  }

  // //////////////////////////////////////////////////////////////
  // Spy
  // //////////////////////////////////////////////////////////////

  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetVirtualComponent", 2);
    out.prop("objectId", objectId);
    out.prop("tpName", tpName);
    out.prop("cachedPolicy", cachedPolicy);
    out.prop("writePriority", writePriority);
    out.prop("isPollSubscribed", isPollSubscribed);
    if (ples != null)
    {
      out.prop("PollListEntries (unsynch)", ples.length);
      for (int i = 0; i < ples.length; i++)
        out.prop("  " + i, ples[i]);
    }
    out.prop("props", propsMap.size());
    Enumeration<String> e = propsMap.keys();
    while (e.hasMoreElements())
    {
      Object k = e.nextElement();
      out.prop(" " + k, propsMap.get(k));
    }
    out.endProps();
  }

  // //////////////////////////////////////////////////////////////
  // Utility
  // //////////////////////////////////////////////////////////////

  /**
   * Get the Property for this id and index pair from the map.
   */
  private Property getProperty(int propertyId, int index)
  {
    return propsMap.get(String.valueOf((propertyId << 16)
      | (index & 0xFFFF)));
  }

//  private Property getMetaDataProperty(String propertyName)
//  {
//    return (Property) propsMap.get(propertyName);
//  }

  /**
   * Convenience reference to BacnetNetwork.
   */
  private BBacnetNetwork network()
  {
    return (BBacnetNetwork)device().getNetwork();
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

//  /**
//   * Indicate a failure polling this object.
//   * @param p property on which the failure occurred
//   * @param failureMsg
//   */
//  private void readFail(Property p, String failureMsg)
//  {
//    if (get(p) instanceof BStatusValue)
//    {
//      updateStatus(p, BStatus.FAULT);
//    }
//    readFail(p.getName() + ":" + failureMsg);
//  }

  private void readOk(Property p, PollListEntry ple)
  {
    if (get(p) instanceof BStatusValue)
    {
      updateStatus(p, ple, BStatus.ok);
    }
  }

  /**
   * Add a property to the list of things that must be polled from this virtual
   * component. This may include references that are not in this object, or even
   * in this device, if status information is being retrieved from other places.
   *
   * @param p
   * @param a
   */
  private void addPolledProperty(Property p, Array<PollListEntry> a)
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine(this + ".addPolledProperty(): " + p + " facets="
                 + getFacets(/* false */));
    }

    int index = NOT_USED;
    BFacets f = p.getFacets();
    BInteger pid = (BInteger)f.getFacet(PROPERTY_ID);
    if (pid == null)
      return;
    BValue v = get(p);

    // Virtual arrays must add all configured elements.
    if (v instanceof BBacnetVirtualArray)
    {
      BBacnetVirtualArray va = (BBacnetVirtualArray)get(p);
      SlotCursor<Property> sc = va.getProperties();
      sc.next();
      sc.next();
      while (sc.next())
      {
        index = BBacnetVirtualArray.index(sc.property().getName());
        a.add(new PollListEntry(objectId, pid.getInt(), index, device(), this));
        String key = String.valueOf((pid.getInt() << 16) | (index & 0xFFFF));
        propsMap.put(key, sc.property());

// Not yet - this will need to go in to drive polling of the configured status property.
//        // Check if this property requires additional polling for status
//        // information.
//        BString statusSourceFacet = (BString) f.getFacet(STATUS_SOURCE_FACET); // FIXX:correct place to check?
//        if ((v instanceof BStatusValue) && (statusSourceFacet != null))
//        {
//          StringTokenizer st = new StringTokenizer(statusSourceFacet.getString(),
//              ";");
//          while (st.hasMoreTokens())
//          {
//            addDoprPLE(st.nextToken(), a, p);
//            key = p.getName();
//          }
//        }
//        // put another entry in with the new key ...
//        propsMap.put(key, p);

      }
    }

    // Non-array components are polled in their entirety.
    else
    {
      BInteger ndx = (BInteger)f.getFacet(INDEX);
      if (ndx != null)
        index = ndx.getInt();
      a.add(new PollListEntry(objectId, pid.getInt(), index, device(), this));
      String key = String.valueOf((pid.getInt() << 16) | (index & 0xFFFF));
      propsMap.put(key, p);

      // Check if this property requires additional polling
      // for status information.
      BString statusSourceFacet = (BString)f.getFacet(STATUS_SOURCE_FACET);
      if ((v instanceof BStatusValue) && (statusSourceFacet != null))
      {
        StringTokenizer st = new StringTokenizer(statusSourceFacet.getString(),
          ";");
        while (st.hasMoreTokens())
        {
          addDoprPLE(st.nextToken(), a, p);
          key = p.getName();
        }
      }
      // put another entry in with the new key ...
      propsMap.put(key, p);
    }
  }

  /**
   * Add a PollListEntry for a BACnetDeviceObjectPropertyReference pointing to
   * an additional source of status information.
   * <p>
   * This uses the MetaDataContext to indicate the main property that is being
   * modified with the status information.
   *
   * @param token         String representation of the DOPR.
   * @param a
   * @param basePropId    propertyId of the main property.
   * @param basePropIndex propertyArrayIndex of the main property.
   */
  private void addDoprPLE(String token, Array<PollListEntry> a, Property baseProp)
  {
    BBacnetDeviceObjectPropertyReference dopr = BBacnetDeviceObjectPropertyReference
      .fromString(token);
    if (dopr != null)
    {
      BBacnetObjectIdentifier deviceId = dopr.getDeviceId();
      if (!dopr.isDeviceIdUsed()
        || (deviceId.hashCode() == device().getObjectId().hashCode()))
      {
        a.add(new PollListEntry(dopr.getObjectId(), dopr.getPropertyId(), dopr
          .getPropertyArrayIndex(), device(), this, new MetaDataContext(
          baseProp.getName())));
        propsMap.put(baseProp.getName(), baseProp); // multiple on one ok?
      }
      else
      {
        BBacnetDevice dev = network().doLookupDeviceById(deviceId);
        if (dev != null)
        {
          a.add(new PollListEntry(dopr.getObjectId(),
            dopr.getPropertyId(),
            dopr.getPropertyArrayIndex(),
            dev,
            this,
            new MetaDataContext(baseProp.getName())));
          propsMap.put(baseProp.getName(), baseProp);
        }
        else
          throw new IllegalStateException(
            "Cannot find BACnet device for virtual component metadata:"
              + this + "  ref=" + dopr);
      }
    }
  }

  /**
   * Remove a property from the list of polled properties for this virtual
   * component.
   *
   * @param p
   * @param oldValue
   * @param a
   */
  private void removePolledProperty(Property p, BValue oldValue, Array<PollListEntry> a)
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine(this + ".removePolledProperty(): " + p);
    }

    int index = NOT_USED;
    BFacets f = p.getFacets();
    BInteger pid = (BInteger)f.getFacet(PROPERTY_ID);
    if (pid == null)
      return;

    PollListEntry ple = null;
    BBacnetPoll pollService = (BBacnetPoll)network().getPollService(this);

    // All elements in the virtual array must be removed.
    if (oldValue instanceof BBacnetVirtualArray)
    {
      BBacnetVirtualArray va = (BBacnetVirtualArray)oldValue;
      SlotCursor<Property> sc = va.getProperties();
      sc.next();
      sc.next();
      while (sc.next())
      {
        index = Integer.parseInt(sc.property().getName().substring(7));
        ple = new PollListEntry(objectId, pid.getInt(), index, device(), this);
        a.remove(ple);
        propsMap.remove(String.valueOf((pid.getInt() << 16) | (index & 0xFFFF)));
        pollService.removePLE(this, ple);
      }
    }

    // Non-array properties are fully removed.
    // FIXX: what about metadata?
    else
    {
      BInteger ndx = (BInteger)f.getFacet(INDEX);
      if (ndx != null)
        index = ndx.getInt();
      ple = new PollListEntry(objectId, pid.getInt(), index, device(), this);
      a.remove(ple);
      propsMap.remove(String.valueOf((pid.getInt() << 16) | (index & 0xFFFF)));
      pollService.removePLE(this, ple);
    }
  }

  private void setProp(Property p,
                       byte[] encodedValue,
                       BValue v,
                       BStatusValue sv,
                       int index)
    throws AsnException
  {
    synchronized (asnIn)
    {
      asnIn.setBuffer(encodedValue);

      // Virtual Array
      if (v instanceof BBacnetVirtualArray)
      {
        ((BBacnetVirtualArray)v).readAsn(asnIn, index);
      }

      // Constructed data
      else if (v instanceof BIBacnetDataType)
      {
        BIBacnetDataType obj = (BIBacnetDataType)v;
        obj.readAsn(asnIn);
      }

      // Primitive data.
      else
      {
        v = readAsn(v, sv, p.getFacets(), encodedValue);
        if (sv != null)
          set(p, sv, noWrite);
        else
          set(p, v, noWrite);
      }
    }
  }

  /**
   * Decode metadata that modifies the status of one of the virtual component's
   * main properties. This could be data from another object, or in another
   * device even.
   * <p>
   * When the status is read, it is used to update the status of the main
   * property.
   *
   * @param encodedValue
   * @param cx
   * @param meta
   * @throws AsnException
   */
  private void readMetaData(byte[] encodedValue,
                            Context cx,
                            MetaDataContext meta)
    throws AsnException
  {
    PollListEntry ple = (PollListEntry)cx;
    Property p = getProperty(meta.getPropertyName());
    BStatus metaStatus = null;
    StringBuilder sb = new StringBuilder();
    if (ple.getObjectId().hashCode() != objectId.hashCode())
      sb.append(ple.getObjectId().toString(facetsContext)).append('_');
    sb.append(BBacnetPropertyIdentifier.tag(ple.getPropertyId()));
    String propertyName = sb.toString();
    synchronized (asnIn)
    {
      asnIn.setBuffer(encodedValue);
      metaStatus = readAsnMetaData(encodedValue,
        ple.getObjectId(),
        ple.getPropertyId(),
        propertyName);
    }
    updateStatus(p, ple, metaStatus);
  }

  /**
   * Decode the raw ASN.1 data received as the response to a poll.
   * <p>
   * This is used for the main property.
   *
   * @param val
   * @param sv
   * @param encodedValue
   * @return
   * @throws AsnException
   */
  private BValue readAsn(BValue val,
                         BStatusValue sv,
                         BFacets f,
                         byte[] encodedValue)
    throws AsnException
  {
    BValue v = null;
    synchronized (asnIn)
    {
      int tag = asnIn.peekApplicationTag();
      switch (tag)
      {
        case ASN_NULL:
          v = BBacnetNull.DEFAULT;
          setValueValue(v, sv, null);
          break;

        case ASN_BOOLEAN:
          v = BBoolean.make(asnIn.readBoolean());
          setValueValue(v, sv, null);
          break;

        case ASN_UNSIGNED:
          // FIXX: this does not yet incorporate the new setValueValue() method
          BBacnetUnsigned u = asnIn.readUnsigned();
          v = (BValue)u;

          if (sv != null)
          {
            sv.setStatusNull(false);
            if (sv instanceof BStatusEnum)
            {
//              BInteger pid = (BInteger) f.getFacet(PROPERTY_ID);
// 2007-11-28 cpg commented to b/c of removed method.
//              if ((pid != null)
//                  && BBacnetVirtualGateway.isMsPV(this, pid.getInt()))
//              {
//                BStatusEnum se = (BStatusEnum) sv;
//                BEnumRange r = se.getValue().getRange();
//                se.setValue(r.get(u.getInt()));
//              }
//              else
//              {
//                sv.setValueValue(BDynamicEnum.make(u.getInt()));
//              }
            }
            else
              // sv.setValueValue(v); // would we ever want to just do this?
              sv.setValueValue(BString.make(v.toString()));
          }
          break;

        case ASN_INTEGER:
          v = asnIn.readSigned();
          setValueValue(v, sv, null);
          break;

        case ASN_REAL:
          v = BDouble.make(asnIn.readReal());
          setValueValue(v, sv, null);
          break;

        case ASN_DOUBLE:
          v = BDouble.make(asnIn.readDouble());
          setValueValue(v, sv, null);
          break;

        case ASN_OCTET_STRING:
          v = BBacnetOctetString.make(asnIn.readOctetString());
          setValueValue(v, sv, null);
          break;

        case ASN_CHARACTER_STRING:
          v = BString.make(asnIn.readCharacterString());
          setValueValue(v, sv, null);
          break;

        case ASN_BIT_STRING:
          v = asnIn.readBitString();
          setValueValue(v, sv, null);
          break;

        case ASN_ENUMERATED:
          int en = asnIn.readEnumerated();
          BEnumRange r = (BEnumRange)f.getFacet(BFacets.RANGE);
          if ((val instanceof BIEnum) && (r == null))
            r = (BEnumRange)((BIEnum)val).getEnumFacets().getFacet(
              BFacets.RANGE);
          if (r != null)
            v = r.get(en);
          else
            v = BDynamicEnum.make(en);
          setValueValue(BInteger.make(en), sv, r);
          break;

        case ASN_DATE:
          v = asnIn.readDate();
          setValueValue(v, sv, null);
          break;

        case ASN_TIME:
          v = asnIn.readTime();
          setValueValue(v, sv, null);
          break;

        case ASN_OBJECT_IDENTIFIER:
          v = asnIn.readObjectIdentifier();
          setValueValue(v, sv, null);
          break;

        case ASHRAE_RESERVED_13:
        case ASHRAE_RESERVED_14:
        case ASHRAE_RESERVED_15:
          // set(p, BString.make(ByteArrayUtil.toHexString(encodedValue)),
          // noWrite);
          break;

        default:
          if (log.isLoggable(Level.FINE))
          {
            log.fine(this + ": unexpected tag:" + tag);
          }
          BIBacnetDataType obj = (BIBacnetDataType)val;
          obj.readAsn(asnIn);
          if (obj instanceof BValue)
          {
            v = (BValue)obj;
            setValueValue(v, sv, null);
          }
          break;
      }
    }
    return v;
  }

  /**
   * Set a BStatusValue from the given BValue.
   *
   * @param v
   * @param sv
   * @param r
   */
  private void setValueValue(BValue v, BStatusValue sv, BEnumRange r)
  {
    // sanity
    if (sv == null)
      return;

    // null status
    if ((v == null) || (v instanceof BBacnetNull))
    {
      sv.setStatusNull(true);
      return;
    }
    else
      sv.setStatusNull(false);

    if (sv instanceof BStatusNumeric)
    {
      if (v instanceof BDouble)
        sv.setValueValue(v);
      else if (v instanceof BNumber)
        ((BStatusNumeric)sv).setValue(((BNumber)v).getDouble());
      else
        throw new IllegalArgumentException("Can't setValueValue: v=" + v + " ["
          + v.getType() + "] sv=" + sv + " [baja:StatusNumeric]");
    }
    else if (sv instanceof BStatusBoolean)
    {
      if (v instanceof BBoolean)
        sv.setValueValue(v);
      else if (v instanceof BNumber) // does this catch any cases?
        ((BStatusBoolean)sv).setValue(((BNumber)v).getInt() != 0);
      else
        throw new IllegalArgumentException("Can't setValueValue: v=" + v + " ["
          + v.getType() + "] sv=" + sv + " [baja:StatusBoolean]");
    }
    else if (sv instanceof BStatusEnum)
    {
      if (v instanceof BDynamicEnum)
        sv.setValueValue(v);
      else if (v instanceof BNumber)
        ((BStatusEnum)sv).setValue(BDynamicEnum
          .make(((BNumber)v).getInt(), r));
      else
        throw new IllegalArgumentException("Can't setValueValue: v=" + v + " ["
          + v.getType() + "] sv=" + sv + " [baja:StatusEnum]");
    }
    else if (sv instanceof BStatusString)
    {
      sv.setValueValue(BString.make(v.toString()));
    }
    else
    // should never happen
    {
      sv.setValueValue(v);
    }
  }

  /**
   * Decode ASN.1-encoded metadata used to indicate status to the user. Null and
   * Status_Flags are used to modify bits in the return status. All other values
   * are included in the facets portion.
   *
   * @param encodedValue
   * @param objectId
   * @param propertyId
   * @param propertyName
   * @return a BStatus for use in updating the main property's status.
   * @throws AsnException
   */
  private BStatus readAsnMetaData(byte[] encodedValue,
                                  BBacnetObjectIdentifier objectId,
                                  int propertyId,
                                  String propertyName)
    throws AsnException
  {
    int bits = 0;
    BFacets f = BFacets.NULL;
    synchronized (asnIn)
    {
      int tag = asnIn.peekApplicationTag();
      switch (tag)
      {
        case ASN_NULL:
          bits |= BStatus.NULL;
          break;
        case ASN_BOOLEAN:
          f = BFacets.make(propertyName, BBoolean.make(asnIn.readBoolean()));
          break;
        case ASN_UNSIGNED:
          f = BFacets.make(propertyName,
            BLong.make(asnIn.readUnsignedInteger()));
          break;
        case ASN_INTEGER:
          f = BFacets.make(propertyName, asnIn.readSigned());
          break;
        case ASN_REAL:
          f = BFacets.make(propertyName, asnIn.readFloat());
          break;
        case ASN_DOUBLE:
          f = BFacets.make(propertyName, BDouble.make(asnIn.readDouble()));
          break;
        case ASN_OCTET_STRING:
          f = BFacets.make(propertyName, BString.make(ByteArrayUtil
            .toHexString(asnIn.readOctetString())));
          break;
        case ASN_CHARACTER_STRING:
          f = BFacets.make(propertyName, BString.make(asnIn
            .readCharacterString()));
          break;
        case ASN_BIT_STRING:
          if (propertyId == BBacnetPropertyIdentifier.STATUS_FLAGS)
          {
            bits |= asnIn.readStatusFlags().getBits();
          }
          else
          {
            f = BFacets.make(propertyName, asnIn.readBitString().toString(
              BacnetBitStringUtil.getBitStringTags(objectId.getObjectType(),
                propertyId)));
          }
          break;
        case ASN_ENUMERATED:
          f = BFacets.make(propertyName, BString.make(device().getEnumRange(
            objectId.getObjectType(), propertyId).getTag(
            asnIn.readEnumerated())));
          break;
        case ASN_DATE:
          f = BFacets.make(propertyName, BString.make(asnIn.readDate()
            .toString()));
          break;
        case ASN_TIME:
          f = BFacets.make(propertyName, BString.make(asnIn.readTime()
            .toString()));
          break;
        case ASN_OBJECT_IDENTIFIER:
          f = BFacets.make(propertyName, BString.make(asnIn
            .readObjectIdentifier().toString()));
          break;
        case ASHRAE_RESERVED_13:
        case ASHRAE_RESERVED_14:
        case ASHRAE_RESERVED_15:
          // f = BFacets.make(propertyName,
          // BString.make(ByteArrayUtil.toHexString(encodedValue)));
          break;
        default:
          // BIBacnetDataType obj = (BIBacnetDataType)val;
          // obj.readAsn(asnIn);
          f = BFacets.make(propertyName, BString.make(AsnUtil
            .fromAsn(encodedValue)[0].toString()));
          break;
      }
    }
    return BStatus.make(bits, f);
  }

  private void updateStatus(Property p, PollListEntry ple, BStatus status)
  {
    if (propsStatusMap == null)
      propsStatusMap = new Hashtable<>(); // create main map if needed
    Hashtable<PollListEntry, BStatus> propMap = propsStatusMap.get(p);
    if (propMap == null)
      propsStatusMap.put(p, propMap = new Hashtable<>()); // create prop map if
    // needed
    propMap.put(ple, status);
    updateStatus(p, 0);
  }

  private void updateStatus(Property p, int bits)
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine("updateStatus(" + p.getName() + ", " + bits + ")");
    }

    // compare status from all sources...
    if (propsStatusMap == null)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("no propsStatusMap");
      }
      return;
    }
    if (!(get(p) instanceof BStatusValue))
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("prop not a statusValue");
      }
      return;
    }
    BStatusValue sv = (BStatusValue)get(p).newCopy();
    Hashtable<PollListEntry, BStatus> propMap = propsStatusMap.get(p);
    if (propMap == null)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("no propMap for " + p.getName());
      }
      return;
    }
    Enumeration<BStatus> e = propMap.elements();
    BFacets facets = BFacets.NULL;
    while (e.hasMoreElements())
    {
      BStatus s = e.nextElement();
      bits |= s.getBits();
      facets = BFacets.make(facets, s.getFacets());
    }
    sv.setStatus(BStatus.make(bits, facets));
    set(p, sv, noWrite);
  }

  ////////////////////////////////////////////////////////////////
  // Inner Class: Write
  ////////////////////////////////////////////////////////////////

  class Write
    implements Runnable
  {
    Write(int propertyId, int propertyArrayIndex, byte[] ev, int pri)
    {
      if (propertyId == BBacnetPropertyIdentifier.PRIORITY_ARRAY)
      {
        this.propertyId = BBacnetPropertyIdentifier.PRESENT_VALUE;
        this.priority = propertyArrayIndex;
      }
      else
      {
        this.propertyId = propertyId;
        this.propertyArrayIndex = propertyArrayIndex;
        this.priority = pri;
      }
      this.ev = ev;
    }

    public void run()
    {
      try
      {
        network().getBacnetComm().writeProperty(device().getAddress(),
          objectId, propertyId, propertyArrayIndex, ev, priority);
      }
      catch (TransactionException e)
      {
        device().ping();
        log.warning("TransactionException writing "
          + BBacnetPropertyIdentifier.tag(propertyId) + " in " + this);
      }
      catch (BacnetException e)
      {
        log.log(Level.SEVERE, "BacnetException writing "
          + BBacnetPropertyIdentifier.tag(propertyId) + " in " + this, e);
      }
    }

    int propertyId;

    int propertyArrayIndex = NOT_USED;

    byte[] ev = null;

    int priority = NOT_USED;
  }

  ////////////////////////////////////////////////////////////////
  // Attributes
  ////////////////////////////////////////////////////////////////

  private BBacnetObjectIdentifier objectId = BBacnetObjectIdentifier.DEFAULT;

  private String tpName = "defaultPolicy";

  private BBacnetTuningPolicy cachedPolicy = null;

  private int writePriority = -1;

  private PollListEntry[] ples = null;

  private Hashtable<String, Property> propsMap = new Hashtable<>();

  private Hashtable<Property, Hashtable<PollListEntry, BStatus>> propsStatusMap = null; // Property keys, HashMap values

  private boolean isPollSubscribed = false;

  private static AsnInputStream asnIn = new AsnInputStream();

  private static Logger log = Logger.getLogger("bacnet.virtual");

  static final String USE_FACETS = "useFacets";

  static final String PROPERTY_ID = "propertyId";

  public static final String INDEX = "index";

  static final String POLICY_DEF = "policy=";

  static final int POLICY_DEF_LEN = 7;

  static final String PRIORITY_DEF = "priority=";

  static final int PRIORITY_DEF_LEN = 9;

  static final String STATUS_TAG = "status=";

  static final int STATUS_TAG_LEN = 7;

  static final String STATUS_SOURCE_FACET = "statusSrc";
}
