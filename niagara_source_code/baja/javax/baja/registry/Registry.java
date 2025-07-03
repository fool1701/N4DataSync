/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.registry;

import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.naming.UnknownSchemeException;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BModule;
import javax.baja.sys.ModuleNotFoundException;
import javax.baja.sys.Type;
import javax.baja.sys.TypeNotFoundException;
import javax.baja.util.BTypeSpec;
import javax.baja.util.Version;
import javax.baja.nre.platform.RuntimeProfile;

/**
 * The registry may be used to interrogate the system for summary
 * information about the modules, types, ord schemes, and agents 
 * installed without loading modules into memory.
 *
 * @author    Brian Frank
 * creation  5 Dec 02
 * @version   $Revision: 29$ $Date: 7/7/11 2:03:50 PM EDT$
 * @since     Baja 1.0
 */
public interface Registry
{  

////////////////////////////////////////////////////////////////
// Meta
////////////////////////////////////////////////////////////////

  /**
   * Get summary information about all the modules
   * currently installed on the system.
   */
  BAbsTime getLastBuildTime();
  
////////////////////////////////////////////////////////////////
// ModuleInfo
////////////////////////////////////////////////////////////////

  /**
   * Get summary information about all the modules
   * currently installed on the system.
   */
  ModuleInfo[] getModules();

  /**
   * Get information for the module that meets the given dependency
   */
  ModuleInfo moduleForDependency(String modulePartName);
  
  /**
   * Get information for the specified module.
   * @throws ModuleNotFoundException
   */
  ModuleInfo getModule(String moduleName, RuntimeProfile profile);

  ModuleInfo[] getModules(String moduleName);

////////////////////////////////////////////////////////////////
// TypeInfo
////////////////////////////////////////////////////////////////  

  /**
   * Get the list of the installed types.
   */
  TypeInfo[] getTypes();
  
  /**
   * Get the list of all the installed types which
   * extend (or implement) the specified type.
   */
  TypeInfo[] getTypes(TypeInfo type);

  /**
   * Get the list of all the concrete (non-abstract) installed 
   * types which extend (or implement) the specified type.
   */
  TypeInfo[] getConcreteTypes(TypeInfo type);

  /**
   * Get details about the Type identified by the specified 
   * type spec of the format "module:typename".
   * @throws TypeNotFoundException
   */
  TypeInfo getType(String typeSpec);

////////////////////////////////////////////////////////////////
// Defs
////////////////////////////////////////////////////////////////  
  
  /**
   * Get the list of all the declared name/value 
   * definitions declared in module manifests.
   */
  String[] getDefs();

  /**
   * Get all the defs for the specified String key.  If
   * the key is not found return an array of length zero.
   */
  String[] getDefs(String name);
  
  /**
   * Convenience for <code>getDef(name, null)</code>.
   */
  String getDef(String name);

  /**
   * Lookup a definition name/value pair with a String
   * key.  If there is more than one def for the given
   * key, return the first one (no order guaranteed).  
   * If not found then return fallback parameter.
   */
  String getDef(String name, String fallback);

////////////////////////////////////////////////////////////////
// Agents
////////////////////////////////////////////////////////////////  
  
  /**
   * Get all the agents registered for the specified target type.
   */
  AgentList getAgents(TypeInfo targetType);

  /**
   * Get all the agents registered specifically on the target type.
   * This does not return agents registered on a super type or
   * interface of this target type.
   */
  AgentList getSpecificAgents(TypeInfo targetType);

  /**
   * Return if the specified agent type is a registered agent
   * on the target type.  Note this method applies only to the
   * the registry database and does not take into account what
   * a BObject may do in its <code>getAgents()</code> method.
   * 
   * @since Niagara 4.0
   */
  boolean isAgent(TypeInfo agentType, TypeInfo targetType);
  
  /**
   * Return if the specified agent type is an agent specifically
   * registered on the target type.  Note this method applies only to the
   * the registry database and does not take into account what
   * a BObject may do in its <code>getAgents()</code> method.
   */
  boolean isSpecificAgent(TypeInfo agentType, TypeInfo targetType);
                         
////////////////////////////////////////////////////////////////
// Adapters
////////////////////////////////////////////////////////////////  
  
  /**
   * Get the list of adapters available to convert instances 
   * of the fromType into instances of the toType.  If no 
   * adapters are available then return an empty array.
   */
  TypeInfo[] getAdapters(TypeInfo fromType, TypeInfo toType);

////////////////////////////////////////////////////////////////
// File Extensions
////////////////////////////////////////////////////////////////  

