/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.CommandArtifact;
import javax.baja.ui.text.BKeyBindings;
import javax.baja.ui.text.BTextEditor;

import com.tridium.ui.BReplaceDialog;

/**
 * Replace does a search and replace.
 *
 * @author    Brian Frank
 * @creation  16 May 02
 * @version   $Revision: 4$ $Date: 11/4/10 1:42:28 PM EDT$
 * @since     Baja 1.0
 */
public class Replace
  extends TextEditorCommand
{ 

  public Replace(BTextEditor editor)
  {
    super(editor, BKeyBindings.replace);
  }  

  public CommandArtifact doInvoke()
  {
    // display dialog if not already open
    if (tryLockModal())
    {
      try
      {
        BReplaceDialog dialog = new BReplaceDialog(editor);
        dialog.setBoundsCenteredOnOwner();
        dialog.open();
        return null;
      }
      finally
      {
        unlockModal();
      }
    }

    return null;
  }  
}

