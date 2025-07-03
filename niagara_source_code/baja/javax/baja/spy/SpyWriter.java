/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.spy;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.baja.io.*;
import javax.baja.file.*;
import javax.baja.session.CsrfException;
import javax.baja.session.INiagaraSuperSession;
import javax.baja.session.SessionUtil;
import javax.baja.sys.*;

import org.owasp.encoder.Encode;
import com.tridium.util.EscUtil;

/**
 * <p>SpyWriter is used to generate the HTML content of Spy pages.</p>
 *
 * <p>As of Niagara 4.10, SpyWriter methods escape HTML by default. To begin
 * writing raw HTML to escaped methods, call {@code unsafe()}. Break the
 * call chain to begin writing escaped HTML again.</p>
 *
 * <pre>{@code
 *  spyWriter.td(stringToBeEscaped);
 *
 *  spyWriter.unsafe() //obtain an unsafe SpyWriter
 *    .td("<b>Disable escaping to write raw HTML</b>")
 *    .td("<b>Keep the chain going to keep escaping disabled</b>");
 *
 *  spyWriter.td(anotherStringToBeEscaped);
 * }</pre>
 *
 * @author    Brian Frank
 * @creation  16 Nov 01
 * @version   $Revision: 5$ $Date: 8/10/10 11:42:29 AM EDT$
 * @since     Niagara 3.0
 */
