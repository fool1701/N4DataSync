/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.virtual;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetArray;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetDeviceObjectPropertyReference;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetEventState;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.enums.BBacnetReliability;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.point.BBacnetTuningPolicy;
import javax.baja.bacnet.util.BIBacnetPollable;
import javax.baja.bacnet.util.GrandchildChangedContext;
import javax.baja.bacnet.util.MetaDataContext;
import javax.baja.bacnet.util.PollListEntry;
import javax.baja.bacnet.util.PropertyInfo;
import javax.baja.driver.util.BPollFrequency;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;
import javax.baja.virtual.BVirtualComponent;

import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.stack.BBacnetPoll;
import com.tridium.bacnet.stack.transport.TransactionException;

/**
 * BBacnetVirtualProperty is the virtual representation of one
 * property of a BBacnetVirtualObject.  It is responsible for
 * managing its own poll subscription.  The actual property
 * value is added as a dynamic slot called 'value'.  The semantics
 * of the proxy point framework, e.g., readOk(), readFail(), etc.
 * are implemented here as appropriate.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 05 Dec 2007
 * @since NiagaraAX 3.3
 */
@NiagaraType
@NiagaraProperty(
  name = "propertyId",
  type = "int",
  defaultValue = "BBacnetPropertyIdentifier.PRESENT_VALUE",
  flags = Flags.HIDDEN | Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.stale",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "useFacets",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.HIDDEN | Flags.TRANSIENT | Flags.READONLY
)
public class BBacnetVirtualProperty
  extends BVirtualComponent
  implements BacnetConst, BIBacnetPollable, BIStatus
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.virtual.BBacnetVirtualProperty(2633896125)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "propertyId"

  /**
   * Slot for the {@code propertyId} property.
   * @see #getPropertyId
   * @see #setPropertyId
   */
  public static final Property propertyId = newProperty(Flags.HIDDEN | Flags.TRANSIENT | Flags.READONLY, BBacnetPropertyIdentifier.PRESENT_VALUE, null);

  /**
   * Get the {@code propertyId} property.
   * @see #propertyId
   */
  public int getPropertyId() { return getInt(propertyId); }

  /**
   * Set the {@code propertyId} property.
   * @see #propertyId
   */
  public void setPropertyId(int v) { setInt(propertyId, v, null); }

  //endregion Property "propertyId"

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.TRANSIENT | Flags.READONLY, BStatus.stale, null);

  /**
   * Get the {@code status} property.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "useFacets"

  /**
   * Slot for the {@code useFacets} property.
   * @see #getUseFacets
   * @see #setUseFacets
   */
  public static final Property useFacets = newProperty(Flags.HIDDEN | Flags.TRANSIENT | Flags.READONLY, false, null);

  /**
   * Get the {@code useFacets} property.
   * @see #useFacets
   */
  public boolean getUseFacets() { return getBoolean(useFacets); }

  /**
   * Set the {@code useFacets} property.
   * @see #useFacets
   */
  public void setUseFacets(boolean v) { setBoolean(useFacets, v, null); }

  //endregion Property "useFacets"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetVirtualProperty.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BBacnetVirtualProperty()
  {
  }

  /**
   * Constructor used by BBacnetVirtualGateway.
   *
   * @param propertyId
   * @param v          initial value
   * @param readFault  null if ok, or a string describing the initial read failure
   * @param useFacets  whether the virtual object's facets apply for this property
   */
  public BBacnetVirtualProperty(int propertyId,
                                BValue v,
                                String readFault,
                                boolean useFacets)
  {
    setPropertyId(propertyId);
    this.readFault = readFault;
    setUseFacets(useFacets);
    setValue(v);
//    setValue(v, ctorCx);
  }


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Started.
   */
  public void started()
    throws Exception
  {
    super.started();
    kidSubs = new ArrayList<>();
    updateStatus();

    // Add the dynamic write action.
    if (getPropertyId() == BBacnetPropertyIdentifier.PRESENT_VALUE)
    {
      if (object().getPrioritizedPoint())
        add(WRITE_ACTION_NAME, new BVirtualPropertyWrite());
      else
        add(WRITE_ACTION_NAME, new BVirtualPropertyWrite());
    }
    else
      add(WRITE_ACTION_NAME, new BVirtualPropertyWrite());

    // Add array index for writing if necessary.
    setArrayIndex();
  }

  /**
   * Changed.
   */
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (cx == noWrite) return;
    if (!isRunning()) return;
    if (p.equals(status)) return;
    if (log.isLoggable(Level.FINE))
    {
      log.fine("changed(" + p + ", " + cx + ") on " + getName() + " in " + object());
    }
    if (p.getName().equals(VALUE))
    {
      byte[] encodedValue = null;
      int index = NOT_USED;
      int priority = NOT_USED;
      if (cx instanceof GrandchildChangedContext)
      {
        GrandchildChangedContext gccCx = (GrandchildChangedContext)cx;
        index = gccCx.getArrayIndex();
        encodedValue = gccCx.getEncodedValue();
      }
      else
      {
        encodedValue = encodeValue(getValue());

        if (usePriority())
          priority = object().getWritePriority();
      }

      write(index, encodedValue, priority);
    }
  }

  /**
   * Subscribed.
   * Subscribe to the poll scheduler for regular updates.
   */
  public void subscribed()
  {
    pollSubscribe();
  }

  /**
   * Unsubscribed.
   * Clear the poll scheduler subscription and set the stale flag.
   */
  public void unsubscribed()
  {
    stale(true);
    if (kidSubs.size() == 0) pollUnsubscribe();
    ples = null;
  }

  /**
   * BBacnetVirtualProperty must reside under a BBacnetVirtualObject.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BBacnetVirtualObject;
  }

  /**
   * Overrides the superclass method to remove the restriction
   * to virtual components to allow special BACnet datatypes.
   */
  public boolean isChildLegal(BComponent child)
  {
    return true;
  }

  public String toString(Context cx)
  {
    loadSlots();
    StringBuilder sb = new StringBuilder();
    if (getStatus().isNull())
      sb.append('-');
    else
      sb.append(valueToString(cx));

    sb.append(' ').append(getStatus().toString(cx));
    return sb.toString();
  }

  public String debugString(Context cx)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getName()).append('=').append(getValue())
      .append(':').append(getStatus());
    if (getValue() instanceof BBacnetArray)
    {
      BBacnetArray a = (BBacnetArray)getValue();
      int len = a.getSize();
      if (len < 0) sb.append(" size UNKNOWN");
      for (int i = 0; i < len; i++)
      {
        sb.append(" [").append(i).append("]=").append(a.getElement(i));
      }
    }
    return sb.toString();
  }

  public BFacets getSlotFacets(Slot s)
  {
    if (s.getName().equals(VALUE) && getUseFacets())
    {
      if (object() != null)
        return object().getFacets();
    }
    return s.getFacets();
  }


