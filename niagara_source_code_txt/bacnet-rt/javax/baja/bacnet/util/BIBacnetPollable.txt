/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.driver.util.BIPollable;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIBacnetPollable
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 12 Jun 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
public interface BIBacnetPollable
  extends BIPollable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.util.BIBacnetPollable(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:32 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIBacnetPollable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the containing device object which will poll this object.
   *
   * @return the containing BBacnetDevice
   */
  BBacnetDevice device();

  /**
   * Get the pollable type of this object.
   *
   * @return one of the pollable types defined in BIBacnetPollable.
   */
  int getPollableType();

  /**
   * Poll the node.
   *
   * @return true if a poll was attempted to this node, or
   * false if the poll was skipped due to device down, out of service, etc.
   * @deprecated As of 3.2
   */
  @Deprecated
  boolean poll();

  /**
   * Indicate a failure polling this object.
   *
   * @param failureMsg
   */
  void readFail(String failureMsg);

  /**
   * Normalize the encoded data into the pollable's data structure.
   *
   * @param encodedValue
   * @param status
   * @param cx           must be a PollListEntry.
   */
  void fromEncodedValue(byte[] encodedValue, BStatus status, Context cx);

  /**
   * Get the list of poll list entries for this pollable.
   * The first entry for points must be the configured property.
   *
   * @return the list of poll list entries.
   */
  PollListEntry[] getPollListEntries();


////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  int BACNET_POLLABLE_DEVICE = 0;
  int BACNET_POLLABLE_PROXY_EXT = 1;
  int BACNET_POLLABLE_OBJECT = 2;
  int BACNET_POLLABLE_VIRTUAL = 3;
  int BACNET_POLLABLE_ENROLLMENT = 4;
  int BACNET_POLLABLE_REMOTE_EXT = 5;
  int BACNET_POLLABLE_OTHER = -1;
}
