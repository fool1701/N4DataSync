/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * Backspace removes the previous character.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:36 AM EST$
 * @since     Baja 1.0
 */
public class Backspace
  extends EditCommand
{ 

  public Backspace(BTextEditor widget)
  {
    super(widget, BKeyBindings.backspace);
  }  

  public CommandArtifact doInvoke()
  {
    Position cur = editor.getCaretPosition();
    Position back = new Position(cur.line, cur.column-1);
    if (back.column < 0)
    {
      // can't backspace past document start, 
      // unless there is selection then bomb
      if (back.line == 0) 
      {
        if (editor.getSelection().isEmpty()) return null;
        back = editor.getModel().getStartPosition();
      }
      else
      {
        // back is end of previous line
        back = new Position(cur.line-1,
          editor.getModel().getLine(cur.line-1).getColumnCountWithoutNewline());
      }
    }
    return remove(back, back);
  }
  
}

