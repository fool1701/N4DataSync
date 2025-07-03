/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BPen;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BTextField;
import javax.baja.ui.list.BList;
import javax.baja.ui.pane.BGridPane;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BPenFE edits a BPen.
 *
 * @author    Andy Frank
 * @creation  05 May 03
 * @version   $Revision: 4$ $Date: 3/28/05 1:40:36 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "gx:Pen"
  )
)
public class BPenFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BPenFE(949868765)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPenFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BPenFE()
  {
    width = new BTextField("", 5);
    cap   = new BListDropDown();
    join  = new BListDropDown();
    dash  = new BListDropDown();
    
    BList list = cap.getList();
    list.addItem("Cap Butt");
    list.addItem("Cap Round");
    list.addItem("Cap Square");

    list = join.getList();
    list.addItem("Join Miter");
    list.addItem("Join Bevel");
    list.addItem("Join Round");
    
    list = dash.getList();
    list.addItem("Solid");
    list.addItem("Dotted");
    list.addItem("Dashed");
    
    BGridPane grid = new BGridPane(4);
    grid.add(null, width);
    grid.add(null, dash);
    grid.add(null, cap);
    grid.add(null, join);    
    setContent(grid);

    linkTo(width, BTextField.textModified,     setModified);
    linkTo(cap,   BListDropDown.valueModified, setModified);
    linkTo(join,  BListDropDown.valueModified, setModified);
    linkTo(dash,  BListDropDown.valueModified, setModified);
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {
    width.setEditable(!readonly);
    cap.setDropDownEnabled(!readonly);
    join.setDropDownEnabled(!readonly);
    dash.setDropDownEnabled(!readonly);
  }

  protected void doLoadValue(BObject value, Context cx)
  {
    BPen pen = (BPen)value;
    
    width.setText(Double.toString(pen.getWidth()));
    
    switch (pen.getCap())
    {
      case BPen.CAP_BUTT:   cap.setSelectedIndex(0); break;
      case BPen.CAP_ROUND:  cap.setSelectedIndex(1); break;
      case BPen.CAP_SQUARE: cap.setSelectedIndex(2); break;
      default: throw new IllegalStateException();
    }

    switch (pen.getJoin())
    {
      case BPen.JOIN_MITER: join.setSelectedIndex(0); break;
      case BPen.JOIN_BEVEL: join.setSelectedIndex(1); break;
      case BPen.JOIN_ROUND: join.setSelectedIndex(2); break;      
      default: throw new IllegalStateException();
    }
    
    double[] d = pen.getDash();
    if (d.length == 0) 
      dash.setSelectedIndex(0);
    else if (d.length == 2 && d[0] == dotted[0] && d[1] == dotted[1]) 
      dash.setSelectedIndex(1);
    else if (d.length == 2 && d[0] == dashed[0] && d[1] == dashed[1]) 
      dash.setSelectedIndex(2);
    else 
      throw new IllegalStateException("Custom dash not implemented.");
  }

  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {  
    double w = Double.parseDouble(width.getText());
    int c, j;
    double[] d;
    
    switch (cap.getSelectedIndex())
    {
      case 0: c = BPen.CAP_BUTT;   break;
      case 1: c = BPen.CAP_ROUND;  break;
      case 2: c = BPen.CAP_SQUARE; break;
      default: throw new IllegalStateException();
    }
    
    switch (join.getSelectedIndex())
    {
      case 0: j = BPen.JOIN_MITER; break;
      case 1: j = BPen.JOIN_BEVEL; break;
      case 2: j = BPen.JOIN_ROUND; break;
      default: throw new IllegalStateException();
    }
    
    switch (dash.getSelectedIndex())
    {
      case 0: d = null; break;
      case 1: d = dotted; break;
      case 2: d = dashed; break;
      default: throw new IllegalStateException();
    }
    
    return BPen.make(w, c, j, d);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private static final double[] dotted = {2, 2};
  private static final double[] dashed = {8, 2};

  private BTextField width;
  private BListDropDown cap;
  private BListDropDown join;
  private BListDropDown dash;
}
