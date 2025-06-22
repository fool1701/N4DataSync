/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.commands;

import javax.baja.ui.*;
import javax.baja.ui.transfer.*;
import javax.baja.ui.util.*;

/**
 * CutCommand is used to invoke doCut() on a BTransferWidget.
 *
 * @author    Brian Frank
 * @creation  19 Jul 01
 * @version   $Revision: 16$ $Date: 11/22/06 4:41:17 PM EST$
 * @since     Baja 1.0
 */
public class CutCommand
  extends Command
{ 

  /**
   * Create a CutCommand which uses the specified BTransferWidget.
   */
  public CutCommand(BTransferWidget owner)
  {
    super(owner, UiLexicon.bajaui().module, "commands.cut");
    setEnabled(owner.isCutEnabled());
  }  

  /**
   * Invoke the action.
   */
  public CommandArtifact doInvoke()
    throws Exception
  {
    return ((BTransferWidget)getOwner()).doCut();
  }

  /**
   * CutCommands are merged by virtue of routing to same TransferWidget.
   */
  protected Command doMerge(Command c)
  {
    return this;
  }
  
}

