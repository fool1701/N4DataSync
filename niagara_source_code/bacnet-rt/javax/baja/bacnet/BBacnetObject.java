/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet;

import java.io.ByteArrayOutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.config.BBacnetConfigDeviceExt;
import javax.baja.bacnet.config.BBacnetConfigFolder;
import javax.baja.bacnet.datatypes.BBacnetAddress;
import javax.baja.bacnet.datatypes.BBacnetArray;
import javax.baja.bacnet.datatypes.BBacnetListOf;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetObjectPropertyReference;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.enums.BExtensibleEnumList;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ErrorException;
import javax.baja.bacnet.io.PropertyReference;
import javax.baja.bacnet.util.BIBacnetPollable;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.bacnet.util.PollListEntry;
import javax.baja.bacnet.util.PropertyInfo;
import javax.baja.data.BIDataValue;
import javax.baja.driver.loadable.BDownloadParameters;
import javax.baja.driver.loadable.BLoadable;
import javax.baja.driver.loadable.BUploadParameters;
import javax.baja.driver.util.BPollFrequency;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.registry.TypeInfo;
import javax.baja.space.BComponentSpace;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInteger;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTypeSpec;
import javax.baja.util.Lexicon;

import com.tridium.bacnet.BacUtil;
import com.tridium.bacnet.asn.AsnConst;
import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NBacnetPropertyReference;
import com.tridium.bacnet.asn.NReadAccessResult;
import com.tridium.bacnet.asn.NReadAccessSpec;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.asn.NWriteAccessSpec;
import com.tridium.bacnet.stack.BBacnetPoll;
import com.tridium.bacnet.stack.BBacnetStack;
import com.tridium.bacnet.stack.client.BBacnetClientLayer;
import com.tridium.bacnet.stack.transport.TransactionException;

/**
 * @author Craig Gemmill
 * @version $Revision: 17$ $Date: 12/17/01 9:14:09 AM$
 * @creation 20 Jul 00
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "pollFrequency",
  type = "BPollFrequency",
  defaultValue = "BPollFrequency.normal"
)
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.DEFAULT",
  flags = Flags.SUMMARY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER)")
)
@NiagaraProperty(
  name = "objectName",
  type = "String",
  defaultValue = "",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_NAME, ASN_CHARACTER_STRING)")
)
@NiagaraProperty(
  name = "objectType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(0, BEnumRange.make(BBacnetObjectType.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED)")
)
@NiagaraAction(
  name = "download",
  parameterType = "BDownloadParameters",
  defaultValue = "new BDownloadParameters()",
  flags = Flags.ASYNC | Flags.HIDDEN,
  override = true
)
@NiagaraAction(
  name = "readBacnetProperty",
  parameterType = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetPropertyIdentifier.presentValue)",
  returnType = "BValue",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "writeBacnetProperty",
  parameterType = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetPropertyIdentifier.presentValue)",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "uploadRequiredProperties",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "uploadOptionalProperties",
  flags = Flags.HIDDEN
)
public class BBacnetObject
  extends BLoadable
  implements BacnetConst,
             BIBacnetPollable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.BBacnetObject(1117206849)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "pollFrequency"

  /**
   * Slot for the {@code pollFrequency} property.
   * @see #getPollFrequency
   * @see #setPollFrequency
   */
  public static final Property pollFrequency = newProperty(0, BPollFrequency.normal, null);

  /**
   * Get the {@code pollFrequency} property.
   * @see #pollFrequency
   */
  public BPollFrequency getPollFrequency() { return (BPollFrequency)get(pollFrequency); }

  /**
   * Set the {@code pollFrequency} property.
   * @see #pollFrequency
   */
  public void setPollFrequency(BPollFrequency v) { set(pollFrequency, v, null); }

  //endregion Property "pollFrequency"

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.TRANSIENT | Flags.READONLY, BStatus.ok, null);

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

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.TRANSIENT | Flags.READONLY, "", null);

  /**
   * Get the {@code faultCause} property.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.SUMMARY, BBacnetObjectIdentifier.DEFAULT, makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER));

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

  //region Property "objectName"

  /**
   * Slot for the {@code objectName} property.
   * @see #getObjectName
   * @see #setObjectName
   */
  public static final Property objectName = newProperty(0, "", makeFacets(BBacnetPropertyIdentifier.OBJECT_NAME, ASN_CHARACTER_STRING));

  /**
   * Get the {@code objectName} property.
   * @see #objectName
   */
  public String getObjectName() { return getString(objectName); }

  /**
   * Set the {@code objectName} property.
   * @see #objectName
   */
  public void setObjectName(String v) { setString(objectName, v, null); }

  //endregion Property "objectName"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, BDynamicEnum.make(0, BEnumRange.make(BBacnetObjectType.TYPE)), makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED));

  /**
   * Get the {@code objectType} property.
   * @see #objectType
   */
  public BEnum getObjectType() { return (BEnum)get(objectType); }

  /**
   * Set the {@code objectType} property.
   * @see #objectType
   */
  public void setObjectType(BEnum v) { set(objectType, v, null); }

  //endregion Property "objectType"

  //region Action "download"

  /**
   * Slot for the {@code download} action.
   * @see #download(BDownloadParameters parameter)
   */
  public static final Action download = newAction(Flags.ASYNC | Flags.HIDDEN, new BDownloadParameters(), null);

  //endregion Action "download"

  //region Action "readBacnetProperty"

  /**
   * Slot for the {@code readBacnetProperty} action.
   * @see #readBacnetProperty(BEnum parameter)
   */
  public static final Action readBacnetProperty = newAction(Flags.HIDDEN, BDynamicEnum.make(BBacnetPropertyIdentifier.presentValue), null);

  /**
   * Invoke the {@code readBacnetProperty} action.
   * @see #readBacnetProperty
   */
  public BValue readBacnetProperty(BEnum parameter) { return invoke(readBacnetProperty, parameter, null); }

  //endregion Action "readBacnetProperty"

  //region Action "writeBacnetProperty"

  /**
   * Slot for the {@code writeBacnetProperty} action.
   * @see #writeBacnetProperty(BEnum parameter)
   */
  public static final Action writeBacnetProperty = newAction(Flags.HIDDEN, BDynamicEnum.make(BBacnetPropertyIdentifier.presentValue), null);

  /**
   * Invoke the {@code writeBacnetProperty} action.
   * @see #writeBacnetProperty
   */
  public void writeBacnetProperty(BEnum parameter) { invoke(writeBacnetProperty, parameter, null); }

  //endregion Action "writeBacnetProperty"

  //region Action "uploadRequiredProperties"

  /**
   * Slot for the {@code uploadRequiredProperties} action.
   * @see #uploadRequiredProperties()
   */
  public static final Action uploadRequiredProperties = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code uploadRequiredProperties} action.
   * @see #uploadRequiredProperties
   */
  public void uploadRequiredProperties() { invoke(uploadRequiredProperties, null, null); }

  //endregion Action "uploadRequiredProperties"

  //region Action "uploadOptionalProperties"

  /**
   * Slot for the {@code uploadOptionalProperties} action.
   * @see #uploadOptionalProperties()
   */
  public static final Action uploadOptionalProperties = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code uploadOptionalProperties} action.
   * @see #uploadOptionalProperties
   */
  public void uploadOptionalProperties() { invoke(uploadOptionalProperties, null, null); }

  //endregion Action "uploadOptionalProperties"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetObject.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetObject()
  {
  }

  /**
   * Create a new BBacnetObject from the given object-identifier.
   *
   * @param id the object-identifier specifying the new object.
   * @return a BBacnetObject with the given objectId.
   */
  public static BBacnetObject make(BBacnetObjectIdentifier id)
  {
    if (!initialized) init();
    Array<TypeInfo> o = byObjectType.get(id.getObjectType());
    if (o != null && o.size() > 0)
    {
      TypeInfo element = o.get(0);
      BBacnetObject bo = (BBacnetObject)element.getInstance();
      bo.setObjectId(id);
      return bo;
    }
    return new BBacnetObject();
  }

  /**
   * Get the TypeInfo for the given BACnet Object ID.
   *
   * @deprecated Use getTypeInfos(BBacnetObjectIdentifier) instead
   */
  @Deprecated
  public static TypeInfo getTypeInfo(BBacnetObjectIdentifier id)
  {
    if (!initialized) init();
    Array<TypeInfo> a = byObjectType.get(id.getObjectType());
    if (a != null) return a.first();
    return BBacnetObject.TYPE.getTypeInfo();
  }

  /**
   * Get the TypeInfos available for the given BACnet Object ID.
   */
  public static TypeInfo[] getTypeInfos(BBacnetObjectIdentifier id)
  {
    if (!initialized) init();
    Array<TypeInfo> a = byObjectType.get(id.getObjectType());
    if (a != null) return a.trim();
    return new TypeInfo[] { BBacnetObject.TYPE.getTypeInfo() };
  }


