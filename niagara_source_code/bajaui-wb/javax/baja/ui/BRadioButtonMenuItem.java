/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.ui.theme.Theme;

/**
 * BRadioButtonMenuItem a menu item which displays an
 * exclusive binary state with a check or lack of a check.
 *
 * @author    Brian Frank       
 * @creation  5 Jan 01
 * @version   $Revision: 19$ $Date: 3/28/05 10:32:18 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BRadioButtonMenuItem
  extends BToggleMenuItem
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BRadioButtonMenuItem(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRadioButtonMenuItem.class);

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
  public BRadioButtonMenuItem(String text, BAccelerator accelerator, boolean selected)
  {
    super(text, accelerator, selected);
  }

  /**
   * Construct a menu item with specified label text and selected state.
   */
  public BRadioButtonMenuItem(String text, boolean selected)
  {
    super(text, selected);
  }  

  /**
   * Construct a menu item with specified label text.
   */
  public BRadioButtonMenuItem(String text)
  {
    super(text);
  }

  /**
   * Construct a menu item with specified command.
   */
  public BRadioButtonMenuItem(ToggleCommand command)
  {
    super(command);
  }

  /**
   * No argument constructor.
   */
  public BRadioButtonMenuItem()
  {
  }

  @Override
  public boolean isRadioButtonItem()
  {
    return true;
  }

  ///////////////////////////////////////////////////////////
// Overrides
/////////////////////////////////////////////////////////// 

  /**
   * Override to paint the icon.
   */
  void paintImage(Graphics g)
  {
    Theme.menuItem().paintRadioBox(g, this, isSelected);
  }
  
  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/radioButtonMenuItem.png");
    
}
