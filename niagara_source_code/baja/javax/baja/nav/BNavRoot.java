/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nav;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.naming.BLocalHost;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconText;

/**
 * BNavRoot is the root of the navigation tree.  There is
 * exactly one instance accessed via INSTANCE.  Generally
 * only BHosts are mounted directly under the BNavRoot using
 * the factory method BHost.make().
 *
 * @author    Brian Frank
 * @creation  22 Jan 03
 * @version   $Revision: 10$ $Date: 1/6/04 6:20:58 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraSingleton
public final class BNavRoot
  extends BNavContainer
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.nav.BNavRoot(2747097003)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BNavRoot INSTANCE = new BNavRoot();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNavRoot.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  static final Logger eventLogger = Logger.getLogger("nav.event");
  /*static { eventLogger.setSeverity(Log.TRACE); }*/

  // force load of BLocalHost
  static
  {
    BLocalHost.INSTANCE.getHostname();
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BNavRoot()
  {
    super("root", LexiconText.make("baja", "nav.root"));
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  /**
   * Return BRootScheme.ORD
   */
  @Override
  public BOrd getNavOrd()
  {
    return BRootScheme.ORD;
  }

////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////

  /**
   * Get a list of the current NavListeners.
   */
  public NavListener[] getNavListeners()
  {
    NavListener[] r;
    synchronized(listeners)
    {
      r = listeners.toArray(new NavListener[listeners.size()]);
    }
    return r;
  }

  /**
   * Add a listener on the nav tree.
   */
  public void addNavListener(NavListener listener)
  {
    synchronized(listeners)
    {
      for(int i=0; i<listeners.size(); ++i)
        if (listeners.get(i) == listener) return;
      listeners.add(listener);
    }
  }

  /**
   * Remove a listener from the nav tree.
   */
  public void removeNavListener(NavListener listener)
  {
    synchronized(listeners)
    {
      for(int i=0; i<listeners.size(); ++i)
        if (listeners.get(i) == listener) { listeners.remove(i); break; }
    }
  }

  /**
   * Fire a NavEvent to all the current listeners.
   */
  @Override
  public void fireNavEvent(NavEvent event)
  {
    if (eventLogger.isLoggable(Level.FINE))
    {
      eventLogger.fine(event.toString());
    }

    // get safe copy of listeners
    NavListener[] x = getNavListeners();

    // fire to my listeners
    for(int i=0; i<x.length; ++i)
    {
      try
      {
        x[i].navEvent(event);
      }
      catch(Throwable e)
      {
        if (eventLogger.isLoggable(Level.FINE))
        {
          eventLogger.log(Level.FINE, "Fail to notify listener " + x[i] + " of nav event", e);
        }
      }
    }
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon =  BIcon.std("planet.png");

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);

    NavListener[] listeners = getNavListeners();
    out.startTable(false);
    out.trTitle("Listeners", 1);
    for(int i=0; i<listeners.length; ++i)
      out.tr(listeners[i]);
    out.endTable();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  List<NavListener> listeners = new ArrayList<>();

}