  /**
   * Get the list of file extensions registered to an
   * implementation of "baja:IFile".
   */
  String[] getFileExtensions();

  /**
   * Get the list of file extensions registered to an
   * implementation of the specified type.
   */
  String[] getFileExtensions(TypeInfo typeInfo);

  /**
   * Given an extension map it to the correct implementation
   * of "baja:IFile".  If no file is found for the extension
   * then "baja:DataFile" is returned.  Extensions are case
   * insensitive.
   */
  TypeInfo getFileTypeForExtension(String ext);

////////////////////////////////////////////////////////////////
// Ord Schemes
////////////////////////////////////////////////////////////////  
  
  /**
   * Get the list of all the registered ord schemes.
   */
  String[] getOrdSchemes();

  /**
   * Get TypeInfo by its global scheme id.
   * @throws UnknownSchemeException
   */
  TypeInfo getOrdScheme(String schemeId);

  /**
   * Find a TypeInfo by its global scheme id.
   *
   * @param schemeId The id of the ord scheme.
   * @return Returns the TypeInfo for the ord scheme or null if the ord scheme is not found.
   */
  TypeInfo findOrdScheme(String schemeId);

  /**
   * Test to see if the specified scheme id refers to a
   * known ord scheme.
   */
  boolean isOrdScheme(String schemeId);
  
////////////////////////////////////////////////////////////////
//Lexicons
//////////////////////////////////////////////////////////////// 

  /**
   * Get the list of all the registered lexicons.
   */
  LexiconInfo[] getLexicons();

  /**
   * Get the list of all the registered lexicons for a module.
   */
  LexiconInfo[] getLexicons(String moduleName);

  /**
   * Get the registered lexicons for a module and language.
   */
  LexiconInfo[] getLexicons(String moduleName, String language);

  /**
   * Get the registered lexicon for a module, language,
   * and lexicon container module name.
   */
  LexiconInfo getLexicon(String moduleName, String language, String container, String containerProfile);


////////////////////////////////////////////////////////////////
//Synthesizing
////////////////////////////////////////////////////////////////

  /**
   * Create a new synthesized Niagara type with the specified attributes. When 
   * called, Java bytecode will be generated and loaded into the local VM.  Type 
   * information will be automatically added to the NRE's registry. An instance
   * of the new type can then be instantiated by calling type.getInstance().
   * 
   * @param typeSpec The type spec of the new type, made up of the module 
   * name and type name. The module must reference a synthesized module created
   * using the synthesizeModule() method.
   * @param className The full Java class name of the new type. The package 
   * name must start with "auto." and match the type name specified in the 
   * typeSpec attribute.
   * @param superType The TypeInfo instance for the desired super type.
   * @param interfaceTypes An array of TypeInfo instances for the desired 
   * interfaces for this new type to implement.
   * @param agentOnTypes An array of AgentInfo instances specifying the other 
   * Niagara types that the new type is registered as an agent on.
   * @param isAbstract Boolean flag that sets the created type as abstract when 
   * true. Classes can not be both abstract and final.
   * @param isFinal Boolean flag that sets the created type as final when true. 
   * Classes can not be both abstract and final.
   * 
   * @since Niagara 3.7
   * @return The Type instance declared on the resulting new Niagara type. The type is
   * typically a specialized Type implementation which provides additional support for
   * modifying or managing the synthetic type.
   */
  Type synthesizeType(
    BTypeSpec typeSpec,
    String className,
    TypeInfo superType,
    TypeInfo[] interfaceTypes,
    AgentInfo[] agentOnTypes,
    boolean isAbstract,
    boolean isFinal);

  /**
   * Create a new synthesized Niagara module with the specified attributes. The result
   * is a new memory construct which represents a module within the Niagara registry.
   * The product is completely memory resident, no actula module file is created on the 
   * file system.
   * 
   * Once a synthesized Niagara module has been created, Niagara types can be synthesized
   * for inclusion in this module.
   * 
   * @since Niagara 3.7
   * 
   * @param moduleName The module name of the synthesized module.  This must not match an
   * existing module within the NRE.
   * @param bajaVersion The required baja version.  Typically always 1.0.
   * @param vendor The module vendor.
   * @param vendorVersion The module vendor version.
   * @param description The textual description of the module contents.
   * 
   * @return A BModule instance for the synthesized module
   */
  BModule synthesizeModule(
    String moduleName,
    Version bajaVersion,
    String vendor,
    Version vendorVersion,
    String description);
}
