/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.menu;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.ToggleCommandGroup;

/**
 * BIToggleMenuItem represents an interface for manipulating menu items within a BIMenu
 * instance that support the notion of toggling (e.g. radio buttons or checkboxes)
 * @author    Danesh Kamal
 * @creation  11/1/2013
 * @version   1
 * @since     Niagara 4.0
 */
@NiagaraType
public interface BIToggleMenuItem
    extends BIMenuItem
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.menu.BIToggleMenuItem(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIToggleMenuItem.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Return the toggle group to which this toggle item belongs
   * @return the toggle group
   */
  @SuppressWarnings("rawtypes")
  public ToggleCommandGroup getToggleGroup();

  /**
   * @return whether the menu item is selected
   */
  public boolean isSelected();

  /**
   * @return whether the menu item is a check box toggle item
   */
  public boolean isCheckBoxItem();

  /**
   * @return whether the menu item is a radio button toggle item
   */
  public boolean isRadioButtonItem();

} 