////////////////////////////////////////////////////////////////
//  BComponent Overrides
////////////////////////////////////////////////////////////////

  /**
   * Started.
   */
  public void started()
    throws Exception
  {
    checkConfig();
    buildPolledProperties();
    BBacnetObject obj = config().lookupBacnetObject(getObjectId());
    if ((obj != null) && (obj != this))
    {
      log.severe("Duplicate Bacnet Object ID for config object " + this + " in " + device()
        + "; defaulting objectId!");
      setObjectId(BBacnetObjectIdentifier.make(getObjectType().getOrdinal()));
    }
  }

  /**
   * Stopped.
   */
  public void stopped()
    throws Exception
  {
    try
    {
      network().getPollService(this).unsubscribe(this);
    }
    catch (NotRunningException e)
    {
      log.warning("BBacnetObject.stopped:NotRunningException unsubscribing from polling on " + this);
    }
    polledProperties = null;
  }

  /**
   * Property changed.
   */
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning() || (cx == noWrite)) return;
    if (p.equals(objectId))
    {
      removeAll(null);
      upload(new BUploadParameters());
      if (isSubscribed())
      {
        BBacnetPoll pollService = (BBacnetPoll)network().getPollService(this);
        pollService.unsubscribe(this);
        buildPolledProperties();
        pollService.subscribe(this);
      }
      return;
    }
    else if (p.equals(pollFrequency))
    {
      if (isSubscribed())
      {
        BBacnetPoll pollService = (BBacnetPoll)network().getPollService(this);
        pollService.unsubscribe(this);
        pollService.subscribe(this);
      }
    }
    if ((cx != noWrite) && (!Flags.isReadonly(this, p)) && (p.getFacets().getFacet(PID) != null))
    {
      BBacnetDevice device = device();
      if (device != null && device().isServiceSupported("writePropertyMultiple"))
      {
        synchronized (wpmList)
        {
          wpmList.add(p);
          if (wpmTkt == null)
            wpmTkt = Clock.schedule(this, WPM_DELAY, download, new BDownloadParameters());
        }
      }
      else if (device != null)
      {
        network().getWriteWorker().post(() ->
        {
          try
          {
            BStatus status = device.getStatus();
            if (status != null && status.isOk())
              writeProperty(p);
          }
          catch (BacnetException e)
          {
            log.warning("Unable to write BACnet property " + p + " in " + this + ":" + e);
          }
        });

      }
    }
  }

  /**
   * For objectId, get the facets from the device's object type facets.
   */
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.equals(objectId))
    {
      if (!isMounted()) return super.getSlotFacets(slot);
      BFacets f = BBacnetObjectType.getObjectIdFacets(getObjectType().getOrdinal());
      if (f != null)
        return f;
      BBacnetDevice dev = device();//(BBacnetDevice)getDevice();
      if (dev != null)
      {
        BExtensibleEnumList elist = dev.getEnumerationList();
        if (elist != null)
        {
          return elist.getObjectTypeFacets();
        }
      }
    }
    if (slot.equals(objectType))
    {
      BBacnetDevice dev = (BBacnetDevice)getDevice();
      if (dev != null)
      {
        BExtensibleEnumList elist = dev.getEnumerationList();
        if (elist != null)
        {
          return elist.getObjectTypeFacets();
        }
      }
    }

    // FIXX: temp handling of dynamic bit strings.
    if (slot.getName().equals(BBacnetPropertyIdentifier.statusFlags.getTag()))
      return BacnetBitStringUtil.BACNET_STATUS_FLAGS_FACETS;
    if (slot.getName().equals(BBacnetPropertyIdentifier.eventEnable.getTag()))
      return BacnetBitStringUtil.BACNET_EVENT_TRANSITION_BITS_FACETS;
    if (slot.getName().equals(BBacnetPropertyIdentifier.ackedTransitions.getTag()))
      return BacnetBitStringUtil.BACNET_EVENT_TRANSITION_BITS_FACETS;
    if (slot.getName().equals(BBacnetPropertyIdentifier.limitEnable.getTag()))
      return BacnetBitStringUtil.BACNET_LIMIT_ENABLE_FACETS;
    return super.getSlotFacets(slot);
  }

  /**
   * Callback when the component enters the subscribed state.
   */
  public void subscribed()
  {
    if (!isRunning()) return;
    ((BBacnetPoll)network().getPollService(this)).subscribe(this);
    upload(new BUploadParameters(false));
  }

  /**
   * Callback when the component exits the subscribed state.
   */
  public void unsubscribed()
  {
    if (!isRunning()) return;
    ((BBacnetPoll)network().getPollService(this)).unsubscribe(this);
  }


