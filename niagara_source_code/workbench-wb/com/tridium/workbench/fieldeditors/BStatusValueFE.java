/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BCheckBox;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.ToggleCommand;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.pane.BExpandablePane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * Plugin for BStatusValues.  This plugin displays
 * a summary field with the string value of the status
 * element.  A user may optionally expand the plugin
 * to manually enter a value or set the null flag.
 *
 * @author    Brian Frank on 6 Sept 01
 * @version   $Revision: 10$ $Date: 3/28/05 1:40:36 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:StatusValue"
  )
)
public class BStatusValueFE
  extends BWbFieldEditor
  implements BWbFieldEditor.IDialogContentProvider
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BStatusValueFE(1500991126)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusValueFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  

////////////////////////////////////////////////////////////////
// Construction
////////////////////////////////////////////////////////////////

  public BStatusValueFE()
  {
    // init null checkbox
    nullToggle = new NullToggle(this);
    isNull.setCommand(nullToggle, false, false);
    
    // summary is a simple readonly text field
    summary = new BTextField("", 40, false);
    
    // edit pane will contain null checkbox and value 
    // editor plugin; initialized in doLoadValue()
    editPane = new BGridPane(2);
    editPane.setHalign(BHalign.left);
    
    // use an expander pane for the content
    expander = new BExpandablePane(summary, editPane);
    setContent(expander);
  }
  
////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////  
  
  protected void doSetReadonly(boolean readonly)
  {
    // hide expander if plugin is readonly
    expander.setExpanderVisible(!readonly);    
  }  

  protected void doLoadValue(BObject raw, Context cx)
    throws Exception
  {
    BStatusValue se = (BStatusValue)raw;
    BObject v = se.getValueValue();
    
    // first time thru, do some initialization
    if (valueEditor == null)
    {
      // build a plugin for the value
      valueEditor = BWbFieldEditor.makeFor(v);
      
      // chain action and modified events 
      linkTo("lk0", valueEditor, BWbFieldEditor.pluginModified, setModified);
      linkTo("lk1", valueEditor, BWbFieldEditor.actionPerformed, actionPerformed);    
      
      // changes on null checkbox result in modified event
      linkTo("lk2", isNull, BCheckBox.selected, setModified);
      
      // put it all together
      editPane.add("null", isNull);
      editPane.add("editor", valueEditor);
    }
    
    // load value editor
    valueEditor.loadValue(v, cx);
    
    // load null checkbox
    isNull.setSelected(se.getStatus().isNull());
    
    // load summary textfield
    summary.setText(se.toString(cx));
  }
  
  protected BObject doSaveValue(BObject raw, Context cx)
    throws Exception
  {
    // save to object passed in
    BStatusValue se = (BStatusValue)raw;
    
    // save editor to value
    BValue v = (BValue)valueEditor.saveValue(cx);
    
    // save null check to status
    BStatus status = isNull.isSelected() ? BStatus.nullStatus : BStatus.ok;
    
    // important to pass-thru context in sets
    se.set(se.getValueProperty(), v, cx);
    se.set(BStatusValue.status, status, cx);
    
    return se;
  }

  @Override
  public BWidget getDialogContent()
  {
    expander.setExpanded(true);
    return this;
  }

////////////////////////////////////////////////////////////////
// NullToggle
////////////////////////////////////////////////////////////////  

  class NullToggle extends ToggleCommand
  {
    NullToggle(BWidget owner) { super(owner, "null"); }
    
    public void setSelected(boolean s)
    { 
      super.setSelected(s); 
      
      // set the value plugin to readonly if null selected
      if (valueEditor != null) valueEditor.setReadonly(s);
    }
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  protected BExpandablePane expander;
  protected BTextField summary;
  protected BGridPane editPane;
  protected BCheckBox isNull = new BCheckBox("null");
  protected NullToggle nullToggle;  
  protected BWbFieldEditor valueEditor;
  
}
