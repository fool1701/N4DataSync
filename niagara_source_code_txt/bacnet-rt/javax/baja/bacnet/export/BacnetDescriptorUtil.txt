/*
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BIBacnetObjectContainer;
import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetDeviceObjectPropertyReference;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetObjectPropertyReference;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.export.extensions.BBacnetRemoteUnsignedPropertyExt;
import javax.baja.bacnet.export.extensions.BBacnetUnsignedPropertyExt;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.io.RejectException;
import javax.baja.bacnet.point.BBacnetBooleanProxyExt;
import javax.baja.bacnet.point.BBacnetEnumProxyExt;
import javax.baja.bacnet.point.BBacnetNumericProxyExt;
import javax.baja.bacnet.point.BBacnetProxyExt;
import javax.baja.bacnet.point.BBacnetStringProxyExt;
import javax.baja.bacnet.util.PropertyInfo;
import javax.baja.control.BBooleanPoint;
import javax.baja.control.BBooleanWritable;
import javax.baja.control.BControlPoint;
import javax.baja.control.BEnumPoint;
import javax.baja.control.BEnumWritable;
import javax.baja.control.BNumericPoint;
import javax.baja.control.BNumericWritable;
import javax.baja.control.BPointExtension;
import javax.baja.control.BStringPoint;
import javax.baja.control.BStringWritable;
import javax.baja.control.ext.BDiscreteTotalizerExt;
import javax.baja.history.BHistoryConfig;
import javax.baja.history.BHistoryId;
import javax.baja.history.BHistoryService;
import javax.baja.history.HistorySpaceConnection;
import javax.baja.history.ext.BHistoryExt;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BLink;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BValue;
import javax.baja.sys.Flags;
import javax.baja.sys.Knob;
import javax.baja.sys.Sys;
import javax.baja.util.BFolder;

import com.tridium.bacnet.ObjectTypeList;
import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.NBacnetPropertyReference;
import com.tridium.bacnet.asn.NBacnetPropertyValue;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.history.BBacnetBitStringTrendLogExt;
import com.tridium.bacnet.history.BBacnetBitStringTrendLogRemoteExt;
import com.tridium.bacnet.history.BBacnetBooleanTrendLogExt;
import com.tridium.bacnet.history.BBacnetBooleanTrendLogRemoteExt;
import com.tridium.bacnet.history.BBacnetEnumTrendLogExt;
import com.tridium.bacnet.history.BBacnetEnumTrendLogRemoteExt;
import com.tridium.bacnet.history.BBacnetNumericTrendLogExt;
import com.tridium.bacnet.history.BBacnetNumericTrendLogRemoteExt;
import com.tridium.bacnet.history.BBacnetStringTrendLogExt;
import com.tridium.bacnet.history.BBacnetStringTrendLogRemoteExt;
import com.tridium.bacnet.history.BBacnetTrendLogAlarmSourceExt;
import com.tridium.bacnet.history.BBacnetTrendLogRemoteExt;
import com.tridium.bacnet.history.BIBacnetTrendLogExt;
import com.tridium.bacnet.stack.server.BBacnetExportTable;

/**
 * A collection of methods for interacting with BacnetDescriptor instances.
 *
 * @author Sandipan Aich on 25/10/2018
 */
public final class BacnetDescriptorUtil
{
  // Prevent object instantiation, static utility only
  private BacnetDescriptorUtil()
  {
  }

  static BComponent parseLogDeviceObjectProperty(PropertyValue pv, BBacnetDeviceObjectPropertyReference dopr)
    throws Exception
  {
    BBacnetDeviceObjectPropertyReference deviceObjectPropertyReference;
    BComponent point = null;

    if (pv != null)
    {
      deviceObjectPropertyReference = new BBacnetDeviceObjectPropertyReference();
      deviceObjectPropertyReference.setDeviceId( BBacnetObjectIdentifier.make(BBacnetObjectType.DEVICE, -1));
      deviceObjectPropertyReference.readAsn(new AsnInputStream(pv.getPropertyValue()));
    }
    else
    {
      deviceObjectPropertyReference = dopr;
    }
    if (isValidDeviceObjectPropertyReference(deviceObjectPropertyReference))
    {
      point = findLocalOrRemotePoint(deviceObjectPropertyReference);
    }

    return point;
  }

  static boolean isValidDeviceObjectPropertyReference(BBacnetDeviceObjectPropertyReference dopr)
  {
    return dopr != null &&
      dopr.getPropertyId() >= 0 &&
      dopr.getObjectId().getInstanceNumber() >= 0 &&
      dopr.getDeviceId().getInstanceNumber() >= -1;
  }

