/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetConfirmedServiceChoice;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetFileAccessMethod;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ChangeListError;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.PropertyReference;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.io.RangeData;
import javax.baja.bacnet.io.RangeReference;
import javax.baja.bacnet.io.RejectException;
import javax.baja.file.BIFile;
import javax.baja.file.BLocalFileStore;
import javax.baja.license.Feature;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.PermissionException;
import javax.baja.space.BISpaceNode;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.DuplicateSlotException;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Subscriber;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.BacUtil;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.services.confirmed.ReadRangeAck;
import com.tridium.bacnet.services.error.NChangeListError;
import com.tridium.bacnet.stack.server.BBacnetExportTable;

/**
 * BBacnetFileDescriptor exposes a BIFile to Bacnet as a File object.
 * <p>
 * This extension is different from the point extensions, which are
 * dropped as children to the control point that they are exposing.
 * The BBacnetFileDescriptor can be placed anywhere.  The BFile which is
 * exposed by this extension is referenced by the file property.
 * <p>
 * The name by which this file is known to Bacnet (the Object_Name
 * property) is the name of the extension.
 * <p>
 * All files in Niagara are accessed using the STREAM_ACCESS method.
 *
 * @author Craig Gemmill on 06 Mar 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:AbstractFile"
  )
)
/*
 the status for Niagara server-side behavior.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
/*
 Provides a description of a fault with server-side behavior.
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 the file exposed via this extension.
 */
@NiagaraProperty(
  name = "fileOrd",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  flags = Flags.DEFAULT_ON_CLONE,
  facets = @Facet(name = "BFacets.TARGET_TYPE", value = "\"baja:IFile\"")
)
/*
 objectId is the identifier by which this point is known
 to the Bacnet world.
 */
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.FILE)",
  flags = Flags.DEFAULT_ON_CLONE
)
/*
 the name by which this object is known to the Bacnet world.
 */
@NiagaraProperty(
  name = "objectName",
  type = "String",
  defaultValue = "",
  flags = Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "description",
  type = "String",
  defaultValue = ""
)
/*
 identifies the intended usage of this file.
 */
@NiagaraProperty(
  name = "fileType",
  type = "String",
  defaultValue = ""
)
/*
 time the file was last archived.
 */
@NiagaraProperty(
  name = "archiveTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.DEFAULT"
)
/*
 only STREAM_ACCESS is supported for now.
 */
