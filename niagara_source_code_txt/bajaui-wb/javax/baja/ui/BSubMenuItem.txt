/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.event.BInputEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.menu.BIMenu;
import javax.baja.ui.menu.BISubMenuItem;

import com.tridium.ui.theme.MenuItemTheme;

/**
 * BSubMenuItem a menu item which when selected 
 * displays a submenu.
 *
 * @author    Brian Frank       
 * @creation  2 Dec 00
 * @version   $Revision: 21$ $Date: 5/9/05 3:40:26 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 This is the submenu that is displayed when
 this menu item is selected.
 */
@NiagaraProperty(
  name = "menu",
  type = "BMenu",
  defaultValue = "new BMenu()"
)
public class BSubMenuItem
  extends BMenuItem implements BISubMenuItem
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BSubMenuItem(3391843382)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "menu"

  /**
   * Slot for the {@code menu} property.
   * This is the submenu that is displayed when
   * this menu item is selected.
   * @see #getMenu
   * @see #setMenu
   */
  public static final Property menu = newProperty(0, new BMenu(), null);

  /**
   * Get the {@code menu} property.
   * This is the submenu that is displayed when
   * this menu item is selected.
   * @see #menu
   */
  public BMenu getMenu() { return (BMenu)get(menu); }

  /**
   * Set the {@code menu} property.
   * This is the submenu that is displayed when
   * this menu item is selected.
   * @see #menu
   */
  public void setMenu(BMenu v) { set(menu, v, null); }

  //endregion Property "menu"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSubMenuItem.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a menu item with specified label 
   * text and sub menu.
   */
  public BSubMenuItem(String text, BIMenu menu)
  {
    super(text);

    if(menu instanceof BMenu)
      setMenu((BMenu)menu);

    if (!menu.isEnabled()) setEnabled(false);
  }

  /**
   * Construct with the specified sub menu, and
   * set the item's text to the menu's text property.
   */
  public BSubMenuItem(BIMenu menu)
  {
    super(menu.getText());

    if(menu instanceof BMenu)
      setMenu((BMenu)menu);

    if (!menu.isEnabled()) setEnabled(false);
  }

  /**
   * Construct a menu item with specified label text.
   */
  public BSubMenuItem(String text)
  {
    super(text);
  }

  /**
   * No argument constructor.
   */
  public BSubMenuItem()
  {
  }

////////////////////////////////////////////////////////////////
//BISubMenuItem
////////////////////////////////////////////////////////////////

  @Override
  public BIMenu getSubMenu()
  {
    return getMenu();
  }

  @Override
  public void setSubMenu(BIMenu subMenu)
  {
    if(subMenu instanceof BMenu)
      setMenu((BMenu)subMenu);
  }

////////////////////////////////////////////////////////////////
// Selection
////////////////////////////////////////////////////////////////  
  
  /**
   * Click opens the submen.
   */
  void doClick(BInputEvent event) 
  { 
    stopTimer();
    openMenu(); 
  }
  
  /**
   * Open the popup menu.
   */
  void openMenu() 
  { 
    if (getMenu().isOpen()) return;
    closeSiblings();
    getMenu().open(this, getWidth(), 0);
  }
  
  /**
   * Close the popup menu.
   */
  void closeMenu() 
  { 
    getMenu().close(); 
  }

  /**
   * Timer expired.
   */
  void timerExpired()
  {
    if (isOver) openMenu();
    else closeMenu();
  }
  
////////////////////////////////////////////////////////////////
// Mouse Eventing
////////////////////////////////////////////////////////////////  

  public void mouseReleased(BMouseEvent event)
  {
    if (contains(event.getX(), event.getY()))
      doClick(event);
  }  
    
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Override point to make paint the item's 
   * background using the specified theme.
   */
  void paintBackground(Graphics g, MenuItemTheme theme)
  {       
    super.paintBackground(g, theme);
    theme.paintSubMenuArrow(g, this, isSelected);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  
}
