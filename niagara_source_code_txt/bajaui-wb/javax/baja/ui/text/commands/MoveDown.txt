/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * Move the caret to next line.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:38 AM EST$
 * @since     Baja 1.0
 */
public class MoveDown
  extends MoveCommand
{ 

  public MoveDown(BTextEditor editor)
  {
    super(editor, BKeyBindings.moveDown);
  }  

  public CommandArtifact doInvoke()
  {

    Position cur = editor.getCaretPosition();
    Line curLine = editor.getModel().getLine(cur.line);
    Line nextLine = editor.getModel().getLine(cur.line + 1);
    
    if (curLine == nextLine) 
    {
      return null;
    }
    
    double targetX = editor.getModel().getAnchorX();
    int targetColumn = editor.getRenderer().xToColumn(nextLine, targetX);
    Position down = new Position(cur.line + 1, targetColumn);
    return move(cur, down);
  }
}

