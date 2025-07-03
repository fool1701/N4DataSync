/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.ui.theme.*;
import com.tridium.ui.theme.custom.nss.StyleUtils;

/**
 * BToggleButton provides a two-state widget which may
 * be selected and unselected.
 *
 * @author    Brian Frank       
 * @creation  2 Dec 00
 * @version   $Revision: 27$ $Date: 6/11/07 12:41:32 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The selected property indicates whether the
 toggle is currently selected or unselected.
 */
@NiagaraProperty(
  name = "selected",
  type = "boolean",
  defaultValue = "false"
)
public class BToggleButton
  extends BAbstractButton
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BToggleButton(2482955830)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "selected"

  /**
   * Slot for the {@code selected} property.
   * The selected property indicates whether the
   * toggle is currently selected or unselected.
   * @see #getSelected
   * @see #setSelected
   */
  public static final Property selected = newProperty(0, false, null);

  /**
   * Get the {@code selected} property.
   * The selected property indicates whether the
   * toggle is currently selected or unselected.
   * @see #selected
   */
  public boolean getSelected() { return getBoolean(selected); }

  /**
   * Set the {@code selected} property.
   * The selected property indicates whether the
   * toggle is currently selected or unselected.
   * @see #selected
   */
  public void setSelected(boolean v) { setBoolean(selected, v, null); }

  //endregion Property "selected"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BToggleButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a toggle button with the specified label text.
   */
  public BToggleButton(String text)
  {
    setText(text);
  }

  /**
   * Construct a toggle button with the specified label text and image.
   */
  public BToggleButton(BImage image, String text)
  {
    setText(text);
    setImage(image);
  }

  /**
   * Construct a toggle button with the specified label 
   * text and selection state.
   */
  public BToggleButton(String text, boolean selected)
  {
    setText(text);
    setSelected(selected);
  }
  
  /**
   * Construct a toggle button with the specified label text
   * and image and selection state.
   */
  public BToggleButton(BImage image, String text, boolean selected)
  {
    setText(text);
    setImage(image);
    setSelected(selected);
  }
  
  /**
   * Constructor with ToggleCommand.  The label and icon
   * of the command are set using specified flags, and
   * the button is automatically registered with the
   * command.
   */
  public BToggleButton(ToggleCommand cmd, boolean useLabel, boolean useIcon)
  {
    setCommand(cmd, useLabel, useIcon);  
  }

  /**
   * Constructor with ToggleCommand where useLabel and useIcon is true.
   */
  public BToggleButton(Command cmd)
  {    
    setCommand(cmd, true, true);
  }

  /**
   * No argument constructor.
   */
  public BToggleButton()
  {
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Return if the toggle is currently selected.
   */
  public boolean isSelected() { return getBoolean(selected); }

  /**
   * If the button has a command associated with 
   * it, return the Command instance, otherwise
   * return null.
   */
  public Command getCommand()
  {
    return command;
  }

  /**
   * Install the command object for this button.  This
   * does *not* automatically change the label or icon
   * for this button.
   */
  public void setCommand(Command command, boolean useLabel, boolean useIcon)
  {    
    if (command != null)
    {
      if (!(command instanceof ToggleCommand))
        throw new IllegalArgumentException("Not ToggleCommand!");
      
      setSelected( ((ToggleCommand)command).isSelected() );
    }
    super.setCommand(command, useLabel, useIcon);
  }

  /**
   * When a toggle button is pressed, it toggles
   * its current selection state.
   */
  public void doInvokeAction(CommandEvent event)
  {
    setSelected( !getSelected() );
    super.doInvokeAction(event);
  }
  
  public void changed(Property prop, Context cx)
  {
    super.changed(prop, cx);
    if (prop == selected)
    {
      boolean sel = getSelected();
      if (command != null && ((ToggleCommand)command).isSelected() != sel)
      {
        ((ToggleCommand)command).setSelected(sel);
        command.invoke();
      }
      StyleUtils.toggleStyleClass(this, "selected", this.isSelected());
    }
  }

  /**
   * Package protected theme access.
   */
  AbstractButtonTheme buttonTheme() { return Theme.toggleButton(); }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/toggleButton.png");
    
}
