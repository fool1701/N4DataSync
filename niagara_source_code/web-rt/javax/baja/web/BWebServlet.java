/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.baja.naming.BLocalHost;
import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.status.BStatus;
import javax.baja.sys.BAbstractService;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.schema.Fw;
import com.tridium.web.servlets.NWebOp;

/**
 * BWebServlet is a server side plugin into BWebService which
 * process HTTP requests to a URIs with a specified prefix.  It is 
 * the Baja wrapper for the <code>javax.servlet.http.HttpServlet</code> API.
 *
 * @author    Brian Frank       
 * @creation  6 Dec 01
 * @version   $Revision: 9$ $Date: 8/2/07 12:37:03 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The servlet name is used to register the servlet
 into the servers URI namespace.  For example set
 this property to "foo" to register the servlet
 to receive requests for anything starting with
 "/foo".
 */
@NiagaraProperty(
  name = "servletName",
  type = "String",
  defaultValue = ""
)
public abstract class BWebServlet
  extends BAbstractService
  implements BINiagaraWebServlet
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BWebServlet(305435241)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "servletName"

  /**
   * Slot for the {@code servletName} property.
   * The servlet name is used to register the servlet
   * into the servers URI namespace.  For example set
   * this property to "foo" to register the servlet
   * to receive requests for anything starting with
   * "/foo".
   * @see #getServletName
   * @see #setServletName
   */
  public static final Property servletName = newProperty(0, "", null);

  /**
   * Get the {@code servletName} property.
   * The servlet name is used to register the servlet
   * into the servers URI namespace.  For example set
   * this property to "foo" to register the servlet
   * to receive requests for anything starting with
   * "/foo".
   * @see #servletName
   */
  public String getServletName() { return getString(servletName); }

  /**
   * Set the {@code servletName} property.
   * The servlet name is used to register the servlet
   * into the servers URI namespace.  For example set
   * this property to "foo" to register the servlet
   * to receive requests for anything starting with
   * "/foo".
   * @see #servletName
   */
  public void setServletName(String v) { setString(servletName, v, null); }

  //endregion Property "servletName"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWebServlet.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Service
////////////////////////////////////////////////////////////////
  
  /**
   * Default implementation returns { web:WebServlet }. 
   */
  public Type[] getServiceTypes()
  {
    return new Type[] { TYPE };
  }                                 

////////////////////////////////////////////////////////////////
// Resolve
////////////////////////////////////////////////////////////////

  /**
   * Resolve to a target inside the scope of this servlet.
   * By default this returns the base OrdTarget from the op.
   */
  public OrdTarget resolve(WebOp op)
    throws Exception
  {
    return op.getBaseOrdTarget();
  }

////////////////////////////////////////////////////////////////
// HTTP
////////////////////////////////////////////////////////////////

  /**
   * This method is called on the servlet to process
   * a HTTP request.  The WebOp wraps up the Servlet 
   * request and response instances.  
   * <p>
   * The default implementation of the service()
   * method is to dispatch to one of doXXX() methods
   * based on the request method type.
   */
  public void service(WebOp op)
    throws Exception
  {
    String method = TextUtil.toLowerCase(op.getRequest().getMethod());
    if (method.equals("get"))
      doGet(op);
    else if (method.equals("post"))
      doPost(op);
    else if (method.equals("put"))
      doPut(op);
    else if (method.equals("delete"))
      doDelete(op);
    else
      op.getResponse().sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, method);
  }

  private void doService(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException
  {
    try
    {
      if (!getEnabled())
      {
        resp.sendError(HttpServletResponse.SC_GONE);
        return;
      }

      Context cx = (Context)req.getAttribute("niagara.context");
      if (!getPermissions(cx).hasOperatorRead())
      {
        resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        return;
      }

      OrdTarget target = getNavOrd().resolve(BLocalHost.INSTANCE, cx);
      BWebService service = (BWebService)Sys.getService(BWebService.TYPE);

      WebOp op =  new NWebOp(target, service, req, resp);
      op.fw(this);

      OrdTarget newTarget = resolve(op);

      // If we have a new target then recreate the WebOp.
      if (newTarget != target)
      {
        op =  new NWebOp(newTarget, service, req, resp);
        op.fw(this);
      }

      req.setAttribute("niagara.target", newTarget);
      req.setAttribute("niagara.op", op);
      service(op);
    }
    catch(Exception e)
    {
      throw new ServletException(e);
    }
  }
  
  /**
   * Service an HTTP "get" request.
   */
  public void doGet(WebOp c)
    throws Exception
  {
     c.getResponse().sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "get");
  }

  /**
   * Service an HTTP "delete" request.
   */
  public void doDelete(WebOp c)
    throws Exception
  {
     c.getResponse().sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "delete");
  }

  /**
   * Service a HTTP "post" request.  The default 
   * implementation responds with an error.
   */
  public void doPost(WebOp op)
    throws Exception
  {
    String protocol = op.getRequest().getProtocol();
    String msg = "Post not allowed";
    if (protocol.endsWith("1.1"))
      op.getResponse().sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
    else
      op.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
  }

  public void doPut(WebOp op)
    throws Exception
  {
    String protocol = op.getRequest().getProtocol();
    String msg = "Put not allowed";
    if (protocol.endsWith("1.1"))
      op.getResponse().sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
    else
      op.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
  }

////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////
  
  /**
   * Framework use only.
   */
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {  
    switch(x)
    {
      case Fw.STARTED: register(); break;
      case Fw.STOPPED: unregister(); break;
      case Fw.CHANGED:
        if (isRunning() && ((Property)a).getName().equals(servletName.getName()))
        {
          unregister();
          register();
        }
        break;
    }
    return super.fw(x, a, b, c, d);
  }
  
  /**
   * Register with the WebService.
   */
  private void register()
  {                
    try
    {
      BWebService service = (BWebService)Sys.getService(BWebService.TYPE);
      BWebServer server = service.getWebServer();

      if (server != null)
        server.register(this);
    }
    catch(Throwable ignore) {}
  }

  /**
   * Unregister with the WebService.
   */
  private void unregister()
  {
    try
    {
      BWebService service = (BWebService)Sys.getService(BWebService.TYPE);
      BWebServer server = service.getWebServer();

      if (server != null)
        server.unregister(this);
    }
    catch(Throwable ignore) {}
  }
  
  /**
   * Set/clear fault condition if invalid servlet name.
   */
  public final void setValidServletName(boolean valid)
  {                       
    setStatus(BStatus.make(getStatus(), BStatus.FAULT, !valid));
    updateStatus();
  }

////////////////////////////////////////////////////////////////
// Servlet
////////////////////////////////////////////////////////////////

  /**
   * The HTTP Servlet used to inject this BWebServlet instance into Niagara's
   * Web Server.
   */
  private final class Servlet extends HttpServlet
  {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
      doService(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
      doService(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
      doService(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
      doService(req, resp);
    }
  }

  /**
   * Return the HttpServlet associated with this BWebServlet instance.
   *
   * @return The HttpServlet instance.
   */
  public final HttpServlet getHttpServlet()
  {
    return servlet;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final Servlet servlet = new Servlet();
}
