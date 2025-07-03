/*
 * Copyright 2022 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.web.js;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.baja.agent.BIAgent;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.json.JSONArray;
import com.tridium.web.graph.CoffmanGrahamDagResolver;

/**
 * <p>
 *   {@code BIWebResource} represents a collection of one or more resource files that can be used on
 *   the web.
 * </p>
 * <p>
 *   These can be configured to declare dependencies between files: for instance, the HTML views
 *   implemented in a {@code .js} file can depend upon CSS implemented in a separate {@code .css}
 *   file. This dependency can be implemented by having a {@link BJsBuild} depend on a
 *   {@link BCssResource}.
 * </p>
 * <p>
 *   By creating classes that implement this interface, you register the existence of these web
 *   resource files with the Niagara Framework. When using the Niagara web interface (BajaScript,
 *   field editors, etc.), the framework can then ensure the correct dependencies are loaded in the
 *   correct order.
 * </p>
 * <p>
 *   Please see the User Interface section of Doc Developer for more details.
 * </p>
 *
 * @author Anita Padman on 5/5/2022
 * @since Niagara 4.13
 */
@NiagaraType
public interface BIWebResource extends BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.js.BIWebResource(2979906276)1.0$ @*/
/* Generated Mon May 09 16:52:47 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIWebResource.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * @return ORDs to the files represented by this resource.
   */
  BOrd[] getFiles();

  /**
   * @return the files represented by this resource as an array of RequireJS module IDs
   */
  default String[] getRequireJsIds()
  {
    BOrd[] files = getFiles();
    String[] ids = new String[files.length];
    for (int i = 0, len = files.length; i < len; ++i)
    {
      ids[i] = JsInfo.toRequireJsId(files[i]);
    }
    return ids;
  }

  /**
   * @return the set of this web resource's direct dependencies.
   */
  default Set<BIWebResource> getDependencies()
  {
    return Collections.emptySet();
  }

  static DependencyGraph resolve(Collection<? extends BIWebResource> resources)
  {
    return new DependencyGraph(resources);
  }

  /**
   * A dependency graph for a collection of web resources.
   */
  class DependencyGraph
  {
    private DependencyGraph(Collection<? extends BIWebResource> resources)
    {
      this.solvedGraph = CoffmanGrahamDagResolver.resolve(resources, BIWebResource::getDependencies);
    }

    /**
     * @return true if there are no resources to resolve
     */
    public boolean isEmpty()
    {
      return solvedGraph.isEmpty();
    }

    /**
     * Return a list of sets of dependencies, arranged in such a way that all the dependencies in
     * each layer (starting with index 0) can be resolved concurrently without any unsatisfied
     * dependencies. These will be filtered using the standard {@link BIWebResource} filter
     * behavior: if it is a {@link BJsBuild} and WebDev is enabled for that build, it will be
     * omitted.
     *
     * @return solved dependency graph
     */
    public List<Set<BIWebResource>> solve()
    {
      return solve(STANDARD_WEBDEV_FILTER);
    }

    /**
     * @return the fully solved graph, but with individual resources filtered out. That is:
     * filtering out one resource will _not_ filter out its dependencies.
     * @see #solve()
     */
    public List<Set<BIWebResource>> solve(Predicate<BIWebResource> filter)
    {
      return solvedGraph.stream()
        .map((webResources) -> filterSet(webResources, filter))
        .filter(s -> !s.isEmpty())
        .collect(Collectors.toList());
    }

    /**
     * Convert to JSON. The standard BIWebResource filtering behavior, where any {@link BJsBuild}s
     * with WebDev enabled are omitted, will be applied.
     * @return a JSON representation of the dependencies (an array of arrays of strings), with each
     * resource converted to the RequireJS IDs that comprise it.
     */
    public JSONArray toJSON()
    {
      return toJSON(STANDARD_WEBDEV_FILTER);
    }

    /**
     * @param filter {@code BIWebResource}s that do not satisfy this filter will not be included
     * in the JSON
     * @return a JSON representation of the dependencies (an array of arrays of strings), with each
     * resource converted to the RequireJS IDs that comprise it.
     */
    private JSONArray toJSON(Predicate<BIWebResource> filter)
    {
      JSONArray arr = new JSONArray();
      for (Set<BIWebResource> layer : solvedGraph)
      {
        JSONArray deps = null;
        for (BIWebResource resource : layer)
        {
          if (filter.test(resource))
          {
            if (deps == null) { deps = new JSONArray(); }
            deps.putAll(resource.getRequireJsIds());
          }
        }
        if (deps != null) { arr.put(deps); }
      }
      return arr;
    }

    private static <T> Set<T> filterSet(Set<T> set, Predicate<T> predicate)
    {
      return set.stream().filter(predicate).collect(Collectors.toCollection(HashSet::new));
    }

    private static final Predicate<BIWebResource> STANDARD_WEBDEV_FILTER = (resource) -> {
      if (resource instanceof BJsBuild)
      {
        return !((BJsBuild) resource).isWebDevEnabled();
      }
      return true;
    };

    private final List<Set<BIWebResource>> solvedGraph;
  }
}
