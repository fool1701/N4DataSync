/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * Property defines a Slot which is a storage location
 * for a variable in a BComplex.
 *
 * @author    Brian Frank
 * @creation  24 Mar 00
 * @version   $Revision: 20$ $Date: 1/26/06 9:40:19 AM EST$
 * @since     Baja 1.0
 */
public interface Property
  extends Slot
{  

  /**
   * Get the type of the property.
   */
  public Type getType();
    
  /**
   * Due to Java's dual type system, some
   * primitive types are handled specially in 
   * calls such as {@code BComplex.get()} or
   * {@code BComplex.set()}.  In order to facilate
   * special processing of these types, this method 
   * provides a type code to indicate that a property
   * is one of the special types.
   *
   * @return the type access code which is one of 
   *    {@code X_TYPE} constants defined by
   *    the Slot interface.
   */
  public int getTypeAccess();
  
  /**
   * Return true if the property's type is
   * a final class. 
   */
  public boolean isTypeFinal();
  
  /**
   * Get a copy of the default value for this property.
   * If this property is dynamic, then this is a
   * copy of the instance value.
   */
  public BValue getDefaultValue();
  
  /**
   * Return if the specified object is equal to
   * this object's default value.
   */
  public boolean isEquivalentToDefaultValue(BValue object);
    
}
