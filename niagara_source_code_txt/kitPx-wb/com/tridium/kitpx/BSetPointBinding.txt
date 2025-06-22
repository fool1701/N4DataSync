/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import java.util.Optional;
import java.util.logging.Level;

import javax.baja.control.BStringPoint;
import javax.baja.hx.px.BHxPxView;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusString;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFloat;
import javax.baja.sys.BIBoolean;
import javax.baja.sys.BIEnum;
import javax.baja.sys.BINumeric;
import javax.baja.sys.BInteger;
import javax.baja.sys.BLong;
import javax.baja.sys.BNumber;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.ui.BDialog;
import javax.baja.ui.BValueBinding;
import javax.baja.ui.BWidget;
import javax.baja.workbench.CannotSaveException;

import com.tridium.hx.px.BHxWidgetShell;
import com.tridium.hx.px.HxShellManager;

/**
 * BSetPointBinding is used to display the current value of a "setpoint" 
 * and also to provide the ability to modify it.  A setpoint is typically
 * a StatusValue property such as fallback.  The SetPointBinding ord must 
 * resolve down to the specific property being manupilated.  If bound to
 * a component or to a readonly property, then the binding attempts to use
 * a "set" action to save. 
 *
 * @author    Brian Frank       
 * @creation  3 Nov 04
 * @version   $Revision$ $Date: 19-May-04 11:11:24 AM$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "bajaui:Widget"
  )
)
/*
 Slot name of widget action or topic to trigger the apply
 the set point
 */
@NiagaraProperty(
  name = "widgetEvent",
  type = "String",
  defaultValue = ""
)
/*
 This is the widget property used to track the
 setpoint being driven.
 */
