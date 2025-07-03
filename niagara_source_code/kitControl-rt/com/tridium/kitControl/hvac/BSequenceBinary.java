/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.hvac;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;


/**
 * BSequenceBinary is a component that 2 - 10 load binary sequence function.
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "delay",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)",
  override = true
)
public class BSequenceBinary
  extends BSequence
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.hvac.BSequenceBinary(1254363785)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "delay"

  /**
   * Slot for the {@code delay} property.
   * @see #getDelay
   * @see #setDelay
   */
  public static final Property delay = newProperty(0, BRelTime.make(0), null);

  //endregion Property "delay"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSequenceBinary.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BSequenceBinary()
  {
  }
  
  public void calculate()
  {
    double maxStages = (int)(Math.pow((double)2, (double)getNumberOutputs())) - 1;
    double maxInValue = getInMaximum();
    double minInValue = getInMinimum();
    double range = maxInValue - minInValue;
    double spDelta = range /  maxStages;
    double cv = getIn().getValue();
    if(cv > maxInValue)
      cv = maxInValue;
    else if( cv < minInValue)
      cv = minInValue;
    int currentStagesOn = numStagesOn;
    if(getIn().getStatus().isValid())
    {
      numStagesOn = (int)((cv-getInMinimum())/spDelta);
      //if(numStagesOn == (int)maxStages)
      //  numStagesOn--;
      if( cv > getInMinimum() && currentStagesOn > numStagesOn )
        numStagesOn++;
    }


    setOutputs(); 
  }

  public void setOutputs()
  {
    if(numStagesOn < 0)
      numStagesOn = 0;
    setDesiredStagesOn(numStagesOn);
    if(numStagesOn == curStagesOn) return;  // don't do anything
    if(numStagesOn > curStagesOn && isDelayProgrammed())
    {
      if(getOnDelayActive() || getOffDelayActive()) // is timer active
        return;          // yes don't do any thing
      curStagesOn++;
      startOnDelayTimer();
    }
    else if(numStagesOn < curStagesOn && isDelayProgrammed())
    {
      if(getOffDelayActive() || getOnDelayActive()) // is timer active
        return;          // yes don't do any thing
      curStagesOn--;
      startOffDelayTimer();
    }
    else
    {
      curStagesOn = numStagesOn;
    }
    getOutA().setValue((curStagesOn &  1) != 0);
    getOutB().setValue((curStagesOn &  2) != 0);
    getOutC().setValue((curStagesOn &  4) != 0);
    getOutD().setValue((curStagesOn &  8) != 0);
    getOutE().setValue((curStagesOn & 16) != 0);
    getOutF().setValue((curStagesOn & 32) != 0);
    getOutG().setValue((curStagesOn & 64) != 0);
    getOutH().setValue((curStagesOn & 128) != 0);
    getOutI().setValue((curStagesOn & 256) != 0);
    getOutJ().setValue((curStagesOn & 512) != 0);
  
    setCurrentStagesOn(curStagesOn);
  
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
}
