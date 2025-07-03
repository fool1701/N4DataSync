/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import java.util.StringTokenizer;
import java.util.Vector;

import javax.baja.util.Version;
import javax.servlet.http.HttpServletRequest;

/**
 * UserAgent is used to parse the user-agent HTTP
 * header field to provide information about the
 * browser (or other software) making an HTTP request.
 * <p>
 * This class is based on the following BNF grammar
 * defined for the User-Agent header:
 * <pre>
 *   User-Agent  = "User-Agent" ":" 1*( product | comment )
 *   product  = token ["/" product-version ]
 *   product-version  = token
 *   comment  = "(" *( ctext | comment ) ")"
 *   ctext  = &lt;any TEXT excluding "(" and ")"&gt;
 *   token  = 1*lt;any CHAR except CTLs or tspecials&gt;
 *   tspecials  = "(" | ")" | "&lt;" | "&gt;" | "@" | "," | ";" | ":" | "\" | &lt;"&gt; | "/" | "[" | "]" | "?" | "=" | "{" | "}" | SP | HT
 * </pre>
 * <p>
 * Examples of common browser's User-Agent:<p>
 *
 * <table border="2" cellpadding="2" summary="Examples of common browsers' User-Agents">
 * <tr><td><b>Platform and Browser</b></td>
 * <td><b>User-Agent</b></td></tr>
 * <tr><td>Windows NT 4.0 w/IE 4.0</td>
 * <td>&quot;Mozilla/4.0 (compatible; MSIE 4.0; Windows NT)&quot;&nbsp;</td></tr>
 * <tr><td>Windows NT 4.0 w/IE 3.02</td>
 * <td>&quot;Mozilla/2.0 (compatible; MSIE 3.02; Windows NT)&quot;&nbsp;</td></tr>
 * <tr><td>Windows 95 w/IE 4.0</td>
 * <td>&quot;Mozilla/4.0 (compatible; MSIE 4.0; Windows 95)&quot;</td></tr>
 * <tr><td>Windows 95 w/IE 3.02</td>
 * <td>&quot;Mozilla/2.0 (compatible; MSIE 3.02; Windows 95)&quot;</td></tr>
 * <tr><td>Windows NT 3.51 w/IE 4.0</td>
 * <td>&quot;Mozilla/4.0 (compatible; MSIE 4.0; Windows 3.1)&quot;</td></tr>
 * <tr><td>Windows 3.1 w/IE 4.0</td>
 * <td>&quot;Mozilla/4.0 (compatible; MSIE 4.0; Windows 3.1)&quot;</td></tr>
 * <tr><td>Windows NT 4.0 w/Navigator 4.04</td>
 * <td>&quot;Mozilla/4.04 [en] (WinNT; I)&quot;</td></tr>
 * <tr><td>Windows NT 4.0 w/Navigator 3.04</td>
 * <td>&quot;Mozilla/3.04 (WinNT; I)&quot;</td></tr>
 * <tr><td>Windows NT 3.51 w/Navigator 4.04&nbsp;</td>
 * <td>&quot;Mozilla/4.04 [en] (WinNT; I)&quot;</td></tr>
 * <tr><td>Windows NT 3.51 w/Navigator 3.04&nbsp;</td>
 * <td>&quot;Mozilla/3.04 (WinNT; I)&quot;</td></tr>
 * <tr><td>Windows 95 w/Navigator 4.03</td>
 * <td>&quot;Mozilla/4.03 [en] (Win95; I)&quot;</td></tr>
 * <tr><td>Windows 95 w/Navigator 3.03</td>
 * <td>&quot;Mozilla/3.03 (Win95; I)&quot;</td></tr>
 * <tr><td>Solaris 2.6 w/Navigator 4.02</td>
 * <td>&quot;Mozilla/4.02 [en] (X11; l; SunOS 5.6 sun4u)&quot;</td></tr>
 * <tr><td>Windows NT 4.0 w/Navigator 6.0</td>
 * <td>&quot;Mozilla/5.0 (Windows; U; WinNT4.0; en-US; m18) Gecko/20001108 Netscape6/6.0&quot;</td></tr>
 * </table><p>
 *
 * <b>NOTE:</b> Mozilla's use of [locale] is non-standard is
 * always stripped before parsing.
 *
 * @author Brian Frank
 * @version $Revision: 5$ $Date: 4/5/05 8:30:55 AM EDT$
 * @creation 16 Nov 00
 * @since Baja 1.0
 */
