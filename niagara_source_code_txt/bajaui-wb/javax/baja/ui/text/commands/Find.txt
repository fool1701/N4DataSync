/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.CommandArtifact;
import javax.baja.ui.text.BKeyBindings;
import javax.baja.ui.text.BTextEditor;
import javax.baja.ui.text.FindPattern;

/**
 * Find displays a dialog to prompt the user for search 
 * criteria then performs a find next.
 *
 * @author    Brian Frank
 * @creation  16 May 02
 * @version   $Revision: 3$ $Date: 11/4/10 1:42:28 PM EDT$
 * @since     Baja 1.0
 */
public class Find
  extends TextEditorCommand
{ 

  public Find(BTextEditor editor)
  {
    super(editor, BKeyBindings.find);
  }  

  public CommandArtifact doInvoke()
  {
    // display dialog if not already open
    if (tryLockModal())
    {
      try
      {
        FindPattern pattern = FindPattern.query(editor);
        if (pattern == null) return null;
        return new FindNext(editor).doInvoke();
      }
      finally
      {
        unlockModal();
      }
    }
    
    return null;    
  }    
}

