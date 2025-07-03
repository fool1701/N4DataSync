/**
 * Copyright 2012 - All Rights Reserved.
 */
package com.tridium.nvideo.ui;

import javax.baja.control.BBooleanPoint;
import javax.baja.control.BNumericPoint;
import javax.baja.control.BStringPoint;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.mgr.MgrModel;
import javax.baja.workbench.mgr.MgrTypeInfo;

import com.tridium.ndriver.ui.point.BNPointManager;
import com.tridium.ndriver.ui.point.NPointModel;

/**
 * BVideoEventManager is a customized BNPointManager for video event proxies.
 *
 * @author   Robert Adams
 * @creation Oct 31, 2012
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "nvideo:VideoEventDeviceExt", "nvideo:VideoEventFolder" }
  )
)
public class BVideoEventManager
    extends BNPointManager
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nvideo.ui.BVideoEventManager(2758860822)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BVideoEventManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  /** Empty constructor   */
  public BVideoEventManager() {  }
  
  /** Custom mgrModel to limit point types */
  protected MgrModel makeModel() {  return new EventMgrModel(this);  }

////////////////////////////////////////////////////////////////
// Custom mgrModel
////////////////////////////////////////////////////////////////
  class EventMgrModel
    extends NPointModel
  {
    public EventMgrModel(BNPointManager manager)
    {
      super(manager);
    }

    public MgrTypeInfo[] getNewTypes()
    {
      return VALID_DB_TYPES;
    }                          
    
  }
  
////////////////////////////////////////////////////////////////
// Statics
////////////////////////////////////////////////////////////////
  public static final MgrTypeInfo[] VALID_DB_TYPES =
      MgrTypeInfo.makeArray(
             new TypeInfo[] {  BBooleanPoint.TYPE.getTypeInfo(), 
                               BStringPoint.TYPE.getTypeInfo(),
                               BNumericPoint.TYPE.getTypeInfo()  }
                           );
  
}
