/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.commands;

import javax.baja.sys.BComponent;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.util.UiLexicon;
import com.tridium.workbench.util.BCompositeEditor;

/**
 * ComponentCompositeCommand opens the CompositeEditor.
 *
 * @author    Brian Frank
 * @creation  31 Aug 01
 * @version   $Revision: 2$ $Date: 11/22/06 4:59:01 PM EST$
 * @since     Baja 1.0
 */
public class ComponentCompositeCommand
  extends Command
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public ComponentCompositeCommand(BWidget owner, BComponent component)
  {
    super(owner, UiLexicon.bajaui().module, "commands.composite");
    this.c = component;
  }

////////////////////////////////////////////////////////////////
// Invoke
////////////////////////////////////////////////////////////////

  /**
   * Invoke the action.
   */
  public CommandArtifact doInvoke()
    throws Exception
  {
    BCompositeEditor.openInDialog(getOwner(), c);
    return null;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BComponent c;
}

