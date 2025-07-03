/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.hvac;

import javax.baja.control.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;


/**
 * BSequenceLinear is a component that 2 - 10 load linear sequence function.
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "action",
  type = "boolean",
  defaultValue = "false",
  facets = @Facet("BFacets.makeBoolean(\"rotating\", \"linear\" )")
)
@NiagaraProperty(
  name = "rotateTime",
  type = "BRelTime",
  defaultValue = "BRelTime.make(1000)"
)
@NiagaraProperty(
  name = "rotateTimerActive",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraAction(
  name = "rotateTimerExpired",
  flags = Flags.HIDDEN
)
public class BSequenceLinear
  extends BSequence
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.hvac.BSequenceLinear(2123181948)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "action"

  /**
   * Slot for the {@code action} property.
   * @see #getAction
   * @see #setAction
   */
  public static final Property action = newProperty(0, false, BFacets.makeBoolean("rotating", "linear" ));

  /**
   * Get the {@code action} property.
   * @see #action
   */
  public boolean getAction() { return getBoolean(action); }

  /**
   * Set the {@code action} property.
   * @see #action
   */
  public void setAction(boolean v) { setBoolean(action, v, null); }

  //endregion Property "action"

  //region Property "rotateTime"

  /**
   * Slot for the {@code rotateTime} property.
   * @see #getRotateTime
   * @see #setRotateTime
   */
  public static final Property rotateTime = newProperty(0, BRelTime.make(1000), null);

  /**
   * Get the {@code rotateTime} property.
   * @see #rotateTime
   */
  public BRelTime getRotateTime() { return (BRelTime)get(rotateTime); }

  /**
   * Set the {@code rotateTime} property.
   * @see #rotateTime
   */
  public void setRotateTime(BRelTime v) { set(rotateTime, v, null); }

  //endregion Property "rotateTime"

  //region Property "rotateTimerActive"

  /**
   * Slot for the {@code rotateTimerActive} property.
   * @see #getRotateTimerActive
   * @see #setRotateTimerActive
   */
  public static final Property rotateTimerActive = newProperty(Flags.TRANSIENT | Flags.READONLY, false, null);

  /**
   * Get the {@code rotateTimerActive} property.
   * @see #rotateTimerActive
   */
  public boolean getRotateTimerActive() { return getBoolean(rotateTimerActive); }

  /**
   * Set the {@code rotateTimerActive} property.
   * @see #rotateTimerActive
   */
  public void setRotateTimerActive(boolean v) { setBoolean(rotateTimerActive, v, null); }

  //endregion Property "rotateTimerActive"

  //region Action "rotateTimerExpired"

  /**
   * Slot for the {@code rotateTimerExpired} action.
   * @see #rotateTimerExpired()
   */
  public static final Action rotateTimerExpired = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code rotateTimerExpired} action.
   * @see #rotateTimerExpired
   */
  public void rotateTimerExpired() { invoke(rotateTimerExpired, null, null); }

  //endregion Action "rotateTimerExpired"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSequenceLinear.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BSequenceLinear()
  {
  }
  
