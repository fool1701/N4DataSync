/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.menu;

import javax.baja.gx.BImage;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BAccelerator;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;

/**
 * BIMenuItem represents an interface for manipulating menu items within a BIMenu instance
 * @author    Danesh Kamal
 * @creation  11/1/2013
 * @version   1
 * @since     Niagara 4.0
 */
@NiagaraType
public interface BIMenuItem extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.menu.BIMenuItem(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIMenuItem.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Get the name by which this item is referenced (not necessarily equal to getText()
   * @return
   */
  public String getName();

  /**
   * Get the menu item text
   * @return
   */
  public String getText();

  /**
   * Get the menu item icon image
   * @return
   */
  public BImage getImage();

  /**
   * Get the accelerator key combination for the menu item
   * @return
   */
  public BAccelerator getAccelerator();

  /**
   * Get the command handler for the menu item
   * @return
   */
  public Command getCommand();

  /**
   * Return the menu item as a BWidget
   * @return
   */
  public BWidget asWidget();
  
}