  static boolean isLocalDeviceID(int deviceNum)
  {
    if (deviceNum == -1)
    {
      return true;
    }

    BLocalBacnetDevice localDevice = BBacnetNetwork.localDevice();
    int localDeviceNum = localDevice.getObjectId().getInstanceNumber();
    return localDeviceNum == deviceNum;
  }

  static BComponent findLocalOrRemotePoint(BBacnetDeviceObjectPropertyReference objectPropRef)
    throws Exception
  {
    int deviceNum = objectPropRef.getDeviceId().getInstanceNumber();
    if (isLocalDeviceID(deviceNum))
    {
      return findLocalPoint(objectPropRef);
    }
    else
    {
      return findRemotePoint(objectPropRef);
    }
  }

  private static BComponent findLocalPoint(BBacnetDeviceObjectPropertyReference objPropRef)
    throws Exception
  {
    return findLocalPoint(objPropRef.getObjectId(), objPropRef.getPropertyId(), objPropRef.getPropertyArrayIndex());
  }

  static BComponent findLocalPoint(BBacnetObjectPropertyReference objPropRef)
    throws Exception
  {
    return findLocalPoint(objPropRef.getObjectId(), objPropRef.getPropertyId(), objPropRef.getPropertyArrayIndex());
  }

  private static BComponent findLocalPoint(BBacnetObjectIdentifier objectId, int propertyId, int propertyArrayIndex)
      throws Exception
  {
    BIBacnetExportObject exportObject = BBacnetNetwork.localDevice().lookupBacnetObject(objectId);
    if (exportObject == null)
    {
      throw new Exception("Could not find a local BACnet export object with ID " + objectId);
    }

    BOrd exportObjectOrd = exportObject.getObjectOrd();
    if (exportObjectOrd.isNull())
    {
      throw new Exception("ObjectOrd is null for local BACnet object with ID " + objectId);
    }

    BComponent point = (BComponent) exportObjectOrd.resolve(Sys.getStation()).get();

    if (propertyId == BBacnetPropertyIdentifier.ELAPSED_ACTIVE_TIME)
    {
      point = getPointForElapsedActiveTime(objectId, propertyArrayIndex, point);
    }

    return point;
  }

  private static BComponent getPointForElapsedActiveTime(BBacnetObjectIdentifier objectId, int propertyIndex, BComponent point)
  {
    BDiscreteTotalizerExt[] extensions = point.getChildren(BDiscreteTotalizerExt.class);
    BDiscreteTotalizerExt extension;
    BControlPoint linkedPoint = null;
    if (extensions.length > 0)
    {
      //Use the first Discrete Totalizer for algorithmic reporting
      extension =  extensions[0];
      extension.setEaTimeUpdateInterval(BRelTime.make(1000));
      linkedPoint = getNumericPointLinkedToDiscreteTotExt(objectId, extension);
    }
    else
    {
      extension = addDiscreteTotalizerExtToPoint(point);
    }

    if (linkedPoint == null)
    {
      linkedPoint = addPropertyPoint(null, objectId, BBacnetPropertyIdentifier.ELAPSED_ACTIVE_TIME, propertyIndex);
      linkToNumericPoint(extension, linkedPoint);
    }

    return linkedPoint;
  }

