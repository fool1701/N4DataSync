/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.logging.Level;
import javax.bajax.analytics.data.BCombination;
import javax.bajax.analytics.data.Combination;
import javax.bajax.analytics.time.BAnalyticTimeRange;
import javax.bajax.analytics.time.BInterval;
import javax.bajax.analytics.time.Interval;
import javax.bajax.analytics.time.TimeRange;

import javax.baja.history.BRolloverValue;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.SlotPath;
import javax.baja.nav.BINavNode;
import javax.baja.status.BStatus;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.units.BUnit;
import javax.baja.user.BUser;
import javax.baja.user.BUserService;
import javax.baja.util.BDaysOfWeekBits;
import javax.baja.util.BFormat;

import com.tridiumx.analytics.Analytics;
import com.tridiumx.analytics.BAnalyticService;
import com.tridiumx.analytics.algorithm.missingdata.BAggregationStrategy;
import com.tridiumx.analytics.algorithm.missingdata.BInterpolationAlgorithm;
import com.tridiumx.analytics.algorithm.missingdata.BMissingDataStrategy;
import com.tridiumx.analytics.algorithm.outlier.BDeltaValues;
import com.tridiumx.analytics.algorithm.outlier.BRawDataFilter;
import com.tridiumx.analytics.data.AnalyticDataPolicy;
import com.tridiumx.analytics.data.BAnalyticDataDefinition;
import com.tridiumx.analytics.util.NeqlPredicate;
import com.tridiumx.analytics.util.Strings;
import com.tridiumx.analytics.util.Utils;

/**
 * Encapsulates the details about a request for the Niagara Analytics
 * Framework.
 *
 * @author Aaron Hansen
 * @see Analytics
 */
