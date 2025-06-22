/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.ui.theme.*;

/**
 * BCheckBoxMenuItem a menu item which displays a binary
 * state with a check or lack of a check.
 *
 * @author    Brian Frank       
 * @creation  5 Jan 01
 * @version   $Revision: 17$ $Date: 3/28/05 10:32:16 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BCheckBoxMenuItem
  extends BToggleMenuItem
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BCheckBoxMenuItem(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCheckBoxMenuItem.class);

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
  public BCheckBoxMenuItem(String text, BAccelerator accelerator, boolean selected)
  {
    super(text, accelerator, selected);
  }

  /**
   * Construct a menu item with specified label text and selected state.
   */
  public BCheckBoxMenuItem(String text, boolean selected)
  {
    super(text, selected);
  }  

  /**
   * Construct a menu item with specified label text.
   */
  public BCheckBoxMenuItem(String text)
  {
    super(text);
  }

  /**
   * Construct a menu item for the specified command
   * using the command's label, accelerator, and icon.
   */
  public BCheckBoxMenuItem(ToggleCommand command)
  {
    super(command);
  }

  /**
   * No argument constructor.
   */
  public BCheckBoxMenuItem()
  {
  }

  @Override
  public boolean isCheckBoxItem()
  {
    return true;
  }

  ////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Override to paint the icon.
   */
  void paintImage(Graphics g)
  {
    Theme.menuItem().paintCheckBox(g, this, isSelected);
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/checkBoxMenuItem.png");
 
}
