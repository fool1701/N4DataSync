/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;

import javax.baja.gx.*;
import javax.baja.ui.*;
import javax.baja.ui.util.*;
import com.tridium.ui.*;

/**
 * FindPattern is used to encapsulate the search string and options.
 *
 * @author    Brian Frank
 * @creation  16 May 02
 * @version   $Revision: 20$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
public class FindPattern
{ 

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Get the current find pattern.
   */
  public static FindPattern getCurrent()
  {
    return current;
  }

  /**
   * Set the current find pattern.
   */
  public static void setCurrent(FindPattern pattern)
  {
    current = pattern;
  }

  /**
   * Query the user for a search pattern or return null if cancelled.
   */
  public static FindPattern query(BWidget owner)
  {                    
    UiLexicon lex = UiLexicon.bajaui();
    String title = lex.getText("commands.find.label");
    BFindPane pane = new BFindPane(owner);
    
    
    // display dialog
    if (BDialog.open(owner, title, pane, BDialog.OK |BDialog.CANCEL, icon) == BDialog.CANCEL)
      return null; 

    current = pane.save();             
    if (current.fromTop && owner instanceof BTextEditor)
      ((BTextEditor)owner).moveCaretPosition(new Position(0, 0));      
    return current;
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Create a FindPattern with the specified string and options.
   */
  public FindPattern(String string, boolean matchCase, boolean matchWord)
  {                
    this(string, matchCase, matchWord, false);
  }
  
  /**
   * Create a FindPattern with the specified string and options.
   */
  public FindPattern(String string, boolean matchCase, boolean matchWord, boolean fromTop)
  {
    this.string = string;
    this.matchCase = matchCase;                                                              
    this.matchWord = matchWord;                                                          
    this.fromTop = fromTop;
  }

////////////////////////////////////////////////////////////////
// Find
////////////////////////////////////////////////////////////////
  
  /**
   * Get the next index of the pattern in the specified 
   * text, or return -1 if no more matches.
   */
  public int findNext(String text, int start)
  {
    return find(text, start, FORWARD);                      
  }

  /**
   * Get the previous index of the pattern in the specified 
   * text, or return -1 if no more matches.
   */
  public int findPrev(String text, int start)
  {
    return find(text, start, BACKWARD);                      
  }                          
  
  /**
   * Get the next index of the pattern in the specified 
   * text, or return -1 if no more matches.
   */
  public int findNext(char[] buf, int start, int len)
  {
    return find(buf, start, len, FORWARD);                               
  }

  /**
   * Get the previous index of the pattern in the specified 
   * text, or return -1 if no more matches.
   */
  public int findPrev(char[] buf, int start, int len)
  {
    return find(buf, start, len, BACKWARD);                               
  }

  private int find(String text, int start, int dir)
  {
    if (buffer == null) buffer = new char[Math.max(64, text.length())];
    if (text.length() > buffer.length) buffer = new char[text.length()];
    text.getChars(0, text.length(), buffer, 0);
    return find(buffer, start, text.length()-start, dir);
  }
  
  private int find(char[] buf, int start, int len, int dir)
  {
    int end = start+len-string.length();
    if (end < 0 || end >= buf.length) return -1;
    
    if (dir == FORWARD)
    {
      for(int i=start; i<=end; ++i)
        if (isMatch(buf, i)) return i;
      return -1;
    }
    else
    {
      for(int i=start; i>=0; --i)
        if (isMatch(buf, i)) return i;
      return -1;
    }
    
  }
  
  private boolean isMatch(char[] buf, int index)
  {                              
    String string = this.string;
    boolean matchCase = this.matchCase;
    int strlen = string.length();
    
    if (matchWord)
    {    
      if ((index > 0)                 && isWord(buf[index-1])) return false;
      if ((index+strlen < buf.length) && isWord(buf[index+strlen])) return false;
    }
    
    for(int i=0; i<strlen; ++i)
    {
      if (matchCase)
      {
        if (buf[index+i] != string.charAt(i)) return false;
      }
      else
      {
        if (Character.toLowerCase(buf[index+i]) != 
            Character.toLowerCase(string.charAt(i))) return false;
      }
    }
    return true;  
  }
  
  private boolean isWord(char c)
  {
    return Character.isLetterOrDigit(c) || c == '_' || c == '$';
  }
  
  public String toString()
  {
    return string;
  }        

////////////////////////////////////////////////////////////////
// Replace
////////////////////////////////////////////////////////////////

  /**
   * For every occurance of a match with the specified text,
   * replace it with the replaceWith string.
   */
  public String replace(String text, String replaceWith)
  {
    char[] buf = text.toCharArray();
    return replace(buf, 0, buf.length, replaceWith);
  }
  
  /**
   * For every occurance of a match with the specified text,
   * replace it with the replaceWith string.
   */
  public String replace(char[] buf, int start, int len, String replaceWith)
  {
    StringBuilder result = new StringBuilder(len-start);
    while(true)
    {
      int x = findNext(buf, start, len-start);
      if (x < 0) break;
      if (start < x) result.append(buf, start, x-start);
      result.append(replaceWith);
      start = x + string.length();                           
    }                             
    if (start < len) result.append(buf, start, len-start);
    return result.toString();
  }                                      
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  static BImage icon = BImage.make("module://icons/x32/magnifyingGlass.png");
  static FindPattern current = new FindPattern("", false, false, false);
  static final int FORWARD  = 0;
  static final int BACKWARD = 1;

  public final String string;
  public final boolean matchCase;
  public final boolean matchWord;
  public final boolean fromTop;
  
  private char[] buffer;
  
}

