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
import javax.baja.util.*;


/** Demand Shed Object
 *
 *   This object receives inputs from a network EDL source and a local EDL source that
 *   specify the number of load levels that should be shed. The 'getSecondaryShedLevel()' is used as
 *   a backup when the 'primaryShedLevel' is not available.  This object controls
 *   outputs for 16 contiguous levels, the first of which is user specified.
 *
 *   It provides an output message that indicates this object's state in reference to
 *   the overall demand limiting control scheme.
 *
 *   Execution of this object can be enabled or disabled (default) either automatically
 *   or manually (via right click).
 *
 * @author    Andy Saunders
 * @creation  28 Feb 2005
 * @version   $Revision: 21$ $Date: 11/5/2003 5:12:11 PM$
 * @since     Baja 1.0
 */
 
@NiagaraType
@NiagaraProperty(
  name = "numberLevels",
  type = "int",
  defaultValue = "16",
  facets = @Facet("BFacets.makeInt(1, 32)")
)
@NiagaraProperty(
  name = "shedEnable",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "primaryShedLevel",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "secondaryShedLevel",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "status",
  type = "String",
  defaultValue = ""
)
@NiagaraAction(
  name = "calculate",
  flags = Flags.HIDDEN
)
public class BShedControl
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.energy.BShedControl(3963947422)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "numberLevels"

  /**
   * Slot for the {@code numberLevels} property.
   * @see #getNumberLevels
   * @see #setNumberLevels
   */
  public static final Property numberLevels = newProperty(0, 16, BFacets.makeInt(1, 32));

  /**
   * Get the {@code numberLevels} property.
   * @see #numberLevels
   */
  public int getNumberLevels() { return getInt(numberLevels); }

  /**
   * Set the {@code numberLevels} property.
   * @see #numberLevels
   */
  public void setNumberLevels(int v) { setInt(numberLevels, v, null); }

  //endregion Property "numberLevels"

  //region Property "shedEnable"

  /**
   * Slot for the {@code shedEnable} property.
   * @see #getShedEnable
   * @see #setShedEnable
   */
  public static final Property shedEnable = newProperty(0, true, null);

  /**
   * Get the {@code shedEnable} property.
   * @see #shedEnable
   */
  public boolean getShedEnable() { return getBoolean(shedEnable); }

  /**
   * Set the {@code shedEnable} property.
   * @see #shedEnable
   */
  public void setShedEnable(boolean v) { setBoolean(shedEnable, v, null); }

  //endregion Property "shedEnable"

  //region Property "primaryShedLevel"

  /**
   * Slot for the {@code primaryShedLevel} property.
   * @see #getPrimaryShedLevel
   * @see #setPrimaryShedLevel
   */
  public static final Property primaryShedLevel = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code primaryShedLevel} property.
   * @see #primaryShedLevel
   */
  public BStatusNumeric getPrimaryShedLevel() { return (BStatusNumeric)get(primaryShedLevel); }

  /**
   * Set the {@code primaryShedLevel} property.
   * @see #primaryShedLevel
   */
  public void setPrimaryShedLevel(BStatusNumeric v) { set(primaryShedLevel, v, null); }

  //endregion Property "primaryShedLevel"

  //region Property "secondaryShedLevel"

  /**
   * Slot for the {@code secondaryShedLevel} property.
   * @see #getSecondaryShedLevel
   * @see #setSecondaryShedLevel
   */
  public static final Property secondaryShedLevel = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code secondaryShedLevel} property.
   * @see #secondaryShedLevel
   */
  public BStatusNumeric getSecondaryShedLevel() { return (BStatusNumeric)get(secondaryShedLevel); }

  /**
   * Set the {@code secondaryShedLevel} property.
   * @see #secondaryShedLevel
   */
  public void setSecondaryShedLevel(BStatusNumeric v) { set(secondaryShedLevel, v, null); }

  //endregion Property "secondaryShedLevel"

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(0, "", null);

  /**
   * Get the {@code status} property.
   * @see #status
   */
  public String getStatus() { return getString(status); }

  /**
   * Set the {@code status} property.
   * @see #status
   */
  public void setStatus(String v) { setString(status, v, null); }

  //endregion Property "status"

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
  public static final Type TYPE = Sys.loadType(BShedControl.class);

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
	  if( !isRunning() || !Sys.atSteadyState() )
		  return;
    addRemoveOutputs();
    doCalculate();


  }
  public void changed(Property property, Context context) 
  {
  	super.changed(property, context);
    if( !Sys.atSteadyState() || !isRunning() )
	    return;
    if(property.equals(numberLevels))
    {
      addRemoveOutputs();
      doCalculate();
    }
    if(property.equals(shedEnable           ) ||
       property.equals(primaryShedLevel     ) ||
       property.equals(secondaryShedLevel   )     )
      doCalculate();
  }

  public void removed(Property property, BValue oldValue, Context context)
  {
    if(oldValue instanceof BLink)
      doCalculate();
  }

  private void addRemoveOutputs()
  {
    int numOuts = getNumberLevels();
    for(int i = 1; i <= 32; i++)
    {
      if( i <= numOuts)
      {
        if( getSlot("out" +i) == null )
        {
          Property prop = add("out" + i, getAutoValue());
          setFlags(prop, getFlags(prop) | Flags.SUMMARY);
        }
      }
      else
        if( getSlot("out" +i) != null )
          remove("out" + i);
    }
  }

  public void doCalculate()
  {
    //
    // Determine shedLevel source
    //
    if ( getLinks(primaryShedLevel).length == 0 || !getPrimaryShedLevel().getStatus().isValid() || getPrimaryShedLevel().getValue() < 0 )
      shedLevel = (int)getSecondaryShedLevel().getValue();
    else
      shedLevel = (int)getPrimaryShedLevel().getValue();
    
    for(int i = 1; i <= getNumberLevels(); i++)
    {
      if ( getShedEnable() != true || shedLevel < i )	// if shed disabled or less than i, relinquish control
      {
        set("out"+i, getAutoValue());
      }
      else
      {
        set("out"+i, getShedValue());
      }
    }
    
    // 
    // Determine mode for messaging 
    //

    mode = SHED_LEVEL;
    if( !getShedEnable() )
    {
      mode = DISABLED;
      shedLevel = 0;
    }
    else if( shedLevel == 0)
      mode = ALL_RESTORED;
    else if( shedLevel >= getNumberLevels() )
      mode = ALL_SHED;
    else if( shedLevel > previousLevel )
      mode = SHEDDING_LEVEL;
    else if( shedLevel < previousLevel )
      mode = RESTORING_LEVEL;
    previousLevel = shedLevel;
    
    // 
    // Inform operator what is happening 
    //
    switch(mode)
    {
    case SHEDDING_LEVEL : setStatus(SHEDDING_LEVEL_MSG + ' ' + shedLevel); break;
    case RESTORING_LEVEL: setStatus(RESTORING_LEVEL_MSG + ' ' + (shedLevel + 1)); break;
    case DISABLED       : setStatus(DISABLED_MSG); break;
    case ALL_SHED       : setStatus(ALL_SHED_MSG); break;
    case ALL_RESTORED   : setStatus(ALL_RESTORED_MSG); break;
    default             : setStatus(SHED_LEVEL_MSG + ' ' + shedLevel); break;
    }
  }

  private BStatusBoolean getAutoValue()
  {
    return new BStatusBoolean(false, BStatus.make(BStatus.NULL));
  }

  private BStatusBoolean getShedValue()
  {
    return new BStatusBoolean(false);
  }

