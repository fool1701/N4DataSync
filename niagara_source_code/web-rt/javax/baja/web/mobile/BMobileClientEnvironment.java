/*
 * Copyright 2012, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.web.mobile;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.license.FeatureNotLicensedException;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.user.BUser;
import javax.baja.web.BIWebProfile;
import javax.baja.web.BUserAgentClientEnvironment;
import javax.baja.web.BWebProfileConfig;
import javax.baja.web.IWebEnv;
import javax.baja.web.WebOp;

import com.tridium.sys.license.LicenseUtil;
import com.tridium.sys.schema.Fw;
import com.tridium.web.IWebEnvProvider;
import com.tridium.web.WebProcessException;
import com.tridium.web.session.WebSessionUtil;

/**
 * A client environment that selects the mobile web environment based on
 * a pattern match with the user-agent of a web request.
 * 
 * @author John Sublett on 02 Apr 2012
 * @since Niagara 3.7
 */
@NiagaraType
@NiagaraProperty(
  name = "userAgentPattern",
  type = "String",
  defaultValue = "BMobileClientEnvironment.DEFAULT_USER_AGENT_PATTERN",
  override = true
)
public final class BMobileClientEnvironment
  extends BUserAgentClientEnvironment
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.mobile.BMobileClientEnvironment(80079514)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "userAgentPattern"

  /**
   * Slot for the {@code userAgentPattern} property.
   * @see #getUserAgentPattern
   * @see #setUserAgentPattern
   */
  public static final Property userAgentPattern = newProperty(0, BMobileClientEnvironment.DEFAULT_USER_AGENT_PATTERN, null);

  //endregion Property "userAgentPattern"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMobileClientEnvironment.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public Type getWebProfileConfigType()
  {
    return BMobileWebProfileConfig.TYPE;
  }
  
  public IWebEnv makeWebEnv(WebOp op)
  {
    IWebEnv env = null;
    BUser user = op.getUser();
    BWebProfileConfig cfg = (BWebProfileConfig)user.getMixIn(getWebProfileConfigType());
    
    if (cfg != null)
    {
      // Stash the profile config in the session so that the profile configuration
      // can be associated with the active profile while processing the request.
      op.getRequest().getSession(true).setAttribute("profileConfig", cfg);
      
      BIWebProfile profile = (BIWebProfile)cfg.make();

      if (profile instanceof IWebEnvProvider)
      {
        try
        {
          env = ((IWebEnvProvider)profile).getWebEnv(op);
        }
        catch(WebProcessException ex)
        {
          LOGGER.log(Level.WARNING, "WebEnv cannot be obtained, falling back to BMobileWebProfile.webEnv", ex);
        }
      }
    }

    if (env == null)
    {
      env = BMobileWebProfile.webEnv();
    }
    if (cfg != null)
    {
      //Stash the hx profile config
      BWebProfileConfig config = env.getHxProfileConfig(cfg);
      op.getRequest().getSession(true).setAttribute("hxProfileConfig", env.getHxProfileConfig(cfg));
      WebSessionUtil.getSession(op.getRequest().getSession()).setAttribute("hxProfileConfig", config);
    }
    return env;
  }

  /**
   * Framework.
   */
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    if (x == Fw.STARTED)
    {
      try
      {
        Sys.getLicenseManager().checkFeature(LicenseUtil.TRIDIUM_VENDOR, "mobile");
        licensed = true;
        
        // If Mobile is not available then set a fault
        if (!isMobileAvailable())
          updateStatus(BStatus.makeFault(getStatus(), true), "Mobile not installed");
      }
      catch (FeatureNotLicensedException ex)
      {
        licensed = false;
        updateStatus(BStatus.makeFault(getStatus(), true), "Feature not licensed: mobile"); 
      }
    }
    else if (x == Fw.CHANGED)
    {
      if (isRunning())
      {
        // make sure unlicensed always equals fault status
        if (a.equals(status))
        {
          if ((!licensed || !isMobileAvailable()) && !getStatus().isFault())
            updateStatus(getStatus(), null);
        }
      }
    }

    return super.fw(x,a,b,c,d);
  }
  
  /**
   * Update the status based on the license.
   */
  protected final void updateStatus(BStatus newStatus, String faultCause)
  {
    if (licensed)
    {
      // If Mobile is not available then set a fault
      if (!isMobileAvailable())
      {
        setStatus(BStatus.makeFault(newStatus, true));
        setFaultCause("Mobile not installed");
      }
      else
        super.updateStatus(newStatus, faultCause);
    }
    else
    {
      setStatus(BStatus.makeFault(newStatus, true));
      setFaultCause("Feature not licensed: mobile");
    }
  }
    
  public static final boolean isMobileAvailable()
  {
    boolean res = false;
    try
    {
      // If there are concrete Mobile Web Profile's available then Mobile is available
      if (Sys.getRegistry().getConcreteTypes(BIMobileWebProfile.TYPE.getTypeInfo()).length > 0) 
        res = true;
    }
    catch(Throwable ignore) {}
    return res;
  }

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private boolean licensed = false;
  public static final Logger LOGGER = Logger.getLogger(BMobileClientEnvironment.class.getName());

  private static final String DEFAULT_USER_AGENT_PATTERN = "mobile|android|playbook|touch";
}