////////////////////////////////////////////////////////////////
// Value
////////////////////////////////////////////////////////////////

//  private Context ctorCx = new BasicContext()
//  {
//    public boolean equals(Object obj) { return this == obj; }
//    public String toString() { return "BVP:ctorCx"; }
//  };

  /**
   * Get the virtual property's value.<p>
   * NOTE: This may be null, if the value property has not been added yet.
   *
   * @return the property value, in its native datatype.
   */
  public BValue getValue()
  {
    return get(VALUE);
  }

  public void setValue(BValue v)
  {
    setValue(v, null);
  }

  public void setValue(BValue v, Context cx)
  {
//    if (!isSubscribed() && !ctorCx.equals(cx)) return;
    Property prop = getProperty(VALUE);
    if (prop == null)
      add(VALUE, v, Flags.TRANSIENT, cx);
    else
      set(prop, v, cx);
  }

  protected String valueToString(Context cx)
  {
    BValue v = getValue();
    if ((v != null) && (object() != null))
      return v.toString(getUseFacets() ? object().getFacets() : cx);
    return "null";
  }


////////////////////////////////////////////////////////////////
// Array Index
////////////////////////////////////////////////////////////////

  /**
   * Get the propertyArrayIndex that shall be used when writing this property
   * through the VirtualPropertyWrite action.
   *
   * @return the value of the 'propertyArrayIndex' slot if it exists, or the
   * default array index.
   */
  protected int getArrayIndex()
  {
    Property p = getProperty(PROPERTY_ARRAY_INDEX);
    if (p != null)
      return getInt(p);
    return DEFAULT_ARRAY_INDEX;
  }

  /**
   * Set the propertyArrayIndex slot based on this virtual property's
   * slot name.  The syntax is "propertyName;index=N/value", where N is the integer
   * array index to be used when writing this single-element array property.
   * Note that the value must be used here too.
   */
  private void setArrayIndex()
  {
    String name = SlotPath.unescape(getName());
    int scndx = name.indexOf(";");
    if (scndx > 0)
    {
      // tokenize the full slot name
      StringTokenizer st = new StringTokenizer(name, ";");
      st.nextToken(); // skip primary property
      while (st.hasMoreTokens())
      {
        String token = st.nextToken();
        if (token.startsWith(INDEX_TAG))
        {
          token = token.substring(INDEX_TAG_LEN);
          add(PROPERTY_ARRAY_INDEX, BInteger.make(Integer.parseInt(token)));
          break;
        }
      }
    }
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Access to the parent virtual object.
   */
  public BBacnetVirtualObject object()
  {
    return (BBacnetVirtualObject)getParent();
  }

  /**
   * Called on successful read.  This clears the stale flag,
   * sets the value, clears read and write faults, and
   * updates the status.
   *
   * @param v the value read from the remote device.
   */
  public void readOk(BValue v)
  {
    setValue(v, noWrite);
    stale(false);
    readFault = null;
    writeFault = null; // clear write faults too
    updateStatus();
  }

  /**
   * Called on successful write.  This clears the write
   * fault and updates the status.
   */
  public void writeOk()
  {
    writeFault = null;
    updateStatus();
  }

  /**
   * Called on write failure.  This sets the write fault
   * and updates the status.
   *
   * @param failureMsg
   */
  public void writeFail(String failureMsg)
  {
    writeFault = failureMsg;
    updateStatus();
  }

  /**
   * Update the property status with no flags or facets.
   */
  public void updateStatus()
  {
    updateStatus(0, null);
    checkSubscription();
  }

  /**
   * Update the virtual property's status.  This merges the status
   * from the parent device with any local status sources, such as
   * readFault or stale().  In addition, metadata status information
   * is included.  The metadata information is OR'd with the other
   * status information, so if any of the various status sources
   * indicate a condition, then it is set for the property's status.
   *
   * @param metaBits
   * @param metaFacets
   */
  public void updateStatus(int metaBits, BFacets metaFacets)
  {
    int newStatus = getStatus().getBits();
    BStatus ds = getDeviceStatus();

    if (ds.isDisabled())
      newStatus |= BStatus.DISABLED;
    else
      newStatus &= ~BStatus.DISABLED;

    if (ds.isDown())
      newStatus |= BStatus.DOWN;
    else
      newStatus &= ~BStatus.DOWN;

    if (fatalFault() || readFault != null || writeFault != null || ds.isFault())
      newStatus |= BStatus.FAULT;
    else
      newStatus &= ~BStatus.FAULT;

    // stale processing...
    if (stale())
      newStatus |= BStatus.STALE;
    else
      newStatus &= ~BStatus.STALE;

    if (metaFacets != null)
    {
      // alarm is only set from metadata
      if ((metaBits & BStatus.ALARM) != 0)
        newStatus |= BStatus.ALARM;
      else
        newStatus &= ~BStatus.ALARM;
    }
    // faultCause?

    // metabits
    newStatus |= metaBits;

    if ((oldStatus == newStatus) && (metaFacets == null)) return;

    // facets
    BFacets f = BFacets.NULL;

    // check if facets should be applied (should this be based on readFault?)
    if ((newStatus & INVALID_MASK) == 0)
      f = BFacets.make(getStatus().getFacets(), metaFacets);

    setStatus(BStatus.make(newStatus, f));

    oldStatus = newStatus;
  }

  /**
   * Called when a component type value property is subscribed,
   * to alert this property to register for polling.
   *
   * @param kid
   */
  public void childSubscribed(BComponent kid)
  {
    kidSubs.add(kid);
    if (kidSubs.size() == 1)
      pollSubscribe();
  }

  /**
   * Called when a component type value property is unsubscribed,
   * to alert this property to unregister from the poll scheduler.
   *
   * @param kid
   */
  public void childUnsubscribed(BComponent kid)
  {
    kidSubs.remove(kid);
    if ((kidSubs.size() == 0) && !isSubscribed())
      pollUnsubscribe();
  }

  /**
   * Should the write priority be included in a write?
   * Currently only true if the propertyId is Present_Value.
   *
   * @return true if priority is needed.
   */
  protected boolean usePriority()
  {
    return getPropertyId() == BBacnetPropertyIdentifier.PRESENT_VALUE;
  }

  /**
   * Should writes performed through the BVirtualPropertyWrite dynamic action
   * generate separate audit log events?
   *
   * @return false by default, subclasses may override.
   */
  protected boolean auditWrites()
  {
    return ((BBacnetVirtualGateway)getVirtualGateway()).getAuditWrites();
  }

  /**
   * Get the name to be used for this virtual property in the AuditEvent
   * for writes to the remote device.
   *
   * @return auditName
   */
  protected String getAuditName()
  {
    return this.device().getName() + "/" + this.object().getName() + ":" + getName();
  }

  /**
   * Get the most recent read fault or null if last read was successful.
   *
   * @return readFault
   */
  public String getReadFault()
  {
    return readFault;
  }

  /**
   * Get the most recent write fault or null if last write was successful.
   *
   * @return writeFault
   */
  public String getWriteFault()
  {
    return writeFault;
  }


////////////////////////////////////////////////////////////////
// BIBacnetPollable
////////////////////////////////////////////////////////////////

  /**
   * Get the component's configured poll frequency.
   */
  public BPollFrequency getPollFrequency()
  {
    BBacnetVirtualObject o = object();
    if (o != null)
    {
      BBacnetTuningPolicy policy = o.getPolicy();
      if (policy != null)
        return policy.getPollFrequency();
    }
    return BPollFrequency.normal;
  }

  /**
   * Get the containing device object which will poll this object.
   *
   * @return the containing BBacnetDevice
   */
  public BBacnetDevice device()
  {
    if (getVirtualGateway() == null) return null;
    return ((BBacnetVirtualGateway)getVirtualGateway()).device();
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
   * @return true if a poll was attempted to this node, or
   * false if the poll was skipped due to device down, out of service, etc.
   * @deprecated As of 3.2
   */
  @Deprecated
  public boolean poll()
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Indicate a failure polling this object.
   *
   * @param failureMsg
   */
  public void readFail(String failureMsg)
  {
    readFault = failureMsg;
    oldStatus = -1; // from BProxyExt - forces status update
    updateStatus();
  }

  /**
   * Normalize the encoded data into the pollable's data structure.
   *
   * @param encodedValue
   * @param status
   * @param cx           must be a PollListEntry.
   */
  public void fromEncodedValue(byte[] encodedValue, BStatus status, Context cx)
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine("fromEncodedValue() on " + object() + "." + getName() + ": ev=" + ByteArrayUtil.toHexString(encodedValue));
    }

    try
    {
//      PollListEntry ple = (PollListEntry)cx;
      Context baseCx = cx.getBase();

      // status/metadata information
      if (baseCx instanceof MetaDataContext)
      {
        readMetaData(encodedValue, cx, (MetaDataContext)baseCx);
        return;
      }

//      ple.setDataSize(encodedValue.length);

      synchronized (asnIn)
      {
        asnIn.setBuffer(encodedValue);

        BValue v = BacnetVirtualUtil.readValue(asnIn, getValue());

        readOk(v);
      }
    }
    catch (Exception e)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "Exception decoding value for " + getName() + ":" + e + " ev=" + ByteArrayUtil.toHexString(encodedValue), e);
      }
      readFail(e.toString());
    }
  }

  /**
   * Get the list of poll list entries for this pollable.
   * The first entry for points must be the configured property.
   * If any other properties are called for by the ord syntax,
   * they must be added here as well, and they will be included in
   * the status of the property.  The other properties are
   * specified in the form of a BACnetDeviceObjectPropertyReference.
   *
   * @return the list of poll list entries.
   */
  public PollListEntry[] getPollListEntries()
  {
    String name = SlotPath.unescape(getName());
    if (ples == null)
    {
      // add primary configured property
      Array<PollListEntry> a = new Array<>(PollListEntry.class);
      int ndx = NOT_USED;
      // check for status properties
      int scndx = name.indexOf(";");
      if (scndx > 0)
      {
        // tokenize the virtualPathName
        StringTokenizer st = new StringTokenizer(name, ";");
        st.nextToken(); // skip primary property

        // Scan for other polled properties.
        while (st.hasMoreTokens())
        {
          String token = st.nextToken();
          if (token.startsWith(STATUS_TAG))
          {
            BBacnetDeviceObjectPropertyReference dopr = createObjectPropRef(token);
            if (dopr != null)
            {
              BBacnetObjectIdentifier deviceId = dopr.getDeviceId();
              if (!dopr.isDeviceIdUsed()
                || (deviceId.hashCode() == device().getObjectId().hashCode()))
              {
                // Add a poll reference within this remote device.
                a.add(createPLE(dopr, device(), token));
              }
              else
              {
                BBacnetDevice dev = network().doLookupDeviceById(deviceId);
                if (dev != null)
                {
                  // Add a poll reference to an external device.
                  a.add(createPLE(dopr, dev, token));
                }
                else
                  throw new IllegalStateException(
                    "Cannot find BACnet device for virtual component metadata:"
                      + this + "  ref=" + dopr);
              }
            }
          }
          else if (token.startsWith(INDEX_TAG))
          {
            String tokenValue = token.substring(INDEX_TAG_LEN);
            try
            {
              ndx = Integer.parseInt(tokenValue);
              BValue v = null;
              if ((v = getValue()) instanceof BBacnetArray)
              {
                BBacnetArray ba = (BBacnetArray)v;
                if (ndx == 0)
                  setValue(BInteger.make(-1), noWrite);
                else
                  setValue((BValue)ba.getArrayTypeSpec().getResolvedType().getInstance(), noWrite);
              }
            }
            catch (NumberFormatException nfe)
            {
              if (log.isLoggable(Level.WARNING))
                log.log(Level.WARNING, "NumberFormatException for " +
                  "VirtualBacnetProperty Index: " + tokenValue, nfe);
            }
          }
        }
      }

      a.add(new PollListEntry(object().getObjectId(),
        getPropertyId(),
        ndx,
        device(),
        this));

      ples = a.trim();
    }
    return ples;
  }

  private PollListEntry createPLE(BBacnetDeviceObjectPropertyReference dopr,
                                  BBacnetDevice device, String token)
  {
    return new PollListEntry(dopr.getObjectId(),
      dopr.getPropertyId(),
      dopr.getPropertyArrayIndex(),
      device,
      this,
      new MetaDataContext(token));
  }

  private BBacnetDeviceObjectPropertyReference createObjectPropRef(String token)
  {
    //token = 'status='
    BBacnetDeviceObjectPropertyReference dopr = null;
    if (token == null || token.length() <= STATUS_TAG_LEN)
    {
      //Default to the status flags of the point in question
      dopr = new BBacnetDeviceObjectPropertyReference();
      dopr.setObjectId(object().getObjectId());
      dopr.setPropertyId(BBacnetPropertyIdentifier.STATUS_FLAGS);
    }
    else
    {
      token = token.substring(STATUS_TAG_LEN);
      dopr = BBacnetDeviceObjectPropertyReference.fromString(token);
    }
    return dopr;
  }

