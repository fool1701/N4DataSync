/*
 * Copyright 2021, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history.db;

import java.util.Optional;

import javax.baja.history.BHistoryConfig;
import javax.baja.history.BHistoryRecord;
import javax.baja.history.HistoryQuery;
import javax.baja.license.Feature;
import javax.baja.license.FeatureNotLicensedException;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInteger;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Cursor;
import javax.baja.sys.Flags;
import javax.baja.sys.IllegalParentException;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BIRestrictedComponent;
import javax.baja.util.Lexicon;

import com.tridium.sys.license.LicenseUtil;
import com.tridium.sys.schema.Fw;

/**
 * When installed on a station as a dynamic child on the HistoryService's
 * {@link BArchiveHistoryProviders} container, this component
 * provides a hybrid mode of operation for the history database, in that
 * it will store and use history data locally, but queries to the database will
 * be supplemented by history data retrieved from an archive specified by this
 * class. An example implementation of this class would be to read archive
 * history data previously archived to the cloud (via a separate mechanism).
 * Please note that archive data will only be accessed at query time (reads).
 * The archive provider will not be used for writing new history data
 * (create/update/writes will only apply to the local history data). Also note
 * that the archive history provider is only tapped for history data when a query
 * is made that exceeds the earliest data available locally (thus the archive
 * data is assumed to be older/archived history data). It is also assumed that
 * all histories accessed must be available locally, even if the local data is
 * minimal in capacity. Therefore the archive history provider will not be queried
 * for histories that don't exist locally.
 *
 * There are some scenarios where accessing archive data is not desired at query
 * time. In order to accomodate those special cases, a special Context facet can
 * be applied to the {@link Context} instance used when resolving a history ORD
 * against a hybrid history database or making a connection to the database.
 * This boolean facet is named "excludeArchiveHistoryData" and when present and
 * set to true, it will signify that archive history data should be excluded from
 * queries using the Context containing it. Refer to
 * {@link HistoryQuery#makeExcludeArchiveDataContext(Context)} for a convenient
 * way to create such a Context. Alternatively, when making a direct connection
 * to the database, you can use the
 * {@link BHistoryDatabase#getDbConnection(boolean, Context)} method and pass
 * in true for the excludeArchiveData argument, and that will ensure that any
 * queries made to the database using that connection will disregard archive
 * history data and only use local data.
 *
 * Multiple instances of BArchiveHistoryProvider implementations are allowed
 * to be installed in a station, but they must be added as direct children of
 * the HistoryService's {@link BArchiveHistoryProviders} container. At history
 * query time, installed BArchiveHistoryProviders will be accessed in slot order
 * until the first match is found (a match is a provider that contains archived
 * history data for the requested history). History queries cannot span across
 * multiple BArchiveHistoryProviders; only the first matching provider for a
 * given history query will be used.
 *
 * @author Scott Hoye on 04/17/2019
 * @since Niagara 4.11
 */
@NiagaraType
/*
 The current status of this provider.  This property should never be set
 directly since it is computed by the framework.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.DEFAULT",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY
)
/*
 The enabled state of this archive history provider.  If false, this archive
 history provider won't be used to supplement local history data.  If true,
 this archive history provider will be tapped (as needed) to supplement
 local history data.
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "false"
)
/*
 When the status is in fault, this property displays a short message
 describing why the provider is in fault.
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "BString.DEFAULT",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY | Flags.TRANSIENT,
  facets = @Facet(name = "BFacets.MULTI_LINE", value = "BBoolean.TRUE")
)
/*
 For each time query requested, this limit determines the maximum number of
 history records that will be read from the archive history provider and
 returned in the results.
 */
@NiagaraProperty(
  name = "maxArchiveResultsPerQuery",
  type = "int",
  defaultValue = "50000",
  facets = @Facet(name = "BFacets.MIN", value = "1")
)
/*
 Specifies the Workbench notification behavior when the
 maxArchiveResultsPerQuery limit is exceeded for a history query made from a
 remote Workbench user.
 */
