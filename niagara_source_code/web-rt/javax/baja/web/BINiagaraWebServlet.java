/*
 * Copyright 2021 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import javax.servlet.http.HttpServlet;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BINiagaraWebServlet is a server side plugin into BWebService that processes HTTP requests to URIs
 * with a specified prefix. An Implementation will need to choose the concrete type
 * of servlet it uses. If you want to use an <code>javax.servlet.http.HttpServlet</code>
 * try using <code>BWebServlet</code>
 *
 * @author Max Redmond on 04 Feb 2021
 * @since Niagara 4.11
 */
@NiagaraType
public interface BINiagaraWebServlet extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BINiagaraWebServlet(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:43 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BINiagaraWebServlet.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   *
   * @return the name that will be registered with the web server
   */
  String getServletName();

  /**
   *
   * @return the <code>HttpServlet</code> to be registered with the web server
   */
  HttpServlet getHttpServlet();

  /**
   * a callback that allows the servlet to report errors or successes in
   * registering the servlet
   * @param valid is the servlet name able to be registered
   */
  void setValidServletName(boolean valid);
}
