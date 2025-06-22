/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.agent.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIMixIn is implemented by BValues which are designed to be "mixed in"
 * with another class.  A MixIn is basically a mechanism to automatically 
 * add a dynamic slot to a specific type of component within a ComponentSpace.
 * MixIns are registered as agents on the type of parent they are designed
 * to mix into.  Then something has to enable the MixIn for a given
 * ComponentSpace.  Typically this done by a service which enables an
 * associated MixIn in its serviceStarted() callback (this is also the
 * most efficient time to do it).  Once a MixIn has been added to a component
 * it may be easily looked up using {@code BComponent.getMixin(Type)}.
 *
 * @author    Brian Frank
 * @creation  23 Feb 05
 * @version   $Revision: 3$ $Date: 8/17/07 10:36:03 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIMixIn
  extends BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BIMixIn(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIMixIn.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Get the display name used for the property this MixIn 
   * occupies in it's parent component.  The programatic name
   * always fixed as the type spec with the colon replaced by
   * an underbar.
   */
  public String getDisplayNameInParent(Context cx); 
}
