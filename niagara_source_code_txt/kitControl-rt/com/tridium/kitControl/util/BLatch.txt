/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import java.io.*;

import javax.baja.control.*;
import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BLatch is the base class for latches. 
 * with only an output element.
 *
 * @author    Andy Saunders
  * @creation  16 April 04
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 These facets are applied against all out properties.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT"
)
/*
 This intput is used to latch the input property into the output property
 on the rising edge.
 */
@NiagaraProperty(
  name = "clock",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraAction(
  name = "latch"
)
public abstract class BLatch
  extends BComponent
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BLatch(2875184461)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against all out properties.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.DEFAULT, null);

  /**
   * Get the {@code facets} property.
   * These facets are applied against all out properties.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * These facets are applied against all out properties.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "clock"

  /**
   * Slot for the {@code clock} property.
   * This intput is used to latch the input property into the output property
   * on the rising edge.
   * @see #getClock
   * @see #setClock
   */
  public static final Property clock = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code clock} property.
   * This intput is used to latch the input property into the output property
   * on the rising edge.
   * @see #clock
   */
  public BStatusBoolean getClock() { return (BStatusBoolean)get(clock); }

  /**
   * Set the {@code clock} property.
   * This intput is used to latch the input property into the output property
   * on the rising edge.
   * @see #clock
   */
  public void setClock(BStatusBoolean v) { set(clock, v, null); }

  //endregion Property "clock"

  //region Action "latch"

  /**
   * Slot for the {@code latch} action.
   * @see #latch()
   */
  public static final Action latch = newAction(0, null);

  /**
   * Invoke the {@code latch} action.
   * @see #latch
   */
  public void latch() { invoke(latch, null, null); }

  //endregion Action "latch"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLatch.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// ControlPoint
////////////////////////////////////////////////////////////////  

  public void started()
  {
  }

  public void changed(Property property, Context context) 
  {
    if(isRunning())
    {
      if(property == clock)
      {
        currentClock = getClock().getValue();
        if(getClock().getStatus().isValid())
        {
          if(currentClock && !lastClock)
          {
            setOutStatusValue(getInStatusValue());
          }
          lastClock = currentClock;
        }
      }
    }
  }


  public void doLatch()
  {
    setOutStatusValue(getInStatusValue());
  }

  public abstract void setOutStatusValue(BStatusValue value);
  public abstract BStatusValue getInStatusValue();


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
  private static final BIcon icon = BIcon.std("control/numericPoint.png");

  private boolean currentClock;
  private boolean lastClock;
}
