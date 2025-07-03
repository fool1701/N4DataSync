/*
 * Copyright 2008 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import javax.baja.bacnet.datatypes.BBacnetAddress;
import javax.baja.bacnet.datatypes.BBacnetCovSubscription;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIBacnetCovSource is the interface implemented by all export
 * descriptors that support object-level COV subscription.<p>
 * <p>
 * Components implementing BIBacnetCovSource must also implement
 * BIBacnetExportObject; although this is not enforced, it may
 * be enforced in the future.
 *
 * @author Craig Gemmill on 02 Apr 2008
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
public interface BIBacnetCovSource
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BIBacnetCovSource(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:32 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIBacnetCovSource.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the exported object.
   *
   * @return the actual exported object by resolving the object ord.
   * @see BIBacnetExportObject
   */
  BObject getObject();

  /**
   * Get the export descriptor for this cov source.  Usually this.
   *
   * @return the relevant export descriptor.
   */
  BIBacnetExportObject getExport();

  /**
   * Add a COV subscription for the given COV subscriber information.
   *
   * @param sub
   */
  void addCovSubscription(BBacnetCovSubscription sub);

  /**
   * Remove the COV subscription for the given COV subscriber.
   *
   * @param sub
   */
  void removeCovSubscription(BBacnetCovSubscription sub);

  /**
   * Attempt to locate a COV subscription for the given subscriber information
   * on this object.
   *
   * @param subscriberAddress
   * @param processId
   * @param objectId
   * @return the subscription if found, or null.
   */
  BBacnetCovSubscription findCovSubscription(BBacnetAddress subscriberAddress,
                                             long processId,
                                             BBacnetObjectIdentifier objectId);

  /**
   * Attempt to locate a COV subscription for the given subscriber information
   * on this object.
   *
   * @param subscriberAddress
   * @param processId
   * @param objectId
   * @param propertyId
   * @return the subscription if found, or null.
   */
  BBacnetCovSubscription findCovPropertySubscription(BBacnetAddress subscriberAddress,
                                                     long processId,
                                                     BBacnetObjectIdentifier objectId,
                                                     int propertyId,
                                                     int propertyArrayIndex);

  /**
   * Start or restart a timer for the given COV subscription.
   *
   * @param covSub   the subscription for which to start the timer.
   * @param lifetime the lifetime, in seconds, of the subscription.
   */
  void startCovTimer(BBacnetCovSubscription covSub, long lifetime);

  /**
   * Check to see if a COV notification is necessary.
   */
  void checkCov();

  /**
   * Get the output property mapped as Present_Value for this export.
   *
   * @return the property used for Present_Value in COV notifications.
   */
  Property getOutProperty();

  /**
   * Does this COV source support SubscribeCOV in addition to SubscribeCOVProperty?
   * This is true for input, output, value, and loop objects.
   *
   * @return true if Subscribe-COV can be used with this object.
   */
  boolean supportsSubscribeCov();

  BValue getCurrentCovValue(BBacnetCovSubscription sub);
}