////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Callback for processing upload on async thread.
   * Default implementation is to call asyncUpload on all
   * children implementing the Loadable interface.
   */
  @SuppressWarnings("unchecked")
  public void doUpload(BUploadParameters p, Context cx)
  {
    // Bail if device is down or disabled, or objectId is bad.
    BStatus status = getStatus();
    if (!device().getEnabled() || device().getStatus().isDown())
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine(device().getName() + " is either disabled or status is down, object upload is unsuccessful.");
      }
      return;
    }
    if (!getObjectId().isValid()) return;

    setStatus(BStatus.make(status.getBits() | BStatus.STALE, BFacets.make("upload", "PENDING")));

    // If the device does not support ReadPropertyMultiple, we can only
    // read the possible properties, and see what exists.
    if (!device().isServiceSupported("readPropertyMultiple"))
    {
      uploadIndividual(new NReadAccessSpec(getObjectId(), device().getPossibleProperties/*Vector*/(getObjectId())));
    }
    else
    {
      // First, try to read all implemented properties using the "all" propertyId.
      @SuppressWarnings("rawtypes") Vector specs = new Vector();
      specs.add(new NReadAccessSpec(getObjectId(), BBacnetPropertyIdentifier.ALL));
      @SuppressWarnings("rawtypes") Vector vals = null;
      boolean ok = false;
      try
      {
        vals = client().readPropertyMultiple(device().getAddress(), specs);

        // If vals is null, comm is disabled so just quit.
        if (vals == null) return;
  
        @SuppressWarnings("rawtypes") Iterator it = ((NReadAccessResult)vals.elementAt(0)).getResults();
        updateProperties(it);
        ok = true;
      }
      catch (Exception e)
      {
        if (log.isLoggable(Level.FINE))
        {
          log.fine("Exception uploading " + this + " using rpm(ALL):" + e);
        }
      }

      if (!ok)
      {
        try
        {
          // Get required, then optional, properties.
          specs.clear();
          specs.add(new NReadAccessSpec(getObjectId(), BBacnetPropertyIdentifier.REQUIRED));
          vals = client().readPropertyMultiple(device().getAddress(), specs);
          @SuppressWarnings("rawtypes") Iterator it = ((NReadAccessResult)vals.elementAt(0)).getResults();
          updateProperties(it);
          specs.clear();

          specs.add(new NReadAccessSpec(getObjectId(), BBacnetPropertyIdentifier.OPTIONAL));
          vals = client().readPropertyMultiple(device().getAddress(), specs);
          it = ((NReadAccessResult)vals.elementAt(0)).getResults();
          updateProperties(it);

          ok = true;
        }
        catch (Exception e)
        {
          if (log.isLoggable(Level.FINE))
          {
            log.fine("Exception uploading " + this + " using rpm(REQ/OPT):" + e);
          }
        }
      }

      if (!ok)
      {
        uploadIndividual(new NReadAccessSpec(getObjectId(), device().getPossibleProperties(getObjectId())));
      }
    }

    // Now that all values have been read from the device, set the
    // facets describing the output property, and update.
    setOutputFacets();
    BComponentSpace space = getComponentSpace();
    if (space != null) space.update(this, 0);
    buildPolledProperties();
    setStatus(BStatus.ok);
    if (log.isLoggable(Level.FINEST))
    {
      log.finest(device().getName() + " object upload execution finish.");
    }
  }

  public void doUploadRequiredProperties()
  {
    uploadProperties(BBacnetPropertyIdentifier.required);
  }

  public void doUploadOptionalProperties()
  {
    uploadProperties(BBacnetPropertyIdentifier.optional);
  }
  
  @SuppressWarnings("unchecked")
  private void uploadProperties(final BBacnetPropertyIdentifier propertyId)
  {
    BBacnetNetwork network = BBacnetNetwork.bacnet();
    if (propertyId == null)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine(lex.get("object.upload.unknown.error"));
      }
    }
    else
    {
      network.getWorker().post(new Runnable()
      {
        public void run()
        {
          try
          {
            @SuppressWarnings("rawtypes") Vector specs = new Vector();
            specs.add(new NReadAccessSpec(getObjectId(), propertyId.getOrdinal()));
            @SuppressWarnings("rawtypes") Vector vals = client().readPropertyMultiple(device().getAddress(), specs);
            @SuppressWarnings("rawtypes") Iterator it = ((NReadAccessResult)vals.elementAt(0)).getResults();
            BBacnetObject.this.updateProperties(it);
          }
          catch (BacnetException e)
          {
            if (log.isLoggable(Level.FINE))
            {
              log.log(Level.FINE, lex.getText("object.upload." + propertyId.getTag() + ".error"), e);
            }
          }
        }
      });
    }
  }

  /**
   * Callback for processing download on async thread.
   * Default implementation is to call asyncDownload on all
   * children implementing the  Loadable interface.
   */
  @SuppressWarnings("unchecked")
  public void doDownload(BDownloadParameters p, Context cx)
  {
    Property[] props = null;
    synchronized (wpmList)
    {
      if (wpmTkt != null) wpmTkt.cancel();
      wpmTkt = null;
      props = new Property[wpmList.size()];
      wpmList.toArray(props);
      wpmList.clear();
    }

    boolean wpmOk = false;
    int firstFailPropId = NOT_USED;
    if (device().isServiceSupported("writePropertyMultiple"))
    {
      try
      {
        NWriteAccessSpec was = new NWriteAccessSpec(getObjectId());
        for (int i = 0; i < props.length; i++)
        {
          BacnetPropertyData d = getPropertyData(props[i]);
          // Only include BACnet properties (those with a pId facet).
          if (d == NOT_BACNET_PROPERTY) continue;

          // Do not include certain non-writable properties like objectId, objectType, etc.
          if (d.propertyId == BBacnetPropertyIdentifier.OBJECT_IDENTIFIER) continue;
          if (d.propertyId == BBacnetPropertyIdentifier.OBJECT_TYPE) continue;
          was.addPropertyValue(d.propertyId, AsnUtil.toAsn(get(props[i])));
        }
        @SuppressWarnings("rawtypes") Vector writeSpecs = new Vector();
        writeSpecs.add(was);
        client().writePropertyMultiple(device().getAddress(), writeSpecs);
        wpmOk = true;
      }
      catch (ErrorException e)
      {
        String msg = MessageFormat.format("BACnet Error downloading " + this + ":\nFailed write for {0}:", e.getErrorParameters());
        firstFailPropId = ((BBacnetObjectPropertyReference)e.getErrorParameters()[0]).getPropertyId();
        log.info(msg + e);
      }
      catch (BacnetException e)
      {
        log.log(Level.INFO, "BacnetException downloading " + this + ":" + e, e);
      }
    }
    if (!wpmOk)
    {
      boolean preFailure = true;
      for (int i = 0; i < props.length; i++)
      {
        if (preFailure)
        {
          if (firstFailPropId == ((BInteger)props[i].getFacets().getFacet(PID)).getInt())
            preFailure = false;
          else
            continue;
        }
        try
        {
          writeProperty(props[i]);
        }
        catch (Exception e2)
        {
          log.warning("Cannot write property " + props[i] + " in " + this + ":" + e2);
        }
      }
    }
    if (log.isLoggable(Level.FINEST))
    {
      log.finest(device().getName() + " object download execution finish.");
    }
  }

  /**
   * Read a property.
   */
  public BValue doReadBacnetProperty(BEnum propId)
    throws BacnetException
  {
    if (!device().isDown())
    {
      Property prop = lookupBacnetProperty(propId.getOrdinal());
      if (prop != null)
      {
        readProperty(prop);
        return get(prop);
      }
    }
    return null;
  }

  /**
   * Write a property.
   */
  public void doWriteBacnetProperty(BEnum propId)
    throws BacnetException
  {
    if (!device().isDown())
    {
      Property prop = lookupBacnetProperty(propId.getOrdinal());
      if (prop != null)
        writeProperty(prop);
    }
  }


