/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.celleditor;


import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BDropDown;
import javax.baja.ui.BTextDropDown;
import javax.baja.ui.event.BWidgetEvent;
import javax.baja.ui.text.BTextEditor;

import com.tridium.workbench.cellmini.BMiniTextDropDown;

/**
 * BTextDropDownCE 
 *
 * @author    Mike Jarmy
 * @creation  08 May 07
 * @version   $Revision: 1$ $Date: 8/15/07 3:38:59 PM EDT$
 * @since     Baja 1.0
 */
 
@NiagaraType
@NiagaraAction(
  name = "textModified",
  parameterType = "BWidgetEvent",
  defaultValue = "new BWidgetEvent()"
)
public abstract class BTextDropDownCE
  extends BDropDownCE
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.celleditor.BTextDropDownCE(2698281053)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "textModified"

  /**
   * Slot for the {@code textModified} action.
   * @see #textModified(BWidgetEvent parameter)
   */
  public static final Action textModified = newAction(0, new BWidgetEvent(), null);

  /**
   * Invoke the {@code textModified} action.
   * @see #textModified
   */
  public void textModified(BWidgetEvent parameter) { invoke(textModified, parameter, null); }

  //endregion Action "textModified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTextDropDownCE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTextDropDownCE()
  {
    add("textDrop", textDrop);
    linkTo("linkMod", textDrop, BDropDown.valueModified, dropDownModified);
    linkTo("linkText", textDrop.getEditor(), BTextEditor.textModified, textModified);
  }

////////////////////////////////////////////////////////////////
// BDropDownCE
////////////////////////////////////////////////////////////////

  public BDropDown getDropDown() { return textDrop; } 

  public BTextDropDown getTextDropDown() { return textDrop; }

////////////////////////////////////////////////////////////////
// actions
////////////////////////////////////////////////////////////////

  public void doTextModified(BWidgetEvent event)
  {
    setModified();
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BMiniTextDropDown textDrop = new BMiniTextDropDown();
}
