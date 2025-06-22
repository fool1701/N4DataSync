/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
/*
 * This source code file is public domain
 * http://sourceforge.net/projects/uxparser
 */
package javax.baja.xml;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * XParser is a very simple lightweight XML parser.  It
 * may be used as a pull parser by iterating through the
 * element and text sections of an XML stream or it may
 * be used to read an entire XML tree into memory as XElems.
 * <p>
 * XParser works in conjunction with XWriter to support plain
 * text or PKZIP documents.  This check happens automatically
 * by sniffing the first few bytes of the input stream.  If a
 * zip file is detected, the first zip entry is parsed.  Note
 * that when reading from a zip file, no guarantee is made where
 * the stream is positioned once the XML has been read.
 *
 * @author    Brian Frank on 21 Dec 01
 * @since     Baja 1.0
 */
public class XParser
{

////////////////////////////////////////////////////////////////
// Factories
////////////////////////////////////////////////////////////////

  /**
   * Make an XParser to parse the specified file.
   *
   * @param file File to parse
   * @return parser for file
   * @throws Exception if parser cannot be created
   */
  public static XParser make(File file)
    throws Exception
  {
    return make(new BufferedInputStream(new FileInputStream(file)));
  }

  /**
   * Make an XParser to parse an ASCII string.
   *
   * @param xml text to parse
   * @return parser for text
   * @throws Exception if parser cannot be created
   */
  public static XParser make(String xml)
    throws Exception
  {
    return make(new ByteArrayInputStream(xml.getBytes()));
  }

  /**
   * Make an XParser to parse XML from the specified input stream.
   *
   * @param in input stream to parse
   * @return parser for input stream
   * @throws Exception if parser cannot be created
   */
  public static XParser make(InputStream in)
    throws Exception
  {
    return new XParser(in);
  }

  /**
   * Make an XParser to parse the specified file.
   *
   * @param file File to parse
   * @param preserveWhitespace if true, then significant whitespace (whitespace between
   *                           starting and ending tags) is not discarded by the parser
   * @return parser for text
   * @throws Exception if parser cannot be created
   */
  public static XParser make(File file, boolean preserveWhitespace)
    throws Exception
  {
    return make(new BufferedInputStream(new FileInputStream(file)), preserveWhitespace);
  }


  /**
   * Make an XParser to parse an ASCII string.
   * @param xml text to parse
   * @param preserveWhitespace if true, then significant whitespace (whitespace between
   *                           starting and ending tags) is not discarded by the parser
   * @return parser for text
   * @throws Exception if parser cannot be created
   */
  public static XParser make(String xml, boolean preserveWhitespace)
    throws Exception
  {
    return make(new ByteArrayInputStream(xml.getBytes()), preserveWhitespace);
  }

