/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.menu;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BISubMenuItem represents an interface for manipulating a BMenuItem that has a submenu
 * @author    Danesh Kamal
 * @creation  1/15/2014
 * @version   1
 * @since     Niagara 4.0
 */
@NiagaraType
public interface BISubMenuItem extends BIMenuItem
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.menu.BISubMenuItem(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BISubMenuItem.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Get the sub-menu for the menu item
   * @return
   */
  public BIMenu getSubMenu();

  /**
   * Set the sub-menu for the menu item
   * @param subMenu
   */
  public void setSubMenu(BIMenu subMenu);


}
