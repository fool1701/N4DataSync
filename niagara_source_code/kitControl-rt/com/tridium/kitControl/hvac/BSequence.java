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


// this is a work in progress  
/**
 * BSequence is a component that 2 - 10 load sequence function.
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 These facets are applied against the out property.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.makeBoolean()"
)
@NiagaraProperty(
  name = "in",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inMinimum",
  type = "double",
  defaultValue = "0.0d"
)
@NiagaraProperty(
  name = "inMaximum",
  type = "double",
  defaultValue = "100.0d"
)
@NiagaraProperty(
  name = "numberOutputs",
  type = "int",
  defaultValue = "3",
  facets = @Facet("BFacets.makeInt(null, 2, 10)")
)
@NiagaraProperty(
  name = "outA",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "outB",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "outC",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "outD",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "outE",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "outF",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "outG",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "outH",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "outI",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "outJ",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "delay",
  type = "BRelTime",
  defaultValue = "BRelTime.make(1000)"
)
@NiagaraProperty(
  name = "onDelayActive",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "offDelayActive",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "desiredStagesOn",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "currentStagesOn",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "nextStageOn",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "nextStageOff",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraAction(
  name = "onDelayTimerExpired",
  flags = Flags.HIDDEN | Flags.ASYNC
)
@NiagaraAction(
  name = "offDelayTimerExpired",
  flags = Flags.HIDDEN | Flags.ASYNC
)
public abstract class BSequence
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.hvac.BSequence(1887849629)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the out property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeBoolean(), null);

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

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code in} property.
   * @see #in
   */
  public BStatusNumeric getIn() { return (BStatusNumeric)get(in); }

  /**
   * Set the {@code in} property.
   * @see #in
   */
  public void setIn(BStatusNumeric v) { set(in, v, null); }

  //endregion Property "in"

  //region Property "inMinimum"

  /**
   * Slot for the {@code inMinimum} property.
   * @see #getInMinimum
   * @see #setInMinimum
   */
  public static final Property inMinimum = newProperty(0, 0.0d, null);

  /**
   * Get the {@code inMinimum} property.
   * @see #inMinimum
   */
  public double getInMinimum() { return getDouble(inMinimum); }

  /**
   * Set the {@code inMinimum} property.
   * @see #inMinimum
   */
  public void setInMinimum(double v) { setDouble(inMinimum, v, null); }

  //endregion Property "inMinimum"

  //region Property "inMaximum"

  /**
   * Slot for the {@code inMaximum} property.
   * @see #getInMaximum
   * @see #setInMaximum
   */
  public static final Property inMaximum = newProperty(0, 100.0d, null);

  /**
   * Get the {@code inMaximum} property.
   * @see #inMaximum
   */
  public double getInMaximum() { return getDouble(inMaximum); }

  /**
   * Set the {@code inMaximum} property.
   * @see #inMaximum
   */
  public void setInMaximum(double v) { setDouble(inMaximum, v, null); }

  //endregion Property "inMaximum"

  //region Property "numberOutputs"

  /**
   * Slot for the {@code numberOutputs} property.
   * @see #getNumberOutputs
   * @see #setNumberOutputs
   */
  public static final Property numberOutputs = newProperty(0, 3, BFacets.makeInt(null, 2, 10));

  /**
   * Get the {@code numberOutputs} property.
   * @see #numberOutputs
   */
  public int getNumberOutputs() { return getInt(numberOutputs); }

  /**
   * Set the {@code numberOutputs} property.
   * @see #numberOutputs
   */
  public void setNumberOutputs(int v) { setInt(numberOutputs, v, null); }

  //endregion Property "numberOutputs"

  //region Property "outA"

  /**
   * Slot for the {@code outA} property.
   * @see #getOutA
   * @see #setOutA
   */
  public static final Property outA = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code outA} property.
   * @see #outA
   */
  public BStatusBoolean getOutA() { return (BStatusBoolean)get(outA); }

  /**
   * Set the {@code outA} property.
   * @see #outA
   */
  public void setOutA(BStatusBoolean v) { set(outA, v, null); }

  //endregion Property "outA"

  //region Property "outB"

  /**
   * Slot for the {@code outB} property.
   * @see #getOutB
   * @see #setOutB
   */
  public static final Property outB = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code outB} property.
   * @see #outB
   */
  public BStatusBoolean getOutB() { return (BStatusBoolean)get(outB); }

  /**
   * Set the {@code outB} property.
   * @see #outB
   */
  public void setOutB(BStatusBoolean v) { set(outB, v, null); }

  //endregion Property "outB"

  //region Property "outC"

  /**
   * Slot for the {@code outC} property.
   * @see #getOutC
   * @see #setOutC
   */
  public static final Property outC = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code outC} property.
   * @see #outC
   */
  public BStatusBoolean getOutC() { return (BStatusBoolean)get(outC); }

  /**
   * Set the {@code outC} property.
   * @see #outC
   */
  public void setOutC(BStatusBoolean v) { set(outC, v, null); }

  //endregion Property "outC"

  //region Property "outD"

  /**
   * Slot for the {@code outD} property.
   * @see #getOutD
   * @see #setOutD
   */
  public static final Property outD = newProperty(Flags.TRANSIENT, new BStatusBoolean(), null);

  /**
   * Get the {@code outD} property.
   * @see #outD
   */
  public BStatusBoolean getOutD() { return (BStatusBoolean)get(outD); }

  /**
   * Set the {@code outD} property.
   * @see #outD
   */
  public void setOutD(BStatusBoolean v) { set(outD, v, null); }

  //endregion Property "outD"

  //region Property "outE"

  /**
   * Slot for the {@code outE} property.
   * @see #getOutE
   * @see #setOutE
   */
  public static final Property outE = newProperty(Flags.TRANSIENT, new BStatusBoolean(), null);

  /**
   * Get the {@code outE} property.
   * @see #outE
   */
  public BStatusBoolean getOutE() { return (BStatusBoolean)get(outE); }

  /**
   * Set the {@code outE} property.
   * @see #outE
   */
  public void setOutE(BStatusBoolean v) { set(outE, v, null); }

  //endregion Property "outE"

  //region Property "outF"

  /**
   * Slot for the {@code outF} property.
   * @see #getOutF
   * @see #setOutF
   */
  public static final Property outF = newProperty(Flags.TRANSIENT, new BStatusBoolean(), null);

  /**
   * Get the {@code outF} property.
   * @see #outF
   */
  public BStatusBoolean getOutF() { return (BStatusBoolean)get(outF); }

  /**
   * Set the {@code outF} property.
   * @see #outF
   */
  public void setOutF(BStatusBoolean v) { set(outF, v, null); }

  //endregion Property "outF"

  //region Property "outG"

  /**
   * Slot for the {@code outG} property.
   * @see #getOutG
   * @see #setOutG
   */
  public static final Property outG = newProperty(Flags.TRANSIENT, new BStatusBoolean(), null);

  /**
   * Get the {@code outG} property.
   * @see #outG
   */
  public BStatusBoolean getOutG() { return (BStatusBoolean)get(outG); }

  /**
   * Set the {@code outG} property.
   * @see #outG
   */
  public void setOutG(BStatusBoolean v) { set(outG, v, null); }

  //endregion Property "outG"

  //region Property "outH"

  /**
   * Slot for the {@code outH} property.
   * @see #getOutH
   * @see #setOutH
   */
  public static final Property outH = newProperty(Flags.TRANSIENT, new BStatusBoolean(), null);

  /**
   * Get the {@code outH} property.
   * @see #outH
   */
  public BStatusBoolean getOutH() { return (BStatusBoolean)get(outH); }

  /**
   * Set the {@code outH} property.
   * @see #outH
   */
  public void setOutH(BStatusBoolean v) { set(outH, v, null); }

  //endregion Property "outH"

  //region Property "outI"

  /**
   * Slot for the {@code outI} property.
   * @see #getOutI
   * @see #setOutI
   */
  public static final Property outI = newProperty(Flags.TRANSIENT, new BStatusBoolean(), null);

  /**
   * Get the {@code outI} property.
   * @see #outI
   */
  public BStatusBoolean getOutI() { return (BStatusBoolean)get(outI); }

  /**
   * Set the {@code outI} property.
   * @see #outI
   */
  public void setOutI(BStatusBoolean v) { set(outI, v, null); }

  //endregion Property "outI"

  //region Property "outJ"

  /**
   * Slot for the {@code outJ} property.
   * @see #getOutJ
   * @see #setOutJ
   */
  public static final Property outJ = newProperty(Flags.TRANSIENT, new BStatusBoolean(), null);

  /**
   * Get the {@code outJ} property.
   * @see #outJ
   */
  public BStatusBoolean getOutJ() { return (BStatusBoolean)get(outJ); }

  /**
   * Set the {@code outJ} property.
   * @see #outJ
   */
  public void setOutJ(BStatusBoolean v) { set(outJ, v, null); }

  //endregion Property "outJ"

  //region Property "delay"

  /**
   * Slot for the {@code delay} property.
   * @see #getDelay
   * @see #setDelay
   */
  public static final Property delay = newProperty(0, BRelTime.make(1000), null);

  /**
   * Get the {@code delay} property.
   * @see #delay
   */
  public BRelTime getDelay() { return (BRelTime)get(delay); }

  /**
   * Set the {@code delay} property.
   * @see #delay
   */
  public void setDelay(BRelTime v) { set(delay, v, null); }

  //endregion Property "delay"

  //region Property "onDelayActive"

  /**
   * Slot for the {@code onDelayActive} property.
   * @see #getOnDelayActive
   * @see #setOnDelayActive
   */
  public static final Property onDelayActive = newProperty(Flags.TRANSIENT | Flags.READONLY, false, null);

  /**
   * Get the {@code onDelayActive} property.
   * @see #onDelayActive
   */
  public boolean getOnDelayActive() { return getBoolean(onDelayActive); }

  /**
   * Set the {@code onDelayActive} property.
   * @see #onDelayActive
   */
  public void setOnDelayActive(boolean v) { setBoolean(onDelayActive, v, null); }

  //endregion Property "onDelayActive"

  //region Property "offDelayActive"

  /**
   * Slot for the {@code offDelayActive} property.
   * @see #getOffDelayActive
   * @see #setOffDelayActive
   */
  public static final Property offDelayActive = newProperty(Flags.TRANSIENT | Flags.READONLY, false, null);

  /**
   * Get the {@code offDelayActive} property.
   * @see #offDelayActive
   */
  public boolean getOffDelayActive() { return getBoolean(offDelayActive); }

  /**
   * Set the {@code offDelayActive} property.
   * @see #offDelayActive
   */
  public void setOffDelayActive(boolean v) { setBoolean(offDelayActive, v, null); }

  //endregion Property "offDelayActive"

  //region Property "desiredStagesOn"

  /**
   * Slot for the {@code desiredStagesOn} property.
   * @see #getDesiredStagesOn
   * @see #setDesiredStagesOn
   */
  public static final Property desiredStagesOn = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, null);

  /**
   * Get the {@code desiredStagesOn} property.
   * @see #desiredStagesOn
   */
  public int getDesiredStagesOn() { return getInt(desiredStagesOn); }

  /**
   * Set the {@code desiredStagesOn} property.
   * @see #desiredStagesOn
   */
  public void setDesiredStagesOn(int v) { setInt(desiredStagesOn, v, null); }

  //endregion Property "desiredStagesOn"

  //region Property "currentStagesOn"

  /**
   * Slot for the {@code currentStagesOn} property.
   * @see #getCurrentStagesOn
   * @see #setCurrentStagesOn
   */
  public static final Property currentStagesOn = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, null);

  /**
   * Get the {@code currentStagesOn} property.
   * @see #currentStagesOn
   */
  public int getCurrentStagesOn() { return getInt(currentStagesOn); }

  /**
   * Set the {@code currentStagesOn} property.
   * @see #currentStagesOn
   */
  public void setCurrentStagesOn(int v) { setInt(currentStagesOn, v, null); }

  //endregion Property "currentStagesOn"

  //region Property "nextStageOn"

  /**
   * Slot for the {@code nextStageOn} property.
   * @see #getNextStageOn
   * @see #setNextStageOn
   */
  public static final Property nextStageOn = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, null);

  /**
   * Get the {@code nextStageOn} property.
   * @see #nextStageOn
   */
  public int getNextStageOn() { return getInt(nextStageOn); }

  /**
   * Set the {@code nextStageOn} property.
   * @see #nextStageOn
   */
  public void setNextStageOn(int v) { setInt(nextStageOn, v, null); }

  //endregion Property "nextStageOn"

  //region Property "nextStageOff"

  /**
   * Slot for the {@code nextStageOff} property.
   * @see #getNextStageOff
   * @see #setNextStageOff
   */
  public static final Property nextStageOff = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, null);

  /**
   * Get the {@code nextStageOff} property.
   * @see #nextStageOff
   */
  public int getNextStageOff() { return getInt(nextStageOff); }

  /**
   * Set the {@code nextStageOff} property.
   * @see #nextStageOff
   */
  public void setNextStageOff(int v) { setInt(nextStageOff, v, null); }

  //endregion Property "nextStageOff"

  //region Action "onDelayTimerExpired"

  /**
   * Slot for the {@code onDelayTimerExpired} action.
   * @see #onDelayTimerExpired()
   */
  public static final Action onDelayTimerExpired = newAction(Flags.HIDDEN | Flags.ASYNC, null);

  /**
   * Invoke the {@code onDelayTimerExpired} action.
   * @see #onDelayTimerExpired
   */
  public void onDelayTimerExpired() { invoke(onDelayTimerExpired, null, null); }

  //endregion Action "onDelayTimerExpired"

  //region Action "offDelayTimerExpired"

  /**
   * Slot for the {@code offDelayTimerExpired} action.
   * @see #offDelayTimerExpired()
   */
  public static final Action offDelayTimerExpired = newAction(Flags.HIDDEN | Flags.ASYNC, null);

  /**
   * Invoke the {@code offDelayTimerExpired} action.
   * @see #offDelayTimerExpired
   */
  public void offDelayTimerExpired() { invoke(offDelayTimerExpired, null, null); }

  //endregion Action "offDelayTimerExpired"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSequence.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BSequence()
  {
  }
  
  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    initNumberOutputs();
    numStagesOn = 0;
    curStagesOn = 0;
    setDesiredStagesOn(0);
    setCurrentStagesOn(0);
    nxtStageOn = 1;
    nxtStageOff = 0;
    setNextStageOn(1);
    setNextStageOff(0);
    setOutputsOff();
    calculate();
    setOutputs();
  }

  public void reinitialize()
  {
    if (offTicket != null) offTicket.cancel();
    if (onTicket != null) onTicket.cancel();
    setOnDelayActive(false);
    setOffDelayActive(false);
    started();

  }

