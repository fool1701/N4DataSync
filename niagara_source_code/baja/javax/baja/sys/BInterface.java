/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BInterface is the super type of all Baja typed interfaces.
 * Only BObject classes can implement this interface and its
 * derived interfaces.  All BInterfaces must declare a public
 * static final called TYPE which loads the Type for the interface
 * class.
 *
 * @author    Brian Frank
 * @since     Baja 1.0
 */

@NiagaraType
public interface BInterface extends BIObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BInterface(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BInterface.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Narrow to BObject.
   */
  BObject asObject();
}