////////////////////////////////////////////////////////////////
// Convenience Access
////////////////////////////////////////////////////////////////

  /**
   * @return the BBacnetNetwork containing this BBacnetObject.
   */
  protected final BBacnetNetwork network()
  {
    return (config != null) ? config.network() : null;
  }

  /**
   * @return the BBacnetDevice containing this BBacnetObject.
   */
  public final BBacnetDevice device()
  {
    if (config != null)
    {
      return config.device();
    }
    BComplex parent = getParent();
    while (parent != null)
    {
      if (parent instanceof BBacnetDevice)
      {
        return (BBacnetDevice)parent;
      }
      parent = parent.getParent();
    }
    return null;
  }

  /**
   * @return the BBacnetDevice containing this BBacnetObject.
   */
  protected final BBacnetConfigDeviceExt config()
  {
    return config;
  }

  private static final BBacnetClientLayer client()
  {
    return ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient();
  }

  /**
   * To String.
   */
  public String toString(Context context)
  {
    return getName() + " [" + getObjectId().toString(context) + "]";
  }

  /**
   * Subclasses that have a present value property should
   * override this method and return this property.  The
   * default returns null.
   */
  public Property getPresentValueProperty()
  {
    return null;
  }

  /**
   * Set the output facets.
   * Object types with properties containing meta-data about the main value
   * can use this to set a slot containing these facets.
   */
  protected void setOutputFacets()
  {
  }

  /**
   * Should this property ID be polled?
   * Override point for objects to filter properties for polling, e.g.,
   * Object_List in Device object, or Log_Buffer in Trend Log.
   */
  protected boolean shouldPoll(int propertyId)
  {
    return true;
  }

  /**
   * Convert the property to an ASN.1-encoded byte array.
   * Subclasses with properties requiring specialized encoding
   * may need to override this method.
   *
   * @param d
   * @param p
   * @return encoded byte array
   */
  protected byte[] toEncodedValue(BacnetPropertyData d, Property p)
  {
    return AsnUtil.toAsn(d.getAsnType(), get(p));
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  @Override
  public boolean isParentLegal(BComponent parent)
  {
    if (parent instanceof BBacnetConfigFolder && (parent.getParent() instanceof BBacnetConfigFolder)) // Not to support nested folders.
    {
      return false;
    }
    return super.isParentLegal(parent);
  }

////////////////////////////////////////////////////////////////
// Bacnet Specification
////////////////////////////////////////////////////////////////

  /**
   * Read a Bacnet property.
   *
   * @param prop
   */
  public void readProperty(Property prop)
    throws BacnetException
  {
    BacnetPropertyData d = getPropertyData(prop);
    if (d == NOT_BACNET_PROPERTY || device().isDown()) return/* false*/;

    // Read array properties differently - they can fall back to a
    // one-at-a-time approach if segmentation constraints require.
    if (prop.getType() == BBacnetArray.TYPE)
      readArrayProperty(prop, d);
    else
    {
      byte[] encodedValue = null;
      encodedValue = client().readProperty(device().getAddress(),
        getObjectId(),
        d.getPropertyId());
      set(prop, AsnUtil.fromAsn(d.getAsnType(), encodedValue, get(prop)), noWrite);
    }
  }

/** Not Yet...
 public void readPropertyMultiple(Property[] props)
 {
 if (props == null) return;
 Vector refs = new Vector();
 for (int i=0; i<props.length; i++)
 {
 BInteger pId = (BInteger)props[i].getFacets().getFacet(PID);
 if (pId != null)
 refs.addPropertyReference(pId.getInt());
 }
 Vector results = client().readPropertyMultiple(device().getAddress(),
 getObjectId(),
 refs);
 for (int i=0; i<props.length; i++)
 {

 }
 }
 */
  /**
   * Write a Bacnet property.
   *
   * @param prop
   */
  public void writeProperty(Property prop)
    throws BacnetException
  {
    BacnetPropertyData d = getPropertyData(prop);
    if (d == NOT_BACNET_PROPERTY) return;
    if (!device().isServiceSupported("writeProperty"))
      throw new UnsupportedOperationException(lex.getText("serviceNotSupported.writeProperty"));

    client().writeProperty(device().getAddress(),
      getObjectId(),
      d.getPropertyId(),
      NOT_USED,
      toEncodedValue(d, prop),
      NOT_USED);
  }

  /**
   * Write a Bacnet property.
   *
   * @param prop
   * @param arrayIndex
   * @param encodedValue
   */
  public void writeProperty(Property prop,
                            int arrayIndex,
                            byte[] encodedValue)
    throws BacnetException
  {
    BacnetPropertyData d = getPropertyData(prop);
    if (d == NOT_BACNET_PROPERTY) return;
    if (!device().isServiceSupported("writeProperty"))
      throw new UnsupportedOperationException(lex.getText("serviceNotSupported.writeProperty"));

    client().writeProperty(device().getAddress(),
      getObjectId(),
      d.getPropertyId(),
      arrayIndex,
      encodedValue);
  }

  /**
   * Add an element to a list property.
   *
   * @param prop
   * @param listElement
   */
  public void addListElement(Property prop,
                             BValue listElement)
    throws BacnetException
  {
    // Make sure this is a BACnet ListOf property.
    BacnetPropertyData d = getPropertyData(prop);
    if (d == NOT_BACNET_PROPERTY) return;
    if (!get(prop).getType().is(BBacnetListOf.TYPE)) return;
    if (!device().isServiceSupported("addListElement"))
      throw new UnsupportedOperationException(lex.getText("serviceNotSupported.addListElement"));

    byte[] encodedListElement = AsnUtil.toAsn(listElement);

    if(getObjectId().getInstanceNumber() != -1)
    {
      client().addListElement(device().getAddress(),
        getObjectId(),
        d.getPropertyId(),
        NOT_USED,
        encodedListElement);
    }
  }

  /**
   * Remove an element from a list property.
   *
   * @param prop
   * @param listElement
   */
  public void removeListElement(Property prop,
                                BValue listElement)
    throws BacnetException
  {
    // Make sure this is a BACnet ListOf property.
    BacnetPropertyData d = getPropertyData(prop);
    if (d == NOT_BACNET_PROPERTY) return;
    if (!get(prop).getType().is(BBacnetListOf.TYPE)) return;
    if (!device().isServiceSupported("writeProperty"))
      throw new UnsupportedOperationException(lex.getText("serviceNotSupported.removeListElement"));

    byte[] encodedListElement = AsnUtil.toAsn(listElement);
    client().removeListElement(device().getAddress(),
      getObjectId(),
      d.getPropertyId(),
      NOT_USED,
      encodedListElement);
  }


////////////////////////////////////////////////////////////////
// BIBacnetPollable
////////////////////////////////////////////////////////////////

  /**
   * Get the pollable type of this object.
   *
   * @return one of the pollable types defined in BIBacnetPollable.
   */
  public final int getPollableType()
  {
    return BACNET_POLLABLE_OBJECT;
  }

  /**
   * Get the poll frequency.
   * @return the poll frequency for this object.
  public final BPollFrequency getPollFrequency()  { return BPollFrequency.normal; }
   */

  /**
   * Poll the object.
   *
   * @return true if the object was successfully polled, false if not.
   * @deprecated
   */
  @Deprecated
  public final boolean poll()
  {
    log.warning("BBacnetObject.poll() is DEPRECATED!!!");
    return false;
  }

  // FIXX:Temporary place holders....

  /**
   * Indicate successful poll.
   */
  public final void readOk()
  {
    setStatus(BStatus.makeFault(getStatus(), false));
    setFaultCause("");
  }

  /**
   * Indicate a failure polling this object.
   *
   * @param failureMsg
   */
  public final void readFail(String failureMsg)
  {
    setStatus(BStatus.makeFault(getStatus(), true));
    setFaultCause(failureMsg);
  }

  /**
   * Normalize the encoded data into the pollable's data structure.
   *
   * @param encodedValue
   * @param status
   * @param cx
   */
  public final void fromEncodedValue(byte[] encodedValue, BStatus status, Context cx)
  {
    try
    {
      Property prop = lookupBacnetProperty(((PollListEntry)cx).getPropertyId());
      BInteger asnType = (BInteger)prop.getFacets().getFacet(ASN_TYPE);
      BValue v = AsnUtil.fromAsn(asnType.getInt(), encodedValue, get(prop));
      BacUtil.set(this, prop, v, noWrite);
      readOk();
    }
    catch (AsnException e)
    {
      readFail(e.toString());
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "Exception decoding value for " + this
          + " [" + cx + "]:" + ByteArrayUtil.toHexString(encodedValue), e);
      }
    }
    catch (Exception e)
    {
      plog.log(Level.SEVERE, "Exception occurred in fromEncodedValue", e);
    }
  }

  /**
   * Get the list of poll list entries for this pollable.
   * The first entry for points must be the configured property.
   *
   * @return the list of poll list entries.
   */
  public final PollListEntry[] getPollListEntries()
  {
    return polledProperties.toArray(new PollListEntry[0]);
  }

