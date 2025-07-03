/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * DocumentStart moves the caret to the first character 
 * position in the document.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:37 AM EST$
 * @since     Baja 1.0
 */
public class DocumentStart
  extends MoveCommand
{ 

  public DocumentStart(BTextEditor editor)
  {
    super(editor, BKeyBindings.documentStart);
  }  

  public CommandArtifact doInvoke()
    throws Exception
  {
    Position cur = editor.getCaretPosition();
    Position start = editor.getModel().getStartPosition();
    return move(cur, start);
  }
  
}

