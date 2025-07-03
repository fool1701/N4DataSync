/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.ui.BAbstractButton;
import javax.baja.ui.BBinding;
import javax.baja.ui.BWidget;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.util.BTypeSpec;

/**
 * BMomentaryToggleBinding maps a button to a boolean property value, boolean point, 
 * or an action with a boolean parameter.  The value of the target is modified
 * to reflect the isPressed button state.
 *  
 * @author    Lee Adcock     
 * @creation  Dec 12
 * @version   $Revision$ $Date: 19-May-04 11:11:24 AM$
 * @since     Niagara 3.7
 */
@NiagaraType(
  agent = @AgentOn(
    types = "bajaui:AbstractButton"
  )
)
public class BMomentaryToggleBinding
  extends BBinding
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BMomentaryToggleBinding(3279431889)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMomentaryToggleBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  

////////////////////////////////////////////////////////////////
// BBinding
////////////////////////////////////////////////////////////////
  
  public boolean firedOnWidget(Topic topic, BValue value, Context context) 
  {
    if (topic.equals(BWidget.mouseEvent))
      if(((BMouseEvent)value).getId()==BMouseEvent.MOUSE_PRESSED || ((BMouseEvent)value).getId()==BMouseEvent.MOUSE_RELEASED)
        setValueOnTarget(((BAbstractButton)getWidget()).isPressed());
    return false;
  }

  public boolean isDegraded()
  {                
    if(super.isDegraded()) return true;
    
    OrdTarget target = getTarget();
    if(target.getSlotInComponent()!=null)
    {
      if(target.getSlotInComponent().isProperty())
      {
        Type propertyType = target.getSlotInComponent().asProperty().getType();
        return !(propertyType.is(BBoolean.TYPE) || propertyType.is(BStatusBoolean.TYPE));
      }


      if(target.getSlotInComponent().isAction())
      {
        Type parameterType = target.getSlotInComponent().asAction().getParameterType();
        return parameterType==null || !(parameterType.is(BBoolean.TYPE) || parameterType.is(BStatusBoolean.TYPE));
      }
      
      return true;

    } else {
      // we do this to avoid a control module dependency
      return !target.getComponent().getType().getTypeSpec().equals(BTypeSpec.make("control:BooleanWritable"));
    }
  }
  
  public void targetChanged()
  {
    if (!Sys.isStation())
    {
      setValueOnTarget(((BAbstractButton)getWidget()).isPressed());
    }
    super.targetChanged();
  }  
  
  /**
   * Set the desired boolean value on the binding's target.
   */
  public synchronized void setValueOnTarget(boolean value)
  {
    if (!value && !targetPressed) return;
    targetPressed = value;

    BOrd ord = getOrd();
    if (ord.isNull()) return;
    if (!isBound()) return;

    long currentMillis = Clock.ticks();
    long diff = currentMillis-lastMillis;
    if(lastMillis > 0 && diff < 100 && diff > -1)
    {
      try
      {
        //browsers may send the mouseup/mousedown at the same time, so this ensures a quick
        //press will change the value long enough to register a change
        Thread.sleep(100-diff);
      }
      catch(InterruptedException ignore) {}
    }

    lastMillis = Clock.ticks();

    
    // get bound component  
    OrdTarget target = getTarget();
    
    if(target.getSlotInComponent()!=null)
    {
      // this is an ord to a slot
      if(target.getSlotInComponent().isProperty())
      {
      
        Type propertyType = target.getSlotInComponent().asProperty().getType();
        if(propertyType.is(BBoolean.TYPE))
        {
          target.getComponent().set(target.getSlotInComponent().asProperty(), BBoolean.make(value));
        } else if (propertyType.is(BStatusBoolean.TYPE)) {
          BStatusBoolean statusBoolean = (BStatusBoolean)target.getComponent().get(target.getSlotInComponent().asProperty());
          statusBoolean.setValue(value);
          statusBoolean.setStatus(BStatus.makeNull(statusBoolean.getStatus(), false));
        } else {
          // configuration error, property must be BBoolean or BStatusBoolean
        }
      } else if(target.getSlotInComponent().isAction()) {
        
        Type actionType = target.getSlotInComponent().asAction().getParameterType();
        if(actionType!=null && actionType.is(BBoolean.TYPE))
        {
          target.getComponent().invoke(target.getSlotInComponent().asAction(), BBoolean.make(value));
        } else {
          // configuration error, action must take boolean parameter
        }
        
      } else {
        // configuration error, slot must be property or action
      }
    } else {
      // we do this to avoid a control module dependency
      if(target.getComponent().getType().getTypeSpec().equals(BTypeSpec.make("control:BooleanWritable")))
      {
        target.getComponent().invoke(target.getComponent().getAction("set"), BBoolean.make(value));
      } else {
        // configuration error, component must be BooleanWritable
      }
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private boolean targetPressed = false;
  private long lastMillis=0;
}
