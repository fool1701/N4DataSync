/*
 * Copyright 2005 Tridium, All Rights Reserved.
 */
package com.tridium.flexSerial.ui;

import javax.baja.control.*;
import javax.baja.driver.*;
import javax.baja.driver.point.*;
import javax.baja.driver.ui.point.*;
import javax.baja.gx.*;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.util.*;
import javax.baja.workbench.mgr.*;
import javax.baja.workbench.mgr.folder.*;

import com.tridium.flexSerial.*;
import com.tridium.flexSerial.point.*;

/**
 * BFlexPointManager
 *
 * @author    Andy Saunders
 * @creation  19-Apr-05
 * @version   $Revision$ $Date: 2/9/2005 4:52:47 PM$
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "flexSerial:FlexPointDeviceExt", "flexSerial:FlexPointFolder" }
  )
)
public class BFlexPointManager
  extends BPointManager
{                
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.ui.BFlexPointManager(2330056975)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexPointManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BFlexPointManager()
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
  
  class Model extends PointModel
  {
    Model(BPointManager manager) { super(manager); }
    
    protected MgrColumn[] makeColumns()
    {        
      return cols;   
    }                                   
  }                                 
                               
////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////

  class Controller extends PointController
  {             
    Controller(BPointManager mgr) { super(mgr); }
  }                 
                         
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static Lexicon lex = Lexicon.make(BFlexDeviceManager.class); 
  
  // base class columns
  MgrColumn colPath        = new MgrColumn.Path(MgrColumn.UNSEEN);
  MgrColumn colName        = new MgrColumn.Name();
  MgrColumn colType        = new MgrColumn.Type();
  MgrColumn colToString    = new MgrColumn.ToString("Out", 0);
  MgrColumn colEnabled     = new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BProxyExt.enabled}, MgrColumn.EDITABLE | MgrColumn.UNSEEN);
  MgrColumn colFacets      = new MgrColumn.PropPath(new Property[] {BControlPoint.facets},  MgrColumn.EDITABLE | MgrColumn.UNSEEN);
  MgrColumn colTuning      = new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BProxyExt.tuningPolicyName}, MgrColumn.EDITABLE);
  MgrColumn colDeviceFacets= new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BProxyExt.deviceFacets}, MgrColumn.EDITABLE | MgrColumn.UNSEEN);
  MgrColumn colConversion  = new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BProxyExt.conversion},   MgrColumn.EDITABLE | MgrColumn.UNSEEN);
  MgrColumn colFaultCause  = new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BProxyExt.faultCause},   MgrColumn.UNSEEN);
  MgrColumn colReadValue   = new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BProxyExt.readValue},    MgrColumn.UNSEEN);
  MgrColumn colWriteValue  = new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BProxyExt.writeValue},   MgrColumn.UNSEEN);
  
  // axonProxyExt specific columns
  MgrColumn colAddress     = new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BFlexProxyExt.address}, MgrColumn.EDITABLE);
  
  MgrColumn[] cols = 
  { 
    colPath, colName, colType, colToString,
    colAddress,
    colEnabled, colFacets, colTuning, colDeviceFacets, colConversion,
    colFaultCause, colReadValue, colWriteValue,
  };
  
}
