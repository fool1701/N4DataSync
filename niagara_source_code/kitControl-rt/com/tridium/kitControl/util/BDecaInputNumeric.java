/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import java.io.*;

import javax.baja.control.*;
import javax.baja.io.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BDecaInputNumeric is the base class for 10 input numeric objects.
 *   It is a 3 to 10 position switch. 
 *
 * @author    Andy Saunders
 * @creation  20 April 2004
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 These facets are applied against the out property.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT"
)
/*
 number of inputs
 */
@NiagaraProperty(
  name = "numberValues",
  type = "int",
  defaultValue = "2",
  facets = @Facet("BFacets.makeInt(null, 2, 10)")
)
/*
 The output of a BNumericPoint is a BStatusNumeric
 */
@NiagaraProperty(
  name = "min",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY
)
/*
 The output of a BNumericPoint is a BStatusNumeric
 */
@NiagaraProperty(
  name = "max",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY
)
/*
 The output of a BNumericPoint is a BStatusNumeric
 */
@NiagaraProperty(
  name = "avg",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "inA",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inB",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inC",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inD",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inE",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inF",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inG",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inH",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inI",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inJ",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
public abstract class BDecaInputNumeric
  extends BComponent
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BDecaInputNumeric(2514205622)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the out property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.DEFAULT, null);

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

  //region Property "numberValues"

  /**
   * Slot for the {@code numberValues} property.
   * number of inputs
   * @see #getNumberValues
   * @see #setNumberValues
   */
  public static final Property numberValues = newProperty(0, 2, BFacets.makeInt(null, 2, 10));

  /**
   * Get the {@code numberValues} property.
   * number of inputs
   * @see #numberValues
   */
  public int getNumberValues() { return getInt(numberValues); }

  /**
   * Set the {@code numberValues} property.
   * number of inputs
   * @see #numberValues
   */
  public void setNumberValues(int v) { setInt(numberValues, v, null); }

  //endregion Property "numberValues"

  //region Property "min"

  /**
   * Slot for the {@code min} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #getMin
   * @see #setMin
   */
  public static final Property min = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code min} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #min
   */
  public BStatusNumeric getMin() { return (BStatusNumeric)get(min); }

  /**
   * Set the {@code min} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #min
   */
  public void setMin(BStatusNumeric v) { set(min, v, null); }

  //endregion Property "min"

  //region Property "max"

  /**
   * Slot for the {@code max} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #getMax
   * @see #setMax
   */
  public static final Property max = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code max} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #max
   */
  public BStatusNumeric getMax() { return (BStatusNumeric)get(max); }

  /**
   * Set the {@code max} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #max
   */
  public void setMax(BStatusNumeric v) { set(max, v, null); }

  //endregion Property "max"

  //region Property "avg"

  /**
   * Slot for the {@code avg} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #getAvg
   * @see #setAvg
   */
  public static final Property avg = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code avg} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #avg
   */
  public BStatusNumeric getAvg() { return (BStatusNumeric)get(avg); }

  /**
   * Set the {@code avg} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #avg
   */
  public void setAvg(BStatusNumeric v) { set(avg, v, null); }

  //endregion Property "avg"

  //region Property "inA"

  /**
   * Slot for the {@code inA} property.
   * @see #getInA
   * @see #setInA
   */
  public static final Property inA = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inA} property.
   * @see #inA
   */
  public BStatusNumeric getInA() { return (BStatusNumeric)get(inA); }

  /**
   * Set the {@code inA} property.
   * @see #inA
   */
  public void setInA(BStatusNumeric v) { set(inA, v, null); }

  //endregion Property "inA"

  //region Property "inB"

  /**
   * Slot for the {@code inB} property.
   * @see #getInB
   * @see #setInB
   */
  public static final Property inB = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inB} property.
   * @see #inB
   */
  public BStatusNumeric getInB() { return (BStatusNumeric)get(inB); }

  /**
   * Set the {@code inB} property.
   * @see #inB
   */
  public void setInB(BStatusNumeric v) { set(inB, v, null); }

  //endregion Property "inB"

  //region Property "inC"

  /**
   * Slot for the {@code inC} property.
   * @see #getInC
   * @see #setInC
   */
  public static final Property inC = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inC} property.
   * @see #inC
   */
  public BStatusNumeric getInC() { return (BStatusNumeric)get(inC); }

  /**
   * Set the {@code inC} property.
   * @see #inC
   */
  public void setInC(BStatusNumeric v) { set(inC, v, null); }

  //endregion Property "inC"

  //region Property "inD"

  /**
   * Slot for the {@code inD} property.
   * @see #getInD
   * @see #setInD
   */
  public static final Property inD = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inD} property.
   * @see #inD
   */
  public BStatusNumeric getInD() { return (BStatusNumeric)get(inD); }

  /**
   * Set the {@code inD} property.
   * @see #inD
   */
  public void setInD(BStatusNumeric v) { set(inD, v, null); }

  //endregion Property "inD"

  //region Property "inE"

  /**
   * Slot for the {@code inE} property.
   * @see #getInE
   * @see #setInE
   */
  public static final Property inE = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inE} property.
   * @see #inE
   */
  public BStatusNumeric getInE() { return (BStatusNumeric)get(inE); }

  /**
   * Set the {@code inE} property.
   * @see #inE
   */
  public void setInE(BStatusNumeric v) { set(inE, v, null); }

  //endregion Property "inE"

  //region Property "inF"

  /**
   * Slot for the {@code inF} property.
   * @see #getInF
   * @see #setInF
   */
  public static final Property inF = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inF} property.
   * @see #inF
   */
  public BStatusNumeric getInF() { return (BStatusNumeric)get(inF); }

  /**
   * Set the {@code inF} property.
   * @see #inF
   */
  public void setInF(BStatusNumeric v) { set(inF, v, null); }

  //endregion Property "inF"

  //region Property "inG"

  /**
   * Slot for the {@code inG} property.
   * @see #getInG
   * @see #setInG
   */
  public static final Property inG = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inG} property.
   * @see #inG
   */
  public BStatusNumeric getInG() { return (BStatusNumeric)get(inG); }

  /**
   * Set the {@code inG} property.
   * @see #inG
   */
  public void setInG(BStatusNumeric v) { set(inG, v, null); }

  //endregion Property "inG"

  //region Property "inH"

  /**
   * Slot for the {@code inH} property.
   * @see #getInH
   * @see #setInH
   */
  public static final Property inH = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inH} property.
   * @see #inH
   */
  public BStatusNumeric getInH() { return (BStatusNumeric)get(inH); }

  /**
   * Set the {@code inH} property.
   * @see #inH
   */
  public void setInH(BStatusNumeric v) { set(inH, v, null); }

  //endregion Property "inH"

  //region Property "inI"

  /**
   * Slot for the {@code inI} property.
   * @see #getInI
   * @see #setInI
   */
  public static final Property inI = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inI} property.
   * @see #inI
   */
  public BStatusNumeric getInI() { return (BStatusNumeric)get(inI); }

  /**
   * Set the {@code inI} property.
   * @see #inI
   */
  public void setInI(BStatusNumeric v) { set(inI, v, null); }

  //endregion Property "inI"

  //region Property "inJ"

  /**
   * Slot for the {@code inJ} property.
   * @see #getInJ
   * @see #setInJ
   */
  public static final Property inJ = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code inJ} property.
   * @see #inJ
   */
  public BStatusNumeric getInJ() { return (BStatusNumeric)get(inJ); }

  /**
   * Set the {@code inJ} property.
   * @see #inJ
   */
  public void setInJ(BStatusNumeric v) { set(inJ, v, null); }

  //endregion Property "inJ"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDecaInputNumeric.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// ControlPoint
