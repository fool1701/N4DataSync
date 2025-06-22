/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.ui;

import javax.baja.control.BControlPoint;
import javax.baja.control.ext.BDiscreteTotalizerExt;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.kitControl.BDiscreteTotalizerAlarmAlgorithm;
import com.tridium.kitControl.BExtensionName;
import com.tridium.workbench.fieldeditors.BComponentNamePickerFE;

/**
 * Plugin for BString when used to select a Discrete Totalizer.
 *
 * @author Andy Saunders on 24 Nov 2004
 * @since Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "kitControl:ExtensionName"
  )
)
public class BDiscreteTotalizerChooserFE
  extends BComponentNamePickerFE
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.ui.BDiscreteTotalizerChooserFE(261119386)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDiscreteTotalizerChooserFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BPlugin
////////////////////////////////////////////////////////////////

  protected void doLoadValue(BObject value, Context cx)
  {
    extensionName = (BExtensionName)value; 
    alarmAlg = (BDiscreteTotalizerAlarmAlgorithm) extensionName.getParent();
    loadName(extensionName.getExtensionName());
  }
  
  protected BObject doSaveValue(BObject value, Context cx)
  {
    extensionName.setExtensionName(saveName());
    return extensionName;
  }
  

  public BComponent[] list()
    throws Exception
  {
    BControlPoint point = alarmAlg.getParentPoint();
    if(point == null) return null;
    return (BComponent[])(point.getChildren( BDiscreteTotalizerExt.class ));
  }

  BDiscreteTotalizerAlarmAlgorithm alarmAlg;
  BExtensionName extensionName;
}
