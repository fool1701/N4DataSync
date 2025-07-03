/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.hvac;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BLink;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;


/**
 * BLeadLagRuntime is a component that can provide lead/lag control of 2 - 10 loads 
 * to balance start cycles.
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
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "numberOutputs",
  type = "int",
  defaultValue = "2",
  facets = @Facet("BFacets.makeInt(null, 2, 10)")
)
@NiagaraProperty(
  name = "maxRuntime",
  type = "BRelTime",
  defaultValue = "BRelTime.make(360000)"
)
@NiagaraProperty(
  name = "feedback",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "feedbackDelay",
  type = "BRelTime",
  defaultValue = "BRelTime.make(5000)"
)
@NiagaraProperty(
  name = "clearAlarmTime",
  type = "BRelTime",
  defaultValue = "BRelTime.makeHours(1)"
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
  flags = Flags.TRANSIENT
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
  name = "runtimeA",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "runtimeB",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "runtimeC",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "runtimeD",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "runtimeE",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "runtimeF",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "runtimeG",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "runtimeH",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "runtimeI",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)",
  flags = Flags.TRANSIENT
)
@NiagaraProperty(
  name = "runtimeJ",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)",
  flags = Flags.TRANSIENT
)
@NiagaraAction(
  name = "rotateTimerExpired",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "feedbackTimerExpired",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "clearAlarmState"
)
public class BLeadLagRuntime
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.hvac.BLeadLagRuntime(3042340647)1.0$ @*/
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
  public static final Property in = newProperty(Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code in} property.
   * @see #in
   */
  public BStatusBoolean getIn() { return (BStatusBoolean)get(in); }

  /**
   * Set the {@code in} property.
   * @see #in
   */
  public void setIn(BStatusBoolean v) { set(in, v, null); }

  //endregion Property "in"

  //region Property "numberOutputs"

  /**
   * Slot for the {@code numberOutputs} property.
   * @see #getNumberOutputs
   * @see #setNumberOutputs
   */
  public static final Property numberOutputs = newProperty(0, 2, BFacets.makeInt(null, 2, 10));

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

  //region Property "maxRuntime"

  /**
   * Slot for the {@code maxRuntime} property.
   * @see #getMaxRuntime
   * @see #setMaxRuntime
   */
  public static final Property maxRuntime = newProperty(0, BRelTime.make(360000), null);

  /**
   * Get the {@code maxRuntime} property.
   * @see #maxRuntime
   */
  public BRelTime getMaxRuntime() { return (BRelTime)get(maxRuntime); }

  /**
   * Set the {@code maxRuntime} property.
   * @see #maxRuntime
   */
  public void setMaxRuntime(BRelTime v) { set(maxRuntime, v, null); }

  //endregion Property "maxRuntime"

  //region Property "feedback"

  /**
   * Slot for the {@code feedback} property.
   * @see #getFeedback
   * @see #setFeedback
   */
  public static final Property feedback = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code feedback} property.
   * @see #feedback
   */
  public BStatusBoolean getFeedback() { return (BStatusBoolean)get(feedback); }

  /**
   * Set the {@code feedback} property.
   * @see #feedback
   */
  public void setFeedback(BStatusBoolean v) { set(feedback, v, null); }

  //endregion Property "feedback"

  //region Property "feedbackDelay"

  /**
   * Slot for the {@code feedbackDelay} property.
   * @see #getFeedbackDelay
   * @see #setFeedbackDelay
   */
  public static final Property feedbackDelay = newProperty(0, BRelTime.make(5000), null);

  /**
   * Get the {@code feedbackDelay} property.
   * @see #feedbackDelay
   */
  public BRelTime getFeedbackDelay() { return (BRelTime)get(feedbackDelay); }

  /**
   * Set the {@code feedbackDelay} property.
   * @see #feedbackDelay
   */
  public void setFeedbackDelay(BRelTime v) { set(feedbackDelay, v, null); }

  //endregion Property "feedbackDelay"

  //region Property "clearAlarmTime"

  /**
   * Slot for the {@code clearAlarmTime} property.
   * @see #getClearAlarmTime
   * @see #setClearAlarmTime
   */
  public static final Property clearAlarmTime = newProperty(0, BRelTime.makeHours(1), null);

  /**
   * Get the {@code clearAlarmTime} property.
   * @see #clearAlarmTime
   */
  public BRelTime getClearAlarmTime() { return (BRelTime)get(clearAlarmTime); }

  /**
   * Set the {@code clearAlarmTime} property.
   * @see #clearAlarmTime
   */
  public void setClearAlarmTime(BRelTime v) { set(clearAlarmTime, v, null); }

  //endregion Property "clearAlarmTime"

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
  public static final Property outC = newProperty(Flags.TRANSIENT, new BStatusBoolean(), null);

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

  //region Property "runtimeA"

  /**
   * Slot for the {@code runtimeA} property.
   * @see #getRuntimeA
   * @see #setRuntimeA
   */
  public static final Property runtimeA = newProperty(Flags.TRANSIENT | Flags.SUMMARY, BRelTime.make(0), null);

  /**
   * Get the {@code runtimeA} property.
   * @see #runtimeA
   */
  public BRelTime getRuntimeA() { return (BRelTime)get(runtimeA); }

  /**
   * Set the {@code runtimeA} property.
   * @see #runtimeA
   */
  public void setRuntimeA(BRelTime v) { set(runtimeA, v, null); }

  //endregion Property "runtimeA"

  //region Property "runtimeB"

  /**
   * Slot for the {@code runtimeB} property.
   * @see #getRuntimeB
   * @see #setRuntimeB
   */
  public static final Property runtimeB = newProperty(Flags.TRANSIENT | Flags.SUMMARY, BRelTime.make(0), null);

  /**
   * Get the {@code runtimeB} property.
   * @see #runtimeB
   */
  public BRelTime getRuntimeB() { return (BRelTime)get(runtimeB); }

  /**
   * Set the {@code runtimeB} property.
   * @see #runtimeB
   */
  public void setRuntimeB(BRelTime v) { set(runtimeB, v, null); }

  //endregion Property "runtimeB"

  //region Property "runtimeC"

  /**
   * Slot for the {@code runtimeC} property.
   * @see #getRuntimeC
   * @see #setRuntimeC
   */
  public static final Property runtimeC = newProperty(Flags.TRANSIENT, BRelTime.make(0), null);

  /**
   * Get the {@code runtimeC} property.
   * @see #runtimeC
   */
  public BRelTime getRuntimeC() { return (BRelTime)get(runtimeC); }

  /**
   * Set the {@code runtimeC} property.
   * @see #runtimeC
   */
  public void setRuntimeC(BRelTime v) { set(runtimeC, v, null); }

  //endregion Property "runtimeC"

  //region Property "runtimeD"

  /**
   * Slot for the {@code runtimeD} property.
   * @see #getRuntimeD
   * @see #setRuntimeD
   */
  public static final Property runtimeD = newProperty(Flags.TRANSIENT, BRelTime.make(0), null);

  /**
   * Get the {@code runtimeD} property.
   * @see #runtimeD
   */
  public BRelTime getRuntimeD() { return (BRelTime)get(runtimeD); }

  /**
   * Set the {@code runtimeD} property.
   * @see #runtimeD
   */
  public void setRuntimeD(BRelTime v) { set(runtimeD, v, null); }

  //endregion Property "runtimeD"

  //region Property "runtimeE"

  /**
   * Slot for the {@code runtimeE} property.
   * @see #getRuntimeE
   * @see #setRuntimeE
   */
  public static final Property runtimeE = newProperty(Flags.TRANSIENT, BRelTime.make(0), null);

  /**
   * Get the {@code runtimeE} property.
   * @see #runtimeE
   */
  public BRelTime getRuntimeE() { return (BRelTime)get(runtimeE); }

  /**
   * Set the {@code runtimeE} property.
   * @see #runtimeE
   */
  public void setRuntimeE(BRelTime v) { set(runtimeE, v, null); }

  //endregion Property "runtimeE"

  //region Property "runtimeF"

  /**
   * Slot for the {@code runtimeF} property.
   * @see #getRuntimeF
   * @see #setRuntimeF
   */
  public static final Property runtimeF = newProperty(Flags.TRANSIENT, BRelTime.make(0), null);

  /**
   * Get the {@code runtimeF} property.
   * @see #runtimeF
   */
  public BRelTime getRuntimeF() { return (BRelTime)get(runtimeF); }

  /**
   * Set the {@code runtimeF} property.
   * @see #runtimeF
   */
  public void setRuntimeF(BRelTime v) { set(runtimeF, v, null); }

  //endregion Property "runtimeF"

  //region Property "runtimeG"

  /**
   * Slot for the {@code runtimeG} property.
   * @see #getRuntimeG
   * @see #setRuntimeG
   */
  public static final Property runtimeG = newProperty(Flags.TRANSIENT, BRelTime.make(0), null);

  /**
   * Get the {@code runtimeG} property.
   * @see #runtimeG
   */
  public BRelTime getRuntimeG() { return (BRelTime)get(runtimeG); }

  /**
   * Set the {@code runtimeG} property.
   * @see #runtimeG
   */
  public void setRuntimeG(BRelTime v) { set(runtimeG, v, null); }

  //endregion Property "runtimeG"

  //region Property "runtimeH"

  /**
   * Slot for the {@code runtimeH} property.
   * @see #getRuntimeH
   * @see #setRuntimeH
   */
  public static final Property runtimeH = newProperty(Flags.TRANSIENT, BRelTime.make(0), null);

  /**
   * Get the {@code runtimeH} property.
   * @see #runtimeH
   */
  public BRelTime getRuntimeH() { return (BRelTime)get(runtimeH); }

  /**
   * Set the {@code runtimeH} property.
   * @see #runtimeH
   */
  public void setRuntimeH(BRelTime v) { set(runtimeH, v, null); }

  //endregion Property "runtimeH"

  //region Property "runtimeI"

  /**
   * Slot for the {@code runtimeI} property.
   * @see #getRuntimeI
   * @see #setRuntimeI
   */
  public static final Property runtimeI = newProperty(Flags.TRANSIENT, BRelTime.make(0), null);

  /**
   * Get the {@code runtimeI} property.
   * @see #runtimeI
   */
  public BRelTime getRuntimeI() { return (BRelTime)get(runtimeI); }

  /**
   * Set the {@code runtimeI} property.
   * @see #runtimeI
   */
  public void setRuntimeI(BRelTime v) { set(runtimeI, v, null); }

  //endregion Property "runtimeI"

  //region Property "runtimeJ"

  /**
   * Slot for the {@code runtimeJ} property.
   * @see #getRuntimeJ
   * @see #setRuntimeJ
   */
  public static final Property runtimeJ = newProperty(Flags.TRANSIENT, BRelTime.make(0), null);

  /**
   * Get the {@code runtimeJ} property.
   * @see #runtimeJ
   */
  public BRelTime getRuntimeJ() { return (BRelTime)get(runtimeJ); }

  /**
   * Set the {@code runtimeJ} property.
   * @see #runtimeJ
   */
  public void setRuntimeJ(BRelTime v) { set(runtimeJ, v, null); }

  //endregion Property "runtimeJ"

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

  //region Action "feedbackTimerExpired"

  /**
   * Slot for the {@code feedbackTimerExpired} action.
   * @see #feedbackTimerExpired()
   */
  public static final Action feedbackTimerExpired = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code feedbackTimerExpired} action.
   * @see #feedbackTimerExpired
   */
  public void feedbackTimerExpired() { invoke(feedbackTimerExpired, null, null); }

  //endregion Action "feedbackTimerExpired"

  //region Action "clearAlarmState"

  /**
   * Slot for the {@code clearAlarmState} action.
   * @see #clearAlarmState()
   */
  public static final Action clearAlarmState = newAction(0, null);

  /**
   * Invoke the {@code clearAlarmState} action.
   * @see #clearAlarmState
   */
  public void clearAlarmState() { invoke(clearAlarmState, null, null); }

  //endregion Action "clearAlarmState"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLeadLagRuntime.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BLeadLagRuntime()
  {
  }
  
  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    initNumberOutputs();
  }
  
  public void atSteadyState()
  {
    currentIn = getIn().getValue();
    calculate();
  }
