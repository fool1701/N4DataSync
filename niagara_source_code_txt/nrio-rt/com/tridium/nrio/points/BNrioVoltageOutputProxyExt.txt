/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.points;

import javax.baja.control.BNumericWritable;
import javax.baja.driver.point.BReadWriteMode;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.UnitDatabase;

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
@NiagaraProperty(
  name = "deviceFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeInt(UnitDatabase.getUnit(\"volt\"))",
  flags = Flags.READONLY,
  override = true
)
public class BNrioVoltageOutputProxyExt extends BNrio16WriteProxyExt
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.points.BNrioVoltageOutputProxyExt(122347822)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "deviceFacets"

  /**
   * Slot for the {@code deviceFacets} property.
   * @see #getDeviceFacets
   * @see #setDeviceFacets
   */
  public static final Property deviceFacets = newProperty(Flags.READONLY, BFacets.makeInt(UnitDatabase.getUnit("volt")), null);

  //endregion Property "deviceFacets"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioVoltageOutputProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////  

  public boolean isParentLegal(BComponent parent)
  {
    if (super.isParentLegal(parent))
      if (parent instanceof BNumericWritable)
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
    int wrValue =  getVoltageValue((BStatusNumeric)out);
    int wrStatus;
    wrStatus = ((BNrio16Module)device()).setAoValue(wrValue, getInstance());
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
