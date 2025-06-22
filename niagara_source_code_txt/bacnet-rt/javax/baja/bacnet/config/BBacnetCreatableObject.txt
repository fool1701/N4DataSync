/*
 * Copyright 2019 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BBacnetObject;
import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.*;
import javax.baja.bacnet.enums.BBacnetEventType;
import javax.baja.bacnet.enums.BBacnetNotifyType;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.ErrorException;
import javax.baja.bacnet.io.IllegalActionInitiationError;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.driver.loadable.BUploadParameters;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.status.BStatus;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.NBacnetPropertyValue;
import com.tridium.bacnet.stack.BBacnetStack;

/**
 * @author Sandipan Aich
 * @version $Revision$ $Date$
 * @creation 05 Jun 2019
 * @since Niagara 4.9
 */

@NiagaraType
@NiagaraProperty(
  name = "description",
  type = "String",
  defaultValue = "",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.DESCRIPTION, BacnetConst.ASN_CHARACTER_STRING)")
)
@NiagaraAction(
  name = "createObject"
)
public abstract class BBacnetCreatableObject extends BBacnetObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetCreatableObject(4116883605)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "description"

  /**
   * Slot for the {@code description} property.
   * @see #getDescription
   * @see #setDescription
   */
  public static final Property description = newProperty(0, "", makeFacets(BBacnetPropertyIdentifier.DESCRIPTION, BacnetConst.ASN_CHARACTER_STRING));

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

  //region Action "createObject"

  /**
   * Slot for the {@code createObject} action.
   * @see #createObject()
   */
  public static final Action createObject = newAction(0, null);

  /**
   * Invoke the {@code createObject} action.
   * @see #createObject
   */
  public void createObject() { invoke(createObject, null, null); }

  //endregion Action "createObject"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetCreatableObject.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  public void started() throws Exception
  {
    super.started();
    if(BBacnetNetwork.bacnet().getType() == BBacnetNetwork.TYPE)
    {
      int hideFlag = getFlags(createObject) | Flags.HIDDEN;
      setFlags(createObject, hideFlag);
    }
  }

  private BBacnetObjectIdentifier sendCreateObjectRequest(Array<PropertyValue> listOfInitialValues)
    throws BacnetException
  {
    BBacnetStack stack = (BBacnetStack) BBacnetNetwork.bacnet().getBacnetComm();
    BBacnetObjectIdentifier objectId = getObjectId();
    BBacnetDevice device = (BBacnetDevice) getParent().getParent();
    
    if (objectId.getInstanceNumber() < 0)
    {
      return stack.getClient()
        .createObject(device.getAddress(), objectId.getObjectType(), listOfInitialValues);
    }
    else
    {
      return stack.getClient()
        .createObject(device.getAddress(), objectId, listOfInitialValues);
    }
  }

  protected void addDescription(Array<PropertyValue> listOfInitialValues)
  {
    String desc = getDescription();
    if (desc != null && desc.length() != 0)
    {
      addProperty(BBacnetPropertyIdentifier.DESCRIPTION, description, listOfInitialValues);
    }
  }

  protected void addObjectName(Array<PropertyValue> listOfInitialValues)
  {
    String name = getObjectName();
    if (name != null && name.length() != 0)
    {
      addProperty(BBacnetPropertyIdentifier.OBJECT_NAME, objectName, listOfInitialValues);
    }
  }

  protected void addEventParameter(BBacnetEventParameter eventParameter, Array<PropertyValue> listOfInitialValues)
  {
    if (eventParameter.getChoice() != BBacnetEventType.NONE)
    {
      AsnOutputStream asnOut = new AsnOutputStream();
      eventParameter.writeAsn(asnOut);
      PropertyValue eventParametersPV = new NBacnetPropertyValue(BBacnetPropertyIdentifier.EVENT_PARAMETERS, asnOut
        .toByteArray());
      listOfInitialValues.add(eventParametersPV);
    }
  }

  protected void addEventEnable(BBacnetBitString eventEnable, Array<PropertyValue> listOfInitialValues)
  {
    PropertyValue eventEnablePV = new BBacnetPropertyValue(BBacnetPropertyIdentifier.EVENT_ENABLE, eventEnable);
    listOfInitialValues.add(eventEnablePV);

  }

  protected void addNotificationClass(BBacnetUnsigned notificationClass, Array<PropertyValue> listOfInitialValues)
  {
    long nc = notificationClass.getInt();
    if (nc > 0 && nc != BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER)
    {
      PropertyValue notificationClassPV = new BBacnetPropertyValue(BBacnetPropertyIdentifier.NOTIFICATION_CLASS, notificationClass);
      listOfInitialValues.add(notificationClassPV);
    }
  }


  protected void addLogDeviceObjectPropertyReference(BBacnetDeviceObjectPropertyReference objectPropertyReference, Array<PropertyValue> listOfInitialValues)
  {
    if (objectPropertyReference.getObjectId().getInstanceNumber() != -1)
    {
      AsnOutputStream asnOut = new AsnOutputStream();
      objectPropertyReference.writeAsn(asnOut);
      PropertyValue objectPropertyReferencePV = new NBacnetPropertyValue(BBacnetPropertyIdentifier.LOG_DEVICE_OBJECT_PROPERTY, asnOut
        .toByteArray());
      listOfInitialValues.add(objectPropertyReferencePV);
    }
  }

  protected void addObjectPropertyReference(BBacnetDeviceObjectPropertyReference objectPropertyReference, Array<PropertyValue> listOfInitialValues)
  {
    if (objectPropertyReference.getObjectId().getInstanceNumber() != -1)
    {
      AsnOutputStream asnOut = new AsnOutputStream();
      objectPropertyReference.writeAsn(asnOut);
      PropertyValue objectPropertyReferencePV = new NBacnetPropertyValue(BBacnetPropertyIdentifier.OBJECT_PROPERTY_REFERENCE, asnOut
              .toByteArray());
      listOfInitialValues.add(objectPropertyReferencePV);
    }
  }
  protected void addEventType(BEnum evType, Array<PropertyValue> listOfInitialValues)
  {
    BBacnetEventType eventType = BBacnetEventType.make(evType.getOrdinal());
    if (eventType != BBacnetEventType.none)
    {
      PropertyValue eventTypePV = new BBacnetPropertyValue(BBacnetPropertyIdentifier.EVENT_TYPE, eventType);
      listOfInitialValues.add(eventTypePV);
    }
  }

  protected void addNotifyType(BBacnetNotifyType notifyType, Array<PropertyValue> listOfInitialValues)
  {
    PropertyValue notifyTypePV = new BBacnetPropertyValue(BBacnetPropertyIdentifier.NOTIFY_TYPE, notifyType);
    listOfInitialValues.add(notifyTypePV);
  }

  protected void addRecipientist(Property recipientList, Array<PropertyValue> listOfInitialValues)
  {
    if(shouldAddListProperty(recipientList, BBacnetDestination.class))
      addProperty(BBacnetPropertyIdentifier.RECIPIENT_LIST, recipientList, listOfInitialValues);

/*    if(recipientList != null)
    {
      BValue value = get(recipientList);
      BBacnetDestination[] destinations = ((BBacnetListOf) value).getChildren(BBacnetDestination.class);
      if(destinations != null && destinations.length > 0)
      {
        addProperty(BBacnetPropertyIdentifier.RECIPIENT_LIST, recipientList, listOfInitialValues);
      }
    }*/
  }

  protected void addListOfObjectPropertyReferences(Property listOfObjectPropertyReferences, Array<PropertyValue> listOfInitialValues)
  {
    if(shouldAddListProperty(listOfObjectPropertyReferences, BBacnetDeviceObjectPropertyReference.class))
    {

      addProperty(BBacnetPropertyIdentifier.LIST_OF_OBJECT_PROPERTY_REFERENCES, listOfObjectPropertyReferences, listOfInitialValues);
    }

/*    if(listOfObjectPropertyReferences != null)
    {
      BValue value = get(listOfObjectPropertyReferences);
      BBacnetDestination[] destinations = ((BBacnetListOf) value).getChildren(BBacnetDestination.class);
      if(destinations != null && destinations.length > 0)
      {
        addProperty(BBacnetPropertyIdentifier.LIST_OF_OBJECT_PROPERTY_REFERENCES, listOfObjectPropertyReferences, listOfInitialValues);
      }
    }*/
  }


  protected void addPriorityForWriting(BBacnetUnsigned priorityForWriting, Array<PropertyValue> listOfInitialValues)
  {
    if(priorityForWriting != null)
    {
      long pow = priorityForWriting.getInt();
      if(pow > 0 && pow != BBacnetObjectIdentifier.UNCONFIGURED_INSTANCE_NUMBER)
      {
        PropertyValue priorityForWritingPV = new BBacnetPropertyValue(BBacnetPropertyIdentifier.PRIORITY_FOR_WRITING, priorityForWriting);
        listOfInitialValues.add(priorityForWritingPV);
      }
    }
  }

  protected void addScheduleDefault(Property scheduleDefault, Array<PropertyValue> listOfInitialValue)
  {
    addProperty(BBacnetPropertyIdentifier.SCHEDULE_DEFAULT, scheduleDefault, listOfInitialValue);
  }

  public void doCreateObject()
  {
    if(BBacnetNetwork.bacnet().getType() == BBacnetNetwork.TYPE)
      throw new IllegalActionInitiationError("bacnet","illegal.create.object.initiation", new Object[]{"Bacnet"});

    Array<PropertyValue> listOfInitialValues = new Array<>(PropertyValue.class);
    addInitialValues(listOfInitialValues);

    try
    {
      BBacnetObjectIdentifier objOid = sendCreateObjectRequest(listOfInitialValues);
      setObjectId(objOid);
      doUpload(null, null);
      setFaultCause("");
    }
    catch (Exception e)
    {
      if (e instanceof ErrorException)
      {
        ErrorException errorException = (ErrorException) e;
        setStatus(BStatus.fault);
        setFaultCause(errorException.toString());
        log
          .severe("Could not send the create object request :: " + errorException
            .toString());
      }
      else
      { log.severe("Could not send the create object request."); }
    }
  }

  protected void addInitialValues(Array<PropertyValue> listOfInitialValues)
  {
    addObjectName(listOfInitialValues);
    addDescription(listOfInitialValues);
    addObjectInitialValues(listOfInitialValues);
  }

  protected void addPriority(Property priority, Array<PropertyValue> listOfInitialValues)
  {
    BValue value = get(priority);
    if(value != null && value instanceof BBacnetArray)
    {
      BBacnetArray priorityArray = (BBacnetArray)value;
      if(priorityArray != null && priorityArray.getSize() > 0)
      {
        addProperty(BBacnetPropertyIdentifier.PRIORITY, priority, listOfInitialValues);
      }
    }
  }

  private <T> boolean shouldAddListProperty(Property property, Class<T> childClass)
  {
    if(property != null)
    {
      BValue value = get(property);
      if(value instanceof BBacnetListOf)
      {
        T[] destinations = ((BBacnetListOf) value).getChildren(childClass);
        if (destinations != null && destinations.length > 0)
          return true;
      }
    }
    return false;
  }

  protected void addAckRequired(Property property, Array<PropertyValue> listOfInitialValues)
  {
    addProperty(BBacnetPropertyIdentifier.ACK_REQUIRED, property, listOfInitialValues);
  }

  private void addProperty(int propertyIdentifer, Property property, Array<PropertyValue> listOfInitialValues)
  {
    BacnetPropertyData d = getPropertyData(property);
    listOfInitialValues.add(new NBacnetPropertyValue(propertyIdentifer, toEncodedValue(d, property)));
  }

  protected abstract void addObjectInitialValues(Array<PropertyValue> listOfInitialValues);
}
