/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.discover;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BValue;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Discovery leaves appear in the auto manager discovery panes. Leaves are not
 * expandable in the discovery list.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
@NiagaraType
public interface BINDiscoveryLeaf
  extends BINDiscoveryObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.discover.BINDiscoveryLeaf(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:11 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BINDiscoveryLeaf.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Specify a default name to use when a discovered object is added to the
   * Niagara database.
   */
  String getDiscoveryName();

  /**
   * Specify TypeInfo for types which may be used to create new Niagara database
   * items for the discovered object.
   * <p>
   * The type at index 0 should be the preferred type.
   */
  TypeInfo[] getValidDatabaseTypes();

  /**
   * This method is inherited from BComplex,
   */
  BComplex getParent();

  /**
   * This method is inherited from BComplex,
   */
  Property[] getPropertiesArray();

  /**
   * This method is inherited from BComplex,
   */
  BValue get(Property p);

  /**
   * Update the specified target component from BINDiscoveryLeaf values. This is
   * used when creating BComponents from a discovery leaf to add to the
   * database.
   */
  void updateTarget(BComponent target);

  /**
   * Check if the specified component is a representation of this discovery
   * leaf.  If unsure return false.<p> This callback is used for MgrLearn
   * isExisting().
   */
  boolean isExisting(BComponent component);

  /**
   * Do updates to specified target from default discovery leaf's values. Used
   * for framework supported updates.
   */
  void defaultTargetUpdate(BComponent target);
}
