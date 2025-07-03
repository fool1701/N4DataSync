/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInteger;
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
import javax.baja.ui.pane.BGridPane;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * FieldEditor for port on BIpAddress
 *
 * @author Robert Adams
 * @creation 2 Oct 2012
 * @since Niagara 3.7
 */
@NiagaraType
public class BIpPortFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.ui.BIpPortFE(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:37 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BIpPortFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Construction
////////////////////////////////////////////////////////////////

  public BIpPortFE()
  {
    // init null checkbox
    toggle = new UnspecifyToggle(this);
    isUnspecified.setCommand(toggle, false, false);

    // edit pane will contain isSpecified checkbox 
    // editor plugin; initialized in doLoadValue()
    editPane = new BGridPane(2);
    editPane.setHalign(BHalign.left);
    setContent(editPane);
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////  

  @Override
  protected void doSetReadonly(boolean readonly)
  {
    // grey edit pane if readonly
    editPane.setVisible(!readonly);
  }

  @Override
  protected void doLoadValue(BObject raw, Context cx)
    throws Exception
  {
    BInteger bint = (BInteger)raw;
    int n = bint.getInt();

    // first time thru, do some initialization
    if (valueEditor == null)
    {
      // build a plugin for the value
      valueEditor = BWbFieldEditor.makeFor(bint);

      // chain action and modified events 
      linkTo("lk0", valueEditor, BWbFieldEditor.pluginModified, setModified);
      linkTo("lk1", valueEditor, BWbFieldEditor.actionPerformed, actionPerformed);

      // changes on null checkbox result in modified event
      linkTo("lk2", isUnspecified, BCheckBox.selected, setModified);

      // put it all together
      editPane.add("unspecified", isUnspecified);
      editPane.add("editor", valueEditor);
    }

    // load value editor
    valueEditor.loadValue(bint, cx);

    // load null checkbox
    isUnspecified.setSelected(n < 0);
  }

  @Override
  protected BObject doSaveValue(BObject raw, Context cx)
    throws Exception
  {
    // save editor to value
    BValue v = (BValue)valueEditor.saveValue(cx);

    // if unspecified select force value to -1
    boolean isU = isUnspecified.isSelected() ? true : false;
    if (isU)
    {
      return BInteger.make(-1);
    }

    isUnspecified.setSelected(((BInteger)v).getInt() < 0);


    return v;
  }

////////////////////////////////////////////////////////////////
// UnspecifyToggle
////////////////////////////////////////////////////////////////  

  class UnspecifyToggle extends ToggleCommand
  {
    UnspecifyToggle(BWidget owner)
    {
      super(owner, "unspecified");
    }

    @Override
    public void setSelected(boolean s)
    {
      super.setSelected(s);

      // set the value plugin to readonly if null selected
      if (valueEditor != null)
      {
        valueEditor.setReadonly(s);
      }
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  //  protected BExpandablePane expander;
  protected BTextField summary;
  protected BGridPane editPane;
  protected BCheckBox isUnspecified = new BCheckBox("unspecified");
  protected UnspecifyToggle toggle;
  protected BWbFieldEditor valueEditor;
}
