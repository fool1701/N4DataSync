/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetAddress;
import javax.baja.bacnet.datatypes.BBacnetCovSubscription;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetRecipient;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.io.RejectException;
import javax.baja.control.BControlPoint;
import javax.baja.status.BStatus;
import javax.baja.util.ICoalesceable;

import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.CovNotificationParameters;
import com.tridium.bacnet.asn.NBacnetPropertyReference;
import com.tridium.bacnet.asn.NBacnetPropertyValue;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.stack.BBacnetStack;
import com.tridium.bacnet.stack.DeviceRegistry;

/**
 * BacnetCovSubscriber handles sending a Cov notification to any
 * Cov subscribers when the point's value changes.
 *
 * @author Craig Gemmill on 10 Sep 2004
 * @since Niagara 3 Bacnet 1.0
 */
public class Cov
  implements Runnable, ICoalesceable
{
  public Cov(BBacnetCovSubscription sub, BIBacnetCovSource object, BControlPoint pt)
  {
    this.sub = sub;
    this.covSrc = object;
    this.pt = pt;
  }

////////////////////////////////////////////////////////////////
// ICoalesceable
////////////////////////////////////////////////////////////////

  public String toString()
  {
    return "Cov:" + sub + " on " + covSrc + " to " + pt.getName() + ":" + pt;
  }

  @Override
  public Object getCoalesceKey()
  {
    return this;
  }

  public boolean equals(Object o)
  {
    if (o instanceof Cov)
    {
      Cov cov = (Cov)o;
      BIBacnetCovSource covObj = cov.covSrc;
      if ((sub.equivalent(cov.sub)) &&
          (covSrc.getExport().getObjectId().equals(covObj.getExport().getObjectId())) &&
          (pt == cov.pt))
      {
        return true;
      }
    }

    return false;
  }

  public int hashCode()
  {
    //A constant hashCode is better than no hashCode.
    //If we start putting cov requests into maps of queues, 
    //a better hashCode will be required.
    return 1;
  }

  @Override
  public ICoalesceable coalesce(ICoalesceable c)
  {
    this.sub = ((Cov)c).sub;
    this.covSrc = ((Cov)c).covSrc;
    this.pt = ((Cov)c).pt;
    return this;
  }

////////////////////////////////////////////////////////////////
// Runnable
////////////////////////////////////////////////////////////////

  @Override
  public void run()
  {
    try
    {
      int timeRemaining = sub.getTimeRemaining();
      if (timeRemaining < 0)
      {
        return;
      }

      // Get the device address.
      BBacnetNetwork bacnet = BBacnetNetwork.bacnet();

      BBacnetRecipient recipient = sub.getRecipient().getRecipient();
      if (recipient.getChoice() == BBacnetRecipient.DEVICE_TAG)
      {
        address = DeviceRegistry.getDeviceAddress(recipient.getDevice());
      }
      else
      {
        address = recipient.getAddress();
      }

      if (log().isLoggable(Level.FINE))
      {
        log().fine("Sending Cov notification to address: " + address);
      }

      BBacnetObjectIdentifier initiatingDeviceId = BBacnetNetwork.localDevice().getObjectId();
      BIBacnetExportObject export = covSrc.getExport();

      buildPropertyValues(export);

      CovNotificationParameters cnp = new CovNotificationParameters(sub.getRecipient().getProcessIdentifier().getUnsigned(),
                                                                    initiatingDeviceId,
                                                                    sub.getMonitoredPropertyReference().getObjectId(),
                                                                    timeRemaining,
                                                                    propertyValues);

      if (sub.getIssueConfirmedNotifications())
      {
        ((BBacnetStack)bacnet.getBacnetComm()).getClient().confirmedCovNotification(address, cnp);
      }
      else
      {
        ((BBacnetStack)bacnet.getBacnetComm()).getClient().unconfirmedCovNotification(address, cnp);
      }
    }
    catch (BacnetException e)
    {
      log().log(Level.SEVERE, "BacnetException sending COV Notification", e);
    }
    catch (Exception e)
    {
      log().log(Level.SEVERE, "Unable to send COV notification to Bacnet", e);
    }
  }

////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  private void buildPropertyValues(BIBacnetExportObject export)
    throws RejectException
  {
    // Loop case
    if (export.getObjectId().getObjectType() == BBacnetObjectType.LOOP)
    {
      PropertyValue cov = new NBacnetPropertyValue(export.readProperty(new NBacnetPropertyReference(sub.getMonitoredPropertyReference().getPropertyId(),
                                                                                                    sub.getMonitoredPropertyReference().getPropertyArrayIndex())));
      PropertyValue status = new NBacnetPropertyValue(export.readProperty(new NBacnetPropertyReference(BBacnetPropertyIdentifier.STATUS_FLAGS,
                                                                                                       BacnetConst.NOT_USED)));
      PropertyValue setpt = new NBacnetPropertyValue(export.readProperty(new NBacnetPropertyReference(BBacnetPropertyIdentifier.SETPOINT,
                                                                                                      BacnetConst.NOT_USED)));
      PropertyValue cvv = new NBacnetPropertyValue(export.readProperty(new NBacnetPropertyReference(BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_VALUE,
                                                                                                    BacnetConst.NOT_USED)));
      propertyValues = new PropertyValue[] { cov, status, setpt, cvv };
    }

    // Other cases could go here as needed...

    // Default case - input/output/value objects, etc.
    else
    {
      PropertyValue cov;
      PropertyValue status = null;
      if (!sub.isCovProperty())
      {
        //Standard COV still has to duplicate the read property (because the checkCov() abstraction
        //prevents caching the previously generated property value
        cov = readPropertyValue(export);
        status = readStatus(export);
      }
      else
      {
        //CovPropertyValues cache the property to be sent on the subscription.
        PropertyValue last = sub.getLastPropertyValue();
        if (last != null)
        {
          cov = new NBacnetPropertyValue(last);
        }
        else
        {
          // read the status if a cov notification has not yet been sent.
          cov = readPropertyValue(export);
          sub.setLastPropertyValue(cov);
        }

        BStatus lastStatus = sub.getLastStatusFlags();
        if (lastStatus != null)
        {
          status = new NBacnetPropertyValue(new NReadPropertyResult(BBacnetPropertyIdentifier.STATUS_FLAGS,
                                                                    BacnetConst.NOT_USED,
                                                                    AsnUtil.statusToAsnStatusFlags(lastStatus)));
        }
        else
        {
          // read the status if a cov notification has not yet been sent.
          status = readStatus(export);
          sub.setLastStatusFlags(AsnUtil.asnStatusFlagsToBStatus(status.getPropertyValue()));
        }
      }

      propertyValues = new PropertyValue[] { cov, status };
    }
  }

  private PropertyValue readPropertyValue(BIBacnetExportObject export)
    throws RejectException
  {
    return new NBacnetPropertyValue(export.readProperty(new NBacnetPropertyReference(sub.getMonitoredPropertyReference().getPropertyId(),
                                                                                     sub.getMonitoredPropertyReference().getPropertyArrayIndex())));
  }

  private static PropertyValue readStatus(BIBacnetExportObject export)
    throws RejectException
  {
    return new NBacnetPropertyValue(export.readProperty(new NBacnetPropertyReference(BBacnetPropertyIdentifier.STATUS_FLAGS,
                                                                                     BacnetConst.NOT_USED)));
  }

  public BBacnetCovSubscription getSub()
  {
    return this.sub;
  }

  private static Logger log()
  {
    return logger;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BBacnetCovSubscription sub;
  private PropertyValue[] propertyValues;
  private BIBacnetCovSource covSrc;
  private BControlPoint pt;
  private BBacnetAddress address;
  private static final Logger logger = Logger.getLogger("bacnet.server");
}
