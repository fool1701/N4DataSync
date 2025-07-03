/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet;

import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BIBacnetObjectContainer resolves a triplet of object identifier,
 * property identifier, and property array index to a Bacnet point.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 02 Sep 03
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
public interface BIBacnetObjectContainer
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.BIBacnetObjectContainer(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:31 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIBacnetObjectContainer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Look up and return the Bacnet object with the given reference.
   *
   * @param objectId
   * @param propertyId
   * @param propertyArrayIndex
   * @param domain             the realm in which to look up the object: point, schedule, history
   * @return a BObject with the given reference parameters, or null if
   * this container does not contain any objects with the given parameters.
   */
  BObject lookupBacnetObject(BBacnetObjectIdentifier objectId,
                             int propertyId,
                             int propertyArrayIndex,
                             String domain);


  String POINT = "point";
  String SCHEDULE = "schedule";
  String HISTORY = "history";
  String CONFIG = "config";
}
