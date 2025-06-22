/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Eric Anderson
 * @creation 6/10/2017
 * @since Niagara 4.3U1
 */
public final class OrdUtil
{
  // private constructor
  private OrdUtil()
  {
  }

  /**
   * Slot and file path ord queries may contain "../" to do relative traversal up the tree.  If
   * there is more than one backup, the ord will contain "/../", which will be replaced by the
   * browser within a URL by removing other sections.  For example, https://127.0.0.1/a/b/c/d/../e/f
   * is converted to https://127.0.0.1/a/b/c/e/f and https://127.0.0.1/a/b/c/d/../../e/f is
   * converted to https://127.0.0.1/a/b/c/f.  This will result in unintended behavior in subsequent
   * ord resolution with that URL.  Therefore, all but the last "../" is replaced with {@code
   * "<schema>:..|"}.  This function is replicated in BajaScript by Ord.replaceBackups.
   *
   * @param ord ord that is searched for "../" backups
   * @return the original ord if no changes are necessary or an updated ord with the necessary
   * replacements
   */
  public static BOrd replaceBackups(BOrd ord)
  {
    OrdQuery[] queries = ord.parse();

    // This list is only instantiated if any replacements are necessary.
    ArrayList<OrdQuery> newQueries = null;
    for (int i = 0; i < queries.length; ++i)
    {
      int backupDepth = queries[i] instanceof Path ? ((Path)queries[i]).getBackupDepth() : 0;
      if (backupDepth > 1)
      {
        if (newQueries == null)
        {
          // Initialize the array list with at least as much room for all of the original queries
          // plus the additional ones need for the current query's backups.  Other queries may also
          // require additional queries for backups but this is at least a good start.
          newQueries = new ArrayList<>(queries.length + backupDepth - 1);
          // Catch the newQueries list up to the current OrdQuery; previous ones did not have a
          // backup depth > 1
          newQueries.addAll(Arrays.asList(queries).subList(0, i));
        }

        Path path = (Path)queries[i];

        // Replace all but one backup with a new path with ".." as the body
        for (int j = 0; j < backupDepth - 1; ++j)
          newQueries.add(path.makePath(".."));

        // Remove all the "/.." from the body of the original OrdQuery.  For example,
        // slot:../../../abc/def becomes slot:../abc/def
        String newBody = queries[i].getBody().replace("/..", "");
        newQueries.add(path.makePath(newBody));
      }
      else if (newQueries != null)
      {
        // A previous replacement has been made so continue to append remaining ord queries.
        newQueries.add(queries[i]);
      }
    }

    // Remake the ord with the new set of ord queries that contains backup replacements.
    if (newQueries != null)
      ord = BOrd.make(newQueries.toArray(new OrdQuery[newQueries.size()]));

    return ord;
  }

  /**
   * Retrieve the ViewQuery for an ord.
   * @param ord The ord to obtain the ViewQuery from.
   * @return the ViewQuery from the ord.
   * @since Niagara 4.12
   */
  public static ViewQuery getViewQuery(BOrd ord)
  {
    if(ord.isNull())
    {
      return null;
    }

    return getViewQuery(getNormalizedParse(ord));
  }

  /**
   * Retrieve the normalized Ord without the ViewQuery.
   *
   * @param ord The ord to remove the ViewQuery from.
   * @return The normalized ord without the View Query.
   * @since Niagara 4.12
   */
  public static BOrd getOrdWithoutViewQuery(BOrd ord)
  {
    if(ord.isNull())
    {
      return BOrd.NULL;
    }

    return getOrdWithoutViewQuery(getNormalizedParse(ord));
  }

  /**
   * Obtain the Ord from an OrdTarget without certain View Query parameters.
   * Any ViewQuery parameter keys left will be alphabetised to help with caching comparisons.
   *
   * @param target This is the OrdTarget to remove View Query parameters from.
   * @param keysToRemove The keys to remove from the ViewQuery.
   * @param removeViewId Set this to true to also remove the viewId.
   * @return The ord without the desired View Query parameters.
   * @since Niagara 4.12
   */
  public static BOrd getOrdWithoutViewQueryParameters(OrdTarget target, List<String> keysToRemove, boolean removeViewId)
  {
    return getOrdWithoutViewQueryParameters(target.getOrdWithoutViewQuery(), target.getViewQuery(), keysToRemove, removeViewId);
  }

  /*
   * Obtain the normalized Ord without certain View Query parameters.
   * Any ViewQuery parameter keys still present will be alphabetised to help with caching comparisons.
   *
   * @param ord This is the Ord to remove View Query parameters from.
   * @param keysToRemove The keys to remove from the ViewQuery.
   * @param removeViewId Set this to true to also remove the viewId.
   * @return The ord without the desired View Query parameters.
   * @since Niagara 4.12
   */
  public static BOrd getOrdWithoutViewQueryParameters(BOrd ord, List<String> keysToRemove, boolean removeViewId)
  {
    if(ord.isNull())
    {
      return BOrd.NULL;
    }

    OrdQuery[] queries = getNormalizedParse(ord);
    return getOrdWithoutViewQueryParameters(getOrdWithoutViewQuery(queries), getViewQuery(queries), keysToRemove, removeViewId);
  }

  private static BOrd getOrdWithoutViewQueryParameters(BOrd ordWithoutViewQuery, ViewQuery viewQuery, List<String> keysToRemove, boolean removeViewId)
  {
    if (viewQuery == null)
    {
      return ordWithoutViewQuery;
    }

    StringBuilder b = new StringBuilder();


    viewQuery.getParameters()
      .entrySet()
      .stream()
      .sorted(Map.Entry.comparingByKey())
      .forEachOrdered(entry -> {
        String key = entry.getKey();
        String value = entry.getValue();
        if (keysToRemove.contains(key))
        {
          return;
        }

        if (b.length() == 0)
        {
          b.append('?');
        }
        else
        {
          b.append(';');
        }
        b.append(key).append('=').append(value);
      });
    String viewId = viewQuery.getViewId();
    if (viewId == null || removeViewId)
    {
      viewId = "";
    }
    String body = viewId + b.toString();
    if (body.isEmpty())
    {
      return ordWithoutViewQuery;
    }
    ViewQuery newViewQuery = new ViewQuery(body);
    return BOrd.make(ordWithoutViewQuery, newViewQuery);
  }

  private static OrdQuery[] getNormalizedParse(BOrd ord)
  {
    return ord.normalize().parse();
  }

  private static ViewQuery getViewQuery(OrdQuery[] queries)
  {
    // view query is always the last query
    int n = queries.length - 1;
    if (n >= 0 && queries[n] instanceof ViewQuery)
    {
      return (ViewQuery) queries[n];
    }

    return null;
  }

  private static BOrd getOrdWithoutViewQuery(OrdQuery[] queries)
  {
    if (getViewQuery(queries) == null)
    {
      return BOrd.make(queries);
    }
    else
    {
      return BOrd.make(queries, 0, queries.length - 1);
    }
  }
}