@NiagaraProperty(
  name = "fileAccessMethod",
  type = "BBacnetFileAccessMethod",
  defaultValue = "BBacnetFileAccessMethod.streamAccess",
  flags = Flags.READONLY
)
public class BBacnetFileDescriptor
  extends BComponent
  implements BIBacnetExportObject,
             BacnetPropertyListProvider
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetFileDescriptor(644408608)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * the status for Niagara server-side behavior.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * the status for Niagara server-side behavior.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * the status for Niagara server-side behavior.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * Provides a description of a fault with server-side behavior.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.READONLY | Flags.TRANSIENT, "", null);

  /**
   * Get the {@code faultCause} property.
   * Provides a description of a fault with server-side behavior.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * Provides a description of a fault with server-side behavior.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "fileOrd"

  /**
   * Slot for the {@code fileOrd} property.
   * the file exposed via this extension.
   * @see #getFileOrd
   * @see #setFileOrd
   */
  public static final Property fileOrd = newProperty(Flags.DEFAULT_ON_CLONE, BOrd.NULL, BFacets.make(BFacets.TARGET_TYPE, "baja:IFile"));

  /**
   * Get the {@code fileOrd} property.
   * the file exposed via this extension.
   * @see #fileOrd
   */
  public BOrd getFileOrd() { return (BOrd)get(fileOrd); }

  /**
   * Set the {@code fileOrd} property.
   * the file exposed via this extension.
   * @see #fileOrd
   */
  public void setFileOrd(BOrd v) { set(fileOrd, v, null); }

  //endregion Property "fileOrd"

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.FILE), null);

  /**
   * Get the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #objectId
   */
  public BBacnetObjectIdentifier getObjectId() { return (BBacnetObjectIdentifier)get(objectId); }

  /**
   * Set the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #objectId
   */
  public void setObjectId(BBacnetObjectIdentifier v) { set(objectId, v, null); }

  //endregion Property "objectId"

  //region Property "objectName"

  /**
   * Slot for the {@code objectName} property.
   * the name by which this object is known to the Bacnet world.
   * @see #getObjectName
   * @see #setObjectName
   */
  public static final Property objectName = newProperty(Flags.DEFAULT_ON_CLONE, "", null);

  /**
   * Get the {@code objectName} property.
   * the name by which this object is known to the Bacnet world.
   * @see #objectName
   */
  public String getObjectName() { return getString(objectName); }

  /**
   * Set the {@code objectName} property.
   * the name by which this object is known to the Bacnet world.
   * @see #objectName
   */
  public void setObjectName(String v) { setString(objectName, v, null); }

  //endregion Property "objectName"

  //region Property "description"

  /**
   * Slot for the {@code description} property.
   * @see #getDescription
   * @see #setDescription
   */
  public static final Property description = newProperty(0, "", null);

  /**
   * Get the {@code description} property.
   * @see #description
   */
  public String getDescription() { return getString(description); }

  /**
   * Set the {@code description} property.
   * @see #description
   */
  public void setDescription(String v) { setString(description, v, null); }

  //endregion Property "description"

  //region Property "fileType"

  /**
   * Slot for the {@code fileType} property.
   * identifies the intended usage of this file.
   * @see #getFileType
   * @see #setFileType
   */
  public static final Property fileType = newProperty(0, "", null);

  /**
   * Get the {@code fileType} property.
   * identifies the intended usage of this file.
   * @see #fileType
   */
  public String getFileType() { return getString(fileType); }

  /**
   * Set the {@code fileType} property.
   * identifies the intended usage of this file.
   * @see #fileType
   */
  public void setFileType(String v) { setString(fileType, v, null); }

  //endregion Property "fileType"

  //region Property "archiveTime"

  /**
   * Slot for the {@code archiveTime} property.
   * time the file was last archived.
   * @see #getArchiveTime
   * @see #setArchiveTime
   */
  public static final Property archiveTime = newProperty(0, BAbsTime.DEFAULT, null);

  /**
   * Get the {@code archiveTime} property.
   * time the file was last archived.
   * @see #archiveTime
   */
  public BAbsTime getArchiveTime() { return (BAbsTime)get(archiveTime); }

  /**
   * Set the {@code archiveTime} property.
   * time the file was last archived.
   * @see #archiveTime
   */
  public void setArchiveTime(BAbsTime v) { set(archiveTime, v, null); }

  //endregion Property "archiveTime"

  //region Property "fileAccessMethod"

  /**
   * Slot for the {@code fileAccessMethod} property.
   * only STREAM_ACCESS is supported for now.
   * @see #getFileAccessMethod
   * @see #setFileAccessMethod
   */
  public static final Property fileAccessMethod = newProperty(Flags.READONLY, BBacnetFileAccessMethod.streamAccess, null);

  /**
   * Get the {@code fileAccessMethod} property.
   * only STREAM_ACCESS is supported for now.
   * @see #fileAccessMethod
   */
  public BBacnetFileAccessMethod getFileAccessMethod() { return (BBacnetFileAccessMethod)get(fileAccessMethod); }

  /**
   * Set the {@code fileAccessMethod} property.
   * only STREAM_ACCESS is supported for now.
   * @see #fileAccessMethod
   */
  public void setFileAccessMethod(BBacnetFileAccessMethod v) { set(fileAccessMethod, v, null); }

  //endregion Property "fileAccessMethod"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetFileDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  /**
   * Register with the Bacnet service when this component is started.
   */
  @Override
  public final void started()
    throws Exception
  {
    super.started();

    // First check for fatal faults.
    checkFatalFault();

    // Export the file and initialize the local copies.
    oldId = getObjectId();
    oldName = getObjectName();
    subscriber = new FileSubscriber(this);
    checkConfiguration();

    // Increment the Device object's Database_Revision for created objects.
    if (!isBackupConigFile() && Sys.isStationStarted())
    {
      BBacnetNetwork.localDevice().incrementDatabaseRevision();
    }
  }

  /**
   * Unregister with the Bacnet service when this component is stopped.
   */
  @Override
  public final void stopped()
    throws Exception
  {
    super.stopped();

    // unexport
    BLocalBacnetDevice local = BBacnetNetwork.localDevice();
    local.unexport(oldId, oldName, this);

    // Clear the local copies.
    subscriber.unsubscribeAll();
    file = null;
    subscriber = null;
    oldId = null;
    oldName = null;

    // Increment the Device object's Database_Revision for deleted objects.
    if (!isBackupConigFile() && local.isRunning())
    {
      local.incrementDatabaseRevision();
    }
  }

  /**
   * Property Changed.
   * If the objectId changes, make sure the new ID is not already in use.
   * If it is, reset it to the current value.
   */
  @Override
  public final void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning())
    {
      return;
    }
    if (p.equals(objectId))
    {
      BBacnetNetwork.localDevice().unexport(oldId, oldName, this);
      checkConfiguration();
      oldId = getObjectId();
      try
      {
        ((BComponent)getParent()).rename(getPropertyInParent(), getObjectId().toString(nameContext));
      }
      catch (DuplicateSlotException ignored)
      {
      }

      if (!isBackupConigFile() && getStatus().isOk())
      {
        BBacnetNetwork.localDevice().incrementDatabaseRevision();
      }
    }
    else if (p.equals(objectName))
    {
      BBacnetNetwork.localDevice().unexport(oldId, oldName, this);
      checkConfiguration();
      oldName = getObjectName();
      if (!isBackupConigFile() && getStatus().isOk())
      {
        BBacnetNetwork.localDevice().incrementDatabaseRevision();
      }
    }
    else if (p.equals(fileOrd))
    {
      checkConfiguration();
      if (!isBackupConigFile() && getStatus().isOk())
      {
        BBacnetNetwork.localDevice().incrementDatabaseRevision();
      }
    }
  }

  /**
   * Get slot facets.
   *
   * @param s
   * @return the appropriate slot facets.
   */
  @Override
  public final BFacets getSlotFacets(Slot s)
  {
    if (s == objectId)
    {
      return BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.FILE);
    }
    return super.getSlotFacets(s);
  }

