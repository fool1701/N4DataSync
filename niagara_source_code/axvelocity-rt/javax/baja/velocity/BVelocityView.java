/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.velocity;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletResponse;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTypeSpec;
import javax.baja.web.BIWebProfile;
import javax.baja.web.BServletView;
import javax.baja.web.WebDev;
import javax.baja.web.WebOp;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.tridium.session.NiagaraSuperSession;
import com.tridium.session.SessionManager;

/**
 * BVelocityView is a servlet view based on an Apache Velocity template. The view
 * may work in conjunction with a BIVelocityWebProfile.
 * 
 * @see BIVelocityWebProfile
 * @see <a href="http://velocity.apache.org/">the Apache Velocity homepage</a>
 * 
 * @author    John Sublett
 * @creation  14 Jun 2011
 * @version   $Revision$ $Date$
 * @since     Niagara 3.7
 */
@NiagaraType
@SuppressWarnings("unchecked") //because Map has Object value
public abstract class BVelocityView
    extends BServletView
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.velocity.BVelocityView(2979906276)1.0$ @*/
/* Generated Thu Nov 18 10:14:51 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BVelocityView.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
////////////////////////////////////////////////////////////////
// Construction
////////////////////////////////////////////////////////////////  
  
  protected BVelocityView() {}
  
////////////////////////////////////////////////////////////////
// Servlet
////////////////////////////////////////////////////////////////

  public void doGet(WebOp op)
      throws Exception
  {
    loadPage(op);
  }


  public void doPost(WebOp op)
    throws Exception
  {
    loadPage(op);
  }

  /**
   * Writes out the velocity page. Does so with the profile if available.
   * @param op wrapper for this web request
   * @throws Exception
   */
  private void loadPage(WebOp op)
    throws Exception
  {
    BIWebProfile profile = (BIWebProfile)op.getProfileConfig().make();

    // TODO: This needs to work any sort of Profile. For instance, an Hx Profile!

    // Add the theme name to be used in profile.vm
    BTypeSpec theme = (BTypeSpec) op.getProfileConfig().get("theme");

    if (useProfile(profile, op))
    {
      BIVelocityWebProfile velocityProfile = (BIVelocityWebProfile)profile;

      // Create a Velocity Context (default implementation calls view's makeVelocityContext)...
      VelocityContext context = velocityProfile.makeVelocityContext(this, op);
      if (theme != null)
      {
        context.put("themeName", theme.getTypeName());
      }

      // Give the view a chance to update the context...
      initVelocityContext(context, op, velocityProfile);

      // If there's a profile then delegate writing the view to it...
      velocityProfile.write(this, context, op);
    }
    else
    {
      // Create a Velocity Context...
      VelocityContext context = makeVelocityContext(op, profile);
      if (theme != null)
      {
        context.put("themeName", theme.getTypeName());
      }

      // Give the view a chance to update the context...
      initVelocityContext(context, op, profile);

      // If there's no profile then just write out the view...
      op.getResponse().setStatus(HttpServletResponse.SC_OK);
      op.setContentType(getMimeType(op));

      op.getResponse().setHeader("Cache-Control", "no-cache, no-store");

      PrintWriter out = op.getWriter();
      Template template = getVelocityEngine().getTemplate(getTemplateFileOrd(op).toString());
      template.merge(context, out);
      out.flush();
    }
  }
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Return the ORD to the template file for this view.
   * <p>
   * Normally the ORD returned is to a file embedded in a Niagara module.
   * For example...
   * <pre>
   *   module://myModule/res/myFile.vm
   * </pre>
   * 
   * @param op  the WebOp for the current request.
   * @return the template ORD
   */
  public abstract BOrd getTemplateFileOrd(WebOp op);
  
  /**
   * Make the VelocityContext.
   * 
   * @param op  the WebOp for the current request.
   * @param profile  the Web Profile for the current request.
   * 
   * @return a new Velocity Context with default elements attached.
   */
  public VelocityContext makeVelocityContext(WebOp op, BIWebProfile profile)
      throws Exception
  {
    VelocityContext context = makeDefaultVelocityContext(op, profile);
    context.put("csrfToken", getCsrfToken());
    Map<String, Object> map = (Map)context.get("ax");
    map.put("viewTemplate", getTemplateFileOrd(op).toString());
    return context;
  }
    
  /**
   * Initialize the VelocityContext that will be provided to the template generator.
   * 
   * @param context the VelocityContext to add too.
   * @param op the WebOp for the current request.
   * @param profile  the Web Profile for the current request.
   */
  protected void initVelocityContext(VelocityContext context, WebOp op, BIWebProfile profile)
      throws Exception
  {
  }
      
  /**
   * Return true if the profile should be allowed to write part of the page.
   * <p>
   * If false is returned, the view will write the entire page. If the view needs to 
   * enforce a special Web Profile then an InvalidProfileException should be thrown.
   * <p>
   * Please note, if true is returned the view must implement the BIVelocityWebProfile interface.
   *
   * @param profile the web profile.
   * @param op  the WebOp for the current request.
   *
   * @return If true, the profile is allowed to
   *  write the page content, delegating the main content
   *  to the view.  If false, the view is responsible
   *  for the entire page content.  Returns true by default.
   *  
   * @see InvalidProfileException
   * @see BIVelocityWebProfile
   *  
   * @throws InvalidProfileException
   */
  protected boolean useProfile(BIWebProfile profile, WebOp op) 
      throws InvalidProfileException
  {
    return profile instanceof BIVelocityWebProfile;
  }
  
  /**
   * Return the MIME Type for the Velocity View.
   * 
   * @param op
   * @return mime type
   */
  public String getMimeType(WebOp op)
  {
    return "text/html"; 
  }
  
