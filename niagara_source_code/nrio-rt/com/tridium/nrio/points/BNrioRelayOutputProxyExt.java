/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.points;

import javax.baja.control.BBooleanWritable;
import javax.baja.driver.point.BReadWriteMode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.BNrio16Module;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.messages.NrioMessageConst;


/**
 * @author    Bill Smith
 * @creation  3 Feb 2004
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */

@NiagaraType
public class BNrioRelayOutputProxyExt extends BNrio16WriteProxyExt
{ 


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.points.BNrioRelayOutputProxyExt(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:13 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioRelayOutputProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////  

  public boolean isParentLegal(BComponent parent)
  {
    if (super.isParentLegal(parent))
      if (parent instanceof BBooleanWritable)
        return true;

    return false;
  }

  public BReadWriteMode getMode()
  {
    return BReadWriteMode.readWrite;
  }

  
////////////////////////////////////////////////////////////////
// Abstract
////////////////////////////////////////////////////////////////  

////////////////////////////////////////////////////////////////
// Implementation
////////////////////////////////////////////////////////////////    


  /**
   * Callback from asynchronous thread to 
   * send a write to the device.
   */
  public void writeData(BStatusValue out)
  {
    BNrioNetwork network = (BNrioNetwork)getNetwork();
    if(network == null)
      return;
    if( !(device() instanceof BNrio16Module))
      return;
    int wrStatus = NrioMessageConst.MESSAGE_STATUS_ERR0R;
    wrStatus = device().setDoValue(((BStatusBoolean)out).getValue(), getInstance());
    if(wrStatus == NrioMessageConst.MESSAGE_STATUS_OK)
        writeOk(out);
      else
        writeFail("writeError: " + wrStatus );
    return;
  } 


  protected int getVoltageValue(BStatusNumeric out)
  {
    float max = 10f;
    float min = 0f;
    int ivalue;
      
    float value = (float)out.getValue();
    if (value > max)
      value = max;
    else if (value < min)
      value = min;
        
    ivalue = (int) ((value / max) * 4095) & 0x0fff;
    return ivalue;
  }  
   
////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

}
