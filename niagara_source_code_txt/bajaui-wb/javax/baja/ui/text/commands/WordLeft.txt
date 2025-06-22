/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * Move the caret to the start position of the current word,
 * or if the current position not in a word segment, then go
 * to the start position of the previous word.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:39 AM EST$
 * @since     Baja 1.0
 */
public class WordLeft
  extends MoveCommand
{ 

  public WordLeft(BTextEditor editor)
  {
    super(editor, BKeyBindings.wordLeft);
  }  

  public CommandArtifact doInvoke()
  {
    Position cur = editor.getCaretPosition();
    Position left = editor.getModel().getWordLeft(cur);
    editor.updateAnchorX(left);
    return move(cur, left);
  }

}

