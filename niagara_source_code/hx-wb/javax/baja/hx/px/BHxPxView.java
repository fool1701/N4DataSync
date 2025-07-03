/*
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx.px;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;

import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentList;
import javax.baja.agent.BAbstractPxView;
import javax.baja.gx.IRectGeom;
import javax.baja.gx.RectGeom;
import javax.baja.hx.BHxView;
import javax.baja.hx.HxOp;
import javax.baja.io.HtmlWriter;
import javax.baja.naming.BOrd;
import javax.baja.naming.UnresolvedException;
import javax.baja.net.Http;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BComponentSpace;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;
import javax.baja.util.LexiconModule;
import javax.baja.web.CsrfUtil;
import javax.baja.web.WebOp;
import javax.baja.workbench.CannotSaveException;

import com.tridium.agent.BILoadablePxView;
import com.tridium.hx.px.BHxHtmlPxView;
import com.tridium.hx.px.HxPxCache;
import com.tridium.hx.stub.StubHxOp;
import com.tridium.hx.util.BenchmarkCommand;
import com.tridium.hx.warmup.BHxPxWarmup;
import com.tridium.nre.diagnostics.DiagnosticUtil;
import com.tridium.web.WebProcessException;


/**
 * BHxPxView.
 *
 * @author    Andy Frank on 24 Oct 07
 * @version   $Revision: 14$ $Date: 8/10/10 10:08:12 AM EDT$
 * @since     Niagara 3.3
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "baja:AbstractPxView", "file:PxFile" },
    requiredPermissions = "r"
  )
)
@NiagaraSingleton
public class BHxPxView
  extends BHxView
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hx.px.BHxPxView(1498244292)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxPxView INSTANCE = new BHxPxView();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxPxView.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  public BHxPxView() {}

  /**
   * This constructor is used via reflection from baja:PxUtil.
   */
  public BHxPxView(BAbstractPxView pxView)
  {
    this.pxView = pxView;
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Override hook for HxView.write()
   */
  protected void doWrite(BWidget root, HxOp op)
    throws Exception
  {
  }

  /**
   * Override hook for HxView.update()
   */
  protected void doUpdate(BWidget root, HxOp op)
    throws Exception
  {
  }

  /**
   * Override hook for HxView.process()
   */
  protected boolean doProcess(BWidget root, HxOp op)
    throws Exception
  {
    return false;
  }

  /**
   * Override hook for HxView.save()
   */
  protected BObject doSave(BWidget root, HxOp op)
    throws Exception
  {
    return null;
  }

  /**
   * Overridden since Niagara 4.3 to check for the async px loading use case.
   */
  @Override
  public void doPost(WebOp c)
    throws Exception
  {
    if (c.getRequest().getHeader(HX_PX_LOADING) != null)
    {
      try
      { // CSRF token check
        CsrfUtil.verifyCsrfToken(c.getRequest());
      }
      catch(Exception e)
      {
        c.getResponse().sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
      }

      boolean responseSent = false;

      if (pxView instanceof BILoadablePxView)
      {
        BILoadablePxView loadablePxView = (BILoadablePxView)pxView;

        // If the content is still loading or has loaded but hasn't successfully loaded
        // then just show the loading dialog.

        if (loadablePxView.isPxViewLoading() ||
            loadablePxView.isPxViewModified(BAbsTime.END_OF_TIME))
        {
          // Tells the client to keep the "Loading" dialog open for a bit longer.
          responseSent = true;
          c.getResponse().setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
      }

      if (!responseSent)
      {
        // Tells the client that the content is now ready, so a reload will pick it up.
        c.getResponse().setStatus(HttpServletResponse.SC_OK);
      }
    }
    else
    {
      super.doPost(c);
    }
  }

////////////////////////////////////////////////////////////////
// HxView
////////////////////////////////////////////////////////////////

  public final void write(HxOp op)
    throws Exception
  {
    long millis = DIAGNOSTICS_log.isLoggable(Level.FINE)? DiagnosticUtil.nanoTime() : 0;

    if(!(op instanceof StubHxOp))
    {
      BHxPxWarmup.webActivity();
    }

    if (pxView instanceof BILoadablePxView)
    {
      BILoadablePxView loadablePx = (BILoadablePxView)pxView;
      if (!loadablePx.isPxViewLoading() && loadablePx.isPxViewModified(BAbsTime.END_OF_TIME))
        loadablePx.reloadPxView();

      if (loadablePx.isPxViewLoading())
      {
        op.addJavaScript(BOrd.make("module://hx/com/tridium/hx/px/hxPx.js"));
        op.addOnload("px.showLoadingUntilPostOk(\"" + HX_PX_LOADING + "\", \"true\", 2000);");
        return;
      }
    }

    op.setDynamic();
    BComponentSpace space;

    try
    {
      space = HxPxCache.get(op, pxView, true, op);
    } catch (UnresolvedException ue)
    {
      throw new WebProcessException(Http.SC_NOT_FOUND, ue.getMessage());
    }
    BWidget root = (BWidget)space.getRootComponent();

    // These are used by hxpx for sending mouse events, by placing them here we
    // avoid problems when trying to create them dynamically but before the <body>
    // has completely rendered.  This avoids an error message when trying to navigate
    // quickly on a slow machine running IE.
    {
      HtmlWriter out = op.getHtmlWriter();
      out.w("<input type='hidden' name='").w(op.scope("y")).w("'/>");
      out.w("<input type='hidden' name='").w(op.scope("x")).w("'/>");
      out.w("<input type='hidden' name='").w(op.scope("button")).w("'/>");
      out.w("<input type='hidden' name='").w(op.scope("shiftModifier")).w("'/>");
      out.w("<input type='hidden' name='").w(op.scope("ctlModifier")).w("'/>");
      out.w("<input type='hidden' name='").w(op.scope("altModifier")).w("'/>");
      out.w("<input type='hidden' name='").w(op.scope("metaModifier")).w("'/>");
    }
    
    StringBuilder out = new StringBuilder();
    out.append("window.hxPxInit = function() {");
    out.append("var hxPx = document.getElementById(\"").append(op.scope("hxPx")).append("\");");
    out.append("if(hxPx!=null) {");
    out.append("hx.addFormElementToPoll(\"").append(op.scope("content.top")).append("\");");
    out.append("hx.addFormElementToPoll(\"").append(op.scope("content.left")).append("\");");
    out.append("hx.addFormElementToPoll(\"").append(op.scope("content.width")).append("\");");
    out.append("hx.addFormElementToPoll(\"").append(op.scope("content.height")).append("\");");
    out.append("hxPxSizing();");    
    
    if(op.getRequest().getSession().getAttribute("contentGeom")==null || !BHxHtmlPxView.USE_SESSION_GEOM)
    {
      out.append("hx.ensureFirstPoll(").append(BenchmarkCommand.isActive()).append(");");
    }

    out.append("}");  
    out.append("};");

    out.append("window.hxPxSizing = function() {");
    out.append("var hxPx = document.getElementById(\"").append(op.scope("hxPx")).append("\");");
    out.append("if(hxPx){");
    out.append("hx.setFormValue(\"").append(op.scope("content.top")).append("\", ").append("hx.getElementBounds(hxPx)[1]);");
    out.append("hx.setFormValue(\"").append(op.scope("content.left")).append("\", ").append("hx.getElementBounds(hxPx)[0]);");
    {
      out.append("if(hxPx.parentNode.childNodes.length==2){");
      out.append("hx.setFormValue(\"").append(op.scope("content.width")).append("\", ").append("hxPx.parentNode.offsetWidth);");
      out.append("hx.setFormValue(\"").append(op.scope("content.height")).append("\", ").append("hxPx.parentNode.offsetHeight);");
      out.append("}");
    }
    out.append("setTimeout(hxPxSizing, 2500);");
    out.append("}};");
    
    op.addGlobal(out.toString());

    // delegate to impl
    getImpl(op).doWrite(root, op);
    
    op.addOnload("hxPxInit();");      

    if(DIAGNOSTICS_log.isLoggable(Level.FINE)) DiagnosticUtil.complete(millis, "BHxPxView.write");
  }

  public final void update(HxOp op)
    throws Exception
  {
    long millis = DIAGNOSTICS_log.isLoggable(Level.FINE)? DiagnosticUtil.nanoTime() : 0;

    BComponentSpace space = HxPxCache.get(op, pxView, true, op);
    BWidget root = (BWidget)space.getRootComponent();

    // delegate to impl
    getImpl(op).doUpdate(root, op);

    if(DIAGNOSTICS_log.isLoggable(Level.FINE)) DiagnosticUtil.complete(millis, "BHxPxView.update");
  }

  public final boolean process(HxOp op)
    throws Exception
  {
    if (super.process(op))
      return true;

    BComponentSpace space = HxPxCache.get(op, pxView, false, op);
    BWidget root = (BWidget)space.getRootComponent();
    // delegate to impl
    return getImpl(op).doProcess(root, op);
  }

  public final BObject save(HxOp op)
    throws Exception
  {
    BComponentSpace space = HxPxCache.get(op, pxView, false, op);
    BWidget root = (BWidget)space.getRootComponent();
    try
    {
      // delegate to impl
      return getImpl(op).doSave(root, op);
    }
    catch (Exception e)
    {
      String msg = LEX.getText("plugin.save.error", op);
      throw new CannotSaveException(msg, e);
    }
  }

////////////////////////////////////////////////////////////////
// Profile
////////////////////////////////////////////////////////////////

  /**
   * Get the HxPxView implementation for this user.
   */
  private BHxPxView getImpl(HxOp op)
  {
    AgentList agents = op.getProfile().getAgents(op).filter(filter);
    return (BHxPxView)agents.getDefault().getInstance();
  }

  private static AgentFilter filter = AgentFilter.is("hx:HxPxView");

////////////////////////////////////////////////////////////////
//Util
////////////////////////////////////////////////////////////////  
  
  public IRectGeom getContentGeom(HxOp op)
  {
    IRectGeom rect;

    if(BHxHtmlPxView.USE_SESSION_GEOM){
      rect = (IRectGeom) op.getRequest().getSession().getAttribute("contentGeom");
    }else{
      rect = (IRectGeom) op.getRequest().getAttribute("contentGeom");
  }

    if(op.getRequest().getHeader("Screen-Width")!=null)
    {
      // load view information sent with update
      int width = (int)  Double.parseDouble(op.getRequest().getHeader("Screen-Width"));
      int height = (int) Double.parseDouble(op.getRequest().getHeader("Screen-Height"));
    
      String left_text = op.getFormValue("content.left");
      String top_text = op.getFormValue("content.top");
      String width_text = op.getFormValue("content.width");
      String height_text = op.getFormValue("content.height");

      if(left_text!=null && top_text!=null)
      {
        int left = (int) Double.parseDouble(left_text);
        int top = (int) Double.parseDouble(top_text);

        rect= new RectGeom(left, top, width-left, height-top);

        if(width_text!=null && height_text!=null)
        {
          width = (int) Double.parseDouble(width_text);
          height = (int) Double.parseDouble(height_text);
          if(width>0 && height>0)
            rect= new RectGeom(left, top, width, height);
        }
      }
    }

    if(BHxHtmlPxView.USE_SESSION_GEOM){
      op.getRequest().getSession().setAttribute("contentGeom", rect);
    }else
      op.getRequest().setAttribute("contentGeom", rect);
    return rect;
  }  
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final Logger log = Logger.getLogger("hx.px");

  public static final Logger DIAGNOSTICS_log = Logger.getLogger("diagnostics.hx.px");

  /**
   * This header indicates the asynchronous loading of certain px views,
   * such as BNiagaraVirtualPxViews.
   *
   * @since Niagara 4.3
   */
  private static final String HX_PX_LOADING = "x-niagara-hx-px-loading";

  private BAbstractPxView pxView;
  private static LexiconModule LEX = LexiconModule.make("bajaui");
}
