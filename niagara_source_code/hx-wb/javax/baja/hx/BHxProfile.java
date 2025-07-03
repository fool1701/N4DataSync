/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx;

import static javax.baja.hx.HxUtil.escapeJsStringLiteral;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.agent.BAbstractPxView;
import javax.baja.bajaux.BBajauxJsBuild;
import javax.baja.control.trigger.BManualTriggerMode;
import javax.baja.file.BExporter;
import javax.baja.file.types.image.BIImageFile;
import javax.baja.file.types.text.BCsvFile;
import javax.baja.file.types.text.BIHtmlFile;
import javax.baja.file.types.text.BPxFile;
import javax.baja.io.HtmlWriter;
import javax.baja.naming.BLocalHost;
import javax.baja.naming.BOrd;
import javax.baja.nav.BNavFileNode;
import javax.baja.nav.NavFileDecoder;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.session.INiagaraSuperSession;
import javax.baja.session.SessionUtil;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BSingleton;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.InvalidEnumException;
import javax.baja.sys.Localizable;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.user.BUser;
import javax.baja.web.BIFormFactorMax;
import javax.baja.web.BIWebProfile;
import javax.baja.web.BServletView;
import javax.baja.web.BWebProfileConfig;
import javax.baja.web.BWebService;
import javax.baja.web.IWebEnv;
import javax.baja.web.WebDev;
import javax.baja.web.WebOp;
import javax.baja.web.hx.BIHxProfile;
import javax.baja.web.js.BIJavaScript;
import javax.baja.web.mobile.BIMobileWebProfile;
import javax.baja.web.mobile.BIMobileWebView;
import javax.baja.web.mobile.BMobileWebProfileConfig;
import javax.baja.xml.XWriter;

import com.tridium.fox.sys.BFoxSession;
import com.tridium.hx.BHxOrdTargetResolver;
import com.tridium.hx.BHxWebWidget;
import com.tridium.hx.ErrorDialog;
import com.tridium.hx.HxHyperlinkInfo;
import com.tridium.hx.px.BHxPxWbView;
import com.tridium.hx.util.BenchmarkCommand;
import com.tridium.hx.util.HxUtils;
import com.tridium.sys.registry.NAgentInfo;
import com.tridium.sys.registry.NTypeInfo;
import com.tridium.util.CustomThemeModuleManager;
import com.tridium.util.PxUtil;
import com.tridium.util.ThrowableUtil;
import com.tridium.ux.NiagaraEnv;
import com.tridium.web.IWebEnvProvider;
import com.tridium.web.RequireJsUtil;
import com.tridium.web.WebEnv;
import com.tridium.web.WebProcessException;
import com.tridium.web.WebUtil;
import com.tridium.web.filters.ViewFilter;
import com.tridium.web.servlets.WbServlet;
import com.tridium.web.session.NiagaraWebSession;
import com.tridium.web.session.WebSessionUtil;

/**
 * BHxProfile is a convenience implementation of BIWebProfile with hooks to customize content.
 *
 * @author Andy Frank
 * @creation 10 Jan 05
 * @version $Revision: 51$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since Baja 1.0
 */
@NiagaraType
public abstract class BHxProfile
  extends BSingleton
  implements BIMobileWebProfile, IWebEnvProvider, BIHxProfile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hx.BHxProfile(2979906276)1.0$ @*/
/* Generated Fri Nov 19 13:59:13 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxProfile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



  protected BHxProfile()
  {
    registerEvent(benchmark = new BenchmarkCommand());
  }

////////////////////////////////////////////////////////////////
// BTypeConfig.IConfigurable
////////////////////////////////////////////////////////////////

  /**
   * Return the string keys of the configurable properties.
   */
  @Override
  public String[] listConfig()
  {
    if (AUTO_REFRESH_ENABLED)
    {
      return new String[] { themeKey, autoRefreshKey};
    }
    else
    {
      return new String[] { themeKey };
    }
  }

  /**
   * Get a configurable property by String key.
   */
  @Override
  public BValue getConfig(String key)
  {
    switch (key)
    {
      case themeKey:
      {
        return BFoxSession.getDefaultThemeEnumForSession();
      }
      case autoRefreshKey:
      {
        return BManualTriggerMode.make();
      }
      default:
      {
        return null;
      }
    }
  }

  /**
   * Get the facets for a configurable property.
   */
  @Override
  public BFacets getConfigFacets(String key)
  {
    if (key.equals(themeKey))
    {
      return THEME_FACETS;
    }

    return BFacets.NULL;
  }

  /**
   * Set a configurable property by String key.
   */
  @Override
  public void setConfig(String key, BValue value)
  {
  }