////////////////////////////////////////////////////////////////
// Helper methods
////////////////////////////////////////////////////////////////

  private void checkConfig()
  {
    BBacnetConfigDeviceExt config = null;
    BComplex parent = getParent();
    while (parent != null)
    {
      if (parent instanceof BBacnetConfigDeviceExt)
      {
        config = (BBacnetConfigDeviceExt)parent;
        break;
      }
      parent = parent.getParent();
    }
    this.config = config;
  }
  
  @SuppressWarnings("unchecked")
  private void readArrayProperty(Property prop, BacnetPropertyData d)
    throws BacnetException
  {
    BBacnetAddress address = device().getAddress();
    BBacnetObjectIdentifier objectId = getObjectId();
    int propertyId = d.getPropertyId();
    byte[] encodedValue = null;
    try
    {
      encodedValue = client().readProperty(address,
        objectId,
        propertyId);
      set(prop, AsnUtil.fromAsn(d.getAsnType(), encodedValue, get(prop)), noWrite);
    }
    catch (Exception e)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("Exception reading property " + prop + " in object " + this + ": " + e
          + "\n building array in groups...");
      }

      // Read the array size.
      int arraySize = AsnUtil.fromAsnInteger(
        client().readProperty(address,
          objectId,
          propertyId,
          0));

      boolean readOk = false;
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      int index = 1;
      if (device().isServiceSupported("readPropertyMultiple"))
      {
        try
        {
          BBacnetArray arr = (BBacnetArray)get(prop);
          BTypeSpec arrTypeSpec = arr.getArrayTypeSpec();
          int elemSize = AsnUtil.getSize(arrTypeSpec);
          int hdrSize = 9; // CxACK (4) + objId (5)
          int elemHdr = 8; // propId (3) + index (3) + open/close tags (2) +
          int maxAPDUSize = device().getMaxAPDULengthAccepted();
          int myMax = BBacnetNetwork.localDevice().getMaxAPDULengthAccepted();
          if (maxAPDUSize > myMax) maxAPDUSize = myMax;
          int safetyFactor = 10;
          int elemsPerRead = (maxAPDUSize - hdrSize - safetyFactor) / (elemSize + elemHdr);

          do
          {
            @SuppressWarnings("rawtypes") Vector refs = new Vector();
            for (int i = index; i < (index + elemsPerRead) && i <= arraySize; i++)
            {
              refs.add(new NBacnetPropertyReference(propertyId, i));
            }
            @SuppressWarnings("rawtypes") Vector results = client().readPropertyMultiple(address, objectId, refs);
            for (int j = 0; j < results.size(); j++)
            {
              NReadPropertyResult rpr = (NReadPropertyResult)results.get(j);
              byte[] val = rpr.getPropertyValue();
              os.write(val, 0, val.length);
              index++;
            }
          } while (index <= arraySize);
          readOk = true;
        }
        catch (Exception e1)
        {
          if (log.isLoggable(Level.FINE))
          {
            log.fine("Exception reading property " + prop + " in object " + this + " in groups: " + e1
              + "\n building array element by element...");
          }
        }
      }

      if (!readOk)
      {
        // Read the array, one element at a time.
        // FIXX: Maybe later, try to read multiple on several at a time,
        // to speed things up a little.
        for (int i = index; i <= arraySize; i++)
        {
          byte[] encodedElement = client().readProperty(address,
            objectId,
            propertyId,
            i);
          os.write(encodedElement, 0, encodedElement.length);
        }
      }

      // Now, put it together.
      byte[] encodedArray = os.toByteArray();
      set(prop, AsnUtil.fromAsn(d.getAsnType(), encodedArray, get(prop)), noWrite);
    }
  }

  /**
   * Read and populate a <code>BBacnetArray</code> for inclusion in this
   * BBacnetObject.  This method should not throw any exception.
   *
   * @param a
   * @param propId
   * @param pi
   * @return true if the array was fully read, false if there was a problem.
   */
  private boolean readArray(BBacnetArray a, int propId, PropertyInfo pi)
  {
    try
    {
      BBacnetAddress address = device().getAddress();
      BBacnetObjectIdentifier objectId = getObjectId();
      int asize = 0;
      try
      {
        asize = AsnUtil.fromAsnUnsignedInt(client().readProperty(address, objectId, propId, 0));
      }
      catch (Exception e)
      {
        if (log.isLoggable(Level.FINE))
        {
          log.log(Level.FINE, "Cannot get array size", e);
        }
        return false;
      }
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      int i = 1;
      try
      {
        for (i = 1; i <= asize; i++)
        {
          byte[] encodedElement = client().readProperty(address,
            objectId,
            propId,
            i);
          os.write(encodedElement, 0, encodedElement.length);
        }
        byte[] encodedArray = os.toByteArray();
        AsnInputStream in = new AsnInputStream(encodedArray);
        a.readAsn(in);
        return true;
      }
      catch (Exception e)
      {
        if (log.isLoggable(Level.FINE))
        {
          log.log(Level.FINE, "Exception reading array element " + i + ":" + e, e);
        }
        return false;
      }
    }
    catch (Throwable t)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "Unable to build BacnetArray for property " + propId, t);
      }
      return false;
    }
  }