public class UserAgent
{

  /**
   * Constructor parses the user-agent header value.
   */
  public UserAgent(String value)
  {
    // save away the value
    this.value = value;

    // first strip out non-standard '[locale]'
    int bs = value.indexOf('[');
    int be = value.indexOf(']');
    if (bs > 0 && be > 0)
    { value = value.substring(0, bs) + value.substring(be + 1); }

    // allocate vectors  
    Vector<Product> products = new Vector<>();
    Vector<String> comments = new Vector<>();

    // parse out comments (we only assume there 
    // is one set although there could be more 
    // strictly according to the grammar)
    int cs = value.indexOf('(');
    int ce = value.indexOf(')');
    if (cs > 0 && ce > 0)
    {
      String commentList = value.substring(cs + 1, ce);
      value = value.substring(0, cs) + value.substring(ce + 1);
      StringTokenizer st = new StringTokenizer(commentList, ";");
      while (st.hasMoreTokens())
      {
        String comment = st.nextToken().trim();
        comments.addElement(comment);

        // check for a declaration of MSIE which
        // appears as a comment, not a product
        if (comment.startsWith("MSIE"))
        {
          try
          {
            int space = comment.indexOf(' ');
            String name = comment.substring(0, space);
            String verStr = comment.substring(space + 1);
            Version ver = new Version(verStr);
            products.addElement(new Product(name, verStr, ver));
          }
          catch (RuntimeException e)
          {
            System.out.println("ERROR: Invalid MSIE User-Agent: " + comment);
          }
        }
      }
    }

    // parse out products
    StringTokenizer st = new StringTokenizer(value, " ");
    while (st.hasMoreTokens())
      products.addElement(parseProduct(st.nextToken()));

    // copy vectors into arrays
    this.products = new Product[products.size()];
    products.copyInto(this.products);
    this.comments = new String[comments.size()];
    comments.copyInto(this.comments);

    // primary product is first one in
    // list (we put MSIE into list first)
    product = this.products[0];
  }

  /**
   * Parse a product token into an instance of Product.
   */
  public static Product parseProduct(String token)
  {
    String name = token;
    String versionString = null;
    Version version = null;
    int slash = token.indexOf('/');
    if (slash > 0)
    {
      name = token.substring(0, slash);
      versionString = token.substring(slash + 1);
      try { version = new Version(versionString); } catch (RuntimeException e) {}
    }
    return new Product(name, versionString, version);
  }

  /**
   * Convenience for <code>getProduct().isIE()</code>.
   * @deprecated IE11 is no longer supported in Niagara. Will be removed in Niagara 5.0
   */
  @Deprecated
  public boolean isIE()
  {
    return getProduct().isIE();
  }

  /**
   * Convenience for <code>getProduct().isMozilla()</code>.
   */
  public boolean isMozilla()
  {
    return getProduct().isMozilla();
  }

  /**
   * Tests the user agent string to determine whether the HTTP request
   * was made by the Niagara AX Web Start application.
   *
   * @since Niagara 3.8U1
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   */
  @Deprecated
  public boolean isNiagaraAxWebStart()
  {
    return value.toLowerCase().contains(AX_WEBSTART);
  }

  /**
   * Static helper method that will inspect the user-agent header string to determine
   * whether the HTTP request was made by the Niagara AX Web Start application.
   *
   * @param req An HTTP request instance
   * @return true if the user agent header indicates the request came from the Web Start app.
   * @since Niagara 3.8U1
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   */
  @Deprecated
  public static boolean isNiagaraAxWebStart(HttpServletRequest req)
  {
    return hasUserAgent(req, AX_WEBSTART);
  }

  /**
   * Tests the user agent string to determine whether the HTTP request
   * was made by the Niagara 4 Web Start application.
   *
   * @since Niagara 4.2
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   */
  @Deprecated
  public boolean isNiagara4WebStart()
  {
    return value.toLowerCase().contains(N4_WEBSTART);
  }

  /**
   * Static helper method that will inspect the user-agent header string to determine
   * whether the HTTP request was made by the Niagara 4 Web Start application.
   *
   * @param req An HTTP request instance
   * @return true if the user agent header indicates the request came from the Web Start app.
   * @since Niagara 4.2
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   */
  @Deprecated
  public static boolean isNiagara4WebStart(HttpServletRequest req)
  {
    return hasUserAgent(req, N4_WEBSTART);
  }