////////////////////////////////////////////////////////////////
// HxWebProfile
////////////////////////////////////////////////////////////////

  /**
   * Get the page title.
   */
  public String getPageTitle(BHxView view, HxOp op)
    throws Exception
  {
    return view.getPageTitle(op);
  }

  /**
   * Write custom head code.
   */
  public void doHead(HxOp op)
    throws Exception
  {
  }

  /**
   * Write body. Make sure you only use the buffered content
   * methods to write output. Also note that you must pass this
   * HxOp to the given BHxView, not a child op.
   *
   * Since this method is responsible for drawing both the view content and the Profile Chrome, please ensure that the
   * Chrome around the view is removed if <code>HxProfile.isFullScreen(BHxView, HxOp)</code> returns true.
   */
  public void doBody(BHxView view, HxOp op)
    throws Exception
  {
    view.write(op);
    displayError(op);
  }

////////////////////////////////////////////////////////////////
// HxOp Lifecycle
////////////////////////////////////////////////////////////////

  /**
   * Create the base HxOp for an HxView transaction.
   */
  public HxOp createOp(WebOp c)
    throws Exception
  {
    HxOp op = new HxOp(c);
    op.getResponse().setCharacterEncoding(UTF_8);
    return op;
  }

  /**
   * Destroy the given HxOp. HxView calls this method at the
   * end of a transaction.
   */
  public void destroyOp(HxOp op)
    throws Exception
  {
    // TODO: this is a HACK that should be reverted after Andy
    // helps me with a better design
    op.deleteTempFiles();
  }

////////////////////////////////////////////////////////////////
// View
////////////////////////////////////////////////////////////////

/**
   * Return the application name for this profile. The appName
   * serves as a registry key to enable certain agents/views
   * that only make sense within a specific application.  See
   * AgentInfo.getAppName() for more details.  Default implementation
   * returns null which filters out all views with a non-null
   * appName.
   */
  public String getAppName()
  {
    return null;
  }

  /**
   * Return the application name(s) for this profile. The appName(s)
   * serve as registry keys to enable certain agents/views
   * that only make sense within a specific application.  See
   * AgentInfo.getAppName() for more details.  The default implementation
   * returns no app names if the getAppName is null. If the AppName is
   * non-null, the default implemenation is to return that single app name
   * in a one entry array.
   *
   * @since Niagara 3.4
   */
  public String[] getAppNames()
  {
    if(getAppName() != null)
      return new String[]{ getAppName()};
    else
      return new String[0];
  }


  /**
   * Return if this profile supports the specified view.
   */
  public boolean hasView(BObject target, AgentInfo agentInfo)
  {

    // issue 13602
    if(agentInfo.getAgentType().toString().equals("obixDriver:ObjectToObix"))
      return false;

    Boolean enabled = devViewsEnabled.get(agentInfo.getAgentId());
    if(enabled != null && !enabled.booleanValue())
    {
      return false;
    }

    return true;
  }

  /**
   * Return the default list of views for the profile.
   *
   * @since Niagara 4.0
   *
   * @param op The Hx Op.
   * @return The Agent List for the Profile.
   */
  public AgentList getViews(HxOp op)
  {
    AgentList list =  WebEnvHolder.INSTANCE.getDefaultViews(op);

    for (String devView : DEV_VIEWS) {
      if (!devViewsEnabled.get(devView)) {
        list.remove(devView);
      }
    }

    BObject obj = op.get();

    if (!(obj instanceof BIHtmlFile || obj instanceof BPxFile || obj instanceof BIImageFile || obj instanceof BCsvFile))
    {
      // Move the text editor above the file download view.
      int downloadIndex = list.indexOf("web:FileDownloadView");
      if (downloadIndex > -1)
      {
        int editorIndex = list.indexOf("webEditors:TextFileEditor");
        if (editorIndex > -1)
        {
          list.add(downloadIndex, "webEditors:TextFileEditor");
        }
      }
    }

    return list;
  }

  /**
   * This method should be used by the BHxProfile subclass to determine whether the Profile chrome should be
   * added around the HxView or not.
   * By default, this method returns true if the ViewQuery parameters includes fullScreen=true.
   *
   * @since Niagara 4.3
   */
  public boolean isFullScreen(BHxView view, HxOp op)
  {
    return  "true".equals(op.getViewParameter(fullScreenKey, "false"));
  }

