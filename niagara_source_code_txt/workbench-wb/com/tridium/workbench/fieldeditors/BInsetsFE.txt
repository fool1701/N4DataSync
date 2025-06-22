/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BInsets;
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
 * BInsetsFE 
 *
 * @author    Mike Jarmy
 * @creation  20 Jan 03
 * @version   $Revision: 5$ $Date: 3/23/05 11:51:44 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "gx:Insets"
  )
)
public class BInsetsFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BInsetsFE(1788657147)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BInsetsFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BInsetsFE()
  {
    BGridPane pane = new BGridPane(2);
    pane.add("lbl0", new BLabel("Top"));    pane.add("fld0", fldTop);
    pane.add("lbl3", new BLabel("Right"));  pane.add("fld3", fldRight);
    pane.add("lbl2", new BLabel("Bottom")); pane.add("fld2", fldBottom);
    pane.add("lbl1", new BLabel("Left"));   pane.add("fld1", fldLeft);
    
    linkTo("lk0", fldTop,    BTextField.textModified, setModified);
    linkTo("lk1", fldRight,  BTextField.textModified, setModified);
    linkTo("lk2", fldBottom, BTextField.textModified, setModified);    
    linkTo("lk3", fldLeft,   BTextField.textModified, setModified);
    
    setContent(pane);
  }

  protected void doSetReadonly(boolean readonly)
  {
    fldTop.setEditable(!readonly);
    fldRight.setEditable(!readonly);
    fldBottom.setEditable(!readonly);
    fldLeft.setEditable(!readonly);
  }
  
  protected void doLoadValue(BObject value, Context cx)
  {
    BInsets insets = (BInsets) value;

    fldTop.setText(Integer.toString((int)insets.top));
    fldRight.setText(Integer.toString((int)insets.right));
    fldBottom.setText(Integer.toString((int)insets.bottom));    
    fldLeft.setText(Integer.toString((int)insets.left));
  }

  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    try
    {
      return BInsets.make(
        Integer.parseInt(fldTop.getText()),
        Integer.parseInt(fldRight.getText()),
        Integer.parseInt(fldBottom.getText()),        
        Integer.parseInt(fldLeft.getText()));
    }
    catch (NumberFormatException e)
    {
      return BInsets.NULL;
    }
  }
  
  private BTextField fldTop    = new BTextField("", 5);
  private BTextField fldRight  = new BTextField("", 5);
  private BTextField fldBottom = new BTextField("", 5);
  private BTextField fldLeft   = new BTextField("", 5);
}
