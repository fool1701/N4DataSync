/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * Move the caret to the previous line.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:38 AM EST$
 * @since     Baja 1.0
 */
public class MoveUp
  extends MoveCommand
{ 

  public MoveUp(BTextEditor editor)
  {
    super(editor, BKeyBindings.moveUp);
  }  
  
  public CommandArtifact doInvoke()
  {
    Position cur = editor.getCaretPosition();
    
    if (cur.line == 0) 
    {
      return null;
    }
    
    Line prevLine = editor.getModel().getLine(cur.line - 1);
    double targetX = editor.getModel().getAnchorX();
    int targetColumn = editor.getRenderer().xToColumn(prevLine, targetX);
    Position up = new Position(cur.line-1, targetColumn);
    return move(cur, up);
  }
  
}

