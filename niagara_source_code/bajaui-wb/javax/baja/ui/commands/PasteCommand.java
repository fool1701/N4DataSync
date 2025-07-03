/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.commands;

import javax.baja.ui.*;
import javax.baja.ui.transfer.*;
import javax.baja.ui.util.*;

/**
 * PasteCommand is used to invoke doPaste() on a BTransferWidget.
 *
 * @author    Brian Frank
 * @creation  19 Jul 01
 * @version   $Revision: 15$ $Date: 11/22/06 4:41:18 PM EST$
 * @since     Baja 1.0
 */
public class PasteCommand
  extends Command
{ 

  /**
   * Create a PasteCommand which uses the specified BTransferWidget.
   */
  public PasteCommand(BTransferWidget owner)
  {
    super(owner, UiLexicon.bajaui().module, "commands.paste");
    setEnabled(owner.isPasteEnabled());
  }  

  /**
   * Invoke the action.
   */
  public CommandArtifact doInvoke()
    throws Exception
  {
    return ((BTransferWidget)getOwner()).doPaste();
  }
  
}

