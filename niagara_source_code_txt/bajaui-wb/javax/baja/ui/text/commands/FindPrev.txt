/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;
import javax.baja.ui.util.*;

/**
 * FindPrev finds the previous instance of the current search string.
 *
 * @author    Brian Frank
 * @creation  16 May 02
 * @version   $Revision: 3$ $Date: 11/22/06 4:41:18 PM EST$
 * @since     Baja 1.0
 */
public class FindPrev
  extends TextEditorCommand
{ 

  public FindPrev(BTextEditor editor)
  {
    super(editor, BKeyBindings.findPrev);
  }  

  /**
   * If after the command was invoked, this returns the position 
   * of the text, if not found then return null.
   */
  public Position getFoundPosition()
  {
    return foundPosition;
  }

  public CommandArtifact doInvoke()
  {
    FindPattern pattern = FindPattern.getCurrent();
    String string = pattern.string;
    Position pos = editor.getModel().findPrev(pattern);
    if (pos != null)
    {
      foundPosition = pos;
      Position end = new Position(pos.line, pos.column+string.length());
      editor.getSelection().select(pos, end);
      editor.getShell().showStatus("Line: " + (pos.line+1) + " Column: " + (pos.column+1));
    }
    else
    {
      editor.getShell().showStatus(UiLexicon.bajaui().getText("noMore") + ": " + string);
    }
    return null;
  }
  
  Position foundPosition;
}

