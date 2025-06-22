/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.discover;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BComponent;
import javax.baja.sys.BStruct;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BNDiscoveryLeaf provides a base implementation of BINDiscoveryLeaf.
 *
 * @author lperkins
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
@NiagaraType
public class BNDiscoveryLeaf
  extends BStruct
  implements BINDiscoveryLeaf
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.discover.BNDiscoveryLeaf(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:11 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNDiscoveryLeaf.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//BINDiscoveryLeaf
////////////////////////////////////////////////////////////////

  /**
   * Override to specify types used in an Add operation for this discovery
   * object.
   * <p>
   * First entry in array should be the best type to map this discovery object.
   */
  @Override
  public TypeInfo[] getValidDatabaseTypes()
  {
    return null;
  }

  /**
   * Override to specify the default name when adding this discovery object to
   * the station.
   */
  @Override
  public String getDiscoveryName()
  {
    return null;
  }

  /**
   * Update the specified target from discovery discovery leaf's values.
   */
  @Override
  public void updateTarget(BComponent target)
  {
  }
  
  /**
   * Return true if the specified component is a representation of this
   * discovery object.  This is used to grey discovery objects that already
   * exist in the database.
   */
  @Override
  public boolean isExisting(BComponent target)
  {
    return false;
  }

  /**
   * Do updates to specified target from default discovery leaf's values. Used
   * for framework supported updates.
   */
  @Override
  public void defaultTargetUpdate(BComponent target)
  {
  }
}
