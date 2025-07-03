# Web

## Overview
 The web module is used to provide HTTP connectivity to a station via the BWebService. The web module provides a
 layered set of abstractions for serving HTTP requests and building a web interface:
Servlet: a standard javax.servlet API provides the lowest level of web integration.
ServletView: is used to provide object views in a web interface.
Web Workbench: is technology which enables the standard workbench to be run in a browser.
Hx: is technology used to build web interfaces using only standards: HTML, JavaScript, and CSS.

## Servlet
 Niagara provides a standard javax.servlet API to service HTTP requests. The WebOp class is used to wrap
 HttpServletRequest and HttpServletResponse. WebOp implements Context to provide additional Niagara
 specific information. These APIs form the basis for the rest of the web framework.
 The BWebServlet component is used to install a basic servlet which may be installed into a station database. The
 servletName is used to define how the servlet is registered into the URI namespace. A servletName of "foo" would
 receive all requests to the host that started with "/foo". Servlets are automatically registered into the URI namespace on
 their component started() method and unregistered on stopped(). The service() or doGet()/doPost()
 methods are used to process HTTP requests.
 Note: The current javax.servlet implementation is based on version 2.4. The following interfaces and methods are
 not supported:
javax.servlet.Filter: Unsupported interface
javax.servlet.FilterChain: Unsupported interface
javax.servlet.FilterConfig: Unsupported interface
javax.servlet.RequestDispatcher: Unsupported interface
javax.servlet.ServletContext: Unsupported methods
getNamedDispatcher(String)
getRequestDispatcher(String)
getResource(String)
getResourceAsStream(String)
getResourcePaths(String)
getServlet(String)
getServlets()
getServletContextName(String)
javax.servlet.ServletRequest: Unsupported method
getRequestDispatcher(String)
javax.servlet.ServletRequestAttributeListener: Unsupported interface
javax.servlet.ServletRequestListener: Unsupported interface
javax.servlet.http.HttpSessionActivationListener: Unsupported interface
javax.servlet.http.HttpSessionAttributeListener: Unsupported interface
javax.servlet.http.HttpSessionListener: Unsupported interface

## ServletView
 The web framework follows an object oriented model similar to the workbench. The user navigates to objects within the
 station using ords. One or more web enabled views are used to view and edit each object.
Niagara Developer Guide
8/26/2015
98

============================================================
PAGE 101
============================================================

 When navigating objects using ords, Niagara must map ords into the URI namespace. This is done with the URI format
 of "/ord?ord".
 The BServletView class is used to build servlets that plug into the ord space using the "view:" scheme. For example if
 you wish to display an HTML table for every instance of component type "Foo", you could create a ServletView called
 "FooTable". Given an instance of "Foo", the URI to access that view might be "/ord?slot:/foo3|view:acme:FooTable". The
 WebOp passed to BServletView.service() contains the target object being viewed (note WebOp subclasses from
 OrdTarget).

## Web Workbench
 A nice feature of Niagara's web framework is the ability to run the entire workbench right in a browser. This web
 workbench technology allows almost any view (or plugin) to run transparently in both a desktop and browser
 environment. The following process illustrates how web workbench works:
1. User requests a workbench view for a specific object via its ord.
2. An HTML page is returned that fills the entire page with a small signed applet called wbapplet.
3. The wbapplet is hosted by the Java Plugin which must be preinstalled
 on the client's machine.
4. The wbapplet loads modules from the station as needed, and caches them on the browser's local drive.
5. The workbench opens a fox connection under the covers for workbench to station communication.
6. The workbench displays itself inside the wbapplet using the respective WbProfile and WbView.
 Web workbench technology allows a sophisticated UI to be downloaded to a user's browser straight out of a Jace. It is
 downloaded the first time and cached - subsequent access requires only the download of wbapplet (13kb). Development
 for web versus desktop workbench is completely transparent. The only difference is that the BWbProfile used for a web
 interface must subclass from BWbWebProfile. Some functionality is limited only to the desktop like the ability to
 access the console and Jikes compiler. Also note that web workbench session is limited to a specific station. So it doesn't
 make sense to navigate to ords outside that station such a "local:|file:".
 Note: in order for web workbench to be used, the client browser machine must have access to the station's fox port. This
 may require the fox port to be opened up in the firewall.

## Hx
 There are cases where using the workbench is overkill or we don't wish to require the Java Plugin. For these use cases,
 Niagara provides the hx technology. Hx is a mini-framework used to build real-time web interfaces only with standard
 HTML, JavaScript, and CSS. See the hx chapter for details.

## WebProfileConfig
 The web experience of a given user is controlled via the BWebProfileConfig class. WebProfileConfig is a MixIn
 added to every User component. The web profile determines whether web workbench or hx is used by specifying an
 WbProfile or HxProfile for the user.
Niagara Developer Guide
8/26/2015
99
