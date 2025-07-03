/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.points;

import javax.baja.control.BBooleanPoint;
import javax.baja.driver.point.BReadWriteMode;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.BNrio16Module;
import com.tridium.nrio.components.BINrioIoStatus;
import com.tridium.nrio.components.BNrio16Status;
import com.tridium.nrio.enums.BUniversalInputTypeEnum;
/**
 * A BNrioBooleanInputProxyExt is a point extention that
 * represents an boolean universal input.
 *
 * @author    Bill Smith
 * @creation  3 Feb 2004
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "uiType",
  type = "BEnum",
  defaultValue = "BUniversalInputTypeEnum.di_Normal",
  flags = Flags.READONLY,
  override = true
)
public class BNrioBooleanInputProxyExt extends BUiProxyExt
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.points.BNrioBooleanInputProxyExt(858627840)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "uiType"

  /**
   * Slot for the {@code uiType} property.
   * @see #getUiType
   * @see #setUiType
   */
  public static final Property uiType = newProperty(Flags.READONLY, BUniversalInputTypeEnum.di_Normal, null);

  //endregion Property "uiType"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioBooleanInputProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////  

  /** 
   * Forces that this extension is on a boolean point.
   */

  public boolean isParentLegal(BComponent parent)
  {
    if (super.isParentLegal(parent))
      if (parent instanceof BBooleanPoint)
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
    if(getUiType().getOrdinal() != BUniversalInputTypeEnum.DI_NORMAL || getInstance() < 1 || getInstance() > maxUiInstance)
      {
    	readFail("readFail.invalidInstanceOrData");
      }
      else
      {
        BStatusBoolean sbValue = (BStatusBoolean)getReadValue().newCopy();
        sbValue.setValue(ioStatus.getDi(getInstance()));
        readOk(sbValue);
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
