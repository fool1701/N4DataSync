/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.commands;

import javax.baja.ui.*;
import javax.baja.ui.transfer.*;
import javax.baja.ui.util.*;

/**
 * RenameCommand is used to invoke doRename() on a BTransferWidget.
 *
 * @author    Brian Frank
 * @creation  22 Mar 05
 * @version   $Revision: 4$ $Date: 11/22/06 4:41:18 PM EST$
 * @since     Baja 1.0
 */
public class RenameCommand
  extends Command
{ 

  /**
   * Create a RenameCommand which uses the specified BTransferWidget.
   */
  public RenameCommand(BTransferWidget owner)
  {
    super(owner, UiLexicon.bajaui().module, "commands.rename"); 
    setEnabled(owner.isRenameEnabled());
  }  

  /**
   * Invoke the action.
   */
  public CommandArtifact doInvoke()
    throws Exception
  {                                
    return ((BTransferWidget)getOwner()).doRename();
  }          
  
  /**
   * RenameCommands are merged by virtue of routing to same TransferWidget.
   */
  protected Command doMerge(Command c)
  {
    return this;
  }
  
}

