/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIService;
import javax.baja.sys.BIcon;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Clock.Ticket;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.ExecutorUtil;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;
import javax.baja.util.Lexicon;

import com.tridium.security.BISecurityService;
import com.tridium.sys.Nre;
import com.tridium.sys.schema.Fw;
import com.tridium.sys.service.BServiceEvent;
import com.tridium.sys.service.ServiceListener;
import com.tridium.web.servlets.UnauthenticatedCache;

/**
 * This class serves as the base for any class wishing to provide web server capabilities. A web
 * server must reside as a child of a {@link BWebService}. This class is primarily responsible for
 * managing the lifecycle of {@link #stopWebServer() stopping} and {@link #startWebServer() starting}
 * the web server.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 * @since Niagara 4.0
 */
@NiagaraType
/*
 The running state of the web server.
 */
@NiagaraProperty(
  name = "serverState",
  type = "String",
  defaultValue = "stopped",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 Restart the web server.
 */
@NiagaraAction(
  name = "restart",
  flags = Flags.ASYNC
)
public abstract class BWebServer
  extends BComponent 
  implements BIService
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BWebServer(2710433030)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "serverState"

  /**
   * Slot for the {@code serverState} property.
   * The running state of the web server.
   * @see #getServerState
   * @see #setServerState
   */
  public static final Property serverState = newProperty(Flags.TRANSIENT | Flags.READONLY, "stopped", null);

  /**
   * Get the {@code serverState} property.
   * The running state of the web server.
   * @see #serverState
   */
  public String getServerState() { return getString(serverState); }

  /**
   * Set the {@code serverState} property.
   * The running state of the web server.
   * @see #serverState
   */
  public void setServerState(String v) { setString(serverState, v, null); }

  //endregion Property "serverState"

  //region Action "restart"

  /**
   * Slot for the {@code restart} action.
   * Restart the web server.
   * @see #restart()
   */
  public static final Action restart = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code restart} action.
   * Restart the web server.
   * @see #restart
   */
  public void restart() { invoke(restart, null, null); }

  //endregion Action "restart"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWebServer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Web Server