/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if(p == numberOutputs)
    {
      initNumberOutputs();
    }

    if (!isRunning()) return;

    if(p == in) 
    {
      currentIn = getIn().getValue();
      calculate();
    }
    else if(p == feedback)
    {
      if(!currentIn) return;
      if(getFeedback().getValue()) return;
      if(feedbackTimerActive) return;
      setCurrentOutputAlarm(true);
      getRuntimes();  // load current cycle counts.
      startOutput();
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
      case 0: initSlot("outA", setFlags | Flags.READONLY); initSlot("runtimeA", setFlags); break;
      case 1: initSlot("outB", setFlags | Flags.READONLY); initSlot("runtimeB", setFlags); break;
      case 2: initSlot("outC", setFlags | Flags.READONLY); initSlot("runtimeC", setFlags); break;
      case 3: initSlot("outD", setFlags | Flags.READONLY); initSlot("runtimeD", setFlags); break;
      case 4: initSlot("outE", setFlags | Flags.READONLY); initSlot("runtimeE", setFlags); break;
      case 5: initSlot("outF", setFlags | Flags.READONLY); initSlot("runtimeF", setFlags); break;
      case 6: initSlot("outG", setFlags | Flags.READONLY); initSlot("runtimeG", setFlags); break;
      case 7: initSlot("outH", setFlags | Flags.READONLY); initSlot("runtimeH", setFlags); break;
      case 8: initSlot("outI", setFlags | Flags.READONLY); initSlot("runtimeI", setFlags); break;
      case 9: initSlot("outJ", setFlags | Flags.READONLY); initSlot("runtimeJ", setFlags); break;
      }
    }
  }

  void initSlot(String slot, int flags)
  {
      try { setFlags(getSlot(slot), flags, null);} catch(Exception e) {};
  }


  public void doClearAlarmState()
  {
    if(this.alarmClearTicket != null)
    {
      alarmClearTicket.cancel();
      alarmClearTicket = null;
    }
    for( int i = 0; i < numOutputs; i++)
    {
      getStatusOutput(i).setStatusInAlarm(false);
    }
  }

  public void doRotateTimerExpired()
  {
    if(!currentIn) return;
    getRuntimes();  // load current cycle counts.
    startOutput();
//    startRotateTimer();     // start rotate timer
  }

  public void doFeedbackTimerExpired()
  {
    feedbackTimerActive = false;
    if(!currentIn) return;
    if(getFeedback().getValue()) return;
    setCurrentOutputAlarm(true);
    getRuntimes();  // load current cycle counts.
    startOutput();
  }


  void calculate()
  { 
    getRuntimes();  // load current cycle counts.
    
    if(currentIn)  // if controlIn is active
    {
      if(!wasOn)   // and was not active on last execute
      {
        startOutput();          // start lowest runtime output
//        startRotateTimer();     // start rotate timer
        wasOn = true;           // set was on flag.
      }
    }
    else  // control in is not active
    {
      if(wasOn)  // if was active on last execute
      {
        if(rotateTicket != null) rotateTicket.cancel();
        if(feedbackTicket != null) feedbackTicket.cancel();
        wasOn = false;             // clear was on flag.
      }
      stopOutput();              // stop all outputs
      
    }
  }

  // read linked runtimes into array
  // set unlinked runtimes to -1
  void getRuntimes()
  {
    for(int i = 0; i < numOutputs; i++)
    {
      if( isRuntimeLinked(i) && !getOutValue(i) )
        runtimes[i] = getRuntime(i);
      else
        runtimes[i] = Long.MAX_VALUE;
    }
  }

  long getRuntime(int index)
  {
    switch(index)
    {
    case 0: return getRuntimeA().getMillis();
    case 1: return getRuntimeB().getMillis();
    case 2: return getRuntimeC().getMillis();
    case 3: return getRuntimeD().getMillis();
    case 4: return getRuntimeE().getMillis();
    case 5: return getRuntimeF().getMillis();
    case 6: return getRuntimeG().getMillis();
    case 7: return getRuntimeH().getMillis();
    case 8: return getRuntimeI().getMillis();
    case 9: return getRuntimeJ().getMillis();
    }
    return Long.MAX_VALUE; 
  }

  boolean getOutValue(int index)
  {
    switch(index)
    {
    case 0: return getOutA().getValue();
    case 1: return getOutB().getValue();
    case 2: return getOutC().getValue();
    case 3: return getOutD().getValue();
    case 4: return getOutE().getValue();
    case 5: return getOutF().getValue();
    case 6: return getOutG().getValue();
    case 7: return getOutH().getValue();
    case 8: return getOutI().getValue();
    case 9: return getOutJ().getValue();
    }
    return false; 
  }
  
  // Test to see if cycle count slot is linked
  boolean isRuntimeLinked(int index)
  {
    BLink[] links = null;
    switch(index)
    {
    case 0: links = getLinks(runtimeA); break;
    case 1: links = getLinks(runtimeB); break;
    case 2: links = getLinks(runtimeC); break;
    case 3: links = getLinks(runtimeD); break;
    case 4: links = getLinks(runtimeE); break;
    case 5: links = getLinks(runtimeF); break;
    case 6: links = getLinks(runtimeG); break;
    case 7: links = getLinks(runtimeH); break;
    case 8: links = getLinks(runtimeI); break;
    case 9: links = getLinks(runtimeJ); break;
    }
    if(links == null)
      return false;
    return (links.length != 0);
  }
  
  // This method will start the output with the lowest runtime.
  // It will turn off all other outputs.
  void startOutput()
  {
    long minRuntime = Long.MAX_VALUE;
    int index = 0;
    for( int i = 0; i < numOutputs; i++)
    {
      if(getStatusOutput(i).getStatus().isAlarm())
        continue;
      if(runtimes[i] < minRuntime)
      {
        minRuntime = runtimes[i];
        index = i;
      }
    }
//    getStatusOutput(index).setStatusInAlarm(false);
    for(int i = 0; i < numOutputs; i++)
    {
      getStatusOutput(i).setValue(index == i);
    }
    startRotateTimer();
    startFeedbackTimer();
  }


  BStatusBoolean getStatusOutput(int index)
  {
    switch(index)
    {
    case 0: return getOutA();
    case 1: return getOutB();
    case 2: return getOutC();
    case 3: return getOutD();
    case 4: return getOutE();
    case 5: return getOutF();
    case 6: return getOutG();
    case 7: return getOutH();
    case 8: return getOutI();
    case 9: return getOutJ();
    }
    if(index < 0) return getOutA();
    return getOutJ();
  }

  
  void setCurrentOutputAlarm(boolean alarm)
  {
    if(alarm)
    {
      startAlarmClearTimer();
    }
    BStatusBoolean currentOut;
    for(int i = 0; i < numOutputs; i++)
    {
      currentOut = getStatusOutput(i);
      if(currentOut.getValue())
        currentOut.setStatusInAlarm(alarm);
    }
  }

  // Turn off all outputs
  void stopOutput()
  {
    for(int i = 0; i < numOutputs; i++)
      getStatusOutput(i).setValue(false);
  }
  
  // start alarm clear timer. It will call back clearAlarmState when it expires.
  void startAlarmClearTimer()
  {
    BRelTime clearTime = getClearAlarmTime();
    if (clearTime.getMillis() == 0 || alarmClearTicket != null) return;
    alarmClearTicket = Clock.schedule(this, clearTime, clearAlarmState, null);
  }    
  
  // start rotate timer. It will call back execute when it expires.
  void startRotateTimer()
  {            
    if (rotateTicket != null) rotateTicket.cancel();
    rotateTicket = Clock.schedule(this, getMaxRuntime(), rotateTimerExpired, null);
  }    
  
  // start feedback timer. It will call back execute when it expires.
  void startFeedbackTimer()
  {            
    if (feedbackTicket != null) feedbackTicket.cancel();
    feedbackTimerActive = true;
    feedbackTicket = Clock.schedule(this, getFeedbackDelay(), feedbackTimerExpired, null);
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
  int numOutputs;
  int lastNumberOutputs;
  boolean feedbackTimerActive = false;
  boolean currentIn;
  boolean wasOn = false;
  long[] runtimes = new long[10];

  // rotate timer ticket.
  Clock.Ticket rotateTicket;    // Used to manage the on delay timer
  Clock.Ticket feedbackTicket;    // Used to manage the on delay timer
  Clock.Ticket alarmClearTicket;    // Used to manage the on delay timer
  
}
