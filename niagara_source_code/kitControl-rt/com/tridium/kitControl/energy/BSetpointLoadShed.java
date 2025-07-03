/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.energy;

import java.io.*;
import java.text.*;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.units.*;

import com.tridium.kitControl.enums.*;
import com.tridium.kitControl.hvac.*;

 /*
 * Setpoint Load Shed Object
 *
 *   This object will provide the application developer
 *   with an easy method of implementing load shedding strategies. In response to an input
 *   link, which activates the SLS object, it will cause a specified setpoint to be raised
 *   or lowered by a specific amount
 *
 * @author    Andy Saunders
 * @creation  24 Mar 2005
 * @version   $Revision: 21$ $Date: 11/5/2003 5:12:11 PM$
 * @since     Baja 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "temperatureFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeNumeric(UnitDatabase.getUnit(\"fahrenheit\"), 1)"
)
/*
 'true' input causes setpoint to be adjusted by offset
 */
@NiagaraProperty(
  name = "enabled",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(true)",
  flags = Flags.SUMMARY
)
/*
 Setpoint input that will be adjusted by this object
 */
@NiagaraProperty(
  name = "setpointIn",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
/*
 Setpoint will be adjusted by this signed (+ or -) amount if active and in heating mode
 */
@NiagaraProperty(
  name = "htgOffset",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(6.0, BStatus.ok)",
  flags = Flags.SUMMARY
)
/*
 Setpoint will be adjusted by this signed (+ or -) amount if active and in cooling mode
 */
@NiagaraProperty(
  name = "clgOffset",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(3.0, BStatus.ok)",
  flags = Flags.SUMMARY
)
/*
 0=Off, 1=Heating, 2=Cooling
 */
@NiagaraProperty(
  name = "modeIn",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum( BDynamicEnum.make(0, BEnumRange.make( BOffHeatCool.TYPE )) )",
  flags = Flags.SUMMARY,
  facets = @Facet("BFacets.makeEnum( BEnumRange.make( BOffHeatCool.TYPE ) )")
)
/*
 Output to indicate if setpointOut has been adjusted
 */
@NiagaraProperty(
  name = "offsetInEffect",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.SUMMARY | Flags.TRANSIENT
)
/*
 Adjusted setpoint if active otherwise passes through original setpoint
 */
@NiagaraProperty(
  name = "setpointOut",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraAction(
  name = "calculate"
)
public class BSetpointLoadShed
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.energy.BSetpointLoadShed(2700361572)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "temperatureFacets"

  /**
   * Slot for the {@code temperatureFacets} property.
   * @see #getTemperatureFacets
   * @see #setTemperatureFacets
   */
  public static final Property temperatureFacets = newProperty(0, BFacets.makeNumeric(UnitDatabase.getUnit("fahrenheit"), 1), null);

  /**
   * Get the {@code temperatureFacets} property.
   * @see #temperatureFacets
   */
  public BFacets getTemperatureFacets() { return (BFacets)get(temperatureFacets); }

  /**
   * Set the {@code temperatureFacets} property.
   * @see #temperatureFacets
   */
  public void setTemperatureFacets(BFacets v) { set(temperatureFacets, v, null); }

  //endregion Property "temperatureFacets"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * 'true' input causes setpoint to be adjusted by offset
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(Flags.SUMMARY, new BStatusBoolean(true), null);

  /**
   * Get the {@code enabled} property.
   * 'true' input causes setpoint to be adjusted by offset
   * @see #enabled
   */
  public BStatusBoolean getEnabled() { return (BStatusBoolean)get(enabled); }

  /**
   * Set the {@code enabled} property.
   * 'true' input causes setpoint to be adjusted by offset
   * @see #enabled
   */
  public void setEnabled(BStatusBoolean v) { set(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "setpointIn"

  /**
   * Slot for the {@code setpointIn} property.
   * Setpoint input that will be adjusted by this object
   * @see #getSetpointIn
   * @see #setSetpointIn
   */
  public static final Property setpointIn = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code setpointIn} property.
   * Setpoint input that will be adjusted by this object
   * @see #setpointIn
   */
  public BStatusNumeric getSetpointIn() { return (BStatusNumeric)get(setpointIn); }

  /**
   * Set the {@code setpointIn} property.
   * Setpoint input that will be adjusted by this object
   * @see #setpointIn
   */
  public void setSetpointIn(BStatusNumeric v) { set(setpointIn, v, null); }

  //endregion Property "setpointIn"

  //region Property "htgOffset"

  /**
   * Slot for the {@code htgOffset} property.
   * Setpoint will be adjusted by this signed (+ or -) amount if active and in heating mode
   * @see #getHtgOffset
   * @see #setHtgOffset
   */
  public static final Property htgOffset = newProperty(Flags.SUMMARY, new BStatusNumeric(6.0, BStatus.ok), null);

  /**
   * Get the {@code htgOffset} property.
   * Setpoint will be adjusted by this signed (+ or -) amount if active and in heating mode
   * @see #htgOffset
   */
  public BStatusNumeric getHtgOffset() { return (BStatusNumeric)get(htgOffset); }

  /**
   * Set the {@code htgOffset} property.
   * Setpoint will be adjusted by this signed (+ or -) amount if active and in heating mode
   * @see #htgOffset
   */
  public void setHtgOffset(BStatusNumeric v) { set(htgOffset, v, null); }

  //endregion Property "htgOffset"

  //region Property "clgOffset"

  /**
   * Slot for the {@code clgOffset} property.
   * Setpoint will be adjusted by this signed (+ or -) amount if active and in cooling mode
   * @see #getClgOffset
   * @see #setClgOffset
   */
  public static final Property clgOffset = newProperty(Flags.SUMMARY, new BStatusNumeric(3.0, BStatus.ok), null);

  /**
   * Get the {@code clgOffset} property.
   * Setpoint will be adjusted by this signed (+ or -) amount if active and in cooling mode
   * @see #clgOffset
   */
  public BStatusNumeric getClgOffset() { return (BStatusNumeric)get(clgOffset); }

  /**
   * Set the {@code clgOffset} property.
   * Setpoint will be adjusted by this signed (+ or -) amount if active and in cooling mode
   * @see #clgOffset
   */
  public void setClgOffset(BStatusNumeric v) { set(clgOffset, v, null); }

  //endregion Property "clgOffset"

  //region Property "modeIn"

  /**
   * Slot for the {@code modeIn} property.
   * 0=Off, 1=Heating, 2=Cooling
   * @see #getModeIn
   * @see #setModeIn
   */
  public static final Property modeIn = newProperty(Flags.SUMMARY, new BStatusEnum( BDynamicEnum.make(0, BEnumRange.make( BOffHeatCool.TYPE )) ), BFacets.makeEnum( BEnumRange.make( BOffHeatCool.TYPE ) ));

  /**
   * Get the {@code modeIn} property.
   * 0=Off, 1=Heating, 2=Cooling
   * @see #modeIn
   */
  public BStatusEnum getModeIn() { return (BStatusEnum)get(modeIn); }

  /**
   * Set the {@code modeIn} property.
   * 0=Off, 1=Heating, 2=Cooling
   * @see #modeIn
   */
  public void setModeIn(BStatusEnum v) { set(modeIn, v, null); }

  //endregion Property "modeIn"

  //region Property "offsetInEffect"

  /**
   * Slot for the {@code offsetInEffect} property.
   * Output to indicate if setpointOut has been adjusted
   * @see #getOffsetInEffect
   * @see #setOffsetInEffect
   */
  public static final Property offsetInEffect = newProperty(Flags.SUMMARY | Flags.TRANSIENT, new BStatusBoolean(), null);

  /**
   * Get the {@code offsetInEffect} property.
   * Output to indicate if setpointOut has been adjusted
   * @see #offsetInEffect
   */
  public BStatusBoolean getOffsetInEffect() { return (BStatusBoolean)get(offsetInEffect); }

  /**
   * Set the {@code offsetInEffect} property.
   * Output to indicate if setpointOut has been adjusted
   * @see #offsetInEffect
   */
  public void setOffsetInEffect(BStatusBoolean v) { set(offsetInEffect, v, null); }

  //endregion Property "offsetInEffect"

  //region Property "setpointOut"

  /**
   * Slot for the {@code setpointOut} property.
   * Adjusted setpoint if active otherwise passes through original setpoint
   * @see #getSetpointOut
   * @see #setSetpointOut
   */
  public static final Property setpointOut = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code setpointOut} property.
   * Adjusted setpoint if active otherwise passes through original setpoint
   * @see #setpointOut
   */
  public BStatusNumeric getSetpointOut() { return (BStatusNumeric)get(setpointOut); }

  /**
   * Set the {@code setpointOut} property.
   * Adjusted setpoint if active otherwise passes through original setpoint
   * @see #setpointOut
   */
  public void setSetpointOut(BStatusNumeric v) { set(setpointOut, v, null); }

  //endregion Property "setpointOut"

  //region Action "calculate"

  /**
   * Slot for the {@code calculate} action.
   * @see #calculate()
   */
  public static final Action calculate = newAction(0, null);

  /**
   * Invoke the {@code calculate} action.
   * @see #calculate
   */
  public void calculate() { invoke(calculate, null, null); }

  //endregion Action "calculate"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSetpointLoadShed.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Initialization  /  Cleanup
////////////////////////////////////////////////////////////////

  public void started()
    throws Exception
  {

    super.started();
  }

  public void stopped()
    throws Exception
  {
    super.stopped();
  }

  public void changed(Property property, Context context)
  {
    super.changed(property, context);
    if( !Sys.atSteadyState() || !isRunning() )
      return;
    if( property.equals(clgOffset) ||
        property.equals(htgOffset) ||
        property.equals(modeIn)    ||
        property.equals(setpointIn)||
        property.equals(enabled)       )
      doCalculate();

  }

  public BFacets getSlotFacets(Slot slot)
  {
    if(slot.equals(clgOffset)  ||
       slot.equals(htgOffset) ||
       slot.equals(setpointIn)  )
      return getTemperatureFacets();
    return super.getSlotFacets(slot);
  }

  public void atSteadyState()
  {
    doCalculate();
  }

  public void doCalculate()
  {
    double setpoint = getSetpointIn().getValue();
    int mode = getModeIn().getValue().getOrdinal();
    boolean shedActive = false;

    if ( getEnabled().getValue() && (mode != BOffHeatCool.OFF) )
    {
      if ( mode == BOffHeatCool.COOL )      //Cooling Mode
        setpoint = setpoint + getClgOffset().getValue();
      else    //Heating Mode
        setpoint = setpoint - getHtgOffset().getValue();
      shedActive = true;
    }
    getSetpointOut().setValue(setpoint);
    getOffsetInEffect().setValue(shedActive);

  }

////////////////////////////////////////////////////////////////
// local variables
////////////////////////////////////////////////////////////////

}
