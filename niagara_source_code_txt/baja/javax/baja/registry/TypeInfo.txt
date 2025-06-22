/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.registry;

import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Type;
import javax.baja.util.BTypeSpec;
import javax.baja.util.Lexicon;
import javax.baja.nre.platform.RuntimeProfile;

/**
 * TypeInfo provides summary information about a type 
 * available in an installed module.
 *
 * @author    Brian Frank
 * creation  5 Dec 02
 * @version   $Revision: 19$ $Date: 7/6/11 2:08:45 PM EDT$
 * @since     Baja 1.0
 */
public interface TypeInfo
{

  /**
   * Get the module name which implements this type.
   */
  String getModuleName();

  /**
   * Get the runtime profile for the module part with this type.
   */
  RuntimeProfile getRuntimeProfile();
        
  /**
   * Get the type name.  The type name is an abreviated
   * String key for the Type to use for serialization and
   * scripting.  The type name is always the class name
   * minus the package and any leading 'B' character.
   * These type names are mapped to classnames by the
   * "type" elements in the modules's "meta-inf/module.xml"
   * file.   
   */
  String getTypeName();
        
  /**
   * Get the BTypeSpec used to represent this Type.
   */
  BTypeSpec getTypeSpec();
  
  /**
   * Convenience for <code>getTypeSpec().getInstance()</code>.
   */
  BObject getInstance();

  /**
   * Get the TypeInfo for the super type.  If this is an 
   * interface or this is "baja:Object" then return null.
   */
  TypeInfo getSuperType();

  /**
   * Get an array of TypeInfos for BInterfaces which this
   * type directly implements.
   */
  TypeInfo[] getInterfaces();

  /**
   * Get the class name which implements this type.
   */
  String getTypeClassName();
  
  /**
   * Return true if the type class is abstract or is an interface.
   */
  boolean isAbstract();
  
  /**
   * Return true if the type class is final.
   */
  boolean isFinal();

  /**
   * Return true if the type class an interface.
   */
  boolean isInterface();

  /**
   * Return true if all instances of the type are transient.
   */
  boolean isTransient();

  /**
   * Return if this type supports the specified type via
   * inheritance.  If this method returns true, then any
   * instance of this type may be cast into the specified
   * type.
   */
  boolean is(TypeInfo typeInfo);
  
  /**
   * Convenience for <code>is(type.getTypeInfo())</code>.
   */
  boolean is(Type type);
  
  /**
   * If this type is an agent type, then return additional
   * agent information.  Otherwise throw RegistryException.
   */
  AgentInfo getAgentInfo();

  /**
   * Return an AgentList of AgentInfos representing types which
   * are registered as an agent on this type.
   */
  AgentList getAgents();
  
  /**
   * Convenience for <code>Lexicon.make(getModuleName(), cx)</code>
   */
  Lexicon getLexicon(Context cx);
  
  /**
   * Get the display name specified using the lexicon for 
   * this type's module and the given context.  The key 
   * is "{typename}.displayName".  Return the result of
   * {@code TextUtil.toFriendlyName(typeName)} if not lexicon
   * key is not found.
   */
  String getDisplayName(Context cx);

  /**
   * Get the icon specified using the lexicon for this 
   * type's module and the given context.  The key 
   * is "{typename}.icon".  If not found, then return the
   * icon of the super type.  Return null if no icon is
   * found in this or any super type.
   */
  BIcon getIcon(Context cx);
        
  /**
   * Return this TypeInfo as a type spec "moduleName:typeName".
   */
  String toString();
  
}