////////////////////////////////////////////////////////////////
// Web Start
////////////////////////////////////////////////////////////////

  /**
   * Enables or disables the address bar at the top of the window when
   * this profile is used within the Niagara Web Start application.
   *
   * The address bar provides the back and forward buttons, the location
   * text field, and a refresh button.
   *
   * The return value of this method has no effect when the profile is
   * used in a browser, it is only used by the Niagara Web Start application.
   * The default behavior is for the address bar to be shown.
   *
   * @return true if the Web Start application's address bar should be shown for a user with this profile.
   * @since Niagara 3.8U1 / Niagara 4.2
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   */
  @Deprecated
  protected boolean hasWebStartAddressBar() { return true; }

  /**
   * Enables or disables the status bar at the bottom of the window when this profile
   * is used within the Niagara Web Start application.
   *
   * The return value of this method has no effect when the profile is
   * used in a browser, it is only used by the Niagara Web Start application.
   * The default behavior is for the status bar to be shown.
   *
   * @return true if the Web Start application's status bar should be shown for a user with this profile.
   * @since Niagara 3.8U1 / Niagara 4.2
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   */
  @Deprecated
  protected boolean hasWebStartStatusBar() { return true; }

  /**
   * Write custom meta tags for basic control of the Web Start window when
   * displaying an Hx view.
   */
  private void doWebStartMetaTags(PrintWriter out)
  {
    out.println("<meta name='niagara-webstart-has-address-bar' content='" + hasWebStartAddressBar() + "' />");
    out.println("<meta name='niagara-webstart-has-status-bar' content='" + hasWebStartStatusBar() + "' />");
  }

