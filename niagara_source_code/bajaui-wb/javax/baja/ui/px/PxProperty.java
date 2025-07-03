/*
 * Copyright 2008 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.px;

import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.SlotPath;
import javax.baja.nre.util.Array;
import javax.baja.sys.BComponent;
import javax.baja.sys.BValue;
import javax.baja.sys.Property;
import javax.baja.util.BTypeSpec;

/**
 * PxProperty.
 *
 * @author    Andy Frank
 * @creation  13 Feb 08
 * @version   $Revision: 5$ $Date: 2/19/08 10:40:25 AM EST$
 * @since     Niagara 3.3
 */
public class PxProperty
{

//////////////////////////////////////////////////////////////////////////
// Constructor
//////////////////////////////////////////////////////////////////////////
  
  /**
   * Create a new PxProperty.
   */  
  public PxProperty(String name, BTypeSpec type, BValue value, SlotPath[] targets)
  {
    this.name    = name;
    this.type    = type;
    this.value   = value;
    this.targets = targets;
  }

//////////////////////////////////////////////////////////////////////////
// Methods
//////////////////////////////////////////////////////////////////////////
  
  /**
   * Apply the sourceSlot value to all the targets against
   * the given base.
   */
  public void apply(BComponent base) 
  {
    apply(base, null);
  }

  /**
   * Apply the given override value to all the targets against
   * the given base.  If override is null, the default value
   * will be used.
   */
  public void apply(BComponent base, BValue override) 
  {
    // cache our OrdTargets if we haven't yet
    if (resolved == null)
    {
      resolved = new OrdTarget[targets.length];
      for (int i=0; i<resolved.length; i++)
        resolved[i] = BOrd.make(targets[i]).resolve(base);
    }
    
    // apply to targets
    for (int i=0; i<resolved.length; i++)
    {
      // use override if not null, otherwise fallback to default value
      BValue v = override != null ? override : value;
            
      // apply value
      BComponent comp = resolved[i].getComponent();
      Property prop = (Property)resolved[i].getSlotInComponent();
      comp.set(prop, v);
    }
  }    

  /**
   * Return the name of this property.
   */  
  public String getName()     
  {
    return name;    
  }

  /**
   * Set the name of this property.
   */  
  public void setName(String name)
  {
    this.name = name;
  }
  
  /**
   * Return the TypeSpec for this property.
   */
  public BTypeSpec getTypeSpec() 
  { 
    return type;
  }
  
  /**
   * Return the value for this property.
   */
  public BValue getValue() 
  { 
    return value;
  }

  /**
   * Set the value of this property.
   */  
  public void setValue(BValue value)
  {
    this.value = value;
  }
  
  /**
   * Return the targets for this property.
   */  
  public SlotPath[] getTargets()
  { 
    return targets; 
  }

  /**
   * Set the targets for this property.
   */  
  public void setTargets(SlotPath[] targets)
  { 
    this.targets = targets;
    this.resolved = null;
  }
  
  /**
   * Return a string representation for this property.
   */ 
  public String toString()
  {
    return "PxProperty[" + 
      "name:" + name + "," + 
      "type:" + type + "," + 
      "value:" + value + "," + 
      "targets:" + (new Array<>(targets)) + "]";
  }

//////////////////////////////////////////////////////////////////////////
// Fields
//////////////////////////////////////////////////////////////////////////

  private String name;           // name of property
  private BTypeSpec type;        // type of property
  private BValue value;          // default value of property
  private SlotPath[] targets;    // target slots to drive
  
  private OrdTarget[] resolved;  // resolved targets
    
}
