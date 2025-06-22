/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.options;

import javax.baja.sys.*;
import javax.baja.registry.*;

/**
 * OptionsManager is used to manage BOptions for a the 
 * current user.  Access to the OptionsManager is via 
 * <code>BWidget.getApplication().getOptionsManager()</code> or
 * <code>BOptions.getOptionsManager()</code>.
 *
 * @author    Brian Frank
 * @creation  26 Nov 01
 * @version   $Revision: 8$ $Date: 11/17/04 9:39:25 AM EST$
 * @since     Baja 1.0
 */
public interface OptionsManager
{ 
  
  /**
   * Convenience for <code>load(typeInfo.getTypeSpec().getResolvedType())</code>.
   */
  BOptions load(TypeInfo typeInfo);
  
  /**
   * Convenience for <code>load(type.toString(), type)</code>.
   */
  BOptions load(Type type);

  /**
   * Get the options by key for the current user.  If the specified 
   * options haven't been accessed yet, then create a new instance 
   * using the specified type.  If the type is a BUserOptions, then 
   * key MUST be <code>type.toString()</code>.
   *
   * In Niagara 4.4 and later, BOptions.loaded() should be called before returning.
   */
  BOptions load(String key, Type type);
    
  /**
   * Save the specified options.
   */
  void save(BOptions options);
  
  /**
   * Save all the options.
   */
  void saveAll();
  
}