////////////////////////////////////////////////////////////////
// local variables
////////////////////////////////////////////////////////////////
int baseLevel			;	// working copy of base shed level 
int mode				  ; // mode flag used as message index 
int previousLevel	; // shed level last pass to detect change in level  
int shedLevel			;	// level used for actual control

protected static final int SHED_LEVEL      =  1;
protected static final int SHEDDING_LEVEL  =  2;
protected static final int RESTORING_LEVEL =  3;
protected static final int DISABLED        =  4;
protected static final int ALL_SHED        =  5;
protected static final int ALL_RESTORED    =  6;


// 
// Messages 
//

protected static String SHED_LEVEL_MSG      = Lexicon.make("kitControl").getText("shedControl.shedLevel");
protected static String SHEDDING_LEVEL_MSG  = Lexicon.make("kitControl").getText("shedControl.sheddingLevel");
protected static String RESTORING_LEVEL_MSG = Lexicon.make("kitControl").getText("shedControl.restoringLevel");
protected static String DISABLED_MSG        = Lexicon.make("kitControl").getText("shedControl.disabled");
protected static String ALL_SHED_MSG        = Lexicon.make("kitControl").getText("shedControl.allShed");
protected static String ALL_RESTORED_MSG    = Lexicon.make("kitControl").getText("shedControl.allRestored");


}
