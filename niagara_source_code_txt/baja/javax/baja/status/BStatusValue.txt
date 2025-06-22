/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.status;

import javax.baja.data.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BStatusValue is the base class for all status-value 
 * pairs in the Baja control architecture.  The two most 
 * common types are BStatusBoolean and BStatusNumeric which 
 * model a binary and analog value respectively.
 *
 * @author    Dan Giorgis
 * @creation  15 Nov 00
 * @version   $Revision: 47$ $Date: 7/12/10 5:16:57 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The status property models the state of the
 StatusValue using a consistent set of status flags
 encapsulated by the BStatus class.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.READONLY
)
public abstract class BStatusValue
  extends BStruct
  implements BIStatusValue
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.status.BStatusValue(3757407138)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * The status property models the state of the
   * StatusValue using a consistent set of status flags
   * encapsulated by the BStatus class.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.READONLY, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * The status property models the state of the
   * StatusValue using a consistent set of status flags
   * encapsulated by the BStatus class.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * The status property models the state of the
   * StatusValue using a consistent set of status flags
   * encapsulated by the BStatus class.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusValue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////
  
  /**
   * Convenience for <code>make(obj, null)</code>.
   */                                             
  public static BIStatusValue make(BObject obj)
  {
    return make(obj, null);                                           
  }

  /**
   * Attempt to map any BObject into a BIStatusValue.  If
   * the specified object is an instance of BIStatusValue
   * then just return it.  Otherwise attempt to coerce it
   * using BINumeric, BIBoolean, BIEnum, and BIStatus.
   */
  public static BIStatusValue make(BObject obj, Context cx)
  {
    if (obj instanceof BIStatusValue) 
      return (BIStatusValue)obj;
      
    BStatus status = BStatus.DEFAULT;
    if (obj instanceof BIStatus)
      status = ((BIStatus)obj).getStatus();
    
    if (obj instanceof BIBoolean) 
      return new BStatusBoolean(((BIBoolean)obj).getBoolean(), status);
      
    if (obj instanceof BINumeric) 
      return new BStatusNumeric(((BINumeric)obj).getNumeric(), status);
      
    if (obj instanceof BIEnum) 
      return new BStatusEnum(((BIEnum)obj).getEnum(), status);
      
    return new BStatusString(obj.toString(cx), status);
  }
 
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////


  /**
   * Constructor with status.
   */
  public BStatusValue(BStatus status) 
  { 
    setStatus(status); 
  }

  /**
   * No argument constructor.
   */
  public BStatusValue() 
  { 
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////  

  /**
   * Get the value as a BValue.
   */
  public abstract BValue getValueValue();
  
  /**
   * Get the "value" property.
   */
  public abstract Property getValueProperty();

  /**
   * Set the value as a BValue.
   */
  public void setValueValue(BValue v)
  {
    //bajaLite need a generic setter, that is why this is here.
    set(getValueProperty(),v);
  }
  
////////////////////////////////////////////////////////////////
// Status Bit Setters
////////////////////////////////////////////////////////////////
  
  /**
   * Set or clear the alarm bit 
   */  
  public void setStatusInAlarm(boolean x)      
  {
    setStatus(BStatus.make(getStatus(), BStatus.ALARM,x)); 
  }

  /**
   * Set or clear the fault bit 
   */  
  public void setStatusFault(boolean x)        
  { 
    setStatus(BStatus.make(getStatus(), BStatus.FAULT, x)); 
  }
  
  /**
   * Set or clear the overridden bit 
   */  
  public void setStatusOverridden(boolean x)   
  { 
    setStatus(BStatus.make(getStatus(), BStatus.OVERRIDDEN, x)); 
  }
  
  /**
   * Set or clear the disabled bit
   */   
  public void setStatusDisabled(boolean x) 
  {
    setStatus(BStatus.make(getStatus(), BStatus.DISABLED, x)); 
  }

  /**
   * Set or clear the null bit 
   */
  public void setStatusNull(boolean x) 
  { 
    setStatus(BStatus.make(getStatus(), BStatus.NULL, x)); 
  }    

  /**
   * Set or clear the unacked_alarm bit 
   */
  public void setStatusUnackedAlarm(boolean x) 
  { 
    setStatus(BStatus.make(getStatus(), BStatus.UNACKED_ALARM, x)); 
  }    

  /**
   * Set or clear the down bit 
   */
  public void setStatusDown(boolean x) 
  { 
    setStatus(BStatus.make(getStatus(), BStatus.DOWN, x)); 
  }    
  
  /**
   * Set the status bits.
   */
  public void setStatus(int bits)
  {
    setStatus(BStatus.make(getStatus(), bits));
  }

  /**
   * Set the specified facet key/value pair in the status.
   */
  public void setStatus(String key, BIDataValue value)
  {
    setStatus(BStatus.make(getStatus(), key, value));
  }

////////////////////////////////////////////////////////////////
// BIStatusValue
////////////////////////////////////////////////////////////////  

  /**
   * Return <code>this</code>
   */
  public BStatusValue getStatusValue()
  {
    return this;
  }

  /**
   * Return facets for this
   */
  public BFacets getStatusValueFacets()
  {
    BComplex parent = getParent();
    if (parent != null)
      return parent.getSlotFacets(getPropertyInParent());
    return BFacets.NULL;
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////  

  /**
   * Get the StatusValue as a String.
   */
  public String toString(Context context)
  {
    String value;
    if (getStatus().isNull())
      value = "-";
    else
      value = valueToString(context);
      
    String status = getStatus().toString(context);
    
    StringBuilder s = new StringBuilder();
    s.append(value).append(' ').append(status);
    return s.toString();
  }

  /**
   * Get the value portion only of StatusValue as a String.
   */
  public abstract String valueToString(Context context);
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  
  
  /**
   * Return the value taking the Null Status into account
   * <p>
   * If the Status is null, the Property's default value is returned.
   * If the Status is not null, the value is returned.
   * 
   * @since Niagara 3.6
   */
  public BValue getNvalue()
  {
    if (getStatus().isNull())
      return getValueProperty().getDefaultValue();
    else
      return getValueValue();
  }
}
