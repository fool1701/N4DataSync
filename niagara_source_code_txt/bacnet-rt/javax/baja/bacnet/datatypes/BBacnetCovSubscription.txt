/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.io.*;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusValue;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnConst;

/**
 * BBacnetCovSubscription represents information about a client subscription
 * for change-of-value notification on a Bacnet server object in this device.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 06 May 02
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
/*
 the recipient process information.
 */
@NiagaraProperty(
  name = "recipient",
  type = "BBacnetRecipientProcess",
  defaultValue = "new BBacnetRecipientProcess()"
)
/*
 the object property reference.
 */
@NiagaraProperty(
  name = "monitoredPropertyReference",
  type = "BBacnetObjectPropertyReference",
  defaultValue = "new BBacnetObjectPropertyReference()"
)
@NiagaraProperty(
  name = "issueConfirmedNotifications",
  type = "boolean",
  defaultValue = "false"
)
/*
 the time at which this subscriber's subscription will end.  This is
 an estimate; the actual timing is handled through an internal timer.
 */
@NiagaraProperty(
  name = "subscriptionEndTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  facets = {
    @Facet(name = "BFacets.SHOW_SECONDS", value = "true"),
    @Facet(name = "BFacets.SHOW_MILLISECONDS", value = "true")
  }
)
/*
 the Cov increment requested for this subscription.
 NOT USED for Subscribe-Cov support.
 */