////////////////////////////////////////////////////////////////
// Poll Support
////////////////////////////////////////////////////////////////

  protected void buildPolledProperties()
  {
    BBacnetPoll pollService = (BBacnetPoll)network().getPollService(this);
    if (isSubscribed()) pollService.unsubscribe(this);
    SlotCursor<Property> sc = getProperties();
    BInteger pId = null;
    if (polledProperties.size() > 0)
      polledProperties.clear();

    while (sc.next())
    {
      Property p = sc.property();
      pId = (BInteger)p.getFacets().getFacet(PID);
      if ((pId != null) && shouldPoll(pId.getInt()))
        polledProperties.add(new PollListEntry(getObjectId(), pId.getInt(), device(), this));
    }
    if (isSubscribed()) pollService.subscribe(this);
  }


////////////////////////////////////////////////////////////////
//  BILoadable support
////////////////////////////////////////////////////////////////
  
  private void updateProperties(@SuppressWarnings("rawtypes") Iterator it)
  {
    synchronized (UPLOAD_LOCK)
    {
      while (it.hasNext())
      {
        NReadPropertyResult rpr = (NReadPropertyResult)it.next();
        int propId = rpr.getPropertyId();
        Property prop = lookupBacnetProperty(propId);   // see if we already have it
        try
        {
          if (prop == null)
          {
            // We need to create a new Property for this one.
            if (!rpr.isError())
            {
              // Get the property meta-data.
              PropertyInfo propInfo = getPropertyInfo(propId);

              // Decode the property value.
              BValue value = AsnUtil.asnToValue(propInfo, rpr.getPropertyValue());

              // Add the property.
              String name = SlotPath.escape(propInfo.getName());
              prop = add(name, value, 0, makeFacets(propInfo, value), null);
              if (shouldPoll(propId))
                polledProperties.add(new PollListEntry(getObjectId(), propId, device(), this));

              // For unknown propertyIds, check to see if we need to add this propertyId
              // to our device's enumeration list.
              if (!device().getEnumerationList().getPropertyIdRange().isOrdinal(propId))
              {
                device().getEnumerationList().addNewPropertyId(propInfo.getName(), propId);
              }
            } // rpr !error
          } // prop == null
          else
          {
            // We already have this Property, so set it from the encoded value.
            if (rpr.isError())
            {
              if (log.isLoggable(Level.FINE))
              {
                log.fine("Error uploading property " + prop + ":" + rpr.getPropertyAccessError());
              }
            }
            else
            {
              set(prop,
                AsnUtil.fromAsn(((BInteger)prop.getFacets().getFacet(ASN_TYPE)).getInt(),
                  rpr.getPropertyValue(),
                  get(prop)),
                noWrite);
            }
          } // else (prop != null)
        } // try
        catch (AsnException e)
        {
          log.info("Unable to convert encoded value: prop=" + prop + ", id=" + propId
            + ", val=" + ByteArrayUtil.toHexString(rpr.getPropertyValue()) + "\n" + e);
        }
        catch (Exception e)
        {          
          log.info("Unable to add/update property: prop=" + prop + ", id=" + propId
            + ", val=" + ByteArrayUtil.toHexString(rpr.getPropertyValue()) + "\n" + e);
          if (log.isLoggable(Level.FINE))
          {
            log.log(Level.FINE, "Stack Trace: ", e);
          }
        }
      } // while
    }
    if (log.isLoggable(Level.FINEST))
    {
      log.finest(device().getName() + " object updateProperties execution finish.");
    }
  }

  /**
   * Upload all properties individually.
   */
  private void uploadIndividual(NReadAccessSpec spec)
  {
    PropertyReference[] refs = spec.getListOfPropertyReferences();
    for (int i = 0; i < refs.length; i++)
    {
      int propId = refs[i].getPropertyId();
      try
      {
        Property prop = lookupBacnetProperty(propId);
        if (prop != null)
          readProperty(prop);
        else
        {
          byte[] encodedValue = null;
          PropertyInfo propInfo = getPropertyInfo(propId);
          String name = SlotPath.escape(propInfo.getName());
          try
          {
            encodedValue = client().readProperty(device().getAddress(),
              getObjectId(),
              propId);
            BValue value = AsnUtil.asnToValue(propInfo, encodedValue);
            prop = add(name, value, 0, makeFacets(propInfo, value), noWrite);
          }
          catch (BacnetException e)
          {
            if (e instanceof ErrorException)
            {
              if (((ErrorException)e).getErrorType().getErrorCode() == BBacnetErrorCode.UNKNOWN_PROPERTY)
              {
                if (log.isLoggable(Level.FINE))
                {
                  log.fine("Unknown Property " + propId + " in object " + getObjectId() + ": " + e);
                }
                continue;
              }
            }

            // Try to read the array bit by bit.
            if (propInfo.isArray())
            {
              BBacnetArray a = new BBacnetArray();
              a.setArrayTypeSpec(BTypeSpec.make(propInfo.getType()));
              readArray(a, propId, propInfo);
              prop = add(name, a, 0, makeFacets(propInfo, a), noWrite);
            }

            log.info("BacnetException uploading propertyId " + propId + " in object " + getObjectId() + ": " + e);
          }

          // add to poll list
          if (shouldPoll(propId))
            polledProperties.add(new PollListEntry(getObjectId(), propId, device(), this));

        } // else (a new property)

      } // outer try
      catch (TransactionException e)
      {
        if (log.isLoggable(Level.FINE))
        {
          log.fine("TransactionException uploading object " + getObjectId() + " in " + device() + ": " + e);
        }
        break;
      }
      catch (Exception e)
      {
        if (log.isLoggable(Level.FINE))
        {
          log.fine("Exception uploading propertyId " + propId + " in object " + getObjectId() + ": " + e);
        }
      }
    }
  }

  /**
   * Get a PropertyInfo object containing metadata about this property.
   *
   * @param propId the property ID.
   * @return a PropertyInfo.
   */
  private PropertyInfo getPropertyInfo(int propId)
  {
    // First, try the list of Bacnet-defined properties.
    PropertyInfo propInfo = device().getPropertyInfo(getObjectId().getObjectType(), propId);

    // If still nothing, just create an "unknown proprietary" PropertyInfo.
    if (propInfo == null)
    {
      propInfo = new PropertyInfo(BBacnetPropertyIdentifier.tag(propId), propId, AsnConst.ASN_UNKNOWN_PROPRIETARY);
    }

    // Return what we have.
    return propInfo;
  }


