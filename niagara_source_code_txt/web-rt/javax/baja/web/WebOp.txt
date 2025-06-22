/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import javax.baja.file.ExportOp;
import javax.baja.io.HtmlWriter;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.web.hx.BIHxProfile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tridium.web.WebEnv;
import com.tridium.web.WebSnoopHtmlWriter;
import com.tridium.web.WebUtil;

/**
 * WebOp is the argument passed to the BWebServlet
 * service() method.  It wraps up the Servlet request
 * and response instances as well adding additional 
 * contextual information.
 *
 * @author    Brian Frank
 * @creation  27 Oct 00
 * @version   $Revision: 33$ $Date: 1/3/11 2:40:42 PM EST$
 * @since     Baja 1.0
 */
public abstract class WebOp
  extends ExportOp
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor
   */
  public WebOp(OrdTarget base, BWebService service,              
               HttpServletRequest request, 
               HttpServletResponse response)
  {
    super(base);
    this.service = service;
    this.request = request;
    this.response = response;
  }                      

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the BWebService which is serving this request.
   */
  public BWebService getService()
  {
    return service;
  }

  /**
   * Get the WebEnv for this request.
   */
  public IWebEnv getWebEnv()
  {
    if (webEnv == null)
      webEnv = initWebEnv();
    return webEnv;
  }

  /**
   * Get the WebEnv for this request.
   */
  public IWebEnv initWebEnv()
  {
    HttpSession session = getRequest().getSession(true);
    webEnv = (IWebEnv)session.getAttribute("webenv");
    if (webEnv == null)
    {
      webEnv = WebEnv.make(this);
      session.setAttribute("webenv", webEnv);
    }
    
    return webEnv;
  }

  /**
   * Get the BWebProfileConfig to use for this request.
   */
  public BWebProfileConfig getProfileConfig()
  {
    return getProfileConfig(null);
  }

  /**
   * Get the BWebProfileConfig to use for this request. Use the BIHxProfile.PREFER_Hx=true
   * facet to get the getHxProfileConfig().
   * @since Niagara 4.6
   */
  public BWebProfileConfig getProfileConfig(Context cx)
  {
    if (profileConfig == null)
    {
      profileConfig =
        (BWebProfileConfig)getRequest().getSession(true).getAttribute("profileConfig");
      
      if (profileConfig == null)
      {
        try
        {                                    
          // let the WebEnv get the correct profile config from the user
          profileConfig = getWebEnv().getWebProfileConfig(getUser());
        }
        catch(Exception e)
        {
          BWebService.log.log(Level.SEVERE, "Cannot obtain profileConfig", e);
        }
      }   
      
      // just create a dummy one
      if (profileConfig == null)
        profileConfig = getWebEnv().makeWebProfileConfig();
    }

    if(cx != null && cx.getFacets() != null && cx.getFacets().getb(BIHxProfile.PREFER_HX, false))
    {
      if (hxProfileConfig == null)
      {
        hxProfileConfig =
          (BWebProfileConfig) getRequest().getSession(true).getAttribute("hxProfileConfig");
      }

      if (hxProfileConfig == null)
      {
        try
        {
          // let the WebEnv get the correct profile config from the user
          hxProfileConfig = getWebEnv().getHxProfileConfig(profileConfig);
          getRequest().getSession().setAttribute("hxProfileConfig", hxProfileConfig );
        }
        catch(Exception e)
        {
          BWebService.log.log(Level.SEVERE, "Cannot obtain hxProfileConfig", e);
        }
      }

      return hxProfileConfig;
    }

    return profileConfig;
  }

  /**
   * This returns the remainder of the uri after the servlet name.
   * op.getRequest().getPathInfo() does not return the desired
   * result because the BWebServlet is not a javax.servlet.Servlet
   * and is registered directly with the web server.
   *
   * Ex. servlet name = foo, /foo/a/b/c -&gt; /a/b/c
   */
  public String getPathInfo()
  {
    if (servlet != null)
    {
      String servletName = servlet.getServletName();
      return request.getRequestURI().substring(servletName.length() + 1);
    }
    else
      return request.getPathInfo();
  }

  /**
   * Get the underlying HttpServletRequest instance.
   */
  public HttpServletRequest getRequest()
  {
    return request;
  }
  
  /**
   * Get the underlying HttpServletResponse instance.
   */
  public HttpServletResponse getResponse()
  {
    return response;
  }

  /**
   * Convenience for <code>getResponse().setContentType(mimeType)</code>.
   */
  public void setContentType(String mimeType)
  {
    getResponse().setContentType(mimeType);
  }

  /**
   * Convenience for <code>getResponse().setContentLength(length)</code>.
   */
  public void setContentLength(int length)
  {
    getResponse().setContentLength(length);
  }

  /**
   * Convenience for <code>getResponse().getWriter()</code>.
   */
  public PrintWriter getWriter()
    throws IOException
  {
    return getResponse().getWriter();
  }  
  
  /**
   * Get an HtmlWriter for generating a text/html response 
   * stream.  This writer has the special function to check
   * for src and href attributes and replace them with the
   * browser's correct ord to URI mapping.
   */
  public HtmlWriter getHtmlWriter()
    throws IOException
  {
    return new WebSnoopHtmlWriter(this, getWriter());
  }
  
  /**
   * Get the User-Agent HTTP header as a parsed instance of 
   * UserAgent or return null if the user-agent header is unavailable
   * or illegally formatted.
   */  
  public UserAgent getUserAgent()
  {
    if (userAgent == null)
    {                   
      String header = request.getHeader("User-Agent");
      if (header == null) return null;
      try
      {
        userAgent = new UserAgent(header);
      }
      catch(Exception e)
      {
        BWebService.log.log(Level.SEVERE, "Invalid User-Agent: \"" + header + '"', e);
      }
    }
    return userAgent;
  }              

  /**
   * Get the response output stream.
   */
  public OutputStream getOutputStream()
  {
    try
    {
      return getResponse().getOutputStream();
    }
    catch(IOException e)
    {
      throw new BajaRuntimeException(e);
    }
  }        

  /**
   * Convert the specified ord into a server relative URI 
   * in this server's URI namespace.
   */
  public String toUri(BOrd ord)
  {          
    return WebUtil.toUri(this, getRequest(), ord);
  }  

  /**
   * Language code (ISO 639) of the user.  There are three ways the context
   * chooses a language, they are (in descending priority):
   * <ul>
   * <li>The language property of the BUser.</li>
   * <li>The first Accept-Language header value sent by the browser.</li>
   * <li>The default language of the server - Sys.getLanguage().</li>
   * </ul>
   *
   * To configure the preferred language of a browser:
   * <br>
   * <ul>
   * <li>IE - Tools:Internet Options:General:Languages</li>
   * <li>Mozilla/Netscape - Edit:Preferences:Navigator:Languages</li>
   * </ul>
   * @return the language code in lower case (null is never returned).
   */
  public String getLanguage()
  {                
    if (lang == null) lang = getRequest().getLocale().toLanguageTag();
    return lang;
  }

  public void fw(Object o)
  {
    if (o instanceof BWebServlet) servlet = (BWebServlet)o;
  }
  
  /**
   * Redirect the current request back to the user's home page.
   */
  public void redirectToHome()
    throws Exception
  {
    BOrd homePage = webEnv.getHomePage(this);
    getResponse().sendRedirect(WebUtil.getRedirect(getRequest(),WebUtil.toUri(this, getRequest(), homePage))); 
  }
    
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BWebService         service;
  IWebEnv             webEnv;
  BWebProfileConfig   hxProfileConfig;
  BWebProfileConfig   profileConfig;
  HttpServletRequest  request;
  HttpServletResponse response;
  UserAgent           userAgent;  
  String              lang;
  BWebServlet         servlet;  
}

