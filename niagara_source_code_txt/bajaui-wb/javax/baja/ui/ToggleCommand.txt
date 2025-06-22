/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.util.*;
import javax.baja.sys.*;
import javax.baja.util.*;
import javax.baja.gx.*;

/**
 * ToggleCommand is a Command designed to manage a boolean
 * state across one or more toggle based controls, usually
 * a BToggleButton or a BToggleMenuItem.
 *
 * @author    Brian Frank
 * @creation  31 Jan 01
 * @version   $Revision: 15$ $Date: 3/28/05 10:32:22 AM EST$
 * @since     Baja 1.0
 */
public class ToggleCommand
  extends Command
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Create a new command by populating the command's
   * visualization information from the module's lexicon.  The
   * command is initialized by looking for the following
   * keys:  keyBase+".label", keyBase+".icon", 
   * keyBase+".accelerator", and keyBase+".description".
   * The icon value should be an ord to a 16x16 png file.
   */
  public ToggleCommand(BWidget owner, BModule module, String keyBase)
  {
    super(owner, module, keyBase);
  }

  /**
   * Create a new command by populating the command's
   * visualization information from the module's lexicon.  The
   * command is initialized by looking for the following
   * keys:  keyBase+".label", keyBase+".icon", 
   * keyBase+".accelerator", and keyBase+".description".
   * The icon value should be an ord to a 16x16 png file.
   */
  public ToggleCommand(BWidget owner, Lexicon lexicon, String keyBase)
  {
    super(owner, lexicon, keyBase);
  }

  /**
   * Create a new command by populating the command's
   * visualization information from a properties file.  The
   * command is initialized by looking for the following
   * keys:  keyBase+".label", keyBase+".icon", 
   * keyBase+".accelerator", and keyBase+".description".
   * The icon value should be an ord to a 16x16 png file.
   */
  public ToggleCommand(BWidget owner, Properties props, String keyBase)
  {
    super(owner, props, keyBase);
  }
  
  /**
   * Create a new Command with the specified 
   * visualization information.
   */
  public ToggleCommand(BWidget owner, String label, BImage icon, BAccelerator acc, String description)
  {
    super(owner, label, icon, acc, description);
  }

  /**
   * Create a new ToggleCommand with the specified label 
   * and defaults all other fields to null.
   */
  public ToggleCommand(BWidget owner, String label)
  {
    super(owner, label);
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Get the ToggleCommandGroup or null if not in a group.
   */
  @SuppressWarnings("rawtypes")
  public ToggleCommandGroup getGroup()
  {
    return group;
  }

////////////////////////////////////////////////////////////////
// Selection
////////////////////////////////////////////////////////////////

  /**
   * Is the toggle currently selected.
   */
  public synchronized boolean isSelected()
  {
    return selected;
  }

  /**
   * Set toggle's current selection.  This updates 
   * all registed widgets.
   */
  @SuppressWarnings("unchecked")
  public synchronized void setSelected(boolean selected)
  {
    if (selected == this.selected) return;
    this.selected = selected;
    BWidget[] widgets = getRegistry();
    for(int i=0; i<widgets.length; ++i)
    {
      BWidget w = widgets[i];
      if (w instanceof BToggleButton)
        ((BToggleButton)w).setSelected(selected);
      else if (w instanceof BCheckBoxMenuItem)
        ((BCheckBoxMenuItem)w).setSelected(selected);
      else if (w instanceof BRadioButtonMenuItem)
        ((BRadioButtonMenuItem)w).setSelected(selected);
    }

    if (group != null) group.checkExclusive(this);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  boolean selected;
  @SuppressWarnings("rawtypes")
  ToggleCommandGroup group;
  
}
