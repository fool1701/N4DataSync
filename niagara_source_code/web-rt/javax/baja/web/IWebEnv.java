/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.naming.BOrd;
import javax.baja.user.BUser;

/**
 * IWebEnv is used to model the user agent environment so that
 * we can tailor which pieces of the framework (such as views)
 * make sense to apply.
 *
 * @author  gjohnson
 * @creation  3 Aug 2011
 * @version  1
 * @since   Niagara 3.7
 */
public interface IWebEnv
{
  /**
   * This hook allows a WebEnv to map a viewId into the 
   * AgentInfo to use the specified view id.
   */
  public AgentInfo getView(AgentList allViews, String viewId);

  /**
   * Get the default view to use for the environment.
   */
  public AgentInfo getDefaultView(WebOp op, AgentList hasViews);

  /**
   * This hook allows a WebEnv to translate a view into
   * another agent type.
   * The default does no translation and if you override this method, make sure not to return null.
   * @deprecated As of Niagara 4.4, this function can take a WebOp as well.
   * @see javax.baja.web.IWebEnv#translate(WebOp, AgentInfo)
   */
  @Deprecated
  default AgentInfo translate(AgentInfo viewInfo)
  {
    return viewInfo;
  }
  /**
   * This hook allows a WebEnv to translate a view into
   * another agent type and take the WebOp's Profile into account.
   * The default does no translation and if you override this method, make sure not to return null.
   */
  @SuppressWarnings("deprecation")
  default AgentInfo translate(WebOp op, AgentInfo viewInfo){
    return translate(viewInfo);
  }


  /**
   * Return the full list of views supported by the environment 
   * on the target object (not including security checks).
   */
  public AgentList getViews(WebOp op);

  /**
   * Return the Web Profile for the given WebOp.
   */
  public BIWebProfile getWebProfile(WebOp op);

  /**
   * Get the WebProfileConfig for this environment from the user.  Typically
   * the profile config is a mixin on the user.
   */
  public BWebProfileConfig getWebProfileConfig(BUser user);
  
  /**
   * Make a default WebProfileConfig for this environment.
   */
  public BWebProfileConfig makeWebProfileConfig();

  /**
   * Get the Hx specific WebProfileConfig for this environment from the standard WebProfileConfig.
   * @since Niagara 4.6
   */
  default BWebProfileConfig getHxProfileConfig(BWebProfileConfig config)
  {
    return config;
  }


  /**
   * Return the home page from the specified WebOp.
   */
  public BOrd getHomePage(WebOp op);
}
