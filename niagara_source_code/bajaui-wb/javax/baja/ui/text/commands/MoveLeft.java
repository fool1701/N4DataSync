/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * Move the caret to the next character.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:38 AM EST$
 * @since     Baja 1.0
 */
public class MoveLeft
  extends MoveCommand
{ 

  public MoveLeft(BTextEditor editor)
  {
    super(editor, BKeyBindings.moveLeft);
  }  

  public CommandArtifact doInvoke()
  {
    Position cur = editor.getCaretPosition();
    Line curLine = editor.getModel().getLine(cur.line);
    Position left;
    if (cur.column == 0) 
    {
      if (cur.line > 0)
      {
        Line prevLine = editor.getModel().getLine(cur.line - 1);
        left = new Position(cur.line - 1, prevLine.getColumnCountWithoutNewline());
      }
      else 
      {
        left = cur;
      }
    }
    else
    {
      left = new Position(cur.line, cur.column - 1);
    }
    editor.updateAnchorX(left);
    return move(cur, left);
  }

}