@NiagaraProperty(
  name = "covIncrement",
  type = "float",
  defaultValue = "BFloat.NaN"
)
public final class BBacnetCovSubscription
  extends BStruct
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetCovSubscription(1810767320)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "recipient"

  /**
   * Slot for the {@code recipient} property.
   * the recipient process information.
   * @see #getRecipient
   * @see #setRecipient
   */
  public static final Property recipient = newProperty(0, new BBacnetRecipientProcess(), null);

  /**
   * Get the {@code recipient} property.
   * the recipient process information.
   * @see #recipient
   */
  public BBacnetRecipientProcess getRecipient() { return (BBacnetRecipientProcess)get(recipient); }

  /**
   * Set the {@code recipient} property.
   * the recipient process information.
   * @see #recipient
   */
  public void setRecipient(BBacnetRecipientProcess v) { set(recipient, v, null); }

  //endregion Property "recipient"

  //region Property "monitoredPropertyReference"

  /**
   * Slot for the {@code monitoredPropertyReference} property.
   * the object property reference.
   * @see #getMonitoredPropertyReference
   * @see #setMonitoredPropertyReference
   */
  public static final Property monitoredPropertyReference = newProperty(0, new BBacnetObjectPropertyReference(), null);

  /**
   * Get the {@code monitoredPropertyReference} property.
   * the object property reference.
   * @see #monitoredPropertyReference
   */
  public BBacnetObjectPropertyReference getMonitoredPropertyReference() { return (BBacnetObjectPropertyReference)get(monitoredPropertyReference); }

  /**
   * Set the {@code monitoredPropertyReference} property.
   * the object property reference.
   * @see #monitoredPropertyReference
   */
  public void setMonitoredPropertyReference(BBacnetObjectPropertyReference v) { set(monitoredPropertyReference, v, null); }

  //endregion Property "monitoredPropertyReference"

  //region Property "issueConfirmedNotifications"

  /**
   * Slot for the {@code issueConfirmedNotifications} property.
   * @see #getIssueConfirmedNotifications
   * @see #setIssueConfirmedNotifications
   */
  public static final Property issueConfirmedNotifications = newProperty(0, false, null);

  /**
   * Get the {@code issueConfirmedNotifications} property.
   * @see #issueConfirmedNotifications
   */
  public boolean getIssueConfirmedNotifications() { return getBoolean(issueConfirmedNotifications); }

  /**
   * Set the {@code issueConfirmedNotifications} property.
   * @see #issueConfirmedNotifications
   */
  public void setIssueConfirmedNotifications(boolean v) { setBoolean(issueConfirmedNotifications, v, null); }

  //endregion Property "issueConfirmedNotifications"

  //region Property "subscriptionEndTime"

  /**
   * Slot for the {@code subscriptionEndTime} property.
   * the time at which this subscriber's subscription will end.  This is
   * an estimate; the actual timing is handled through an internal timer.
   * @see #getSubscriptionEndTime
   * @see #setSubscriptionEndTime
   */
  public static final Property subscriptionEndTime = newProperty(0, BAbsTime.NULL, BFacets.make(BFacets.make(BFacets.SHOW_SECONDS, true), BFacets.make(BFacets.SHOW_MILLISECONDS, true)));

  /**
   * Get the {@code subscriptionEndTime} property.
   * the time at which this subscriber's subscription will end.  This is
   * an estimate; the actual timing is handled through an internal timer.
   * @see #subscriptionEndTime
   */
  public BAbsTime getSubscriptionEndTime() { return (BAbsTime)get(subscriptionEndTime); }

  /**
   * Set the {@code subscriptionEndTime} property.
   * the time at which this subscriber's subscription will end.  This is
   * an estimate; the actual timing is handled through an internal timer.
   * @see #subscriptionEndTime
   */
  public void setSubscriptionEndTime(BAbsTime v) { set(subscriptionEndTime, v, null); }

  //endregion Property "subscriptionEndTime"

  //region Property "covIncrement"

  /**
   * Slot for the {@code covIncrement} property.
   * the Cov increment requested for this subscription.
   * NOT USED for Subscribe-Cov support.
   * @see #getCovIncrement
   * @see #setCovIncrement
   */
  public static final Property covIncrement = newProperty(0, BFloat.NaN, null);

  /**
   * Get the {@code covIncrement} property.
   * the Cov increment requested for this subscription.
   * NOT USED for Subscribe-Cov support.
   * @see #covIncrement
   */
  public float getCovIncrement() { return getFloat(covIncrement); }

  /**
   * Set the {@code covIncrement} property.
   * the Cov increment requested for this subscription.
   * NOT USED for Subscribe-Cov support.
   * @see #covIncrement
   */
  public void setCovIncrement(float v) { setFloat(covIncrement, v, null); }

  //endregion Property "covIncrement"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetCovSubscription.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetCovSubscription()
  {
  }

  /**
   * Constructor for use with SubscribeCOV-Request.
   *
   * @param subscriberAddress
   * @param subscriberProcessId
   * @param monitoredObjectId
   * @param issueConfirmedNotifications
   */
  public BBacnetCovSubscription(BBacnetAddress subscriberAddress,
                                long subscriberProcessId,
                                BBacnetObjectIdentifier monitoredObjectId,
                                boolean issueConfirmedNotifications)
  {
    getRecipient().getRecipient().setRecipient(subscriberAddress);
    getRecipient().setProcessIdentifier(BBacnetUnsigned.make(subscriberProcessId));
    getMonitoredPropertyReference().setObjectId(monitoredObjectId);
    setIssueConfirmedNotifications(issueConfirmedNotifications);
  }

  /**
   * Constructor for use with SubscribeCOVProperty-Request.
   *
   * @param subscriberAddress
   * @param subscriberProcessId
   * @param monitoredObjectId
   * @param monitoredPropertyId
   * @param issueConfirmedNotifications
   */
  public BBacnetCovSubscription(BBacnetAddress subscriberAddress,
                                long subscriberProcessId,
                                BBacnetObjectIdentifier monitoredObjectId,
                                PropertyReference monitoredPropertyId,
                                boolean issueConfirmedNotifications,
                                BNumber covIncr)
  {
    getRecipient().getRecipient().setRecipient(subscriberAddress);
    getRecipient().setProcessIdentifier(BBacnetUnsigned.make(subscriberProcessId));
    getMonitoredPropertyReference().setObjectId(monitoredObjectId);
    getMonitoredPropertyReference().setPropertyId(monitoredPropertyId.getPropertyId());
    getMonitoredPropertyReference().setPropertyArrayIndex(monitoredPropertyId.getPropertyArrayIndex());
    setIssueConfirmedNotifications(issueConfirmedNotifications);
    setCovIncrement(covIncr != null ? covIncr.getFloat() : Float.NaN);
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the last value that was sent to this COV subscriber.
   *
   * @return the value that last caused a COV notification.
   */
  public BStatusValue getLastValue()
  {
//    return lastValue;
    return covProperty ? null : (BStatusValue)lastPropValue;
  }

  /**
   * Set the last value sent to this COV subscriber.  Called when
   * we notify this subscriber of a change of value.
   *
   * @param newValue the COV value.
   */
  public void setLastValue(BStatusValue newValue)
  {
//    lastValue = (BStatusValue)newValue.newCopy();
    lastPropValue = newValue.newCopy();
  }

  /**
   * Get the last value that was sent to this COV Property subscriber.
   *
   * @return the value that last caused a COV notification.
   */
  public BValue getLastPropValue()
  {
    return lastPropValue;
  }

  /**
   * Set the last value sent to this COV Property subscriber.  Called
   * when we notify this subscriber of a change of value.
   *
   * @param newValue the COV value.
   */
  public void setLastPropValue(BValue newValue)
  {
    lastPropValue = newValue.newCopy();
  }

  /**
   * Get the last encoded PropertyValue that was sent to this COV Property subscriber.
   *
   * @return the ASN encoded property that last caused a COV notification.
   */
  public PropertyValue getLastPropertyValue()
  {
    return lastPropertyValue;
  }

  /**
   * Set the last value sent to this COV Property subscriber.  Called
   * when we notify this subscriber of a change of value.
   *
   * @param lastPropertyValue the asn.1 encoded COV value.
   */
  public void setLastPropertyValue(PropertyValue lastPropertyValue)
  {
    this.lastPropertyValue = lastPropertyValue;
  }

  /**
   * Get the ticket that represents the scheduled termination of this
   * subscription.
   *
   * @return the ticket.
   */
  public Clock.Ticket getTicket()
  {
    return ticket;
  }

  /**
   * Set the ticket representing the scheduled termination of this subscription.
   *
   * @param ticket the termination ticket.
   */
  public void setTicket(Clock.Ticket ticket)
  {
    this.ticket = ticket;
  }

  /**
   * Get the remaining life of this subscription in seconds.
   * This estimated calculation is done by determining the time
   * until the end time, which is set at the time when the termination
   * ticket is generated.
   *
   * @return the estimated remaining lifetime in seconds.
   */
  public int getTimeRemaining()
  {
    if (getSubscriptionEndTime().equals(BAbsTime.NULL))
      return 0;
    long curTime = BAbsTime.make().getMillis();
    int timeRemaining = (int)((getSubscriptionEndTime().getMillis() - curTime) / 1000);
    return timeRemaining > 0 ? timeRemaining : -1;
  }

  /**
   * To String.
   */
  public String toString(Context cx)
  {
    if ((cx != null) && cx.equals(nameContext))
      return getNameString();
    StringBuilder sb = new StringBuilder();
    sb.append(covProperty ? "CovPSub " : "CovSub ")
      .append(getRecipient().toString(cx))
      .append('{')
      .append(getMonitoredPropertyReference().toString(cx));
    if (covProperty)
      sb.append(':').append(getCovIncrement());

    sb.append(getIssueConfirmedNotifications() ? "} C until " : "} U until ")
      .append(getSubscriptionEndTime());
    return sb.toString();
  }

  public boolean isCovIncrementUsed()
  {
    return !(Float.isNaN(getCovIncrement()));
  }

  public boolean isCovProperty()
  {
    return covProperty;
  }

  public void setCovProperty(boolean v)
  {
    covProperty = v;
  }


  /**
   * @return the lastStatusBits
   */
  public int getLastStatusBits()
  {
    BStatus status = getLastStatusFlags();
    if (status != null)
    {
      return status.getBits();
    }
    return 0;
  }

  /**
   * @param lastStatusBits the lastStatusBits to set
   */
  public void setLastStatusBits(int lastStatusBits)
  {
    this.lastStatusFlags = BStatus.make(lastStatusBits & BACNET_SBITS_MASK);
  }

  /**
   * @param lastStatusFlags the lastStatusFlags to set
   */
  public void setLastStatusFlags(BStatus lastStatusFlags)
  {
    this.lastStatusFlags = BStatus.make(lastStatusFlags.getBits() & BACNET_SBITS_MASK);
  }

  /**
   * @return the lastStatusFlags PropertyValue
   */
  public BStatus getLastStatusFlags()
  {
    return lastStatusFlags;
  }


////////////////////////////////////////////////////////////////
//  Support
////////////////////////////////////////////////////////////////

  /**
   * Set the time remaining.
   * This is used only by the readAsn() method.
   */
  private void setTimeRemaining(long timeRemaining)
  {
    set(subscriptionEndTime, BAbsTime.make().add(BRelTime.make(timeRemaining * BRelTime.MILLIS_IN_SECOND)), noWrite);
  }

  /**
   * Generate a string that is unique for a given recipient, processId, and
   * monitored objectId.  This is used to name the Cov subscription, and
   */
  private String getNameString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append(covProperty ? "covP_" : "cov_")
      .append(SlotPath.unescape(getRecipient().toString(nameContext)))
      .append("_")
      .append(getMonitoredPropertyReference().toString(nameContext));
    if (covProperty) sb.append('_').append(getCovIncrement());
    return SlotPath.escape(sb.toString());
  }


