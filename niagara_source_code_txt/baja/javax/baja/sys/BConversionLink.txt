/*
 * Copyright 2010 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.sys;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BConverter;
import javax.baja.util.BNullConverter;

/**
 * BConversionLink defines a special type of BLink where 
 * the propagated value must undergo conversion as it
 * is set on the target.  Conversion is done using a 
 * subclass of BConverter, set as the value of the 
 * ConversionLink's converter property.
 * 
 * For a BConverter to be selected automatically by
 * workbench for use on a BConversionLink it must
 * be registered as an agent on BConversionLink.
 * 
 * @author    Lee Adcock
 * @creation  28 June 10
 * @version   $Revision: 3$ $Date: 4/14/11 9:38:11 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "converter",
  type = "BConverter",
  defaultValue = "new BNullConverter()"
)
public class BConversionLink 
  extends BLink
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BConversionLink(908842868)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "converter"

  /**
   * Slot for the {@code converter} property.
   * @see #getConverter
   * @see #setConverter
   */
  public static final Property converter = newProperty(0, new BNullConverter(), null);

  /**
   * Get the {@code converter} property.
   * @see #converter
   */
  public BConverter getConverter() { return (BConverter)get(converter); }

  /**
   * Set the {@code converter} property.
   * @see #converter
   */
  public void setConverter(BConverter v) { set(converter, v, null); }

  //endregion Property "converter"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BConversionLink.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////  
  
  public BConversionLink()
  {
  }
  
  public BConversionLink(BOrd sourceOrd, String sourceSlot, String targetSlot, boolean enabled, BConverter converter)
  {
    super(sourceOrd, sourceSlot, targetSlot, enabled);
    setConverter(converter);
  }

  public BConversionLink(BComponent source, Slot sourceSlot, Slot targetSlot, BConverter converter)
  {
    super(source, sourceSlot, targetSlot);
    setConverter(converter);
  }
  
////////////////////////////////////////////////////////////////
// BLink
////////////////////////////////////////////////////////////////

  @Override
  public void propagate(BValue arg)
  {                     
    // short circuit if sourceSlot and targetSlot
    // are same slot on same parent
    if (getTargetComponent() == getSourceComponent() &&
        getTargetSlot() == getSourceSlot())
    {
      System.out.println("WARNING: Infinite link: " + this);
      return;
    }
  
    Slot sourceSlot = getSourceSlot();
    Slot targetSlot = getTargetSlot();
    
    if (sourceSlot.isAction())
    {
      if(targetSlot.asAction().getParameterType()!=null)
      {
        arg = (BValue) getConverter().convert(arg, targetSlot.asAction().getParameterDefault());    
      }
      if (targetSlot.isAction()) { propagateActionToAction(arg); return; }
      if (targetSlot.isTopic()) { propagateActionToTopic(arg); return; }
    }
    else if (sourceSlot.isTopic())
    {
      if (targetSlot.isAction()) { propagateTopicToAction(arg); return; }
      if (targetSlot.isTopic()) { propagateTopicToTopic(arg); return; }
    }
    else if (sourceSlot.isProperty()) // must be last
    {
      if (targetSlot.isAction()) { propagatePropertyToAction(); return; }
      if (targetSlot.isProperty()) { propagatePropertyToProperty(); return; }
    }
    throw new IllegalStateException("Invalid link config");
  }  
  
  @Override
  public void propagatePropertyToAction()
  {
    Slot targetSlot = getTargetSlot();
    Action targetAction = targetSlot.asAction();
    
    // get the argument as the new property
    // value only if needed for the action
    BValue arg = null;
    if (targetAction.getParameterType() != null)
    {
      arg = (BValue) getConverter().convert(
          getSourceComponent().get(getSourceSlot().asProperty()),
          targetSlot.asAction().getParameterDefault());         
    }
      
    // invoke the action on the target
    getTargetComponent().invoke(targetAction, arg);
  }  
  
  @Override
  public void propagatePropertyToProperty()
  {

    BComponent t = getTargetComponent();
    BComponent s = getSourceComponent();
    Property sProp = getSourceSlot().asProperty();
    Property tProp = getTargetSlot().asProperty();

    BValue value = (BValue) getConverter().convert(s.get(sProp), t.get(tProp));

    switch (tProp.getTypeAccess())
    {
      case Slot.BOOLEAN_TYPE:
        t.setBoolean(tProp, ((BBoolean) value).getBoolean(), null);
        break;
      case Slot.INT_TYPE:
        t.setInt(tProp, ((BInteger) value).getInt(), null);
        break;
      case Slot.LONG_TYPE:
        t.setLong(tProp, ((BLong) value).getLong(), null);
        break;
      case Slot.FLOAT_TYPE:
        t.setFloat(tProp, ((BFloat) value).getFloat(), null);
        break;
      case Slot.DOUBLE_TYPE:
        t.setDouble(tProp, ((BDouble) value).getDouble(), null);
        break;
      case Slot.STRING_TYPE:
        t.setString(tProp, ((BString) value).getString(), null);
        break;
      case Slot.BOBJECT_TYPE:
        if (value.isSimple())
          t.set(tProp, value, null);
        else
          t.get(tProp).asComplex().copyFrom(value.asComplex());
        break;
      default:
        throw new IllegalStateException("Unexpected link source type: "
            + sProp.getType() + " (" + sProp.getTypeAccess() + ")");
    }
  }
}