public class SpyWriter
  extends HtmlWriter
{ 

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public SpyWriter(Writer out, FilePath path)
  {
    this(out, path, null);
  }
  
  /**
   * Constructor with Context.
   *
   * @since Niagara 3.5
   */
  public SpyWriter(Writer out, FilePath path, Context cx)
  {
    super(out);
    this.path = path;
    this.cx = cx;
  }

  /**
   * Constructor.
   */
  public SpyWriter(OutputStream out, FilePath path)
  {
    this(out, path, null);
  }
  
  /**
   * Constructor with Context.
   *
   * @since Niagara 3.5
   */
  public SpyWriter(OutputStream out, FilePath path, Context cx)
  {
    super(out);
    this.path = path;
    this.cx = cx;
  }

  /**
   * Constructor for an unsafe proxy that performs no HTML escaping, but
   * writes all HTML through to the original SpyWriter.
   * @param actual the actual SpyWriter to perform the writing
   * @since Niagara 4.10
   */
  private SpyWriter(SpyWriter actual)
  {
    super(actual);
    unsafe = this;
  }

  /**
   * @since Niagara 4.10
   * @return a SpyWriter instance that will write unsafe HTML, and will continue
   * to write unsafe HTML as long as more calls are chained from it. To revert
   * back to safe/escaped HTML, start your calls again from the
   * {@code SpyWriter} instance on which you called {@code unsafe()}.
   */
  public SpyWriter unsafe()
  {
    if (!closed && unsafe == null)
    {
      unsafe = makeUnsafeSpyWriter();
    }
    return unsafe;
  }

  /**
   * @since Niagara 4.10
   * @return a SpyWriter instance that does not perform any HTML-escaping.
   */
  protected SpyWriter makeUnsafeSpyWriter()
  {
    return new SpyWriter(this);
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Get the current path of the spy page being written.
   */
  public FilePath getPath()
  {
    return path;
  }
  
  /**
   * Get the current Context of the spy page being written.
   *
   * @since Niagara 3.5
   */
  public Context getContext()
  {
    return cx;
  }

////////////////////////////////////////////////////////////////
// HtmlWriter
////////////////////////////////////////////////////////////////    

  @Override
  public SpyWriter w(Object o)
  { 
    if (o instanceof BAbsTime)
    {
      BAbsTime x = (BAbsTime)o;
      if (x == null || x.isNull()) print("null");
      else print(timeFormat.format(new Date(x.getMillis()))); 
    }
    else
    {
      print(o); 
    }
    return this; 
  }
    
////////////////////////////////////////////////////////////////
// Properties
////////////////////////////////////////////////////////////////  

  /**
   * Start a two column properties table.
   */
  public SpyWriter startProps()
  {
    w("<table class='spy-props'>\n");
    return this;
  }

  /**
   * Start a two column properties table. HTML will be escaped for safety.
   */
  public SpyWriter startProps(String title)
  {
    w("<table class='spy-props'>\n");
    w("<tr><th class='spy-prop-title' nowrap='true' colspan='2'>").w(encode(title)).w("</th></tr>\n");
    return this;
  }

  /**
   * Write a two column table row. HTML will be escaped for safety.
   */
  public SpyWriter prop(Object name, Object value)
  {
    return writeProp(name, String.valueOf(value));
  }

  /**
   * Write a two column table row. HTML will be escaped for safety.
   */
  public SpyWriter prop(Object name, boolean value)
  {
    return writeProp(name, String.valueOf(value));
  }

  /**
   * Write a two column table row. HTML will be escaped for safety.
   */
  public SpyWriter prop(Object name, int value)
  {
    return writeProp(name, String.valueOf(value));
  }
  
  /**
   * Write a two column table row. HTML will be escaped for safety.
   */
  public SpyWriter prop(Object name, double value)
  {
    return writeProp(name, String.valueOf(value));
  }

  /**
   * Writes a name/value property pair, where the name is a hyperlink. Call
   * where you may otherwise call {@code prop()}. (You do not need to call
   * {@code href()} first.)
   * @param href hyperlink target
   * @param linkTitle text of hyperlink
   * @param value property value
   * @since Niagara 4.10
   */
  public SpyWriter propNameLink(Object href, Object linkTitle, Object value)
  {
    unsafe().prop(createLink(String.valueOf(href), linkTitle), encode(value));
    return this;
  }

  /**
   * Writes a name/value property pair, where the value is a hyperlink. Call
   * where you may otherwise call {@code prop()}. (You do not need to call
   * {@code href()} first.)
   * @param name property name
   * @param href hyperlink target
   * @param linkTitle text of hyperlink
   * @since Niagara 4.10
   */
  public SpyWriter propValueLink(Object name, Object href, Object linkTitle)
  {
    unsafe().prop(encode(name), createLink(String.valueOf(href), linkTitle));
    return this;
  }

  private SpyWriter writeProp(Object name, String value)
  {
     return w("<tr class='spy-prop'><td class='spy-prop-name'>")
      .w(encode(name))
      .w("</td><td class='spy-prop-value'>")
      .w(encode(value))
      .w("</td></tr>\n");
  }

  /**
   * Close a two column properties table.
   */
  public SpyWriter endProps()
  {
    endTable();
    return this;
  }

  /**
   * SpyWriter sanitizes hrefs by encoding them for HTML.
   */
  @Override
  public String href(String href)
  {
    return encode(href);
  }

  /**
   * Write a table row with the specified colspan that can be used as a title
   * header to separate rows. HTML will be escaped for safety.
   */
  @Override
  public SpyWriter trTitle(Object title, int colspan)
  {
    return (SpyWriter) super.trTitle(encode(title), colspan);
  }

  /**
   * Write a table header column using the predefined formatting of
   * {@code trTitle()}. HTML will be escaped for safety.
   */
  @Override
  public SpyWriter thTitle(Object title)
  {
    return (SpyWriter) super.thTitle(encode(title));
  }

  /**
   * Write a th tag with align=left and nowrap. HTML will be escaped for safety.
   */
  @Override
  public SpyWriter th(Object s)
  {
    return (SpyWriter) super.th(encode(s));
  }

  /**
   * Write a td tag with align=left and nowrap. HTML will be escaped for safety.
   */
  @Override
  public SpyWriter td(Object s)
  {
    return (SpyWriter) super.td(encode(s));
  }

  @Override
  public void close()
  {
    closed = true;
    if (isUnsafeProxy())
    {
      super.close();
    }
    else
    {
      if (unsafe != null && !unsafe.closed)
      {
        unsafe.closed = true;
        unsafe.close();
      }
      else
      {
        super.close();
      }
    }
  }

  ////////////////////////////////////////////////////////////////
// CSRF Token Utilities
////////////////////////////////////////////////////////////////

  /**
   * Intended for use when inserting hyperlinks into spy pages that could potentially
   * change state on the server side, given an href name, append a CSRF token
   * to the href name that is valid for the current session (if no current session is
   * available, indicating a call from the Workbench/Local spy, the original hrefName
   * will be returned with no CSRF token appended). A subsequent call to
   * <code>verifyNameAndCsrfToken()</code> can validate that a spy request contains
   * a proper CSRF token for the current session.
   *
   * @param hrefName The href String on which to append a CSRF token for the current session.
   * @return A new href String containing a valid CSRF token for the current session. If the
   * current session is null (indicating a call from Workbench/Local spy), then the original
   * hrefName String is returned.
   *
   * @since Niagara 4.3
   */
  public static String addCsrfToken(String hrefName)
  {
    // NOTE TO DEVELOPERS - If you use these CSRF token methods for your spy pages, you should
    // consider adding tests for your specific spy ORDs to the test class
    // test.javax.baja.spy.BSpyCsrfTest

    INiagaraSuperSession session = SessionUtil.getCurrentNiagaraSuperSession();
    if (session != null)
    {
      return hrefName + CSRF_TOKEN_PREFIX + EscUtil.slot.escape(session.getCsrfToken());
    }
    else
    {
      return hrefName;
    }
  }

  /**
   * Intended for use when validating spy ORD requests, given an href name and CSRF token String,
   * checks the name and verifies that the CSRF token is valid for the current session. A
   * CsrfException will be thrown if the hrefNameAndToken contains an invalid CSRF token for the
   * current session or if the hrefNameAndToken starts with the expectedHrefName but doesn't contain
   * a properly formed CSRF token.  If there is no current session available (indicates a
   * call from Workbench/Local spy), then the CSRF token validation step will be skipped and
   * only the name will be compared to compute the result.
   *
   * @param hrefNameAndToken An href name and CSRF token String from a spy ORD request.
   * @param expectedHrefName The expected href name to use for validation of the hrefNameAndToken.
   * @return true if the name check and CSRF token validation is successful or false if the
   * hrefNameAndToken does not contain a CSRF token and it doesn't start with the expectedHrefName.
   * If the current session is not available (indicating a call from Workbench/Local spy), then
   * only the name check will determine the result.
   * @throws CsrfException if the current session is available and the hrefNameAndToken contains
   * an invalid CSRF token for the current session or if the hrefNameAndToken starts with the
   * expectedHrefName but doesn't contain a properly formed CSRF token.
   *
   * @since Niagara 4.3
   */
  public static boolean verifyNameAndCsrfToken(String hrefNameAndToken, String expectedHrefName)
  {
    // NOTE TO DEVELOPERS - If you use these CSRF token methods for your spy pages, you should
    // consider adding tests for your specific spy ORDs to the test class
    // test.javax.baja.spy.BSpyCsrfTest

    INiagaraSuperSession session = SessionUtil.getCurrentNiagaraSuperSession();

    int idx = hrefNameAndToken.indexOf(CSRF_TOKEN_PREFIX);
    if (idx >= 0)
    {
      String namePart = hrefNameAndToken.substring(0, idx);
      if (expectedHrefName.equals(namePart))
      {
        if (session != null)
        {
          String token = EscUtil.slot.unescape(hrefNameAndToken.substring(idx + CSRF_TOKEN_PREFIX.length()));
          session.verifyCsrfToken(token);
        }
        return true;
      }
    }

    if (hrefNameAndToken.startsWith(expectedHrefName))
    {
      if (session != null)
      {
        throw new CsrfException("csrf token missing");
      }
      return true;
    }

    return false;
  }

  /**
   * Intended for use when validating spy ORD requests when you don't know the expected
   * href name (without the CSRF token), given an href name and CSRF token String,
   * computes an expected href name and verifies that the CSRF token is valid for the
   * current session (if the session is available - no session indicates a call from Workbench/
   * Local spy). A CsrfException will be thrown if the hrefNameAndToken contains an invalid
   * CSRF token for the current session or if the hrefNameAndToken doesn't contain a
   * properly formed CSRF token and the current session is available.
   *
   * @param hrefNameAndToken An href name and CSRF token String from a spy ORD request.
   * @return A new href String with the validated CSRF token removed.
   * @throws CsrfException if the hrefNameAndToken contains an invalid CSRF token for the current
   * session or if the hrefNameAndToken doesn't contain a properly formed CSRF token and the
   * current session is available.
   *
   * @since Niagara 4.3
   */
  public static String getNameWithoutCsrfToken(String hrefNameAndToken)
  {
    // NOTE TO DEVELOPERS - If you use these CSRF token methods for your spy pages, you should
    // consider adding tests for your specific spy ORDs to the test class
    // test.javax.baja.spy.BSpyCsrfTest

    int idx = hrefNameAndToken.indexOf(CSRF_TOKEN_PREFIX);
    if (idx >= 0)
    {
      String namePart = hrefNameAndToken.substring(0, idx);
      verifyNameAndCsrfToken(hrefNameAndToken, namePart);
      return namePart;
    }
    else if (SessionUtil.getCurrentNiagaraSuperSession() != null)
    {
      throw new CsrfException("csrf token missing");
    }

    return hrefNameAndToken;
  }

  private String encode(Object s)
  {
    String str = String.valueOf(s);
    return isUnsafeProxy() ? str : Encode.forHtml(String.valueOf(s));
  }

  private boolean isUnsafeProxy()
  {
    return unsafe == this;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final String CSRF_TOKEN_PREFIX = EscUtil.slot.escape("?token=");
  static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss dd-MMM-yy zzz");
  private SpyWriter unsafe;
  private boolean closed;

  FilePath path;
  Context cx;
}
