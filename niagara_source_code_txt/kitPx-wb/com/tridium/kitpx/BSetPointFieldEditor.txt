/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatusValue;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIBoolean;
import javax.baja.sys.BIEnum;
import javax.baja.sys.BINumeric;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;

/**
 * BSetPointFieldEditor is a specialized field editor that uses
 * the SetPointBinding to display the current value of a point,
 * but provide change functionality via a property change or
 * a set() action.
 *
 * @author    Brian Frank       
 * @creation  11 May 05
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType
public class BSetPointFieldEditor
  extends BGenericFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BSetPointFieldEditor(2979906276)1.0$ @*/
/* Generated Fri Nov 19 14:22:33 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSetPointFieldEditor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



    
////////////////////////////////////////////////////////////////
// SetPoint
////////////////////////////////////////////////////////////////

  BSetPointBinding getSetPointBinding()
  {
    BBinding[] bindings = getBindings();
    for(int i=0; i<bindings.length; ++i)
      if (bindings[i] instanceof BSetPointBinding)
        return (BSetPointBinding)bindings[i];
    return null;
  }

  void saveSetPoint(BSetPointBinding binding, Context cx)
    throws Exception
  { 
    if (!binding.isBound()) return;
    if (!isModified()) return;
    
    // save editor
    BValue value = (BValue)saveValue();
   
    // pass value to binding to apply it
    binding.saveSetPoint(value,cx);
  }

  void loadSetPoint(BSetPointBinding binding)
  {
    // make sure we start with a fresh editor (sometimes
    // constructors accidently set the modified flag)    
    clearModified();

    // don't reload if currently modified
    if (isModified()) return;
    
    // map binding to a value/facets
    OrdTarget target = binding.getTarget();            
    BValue value = (BValue)target.get();
    BFacets facets = null;     
    if (value instanceof BIBoolean)
    {
      BIBoolean x = (BIBoolean)target.get();
      value = BBoolean.make(x.getBoolean());
      facets = x.getBooleanFacets();
    }            
    else if (value instanceof BINumeric)
    {
      BINumeric x = (BINumeric)value;
      value = BDouble.make(x.getNumeric());
      facets = x.getNumericFacets();
    }            
    else if (value instanceof BIEnum)
    {
      BIEnum x = (BIEnum)value;
      value = x.getEnum();
      facets = x.getEnumFacets();
    }          
    else if (value instanceof BIStatusValue) // assume string point 
    {
      BIStatusValue x = (BIStatusValue)value;
      value = x.getStatusValue().getValueValue();
      facets = x.getStatusValueFacets();
    }
    else if(value instanceof BComponent)
    {
      BComponent c = value.asComponent();
      //This is required for NiagaraVirtuals that happen to be representing kitControl:NumericConst
      c.loadSlots();
      BValue out = c.get("out");
      if (out instanceof BIStatusValue)
      {
        BIStatusValue x = (BIStatusValue)out;
        value = x.getStatusValue().getValueValue();
        facets = x.getStatusValueFacets();
      }
    }
    
    // fallback to OrdTarget facets     
    if (facets == null || facets.isNull())
      facets = target.getFacets();

    loadValue(value.newCopy(), facets);
  }                          
  
}
