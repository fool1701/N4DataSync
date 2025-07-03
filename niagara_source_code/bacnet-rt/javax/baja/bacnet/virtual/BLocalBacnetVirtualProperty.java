/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.virtual;

import java.util.logging.Level;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetPropertyReference;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.export.BLocalBacnetDevice;
import javax.baja.bacnet.io.PropertyReference;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.util.PropertyInfo;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NBacnetPropertyValue;

@NiagaraType
public class BLocalBacnetVirtualProperty
  extends BBacnetVirtualProperty
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.virtual.BLocalBacnetVirtualProperty(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:32 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLocalBacnetVirtualProperty.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  public BLocalBacnetVirtualProperty()
  {
  }

  public BLocalBacnetVirtualProperty(int propertyId,
                                     BValue v,
                                     String readFault,
                                     boolean useFacets)
  {
    super(propertyId, v, readFault, useFacets);
    propertyReference = new BBacnetPropertyReference(propertyId);
  }


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Local properties are not in a BBacnetDevice.
   */
  public BBacnetDevice device()
  {
    return null;
  }

  /**
   * Convenience method to get the local device object.
   *
   * @return localBacnetDevice
   */
  public BLocalBacnetDevice localDevice()
  {
    return ((BLocalBacnetVirtualGateway)getVirtualGateway()).localDevice();
  }

  /**
   * Get the status of the containing device.
   *
   * @return the status of the device
   */
  protected BStatus getDeviceStatus()
  {
    return ((BLocalBacnetVirtualGateway)getVirtualGateway()).localDevice().getStatus();
  }

  /**
   * Override to subscribe with the gateway's local poll thread
   * instead of the BacnetPoll service.
   */
  protected void pollSubscribe()
  {
    ((BLocalBacnetVirtualGateway)getVirtualGateway()).getLocalPoll().subscribe(this);
  }

  /**
   * Override to unsubscribe from the gateway's local poll thread
   * instead of the BacnetPoll service.
   */
  protected void pollUnsubscribe()
  {
    ((BLocalBacnetVirtualGateway)getVirtualGateway()).getLocalPoll().unsubscribe(this);
  }

  /**
   * Only allowed inside BLocalBacnetVirtualObject.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BLocalBacnetVirtualObject;
  }

  /**
   * Override to post a LocalWrite object instead of the parent's
   * (remote) Write object.
   */
  void write(int propertyArrayIndex, byte[] encodedValue, int priority)
  {
    network().postWrite(new LocalWrite(getPropertyId(),
      propertyArrayIndex,
      encodedValue,
      priority));
  }

  /**
   * Override superclass method to encode the value using PropertyInfo from
   * the local device, instead of trying to find a parent BBacnetDevice.
   */
  byte[] encodeValue(BValue v)
  {
    byte[] ev = null;
//    PropertyInfo pi = ObjectTypeList.getPropertyInfo(object().getObjectId().getObjectType(),
//        getPropertyId());
    PropertyInfo pi = localDevice().getPropertyInfo(object().getObjectId().getObjectType(),
      getPropertyId());
    if (pi != null)
      ev = AsnUtil.toAsn(pi.getAsnType(), v);
    else
      ev = AsnUtil.toAsn(v);
    return ev;
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  PropertyReference getPropertyReference()
  {
    return propertyReference;
  }

  BLocalBacnetVirtualObject localObject()
  {
    return (BLocalBacnetVirtualObject)getParent();
  }


////////////////////////////////////////////////////////////////
// Inner Class: LocalWrite
////////////////////////////////////////////////////////////////

  class LocalWrite
    implements Runnable
  {
    LocalWrite(int propId,
               int index,
               byte[] ev,
               int pri)
    {
      if (propId == BBacnetPropertyIdentifier.PRIORITY_ARRAY)
      {
        pv = new NBacnetPropertyValue(BBacnetPropertyIdentifier.PRESENT_VALUE,
          NOT_USED,
          ev,
          index);
      }
      else
      {
        pv = new NBacnetPropertyValue(propId,
          index,
          ev,
          pri);
      }
    }

    public void run()
    {
      try
      {
        localObject().getExport().writeProperty(pv);
        writeOk();
      }
      catch (BacnetException e)
      {
        log.severe("BacnetException writing "
          + BBacnetPropertyIdentifier.tag(getPropertyId()) + " in " + this);
        writeFail(e.toString());
        if (log.isLoggable(Level.FINE))
        {
          log.log(Level.FINE, "Stack Trace: ", e);
        }        
      }
    }

    BBacnetObjectIdentifier objectId;
    PropertyValue pv = null;
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private PropertyReference propertyReference = null;
}