/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if(p == numberOutputs)
    {
      initNumberOutputs();
    }
    else if(p == in /*|| p == offDelayActive || p == onDelayActive */ )
    {
      calculate();
      setOutputs(); 
    }
  }

  public void initNumberOutputs()
  {
    numOutputs = getNumberOutputs();
    int setFlags;
    for(int i = 0; i < 10; i++)
    {
      if(i >= numOutputs)
        setFlags = Flags.HIDDEN | Flags.TRANSIENT;
      else
        setFlags = Flags.SUMMARY | Flags.TRANSIENT;
      switch(i)
      {
      case 0: initSlot("outA", setFlags | Flags.READONLY); break;
      case 1: initSlot("outB", setFlags | Flags.READONLY); break;
      case 2: initSlot("outC", setFlags | Flags.READONLY); break;
      case 3: initSlot("outD", setFlags | Flags.READONLY); break;
      case 4: initSlot("outE", setFlags | Flags.READONLY); break;
      case 5: initSlot("outF", setFlags | Flags.READONLY); break;
      case 6: initSlot("outG", setFlags | Flags.READONLY); break;
      case 7: initSlot("outH", setFlags | Flags.READONLY); break;
      case 8: initSlot("outI", setFlags | Flags.READONLY); break;
      case 9: initSlot("outJ", setFlags | Flags.READONLY); break;
      }
    }
  }

  void initSlot(String slot, int flags)
  {
      try { setFlags(getSlot(slot), flags, null);} catch(Exception e) {};
  }


  public void doOnDelayTimerExpired()
  {
    //setOutput(true);
    setOnDelayActive(false);
    calculate();
    setOutputs();
  }

  public void doOffDelayTimerExpired()
  {
    //setOutput(false);
    setOffDelayActive(false);
    calculate();
    setOutputs();
  }

  public abstract void calculate();
  
  public abstract void setOutputs();
  
  public BStatusBoolean getStatusOutput(int index)
  {
    if(index < 1) return getOutA();
    switch(index)
    {
    case 1: return getOutA();
    case 2: return getOutB();
    case 3: return getOutC();
    case 4: return getOutD();
    case 5: return getOutE();
    case 6: return getOutF();
    case 7: return getOutG();
    case 8: return getOutH();
    case 9: return getOutI();
    case 10: return getOutJ();
    }
    return getOutJ();
  }

  public void setOutputsOff()
  {
    for(int i = 1; i<= getNumberOutputs(); i++)
    {
      try {getStatusOutput(i).setValue(false);} catch(Exception e){}
    }
  }

  public boolean isDelayProgrammed()
  {
    return(getDelay().getMillis() != 0);
  }
  
  public void startOnDelayTimer()
  {            
    if(offTicket != null)
    {
      offTicket.cancel();
      setOffDelayActive(false);
    }
    if (onTicket != null)
    {  
      onTicket.cancel();
    }
    onTicket = Clock.schedule(this, getDelay(), onDelayTimerExpired, null);
    setOnDelayActive(true);
  }    
  
  public void startOffDelayTimer()
  {
    if(onTicket != null)
    {
      onTicket.cancel();
      setOnDelayActive(false);
    }
    if (offTicket != null)
    {
      offTicket.cancel();
    }
    offTicket = Clock.schedule(this, getDelay(), offDelayTimerExpired, null);
    setOffDelayActive(true);
  }    
  

  /**
   * Apply the "facets" property to the "out" property.
   */
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.getName().startsWith("out")) return getFacets();
    return super.getSlotFacets(slot);
  }


  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/control.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  int numStagesOn;
  int curStagesOn;
  int nxtStageOn = 1;
  int nxtStageOff = 0;
  int numOutputs;
  int startStage = 1;
  boolean wasEqual = false;

  Clock.Ticket onTicket;    // Used to manage the on delay timer
  Clock.Ticket offTicket;   // Used to manage the off delay timer
  

}
