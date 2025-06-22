/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.util.Arrays;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.timezone.BTimeZone;
import javax.baja.timezone.TimeZoneDatabase;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BTextDropDown;
import javax.baja.workbench.CannotSaveException;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * Field editor for selecting a BTimeZone
 * 
 * @author    Matt Boon, Lee Adcock
 * @creation  5 Oct 04
 * @since     Niagara 3.7
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:TimeZone"
  )
)
public class BTimeZoneFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BTimeZoneFE(2787669435)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTimeZoneFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  public BTimeZoneFE()
  {
    setContent(field);
    linkTo("la", field, BTextDropDown.valueModified,   setModified);
    linkTo("lb", field, BTextDropDown.actionPerformed, actionPerformed);

    field.getList().addItem(BTimeZone.NULL);
    BTimeZone[] zones = TimeZoneDatabase.get().getTimeZones();
    Arrays.sort(zones, BTimeZone.OFFSET_COMPARATOR);

    for (BTimeZone zone : zones)
    {
      field.getList().addItem(zone);
    }
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////
  
  protected void doLoadValue(BObject value, Context cx)
  {
    field.getList().getSelection().deselectAll();
    if(field.getList().indexOfItem(value)==-1)
      field.getList().addItem(value);
    field.getList().getSelection().select(field.getList().indexOfItem(value));
  }

  protected void doSetReadonly(boolean readonly)
  {
    field.getDisplayWidget().setEnabled(!readonly);
    field.getDropDownWidget().setEnabled(!readonly);
    field.setDropDownEnabled(!readonly);
  }

  protected BObject doSaveValue(BObject value, Context cx)
    throws CannotSaveException, Exception
  {
    return (BTimeZone)field.getList().getSelectedItem();
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private BListDropDown field = new BListDropDown();
}