////////////////////////////////////////////////////////////////
// Document
////////////////////////////////////////////////////////////////
  /**
   * Write document. You must pass this HxOp to the
   * given BHxView, not a child op.
   */
  public void writeDocument(BHxView view, HxOp op)
    throws Exception
  {
    String themeName = setupTheme(op);

    op.getResponse().setCharacterEncoding("UTF-8"); //NCCB-11956
  
    INiagaraSuperSession superSession = SessionUtil.getCurrentNiagaraSuperSession();
    if (superSession != null)
    {
      //before body is written, provide csrf token
      HxUtil.writeFormValue("csrfToken", superSession.getCsrfToken(), op);
    }

    // Build view in content buffer
    doBody(view, op);

    // If a view has outputed raw content, bail out here
    if (op.isRaw())
    {
      return;
    }

    // If a view sent a manual error response, bail out here
    if (op.isErrorSent())
    {
      return;
    }

    // If redirect requested, send response, then bail
    String url = op.getRedirect();
    if (url != null)
    {
      op.getResponse().sendRedirect(url);
      return;
    }

    String[] htags   = op.getHeadTags();
    BOrd[] styles    = op.getStyleSheetOrds();
    BOrd[] scripts   = op.getJavaScriptOrds();
    String[] global  = op.getGlobal();

    op.setContentType("text/html");
    PrintWriter out = op.getResponse().getWriter();
    String title = "";
    String pageTitle = getPageTitle(view, op);
    if(pageTitle != null)
    {
      title = pageTitle;
    }
    out.println("<!DOCTYPE html>");
    out.println("<html xmlns='http://www.w3.org/1999/xhtml' lang='en' xml:lang='en' style='overflow:auto;'>");
    out.println("<head>");
    out.println("<title>" + XWriter.safeToString(title, true) + "</title>");

    out.print("<meta http-equiv=\"X-UA-Compatible\" content=\"");
    out.print(XWriter.safeToString(WbServlet.xuaCompatibleContent, false));
    out.println("\">");

    // Use this if served as HTML
    out.println("<meta http-equiv='Content-type' content='text/html;charset=UTF-8' />");

    // Misc tags
    for (int i=0; i<htags.length; i++)
    {
      out.println(htags[i]);
    }

    // Instructions for the Niagara Web Start application
    doWebStartMetaTags(out);

    // Favicon
    out.println("<link rel='shortcut icon' href='/favicon.ico' />");

    // An private attribute used to bind resources together.
    boolean bindResources = op.isBindResources();
    
    if (bindResources)
    {
      long lastBuildTime = Sys.getRegistry().getLastBuildTime().getMillis();
      String typeSpec = BHxOrdTargetResolver.TYPE.toString().replace(":", "%3A");
      RequireJsUtil requireJsUtil = RequireJsUtil.make(false, op);
      out.println("<script type='text/javascript'>");
      requireJsUtil.requirejsNoHtml(out);
      out.println("</script>");

      out.println(String.format("<link rel='stylesheet' type='text/css' href='/vfile/hx/app.css?typeSpec=%s&version=%d'/>",
        typeSpec,
        lastBuildTime));
  
      out.println(String.format("<script type='text/javascript' src='/vfile/hx/app.js?typeSpec=%s&version=%d'></script>",
        typeSpec,
        lastBuildTime));
  
      out.println(HxUtils.getInitSyncedSessionStorageScript());
      out.println("<script type='text/javascript'>");
      out.println(requireJsUtil.defineSystemProperties());
      out.println("</script>");
    }
    else
    {
      out.println("<link rel='stylesheet' type='text/css' href='" +
              WebUtil.toUri(op, op.getRequest(), coreThemeCss) + "'/>");

      out.println("<link rel='stylesheet' type='text/css' href='" +
        WebUtil.toUri(op, op.getRequest(), coreHxCss) + "'/>");

      //hx menu should use jquery context menu styling for consistency
      out.println("<link rel='stylesheet' type='text/css' href='" +
        WebUtil.toUri(op, op.getRequest(), contextMenuCss) + "'/>");

      out.println("<link rel='stylesheet' type='text/css' href='" +
        WebUtil.toUri(op, op.getRequest(), hxContainerCss) + "'/>");
    }
  
    if (!themeName.isEmpty())
    {
      BOrd ord = BOrd.make("module://theme" + themeName + "/hx/theme.css");
    
      try
      {
        // Make sure resource exists
        ord.resolve(BLocalHost.INSTANCE, op);
      
        // Write this into the output
        out.println("<link rel='stylesheet' type='text/css' href='" +
          WebUtil.toUri(op, op.getRequest(), ord) +
          "'/>");
      }
      catch (Throwable ignore) {}
    }

    for (int i=0; i<styles.length; i++)
    {
      String cssUrl = WebUtil.toUri(op, op.getRequest(), styles[i]);
      out.println("<link rel='stylesheet' type='text/css' href='"+ HxUtil.encodeURLForHref(cssUrl) + "'/>");
    }

    if (!bindResources)
    {
      if (!RequireJsUtil.USE_NATIVE_PROMISES)
      {
        out.println("<script type='text/javascript' src='" +
          WebUtil.toUri(op, op.getRequest(), getPromiseJS()) + "'></script>");
      }

      // NCCB-20546: require is available by default for all HxProfiles
      RequireJsUtil.make(/*useLocalWbRc*/false, op).requirejs(out);
      out.println(HxUtils.getInitSyncedSessionStorageScript());

      // jQuery
      if (op.isJQuery())
      {
        out.println("<script type='text/javascript' src='" + WebUtil.toUri(op, op.getRequest(), jQueryJs) + "'></script>");

        // Ensure '$' doesn't overwrite the Hx '$' function
        out.println("<script type='text/javascript'>jQuery.noConflict();</script>");
      }

      // External JavaScript includes
      out.println("<script type='text/javascript' src='" + WebUtil.toUri(op, op.getRequest(), coreHxJs) + "'></script>");
    }

    for (int i=0; i<scripts.length; i++)
    {
      String javascriptUrl = WebUtil.toUri(op, op.getRequest(), scripts[i]);
      out.println("<script type='text/javascript' src='"+javascriptUrl + "'></script>");
    }

    // Global JavaScript
    out.println("<script type='text/javascript'>");
    new NiagaraEnv(NiagaraEnv.EnvType.HX)
      .withProfile(op.getProfile().getType())
      .withWebOp(op)
      .toJavaScript(out);
    
    //Start the activity monitor
    out.println("hx.startActivityMonitor();");
    if (isFullScreen(view, op))
    {
      out.println("hx.setFullScreen(true);");
    }

    String agentId = ViewFilter.getViewId(op);
    String profileInfo = "{ viewId: '" + escapeJsStringLiteral(agentId) + "' }";
    out.print("hx.setProfileInfo(" +profileInfo + ");");
    
    if (global.length > 0)
    {
      for (int i=0; i<global.length; i++)
      {
        out.println(global[i]);
      }
    }

    if (BenchmarkCommand.isActive())
    {
      op.addOnload(getBenchmarkCommand().getInvokeCode(op));
    }


    out.print("function hxProfileOnload(){  require(['Promise'], function () { hx.started(" + op.isDynamic() + ", " + HxUtil.pollFreq + ");");
    String[] onload = op.getOnload();
    for (int i=0; i<onload.length; i++)
    {
      out.print(" " + HxUtil.unescapeJsForInvocation(onload[i]));
    }
    out.println("})}");

    String[] resize = op.getOnresize();
    if (resize.length > 0)
    {
      out.print("function hxProfileOnresize(){");

      for (int i = 0; i < resize.length; i++)
      {
        out.print(" " + HxUtil.unescapeJsForInvocation(resize[i]));
      }
      out.println("}");
    }

    String[] onunload = op.getOnunload();
    if (onunload.length > 0)
    {
      out.print("function hxProfileOnunload(){");

      for (int i = 0; i < onunload.length; i++)
      {
        out.print(" " + HxUtil.unescapeJsForInvocation(onunload[i]));
      }
      out.println("}");
    }

    out.print("try {");

    RequireJsUtil.withRequiredBuiltFiles(out, () -> {
      out.print("  require([ 'bajaux/commands/UndoManager' ], function (UndoManager) {");
      out.print("    UndoManager.$installGlobal()");
      out.print("      .catch(function (err) { console.error(err); });");
      out.print("  });");
    }, BBajauxJsBuild.INSTANCE);

    out.print("} catch (e) {");
    out.print("  console.error(e);");
    out.print("}");

    out.println("</script>");

    if (AUTO_REFRESH_ENABLED)
    {
      HxUtils.writeAutoRefresh(op);
    }

    doHead(op);
    out.println("</head>");
    out.print("<body ");

    if (!themeName.isEmpty())
    {
      out.print("class=\"" + themeName + "\" ");
    }

    out.print(" onload='hxProfileOnload();' ");

    if (resize.length > 0)
    {
      out.print("onresize='hxProfileOnresize();' ");
    }

    if (onunload.length > 0)
    {
      out.print("onunload='hxProfileOnunload();' ");
    }

    out.println(">");

    out.print("<form class='hx " + XWriter.safeToString(String.valueOf(getCssClassName(op)), false) + "' method='post' action='/ord?");
    XWriter.safe(out, String.valueOf(op.getOrd()), false);
    out.print("'");
    if (op.isMultiPartForm())
    {
      out.print(" enctype='multipart/form-data'");
    }
    
    out.println(">");
    out.print(op.getContent().toString());
    out.println("<div style='display:none'><input type='submit' id='hx_submit' onclick='hx.$formClicked=true; return hx.$allowFormSubmit;'/></div>");
    out.println("</form>");

    out.println("</body>");
    out.println("</html>");
  }

  /**
   * The result of this method will be added to the class attribute of the main
   * form element of the profile.
   * This defaults to the TypeName of the BHxProfile.
   * @since Niagara 4.12
   */
  public String getCssClassName(HxOp op)
  {
    return this.getType().getTypeSpec().getTypeName();
  }

  /**
   * @return promise library ord
   */
  private BOrd getPromiseJS()
  {
    if (WebDev.get("js").isEnabled())
      return BOrd.make("module://js/rc/bluebird/bluebird.js");
    else
      return BOrd.make("module://js/rc/bluebird/bluebird.min.js");
  }

  /**
   * Update document. You must pass this HxOp to the
   * given BHxView, not a child op.
   */
  public void updateDocument(BHxView view, HxOp op)
    throws Exception
  {
    view.update(op);
  }

  /**
   * Process a non-update post. Return true if some view
   * handled the message, else return false.  You must
   * pass this HxOp to the given BHxView, not a child op.
   */
  public boolean processDocument(BHxView view, HxOp op)
    throws Exception
  {
    return view.process(op);
  }

  /**
   * Save document. You must pass this HxOp to the given
   * BHxView, not a child op.
   */
  public void saveDocument(BHxView view, HxOp op)
    throws Exception
  {
    view.save(op);
  }
  
