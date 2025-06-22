/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.sidebar;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;

/**
 * BIWbSideBar represents an interface to installed sidebars in workbench
 * @author Danesh Kamal on 1/19/14
 * @since Niagara 4.0
 */
@NiagaraType
public interface BIWbSideBar extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.sidebar.BIWbSideBar(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:49 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIWbSideBar.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  /**
   * Get an array of TypeInfos for all the implementations
   * of BIWbSideBar currently installed on the system.
   */
  public TypeInfo[] getInstalledSideBars();

  /**
   * @return true if this sidebar should have a 'Close' command.
   */
  public boolean hasCloseCommand();

  /**
   * This callback is invoked when the active view is
   * modified via a hyperlink operation.
   */
  public void activeViewChanged();

  /**
   * Get the sidebar label
   * @return
   */
  public String getLabel();

  /**
   * Get the sidebar icon
   * @return
   */
  public BIcon getIcon();

  /**
   * Return the sidebar as a BWidget
   * @return
   */
  public BWidget asWidget();

}
