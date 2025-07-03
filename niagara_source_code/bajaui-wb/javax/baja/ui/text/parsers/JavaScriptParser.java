/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.parsers;

import javax.baja.ui.text.*;

/**
 * TextParser to color code JavaScipt source code.
 *
 * @author    Brian Frank       
 * @creation  6 Aug 01
 * @version   $Revision: 2$ $Date: 1/24/05 10:56:34 AM EST$
 * @since     Baja 1.0
 */
public class JavaScriptParser
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
    if (c == '/' && next == '/')
    {
      advance();
      advance();
      return newSegment(Segment.LINE_COMMENT);
    }
    
    // check multi-line comment start
    if (c == '/' && next == '*')
    {
      advance();
      advance();
      int mods = Segment.MOD_NON_JAVADOC;
      if (current == '*') { advance(); mods = 0; }
      return newSegment(Segment.MULTI_LINE_COMMENT_START, mods);
    }

    // check multi-line comment end
    if (c == '*' && next == '/')
    {
      advance();
      advance();
      return newSegment(Segment.MULTI_LINE_COMMENT_END);
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
        if (isKeyword(seg, "abstract")) return true;
        return false;
      case 'b':
        if (isKeyword(seg, "boolean")) return true;
        if (isKeyword(seg, "break")) return true;
        if (isKeyword(seg, "byte")) return true;
        return false;
      case 'c':
        if (isKeyword(seg, "case")) return true;
        if (isKeyword(seg, "catch")) return true;
        if (isKeyword(seg, "char")) return true;                   
        if (isKeyword(seg, "class")) return true;
        if (isKeyword(seg, "continue")) return true;
        if (isKeyword(seg, "const")) return true;
        return false;
      case 'd':
        if (isKeyword(seg, "debugger")) return true;
        if (isKeyword(seg, "default")) return true;
        if (isKeyword(seg, "delete")) return true;
        if (isKeyword(seg, "do")) return true;
        if (isKeyword(seg, "double")) return true;
        return false;
      case 'e':
        if (isKeyword(seg, "enum")) return true;
        if (isKeyword(seg, "else")) return true;
        if (isKeyword(seg, "extends")) return true;
        return false;
      case 'f':
        if (isKeyword(seg, "false")) return true;
        if (isKeyword(seg, "final")) return true;
        if (isKeyword(seg, "finally")) return true;
        if (isKeyword(seg, "float")) return true;
        if (isKeyword(seg, "for")) return true;
        if (isKeyword(seg, "function")) return true;
        return false;
      case 'g':
        if (isKeyword(seg, "goto")) return true;
        return false;
      case 'i':
        if (isKeyword(seg, "if")) return true;
        if (isKeyword(seg, "in")) return true;
        if (isKeyword(seg, "implements")) return true;
        if (isKeyword(seg, "import")) return true;
        if (isKeyword(seg, "instanceof")) return true;
        if (isKeyword(seg, "int")) return true;
        if (isKeyword(seg, "interface")) return true;
        return false;
      case 'l':
        if (isKeyword(seg, "long")) return true;
        return false;
      case 'n':
        if (isKeyword(seg, "native")) return true;
        if (isKeyword(seg, "new")) return true;
        if (isKeyword(seg, "null")) return true;
        return false;
      case 'p':
        if (isKeyword(seg, "package")) return true;
        if (isKeyword(seg, "private")) return true;
        if (isKeyword(seg, "protected")) return true;
        if (isKeyword(seg, "public")) return true;
        return false;
      case 'r':
        if (isKeyword(seg, "return")) return true;
        return false;
      case 's':
        if (isKeyword(seg, "short")) return true;
        if (isKeyword(seg, "static")) return true;
        if (isKeyword(seg, "super")) return true;
        if (isKeyword(seg, "switch")) return true;
        if (isKeyword(seg, "synchronized")) return true;
        return false;
      case 't':
        if (isKeyword(seg, "this")) return true;
        if (isKeyword(seg, "throw")) return true;
        if (isKeyword(seg, "throws")) return true;
        if (isKeyword(seg, "transient")) return true;
        if (isKeyword(seg, "true")) return true;
        if (isKeyword(seg, "try")) return true;
        if (isKeyword(seg, "typeof")) return true;
        return false;
      case 'v':
        if (isKeyword(seg, "var")) return true;
        if (isKeyword(seg, "void")) return true;
        if (isKeyword(seg, "volatile")) return true;
        return false;
      case 'w':
        if (isKeyword(seg, "while")) return true;
        if (isKeyword(seg, "with")) return true;
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
