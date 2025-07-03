/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.parsers;

import javax.baja.ui.text.*;

/**
 * TextParser to color code HTML source code.
 *
 * @author    Brian Frank       original 
 * @author    Danny Wahlquist       
 * @creation  23 May 02
 * @version   $Revision: 3$ $Date: 3/30/05 3:43:50 PM EST$
 * @since     Baja 1.0
 */
public class HtmlParser
  extends TextParser
{ 
  
  /**
   * Parse the next segment from the buffer.  When this
   * method is called the value of pos is on the first
   * character of the segment.  When this method returns 
   * the value of pos should be the first character AFTER 
   * the segment.  Use the advance() to move to the next
   * character.
   */
  protected Segment nextSegment()
  {
    // check whitespace
    Segment whitespace = parseWhitespace();
    if (whitespace != null) return whitespace;
        
    // check identifier
    int c = current;
    if (Character.isJavaIdentifierStart((char)c))
    {
      Segment id = parseIdentifier();
      if (inTag && isKeyword(id))
        return new Segment(Segment.KEYWORD, Segment.MOD_WORD, id.offset, id.length);
      else
        return id;
    }

    // check multi-line comment start
    if (c == '<' && next == '!' && isCurrent("<!--"))
      return newSegment(Segment.MULTI_LINE_COMMENT_START);

    // check multi-line comment end
    if (c == '-' && next == '-' && isCurrent("-->"))
      return newSegment(Segment.MULTI_LINE_COMMENT_END);

    // check opening bracket
    if (c == '<')
    {
      if (next == '/') advance();  
      inTag = true;
      advance();
      return newSegment(Segment.BRACKET);
    }

    // check closing bracket
    if (c == '>')
    {
      inTag = false;
      advance();
      return newSegment(Segment.BRACKET);
    }
    if (c == '/' && next == '>')
    {
      inTag = false;
      advance();
      advance();
      return newSegment(Segment.BRACKET);
    }

    // check String literal
    if (inTag && ((c == '"' || c == '\'') && (last != '\\' || lastLast == '\\')))
    {
      advance();
      return newSegment(Segment.STRING_DELIMITER);
    }

    // check number literal
    if (inTag && (('0' <= c && c <= '9') ||
        c == '-' && ('0' <= next && next <= '9')))
      return parseNumberLiteral();
    
    // by default everything else is must be some weird 
    // symbol combination (usually operators) that we treat 
    // as a single text block until we get some interesting
    // break point
    advance();
    while(isSymbol(current))
      if (!advance()) break;
    return newSegment(Segment.TEXT);
  }
  
  /**
   * Parse a HTML identifier, assuming that the current char 
   * is already a valid identifier staring char.
   */
  private Segment parseIdentifier()
  {
    while(Character.isJavaIdentifierPart((char)current)) advance();
    return newSegment(Segment.IDENTIFIER, Segment.MOD_WORD);
  }
  
  /**
   * Is the specified identifier segment an HTML keyword.
   */
  private boolean isKeyword(Segment seg)
  { 
    switch(buffer[segmentStart])
    {
      case 'a':
        if (isKeyword(seg, "a")) return true;
        if (isKeyword(seg, "abbr")) return true;
        if (isKeyword(seg, "above")) return true;
        if (isKeyword(seg, "acronym")) return true;
        if (isKeyword(seg, "address")) return true;
        if (isKeyword(seg, "applet")) return true;
        if (isKeyword(seg, "array")) return true;
        if (isKeyword(seg, "area")) return true;
        if (isKeyword(seg, "abbr")) return true;
        if (isKeyword(seg, "accept-charset")) return true;
        if (isKeyword(seg, "accept")) return true;
        if (isKeyword(seg, "accesskey")) return true;
        if (isKeyword(seg, "action")) return true;
        if (isKeyword(seg, "align")) return true;
        if (isKeyword(seg, "alink")) return true;
        if (isKeyword(seg, "alt")) return true;
        if (isKeyword(seg, "archive")) return true;
        if (isKeyword(seg, "axis")) return true;
        return false;
      case 'b':
        if (isKeyword(seg, "b")) return true;
        if (isKeyword(seg, "base")) return true;
        if (isKeyword(seg, "basefont")) return true;
        if (isKeyword(seg, "bdo")) return true;
        if (isKeyword(seg, "bbsound")) return true;
        if (isKeyword(seg, "big")) return true;
        if (isKeyword(seg, "blink")) return true;
        if (isKeyword(seg, "blockquote")) return true;
        if (isKeyword(seg, "body")) return true;
        if (isKeyword(seg, "br")) return true;
        if (isKeyword(seg, "button")) return true;
        if (isKeyword(seg, "background")) return true;
        if (isKeyword(seg, "behavior")) return true;
        if (isKeyword(seg, "below")) return true;
        if (isKeyword(seg, "bgcolor")) return true;
        if (isKeyword(seg, "border")) return true;
        return false;
      case 'c':
        if (isKeyword(seg, "caption")) return true;
        if (isKeyword(seg, "center")) return true;
        if (isKeyword(seg, "cite")) return true;
        if (isKeyword(seg, "code")) return true;
        if (isKeyword(seg, "col")) return true;
        if (isKeyword(seg, "colgroup")) return true;
        if (isKeyword(seg, "command")) return true;
        if (isKeyword(seg, "comment")) return true;
        if (isKeyword(seg, "cellpadding")) return true;
        if (isKeyword(seg, "cellspacing")) return true;
        if (isKeyword(seg, "char")) return true;
        if (isKeyword(seg, "charoff")) return true;
        if (isKeyword(seg, "charset")) return true;
        if (isKeyword(seg, "checked")) return true;
        if (isKeyword(seg, "cite")) return true;
        if (isKeyword(seg, "class")) return true;
        if (isKeyword(seg, "classid")) return true;
        if (isKeyword(seg, "clear")) return true;
        if (isKeyword(seg, "codebase")) return true;
        if (isKeyword(seg, "codetype")) return true;
        if (isKeyword(seg, "color")) return true;
        if (isKeyword(seg, "cols")) return true;
        if (isKeyword(seg, "colspan")) return true;
        if (isKeyword(seg, "compact")) return true;
        if (isKeyword(seg, "content")) return true;
        if (isKeyword(seg, "coords")) return true;
        return false;
      case 'd':
        if (isKeyword(seg, "dd")) return true;
        if (isKeyword(seg, "del")) return true;
        if (isKeyword(seg, "dfn")) return true;
        if (isKeyword(seg, "dir")) return true;
        if (isKeyword(seg, "div")) return true;
        if (isKeyword(seg, "dl")) return true;
        if (isKeyword(seg, "dt")) return true;
        if (isKeyword(seg, "data")) return true;
        if (isKeyword(seg, "datetime")) return true;
        if (isKeyword(seg, "declare")) return true;
        if (isKeyword(seg, "defer")) return true;
        if (isKeyword(seg, "disabled")) return true;
        return false;
      case 'e':
        if (isKeyword(seg, "em")) return true;
        if (isKeyword(seg, "embed")) return true;
        if (isKeyword(seg, "envar")) return true;
        if (isKeyword(seg, "enctype")) return true;
        return false;
      case 'f':
        if (isKeyword(seg, "fieldset")) return true;
        if (isKeyword(seg, "filename")) return true;
        if (isKeyword(seg, "fig")) return true;
        if (isKeyword(seg, "font")) return true;
        if (isKeyword(seg, "form")) return true;
        if (isKeyword(seg, "frame")) return true;
        if (isKeyword(seg, "frameset")) return true;
        if (isKeyword(seg, "face")) return true;
        if (isKeyword(seg, "for")) return true;
        if (isKeyword(seg, "frameborder")) return true;
        if (isKeyword(seg, "framespacing")) return true;
        return false;
      case 'h':
        if (isKeyword(seg, "h1")) return true;
        if (isKeyword(seg, "h2")) return true;
        if (isKeyword(seg, "h3")) return true;
        if (isKeyword(seg, "h4")) return true;
        if (isKeyword(seg, "h5")) return true;
        if (isKeyword(seg, "h6")) return true;
        if (isKeyword(seg, "head")) return true;
        if (isKeyword(seg, "hr")) return true;
        if (isKeyword(seg, "html")) return true;
        if (isKeyword(seg, "headers")) return true;
        if (isKeyword(seg, "height")) return true;
        if (isKeyword(seg, "hidden")) return true;
        if (isKeyword(seg, "href")) return true;
        if (isKeyword(seg, "hreflang")) return true;
        if (isKeyword(seg, "hspace")) return true;
        if (isKeyword(seg, "http-equiv")) return true;
        return false;
      case 'i':
        if (isKeyword(seg, "i")) return true;
        if (isKeyword(seg, "iframe")) return true;
        if (isKeyword(seg, "ilayer")) return true;
        if (isKeyword(seg, "img")) return true;
        if (isKeyword(seg, "input")) return true;
        if (isKeyword(seg, "ins")) return true;
        if (isKeyword(seg, "isindex")) return true;
        if (isKeyword(seg, "id")) return true;
        if (isKeyword(seg, "ismap")) return true;
        return false;
      case 'l':
        if (isKeyword(seg, "label")) return true;
        if (isKeyword(seg, "layer")) return true;
        if (isKeyword(seg, "legend")) return true;
        if (isKeyword(seg, "li")) return true;
        if (isKeyword(seg, "link")) return true;
        if (isKeyword(seg, "listing")) return true;
        if (isKeyword(seg, "lang")) return true;
        if (isKeyword(seg, "language")) return true;
        if (isKeyword(seg, "loop")) return true;
        if (isKeyword(seg, "longdesc")) return true;
        return false;
      case 'm':
        if (isKeyword(seg, "map")) return true;
        if (isKeyword(seg, "marquee")) return true;
        if (isKeyword(seg, "menu")) return true;
        if (isKeyword(seg, "meta")) return true;
        if (isKeyword(seg, "multicol")) return true;
        if (isKeyword(seg, "mailto")) return true;
        if (isKeyword(seg, "marginheight")) return true;
        if (isKeyword(seg, "marginwidth")) return true;
        if (isKeyword(seg, "maxlength")) return true;
        if (isKeyword(seg, "media")) return true;
        if (isKeyword(seg, "method")) return true;
        if (isKeyword(seg, "multiple")) return true;
        return false;
      case 'n':
        if (isKeyword(seg, "nextid")) return true;
        if (isKeyword(seg, "nobr")) return true;
        if (isKeyword(seg, "noframes")) return true;
        if (isKeyword(seg, "nolayer")) return true;
        if (isKeyword(seg, "note")) return true;
        if (isKeyword(seg, "noscript")) return true;
        if (isKeyword(seg, "name")) return true;
        if (isKeyword(seg, "nohref")) return true;
        if (isKeyword(seg, "noresize")) return true;
        if (isKeyword(seg, "noshade")) return true;
        return false;
      case 'o':
        if (isKeyword(seg, "object")) return true;
        if (isKeyword(seg, "ol")) return true;
        if (isKeyword(seg, "option")) return true;
        if (isKeyword(seg, "optgroup")) return true;
        if (isKeyword(seg, "object")) return true;
        if (isKeyword(seg, "onblur")) return true;
        if (isKeyword(seg, "onchange")) return true;
        if (isKeyword(seg, "onfocus")) return true;
        if (isKeyword(seg, "onkeydown")) return true;
        if (isKeyword(seg, "onkeypress")) return true;
        if (isKeyword(seg, "onkeyup")) return true;
        if (isKeyword(seg, "onload")) return true;
        if (isKeyword(seg, "onreset")) return true;
        if (isKeyword(seg, "onselect")) return true;
        if (isKeyword(seg, "onsubmit")) return true;
        if (isKeyword(seg, "onunload")) return true;
        if (isKeyword(seg, "onclick")) return true;
        if (isKeyword(seg, "ondblclick")) return true;
        if (isKeyword(seg, "onmousedown")) return true;
        if (isKeyword(seg, "onmousemove")) return true;
        if (isKeyword(seg, "onmouseout")) return true;
        if (isKeyword(seg, "onmouseover")) return true;
        if (isKeyword(seg, "onmouseup")) return true;
        return false;
      case 'p':
        if (isKeyword(seg, "p")) return true;
        if (isKeyword(seg, "param")) return true;
        if (isKeyword(seg, "pre")) return true;
        if (isKeyword(seg, "profile")) return true;
        if (isKeyword(seg, "prompt")) return true;
        return false;
      case 'q':
        if (isKeyword(seg, "q")) return true;
        if (isKeyword(seg, "quote")) return true;
        return false;
      case 'r':
        if (isKeyword(seg, "range")) return true;
        if (isKeyword(seg, "root")) return true;
        if (isKeyword(seg, "readonly")) return true;
        if (isKeyword(seg, "rel")) return true;
        if (isKeyword(seg, "rev")) return true;
        if (isKeyword(seg, "rows")) return true;
        if (isKeyword(seg, "rowspan")) return true;
        if (isKeyword(seg, "rules")) return true;
        return false;
      case 's':
        if (isKeyword(seg, "s")) return true;
        if (isKeyword(seg, "samp")) return true;
        if (isKeyword(seg, "script")) return true;
        if (isKeyword(seg, "select")) return true;
        if (isKeyword(seg, "small")) return true;
        if (isKeyword(seg, "sound")) return true;
        if (isKeyword(seg, "spacer")) return true;
        if (isKeyword(seg, "span")) return true;
        if (isKeyword(seg, "sqrt")) return true;
        if (isKeyword(seg, "strike")) return true;
        if (isKeyword(seg, "strong")) return true;
        if (isKeyword(seg, "style")) return true;
        if (isKeyword(seg, "sub")) return true;
        if (isKeyword(seg, "sup")) return true;
        if (isKeyword(seg, "scheme")) return true;
        if (isKeyword(seg, "scope")) return true;
        if (isKeyword(seg, "scrolling")) return true;
        if (isKeyword(seg, "selected")) return true;
        if (isKeyword(seg, "shape")) return true;
        if (isKeyword(seg, "size")) return true;
        if (isKeyword(seg, "span")) return true;
        if (isKeyword(seg, "src")) return true;
        if (isKeyword(seg, "standby")) return true;
        if (isKeyword(seg, "start")) return true;
        if (isKeyword(seg, "summary")) return true;
        return false;
      case 't':
        if (isKeyword(seg, "table")) return true;
        if (isKeyword(seg, "tbody")) return true;
        if (isKeyword(seg, "td")) return true;
        if (isKeyword(seg, "text")) return true;
        if (isKeyword(seg, "textarea")) return true;
        if (isKeyword(seg, "tfoot")) return true;
        if (isKeyword(seg, "th")) return true;
        if (isKeyword(seg, "thead")) return true;
        if (isKeyword(seg, "title")) return true;
        if (isKeyword(seg, "tr")) return true;
        if (isKeyword(seg, "tt")) return true;
        if (isKeyword(seg, "tabindex")) return true;
        if (isKeyword(seg, "target")) return true;
        if (isKeyword(seg, "topmargin")) return true;
        if (isKeyword(seg, "type")) return true;
        return false;
      case 'u':
        if (isKeyword(seg, "u")) return true;
        if (isKeyword(seg, "ul")) return true;
        if (isKeyword(seg, "url")) return true;
        if (isKeyword(seg, "usemap")) return true;
        return false;
      case 'v':
        if (isKeyword(seg, "var")) return true;
        if (isKeyword(seg, "valign")) return true;
        if (isKeyword(seg, "value")) return true;
        if (isKeyword(seg, "valuetype")) return true;
        if (isKeyword(seg, "version")) return true;
        if (isKeyword(seg, "vlink")) return true;
        if (isKeyword(seg, "vspace")) return true;
        return false;
      case 'w':
        if (isKeyword(seg, "wbr")) return true;
        if (isKeyword(seg, "width")) return true;
        return false;
      case 'x':
        if (isKeyword(seg, "xmp")) return true;
        return false;
    }
    return false;
  }

  private boolean isKeyword(Segment seg, String s)
  {
    int len = seg.length;
    if (len != s.length()) return false;
    
    char[] buf = buffer;
    int start = segmentStart;
    for(int i=1; i<len; ++i)
      if (s.charAt(i) != buf[start+i]) return false;
      
    return true;
  }

  /**
   * Parse a number literal.
   */
  private Segment parseNumberLiteral()
  {
    advance();
    
    // handle hex numbers
    if (last == '0' && (current == 'x' || current == 'X'))
      advance();
      
    while(true)
    {
      int c = current;
      if (('0' <= c && c <= '9') ||    // digits
          ('a' <= c && c <= 'f') ||    // hex, float, double, exp
          ('A' <= c && c <= 'F') ||    // ""
          (c == 'l') || (c == 'L') ||  // long
          (c == '.') ||                // decimal
          (c == '-' && last == 'e'))   // e-XX
        advance();
      else
        break;            
    }
    
    return newSegment(Segment.NUMBER_LITERAL, Segment.MOD_WORD);
  }
  
  /**
   * Return true if the specified character is some symbol 
   * that we keeps us from breaking up a symbol run.
   */
  private boolean isSymbol(int c)
  {
    // possible new tokens:  alpha, digit, /*, //, 
    //   */, -x, (), {}, []
    switch(c)
    {
      case '~':
      case '!':
      case '@':
      case '#':
      case '%':
      case '^':
      case '&':
      case '_':
      case '+':
      case '=':
      case '|':
      case '\\':
      case ':':
      case ';':
      case '.':
      case ',':
      case '?':
        return true;
      default:
        return false;
    }
  }
  
  boolean inTag;   // between < and > 
}