////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  /**
   * Issue a write to this virtual property.
   *
   * @param propertyArrayIndex
   * @param encodedValue
   * @param priority
   */
  void write(int propertyArrayIndex, byte[] encodedValue, int priority)
  {
    network().postWrite(new Write(getPropertyId(),
      propertyArrayIndex,
      encodedValue,
      priority));
  }

  /**
   * Issue a write to this virtual property.  Convenience for BValues.
   *
   * @param propertyArrayIndex
   * @param v
   * @param priority
   */
  void write(int propertyArrayIndex, BValue v, int priority)
  {
    byte[] encodedValue = encodeValue(v);
    write(propertyArrayIndex, encodedValue, priority);
  }

  /**
   * Get the status of the containing device.
   *
   * @return the status of the device
   */
  protected BStatus getDeviceStatus()
  {
    if (device() == null) return BStatus.nullStatus;
    return device().getStatus();
  }

  protected BBacnetNetwork network()
  {
    return ((BBacnetVirtualGateway)getVirtualGateway()).network();
  }

  private void readMetaData(byte[] encodedValue, Context pleCx, MetaDataContext metaCx)
    throws AsnException
  {
    // read ev to get status info
    PollListEntry ple = (PollListEntry)pleCx;
    BBacnetObjectIdentifier objectId = ple.getObjectId();
    String key = "";
    if (!object().getObjectId().equals(objectId))
      key = objectId.toShortString() + "_";
    int propId = ple.getPropertyId();
    key += BBacnetPropertyIdentifier.tag(propId);
//    PropertyInfo pi = ObjectTypeList.getPropertyInfo(ple.getObjectId().getObjectType(), propId);
    PropertyInfo pi = device().getPropertyInfo(ple.getObjectId().getObjectType(), propId);
    BValue metaData = AsnUtil.asnToValue(pi, encodedValue);

    // merge with existing status -> updateStatus() with facet data...
    if (propId == BBacnetPropertyIdentifier.STATUS_FLAGS)
    {
      int ibits = 0;
      BBacnetBitString bs = (BBacnetBitString)metaData;
      boolean[] bits = bs.getBits();
      if (bits[0]) ibits |= BStatus.ALARM;
      if (bits[1]) ibits |= BStatus.FAULT;
      if (bits[2]) ibits |= BStatus.OVERRIDDEN;
      if (bits[3]) ibits |= BStatus.DISABLED;
      updateStatus(ibits, BFacets.make(key, BString.make(metaData.toString())));
    }
    else if (propId == BBacnetPropertyIdentifier.EVENT_STATE)
    {
      BEnum e = (BEnum)metaData;
      int ibits = 0;
      if (BBacnetEventState.isFault(e))
        ibits |= BStatus.FAULT;
      else if (BBacnetEventState.isOffnormal(e))
        ibits |= BStatus.ALARM;
      updateStatus(ibits, BFacets.make(key, BString.make(metaData.toString())));
    }
    else if (propId == BBacnetPropertyIdentifier.RELIABILITY)
    {
      BEnum e = (BEnum)metaData;
      int ibits = 0;
      if (e.getOrdinal() != BBacnetReliability.NO_FAULT_DETECTED)
        ibits |= BStatus.FAULT;
      updateStatus(ibits, BFacets.make(key, BString.make(metaData.toString())));
    }
    else if (propId == BBacnetPropertyIdentifier.OUT_OF_SERVICE)
    {
      boolean oos = ((BBoolean)metaData).getBoolean();
      if (oos)
        updateStatus(BStatus.DISABLED, BFacets.make("outOfService", BBoolean.TRUE));
      else
        updateStatus(0, BFacets.make(key, BBoolean.FALSE));
    }
    else
    {
      updateStatus(0, BFacets.make(key, BString.make(metaData.toString())));
    }
  }

  protected void pollSubscribe()
  {
    synchronized (SUBSCRIPTION_LOCK)
    {
      if (!pollSubscribed)
      {
        if (device().isAddressValid())
        {
          resolvingAddress = false;

          BBacnetPoll pollService = (BBacnetPoll)network().getPollService(this);
          pollService.subscribe(this);
          pollSubscribed = true;
        }
        else
        {
          resolvingAddress = true;
          device().checkAddress();
        }
      }
    }
  }

  protected void pollUnsubscribe()
  {
    synchronized (SUBSCRIPTION_LOCK)
    {
      if (pollSubscribed)
      {
        BBacnetPoll pollService = (BBacnetPoll)network().getPollService(this);
        pollService.unsubscribe(this);
        pollSubscribed = false;
      }
      //Do not subscribe if/when a MAC address is resolved.
      resolvingAddress = false;
    }
  }

  protected void checkSubscription()
  {
    synchronized (SUBSCRIPTION_LOCK)
    {
      //If resolvingAddress is true then a subscription was desired,
      //when a valid mac address was unavailable.
      if (resolvingAddress && device().isAddressValid())
        pollSubscribe();
    }
  }

  byte[] encodeValue(BValue v)
  {
    byte[] ev = null;
//    PropertyInfo pi = ObjectTypeList.getPropertyInfo(object().getObjectId().getObjectType(),
//        getPropertyId());
    PropertyInfo pi = device().getPropertyInfo(object().getObjectId().getObjectType(),
      getPropertyId());
    if (pi != null)
      ev = AsnUtil.toAsn(pi.getAsnType(), v);
    else
      ev = AsnUtil.toAsn(v);
    return ev;
  }


