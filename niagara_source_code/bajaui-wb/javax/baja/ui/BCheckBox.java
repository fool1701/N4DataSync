/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.event.BMouseEvent;

import com.tridium.ui.theme.AbstractButtonTheme;
import com.tridium.ui.theme.Theme;

/**
 * BCheckBox is a specialized BToggleButton which
 * displays its label next to a box which can be
 * checked and unchecked.
 *
 * @author    Brian Frank       
 * @creation  7 Dec 00
 * @version   $Revision: 22$ $Date: 6/28/11 1:23:39 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "halign",
  type = "BHalign",
  defaultValue = "BHalign.left",
  override = true
)
public class BCheckBox
  extends BToggleButton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BCheckBox(1261202488)1.0$ @*/
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
  public static final Type TYPE = Sys.loadType(BCheckBox.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a check box with the specified label text.
   */
  public BCheckBox(String text)
  {
    setText(text);
  }

  /**
   * Construct a check box with the specified label 
   * text and selection state.
   */
  public BCheckBox(String text, boolean selected)
  {
    setText(text);
    setSelected(selected);
  }

  /**
   * Constructor with ToggleCommand.
   */
  public BCheckBox(ToggleCommand cmd)
  {
    super(cmd, true, false);  
  }

  /**
   * No argument constructor.
   */
  public BCheckBox()
  {
  }
  
  public void computePreferredSize()
  {
    //If empty text or small font, check box preferred width is governed by size
    //of the Checkbox in the installed theme
    super.computePreferredSize();
    
    int checkBoxSize = Theme.checkBox().getCheckBoxSize();
    double preferredWidth = getPreferredWidth(),
           preferredHeight = getPreferredHeight();    
    
    boolean changed = false;
    
    if (preferredWidth < checkBoxSize)
    {
      preferredWidth = checkBoxSize;
      changed = true;
    }
    
    if (preferredHeight < checkBoxSize)
    {
      preferredHeight = checkBoxSize;
      changed = true;
    }
    
    if (changed) setPreferredSize(preferredWidth, preferredHeight);
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
  AbstractButtonTheme buttonTheme() { return Theme.checkBox(); }

  public String getStyleSelector() { return "button checkbox"; }
  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/checkBox.png");
    
  
}
