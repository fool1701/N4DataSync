/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * Move the caret down by several lines based on the current
 * number of visible lines.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:38 AM EST$
 * @since     Baja 1.0
 */
public class PageDown
  extends MoveCommand
{ 

  public PageDown(BTextEditor editor)
  {
    super(editor, BKeyBindings.pageDown);
  }  

  public CommandArtifact doInvoke() 
    throws Exception
  {
    Position cur = editor.getCaretPosition();
    int visible = editor.getVisibleLineCount();
    int lineCount = editor.getModel().getLineCount();
    int targetLineNumber = Math.min(cur.line + visible-1, lineCount-1);

    Line curLine = editor.getModel().getLine(cur.line);
    Line targetLine = editor.getModel().getLine(targetLineNumber);
    
    if (curLine == targetLine) 
    {
      return null;
    }
    
    double caretX = editor.getRenderer().getLineWidth(curLine, 0, cur.column + 1);
    int targetColumn = editor.getRenderer().xToColumn(targetLine, caretX);
    
    Position to = new Position(targetLineNumber, targetColumn);
    return move(cur, to);
  }      
  
}

