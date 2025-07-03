/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * RemoveText removes the currently selected text.
 *
 * @author    Brian Frank
 * @creation  8 Aug 01
 * @version   $Revision: 3$ $Date: 3/28/05 10:32:38 AM EST$
 * @since     Baja 1.0
 */
public class RemoveText
  extends EditCommand
{ 

  public RemoveText(BTextEditor widget)
  {
    super(widget, BKeyBindings.cut);
  }  

  public CommandArtifact doInvoke()
  {
    RemoveArtifact remove = removeSelection();
    if (remove != null) remove.redo();
    return remove;
  }
  
}

