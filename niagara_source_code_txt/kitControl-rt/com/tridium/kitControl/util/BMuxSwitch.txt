/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import javax.baja.control.*;
import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BMuxSwitch is superclass for numeric, boolean, enum & string muxes.
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
 * @version   $Revision: 19$ $Date: 3/30/2004 3:43:05 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.makeNumeric()",
  override = true
)
public abstract class BMuxSwitch
  extends BSwitch
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BMuxSwitch(3343635388)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeNumeric(), null);

  //endregion Property "facets"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMuxSwitch.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Interfaces
////////////////////////////////////////////////////////////////


  public abstract void setOutput(BStatusValue value);
  public abstract BStatusValue getInStatusValue(int select);

  public void doCalculate(int select)
  {
    BStatusValue workingValue = (BStatusValue)getInStatusValue(select).newCopy();
    workingValue.setStatus(propagate(workingValue.getStatus()));
    setOutput( workingValue );
  }

  public void initNumberValues()
  {
    numValues = getNumberValues();
    int setFlags;
    for(int i = 0; i < 10; i++)
    {
      boolean active = i < numValues;
      switch(i)
      {
      case 0: initSlotFlags(i, getSlot("inA"), active); break;
      case 1: initSlotFlags(i, getSlot("inB"), active); break;
      case 2: initSlotFlags(i, getSlot("inC"), active); break;
      case 3: initSlotFlags(i, getSlot("inD"), active); break;
      case 4: initSlotFlags(i, getSlot("inE"), active); break;
      case 5: initSlotFlags(i, getSlot("inF"), active); break;
      case 6: initSlotFlags(i, getSlot("inG"), active); break;
      case 7: initSlotFlags(i, getSlot("inH"), active); break;
      case 8: initSlotFlags(i, getSlot("inI"), active); break;
      case 9: initSlotFlags(i, getSlot("inJ"), active); break;
      }
    }
  }

  private void initSlotFlags(int input, Slot slot, boolean active)
  {
    int curFlags = getFlags(slot);
    if(active)
    {
      try{setFlags(slot, curFlags & ~(Flags.HIDDEN | Flags.TRANSIENT) | Flags.SUMMARY); } catch(Exception e) {}
    }
    else
    {
      try{setFlags(slot, curFlags | Flags.HIDDEN | Flags.TRANSIENT) ; } catch(Exception e) {}
    }
      
        
  }
  
  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/util/switch.png");


}
