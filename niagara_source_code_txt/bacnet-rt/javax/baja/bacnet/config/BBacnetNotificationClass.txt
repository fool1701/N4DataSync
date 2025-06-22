/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.bacnet.datatypes.*;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.driver.loadable.BUploadParameters;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.*;

import com.tridium.bacnet.datatypes.BNcRecipientList;



/**
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 24 Jun 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.NOTIFICATION_CLASS)",
  flags = Flags.SUMMARY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER)"),
  override = true
)
@NiagaraProperty(
  name = "objectType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetObjectType.NOTIFICATION_CLASS, BEnumRange.make(BBacnetObjectType.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED)"),
  override = true
)
@NiagaraProperty(
  name = "notificationClass",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.DEFAULT",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.NOTIFICATION_CLASS, ASN_UNSIGNED)")
)
@NiagaraProperty(
  name = "priority",
  type = "BBacnetArray",
  defaultValue = "new BBacnetArray(BBacnetUnsigned.TYPE, 3)",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PRIORITY, ASN_BACNET_ARRAY)")
)
@NiagaraProperty(
  name = "ackRequired",
  type = "BBacnetBitString",
  defaultValue = "BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength(\"BacnetEventTransitionBits\"))",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.ACK_REQUIRED, ASN_BIT_STRING, BacnetBitStringUtil.BACNET_EVENT_TRANSITION_BITS_MAP)")
)
@NiagaraProperty(
  name = "recipientList",
  type = "BBacnetListOf",
  defaultValue = "new BNcRecipientList(BBacnetDestination.TYPE)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.RECIPIENT_LIST, ASN_BACNET_LIST)")
)
/*
 add a destination to the notification class's recipient list.
 */
@NiagaraAction(
  name = "addDestination",
  parameterType = "BBacnetDestination",
  defaultValue = "new BBacnetDestination()"
)
/*
 remove a destination from the notification class's recipient list.
 */
@NiagaraAction(
  name = "removeDestination",
  parameterType = "BBacnetDestination",
  defaultValue = "new BBacnetDestination()"
)
/*
 remove all destinations with this recipient
 from the notification class's recipient list.
 */
