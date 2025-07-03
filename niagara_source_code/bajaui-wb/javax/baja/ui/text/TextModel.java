/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import javax.baja.ui.event.BWidgetEvent;

import javax.baja.nre.util.TextUtil;

/**
 * TextModel stores the text document being viewed and
 * edited by a BTextWidget.
 *
 * @author    Brian Frank       
 * @creation  9 Dec 00
 * @version   $Revision: 46$ $Date: 6/27/07 1:31:17 PM EDT$
 * @since     Baja 1.0
 */
public class TextModel
  extends BTextEditor.TextSupport
{ 

////////////////////////////////////////////////////////////////
// Line
////////////////////////////////////////////////////////////////  

  /**
   * Get the number of lines in this document.
   */
  public int getLineCount()
  {
    return lines.length;
  }

  /**
   * Get the Line modeling the specified zero indexed line.
   */
  public Line getLine(int line)
  {
    try
    {
      if (line >= lines.length) 
      {
        return lines[lines.length - 1];
      }
      else if (line < 0)
      {
        return lines[0];
      }
      return lines[line];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      System.out.println("ERROR TextModel.getLine(" + line + ") length=" + lines.length);
      throw e;
    }
  }

////////////////////////////////////////////////////////////////
// IO
////////////////////////////////////////////////////////////////

  /**
   * Get the document as a String.
   */
  public String getText() 
  { 
    if (text == null)
    {
      try
      {
        StringWriter out = new StringWriter();
        write(out);
        text = out.toString();
      }
      catch(IOException e)
      {
        e.printStackTrace();
        throw new IllegalStateException(e.toString());
      }
    }
    return text;
  }
    
  /**
   * Get the text between fromPos and toPos inclusively.
   */
  public String getText(Position fromPos, Position toPos)
  {
    return new String(getCharArray(fromPos, toPos));
  }

  /**
   * Get the text between fromPos and toPos 
   * inclusively as a char[].
   */
  public char[] getCharArray(Position fromPos, Position toPos)
  {
    if (toPos.compareTo(fromPos) < 0)
      throw new IllegalArgumentException("toPos < fromPos");

    char[] buf = new char[256];
    int count = 0;
    for(int q = fromPos.line; q <= toPos.line; ++q)
    {
      Line line = getLine(q);
      int lineLen = line.buffer.length;
      int c1 = (q == fromPos.line) ? Math.min(fromPos.column, lineLen-1) : 0;
      int c2 = (q == toPos.line) ? Math.min(toPos.column, lineLen-1) : lineLen-1;
      
      int len = c2-c1+1;
      if (count+len >= buf.length)
      {
        char[] temp = new char[Math.max(count*2, count+len)];
        System.arraycopy(buf, 0, temp, 0, count);
        buf = temp;
      }

      try
      {
        if(c1 >= 0)
        {
          System.arraycopy(line.buffer, c1, buf, count, len);
          count += len;
        }
      }
      catch(RuntimeException e)
      {
        System.out.println("ERROR: TextModel.getCharArray(" + fromPos + "," + toPos + ")");
        System.out.println("  q          = " + q);
        System.out.println("  buf.length = " + buf.length);
        System.out.println("  count      = " + count);
        System.out.println("  len        = " + len);
        System.out.println("  lineLen    = " + lineLen);
        System.out.println("  c1         = " + c1);
        System.out.println("  c2         = " + c2);
        System.out.println("  e          = " + e);
        throw e;
      }
    }
    
    char[] result = new char[count];
    System.arraycopy(buf, 0, result, 0, count);
    return result;
  }

  /**
   * Set the document with a String.
   */
  public void setText(String text) 
  { 
    update(text.toCharArray());
    this.text = text;
  }
  

  /**
   * Gets the text model's current "anchor" X position.
   * @return the current "anchor" X position, in pixels
   */
  public double getAnchorX() 
  {
    return anchorX; 
  }
  
  /**
   * Sets the text model's current "anchor" X position. Once the anchor is 
   * updated, navigating through the text editor by pressing up/down/pgup/pgdown
   * will try to stay as close to this X position as it can. Prevents the caret 
   * from slamming hard left when navigating past empty lines, for instance.
   * 
   * @param anchorX the new "anchor" X position, in pixels
   */
  public void setAnchorX(double anchorX)
  {
    this.anchorX = anchorX;
  }

  /**
   * Read the document from the specified input stream
   * given the number of characters to read.
   */
  public void read(Reader in, int length)
    throws IOException
  {
    char[] buf = new char[length];
    int count = 0;
    while(count < length)
    {
      int n = in.read(buf, count, length-count);
      if (n < 0)
        throw new IOException("Unexpected EOF: " + this);
      count += n;
    }
    update(buf);    
  }

  /**
   * Read the document from the specified input stream
   * until the end of file is reached.
   */
  public void read(Reader in)
    throws IOException
  {
    int n;
    CharArrayWriter out = new CharArrayWriter();
    char[] buf = new char[256];
    while((n = in.read(buf)) >= 0) out.write(buf, 0, n);
    update(out.toCharArray());    
  }
  
  /**
   * Read the document from the specified char array.
   */
  public void read(char[] buf)
  {
    update(buf);    
  }
  
  /**
   * Write the document to the specified writer stream.
   */
  public void write(Writer out)
    throws IOException
  {
    for(int q=0; q<lines.length; ++q)
     lines[q].write(out);
  }

////////////////////////////////////////////////////////////////
// Position
////////////////////////////////////////////////////////////////  
 
  /**
   * Get the document's starting position.
   */
  public Position getStartPosition()
  {
    return new Position(0, 0);
  }

  /**
   * Get the document's ending position.
   */
  public Position getEndPosition()
  {
    int q = getLineCount()-1;
    return new Position(q, getLine(q).getColumnCount());
  }
    
  /**
   * Get the position immediately before the specified
   * position, or if the given position is the document
   * start, then return the start position.
   */
  public Position getPrevPosition(Position pos)
  {
    int c = pos.column - 1, q = pos.line;
    if (c < 0)
    {
      if (q == 0) return getStartPosition();
      q--;
      c = getLine(q).getColumnCount()-1;
    }
    return new Position(q, c);
  }

  /**
   * Get the position immediately after the 
   * specified position.
   */
  public Position getNextPosition(Position pos)
  {
    return new Position(pos.line, pos.column+1);
  }
  
  /**
   * Get the start position of the current word,
   * of if pos not in a word segment, then get
   * the start position of the previous word.
   */
  public Position getWordLeft(Position pos)
  {
    int q = pos.line;
    int s = lines[q].getSegmentIndexAt(pos.column);
    if (s == -1) s = lines[q].segments.length;
    else s++;
    while(true)
    {
      if (--s < 0) 
      {
        q--; if (q < 0) break;
        s = lines[q].segments.length-1;
      }
      
      Segment seg = lines[q].segments[s];
      if (seg.isWord()) 
      {
        Position left = new Position(q, seg.offset);
        if (!left.equals(pos)) return left;
      }
    }
    return getStartPosition();
  }

  /**
   * Get the "wordRight" caret position.  The
   * returned value is based on the current
   * setting of the wordRightToEndOfWord option.
   * <p>
   * If the option is true, get the ending position
   * of the current word, or if pos not inside a word
   * segment then the ending position of the next word.
   * <p>
   * If the option is false, get the start position
   * of the next word.
   */
  public Position getWordRight(Position pos)
  {
    boolean wordEnd = editor.getOptions().getWordRightToEndOfWord();

    int q = pos.line;
    int s = getLine(q).getSegmentIndexAt(pos.column);
    if (s == -1) s = lines[q].segments.length;
    else if (wordEnd) s--;

    // If we're moving to the start of the words, still
    // move to the end of the last word on the line.  This
    // is how UltraEdit works and it's nice.
    if ((!wordEnd) && (s == lines[q].segments.length-2))
    {
      Segment seg = lines[q].segments[s];
      return new Position(q, seg.offset+seg.length);
    }

    while(true)
    {
      if (++s >= lines[q].segments.length) 
      {
        q++; if (q >= lines.length) break;
        s = 0;
        if (lines[q].segments.length <= 1) continue;
      }
      
      Segment seg = lines[q].segments[s];
      if (seg.isWord()) return new Position(q, wordEnd ? seg.offset+seg.length : seg.offset);
    }
    
    return getEndPosition();
  }
  
  /**
   * Starting with the caret position, find the next 
   * occurance of the specified text or return null if 
   * not found before the end of the document.
   */
  public Position findNext(FindPattern pattern)
  {            
    String text = pattern.string;
    if (text == null || text.length() == 0) return null;
    Position cur = getEditor().getCaretPosition();
    int c = cur.column;
    int len = text.length();
    for(int q=cur.line; q<lines.length; ++q, c=0)
    {
      char[] buffer = lines[q].buffer;
      int x = pattern.findNext(buffer, c, buffer.length-c);
      if (x != -1)
        return new Position(q, x);
    }
    return null;
  }

  /**
   * Starting with the caret position, find the previous
   * occurance of the specified text or return null if 
   * not found before the end of the document.
   */
  public Position findPrev(FindPattern pattern)
  {
    String text = pattern.string;
    if (text == null || text.length() == 0) return null;
    Position cur = getEditor().getCaretPosition();
    int len = text.length();
    for(int q=cur.line; q>=0; --q)
    {
      char[] buffer = lines[q].buffer;
      
      int c = buffer.length-len;
      if ((q == cur.line) && ((cur.column-1) < buffer.length))
        c = cur.column-len-1;
        
      int x = pattern.findPrev(buffer, c, buffer.length-c);
      if (x != -1)
        return new Position(q, x);
    }
    return null;
  }
  
////////////////////////////////////////////////////////////////
// Editing
////////////////////////////////////////////////////////////////

  /**
   * Insert the given text into the document at 
   * the specified offset position.
   *
   * @return the Position immediately following
   *   the inserted text.
   */
  public Position insert(Position pos, String text)
  {
    char[] buf = text.toCharArray();
    return insert(pos, buf, 0, buf.length);
  }
  
  /**
   * Insert the given text into the document at
   * the specified offset position.
   *
   * @return the Position immediately following
   *   the inserted text.
   */
  public Position insert(Position pos, char[] buf, int offset, int len)
  {
    if (pos.line >= lines.length)
      throw new IllegalArgumentException("Position out of bounds: " + pos);
      
    // get the original line; if the position is 
    // past the end of the line, then we need to 
    // grow the line with empty space
    Line origLine = lines[pos.line];
    char[] orig = origLine.toCharArrayGrowAsNeeded(pos.column);

    // create a continuous new buffer with the 
    // current line and new text to insert
    char[] mix = new char[orig.length + len];    

    // fill in everything up to pos.col using the original line
    System.arraycopy(orig, 0, mix, 0, pos.column);
    int c = pos.column;

    // fill in characters to insert
    System.arraycopy(buf, offset, mix, c, len);
    c += len;
    
    // fill in rest of line
    if (orig.length > pos.column)
      System.arraycopy(orig, pos.column, mix, c, orig.length-pos.column);

    // splice back into the document
    splice(pos.line, 1, mix);  
    
    // compute new insertion position
    int iq = pos.line, ic = pos.column;
    for(int i=0; i<len && iq < lines.length; ++i)
    {
      ic++;
      if (ic >= lines[iq].buffer.length) { iq++; ic = 0; }
    }
    if (iq >= lines.length) return getEndPosition();
    else return new Position(iq, ic);
  }

  /**
   * Remove the characters between the from
   * and to positions inclusively.
   */
  public void remove(Position fromPos, Position toPos)
  {
    // sanity check
    if (toPos.compareTo(fromPos) < 0)
      throw new IllegalArgumentException("toPosition < fromPosition");
    
    // get the boundary lines for the splice
    Line line1 = lines[fromPos.line];
    Line line2 = lines[toPos.line];

//System.out.println("------ remove(" + fromPos + "," + toPos + ")");
//System.out.println("  line1: \"" + toString(line1.buffer) + "\"");
//System.out.println("  line2: \"" + toString(line2.buffer) + "\"");
    
    // get the two lines where we splice in (they
    // may be the same line).  If the fromPos is past 
    // line1, then we need to add spaces to the end of it.  
    // We do the same for line2 just for convenience (although 
    // we could handle it other ways).
    char[] line1Char = line1.toCharArrayGrowAsNeeded(fromPos.column);
    char[] line2Char = line2.toCharArrayGrowAsNeeded(toPos.column);

    // get a continuous char buffer from line1
    // to line2 minus the chunk being cut out
    int leftIn1 = fromPos.column;
    int leftIn2 = line2Char.length - toPos.column - 1;
    if (leftIn1 + leftIn2 < 0) return;
    char[] cut = new char[leftIn1 + leftIn2];
    
    if(leftIn1 >= 0 && leftIn2 >= 0)
    {
      System.arraycopy(line1Char, 0, cut, 0, leftIn1);
      System.arraycopy(line2Char, toPos.column+1, cut, fromPos.column, leftIn2);    

      //System.out.println("  line1Char: \"" + toString(line1Char) + "\"");
      //System.out.println("  line2Char: \"" + toString(line2Char) + "\"");
      //System.out.println("  cut:   \"" + toString(cut) + "\"");
  
      // splice it in    
      splice(fromPos.line, toPos.line-fromPos.line+1, cut);
    }
  }
  
  /**
   * Splice the character buffer back into the 
   * document at the specified line number.
   */
  private void splice(int q, int origLineCount, char[] buf)
  {
    // if the buffer doesn't end in newline, then
    // we might need to join with the following line
    if (buf.length == 0 || buf[buf.length-1] != '\n')
    {
      if (q+origLineCount < lines.length)
      {
        Line join = lines[q+origLineCount];
        char[] temp = new char[buf.length + join.buffer.length];
        System.arraycopy(buf, 0, temp, 0, buf.length);
        System.arraycopy(join.buffer, 0,  temp, buf.length, join.buffer.length);
        buf = temp;
        origLineCount++;
      }
    }
    
    // parse into a set of modified lines
    Line[] modLines = getParser().parse(buf);

//PrintWriter out = new PrintWriter(System.out);
//out.println("splice:           q = " + q);  
//out.println("    lines.length    = " + lines.length);
//out.println("    modLines.length = " + modLines.length);
//out.println("    origLinesCount  = " + origLineCount);

//out.println("old lines:");
//for(int i=0; i<origLineCount; ++i)
//  dump(out, q+i, lines[q+i]);
//  
//out.println("new lines:");
//for(int i=0; i<modLines.length; ++i)
//  dump(out, i, modLines[i]);
          
    // check if we need to add an empty last
    // line (boundary condition for adding a newline
    // at the end of the document)
    Line lastModLine = modLines[modLines.length-1];
    if (lastModLine.endsWithNewline() && q+origLineCount >= lines.length)
    {
      Line[] temp = new Line[modLines.length+1];
      System.arraycopy(modLines, 0, temp, 0, modLines.length);
      temp[modLines.length] = new Line(new char[0], new Segment[0]);
      modLines = temp;
    }
    
    // check for a simple replacement
    if (origLineCount == modLines.length)
    {
//out.println("replace");    
      for(int i=0; i<modLines.length; ++i) 
        lines[i+q] = modLines[i];      
    }
    
    // otherwise we need to add or remove lines
    else
    {
      Line[] temp = new Line[lines.length + modLines.length - origLineCount];
//out.println("add/remove: " + lines.length + " -> temp.length=" + temp.length);    
      System.arraycopy(lines, 0, temp, 0, q);
      System.arraycopy(modLines, 0, temp, q, modLines.length);
      System.arraycopy(lines, q+origLineCount, temp, q+modLines.length, lines.length-q-origLineCount);
      lines = temp;
    }

//out.flush();
  
    // update ourselves
    update(lines);
  }

////////////////////////////////////////////////////////////////
// Clipboard
////////////////////////////////////////////////////////////////  

  /**
   * Cut the current selection to the clipboard, or 
   * if no selection, then don't do anything.
   */
  public void cut()
  {
    getCommandFactory().make(TextCommandFactory.CUT).invoke();
  }

  /**
   * Copy the current selection to the clipboard, or 
   * if no selection, then don't do anything.
   */
  public void copy()
  {
    getCommandFactory().make(TextCommandFactory.COPY).invoke();
  }

  /**
   * Paste the text on the clipboard into the document
   * at the current caret position.
   */
  public void paste()
  {
    getCommandFactory().make(TextCommandFactory.PASTE).invoke();
  }

////////////////////////////////////////////////////////////////
// Selection
////////////////////////////////////////////////////////////////  

  /**
   * Get the selected text or null if no current selection.
   */
  public String getSelectedText()
  {
    if (getSelection().isEmpty()) return null;
    Position start = getSelection().getStart();
    Position end = getSelection().getEnd();
    
    // we need to get the character previous to end
    end = getPrevPosition(end);

    // get the text
    return getText(start, end);
  }  

  /**
   * Get the selected text as a char[] or null if no 
   * current selection.
   */
  public char[] getSelectedCharArray()
  {
    if (getSelection().isEmpty()) return null;
    Position start = getSelection().getStart();
    Position end = getSelection().getEnd();
    
    // we need to get the character previous to start
    start = getPrevPosition(start);

    // get the text
    return getCharArray(start, end);
  }  
  
////////////////////////////////////////////////////////////////
// Update
////////////////////////////////////////////////////////////////  

  /**
   * Perform an reset on the document by reparsing
   * the specified character buffer.
   */
  protected void update(char[] buf)
  {
    update(getParser().parse(buf));
  }
  
  /**
   * All changes to the model eventually route here 
   * for consistent processing.
   */
  protected void update(Line[] lines)
  {
    BTextEditor editor = getEditor();
    boolean singleLine = editor.isSingleLine();
    
    // update our local attributes
    this.lines = lines;
    this.text = null;
    
    // walk the lines which computes our preferred
    // dimensions, and updates the segments in comments
    double pw = prefWidth, ph = prefHeight;
    if (!singleLine) walkLines(lines);
    
    // if caret is out of bounds, fix it
    Position caret = editor.getCaretPosition();
    Position end = getEndPosition();
    if (caret.compareTo(end) > 0)
      editor.moveCaretPosition(end);
    
    // if the preferred size changes, then we need
    // more than a simple repaint on the BTextEditor
    if ((pw != prefWidth || ph != prefHeight) && !singleLine)
      editor.relayout();
    else
      editor.repaint();
      
    editor.fireTextModified(new BWidgetEvent(BWidgetEvent.MODIFIED, getEditor()));
    textModified();
  }
  
  /**
   * This callback is invoked whenever the text is modified.
   */
  protected void textModified()
  {
  }
  
  /**
   * Walk thru all the lines and all the segments
   *   - updating those segments which appear in comment
   *   - calculate the prefWidth and prefHeight fields
   *   - ensure consistent newlines
   */
  private void walkLines(Line[] lines)
  {    
    TextRenderer renderer = getRenderer();
    int len = lines.length;
    Segment string = null;
    Segment multi = null;
    Segment line = null;
    char stringChar = 0;
    int mask = 0;
    double maxw = 10;    
    
    int clear = ~(Segment.MOD_NEWLINE_N |
                  Segment.MOD_NEWLINE_R | 
                  Segment.MOD_NEWLINE_RN | 
                  Segment.MOD_IN_STRING_LITERAL |
                  Segment.MOD_IN_LINE_COMMENT |
                  Segment.MOD_IN_MULTI_LINE_COMMENT |
                  Segment.MOD_IN_NON_JAVADOC);
    
    int newlineMask = Segment.MOD_NEWLINE_N;
    Line first = lines[0];
    if (first.segments.length > 0)
    {
      Segment seg = first.segments[first.segments.length-1];
      if (seg.isNewlineR()) newlineMask = Segment.MOD_NEWLINE_R;
      else if (seg.isNewlineRN()) newlineMask = Segment.MOD_NEWLINE_RN;
    }

    int newTextLength = 0;
    for(int q=0; q<len; ++q)
    {
      Segment[] segments = lines[q].segments;
      char[] buffer = lines[q].buffer;
      newTextLength += buffer.length;
      maxw = Math.max(maxw, renderer.getLineWidth(lines[q]));
      int segmentsLength = segments.length;
      for(int i=0; i<segmentsLength; ++i)
      {
        Segment segment = segments[i];
        int type = segment.type;
        segment.modifiers &= clear;
        
        if (type == Segment.NEWLINE) 
          segment.modifiers |= newlineMask;
          
        if (multi != null)
        {
          segment.modifiers |= mask;
          if (type == Segment.MULTI_LINE_COMMENT_END)
            { multi = null; mask = 0; }
        }
        else if (line != null)
        {
          segment.modifiers |= mask;        
        }
        else if (string != null)
        {                   
          segment.modifiers |= mask;                        
          if (type == Segment.STRING_DELIMITER &&
              buffer[segment.offset] == stringChar)
            { string = null; mask = 0; }
        }
        else
        {
          if (type == Segment.MULTI_LINE_COMMENT_START)
          {
            multi = segment;
            mask = Segment.MOD_IN_MULTI_LINE_COMMENT;
            if (segment.isNonJavadoc()) mask |= Segment.MOD_IN_NON_JAVADOC;
            segment.modifiers |= mask;
          }
          else if (type == Segment.LINE_COMMENT)
          {
            line = segment;
            mask = Segment.MOD_IN_LINE_COMMENT;
            segment.modifiers |= mask;
          }
          else if (type == Segment.STRING_DELIMITER)
          {
            string = segment;                       
            stringChar = buffer[segment.offset];
            mask = Segment.MOD_IN_STRING_LITERAL;
            segment.modifiers |= mask;
          }
        }
      }
      line = string = null;
    }
    
    textLength = newTextLength; 
    
    this.prefWidth = maxw;
    this.prefHeight = renderer.getLineHeight() * len;
  }
  
  /**
   * Get the total number of characters in the text string.
   */
  public int getTextLength()
  {
    return textLength; 
  }
      
  public double getPreferredWidth()
  {
    return prefWidth;
  }
  
  public double getPreferredHeight()
  {
    return prefHeight;
  }
  
////////////////////////////////////////////////////////////////
// Debug
////////////////////////////////////////////////////////////////  

  /**
   * Dump the model to standard output.
   */
  public void dump()
  {
    PrintWriter out = new PrintWriter(System.out);
    dump(out);
    out.flush();
  }

  /**
   * Dump the model to the specified print writer.
   */
  public void dump(PrintWriter out)
  {
    for(int q=0; q<lines.length && q<20; ++q)
      dump(out, q, lines[q]);
  }

  /**
   * Dump the model to the specified print writer.
   */
  void dump(PrintWriter out, int q, Line line)
  {
    out.println(TextUtil.padRight(""+q+": ", 3) + toString(line.buffer));
    Segment[] segs = line.segments;
    for(int i=0; i<segs.length; ++i)
      out.println("   " + segs[i]);      
  }
  
  /**
   * Get an escaped String for the char[] buffer.
   */
  public static String toString(char[] buf)
  {
    if (buf == null) return "null";
    StringBuilder s = new StringBuilder();
    for(int i=0; i<buf.length; ++i)
    {
      char c = buf[i];
      if (c == '\n') s.append("\\n");
      else if (c == '\r') s.append("\\r");
      else s.append(c);
    }
    return s.toString();
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  private String text;
  private Line[] lines = new Line[] { new Line(new char[0], new Segment[0]) };
  double prefWidth;
  double prefHeight;
  int textLength = 0;
  double anchorX = 0;
}
