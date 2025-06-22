/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench;

import javax.baja.naming.BISession;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BDialog;
import javax.baja.ui.BIActiveOrdShell;
import javax.baja.ui.BIHyperlinkShell;
import javax.baja.ui.BRoundedDialog;
import javax.baja.ui.BWidget;
import javax.baja.ui.BWidgetShell;
import javax.baja.ui.Command;
import javax.baja.ui.HyperlinkInfo;
import javax.baja.workbench.view.BWbView;

/**
 * BWbShell is a BWidgetShell for workbench applications.
 *
 * @author    Brian Frank on 7 Jan 01
 * @version   $Revision: 15$ $Date: 7/23/08 4:51:23 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BWbShell
  extends BWidgetShell
  implements BIHyperlinkShell, BIActiveOrdShell
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.BWbShell(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbShell.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BWbShell() {}
  public BWbShell(Object peer) { super(peer); }
  
  /**
   * Get the BOrd the plugin shell is currently viewing.
   */
  public abstract BOrd getActiveOrd();

  /**
   * Get resolved OrdTarget of the active ord.
   */
  public abstract OrdTarget getActiveOrdTarget();

  /**
   * Get the BWbView currently being used to
   * view (and/or edit) the active ord.
   */
  public abstract BWbView getActiveView();

  /**
   * Get the BWbProfile which defines how the WbShell
   * is used for a specific application.
   */
  public abstract BWbProfile getProfile();

  /**
   * Convenience for <code>hyperlink(new HyperlinkInfo(ord))</code>
   * which does a replace hyperlink to the specified ord.
   */
  public final void hyperlink(BOrd ord)
  {
    hyperlink(new HyperlinkInfo(ord));
  }

  /**
   * Get the command used to refresh the current plugin.
   */
  public abstract Command getRefreshCommand();

  /**
   * Get the command used to save the current plugin.
   */
  public abstract Command getSaveCommand();

  /**
   * Get the command used to export the current plugin.
   */
  public abstract Command getExportCommand();

  /**
   * Get the command used to navigate back one view.
   */
  public abstract Command getBackCommand();

  /**
   * Get the command used to navigate forward one view.
   */
  public abstract Command getForwardCommand();

  /**
   * Get the command used to log off the current session.
   */
  public abstract Command getLogoffCommand();

  /**
   * Add an ActivityListener that gets notified each time user activity
   * is made in workbench.
   *
   * @param listener the listener to add
   * @since Niagara 4.4
   */
  public void addActivityListener(ActivityListener listener)
  {

  }

  /**
   * remove an ActivityListener
   *
   * @param listener the listener to remove
   * @since Niagara 4.4
   */
  public void removeActivityListener(ActivityListener listener)
  {

  }

  /**
   * Open a dialog to notify the user that their session is about to timeout
   *
   * @param widget the parent widget
   * @param session the session that is expiring
   * @return true if the timeout should be reset, false otherwise.
   * @since Niagara 4.4
   */
  public boolean notifyTimeout(BWidget widget, BISession session)
  {
    return false;
  }

  /**
   * A listener to be notified when workbench activity is detected
   *
   * @since Niagara 4.4
   */
  public interface ActivityListener
  {
    /**
     * Called when workbench activity is detected
     */
    public void activity();
  }

  /**
   * Get the widget shell cast to a BWbShell.  If the widget
   * is not mounted in a shell or the shell isn't an instanceof
   * BWbShell then return null.  If the widget is in a dialog,
   * then find the parent BWbShell.
   */
  public static BWbShell getWbShell(BWidget widget)
  {
    // if I am in a wb shell, easy as pie
    BWidgetShell shell = widget.getShell();
    if (shell instanceof BWbShell)
      return (BWbShell)shell;

    // check if I am in a dialog parented by a shell
    while (shell instanceof BDialog || shell instanceof BRoundedDialog)
    {
      BWidget owner;
      if( shell instanceof BDialog)
        owner = ((BDialog)shell).getOwner();
      else
        owner = ((BRoundedDialog)shell).getOwner();
      
      if (owner != null)
      {
        shell = owner.getShell();
        if (shell instanceof BWbShell)
          return (BWbShell)shell;
      }
    }

    return null;
  }

  public Context getContext() { return null; }

}
