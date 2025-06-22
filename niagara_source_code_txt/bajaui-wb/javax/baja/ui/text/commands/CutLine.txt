/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * CutLine cuts the line where the caret is currently 
 * positioned.
 *
 * @author    Brian Frank
 * @creation  12 Aug 01
 * @version   $Revision: 3$ $Date: 3/28/05 10:32:36 AM EST$
 * @since     Baja 1.0
 */
public class CutLine
  extends EditCommand
{ 

  public CutLine(BTextEditor editor)
  {
    super(editor, BKeyBindings.cutLine);
  }  

  public CommandArtifact doInvoke()
  {
    Position cur = editor.getCaretPosition();
    Line line = editor.getModel().getLine(cur.line);
    
    Position from = new Position(cur.line, 0);
    Position to = new Position(cur.line, line.getColumnCount());
    if (from.equals(to)) return null;
    
    editor.getSelection().select(from, to);
    editor.getModel().cut();
    
    return null;
  }
    
}