  private static void linkToNumericPoint(BDiscreteTotalizerExt extension, BControlPoint linkedPoint)
  {
    try
    {
      BControlPoint divide;
      BValue divideCheckVar = linkedPoint.get("divide");
      if (divideCheckVar == null)
      {
        Class<?> divideClass = Sys.loadClass("kitControl", "com.tridium.kitControl.math.BDivide");
        divide = (BControlPoint)divideClass.getDeclaredConstructor().newInstance();
        divide.set("inB", new BStatusNumeric(BacnetConst.THOUSAND));
        linkedPoint.add("divide", divide);
      }
      else
      {
        divide = (BControlPoint) divideCheckVar;
      }

      BLink linkToDivide = new BLink(extension.getHandleOrd(),
                                     BDiscreteTotalizerExt.elapsedActiveTimeNumeric.getName(),
                                     "inA", true);
      divide.add(null, linkToDivide, BLocalBacnetDevice.getBacnetContext());

      BLink link = new BLink(divide.getHandleOrd(),"out",
                             BNumericWritable.in16.getName(), true);

      linkedPoint.add(null, link, BLocalBacnetDevice.getBacnetContext());
    }
    catch (ClassNotFoundException e)
    {
      removePoint(linkedPoint);
      logger.severe("Class BDivide is not found or kitControl module is not found: " + e);
    }
    catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e)
    {
      removePoint(linkedPoint);
      logger.severe("Exception while creating instance of divide: " + e);
    }
  }

  private static BDiscreteTotalizerExt addDiscreteTotalizerExtToPoint(BComponent point)
  {
    BDiscreteTotalizerExt discreteTotalizerExt = new BDiscreteTotalizerExt();
    discreteTotalizerExt.setEaTimeUpdateInterval(BRelTime.make(1000));
    point.add(DISCRETE_TOTALIZER_EXT + "?", discreteTotalizerExt);
    return discreteTotalizerExt;
  }

  private static BControlPoint getNumericPointLinkedToDiscreteTotExt(BBacnetObjectIdentifier objectId, BDiscreteTotalizerExt extension)
  {
    BControlPoint existingPoint = null;

    Knob[] knobs = extension.getKnobs(BDiscreteTotalizerExt.elapsedActiveTimeNumeric);
    ArrayList<BComponent> components = new ArrayList<>();
    for (int j = 0; j < knobs.length; j++)
    {
      components.add(knobs[j].getTargetComponent().getParent().getParentComponent());
    }
    if (components.size() > 0 )
    {
      existingPoint = (BControlPoint) findPoint(components.toArray(EMPTY_COMPONENT_ARRAY), objectId);
    }
    return existingPoint;
  }

  private static BControlPoint findRemotePoint(BBacnetDeviceObjectPropertyReference objPropRef)
  {
    BBacnetDevice device = retrieveRemoteDevice(objPropRef.getDeviceId());
    if (device == null)
    {
      return null;
    }

    BBacnetObjectIdentifier objectId = objPropRef.getObjectId();
    int propertyId = objPropRef.getPropertyId();
    int propertyIndex = objPropRef.getPropertyArrayIndex();
    BObject object = findRemotePoint(
      device,
      objectId,
      propertyId,
      propertyIndex,
      BIBacnetObjectContainer.POINT);

    if (object == null)
    {
      object = addPropertyPoint(device, objectId, propertyId, propertyIndex);
    }

    return object instanceof BControlPoint ? (BControlPoint) object : null;
  }

  private static BObject findRemotePoint(
    BBacnetDevice device,
    BBacnetObjectIdentifier objectId,
    int propertyId,
    int propertyArrayIndex,
    String objectContainer)
  {
    if (propertyId == BBacnetPropertyIdentifier.ELAPSED_ACTIVE_TIME)
    {
      BComponent[] remotePoints = device.getPoints().getChildComponents();
      BObject object = findPoint(remotePoints, objectId);
      if (object != null)
      {
        return object;
      }
    }

    return device.lookupBacnetObject(objectId, propertyId, propertyArrayIndex, objectContainer);
  }

  private static BObject findPoint(BComponent[] points, BBacnetObjectIdentifier objectId)
  {
    if (points == null)
    {
      return null;
    }

    for (BComponent point: points)
    {
      BBacnetUnsignedPropertyExt[] extensions = point.getChildren(BBacnetUnsignedPropertyExt.class);
      for (BBacnetUnsignedPropertyExt ext : extensions)
      {
        if (ext.getPropertyId() == BBacnetPropertyIdentifier.ELAPSED_ACTIVE_TIME &&
            ext.getObjectId().equivalent(objectId))
        {
          return point;
        }
      }
    }

    return null;
  }

  private static BControlPoint addPropertyPoint(
    BBacnetDevice device,
    BBacnetObjectIdentifier objectId,
    int propertyId,
    int propertyArrayIndex)
  {
    try
    {
      BControlPoint point = makePropertyPoint(device, objectId, propertyId, propertyArrayIndex);
      String name = makePointName(objectId, propertyId, propertyArrayIndex);
      if (device == null)
      {
        BComponent dynamicPointsFolder = (BComponent) BBacnetNetwork.bacnet().get(BacnetConst.DYNAMIC_POINTS_CREATED_FOR_EVENT_ENROLLMENT);
        if (dynamicPointsFolder == null)
        {
          dynamicPointsFolder = new BFolder();
          BBacnetNetwork.bacnet().add(BacnetConst.DYNAMIC_POINTS_CREATED_FOR_EVENT_ENROLLMENT, dynamicPointsFolder, Flags.HIDDEN | Flags.READONLY);
        }

        dynamicPointsFolder.add(SlotPath.escape(name), point);
      }
      else
      {
        device.getPoints().add(SlotPath.escape(name), point);
      }

      return point;
    }
    catch (Exception e)
    {
      logger.severe("Could not add point for property " + propertyId + " on device " + device + "; " + e.getMessage());
      return null;
    }
  }

  /**
   * Based on how BBacnetPointManager.Learn#toRow(Object, MgrEditRow)} sets the row's default name.
   *
   * @since Niagara 4.10u5
   * @since Niagara 4.12u2
   * @since Niagara 4.13
   */
  private static String makePointName(BBacnetObjectIdentifier objectId, int propertyId, int propertyArrayIndex)
  {
    StringBuilder pointName = new StringBuilder(objectId.toString());

    if (propertyId != BBacnetPropertyIdentifier.PRESENT_VALUE)
    {
      pointName.append('-').append(BBacnetPropertyIdentifier.tag(propertyId));
    }

    if (propertyArrayIndex > 0)
    {
      pointName.append('_').append(propertyArrayIndex);
    }

    return FORWARD_SLASH_PATTERN.matcher(pointName.toString()).replaceAll(".");
  }

  private static final Pattern FORWARD_SLASH_PATTERN = Pattern.compile("/");

  private static void removePoint(BControlPoint point)
  {
    if (null == BBacnetNetwork.bacnet().get(BacnetConst.DYNAMIC_POINTS_CREATED_FOR_EVENT_ENROLLMENT))
    {
      BBacnetNetwork.bacnet().get(BacnetConst.DYNAMIC_POINTS_CREATED_FOR_EVENT_ENROLLMENT).asComponent().remove(point.getName());
    }
  }

  private static BBacnetDevice retrieveRemoteDevice(BBacnetObjectIdentifier deviceId)
  {
    BBacnetNetwork network = BBacnetNetwork.bacnet();
    BBacnetDevice device = network.lookupDeviceById(deviceId);
    if (device == null)
    {
      device = addDevice(deviceId.getInstanceNumber());
    }

    return device;
  }

  private static BBacnetDevice addDevice(int instanceNum)
  {
    BBacnetObjectIdentifier id = BBacnetObjectIdentifier.make(BBacnetObjectType.DEVICE, instanceNum);

    BBacnetDevice device = new BBacnetDevice();
    device.setObjectId(id, null);
    BBacnetNetwork.bacnet().add(null, device);
    return device;
  }

  private static BControlPoint addPointForElapsedActiveTime(BBacnetObjectIdentifier bOid, boolean isLocal)
  {
    BControlPoint point = new BNumericWritable();
    BPointExtension extension = null;
    if (isLocal)
    {
      extension = new BBacnetUnsignedPropertyExt(bOid, BBacnetPropertyIdentifier.ELAPSED_ACTIVE_TIME);
    }
    else
    {
      extension = new BBacnetRemoteUnsignedPropertyExt(bOid, BBacnetPropertyIdentifier.ELAPSED_ACTIVE_TIME);
    }
    point.add("ElapsedActiveTimeExtension?", extension);
    return point;
  }

  private static BControlPoint makePropertyPoint(
    BBacnetDevice device,
    BBacnetObjectIdentifier objectId,
    int propertyId,
    int propertyArrayIndex)
      throws BacnetException
  {
    if (propertyId == BBacnetPropertyIdentifier.ELAPSED_ACTIVE_TIME)
    {
      return addPointForElapsedActiveTime(objectId, device == null);
    }

    int objectType = objectId.getObjectType();
    PropertyInfo propInfo = device.getPropertyInfo(objectType, propertyId);
    if (propInfo == null)
    {
      throw new BacnetException(
        "BACnet property information not found when making a property point" +
        "; object type: " + BBacnetObjectType.tag(objectType) +
        ", property ID: " + BBacnetPropertyIdentifier.tag(propertyId));
    }

    BControlPoint point = makePointForPropertyInfo(objectType, propInfo);

    BBacnetProxyExt ext = (BBacnetProxyExt) point.getProxyExt();
    ext.setDeviceFacets((BFacets) point.getFacets().newCopy());
    ext.setDataType(propInfo.getDataType());
    ext.setObjectId(objectId);
    ext.setPropertyId(BDynamicEnum.make(BBacnetPropertyIdentifier.make(propertyId)));
    ext.setPropertyArrayIndex(propertyArrayIndex);
    ext.setEnabled(true);

    return point;
  }

  private static BControlPoint makePointForPropertyInfo(int objectType, PropertyInfo propInfo)
    throws BacnetException
  {
    switch (propInfo.getAsnType())
    {
      case BacnetConst.ASN_NULL:
        return makeBacnetStringWritable();

      case BacnetConst.ASN_BOOLEAN:
        return makeBacnetBooleanWritable();

      case BacnetConst.ASN_UNSIGNED:
        return isMultiStatePresentValue(propInfo.getId(), objectType) ?
          makeBacnetEnumWritable() :
          makeBacnetNumericWritable();

      case BacnetConst.ASN_INTEGER:
      case BacnetConst.ASN_REAL:
      case BacnetConst.ASN_DOUBLE:
        return makeBacnetNumericWritable();

      case BacnetConst.ASN_OCTET_STRING:
      case BacnetConst.ASN_CHARACTER_STRING:
      case BacnetConst.ASN_BIT_STRING:
        return makeBacnetStringWritable();

      case BacnetConst.ASN_ENUMERATED:
        return propInfo.getType().equals("bacnet:BacnetBinaryPv") ?
          makeBacnetBooleanWritable() :
          makeBacnetEnumWritable();

      case BacnetConst.ASN_DATE:
      case BacnetConst.ASN_TIME:
      case BacnetConst.ASN_OBJECT_IDENTIFIER:
      case BacnetConst.ASN_CONSTRUCTED_DATA:
      case BacnetConst.ASN_BACNET_ARRAY:
      case BacnetConst.ASN_BACNET_LIST:
      case BacnetConst.ASN_ANY:
      case BacnetConst.ASN_CHOICE:
      case BacnetConst.ASN_UNKNOWN_PROPRIETARY:
        return makeBacnetStringWritable();

      default:
        throw new BacnetException("BACnet property type " + BBacnetPropertyIdentifier.tag(objectType)
          + " is not supported when making a property point");
    }
  }

  private static BBooleanWritable makeBacnetBooleanWritable()
  {
    BBooleanWritable booleanWritable = new BBooleanWritable();
    booleanWritable.setProxyExt(new BBacnetBooleanProxyExt());
    return booleanWritable;
  }

  private static BNumericWritable makeBacnetNumericWritable()
  {
    BNumericWritable numericWritable = new BNumericWritable();
    numericWritable.setProxyExt(new BBacnetNumericProxyExt());
    return numericWritable;
  }

  private static BEnumWritable makeBacnetEnumWritable()
  {
    BEnumWritable enumWritable = new BEnumWritable();
    enumWritable.setProxyExt(new BBacnetEnumProxyExt());
    return enumWritable;
  }

  private static BStringWritable makeBacnetStringWritable()
  {
    BStringWritable stringWritable = new BStringWritable();
    stringWritable.setProxyExt(new BBacnetStringProxyExt());
    return stringWritable;
  }

  private static boolean isMultiStatePresentValue(int propertyId, int objectType)
  {
    return propertyId == BBacnetPropertyIdentifier.PRESENT_VALUE &&
           (objectType == BBacnetObjectType.MULTI_STATE_INPUT ||
            objectType == BBacnetObjectType.MULTI_STATE_OUTPUT ||
            objectType == BBacnetObjectType.MULTI_STATE_VALUE);
  }

  static boolean isEqual(BBacnetDeviceObjectPropertyReference dopr1 , BBacnetDeviceObjectPropertyReference dopr2)
  {
    if (dopr1 == null && dopr2 == null)
    {
      //Both are null values
      return true;
    }

    if (dopr1 == null || dopr2 == null)
    {
      //Only one is a null value, can't be equal
      return false;
    }

    if (dopr1.isNull() && dopr2.isNull())
    {
      //Both are logically null
      return true;
    }

    if ((dopr1.getDeviceId().getInstanceNumber() == dopr2.getDeviceId().getInstanceNumber()) &&
        (dopr1.getObjectId().getObjectType() == dopr2.getObjectId().getObjectType()) &&
        (dopr1.getObjectId().getInstanceNumber() == dopr2.getObjectId().getInstanceNumber()) &&
        (dopr1.getPropertyId() == dopr2.getPropertyId()) &&
        (dopr1.getPropertyArrayIndex() == dopr2.getPropertyArrayIndex()))
    {
      //Items are field-wise equal
      return true;
    }

    //Must not be equal
    return false;
  }

  static boolean areTrendLogAndPointCompatible(
    BControlPoint point,
    BIBacnetTrendLogExt trendLogExt,
    BBacnetDeviceObjectPropertyReference objPropRef)
  {
    PropertyInfo propInfo = ObjectTypeList.getInstance().getPropertyInfo(objPropRef.getObjectId().getObjectType(), objPropRef.getPropertyId());
    if (propInfo != null && propInfo.isBitString())
    {
      return trendLogExt instanceof  BBacnetBitStringTrendLogExt || trendLogExt instanceof BBacnetBitStringTrendLogRemoteExt;
    }

    if (point instanceof BNumericPoint)
    {
      return trendLogExt instanceof BBacnetNumericTrendLogExt || trendLogExt instanceof BBacnetNumericTrendLogRemoteExt;
    }

    if (point instanceof BStringPoint)
    {
      return trendLogExt instanceof BBacnetStringTrendLogExt || trendLogExt instanceof BBacnetStringTrendLogRemoteExt;
    }

    if (point instanceof BBooleanPoint)
    {
      return trendLogExt instanceof BBacnetBooleanTrendLogExt || trendLogExt instanceof BBacnetBooleanTrendLogRemoteExt;
    }

    if (point instanceof BEnumPoint)
    {
      return trendLogExt instanceof BBacnetEnumTrendLogExt || trendLogExt instanceof BBacnetEnumTrendLogRemoteExt;
    }

    return false;
  }

  static BIBacnetTrendLogExt copy(BIBacnetExportObject descriptor, BBacnetDeviceObjectPropertyReference dopr, PropertyValue[] initialPVs)
    throws BacnetException
  {
    try
    {
      int objectType = descriptor.getObjectId().getObjectType();
      switch (objectType)
      {
        case BBacnetObjectType.TREND_LOG:
          return copyTrendLogProperties(descriptor, dopr, initialPVs);
      }
    }
    catch (BacnetException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      logger.severe("Could not copy the trend log ext: " + e);
    }

    return null;
  }

  private static BIBacnetTrendLogExt copyTrendLogProperties(BIBacnetExportObject descriptor, BBacnetDeviceObjectPropertyReference dopr, PropertyValue[] initialPVs)
    throws Exception
  {
    BIBacnetTrendLogExt trendLogExt = decideTrendLogExtension(dopr);

    BComponent component = parseLogDeviceObjectProperty(null, dopr);
    BControlPoint point = (component instanceof BControlPoint) ? (BControlPoint) component : null;

    if (point != null && trendLogExt != null )
    {
      BHistoryExt historyExt = (BHistoryExt)trendLogExt;
      deleteOrd(descriptor);
      point.add("TrendLog" + descriptor.getObjectId().getInstanceNumber(), (BComponent)trendLogExt);

      BOrd newOrd = ((BComponent)trendLogExt).getHandleOrd();
      descriptor.setObjectOrd(newOrd, null);
      ((BBacnetTrendLogDescriptor)descriptor).getLog(true);

      for (PropertyValue pv : initialPVs)
      {
        if (pv instanceof NErrorType)
        {
          continue;
        }

        byte[] value = pv.getPropertyValue();
        int propertyId = pv.getPropertyId();
        PropertyValue bpv = new NBacnetPropertyValue(propertyId, value);
        descriptor.writeProperty(bpv);
      }
    }
    return trendLogExt;
  }

  private static void deleteOrd(BIBacnetExportObject descriptor)
  {
    BOrd ord = descriptor.getObjectOrd();
    if (!ord.isNull())
    {
      BComponent tle = ord.resolve(Sys.getStation()).get().asComponent();
      BComponent parent = tle.getParent().asComponent();
      parent.remove(tle.getName());
    }
  }

  static void removeHistory(BIBacnetExportObject descriptor, boolean rename)
  {
    BOrd ord = descriptor.getObjectOrd();
    if (!ord.isNull())
    {
      BComponent tle = ord.resolve(Sys.getStation()).get().asComponent();
      BHistoryExt historyExt = ((BHistoryExt) tle);
      BHistoryConfig config = historyExt.getHistoryConfig();
      BHistoryService historyService = (BHistoryService) Sys.getService(BHistoryService.TYPE);
      try (HistorySpaceConnection conn = historyService.getDatabase().getConnection(BLocalBacnetDevice.getBacnetContext()))
      {
        BHistoryId historyId = config.getId();

        if (rename)
        {
          // Here has to be the code to rename the existing log table.
          // This code needs to be removed once we decide on the renaming strategy.
          removeHistory(descriptor, false);
        }
        else
        {
          if (conn.exists(historyId))
          {
            conn.clearAllRecords(config.getId());
          }
        }
      }
    }
  }

  static PropertyValue[] getValuesWrittenToTrendExtension(BIBacnetExportObject descriptor)
    throws RejectException
  {
    return descriptor.readPropertyMultiple(new NBacnetPropertyReference[]
      {
        new NBacnetPropertyReference(BBacnetPropertyIdentifier.START_TIME),
        new NBacnetPropertyReference(BBacnetPropertyIdentifier.STOP_TIME),
        new NBacnetPropertyReference(BBacnetPropertyIdentifier.CLIENT_COV_INCREMENT),
        new NBacnetPropertyReference(BBacnetPropertyIdentifier.COV_RESUBSCRIPTION_INTERVAL),
        new NBacnetPropertyReference(BBacnetPropertyIdentifier.LOG_INTERVAL),
        new NBacnetPropertyReference(BBacnetPropertyIdentifier.STOP_WHEN_FULL),
        new NBacnetPropertyReference(BBacnetPropertyIdentifier.RECORD_COUNT),
        new NBacnetPropertyReference(BBacnetPropertyIdentifier.NOTIFY_TYPE),
        new NBacnetPropertyReference(BBacnetPropertyIdentifier.NOTIFICATION_CLASS),
        new NBacnetPropertyReference(BBacnetPropertyIdentifier.NOTIFICATION_THRESHOLD),
        new NBacnetPropertyReference(BBacnetPropertyIdentifier.EVENT_ENABLE)
      });
  }

  static BIBacnetTrendLogExt decideTrendLogExtension(BBacnetDeviceObjectPropertyReference dopr)
    throws BacnetException
  {
    int propertyId = dopr.getPropertyId();

    BIBacnetTrendLogExt trendLog = null;

    if (propertyId == BBacnetPropertyIdentifier.PRESENT_VALUE)
    {
      trendLog = decideTrendLogExtensionOnPresentValue(dopr);
    }
    else
    {
      trendLog = decideTrendLogExtensionOnOtherProperties(dopr);
    }

    if (trendLog == null)
    {
      throw new BacnetException("Could not copy the trend log ext");
    }

    BBacnetTrendLogAlarmSourceExt almExt = new BBacnetTrendLogAlarmSourceExt();
    ((BComponent)trendLog).add(almExt.getName(), almExt);

    return trendLog;
  }

  private static BIBacnetTrendLogExt decideTrendLogExtensionOnOtherProperties(BBacnetDeviceObjectPropertyReference dopr)
  {
    BBacnetNetwork network = BBacnetNetwork.bacnet();
    BBacnetObjectIdentifier deviceId = dopr.getDeviceId();
    BBacnetDevice device = network.lookupDeviceById(deviceId);

    int deviceInstanceNumber = deviceId.getInstanceNumber();
    BBacnetObjectIdentifier objectId = dopr.getObjectId();
    int objectType = dopr.getObjectId().getObjectType();
    int arrayIndex = dopr.getPropertyArrayIndex();

    int propertyId = dopr.getPropertyId();
    boolean isLocal = isLocalDeviceID(deviceInstanceNumber);

    switch (propertyId)
    {
      case BBacnetPropertyIdentifier.OUT_OF_SERVICE:
        return (!isLocal) ? new BBacnetBooleanTrendLogRemoteExt(device, objectId, propertyId, BacnetConst.NOT_USED) : null;
      case BBacnetPropertyIdentifier.PRIORITY_ARRAY:
        switch (objectType)
        {
          case BBacnetObjectType.ANALOG_OUTPUT:
            return (!isLocal) ? new BBacnetNumericTrendLogRemoteExt(device, objectId, propertyId, arrayIndex) : null;

          case BBacnetObjectType.BINARY_OUTPUT:
            return (!isLocal) ? new BBacnetBooleanTrendLogRemoteExt(device, objectId, propertyId, arrayIndex) : null;

          case BBacnetObjectType.CHARACTER_STRING_VALUE:
            return (!isLocal) ? new BBacnetStringTrendLogRemoteExt(device, objectId, propertyId, arrayIndex) : null;

          case BBacnetObjectType.MULTI_STATE_OUTPUT:
            return (!isLocal) ? new BBacnetEnumTrendLogRemoteExt(device, objectId, propertyId, arrayIndex) : null;
        }
        break;

      case BBacnetPropertyIdentifier.STATUS_FLAGS:
        return (!isLocal) ? new BBacnetBitStringTrendLogRemoteExt(device, objectId, propertyId, arrayIndex) : new BBacnetBitStringTrendLogExt();
    }

    return null;
  }

  private static BIBacnetTrendLogExt decideTrendLogExtensionOnPresentValue(BBacnetDeviceObjectPropertyReference dopr)
  {
    BBacnetObjectIdentifier objectId = dopr.getObjectId();
    int objectType = objectId.getObjectType();
    int propertyId = dopr.getPropertyId();
    int arrayIndex = dopr.getPropertyArrayIndex();

    BBacnetObjectIdentifier deviceId = dopr.getDeviceId();
    int deviceInstanceNumber = deviceId.getInstanceNumber();
    boolean isLocal = isLocalDeviceID(deviceInstanceNumber);
    BIBacnetTrendLogExt trendLog = null;

    if (isLocal)
    {
      switch (objectType)
      {
        case BBacnetObjectType.ANALOG_INPUT:
        case BBacnetObjectType.ANALOG_OUTPUT:
        case BBacnetObjectType.ANALOG_VALUE:
        case BBacnetObjectType.INTEGER_VALUE:
        case BBacnetObjectType.POSITIVE_INTEGER_VALUE:
          trendLog = new BBacnetNumericTrendLogExt();
          break;

        case BBacnetObjectType.BINARY_INPUT:
        case BBacnetObjectType.BINARY_OUTPUT:
        case BBacnetObjectType.BINARY_VALUE:
          trendLog = new BBacnetBooleanTrendLogExt();
          break;

        case BBacnetObjectType.CHARACTER_STRING_VALUE:
          trendLog = new BBacnetStringTrendLogExt();
          break;

        case BBacnetObjectType.MULTI_STATE_INPUT:
        case BBacnetObjectType.MULTI_STATE_OUTPUT:
        case BBacnetObjectType.MULTI_STATE_VALUE:
          trendLog = new BBacnetEnumTrendLogExt();
          break;
      }
    }
    else
    {
      BBacnetNetwork network = BBacnetNetwork.bacnet();
      BBacnetDevice device = network.lookupDeviceById(deviceId);
      switch (objectType)
      {
        case BBacnetObjectType.ANALOG_INPUT:
        case BBacnetObjectType.ANALOG_OUTPUT:
        case BBacnetObjectType.ANALOG_VALUE:
        case BBacnetObjectType.INTEGER_VALUE:
        case BBacnetObjectType.POSITIVE_INTEGER_VALUE:
          trendLog = new BBacnetNumericTrendLogRemoteExt(device, objectId, propertyId, arrayIndex);
          break;

        case BBacnetObjectType.BINARY_INPUT:
        case BBacnetObjectType.BINARY_OUTPUT:
        case BBacnetObjectType.BINARY_VALUE:
          trendLog = new BBacnetBooleanTrendLogRemoteExt(device, objectId, propertyId, arrayIndex);
          break;

        case BBacnetObjectType.CHARACTER_STRING_VALUE:
          trendLog = new BBacnetStringTrendLogRemoteExt(device, objectId, propertyId, arrayIndex);
          break;

        case BBacnetObjectType.MULTI_STATE_INPUT:
        case BBacnetObjectType.MULTI_STATE_OUTPUT:
        case BBacnetObjectType.MULTI_STATE_VALUE:
          trendLog = new BBacnetEnumTrendLogRemoteExt(device, objectId, propertyId, arrayIndex);
          break;
      }
    }

    return trendLog;
  }

  public static boolean isGenericTrendLogExtension(BIBacnetTrendLogExt ext)
  {
    if ((ext instanceof BBacnetNumericTrendLogExt) ||
        (ext instanceof BBacnetStringTrendLogExt) ||
        (ext instanceof BBacnetEnumTrendLogExt) ||
        (ext instanceof BBacnetBooleanTrendLogExt) ||
        (ext instanceof BBacnetTrendLogRemoteExt))
    {
      return true;
    }

    return false;
  }

  /**
   * This method calculates the next oid that can be used by a dynamic object for the particular object type.
   *
   * @param objectType The type of object that needs to be created
   * @return BBacnetObjectIdentifier the next oid to be used for dynamic object creation
   */
  public static BBacnetObjectIdentifier nextObjectIdentifier(int objectType)
  {
    BBacnetExportTable et = exportTable();
    BBacnetObjectIdentifier oid;
    for (int i = 0; i < BBacnetObjectIdentifier.MAX_INSTANCE_NUMBER; i++)
    {
      oid = BBacnetObjectIdentifier.make(objectType, i);
      if (et.byObjectId(oid) == null)
      {
        return oid;
      }
    }

    return null;
  }

  /**
   * Simple utility to ge the exportTable of bacnet network
   *
   * @return The exportTable
   */
  public static BBacnetExportTable exportTable()
  {
    return ((BBacnetExportTable)BBacnetNetwork.localDevice().getExportTable());
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final Logger logger = Logger.getLogger("bacnet.export.object.util");
  private static final BComponent[] EMPTY_COMPONENT_ARRAY = new BComponent[0];

  private static final String DISCRETE_TOTALIZER_EXT = "DiscreteTotalizerExtension";
}
