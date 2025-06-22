/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * LineShiftArtifact shifts a block of lines left or right.
 *
 * @author    Brian Frank
 * @creation  2 Sept 01
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:37 AM EST$
 * @since     Baja 1.0
 */
class LineShiftArtifact
  implements CommandArtifact
{ 

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  LineShiftArtifact(BTextEditor editor, boolean right)
  {
    this.editor = editor;
    this.right = right;
    
    Position start = editor.getSelection().getStart();
    Position end = editor.getSelection().getEnd();
    this.line0 = start.line;
    this.line1 = end.column > 0 ? end.line : end.line-1;
  }  

////////////////////////////////////////////////////////////////
// Shifting
////////////////////////////////////////////////////////////////  

  private void shiftLeft()
  {
    // get current lines
    String[] lines = getLines();
    
    // create insert buffer
    String text = shiftLeft(lines);
    
    // remove old lines
    Position rs = new Position(line0, 0);
    Position re = new Position(line1, editor.getModel().getLine(line1).getColumnCount());
    editor.getModel().remove(rs, re);
    
    // insert new tet
    editor.getModel().insert(rs, text);
  }

  private String shiftLeft(String[] lines)
  {
    StringBuilder s = new StringBuilder();
    int spaces = editor.getOptions().getTabToSpaceConversion();
    for(int i=0; i<lines.length; ++i)
    {
      String line = lines[i];
      
      // strip spaces characters if this line 
      // starts with that many spaces
      int j=0;
      for(; j<spaces; ++j)
      {
        if (j < line.length())
        {
          char c = line.charAt(j);
          if (c != ' ') break;
        }
      }

      for(; j<line.length(); ++j)
        s.append(line.charAt(j));
    }
    return s.toString();
  }

  private void shiftRight()
  {
    // get current lines
    String[] lines = getLines();
    
    // create insert buffer
    String text = shiftRight(lines);
    
    // remove old lines
    Position rs = new Position(line0, 0);
    Position re = new Position(line1, editor.getModel().getLine(line1).getColumnCount());
    editor.getModel().remove(rs, re);
    
    // insert new tet
    editor.getModel().insert(rs, text);
  }
  
  private String shiftRight(String[] lines)
  {
    StringBuilder s = new StringBuilder();
    int spaces = editor.getOptions().getTabToSpaceConversion();
    for(int i=0; i<lines.length; ++i)
    {
      String line = lines[i];
      for(int j=0; j<spaces; ++j) s.append(' ');
      s.append(lines[i]);
    }
    return s.toString();
  }
    
  private String[] getLines()
  {
    String[] lines = new String[line1-line0+1];
    for(int i=0; i<lines.length; ++i)
      lines[i] = new String(editor.getModel().getLine(i+line0).buffer);
    return lines;
  }

////////////////////////////////////////////////////////////////
// CommandArtifact
////////////////////////////////////////////////////////////////  

  public void undo()
  {
    if (right) shiftLeft();
    else shiftRight();
  }

  public void redo()
  {
    if (right) shiftRight();
    else shiftLeft();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  BTextEditor editor;
  boolean right;
  int line0, line1;
  
}