////////////////////////////////////////////////////////////////
// Error
////////////////////////////////////////////////////////////////

  /**
   * Set the error state for the transaction.  The error
   * state is stored on the session, so it exists beyond
   * the life of HxOp.  Therefore you must make sure
   * <code>BHxProfile.clearError()</code> is called after
   * the error is handled to clear the state.
   */
  public void setError(Throwable err, HxOp op)
  {
    String details = "";
    if (((BWebService)Sys.getService(BWebService.TYPE)).getShowStackTrace())
    {
      details = ThrowableUtil.dumpToString(err);
    }
    else
    {
      details = err.toString();
    }

    String error = null;
    if(err instanceof Localizable)
    {
      //top level localization
      error = ((Localizable)err).toString(op);
    }
    else
    {
      //display top level non-localization error, then any additional local (like BDialog.open)
      error = err.getMessage();

    if (error == null)
        error = err.getClass().getName();

      // if the inner details exception is localizable and non-null, then
      // add a line to the content to display the message
      Localizable localizable = ThrowableUtil.toLocalizable(err);
      if (localizable != null)
      {
        String localizableToString = localizable.toString(op);
        if(localizableToString != null)
          error = error + "\n" + localizableToString;
      }
    }

    op.getRequest().getSession().setAttribute("hx.error", error);
    op.getRequest().getSession().setAttribute("hx.error.name", err.getClass().getName().substring(err.getClass().getName().lastIndexOf('.')+1));
    op.getRequest().getSession().setAttribute("hx.error.details", details);
  }

  /**
   * Clear error state.
   */
  public void clearError(HxOp op)
  {
    op.getRequest().getSession().removeAttribute("hx.error");
    op.getRequest().getSession().removeAttribute("hx.error.name");
    op.getRequest().getSession().removeAttribute("hx.error.details");
  }

  /**
   * Display an error message if an error state is present.
   * Do nothing if no error state is set.
   */
  public boolean displayError(HxOp op)
    throws Exception
  {
    String error = (String) op.getRequest().getSession().getAttribute("hx.error");
    String details = (String) op.getRequest().getSession().getAttribute("hx.error.details");
    String name = (String) op.getRequest().getSession().getAttribute("hx.error.name");

    if (error != null)
    {
      HtmlWriter out = op.getHtmlWriter();
      out.write("<script>");
      Dialog dialog = new ErrorDialog(name, error, details);
      dialog.open(op);
      out.write("</script>");

      // Clear error state
      clearError(op);
      return true;
    }
    else
      return false;
  }

  private String setupTheme(HxOp op)
  {
    String themeName = "";
    BDynamicEnum userTheme = (BDynamicEnum) op.getProfileConfig().get(themeKey);
    if (userTheme != null)
    {
      themeName = userTheme.getTag();
    }

    BDynamicEnum themesEnum = CustomThemeModuleManager.getModuleEnumForTag(themeName);

    try
    {
      // Attempt to check if the user's theme is available
      themesEnum.getRange().get(themeName);
    }
    catch(InvalidEnumException e)
    {
      // Fallback to a default theme
      themeName = themesEnum.getTag();
      if (!themeWarningWasLogged)
      {
        Logger.getLogger("hx").warning(String.format("There was a problem setting profile theme and was switched to the fallback %s theme.", themeName));
        themeWarningWasLogged = true;
      }
    }

    NiagaraWebSession session = WebSessionUtil.getSession(op.getRequest());
    if (!themeName.isEmpty())
    {
      // Make sure the theme name is cached in the session so the correct
      // themed icons download.
      if (session.getAttribute("themeName") == null)
      {
        session.setAttribute("themeName", themeName);
      }
    }
    else
    {
      //Since other profiles like MobileProfile set the theme in the session, lets get it from the session as a backup
      String sessionTheme = session.getAttribute("themeName");
      if (sessionTheme != null && !sessionTheme.isEmpty())
      {
        themeName = sessionTheme;
      }
    }

    if (!themeName.isEmpty())
    {
      //continue to store themeName in non-shared session for continued public api access in HxViews.
      HttpSession httpSession = op.getRequest().getSession();
      if (httpSession.getAttribute("themeName") == null)
      {
        httpSession.setAttribute("themeName", themeName);
      }
    }
    return themeName;
  }

