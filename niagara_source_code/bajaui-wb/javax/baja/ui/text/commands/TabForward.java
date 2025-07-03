/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * TabForward has two modes of operation.  If nothing is 
 * selected it inserts as many spaces as needed to get to 
 * the next "tab stop".  If there is a selection, then all 
 * the selected lines are indented tabToSpacesConversion.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 5$ $Date: 7/26/10 9:50:47 AM EDT$
 * @since     Baja 1.0
 */
public class TabForward
  extends EditCommand
{ 

  public TabForward(BTextEditor editor)
  {
    super(editor, BKeyBindings.tabForward);
  }  

  public CommandArtifact doInvoke()
  {
    if (!editor.isEditable()) return null;

    // if no selection, then this is 
    if (editor.getSelection().isEmpty())
    {
      Position cur = editor.getCaretPosition();
      int spaces = editor.getOptions().getTabToSpaceConversion();
      int add = cur.column % spaces;
      if (add == 0) add = spaces;
      
      char[] text = new char[add];
      for(int i=0; i<text.length; ++i) text[i] = ' ';
      
      return insert(text);
    }
    else
    {
      LineShiftArtifact art = new LineShiftArtifact(editor, true);
      art.redo();
      return art;
    }
  }
    
}

