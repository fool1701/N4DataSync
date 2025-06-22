/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.points;

import javax.baja.control.BNumericPoint;
import javax.baja.driver.point.BReadWriteMode;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.UnitDatabase;

import com.tridium.nrio.BNrio16Module;
import com.tridium.nrio.components.BINrioIoStatus;
import com.tridium.nrio.components.BNrio16Status;
import com.tridium.nrio.enums.BUniversalInputTypeEnum;
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
  defaultValue = "BFacets.makeInt(UnitDatabase.getUnit(\"ohm\"))",
  flags = Flags.READONLY,
  override = true
)
@NiagaraProperty(
  name = "uiType",
  type = "BEnum",
  defaultValue = "BUniversalInputTypeEnum.ai_Resistive",
  flags = Flags.READONLY,
  override = true
)
public class BNrioResistiveInputProxyExt extends BUiProxyExt
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.points.BNrioResistiveInputProxyExt(3494365077)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "deviceFacets"

  /**
   * Slot for the {@code deviceFacets} property.
   * @see #getDeviceFacets
   * @see #setDeviceFacets
   */
  public static final Property deviceFacets = newProperty(Flags.READONLY, BFacets.makeInt(UnitDatabase.getUnit("ohm")), null);

  //endregion Property "deviceFacets"

  //region Property "uiType"

  /**
   * Slot for the {@code uiType} property.
   * @see #getUiType
   * @see #setUiType
   */
  public static final Property uiType = newProperty(Flags.READONLY, BUniversalInputTypeEnum.ai_Resistive, null);

  //endregion Property "uiType"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioResistiveInputProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////  

  public boolean isParentLegal(BComponent parent)
  {
    if (super.isParentLegal(parent))
      if (parent instanceof BNumericPoint)
        return true;

    return false;
  }

  public BReadWriteMode getMode()
  {
    return BReadWriteMode.readonly;
  }

  public void ioValueChanged()
  {
    BINrioIoStatus ioStatus = (BINrioIoStatus)((BNrio16Module)getDevice()).getIoStatus();
    int maxUiInstance = ioStatus.getMaxUiInstance();
    if(getUiType().getOrdinal() != BUniversalInputTypeEnum.AI_RESISTIVE  || getInstance() < 1 || getInstance() > maxUiInstance)
      {
    	readFail("readFail.invalidInstanceOrData");
      }
      else
      {
    	try 
    	{
          float value = (float) ioStatus.getAi(getInstance());
          if (value < 4095f)
          {
            value = (value * 10000f / (4095f - value));
          if (value < 0f)
        	value = 0f;
          if (value > 100000f)
        	value = 100000f;
          }
          else
            value = 100000f;
          BStatusNumeric svalue = new BStatusNumeric(value);
          readOk(svalue);
    	}
    	catch(Exception e)
    	{
    	  readFail(e.getMessage());
    	}
      }
  }
  
////////////////////////////////////////////////////////////////
// Abstract
////////////////////////////////////////////////////////////////  

////////////////////////////////////////////////////////////////
// Implementation
////////////////////////////////////////////////////////////////    

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

}
