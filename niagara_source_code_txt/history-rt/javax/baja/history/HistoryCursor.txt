/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import java.io.IOException;
import javax.baja.collection.AbstractCursor;
import javax.baja.nre.util.ByteBuffer;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BString;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.util.BTypeSpec;


/**
 * HistoryCursor is a cursor for iterating through history records.
 *
 * @author    John Sublett
 */
public abstract class HistoryCursor extends AbstractCursor<BHistoryRecord>
{
  /**
   * @throws HistoryException
   */
  protected HistoryCursor(BHistoryConfig config,
                          BHistoryRecord iteratorRec,
                          Context context)
  {
    this.config = config;
    this.iteratorRec = iteratorRec;
    this.context = context;
  }

  /**
   * Sets the Context from the history database connection using this history
   * cursor.  This Context is not necessarily the same Context instance passed
   * into the constructor for this class.  Instead it is the Context
   * subsequently determined by a history connection utilizing this history
   * cursor instance.  By default, this method is a no-op, but subclasses can
   * use this callback to be notified about the connection Context.
   *
   * @param connectionCx The Context associated with a history database
   *                     connection utilizing the history cursor.
   *
   * @since Niagara 4.11
   */
  public void setConnectionContext(Context connectionCx)
  {

  }

////////////////////////////////////////////////////////////////
// Cursor
////////////////////////////////////////////////////////////////

  /**
   * Get the configuration for the history that this cursor
   * is iterating through.
   */
  public BHistoryConfig getConfig()
  {
    return config;
  }

  /**
   * Get the context for this cursor.
   */
  @Override
  public Context getContext()
  {
    return context;
  }

  /**
   * Advance the cursor to the next record and read the record contents
   * into the iterator record.  The same instance of BHistoryRecord
   * will be returned from every call to get(), but the values will
   * be updated on every call to next().  If the records returned
   * from get() need to be stored, they must be cloned first.
   */
  @Override
  protected final boolean advanceCursor()
  {
    return doNext(iteratorRec);
  }

  /**
   * Advance the cursor to the next record and read the record contents
   * into the iterator record.
   *
   * @param iteratorRec A record used to iterate through the records in the
   *   history.  This instance will be returned from every call to get(), but
   *   the values will be updated on every call to next().  If the records
   *   returned from get() need to be stored, they must be cloned first.
   */
  protected abstract boolean doNext(BHistoryRecord iteratorRec);

  /**
   * Get the current record.
   */
  @Override
  protected BHistoryRecord doGet()
  {
    return iteratorRec;
  }


////////////////////////////////////////////////////////////////
// Convenience
////////////////////////////////////////////////////////////////

  /**
   * Convenience method which takes the pre and post history records
   * (assumed to be the history records just prior to and just after
   * the starting record and ending record respectively in a
   * history cursor) and generates a BFacets instance containing
   * these records.  This is typically used to populate the context
   * of the history cursor so that callers can determine the
   * previous/subsequent records surrounding a history
   * time query. Note that either the preRec or postRec can be null,
   * if one is not applicable. The resulting facets will only contain
   * the non-null records. Also note that if both parameters are non-null,
   * they are assumed to be the same history record type.
   *
   * @since Niagara 3.7
   */
  public static BFacets makeBoundaryRecordFacets(BHistoryRecord preRec,
                                                 BHistoryRecord postRec)
    throws IOException
  {
    if (preRec != null && postRec != null)
    {
      ByteBuffer buf = new ByteBuffer();
      preRec.write(buf);
      byte[] preRecBytes = buf.toByteArray();
      buf.reset();
      postRec.write(buf);
      byte[] postRecBytes = buf.toByteArray();
      return BFacets.make(HISTORY_REC_TYPE, BString.make(preRec.getType().getTypeSpec().encodeToString()),
                          PRE_HISTORY_REC, BString.make(java.util.Base64.getEncoder().encodeToString(preRecBytes)),
                          POST_HISTORY_REC, BString.make(java.util.Base64.getEncoder().encodeToString(postRecBytes)));
    }
    if (preRec != null)
    {
      ByteBuffer buf = new ByteBuffer();
      preRec.write(buf);
      return BFacets.make(HISTORY_REC_TYPE, BString.make(preRec.getType().getTypeSpec().encodeToString()),
                          PRE_HISTORY_REC, BString.make(java.util.Base64.getEncoder().encodeToString(buf.toByteArray())));
    }
    if (postRec != null)
    {
      ByteBuffer buf = new ByteBuffer();
      postRec.write(buf);
      return BFacets.make(HISTORY_REC_TYPE, BString.make(postRec.getType().getTypeSpec().encodeToString()),
                          POST_HISTORY_REC, BString.make(java.util.Base64.getEncoder().encodeToString(buf.toByteArray())));
    }

    return BFacets.NULL;
  }

