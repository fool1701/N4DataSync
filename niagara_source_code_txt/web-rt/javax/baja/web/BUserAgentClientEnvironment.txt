/*
 * Copyright 2012, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BComponentSpace;
import javax.baja.status.BStatus;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A client environment that matches based on patterns in the user-agent
 * of the request.
 *
 * @author    John Sublett
 * @creation  02 Apr 2012
 * @version   $Revision$ $Date$
 * @since     Niagara 3.7
 */
@NiagaraType
@NiagaraProperty(
  name = "userAgentPattern",
  type = "String",
  defaultValue = ""
)
public abstract class BUserAgentClientEnvironment
  extends BClientEnvironment
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BUserAgentClientEnvironment(2792416720)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "userAgentPattern"

  /**
   * Slot for the {@code userAgentPattern} property.
   * @see #getUserAgentPattern
   * @see #setUserAgentPattern
   */
  public static final Property userAgentPattern = newProperty(0, "", null);

  /**
   * Get the {@code userAgentPattern} property.
   * @see #userAgentPattern
   */
  public String getUserAgentPattern() { return getString(userAgentPattern); }

  /**
   * Set the {@code userAgentPattern} property.
   * @see #userAgentPattern
   */
  public void setUserAgentPattern(String v) { setString(userAgentPattern, v, null); }

  //endregion Property "userAgentPattern"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUserAgentClientEnvironment.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the type of the BIWebProfileConfig for this environment.
   * The returned BIWebProfileConfig will be added to BUser.
   */
  public abstract Type getWebProfileConfigType();

  /**
   * Make an IWebEnv for the specified web request.  When this callback
   * is invoked, the user-agent pattern has already been matched.
   */
  public abstract IWebEnv makeWebEnv(WebOp op);

////////////////////////////////////////////////////////////////
// Component callbacks
////////////////////////////////////////////////////////////////

  /**
   * Initialize on startup.
   */
  @Override
  public void started()
  {
    super.started();

    initPattern();
    enableMixIn();
  }

  /**
   * Cleanup on stop.
   */
  @Override
  public void stopped()
  {
    disableMixIn();
  }

  /**
   * Handle a property change.
   */
  @Override
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if (p.equals(userAgentPattern))
      initPattern();
    else
      super.changed(p, cx);
  }

  /**
   * Get the IWebEnv for the specified web request.  A match is
   * determined by comparing the requesting user-agent
   * to the userAgentPattern regular expression.  If no match
   * is found, null is returned.
   */
  @Override
  public IWebEnv doMatchWebEnv(WebOp op)
  {
    // check for a regular expresssion match with the user agent
    UserAgent ua = op.getUserAgent();
    if (ua == null)
    {
      return null;
    }
    
    String txt = ua.toString();
    if (matchesUserAgentPattern(txt))
    {
      return makeWebEnv(op);
    }
    else
    {
      return null;
    }
  }

  /**
   * Get the IWebEnv for the specified web request.
   */
  @Override
  public IWebEnv doGetWebEnv(WebOp op)
  {
    return makeWebEnv(op);
  }
  
  /**
   * Test whether the provided user-agent string matches the regular
   * expression pattern specified in the userAgentPattern property.
   * The pattern will be tested in a case insensitive manner.
   * 
   * @param txt The browser's user-agent string.
   * @return true if the text matches the user-agent pattern.
   */
  public boolean matchesUserAgentPattern(String txt)
  {
    Matcher matcher = compiledPattern.matcher(txt);
    if (matcher.find())
    {
      if (envLog.isLoggable(Level.FINE))
        envLog.fine("match: " + txt + " -> " + getUserAgentPattern());
      
      return true;
    }
    
    return false;
  }

////////////////////////////////////////////////////////////////
// Init
////////////////////////////////////////////////////////////////

  /**
   * Initialize the pattern for matching against the user agent.
   */
  private void initPattern()
  {
    // Compile RegExp
    int options = Pattern.DOTALL | Pattern.CASE_INSENSITIVE;

    try
    {
      compiledPattern = Pattern.compile(getUserAgentPattern(), options);
      updateStatus(BStatus.makeFault(getStatus(), false), "");
    }
    catch (PatternSyntaxException e)
    {
      updateStatus(BStatus.makeFault(getStatus(), true),
                   "Invalid expression: '"+getUserAgentPattern()+"'");
    }
  }

  /**
   * Enable the profile mixin
   */
  private void enableMixIn()
  {
    if (!isMounted()) return;
    if (getStatus().isFault()) return;

    BComponentSpace space = getComponentSpace();
    space.enableMixIn(getWebProfileConfigType());
  }

  /**
   * Disable the profile mixin.
   */
  private void disableMixIn()
  {
    BComponentSpace space = getComponentSpace();
    space.disableMixIn(getWebProfileConfigType());
  }

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private static Logger envLog = Logger.getLogger("web.env");

  private Pattern compiledPattern;

}
