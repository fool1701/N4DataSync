/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.report;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Flags;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BReportRecipient consumes a Report generated from a ReportSource.
 * Typically this class will route the report object to some recipient,
 * such as emailing someone the report file as an attachment. Subclasses
 * should override {@code handleReport()} to customize:
 *
 * {@code <pre>
 *   public class BMyReportRecipient
 *     extends BReportRecipient
 *   {
 *     public void handleRoute(BReport event)
 *       throws Exception
 *     {
 *       ...
 *     }
 *   }
 * </pre>}
 *
 * @author    Andy Frank
 * @creation  16 Oct 06
 * @version   $Revision: 3$ $Date: 11/3/06 4:39:01 PM EST$
 * @since     Niagara 3.2
 */
@NiagaraType
/*
 Route a report event.
 */
@NiagaraAction(
  name = "route",
  parameterType = "BReport",
  defaultValue = "new BReport()",
  flags = Flags.SUMMARY
)
public abstract class BReportRecipient
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.report.BReportRecipient(2687110910)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "route"

  /**
   * Slot for the {@code route} action.
   * Route a report event.
   * @see #route(BReport parameter)
   */
  public static final Action route = newAction(Flags.SUMMARY, new BReport(), null);

  /**
   * Invoke the {@code route} action.
   * Route a report event.
   * @see #route
   */
  public void route(BReport parameter) { invoke(route, parameter, null); }

  //endregion Action "route"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BReportRecipient.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Handle the ReportRecipient specific routing of the report.
   */
  public abstract void handleRoute(BReport event) throws Exception;

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  public final void doRoute(BReport event)
  {
    try
    {
      handleRoute(event);
    }
    catch (Exception e)
    {
      log.log(Level.SEVERE, "Route failed", e);
      throw new BajaRuntimeException(e);
    }
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("export.png");

////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  public static final Logger log = Logger.getLogger("report");

}
