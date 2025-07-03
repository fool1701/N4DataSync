/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.energy;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.units.*;

import com.tridium.kitControl.enums.*;

/** Degree Day Calculation object
 *
 * 
 * @author    Andy Saunders
 * @creation  11 Jan 2005
 * @version   $Revision: 21$ $Date: 11/5/2003 5:12:11 PM$
 * @since     Baja 1.0
 */
 
@NiagaraType
/*
 These facets are applied against the out property.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.makeNumeric(UnitDatabase.getUnit(\"fahrenheit\"), 1)"
)
/*
 Shed level for maximum offset
 */
@NiagaraProperty(
  name = "shedLevelHighLimit",
  type = "int",
  defaultValue = "32",
  facets = @Facet("BFacets.makeInt(2, 32)")
)
/*
 Shed level at which offset begins
 */
@NiagaraProperty(
  name = "shedLevelLowLimit",
  type = "int",
  defaultValue = "1",
  facets = @Facet("BFacets.makeInt(1, 31)")
)
/*
 'false' input causes setpoint to be adjusted by offset
 */
@NiagaraProperty(
  name = "shedInhibit",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
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
 Maximum setpoint offset (signed + or -) if active and in heating mode
 */
@NiagaraProperty(
  name = "htgOffset",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
/*
 Maximum setpoint offset (signed + or -) if active and in cooling mode
 */
@NiagaraProperty(
  name = "clgOffset",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "modeIn",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY,
  facets = @Facet("BFacets.makeEnum( BEnumRange.make(BOffHeatCool.TYPE) )")
)
/*
 Shed level in effect
 */
@NiagaraProperty(
  name = "shedLevel",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY,
  facets = @Facet("BFacets.makeNumeric(0)")
)
/*
 Output to indicate if setpointOutStatus has been adjusted
 */
@NiagaraProperty(
  name = "offsetInEffect",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.SUMMARY
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
  name = "calculate",
  flags = Flags.HIDDEN
)
public class BSetpointOffset
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.energy.BSetpointOffset(2846635637)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the out property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeNumeric(UnitDatabase.getUnit("fahrenheit"), 1), null);

  /**
   * Get the {@code facets} property.
   * These facets are applied against the out property.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * These facets are applied against the out property.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "shedLevelHighLimit"

  /**
   * Slot for the {@code shedLevelHighLimit} property.
   * Shed level for maximum offset
   * @see #getShedLevelHighLimit
   * @see #setShedLevelHighLimit
   */
  public static final Property shedLevelHighLimit = newProperty(0, 32, BFacets.makeInt(2, 32));

  /**
   * Get the {@code shedLevelHighLimit} property.
   * Shed level for maximum offset
   * @see #shedLevelHighLimit
   */
  public int getShedLevelHighLimit() { return getInt(shedLevelHighLimit); }

  /**
   * Set the {@code shedLevelHighLimit} property.
   * Shed level for maximum offset
   * @see #shedLevelHighLimit
   */
  public void setShedLevelHighLimit(int v) { setInt(shedLevelHighLimit, v, null); }

  //endregion Property "shedLevelHighLimit"

  //region Property "shedLevelLowLimit"

  /**
   * Slot for the {@code shedLevelLowLimit} property.
   * Shed level at which offset begins
   * @see #getShedLevelLowLimit
   * @see #setShedLevelLowLimit
   */
  public static final Property shedLevelLowLimit = newProperty(0, 1, BFacets.makeInt(1, 31));

  /**
   * Get the {@code shedLevelLowLimit} property.
   * Shed level at which offset begins
   * @see #shedLevelLowLimit
   */
  public int getShedLevelLowLimit() { return getInt(shedLevelLowLimit); }

  /**
   * Set the {@code shedLevelLowLimit} property.
   * Shed level at which offset begins
   * @see #shedLevelLowLimit
   */
  public void setShedLevelLowLimit(int v) { setInt(shedLevelLowLimit, v, null); }

  //endregion Property "shedLevelLowLimit"

  //region Property "shedInhibit"

  /**
   * Slot for the {@code shedInhibit} property.
   * 'false' input causes setpoint to be adjusted by offset
   * @see #getShedInhibit
   * @see #setShedInhibit
   */
  public static final Property shedInhibit = newProperty(Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code shedInhibit} property.
   * 'false' input causes setpoint to be adjusted by offset
   * @see #shedInhibit
   */
  public BStatusBoolean getShedInhibit() { return (BStatusBoolean)get(shedInhibit); }

  /**
   * Set the {@code shedInhibit} property.
   * 'false' input causes setpoint to be adjusted by offset
   * @see #shedInhibit
   */
  public void setShedInhibit(BStatusBoolean v) { set(shedInhibit, v, null); }

  //endregion Property "shedInhibit"

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
   * Maximum setpoint offset (signed + or -) if active and in heating mode
   * @see #getHtgOffset
   * @see #setHtgOffset
   */
  public static final Property htgOffset = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code htgOffset} property.
   * Maximum setpoint offset (signed + or -) if active and in heating mode
   * @see #htgOffset
   */
  public BStatusNumeric getHtgOffset() { return (BStatusNumeric)get(htgOffset); }

  /**
   * Set the {@code htgOffset} property.
   * Maximum setpoint offset (signed + or -) if active and in heating mode
   * @see #htgOffset
   */
  public void setHtgOffset(BStatusNumeric v) { set(htgOffset, v, null); }

  //endregion Property "htgOffset"

  //region Property "clgOffset"

  /**
   * Slot for the {@code clgOffset} property.
   * Maximum setpoint offset (signed + or -) if active and in cooling mode
   * @see #getClgOffset
   * @see #setClgOffset
   */
  public static final Property clgOffset = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code clgOffset} property.
   * Maximum setpoint offset (signed + or -) if active and in cooling mode
   * @see #clgOffset
   */
  public BStatusNumeric getClgOffset() { return (BStatusNumeric)get(clgOffset); }

  /**
   * Set the {@code clgOffset} property.
   * Maximum setpoint offset (signed + or -) if active and in cooling mode
   * @see #clgOffset
   */
  public void setClgOffset(BStatusNumeric v) { set(clgOffset, v, null); }

  //endregion Property "clgOffset"

  //region Property "modeIn"

  /**
   * Slot for the {@code modeIn} property.
   * @see #getModeIn
   * @see #setModeIn
   */
  public static final Property modeIn = newProperty(Flags.SUMMARY, new BStatusEnum(), BFacets.makeEnum( BEnumRange.make(BOffHeatCool.TYPE) ));

  /**
   * Get the {@code modeIn} property.
   * @see #modeIn
   */
  public BStatusEnum getModeIn() { return (BStatusEnum)get(modeIn); }

  /**
   * Set the {@code modeIn} property.
   * @see #modeIn
   */
  public void setModeIn(BStatusEnum v) { set(modeIn, v, null); }

  //endregion Property "modeIn"

  //region Property "shedLevel"

  /**
   * Slot for the {@code shedLevel} property.
   * Shed level in effect
   * @see #getShedLevel
   * @see #setShedLevel
   */
  public static final Property shedLevel = newProperty(Flags.SUMMARY, new BStatusNumeric(), BFacets.makeNumeric(0));

  /**
   * Get the {@code shedLevel} property.
   * Shed level in effect
   * @see #shedLevel
   */
  public BStatusNumeric getShedLevel() { return (BStatusNumeric)get(shedLevel); }

  /**
   * Set the {@code shedLevel} property.
   * Shed level in effect
   * @see #shedLevel
   */
  public void setShedLevel(BStatusNumeric v) { set(shedLevel, v, null); }

  //endregion Property "shedLevel"

  //region Property "offsetInEffect"

  /**
   * Slot for the {@code offsetInEffect} property.
   * Output to indicate if setpointOutStatus has been adjusted
   * @see #getOffsetInEffect
   * @see #setOffsetInEffect
   */
  public static final Property offsetInEffect = newProperty(Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code offsetInEffect} property.
   * Output to indicate if setpointOutStatus has been adjusted
   * @see #offsetInEffect
   */
  public BStatusBoolean getOffsetInEffect() { return (BStatusBoolean)get(offsetInEffect); }

  /**
   * Set the {@code offsetInEffect} property.
   * Output to indicate if setpointOutStatus has been adjusted
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
  public static final Action calculate = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code calculate} action.
   * @see #calculate
   */
  public void calculate() { invoke(calculate, null, null); }

  //endregion Action "calculate"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSetpointOffset.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Initialization  /  Cleanup
