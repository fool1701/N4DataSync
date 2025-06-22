
/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import java.util.HashMap;
import javax.baja.file.FilePath;
import javax.baja.history.db.BArchiveHistoryProvider;
import javax.baja.naming.BasicQuery;
import javax.baja.naming.OrdQueryList;
import javax.baja.naming.SyntaxException;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;

import com.tridium.bql.util.BDynamicTimeRange;

/**
 * A HistoryQuery is the OrdQuery for histories.  The grammar for the body is:
 *
 * <pre>{@code
 * history    := path ["?" params]
 * path       := absolute | relative
 * params     := param ( ";" param )*
 * param      := "period=" periodId | "start=" time | "end=" time | "delta=" boolean
 *
 * absolute         := fullAbs | localDeviceAbs | folderAbs | defaultFolderAbs
 * fullAbs          := "/" deviceName "/" historyName
 * defaultFolderAbs := "///" deviceName "/" historyName
 * folderAbs        := "//" [folderNames] "//" deviceName "/" historyName
 * localDeviceAbs   := "^" historyName
 * parentNiagaraStation := "@" historyName
 *
 * relative  := historyName
 *
 * deviceName  := name
 * historyName := name
 *
 * folderNames    := folderName [ "/" folderNames ]
 * folderName     := folderNameChar (folderNameChar)*
 * folderNameChar := nameChar | specials
 * specials       := space | . | - | _ | + | ( | ) | & | ` | ' | [ | ]
 *
 * name       := nameChar (nameChar)*
 * nameChar   := (a-z) | (A-Z) | (0-9) | _
 * periodId   := today | yesterday | lastWeek | last7Days | monthToDate | lastMonth | yearToDate | lastYear
 * time       := string formatted timestamp
 *
 * }</pre>
 *
 * @author    John Sublett
 * @creation  24 Mar 2003
 * @version   $Revision: 21$ $Date: 8/18/09 4:28:34 PM EDT$
 * @since     Baja 1.0
 */
