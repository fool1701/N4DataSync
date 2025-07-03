/*
 * Copyright 2012, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.web;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.user.*;

import com.tridium.sys.schema.*;

/**
 * BClientEnvironment provides support for a web client environment.  A client environment
 * is selected by passing a web request (WebOp) to each available BClientEnvironment and
 * allowing the first matching environment to provide a web profile.
 * <p>
 * The available client environments are listed under the clientEnvs property of the
 * WebService.
 * 
 * @author    John Sublett
 * @creation  01 Apr 2012
 * @version   $Revision$ $Date$
 * @since     Niagara 3.7
 */
@NiagaraType
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.TRANSIENT | Flags.READONLY
)
public abstract class BClientEnvironment
  extends BComponent
  implements BIStatus
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BClientEnvironment(775232858)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.TRANSIENT | Flags.READONLY, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.TRANSIENT | Flags.READONLY, "", null);

  /**
   * Get the {@code faultCause} property.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BClientEnvironment.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Examines the specified web request and returns an appropriate IWebEnv
   * to handle the request.  If this environment is not a match
   * for the specified web request, null is returned.
   *
   * @param webOp The web request to match.
   * @return If the web request matches this environment, an IWebEnv is returned.
   *   If the requesting environment does not match, or if the ClientEnvironment
   *   is disabled or in fault status, null is returned.
   */
  public final IWebEnv matchWebEnv(WebOp webOp)
  {
    if (getStatus().isDisabled() || getStatus().isFault())
      return null;
    else
      return doMatchWebEnv(webOp);
  }
  
  /**
   * Examines the specified web request and returns an appropriate IWebEnv
   * to handle the request.  If this environment is not a match
   * for the specified web request, null is returned.
   *
   * @param webOp The web request to match.
   * @return If the web request matches this environment, an IWebEnv is returned.
   *   If the requesting environment does not match, null is returned.
   */
  protected abstract IWebEnv doMatchWebEnv(WebOp webOp);

  /**
   * Get the IWebEnv associated with this ClientEnvironment.  In some cases
   * the IWebEnv depends on information provided by the WebOp (ex. the user).
   *
   * @return The associated IWebEnv or null if the ClientEnvironment
   *   is disabled.
   */
  public final IWebEnv getWebEnv(WebOp op)
  {
    if (getStatus().isDisabled())
      return null;
    else
      return doGetWebEnv(op);
  }

  /**
   * Get the IWebEnv associated with this ClientEnvironment.
   */
  protected abstract IWebEnv doGetWebEnv(WebOp op);

  public void started()
  {
    if (!getEnabled())
      updateStatus(BStatus.makeDisabled(getStatus(), true), null);
  }

  /**
   * Handle a property change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if (p.equals(enabled))
    {
      updateStatus(BStatus.makeDisabled(getStatus(), !getEnabled()), null);
    }
    else
      super.changed(p, cx);
  }

  /**
   * Callback allows subclasses to override status changes.  Subclasses
   * must call super.updateStatus().
   *
   * @param newStatus The new status bits to set for this environment.
   * @param faultCause The new fault cause.  If null, fault cause is
   *   not changed.
   */
  protected void updateStatus(BStatus newStatus, String faultCause)
  {
    setStatus(newStatus);
    if (faultCause != null)
      setFaultCause(faultCause);
  }
}
