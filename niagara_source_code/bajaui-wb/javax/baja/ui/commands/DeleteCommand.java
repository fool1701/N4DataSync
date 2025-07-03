/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.commands;

import javax.baja.ui.*;
import javax.baja.ui.transfer.*;
import javax.baja.ui.util.*;

/**
 * DeleteCommand is used to invoke doDelete() on a BTransferWidget.
 *
 * @author    Brian Frank
 * @creation  19 Jul 01
 * @version   $Revision: 11$ $Date: 11/22/06 4:41:17 PM EST$
 * @since     Baja 1.0
 */
public class DeleteCommand
  extends Command
{ 

  /**
   * Create a DeleteCommand which uses the specified BTransferWidget.
   */
  public DeleteCommand(BTransferWidget owner)
  {
    super(owner, UiLexicon.bajaui().module, "commands.delete"); 
    setEnabled(owner.isDeleteEnabled());
  }  

  /**
   * Invoke the action.
   */
  public CommandArtifact doInvoke()
    throws Exception
  {                                
    // NOTE: clearing the clipboard here breaks Cut and Paste in the PxEditor. We
    // need to be cleverer than this -- Mike.

//        // clear clipboard in case what we are deleting is on it
//        Clipboard.getDefault().setContents(null);
    
    return ((BTransferWidget)getOwner()).doDelete();
  }

  /**
   * DeleteCommands are merged by virtue of routing to same TransferWidget.
   */
  protected Command doMerge(Command c)
  {
    return this;
  }
  
}