////////////////////////////////////////////////////////////////
//  BIBacnetExportObject
////////////////////////////////////////////////////////////////

  /**
   * Get the exported object.
   *
   * @return the actual exported object by resolving the object ord.
   */
  @Override
  public final BObject getObject()
  {
    return (BObject)getFile();
  }

  /**
   * Get the BOrd to the exported object.
   */
  @Override
  public final BOrd getObjectOrd()
  {
    return getFileOrd();
  }

  /**
   * Set the BOrd to the exported object.
   *
   * @param objectOrd
   */
  @Override
  public final void setObjectOrd(BOrd objectOrd, Context cx)
  {
    set(fileOrd, objectOrd, cx);
  }

  /**
   * Check the configuration of this object.
   */
  @Override
  public void checkConfiguration()
  {
    // quit if fatal fault
    if (isFatalFault())
    {
      setStatus(BStatus.makeFault(getStatus(), true));
      log.warning(this + ": is in fatal fault");
      return;
    }

    // Clear the name subscriber.
    subscriber.unsubscribeAll();

    // Find the exported point.
    getFile();

    // Check the configuration.
    boolean configOk = true;
    if (file == null)
    {
      setFaultCause("Cannot find exported file");
      configOk = false;
      log.warning(this + ": cannot find exported file");
    }
    else
    {
      subscriber.subscribe((BComponent) getParent());
    }

    // Check for valid object id.
    if (!getObjectId().isValid())
    {
      setFaultCause("Invalid Object ID");
      configOk = false;
      log.warning(this + ": invalid Object Id");
    }

    if (configOk)
    {
      // Try to export - duplicate id & names will be checked in here.
      String err = BBacnetNetwork.localDevice().export(this);
      if (err != null)
      {
        duplicate = true;
        setFaultCause(err);
        configOk = false;
        log.warning(this + ": found duplicate id or name");
      }
      else
      {
        duplicate = false;
      }
    }

    // Set the exported flag.
    if (configOk)
    {
      setFaultCause("");
    }
    setStatus(BStatus.makeFault(getStatus(), !configOk));
    log.info(this + ": configurationOk state is " + configOk);
  }