/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if(p == action )
    {
      if(!isRotating())
        setFlags(rotateTime, Flags.HIDDEN, null);
      else
        setFlags(rotateTime, 0, null);
      if (rotateTicket != null) rotateTicket.cancel();
      reinitialize();
    }
    else if(p == rotateTimerActive )
    {
      calculate();
      setOutputs(); 
    }
    else super.changed(p, cx);
  }

  public void doRotateTimerExpired()
  {
    //setOutput(false);
    setRotateTimerActive(false);
    setOutputs();
  }

  public void calculate()
  {
    numOutputs = getNumberOutputs();
    double maxInValue = getInMaximum();
    double minInValue = getInMinimum();
    double range = maxInValue - minInValue;
    double spDelta = range / (float)(numOutputs);
    double cv = getIn().getValue();
    if(cv > maxInValue)
      cv = maxInValue;
    else if( cv < minInValue)
      cv = minInValue;
    int currentStagesOn = numStagesOn;
    if(getIn().getStatus().isValid())
    {
      numStagesOn = (int)((cv-getInMinimum())/spDelta);
      if( cv > getInMinimum() && currentStagesOn > numStagesOn )
        numStagesOn++;
    }
    if(numStagesOn < 0)
      numStagesOn = 0;
    setDesiredStagesOn(numStagesOn);
  }

  boolean isRotating()
  {
    return getAction();
  }

  public void setOutputs()
  {
    if(numStagesOn == curStagesOn)
    {
      if(isRotating() && isRotateTimeProgrammed() && numStagesOn > 0 && numStagesOn < numOutputs)
      {
        if(!getRotateTimerActive())
        {
          if(!wasEqual)
          {
            startRotateTimer();  
            wasEqual = true;
          }  
          else
          {
            rotateNextStageOff();
            rotateNextStageOn();
            startRotateTimer();
            setRotateTimerActive(true);
          }
        }
      }
      return;  // don't do anything
    }
    wasEqual = false;
    if(getRotateTimerActive())
    {
      rotateTicket.cancel();
      rotateTicket = null;
      setRotateTimerActive(false);
    }
      
    if(numStagesOn > curStagesOn)
    {
      if(getOnDelayActive() || getOffDelayActive() ) // is either timer active
      {
        return;          // yes don't do any thing
      }
      //curStagesOn++;
      if(isDelayProgrammed())
      {
        setNextStageOn();
        startOnDelayTimer();
      }
      else
      {
        while(numStagesOn > curStagesOn)
          setNextStageOn();
      }  
    }
    else if(numStagesOn < curStagesOn)
    {
      if(getOffDelayActive() || getOnDelayActive()) // is either timer active
      {
        return;          // yes don't do any thing
      }
      //curStagesOn--;
      if(isDelayProgrammed())
      {
        setNextStageOff();
        startOffDelayTimer();
      }
      else
      {
        while(numStagesOn < curStagesOn)
          setNextStageOff();
      }
    }
    setCurrentStagesOn(curStagesOn);
    // test to see if rotate time needs to be set
    if( numStagesOn == curStagesOn )
    {
      if(isRotating() && isRotateTimeProgrammed() && numStagesOn > 0 && numStagesOn < numOutputs)
      {
          startRotateTimer();  
          wasEqual = true;
      }
    }
  }

  
  void setNextStageOn()
  {
    if(isRotating()) // rotating
      rotateNextStageOn();
    else
      linearNextStageOn();
      
  }
  
  void setNextStageOff()
  {
    if(isRotating()) // rotating
      rotateNextStageOff();
    else
      linearNextStageOff();
      
  }

  void linearNextStageOn()
  {
    nxtStageOff = nxtStageOn;
    curStagesOn = nxtStageOn;
    getStatusOutput(nxtStageOff).setValue(true);
    //((BStatusBoolean)get(("out"+nxtStageOff))).setValue(true);
    nxtStageOn = nxtStageOff+1;
    setNextStageOff(nxtStageOff);
    setNextStageOn(nxtStageOn);
  }
  
  void linearNextStageOff()
  {
    nxtStageOn = nxtStageOff;
    curStagesOn = nxtStageOff-1;
    getStatusOutput(nxtStageOff).setValue(false);
    //((BStatusBoolean)get(("out"+nxtStageOff))).setValue(false);
    nxtStageOff = nxtStageOff-1;
    setNextStageOff(nxtStageOff);
    setNextStageOn(nxtStageOn);
  }
  

  void rotateNextStageOn()
  {
    if(curStagesOn == 0)
    {
      nxtStageOff = nxtStageOn;
      setNextStageOff(nxtStageOff);
      startStage = nxtStageOn;
    }
    getStatusOutput(nxtStageOn).setValue(true);
    //((BStatusBoolean)get(("out"+nxtStageOn))).setValue(true);
    curStagesOn++;
    incNextStageOn();
  }

  void incNextStageOn()
  {
    if(nxtStageOn < getNumberOutputs())
      nxtStageOn++;
    else
    {
      if(curStagesOn == numOutputs)
        nxtStageOn = 0;
      else
        nxtStageOn = 1;
    }
    setNextStageOn(nxtStageOn);  
  }
  
  void rotateNextStageOff()
  {
    if(curStagesOn == numOutputs)
    {
      nxtStageOn = nxtStageOff;
      setNextStageOn(nxtStageOn);
    }
    //else
    //{
      getStatusOutput(nxtStageOff).setValue(false);
      //((BStatusBoolean)get(("out"+nxtStageOff))).setValue(false);
      curStagesOn--;
      incNextStageOff();
    //}
  }
  
  void incNextStageOff()
  {
    if(nxtStageOff < getNumberOutputs())
      nxtStageOff++;
    else
    {
      if(curStagesOn == 0)
        nxtStageOff = 0;
      else
        nxtStageOff = 1;
    }
    if(curStagesOn == 0)
    {
      if( startStage < numOutputs )
        startStage++;
      else
        startStage = 1;
      nxtStageOn = startStage;
      setNextStageOn(nxtStageOn);  
    }    
    setNextStageOff(nxtStageOff);  
  }
    
  boolean isRotateTimeProgrammed()
  {
    return(getRotateTime().getMillis() != 0);
  }
  
  void startRotateTimer()
  {            
    if (rotateTicket != null) rotateTicket.cancel();
    setRotateTimerActive(true);
    rotateTicket = Clock.schedule(this, getRotateTime(), rotateTimerExpired, null);
  }
  

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  Clock.Ticket rotateTicket;   // Used to manage the rotatey timer
  
}
