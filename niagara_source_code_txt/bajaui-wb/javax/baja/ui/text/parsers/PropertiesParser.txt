/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.parsers;

import javax.baja.ui.text.*;

/**
 * PropertiesParser to color code Java properties files.
 *
 * @author    Brian Frank       
 * @creation  24 Jan 02
 * @version   $Revision: 1$ $Date: 1/24/02 10:47:25 AM EST$
 * @since     Baja 1.0
 */
public class PropertiesParser
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

    // check comment
    if (c == '#')
    {
      advance();
      return newSegment(Segment.LINE_COMMENT);
    }

    // check equal
    if (c == '=')
    {
      advance();
      return newSegment(Segment.BRACKET);
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