////////////////////////////////////////////////////////////////  

  public void started()
  {
    initNumberValues();
    calculate();
  }

  public void changed(Property property, Context context) 
  {
    if(isRunning())
    {
      if(property == numberValues)
      {
        initNumberValues();
        calculate();
      }
      else if(property.getName().startsWith("in") )
      {
        calculate();
      }
    }
  }

  //public abstract BStatusValue getOutStatusValue();
  //public abstract BStatusValue getInStatusValue();
  //public abstract BStatusValue getNullInstance();
  //public abstract BStatusValue getInStatusValue(int select);

  //public abstract void setOutputs(BStatusValue[] values);
  //public abstract void setOutput(BStatusValue value);
  public abstract void doCalculate();
  //public abstract void initNumberValues();

  //public abstract boolean isMuxSwitch();

  /**
   * Default implementation is to do nothing.
   */
  public void calculate()
  {
    doCalculate();
  }


  public void initNumberValues()
  {
    numValues = getNumberValues();
    int setFlags;
    for(int i = 0; i < 10; i++)
    {
      if(i >= numValues)
        setFlags = Flags.HIDDEN | Flags.TRANSIENT;
      else
        setFlags = Flags.SUMMARY;
      switch(i)
      {
      case 0: try {setFlags(getSlot("inA"), setFlags, null);} catch(Exception e) {} break;
      case 1: try {setFlags(getSlot("inB"), setFlags, null);} catch(Exception e) {} break;
      case 2: try {setFlags(getSlot("inC"), setFlags, null);} catch(Exception e) {} break;
      case 3: try {setFlags(getSlot("inD"), setFlags, null);} catch(Exception e) {} break;
      case 4: try {setFlags(getSlot("inE"), setFlags, null);} catch(Exception e) {} break;
      case 5: try {setFlags(getSlot("inF"), setFlags, null);} catch(Exception e) {} break;
      case 6: try {setFlags(getSlot("inG"), setFlags, null);} catch(Exception e) {} break;
      case 7: try {setFlags(getSlot("inH"), setFlags, null);} catch(Exception e) {} break;
      case 8: try {setFlags(getSlot("inI"), setFlags, null);} catch(Exception e) {} break;
      case 9: try {setFlags(getSlot("inJ"), setFlags, null);} catch(Exception e) {} break;
      }
    }
  }

  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.getName().startsWith("out")) return getFacets();
    return super.getSlotFacets(slot);
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////  

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/util/switch.png");

  int numValues = 2;

}
