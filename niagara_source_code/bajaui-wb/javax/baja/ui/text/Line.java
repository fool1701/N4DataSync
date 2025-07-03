/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;

import java.io.*;
import java.util.*;

import javax.baja.gx.*;

/**
 * Line stores the character buffer of a logical line
 * in a document.  A Line is composed of a char[] which 
 * stores the individual chars and a Segment[] with the 
 * Line's segment pointers.  Each segment models a run of
 * characters in the buffer that gets rendered uniquely.
 * Although the underlying char buffer and segments are 
 * publicly exposed, the Line class should be treated as 
 * immutable, and never modified once created.
 *
 * @author    Brian Frank       
 * @creation  6 Aug 01
 * @version   $Revision: 13$ $Date: 8/12/01 1:49:25 PM EDT$
 * @since     Baja 1.0
 */
public class Line
{ 

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  
  
  /**
   * Construct a new Line with the specified character
   * data, and parsed Segment runs.  The arrays passed
   * should be immutable and never be changed once passed
   * to the Line constructor.
   */
  public Line(char[] buffer, Segment[] segments)
  {
    this.buffer = buffer;
    this.segments = segments;
  }
  
  /**
   * Construct a new Line from the specified mutable buffer
   * and segments List, by allocating local copies.
   */
  public Line(char[] buffer, int offset, int length, List<Segment> segments)
  {
    this.buffer = new char[length];
    System.arraycopy(buffer, offset, this.buffer, 0, length);
    this.segments = segments.toArray(new Segment[segments.size()]);
  }
  
  /**
   * Construct a Line which is a join of the two specified
   * lines.  Line1 must not end with a newline segment.
   */
  public Line(Line line1, Line line2)
  {
    if (line1.segments[line1.segments.length-1].type == Segment.NEWLINE)
      throw new IllegalArgumentException();
    
    this.buffer = new char[line1.buffer.length + line2.buffer.length];
    this.segments = new Segment[line1.segments.length + line2.segments.length];
    
    System.arraycopy(line1.buffer, 0, buffer, 0, line1.buffer.length);
    System.arraycopy(line2.buffer, 0, buffer, line1.buffer.length, line2.buffer.length);
    System.arraycopy(line1.segments, 0, segments, 0, line1.segments.length);
    System.arraycopy(line2.segments, 0, segments, line1.segments.length, line2.segments.length);    
  }
  
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Get the number of logical columns in this line.
   */
  public int getColumnCount()
  {
    return buffer.length;
  }

  /**
   * Get the Segment at the specified column, or null
   * if the column is out of bounds for this line.
   */
  public Segment getSegmentAt(int col)
  {
    int index = getSegmentIndexAt(col);
    if (index < 0) return null;
    else return segments[index];
  }
  
  /**
   * Get the Segment at the specified column, or -1
   * if the column is out of bounds for this line.
   */
  public int getSegmentIndexAt(int col)
  {
    if (col < 0 || col > buffer.length) return -1;
    for(int i=0; i<segments.length; ++i)
      if (col < segments[i].offset+segments[i].length) 
        return i;
    return -1;
  }

  /**
   * Does this line end with a newline character.
   */
  public boolean endsWithNewline()
  {
    int len = buffer.length;
    if (len == 0) return false;
    return buffer[len-1] == '\n';
  }
  
  /**
   * Get the length of the line in logical columns
   * without a newline character.
   */
  public int getColumnCountWithoutNewline()
  {
    int len = buffer.length;
    if (len == 0) return 0;
    return (buffer[len-1] == '\n') ? len-1 : len;
  }
    
  /**
   * Return the width of the line using the specified
   * font.
   */
  public double getWidth(BFont font)
  {
    return font.width(buffer, 0, buffer.length);
  }
  
  public double getWidth(BFont font, int startChar, int endChar) 
  {
    if (endChar > buffer.length - 1) 
    {
      if (buffer.length == 0)
        endChar = 0;
      else
        endChar = buffer.length - 1;
    }
    return font.width(buffer, startChar, endChar);
  }
  
  /**
   * To string.
   */
  public String toString()
  {
    return "Line: " + new String(buffer); 
  }

////////////////////////////////////////////////////////////////
// Package Private
////////////////////////////////////////////////////////////////     

  /**
   * If this line ends with a new line, then return
   * a new Line with the newline segment stripped off.
   * Otherwise return this line.
   */
  Line stripNewline()
  {
    if (!endsWithNewline()) return this;
    
    char[] newBuf = new char[buffer.length-1];
    System.arraycopy(buffer, 0, newBuf, 0, newBuf.length);
      
    Segment[] newSeg = new Segment[segments.length-1];
    System.arraycopy(segments, 0, newSeg, 0, newSeg.length);
      
    return new Line(newBuf, newSeg);
  }
  
  /**
   * Write this line to the PrintWriter taking into 
   * consideration the newline segment type.
   */
  void write(Writer out)
    throws IOException
  {
    if (buffer.length == 0) return;
    
    Segment last = segments[segments.length-1];
    if (last.type == Segment.NEWLINE)
    {
      out.write(buffer, 0, buffer.length-1);
      if (last.isNewlineR()) out.write('\r');
      else if (last.isNewlineRN()) out.write("\r\n");
      else out.write('\n');
    }
    else
    {
      out.write(buffer, 0, buffer.length);
    }
  }
  
  /**
   * Add spaces as needed so that we have as many
   * columns as specified.
   */
  char[] toCharArrayGrowAsNeeded(int col)
  {
    char[] buf = buffer;
    if (col < getColumnCountWithoutNewline()) return buf;
    
    // we need to do this differently depending
    // on whether the line trails with a newline,
    // because inserted space is still inserted
    // before the newline
    if (endsWithNewline())
    {
      char[] grow = new char[col+1];
      System.arraycopy(buf, 0, grow, 0, buf.length-1);
      for(int i=buf.length-1; i<col; ++i) grow[i] = ' ';
      grow[col] = '\n';
      return grow;
    }
    else
    {
      char[] grow = new char[col];
      System.arraycopy(buf, 0, grow, 0, buf.length);
      for(int i=buf.length; i<col; ++i) grow[i] = ' ';
      return grow;
    }
  }
     
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
 
  /**
   * This is the character buffer containing the chars 
   * which compose the line.  Every line except the last 
   * line MUST end with "\n".  Other newline combinations 
   * such as "\r" and "\r\n" are not supported and must be 
   * parsed out before constructing a Line.  This array
   * must never be modified!
   */ 
  public final char[] buffer;
  
  /**
   * This is the array containing the line's segement series.
   * Each segment provides an offset and length into the char[] 
   * buffer.  Each segment also has a type and subType attribute
   * which is used by the renderer to provide color coding.
   */
  public final Segment[] segments;
  
}
