/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.BDialog;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.text.BKeyBindings;
import javax.baja.ui.text.BTextEditor;
import javax.baja.ui.text.Position;
import javax.baja.ui.util.UiLexicon;

/**
 * Goto prompts for a document location to jump to.
 *
 * @author    Brian Frank
 * @creation  16 May 02
 * @version   $Revision: 7$ $Date: 11/4/10 1:42:28 PM EDT$
 * @since     Baja 1.0
 */
public class Goto
  extends MoveCommand
{ 

  public Goto(BTextEditor editor)
  {
    super(editor, BKeyBindings.goTo);
  }  

  public CommandArtifact doInvoke()
    throws Exception
  {
    // display dialog if not already open
    if (tryLockModal())
    {
      try
      {
        // prompt user for line number
        String msg = UiLexicon.bajaui().getText("search.goto");
        String intStr = BDialog.prompt(editor, msg, ""+lastGotoLine, 8);
        if (intStr == null) return null;
              
        // goto
        lastGotoLine = Integer.parseInt(intStr);
        Position end = editor.getModel().getEndPosition();
        int actual = Math.max(0, Math.min(lastGotoLine-1, end.line));
        Position g = new Position(actual, 0);
        return move(editor.getCaretPosition(), g);
      }
      finally
      {
        unlockModal();
      }
    }

    return null;
  }
  
  static int lastGotoLine = 1;    
}