////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetVirtualProperty", 2);
    out.prop("oldStatus", oldStatus);
    out.prop("flags", flags);
    out.prop("readFault", readFault);
    out.prop("writeFault", writeFault);
    if (ples != null)
    {
      out.prop("PollListEntries", ples.length);
      for (int i = 0; i < ples.length; i++)
        out.prop("  " + i, ples[i]);
    }
    out.prop("pollSubscribed", pollSubscribed);
    if (kidSubs != null)
    {
      out.prop("kidSubs", kidSubs.size());
      int cnt = 0;
      Iterator<BComponent> i = kidSubs.iterator();
      while (i.hasNext())
        out.prop("  " + (cnt++), i.next());
    }
    out.endProps();
  }


////////////////////////////////////////////////////////////////
// Inner Class: Write
////////////////////////////////////////////////////////////////

  class Write
    implements Runnable
  {
    Write(int propId,
          int propertyArrayIndex,
          byte[] ev,
          int pri)
    {
      if (propId == BBacnetPropertyIdentifier.PRIORITY_ARRAY)
      {
        this.propId = BBacnetPropertyIdentifier.PRESENT_VALUE;
        this.priority = propertyArrayIndex;
      }
      else
      {
        this.propId = propId;
        this.propertyArrayIndex = propertyArrayIndex;
        this.priority = pri;
      }
      this.encodedValue = ev;
    }

    public void run()
    {
      try
      {
        network().getBacnetComm().writeProperty(device().getAddress(),
          object().getObjectId(), propId, propertyArrayIndex, encodedValue, priority);
        writeOk();
      }
      catch (TransactionException e)
      {
        device().ping();
        log.warning("TransactionException writing "
          + BBacnetPropertyIdentifier.tag(propId) + " in " + this);
        writeFail(e.toString());
      }
      catch (BacnetException e)
      {
        log.log(Level.SEVERE, "BacnetException writing "
          + BBacnetPropertyIdentifier.tag(propId) + " in " + this, e);
        writeFail(e.toString());
      }
    }

    int propId;
    int propertyArrayIndex = NOT_USED;
    byte[] encodedValue = null;
    int priority = NOT_USED;
  }


