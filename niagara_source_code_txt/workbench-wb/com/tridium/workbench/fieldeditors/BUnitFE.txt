/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.options.BMruButton;
import javax.baja.ui.pane.BGridPane;
import javax.baja.units.BUnit;
import javax.baja.units.UnitDatabase;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BUnitFE allows viewing and editing of a BUnit.
 *
 * @author    Brian Frank       
 * @creation  22 Jan 01
 * @version   $Revision: 16$ $Date: 8/3/10 4:43:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:Unit"
  )
)
@NiagaraAction(
  name = "qboxChanged"
)
public class BUnitFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BUnitFE(113216704)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "qboxChanged"

  /**
   * Slot for the {@code qboxChanged} action.
   * @see #qboxChanged()
   */
  public static final Action qboxChanged = newAction(0, null);

  /**
   * Invoke the {@code qboxChanged} action.
   * @see #qboxChanged
   */
  public void qboxChanged() { invoke(qboxChanged, null, null); }

  //endregion Action "qboxChanged"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUnitFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  
  public BUnitFE()
  {
    history = new BMruButton("unitFE");
    history.setMruController(new MruController());
    history.setButtonStyle(BButtonStyle.toolBar);

    BGridPane pane = new BGridPane(3);
    pane.setColumnAlign(BHalign.fill);
    pane.add("qbox", qbox);
    pane.add("ubox", ubox);
    pane.add("history", history);
    setContent(pane);
    linkTo("lk0", qbox, BListDropDown.actionPerformed, actionPerformed);
    linkTo("lk1", qbox, BListDropDown.valueModified,   qboxChanged);
    linkTo("lk3", qbox, BListDropDown.valueModified,   setModified);
    linkTo("lk4", ubox, BListDropDown.valueModified,   setModified);
    linkTo("lk5", ubox, BListDropDown.actionPerformed, actionPerformed);
    
    UnitDatabase.Quantity[] q = UnitDatabase.getDefault().getQuantities();
    for(int i=0; i<q.length; ++i)
      qbox.getList().addItem(q[i]);
    
    qbox.setSelectedIndex(0);  
  }

  protected void doSetReadonly(boolean readonly)
  {
    qbox.setDropDownEnabled(!readonly);
    ubox.setDropDownEnabled(!readonly);
    history.setEnabled(!readonly);
  }
  
  protected void doLoadValue(BObject value, Context cx)
  {
    BUnit unit = (BUnit)value;
    UnitDatabase.Quantity q = UnitDatabase.getDefault().getQuantity(unit);
    if (q == null) q = UnitDatabase.getDefault().getQuantities()[0];

    loading = unit;
    qbox.setSelectedItem(null);
    qbox.setSelectedItem(q);
    loading = null;
  }

  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    UnitEntry entry = (UnitEntry)ubox.getSelectedItem();
    history.getMruOptions().save(entry.unit.encodeToString());
    return entry.unit;
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////  

  public void doQboxChanged()
  {
    int def = -1;
    ubox.getList().removeAllItems();

    UnitDatabase.Quantity q;
    q = (UnitDatabase.Quantity)qbox.getSelectedItem();
    if (q != null)
    {
      BUnit[] u = q.getUnits();
      for(int i=0; i<u.length; ++i)
      {      
        ubox.getList().addItem(new UnitEntry(u[i]));
        if (u[i] == loading) def = i;
      }
      if(def==-1 && loading!=null)
      {
        // unit not found, must be custom
        ubox.getList().insertItem(0,new UnitEntry(loading));
        def = 0;
      }  
      ubox.setSelectedIndex(def);
    }
  }

////////////////////////////////////////////////////////////////
// UnitEntry
////////////////////////////////////////////////////////////////  

  static class UnitEntry
  {
    UnitEntry(BUnit unit)
    {
      this.unit = unit;
      this.string = unit.getUnitName() + " (" + unit.getSymbol() + ")";
    }
    
    public String toString() { return string; }
    
    BUnit unit;
    String string;
  }

////////////////////////////////////////////////////////////////
// MruController
////////////////////////////////////////////////////////////////
  
  class MruController extends BMruButton.MruController
  {
    public String toDisplayString(String value) 
    { 
      try
      {
        BUnit unit = (BUnit)BUnit.DEFAULT.decodeFromString(value);
        return unit.getUnitName() + " (" + unit.getSymbol() + ")";
      }
      catch (Exception e) { e.printStackTrace(); }
      return value;
    }

    public void select(String value)
    {
      try
      {
        loadValue(BUnit.DEFAULT.decodeFromString(value), getCurrentContext());
        setModified();
      }
      catch (Exception e) { e.printStackTrace(); }
    }
  }
  
////////////////////////////////////////////////////////////////
// Test Driver
////////////////////////////////////////////////////////////////  

  /*
  public static void main(String[] args)
    throws Exception
  {
    BUnitPlugin p = new BUnitPlugin();
    
    BGridPane pane = new BGridPane();
    pane.add("up", p);
    
    BFrame frame = new BFrame("Test", pane);
    frame.setScreenBounds(100,100,500,500);
    frame.open();
  }
  */
    
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private BListDropDown qbox = new BListDropDown();
  private BListDropDown ubox = new BListDropDown();
  private BMruButton history;
  private BUnit loading;
}
