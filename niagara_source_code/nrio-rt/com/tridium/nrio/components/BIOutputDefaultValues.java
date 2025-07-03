/*
 * @copyright 2016 Tridium Inc.
 */
package com.tridium.nrio.components;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFloat;
import javax.baja.sys.BInterface;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.UnitDatabase;

import com.tridium.nrio.BNrio16Module;
import com.tridium.nrio.BNrioDevice;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.messages.NrioInputStream;
import com.tridium.nrio.util.DualModuleUtils;

/**
 * BOutputDefaultValues - This is a structure that is used to specify the DO and AO default values.
 *   These values are used by the IO module itself to put the AO's and DO's into a user defined state
 *   when it is not actively communicating with the Jace, i.e., off-line.
 *
 * @author    Andy Saunders
 * @creation  Oct 25, 2016
 */

@NiagaraType
public abstract interface BIOutputDefaultValues
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.components.BIOutputDefaultValues(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:13 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIOutputDefaultValues.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public abstract boolean getEnableCommLossDefaults();
  public abstract boolean getEnableStartupDefaults();
  public abstract void setCommLossTimeout(int seconds);
  public abstract void setStartupTimeout(int seconds);
  public abstract boolean isDualModule();
  public abstract byte[] toMessageBytes(boolean isPrimary);
  public abstract Property[] getDoProperties();
  public abstract Property[] getAoProperties();

  public default int getRawValue(float value)
  {
    float max = 10f;
    float min = 0f;
    int ivalue;

    if (value > max)
      value = max;
    else if (value < min)
      value = min;

    ivalue = (int) ((value / max) * 4095) & 0x0fff;
    return ivalue;
  }


  public static BFacets aoFacets = BFacets.makeNumeric(UnitDatabase.getUnit("volt"), 1, 0, 10);
  public static int MAX_DO = 4;
  public static int MAX_AO = 4;
  public static int ARRAY_SIZE = 1 + (MAX_AO * 12)/8; // 12 bit AO values + 1 byte for DO values

}