@NiagaraAction(
  name = "removeRecipient",
  parameterType = "BBacnetRecipient",
  defaultValue = "new BBacnetRecipient()"
)
public class BBacnetNotificationClass
  extends BBacnetCreatableObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetNotificationClass(1992939271)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.SUMMARY, BBacnetObjectIdentifier.make(BBacnetObjectType.NOTIFICATION_CLASS), makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER));

  //endregion Property "objectId"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetObjectType.NOTIFICATION_CLASS, BEnumRange.make(BBacnetObjectType.TYPE)), makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED));

  //endregion Property "objectType"

  //region Property "notificationClass"

  /**
   * Slot for the {@code notificationClass} property.
   * @see #getNotificationClass
   * @see #setNotificationClass
   */
  public static final Property notificationClass = newProperty(Flags.READONLY, BBacnetUnsigned.DEFAULT, makeFacets(BBacnetPropertyIdentifier.NOTIFICATION_CLASS, ASN_UNSIGNED));

  /**
   * Get the {@code notificationClass} property.
   * @see #notificationClass
   */
  public BBacnetUnsigned getNotificationClass() { return (BBacnetUnsigned)get(notificationClass); }

  /**
   * Set the {@code notificationClass} property.
   * @see #notificationClass
   */
  public void setNotificationClass(BBacnetUnsigned v) { set(notificationClass, v, null); }

  //endregion Property "notificationClass"

  //region Property "priority"

  /**
   * Slot for the {@code priority} property.
   * @see #getPriority
   * @see #setPriority
   */
  public static final Property priority = newProperty(Flags.READONLY, new BBacnetArray(BBacnetUnsigned.TYPE, 3), makeFacets(BBacnetPropertyIdentifier.PRIORITY, ASN_BACNET_ARRAY));

  /**
   * Get the {@code priority} property.
   * @see #priority
   */
  public BBacnetArray getPriority() { return (BBacnetArray)get(priority); }

  /**
   * Set the {@code priority} property.
   * @see #priority
   */
  public void setPriority(BBacnetArray v) { set(priority, v, null); }

  //endregion Property "priority"

  //region Property "ackRequired"

  /**
   * Slot for the {@code ackRequired} property.
   * @see #getAckRequired
   * @see #setAckRequired
   */
  public static final Property ackRequired = newProperty(0, BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength("BacnetEventTransitionBits")), makeFacets(BBacnetPropertyIdentifier.ACK_REQUIRED, ASN_BIT_STRING, BacnetBitStringUtil.BACNET_EVENT_TRANSITION_BITS_MAP));

  /**
   * Get the {@code ackRequired} property.
   * @see #ackRequired
   */
  public BBacnetBitString getAckRequired() { return (BBacnetBitString)get(ackRequired); }

  /**
   * Set the {@code ackRequired} property.
   * @see #ackRequired
   */
  public void setAckRequired(BBacnetBitString v) { set(ackRequired, v, null); }

  //endregion Property "ackRequired"

  //region Property "recipientList"

  /**
   * Slot for the {@code recipientList} property.
   * @see #getRecipientList
   * @see #setRecipientList
   */
  public static final Property recipientList = newProperty(0, new BNcRecipientList(BBacnetDestination.TYPE), makeFacets(BBacnetPropertyIdentifier.RECIPIENT_LIST, ASN_BACNET_LIST));

  /**
   * Get the {@code recipientList} property.
   * @see #recipientList
   */
  public BBacnetListOf getRecipientList() { return (BBacnetListOf)get(recipientList); }

  /**
   * Set the {@code recipientList} property.
   * @see #recipientList
   */
  public void setRecipientList(BBacnetListOf v) { set(recipientList, v, null); }

  //endregion Property "recipientList"

  //region Action "addDestination"

  /**
   * Slot for the {@code addDestination} action.
   * add a destination to the notification class's recipient list.
   * @see #addDestination(BBacnetDestination parameter)
   */
  public static final Action addDestination = newAction(0, new BBacnetDestination(), null);

  /**
   * Invoke the {@code addDestination} action.
   * add a destination to the notification class's recipient list.
   * @see #addDestination
   */
  public void addDestination(BBacnetDestination parameter) { invoke(addDestination, parameter, null); }

  //endregion Action "addDestination"

  //region Action "removeDestination"

  /**
   * Slot for the {@code removeDestination} action.
   * remove a destination from the notification class's recipient list.
   * @see #removeDestination(BBacnetDestination parameter)
   */
  public static final Action removeDestination = newAction(0, new BBacnetDestination(), null);

  /**
   * Invoke the {@code removeDestination} action.
   * remove a destination from the notification class's recipient list.
   * @see #removeDestination
   */
  public void removeDestination(BBacnetDestination parameter) { invoke(removeDestination, parameter, null); }

  //endregion Action "removeDestination"

  //region Action "removeRecipient"

  /**
   * Slot for the {@code removeRecipient} action.
   * remove all destinations with this recipient
   * from the notification class's recipient list.
   * @see #removeRecipient(BBacnetRecipient parameter)
   */
  public static final Action removeRecipient = newAction(0, new BBacnetRecipient(), null);

  /**
   * Invoke the {@code removeRecipient} action.
   * remove all destinations with this recipient
   * from the notification class's recipient list.
   * @see #removeRecipient
   */
  public void removeRecipient(BBacnetRecipient parameter) { invoke(removeRecipient, parameter, null); }

  //endregion Action "removeRecipient"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetNotificationClass.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
  /*@ $javax.baja.bacnet.config.BBacnetNotificationClass(2045107987)1.0$ @*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBacnetNotificationClass()
  {
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

//  /**
//   * Overridden here to provide the default value for the
//   * Set action.
//   */
//  public BValue getActionParameterDefault(Action action)
//  {
//    if (action == addDestination)
//    {
//      BBacnetDestination dest = new BBacnetDestination();
//      dest.getRecipient().setRecipient(((BBacnetNetwork)getNetwork()).getLocalDevice().getObjectId());
//      dest.setProcessIdentifier(BBacnetUnsigned.make(((BBacnetDevice)getDevice()).getAlarms().getNiagaraProcessId()));
//      return dest;
//    }
//    return super.getActionParameterDefault(action);
//  }


////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Add a destination to the recipient list.
   */
  public void doAddDestination(BBacnetDestination dest)
  {
    getRecipientList().addListElement(dest, null);
  }

  /**
   * Remove a destination from the recipient list.
   */
  public void doRemoveDestination(BBacnetDestination dest)
  {
    getRecipientList().removeListElement(dest, null);
  }

  /**
   * Remove all destinations for a particular recipient from the recipient list.
   */
  public void doRemoveRecipient(BBacnetRecipient recip)
  {
    network().postAsync(new NCRemoveRecipientRequest(recip));
    upload(new BUploadParameters());
  }

  //////////////////////////////////////////////////////////////
  //  Inner Class: NCRemoveRecipientRequest
  //////////////////////////////////////////////////////////////
  class NCRemoveRecipientRequest
    implements Runnable
  {
    NCRemoveRecipientRequest(BBacnetRecipient recip)
    {
      this.recip = recip;
    }

    public void run()
    {
      SlotCursor<Property> sc = getRecipientList().getProperties();
      while (sc.next(BBacnetDestination.class))
      {
        BBacnetDestination d = (BBacnetDestination) sc.get();
        if (d.getRecipient().equivalent(recip))
        {
          getRecipientList().removeListElement(d, null);
        }
      }
    }

    public BBacnetRecipient recip;
  }

  //////////////////////////////////////////////////////////////
  //  Overrides
  //////////////////////////////////////////////////////////////
  protected void addObjectInitialValues(Array<PropertyValue> listOfInitialValues)
  {
    addPriority(priority, listOfInitialValues);
    addAckRequired(ackRequired, listOfInitialValues);
    addRecipientist( recipientList, listOfInitialValues);
  }

}
