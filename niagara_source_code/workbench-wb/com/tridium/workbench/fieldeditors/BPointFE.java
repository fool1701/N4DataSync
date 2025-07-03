/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BPoint;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.BTextField;
import javax.baja.ui.pane.BGridPane;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BPointFE 
 *
 * @author    Mike Jarmy
 * @creation  20 Jan 03
 * @version   $Revision: 4$ $Date: 3/23/05 11:51:45 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "gx:Point"
  )
)
public class BPointFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BPointFE(925012270)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPointFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BPointFE()
  {
    BGridPane pane = new BGridPane(2);
    pane.add("lbl0", new BLabel("X")); pane.add("fld0", fldX);
    pane.add("lbl1", new BLabel("Y")); pane.add("fld1", fldY);
    
    linkTo("lk0", fldX, BTextField.textModified, setModified);
    linkTo("lk1", fldY, BTextField.textModified, setModified);
    
    setContent(pane);
  }

  protected void doSetReadonly(boolean readonly)
  {
    fldX.setEditable(!readonly);
    fldY.setEditable(!readonly);
  }
  
  protected void doLoadValue(BObject value, Context cx)
  {
    BPoint point = (BPoint) value;

    fldX.setText(Integer.toString((int)point.x));
    fldY.setText(Integer.toString((int)point.y));
  }

  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    try
    {
      return BPoint.make(
        Integer.parseInt(fldX.getText()),
        Integer.parseInt(fldY.getText()));
    }
    catch (NumberFormatException e)
    {
      return BPoint.NULL;
    }
  }
  
  private BTextField fldX = new BTextField("", 5);
  private BTextField fldY = new BTextField("", 5);
}
