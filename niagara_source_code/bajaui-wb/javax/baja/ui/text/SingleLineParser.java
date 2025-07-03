/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;


/**
 * The SingleLineParser is used to insure that no
 * matter what kind of multi-line data is attempted
 * to insert into a BTextField, only one line without
 * a newline is returned.
 *
 * @author    Brian Frank       
 * @creation  5 Aug 01
 * @version   $Revision: 1$ $Date: 8/12/01 12:14:39 PM EDT$
 * @since     Baja 1.0
 */
public class SingleLineParser
  extends TextParser
{ 
  
  /**
   * Parse into an array of lines.
   */
  public Line[] parse(char[] buf)
  {    
    // parse normally
    Line[] lines = super.parse(buf);
    
    // make sure only one line
    if (lines.length > 1)
      lines = new Line[] { lines[0] };
    
    // insure our single line doesn't contain a newline
    lines[0] = lines[0].stripNewline();
      
    return lines;
  }

  /*
  // This parses all lines as a single segment, but
  // doesn't seem to provide much of a performance improvment
  public Line[] parse(char[] buf)
  {
    // make sure no newlines
    int n = 0;
    while(n < buf.length)
    {
      char c = buf[n];
      if (c == '\n' || c == '\r') break;
      n++;
    }
    
    // strip out newlines if necessary
    if (n != buf.length)
    {
      char[] temp = new char[n];
      System.arraycopy(buf, 0, temp, 0, n);
      buf = temp;
    }

    // return a single line
    Segment[] segments = { new Segment(Segment.TEXT, Segment.MOD_WORD, 0, n) };
    return new Line[] { new Line(buf, segments) };
  }
  */
                     
}
