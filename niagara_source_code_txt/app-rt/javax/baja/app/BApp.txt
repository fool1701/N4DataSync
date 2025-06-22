/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.app;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.license.BILicensed;
import javax.baja.license.Feature;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIService;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.Nre;
import com.tridium.sys.schema.Fw;
import com.tridium.sys.service.BServiceEvent;
import com.tridium.sys.service.ServiceListener;
import com.tridium.sys.service.ServiceManager;

/**
 * Niagara Application
 *
 * @author		gjohnson
 * @creation 	27 Jul 2011
 * @version 	1
 * @since 		Niagara 3.7
 */
@NiagaraType
/*
 App Status
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 App Fault Cause
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 App enable
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 App version
 */
@NiagaraProperty(
  name = "version",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY
)
/*
 Update App Configuration
 */
@NiagaraAction(
  name = "update",
  flags = Flags.ASYNC | Flags.HIDDEN
)
public abstract class BApp
    extends BComponent
    implements BIService, BIStatus, ServiceListener, BIAppComponent, BILicensed
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.app.BApp(984032451)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * App Status
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.READONLY | Flags.TRANSIENT, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * App Status
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * App Status
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * App Fault Cause
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.READONLY | Flags.TRANSIENT, "", null);

  /**
   * Get the {@code faultCause} property.
   * App Fault Cause
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * App Fault Cause
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * App enable
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * App enable
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * App enable
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "version"

  /**
   * Slot for the {@code version} property.
   * App version
   * @see #getVersion
   * @see #setVersion
   */
  public static final Property version = newProperty(Flags.READONLY, "", null);

  /**
   * Get the {@code version} property.
   * App version
   * @see #version
   */
  public String getVersion() { return getString(version); }

  /**
   * Set the {@code version} property.
   * App version
   * @see #version
   */
  public void setVersion(String v) { setString(version, v, null); }

  //endregion Property "version"

  //region Action "update"

  /**
   * Slot for the {@code update} action.
   * Update App Configuration
   * @see #update()
   */
  public static final Action update = newAction(Flags.ASYNC | Flags.HIDDEN, null);

  /**
   * Invoke the {@code update} action.
   * Update App Configuration
   * @see #update
   */
  public void update() { invoke(update, null, null); }

  //endregion Action "update"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BApp.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// App
////////////////////////////////////////////////////////////////

  /**
   * Return the list of Services required for this App.
   */
  public Type[] getRequiredServices()
  {
    return noTypes;
  }

  /**
   * Return a String that uniquely identifies this App.
   */
  public String getAppId()
  {
    return getType().toString();
  }

////////////////////////////////////////////////////////////////
// App Component
////////////////////////////////////////////////////////////////

  @Override
  public String getAppDisplayName(Context cx)
  {
    return getDisplayName(cx);
  }

  @Override
  public BIcon getAppDisplayIcon()
  {
    return getIcon();
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////

  /**
   * This method is called when moving from disabled
   * state into the enabled state.
   */
  protected void enabled() {}

  /**
   * This method is called when moving from enabled
   * state into the disabled state.
   */
  protected void disabled() {}

  /**
   * This method is called when moving from a non-operational to an operational state
   */
  protected void appOk() {}

  /**
   * This method is called when moving from an operational to a non-operational state
   */
  protected void appFail() {}

////////////////////////////////////////////////////////////////
// Util
////////////////////////////////////////////////////////////////

  protected static final Type[] append(Type[] a, Type b)
  {
    return append(a, new Type[] { b });
  }

  protected static final Type[] append(Type[] a, Type[] b)
  {
    Type[] types = new Type[a.length + b.length];
    System.arraycopy(a, 0, types, 0, a.length);
    System.arraycopy(b, 0, types, a.length, b.length);
    return types;
  }

////////////////////////////////////////////////////////////////
// IService
////////////////////////////////////////////////////////////////

  @Override
  public void serviceStarted() throws Exception {}
  @Override
  public void serviceStopped() throws Exception {}

  @Override
  public Type[] getServiceTypes() { return new Type[] { getType() }; }

////////////////////////////////////////////////////////////////
// Component
////////////////////////////////////////////////////////////////

  @Override
  public final boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BIAppFolder;
  }

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////

  /**
   * Framework use only.
   */
  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.SERVICE_STARTED:      fwServiceStarted(); break;
      case Fw.SERVICE_STOPPED:      fwServiceStopped(); break;
      case Fw.DESCENDANTS_STARTED:  fwDescendantsStarted(); break;
      case Fw.CHANGED:              fwChanged((Property)a); break;
    }
    return super.fw(x, a, b, c, d);
  }

  private void fwServiceStarted()
  {
    // For now, just set the App version to the Module vendor version
    setVersion(getType().getVendorVersion().toString());

    container = (BAppContainer)Sys.getService(BAppContainer.TYPE);

    AccessController.doPrivileged((PrivilegedAction<ServiceManager>)() -> Nre.getServiceManager()).addServiceListener(this);

    checkLicense();
    update();
  }

  private void fwServiceStopped()
  {
    AccessController.doPrivileged((PrivilegedAction<ServiceManager>)() -> Nre.getServiceManager()).removeServiceListener(this);
  }

  private void fwDescendantsStarted()
  {
    update();
  }

  private void fwChanged(Property prop)
  {
    if (!isRunning()) return;

    if (prop.equals(enabled))
      update();
  }

