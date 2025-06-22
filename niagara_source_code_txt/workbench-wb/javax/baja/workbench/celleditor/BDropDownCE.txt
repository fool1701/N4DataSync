/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.celleditor;

import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BDropDown;
import javax.baja.ui.BWidget;
import javax.baja.ui.event.BWidgetEvent;

/**
 * BDropDownCE 
 *
 * @author    Mike Jarmy
 * @creation  08 May 07
 * @version   $Revision: 1$ $Date: 8/15/07 3:38:59 PM EDT$
 * @since     Baja 1.0
 */
 
@NiagaraType
@NiagaraAction(
  name = "dropDownModified",
  parameterType = "BWidgetEvent",
  defaultValue = "new BWidgetEvent()"
)
public abstract class BDropDownCE
  extends BWbCellEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.celleditor.BDropDownCE(3248649028)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "dropDownModified"

  /**
   * Slot for the {@code dropDownModified} action.
   * @see #dropDownModified(BWidgetEvent parameter)
   */
  public static final Action dropDownModified = newAction(0, new BWidgetEvent(), null);

  /**
   * Invoke the {@code dropDownModified} action.
   * @see #dropDownModified
   */
  public void dropDownModified(BWidgetEvent parameter) { invoke(dropDownModified, parameter, null); }

  //endregion Action "dropDownModified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDropDownCE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  /**
   * doSetReadonly
   */
  protected void doSetReadonly(boolean readonly)
  {
    getDropDown().setDropDownEnabled(!readonly);
    getDropDown().setEnabled(!readonly);
  }

////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////
  
  /**
   * doLayout
   */
  public void doLayout(BWidget[] children)
  {
    getDropDown().setBounds(0, 2, getWidth()-1, getHeight()-2);
  }

  /**
   * paint
   */
  public void paint(Graphics g)
  {
    paintBackground(g);

    super.paint(g);    
  }

////////////////////////////////////////////////////////////////
// actions
////////////////////////////////////////////////////////////////

  public void doDropDownModified(BWidgetEvent event)
  {
    setModified();
  }

////////////////////////////////////////////////////////////////
// access
////////////////////////////////////////////////////////////////

  public abstract BDropDown getDropDown();
}