  /**
   * Tests the user agent string to determine whether the HTTP request
   * was made by the Niagara Web Launcher application.
   *
   * @since Niagara 4.4 Update 3 patch (4.4.94.12.2)
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   */
  @Deprecated
  public boolean isNiagaraWebLauncher()
  {
    return value.toLowerCase().contains(NIAGARA_WEB_LAUNCHER);
  }
  /**
   * Static helper method that will inspect the user-agent header string to determine
   * whether the HTTP request was made by the Niagara Web Launcher application.
   *
   * @param req An HTTP Request instance
   * @return true if the user agent indicates the request came from Niagara Web Launcher app
   * @since Niagara 4.4 Update 3 patch (4.4.94.12.2)
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   */
  @Deprecated
  public static boolean isNiagaraWebLauncher(HttpServletRequest req)
  {
    return hasUserAgent(req, NIAGARA_WEB_LAUNCHER);
  }

  /**
   * Helper to check if the header contains the passed in user agent identifier
   *
   * @param req     An HTTP request instance
   * @param agentId The user agent identifier to check the header against
   * @return true if the the User-Agent header contains the agentId
   */
  private static boolean hasUserAgent(HttpServletRequest req, String agentId)
  {
    String header = req.getHeader("User-Agent");
    return header != null ? header.toLowerCase().contains(agentId) : false;
  }

  /**
   * Get the primary product of the user agent.  In
   * most cased this is the first product listed.
   * The exception is Microsoft IE which lists its
   * product incorrectly as a comment.
   */
  public Product getProduct()
  {
    return product;
  }

  /**
   * Get the list of all the product tokens
   * sent in the user agent header.
   */
  public Product[] getProducts()
  {
    return products;
  }

  /**
   * Get the list of parsed comments which
   * is all the tokens found inside a "(..)"
   * separated by semicolons.
   */
  public String[] getComments()
  {
    return comments;
  }

  /**
   * To string returns the unparsed header value.
   */
  public String toString()
  {
    return value;
  }

  /**
   * Dump to standard output.
   */
  public void dump()
  {
    System.out.println("User-Agent: " + value);
    System.out.println("  Primary Product: " + product);
    System.out.println("  Products:");
    for (int i = 0; i < products.length; ++i)
    { System.out.println("    " + products[i]); }
    System.out.println("  Comments:");
    for (int i = 0; i < comments.length; ++i)
    { System.out.println("    " + comments[i]); }
  }

  /**
   * The Product class stores the name an optional
   * version of a product token in a User-Agent header.
   * If the version not included, then version and
   * versionString are null.  If it is included then
   * versionString is the String value.  If the version
   * can be parsed as a Version, then version contains
   * that instance.
   */
  public static class Product
  {

    /**
     * Construct a new Product instance.
     */
    public Product(String name, String versionString, Version version)
    {
      this.name = name;
      this.versionString = versionString;
      this.version = version;
    }

    /**
     * Return true if the product name equals
     * UserAgent.MSIE = "MSIE", indicating
     * Microsoft Internet Explorer.
     * @deprecated IE11 is no longer supported in Niagara. Will be removed in Niagara 5.0
     */
    @Deprecated
    public boolean isIE() { return name.equals(MSIE); }

    /**
     * Return true if the product name equals
     * UserAgent.MOZILLA = "Mozilla", indicating
     * a Netscape product.  Versions 5.0 and
     * after indicate the open source version
     * of Netscape based on Gecko.
     */
    public boolean isMozilla() { return name.equals(MOZILLA); }

    /**
     * To string.
     */
    public String toString()
    {
      if (versionString == null)
      { return name; }
      else
      { return name + "/" + version; }
    }

    public final String name;
    public final String versionString;
    public final Version version;
  }

  public static final String MSIE = "MSIE";
  public static final String MOZILLA = "Mozilla";

  private String value;
  private Product product;
  private Product[] products;
  private String[] comments;

  private static final String AX_WEBSTART = "niagaraax/webstart";
  private static final String N4_WEBSTART = "niagara4/webstart";
  private static final String NIAGARA_WEB_LAUNCHER = "niagara/weblauncher";
}

