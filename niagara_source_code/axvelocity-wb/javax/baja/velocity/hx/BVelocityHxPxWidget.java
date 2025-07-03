/*
 * Copyright 2012, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.velocity.hx;

import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import javax.baja.hx.BHxProfile;
import javax.baja.hx.HxOp;
import javax.baja.hx.px.BHxPxWidget;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.velocity.BVelocityView;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * Velocity Hx Px Widget 
 *
 * @see BVelocityHxView
 * @see BVelocityHxPxWidget
 *
 * @author		gjohnson
 * @creation 	Jan 23, 2012
 * @version 	1
 * @since 		Niagara 3.7
 */
@NiagaraType
public abstract class BVelocityHxPxWidget
    extends BHxPxWidget
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.velocity.hx.BVelocityHxPxWidget(2979906276)1.0$ @*/
/* Generated Thu Nov 18 10:19:07 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BVelocityHxPxWidget.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  
////////////////////////////////////////////////////////////////
// Hx
////////////////////////////////////////////////////////////////

  public void write(HxOp op) throws Exception
  {
    BHxProfile profile = op.getProfile();

    // Create a Velocity Context...
    VelocityContext context = makeVelocityContext(op, profile);

    // Give the view a chance to update the context...
    initVelocityContext(context, op, profile);

    // If there's no profile then just write out the view...
    op.getResponse().setStatus(HttpServletResponse.SC_OK);
    op.setContentType("text/html");

    PrintWriter out = op.getWriter();
    Template template = BVelocityView.getVelocityEngineInstance().getTemplate(getTemplateFileOrd(op).toString());
    template.merge(context, out);
    out.flush();
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
   * @param op  the HxOp for the current request.
   * @return the template ORD
   */
  public abstract BOrd getTemplateFileOrd(HxOp op);

  /**
   * Make the VelocityContext.
   *
   * @param op  the HxOp for the current request.
   * @param profile  the Hx Profile for the current request.
   * 
   * @return a new Velocity Context with default elements attached.
   */
  @SuppressWarnings("unchecked") //because Map has Object value
  protected VelocityContext makeVelocityContext(HxOp op, BHxProfile profile)
      throws Exception
  {
    
    VelocityContext context = BVelocityHxView.makeDefaultVelocityContext(op, profile);
    Map<String, Object> map = (Map<String, Object>)context.get("ax");
    map.put("viewTemplate", getTemplateFileOrd(op).toString());
    return context;
  }

  /**
   * Initialize the VelocityContext that will be provided to the template generator.
   * 
   * @param context  the VelocityContext to add too.
   * @param op  the HxOp for the current request.
   * @param profile  the Hx Profile for the current request.
   */
  protected void initVelocityContext(VelocityContext context, HxOp op, BHxProfile profile)
      throws Exception
  {
  }
}
