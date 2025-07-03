/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.agent.BIAgent;
import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.io.ChangeListError;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.PropertyReference;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.io.RangeData;
import javax.baja.bacnet.io.RangeReference;
import javax.baja.bacnet.io.RejectException;
import javax.baja.control.BControlPoint;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BComponentEventMask;
import javax.baja.sys.BInterface;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Slot;
import javax.baja.sys.Subscriber;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.stack.transport.BBacnetTransportLayer;

/**
 * BIBacnetExportObject is the interface implemented by all objects
 * that export Niagara objects as Bacnet objects.
 *
 * @author Craig Gemmill on 31 Jul 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
public interface BIBacnetExportObject
  extends BInterface,
          BIAgent,
          BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BIBacnetExportObject(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:32 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIBacnetExportObject.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Support methods
////////////////////////////////////////////////////////////////

  /**
   * Get the parent.  Implemented by BComplex.
   *
   * @return the parent of this object.
   */
  BComplex getParent();

  /**
   * Get the exported object.
   *
   * @return the actual exported object by resolving the object ord.
   */
  BObject getObject();

  /**
   * Get the BOrd to the exported object.
   *
   * @return a BOrd resolving to the actual exported object.
   */
  BOrd getObjectOrd();

  /**
   * Set the BOrd to the exported object.
   *
   * @param objectOrd
   */
  void setObjectOrd(BOrd objectOrd, Context cx);

  /**
   * Get the object's export status.
   *
   * @return true if the object is properly exported.
   */
  BStatus getStatus();

  /**
   * Is the object in fatal fault?
   * Fatal faults require a restart to correct.
   * If an object is not in fatal fault, it is included
   * in the local device's object list.
   *
   * @return true if the object is in fatal fault.
   */
  boolean isFatalFault();

  /**
   * Check the object configuration.
   * Checks that must be done:
   * - fatal fault
   * - object ord ok
   * - objectId valid
   * - any object-specific configuration (e.g., MS state=0)
   * - attempt to export object
   * - perform validation of value
   * Set reliability, status, and faultCause to appropriate
   * values, where applicable.
   */
  void checkConfiguration();

////////////////////////////////////////////////////////////////
// Bacnet Property Access
////////////////////////////////////////////////////////////////

  /**
   * Get the Object_Identifier property.
   */
  BBacnetObjectIdentifier getObjectId();

  /**
   * Set the Object_Identifier property.
   */
  void setObjectId(BBacnetObjectIdentifier objectId);

  /**
   * Get the Object_Name property.
   */
  String getObjectName();

  /**
   * Set the Object_Name property.
   */
  void setObjectName(String objectName);

  /**
   * Get the PropertyList property.
   */
  int[] getPropertyList();

////////////////////////////////////////////////////////////////
// Bacnet Request Execution
////////////////////////////////////////////////////////////////

  /**
   * Get the value of a property.
   *
   * @param propertyReference the PropertyReference containing id and index.
   * @return a PropertyValue containing the encoded value or the error.
   */
  PropertyValue readProperty(PropertyReference propertyReference)
    throws RejectException;

  /**
   * Read the value of multiple Bacnet properties.
   *
   * @param propertyReferences the list of property references.
   * @return an array of PropertyValues.
   */
  PropertyValue[] readPropertyMultiple(PropertyReference[] propertyReferences)
    throws RejectException;

  /**
   * Read the specified range of values of a compound property.
   *
   * @param rangeReference the range reference describing the requested range.
   * @return a byte array containing the encoded range.
   */
  RangeData readRange(RangeReference rangeReference)
    throws RejectException;

  /**
   * Set the value of a property.
   *
   * @param propertyValue the PropertyValue containing the write information.
   * @return null if everything goes OK, or
   * an ErrorType describing the error if not.
   */
  ErrorType writeProperty(PropertyValue propertyValue)
    throws BacnetException;

  /**
   * Add list elements.
   *
   * @param propertyValue the PropertyValue containing the propertyId,
   *                      propertyArrayIndex, and the encoded list elements.
   * @return a ChangeListError if unable to add any elements,
   * or null if ok.
   */
  ChangeListError addListElements(PropertyValue propertyValue)
    throws BacnetException;

  /**
   * Remove list elements.
   *
   * @param propertyValue the PropertyValue containing the propertyId,
   *                      propertyArrayIndex, and the encoded list elements.
   * @return a ChangeListError if unable to remove any elements,
   * or null if ok.
   */
  ChangeListError removeListElements(PropertyValue propertyValue)
    throws BacnetException;

  /**
   * Few Export Object may require to communicate with the remote device.
   * @param transportLayer
   */
  default void setTransportLayer(BBacnetTransportLayer transportLayer){}

  default boolean isDynamicallyCreated()
  {
    return false;
  }
  
