/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.file.BIFile;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.net.Http;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BComplex;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.util.Lexicon;
import javax.baja.web.BWebProfileConfig;
import javax.baja.web.BWebService;
import javax.baja.web.WebDev;
import javax.baja.web.WebOp;
import javax.baja.web.hx.BIHxProfile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.owasp.encoder.Encode;
import com.tridium.net.BHttpsScheme;
import com.tridium.hx.util.HxUtils;
import com.tridium.web.WebUtil;


/**
 * HxOp wraps a WebOp or another HxOp with additional
 * contextual information for Hx pages.
 *
 * @author    Andy Frank
 * @creation  4 Jan 05
 * @version   $Revision: 47$ $Date: 11/2/10 3:34:58 PM EDT$
 * @since     Baja 1.0
 */
public class HxOp
  extends WebOp
{
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Wrap an existing HxOp with a new base using an
   * auto created unique name.
   */
  public HxOp make(OrdTarget base)
  {
    return make(getUniqueName(base), base);
  }

  /**
   * Wrap an existing HxOp with a new base and given name. The
   * name must be unique to all peer instances of this op. The
   * name is used for building paths in order to resolve form
   * elements back to the appropriate servlet after a submit.
   */
  public HxOp make(String name, OrdTarget base)
  {
    HxOp newOp = new HxOp(name, base, this);
    newOp.mounted = mounted;
    return newOp;
  }

  /**
   * <p>
   * Wrap an existing HxOp with a new base and given name. The
   * name must be unique to all peer instances of this op. The
   * name is used for building paths in order to resolve form
   * elements back to the appropriate servlet after a submit.
   * </p>
   * <p>
   * Typical use case is to reference an object that is not
   * mounted.  In this case, the write permissions for the op
   * are specified by <code>setReadonly()</code>.
   * </p>
   */
  public HxOp make(String name, BObject def, BFacets facets)
  {
    HxOp sub = make(name, new OrdTarget(this, def));
    sub.mergeFacets(facets);
    sub.mounted = false;
    return sub;
  }

  /**
   * Wrap an existing HxOp with a new base and given name. The
   * name must be unique to all peer instances of this op. The
   * name is used for building paths in order to resolve form
   * elements back to the appropriate servlet after a submit.
   */
  protected HxOp(String name, OrdTarget base, HxOp op)
  {
    super(base, op.getService(), op.getRequest(), op.getResponse());
    this.document = op.document;

    path = op.path;
    if (op.path.length() > 0) path += ".";
    path += name;
  }

  public HxOp(WebOp op) throws Exception
  {
    this(op, /*initializeDocument*/true);
  }

  HxOp(WebOp op, boolean initializeDocument) throws Exception
  {
    super((OrdTarget)op.getBase(), op.getService(), op.getRequest(), op.getResponse());
    this.document = new HxDocument(op);

    if (!initializeDocument)
      return;

    this.document.init();

    // POST
    if (op.getRequest().getMethod().equals(Http.METHOD_POST))
    {
      String contentType = op.getRequest().getContentType();
      if (contentType != null)
      {
        if (contentType.startsWith(HxConst.FORM_URL_ENCODED) ||
            contentType.startsWith(HxConst.FORM_MULTI_PART))
        {
          // Must use startsWith for FORM_MULTI_PART because
          // contentType includes additional information
          // describing part boundries

          if (contentType.startsWith(HxConst.FORM_MULTI_PART))
            setMultiPartForm();
          else
            loadPostBody();

          // Form submit
          document.formPost = true;
          decodeFormValues();
        }
        else if (contentType.startsWith(HxConst.UPDATE))
        {
          // Update
          loadPostBody();
          document.update = true;
        }
        else if (contentType.startsWith(HxUtils.HX_PROCESS_WITH_JSON))
        {
          loadPostBody();
          //these requests are raw json and do not require any individual form information.
          //When other Widgets attempt to parse this, it could fail to parse.
          document.form = new HashMap<>();
        }
        else
        {
          // Else we assume we have a URL_ENCODED form body
          loadPostBody();
        }
      }
    }
  }

  /**
   * Get the BWebProfileConfig to use for this request.
   * BHxIProfile.PREFER_FX_FACETS will be merged with the Context facets.
   */
  @Override
  public BWebProfileConfig getProfileConfig(Context cx)
  {
    BFacets facets = BIHxProfile.PREFER_HX_FACETS;
    if(cx != null && cx.getFacets() != null)
      facets = BFacets.make(facets, cx.getFacets());
    return super.getProfileConfig(facets);
  }

  /**
   * Get the BWebProfileConfig to use for this request.
   * BHxIProfile.PREFER_FX_FACETS will be used.
   */
  @Override
  public BWebProfileConfig getProfileConfig()
  {
    return super.getProfileConfig(BIHxProfile.PREFER_HX_FACETS);
  }

  /**
   * Load post body into memory.  Never do this if the
   * post is a multipart form submission.
   */
  void loadPostBody()
    throws Exception
  {
    // parse post body
    WebOp c = getWebOp();
    byte[] buf = new byte[c.getRequest().getContentLength()];
    DataInputStream in = new DataInputStream(c.getRequest().getInputStream());
    in.readFully(buf);
    document.postBody = new String(buf);
  }

////////////////////////////////////////////////////////////////
// General Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the profile.
   */
  public BHxProfile getProfile()
  {
    return document.profile;
  }

  /**
   * Get the base WebOp.
   */
  public WebOp getWebOp()
  {
    return document.webop;
  }

  /**
   * Get the current path.
   */
  public String getPath()
  {
    return path;
  }

  /**
   * Mutate the existing HxOp by merging additional facets and return the existing HxOp.
   */
  @Override
  public HxOp mergeFacets(BFacets newFacets)
  {
    return (HxOp) super.mergeFacets(newFacets);
  }

  /**
   * Override this OrdTarget as readonly. If the underlying
   * OrdTarget does not already have write access, this method
   * has no effect. This flag is <b>not</b> inherited.
   */
  public void setReadonly(boolean readonly)
  {
    this.readonly = readonly;
  }


  /**
   * Return true if op is configured to use <code>multipart/form-data</code >
   * encoding for form elements. Defaults to false.
   */
  public boolean isMultiPartForm()
  {
    return document.multiPart;
  }

  /**
   * Configure op to use <code>multipart/form-data</code>
   * encoding for form elements.
   */
  public void setMultiPartForm()
  {
    document.multiPart = true;
  }

////////////////////////////////////////////////////////////////
// Lexicon
////////////////////////////////////////////////////////////////

  /**
   * Get the lexicon for the specified module and the
   * current language.
   */
  public Lexicon getLexicon(String module)
  {
    Lexicon lex = document.lexicons.get(module);
    if (lex == null)
    {
      lex = Lexicon.make(module, this);
      document.lexicons.put(module, lex);
    }
    return lex;
  }

////////////////////////////////////////////////////////////////
// Cookies
////////////////////////////////////////////////////////////////

  /**
   * Convenience method to add a cookie.
   */
  public void addCookie(String name, String value)
  {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    getResponse().addCookie(cookie);
  }

  /**
   * Convenience method to get a cookie value.  If this
   * cookie does not exist, return null.
   */
  public String getCookie(String name)
  {
    Cookie[] cookies = getRequest().getCookies();
    for (int i=0; i<cookies.length; i++)
      if (cookies[i].getName().equals(name))
        return cookies[i].getValue();
    return null;
  }

  /**
   * Convience method to remove a cookie
   */
  public void removeCookie(String name)
  {
    Cookie c = new Cookie(name, "");
    c.setPath("/");
    c.setMaxAge(0);
    getResponse().addCookie(c);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>getRequest().getAttribute(name)</code>.
   */
  public Object getAttribute(String name)
  {
    return getRequest().getAttribute(name);
  }

  /**
   * Convenience for <code>getRequest().getAttributeNames()</code>,
   * return a String[] array instead of an Enumeration.
   *
   */
  public String[] getAttributeNames()
  {
    ArrayList<String> s = new ArrayList<>();
    Enumeration<String> e = getRequest().getAttributeNames();
    while (e.hasMoreElements())
      s.add(e.nextElement());
    return s.toArray(new String[s.size()]);
  }

  /**
   * Convenience for <code>getRequest().setAttribute(name, value)</code>.
   */
  public void setAttribute(String name, Object value)
  {
    getRequest().setAttribute(name, value);
  }

////////////////////////////////////////////////////////////////
// Configuration
////////////////////////////////////////////////////////////////

  /**
   * Get the configuration property by this name. If no
   * property by that name has been set, return the default
   * value. Each HxOp instance maintains its own configuration
   * properties. So sub ops do not inherit their parents
   * configuration.
   */
  public BValue getConfig(String name, BValue def)
  {
    if (config != null)
    {
      BValue v = config.get(name);
      if (v != null) return v;
    }
    if (delegate != null)
    {
      BValue v = delegate.get(name);
      if (v != null) return v;
    }
    return def;
  }

  /**
   * Set the configuration property by this name to the
   * given value, overriding any previous set value.
   */
  public void setConfig(String name, BValue value)
  {
    if (config == null) config = new HashMap<>();
    config.put(name, value);
  }

  /**
   * Use the given BComplex to map configuration properties.
   */
  public void setConfig(BComplex complex)
  {
    this.delegate = complex;
  }

////////////////////////////////////////////////////////////////
// GET Methods
////////////////////////////////////////////////////////////////

  /**
   * Return the content buffer.
   */
  public StringBuffer getContent()
  {
    return document.content.getBuffer();
  }

  /**
   * This method overrides WebOp to route to our buffered
   * content - getHtmlWriter() will also route to the buffer.
   * Use getResponse().getWriter() to get the original
   * PrintWriter.
   */
  public PrintWriter getWriter()
    throws IOException
  {
    return document.writerStack.peek();
  }

  /**
   * Set the override writer such that calls to getWriter will return the override.
   * Up to two levels of override are available. An IllegalStateException will occur if a third
   * level of override is attempted.
   */
  public PrintWriter setWriter(PrintWriter writer)
    throws IOException
  {
    return document.writerStack.push(writer);
  }

  /**
   * Reset the Writer to its original state before the last call to 'HxOp.setWriter'.
   */
  public void resetWriter()
    throws IOException
  {
    if (document.writerStack.size() > 1)
    {
      document.writerStack.pop();
    }
  }

  /**
   * Return true if one or more views marked op as dynamic.
   */
  public boolean isDynamic()
  {
    return document.dynamic;
  }

  /**
   * Mark the view as dynamic.
   */
  public void setDynamic()
  {
    document.dynamic = true;
  }

  /**
   * Return true if this resposne was marked as raw.
   */
  public boolean isRaw()
  {
    return document.raw;
  }

  /**
   * Mark this response as raw.
   */
  public void setRaw()
  {
    document.raw = true;
  }

  /**
   * Scope the local name with the current path.
   */
  public String scope(String s)
  {
    if (path.length() == 0) return s;
    return path + "." + s;
  }

  /**
   * Return a unique identifier for the scope
   * of this context.
   */
  public String getUniqueName()
  {
    return "uid" + document.id++;
  }

  /**
   * Return a unique identifier for the scope
   * of this context.
   */
  public String getUniqueName(OrdTarget target)
  {
    if(target==null || !(target.get() instanceof BComplex))
      return getUniqueName();

    return ((BComplex)target.get()).getName();
  }

  /**
   * Include an arbitrary tag for the head block. This tag should
   * be a complete and well formed xml tag.
   */
  public void addHeadTag(String tag)
  {
    if (!document.htags.contains(tag))
      document.htags.add(tag);
  }

  /**
   * Get misc tags for head block.
   */
  public String[] getHeadTags()
  {
    return document.htags.toArray(new String[document.htags.size()]);
  }

  /**
   * Include a stylesheet if has not already been included.
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0. Use {@link #addStyleSheet(BOrd)} instead.
   */
  @Deprecated
  public void addStyleSheet(String url)
  {
    BOrd ord = BOrd.make(url);
    addStyleSheet(ord);
  }

  /**
   * Include a stylesheet if has not already been included.
   * @since Niagara 3.5
   */
  public void addStyleSheet(BOrd ord)
  {
    if (!document.styles.contains(ord))
    {
      document.styles.add(ord);

      //POST style add
      if(!getRequest().getMethod().toLowerCase().equals("get"))
        addOnload("hx.addStyleSheet('" + WebUtil.toUri(this, getRequest(), ord) + "');");
    }
  }

  /**
   * Get the style sheets that were included in this op.
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0. Use {@link #getStyleSheetOrds()} instead.
   */
  @Deprecated
  public String[] getStyleSheets()
  {
    String[] styles = new String[document.styles.size()];
    for(int i=0; i<document.styles.size(); i++)
      styles[i]=document.styles.get(i).toString();
    return styles;
  }

  /**
   * Get the style sheets that were included in this op.
   * @since Niagara 3.5
   */
  public BOrd[] getStyleSheetOrds()
  {
    return document.styles.toArray(new BOrd[document.styles.size()]);
  }

  /**
   * Include an external javascript file if has not already been included.
   * @deprecated since Niagara 3.5 - will be removed in Niagara 5.0. Use {@link #addJavaScript(BOrd)} instead.
   */
  @Deprecated
  public void addJavaScript(String url)
  {
    BOrd ord = BOrd.make(url);
    addJavaScript(ord);
  }

  /**
   * Include an external javascript file ord if has not already been included.
   *
   * Starting in Niagara 4.4, addJavascript does not immediately call 'hx.addJavascript' during a POST, instead this
   * call waits for HxUtil.addJavascriptOnload to be called at a point when both the javascript resources and the
   * desired HxOp.addOnload code has been added so that they execute in the appropriate order. When launching a
   * javax.baja.hx.Dialog, this function is already called at the appropriate time.
   * @since Niagara 3.5
   */
  public void addJavaScript(BOrd ord)
  {
    if (!document.scripts.contains(ord))
    {
      document.scripts.add(ord);
    }
  }

  /**
   * Get the external javascript files that were included in this op.
   * @deprecated since Niagara 3.5 - will be removed in Niagara 5.0
   */
  @Deprecated
  public String[] getJavaScript()
  {
    String[] javascript = new String[document.scripts.size()];
    for(int i=0; i<document.scripts.size(); i++)
      javascript[i]=document.scripts.get(i).toString();
    return javascript;
  }

  /**
   * Get the external javascript files that were included in this op.
   * @since Niagara 3.5
   */
  public BOrd[] getJavaScriptOrds()
  {
    return document.scripts.toArray(new BOrd[document.scripts.size()]);
  }

  /**
   * Add JavaScript code to invoke in body.onload.  The <code>code</code>
   * value is appended directly to the end of the existing string, so it
   * is the developer's responsibility to properly terminate expressions
   * with the appropriate semi-colon.
   *
   * Note that Starting in Niagara 4.4, onload is written within a script tag during GET, so a single quote ' is now
   * allowed in an addOnload for both GET and POST quoting semantics. For backwards compatibility, &quot; will now be
   * unescaped to \" so it is best to avoid &quot; inside some quoted text.
   * For example, dom.innerHTML="He said &quot; hello &quot;"; will be escaped to dom.innerHtml=" He said "hello"" which
   * will not properly parse. To avoid this problem its best to use 'HxUtil.escapeJsStringLiteral'  or HtmlWriter.safe()
   * for placing text within quotes.
   */
  public void addOnload(String code)
  {
    document.onload.add(code);
  }
  
  /**
   * Add JavaScript code to invoke in body.onunload.  The <code>code</code>
   * value is appended directly to the end of the existing string, so it
   * is the developer's responsibility to properly terminate expressions
   * with the appropriate semi-colon.
   *
   * @see HxOp#addOnload(String)
   */
  public void addOnunload(String code)
  {
    document.onunload.add(code);
  }

  /**
   * Add JavaScript code to invoke in body.resize.  The <code>code</code>
   * value is appended directly to the end of the existing string, so it
   * is the developer's responsibility to properly terminate expressions
   * with the appropriate semi-colon.
   *
   * @see HxOp#addOnload(String)
   */
  public void addOnresize(String code)
  {
    document.onresize.add(code);
  }

  /**
   * Get the onload javascript code.
   */
  public String[] getOnload()
  {
    return document.onload.toArray(new String[document.onload.size()]);
  }
  
  /**
   * Get the onunload javascript code.
   */
  public String[] getOnunload()
  {
    return document.onunload.toArray(new String[document.onunload.size()]);
  }

  /**
   * Get the resize javascript code.
   */
  public String[] getOnresize()
  {
    return document.onresize.toArray(new String[document.onresize.size()]);
  }


  /**
   *
   * Add JavaScript code to the global script block. When Globals are run as part of a Post, they cannot run with
   * the global scope because this code needs to wait for asynchronous javascript sources to load, make sure to assign
   * global functions to the 'window' if they need to be used later.
   */
  public void addGlobal(String code)
  {
    document.global.add(code);
  }

  /**
   * Get the global javascript code. When Globals are run as part of a Post, they cannot run with global scope because it
   * needs to wait for asynchronous javascript sources to load, so make sure to assign global functions to the 'window'
   * if they need to be used later.
   */
  public String[] getGlobal()
  {
    return document.global.toArray(new String[document.global.size()]);
  }
    
  /**
   * Return true if Hx is using jQuery.
   * 
   * @since Niagara 3.7
   */
  public boolean isJQuery()
  {
    return document.jQuery;
  }
  
  /**
   * Query whether resources are going to be bound.
   *
   * @return Returns true if resources are bundled together when Hx loads.
   */
  public boolean isBindResources()
  {
    return document.isBindResources;
  }
  
////////////////////////////////////////////////////////////////
// POST Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the POST body contents or null if there is no body.
   * <p>
   * Please note, the post body is raw and hasn't been processed for possible
   * XSS attacks. If you want to use a POST body that's escaped from XSS attacks
   * please use {@link HxOp#getPostBody()}.
   * </p>
   *
   * @see Encode#forHtml(String)
   *
   * @return The post body or null if none is available.
   */
  public String getUnsafePostBody()
  {
    return document.postBody;
  }

  /**
   * Return the POST body contents or null if there is no body.
   * Any Strings returned will be escaped to prevent XSS attacks.
   *
   * @return The post body or null if none is available.
   */
  public String getPostBody()
  {
    String body = getUnsafePostBody();
    return body != null ? Encode.forHtml(body) : null;
  }

  /**
   * Decode the POST body into name/value pairs.
   */
  public void decodeFormValues()
  {
    // Only need to decode once
    if (document.form != null) return;

    if (isMultiPartForm())
    {
      try
      {
        document.form = new MultiPartForm().decode(this);
      }
      catch (Exception e) { throw new BajaRuntimeException(e); }
    }
    else
    {
      document.form = new HashMap<>();

      if (document.postBody != null)
      {
        String[] pairs = TextUtil.split(document.postBody, '&');
        for (int i = 0; i < pairs.length; i++)
        {
          String[] pair = TextUtil.split(pairs[i], '=');

          if (pair.length >= 2)
            document.form.put(HxUtil.decode(pair[0]), HxUtil.decode(pair[1]));
        }
      }
    }
  }

  /**
   * Return true if this HxOp wraps a form submit post.
   */
  public boolean isFormPost()
  {
    return document.formPost;
  }

  /**
   * Return true if this HxOp wraps an update request.
   */
  public boolean isUpdate()
  {
    return document.update;
  }

  /**
   * Get the value for this key. Return null if the key does not exist.
   * <p>
   * Any returned string will be escaped to avoid possible XSS attacks.
   * </p>
   *
   * @see Encode#forHtml(String)
   *
   * @param key The form value to decode.
   * @return If the key exists, return an escaped form value otherwise
   *         return null.
   */
  public String getFormValue(String key)
  {
    decodeFormValues();

    final String formKey = makeFormKey(key);
    String value = document.escapedForm != null ? document.escapedForm.get(formKey) : null;

    if (value == null)
    {
      value = document.form.get(formKey);

      if (value != null)
      {
        if (document.escapedForm == null)
          document.escapedForm = new HashMap<>();

        document.escapedForm.put(formKey, (value = Encode.forHtml(value)));
      }
    }

    return value;
  }

  /**
   * Returns an unsafe form value that's not escaped from possible XSS attacks.
   * Null is returned if the key can't be found.
   * <p>
   * The value returned will not be escaped against XSS attacks. It's advised to
   * use {@link HxOp#getFormValue(String)} instead.
   * </p>
   *
   * @see HxOp#getFormValue(String)
   * @see Encode#forHtml(String)
   *
   * @param key
   * @return The form value or null if it can't be found.
   */
  @SuppressWarnings("unused")
  public String getUnsafeFormValue(String key)
  {
    decodeFormValues();
    return document.form.get(makeFormKey(key));
  }

  /**
   * Make a valid form key that can be used to look up a form value.
   *
   * @param key The key to look up the form value with.
   * @return A valid form key that takes the path into account.
   */
  private String makeFormKey(String key)
  {
    return path.length() > 0 ? path + "." + key : key;
  }

  /**
   * Return an array containing all key values.
   */
  public String[] getFormKeys()
  {
    decodeFormValues();

    if (keys == null)
    {
      ArrayList<String> list = new ArrayList<>();
      Iterator<String> iter = document.form.keySet().iterator();
      while (iter.hasNext())
      {
        String key = iter.next();
        if (path.length() == 0)
          list.add(key);
        else if (key.startsWith(path) && key.length() > path.length() && key.charAt(path.length()) == '.')
          list.add(key.substring(path.length()+1));
      }
      keys = list.toArray(new String[list.size()]);
    }

    return keys;
  }

  /**
   * Get the file uploaded by the form control with
   * this name, or null if no file by this name.
   */
  public BIFile getFile(String key)
  {
    if (document.files == null) return null;
    if (path.length() > 0)
    {
      StringBuilder buf = new StringBuilder(path).append(".").append(key);
      key = buf.toString();
    }
    return document.files.get(key);
  }

  /**
   * Get the redirect url to use following a save. Return null
   * no url has been set.
   */
  public String getRedirect()
  {
    return document.redirect;
  }

  /**
   * Set the redirect url used following a save.
   */
  public void setRedirect(String url)
  {
    document.redirect = url;
  }

  /**
   * Return true if an error was sent using <code>sendError()</code>.
   */
  public boolean isErrorSent()
  {
    return document.error;
  }

  /**
   * Convienence for <code>sendError(
   * HttpServletResponse.SC_BAD_REQUEST, ex)</code>.
   */
  public void sendError(Exception ex)
  {
    sendError(HttpServletResponse.SC_BAD_REQUEST, ex);
  }

  /**
   * Send an error response to the client with the given HTTP
   * status code and Exception details. This method will use
   * the raw OutputStream, bypassing the buffered Hx version.
   * This method should only be used on GET requests.
   */
  public void sendError(int sc, Exception ex)
  {
    try
    {
      WebOp op = document.webop;
      String message = WebUtil.toUserMessage(ex, op);
      if(op.getResponse().isCommitted())
      {
        ServletOutputStream out = op.getResponse().getOutputStream();
        out.write(message.getBytes());
      }
      else
      {
        op.getResponse().sendError(sc, message);
      }
      document.error = true;
    }
    catch (IOException e)
    {
      LOGGER.log(Level.SEVERE, "Cannot send Error", e);
    }
  }
  /**
   * Cache current profile.
   */
  public void setProfile(BHxProfile profile)
  {
    document.profile = profile;
  }

////////////////////////////////////////////////////////////////
// OrdTarget
////////////////////////////////////////////////////////////////

  /**
   * Return true if op has write access.
   */
  public boolean canWrite()
  {
    if (!mounted || (getComponent() == null)) return !readonly;
    return (super.canWrite() && !readonly);
  }

////////////////////////////////////////////////////////////////
// Package-private
////////////////////////////////////////////////////////////////

  /**
   * Set this control value to point to the given file.
   */
  void setFile(String name, BIFile file)
  {
    if (document.files == null)
      document.files = new HashMap<>();
    document.files.put(name, file);
  }

  /**
   * Remove any temp files that were created.
   */
  void deleteTempFiles()
    throws Exception
  {
    if (document.files == null) return;

    Iterator<BIFile> iter = document.files.values().iterator();
    while (iter.hasNext())
    {
      BIFile file = iter.next();
      file.delete();
    }
  }
    
////////////////////////////////////////////////////////////////
// jQuery
////////////////////////////////////////////////////////////////
  
  /**
   * Use jQuery in Hx.
   * <p>
   * Please note that Hx has its own global '$' function (although this is now being deprecated
   * in favor of 'hx.$'). To workaround this, one should use 'jQuery' instead of '$' or use the
   * following design pattern for code that uses jQuery. Please note, since variables are function
   * and not block scoped, this is best practice for JavaScript because it avoids the 
   * unnecessary creation of global variables...
   * 
   * <pre>
   * // Anything that runs in this self-executing function will use the jQuery '$' function.
   * (function($) {
   *   // your jQuery code goes here (jQuery === $)...
   *   $("#foo").click(function() { alert("hi"); });
   * }(jQuery));
   * </pre>
   * 
   * @since Niagara 3.7
   */
  public void jQuery()
  {
    //POST jQuery
    if(!document.jQuery && !getRequest().getMethod().toLowerCase().equals("get"))
    {
      addJavaScript(BHxProfile.jQueryJs);
      addOnload("jQuery.noConflict();");
    }

    document.jQuery = true;
  }
  
////////////////////////////////////////////////////////////////
// HxDocument
////////////////////////////////////////////////////////////////

  private static class HxDocument
  {
    public HxDocument(WebOp webop)
    {
      this.webop = webop;
    }

    private void init()
    {
      lexicons = new HashMap<>();
      htags   = new ArrayList<>();
      styles  = new ArrayList<>();
      scripts = new ArrayList<>();
      onload  = new ArrayList<>();
      onunload = new ArrayList<>();
      onresize  = new ArrayList<>();
      global  = new ArrayList<>();
      content = new StringWriter();
      writerStack = new Stack<>();
      writerStack.push(new PrintWriter(content));
      
      isBindResources = !bindRcWebDev.isEnabled() &&
        !WebDev.get("js").isEnabled() &&
        !WebDev.get("search").isEnabled() &&
        !WebDev.get("webChart").isEnabled() &&
        !WebDev.get("webEditors").isEnabled() &&
        !WebDev.get("bajaScript").isEnabled() &&
        !WebDev.get("hierarchy-ux").isEnabled();
    }

    WebOp webop;
    int id = 0;
    HashMap<String, Lexicon> lexicons;
    BHxProfile profile;
    boolean multiPart = false; // use for both GET and POST

    // GET
    ArrayList<String> htags;
    ArrayList<BOrd> styles;
    ArrayList<BOrd> scripts;
    ArrayList<String> onload;
    ArrayList<String> onunload;
    ArrayList<String> onresize;
    ArrayList<String> global;
    StringWriter content;
    Stack<PrintWriter> writerStack;
    boolean dynamic = false;
    boolean raw     = false;
    boolean error   = false;
    boolean jQuery = false;
    boolean isBindResources = false;

    // POST
    Map<String, String> form = null;
    Map<String, String> escapedForm = null;
    HashMap<String, BIFile> files = null;
    String postBody = null;
    boolean formPost = false;
    boolean update = false;
    String redirect = null;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BComplex delegate = null;
  private HashMap<String, BValue> config = null;
  private boolean readonly = false;
  private String path = "";
  private String[] keys = null;
  private HxDocument document;
  protected boolean mounted = true;
  
  private static final WebDev bindRcWebDev = WebDev.get("bindResources");
  private static final Logger LOGGER = Logger.getLogger("hx");
}


