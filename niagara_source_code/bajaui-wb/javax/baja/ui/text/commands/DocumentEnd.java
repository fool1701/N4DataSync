/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * DocumentEnd moves the caret to the last character 
 * position in the document.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:37 AM EST$
 * @since     Baja 1.0
 */
public class DocumentEnd
  extends MoveCommand
{ 

  public DocumentEnd(BTextEditor editor)
  {
    super(editor, BKeyBindings.documentEnd);
  }  

  public CommandArtifact doInvoke() 
    throws Exception
  {
    Position cur = editor.getCaretPosition();
    Position end = editor.getModel().getEndPosition();
    return move(cur, end);
  }  
  
}