////////////////////////////////////////////////////////////////
// Flags
////////////////////////////////////////////////////////////////

  private static final int FATAL_FAULT = 0x01;
  private static final int STALE = 0x02;

  private boolean fatalFault()
  {
    return (flags & FATAL_FAULT) != 0;
  }
//  private void fatalFault(boolean b) { if (b) flags |= FATAL_FAULT; else flags &= ~FATAL_FAULT; }

  private boolean stale()
  {
    return (flags & STALE) != 0;
  }

  private void stale(boolean b)
  {
    if (b) flags |= STALE;
    else flags &= ~STALE;
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  protected static final Lexicon lex = Lexicon.make("bacnet");
  static Logger log = Logger.getLogger("bacnet.virtual");
  public static final String VALUE = "value";
  protected static final String WRITE_ACTION_NAME = SlotPath.escape(lex.getText("BacnetVirtualProperty.set"));
  private static final String PROPERTY_ARRAY_INDEX = "propertyArrayIndex";
  static final String STATUS_TAG = "status";
  static final int STATUS_TAG_LEN = 7;
  static final String INDEX_TAG = "index=";
  static final int INDEX_TAG_LEN = 6;

  /**
   * Mask for status bits indicating a problem reading.
   */
  private static final int INVALID_MASK = BStatus.FAULT | BStatus.DOWN | BStatus.STALE;


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private PollListEntry[] ples = null;
  private int oldStatus = BStatus.STALE;
  private int flags = STALE;
  private String readFault = null;
  private String writeFault = null;
  private boolean pollSubscribed = false;
  private boolean resolvingAddress = false;

  private ArrayList<BComponent> kidSubs;
  private static AsnInputStream asnIn = new AsnInputStream();
  protected int DEFAULT_ARRAY_INDEX = 1;

  private Object SUBSCRIPTION_LOCK = new Object();
}