  /**
   * Convenience method which takes a BFacets instance and extracts
   * the special "historyCursorPreRec" history record, if it exists in
   * the facets instance (otherwise null is returned).  This is typically
   * used to aid callers that need to know the preceding record just prior
   * to the starting record of a history time query.
   *
   * @since Niagara 3.7
   */
  public static BHistoryRecord extractPreRecord(BFacets facets)
    throws IOException
  {
    if (facets == null)
    {
      return null;
    }

    String historyRecTypeSpec = facets.gets(HISTORY_REC_TYPE, null);
    if (historyRecTypeSpec == null)
    {
      return null;
    }
    String encodedRec = facets.gets(PRE_HISTORY_REC, null);
    if (encodedRec == null)
    {
      return null;
    }
    BHistoryRecord rec = (BHistoryRecord)((BTypeSpec)BTypeSpec.DEFAULT.decodeFromString(historyRecTypeSpec)).getInstance();
    ByteBuffer buf = new ByteBuffer(java.util.Base64.getDecoder().decode(encodedRec));
    return rec.read(buf);
  }

  /**
   * Convenience method which takes a BFacets instance and extracts
   * the special "historyCursorPostRec" history record, if it exists in
   * the facets instance (otherwise null is returned).  This is typically
   * used to aid callers that need to know the subsequent record just after
   * the ending record of a history time query.
   *
   * @since Niagara 3.7
   */
  public static BHistoryRecord extractPostRecord(BFacets facets)
    throws IOException
  {
    if (facets == null)
    {
      return null;
    }

    String historyRecTypeSpec = facets.gets(HISTORY_REC_TYPE, null);
    if (historyRecTypeSpec == null)
    {
      return null;
    }
    String encodedRec = facets.gets(POST_HISTORY_REC, null);
    if (encodedRec == null)
    {
      return null;
    }
    BHistoryRecord rec = (BHistoryRecord)((BTypeSpec)BTypeSpec.DEFAULT.decodeFromString(historyRecTypeSpec)).getInstance();
    ByteBuffer buf = new ByteBuffer(java.util.Base64.getDecoder().decode(encodedRec));
    return rec.read(buf);
  }

  /**
   * Convenience method to generate a Context instance that contains a special
   * facet (named "archiveHistoryLimitExceeded") to signify that a limit was
   * exceeded while gathering archive history data (e.g. history data archived to
   * the cloud or some other archive provider). When this special Context facet
   * is set to true, it informs consumers that the limit was exceeded at history
   * query time and the query results contain only partial matching records up
   * to the limit.
   *
   * @param cx A Context that will be used as a base for generating the result.
   *           This parameter can be null.
   * @return A Context instance generated from the given base Context argument
   * enhanced with one additional special facet called
   * "archiveHistoryLimitExceeded" that has a boolean value of true.
   *
   * @since Niagara 4.11
   */
  public static Context makeArchiveLimitExceededContext(Context cx)
  {
    return cx == null? ARCHIVE_LIMIT_EXCEEDED_FACET : new BasicContext(cx, ARCHIVE_LIMIT_EXCEEDED_FACET);
  }

  /**
   * Convenience method that checks the given Context argument for the presence
   * of a special "archiveHistoryLimitExceeded" facet, and if found, returns its
   * boolean value.  This value is used to signify whether a limit was exceeded
   * while gathering archive history data (e.g. history data archived to the
   * cloud or some other archive provider). When true, it informs consumers that
   * a limit was exceeded at history query time and the query result contains
   * only partial matching records up to the limit.
   *
   * @param cx The Context instance to check
   * @return true if the "archiveHistoryLimitExceeded" facet is present on the
   * Context argument and it has a boolean true value.  Otherwise false is
   * returned.
   *
   * @since Niagara 4.11
   */
  public static boolean archiveLimitExceeded(Context cx)
  {
    return cx != null && BBoolean.TRUE.equals(cx.getFacet(ARCHIVE_LIMIT_EXCEEDED_KEY));
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final String HISTORY_REC_TYPE = "historyRecordTypeSpec";
  private static final String PRE_HISTORY_REC = "historyCursorPreRec";
  private static final String POST_HISTORY_REC = "historyCursorPostRec";

  private static final String ARCHIVE_LIMIT_EXCEEDED_KEY = "archiveHistoryLimitExceeded";
  private static final BFacets ARCHIVE_LIMIT_EXCEEDED_FACET = BFacets.make(ARCHIVE_LIMIT_EXCEEDED_KEY, true);

  private final BHistoryConfig config;
  private final BHistoryRecord iteratorRec;
  private final Context        context;
}
