/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * Delete removes the current character.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 3$ $Date: 3/28/05 10:32:36 AM EST$
 * @since     Baja 1.0
 */
public class Delete
  extends EditCommand
{ 

  public Delete(BTextEditor widget)
  {
    super(widget, BKeyBindings.delete);
  }  

  public CommandArtifact doInvoke()
  {
    Position cur = editor.getCaretPosition();
    Position end = editor.getModel().getEndPosition();
    
    // don't delete past end of document
    if (cur.line >= end.line && cur.column >= end.column) 
      return remove(null, null);  // in case selected
      
    return remove(cur, cur);
  }  
  
}

