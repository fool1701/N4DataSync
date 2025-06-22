/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.parsers;

import javax.baja.ui.text.*;

/**
 * TextParser to color code XML source code.
 *
 * @author    Brian Frank       
 * @creation  6 Aug 01
 * @version   $Revision: 2$ $Date: 10/18/01 8:50:42 AM EDT$
 * @since     Baja 1.0
 */
public class XmlParser
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
    int c = current;

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
      advance();
      return newSegment(Segment.BRACKET);
    }

    // check closing bracket
    if (c == '>')
    {
      advance();
      return newSegment(Segment.BRACKET);
    }
    if (c == '/' && next == '>')
    {
      advance();
      advance();
      return newSegment(Segment.BRACKET);
    }
        
    // check String literal
    if ((c == '"') || (c == '\''))
    {
      advance();
      return newSegment(Segment.STRING_DELIMITER);
    }
    
    // by default everything else is string of 
    // characters until we hit whitespace, bracket, or
    // some other symbol
    advance();
    while(true)
    {
      c = current;
      if (Character.isWhitespace((char)c)) break;
      if (isSymbol((char)c)) break;
      if (c == -1) break;
      advance();
    }
    return newSegment(Segment.TEXT, Segment.MOD_WORD);
  }
  
  private boolean isSymbol(char c)
  {
    switch(c)
    {
      case '<':
      case '>':
      case '"':
      case '\'':
      case '.':
      case ',':
      case ';':
      case ':':
      case '[':
      case ']':      
      case '!':
      case '@':
      case '*':
      case '&':
      case '(':
      case ')':
      case '-':
      case '=':
      case '+':
      case '/':
      case '\\':
        return true;
      default: 
        return false;
    }
  }
                           
}