////////////////////////////////////////////////////////////////

  protected final BWebService getWebService()
  {
    return (BWebService)getParent();
  }

  /**
   * When a configuration property changes on the parent {@link BWebService}, the web service will
   * invoke this callback. The default behavior of this method is to schedule a delayed restart
   * if the web server {@link #isRunning()}
   *
   * @param property The Property that changed on the web service.
   * @param context The Context of the change.
   */
  final void webServiceConfigurationChanged(Property property, Context context)
  {
    post(() ->
    {
      doWebServiceConfigurationChanged(property, context);
      if (isRestartRequired(property, context))
      {
        scheduleRestart();
      }
    });
  }

  protected boolean isRestartRequired(Property property, Context context)
  {
    return true;
  }

  protected void doWebServiceConfigurationChanged(Property property, Context context) {}

  protected abstract void doStartWebServer()
    throws Exception;

  /**
   * Stop the web server.
   *
   * @deprecated use {@link #doStopWebServer(Context)} instead.
   */
  @Deprecated
  protected abstract void doStopWebServer()
    throws Exception;

  /**
   * Stop the web server. The context can be used to modify the stop behavior.
   *
   * @param context
   * @throws Exception
   *
   * @since Niagara 4.9
   */
  protected void doStopWebServer(Context context)
    throws Exception
  {
    doStopWebServer();
  }

  /**
   * Register a WebServlet Component with the Web Server. This will dynamically
   * mount a Servlet so it can be accessed through the Web Server.
   *
   * @param servlet The Servlet to register.
   */
  public final void register(BINiagaraWebServlet servlet)
  {
    post(() -> doRegister(servlet));
  }

  /**
   * @see BWebServer#register(BINiagaraWebServlet)
   *
   * @param servlet The Web Servlet to register.
   */
  protected abstract void doRegister(BINiagaraWebServlet servlet);

  /**
   * Unregister the Servlet from the Web Server. This will unmount the Servlet
   * so it can no longer be accessed from the Web Server.
   *
   * @param servlet The Servlet to unregister.
   */
  public final void unregister(BINiagaraWebServlet servlet)
  {
    post(() -> doUnregister(servlet));
  }

  /**
   * @see BWebServer#unregister(BINiagaraWebServlet)
   *
   * @param servlet The Web Servlet to unregister.
   */
  protected abstract void doUnregister(BINiagaraWebServlet servlet);

  /**
   * Return an array of the registered BWebServlets.
   *
   * We have changed getServlets() to return a new BINiagaraWebServlet type. This method was
   * added if developers need the old behavior that returned only BWebServlets and its subclasses.
   *
   * @return An array of the currently registered BWebServlets.
   * @since Niagara 4.11
   */
  public BWebServlet[] getWebServlets()
  {
    BINiagaraWebServlet[] servlets = getServlets();
    Collection<BWebServlet> webServlets = new ArrayList<>(servlets.length);
    for (BINiagaraWebServlet servlet : servlets)
    {
      if (servlet instanceof BWebServlet)
      {
        webServlets.add((BWebServlet) servlet);
      }
    }
    return webServlets.toArray(EMPTY_WEB_SERVLET_ARRAY);
  }

  /**
   * Return an array of the registered Servlets. Includes any implementations of BINiagaraWebServlet and
   * not only BWebServlets. The signature was changed with the addition of the BINiagaraWebServlet type.
   * The getWebServlets() method is available if only BWebServlet and its subclasses are required.
   *
   * @return an array of the currently registered BINiagaraWebServlets
   * @since Niagara 4.11
   */
  public abstract BINiagaraWebServlet[] getServlets();

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  /**
   * Start the web server. The web server will only start if the parent web service is not disabled
   * and not in fault.
   * <p/>
   * This should only ever be invoked from the Web Server Config Thread or on start up.
   */
  private void startWebServer()
  {
    final BWebService service = getWebService();
    try
    {
      if (service.isDisabled())
      {
        if (log.isLoggable(Level.FINE))
        {
          log.fine(lex.getText("webserver.serviceNotOperational"));
        }
        return;
      }
      if (getWebService().getWebServer() != this)
      {
        log.warning(lex.getText("webserver.notPrimary"));
        return;
      }
      if (isServerRunning())
      {
        return;
      }

      UnauthenticatedCache.init();

      transitionToState(ServerState.starting);
      doStartWebServer();
      transitionToState(ServerState.started);
      service.configOk();
      
      // Register our parent service with the security service
      Optional<BIService> securityService = Sys.findService(BISecurityService.TYPE);
      if (securityService.isPresent())
      {
        securityService.get().as(BISecurityService.class).register(service);
      }
      else
      {
        // Register a service listener so we get notified if someone dropped one
        AccessController.doPrivileged((PrivilegedAction<Void>)() ->
        {
          Nre.getServiceManager().addServiceListener(serviceListener);
          return null;
        });
      }
    }
    catch (Throwable t)
    {
      stopWebServer(null);
      setFailed(t);
      log.info(lex.getText("webserver.scheduleRestart", new Object[] { RESTART_DELAY_SECONDS }));
      scheduleRestart(BRelTime.makeSeconds(RESTART_DELAY_SECONDS));
    }
  }

  /**
   * Stop the web server.
   * <p/>
   * This should only ever be invoked from the Web Server Config Thread.
   * 
   * @deprecated use {@link #stopWebServer(Context) instead}
   */
  @Deprecated
  final void stopWebServer()
  {
    stopWebServer(null);
  }
  
  /**
   * Stop the web server.
   * <p/>
   * This should only ever be invoked from the Web Server Config Thread.
   */
  final void stopWebServer(Context cx)
  {
    try
    {
      if (!isServerRunning())
      {
        return;
      }
      transitionToState(ServerState.stopping);
      doStopWebServer(cx);
      transitionToState(ServerState.stopped);
    }
    catch (Throwable t)
    {
      setFailed(t);
    }
    finally
    {
      Sys.findService(BISecurityService.TYPE).ifPresent(s -> s.as(BISecurityService.class).unregister(getWebService()));

      // remove service listener, if it was present
      AccessController.doPrivileged((PrivilegedAction<Void>)() ->
      {
        Nre.getServiceManager().removeServiceListener(serviceListener);
        return null;
      });
    }
  }

  /**
   * Schedule a restart for 500ms from now. Any pending restart is cancelled and a new one is
   * scheduled.
   */
  protected final synchronized void scheduleRestart()
  {
    scheduleRestart(BRelTime.make(500));
  }

  protected synchronized void scheduleRestart(BRelTime delay)
  {
    restartTicket.cancel();
    restartTicket = Clock.schedule(this, delay, restart, null);
  }

  /**
   * Implementation of {@link #restart} action.
   *
   * @param cx the Context at time of invocation.
   */
  @SuppressWarnings("unused")
  public final void doRestart(Context cx)
  {
    log.fine("Restarting " + getType());
    stopWebServer(cx);
    if (!isServerRunning())
    {
      startWebServer();
    }
  }

  private void transitionToState(ServerState newState)
  {
    if (newState == ServerState.failed)
    {
      setFailed(new Throwable("web server indicated unknown failure").fillInStackTrace());
    }
    else
    {
      log.log(Level.FINE, newState.name() + " {0}", getType());
      setServerState(newState.name());
      state = newState;
    }
  }

  private void setFailed(Throwable t)
  {
    log.log(Level.SEVERE, "failed: " + t, t);
    setServerState(ServerState.failed.name());
    state = ServerState.failed;
    getWebService().configFail(Objects.toString(t.getMessage(), ""));
  }

  private boolean isServerRunning()
  {
    return ServerState.started == state || ServerState.starting == state;
  }

  /**
   * Post some work to the Web Server Config Thread.
   *
   * @param r The work to be done.
   */
  public final Future<?> post(Runnable r)
  {
    if (executorService == null)
    {
      throw new IllegalStateException("Cannot post to Web Server Config Thread. Thread not running: " + r.toString());
    }

    return executorService.submit(r);
  }
  
