/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;

/**
 * Segment is a run of characters in a Line which is
 * rendered individually by a TextRenderer.  A Segment
 * doesn't store any characters itself, but rather stores
 * a start index and length into a Line's char[] buffer.
 *
 * @author    Brian Frank       
 * @creation  6 Jul 01
 * @version   $Revision: 5$ $Date: 9/30/01 7:07:29 PM EDT$
 * @since     Baja 1.0
 */
public class Segment
{ 

////////////////////////////////////////////////////////////////
// Segment Types
////////////////////////////////////////////////////////////////

  public static final int TEXT                     = 0;
  public static final int SPACES                   = 1;
  public static final int TAB                      = 2;
  public static final int NEWLINE                  = 3;
  public static final int NUMBER_LITERAL           = 4;
  public static final int IDENTIFIER               = 5;
  public static final int KEYWORD                  = 6;
  public static final int PREPROCESSOR             = 7;
  public static final int BRACKET                  = 8;
  public static final int STRING_DELIMITER         = 9;
  public static final int LINE_COMMENT             = 10;
  public static final int MULTI_LINE_COMMENT_START = 11;
  public static final int MULTI_LINE_COMMENT_END   = 12;

  private static final String[] typeStrings =
  {
    "text",                     // 0
    "spaces",                   // 1
    "tab",                      // 2
    "newline",                  // 3
    "numberLiteral",            // 4
    "keyword",                  // 6
    "identifier",               // 7
    "preprocessor",             // 8
    "brackets",                 // 9
    "stringDelimiter",          // 10
    "lineComment",              // 11
    "multiLineCommentStart",    // 12
    "multiLineCommentEnd",      // 13
  };

////////////////////////////////////////////////////////////////
// Segment Modifiers
////////////////////////////////////////////////////////////////
  
  public static final int MOD_NEWLINE_N             = 0x000001;
  public static final int MOD_NEWLINE_R             = 0x000002;
  public static final int MOD_NEWLINE_RN            = 0x000004;
  public static final int MOD_WORD                  = 0x000008;
  public static final int MOD_NON_JAVADOC           = 0x000010;
  public static final int MOD_IN_STRING_LITERAL     = 0x000020;
  public static final int MOD_IN_LINE_COMMENT       = 0x000040;
  public static final int MOD_IN_MULTI_LINE_COMMENT = 0x000080;
  public static final int MOD_IN_NON_JAVADOC        = 0x000100;

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
    
  /**
   * Construct a new Segement with the specified type,
   * offset, and length.  The subType defaults to zero.
   */
  public Segment(int type, int offset, int length)
  {
    this.type      = type;
    this.modifiers = 0;
    this.offset    = offset;
    this.length    = length;
  }

  /**
   * Construct a new Segement with the specified type, 
   * modifiers, offset, and length.
   */
  public Segment(int type, int modifiers, int offset, int length)
  {
    this.type      = type;
    this.modifiers = modifiers;
    this.offset    = offset;
    this.length    = length;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Return if this segment is a whitepace type.
   */
  public boolean isWhitespace()
  {
    return type == NEWLINE ||
           type == SPACES ||
           type == TAB;
  }

////////////////////////////////////////////////////////////////
// Modifiers
////////////////////////////////////////////////////////////////

  /**
   * This is a NEWLINE segment that ends with a "\n".
   */
  public final boolean isNewlineN()
  {
    return (modifiers & MOD_NEWLINE_N) != 0;
  }
  
  /**
   * This is a NEWLINE segment that ends with a "\r".
   */
  public final boolean isNewlineR()
  {
    return (modifiers & MOD_NEWLINE_R) != 0;
  }

  /**
   * This is a NEWLINE segment that ends with a "\r\n".
   */
  public final boolean isNewlineRN()
  {
    return (modifiers & MOD_NEWLINE_RN) != 0;
  }

  /**
   * Is this segment considered a word when it comes
   * to commands which are word based such as WordLeft,
   * WordRight, and DeleteWord.
   */
  public final boolean isWord()
  {
    return (modifiers & MOD_WORD) != 0;
  }

  /**
   * If the current segment is a multi-line comment start, 
   * this flag indicates if it is not a Javadoc comment.
   */
  public final boolean isNonJavadoc()
  {
    return (modifiers & MOD_NON_JAVADOC) != 0;
  }
  
  /**
   * Is this segment currently inside a string literal.
   */
  public final boolean isInStringLiteral()
  {
    return (modifiers & MOD_IN_STRING_LITERAL) != 0;
  }

  /**
   * Is this segment currently in a line comment.
   */
  public final boolean isInLineComment()
  {
    return (modifiers & MOD_IN_LINE_COMMENT) != 0;
  }

  /**
   * Is this segment currently in a multiline comment.
   */
  public final boolean isInMultiLineComment()
  {
    return (modifiers & MOD_IN_MULTI_LINE_COMMENT) != 0;
  }

  /**
   * If this segment is inside a multi-line comment, 
   * this returns if the start segment has the MOD_JAVADOC 
   * modififer set.
   */
  public final boolean isInNonJavadoc()
  {
    return (modifiers & MOD_IN_NON_JAVADOC) != 0;
  }
  
////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////
  
  /**
   * To string.
   */
  public String toString()
  {
    String t = (type >= 0 && type < typeStrings.length) ?
               typeStrings[type] : String.valueOf(type);
    StringBuilder s = new StringBuilder();
    if (isInLineComment()) s.append("// ");
    else if (isInMultiLineComment()) s.append("/*");
    else s.append("   ");
    s.append(" [").append(t);
    if (modifiers != 0) s.append(",0x").append(Integer.toHexString(modifiers));
    s.append("] ");
    s.append(offset).append(',').append(length);
    return s.toString();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * This is one of the predefined type code constants.
   */
  public final int type;

  /**
   * The modifiers allows the user to configure color coding 
   * using a finer granuality than just type.  A good 
   * example is using modifiers to distinguish between Java's 
   * "slash/star/star" multiline Javadoc comments and the 
   * normal C "slash/star" comments.  Modifier constants
   * are defined as MOD_x.  Although this field is not
   * final, it should not be modified.
   */
  public int modifiers;

  /**
   * This is the zero based column index into the 
   * Line's char[] buffer.
   */
  public final int offset;
  
  /**
   * This is the number of characters the segment 
   * run emcompasses in the Line's char[] buffer.
   */
  public final int length;
  
}
