/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * Move the caret up by several lines based on the current
 * number of visible lines.
 *
 * @author    Brian Frank
 * @creation  8 Aug 01
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:38 AM EST$
 * @since     Baja 1.0
 */
public class PageUp
  extends MoveCommand
{ 

  public PageUp(BTextEditor editor)
  {
    super(editor, BKeyBindings.pageUp);
  }  

  public CommandArtifact doInvoke() 
    throws Exception
  {
    Position cur = editor.getCaretPosition();
    int visible = editor.getVisibleLineCount();
    int targetLineNumber = Math.max(cur.line - visible - 1, 0);

    Line curLine = editor.getModel().getLine(cur.line);
    Line targetLine = editor.getModel().getLine(targetLineNumber);
    
    if (curLine == targetLine) {
      return null;
    }
    
    double caretX = editor.getRenderer().getLineWidth(curLine, 0, cur.column);
    int targetColumn = editor.getRenderer().xToColumn(targetLine, caretX + 1);
    
    Position to = new Position(targetLineNumber, targetColumn);
    return move(cur, to);
  }    
  
}

