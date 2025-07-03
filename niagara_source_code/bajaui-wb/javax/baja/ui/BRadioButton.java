/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.enums.*;
import javax.baja.ui.event.*;

import com.tridium.ui.theme.*;

/**
 * BRadioButton is a specialized BToggleButton which
 * displays its label next to a circle which can be
 * checked and unchecked.  It is used with groups of
 * other BRadioButton's to provide an choice which is
 * exclusive of other options.
 *
 * @author    Brian Frank       
 * @creation  7 Dec 00
 * @version   $Revision: 21$ $Date: 6/28/11 1:23:39 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "halign",
  type = "BHalign",
  defaultValue = "BHalign.left",
  override = true
)
public class BRadioButton
  extends BToggleButton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BRadioButton(1261202488)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "halign"

  /**
   * Slot for the {@code halign} property.
   * @see #getHalign
   * @see #setHalign
   */
  public static final Property halign = newProperty(0, BHalign.left, null);

  //endregion Property "halign"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRadioButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a radio button with the specified label text.
   */
  public BRadioButton(String text)
  {
    setText(text);
  }

  /**
   * Construct a radio button with the specified label 
   * text and selection state.
   */
  public BRadioButton(String text, boolean selected)
  {
    setText(text);
    setSelected(selected);
  }

  /**
   * Create radio button with a command.
   */
  public BRadioButton(ToggleCommand cmd, boolean useLabel, boolean useIcon)
  {
    super(cmd, useLabel, useIcon);  
  }

  /**
   * Create radio button with a command with label, but not icon.
   */
  public BRadioButton(ToggleCommand cmd)
  {
    super(cmd, true, false);  
  }

  /**
   * Convenience for <code>this(group, text, false)</code>.
   */
  @SuppressWarnings("rawtypes")
  public BRadioButton(ToggleCommandGroup group, String text)
  {
    this(group, text, false);  
  }

  /**
   * Create a radio button which is automatically in the specified 
   * group.  A toggle command is automatically created and registered 
   * into the group for this widget.
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public BRadioButton(ToggleCommandGroup group, String text, boolean selected)
  {
    this(text, selected);
    ToggleCommand cmd = new ToggleCommand(this, text);
    setCommand(cmd, false, false);
    group.add(cmd);
  }

  /**
   * No argument constructor.
   */
  public BRadioButton()
  {
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public void mousePressed(BMouseEvent event)
  {
    super.mousePressed(event);
    requestFocus();
  }

  /**
   * Package protected theme access.
   */
  AbstractButtonTheme buttonTheme() { return Theme.radioButton(); }    

  public String getStyleSelector() { return "button radio"; }
  
  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/radioButton.png");
  
}