////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  /**
   * Spy.
   */
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetObject", 2);
    out.prop("config", config);
    if (polledProperties != null)
    {
      int siz = polledProperties.size();
      out.prop("polledProperties", siz);
      for (int i = 0; i < siz; i++)
        out.prop("polledProperties[" + i + "]:", polledProperties.get(i).debugString());
    }
    else
      out.prop("polledProperties", "NULL");
    out.prop("propDataMap", propDataMap.size());
    Iterator<Map.Entry<BFacets,BacnetPropertyData>> it = propDataMap.entrySet().iterator();
    while (it.hasNext())
    {
      Map.Entry<BFacets,BacnetPropertyData> entry = it.next();
      out.prop(entry.getKey(), entry.getValue());
    }
    out.endProps();
  }


//////////////////////////////////////////////////////////////////
//   Bacnet Property management
//////////////////////////////////////////////////////////////////

  private Property lookupBacnetProperty(int propId)
  {
    SlotCursor<Property> c = getProperties();
    while (c.next())
    {
      try
      {
        Property property = c.property();
        BInteger propertyIdFacet = (BInteger)property.getFacets().getFacet(PID);
        if (propertyIdFacet != null &&
            propertyIdFacet.getInt() == propId)
        {
          return property;
        }
      }
      catch (Exception ignored)
      {
        //Keep looking
      }
    }

    return null;
  }


