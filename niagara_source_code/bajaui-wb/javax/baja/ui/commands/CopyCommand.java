/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.commands;

import javax.baja.ui.*;
import javax.baja.ui.transfer.*;
import javax.baja.ui.util.*;

/**
 * CopyCommand is used to invoke doCopy() on a BTransferWidget.
 *
 * @author    Brian Frank
 * @creation  19 Jul 01
 * @version   $Revision: 17$ $Date: 11/22/06 4:41:17 PM EST$
 * @since     Baja 1.0
 */
public class CopyCommand
  extends Command
{ 

  /**
   * Create a CopyCommand which uses the specified BTransferWidget.
   */
  public CopyCommand(BTransferWidget owner)
  {
    super(owner, UiLexicon.bajaui().module, "commands.copy");
    setEnabled(owner.isCopyEnabled());
  }  

  /**
   * Invoke the action.
   */
  public CommandArtifact doInvoke()
    throws Exception
  {
    return ((BTransferWidget)getOwner()).doCopy();
  }

  /**
   * CopyCommands are merged by virtue of routing to same TransferWidget.
   */
  protected Command doMerge(Command c)
  {
    return this;
  }
  
}

