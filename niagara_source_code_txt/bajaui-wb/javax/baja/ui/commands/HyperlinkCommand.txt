/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.commands;

import javax.baja.naming.*;
import javax.baja.gx.*;
import javax.baja.ui.*;
import javax.baja.ui.event.*;

/**
 * HyperlinkCommand is a specialized command which can be 
 * used in widgets to hyperlink to a specified BOrd.
 *
 * @author    Brian Frank
 * @creation  16 Feb 01
 * @version   $Revision: 10$ $Date: 5/16/05 8:56:47 AM EDT$
 * @since     Baja 1.0
 */
public class HyperlinkCommand
  extends Command
{ 

  /**
   * Create HyperlinkCommand with a specified label
   * and HyperlinkTarget.
   */
  public HyperlinkCommand(BWidget owner, String label, BOrd ord)
  {
    super(owner, label);
    this.ord = ord;
  }  

  /**
   * Create HyperlinkCommand with a specified label, icon,
   * and HyperlinkTarget.
   */
  public HyperlinkCommand(BWidget owner, BImage icon, String label, BOrd ord)
  {
    this(owner, label, ord);
    this.icon = icon;
  }  

  /**
   * Invoke the action.
   */
  public CommandArtifact doInvoke(CommandEvent cmdEvent)
  {                                                 
    BInputEvent inputEvent = null;
    if (cmdEvent != null) inputEvent = cmdEvent.getInputEvent();
  
    BWidgetShell shell = getShell();
    if (shell instanceof BIHyperlinkShell)
      ((BIHyperlinkShell)shell).hyperlink(new HyperlinkInfo(ord, inputEvent));
    return null;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
    
  private BOrd ord;
  
}

