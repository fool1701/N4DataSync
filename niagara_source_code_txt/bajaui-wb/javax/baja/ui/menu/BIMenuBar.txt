/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.menu;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;

/**
 * BIMenuBar represents an interface for managing a list of BIMenu elements
 * and also supports an embeddable quick search widget.
 *
 * @author Danesh Kamal
 * @creation  11/1/2013
 * @since Niagara 4.0
 *
 */
@NiagaraType
public interface BIMenuBar extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.menu.BIMenuBar(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIMenuBar.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Sets the id of the menu bar.
   * @param id
   */
  public void setId(String id);
  
  /**
   * Remove all menus from the menu bar
   */
  public void removeAllMenus();

  /**
   * Get the child menus
   * @return
   */
  public BIMenu[] getMenus();

  /**
   * Add a menu
   * @param menuName
   * @param menu
   */
  public void addMenu(String menuName, BIMenu menu);

  /**
   * Set a menu
   * @param menuName
   * @param menu
   */
  public void setMenu(String menuName, BIMenu menu);

  /**
   * Get a child menu by name
   * @param menuName
   * @return
   */
  public BIMenu getMenu(String menuName);

  /**
   * Remove a child menu by name
   * @param menuName
   * @return
   */
  public BIMenu removeMenu(String menuName);

  /**
   * Remove a child menu by reference
   * @param menu
   */
  public void removeMenu(BIMenu menu);

  /**
   * Add a quick search to this menu bar.
   * @param quickSearch the quick search widget to add
   */
  public void addQuickSearch(BWidget quickSearch);

  /**
   * Remove the quick search from this menu bar.
   */
  public void removeQuickSearch();

  /**
   * Return the menu bar as a BWidget
   * @return
   */
  public BWidget asWidget();
}