  /**
   * Make an XParser to parse XML from the specified input stream
   * @param in input stream to parse
   * @param preserveWhitespace if true, then significant whitespace (whitespace between
   *                           starting and ending tags) is not discarded by the parser
   * @return parser for input stream
   * @throws Exception if parser cannot be created
   */
  public static XParser make(InputStream in, boolean preserveWhitespace)
    throws Exception
  {
    return new XParser(in, preserveWhitespace);
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   *
   * @param in input stream to parse
   * @throws IOException if in cannot be opened
   */
  protected XParser(InputStream in)
    throws IOException
  {
    this.in = new XInputStreamReader(in);
    this.preserveWhitespace = false;
  }

  /**
   * Private constructor
   *
   * @param in input stream to parse
   * @param preserveWhitespace if true, then significant whitespace (whitespace between
   *                           starting and ending tags) is not discarded by the parser
   * @throws IOException if in cannot be opened
   */
  protected XParser(InputStream in, boolean preserveWhitespace)
    throws IOException
  {
    this.in = new XInputStreamReader(in);
    this.preserveWhitespace = preserveWhitespace;
  }

////////////////////////////////////////////////////////////////
// "DOM" API
////////////////////////////////////////////////////////////////

  /**
   * Get the character encoding of the underlying input stream.
   *
   * @return character encoding of underlying input stream
   * @throws IOException if encoding detection fails
   */
  public String getEncoding()
    throws IOException
  {
    return in.getEncoding();
  }

  /**
   * Return if the stream was zipped.
   *
   * @return true if the underlying input stream is zipped
   * @throws IOException if zipped detection fails
   */
  public boolean isZipped()
    throws IOException
  {
    return in.isZipped();
  }

  /**
   * Convenience for {@code parse(true)}.
   *
   * @return XElem of the first element in the document
   * @throws Exception if parsing fails
   */
  public final XElem parse()
    throws Exception
  {
    return parse(true);
  }

  /**
   * Parse the entire next element into memory as a tree
   * of XElems and optionally close the underlying input
   * stream.
   *
   * @param close whether to close the input stream after parsing
   * @return XElem of the first element in the document
   * @throws Exception if parsing fails
   */
  public final XElem parse(boolean close)
    throws Exception
  {
    if (next() != ELEM_START)
    {
      if (close) close();
      throw error("Expecting element start");
    }

    return parseCurrent(close);
  }

  /**
   * Convenience for {@code parseCurrent(false)}.
   *
   * @return XElem of the current element in the document
   * @throws Exception if parsing fails
   */
  public final XElem parseCurrent()
    throws Exception
  {
    return parseCurrent(false);
  }

  /**
   * Parse the entire current element into memory as a tree
   * of XElems and optionally close the underlying input
   * stream.
   *
   * @param close whether to close the input stream after parsing
   * @return XElem of the current element in the document
   * @throws Exception if parsing fails
   */
  public XElem parseCurrent(boolean close)
    throws Exception
  {
    try
    {
      int depth = 1;
      XElem root = elem().copy();
      XElem cur = root;
      while(depth > 0)
      {
        int type = next();
        if (type == ELEM_START)
        {
          XElem oldCur = cur;
          cur = elem().copy();
          oldCur.addContent(cur);
          depth++;
        }
        else if (type == ELEM_END)
        {
          cur = cur.parent();
          depth--;
        }
        else if (type == TEXT)
        {
          cur.addContent(text().copy());
        }
        else if (type == EOF)
        {
          throw new EOFException();
        }
      }
      return root;
    }
    finally
    {
      if (close) close();
    }
  }

////////////////////////////////////////////////////////////////
// Pull API
////////////////////////////////////////////////////////////////

  /**
   * Advance the parser to the next node and return the node type.
   * Return the current node type: ELEM_START, ELEM_END, or TEXT.
   * If no more data to parse then return EOF.
   *
   * @return type of the current node
   * @throws Exception if parsing fails
   */
  public final int next()
    throws Exception
  {
    if (popStack)
    {
      popStack = false;
      pop();
    }

    if (emptyElem)
    {
      emptyElem = false;
      popStack = true; // pop stack on next call to next()
      return type = ELEM_END;
    }

    while(true)
    {
      int c;
      try
      {
        c = read();
      }
      catch(EOFException e)
      {
        return type = EOF;
      }

      // markup
      if (c == '<')
      {
        c = read();

        // comment, CDATA, or DOCType
        if (c == '!')
        {
          c = read();
          if (c == '-')
          {
            c = read();
            if (c != '-') throw error("Expecting comment");
            skipComment();
            continue;
          }
          else if (c == '[')
          {
            consume("CDATA[");
            parseCDATA();
            return type = TEXT;
          }
          else if (c == 'D')
          {
             consume("OCTYPE");
             skipDocType();
             continue;
          }
          throw error("Unexpected markup");
        }

        // processor instruction
        else if (c == '?')
        {
          skipPI();
          continue;
        }

        // element end
        else if (c == '/')
        {
          parseElemEnd();
          popStack = true;  // pop stack on next call to next()
          return type = ELEM_END;
        }

        // must be element start
        else
        {
          parseElemStart(c);
          return type = ELEM_START;
        }
      }

      // char data
      if (!parseText(c) && !preserveWhitespace) continue;
      return type = TEXT;
    }
  }

  /**
   * Convenience for {@code skip(depth())}.
   *
   * @throws Exception if parsing fails
   */
  public void skip()
    throws Exception
  {
    skip(depth);
  }

  /**
   * Skip parses all the content until reaching the end tag
   * of the specified depth.  When this method returns, the
   * next call to {@code next()} will return the element
   * or text immediately following the end tag.
   *
   * @param toDepth depth of elements to skip to
   * @throws Exception if parsing fails
   */
  public void skip(int toDepth)
    throws Exception
  {
    while(true)
    {
      if (type == ELEM_END && depth == toDepth) return;
      int type = next();
      if (type == EOF) throw new EOFException();
    }
  }

  /**
   * Get the current node type constant which is always the
   * result of the last call to next().  This constant may be
   * ELEM_START, ELEM_END, TEXT, or EOF.
   *
   * @return current node type
   */
  public final int type()
  {
    return type;
  }

  /**
   * Get the depth of the current element with the document
   * root being a depth of one.  A depth of 0 indicates
   * a position before or after the root element.
   *
   * @return current node depth
   */
  public final int depth()
  {
    return depth;
  }

  /**
   * Get the current element if {@code type()} is ELEM_START or
   * ELEM_END.  If {@code type()} is TEXT then this is the parent
   * element of the current character data.  After ELEM_END this XElem
   * instance is no longer valid and will be reused for further
   * processing.  If depth is zero return null.
   *
   * @return the current element
   */
  public final XElem elem()
  {
    if (depth < 1) return null;
    return stack[depth-1];
  }

  /**
   * Get the at the current depth.  Depth must be between 0 and
   * {@code depth()} inclusively.  Calling {@code elem(0)}
   * will return the root element and {@code elem(depth()-1)}
   * returns the current element.  If depth is invalid, return null.
   *
   * @param depth depth
   * @return the element at depth
   */
  public final XElem elem(int depth)
  {
    if (depth < 0 || depth >= this.depth) return null;
    return stack[depth];
  }

  /**
   * If the current type is TEXT return the XText instance used to
   * store the character data.  After a call to {@code next()}
   * this XText instance is no longer valid and will be reused for
   * further processing.  If the current type is not TEXT then
   * return null.
   *
   * @return current element as Text
   */
  public final XText text()
  {
    if (type == TEXT) return text;
    return null;
  }

  /**
   * Get current line number.
   *
   * @return current line number, if available
   */
  public final int line()
  {
    return line;
  }

  /**
   * Get current column number.
   *
   * @return current column number, if available
   */
  public final int column()
  {
    return col;
  }

  /**
   * Get whether the last element was empty
   *
   * @return true if the last element was empty, false otherwise
   */
  public final boolean emptyElem() { return emptyElem; }

  /**
   * Close the underlying input stream.
   */
  public final void close()
  {
    try { in.close(); } catch(IOException e) {}
  }

////////////////////////////////////////////////////////////////
// Parse Utils
////////////////////////////////////////////////////////////////

  /**
   * Parse a [40] element start production.  We are passed
   * the first character after the &lt; (beginning of name).
   *
   * @param c first character after start of element
   * @throws Exception if parsing fails
   */
  private void parseElemStart(int c)
    throws Exception
  {
    // get our next XElem onto stack to reuse
    XElem elem = push();

    // prefix / name
    parseQName(c);
    elem.name = name;
    elem.line = line;
    String prefix = this.prefix;
    boolean resolveAttrNs = false;

    // attributes
    while(true)
    {
      boolean sp = skipSpace();
      c = read();
      if (c == '>')
      {
        break;
      }
      else if (c == '/')
      {
        c = read();
        if (c != '>') throw error("Expecting /> empty element");
        emptyElem = true;
        break;
      }
      else
      {
        if (!sp) throw error("Expecting space before attribute");
        resolveAttrNs |= parseAttr(c, elem);
      }
    }

    // after reading all the attributes, now it is safe to
    // resolve prefixes into their actual XNs instances;
    // first resolve the element itself...
    if (prefix == null)
      elem.ns = defaultNs;
    else
      elem.ns = prefixToNs(prefix);

    // resolve attribute prefixes (optimize to short circuit if
    // no prefixes were specified since that is the common case)...
    if (resolveAttrNs)
    {
      for(int i=0; i<elem.attrSize; ++i)
        if (elem.attr[i*3+1] != null)
          elem.attr[i*3+1] = prefixToNs((String)elem.attr[i*3+1]);
    }
  }

  /**
   * Parse an element end production.  Next character
   * should be first char of element name.
   *
   * @throws Exception if parsing fails
   */
  private void parseElemEnd()
    throws Exception
  {
    // prefix / name
    parseQName(read());
    XNs ns = null;
    if (prefix == null)
      ns = defaultNs;
    else
      ns = prefixToNs(prefix);

    // get end element
    if (depth == 0) throw error("Element end without start");
    XElem elem = stack[depth-1];

    // verify
    if (!elem.name.equals(name) || elem.ns != ns)
      throw error("Expecting end of element '" + elem.qname() + "'[" + elem.line + "]");

    skipSpace();
    if (read() != '>')
      throw error("Expecting > end of element");
  }

  /**
   * Parse a [41] attribute production.  We are passed
   * the first character of the attribute name.  Return
   * if the attribute had a namespace prefix.
   *
   * @param c first character of element name
   * @param elem elem
   * @return true if attribute had namespace prefix
   * @throws Exception if parsing fails
   */
  private boolean parseAttr(int c, XElem elem)
    throws Exception
  {
    // prefix / name
    parseQName(c);
    String prefix = this.prefix;
    String name = this.name;

    // Eq [25] production
    skipSpace();
    if (read() != '=') throw error("Expecting '='");
    skipSpace();

    // String literal
    c = read();
    if (c != '"' && c != '\'') throw error("Expecting quoted attribute value");
    String value = parseString(c);

    // check namespace declaration "xmlns" or "xmlns:prefix"
    if (prefix == null)
    {
      if (name.equals("xmlns"))
      {
        pushNs(elem, "", value);
      }
    }
    else
    {
      if (prefix.equals("xmlns"))
      {
        pushNs(elem, name, value);
        prefix = null;
        name = "xmlns:" + name;
      }
      else if (prefix.equalsIgnoreCase("xml"))
      {
        prefix = null;
        name = "xml:" + name;
      }
    }

    // add attribute using raw prefix string - we
    // will resolve later in parseElemStart
    elem.addAttrImpl(prefix, name, value);
    return prefix != null;
  }

  /**
   * Parse an element or attribute name of the
   * format {@code [<prefix>:]name} and store result in
   * prefix and name fields.
   *
   * @param c first character of name
   * @throws Exception if parsing fails
   */
  private void parseQName(int c)
    throws Exception
  {
    prefix = null;
    name = parseName(c);

    c = read();
    if (c == ':')
    {
      prefix = name;
      name = parseName(read());
    }
    else
    {
      pushback = c;
    }
  }

  /**
   * Parse a string literal token "..." or '...'
   *
   * @param quote quote character
   * @return String literal
   * @throws Exception if parsing fails
   */
  private String parseString(int quote)
    throws Exception
  {
    XText buf = this.buf;
    buf.setLength(0);
    int c;
    while((c = read()) != quote)
      buf.append(toCharData(c));
    return bufToString();
  }

  /**
   * Parse an XML name token.
   *
   * @param c character
   * @return XML name
   * @throws Exception if parsing fails
   */
  private String parseName(int c)
    throws Exception
  {
    if (!isName(c))
      throw error("Expected XML name");

    XText buf = this.buf;
    buf.setLength(0);
    buf.append(c);
    while(isName(c = read()))
      buf.append(c);
    pushback = c;

    return bufToString();
  }

  /**
   * Parse a CDATA section.
   *
   * @throws Exception if parsing fails
   */
  private void parseCDATA()
    throws Exception
  {
    XText text = this.text;
    text.length = 0;
    text.cdata = true;

    int c2 = -1, c1 = -1, c0 = -1;
    while(true)
    {
      c2 = c1;
      c1 = c0;
      c0 = read();
      if (c2 == ']' && c1 == ']' && c0 == '>')
      {
        text.setLength(text.length-2);
        return;
      }
      text.append(c0);
    }
  }

  /**
   * Parse a character data text section.  Return
   * false if all the text was whitespace only.
   *
   * @param c starting character
   * @return false if all text was whitespace
   * @throws Exception if parsing fails
   */
  private boolean parseText(int c)
    throws Exception
  {
    XText text = this.text;
    text.length = 0;
    text.cdata = false;
    text.append(toCharData(c));
    boolean gotText = !isSpace(c);

    while(true)
    {
      try
      {
        c = read();
      }
      catch(EOFException e)
      {
        if (gotText) throw e;
        return false;
      }

      if (c == '<')
      {
        pushback = c;
        return gotText;
      }

      if (!isSpace(c)) gotText = true;
      text.append(toCharData(c));
    }
  }

////////////////////////////////////////////////////////////////
// Skip Utils
////////////////////////////////////////////////////////////////

  /**
   * Skip [3] Space = ' ' '\n' '\r' '\t'
   * Return true if one or more space chars found.
   *
   * @return true if space was skipped
   * @throws Exception if parsing fails
   */
  private boolean skipSpace()
    throws Exception
  {
    int c = read();
    if (!isSpace(c))
    {
      pushback = c;
      return false;
    }

    while(isSpace(c = read()));
    pushback = c;
    return true;
  }

  /**
   * Skip [15] Comment := <!-- ... -->
   *
   * @throws Exception if parsing fails
   */
  private void skipComment()
    throws Exception
  {
    int c2 = -1, c1 = -1, c0 = -1;
    while(true)
    {
      c2 = c1;
      c1 = c0;
      c0 = read();
      if (c2 == '-' && c1 == '-')
      {
        if (c0 != '>') throw error("Cannot have -- in middle of comment");
        return;
      }
    }
  }

  /**
   * Skip [16] PI := <? ... ?>
   *
   * @throws Exception if parsing fails
   */
  private void skipPI()
    throws Exception
  {
    int c1 = -1, c0 = -1;
    while(true)
    {
      c1 = c0;
      c0 = read();
      if (c1 == '?' && c0 == '>') return;
    }
  }

  /**
   * Skip [28] DocType := <!DOCTYPE ... >
   *
   * @throws Exception if parsing fails
   */
  private void skipDocType()
    throws Exception
  {
    int depth = 1;
    while(true)
    {
      int c = read();
      if (c == '<') depth++;
      if (c == '>') depth--;
      if (depth == 0) return;
    }
  }

////////////////////////////////////////////////////////////////
// Consume Utils
////////////////////////////////////////////////////////////////

  /**
   * Read from the stream and verify that the next
   * characters match the specified String.
   *
   * @param s String to match
   * @throws Exception if parsing fails or if s was not pulled from stream
   */
  private void consume(String s)
    throws Exception
  {
    int len = s.length();
    for(int i=0; i<len; ++i)
      if (read() != s.charAt(i))
        throw error("Expected '" + s + "'");
  }

////////////////////////////////////////////////////////////////
// Read
////////////////////////////////////////////////////////////////

  /**
   * Read the next character from the stream:
   *  - handle pushbacks
   *  - updates the line and col count
   *  - normalizes line breaks
   *  - throw EOFException if end of stream reached
   *
   * @return next character from stream
   * @throws Exception if read fails
   */
  private int read()
    throws Exception
  {
    int c;

    // check pushback
    c = pushback;
    if (c != -1) { pushback = -1; return c; }

    // read the next character
    c = in.read();
    if (c < 0) throw new EOFException();

    // update line:col and normalize line breaks (2.11)
    if (c == '\n')
    {
      line++; col=0;
      return '\n';
    }
    else if (c == '\r')
    {
      int lookAhead = in.read();
      if (lookAhead != '\n') pushback = lookAhead;
      line++; col=0;
      return '\n';
    }
    else
    {
      col++;
      return c;
    }
  }

  /**
   * Read the specified char is the amp (&) then resolve
   * the entity otherwise just return the char.  If the
   * character is markup then throw an exception.
   *
   * @param c character to map
   * @return mapped character
   * @throws Exception if parsing fails
   */
  private int toCharData(int c)
    throws Exception
  {
    if (c == '<')
      throw error("Invalid markup in char data");

    if (c != '&') return c;

    // The data is specified as an HTML entity. We have to translate
    // the specified value to its standard character representation.
    // For more information, see:
    //
    //   https://www.w3schools.com/html/html_entities.asp
    //
    // As an example:
    //
    //   &#xc4;
    //
    // Should become:
    //
    //   Ä
    //
    // And:
    //
    //   &lt
    //
    // Should become:
    //
    //   <
    //

    c = read();

    if (c == '#')
    {
      // Handle &#_; and &#x_; (internationalization)

      // Read the next character, it is either the 'x' character, meaning
      // the entity code is specified in hexadecimal, or a numeric (in decimal)
      // of the entity code itself.
      c = in.read(); col++;

      // Start with zero value for resulting character
      int x = 0;

      // Default to decimal entity specification
      int base = 10;
      if (c == 'x')
      {
        // Use hexadecimal
        base = 16;
      }
      else
      {
        // Value is specified in decimal, convert value to character data
        x = toNum(x, c, base);
      }

      // Read the next character
      c = in.read(); col++;

      // Continue until we encounter the ; terminator
      while(c != ';')
      {
        // Read the next part of the entity in the determined base
        x = toNum(x, c, base);

        // Continue the loop
        c = in.read(); col++;
      }

      // Return the character value of this entity
      return (char)x;
    }

    // Handle all others (<,>,&,",')

    XText ebuf = this.entityBuf;
    ebuf.setLength(0);
    ebuf.append(c);
    while((c = read()) != ';') ebuf.append(c);
    String entity = ebuf.string().intern();

    if (entity == "lt") return '<';
    if (entity == "gt") return '>';
    if (entity == "amp") return '&';
    if (entity == "quot") return '"';
    if (entity == "apos") return '\'';

    throw error("Unsupported entity &" + entity + ";");
  }

  private int toNum(int x, int c, int base)
    throws Exception
  {
    x = x*base;
    if ('0' <= c && c <= '9') return x + (c - '0');
    else if (base == 16)
    {
      if ('a' <= c && c <= 'f') return x + 10 + (c - 'a');
      else if ('A' <= c && c <= 'F') return x + 10 + (c - 'A');
    }
    throw error("Expected base " + base + " number");
  }

  private String bufToString()
  {
    if (buf.length == 1)
    {
      int ch = buf.data[0];
      if (' ' <= ch && ch < 128) return internCache[ch];
    }
    return buf.string();
  }

////////////////////////////////////////////////////////////////
// Namespace Scoping
////////////////////////////////////////////////////////////////

  /**
   * Map the prefix string to a XNs instance declared
   * in the current element or ancestor element.
   *
   * @param prefix prefix for namespace
   * @return namespace
   */
  private XNs prefixToNs(String prefix)
  {
    for(int i=depth-1; i>=0; --i)
    {
      XNs[] ns = nsStack[i];
      if (ns == null) continue;
      for(int j=0; j<ns.length; ++j)
        if (ns[j].prefix.equals(prefix))
        {
          return ns[j];
        }
    }
    throw new XException("Undeclared namespace prefix '" + prefix + "'");
  }

  /**
   * Push a namespace onto the stack at the current depth.
   *
   * @param elem element
   * @param prefix namespace prefix
   * @param value namespace uri
   */
  private void pushNs(XElem elem, String prefix, String value)
  {
    // make ns instance
    XNs ns = new XNs(prefix, value);
    ns.declaringElem = elem;

    // update defaultNs
    if (prefix == "")
    {
      if (value.equals(""))
        defaultNs = null;
      else
        defaultNs = ns;
    }

    // update stack
    XNs[] list = nsStack[depth-1];
    if (list == null)
    {
      list = new XNs[] { ns };
    }
    else
    {
      XNs[] temp = new XNs[list.length+1];
      System.arraycopy(list, 0, temp, 0, list.length);
      temp[list.length] = ns;
      list = temp;
    }
    nsStack[depth-1] = list;
  }

  /**
   * Recalculate what the default namespace should be
   * because we just popped the element that declared
   * the default namespace last.
   */
  private void reEvalDefaultNs()
  {
    defaultNs = null;
    for(int i=depth-1; i>=0; --i)
    {
      XNs[] ns = nsStack[i];
      if (ns != null)
      {
        for(int j=0; j<ns.length; ++j)
        {
          if (ns[j].isDefault())
          {
            if (!ns[j].uri.equals("")) defaultNs = ns[j];
            return;
          }
        }
      }
    }
  }

////////////////////////////////////////////////////////////////
// Stack
////////////////////////////////////////////////////////////////

  /**
   * Push a new XElem on the stack.  The stack itself
   * only allocates a new XElem the first time a given
   * depth is reached.  Further pushes at that depth
   * will always reuse the last XElem from the given
   * depth.
   *
   * @return new XElem
   */
  private XElem push()
  {
    // attempt to reuse element from given depth
    XElem elem = stack[depth];

    // allocate instance if necessary
    if (elem == null)
      elem = stack[depth] = new XElem();

    // increase stack size
    depth++;

    // init element and return
    elem.clearAttr();
    return elem;
  }

  /**
   * Pop decreases the element depth, but leaves the
   * actual element in the stack for reuse.  However
   * we do need to re-evaluate our namespace scope if
   * the popped element declared namespaces.
   */
  private void pop()
  {
    depth--;

    //XElem elem = stack[depth];

    XNs[] ns = nsStack[depth];
    if (ns != null)
    {
      nsStack[depth] = null;
      reEvalDefaultNs();
    }
  }

////////////////////////////////////////////////////////////////
// Error
////////////////////////////////////////////////////////////////

  /**
   * Make an XException using with current line and column.
   *
   * @param msg error message
   * @return new XException
   */
  private XException error(String msg)
  {
    return new XException(msg, line, col);
  }

////////////////////////////////////////////////////////////////
// Test
////////////////////////////////////////////////////////////////

  /*
  public static long mem()
  {
    return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
  }

  public static void main(String[] args)
    throws Exception
  {
    long t1 = System.currentTimeMillis();
    System.out.println("start mem  " + (mem()/1024) + "kb");
    XElem e = XParser.make(new File(args[0])).parse();
    long t2 = System.currentTimeMillis();
    System.out.println("finish mem " + (mem()/1024) + "kb " + (t2-t1) + "ms");
  }
  */

////////////////////////////////////////////////////////////////
// Pull API Constants
////////////////////////////////////////////////////////////////

  /** Indicates end of file  (or input stream) */
  public static final int EOF        = -1;
  /** Indicates parser currently on element start. */
  public static final int ELEM_START = 1;
  /** Indicates parser currently on element end. */
  public static final int ELEM_END   = 2;
  /** Indicates parser currently at character data. */
  public static final int TEXT       = 3;

////////////////////////////////////////////////////////////////
// Char Map
////////////////////////////////////////////////////////////////

  static boolean isName(int c)  { return (c < 128) ? (charMap[c] & CT_NAME)  != 0 : true; }
  static boolean isSpace(int c) { return (c < 128) ? (charMap[c] & CT_SPACE) != 0 : false; }

  private static final byte[] charMap = new byte[128];
  private static final int CT_SPACE = 0x01;
  private static final int CT_NAME  = 0x02;
  static
  {
    for(int i='a'; i<='z'; ++i) charMap[i] = CT_NAME;
    for(int i='A'; i<='Z'; ++i) charMap[i] = CT_NAME;
    for(int i='0'; i<='9'; ++i) charMap[i] = CT_NAME;
    charMap['-'] = CT_NAME;
    charMap['.'] = CT_NAME;
    charMap['_'] = CT_NAME;

    charMap['\n'] = CT_SPACE;
    charMap['\r'] = CT_SPACE;
    charMap[' ']  = CT_SPACE;
    charMap['\t'] = CT_SPACE;
  }

////////////////////////////////////////////////////////////////
// String Cache
////////////////////////////////////////////////////////////////

  private static final String[] internCache = new String[128];
  static
  {
    for(int i=' '; i<128; ++i)
      internCache[i] = new String(new char[] { (char)i }).intern();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private XInputStreamReader in;
  private int pushback = -1;
  private int line = 1;
  private int col;
  private int type;
  private XText text = new XText();
  private int depth;
  private XElem[] stack = new XElem[256];
  private XNs[][] nsStack = new XNs[256][];
  private XNs defaultNs;
  private XText buf = new XText();        // working string buffer
  private XText entityBuf = new XText();  // working string buffer
  private String name;        // result of parseQName()
  private String prefix;      // result of parseQName()
  private boolean popStack;   // used for next event
  private boolean emptyElem;  // used for next event
  private final boolean preserveWhitespace; // preserve significant whitespace

}
