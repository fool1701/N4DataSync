/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * SelectAll displays a dialog to prompt the user for search 
 * criteria then performs a SelectAll next.
 *
 * @author    Brian Frank
 * @creation  16 May 02
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:39 AM EST$
 * @since     Baja 1.0
 */
public class SelectAll
  extends TextEditorCommand
{ 

  public SelectAll(BTextEditor editor)
  {
    super(editor, BKeyBindings.selectAll);
  }  

  public CommandArtifact doInvoke()
  {             
    editor.getSelection().selectAll();
    return null;
  }
    
}