////////////////////////////////////////////////////////////////
// Bacnet Request Execution
////////////////////////////////////////////////////////////////

  @Override
  public int[] getPropertyList()
  {
    return BacnetPropertyList.makePropertyList(REQUIRED_PROPS, OPTIONAL_PROPS);
  }

  /**
   * Get the value of a property.
   *
   * @param ref the PropertyReference containing id and index.
   * @return a PropertyValue containing either the encoded value or the error.
   */
  @Override
  public final PropertyValue readProperty(PropertyReference ref)
    throws RejectException
  {
    getFile();
    return readProperty(ref.getPropertyId(), ref.getPropertyArrayIndex());
  }

  /**
   * Read the value of multiple Bacnet properties.
   *
   * @param refs the list of property references.
   * @return an array of PropertyValues.
   */
  @Override
  public final PropertyValue[] readPropertyMultiple(PropertyReference[] refs)
    throws RejectException
  {
    getFile();
    PropertyValue[] readResults = new PropertyValue[0];
    ArrayList<PropertyValue> results = new ArrayList<>(refs.length);
    for (int i = 0; i < refs.length; i++)
    {
      int[] props;
      switch (refs[i].getPropertyId())
      {
        case BBacnetPropertyIdentifier.ALL:
          props = REQUIRED_PROPS;
          for (int j = 0; j < props.length; j++)
          {
            results.add(readProperty(props[j], NOT_USED));
          }
          props = OPTIONAL_PROPS;
          for (int j = 0; j < props.length; j++)
          {
            results.add(readProperty(props[j], NOT_USED));
          }
          break;

        case BBacnetPropertyIdentifier.OPTIONAL:
          props = OPTIONAL_PROPS;
          for (int j = 0; j < props.length; j++)
          {
            results.add(readProperty(props[j], NOT_USED));
          }
          break;

        case BBacnetPropertyIdentifier.REQUIRED:
          props = REQUIRED_PROPS;
          for (int j = 0; j < props.length; j++)
          {
            results.add(readProperty(props[j], NOT_USED));
          }
          break;

        default:
          results.add(readProperty(refs[i].getPropertyId(),
                                   refs[i].getPropertyArrayIndex()));
          break;
      }
    }

    return results.toArray(readResults);
  }

  /**
   * Read the specified range of values of a compound property.
   *
   * @param rangeReference the range reference describing the requested range.
   * @return a byte array containing the encoded range.
   */
  @Override
  public RangeData readRange(RangeReference rangeReference)
    throws RejectException
  {
    int pId = rangeReference.getPropertyId();
    for (int i = 0; i < REQUIRED_PROPS.length; i++)
    {
      if (pId == REQUIRED_PROPS[i])
      {
        return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST);
      }
    }

    for (int i = 0; i < OPTIONAL_PROPS.length; i++)
    {
      if (pId == OPTIONAL_PROPS[i])
      {
        return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST);
      }
    }

    return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.UNKNOWN_PROPERTY);
  }

  /**
   * Set the value of a property.
   *
   * @param val the PropertyValue containing the write information.
   * @return null if everything goes OK, or
   * an ErrorType describing the error if not.
   */
  @Override
  public final ErrorType writeProperty(PropertyValue val)
    throws BacnetException
  {
    getFile();
    return writeProperty(val.getPropertyId(),
                         val.getPropertyArrayIndex(),
                         val.getPropertyValue(),
                         val.getPriority());
  }

  /**
   * Add list elements.
   *
   * @param propertyValue the PropertyValue containing the propertyId,
   *                      propertyArrayIndex, and the encoded list elements.
   * @return a ChangeListError if unable to add any elements,
   * or null if ok.
   */
  @Override
  public final ChangeListError addListElements(PropertyValue propertyValue)
    throws BacnetException
  {
    getFile();
    int propertyId = propertyValue.getPropertyId();
    for (int i = 0; i < REQUIRED_PROPS.length; i++)
    {
      if (propertyId == REQUIRED_PROPS[i])
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.SERVICES,
                                                   BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                    0);
      }
    }
    for (int i = 0; i < OPTIONAL_PROPS.length; i++)
    {
      if (propertyId == OPTIONAL_PROPS[i])
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.SERVICES,
                                                   BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                    0);
      }
    }

    return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                new NErrorType(BBacnetErrorClass.PROPERTY,
                                               BBacnetErrorCode.UNKNOWN_PROPERTY),
                                0);
  }

  /**
   * Remove list elements.
   *
   * @param propertyValue the PropertyValue containing the propertyId,
   *                      propertyArrayIndex, and the encoded list elements.
   * @return a ChangeListError if unable to remove any elements,
   * or null if ok.
   */
  @Override
  public final ChangeListError removeListElements(PropertyValue propertyValue)
    throws BacnetException
  {
    getFile();
    int propertyId = propertyValue.getPropertyId();
    for (int i = 0; i < REQUIRED_PROPS.length; i++)
    {
      if (propertyId == REQUIRED_PROPS[i])
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.SERVICES,
                                                   BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                    0);
      }
    }
    for (int i = 0; i < OPTIONAL_PROPS.length; i++)
    {
      if (propertyId == OPTIONAL_PROPS[i])
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.SERVICES,
                                                   BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                    0);
      }
    }

    return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                new NErrorType(BBacnetErrorClass.PROPERTY,
                                               BBacnetErrorCode.UNKNOWN_PROPERTY),
                                0);
  }

