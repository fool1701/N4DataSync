/*
 * Copyright 2012, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.web;

import javax.baja.license.Feature;
import javax.baja.license.FeatureNotLicensedException;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BVector;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.web.mobile.BMobileClientEnvironment;

import com.tridium.sys.license.LicenseUtil;
import com.tridium.web.BClientEnvServlet;

/**
 * A list of supported web client environments.  Environments in the list are evaluated in
 * order to determine if the one of the environments matches the environment of the
 * requesting client.  A match is typically determined by evaluating the user-agent
 * in the web request.
 * <p>
 * If no match is found, the default client environment is used.  The default is
 * the desktop environment which allows any available web profile to be used
 * to service the request.
 * 
 * @author John Sublett on 01 Apr 2012
 * @since Niagara 3.7
 */
@NiagaraType
@NiagaraProperty(
  name = "envServlet",
  type = "BClientEnvServlet",
  defaultValue = "new BClientEnvServlet()",
  flags = Flags.HIDDEN
)
public class BClientEnvironments
  extends BVector
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BClientEnvironments(1368062791)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "envServlet"

  /**
   * Slot for the {@code envServlet} property.
   * @see #getEnvServlet
   * @see #setEnvServlet
   */
  public static final Property envServlet = newProperty(Flags.HIDDEN, new BClientEnvServlet(), null);

  /**
   * Get the {@code envServlet} property.
   * @see #envServlet
   */
  public BClientEnvServlet getEnvServlet() { return (BClientEnvServlet)get(envServlet); }

  /**
   * Set the {@code envServlet} property.
   * @see #envServlet
   */
  public void setEnvServlet(BClientEnvServlet v) { set(envServlet, v, null); }

  //endregion Property "envServlet"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BClientEnvironments.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public final void started()
  {
    try
    {
      // automatically add the MobileClientEnvironment if the "mobile" feature
      // is licensed
      Feature mobileFeature = Sys.getLicenseManager().getFeature(LicenseUtil.TRIDIUM_VENDOR, "mobile");
      if ((mobileFeature != null) && !mobileFeature.isExpired() && BMobileClientEnvironment.isMobileAvailable())
      {
        if (get("mobile") == null)
          add("mobile", new BMobileClientEnvironment());
      }
    }
    catch (FeatureNotLicensedException ex)
    {
      // silently do nothing if not licensed
    }
  }

  /**
   * Get the IWebEnv for the specified web request.
   */
  public IWebEnv getWebEnv(WebOp op)
  {
    // first check the session to see if an environment has already been selected
    IWebEnv webEnv = (IWebEnv)op.getRequest().getSession(true).getAttribute("webenv");
    if (webEnv != null)
      return webEnv;
    
    // if no environment has been selected, walk through the children and check
    // each client environment for a match
    SlotCursor<Property> c = getProperties();
    while (c.next(BClientEnvironment.class))
    {
      BClientEnvironment clientEnv = (BClientEnvironment)c.get();
      webEnv = clientEnv.matchWebEnv(op);
      if (webEnv != null)
        break;
    }

    // if no IWebEnv was found, use the BDefaultClientEnvironment to
    // get the IWebEnv
    if (webEnv == null)
    {
      webEnv = new BDefaultClientEnvironment().getWebEnv(op);
    }

    op.getRequest().getSession(true).setAttribute("webenv", webEnv);
    return webEnv;
  }
}
