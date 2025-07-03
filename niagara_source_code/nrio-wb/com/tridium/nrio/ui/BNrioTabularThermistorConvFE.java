/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.ui;

import javax.baja.gx.BImage;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BLabel;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.pane.BGridPane;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.nrio.conv.BNrioTabularThermistorConversion;

/**
 * BTabularThermistorConvFE
 *
 * @author    Bill Smith
 * @creation  9 Feb 05
 * @version   $Revision: 3$ $Date: 2/9/2005 5:49:43 PM$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "nrio:NrioTabularThermistorConversion"
  )
)
public class BNrioTabularThermistorConvFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.ui.BNrioTabularThermistorConvFE(1098611204)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioTabularThermistorConvFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BNrioTabularThermistorConvFE()
  {    
    TableCmd tablecmd = new TableCmd(); 
    button = new BButton(tablecmd);
    button.setButtonStyle(BButtonStyle.toolBar);
    //linkTo(button, button.actionPerformed, actionPerformed);
    
    desc = new BLabel();
    
    BGridPane grid = new BGridPane(2);
    grid.add("a", button);
    grid.add("b", desc);
    grid.setStretchColumn(1);
    grid.setColumnAlign(BHalign.fill);

    setContent(grid);
  }

////////////////////////////////////////////////////////////////
// WbEditor
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {
    button.setEnabled(!readonly);
  }
  
  protected void doLoadValue(BObject value, Context cx)
  {                                 
    conversion = (BNrioTabularThermistorConversion)value;
    desc.setText("[" + conversion.getDescription() + "]");
  }

  protected BObject doSaveValue(BObject value, Context cx)        
    throws Exception
  {                                             
    return conversion;
  }
  
  
  class TableCmd extends Command
  {
    public TableCmd() 
    { 
       super(BNrioTabularThermistorConvFE.this, null, buttonIcon, null, null);
    }

    public CommandArtifact doInvoke()
    {
      BNrioTabularThermistorConversion conv = BNrioTabularThermistorDialog.show(BNrioTabularThermistorConvFE.this, conversion);
      if (conv != null)
      {
        conversion = conv;
        desc.setText("[" + conversion.getDescription() + "]");
        setModified();
      }
      return null;
    }
  }
  

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static BImage buttonIcon = BImage.make("module://icons/x16/edit.png");

  BButton button;
  BNrioTabularThermistorConversion conversion;
  BLabel desc;
}