////////////////////////////////////////////////////////////////
//  BIBacnetDataType
////////////////////////////////////////////////////////////////

  /**
   * Write the value to the Asn output stream.
   *
   * @param out the AsnOutput stream.
   */
  public void writeAsn(AsnOutput out)
  {
    out.writeOpeningTag(RECIPIENT_TAG);
    getRecipient().writeAsn(out);
    out.writeClosingTag(RECIPIENT_TAG);
    out.writeOpeningTag(MONITORED_PROPERTY_REFERENCE_TAG);
    getMonitoredPropertyReference().writeAsn(out);
    out.writeClosingTag(MONITORED_PROPERTY_REFERENCE_TAG);
    out.writeBoolean(ISSUE_CONFIRMED_NOTIFICATIONS_TAG, getIssueConfirmedNotifications());
    out.writeUnsignedInteger(TIME_REMAINING_TAG, getTimeRemaining());
    if (isCovIncrementUsed())
      out.writeReal(COV_INCREMENT_TAG, getCovIncrement());
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public void readAsn(AsnInput in)
    throws AsnException
  {
    int tag = in.peekTag();
    if (in.isOpeningTag(RECIPIENT_TAG))
    {
      in.skipTag();   // skip opening tag
      getRecipient().readAsn(in);
      in.skipTag();   // skip closing tag
    }
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);

    tag = in.peekTag();
    if (in.isOpeningTag(MONITORED_PROPERTY_REFERENCE_TAG))
    {
      in.skipTag();   // skip opening tag
      getMonitoredPropertyReference().readAsn(in);
      in.skipTag();   // skip closing tag
    }
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);

    setBoolean(issueConfirmedNotifications, in.readBoolean(ISSUE_CONFIRMED_NOTIFICATIONS_TAG), noWrite);
    setTimeRemaining(in.readUnsignedInteger(TIME_REMAINING_TAG));
    in.peekTag();
    if (in.isValueTag(COV_INCREMENT_TAG))
      setFloat(covIncrement, in.readReal(COV_INCREMENT_TAG), noWrite);
  }


/////////////////////////////////////////////////////////////////
//  Constants
/////////////////////////////////////////////////////////////////

  /**
   * BacnetCovSubscription Asn Context Tags
   * See Bacnet Clause 21.
   */
  public static final int RECIPIENT_TAG = 0;
  public static final int MONITORED_PROPERTY_REFERENCE_TAG = 1;
  public static final int ISSUE_CONFIRMED_NOTIFICATIONS_TAG = 2;
  public static final int TIME_REMAINING_TAG = 3;
  public static final int COV_INCREMENT_TAG = 4;

  // The maximum possible encoded size for this datatype is 49, assuming
  // a maximum MAC address length of 7 (this could conceivably be longer?)
  // However, the covIncrement field is never used by us, and this accounts
  // for 5 bytes, so the max encoded size we would see is 44.
  public static final int MAX_ENCODED_SIZE = 44;


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  //  private BStatusValue lastValue;
  private Clock.Ticket ticket;
  private boolean covProperty = false;
  private BValue lastPropValue = null;
  private PropertyValue lastPropertyValue = null;
  private BStatus lastStatusFlags = null;

}