////////////////////////////////////////////////////////////////
//  Bacnet Support
////////////////////////////////////////////////////////////////

  /**
   * Get the value of a property.
   * Subclasses with additional properties override this to check for
   * their properties.  If no match is found, call this superclass
   * method to check these properties.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @return a PropertyValue containing the encoded value or the error.
   */
  protected PropertyValue readProperty(int pId, int ndx)
  {
    if (file == null)
    {
      return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.OBJECT,
                                                              BBacnetErrorCode.TARGET_NOT_CONFIGURED));
    }

    if (pId == BBacnetPropertyIdentifier.PROPERTY_LIST)
    {
      return readPropertyList(ndx);
    }

    // Check for array index on non-array property.
    if (ndx >= 0)
    {
      return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                              BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY));
    }

    switch (pId)
    {
      case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnObjectId(getObjectId()));

      case BBacnetPropertyIdentifier.OBJECT_TYPE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(getObjectId().getObjectType()));

      case BBacnetPropertyIdentifier.OBJECT_NAME:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(getObjectName()));

      case BBacnetPropertyIdentifier.FILE_TYPE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(file.getMimeType()));

      case BBacnetPropertyIdentifier.FILE_SIZE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(file.getSize()));

      case BBacnetPropertyIdentifier.MODIFICATION_DATE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toBacnetDateTime(file.getLastModified()));

      case BBacnetPropertyIdentifier.ARCHIVE:
        boolean archive = getArchiveTime().isAfter(file.getLastModified());
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBoolean(archive));

      case BBacnetPropertyIdentifier.READ_ONLY:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBoolean(file.isReadonly()));

      case BBacnetPropertyIdentifier.FILE_ACCESS_METHOD:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(getFileAccessMethod()));

      case BBacnetPropertyIdentifier.DESCRIPTION:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(getDescription()));

      default:
        return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                BBacnetErrorCode.UNKNOWN_PROPERTY));
    }
  }

  /**
   * Set the value of a property.
   * Subclasses with additional properties override this to check for
   * their properties.  If no match is found, call this superclass
   * method to check these properties.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @param val the Asn-encoded value for the property.
   * @param pri the priority level (only used for commandable properties).
   * @throws BacnetException
   * @return null if everything goes OK, or
   * an ErrorType describing the error if not.
   */
  protected ErrorType writeProperty(int pId,
                                    int ndx,
                                    byte[] val,
                                    int pri)
    throws BacnetException
  {
    if (file == null)
    {
      return new NErrorType(BBacnetErrorClass.OBJECT,
                            BBacnetErrorCode.TARGET_NOT_CONFIGURED);
    }

    // Check for array index on non-array property.
    if (ndx >= 0 && pId != BBacnetPropertyIdentifier.PROPERTY_LIST)
    {
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY);
    }

    try
    {
      switch (pId)
      {
        case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
        case BBacnetPropertyIdentifier.OBJECT_TYPE:
        case BBacnetPropertyIdentifier.PROPERTY_LIST:
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.OBJECT_NAME:
          return BacUtil.setObjectName(this, objectName, val);

        case BBacnetPropertyIdentifier.FILE_TYPE:
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.FILE_SIZE:
          if (file.isReadonly())
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
          }

          try
          {
            if (AsnUtil.fromAsnUnsignedInt(val) == 0)
            {
              file.write(new byte[] {});
            }
            else
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            return null;
          }
          catch (IOException e)
          {
            return new NErrorType(BBacnetErrorClass.DEVICE,
                                  BBacnetErrorCode.OPERATIONAL_PROBLEM);
          }
          catch (IllegalArgumentException e)
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.VALUE_OUT_OF_RANGE);
          }

        case BBacnetPropertyIdentifier.MODIFICATION_DATE:
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.ARCHIVE:
          boolean archive = AsnUtil.fromAsnBoolean(val);
          if (archive)
          {
            set(archiveTime, BAbsTime.make(), BLocalBacnetDevice.getBacnetContext());
          }
          else
          {
            set(archiveTime, BAbsTime.DEFAULT, BLocalBacnetDevice.getBacnetContext());
          }
          return null;

        case BBacnetPropertyIdentifier.READ_ONLY:
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.FILE_ACCESS_METHOD:
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.DESCRIPTION:
          setString(description, AsnUtil.fromAsnCharacterString(val), BLocalBacnetDevice.getBacnetContext());
          return null;

        default:
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.UNKNOWN_PROPERTY);
      }
    }
    catch (AsnException e)
    {
      log.warning("AsnException writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.INVALID_DATA_TYPE);
    }
    catch (PermissionException e)
    {
      log.warning("PermissionException writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }
  }

////////////////////////////////////////////////////////////////
// Access methods
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  @Override
  public String toString(Context c)
  {
    return getObjectName() + " [" + getObjectId() + "]";
  }

  protected final BIFile getFile()
  {
    if (file == null)
    {
      return findFile();
    }
    return file;
  }

  private BIFile findFile()
  {
    try
    {
      if (!fileOrd.isEquivalentToDefaultValue(getFileOrd()))
      {
        BObject o = getFileOrd().get(this);
        if (o instanceof BIFile)
        {
          file = (BIFile) o;
        }
        else
        {
          file = null;
        }
      }
    }
    catch (Exception e)
    {
      log.warning("Unable to resolve file ord for " + this + ":" + getFileOrd() + ": " + e);
      file = null;
    }

    if (file == null)
    {
      setFaultCause("Cannot find exported file");
      setStatus(BStatus.makeFault(getStatus(), true));
    }

    return file;
  }

////////////////////////////////////////////////////////////////
// File Access
////////////////////////////////////////////////////////////////

  /**
   * Read from the file.
   *
   * @param fileStartPosition
   * @param requestedOctetCount
   * @return byte array containing the data.
   */
  public final byte[] read(int fileStartPosition,
                           int requestedOctetCount)
    throws IOException
  {
    return readFile(fileStartPosition, requestedOctetCount);
  }

  protected byte[] readFile(int fileStartPosition,
                            int requestedOctetCount)
    throws IOException
  {
    if (file == null)
    {
      return null;
    }
    eof = false;
    InputStream in = file.getInputStream();
    try
    {
      //noinspection ResultOfMethodCallIgnored
      in.skip(fileStartPosition);
      int avail = in.available();
      byte[] b;
      if (avail <= requestedOctetCount)
      {
        eof = true;
        b = new byte[avail];
      }
      else
      {
        eof = false;
        b = new byte[requestedOctetCount];
      }
      int numRead = in.read(b);
      if (log.isLoggable(Level.FINE))
      {
        log.fine("BacnetFile " + file.getFileName() + " {" + getObjectId() + "}.read(): " + requestedOctetCount + " bytes requested, " + numRead + " bytes read from file.");
      }
      if (numRead < 0)
      {
        eof = true;
      }
      return b;
    }
    finally
    {
      in.close();
    }
  }

  /**
   * Write to the file.
   * This has to use the java.io.RandomAccessFile API because the Baja
   * file API does not support the necessary functionality.
   *
   * @param fileStartPosition
   * @param fileData
   * @return -1 if ok, or BBacnetErrorCode ordinal if failed.
   */
  public final int write(int fileStartPosition,
                         byte[] fileData)
    throws IOException
  {
    return writeFile(fileStartPosition, fileData);
  }

  protected int writeFile(int fileStartPosition,
                          byte[] fileData)
    throws IOException
  {
    if (file == null)
    {
      return BBacnetErrorCode.FILE_ACCESS_DENIED;
    }
    if (file.isReadonly())
    {
      return BBacnetErrorCode.FILE_ACCESS_DENIED;
    }
    if (log.isLoggable(Level.FINE))
    {
      log.fine("BacnetFile " + file.getFileName() + " {" + getObjectId() + "}.write() :" + fileData.length + " bytes starting at " + fileStartPosition);
    }
    long len = file.getSize();
    File f = ((BLocalFileStore)file.getStore()).getLocalFile();
    RandomAccessFile out = new RandomAccessFile(f, "rw");
    try
    {
      if (fileStartPosition >= len)
      {
        // Extend file to fileStartPosition with zeroes.
        int diff = (int)(fileStartPosition - len);
        out.seek(len);
        byte[] b = new byte[diff];
        for (int i = 0; i < diff; i++) b[i] = 0;
        out.write(b);
      }
      else
      {
        if (fileStartPosition == APPEND_START_POSITION)
        {
          out.seek(len);
        }
        else if (fileStartPosition >= 0)
        {
          out.seek(fileStartPosition);
        }
        else
        {
          return BBacnetErrorCode.INVALID_FILE_START_POSITION;
        }
      }

      // Now, write the data.
      out.write(fileData);

      log.fine("File write OK");
      return FILE_WRITE_OK;
    }
    finally
    {
      out.close();
    }
  }

  /**
   * Get the file size.
   */
  public long getFileSize()
  {
    return getFile().getSize();
  }

  /**
   * Did the last read reach the end of the file?
   *
   * @return true if file read to EOF.
   */
  public final boolean isEOF()
  {
    return eof;
  }

////////////////////////////////////////////////////////////////
// Fatal Fault
////////////////////////////////////////////////////////////////

  private boolean fatalFault = false;

  /**
   * Is this component in a fatal fault condition?
   */
  @Override
  public final boolean isFatalFault()
  {
    return fatalFault;
  }

  private void checkFatalFault()
  {
    BBacnetExportTable exports = null;
    BLocalBacnetDevice local = null;
    BBacnetNetwork network = null;

    // short circuit if already in fatal fault
    if (fatalFault)
    {
      return;
    }

    // find local device
    BComplex parent = getParent();
    while (parent != null)
    {
      if (parent instanceof BBacnetExportTable)
      {
        exports = (BBacnetExportTable)parent;
      }
      else if (parent instanceof BLocalBacnetDevice)
      {
        local = (BLocalBacnetDevice)parent;
        break;
      }
      parent = parent.getParent();
    }

    // check mounted in local device
    if ((exports == null) || (local == null))
    {
      fatalFault = true;
      setFaultCause("Not under LocalBacnetDevice Export Table");
      return;
    }

    // check local device fatal fault
    if (local.isFatalFault())
    {
      fatalFault = true;
      setFaultCause("LocalDevice fault: " + local.getFaultCause());
      return;
    }

    // check mounted in network
    network = (BBacnetNetwork)local.getParent();
    if (network == null)
    {
      fatalFault = true;
      setFaultCause("Not under BacnetNetwork");
      return;
    }

    // check network fatal fault
    if (network.isFatalFault())
    {
      fatalFault = true;
      setFaultCause("Network fault: " + network.getFaultCause());
      return;
    }

    // check license
    Feature feature = network.getLicenseFeature();
    boolean serverLicensed = feature.getb("export", false);
    if (!serverLicensed)
    {
      fatalFault = true;
      setFaultCause("Server capability not licensed");
      return;
    }

    // no fatal faults
    setFaultCause("");
  }

  /**
   * Check if the file description created for backup procedure
   */
  public boolean isBackupConigFile()
  {
    return backupConigFile;
  }

  /**
   * Set as file description created for backup procedure.
   * Creation and deletion of temporary configuration files during a backup or restore
   * procedure shall not affect database revision. Setting file descriptor as
   * backup file will not increment the database revision at any time.
   * @param backupConigFile set file description as temporary backup file. By default false.
   */
  public void setBackupConigFile(boolean backupConigFile)
  {
    this.backupConigFile = backupConigFile;
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetFileDescriptor", 2);
    out.prop("fatalFault", fatalFault);
    out.prop("file", file);
    out.prop("eof", eof);
    out.prop("subscriber", subscriber);
    out.prop("oldId", oldId);
    out.prop("oldName", oldName);
    out.prop("duplicate", duplicate);
    out.endProps();
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.make(BIcon.std("file.png"), BIcon.std("badges/export.png"));

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BIFile file;
  private boolean eof = true;

  private Subscriber subscriber;
  private BBacnetObjectIdentifier oldId = null;
  private String oldName = null;
  private boolean duplicate = false;

  private boolean backupConigFile = false;

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int FILE_WRITE_OK = -1;
  public static final int APPEND_START_POSITION = -1;

  static Logger log = Logger.getLogger("bacnet.server");

  private static final int[] REQUIRED_PROPS = new int[]
    {
      BBacnetPropertyIdentifier.OBJECT_IDENTIFIER,
      BBacnetPropertyIdentifier.OBJECT_NAME,
      BBacnetPropertyIdentifier.OBJECT_TYPE,
      BBacnetPropertyIdentifier.FILE_TYPE,
      BBacnetPropertyIdentifier.FILE_SIZE,
      BBacnetPropertyIdentifier.MODIFICATION_DATE,
      BBacnetPropertyIdentifier.ARCHIVE,
      BBacnetPropertyIdentifier.READ_ONLY,
      BBacnetPropertyIdentifier.FILE_ACCESS_METHOD,
    };

  private static final int[] OPTIONAL_PROPS = new int[]
    {
      BBacnetPropertyIdentifier.DESCRIPTION
    };

////////////////////////////////////////////////////////////////
// Inner class: FileSubscriber
////////////////////////////////////////////////////////////////

  /**
   * FileSubscriber handles updating the local device's export
   * table when the server object's Object_Name property is changed.
   */
  static class FileSubscriber
    extends Subscriber
  {
    public FileSubscriber(BBacnetFileDescriptor obj)
    {
      this.obj = obj;
    }

    @Override
    public void event(BComponentEvent event)
    {
      try
      {
        if (event.getId() == BComponentEvent.PROPERTY_RENAMED)
        {
          if (event.getSlotName().equals(obj.getObjectName()))
          {
            obj.setObjectOrd(((BISpaceNode)obj.getObject()).getOrdInSpace(), null);
            obj.checkConfiguration();
          }
        }
      }
      catch (Exception e)
      {
        logger.log(Level.WARNING, "obj=" + obj.getObjectId(), e);
      }
    }

    private final BBacnetFileDescriptor obj;
  }

  private static final Logger logger = Logger.getLogger("bacnet.export");
}
