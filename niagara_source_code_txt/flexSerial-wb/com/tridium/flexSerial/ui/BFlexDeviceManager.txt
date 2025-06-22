/*
 * Copyright 2005 Tridium, All Rights Reserved.
 */
package com.tridium.flexSerial.ui;

import javax.baja.driver.*;
import javax.baja.driver.ui.device.*;
import javax.baja.gx.*;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.util.*;
import javax.baja.workbench.mgr.*;
import javax.baja.workbench.mgr.folder.*;

import com.tridium.flexSerial.*;

/**
 * BFlexDeviceManager
 *
 * @author    Andy Saunders
 * @creation  19-Apr-05
 * @version   $Revision$ $Date: 1/26/2005 6:27:07 PM$
 *          
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "flexSerial:FlexSerialNetwork", "flexSerial:FlexSerialDeviceFolder" }
  )
)
public class BFlexDeviceManager
  extends BDeviceManager
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.ui.BFlexDeviceManager(473270697)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexDeviceManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BFlexDeviceManager()
  {
  }
  
////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  protected MgrModel makeModel() { return new Model(this); }
  protected MgrController makeController() { return new Controller(this); }
                                        
////////////////////////////////////////////////////////////////
// Model
////////////////////////////////////////////////////////////////
  
  class Model extends DeviceModel
  {
    Model(BDeviceManager manager) { super(manager); }
    
    protected MgrColumn[] makeColumns()
    {        
      return cols;   
    }                                   
  }                                 
                               
////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////

  class Controller extends DeviceController
  {             
    Controller(BDeviceManager mgr) { super(mgr); }
  }                 
                         
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static Lexicon lex = Lexicon.make(BFlexDeviceManager.class); 
  
  // base class columns
  MgrColumn colName         = new MgrColumn.Name();
  MgrColumn colType         = new MgrColumn.Type();
  MgrColumn colDeviceExts   = new DeviceExtsColumn(new BFlexSerialDevice());
  MgrColumn colStatus       = new MgrColumn.Prop(BDevice.status);
  MgrColumn colEnabled      = new MgrColumn.Prop(BDevice.enabled, MgrColumn.EDITABLE | MgrColumn.UNSEEN);
  MgrColumn colHealth       = new MgrColumn.Prop(BDevice.health, 0);
  
  // axonDevice specific columns
  MgrColumn colAddress      = new MgrColumn.Prop(BFlexSerialDevice.address, MgrColumn.EDITABLE);
  
  MgrColumn[] cols = 
  { 
    colName, colType, colDeviceExts,
    colAddress,
    colStatus, colEnabled, colHealth, 
  };
  
}