////////////////////////////////////////////////////////////////
// Context
////////////////////////////////////////////////////////////////
  
  /**
   * Make the VelocityContext.
   * 
   * @param op  the WebOp for the current request.
   * @param profile  the Web Profile for the current request.
   * 
   * @return a new Velocity Context with default elements attached.
   */
  public static VelocityContext makeDefaultVelocityContext(WebOp op, BIWebProfile profile)
  {
    VelocityContext context = makeDefaultVelocityContext(op.get(), op);
    Map<String, Object> ax = (Map<String, Object>)context.get("ax");
    
    // $ax namespace...
    ax.put("session", op.getRequest().getSession(true));
    ax.put("target", op);
    ax.put("op", op);
    ax.put("profile", profile);
    
    return context;
  }
  
  /**
   * Make the VelocityContext.
   * 
   * @param obj the object used in the current request.
   * @param cx the Context being used in the current request.
   * 
   * @return a new Velocity Context with default elements attached.
   */
  public static VelocityContext makeDefaultVelocityContext(BObject obj, Context cx)
  {
    VelocityContext context = new VelocityContext();
    
    // TODO: More things deemed 'useful' need to be added here...
    
    // TODO: ord resolution? Nice helpful ORD and lease routines?
    // TODO: ord resolution to collection to VTL array? How could this be used to create a table
    // TODO: button Action invocation?
    // TODO: Useful constants... Flags, baja.Status, enums etc...
    // TODO: BajaScript stop and start
    
    Map<String, Object> ax = new HashMap<>();

    VelocityContextUtil util = new VelocityContextUtil(cx);
   
    // $ax namespace...
    ax.put("cx", cx);
    ax.put("obj", obj);
    ax.put("Flags", Flags.class);
    ax.put("lang", cx.getLanguage());
    ax.put("user", cx.getUser());
    
    // $ax.util
    ax.put("util", util);
        
    // $ax
    context.put("ax", ax);
        
    // $util (so useful we have this accessible from $util and $ax.util)
    context.put("util", util);
    
    return context;
  }
  
////////////////////////////////////////////////////////////////
// InvalidProfileException
////////////////////////////////////////////////////////////////
  
  /**
   * Exception thrown if an incorrect Web Profile is used with this view.
   */
  public static final class InvalidProfileException
     extends Exception
  {
    public InvalidProfileException(Type type)
    {
      super("Invalid Web Profile! Web Profile must be: " + type);
    }
    
    public InvalidProfileException(String error)
    {
      super(error);
    }
  }
  
////////////////////////////////////////////////////////////////
// Velocity Engine
////////////////////////////////////////////////////////////////
  
  /**
   * Return a VelocityEngine to use for a request.
   * 
   * @return VelocityEngine.
   */
  protected VelocityEngine getVelocityEngine()
  {
    return getVelocityEngineInstance();
  }
  
  /**
   * Return a new VelocityEngine instance.
   * 
   * @return VelocityEngine.
   */
  public static VelocityEngine makeVelocityEngine()
  {
    VelocityEngine velocity = new VelocityEngine();    
    Properties initProps = new Properties();
    
    initProps.setProperty("resource.loader", "niagara");
    initProps.setProperty("niagara.resource.loader.class", "com.tridium.velocity.ResourceLoader");
    initProps.setProperty("input.encoding", "UTF-8");
    
    String uberspect = 
        "org.apache.velocity.util.introspection.SecureUberspector";
    
    initProps.setProperty("runtime.introspector.uberspect", uberspect);
    
    /*
     * restricted packages and classes are c&p'ed from the default
     * velocity.properties file. adding to RuntimeInstance post-init seems to
     * be a no go.
     */
    initProps.setProperty("introspector.restrict.packages",
      "java.lang.reflect");
      
    initProps.setProperty("introspector.restrict.classes", 
      "java.lang.Class," +
      "java.lang.ClassLoader," +
      "java.lang.Compiler," +
      "java.lang.InheritableThreadLocal," +
      "java.lang.Package," +
      "java.lang.Process," +
      "java.lang.Runtime," +
      "java.lang.RuntimePermission," +
      "java.lang.SecurityManager," +
      "java.lang.System," +
      "java.lang.Thread," +
      "java.lang.ThreadGroup," +
      "java.lang.ThreadLocal," +
      
      //niagara-specific types
      "javax.baja.security.BPassword");
    
    // Turn off macro and file caching for web development mode...
    if (WebDev.get("velocity").isEnabled())
    {
      initProps.setProperty("velocimacro.library.autoreload", "true");
      initProps.setProperty("velocimacro.permissions.allow.inline.to.replace.global", "true");
      initProps.setProperty("velocimacro.permissions.allow.inline.local.scope", "false");
      initProps.setProperty("file.resource.loader.cache", "false");
    }
    
    velocity.init(initProps);
    
    return velocity;
  }
  
  /**
   * VelocityEngineLoader - uses 'Java Concurrency in Practice' lazy 
   * load thread safe Singleton design pattern... 
   */
  private static class VelocityEngineLoader
  {
    private static final VelocityEngine engine = makeVelocityEngine();
  }
  
  /**
   * Return an instance of a VelocityEngine.
   * 
   * @return VelocityEngine
   */
  public static VelocityEngine getVelocityEngineInstance()
  {
    // For now it's assumed that everything can share the same VelocityEngine instance. After all,
    // every view will be sharing the same resources as far as accessing the Station goes...
    return VelocityEngineLoader.engine;
  }

  /**
   * Get the CSRF token if one is available in the current session
   * @return
   */
  private String getCsrfToken()
  {
    NiagaraSuperSession session = SessionManager.getCurrentNiagaraSuperSession();
    return session != null ? session.getCsrfToken() : null;
  }
}
