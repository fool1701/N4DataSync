/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * ReloadMacros displays a dialog to prompt the user for search 
 * criteria then performs a ReloadMacros next.
 *
 * @author    Brian Frank
 * @creation  16 May 02
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:38 AM EST$
 * @since     Baja 1.0
 */
public class ReloadMacros
  extends TextEditorCommand
{ 

  public ReloadMacros(BTextEditor editor)
  {
    super(editor, BKeyBindings.reloadMacros);
  }  

  public CommandArtifact doInvoke()
  {             
    BKeyBindings.loadMacros();
    return null;
  }
    
}

