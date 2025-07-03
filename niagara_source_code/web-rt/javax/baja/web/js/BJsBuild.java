/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web.js;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BSingleton;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.web.WebDev;

/**
 * <p>
 * This class declares one particular bundle of JavaScript, optimized by the
 * r.js optimizer (and probably minified as well).
 * </p>
 * <p>
 * If webdev is turned off for this build's ID, then any built files it declares
 * should be downloaded first, before any individual modules it contains are
 * loaded. If webdev is turned on, the built files should be skipped so that
 * the raw, unminified modules are loaded.
 * </p>
 * <p>
 * Most commonly, there will be one {@code BJsBuild} per Niagara module,
 * declaring a single builtfile containing all the JS in that module. Consider
 * using {@code grunt-niagara} to easily create that builtfile as part of the
 * module build process.
 * </p>
 * <p>
 * Additionally, {@link BCssResource}s may be declared as dependencies
 * of a JsBuild to specify all the CSS files used by that module.
 * </p>
 *
 * @author Logan Byam
 * @since Niagara 4.0
 * @see <a href="https://github.com/tridium/grunt-niagara">grunt-niagara</a>
 * @see <a href="https://github.com/tridium/grunt-init-niagara">grunt-init-niagara</a>
 */
@NiagaraType
public abstract class BJsBuild extends BSingleton implements BIWebResource, BIJavaScript
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.js.BJsBuild(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:44 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BJsBuild.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * @param id the ID to search for
   * @return the BJsBuild instance with the given ID, or empty if not found
   */
  public static Optional<BJsBuild> forId(String id) {
    if (id == null)
    {
      return Optional.empty();
    }

    return CacheHolder.BY_ID.computeIfAbsent(id, (i) -> Optional.empty());
  }


  /**
   * Get an array of all RequireJS module dependencies from the given types.
   * @param types array of types that extend BJsBuild
   * @return array of all module js dependencies necessary to instantiate the
   * JS constructors for these types
   */
  private static String[] typesToDependencies(Type[] types) {
    if (types == null)
    {
      throw new IllegalArgumentException("array of BIWebResource types required");
    }

    ArrayList<String> jsTypeIds = new ArrayList<>();
    for (Type type : types)
    {
      if (type == null || type.isAbstract() || !type.is(BIWebResource.TYPE))
      {
        throw new IllegalArgumentException("array of BIWebResource types required");
      }

      if (type.getInstance() instanceof BJsBuild)
      {
        jsTypeIds.add(((BJsBuild) type.getInstance()).getId());
      }
    }

    String[] allJsIds = new String[jsTypeIds.size()];
    return jsTypeIds.toArray(allJsIds);
  }

  /**
   * Update the web resource dependencies list with all type instances
   * @param types found in JsBuild dependency list
   * @since Niagara 4.13
   */
  private void addWebResources(Type[] types)
  {
    webResources = new HashSet<>();
    for (Type type : types)
    {
        webResources.add((BIWebResource) type.getInstance());
    }
  }

  /**
   * Create an instance with a string ID and array of built/minified Javascript
   * files. This instance will not register dependencies on any other builds,
   * so avoid this constructor unless you are certain your build has no
   * dependencies on any other JS builds.
   *
   * @param id string to uniquely identify this build
   * @param builtFiles array of built/minified files
   * @throws IllegalArgumentException if id or file array is missing
   */
  protected BJsBuild(String id, BOrd[] builtFiles)
  {
    this(id, builtFiles, EMPTY);
  }

  /**
   * Create an instance with a string ID and a single built/minified Javascript
   * file. This instance will not register dependencies on any other builds,
   * so avoid this constructor unless you are certain your build has no
   * dependencies on any other JS builds.
   *
   * @param id string to uniquely identify this build
   * @param builtFile built/minified file
   * @throws IllegalArgumentException if id or file array is missing
   * @since Niagara 4.8
   */
  protected BJsBuild(String id, BOrd builtFile)
  {
    this(id, new BOrd[] { builtFile });
  }

  /**
   * Create an instance with a string ID and a single built/minified Javascript
   * file.
   *
   * @param id string to uniquely identify this build
   * @param builtFile built/minified file
   * @param dependentBuildIds array of IDs of builds this build depends on
   * @throws IllegalArgumentException if id or file array is missing
   * @since Niagara 4.8
   */
  protected BJsBuild(String id, BOrd builtFile, String... dependentBuildIds)
  {
    this(id, new BOrd[] { builtFile }, dependentBuildIds);
  }

  /**
   * Create an instance with a string ID and a single built/minified Javascript
   * file.
   *
   * @param id string to uniquely identify this build
   * @param builtFile built/minified file
   * @param dependentBuilds array of Types of builds this build depends on
   * @throws IllegalArgumentException if id or file array is missing
   * @since Niagara 4.8
   */
  protected BJsBuild(String id, BOrd builtFile, Type... dependentBuilds)
  {
    this(id, new BOrd[] { builtFile }, dependentBuilds);
  }

  /**
   * Create an instance with a string ID and array of built/minified Javascript
   * files.
   * 
   * @param id string to uniquely identify this build
   * @param builtFiles array of built/minified files
   * @param dependentBuildIds array of ID of builds this build depends on
   * @throws IllegalArgumentException if id or file array is missing
   */
  protected BJsBuild(String id, BOrd[] builtFiles, String... dependentBuildIds)
  {
    if (id == null)
    {
      throw new IllegalArgumentException("id required");
    }

    if (!isArrayOfOrds(builtFiles))
    {
      throw new IllegalArgumentException("built files required");
    }
    
    this.id = id;
    this.builtFiles = builtFiles.clone();
    this.dependentBuildIds = dependentBuildIds.clone();
  }
  
  /**
   * @param id string to uniquely identify this build
   * @param builtFiles array of built/minified files
   * @param dependentTypes array of Types of builds this build depends on
   * @throws IllegalArgumentException if id or built files are missing, or
   * if a non-BJsBuild subtype is given
   */
  protected BJsBuild(String id, BOrd[] builtFiles, Type[] dependentTypes)
  {
    this(id, builtFiles, typesToDependencies(dependentTypes));
    addWebResources(dependentTypes);
  }

  /**
   * Get a string to uniquely identify this build file.
   * 
   * @return this build's ID
   */
  public String getId()
  {
    return id;
  }

  /**
   * Get the array of built/minified files represented by this build.
   *
   * @return array of built/minified files
   */
  public BOrd[] getBuiltFiles()
  {
    return builtFiles.clone();
  }

  /**
   * @return array of built/minified files
   * @since Niagara 4.13
   */
  @Override
  public BOrd[] getFiles()
  {
    return getBuiltFiles();
  }

  /**
   * Get the other builds this build directly depends on.
   * 
   * @return array of BJsBuilds
   */
  public BJsBuild[] getDependentBuilds()
  {
    return Arrays.stream(dependentBuildIds)
      .map(b -> forId(b))
      .filter(Optional::isPresent)
      .map(Optional::get)
      .toArray(size -> new BJsBuild[size]);
  }

  /**
   * Get all web resource dependencies of BJsBuild
   *
   * @return a set of web resources dependencies either defined by type or id,
   * or an empty set if none are found
   * @since Niagara 4.13
   */
  @Override
  public Set<BIWebResource> getDependencies()
  {
    if (webResources != null)
    {
      return Collections.unmodifiableSet(webResources);
    }
    else //retrieve dependentBuildId instances if present
    {
      Set<BIWebResource> dependencies = new HashSet<>();
      for (String buildId : dependentBuildIds)
      {
        Optional<BJsBuild> build = forId(buildId);
        if (build.isPresent())
        {
          dependencies.add(build.get());
        }
        else
        {
          throw new RuntimeException("JsBuild " + buildId + " is not found");
        }
      }
      return dependencies;
    }
  }

  /**
   * Check to see if webdev is enabled for this build. If it is disabled,
   * ensure that RequireJS loads the built files before the actual modules;
   * otherwise, download the raw/unminified modules.
   * 
   * @return true if webdev is enabled for this build
   */
  public boolean isWebDevEnabled()
  {
    return WebDev.get(getId()).isEnabled();
  }

  private static boolean isArrayOfOrds(BOrd[] ords)
  {
    if (ords == null || ords.length == 0) { return false; }
    for (BOrd ord : ords) { if (ord == null) { return false; } }
    return true;
  }

  private interface CacheHolder {
    Map<String, Optional<BJsBuild>> BY_ID = retrieveInstancesFromRegistry();
  }

  private static Map<String, Optional<BJsBuild>> retrieveInstancesFromRegistry()
  {
    Map<String, Optional<BJsBuild>> map = new HashMap<>();
    for (TypeInfo typeInfo : Sys.getRegistry().getTypes(TYPE.getTypeInfo()))
    {
      if (!typeInfo.isAbstract())
      {
        try
        {
          BJsBuild instance = (BJsBuild) typeInfo.getInstance();
          map.put(instance.getId(), Optional.of(instance));
        }
        catch (Exception e)
        {
          final String message = "Unable to load BJsBuild type -> " + typeInfo;

          if (!jsBuildErrorLogged)
          {
            Logger.getLogger("web.jsbuild").log(Level.SEVERE, message, e);
            jsBuildErrorLogged = true;
          }
          else
          {
            Logger.getLogger("web.jsbuild").log(Level.FINE, message, e);
          }
        }
      }
    }
    return map;
  }

  @Override
  public final JsInfo getJsInfo(Context cx)
  {
    return getType() == BJsBuild.TYPE ?
      JsInfo.make(BOrd.DEFAULT) :
      JsInfo.make(BOrd.DEFAULT, getType());
  }

  private static final String[] EMPTY = new String[0];
  private final String id;
  private final BOrd[] builtFiles;
  private final String[] dependentBuildIds;
  private static boolean jsBuildErrorLogged = false;
  private Set<BIWebResource> webResources;
}
