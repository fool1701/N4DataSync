/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.conversion;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.units.*;

import com.tridium.kitControl.enums.BNullValueOverrideSelect;

/**
 * BStatusNumericToDouble is a component that converts a float to a statusNumeric.
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BStatusValueToValue
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.conversion.BStatusValueToValue(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusValueToValue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BStatusValueToValue()
  {
  }
  
/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if(p.getName().equals(ON_NULL_IN_VALUE_NAME))
    {
      if(getOnNullInValue().getOrdinal() == BNullValueOverrideSelect.SPECIFY_OUT_VALUE)
      {
        BValue nVal = get(NULL_VALUE_PROPERTY_NAME);
        if(nVal == null)
        {
          add(NULL_VALUE_PROPERTY_NAME, (BValue)getOutType().getInstance());
        }
      }
      else
      {
        try{remove(NULL_VALUE_PROPERTY_NAME);}
        catch(Exception e){}
      }
      execute();
    }
    else if(p.getName().equals(NULL_VALUE_PROPERTY_NAME))
      execute();
      
  }

  public abstract Type getOutType();
  
  public abstract BFacets getOutFacets();
//  {
//    throw(new RuntimeException("BStatusValueToValue.getOutFacets(): SubClass should override"));
//  }
  
  public abstract void execute();
//  {
//    throw(new RuntimeException("BStatusValueToValue.getOutType(): SubClass should override"));
//  }

  public abstract BNullValueOverrideSelect getOnNullInValue(); 
//  { 
//    throw(new RuntimeException("BStatusValueToValue.getOnNullInValue(): SubClass should override"));
//  }

  public void setOnNullInValue(BNullValueOverrideSelect value)
  {
    throw(new RuntimeException("BStatusValueToValue.setOnNullInValue(): SubClass should override"));
  }

  public BValue calculate(BStatusValue sValue)
  {
    if( sValue.getStatus().isNull() && 
        getOnNullInValue().getOrdinal() == BNullValueOverrideSelect.SPECIFY_OUT_VALUE )
    {
      return get(NULL_VALUE_PROPERTY_NAME);
    }
    else
    {
      return sValue.getValueValue() ;
    }
  }

  /**
   * Apply the "facets" property to the "out" property.
   */
  public BFacets getSlotFacets(Slot slot)
  {
    if(slot.getName().equals(NULL_VALUE_PROPERTY_NAME))
      return getOutFacets();
    return super.getSlotFacets(slot);
  }

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/control.png");
  public static String NULL_VALUE_PROPERTY_NAME = "outValueOnNull";
  public static final String ON_NULL_IN_VALUE_NAME = "onNullInValue";

  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  
}
