/**
 * Copyright 2013 Tridium, Inc. - All Rights Reserved.
 */
package javax.baja.sys;

import java.util.HashMap;

/**
 * A Validatable is a data structure created during property set validation
 * that wraps a BComplex and provides a mechanism for validator implementations
 * to interrogate the existing and proposed state of a BComplex.  It provides 
 * methods to retrieve the existing property values of the BComplex instance as 
 * well as the proposed property values (future state) that will be committed on 
 * the BComplex if validation succeeds.  
 * 
 * @author Scott Hoye
 * @creation Aug 16, 2013
 * @version $Revision: 1$ $Date: 8/16/13 1:57:55 PM EDT$
 * @since Niagara 4.0
 */
public final class Validatable
{

  /**
   * Construct a Validatable instance.
   * @param existing - The existing BComplex instance that is pending a property
   * set operation that requires validation first.
   * @param modifiedProperties - The properties on the BComplex instance that
   * are pending a change of value.
   * @param modifiedValues - The pending values of the properties that are pending
   * a change on the BComplex instance.
   */
  public Validatable(BComplex existing,
                     Property[] modifiedProperties,
                     BValue[] modifiedValues)
  {
    this.existing = existing;
    this.modifiedProperties = modifiedProperties;
    this.modifiedValues = modifiedValues;
    proposed = new HashMap<>(modifiedProperties.length);
    for (int i = 0; i < modifiedProperties.length; i++)
    {
      proposed.put(modifiedProperties[i], modifiedValues[i]);
    }
  }
  
  /**
   * Retrieve the proposed value for the given property of the 
   * wrapped BComplex instance IF the pending property changes 
   * are successful.  This allows you to interrogate the future
   * state of the BComplex instance prior to the property changes
   * being committed.  Even if the property is not one of the pending
   * changes, this method will still return the existing value.
   * 
   * @param property - The property to retrieve the proposed 
   * (future) value.
   * @return The value of the property on the wrapped BComplex instance
   * assuming that the pending property changes are successful.
   */
  public BValue getProposedValue(Property property)
  {
    BValue val = proposed.get(property);
    if (val != null)
      return val;
    else
      return getExistingValue(property);
  }
  
  /**
   * Retrieve the proposed value for the given property (by property name)
   * of the wrapped BComplex instance IF the pending property changes 
   * are successful.  This allows you to interrogate the future
   * state of the BComplex instance prior to the property changes
   * being committed.  Even if the property is not one of the pending
   * changes, this method will still return the existing value.
   * 
   * @param propertyName - The name of the property to retrieve the proposed
   * (future) value.
   * @return The value of the property on the wrapped BComplex instance
   * assuming that the pending property changes are successful.
   */
  public BValue getProposedValue(String propertyName)
  {
    Property prop = existing.getProperty(propertyName);
    if (prop != null)
      return getProposedValue(prop);
    else
      return null;
  }
  
  /**
   * Retrieve the existing value for the given property of the 
   * wrapped BComplex instance disregarding the pending property 
   * changes.  This allows you to interrogate the existing (old)
   * state of the BComplex instance prior to the pending property changes
   * being committed.
   * 
   * @param property - The property to retrieve the existing
   * value.
   * @return The existing value of the property on the wrapped 
   * BComplex instance, disregarding any pending property changes.
   */
  public BValue getExistingValue(Property property)
  {
    return existing.get(property);
  }
  
  /**
   * Retrieve the existing value for the given property (by property name)
   * of the wrapped BComplex instance disregarding the pending property 
   * changes.  This allows you to interrogate the existing (old)
   * state of the BComplex instance prior to the pending property changes
   * being committed.
   * 
   * @param propertyName - The name of the property to retrieve the existing
   * value.
   * @return The existing value of the property on the wrapped 
   * BComplex instance, disregarding any pending property changes.
   */
  public BValue getExistingValue(String propertyName)
  {
    return existing.get(propertyName);
  }
  
  /**
   * Retrieve the wrapped (existing) BComplex instance that is currently
   * being validated for pending property value changes.
   */
  public BComplex getExistingComplex()
  {
    return existing;
  }
  
  /**
   * Retrieve an array of the properties that are pending modification on
   * the wrapped BComplex instance
   */
  public Property[] getModifiedProperties()
  {
    return modifiedProperties;
  }
  
  /**
   * Retrieve an array of the new property values that are pending modification on
   * the wrapped BComplex instance
   */
  public BValue[] getModifiedValues()
  {
    return modifiedValues;
  }
  
  /**
   * Convenience method to return whether the wrapped (existing) BComplex instance
   * is part of a running component in the master ComponentSpace.
   */
  public boolean isRunning()
  {
    BComponent comp = existing.getParentComponent();
    if (comp != null)
      return comp.isRunning();
    else
      return false;
  }
  
  private BComplex existing;
  private Property[] modifiedProperties;
  private BValue[] modifiedValues;
  private HashMap<Property, BValue> proposed;
  
}
