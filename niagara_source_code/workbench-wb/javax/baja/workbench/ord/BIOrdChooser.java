/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.ord;

import javax.baja.agent.BIAgent;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;

/**
 * OrdChooser
 *
 * @author    Andy Frank
 * @creation  21 Mar 03
 * @version   $Revision: 2$ $Date: 8/16/07 2:26:47 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIOrdChooser
  extends BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.ord.BIOrdChooser(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIOrdChooser.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Open this chooser with the specified Ord.  Returns the new
   * ord if successful, or null if the chooser was cancelled.
   */
  public BOrd openChooser(BWidget owner, BObject base, BOrd ord, Context cx);

}