////////////////////////////////////////////////////////////////
// ObjectSubscriber
////////////////////////////////////////////////////////////////

  class ObjectSubscriber
    extends Subscriber
  {
    public ObjectSubscriber()
    {
      setMask(EVENT_MASK);
    }

    public void subscribe(BIBacnetExportObject export, BComponent src)
    {
      sublist.put(src, export);
      super.subscribe(src);
    }

    public void unsubscribe(BIBacnetExportObject export, BComponent src)
    {
      if (src == null) return;
      sublist.remove(src);
      super.unsubscribe(src);
    }

    @Override
    public void event(BComponentEvent event)
    {
      BComponent src = event.getSourceComponent();
      BIBacnetExportObject export = sublist.get(src);
      if (export == null)
      {
        logger.info("ObjectSubscriber: event from unknown source:" + src);
        sublist.remove(src);
        return;
      }

      try
      {
        switch (event.getId())
        {
          // Used for most exports to handle rename of exported object.
          case BComponentEvent.COMPONENT_RENAMED:
            String oldOrd = src.getSlotPath().getParent().getBody().substring(1);
            oldOrd += "/" + event.getValue().toString();
            // If the export object is using the default string for the
            // Object_Name, then update that.  If not, leave it alone.
            if (export.getObjectName().equals(oldOrd))
            {
              export.setObjectName(((BComponent)export.getObject()).getSlotPath().getBody().substring(1));
            }
            // If the export object is using the slot path ord for its
            // exportOrd, then this needs to be updated with the new
            // slot path ord.
            if (export.getObjectOrd().toString().endsWith(oldOrd))
            {
              export.setObjectOrd(BOrd.make("station:|" + event.getSourceComponent().getSlotPath().toString()), null);
            }
            break;

          // Used for most exports to handle removal of exported object.
          case BComponentEvent.COMPONENT_STOPPED:
            BObject object = export.getObject();
            // Do not remove if the object IS the export (Acc, Cmd, etc...)
            if (object != export)
            {
              ((BComponent)export.getParent()).remove((BComponent)export);
            }
            break;

          // Used for NotificationClassDescriptors to handle linkage of recipients.
          case BComponentEvent.KNOB_ADDED:
          case BComponentEvent.KNOB_REMOVED:
            if (export instanceof BBacnetNotificationClassDescriptor)
            {
              ((BBacnetNotificationClassDescriptor)export).recipientListChanged();
            }
            break;

          // Used for ScheduleDescriptors to handle writing output to targets,
          // and for BBacnetEventSource to handle when facets or out slot is changed.
          case BComponentEvent.PROPERTY_CHANGED:
            if (export instanceof BBacnetScheduleDescriptor)
            {
              if (src == ((BBacnetScheduleDescriptor)export).getSchedule())
              {
                if(event.getSlotName().equals("out"))
                {
                  ((BBacnetScheduleDescriptor)export).writePresentValue();
                }
                else if(event.getSlotName().equals("lastModified"))
                {
                  export.checkConfiguration();
                }
              }
            }

            if (export instanceof BBacnetEventSource)
            {
              Slot s = event.getSlot();
              String sn = event.getSlotName();
              if (s.equals(BControlPoint.facets))
              {
                export.checkConfiguration();
              }
              else if (sn.equals("out"))
              {
                BBacnetEventSource eventSource = (BBacnetEventSource)export;
                eventSource.checkValid();
                eventSource.statusChanged();
              }
            }
            break;
        }

      }
      catch (Exception e)
      {
        if (logger.isLoggable(Level.FINE))
        {
          logger.log(Level.FINE, "Error in BACnet ObjectSubscriber:export=" + export + "; src=" + src, e);
        }
      }
    }

    private static final BComponentEventMask EVENT_MASK = BComponentEventMask.make(new int[] {
      BComponentEvent.PROPERTY_CHANGED,
      BComponentEvent.KNOB_REMOVED,
      BComponentEvent.KNOB_ADDED,
      BComponentEvent.COMPONENT_RENAMED,
      BComponentEvent.COMPONENT_STOPPED });
    private HashMap<BComponent, BIBacnetExportObject> sublist = new HashMap<>();
    private static final Logger logger = Logger.getLogger("bacnet.server");
  }
}