@NiagaraProperty(
  name = "archiveLimitNotifications",
  type = "BArchiveLimitNotificationBehavior",
  defaultValue = "BArchiveLimitNotificationBehavior.notifyOncePerQueryRangePerSession"
)
public abstract class BArchiveHistoryProvider
  extends BComponent
  implements BIRestrictedComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.db.BArchiveHistoryProvider(367089448)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * The current status of this provider.  This property should never be set
   * directly since it is computed by the framework.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, BStatus.DEFAULT, null);

  /**
   * Get the {@code status} property.
   * The current status of this provider.  This property should never be set
   * directly since it is computed by the framework.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * The current status of this provider.  This property should never be set
   * directly since it is computed by the framework.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * The enabled state of this archive history provider.  If false, this archive
   * history provider won't be used to supplement local history data.  If true,
   * this archive history provider will be tapped (as needed) to supplement
   * local history data.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, false, null);

  /**
   * Get the {@code enabled} property.
   * The enabled state of this archive history provider.  If false, this archive
   * history provider won't be used to supplement local history data.  If true,
   * this archive history provider will be tapped (as needed) to supplement
   * local history data.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * The enabled state of this archive history provider.  If false, this archive
   * history provider won't be used to supplement local history data.  If true,
   * this archive history provider will be tapped (as needed) to supplement
   * local history data.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * When the status is in fault, this property displays a short message
   * describing why the provider is in fault.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY | Flags.TRANSIENT, BString.DEFAULT, BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

  /**
   * Get the {@code faultCause} property.
   * When the status is in fault, this property displays a short message
   * describing why the provider is in fault.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * When the status is in fault, this property displays a short message
   * describing why the provider is in fault.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "maxArchiveResultsPerQuery"

  /**
   * Slot for the {@code maxArchiveResultsPerQuery} property.
   * For each time query requested, this limit determines the maximum number of
   * history records that will be read from the archive history provider and
   * returned in the results.
   * @see #getMaxArchiveResultsPerQuery
   * @see #setMaxArchiveResultsPerQuery
   */
  public static final Property maxArchiveResultsPerQuery = newProperty(0, 50000, BFacets.make(BFacets.MIN, 1));

  /**
   * Get the {@code maxArchiveResultsPerQuery} property.
   * For each time query requested, this limit determines the maximum number of
   * history records that will be read from the archive history provider and
   * returned in the results.
   * @see #maxArchiveResultsPerQuery
   */
  public int getMaxArchiveResultsPerQuery() { return getInt(maxArchiveResultsPerQuery); }

  /**
   * Set the {@code maxArchiveResultsPerQuery} property.
   * For each time query requested, this limit determines the maximum number of
   * history records that will be read from the archive history provider and
   * returned in the results.
   * @see #maxArchiveResultsPerQuery
   */
  public void setMaxArchiveResultsPerQuery(int v) { setInt(maxArchiveResultsPerQuery, v, null); }

  //endregion Property "maxArchiveResultsPerQuery"

  //region Property "archiveLimitNotifications"

  /**
   * Slot for the {@code archiveLimitNotifications} property.
   * Specifies the Workbench notification behavior when the
   * maxArchiveResultsPerQuery limit is exceeded for a history query made from a
   * remote Workbench user.
   * @see #getArchiveLimitNotifications
   * @see #setArchiveLimitNotifications
   */
  public static final Property archiveLimitNotifications = newProperty(0, BArchiveLimitNotificationBehavior.notifyOncePerQueryRangePerSession, null);

  /**
   * Get the {@code archiveLimitNotifications} property.
   * Specifies the Workbench notification behavior when the
   * maxArchiveResultsPerQuery limit is exceeded for a history query made from a
   * remote Workbench user.
   * @see #archiveLimitNotifications
   */
  public BArchiveLimitNotificationBehavior getArchiveLimitNotifications() { return (BArchiveLimitNotificationBehavior)get(archiveLimitNotifications); }

  /**
   * Set the {@code archiveLimitNotifications} property.
   * Specifies the Workbench notification behavior when the
   * maxArchiveResultsPerQuery limit is exceeded for a history query made from a
   * remote Workbench user.
   * @see #archiveLimitNotifications
   */
  public void setArchiveLimitNotifications(BArchiveLimitNotificationBehavior v) { set(archiveLimitNotifications, v, null); }

  //endregion Property "archiveLimitNotifications"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BArchiveHistoryProvider.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Since multiple BArchiveHistoryProvider instances can be installed in the
   * HistoryService's {@link BArchiveHistoryProviders} container, at query time
   * a specific BArchiveHistoryProvider instance which contains the archived
   * data for a given history must be found. Determining the existence of
   * archived history data could be expensive (e.g. a network call to a remote
   * historian). So prior to calling
   * {@link #timeQuery(BHistoryConfig, BAbsTime, BAbsTime, boolean, Context)},
   * this method will be called which is intended to be a quick, unreliable
   * existence check (e.g. no network calls) that indicates whether this
   * BArchiveHistoryProvider instance is likely to contain archived data for
   * the given history. Subclasses should implement this method to perform a
   * quick check if possible (if not possible, subclasses should just return
   * false). For example, a quick check could involve looking for a matching
   * history export descriptor in the local Niagara station that might be a good
   * indication of where the history's archive data is located. Installed
   * BArchiveHistoryProvider instances will have this method called (in slot
   * order), and any instances that return true will be given precedence in calling
   * {@link #timeQuery(BHistoryConfig, BAbsTime, BAbsTime, boolean, Context)}.
   * If {@link #timeQuery(BHistoryConfig, BAbsTime, BAbsTime, boolean, Context)}
   * then returns something other than {@link Optional#empty()} (an empty
   * Optional result is the conclusive indicator that the history is not
   * archived to a given BArchiveHistoryProvider), then the search for archived
   * data can stop. This method is designed to be a helpful indicator for where
   * archived history data is likely to exist in order to speed up query
   * performance (by reducing unnecessary network calls). Even if this method
   * returns false for a given BArchiveHistoryProvider, it can still be later
   * queried for archive history data if no other check on another installed
   * BArchiveHistoryProvider instance locates the history's archive data first.
   *
   * @param historyConfig The history for which a query is about to be made, so
   *                      the quick check should look for the probable existence
   *                      of archived data for the history it specifies.
   * @param context The Context associated with this request
   * @return true if archived history data is likely (but not guaranteed) to be
   * contained in this BArchiveHistoryProvider for the given history. Returns
   * false if archived history data is unlikely for the given history or if it
   * can't be determined through a quick check.
   */
  public abstract boolean isLikelyToContainArchivedHistory(BHistoryConfig historyConfig,
                                                           Context context);

  /**
   * Perform a query for archive history data and return the results as a
   * {@link Cursor} of history records ({@link BHistoryRecord}). The returned
   * Cursor will be limited by the max size specified in the
   * {@link #getMaxArchiveResultsPerQuery()} property with most recent history
   * data favored over older data when the limit is reached.  The returned
   * Cursor will also include a Context (see {@link Cursor#getContext()}) that
   * includes special facet information described below. If the history does
   * not exist in the archive provider, {@link Optional#empty()} is returned.
   *
   * @param historyConfig The local history configuration instance for which to
   *                      retrieve data from the archive.
   * @param startTime The start time for the archive data query (inclusive). If
   *                  the start time is BAbsTime.NULL, then the query will start
   *                  from the beginning record available.
   * @param endTime The end time for the archive data query (inclusive). If the
   *                end time is BAbsTime.NULL, then the query will end at the
   *                last record available.
   * @param descending When true, the results will be returned in timestamp
   *                   descending order.  When false, the results will be
   *                   returned in timestamp ascending order.
   * @param context The context associated with this archive time query request,
   *                not to be confused with the context in the Cursor returned
   *                by this method. This context is for future use (can be null)
   *                and could contain additional facets to provide more
   *                qualifiers for the computed result.
   *
   * @return A cursor of history records that results from the given time query
   * against the archive history provider. If the history does not exist in
   * the archive provider, {@link Optional#empty()} is returned giving control
   * back to the framework to proceed with using local history data only. Also
   * when called by the framework, any runtime exceptions thrown by this method
   * will get logged as a warning before proceeding to use local history data
   * only. Another vital part of the returned Cursor is its Context (see
   * {@link Cursor#getContext()}. The returned Cursor's Context is expected to
   * be immediately available (not requiring the Cursor to be drained first) and
   * it is expected to include some special Context facets in order to inform
   * the caller about the following conditions:
   *  (1) If the {@link #getMaxArchiveResultsPerQuery()} limit is exceeded while
   *      computing the matching history records from the archive, an
   *      "archiveHistoryLimitExceeded" boolean facet will be set to true in the
   *      Context of the returned cursor. You can use the
   *      {@link javax.baja.history.HistoryCursor#archiveLimitExceeded(Context)}
   *      method to check the Cursor's Context to see if this boolean facet is
   *      set to true. This Context facet allows callers to be informed that
   *      the limit was exceeded and the Cursor result contains partial matching
   *      records up to the limit (favoring recent records over old ones).
   *  (2) The pre and post history records (the archive history records just
   *      outside the boundaries of the requested time range or just outside of
   *      the limited results, whichever applies first) will be included
   *      as facets in the Context of the returned cursor.  The
   *      {@link javax.baja.history.HistoryCursor#extractPreRecord(BFacets)} or
   *      {@link javax.baja.history.HistoryCursor#extractPostRecord(BFacets)}
   *      methods can be used to check the returned Cursor's Context's facets
   *      for the presence of these boundary archive history records. If
   *      descending order is requested, the pre history record will be a more
   *      recent history record that is just after the requested end time (or
   *      null if no records exist that are more recent than the requested time
   *      range), and the post history record will be an older history record
   *      that is just before the requested start time (or null if no records
   *      exist that are older than the requested time range) or, if the
   *      {@link #getMaxArchiveResultsPerQuery()} limit is exceeded, the record
   *      just before the oldest recent record fitting into the limit. If
   *      ascending order is requested, the pre history record will be an older
   *      history record that is just before the requested start time (or null
   *      if no records exist that are older than the requested time range) or,
   *      if the {@link #getMaxArchiveResultsPerQuery()} limit is exceeded, the
   *      record just before the oldest recent record fitting into the limit,
   *      and the post history record will be a more recent history record that
   *      is just after the requested end time (or null if no records exist that
   *      are more recent than the requested time range).
   */
  public final Optional<Cursor<BHistoryRecord>> timeQuery(BHistoryConfig historyConfig,
                                                          BAbsTime startTime,
                                                          BAbsTime endTime,
                                                          boolean descending,
                                                          Context context)
  {
    if (!isOperational())
    {
      throw new NotRunningException("A time query cannot be executed when the archive history provider is not operational.");
    }

    return doTimeQuery(historyConfig, startTime, endTime, descending,
                       computeArchiveQueryLimit(context), context);
  }

  /**
   * Callback for subclasses to perform a query for archive history data and
   * return the results as a {@link Cursor} of history records
   * ({@link BHistoryRecord}). The returned Cursor should include a Context
   * (see {@link Cursor#getContext()}) that includes special facet information
   * described below. If the history does not exist in the archive provider,
   * {@link Optional#empty()} should be returned.
   *
   * @param historyConfig The local history configuration instance for which to
   *                      retrieve data from the archive.
   * @param startTime The start time for the archive data query (inclusive). If
   *                  the start time is BAbsTime.NULL, then query from the
   *                  beginning record available.
   * @param endTime The end time for the archive data query (inclusive). If the
   *                end time is BAbsTime.NULL, then query to the last record
   *                available.
   * @param descending When true, return the results in timestamp descending
   *                   order.  When false, return the results in timestamp
   *                   ascending order.
   * @param limit A limit on the total number of history records that should be
   *              returned, even if more matches exist for the specified time
   *              range in the archive. If descending order is requested and the
   *              limit is reached, the most recent matching history records
   *              should be returned (in descending order) and the limit should
   *              be enforced to exclude the oldest matching history records. If
   *              ascending order is requested and the limit is reached, the
   *              most recent matching history records should be returned (in
   *              ascending order) and the limit should be enforced to exclude
   *              the oldest matching history records. In either case, if the
   *              number of matching history records exceeds the limit, the
   *              "archiveHistoryLimitExceeded" boolean facet should be set to
   *              true in the Context of the returned cursor (see below) so that
   *              consumers can be notified that the limit was exceeded and the
   *              result contains partial matching records up to the limit.
   * @param context The context associated with this archive time query request,
   *                not to be confused with the context in the Cursor returned
   *                by this method. This context is for future use (can be null)
   *                and could contain additional facets to provide more
   *                qualifiers for the computed result.
   *
   * @return A cursor of history records that results from the given time query
   * against the archive history provider. If the history does not exist in
   * the archive provider, {@link Optional#empty()} should be returned so that
   * the framework can proceed with using local history data only. If the
   * history exists but no matching history records are found for the time
   * query, an {@link com.tridium.util.EmptyCursor} should be returned in the
   * Optional result, with the appropriate Context facets described below. Any
   * runtime exceptions thrown by this callback will get logged by the framework
   * as a warning before proceeding to use local data only. Another vital part
   * of the returned Cursor is its Context (see {@link Cursor#getContext()}. The
   * returned Cursor's Context is expected to be immediately available (not
   * requiring the Cursor to be drained first) and it is expected to include
   * some special Context facets in order to inform the caller about the
   * following conditions:
   *  (1) If the limit is exceeded while computing the matching history records
   *      from the archive, an "archiveHistoryLimitExceeded" boolean facet must
   *      be set to true in the Context of the returned cursor. Use the
   *      {@link javax.baja.history.HistoryCursor#makeArchiveLimitExceededContext(Context)}
   *      method to create a Context with this boolean facet set to true (or to
   *      enhance an existing Context instance).  This Context facet allows
   *      consumers to be informed that the limit was exceeded and the cursor
   *      result contains partial matching records up to the limit.
   *  (2) The pre and post history records (the archive history records just
   *      outside the boundaries of the requested time range or just outside of
   *      the limited results, whichever applies first) must be included
   *      as facets in the Context of the returned cursor.  The
   *      {@link javax.baja.history.HistoryCursor#makeBoundaryRecordFacets(BHistoryRecord, BHistoryRecord)}
   *      method should be used to generate these facets, and then they must
   *      be applied to the returned cursor's Context. If descending order is
   *      requested, the pre history record should be a more recent history
   *      record that is just after the requested end time (or null if no
   *      records exist that are more recent than the requested time range), and
   *      the post history record should be an older history record that is just
   *      before the requested start time (or null if no records exist that are
   *      older than the requested time range) or, if the limit is exceeded, the
   *      record just before the oldest recent record fitting into the limit. If
   *      ascending order is requested, the pre history record should be an
   *      older history record that is just before the requested start time
   *      (or null if no records exist that are older than the requested time
   *      range) or, if the limit is exceeded, the record just before the oldest
   *      recent record fitting into the limit, and the post history record
   *      should be a more recent history record that is just after the
   *      requested end time (or null if no records exist that are more recent
   *      than the requested time range).
   */
  protected abstract Optional<Cursor<BHistoryRecord>> doTimeQuery(BHistoryConfig historyConfig,
                                                                  BAbsTime startTime,
                                                                  BAbsTime endTime,
                                                                  boolean descending,
                                                                  int limit,
                                                                  Context context);

////////////////////////////////////////////////////////////////
// Status
////////////////////////////////////////////////////////////////

  private void checkLicense() throws FeatureNotLicensedException
  {
    try
    {
      Feature historyArchiveFeature = Sys.getLicenseManager().getFeature(LicenseUtil.TRIDIUM_VENDOR, "historyArchive");
      if (historyArchiveFeature == null)
      {
        throw new FeatureNotLicensedException(UNLICENSED);
      }

      historyArchiveFeature.check();
    }
    catch (Exception e)
    {
      throw new FeatureNotLicensedException(UNLICENSED);
    }

    checkProviderLicense();
  }

  /**
   * This method can be overridden by sub classes to enforce the existence of a license feature.  If implemented,
   * this method should throw a {@link FeatureNotLicensedException} with an appropriate message to be displayed
   * as the {@link BArchiveHistoryProvider#faultCause} of the sub class.
   *
   * @throws FeatureNotLicensedException if a license feature is required for a sub class and it
   * cannot be found in the license file.
   */
  protected void checkProviderLicense() throws FeatureNotLicensedException
  {

  }

  /**
   * Return true if this ArchiveHistoryProvider is operational. A provider is
   * operational if it is mounted in a running station, the {@link #enabled}
   * property is set to true, and the status is not in fault.
   */
  public final boolean isOperational()
  {
    return isRunning() && getEnabled() && !configFault && !fatalFault;
  }

  /**
   * Recompute this ArchiveHistoryProvider's status.
   */
  protected final void updateStatus()
  {
    // compute new status
    int newStatus = 0;

    // check for disabled bit
    if (!getEnabled())
    {
      newStatus |= BStatus.DISABLED;
    }

    // check for fault bit
    if (fatalFault || configFault)
    {
      newStatus |= BStatus.FAULT;
    }

    // short circuit if nothing has changed
    if (getStatus().getBits() == newStatus)
    {
      return;
    }

    // Otherwise set the new computed status property
    setStatus(BStatus.make(newStatus));
  }

////////////////////////////////////////////////////////////////
// Fault
////////////////////////////////////////////////////////////////

  /**
   * Clear the configuration fault status.  This ArchiveHistoryProvider may
   * remain in fault if it has fatal faults.
   */
  protected final void configOk()
  {
    // clear config fault flag
    configFault = false;

    // fatal faults always trump
    if (fatalFault)
    {
      return;
    }

    // update props
    setFaultCause("");
    updateStatus();
  }

  /**
   * Set this ArchiveHistoryProvider into configuration fault.  If it
   * was previously not in fault, then this sets the fault status and fault
   * cause.
   */
  protected final void configFail(String cause)
  {
    // set config fault flag
    configFault = true;

    // fatal faults always trump
    if (fatalFault)
    {
      return;
    }

    // update props
    setFaultCause(cause);
    updateStatus();
  }

  /**
   * Set this ArchiveHistoryProvider into the fatal fault condition.  Unlike
   * configFail(), the fatal fault condition cannot be cleared until restart.
   * Fatal faults trump config faults. Fatal faults usually indicate licensing
   * failures.
   */
  protected final void configFatal(String cause)
  {
    fatalFault = true;
    setFaultCause(cause);
    updateStatus();
  }

////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  /**
   * Convenience method to compute the limit on the maximum number of history
   * records that should be read from this archive history provider at query
   * time. It checks the given Context argument for the presence of a special
   * "archiveHistoryQueryLimit" facet, and if found, returns its integer value
   * ONLY IF it is lower than the
   * {@link BArchiveHistoryProvider#getMaxArchiveResultsPerQuery()} property
   * value. Therefore any limit specified in the given Context will be upper
   * bounded by the configured limit on this archive history provider instance
   * itself. If no limit is specified in the given Context, then the
   * {@link BArchiveHistoryProvider#getMaxArchiveResultsPerQuery()} property
   * value will be returned.
   *
   * @param context The Context instance to check for a special
   *                "archiveHistoryQueryLimit" facet.
   * @return the limit on the maximum number of history records that should be
   * read from this archive history provider at query time.
   */
  public final int computeArchiveQueryLimit(Context context)
  {
    int maxLimit = getMaxArchiveResultsPerQuery();
    int limit = HistoryQuery.getArchiveQueryLimit(context, maxLimit);
    if (limit < 1)
    {
      // While the min facet on the maxArchiveResultsPerQuery should prevent
      // users from setting the limit below 1, since it could be set lower
      // programmatically or from a link, lets enforce the minimum limit here in
      // the code and revert to the default value if detected.
      limit = maxArchiveResultsPerQuery.getDefaultValue().as(BInteger.class).getInt();
    }
    else if (limit > maxLimit)
    {
      // Prevent the Context from specifying an archive limit higher than the
      // one configured on the maxArchiveResultsPerQuery property.
      limit = maxLimit;
    }

    return limit;
  }

///////////////////////////////////////////////////////////
// BIRestrictedComponent
///////////////////////////////////////////////////////////

  /**
   * Ensures that BArchiveHistoryProvider instances are only allowed to live
   * under the {@link BArchiveHistoryProviders} container in a station.
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context context)
  {
    // Using this BIRestrictedComponent callback instead of the isParentLegal()
    // callback has the advantage of catching an attempt to add a preconfigured
    // folder (or unrestricted folder) that contains a descendant
    // BArchiveHistoryProvider. isParentLegal() won't catch that and allows the
    // invalid add, but this callback will catch such attempts because it
    // is called on all descendant components of the pending component (folder)
    // being added to the station.
    if (!(parent instanceof BArchiveHistoryProviders))
    {
      throw new IllegalParentException("baja", "IllegalParentException.parentAndChild",
        new Object[] { parent.getType(), getType() });
    }
  }

////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////

  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    if (x == Fw.STARTED)
    {
      fwStarted();
    }
    else if (isRunning() && x == Fw.CHANGED)
    {
      if (enabled.equals(a))
      {
        updateStatus();
      }
      else if (status.equals(a))
      {
        // Notify the parent BArchiveHistoryProviders container of any 'status'
        // property changes on this BArchiveHistoryProvider so the container can
        // update its cache
        BComplex parent = getParent();
        if (parent != null)
        {
          parent.fw(x, getPropertyInParent(), b, c, d);
        }
      }
    }
    return super.fw(x, a, b, c, d);
  }

  private void fwStarted()
  {
    try
    {
      checkLicense();
      updateStatus();
    }
    catch (Exception e)
    {
      configFatal(e.getMessage());
    }
  }

////////////////////////////////////////////////////////////////
// Icon
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon()
  {
    BValue dynamic = get("icon");
    if (dynamic instanceof BIcon)
    {
      return (BIcon) dynamic;
    }
    return ICON;
  }

  private static final BIcon ICON = BIcon.make(BIcon.std("database.png"),
    BIcon.std("badges/history.png"));

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final Lexicon LEXICON = Lexicon.make("history");
  private static final String UNLICENSED = LEXICON.getText("archiveHistoryProvider.unlicensed");

  private boolean fatalFault;
  private boolean configFault;
}
