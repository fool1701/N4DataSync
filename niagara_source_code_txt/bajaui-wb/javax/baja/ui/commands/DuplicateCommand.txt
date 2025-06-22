/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.commands;

import javax.baja.ui.*;
import javax.baja.ui.transfer.*;
import javax.baja.ui.util.*;

/**
 * DuplicateCommand is used to invoke doDuplicate() on a BTransferWidget.
 *
 * @author    Brian Frank
 * @creation  19 Jul 01
 * @version   $Revision: 11$ $Date: 11/22/06 4:41:17 PM EST$
 * @since     Baja 1.0
 */
public class DuplicateCommand
  extends Command
{ 

  /**
   * Create a DuplicateCommand which uses the specified BTransferWidget.
   */
  public DuplicateCommand(BTransferWidget owner)
  {
    super(owner, UiLexicon.bajaui().module, "commands.duplicate");  
    setEnabled(owner.isDuplicateEnabled());
  }  
  
  /**
   * Invoke the action.
   */
  public CommandArtifact doInvoke()
    throws Exception
  {
    return ((BTransferWidget)getOwner()).doDuplicate();
  }

  /**
   * DuplicateCommands are merged by virtue of routing to same TransferWidget.
   */
  protected Command doMerge(Command c)
  {
    return this;
  }
  
}
