/*
 * Copyright 2012, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.web;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.user.BUser;

import com.tridium.web.IWebEnvProvider;
import com.tridium.web.WebEnv;
import com.tridium.web.WebProcessException;
import com.tridium.web.session.WebSessionUtil;

/**
 * The default client environment provides a web profile for web requests
 * when no other client environment matches a web request.
 * 
 * @author    John Sublett
 * @creation  01 Apr 2012
 * @version   $Revision$ $Date$
 * @since     Niagara 3.7
 */
@NiagaraType
public class BDefaultClientEnvironment
  extends BClientEnvironment
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BDefaultClientEnvironment(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:43 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDefaultClientEnvironment.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Get the web profile from the user for this request.
   */
  public IWebEnv doMatchWebEnv(WebOp op)
  {
    return doGetWebEnv(op);
  }
  
  /**
   * Get the web profile from the user for this request.
   */
  public IWebEnv doGetWebEnv(WebOp op)
  {
    BUser user = op.getUser();
    IWebEnv env;
    BWebProfileConfig cfg = (BWebProfileConfig)user.getMixIn(BWebProfileConfig.TYPE);
    if (cfg == null)
      return null;
    else
    {
      BIWebProfile profile = (BIWebProfile)cfg.make();
      
      // Stash the profile config in the session so that the profile configuration
      // can be associated with the active profile while processing the request.
      op.getRequest().getSession(true).setAttribute("profileConfig", cfg);

      if (profile instanceof IWebEnvProvider)
      {
        try
        {
          env = ((IWebEnvProvider)profile).getWebEnv(op);
        }
        catch(WebProcessException ex)
        {
          LOGGER.log(Level.SEVERE, "WebEnv cannot be obtained", ex);
          return null;
        }
      }
      else
      {
        if (profile.getType().is(WebEnv.wbProfile))
          env = WebEnv.wb();
        else
          env = WebEnv.hx();
      }
    }


    //Stash the hx profile config
    if(env != null)
    {
      BWebProfileConfig config = env.getHxProfileConfig(cfg);
      op.getRequest().getSession(true).setAttribute("hxProfileConfig", config);
      WebSessionUtil.getSession(op.getRequest().getSession()).setAttribute("hxProfileConfig", config);
    }

    return env;
  }




  public static final Logger LOGGER = Logger.getLogger(BDefaultClientEnvironment.class.getName());
}
