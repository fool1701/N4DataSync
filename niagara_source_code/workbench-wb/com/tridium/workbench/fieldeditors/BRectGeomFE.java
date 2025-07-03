/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BRectGeom;
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
 * BRectGeomFE 
 *
 * @author    Andy Jarmy
 * @creation  20 Jan 03
 * @version   $Revision: 4$ $Date: 4/1/04 5:37:12 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "gx:RectGeom"
  )
)
public class BRectGeomFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BRectGeomFE(3505680837)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRectGeomFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BRectGeomFE()
  {
    BGridPane pane = new BGridPane(2);
    pane.add(null, new BLabel("x"));      
    pane.add(null, x);
    
    pane.add(null, new BLabel("y"));      
    pane.add(null, y);
    
    pane.add(null, new BLabel("width"));  
    pane.add(null, width);
    
    pane.add(null, new BLabel("height")); 
    pane.add(null, height);
    
    linkTo(null, x, BTextField.textModified, setModified);
    linkTo(null, y, BTextField.textModified, setModified);
    linkTo(null, width,  BTextField.textModified, setModified);
    linkTo(null, height, BTextField.textModified, setModified);
    
    setContent(pane);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {
    x.setEditable(!readonly);
    y.setEditable(!readonly);
    width.setEditable(!readonly);
    height.setEditable(!readonly);
  }
  
  protected void doLoadValue(BObject value, Context cx)
  {
    BRectGeom rect = (BRectGeom) value;

    x.setText(Integer.toString((int)rect.x));
    y.setText(Integer.toString((int)rect.y));
    width.setText(Integer.toString((int)rect.width));
    height.setText(Integer.toString((int)rect.height));
  }

  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    try
    {
      return BRectGeom.make(
        Integer.parseInt(x.getText()),
        Integer.parseInt(y.getText()),
        Integer.parseInt(width.getText()),
        Integer.parseInt(height.getText()));
    }
    catch (NumberFormatException e) 
    {
      return BRectGeom.NULL;
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private BTextField x = new BTextField("", 5);
  private BTextField y = new BTextField("", 5);
  private BTextField width  = new BTextField("", 5);
  private BTextField height = new BTextField("", 5);    
}
