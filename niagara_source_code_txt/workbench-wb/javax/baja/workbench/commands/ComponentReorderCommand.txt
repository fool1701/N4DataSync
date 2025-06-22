/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.commands;

import javax.baja.sys.BComponent;
import javax.baja.sys.BIPropertyContainer;
import javax.baja.sys.Property;
import javax.baja.ui.BDialog;
import javax.baja.ui.BWidget;
import javax.baja.ui.BWidgetShell;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.util.UiLexicon;
import com.tridium.workbench.util.BReorderPad;

/**
 * ComponentReorderCommand - reorder the components children.
 *
 * @author    Brian Frank
 * @creation  16 Aug 01
 * @version   $Revision: 11$ $Date: 8/18/09 4:26:55 PM EDT$
 * @since     Baja 1.0
 */
public class ComponentReorderCommand
  extends Command
{ 

  /**
   * Create a ComponentReorderCommand for the specified component.
   */
  public ComponentReorderCommand(BWidget owner, BComponent component)
  {
    this(owner, (BIPropertyContainer)component);
  }  
  
  /**
   * Create a ComponentReorderCommand for the specified BIPropertyContainer instance.
   *
   * @since Niagara 3.5
   */
  public ComponentReorderCommand(BWidget owner, BIPropertyContainer container)
  {
    super(owner, UiLexicon.bajaui().module, "commands.reorder");
    this.component = container;
  }  

  /**
   * Invoke the action.
   */
  public CommandArtifact doInvoke()
    throws Exception
  {
    String title = UiLexicon.bajaui().getText("reorder.title");
    title += " " + component.getDisplayName(null);
    BReorderPad pad = new BReorderPad(component);
    int r = BDialog.open(getOwner(), title, pad, BDialog.OK_CANCEL);
    
    if (r == BDialog.CANCEL) return null;
    
    Property[] oldOrder = pad.oldOrder();
    Property[] newOrder = pad.newOrder();
      
    BWidgetShell shell = getShell();
    if (shell != null) shell.enterBusy();
    try
    {
      component.reorder(newOrder, null);
      return null;  // no undo yet..
    }
    finally
    {
      if (shell != null) shell.exitBusy();
    }
  }
    
  private BIPropertyContainer component;
    
}

