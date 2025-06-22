/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;

import javax.baja.gx.*;
import javax.baja.ui.*;
import com.tridium.ui.theme.*;

/**
 * TextRenderer is used to paint the text document to 
 * a BTextEditor's graphics context.  
 *
 * @author    Brian Frank       
 * @creation  5 Aug 01
 * @version   $Revision: 23$ $Date: 6/20/11 9:34:25 AM EDT$
 * @since     Baja 1.0
 */
public class TextRenderer
  extends BTextEditor.TextSupport
{   

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor for default implementation of TextRenderer.
   */
  public TextRenderer()
  {
    this(Theme.textEditor().getFixedWidthFont());
  }

  /**
   * Constructor with specified font.
   */
  public TextRenderer(BFont font)
  {
    this.font = font;
    
      // ensure it is a fixed width font
    double w1 = font.width(".");
    double w2 = font.width("m");
    
    if (w1 != w2)
      throw new IllegalStateException("Not fixed width font: "+font.getName());
        
    // compute cell dimensions
    //this.cellWidth  = w1;
    this.cellHeight = font.getHeight() - 1;
    this.baseline   = cellHeight - 2;
  }

////////////////////////////////////////////////////////////////
// Positioning
////////////////////////////////////////////////////////////////

  /**
   * Get the fixed line height in pixels used for the 
   * text widget.  All lines in a BTextEditor must use 
   * this fixed height
   */
  public double getLineHeight()
  {
    return cellHeight;
  }

  /**
   * Calculate the total width of the specified line.
   */
  public double getLineWidth(Line line)
  {
    return line.getWidth(font);
  }
  
  public double getLineWidth(Line line, int startChar, int endChar) 
  {
    return line.getWidth(font, startChar, endChar);
  }

  /**
   * Calculate the width of the specified column
   * on the given line.
   */
  public double getColumnWidth(Line line, int col)
  {
    if(line==null || col >= line.buffer.length)
      return font.width("m");
    return font.width(line.buffer[col]);
  }
  
  /**
   * Get the x coordinate of the column cell for 
   * the specified line.
   */
  public double columnToX(Line line, int column)
  {
    int x = 0;
    for (int i = 0; i < column; i++)
      x += getColumnWidth(line, i);
    return x;
  }
  
  /**
   * Get the column index of the specified x 
   * coordinate in the given line.
   */
  public int xToColumn(Line line, double x)
  {
    int columnCount = line.getColumnCountWithoutNewline();
    double totalWidth = 0, columnWidth = 0;
    
    for (int i = 0; i < columnCount; i++)
    {
      columnWidth = getColumnWidth(line, i);
      totalWidth += columnWidth;
      
      if (totalWidth > x) //we've stepped past it
      {
        double distanceFromRightEdge = totalWidth - x;
        
        //if we're closer to the right edge than to the left and we're not
        //already on the last column, return column + 1
        if (distanceFromRightEdge < columnWidth / 2.0 &&
            i < columnCount - 1)
        {
          return i + 1;
        }
        else
        {
          return i;
        }
      }
    }
    return columnCount;
  }    
  
////////////////////////////////////////////////////////////////
// Background
////////////////////////////////////////////////////////////////

  /**
   * Paint the editor background.
   */
  public void paintBackground(Graphics g)
  {
    IRectGeom clip = g.getClipBounds();
    BInsets insets = editor.getInsets();
    double lineHeight = getLineHeight();
    
    g.setBrush(getBackground());    
    if (editor.isSingleLine() && !(editor.getParent() instanceof BDropDown))
      g.fillRect(0, 0, 32000, lineHeight + insets.top +  insets.bottom);
    else
      g.fill(clip);      
  }
  
////////////////////////////////////////////////////////////////
// Painting
////////////////////////////////////////////////////////////////  

  /**
   * Paint the specified line to the graphics context.  
   * The graphics context will be translated so that
   * the origin is the top left corner of the line.
   */
  public void paintLine(Graphics g, LineInfo info)
  {
    char[] buffer = info.line.buffer;
    Segment[] segments = info.line.segments;
    double x = 0;
    int selStart = info.selectionStartColumn;
    int selEnd = info.selectionEndColumn;
    boolean isSel = selStart >= 0;
    int q = info.lineIndex;                      

    // check for bracket match highlights
    if (matchOpenPos != null)
    {                    
      if (matchOpenPos.line == q)
      {
        g.setBrush(BColor.yellow);
        double columnWidth = getColumnWidth(info.line, matchOpenPos.column);
        double columnX = columnToX(info.line, matchOpenPos.column);
        g.fillRect(columnX, 0, columnWidth, cellHeight);
      }
      if (matchClosePos.line == q)
      {
        g.setBrush(BColor.yellow);
        double columnWidth = getColumnWidth(info.line, matchClosePos.column);
        double columnX = columnToX(info.line, matchClosePos.column);
        g.fillRect(columnX, 0, columnWidth, cellHeight);
      }
    }
    
    // paint selection background, if sel continues to 
    // the next line, then we want to paint to the edge
    if (isSel)
    {
      double selWidth;
      if (selEnd == Integer.MAX_VALUE) 
        selWidth = getEditor().getWidth();
      else
        selWidth = columnToX(info.line, selEnd)-columnToX(info.line, selStart);
      g.setBrush(getSelectionBackground());
            
      g.fillRect(columnToX(info.line, selStart), 0, selWidth, cellHeight);      
    }
    
    // paint each of the segments
    g.setFont(font);
    for(int i=0; i<segments.length; ++i)
    {
      Segment segment = segments[i];
      int type = segment.type;
      int mods = segment.modifiers;
      int offset = segment.offset;
      int length = segment.length;
      
      // if segment is totally inside selection, then don't 
      // bother to paint the segment with its color coding
      if (isSel && selStart <= offset && offset+length <= selEnd)
      {
        g.setBrush(getSelectionForeground());        
        x += paintSegment(g, x, buffer, type, mods, offset, length);              
      }
      
      // otherwise always paint the segment normally, then
      // check if we need to repaint a selected piece of it
      else 
      {        
        // paint the segment normal
        g.setBrush(getForeground(segment));
        x += paintSegment(g, x, buffer, type, mods, offset, length);      
        // now check if we need to partially repaint the 
        // segment as selected
        if (isSel && selStart <= offset+length && offset <= selEnd)
        {
          // compute the piece of the segment which is selected
          int selOffset = (offset < selStart) ? selStart : offset;
          double selX = columnToX(info.line, selOffset);

          int selLength = Math.min(length-(selOffset-offset), selEnd-selOffset);
          double selWidth = columnToX(info.line, selOffset+selLength)-selX;
          
          // repaint the selected piece of the segment
          g.setBrush(getSelectionForeground());        
          paintSegment(g, selX, buffer,  type, mods, selOffset, selLength);              
        }
      }
    }
  }     
  
////////////////////////////////////////////////////////////////
// Colors
////////////////////////////////////////////////////////////////
  
  /**
   * Get the foreground used to paint selected text.
   */
  public BBrush getSelectionForeground()
  {
    return Theme.textEditor().getSelectionForeground(editor);
  }

  /**
   * Get the background used to paint selected text.
   */
  public BBrush getSelectionBackground()
  {
    return Theme.textEditor().getSelectionBackground(editor);
  }

  /**
   * Get the brush used to paint the background for paintBackground().
   */
  public BBrush getBackground()
  {            
    return Theme.textEditor().getTextBackground(editor);    
  }

  /**
   * Get the brush to paint the specified segment.
   */
  public BBrush getForeground(Segment seg)
  { 
    // disabled brush
    if (!getEditor().isEnabled() ||
        !getEditor().getParentWidget().isEnabled())
      return Theme.textEditor().getDisabledTextBrush(getEditor());
    
    // otherwise use options color coding
    return BTextEditorOptions.make().getColorCoding().getColor(seg).toBrush();
  }
  
////////////////////////////////////////////////////////////////
// Paint Utils
////////////////////////////////////////////////////////////////
  
  /**
   * Paint the specified segment of a line and return the
   * x offset of the segment drawn
   */
  double paintSegment(Graphics g, double x, char[] buffer, 
                   int type, int mods, int offset, int length)
  {  
    // paint the segment
    switch(type)
    {
      case Segment.SPACES:
        if (getOptions().getShowSpaces()) 
          paintSpaces(g, x, length);
        return (font.width(" ")*length);
      case Segment.TAB:
        if (getOptions().getShowTabs()) 
          paintTab(g, x);
        return font.width("\t");
      case Segment.NEWLINE:
        if (getOptions().getShowNewlines()) 
          paintNewline(g, x, mods);
        return 0;
      default:
        g.drawString(buffer, offset, length, x, baseline);
        return font.width(buffer, offset, length);
    }
  }
  
  /**
   * Paint the spaces.
   */
  void paintSpaces(Graphics g, double x, int len)
  {
    double spaceWidth = font.width(" ");
    for(int i=0; i<len; ++i)
    {
      g.strokeLine(x+1, baseline-1, x+spaceWidth-2, baseline-1);
      x += spaceWidth;
    }
  }

  /**
   * Paint the newline character.
   */
  double paintTab(Graphics g, double x)
  {
    double tabWidth = font.width("\t"); 
    g.strokeLine(x+1, 2, tabWidth-3, baseline-3);
    return x+tabWidth;
  }

  /**
   * Paint the newline character.
   */
  void paintNewline(Graphics g, double xCell, int mods)
  {
    double x = xCell+1;
    double y = 2;
    double w = font.width("n")-2;
    double h = baseline-3;
    
    if ((mods & Segment.MOD_NEWLINE_N) != 0)
      g.drawString("n", x, baseline);
    else if ((mods & Segment.MOD_NEWLINE_R) != 0)
      g.drawString("r", x, baseline);
    else
      g.strokeRect(x, y, w, h);
  }

////////////////////////////////////////////////////////////////
// Caret
////////////////////////////////////////////////////////////////

  /**
   * Paint the caret for the line positioned at the specified 
   * column index.  The graphics context will be translated 
   * so that the origin is the top left corner of the line.
   */
  public void paintCaret(Graphics g, LineInfo lineInfo, int col)
  {   
    g.setBrush(inverseBrush);
    g.fillRect(columnToX(lineInfo.line, col), 0, 2, cellHeight-1);
  }         

////////////////////////////////////////////////////////////////
// Info
////////////////////////////////////////////////////////////////  

  /**
   * The Info class is used to pass rendering information
   * between the BTextEditor and TextRenderer.
   */
  public static class LineInfo
  {
    /**
     * The line currently being rendered.
     */
    public Line line;
    
    /**
     * The zero indexed line number currently being rendered.
     */
    public int lineIndex;        
    
    /**
     * If the current line is part of the selection, then 
     * this is the start column index of the selection
     * on the line.  If the current line is not part of 
     * the selection this field is -1.
     */
    public int selectionStartColumn;

    /**
     * If the current line is part of the selection, Then 
     * this field is the end column index of the selection
     * on this line.  If the selection continues onto the next 
     * line then this is Integer.MAX_VALUE.  If the current 
     * line is not part of the selection this field is -1.
     */
    public int selectionEndColumn;    
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static BBrush inverseBrush = BBrush.makeInverse(BColor.black);
  
  BFont font;   
  //double cellWidth;
  double cellHeight;
  double baseline;      
  
  Position matchOpenPos;
  Position matchClosePos;
  
}