@NiagaraProperty(
  name = "widgetProperty",
  type = "String",
  defaultValue = ""
)
public class BSetPointBinding
  extends BValueBinding
{                          
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BSetPointBinding(2458460630)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "widgetEvent"

  /**
   * Slot for the {@code widgetEvent} property.
   * Slot name of widget action or topic to trigger the apply
   * the set point
   * @see #getWidgetEvent
   * @see #setWidgetEvent
   */
  public static final Property widgetEvent = newProperty(0, "", null);

  /**
   * Get the {@code widgetEvent} property.
   * Slot name of widget action or topic to trigger the apply
   * the set point
   * @see #widgetEvent
   */
  public String getWidgetEvent() { return getString(widgetEvent); }

  /**
   * Set the {@code widgetEvent} property.
   * Slot name of widget action or topic to trigger the apply
   * the set point
   * @see #widgetEvent
   */
  public void setWidgetEvent(String v) { setString(widgetEvent, v, null); }

  //endregion Property "widgetEvent"

  //region Property "widgetProperty"

  /**
   * Slot for the {@code widgetProperty} property.
   * This is the widget property used to track the
   * setpoint being driven.
   * @see #getWidgetProperty
   * @see #setWidgetProperty
   */
  public static final Property widgetProperty = newProperty(0, "", null);

  /**
   * Get the {@code widgetProperty} property.
   * This is the widget property used to track the
   * setpoint being driven.
   * @see #widgetProperty
   */
  public String getWidgetProperty() { return getString(widgetProperty); }

  /**
   * Set the {@code widgetProperty} property.
   * This is the widget property used to track the
   * setpoint being driven.
   * @see #widgetProperty
   */
  public void setWidgetProperty(String v) { setString(widgetProperty, v, null); }

  //endregion Property "widgetProperty"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSetPointBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BBinding
////////////////////////////////////////////////////////////////

  public boolean invokedOnWidget(Action action, BValue value, Context context) 
  {
    if (action.getName().equals(getWidgetEvent()))
    {
      try
      {
        saveSetPoint(context);
        return true;
      }
      catch (CannotSaveException e)
      {
        BDialog.error(getWidget(),BDialog.TITLE_ERROR, e.getLocalizedMessage(), e);
      }
    }     
    return false;
  }

  public boolean firedOnWidget(Topic topic, BValue event, Context context)
  {                    
    if (topic.getName().equals(getWidgetEvent()))
    {
      try
      {
        saveSetPoint(context);
        return true;
      }
      catch (CannotSaveException e)
      {
        BDialog.error(getWidget(),BDialog.TITLE_ERROR, e.getLocalizedMessage(), e);
      }
    }     
    return false;
  }

  public boolean isDegraded()
  {                              
    return !isBound() || !getTarget().canWrite();
  }  

  public void targetChanged()
  {                   
    super.targetChanged();
    loadSetPoint();
  }          
  
  public void save(Context cx)
    throws Exception
  {
    if (!isBound()) return;   
    
    BWidget widget = getWidget();
    
    // handle SetPointFieldEditor special
    if (widget instanceof BSetPointFieldEditor)
    { 
      ((BSetPointFieldEditor)widget).saveSetPoint(this,cx);
      return;
    }
  }

////////////////////////////////////////////////////////////////
// Load/Save
////////////////////////////////////////////////////////////////
  
  /**
   * Target -> Widget.widgetProperty
   */
  public void loadSetPoint()
  { 
    BWidget widget = getWidget();
    
    // handle SetPointFieldEditor special
    if (widget instanceof BSetPointFieldEditor)
    { 
      ((BSetPointFieldEditor)widget).loadSetPoint(this);
      return;
    }
    
    // get current widget value
    Property prop = widget.getProperty(getWidgetProperty());
    if (prop == null) return;
    BValue w = widget.get(prop);
    
    // get current target value
    if (!isBound()) return;
    BValue t = (BValue)get();
    
    // convert target -> widget
    BValue s = convert(t, w);
    
    // if we found a solution, update widget prop
    if (s != null) widget.set(prop, s);
  }

  /**
   * Widget.widgetProperty -> Target
   * @deprecated
   */
  @Deprecated
  public void saveSetPoint()
    throws CannotSaveException
  {                  
    // get current widget value
    BValue w = saveWidgetProperty();
    if (w == null) return;
    saveSetPoint(w);
  }
  

  /**
   * Widget.widgetProperty -> Target
   * @throws CannotSaveException 
   */
  public void saveSetPoint(Context ctx) 
  throws CannotSaveException
  {
    // get current widget value
    BValue w = saveWidgetProperty();
    if (w == null) return;
    saveSetPoint(w,ctx);
  }
  
  /**
   * w (Widget.widgetProperty) -> Target
   * @deprecated
   */
  @Deprecated
  public void saveSetPoint(BValue w)
    throws CannotSaveException
  {                  
    // get current target value
    if (!isBound()) return;    
    BValue t = (BValue)get();

    // if we are bound to a property under a component, 
    // then convert widget -> target and save property
    if (!t.isComponent())
    {
      BValue s = convert(w, t);     
      if (s == null) return;
    
      // try to save value
      try
      {
        if (saveProperty(s, null)) return;
      }
      catch (CannotSaveException e) { return; }
    } 
    
    // otherwise try to save action
    if (saveAction(w,null)) return;
    System.out.println("WARNING: No mechanism found to save " + getOrd());
  }
  
  /**
   * w (Widget.widgetProperty) -> Target
   */
  public void saveSetPoint(BValue w, Context ctx)
    throws CannotSaveException
  {
    // get current target value
    if (!isBound()) return;    
    BValue t = (BValue)get();

    // if we are bound to a property under a component, 
    // then convert widget -> target and save property
    if (!t.isComponent())
    {
      BValue s = convert(w, t);     
      if (s == null) return;
    
      // try to save value
        if (saveProperty(s, ctx)) return;
    } 
    
    // otherwise try to save action
    if (saveAction(w,ctx)) return;

    //In Hx, save might be used for another field editor but this binding gets in the way of other widgets
    if(getWidget() != null && getWidget().getShellManager() instanceof HxShellManager)
    {
      BHxPxView.log.fine("No mechanism found to save BSetPointBinding" + getOrd());
    }
    else
    {
      throw new CannotSaveException("No mechanism found to save " + getOrd());
    }
  }
  
  /**
   * Save the widget property driving the target.
   */
  BValue saveWidgetProperty()
  {
    BWidget widget = getWidget();
    Property prop = widget.getProperty(getWidgetProperty());
    if (prop == null) return null;
    return widget.get(prop);
  }
  
  /**
   * Try save to a specified non-readonly property.
   */
  private boolean saveProperty(BValue v,Context ctx)
    throws CannotSaveException
  {
    // get the property path
    BComponent c = getTarget().getComponent();
    Property[] path = getTarget().getPropertyPathInComponent();
    if (path == null || path.length == 0) 
      return false;
    
    // make sure property is not readonly
    if (Flags.isReadonly(c, path[0])) return false;
    
    // find appriopate prop on target
    BComplex parent = c;
    for(int i=0; i<path.length-1; ++i)
      parent = (BComplex)parent.get(path[i]);
    
    // check min/max
    verifyBounds(v, c.getSlotFacets(path[0]));
        
    // set prop
    parent.set(path[path.length-1], v, ctx);
    return true;
  }                   
    
  /**
   * Try to find an action to use.
   */
  private boolean saveAction(BValue v,Context ctx)
    throws CannotSaveException
  {
    BComponent c = getTarget().getComponent();

    try
    {
      BValue existingValue = convert(c, v);
      if (existingValue != null && existingValue.equivalent(v))
      {
        return true;
      }
    }
    catch (Exception e)
    {
      LOGGER.log(Level.WARNING, "error in SetPointBinding checking for existing value", e);
    }

    // Enforce facets min/max
    Object[] facetsArray = c.getChildren(BFacets.class);
    if ( facetsArray.length > 0)
    {
      BFacets facets  = (BFacets)facetsArray[0];
      verifyBounds(v, facets);
    }
    
    Action action = c.getAction("set");
    if (action == null) return false;
    if (action.getParameterType() != v.getType()) return false;
    
    try
    {
      c.invoke(action, v, ctx);
    }catch (Exception e)
    {
      return false;
    }
    
    return true;
  }
  
  /**
   * Verify that value is within min/max if specified.
   */
  private void verifyBounds(BValue v, BFacets facets)
    throws CannotSaveException
  {
    if (facets == null) return;
    
    BNumber min = (BNumber)facets.get(BFacets.MIN);
    BNumber max = (BNumber)facets.get(BFacets.MAX);
    
    // Convert to the value for comparison
    if (v instanceof BStatusNumeric)
      v = ((BStatusNumeric)v).getValueValue();
    
    if (v instanceof BDouble)
    {
      double d = ((BDouble)v).getDouble();
      if (min != null && d < min.getDouble())
        throw new CannotSaveException(d + " < " + min + " [" + min + "," + max + "]");
      if (max != null && d > max.getDouble())
        throw new CannotSaveException(d + " > " + max + " [" + min + "," + max + "]");
    }
    else if (v instanceof BInteger)
    {
      int i = ((BInteger)v).getInt();
      if (min != null && i < min.getInt())
        throw new CannotSaveException(i + " < " + min + " [" + min + "," + max + "]");
      if (max != null && i > max.getInt())
        throw new CannotSaveException(i + " > " + max + " [" + min + "," + max + "]");
    }
  }
              
////////////////////////////////////////////////////////////////
// Convert
////////////////////////////////////////////////////////////////

  /**
   * Convert from from -> to
   */
  public BValue convert(BValue from, BValue to)
  {                                          
    // if same type easy as pie
    if (from.getType() == to.getType()) return from;
    
    // ------- Numeric -------
    
    // INumeric -> Number
    if (from instanceof BINumeric && to instanceof BNumber)
    {
      double x = ((BINumeric)from).getNumeric();
      switch(to.getType().getDataTypeSymbol())
      {                                       
        case 'i': return BInteger.make((int)x);
        case 'l': return BLong.make((long)x);
        case 'f': return BFloat.make((float)x);
        case 'd': return BDouble.make((float)x);
      }
    }

    // INumeric -> StatusNumeric
    if (from instanceof BINumeric && to instanceof BStatusNumeric)
    {
      double x = ((BINumeric)from).getNumeric();
      return new BStatusNumeric(x);
    }

    // ------- Boolean -------
    
    // IBoolean -> Boolean
    if (from instanceof BIBoolean && to instanceof BBoolean)
    {
      return BBoolean.make( ((BIBoolean)from).getBoolean() );
    }

    // IBoolean -> StatusBoolean
    if (from instanceof BIBoolean && to instanceof BStatusBoolean)
    {
      return new BStatusBoolean( ((BIBoolean)from).getBoolean() );
    }                 

    // ------- Enum -------
    
    // IEnum -> DynamicEnum
    if (from instanceof BIEnum && to instanceof BDynamicEnum)
    {
      return ((BIEnum)from).getEnum();
    }

    // IBoolean -> StatusBoolean
    if (from instanceof BIEnum && to instanceof BStatusEnum)
    {
      return new BStatusEnum( ((BIEnum)from).getEnum() );
    }                 

    // ------- String -------

    // String -> StatusString
    BValue stringValue = getFromStringlike(from)
      .map(bString -> toStringLike(bString, to))
      .orElse(null);

    if (stringValue != null)
    {
      return stringValue;
    }

    // no solution
    System.out.println("WARNING: SetPointBinding could not convert " + from.getType() + " -> " + to.getType());
    return null;
  }

  private static BValue toStringLike(BString stringValue, BValue target)
  {
    if (target instanceof BString)
    {
      return stringValue;
    }
    if (target instanceof BStatusString)
    {
      return new BStatusString(stringValue.getString());
    }
    return null;
  }

  private static Optional<BString> getFromStringlike(BValue from)
  {
    if (from instanceof BString)
    {
      return Optional.of((BString) from);
    }
    if (from instanceof BStatusString)
    {
      return Optional.of((BString) ((BStatusString) from).getValueValue());
    }
    if (from instanceof BStringPoint)
    {
      return Optional.of((BString) ((BStringPoint) from).getOut().getValueValue());
    }
    return Optional.empty();
  }
}