////////////////////////////////////////////////////////////////
// Events
////////////////////////////////////////////////////////////////

  /**
   * Register this event.  Events are registered by
   * the event id - which is used to route requests
   * from the browser to the correct event handler.
   */
  public void registerEvent(Event event)
  {
    event.setId("profileEvent" + eventCounter++);
    events.put(event.getId(), event);
  }

////////////////////////////////////////////////////////////////
// IWebEnvProvider
////////////////////////////////////////////////////////////////

  public static IWebEnv webEnv()
  {
    return WebEnvHolder.INSTANCE;
  }

  public final IWebEnv getWebEnv(WebOp op)
    throws WebProcessException
  {
    return webEnv();
  }

////////////////////////////////////////////////////////////////
// IWebEnv
////////////////////////////////////////////////////////////////

  private static class HxWebEnv extends WebEnv
  {
    private HxWebEnv()
    {
      filter = add(filter, pxView);
      filter = add(filter, exporter);
      filter = AgentFilter.or(filter, new HxFilter());
      filter = AgentFilter.and(filter, new NoMobileFilter());
    }

    /**
     * Get the full list of views supported by the environment
     * on the target object (not including security checks).
     */
    public AgentList getViews(WebOp op)
    {
      BIWebProfile profile = op.getWebEnv().getWebProfile(op);
      // If possible, delegate to the profile.
      if (profile != null && profile instanceof BHxProfile)
      {
        try
        {
          HxOp hxOp = op instanceof HxOp ? (HxOp)op : new HxOp(op, /*initializeDocument*/false);
          return ((BHxProfile) profile).getViews(hxOp);
        }
        catch(Exception e)
        {
          throw new BajaRuntimeException(e);
        }
      }
      else
        return getDefaultViews(op);
    }

    private AgentList getDefaultViews(WebOp op)
    {
      AgentList agentList = op.get().getAgents(op).filter(AgentFilter.or(filter, new HxWbFilter(getWebProfile(op))));
      agentList.toBottom(new HxHyperlinkInfo.DeprecatedFilter(op));
      return agentList;
    }

    public AgentInfo getDefaultView(WebOp op, AgentList views)
    {
      BObject obj = op.get();

      // if a file or spy, use configured default
      if (obj instanceof BPxFile)
        return views.filter(AgentFilter.is(hxView)).getDefault();

      // then try servlet, first px, wb or web view
      for(int i=0; i<views.size(); ++i)
      {
        AgentInfo agent = views.get(i);
        TypeInfo agentType = agent.getAgentType();
        if (agentType.is(servlet)) return agent;
        if (agentType.is(pxView)) return agent;
        if (agentType.is(wbView)) return agent;
        if (agentType.is(BIFormFactorMax.TYPE) && agentType.is(BIJavaScript.TYPE)) return agent;
      }

      // use default
      return super.getDefaultView(op, views);
    }

    public AgentInfo getView(AgentList allViews, String viewId)
    {
      if (viewId.equals("hx:HxActionView"))
      {
        TypeInfo actionInfo = Sys.getRegistry().getType("hx:HxActionView");
        return new NAgentInfo((NTypeInfo)actionInfo);
      }
      return super.getView(allViews, viewId);
    }

    public AgentInfo translate(WebOp op, AgentInfo viewInfo)
    {
      TypeInfo viewTypeInfo = viewInfo.getAgentType();

      if (viewInfo instanceof BAbstractPxView)
      {
        AgentList agentList =  Sys.getRegistry().getAgents(((BAbstractPxView) viewInfo).getType().getTypeInfo());
        agentList = agentList.filter(AgentFilter.is(hxView));
        TypeInfo typeInfo = agentList.getDefault().getAgentType();
        return new PxUtil.PxHx((BAbstractPxView)viewInfo, typeInfo);
      }
      if (viewTypeInfo.is(wbView))
      {
        AgentList hxViews = Sys.getRegistry().getAgents(viewTypeInfo);
        AgentList result = hxViews.filter(AgentFilter.and(AgentFilter.is(hxView), HxHyperlinkInfo.getViewsFilter(op)));
        return result.getDefault();
      }
      if (viewTypeInfo.is(BIFormFactorMax.TYPE) && viewTypeInfo.is(BIJavaScript.TYPE))
      {
        return BHxWebWidget.TYPE.getTypeInfo().getAgentInfo();
      }

      return viewInfo;
    }

    public BWebProfileConfig makeWebProfileConfig()
    {
      return new BWebProfileConfig();
    }

    public BWebProfileConfig getWebProfileConfig(BUser user)
    {
      return (BWebProfileConfig)user.getMixIn(BWebProfileConfig.TYPE);
    }

    public BIWebProfile getWebProfile(WebOp op)
    {
      BUser user = op.getUser();
      BWebProfileConfig profileConfig =
        (BWebProfileConfig)op.getRequest().getSession(true).getAttribute("profileConfig");
      if (profileConfig == null)
      {
        profileConfig = (BWebProfileConfig)user.getMixIn(BWebProfileConfig.TYPE);
        op.getRequest().getSession(true).setAttribute("profileConfig", profileConfig);
      }

      BIWebProfile profile = (BIWebProfile)profileConfig.make();
      return profile;
    }

    public BOrd getHomePage(WebOp op)
    {
      // Attempt to get the Web Profile Config from the WebOp's HttpSession.
      BWebProfileConfig profileConfig = op.getProfileConfig();

      // Attempt to use the mobile profile config.
      if (profileConfig != null && profileConfig instanceof BMobileWebProfileConfig)
      {
        try
        {
          BOrd navFile = ((BMobileWebProfileConfig)profileConfig).getMobileNavFile();
          if (!navFile.isNull())
          {
            BNavFileNode root = NavFileDecoder.load(navFile).getRootNode();
            return root.getOrdInSession();
          }
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
      }

      // Otherwise default back to the user's home page.
      return op.getUser().getHomePage();
    }

    private static class HxFilter extends AgentFilter
    {
      // Exclude the hx px agent
      public boolean include(AgentInfo agent)
      {
        if (agent.getAgentType().is(hxView))
          return (!agent.getAgentId().endsWith("/hx"));

        if (agent.getAgentType().is(servlet))
          return true;

        if (agent.getAgentType().is(BIFormFactorMax.TYPE) && agent.getAgentType().is(BIJavaScript.TYPE))
          return true;

        return false;
      }
    }

    /**
     * HxWbFilter makes sure that the hx view that acquires the wb views is profile appropriate.
     */
    private static class HxWbFilter extends AgentFilter
    {
      public HxWbFilter(BIWebProfile profile)
      {
        this.profile = profile;
      }

      public boolean include(AgentInfo agent)
      {
        if (agent.getAgentType().is(wbView))
        {
          AgentList hxViews = Sys.getRegistry().getAgents(agent.getAgentType());
          hxViews = hxViews.filter(AgentFilter.is(hxView));
          for (int i=0; i<hxViews.size(); i++)
          {
            AgentInfo hxView = hxViews.get(i);
            TypeInfo typeInfo = hxView.getAgentType();
            if (!hxView.getAgentType().is(hxPxWbView)) {

              if (hxView.getAppName() != null && profile != null)
              {
                String[] profileAppNames = profile.getAppNames();
                for(int j=0; j<profileAppNames.length; j++)
                {
                  if(profileAppNames[j].equals(hxView.getAppName()))
                    return true;
                }
              }
              else{
                return true;
              }
            }
          }
        }
        return false;
      }

      BIWebProfile profile;
    }

    // TODO: Currently all mobile views are filtered out. This could be improved...
    private static class NoMobileFilter extends AgentFilter
    {
      public boolean include(AgentInfo agent)
      {
        return !agent.getAgentType().is(mobileView);
      }
    }
  }

  /**
   * Provide the BenchmarkCommand for this profile to obtain load times when
   * Logger for 'diagnostics.hx.loadTime' is set to FINE or higher.
   */
  public Command getBenchmarkCommand(){
    return benchmark;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final String themeKey = "selectedHxTheme";
  public static final String fullScreenKey = "fullScreen";
  public static final String autoRefreshKey = "autoRefreshTrigger";
  
  public static final BOrd coreThemeCss = BOrd.make("module://web/rc/theme/theme.css");
  public static final BOrd coreHxCss = BOrd.make("module://hx/javax/baja/hx/default.css");
  public static final BOrd coreHxJs = BOrd.make("module://hx/javax/baja/hx/hx.js");
  public static final BOrd hxContainerCss = BOrd.make("module://hx/rc/container/container.css");
  public static final BOrd hxContainerJs = BOrd.make("module://hx/rc/container/hxContainer.built.min.js");

  public static final BOrd jQueryJs = BOrd.make("module://js/rc/jquery/jquery.min.js");
  public static final BOrd contextMenuCss = BOrd.make("module://js/rc/jquery/contextMenu/jquery.contextMenu.css");

  private int eventCounter = 0;
  HashMap<String, Event> events = new HashMap<>();

  private static Command benchmark;
  
  private static final TypeInfo hxPxWbView   = BHxPxWbView.TYPE.getTypeInfo();
  private static final TypeInfo servlet      = BServletView.TYPE.getTypeInfo();
  private static final TypeInfo exporter     = BExporter.TYPE.getTypeInfo();
  private static final TypeInfo mobileView   = BIMobileWebView.TYPE.getTypeInfo();

  private static final String UTF_8 = StandardCharsets.UTF_8.name();
  private static final BFacets THEME_FACETS = BFacets.make(BFacets.FIELD_EDITOR, BString.make("workbench:FrozenEnumFE"), BFacets.UX_FIELD_EDITOR, BString.make("webEditors:FrozenEnumEditor"));

  //NCCB-2623: This needs to go after TypeInfo exporter
  private interface WebEnvHolder
  {
    HxWebEnv INSTANCE = new HxWebEnv();
  }
  
  private static final String[] DEV_VIEWS = {
    "bajaui:CollectionView"
  };

  private static final boolean AUTO_REFRESH_ENABLED =
    AccessController.doPrivileged((PrivilegedAction<Boolean>)
      () -> Boolean.getBoolean("niagara.profile.hx.autoRefreshEnabled"));

  private static final Map<String, Boolean> devViewsEnabled = new HashMap<>();
  static {
    AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
      for (String s : DEV_VIEWS) {
        devViewsEnabled.put(s, Boolean.getBoolean("profile.enableDevView." + s));
      }
      return null;
    });
  }

  private static boolean themeWarningWasLogged;
}