////////////////////////////////////////////////////////////////
// Status
////////////////////////////////////////////////////////////////

  /**
   * Return true if the App is disabled.  An App is
   * disabled if the user has manually set the enabled property
   * to false.
   */
  public final boolean isDisabled()
  {
    return getStatus().isDisabled();
  }

  /**
   * Return true if the service is in fault.  An App
   * is in fault if either a fatal fault was detected or if
   * <code>configFail()</code> has been called more recently
   * than <code>configOk()</code>.  Refer to <code>faultCause</code>
   * for the fault reason.
   */
  public final boolean isFault()
  {
    return getStatus().isFault();
  }

  /**
   * Return true if the App is neither disabled, nor in fault.
   */
  public final boolean isOperational()
  {
    if (fatalFault) return false;
    return isOperational(getStatus());
  }

  private static boolean isOperational(BStatus status)
  {
    return !status.isDisabled() && !status.isFault();
  }

  /**
   * Update App Status
   */
  public void doUpdate()
  {
    if (!isRunning()) return;

    log.fine("App Update: " + getType());

    checkServices();

    boolean oldEnabled = !isDisabled();
    BStatus oldStatus = getStatus();
    int newBits = 0;

    // ... disabled bit
    if (!getEnabled())
      newBits |= BStatus.DISABLED;

    String cause = "";

    if (fatalFault)
    {
      newBits |= BStatus.FAULT;
      cause = fatalFaultCause;
    }
    else if (servicesFault)
    {
      newBits |= BStatus.FAULT;
      cause = servicesFaultCause;
    }
    else if (configFault)
    {
      newBits |= BStatus.FAULT;
      cause = configFaultCause;
    }

    // If the fault cause has changed then update it
    if (!getFaultCause().equals(cause)) setFaultCause(cause);

    // short circuit if nothing has changed since last time
    if (oldStatus.getBits() == newBits) return;
    BStatus newStatus = BStatus.make(newBits);
    setStatus(newStatus);

    // App ok and fail callbacks
    if (isOperational(oldStatus) && !isOperational(newStatus))
      appFail();
    else if (!isOperational(oldStatus) && isOperational(newStatus))
      appOk();

    // Check for enabled/disabled callbacks
    boolean newEnabled = !isDisabled();
    if (oldEnabled != newEnabled)
    {
      if (newEnabled)
        enabled();
      else
        disabled();
    }

    // If we're at a steady state then fire any modification events (certainly don't want to do this on start up)
    if (Sys.atSteadyState()) container.fireAppsModified(null);
  }

  private void checkServices()
  {
    // TODO: need to call this automatically whenever the framework detects a new Service has been added!
    servicesFault = false;

    // Check required Services
    Type[] requiredServices = getRequiredServices();
    for (int i = 0; i < requiredServices.length; i++)
    {
      try
      {
        Sys.getService(requiredServices[i]);
      }
      catch(ServiceNotFoundException ex)
      {
        servicesFault = true;
        servicesFaultCause = "Missing required service: " + requiredServices[i].toString();
        break;
      }
    }

    if (!servicesFault) servicesFaultCause = "";
  }

////////////////////////////////////////////////////////////////
// Fault
////////////////////////////////////////////////////////////////

  /**
   * Return true if the App detected a fatal fault.
   * Fatal faults cannot be recovered until the App
   * is restarted.  Fatal faults trump config faults.
   */
  public final boolean isFatalFault()
  {
    return fatalFault;
  }

  /**
   * Return true if a config fault was detected
   */
  public final boolean isConfigFault()
  {
    return configFault;
  }

  /**
   * Return true if a services fault was detected (i.e. a required service is not installed)
   */
  public final boolean isServicesFault()
  {
    return servicesFault;
  }

  /**
   * Clear the configuration fault status.  If there are no fatal
   * or service faults then clear the entire App's fault status, otherwise
   * the service remains in fault.
   */
  public final void configOk()
  {
    // clear config fault flag
    configFault = false;
    configFaultCause = "";

    update();
  }

  /**
   * Set the service into configuration fault.  If the service was
   * previously not in fault, then this sets the service into fault.
   */
  public final void configFail(String cause)
  {
    // set config fault flag
    configFault = true;
    configFaultCause = cause;

    update();
  }

  /**
   * Set the service into the fatal fault condition.  Unlike
   * configFail(), the fatal fault condition cannot be cleared
   * until station restart.
   */
  public final void configFatal(String cause)
  {
    fatalFault = true;
    fatalFaultCause = cause;

    update();
  }

////////////////////////////////////////////////////////////////
// Licensing
////////////////////////////////////////////////////////////////

  /**
   * If this service is to be licensed using the standard licensing
   * mechanism then override this method to return the Feature or
   * return null for no license checks.  Convention is that the
   * vendor and feature name matches the declaring module.
   */
  @Override
  public Feature getLicenseFeature()
  {
    return null;
  }

  private void checkLicense()
  {
    try
    {
      // check feature
      Feature feature = getLicenseFeature();
      if (feature != null) feature.check();
    }
    catch(Exception e)
    {
      fatalFault = true;
      fatalFaultCause = "Unlicensed: " + e;
      Logger.getLogger("service").log(Level.SEVERE, "Unlicensed: " + toPathString(), e);
    }
  }

////////////////////////////////////////////////////////////////
// ServiceListener
////////////////////////////////////////////////////////////////

  @Override
  public final void serviceEvent(BServiceEvent event)
  {
    if (event.getId() == BServiceEvent.SERVICE_ADDED ||
        event.getId() == BServiceEvent.SERVICE_REMOVED)
    {
      update();
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final BIcon icon = BIcon.std("app.png");

  private static final Logger log = Logger.getLogger("app");
  private static final Type[] noTypes = new Type[0];

  private boolean fatalFault;
  private String fatalFaultCause = "";

  private boolean servicesFault;
  private String servicesFaultCause = "";

  private boolean configFault;
  private String configFaultCause = "";

  private BAppContainer container = null;
}
