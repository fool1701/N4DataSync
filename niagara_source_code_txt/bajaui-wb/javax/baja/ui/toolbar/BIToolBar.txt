/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.toolbar;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BAbstractButton;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;

/**
 * BIToolBar represents an interface for manipulating toolbar widget implementations
 * @author    Danesh Kamal
 * @creation  11/1/2013
 * @version   1
 * @since     Niagara 4.0
 */
@NiagaraType
public interface BIToolBar
    extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.toolbar.BIToolBar(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:35 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIToolBar.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Set the id of the toolbar.
   * @param id
   */
  public void setId(String id);
  
  /**
   * @deprecated use {@link BIToolBar#addButton(String, Command)} instead.
   * 
   * @param buttonName
   * @param command
   * @return the BAbstractButton instance bound to the command handler
   */
  @Deprecated
  public BAbstractButton add(String buttonName, Command command);

  /**
   * Add a button to the toolbar with the associated command handler.
   * 
   * @param buttonName
   * @param command
   * @return the BAbstractButton instance bound to the command handler
   */
  public BAbstractButton addButton(String buttonName, Command command);

  /**
   * @param buttonName
   * @return the BAbstractButton corresponding to the buttonName
   */
  public BAbstractButton getButton(String buttonName);

  /**
   * Remove a button keyed by name from the toolbar.
   * 
   * @param buttonName
   * @return the BAbstractButton instance corresponding to the buttonName
   */
  public BAbstractButton removeButton(String buttonName);

  /**
   * Remove all buttons from the toolbar.
   */
  public void removeAllButtons();
  /**
   * Add a separator to the toolbar.
   */
  public void addSeparator();

  /**
   * Remove consecutive separators between toolbar items.
   */
  public void removeConsecutiveSeparators();

  /**
   * Return the toolbar as a BWidget.
   * @return
   */
  public BWidget asWidget();
}
