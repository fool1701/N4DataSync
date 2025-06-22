/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.registry.*;
import javax.baja.util.*;
import javax.baja.nre.platform.RuntimeProfile;

/**
 * Type defines the typing information associated
 * with a BObject class.  Type is loaded by the framework
 * using the Sys.loadType() method.  Every BObject type
 * must load their type and store it in a static variable
 * which is access via the type() method.
 * 
 * @author    Brian Frank
 * @creation  1 Feb 00
 * @version   $Revision: 42$ $Date: 6/28/11 12:27:04 PM EDT$
 * @since     Baja 1.0
 */
public interface Type
{

  /**
   * Get the Type which is used to represent the
   * type meta-data for this type class's superclass.
   * <p>
   * If the type is "baja.Object" return null.  
   * <p>
   * If this type is an interface return null.
   */
  public Type getSuperType();
  
  /**
   * Get the list of BInterface Types which are implemented
   * by this Type.  If this type is a subclass of BObject,
   * then this is all the BInterfaces which the
   * BObject directly implements.  If this is an interface
   * type, then this is all the interface types that this
   * interface directly extends.
   */
  public Type[] getInterfaces();

  /**
   * Return the vendor version for the module JAR that supports
   * this Type.
   *
   * @since Niagara 4.0
   */
  default Version getVendorVersion()
  {
    return getModule().getVendorVersion(getRuntimeProfile());
  }

  /**
   * Return the vendor for the module JAR that supports
   * this Type.
   *
   * @since Niagara 4.0
   */
  default String getVendor()
  {
    return getModule().getVendor(getRuntimeProfile());
  }

  /**
   * Get a unique integer identifier for this type.  This
   * identifier is not persistent, and is only good for the
   * life of the VM.
   */
  public int getId();

  /**
   * Get the module which was used to load 
   * this type's implementation class.
   */
  public BModule getModule();

  /**
   * Get the runtime profile for which this type is registered.
   *
   * @since Niagara 4.0
   */
  public RuntimeProfile getRuntimeProfile();
    
  /**
   * Get the class which implements this type.  The
   * implementation class is the class which was passed
   * to {@code Sys.loadType()}.
   */
  public Class<?> getTypeClass();
    
  /**
   * Get the type name.  The type name is an abreviated
   * String key for the Type to use for serialization and
   * scripting.  The type name is always the class name
   * minus the package and any leading 'B' character.
   * These type names are mapped to classnames by the
   * "type" elements in the modules's "meta-inf/module.xml"
   * file.   
   */
  public String getTypeName();
  
  /**
   * Return {@code getTypeInfo().getDisplayName(cx)}.
   */
  public String getDisplayName(Context c);
  
  /**
   * Return true if this Type's implementation class is
   * abstract.  If this is an abstract type, then many
   * operations will throw the AbstractTypeException.
   */
  public boolean isAbstract();
  
  /**
   * Return true if this Type's implementation class is
   * an interface.  If this is an interface, then many
   * operations will throw the InterfaceTypeException.
   */
  public boolean isInterface();
    
  /**
   * Return if this Type is one of the predefined types
   * defined in DataTypes and used by the Baja Data APIs.
   */
  public boolean isDataType();
  
  /**
   * Return if values of this Type should not be persisted to
   * the station bog.
   * @since Niagara 3.7
   */
  public boolean isTransient();
  
  /**
   * If this Type returns true for {@code isDataType}
   * then return the unique ASCII character that identifies
   * this data type.  Otherwise return 0.
   */
  public char getDataTypeSymbol();
    
  /**
   * Get an instance of the type's implementation class.
   * What this method returns is dependent on the semantics
   * of the BObject type.  In general:
   * <ul>
   * <li>BSingleton: returns INSTANCE;</li>
   * <li>BSimple: returns DEFAULT;</li>
   * <li>BFrozenEnum: returns first value in range;</li>
   * <li>BComplex: returns new instance using no arg constructor;</li>
   * </ul>
   */
  public BObject getInstance();
  
  /**
   * Get the TypeInfo for this Type which provides access 
   * for registry interrogation.
   */
  public TypeInfo getTypeInfo();
  
  /**
   * Return if this type supports the specified type via
   * inheritance.  If this method returns true, then any
   * instance of this type may be cast into the specified
   * type.
   */
  public boolean is(TypeInfo type);

  /**
   * Return if this type supports the specified type via
   * inheritance.  If this method returns true, then any
   * instance of this type may be cast into the specified
   * type.
   */
  public boolean is(Type type);
    
  /**
   * Get the BTypeSpec used to represent this Type.
   */
  public BTypeSpec getTypeSpec();
    
  /**
   * Return the type as a type spec "module:typename".
   */
  public String toString();

}