////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////

  @Override
  public Type[] getServiceTypes()
  {
    return new Type[] { TYPE };
  }

  @Override
  public void serviceStarted() throws Exception {}

  @Override
  public void serviceStopped() throws Exception {}

  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    if (Fw.SERVICE_STARTED == x)
    {
      // Create a single worker thread with a queue. The thread will close itself after X
      // seconds of not being used. It will lazily start up again on demand.
      executorService = ExecutorUtil.newSingleThreadBackgroundExecutor("webServerConfig", 1, TimeUnit.MINUTES);
    }
    else if (Fw.SERVICE_STOPPED == x)
    {
      post(() ->
      {
        stopWebServer(null);
        executorService.shutdownNow();
      });
    }
    else if (Fw.STARTED == x)
    {
      CountDownLatch startedLatch = new CountDownLatch(1);
      CountDownLatch postLatch = new CountDownLatch(1);

      // Block this thread until the web server has started up. All
      // other configuration happens within this thread so we don't want to
      // have this thread restarting (or configuring) the web server at the same
      // time. This will probably never happen but we want to latch this
      // just in case. Please note, we could try blocking the other way around but
      // we can't do this due to the synchronization used during the started callback.
      post(() ->
      {
        try
        {
          // Let the EngineThread know it can proceed.
          postLatch.countDown();

          // Wait until the web server has finished trying to start.
          startedLatch.await();
        }
        catch (InterruptedException ignore) {}
      });

      // Wait until the webServerConfig thread is blocked.
      try
      {
        postLatch.await();
      }
      catch (InterruptedException ignore) {}

      try
      {
        // Now the config thread is blocked, try starting the web server...
        startWebServer();
      }
      finally
      {
        // Now the web server has finished trying to start,
        // unblock the webServerConfig thread.
        startedLatch.countDown();
      }
    }

    return super.fw(x, a, b, c, d);
  }

  /**
   * May only be parented by a {@link BWebService}.
   * @param parent the parent component.
   * @return true if the parent is a {@link BWebService}. false otherwise.
   */
  @Override
  public final boolean isParentLegal(BComponent parent)
  {
    return super.isParentLegal(parent) && (parent instanceof BWebService);
  }

  @Override
  public IFuture post(Action action, BValue argument, Context cx)
  {
    if (restart == action && isRunning())
    {
      post(new Invocation(this, action, argument, cx));
      return null;
    }
    else
    {
      return super.post(action, argument, cx);
    }
  }

  @Override
  public BIcon getIcon()
  {
    return Icon;
  }

  @Override
  public String toString(Context context)
  {
    return String.format("%s (%s)", super.toString(context), getServerState());
  }

////////////////////////////////////////////////////////////////
// ServiceListener
////////////////////////////////////////////////////////////////

  private final ServiceListener serviceListener = new ServiceListener()
  {
    @Override
    public void serviceEvent(BServiceEvent event)
    {
      if (event.getServiceType().is(BISecurityService.TYPE) &&
          event.getId() == BServiceEvent.SERVICE_ADDED)
      {
        event.getService()
             .as(BISecurityService.class)
             .register(getWebService());
      }
    }
  };
  
////////////////////////////////////////////////////////////////
// Session Management
////////////////////////////////////////////////////////////////

  /**
   * This method should invalidate all currently active sessions tied to this BWebServer, removing them,
   * if appropriate, from the SessionManager as well.
   * This will be called when the BWebService.resetAllConnections is invoked.
   *
   * @since Niagara 4.4
   */
  protected void invalidateAllSessions()
  {
    throw new UnsupportedOperationException("Invalidate all sessions not supported by this BWebServer");
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private ExecutorService executorService;

  protected enum ServerState { stopped, starting, started, stopping, failed }

  /** Current lifecycle state of the server */
  private volatile ServerState state = ServerState.stopped;

  /** For scheduling a restart in the future */
  protected volatile Ticket restartTicket = Clock.expiredTicket;

  public static final Logger log = Logger.getLogger(BWebServer.class.getName());
  private static final BIcon Icon = BIcon.std("web.png");
  private static final int RESTART_DELAY_SECONDS = 5;
  private static final Lexicon lex = Lexicon.make(BWebServer.class);
  protected static final BWebServlet[] EMPTY_WEB_SERVLET_ARRAY = new BWebServlet[0];
}