////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.make("module://bacnet/com/tridium/bacnet/ui/icons/bacObject.png");

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  protected static final Lexicon lex = Lexicon.make("bacnet");
  public static final Logger log = Logger.getLogger("bacnet.client");
  public static final Logger plog = Logger.getLogger("bacnet.point");

  private static HashMap<Integer, Array<TypeInfo>> byObjectType = new HashMap<>();
  private static boolean initialized = false;

  private static final BRelTime WPM_DELAY = BRelTime.make(20); // 20ms delay
  private static final Object UPLOAD_LOCK = new Object();

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * List of BACnetPropertyReferences for the properties of this object.
   */
  protected volatile ArrayList<PollListEntry> polledProperties = new ArrayList<>();

  private BBacnetConfigDeviceExt config;
  private HashMap<BFacets, BacnetPropertyData> propDataMap = new HashMap<>();

  private HashSet<Property> wpmList = new HashSet<>();
  private Clock.Ticket wpmTkt = null;


////////////////////////////////////////////////////////////////
// Initialization
////////////////////////////////////////////////////////////////

  static void init()
  {
    TypeInfo base = BBacnetObject.TYPE.getTypeInfo();
    TypeInfo[] types = Sys.getRegistry().getConcreteTypes(base);
    for (int i = 0; i < types.length; i++)
    {
      if (types[i].equals(base)) continue;
      BBacnetObject o = (BBacnetObject)types[i].getInstance();
      int objTypOrd = o.getObjectType().getOrdinal();
      Array<TypeInfo> cur = byObjectType.get(objTypOrd);
      if (cur == null)
        cur = new Array<>(TypeInfo.class);
      cur.add(types[i]);
      byObjectType.put(objTypOrd, cur);
    }
    initialized = true;
  }

////////////////////////////////////////////////////////////////
// Facets support
////////////////////////////////////////////////////////////////

  /**
   * Property ID facet name.
   */
  public static final String PID = "pId";
  /**
   * Asn Type facet name.
   */
  public static final String ASN_TYPE = "asn";
  /**
   * Non-Bacnet property metadata.
   */
  private static final BacnetPropertyData NOT_BACNET_PROPERTY = new BacnetPropertyData(NOT_USED, 0);

  /**
   * Make a BFacets with property ID and Asn type.
   */
  protected static BFacets makeFacets(int propertyId, int asnType)
  {
    HashMap<String, BIDataValue> map = new HashMap<>();
    map.put(PID, BInteger.make(propertyId));
    map.put(ASN_TYPE, BInteger.make(asnType));
    return BFacets.make(map);
  }

  /**
   * Make a BFacets with property ID, Asn type, and a Map
   * which contains additional info.
   * Used for bit strings, to name the bits.
   */
  protected static BFacets makeFacets(int propertyId, int asnType, Map<String, BIDataValue> m)
  {
    HashMap<String, BIDataValue> map = new HashMap<>(m);
    map.put(PID, BInteger.make(propertyId));
    map.put(ASN_TYPE, BInteger.make(asnType));
    return BFacets.make(map);
  }

  /**
   * Make a BFacets with property ID, Asn type, and two arrays of additional
   * keys and values.
   */
  protected static BFacets makeFacets(int propertyId, int asnType, String[] keys, BIDataValue[] values)
  {
    if (keys.length != values.length)
      throw new IllegalArgumentException();

    String[] k = new String[keys.length + 2];
    System.arraycopy(keys, 0, k, 0, keys.length);
    k[keys.length] = PID;
    k[keys.length + 1] = ASN_TYPE;

    BIDataValue[] v = new BIDataValue[values.length + 2];
    System.arraycopy(values, 0, v, 0, values.length);
    v[values.length] = BInteger.make(propertyId);
    v[values.length + 1] = BInteger.make(asnType);

    return BFacets.make(k, v);
  }

  /**
   * Make a BFacets from the given <code>PropertyInfo</code>.
   * Used in dynamic creation of properties.
   */
  protected static BFacets makeFacets(PropertyInfo info, BValue value)
  {
    HashMap<String, BIDataValue> map;
    if (info.isBitString())
      map = new HashMap<>(BacnetBitStringUtil.getBitStringMap(info.getBitStringName()));
    else
      map = new HashMap<>();
    map.put(PID, BInteger.make(info.getId()));
    map.put(ASN_TYPE, BInteger.make(info.getAsnType()));
    return BFacets.make(map);
  }

  protected BacnetPropertyData getPropertyData(Property prop)
  {
    BFacets f = prop.getFacets();
    if (f == null) return NOT_BACNET_PROPERTY;
    if (f.geti(PID, NOT_USED) == NOT_USED) return NOT_BACNET_PROPERTY;

    if (!prop.isDynamic())
    {
      BacnetPropertyData d = propDataMap.get(f);
      if (d == null)
      {
        d = makePropertyData(f);
        propDataMap.put(f, d);
        return d;
      }
    }
    return makePropertyData(f);
  }

  /**
   * Make a new metadata container object from the given BFacets.
   */
  private static BacnetPropertyData makePropertyData(BFacets f)
  {
    int propertyId = NOT_USED;
    int asnType = 0;
    BObject s;
    if ((s = f.getFacet(PID)) != null)
    {
      propertyId = ((BInteger)s).getInt();
    }
    if ((s = f.getFacet(ASN_TYPE)) != null)
    {
      asnType = ((BInteger)s).getInt();
    }
    return BacnetPropertyData.make(propertyId, asnType);
  }


////////////////////////////////////////////////////////////////
// BacnetPropertyData
////////////////////////////////////////////////////////////////

  public static class BacnetPropertyData
  {
    private BacnetPropertyData(int propertyId, int asnType)
    {
      this.propertyId = propertyId;
      this.asnType = asnType;
    }

    static BacnetPropertyData make(int pid, int asn)
    {
      if ((pid == NOT_USED) && (asn == 0)) return NOT_BACNET_PROPERTY;
      return new BacnetPropertyData(pid, asn);
    }

    public int getPropertyId()
    {
      return propertyId;
    }

    public int getAsnType()
    {
      return asnType;
    }

    int propertyId;
    int asnType;
  }

}
