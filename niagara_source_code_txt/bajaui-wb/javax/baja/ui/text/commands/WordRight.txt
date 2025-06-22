/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * Move the caret to the ending position of the current word, 
 * or if the current position is not inside a word segment 
 * then the go to the ending position of the next word.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:39 AM EST$
 * @since     Baja 1.0
 */
public class WordRight
  extends MoveCommand
{ 

  public WordRight(BTextEditor editor)
  {
    super(editor, BKeyBindings.moveRight);
  }  

  public CommandArtifact doInvoke()
  {
    Position cur = editor.getCaretPosition();
    Position right = editor.getModel().getWordRight(cur);
    editor.updateAnchorX(right);
    return move(cur, right);
  }
    
}

