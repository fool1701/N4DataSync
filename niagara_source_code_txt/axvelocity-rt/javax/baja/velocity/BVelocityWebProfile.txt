/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.velocity;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

import javax.baja.agent.AgentInfo;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTypeSpec;
import javax.baja.web.WebOp;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * A Web Profile for Velocity
 * 
 * @author    John Sublett
 * @creation  14 Jun 2011
 * @version   $Revision$ $Date$
 * @since     Niagara 3.7
 */
@NiagaraType
public abstract class BVelocityWebProfile
    extends BObject
    implements BIVelocityWebProfile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.velocity.BVelocityWebProfile(2979906276)1.0$ @*/
/* Generated Thu Nov 18 10:14:51 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BVelocityWebProfile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// WebProfile
////////////////////////////////////////////////////////////////
        
  /**
   * By default, a VelocityWebProfile does not filter by application.
   * This method returns null.
   */
  public String[] getAppNames()
  {
    return emptyStringArray;
  }

  /**
   * A BVelocityWebProfile can only contain non-hx servlet views.
   */
  public boolean hasView(BObject target, AgentInfo agentInfo)
  {
    // TODO: Since we eventually want Hx and this type of view to work together seemlessly,
    // this will change.
    return agentInfo.getAgentType().is(servletViewType) &&
           !agentInfo.getAgentType().is(hxViewType);
  }
  
////////////////////////////////////////////////////////////////
// IVelocityWebProfile
////////////////////////////////////////////////////////////////

  /**
   * Return a new VelocityContext that will be used by the template generator.
   * <p>
   * By default, the profile asks the view to make the Profile.
   * 
   * @see BVelocityView#makeVelocityContext(WebOp, javax.baja.web.BIWebProfile)
   * 
   * @return VelocityContext
   */
  public VelocityContext makeVelocityContext(BVelocityView view, WebOp op)
      throws Exception
  {
    return view.makeVelocityContext(op, this);
  }
   
  /**
   * Write the view.
   * 
   * @param view  the view that needs to be written by the Profile.
   * @param context  the Velocity Context used by the template generator.
   * @param op  the current request's WebOp .
   */
  public void write(BVelocityView view, VelocityContext context, WebOp op)
      throws Exception
  {        
    // Write...
    HttpServletResponse res = op.getResponse();
    res.setStatus(HttpServletResponse.SC_OK);
    res.setCharacterEncoding("UTF-8");
    res.setHeader("Cache-Control", "no-cache, no-store");

    op.setContentType(view.getMimeType(op));

    PrintWriter out = op.getWriter();
    Template template = getVelocityEngine().getTemplate(getTemplateFileOrd(op).toString());
    template.merge(context, out);
    out.flush();
  }
  
////////////////////////////////////////////////////////////////
// Template
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
 
////////////////////////////////////////////////////////////////
// Velocity Engine
////////////////////////////////////////////////////////////////
  
  /**
   * Return the VelocityEngine used by the Profile
   * 
   * @return VelocityEngine.
   */
  protected VelocityEngine getVelocityEngine()
  {
    return BVelocityView.getVelocityEngineInstance();
  }
    
////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  protected static final TypeInfo servletViewType = BTypeSpec.make("web", "ServletView").getTypeInfo();
  protected static final TypeInfo hxViewType      = BTypeSpec.make("hx", "HxView").getTypeInfo();
  
  protected static final String[] emptyStringArray = new String[0];
}