////////////////////////////////////////////////////////////////

  public void started()
    throws Exception
  {

    if(ticket != null)
      ticket.cancel();
    ticket = Clock.schedule(this, BRelTime.makeMinutes(1), calculate, null);

    super.started();
	  if( !Sys.atSteadyState() )
		  return;


  }

  public void stopped()
    throws Exception
  {
    if(ticket != null)
      ticket.cancel();
    super.stopped();
  }


  public void changed(Property property, Context context) 
  {
  	super.changed(property, context);
    if( !Sys.atSteadyState() || !isRunning() )
	    return;
    if(property.equals(shedLevelHighLimit))
    {
      if (getShedLevelHighLimit() <= getShedLevelLowLimit() ) 
      {
        setShedLevelHighLimit(getShedLevelLowLimit()+1);
      }
    }
    if(property.equals(shedLevelLowLimit))
    {
      if (getShedLevelLowLimit() >= getShedLevelHighLimit() ) 
      {
        setShedLevelLowLimit(getShedLevelHighLimit()-1);
      }
    }
    if(property.equals(setpointIn        ) ||
       property.equals(modeIn            ) ||
       property.equals(shedInhibit       ) ||
       property.equals(shedLevel         ) ||
       property.equals(clgOffset         ) ||
       property.equals(htgOffset         ) ||
       property.equals(shedLevelHighLimit) ||
       property.equals(shedLevelLowLimit )    )
      doCalculate();
  }

  public BFacets getSlotFacets(Slot slot)
  {
    if( slot.equals(setpointIn)  ||
        slot.equals(htgOffset)   ||
        slot.equals(clgOffset)   ||
        slot.equals(setpointOut)    )
      return getFacets();
    return super.getSlotFacets(slot);
  }

  public void doCalculate()
  {
    if(ticket != null)
      ticket.cancel();
    ticket = Clock.schedule(this, BRelTime.makeMinutes(1), calculate, null);
    
    int lowLimit = getShedLevelLowLimit();
    int highLimit = getShedLevelHighLimit();
    boolean localOffsetInEffect = false;
    double setpointOffset = 0.0;
    
    //Offset only applies when shedLevel > 0 and shedInhibit is 'commanded' to false
    if ( getShedLevel().getValue() > 0  &&  getShedInhibit().getValue() == false  && getModeIn().getValue().getOrdinal() != BOffHeatCool.OFF ) 
    {
    
    // validate limits
      if ( getShedLevelLowLimit() >= getShedLevelHighLimit())
      {
        setShedLevelLowLimit(1);
        lowLimit = 1;
      }
      else
      {
        if ( getShedLevelLowLimit() < 1  || getShedLevelLowLimit() > 32 )
          lowLimit = 1;
        if ( getShedLevelHighLimit() < 1 || getShedLevelHighLimit() > 32 )
          highLimit = 32;
      }
      if ( getShedLevel().getValue() >= lowLimit )
      {
        localOffsetInEffect = true;
        if ( getModeIn().getValue().getOrdinal() == BOffHeatCool.COOL )   			//Cooling Mode
          setpointOffset = getClgOffset().getValue();
        else if ( getModeIn().getValue().getOrdinal() == BOffHeatCool.HEAT )		//Heating Mode
          setpointOffset = getHtgOffset().getValue();
        if ( getShedLevel().getValue() < highLimit ) 
          setpointOffset = ( setpointOffset * ( getShedLevel().getValue() - lowLimit + 1 ) ) / ( highLimit - lowLimit + 1 );
      }
    }
    getOffsetInEffect().setValue(localOffsetInEffect);
    getSetpointOut().setValue( getSetpointIn().getValue() + setpointOffset);

    
  }

////////////////////////////////////////////////////////////////
// local variables
////////////////////////////////////////////////////////////////

  Clock.Ticket ticket = null;

}
