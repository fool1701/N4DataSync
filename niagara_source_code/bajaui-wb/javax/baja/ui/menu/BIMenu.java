/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.menu;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;

/**
 * BIMenu represents an interface for manipulating menu widget implementations
 * @author    Danesh Kamal
 * @creation  11/1/2013
 * @version   1
 * @since     Niagara 4.0
 */
@NiagaraType
public interface BIMenu extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.menu.BIMenu(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIMenu.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Returns whether the menu is enabled
   * @return
   */
  public boolean isEnabled();

  /**
   * Get the list of items in the menu
   * @return
   */
  public BIMenuItem[] getMenuItems();

  /**
   * Get the number of items in the menu
   * @return
   */
  public int getItemCount();

  /**
   * Add a menu item by reference, keyed by menuItemName
   * @param menuItemName
   * @param menuItem
   */
  public void addItem(String menuItemName, BIMenuItem menuItem);

  /**
   * Create and add a menu item with the associated menuItemCommand
   * @param menuItemName
   * @param menuItemCommand
   * @return the newly added menu item
   */
  public BIMenuItem addItem(String menuItemName, Command menuItemCommand);

  /**
   * Append a menu item to the start of the menu
   * @param menuItemName
   * @param menuItem
   */
  public void addItemToFront(String menuItemName, BIMenuItem menuItem);

  /**
   * Remove an item from the menu by its menu name key
   * @param menuItemName
   * @return the menu item that was removed
   */
  public BIMenuItem removeItem(String menuItemName);

  /**
   * Get a reference to a menu item by its menu name key
   * @param menuItemName
   * @return the menu item corresonding to menuItemName
   */
  public BIMenuItem getItem(String menuItemName);

  /**
   * Retains only the menu items with names contained in the menuItems array
   * @param menuItems
   */
  public void retainItems(String[] menuItems);

  /**
   * Get the sub-menu associated with a named menu item (if applicable)
   * @param menuItemName
   * @return the sub-menu for the menu item or null if the menu item has no sub menu
   */
  public BIMenu getSubMenu(String menuItemName);

  /**
   * Remove the sub-menu associated with a named menu item (if applicable)
   * @param menuItemName
   * @return the sub-menu removed for the menu item or null if the menu item has no sub menu
   */
  public BIMenu removeSubMenu(String menuItemName);

  /**
   * Get the name key used to reference the menu in BIMenu operations
   * @return menu name key
   */
  public String getName();

  /**
   * Return the text to display for the menu
   * @return
   */
  public String getText();

  /**
   * Add a seperator between menu items
   */
  public void addSeparator();

  /**
   * Append a separator to the start of the menu
   */
  public void addSeparatorToFront();

  /**
   * Removed multiple consective separators
   */
  public void removeConsecutiveSeparators();

  /**
   * Callback to update the menu contents before the menu is opened
   */
  public void update();

  /**
   * @return whether the menu is constructed dynamically
   */
  public boolean isDynamic();

  /**
   * Return the menu as a BWidget
   * @return the menu cast as a BWidget instance
   */
  public BWidget asWidget();
}
