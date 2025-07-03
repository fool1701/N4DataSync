/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * LineEnd moves the caret to the end of the current line.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:37 AM EST$
 * @since     Baja 1.0
 */
public class LineEnd
  extends MoveCommand
{ 

  public LineEnd(BTextEditor editor)
  {
    super(editor, BKeyBindings.lineEnd);
  }  


  public CommandArtifact doInvoke() 
    throws Exception
  {
    Position cur = editor.getCaretPosition();
    Line line = editor.getModel().getLine(cur.line);
    int col = Math.max(0, line.getColumnCountWithoutNewline());
    Position end = new Position(cur.line, col);
    editor.updateAnchorX(end);
    return move(cur, end);
  }      
  
}

