/*
* Copyright 2014 Tridium, Inc. All Rights Reserved.
*/
package javax.baja.ui;

import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Implemented by a Shell that has a view loaded from an ORD.
 *
 * @see BWidgetShell
 *
 * @author Gareth Johnson on 25/09/2014
 * @since Niagara 4.0
 */
@NiagaraType
public interface BIActiveOrdShell extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BIActiveOrdShell(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIActiveOrdShell.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Returns the target for the Shell.
   *
   * @return The active ORD target for the Shell.
   */
  public OrdTarget getActiveOrdTarget();

  /**
   * Returns the id of the current view being shown. This id
   * can be used in view query.
   *
   * @return The id of the view currently shown.
   */
  public String getActiveViewId();
}
