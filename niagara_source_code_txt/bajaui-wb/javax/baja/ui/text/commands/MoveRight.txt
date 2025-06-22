/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * Move the caret to the previous character.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:38 AM EST$
 * @since     Baja 1.0
 */
public class MoveRight
  extends MoveCommand
{ 

  public MoveRight(BTextEditor editor)
  {
    super(editor, BKeyBindings.moveRight);
  }  

  public CommandArtifact doInvoke()
  {
    Position cur = editor.getCaretPosition();
    Position right;
    Line curLine = editor.getModel().getLine(cur.line);
    int lineCount = editor.getModel().getLineCount();
    
    if (cur.column >= curLine.getColumnCountWithoutNewline())
    {
      if (cur.line + 1 < lineCount) 
      {
        right = new Position(cur.line + 1, 0);
      }
      else
      {
        right = cur;
      }
    } 
    else 
    {
      right = new Position(cur.line, cur.column+1);
    }

    editor.updateAnchorX(right);
    return move(cur, right);
  }
    
}

