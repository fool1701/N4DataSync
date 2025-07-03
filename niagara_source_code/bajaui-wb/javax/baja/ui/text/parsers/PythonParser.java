/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.parsers;

import javax.baja.ui.text.*;

/**
 * TextParser to color code Python source code.
 *
 * @author    Brian Frank       
 * @creation  26 Sept 01
 * @version   $Revision: 1$ $Date: 3/18/02 1:55:05 PM EST$
 * @since     Baja 1.0
 */
public class PythonParser
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
        
    // check bracket
    int c = current;
    if (c == '{' || c == '}' || 
        c == '(' || c == ')' ||
        c == '[' || c == ']')
    {
      advance();
      return newSegment(Segment.BRACKET);
    }
    
    // check identifier
    if (Character.isJavaIdentifierStart((char)c))
    {
      Segment id = parseIdentifier();
      if (isKeyword(id))
        return new Segment(Segment.KEYWORD, Segment.MOD_WORD, id.offset, id.length);
      else
        return id;
    }
        
    // check line comment
    if (c == '#')
    {
      advance();
      return newSegment(Segment.LINE_COMMENT);
    }
    
    // check String literal
    if ((c == '"' || c == '\'') && (last != '\\' || lastLast == '\\'))
    {
      advance();
      return newSegment(Segment.STRING_DELIMITER);
    }

    // check number literal
    if (('0' <= c && c <= '9') ||
        c == '-' && ('0' <= next && next <= '9'))
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
   * Parse a java identifier, assuming that the current char 
   * is already a valid identifier staring char.
   */
  private Segment parseIdentifier()
  {
    while(Character.isJavaIdentifierPart((char)current)) advance();
    return newSegment(Segment.IDENTIFIER, Segment.MOD_WORD);
  }
  
  /**
   * Is the specified identifier segment a Java keyword.
   */
  private boolean isKeyword(Segment seg)
  {
    switch(buffer[segmentStart])
    {
      case 'a':
        if (isKeyword(seg, "and")) return true;
        if (isKeyword(seg, "assert")) return true;
        return false;
      case 'b':
        if (isKeyword(seg, "break")) return true;
        return false;
      case 'c':
        if (isKeyword(seg, "class")) return true;
        if (isKeyword(seg, "continue")) return true;
        return false;
      case 'd':
        if (isKeyword(seg, "def")) return true;
        if (isKeyword(seg, "del")) return true;
        return false;
      case 'e':
        if (isKeyword(seg, "elif")) return true;
        if (isKeyword(seg, "else")) return true;
        if (isKeyword(seg, "except")) return true;
        if (isKeyword(seg, "exec")) return true;
        return false;
      case 'f':
        if (isKeyword(seg, "finally")) return true;
        if (isKeyword(seg, "from")) return true;
        if (isKeyword(seg, "for")) return true;
        return false;
      case 'g':
        if (isKeyword(seg, "global")) return true;
        return false;
      case 'i':
        if (isKeyword(seg, "if")) return true;
        if (isKeyword(seg, "import")) return true;
        if (isKeyword(seg, "in")) return true;
        if (isKeyword(seg, "is")) return true;
        return false;
      case 'l':
        if (isKeyword(seg, "lambda")) return true;
        return false;
      case 'n':
        if (isKeyword(seg, "not")) return true;
        return false;
      case 'o':
        if (isKeyword(seg, "or")) return true;
        return false;
      case 'p':
        if (isKeyword(seg, "pass")) return true;
        if (isKeyword(seg, "print")) return true;
        return false;
      case 'r':
        if (isKeyword(seg, "raise")) return true;
        if (isKeyword(seg, "return")) return true;
        return false;
      case 't':
        if (isKeyword(seg, "try")) return true;
        return false;
      case 'w':
        if (isKeyword(seg, "while")) return true;
        return false;
      case 'y':
        if (isKeyword(seg, "yield")) return true;
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
      case '<':
      case '>':
      case '?':
        return true;
      default:
        return false;
    }
  }
                       
}
