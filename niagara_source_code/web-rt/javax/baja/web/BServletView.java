/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.baja.agent.BIAgent;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BSingleton;
import javax.baja.sys.Clock;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BServletView is a special view on an BObject which 
 * services HTTP requests routed by the BWebService.
 *
 * @author    Brian Frank on 6 Dec 01
 * @version   $Revision: 9$ $Date: 3/28/05 11:31:46 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BServletView
  extends BSingleton
  implements BIAgent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BServletView(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:43 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BServletView.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  
  
////////////////////////////////////////////////////////////////
// HTTP
////////////////////////////////////////////////////////////////
  
  /**
   * This method is called on the servlet to process
   * a HTTP request.  The WebOp wraps up the Servlet 
   * request and response instances.  Servicing an HTTP 
   * request must be stateless in that the same instance is 
   * shared for all request commands.
   * <p>
   * The default implementation of the service()
   * method is to dispatch to one of doXXX() methods
   * based on the request method type.
   */
  public void service(WebOp c)
    throws Exception
  {
    // Benchmark and add to web.servlet log (fine)
    long start, end;
    start = Clock.millis();
    String method = TextUtil.toLowerCase(c.getRequest().getMethod());
    switch(method)
    {
      case "get":
        doGet(c);
        break;

      case "post":
        doPost(c);
        break;

      case "put":
        doPut(c);
        break;

      default:
        c.getResponse().sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, method);
    }
    end = Clock.millis();
    if (log.isLoggable(Level.FINE))
    {
      HttpServletRequest request = c.getRequest();
      StringJoiner joiner = new StringJoiner("");
      joiner.add("Served request for ")
            .add(request.getMethod())
            .add(" ")
            .add(request.getRequestURI())
            .add(request.getQueryString() != null ? request.getQueryString() : "")
            .add(" in ")
            .add("" + (end - start))
            .add("ms");
      log.fine(joiner.toString());
    }
    
  }
  
  /**
   * Service an HTTP "get" request.  The default 
   * implementation responds with an error.
   */
  public void doGet(WebOp c)
    throws Exception
  {
    c.getResponse().sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "get");
  }

  /**
   * Service a HTTP "post" request.  The default 
   * implementation responds with an error.
   */
  public void doPost(WebOp c)
    throws Exception
  {
   String protocol = c.getRequest().getProtocol();
    String msg = "Post not allowed";
    if (protocol.endsWith("1.1"))
     c.getResponse().sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
   else
     c.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
  }

  /**
   * Server an HTTP 'put' request. The default
   * implementation responds with an error.
   *
   * @param c The web operation.
   */
  public void doPut(WebOp c)
    throws Exception
  {
    String protocol = c.getRequest().getProtocol();
    String msg = "Put not allowed";
    if (protocol.endsWith("1.1"))
      c.getResponse().sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
    else
      c.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
  }
  
  private final Logger log = Logger.getLogger("web.servletView");
}
