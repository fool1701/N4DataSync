/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * LineStart moves the caret to the first non-whitepsace
 * character on the current line.  If the cursor was already
 * at this position, then it is moved to the first column
 * on the current line.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 4$ $Date: 3/28/05 10:32:37 AM EST$
 * @since     Baja 1.0
 */
public class LineStart
  extends MoveCommand
{ 

  public LineStart(BTextEditor editor)
  {
    super(editor, BKeyBindings.lineStart);
  }  

  public CommandArtifact doInvoke() 
    throws Exception
  {
    // If already at the line start, return.
    Position cur = editor.getCaretPosition();
    if (cur.column == 0) 
      return null;
      
    Line line = editor.getModel().getLine(cur.line);
    int pos = cur.column;
    
    // Move to the end of the line if the cursor is
    // past it, otherwise getSegmentAt will fail.
    if (pos >= line.getColumnCount()) pos = line.getColumnCount()-1;
    int col = pos;
    
    // Move back through the line, recording any
    // non-whitespace characters.  The last one
    // we find is where we want to jump to.
    while (col >= 0)
    {
      if (!line.getSegmentAt(col).isWhitespace()) pos = col;
      col--;
    }
    
    // If the cursor was already at the first non-whitespace
    // character, then we jump to the 0 column.
    if (cur.column == pos) pos = 0;    
    
    Position target = new Position(cur.line, pos);
    editor.updateAnchorX(target);
    return move(cur, target);
  }
}