public class HistoryQuery
  extends BasicQuery
{
  public HistoryQuery(String scheme, String body)
    throws SyntaxException
  {
    super(scheme, body);
    parse(body);
  }

  public HistoryQuery(String body)
  {
    this("history", body);
  }

  /**
   * Get the substring of the body that appears after the last '?'.
   *
   * @return Returns the query string or null if no query string is present.
   */
  public String getQueryString()
  {
    return paramString;
  }

  /**
   * Parse the query body.
   */
  private void parse(String body)
    throws SyntaxException
  {
    String path;

    int p = body.indexOf('?');
    if (p == -1)
    {
      paramString = "";
      path = body;
    }
    else
    {
      paramString = body.substring(p+1);
      path = body.substring(0, p);
    }

    boolean folderAbs = path.startsWith("//");
    if (folderAbs)
    {
      if (path.startsWith("///")) // triple means DEFAULT folder
      {
        path = path.substring(2);
        historyFolderAbs = true;
        defaultFolder = true;
      }
      else
      {
        path = path.substring(1);
        int idIdx = path.lastIndexOf("//");
        historyFolderAbs = (idIdx >= 0);
        if (historyFolderAbs)
        {
          folderFilePath = new FilePath(scheme, path.substring(0, idIdx));
          path = path.substring(idIdx+1);
          if (path.lastIndexOf("/") == 0)
            path = path.substring(1);
        }
        else
          folderFilePath = new FilePath(scheme, path);
      }
    }
    filePath = new FilePath(scheme, path);

    if (((!folderAbs) || historyFolderAbs) && (filePath.depth() > 2))
      throw new SyntaxException("Path depth must be <= 2.");

    if (filePath.getBackupDepth() != 0)
      throw new SyntaxException("Backup \"..\" is not allowed.");

    boolean goodScope =
      filePath.isRelative() ||
      filePath.isLocalAbsolute() ||
      (filePath.isStationHomeAbsolute() && (filePath.depth() <= 1)) ||
      ((filePath.depth() == 1) && (filePath.nameAt(0).startsWith("@"))); // issue 8631 - also check for "@" shorthand
    if (!goodScope)
      throw new SyntaxException("Invalid history ord: " + body);

    if (paramString.length() != 0)
      params = parseParams(paramString);

    timeRange = parseTimeRange(params);
    delta = parseDelta(params);

    if (paramString.length() == 0) paramString = null;
  }

  /**
   * Parse the param string.
   */
  private HashMap<String,String> parseParams(String paramString)
  {
    HashMap<String,String> params = new HashMap<String,String>();
    while (paramString.length() != 0)
    {
      String param = null;
      int sep = paramString.indexOf(';');
      if (sep == -1)
      {
        param = paramString;
        paramString = "";
      }
      else
      {
        param = paramString.substring(0, sep);
        if (sep != paramString.length()-1)
          paramString = paramString.substring(sep+1);
        else
          paramString = "";
      }

      int eq = param.indexOf('=');
      if (eq == -1)
        continue;
      else
      {
        String paramName = param.substring(0, eq);
        String paramValue;
        if (eq == param.length()-1)
          paramValue = "";
        else
          paramValue = param.substring(eq+1);
        params.put(paramName, paramValue);
      }
    }

    return params;
  }

  /**
   * Parse the time range if specified in the parameters.
   */
  private BDynamicTimeRange parseTimeRange(HashMap<String,String> params)
    throws SyntaxException
  {
    if ((params == null) || (params.size() == 0)) return null;
    String period = params.get("period");
    if ((period != null) && !period.equalsIgnoreCase("timeRange"))
      return BDynamicTimeRange.make(period);
    else
    {
      BAbsTime start = null;
      String startSpec = params.get("start");
      if (startSpec != null)
        start = parseTime(startSpec, true);
      else
        start = BAbsTime.NULL;

      BAbsTime end = null;
      String endSpec = params.get("end");
      if (endSpec != null)
        end = parseTime(endSpec, false);
      else
        end = BAbsTime.NULL;

      return  BDynamicTimeRange.make(start, end);
    }
  }

  private BAbsTime parseTime(String timeString, boolean isStart)
    throws SyntaxException
  {
    try
    {
      return (BAbsTime)BAbsTime.DEFAULT.decodeFromString(timeString);
    }
    catch(SyntaxException e)
    {
      throw e;
    }
    catch(Exception e)
    {
      throw new SyntaxException(e);
    }
  }

  /**
   * Parse the delta boolean if specified in the parameters.
   */
  private boolean parseDelta(HashMap<String,String> params)
    throws SyntaxException
  {
    if ((params == null) || (params.size() == 0)) return false;
    boolean retVal = false;
    String deltaSpec = params.get("delta");
    if (deltaSpec != null)
    {
      try
      {
        retVal = Boolean.valueOf(deltaSpec).booleanValue();
      }
      catch (Exception e)
      {
        throw new SyntaxException(e);
      }
    }
    return retVal;
  }

  /**
   * Is this a space query?
   */
  public boolean isSpaceQuery()
  {
    if ((filePath.depth() == 0) && filePath.isRelative())
      return true;
    if ((getBody().equals("/")) || (getBody().equals("//")))
      return true;
    return false;
  }

  /**
   * Is this a device query?
   */
  public boolean isDeviceQuery()
  {
    return !isHistoryFolderQuery() && (((filePath.isStationHomeAbsolute() && (filePath.depth() == 0)) ||
                          (filePath.isLocalAbsolute() && (filePath.depth() == 1))));
  }

  /**
   * Is this a history query?
   */
  public boolean isHistoryQuery()
  {
    return !isHistoryFolderQuery() && ((filePath.isStationHomeAbsolute() && (filePath.depth() == 1)) ||
           (filePath.isLocalAbsolute() && (filePath.depth() == 2)) ||
           ((filePath.depth() == 1) && (filePath.nameAt(0).startsWith("@")))); // issue 8631 - also check for "@" shorthand
  }

  /**
   * Is this a history folder query?
   *
   * @since Niagara 3.5
   */
  public boolean isHistoryFolderQuery()
  {
    return ((folderFilePath != null) && !historyFolderAbs) || (getBody().equals("///"));
  }

  /**
   * Get the device name.
   *
   * @return Returns the name of the device that was specified
   *   in the query or null if no device was specified.
   */
  public String getDeviceName()
  {
    if (isHistoryFolderQuery()) return null;

    if (filePath.isStationHomeAbsolute())
      return "^";
    // issue 8631 - also check for "@" shorthand
    else if ((filePath.depth() == 1) && (filePath.nameAt(0).startsWith("@")))
      return "@";
    else
      return filePath.nameAt(0);
  }

  /**
   * Get the history name.
   *
   * @return Returns the name of the history that was specified
   *   in the query or null if no history was specified.
   */
  public String getHistoryName()
  {
    if (isHistoryFolderQuery()) return null;

    if (filePath.isStationHomeAbsolute())
      return filePath.nameAt(0);
    // issue 8631 - also check for "@" shorthand
    else if ((filePath.depth() == 1) && (filePath.nameAt(0).startsWith("@")))
      return filePath.nameAt(0).substring(1);
    else
      return filePath.nameAt(1);
  }

  /**
   * Get the history folder path names.
   *
   * @return Returns the String array of folder names that make up the folder path.
   *
   * @since Niagara 3.5
   */
  public String[] getHistoryFolderPath()
  {
    if (defaultFolder) return new String[] { "/" };

    if (folderFilePath != null)
      return folderFilePath.getNames();

    return null;
  }

  /**
   * Get the history id that was specified in the body.
   * If the body is not a history id, null is returned.
   */
  public BHistoryId getHistoryId()
  {
    if (isHistoryFolderQuery()) return null;

    if (filePath.isStationHomeAbsolute())
      return BHistoryId.make("^", filePath.nameAt(0));
    // issue 8631 - also check for "@" shorthand
    else if ((filePath.depth() == 1) && (filePath.nameAt(0).startsWith("@")))
      return BHistoryId.make("@", filePath.nameAt(0).substring(1));
    else if (filePath.depth() == 2)
      return BHistoryId.make(filePath.nameAt(0), filePath.nameAt(1));
    else
      throw new BajaRuntimeException
        ("Cannot get history id from an unnormalized relative history ord.");
  }

  /**
   * Does the query include a time range specification?
   */
  public boolean hasTimeRange()
  {
    return timeRange != null;
  }

  /**
   * Does the query ask for a delta log?
   */
  public boolean isDelta()
  {
    return delta;
  }

  /**
   * Get the start time for this query based on the current time.
   * The current time is necessary because the time range
   * may be based on the current time (e.g. today, yesterday, etc.).
   */
  public BAbsTime getStartTime(BAbsTime currentTime)
  {
    return timeRange.getStartTime(currentTime);
  }

  /**
   * Get the end time for this query based on the current time.
   * The current time is necessary because the time range
   * may be based on the current time (e.g. today, yesterday, etc.).
   */
  public BAbsTime getEndTime(BAbsTime currentTime)
  {
    return timeRange.getEndTime(currentTime);
  }

  /**
   * Get the names of all parameters in the parameter list.
   */
  public String[] getParameterNames()
  {
    if (params.size() == 0) return NO_NAMES;
    return params.keySet().toArray(new String[params.size()]);
  }

  /**
   * Get the parameter with the specified name.
   */
  public String getParameter(String paramName)
  {
    return params.get(paramName);
  }

  /**
   * Get the parameter with the specified name.  If the
   * parameter is not found, return the specified value as
   * the default.
   */
  public String getParameter(String paramName, String def)
  {
    String val = params.get(paramName);
    if (val == null)
      return def;
    else
      return val;
  }

  /**
   * Return false.
   */
  @Override
  public boolean isHost()
  {
    return false;
  }

  /**
   * Return false.
   */
  @Override
  public boolean isSession()
  {
    return false;
  }

  /**
   * This method is called during BOrd.normalize() to give
   * each query the ability to normalize itself.  The
   * index specifies the location of this query in the
   * parsed queries list.  This method allows OrdQueries to
   * merge or truncate relative ords.
   */
  @Override
  public void normalize(OrdQueryList list, int index)
  {
    // if two like paths are next to one another then merge
    if (list.isSameScheme(index, index+1))
    {
      HistoryQuery next = (HistoryQuery)list.get(index+1);
      if (!next.isSpaceQuery())
      {
        if((folderFilePath != null) || (next.folderFilePath != null) ||
           (defaultFolder) || (next.defaultFolder)) // Always use next
          list.merge(index, next);
        else
        {
          String params = paramString;
          String nextParams = next.getQueryString();
          if (nextParams != null) params = nextParams;
          FilePath append = next.filePath;
          String body = null;
          FilePath myPath = filePath;
          if (myPath.getBody().equals(""))
          {
            String s = append.getBody();
            if ((s != null) && (s.length() > 0))
            {
              switch (s.charAt(0))
              {
                case '/': case '^': case '@': break; // issue 8631 - also check for "@" shorthand
                default : myPath = filePath.merge(new FilePath(getScheme(),"/"));
              }
            }
          }
          if (params == null)
            body = myPath.merge(append).getBody();
          else
            body = myPath.merge(append).getBody() + "?" + params;

          list.merge(index, new HistoryQuery(getScheme(), body));
        }
      }
    }

    // strip any non-sessions to my left
    list.shiftToSession(index);
  }

////////////////////////////////////////////////////////////////
// Utilities
////////////////////////////////////////////////////////////////

  /**
   * Convenience method to generate a Context instance that contains a special
   * facet (named "excludeArchiveHistoryData") to signify that archive history
   * data (e.g. history data archived to the cloud or some other archive provider)
   * should be excluded from queries for any history connections that
   * subsequently use the returned Context.
   *
   * @param cx A Context that will be used as a base for generating the result.
   *           This parameter can be null.
   * @return A Context instance generated from the given base Context argument
   * enhanced with one additional special facet called
   * "excludeArchiveHistoryData" that has a boolean value of true.
   *
   * @since Niagara 4.11
   */
  public static Context makeExcludeArchiveDataContext(Context cx)
  {
    return cx == null? EXCLUDE_ARCHIVE_DATA_FACET :new BasicContext(cx, EXCLUDE_ARCHIVE_DATA_FACET);
  }

  /**
   * Convenience method that checks the given Context argument for the presence
   * of a special "excludeArchiveHistoryData" facet, and if found, returns its
   * boolean value.  This value is used to signify that archive history data
   * (e.g. history data archived to the cloud or some other archive provider)
   * should be excluded from queries against a Niagara history.
   *
   * @param cx The Context instance to check
   * @return true if the "excludeArchiveHistoryData" facet is present on the
   * Context argument and it has a boolean true value.  Otherwise false is
   * returned.
   *
   * @since Niagara 4.11
   */
  public static boolean excludeArchiveData(Context cx)
  {
    return cx != null && BBoolean.TRUE.equals(cx.getFacet(EXCLUDE_ARCHIVE_DATA_KEY));
  }

  /**
   * Convenience method to generate a Context instance that contains a special
   * facet (named "archiveHistoryQueryLimit") to specify a positive limit on the
   * maximum number of history records that should be read from an archive
   * history provider (see {@link BArchiveHistoryProvider}) for queries from any
   * history connections that subsequently use the returned Context. If a non-
   * positive integer value is passed in for the archiveLimit argument, then
   * the original context argument will be returned. The archive history
   * provider has its own configurable limit (see
   * {@link BArchiveHistoryProvider#getMaxArchiveResultsPerQuery()}), so any
   * limit specified in this Context will only be recognized by the framework if
   * it is lower than the configured limit on the archive history provider
   * itself, otherwise it will be disregarded and the limit configured on the
   * archive history provider will take precedence.
   *
   * @param context A Context that will be used as a base for generating the result.
   *           This parameter can be null.
   * @param archiveLimit Specifies a positive limit on the maximum number of
   *                     history records that will be read from an archive history
   *                     provider for any queries submitted to it. If this
   *                     argument is less than or equal to zero, then it will
   *                     be disregarded and the original context argument will
   *                     be returned (unaltered).
   * @return A Context instance generated from the given base Context argument
   * enhanced with one additional special facet called "archiveHistoryQueryLimit"
   * that has an integer value set to the archiveLimit argument.
   *
   * @since Niagara 4.11
   */
  public static Context makeArchiveQueryLimitContext(Context context, int archiveLimit)
  {
    if (archiveLimit < 1)
    {
      return context;
    }

    BFacets facets = BFacets.make(ARCHIVE_QUERY_LIMIT_KEY, archiveLimit);
    return context == null ? facets : new BasicContext(context, facets);
  }

  /**
   * Convenience method that checks the given Context argument for the presence
   * of a special "archiveHistoryQueryLimit" facet, and if found, returns its
   * integer value as long as its greater than zero. If not found or less than
   * or equal to zero, the defaultArchiveLimit argument is returned instead.
   * This value is used to specify a limit on the maximum number of history
   * records that should be read from an archive history provider (see
   * {@link BArchiveHistoryProvider}) at query time. The archive history
   * provider has its own configurable limit (see
   * {@link BArchiveHistoryProvider#getMaxArchiveResultsPerQuery()}), so any
   * limit specified in the Context will only be recognized by the framework if
   * it is lower than the configured limit on the archive history provider
   * itself (the limit configured on the archive history provider takes
   * precedence).
   *
   * @param cx The Context instance to check
   * @return the integer value specified in the Context when the
   * "archiveHistoryQueryLimit" facet is present and has a positive integer
   * value. Otherwise the defaultArchiveLimit argument is returned.
   *
   * @since Niagara 4.11
   */
  public static int getArchiveQueryLimit(Context cx, int defaultArchiveLimit)
  {
    if (cx != null)
    {
      BFacets facets = cx.getFacets();
      if (facets != null)
      {
        int cxLimit = facets.geti(ARCHIVE_QUERY_LIMIT_KEY, -1);
        if (cxLimit > 0)
        {
          return cxLimit;
        }
      }
    }
    return defaultArchiveLimit;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final String[] NO_NAMES = new String[0];
  private static final String EXCLUDE_ARCHIVE_DATA_KEY = "excludeArchiveHistoryData";
  private static final BFacets EXCLUDE_ARCHIVE_DATA_FACET = BFacets.make(EXCLUDE_ARCHIVE_DATA_KEY, true);
  private static final String ARCHIVE_QUERY_LIMIT_KEY = "archiveHistoryQueryLimit";

  FilePath filePath;
  private HashMap<String,String> params;
  private String paramString;
  private BDynamicTimeRange timeRange;
  private boolean delta = false;
  FilePath folderFilePath = null;
  boolean historyFolderAbs = false;
  boolean defaultFolder = false;
}
