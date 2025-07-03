/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * TabBack removes a tab from the beginning of the 
 * selected lines.  If no lines are selected then
 * the current line is shifted back.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 4$ $Date: 7/26/10 9:50:47 AM EDT$
 * @since     Baja 1.0
 */
public class TabBack
  extends TextEditorCommand
{ 

  public TabBack(BTextEditor editor)
  {
    super(editor, BKeyBindings.tabBack);
  }  

  public CommandArtifact doInvoke()
  {
    if (editor.getSelection().isEmpty()) return null;    
    if (!editor.isEditable()) return null;
    LineShiftArtifact art = new LineShiftArtifact(editor, false);
    art.redo();
    return art;
  }
    
}