public class AnalyticContext
  implements AnalyticConstants, Context
{
/////////////////////////////////////////////////////////////////
  // Methods - Constructors
  /////////////////////////////////////////////////////////////////

  public AnalyticContext()
  {
  }

  /////////////////////////////////////////////////////////////////


  public AnalyticContext(Context cx)
  {
    this.base = cx;

  }


  public Context getBase()
  {
    return base;
  }

  public BObject getFacet(String name)
  {
    return getFacets().getFacet(name);
  }

  /**
   * Returns the facets of the data policy and the overriding unit present.
   */
  public BFacets getFacets()
  {
    if (cacheFacets == null)
    {
      if (unit == null)
      { return getDataPolicy().getFacets(); }
      cacheFacets = BFacets.make(
        getDataPolicy().getFacets(), BFacets.UNITS, unit);
      cacheFacets = BFacets
        .make(cacheFacets, BFacets.make("seriesName", getSeriesName()));
    }
    return cacheFacets;
  }

  /**
   * The langague of the base context, or Sys.language().
   */
  public String getLanguage()
  {
    if (base != null) { return base.getLanguage(); }
    return Sys.getLanguage();
  }

  /**
   * Return the user of the base context, or admin.
   */
  public BUser getUser()
  {
    if (getBase() == null)
    { return BUserService.getService().getAdmin(); }
    BUser ret = getBase().getUser();
    if (ret == null)
    { ret = BUserService.getService().getAdmin(); }
    return ret;
  }


  /////////////////////////////////////////////////////////////////
  // Prophet methods - alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /**
   * Aggregation defines how to combine mutiple values from multiple
   * data sources.  If an aggregation was specified in the request, it is
   * returned, otherwise if force is true, a value is determined using the
   * data policy and returned.
   */
  public Combination getAggregation(boolean force)
  {
    if ((aggregation == null) && force)
    { return getDataPolicy().getDefaultAggregation(); }
    return aggregation;
  }

  /**
   * The data desired in the request.
   */
  public Id getData()
  {
    return data;
  }

  /**
   * This predicate helps filter sources used for processing the request.
   */
  public Predicate<Entity> getDataFilter()
  {
    return dataFilter;
  }

  /**
   * A convenience method for getting the data policy for the requested data.
   *
   * @return Never null.
   */
  public AnalyticDataPolicy getDataPolicy()
  {
    if (cachedDataPolicy == null)
    {
      cachedDataPolicy =
        BAnalyticService.getInstance().getDataPolicy(getData(), true);
    }
    return cachedDataPolicy;
  }

  /**
   * Creates a unique identifier for the request, for caching purposes.
   */
  public String makeRequestIdentifier(String prefix)
  {
    StringBuilder buf = new StringBuilder(prefix);
    //data
    if (getData() != null)
    { buf.append(getData().hashCode()); }
    else
    { buf.append("0"); }
    //tags
    buf.append("_");
    //time range
    buf.append("_");
    if (getTimeRange(false) != null) ///////////////////Check this false for force
    { buf.append(getTimeRange(false).hashCode()); }
    else
    { buf.append("0"); }
    //agg
    buf.append("_");
    if (getAggregation(false) != null)
    {
      buf.append(getAggregation(false)
        .hashCode()); ///////////////////Check this false for force
    }
    else
    { buf.append("0"); }
    //ivl
    buf.append("_");
    if (getInterval(false) != null)
    { buf.append(getInterval(false).hashCode()); }
    else
    { buf.append("0"); }
    buf.append("_");
    //roll
    if (getRollup(false) != null)
    { buf.append(getRollup(false).hashCode()); }
    else
    { buf.append("0"); }
    buf.append("_");
    //tz
    if (getTimeZone() != null)
    { buf.append(getTimeZone().getID().hashCode()); }
    else
    { buf.append("0"); }
    return SlotPath.escape(buf.toString());
  }

  /**
   * Uses the time range and reference time to calculate the end time
   * of the time range.
   *
   * @return The end time, or -1 if there is no end.
   */
  public long getEndTime()
  {
    return getTimeRange(true).getEnd(System.currentTimeMillis());
  }

  /**
   * Intervals determine the time between values in a trend (time servies).
   * If a value was provided in the request, it is returned, otherwise
   * if force is true, a value is auto calculated based on the time range
   * and returned.
   */
  public Interval getInterval(boolean force)
  {
    if ((interval == null) && force)
    {
      if (cachedInterval == null)
      { cachedInterval = computeInterval(); }
      return cachedInterval;
    }
    return interval;
  }

  /**
   * When true, delta values are assigned to the leading timestamp, which
   * is the default behavior.
   */
  public boolean getLeadingDeltas()
  {
    if (leadingDeltas == null) { return true; }
    return leadingDeltas.getBoolean();
  }

  /**
   * The base node of the request.
   */
  public BINavNode getNode()
  {
    return node;
  }

  /**
   * Ord target for the node, can be used for checking permissions, but
   * only on the station side.
   */
  public OrdTarget getNodeTarget()
  {
    if (navTarget == null)
    {
      navTarget = getNode().getNavOrd().resolve(Sys.getStation(), this);
    }
    return navTarget;
  }

  /**
   * When true, the timestamps of all data involved in process the request
   * are converted to the time zone returned getTimeZone().  This means
   * their time fields, such as date, hour and second are preserved.  The
   * raw time in millis will be modified.
   */
  public boolean getNormalizeTime()
  {
    if (normalizeTime == null) { return true; }
    return normalizeTime.getBoolean();
  }

  /**
   * Rollup defines how to combine multiple values in an interval.
   * If value was provided in the request, it is returned, otherwise
   * if force is true, a value is determined using the data policy
   * and returned.
   */
  public Combination getRollup(boolean force)
  {
    if ((rollup == null) && force)
    { return getDataPolicy().getDefaultRollup(); }
    return rollup;
  }

  /**
   * Uses the time range and reference time to calculate the start time
   * of the time range.
   *
   * @return The start time, or -1 if there is no start.
   */
  public long getStartTime()
  {
    return getTimeRange(true).getStart(System.currentTimeMillis());
  }

  /**
   * If value was provided in the request, it is returned, otherwise
   * today is returned.
   */
  public TimeRange getTimeRange(boolean force)
  {
    if ((timeRange == null) && force)
    { return BAnalyticTimeRange.today; }
    return timeRange;
  }

  /**
   * The timezone to use when normalizing timestamps.
   */
  public TimeZone getTimeZone()
  {
    if (timeZone == null) { return TimeZone.getDefault(); }
    return timeZone;
  }

  /**
   * Whether or not the request can get it's results from the
   * request cache.
   */
  public boolean getUseCache()
  {
    if (useCache == null) { return true; }
    return useCache.getBoolean();
  }

  /**
   * If units were set on this object they are returned.  If not, and force
   * is true, the units of the data policy are returned or BUnit.NULL.
   */
  public BUnit getUnit(boolean force)
  {
    if (unit != null) { return unit; }
    if (!force) { return null; }
    BUnit ret = (BUnit) getDataPolicy().getFacets().get(BFacets.UNITS);
    if (ret != null) { return ret; }
    return BUnit.NULL;
  }

  public BUnit getInnermostUnit()
  {
    return getUnit(true);
  }

  /**
   * Creates a context which clones the settings of this, but
   * removes the base / inner context just in case it holds onto
   * resources (such as web) that need to be released.
   */
  public AnalyticContext newCopy()
  {
    return new AnalyticContext(new BasicContext(getUser()))
      .setAggregation(getAggregation(true))
      .setData(getData())
      .setDataFilter(getDataFilter())
      .setLeadingDeltas(getLeadingDeltas())
      .setInterval(getInterval(true))
      .setNode(getNode())
      .setRollup(getRollup(true))
      .setTimeRange(getTimeRange(true))
      .setTimeZone(getTimeZone())
      .setNormalizeTime(getNormalizeTime())
      .setUnit(getUnit(false))
      .setUseCache(getUseCache())
      .setDowInclusion(getDowInclusion().encodeToString())
      .setSeriesName(this.seriesName)
      .setHistory(getHistory().getBoolean())
      .setHisTotEnabled(getHisTotEnabled())
      .setMissingDataStrategy(getMissingDataStrategy(false))
      .setStatusFilter(getStatusFilter().toString())
      .setRawDataFilter(getRawDataFilter())
      .setIsBaseline(isBaseline());
  }

  /**
   * Sets the request aggregation and returns this.
   */
  public AnalyticContext setAggregation(Combination arg)
  {
    toString = null;
    this.aggregation = arg;
    return this;
  }

  /**
   * Sets the desired data and returns this.
   */
  public AnalyticContext setData(Id arg)
  {
    toString = null;
    this.data = arg;
    this.cachedDataPolicy = null;
    return this;
  }

  /**
   * Sets the node filter and returns this.
   */
  public AnalyticContext setDataFilter(Predicate<Entity> arg)
  {
    toString = null;
    this.dataFilter = arg;
    return this;
  }

  /**
   * A convenience that create the a predicate from the neql string.
   */
  public AnalyticContext setDataFilter(String arg)
  {
    if ((arg == null) || (arg.length() == 0))
    { return setDataFilter((Predicate<Entity>) null); }
    return setDataFilter(new NeqlPredicate(arg));
  }

  /**
   * Sets the request interval and returns this.
   */
  public AnalyticContext setInterval(Interval arg)
  {
    toString = null;
    cachedInterval = null;
    this.interval = arg;
    return this;
  }

  /**
   * Set the node of the target and returns this.
   */
  public AnalyticContext setNode(BINavNode arg)
  {
    toString = null;
    this.node = arg;
    this.navTarget = null;
    return this;
  }

  /**
   * Sets whether delta values are assigned to the leading (true) or
   * trailing (false) timestamp.  True by default.
   */
  public AnalyticContext setLeadingDeltas(boolean arg)
  {
    toString = null;
    leadingDeltas = BBoolean.make(arg);
    return this;
  }

  /**
   * Sets whether or not to normalize timestamps and returns this.
   */
  public AnalyticContext setNormalizeTime(boolean arg)
  {
    toString = null;
    normalizeTime = BBoolean.make(arg);
    return this;
  }

  /**
   * Sets the request rollup and returns this.
   */
  public AnalyticContext setRollup(Combination arg)
  {
    toString = null;
    this.rollup = arg;
    return this;
  }

  /**
   * Sets the request time range and returns this.
   */
  public AnalyticContext setTimeRange(TimeRange arg)
  {
    toString = null;
    this.timeRange = arg;
    return this;
  }

  /**
   * Set the timezone for normalizing timestamps and returns this.
   */
  public AnalyticContext setTimeZone(TimeZone arg)
  {
    toString = null;
    this.timeZone = arg;
    return this;
  }

  /**
   * Used to override the units of the data policy.  The data policy
   * units must be null or convertible to these.
   */
  public AnalyticContext setUnit(BUnit arg)
  {
    toString = null;
    this.unit = arg;
    cacheFacets = null;
    return this;
  }

  /**
   * Set the series name of the analytic context.
   *
   * @param seriesName
   * @return
   */
  public AnalyticContext setSeriesName(BFormat seriesName)
  {
    this.seriesName = seriesName;
    return this;
  }

  /**
   * Returns the series name.
   *
   * @return
   */
  public BString getSeriesName()
  {
    String formattedSeriesName = "";
    if (this.seriesName != null)
    {
      formattedSeriesName = this.seriesName.format(this);
      if (formattedSeriesName.contains("%err:"))
      {
        formattedSeriesName = Utils.lex("problemWithBFormatUIMessage");
        Utils.log()
          .log(Level.WARNING, Utils.lex("problemWithBFormatStationMessage"));
      }
    }
    return BString.make(formattedSeriesName);
  }

  /***
   * Returns the days of week to be excluded.
   * @return the
   */
  public BDaysOfWeekBits getDowInclusion()
  {
    return dowInclusion != null ? dowInclusion : BDaysOfWeekBits.DEFAULT;
  }

  /***
   * Set the days of week to be excluded.
   * @param dowBits
   * @return
   */
  public AnalyticContext setDowInclusion(String dowBits)
  {
    if (dowBits != null)
    {
      try
      {
        dowInclusion = (BDaysOfWeekBits) BDaysOfWeekBits.DEFAULT
          .decodeFromString(dowBits);
      }
      catch (IOException e)
      {
        dowInclusion = BDaysOfWeekBits.DEFAULT;
      }
    }
    else
    {
      dowInclusion = BDaysOfWeekBits.DEFAULT;
    }
    return this;
  }

  public AnalyticContext setDowInclusion(BDaysOfWeekBits dowBits)
  {
    dowInclusion = dowBits;
    return this;
  }


  public BBoolean getHistory()
  {
    return history != null ? history : BBoolean.TRUE;
  }


  public AnalyticContext setHistory(boolean history)
  {
    this.history = BBoolean.make(history);
    return this;
  }

  public AnalyticContext setHisTotEnabled(boolean status)
  {
    this.hisTotEnabled = BBoolean.make(status);
    return this;
  }

  public boolean getHisTotEnabled()
  {
    if (this.hisTotEnabled == null)
    {
      return true;
    }
    return this.hisTotEnabled.getBoolean();
  }

  public AnalyticContext setMissingDataStrategy(BMissingDataStrategy strategy)
  {
    this.mdhStrategy = strategy;
    return this;
  }

  /**
   * Get the missing data strategy that is set in context (From UI or any other client
   * that creates the context)
   * If it is not set, retrieve it from the data definition.
   * If it is not set in data definition, retrieve it from analytic service.
   *
   * @param force
   * @return
   */
  public BMissingDataStrategy getMissingDataStrategy(boolean force)
  {
    if (mdhStrategy != null && mdhStrategy.getEnabled()) { return mdhStrategy; }
    if (!force) { return null; }
    BMissingDataStrategy strategy = BMissingDataStrategy.DEFAULT;
    AnalyticDataPolicy policy = getDataPolicy();
    if (policy != null && policy instanceof BAnalyticDataDefinition)
    {
      BAnalyticDataDefinition dp = (BAnalyticDataDefinition) policy;
      strategy = dp.getMissingDataStrategy();
    }
    if (strategy != null && strategy.getEnabled())
    {
      return strategy;
    }
    strategy = BAnalyticService.getInstance().getMissingDataStrategy();
    if (strategy != null && strategy.getEnabled()) { return strategy; }
    return BMissingDataStrategy.DEFAULT;
  }

  /**
   * Function is used to get the status filter from Analytic Data Definition.
   * If data definition is not defined for the point then 0 which is {ok} status is returned.{ok} is an all pass filter for the StatusFilterTrend
   * allowing all the records to pass through the filter.
   * If data definition is defined with status filter, any record matching a combination of the statuses will be filtered out.
   * For example-
   * If filter is {alarm,fault} records with status as {alarm}, {fault} and {alarm,fault} will be filtered out.
   * getStatusFilter() is also used in the StatusFilterTrend to get the status filter from the context.
   *
   * @returns BStatus statuses set in the data definition property sheet.
   * @since Niagara 4.8
   */
  public BStatus getStatusFilter()
  {
    AnalyticDataPolicy policy = getDataPolicy();
    if (policy instanceof BAnalyticDataDefinition)
    {
      BAnalyticDataDefinition dp = (BAnalyticDataDefinition) policy;
      statusFilter = dp.getOutlier().getStatus();
      return statusFilter;
    }
    return BStatus.ok;
  }

  /**
   * Function is used to set the status filter by decoding the string passed as status.
   *
   * @param status string which can be decoded as a status
   * @return returns this with the filter set on the context.
   * @since Niagara 4.8
   */
  public AnalyticContext setStatusFilter(String status)
  {
    if (status != null)
    {
      try
      {
        statusFilter = (BStatus) BStatus.DEFAULT.decodeFromString(status);
      }
      catch (IOException e)
      {
        statusFilter = BStatus.DEFAULT;
      }
    }
    else
    {
      statusFilter = BStatus.DEFAULT;
    }
    return this;
  }

  /**
   * Function is used to get the raw data filter from Analytic Data Definition.
   * If data definition is not defined for the point then null is returned.
   *
   * @returns BRawDataFilter/null raw data filter set in the data definition.
   * @since Niagara 4.9
   */
  public BRawDataFilter getRawDataFilter()
  {
    AnalyticDataPolicy policy = getDataPolicy();
    if (policy instanceof BAnalyticDataDefinition)
    {
      BAnalyticDataDefinition dp = (BAnalyticDataDefinition) policy;
      if (!dp.getOutlier().getRawDataFilter().getHighLimit().getUnspecified()|| !dp
        .getOutlier().getRawDataFilter().getLowLimit().getUnspecified())
      {
        rawDataFilter = dp.getOutlier().getRawDataFilter();
        return rawDataFilter;
      }
      return null;
    }
    return null;
  }

  /**
   * Function is used to get the raw data filter from Analytic Data Definition.
   * If data definition is not defined for the point then null is returned.
   *
   * @returns BDeltaValues/null delta Values set in the data definition.
   * @since Niagara 4.9
   */
  public BDeltaValues getDeltaValues()
  {
    AnalyticDataPolicy policy = getDataPolicy();
    if (policy instanceof BAnalyticDataDefinition)
    {
      BAnalyticDataDefinition dp = (BAnalyticDataDefinition) policy;
      if (!dp.getOutlier().getDeltaValues().getHighLimit()
        .getUnspecified() || !dp
        .getOutlier().getDeltaValues().getLowLimit().getUnspecified())
      {
        deltaValues = dp.getOutlier().getDeltaValues();
        return deltaValues;
      }
      return null;
    }
    return null;
  }

  /**
   * Function sets the Raw Data Filter
   * @param rawDataFilter
   * @return
   */
  public AnalyticContext setRawDataFilter(BRawDataFilter rawDataFilter)
  {
    this.rawDataFilter = rawDataFilter;
    return this;
  }

  //////////////////////////////////////////////////////////////////

  /**
   * Whether or not the request can get it's results from the
   * request cache.
   */
  public AnalyticContext setUseCache(boolean arg)
  {
    useCache = BBoolean.make(arg);
    return this;
  }

  /**
   * The default implementation calls BComponent.lease on components.
   * Other implementations might want to use their own subscriber.
   */
  public AnalyticContext subscribe(BINavNode comp)
  {
    if (comp instanceof BComponent)
    {
      ((BComponent) comp).lease();
    }
    return this;
  }

  /**
   * Query string the internal state of the context, URL encoded.
   * Other requests with the same options will produce the same string.
   * Can be used to match identical requests for caching purposes.
   */
  public String toString()
  {
    if (toString != null) { return toString; }
    Map<String, String> map = new TreeMap<String, String>();

    map.put("aggregation", getAggregation(true).getTag());
    map.put("data", getData().toString());
    map.put("interval", getInterval(true).getTag());
    if (getNode() != null && getNode().getNavOrd() != null)
    {
      map.put("node", getNode().getNavOrd().toString(null));
    }
    map.put("rollup", getRollup(true).getTag());
    map.put("timeRange", getTimeRange(true).toString());
    map.put("timeZone", getTimeZone().getID());
    map.put("user", getUser().getUsername());
    map.put("seriesName", getSeriesName().toString());
    map.put("dow", getDowInclusion().encodeToString());
    if (dataFilter != null)
    { map.put("dataFilter", getDataFilter().toString()); }
    if (!getLeadingDeltas())
    { map.put("leadingDeltas", "false"); }
    if (!getNormalizeTime())
    { map.put("normalizeTime", "false"); }
    if (getUnit(false) != null)
    { map.put("unit", getUnit(false).getUnitName()); }
    if (!getUseCache())
    { map.put("useCache", "false"); }
    if (!getHisTotEnabled())
    {
      map.put("hisTotEnabled", "false");
    }
    BMissingDataStrategy strategy = getMissingDataStrategy(true);
    if (strategy != null && strategy.getEnabled())
    {
      map.put("mdStrategy", strategy.toString());
    }
    if (getStatusFilter() != null)
    {
      map.put("statusFilter", getStatusFilter().toString());
    }

    BRawDataFilter rawDataFilter = getRawDataFilter();
    if (rawDataFilter!=null &&
      (!rawDataFilter.getHighLimit().getUnspecified()|| !rawDataFilter.getLowLimit().getUnspecified()))
    {
      map.put("rawDataFilter", getRawDataFilter().toString());
    }

    return toString = Strings.encodeQueryString(map);
  }

  /**
   * Makes an analytics context for the given arguments and context.
   */
  public static AnalyticContext make(Map<String, String> args, Context base)
  {
    return AnalyticContext.make(args, base, Optional.empty());
  }

  public static AnalyticContext make(Map<String, String> args, Context base, Optional<AnalyticContext> baseAnalyticContext)
  {
    AnalyticContext cx;
    if (baseAnalyticContext.isPresent())
    {
      cx = baseAnalyticContext.get().newCopy();
    }
    else
    {
      cx = new AnalyticContext(base);
    }

    return updateAnalyticContext(args, cx);
  }

  public static AnalyticContext updateAnalyticContext(Map<String, String> args, AnalyticContext cx)
  {
    String str = args.get("aggregation");
    // Analytics trend request from charts use "rainge" for "range", applied a decoding for that.
    if (str != null)
    {
      cx.setAggregation(BCombination
        .make(str.equalsIgnoreCase("rainge") ? "range" : str));
    }
    str = args.get("data");
    if (str != null) { cx.setData(Id.newId(str)); }
    else
    {
      str = args.get("multiOrd");
      if (str != null)
      {
        cx.setData(Id.newId(AnalyticContext.DEFAULT_DATA_TAG));
      }
    }
    str = args.get("dataFilter");
    if (str != null) { cx.setDataFilter(str); }
    str = args.get("interval");
    if (str != null) { cx.setInterval(BInterval.make(str)); }
    str = args.get("leadingDeltas");
    if (str != null) { cx.leadingDeltas = BBoolean.make(str); }
    str = args.get("node");
    if (str != null) { cx.setNode(Utils.resolveNode(str, Sys.getStation())); }
    str = args.get("normalizeTime");
    if (str != null) { cx.normalizeTime = BBoolean.make(str); }
    str = args.get("rollup");
    // Analytics trend request from charts use "rainge" for "range", applied a decoding for that.
    if (str != null)
    {
      cx.setRollup(BCombination
        .make(str.equalsIgnoreCase("rainge") ? "range" : str));
    }
    str = args.get("timeRange");
    if (str != null) { cx.setTimeRange(BAnalyticTimeRange.make(str)); }
    str = args.get("timeZone");
    if (str != null) { cx.setTimeZone(TimeZone.getTimeZone(str)); }
    str = args.get("unit");
    if (str != null) { cx.setUnit(BUnit.getUnit(str)); }
    str = args.get("useCache");
    if (str != null) { cx.useCache = BBoolean.make(str); }
    str = args.get("seriesName");
    if (str != null) { cx.setSeriesName(BFormat.make(str)); }
    str = args.get("dow");
    if (str != null) { cx.setDowInclusion(str); }
    str = args.get("isHistory");
    if (str != null) { cx.history = BBoolean.make(str); }
    str = args.get("hisTotEnabled");
    if (str != null) { cx.hisTotEnabled = BBoolean.make(str); }
    BMissingDataStrategy strategy = cx.mdhStrategy;
    str = args.get("aggStrategy");
    if (str != null)
    {
      if (strategy == null) { strategy = new BMissingDataStrategy().enable(); }
      strategy.setAggregationStrategy(BAggregationStrategy.make(str));
    }
    str = args.get("intpAlgorithm");
    if (str != null)
    {
      if (strategy == null) { strategy = new BMissingDataStrategy().enable(); }
      strategy.setInterpolationAlgorithm(BInterpolationAlgorithm.make(str));
    }
    str = args.get("knnValue");
    if (str != null && strategy != null)
    {
      strategy.setKValue(Integer.parseInt(str));
    }
    cx.mdhStrategy = strategy;
    str = args.get("statusFilter");
    if (str != null)
    {
      cx.setStatusFilter(str);
    }
    BRawDataFilter rawDataFilter = cx.rawDataFilter;
    BRolloverValue rolloverValue = new BRolloverValue();
    str = args.get("rawFilterHighLimit");
    if (str != null)
    {
      //check if unspecified needs to be set
      rolloverValue.setValue(Double.parseDouble(str));
      rawDataFilter.setHighLimit(rolloverValue);
    }
    str = args.get("rawFilterLowLimit");
    if (str != null)
    {
      rolloverValue.setValue(Double.parseDouble(str));
      rawDataFilter.setLowLimit(rolloverValue);
    }
    //delta Values changes
    BDeltaValues deltaValues = cx.deltaValues;
    str = args.get("deltaValuesHighLimit");
    if (str != null)
    {
      //check if unspecified needs to be set
      rolloverValue.setValue(Double.parseDouble(str));
      deltaValues.setHighLimit(rolloverValue);
    }
    str = args.get("deltaValuesLowLimit");
    if (str != null)
    {
      rolloverValue.setValue(Double.parseDouble(str));
      deltaValues.setLowLimit(rolloverValue);
    }

    return cx;
  }

  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Private and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  private Interval computeInterval()
  {
    long end = getEndTime();
    long start = getStartTime();
    if (start < 0)
    { return BInterval.hour; }
    if (end < 0)
    { end = System.currentTimeMillis(); }
    return computeInterval(start, end);
  }

  private Interval computeInterval(long start, long end)
  {
    long duration = end - start;
    if (duration >= MILLIS_YEAR)
    { return BInterval.month; }
    if (duration >= MILLIS_MONTH)
    { return BInterval.day; }
    if (duration >= MILLIS_WEEK)
    { return BInterval.sixHours; }
    if (duration >= MILLIS_DAY)
    { return BInterval.fifteenMinutes; }
    if (duration >= MILLIS_TWELVE_HOURS)
    { return BInterval.fiveMinutes; }
    return BInterval.minute;
  }

  public BBoolean isBaseline()
  {
    return isBaseline;
  }

  public AnalyticContext setIsBaseline(BBoolean isBaseline)
  {
    this.isBaseline = isBaseline;
    return this;
  }

  /////////////////////////////////////////////////////////////////
  // Inner Classes
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Constants - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Protected Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  protected Combination aggregation;
  protected Id data;
  protected Predicate<Entity> dataFilter;
  protected Context base;
  protected Interval interval;
  protected BBoolean leadingDeltas;
  protected BINavNode node;
  protected BBoolean normalizeTime;
  protected Combination rollup;
  protected TimeRange timeRange;
  protected TimeZone timeZone;
  protected BUnit unit;
  protected BBoolean useCache;
  protected BFormat seriesName;
  protected BDaysOfWeekBits dowInclusion;
  protected BBoolean history;
  protected BBoolean hisTotEnabled;


  protected BMissingDataStrategy mdhStrategy;
  protected BStatus statusFilter;
  protected BRawDataFilter rawDataFilter;
  protected BDeltaValues deltaValues;
  /////////////////////////////////////////////////////////////////
  // Private Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  private AnalyticDataPolicy cachedDataPolicy;
  private Interval cachedInterval;
  private BFacets cacheFacets;
  private OrdTarget navTarget;
  private String toString;
  protected BBoolean isBaseline;

  private static final String DEFAULT_DATA_TAG = "a:a";

  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//AnalyticContext
