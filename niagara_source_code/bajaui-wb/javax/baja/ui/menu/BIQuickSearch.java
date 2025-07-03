/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.menu;

import javax.baja.agent.BIAgent;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;

/**
 * BIQuickSearch is an interface implemented by global quick search BWidgets. It is
 * used to populate the global quick search box embedded to the right of the menu bar.
 *
 * BIQuickSearch is also a BIAgent implementation.  The reason is that the quick search
 * widget is pluggable for a BWbProfile or even a specific BWbView that is currently in
 * view in Workbench.  Implementations of BIQuickSearch should register as agents on
 * BWbProfile (or one of its subclasses) or a specific BWbView. BIQuickSearch agents on
 * BWbView take precedence over BIQuickSearch agents on BWbProfile.  Furthermore, agent
 * filtering is used to further refine the quick search implementation selected for use.
 * For example, a JavaFx implementation of quick search may be requested by filtering for
 * BFxWidget implementations of BIQuickSearch that are registered as agents on the
 * BWbProfile or BWbView.
 *
 * Also note that for the global quick search box, it is expected that when asGlobalWidget()
 * is called on a BIQuickSearch instance, it should return a single (reused) instance.
 * Thus the setView() callback can be used to notify the reused BIQuickSearch instance
 * about the new view for which it is currently being utilized.
 *
 * @author Scott Hoye
 * @creation  1/23/2014
 * @since Niagara 4.0
 *
 */
@NiagaraType
public interface BIQuickSearch extends BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.menu.BIQuickSearch(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIQuickSearch.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Return true if this global quick search should be visible/enabled
   * in the menu bar for the current view.  Returning false will remove
   * (hide) the quick search box from the menu bar unless another valid
   * BIQuickSearch implementation is found.  Note that this method could
   * be called frequently on the client (Workbench) side, so avoid or minimize
   * network calls when implementing this method (consider using caching to reduce
   * network calls).
   */
  public boolean isQuickSearchEnabled(BWidget view);

  /**
   * Return the single, reused quick search as a BWidget for the current
   * view.  Make sure to initialize the view and id on the reused widget
   * before returning it from this method.
   *
   * @param view The current view in Workbench for the widget to reference
   * @param id The id to assign to the widget
   * @return a reused BWidget implementation for the global quick search
   */
  public BWidget asGlobalWidget(BWidget view, String id);

}
