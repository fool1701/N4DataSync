/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

import javax.baja.xml.XException;
import javax.baja.xml.XWriter;

import javax.baja.nre.util.TextUtil;

import org.owasp.encoder.Encode;

/**
 * HtmlWriter is used to generate HTML code with common tags.
 *
 * @author    Brian Frank
 * @creation  28 Feb 03
 * @version   $Revision: 10$ $Date: 6/25/08 1:56:14 PM EDT$
 * @since     Niagara 3.0
 */
public class HtmlWriter
  extends PrintWriter
{ 

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct for specified writer.
   */
  public HtmlWriter(Writer out)
  {
    super(out);
  }

  /**
   * Construct for specified output stream.
   */
  public HtmlWriter(OutputStream out)
  {
    super(out);
  }

////////////////////////////////////////////////////////////////
// Public
////////////////////////////////////////////////////////////////

  /**
   * Println always uses "\n" for newline.
   */
  @Override
  public void println(String s)
  {
    print(s);
    print('\n');
    flush();
  }

  /**
   * Write the specified Object and return this.
   */
  public HtmlWriter w(Object o) { print(o); return this; }

  /**
   * Write the specified boolean and return this.
   */
  public final HtmlWriter w(boolean b) { print(b); return this; }

  /**
   * Write the specified char and return this.
   */
  public final HtmlWriter w(char c) { print(c); return this; }

  /**
   * Write the specified int and return this.
   */
  public final HtmlWriter w(int i) { print(i); return this; }

  /**
   * Write the specified long and return this.
   */
  public final HtmlWriter w(long l) { print(l); return this; }

  /**
   * Write the specified float and return this.
   */
  public final HtmlWriter w(float f) { print(f); return this; }

  /**
   * Write the specified double and return this.
   */
  public final HtmlWriter w(double d) { print(d); return this; }

  /**
   * Write a non-breaking space character and return this.
   * @since Niagara 3.4
   */
  public final HtmlWriter nbsp() { print("&nbsp;"); return this; }

  /**
   * Write a newline character and return this.
   */
  public final HtmlWriter nl() { print('\n'); return this; }

  /**
   * Write the specified number of spaces.
   */
  public final HtmlWriter indent(int indent)
  {
    return w(TextUtil.getSpaces(indent));
  }
  
  /**
   * Write an attribute pair {@code name='value'}
   * where the value is written using safe().
   */
  public final HtmlWriter attr(String name, String value)
  {
    return w(name).w('=').w('\'').safe(value).w('\'');
  }

  /**
   * Convenience for {@code attr(name, Integer.toString(value))}.
   */
  public final HtmlWriter attr(String name, int value)
  {
    return attr(name, Integer.toString(value));
  }
  
  /**
   * Convenience for {@code attr(name, Float.toString(value))}.
   */
  public final HtmlWriter attr(String name, float value)
  {
    return attr(name, Float.toString(value));
  }
  
  /**
   * Convenience for {@code attr(name, Double.toString(value))}.
   */
  public final HtmlWriter attr(String name, double value)
  {
    return attr(name, Double.toString(value));
  }
  /**
   * Convenience for {@code attr(name, String.valueOf(value))}.
   */
  public final HtmlWriter attr(String name, Object value)
  {
    return attr(name, String.valueOf(value));
  }  
  
////////////////////////////////////////////////////////////////
// Safe
////////////////////////////////////////////////////////////////  

  /**
   * Convenience for {@code XWriter.safe(this, s, escapeWhitespace)}.
   */
  public final HtmlWriter safe(String s, boolean escapeWhitespace)
  {                 
    try
    {
      XWriter.safe(this, s, escapeWhitespace);
      return this;
    }
    catch(IOException e)
    {
      throw new XException(e.toString(), e);
    }
  }

  /**
   * Convenience for {@code XWriter.safe(this, s, true)}.
   */
  public final HtmlWriter safe(Object s)
  {   
    try
    {
      XWriter.safe(this, String.valueOf(s), true);
      return this;
    }
    catch(IOException e)
    {
      throw new XException(e.toString(), e);
    }
  }

  /**
   * Convenience for {@code XWriter.safe(this, c, escapeWhitespace)}.
   */
  public final HtmlWriter safe(int c, boolean escapeWhitespace)
  {
    try
    {
      XWriter.safe(this, c, escapeWhitespace);
      return this;
    }
    catch(IOException e)
    {
      throw new XException(e.toString(), e);
    }
  }

////////////////////////////////////////////////////////////////
// Anchor
////////////////////////////////////////////////////////////////  

  /**
   * Use this method to sanitize all hrefs used in 
   * anchors, images, and other urls.  The default
   * implementation returns the specified parameter.
   */
  public String href(String href)
  {
    return href;
  }

  /**
   * Write an anchor tag with the specified href and text
   * between the open and close anchor elements.
   */
  public HtmlWriter a(String href, Object body)
  {
    return w(createLink(href, body));
  }

  /**
   * Create an {@code <a>} tag with the specified href and body. The href and
   * body will be HTML-sanitized.
   * @since Niagara 4.10
   */
  public String createLink(String href, Object body)
  {
    return "<a href='" + href(href) + "'>" + Encode.forHtml(String.valueOf(body)) + "</a>";
  }

  /**
   * Convenience for {@code a(href, href)}.
   */
  public HtmlWriter a(String href)
  {
    return a(href, href);
  }

////////////////////////////////////////////////////////////////
// Table
////////////////////////////////////////////////////////////////  

  /**
   * Write a table start tag.
   */
  public HtmlWriter startTable(boolean border)
  {
    w("<table");
    if (border) { w(" border='1' cellspacing='0'"); }
    w(">\n");
    return this;
  }

  /**
   * Write a table row with the specified colspan that
   * can be used as a title header to separate rows.
   */
  public HtmlWriter trTitle(Object title, int colspan)
  {
    return tr().w("<th nowrap='true' colspan='").w(colspan).w("' bgcolor='#d0d0d0'>").w(title).endTh().endTr();
  }

  /**
   * Write a table header column using the predefined
   * formatting of {@code trTitle()}.
   */
  public HtmlWriter thTitle(Object title)
  {
    return thTitle().w(title).endTh();
  }

  /**
   * Write a table end tag.
   */
  public HtmlWriter endTable()
  {
    return w("</table>\n");
  }

  /**
   * Write a table row with one td column.
   */
  public HtmlWriter tr(Object c0)
  {
    return tr().td(c0).endTr();
  }

  /**
   * Write a table row with two td columns.
   */
  public HtmlWriter tr(Object c0, Object c1)
  {
    return tr().td(c0).td(c1).endTr();
  }

  /**
   * Write a table row with three td columns.
   */
  public HtmlWriter tr(Object c0, Object c1, Object c2)
  {
    return tr().td(c0).td(c1).td(c2).endTr();
  }

  /**
   * Write a table row with four td columns.
   */
  public HtmlWriter tr(Object c0, Object c1, Object c2, Object c3)
  {
    return tr().td(c0).td(c1).td(c2).td(c3).endTr();
  }

  /**
   * Write a table row with five td columns.
   */
  public HtmlWriter tr(Object c0, Object c1, Object c2, Object c3, Object c4)
  {
    return tr().td(c0).td(c1).td(c2).td(c3).td(c4).endTr();
  }

  /**
   * Write a table row with six td columns.
   */
  public HtmlWriter tr(Object c0, Object c1, Object c2, Object c3, Object c4, Object c5)
  {
    return tr().td(c0).td(c1).td(c2).td(c3).td(c4).td(c5).endTr();
  }

  /**
   * Write a table row with seven td columns.
   */
  public HtmlWriter tr(Object c0, Object c1, Object c2, Object c3, Object c4, Object c5, Object c6)
  {
    return tr().td(c0).td(c1).td(c2).td(c3).td(c4).td(c5).td(c6).endTr();
  }
  
  /**
   * Write a th tag with align=left and nowrap.
   */
  public HtmlWriter th(Object s)
  {
    return th().w(s).endTh();
  }

  /**
   * Write a td tag with align=left and nowrap.
   */
  public HtmlWriter td(Object s)
  {
    return td().w(s).endTd();
  }

  /**
   * Starts a new {@code <th>} element to contain the table title. Remember to
   * call {@code endTh()} after writing its contents.
   * @since Niagara 4.10
   */
  public HtmlWriter thTitle()
  {
    return w("<th align='left' nowrap='true' bgcolor='#d0d0d0'>");
  }

  /**
   * Starts a new {@code <th>} element for a table header. Remember to call
   * {@code endTh()} after writing its contents.
   * @since Niagara 4.10
   */
  public HtmlWriter th()
  {
    return w("<th align='left' nowrap='true'>");
  }

  /**
   * Ends an open {@code <th>} element.
   * @since Niagara 4.10
   */
  public HtmlWriter endTh()
  {
    return w("</th>\n");
  }

  /**
   * Starts a new {@code <tr>} element. Remember to call {@code endTr()} after
   * writing its contents.
   * @since Niagara 4.10
   */
  public HtmlWriter tr()
  {
    return w("<tr>");
  }

  /**
   * Ends an open {@code <tr>} element.
   * @since Niagara 4.10
   */
  public HtmlWriter endTr()
  {
    return w("</tr>\n");
  }

  /**
   * Starts a new {@code <td>} element. Remember to call {@code endTd()} after
   * writing its contents.
   * @since Niagara 4.10
   */
  public HtmlWriter td()
  {
    return w("<td align='left' nowrap='true'>");
  }

  /**
   * Ends an open {@code <td>} element.
   * @since Niagara 4.10
   */
  public HtmlWriter endTd()
  {
    return w("</td>");
  }
}
