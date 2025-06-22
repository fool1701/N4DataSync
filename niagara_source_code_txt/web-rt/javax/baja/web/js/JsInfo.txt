/*
 * Copyright 2012, Tridium Inc, All Rights Rervered.
 */
package javax.baja.web.js;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.baja.naming.BOrd;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Type;

import com.tridium.web.js.NiagaraRequireJsMapper;

/**
 * A data structure for holding JavaScript information.
 * 
 * @see BIJavaScript
 * 
 * @author   Gareth Johnson on 17 Oct 2012
 * @since    Niagara 4.0
 */
public final class JsInfo
{
  private JsInfo(BOrd js, String buildId)
  {
    if (js == null)
    {
      throw new IllegalArgumentException("js required");
    }
    
    this.js = js;
    this.buildId = buildId;
  }
  
////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////  

  /**
   * @param js ORD to the JS file this JsInfo represents
   * @param buildId ID for the JsBuild in which this JS file resides. If null,
   *                it is not considered part of any JsBuild and therefore 
   *                will always be downloaded unminified.
   * @return new JsInfo
   */
  public static JsInfo make(BOrd js, String buildId)
  {
    return new JsInfo(js, buildId);
  }

  /**
   * Create a JsInfo using the given BJsBuild Type's ID as the build ID.
   * 
   * @param js ORD to the JS file this JsInfo represents
   * @param buildType BJsBuild subtype whose ID to use
   * @return new JsInfo
   */
  public static JsInfo make(BOrd js, Type buildType)
  {
    if (buildType == null ||
      buildType.isAbstract() ||
      !buildType.is(BJsBuild.TYPE))
    {
      throw new IllegalArgumentException("BJsBuild subtype required");
    }
    
    BJsBuild build = (BJsBuild) buildType.getInstance();
    
    return new JsInfo(js, build.getId());
  }

  /**
   * Create JsInfo with no build ID - this file will always be downloaded
   * unminified.
   * 
   * @param js ORD to the JS file this JsInfo represents
   * @return new JsInfo
   */
  public static JsInfo make(BOrd js)
  {
    return new JsInfo(js, null);
  }
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * @return an ORD to the non-minified JavaScript file. 
   */
  public BOrd getJs()
  {
    return js;
  }
  
  /**
   * @return the RequireJS Id for the JavaScript resource.
   */
  public String getJsId()
  {
    return js.isNull() ? "" : toRequireJsId(js); 
  }

  /**
   * @return ID for the JsBuild in which this JS file resides, or null if this JS file is not
   * associated with a JsBuild
   */
  public String getBuildId()
  {
    return buildId;
  }

  /**
   * Create and return all RequireJS IDs for a js module resource build
   * @param list containing module resource builds
   * @return all RequireJS IDs
   * @since Niagara 4.13
   */
  private static Set<String> getBuiltFileIds(Collection<? extends BIWebResource> list)
  {
    Set<String> ids = new HashSet<>();
    for (BIWebResource build : list)
    {
      Collections.addAll(ids, build.getRequireJsIds());
    }
    return ids;
  }
  
  /**
   * Return the RequireJS IDs necessary to preload before loading this
   * JavaScript resource. Each dependent BJsBuild will have its webdev
   * status checked; its module IDs will be included in the resultant list
   * only if webdev is disabled.
   *
   * As of Niagara 4.13, this method is deprecated, as a flat list of dependencies will not ensure
   * that builtfiles are correctly loaded in the right order, and unnecessary network calls may
   * result. Use {@link #resolveDependencies()} instead to correctly resolve dependencies.
   *
   * @return all RequireJS IDs
   * @deprecated
   */
  @Deprecated
  public String[] getBuiltJsIds()
  {
    List<BJsBuild> builds = getBuilds();
    List<String> ids = new ArrayList<>(getBuiltFileIds(builds));

    return ids.toArray(STRINGS);
  }

  /**
   * @return a graph of all dependencies that are required to load the JS represented by this
   * JsInfo.
   * @since Niagara 4.13
   */
  public BIWebResource.DependencyGraph resolveDependencies()
  {
    return BIWebResource.resolve(getBuild()
      .map(Collections::singletonList)
      .orElse(Collections.emptyList()));
  }

  private Optional<BJsBuild> getBuild()
  {
    return BJsBuild.forId(buildId);
  }

  private List<BJsBuild> getBuilds()
  {
    List<BJsBuild> list = new ArrayList<>();
    //else logger.warn
    getBuild().ifPresent(bJsBuild -> addBuilds(list, bJsBuild));
    return list;
  }

  private static void addBuilds(List<BJsBuild> list, BJsBuild build)
  {
    if (!build.isWebDevEnabled())
    {
      list.add(build);
    }

    for (BJsBuild dep : build.getDependentBuilds())
    {
      addBuilds(list, dep);
    }
  }

  /**
   * As of Niagara 4.13, this method is deprecated. The JS represented by this {@code JsInfo} may
   * not have any JS builtfiles associated with it, but may still have other dependencies to load.
   * Instead, use {@link #resolveDependencies()} and the
   * {@link BIWebResource.DependencyGraph#isEmpty() isEmpty()} method.
   *
   * @return true if built JavaScript information is available.
   * @deprecated
   */
  @Deprecated
  public boolean hasBuiltJs()
  {
    return !getBuilds().isEmpty();
  }

  /**
   * @param ord an ORD pointing to a JS or CSS file
   * @return a RequireJS AMD ID representation of that ORD
   */
  public static String toRequireJsId(BOrd ord)
  {
    String ordString = ord.toString();
    Matcher matcher = modulePathPattern.matcher(ordString);

    if (!matcher.find())
    {
      throw new BajaRuntimeException(invalidErr + ordString);
    }
    
    String path = matcher.group(1);
    String requireJsModuleId = NiagaraRequireJsMapper.getInstance().pathToRequireJsModuleId(path);

    if (cssFilePattern.matcher(ordString).find())
    {
      return "css!" + requireJsModuleId;
    }

    return requireJsModuleId;
  }
   
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private final BOrd js;
  private final String buildId;
  public static final String[] STRINGS = new String[0];
  
  private static final String invalidErr = "Invalid JavaScript ORD: ";

  private static final Pattern modulePathPattern =
    Pattern.compile("^module://(.+\\.(js|css))$", Pattern.CASE_INSENSITIVE);
  private static final Pattern cssFilePattern =
    Pattern.compile("^module://(.+)\\.(css)$");
}
