/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.menu.BIToggleMenuItem;

/**
 * BToggleMenuItem is the base class for BCheckBoxMenuItem
 * and BRadioButtonMenuItem.
 *
 * @author    Brian Frank       
 * @creation  5 Jan 01
 * @version   $Revision: 14$ $Date: 5/9/05 3:40:26 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The selected property indicates whether the
 checkbox is currently selected or unselected.
 */
@NiagaraProperty(
  name = "selected",
  type = "boolean",
  defaultValue = "false"
)
public abstract class BToggleMenuItem
  extends BActionMenuItem implements BIToggleMenuItem
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BToggleMenuItem(3527019266)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "selected"

  /**
   * Slot for the {@code selected} property.
   * The selected property indicates whether the
   * checkbox is currently selected or unselected.
   * @see #getSelected
   * @see #setSelected
   */
  public static final Property selected = newProperty(0, false, null);

  /**
   * Get the {@code selected} property.
   * The selected property indicates whether the
   * checkbox is currently selected or unselected.
   * @see #selected
   */
  public boolean getSelected() { return getBoolean(selected); }

  /**
   * Set the {@code selected} property.
   * The selected property indicates whether the
   * checkbox is currently selected or unselected.
   * @see #selected
   */
  public void setSelected(boolean v) { setBoolean(selected, v, null); }

  //endregion Property "selected"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BToggleMenuItem.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a menu item with specified label 
   * text, accelerator, and initial selection state.
   */
  public BToggleMenuItem(String text, BAccelerator accelerator, boolean selected)
  {
    super(text, accelerator);
    setSelected(selected);
  }

  /**
   * Construct a menu item with specified label text and selected state.
   */
  public BToggleMenuItem(String text, boolean selected)
  {
    super(text);
    setSelected(selected);
  }  

  /**
   * Construct a menu item with specified label text.
   */
  public BToggleMenuItem(String text)
  {
    super(text);
  }

  /**
   * Construct a menu item for the specified command
   * using the command's label, accelerator, and icon.
   */
  public BToggleMenuItem(ToggleCommand command)
  {
    super(command);
  }

  /**
   * No argument constructor.
   */
  public BToggleMenuItem()
  {
  }

////////////////////////////////////////////////////////////////
// BIMenuItem
////////////////////////////////////////////////////////////////

  @Override
  public boolean isSelected()
  {
    return getSelected();
  }

  @Override
  public boolean isCheckBoxItem()
  {
    return false;
  }

  @Override
  public boolean isRadioButtonItem()
  {
    return false;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public ToggleCommandGroup getToggleGroup()
  {
    return ((ToggleCommand)getCommand()).getGroup();
  }

  ////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public void doInvokeAction(CommandEvent event)
  {
    setSelected( !getSelected() );    
    super.doInvokeAction(event);
  }

  public void setCommand(Command command, boolean useLabel, boolean useIcon, boolean useAcc)
  {
    if (command != null)
    {
      if (!(command instanceof ToggleCommand))
        throw new IllegalArgumentException("Not ToggleCommand!");
      setSelected( ((ToggleCommand)command).isSelected() );
    }
    super.setCommand(command, useLabel, useIcon, useAcc);
  }

  public void changed(Property prop, Context cx)
  {
    super.changed(prop, cx);
    if (prop == selected)
    {
      if (command != null)
        ((ToggleCommand)command).setSelected(getSelected());
    }
  }

}
