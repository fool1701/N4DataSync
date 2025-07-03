/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.pane.BEdgePane;

/**
 * BWbLocatorBar is used by a BWbShell to visualization the
 * location/ord of the active view.
 *
 * @author    Brian Frank       
 * @creation  21 Jul 04
 * @version   $Revision: 2$ $Date: 3/28/05 1:40:55 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BWbLocatorBar
  extends BEdgePane   
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.BWbLocatorBar(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbLocatorBar.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



  
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * This callback is invoked when the active view is 
   * modified via a hyperlink operation.
   */
  public void activeViewChanged()
  {
  }

}
