/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;

import java.util.*;

/**
 * TextParser is responsible for parsing char[] data 
 * into Line[] data.
 *
 * @author    Brian Frank       
 * @creation  9 Dec 00
 * @version   $Revision: 6$ $Date: 3/28/05 10:32:35 AM EST$
 * @since     Baja 1.0
 */
public class TextParser
  extends BTextEditor.TextSupport
{ 

////////////////////////////////////////////////////////////////
// Public 
////////////////////////////////////////////////////////////////
  
  /**
   * Parse the given character buffer into an array of Lines,
   * which are in turn parsed into their elemental Segment.
   * This method sets up the local buffer fields, and loops
   * calling nextSegment() until the end of file is reached.
   */
  public synchronized Line[] parse(char[] buffer)
  {
    // initialize the parse
    this.buffer  = buffer;
    this.length  = buffer.length;
    this.pos     = 0;
    this.last    = -1;
    this.current = (length > 0) ? buffer[0] : -1;;
    this.next    = (length > 1) ? buffer[1] : -1;
    this.segmentStart = 0;
    this.lineStart = 0;

    // loop until nextSegment returns null
    ArrayList<Line> lines = new ArrayList<>();
    ArrayList<Segment> segments = new ArrayList<>();
    while(pos < length)
    {
      Segment seg = nextSegment();
      segments.add( seg );
      if (seg.type == Segment.NEWLINE)
      {
        int len = pos-lineStart;
        if (seg.modifiers == Segment.MOD_NEWLINE_RN) len--;
        lines.add(new Line(buffer, lineStart, len, segments));
        segments.clear();
        lineStart = pos;
      }
      segmentStart = pos;
    }
    
    // ensure we got last line if not a newline
    if (segments.size() > 0)
      lines.add(new Line(buffer, lineStart, buffer.length-lineStart, segments));

    // ensure we have at least one line
    if (lines.size() == 0) lines.add( new Line(new char[0], new Segment[0]) );
    
    // return result
    return lines.toArray(new Line[lines.size()]);
  }

////////////////////////////////////////////////////////////////
// Tokenizing
////////////////////////////////////////////////////////////////
  
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
        
    // default everything else to a word
    int c = current;
    while(c != ' ' && c != '\n' && c != '\r' && c != '\t')
    {
      if (!advance()) break;
      c = current;
    }      
    return newSegment(Segment.TEXT, Segment.MOD_WORD);
  }

  /**
   * This method checks if the current character is
   * whitespace, and if so parses it into a SPACES,
   * TAB, or NEWLINE Segment.  If the current token
   * is not whitespace, then return true.
   */
  protected Segment parseWhitespace()
  {
    int c = current;
    
    // newline
    if (c == '\n' || c == '\r')
    {
      int mod = Segment.MOD_NEWLINE_N;
      int p = pos;
      if (c == '\r') 
      {
        mod = Segment.MOD_NEWLINE_R;
        buffer[p] = '\n';  // convert "\r" to "\n"
        if (next == '\n') 
        {
          advance();  // "\r\n" combo
          mod = Segment.MOD_NEWLINE_RN;
        }
      }
      advance();
      return new Segment(Segment.NEWLINE, mod, p-lineStart, 1);
    }
    
    // spaces and tabs
    if (c == ' ' || c == '\t')
    {
      int n = 1;
      while(current == ' ' || current == '\t') 
      {
        // replace tabs with one space
        if (current == '\t') buffer[pos] = ' ';
        advance();
      }
      return newSegment(Segment.SPACES);
    }
        
    // not whitespace
    return null;
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////
  
  /**
   * Allocate a new Segment using the specified type
   * code, getSegmentOffset(), and getSegmentLength().
   */
  protected final Segment newSegment(int type)
  {
    return new Segment(type, segmentStart-lineStart, pos-segmentStart);
  }

  /**
   * Allocate a new Segment using the specified type
   * code, subType code, getSegmentOffset(), and 
   * getSegmentLength().
   */
  protected final Segment newSegment(int type, int subType)
  {
    return new Segment(type, subType, segmentStart-lineStart, pos-segmentStart);
  }
  
  /**
   * Get the segment offset on the current line which
   * may be used for the Segment constructor.  This is
   * computed as <code>segmentStart-lineStart</code>.
   */
  protected final int getSegmentOffset()
  {
    return segmentStart-lineStart;
  }

  /**
   * Get the segment length on the current line which
   * may be used for the Segment constructor.  This is
   * computed as <code>pos-segmentStart</code>.
   */
  protected final int getSegmentLength()
  {
    return pos-segmentStart-1;
  }

  /**
   * Advance the position of the current buffer to the
   * next character and update the pos, last, current,
   * and next instance fields.  Return true if we advanced
   * to a valid char, or false if we advanced beyond
   * the end of the file.
   */
  protected final boolean advance()
  {
    pos++;                              
    lastLast = last;
    last = current;
    current = next;
    next = (pos+1 < length) ? buffer[pos+1] : -1;
    return current != -1;
  }
  
  /**
   * Return is the current position contains the specified
   * string.  If it does then advance to the end of the string
   * in the segment stream.
   */
  protected boolean isCurrent(String s)
  {
    int len = s.length();
    if (pos+len >= length) return false;
    for(int i=0; i<len; ++i)
      if (buffer[pos+i] != s.charAt(i)) return false;
      
    for(int i=0; i<len; ++i) advance();
    return true;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /** The buffer currently being parsed.
      This field should NOT be modified by subclasses. */
  protected char[] buffer;

  /** The current buffer's length.
      This field should NOT be modified by subclasses. */
  protected int length;

  /** Index into buffer where the current line started. 
      This field should NOT be modified by subclasses. */
  protected int lineStart;

  /** Index into buffer where the current segment started.
      This field should NOT be modified by subclasses. */
  protected int segmentStart;
  
  /** The index into buffer where currently positioned.
      This field should NOT be modified by subclasses. */
  protected int pos;

  /** The second to last character read or -1 if at position 0.
      This field should NOT be modified by subclasses. */
  protected int lastLast = -1;
  
  /** The last character read or -1 if at position 0.
      This field should NOT be modified by subclasses. */
  protected int last = -1;
  
  /** The current character at buffer[position].
      This field should NOT be modified by subclasses. */
  protected int current = -1;
  
  /** The next character in the buffer or -1 if we 
      are at the last character in the buffer.
      This field should NOT be modified by subclasses. */
  protected int next = -1;
                       
}
