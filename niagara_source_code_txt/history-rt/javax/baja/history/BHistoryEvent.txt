/*
 * Copyright 2008 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Hashtable;

import javax.baja.io.BIEncodable;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.ByteBuffer;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BObject;
import javax.baja.sys.BStruct;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTypeSpec;

import com.tridium.history.BHistoryRecordGroup;
import com.tridium.history.audit.BAuditRecord;
import com.tridium.history.audit.BSecurityAuditRecord;
import com.tridium.history.log.BLogRecord;
import com.tridium.util.ValueByteBuffer;

/**
 * BHistoryEvent encapsulates the information associated
 * with a modification to the history database.
 *
 * @author    Scott Hoye
 * @creation  14 Nov 08
 * @version   $Revision: 5$ $Date: 8/3/10 1:04:04 PM EDT$
 * @since     Niagara 3.4.47
 */
@NiagaraType
public final class BHistoryEvent
  extends BStruct
  implements BIEncodable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BHistoryEvent(2979906276)1.0$ @*/
/* Generated Thu Jan 27 19:02:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryEvent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Ids
////////////////////////////////////////////////////////////////

  /**
   * This event id indicates that the specified
   * history was created in the history database.
   * The value contains the history id of the new
   * history created.
   */
  public static final int CREATED = 0;

  /**
   * This event id indicates that the specified
   * history was deleted from the history database.
   * The value contains the history id of the old
   * history just deleted.
   */
  public static final int DELETED = 1;

  /**
   * This event id indicates that the specified
   * history has had an append of a history record.
   * The value contains the new history record appended.
   */
  public static final int APPENDED = 2;

  /**
   * This event id indicates that the specified
   * history has had an update of an existing history record.
   * The value contains the new (updated) history record.
   */
  public static final int UPDATED = 3;

  /**
   * This event id indicates that the specified
   * history has had all records cleared.
   * The value contains the history id of the
   * history just cleared.
   */
  public static final int CLEARED_ALL_RECORDS = 4;

  /**
   * This event id indicates that the specified
   * history has had old records cleared.
   * The value contains the history id of the
   * history just cleared.  The timestamp indicates
   * the earliest time to kept in the result after the
   * clear (Records before this time were removed from
   * the specified history).
   */
  public static final int CLEARED_OLD_RECORDS = 5;

  /**
   * This event id indicates that the specified
   * history was renamed in the history database.
   * The value contains the new name
   * assigned to the history.
   */
  public static final int RENAMED = 6;

  /**
   * This event id indicates that the specified
   * history was resized in the history database.
   * The value contains the new capacity and full policy.
   */
  public static final int RESIZED = 7;

  /**
   * This event id indicates that the history
   * databased was just opened.
   */
  public static final int DB_OPENED = 8;

  /**
   * This event id indicates that the history
   * databased was just closed.
   */
  public static final int DB_CLOSED = 9;

  /**
   * This event id indicates that the history
   * databased was just flushed.
   */
  public static final int DB_FLUSHED = 10;

  /**
   * This event id indicates that the history
   * databased was just saved (perhaps archived).
   * Note that a DB_FLUSHED event will normally happen
   * just prior to a DB_SAVED event.
   */
  public static final int DB_SAVED = 11;

  /**
   * This event id indicates that the specified
   * history has had its history config changed.
   * The value contains the history id of the
   * history whose config has changed.  The config indicates
   * the new history config assigned to the history.
   *
   * @since Niagara 3.5
   */
  public static final int CONFIG_CHANGED = 12;

  /**
   * Private String array for the id names.
   */
  private static String[] ID_STRINGS =
  {
    "created",
    "deleted",
    "appended",
    "updated",
    "clearedAllRecords",
    "clearedOldRecords",
    "renamed",
    "resized",
    "dbOpened",
    "dbClosed",
    "dbFlushed",
    "dbSaved",
    "configChanged"
  };

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Make a CREATED history event.
   *
   * @deprecated As of Niagara 3.5, use <code>makeCreated(BHistoryId historyId, BHistoryConfig config)</code> instead.
   */
  @Deprecated
  public static BHistoryEvent makeCreated(BHistoryId historyId)
  {
    return new BHistoryEvent(CREATED, historyId);
  }

  /**
   * Make a CREATED history event.
   *
   * @since Niagara 3.5
   */
  public static BHistoryEvent makeCreated(BHistoryId historyId, BHistoryConfig config)
  {
    BHistoryEvent event = new BHistoryEvent(CREATED, historyId);
    event.config = config;
    return event;
  }

  /**
   * Make a DELETED history event.
   */
  public static BHistoryEvent makeDeleted(BHistoryId historyId)
  {
    return new BHistoryEvent(DELETED, historyId);
  }

  /**
   * Make an APPENDED history event.
   */
  public static BHistoryEvent makeAppended(BHistoryId historyId, BIHistoryRecordSet records)
  {
    BHistoryEvent event = new BHistoryEvent(APPENDED, historyId);
    event.records = records;
    return event;
  }

  /**
   * Make an UPDATED history event.
   */
  public static BHistoryEvent makeUpdated(BHistoryId historyId, BIHistoryRecordSet record)
  {
    BHistoryEvent event = new BHistoryEvent(UPDATED, historyId);
    event.records = record;
    return event;
  }

  /**
   * Make a CLEARED_ALL_RECORDS history event.
   */
  public static BHistoryEvent makeClearedAll(BHistoryId historyId)
  {
    return new BHistoryEvent(CLEARED_ALL_RECORDS, historyId);
  }

  /**
   * Make a CLEARED_OLD_RECORDS history event.
   */
  public static BHistoryEvent makeClearedOld(BHistoryId historyId, BAbsTime clearOldTime)
  {
    BHistoryEvent event = new BHistoryEvent(CLEARED_OLD_RECORDS, historyId);
    event.clearOldTime = clearOldTime;
    return event;
  }

  /**
   * Make a RENAMED history event.
   */
  public static BHistoryEvent makeRenamed(BHistoryId historyId, String newHistoryName)
  {
    BHistoryEvent event = new BHistoryEvent(RENAMED, historyId);
    event.newHistoryName = newHistoryName;
    return event;
  }

  /**
   * Make a RESIZED history event.
   */
  public static BHistoryEvent makeResized(BHistoryId historyId, BCapacity capacity, BFullPolicy fullPolicy)
  {
    BHistoryEvent event = new BHistoryEvent(RESIZED, historyId);
    event.capacity = capacity;
    event.fullPolicy = fullPolicy;
    return event;
  }

  /**
   * Make a DB_OPENED history event.
   */
  public static BHistoryEvent makeDbOpened()
  {
    return dbOpenedEvent;
  }

  /**
   * Make a DB_CLOSED history event.
   */
  public static BHistoryEvent makeDbClosed()
  {
    return dbClosedEvent;
  }

  /**
   * Make a DB_FLUSHED history event.
   */
  public static BHistoryEvent makeDbFlushed()
  {
    return dbFlushedEvent;
  }

  /**
   * Make a DB_SAVED history event.
   */
  public static BHistoryEvent makeDbSaved()
  {
    return dbSavedEvent;
  }

  /**
   * Make a CONFIG_CHANGED history event.
   *
   * @since Niagara 3.5
   */
  public static BHistoryEvent makeConfigChanged(BHistoryId historyId, BHistoryConfig config)
  {
    BHistoryEvent event = new BHistoryEvent(CONFIG_CHANGED, historyId);
    event.config = config;
    return event;
  }

  /**
   * The default encoding for history events does not
   * use the shorthand version for the history ids.  This means
   * the encoding is slightly bloated, but necessary for decoding in some
   * cases.  You can use this factory method to return a shorthand version
   * of the given history event (if useShorthand is true) which
   * enables shorthand encoding of history ids.  A false parameter indicates
   * the (default) full history id encoding will be used.  Be aware that if
   * this history event is being encoded to be passed to another station, you
   * may not want to use shorthand encoding since the device name
   * will be lost on decode.  So shorthand events should only be used for local
   * history events.
   *
   * @since Niagara 3.6
   */
  public static BHistoryEvent makeShorthand(BHistoryEvent event, boolean useShorthand)
  {
    BHistoryEvent result = new BHistoryEvent();
    result.eventId = event.eventId;
    result.historyId = event.historyId;
    result.records = event.records;
    result.clearOldTime = event.clearOldTime;
    result.newHistoryName = event.newHistoryName;
    result.capacity = event.capacity;
    result.fullPolicy = event.fullPolicy;
    result.config = event.config;
    result.useShorthand = useShorthand;
    return result;
  }
  
  /**
   * Permit caller to substitute an alternative history ID string to that of the one found in the
   * HistoryEvent provided. Will internally call makeShorthand() passing the provided argument,
   * applying the provided ID afterwords. This is used to store history events in the data recovery
   * service and generally has no use outside of that workflow.
   *
   * @since Niagara 4.10
   */
  public static BHistoryEvent makeStorageEvent(BHistoryEvent event, BIEncodable alternativeEncodable, boolean useShorthand)
  {
    BHistoryEvent result = makeShorthand(event, useShorthand);
    result.alternativeEncodable = alternativeEncodable;
    return result;
  }
  
  /**
   * Permit caller to substitute an alternative history ID to that of the one found in the
   * HistoryEvent provided. Will internally call makeShorthand() passing the provided argument,
   * applying the provided ID afterwords. This is used to replay data recovery history events
   * to the history space and generally has no use outside of that workflow.
   *
   * @since Niagara 4.10
   */
  public static BHistoryEvent makeReplayEvent(BHistoryEvent event, BHistoryId alternativeId, boolean useShorthand)
  {
    BHistoryEvent result = makeShorthand(event, useShorthand);
    result.historyId = alternativeId;
    return result;
  }
  
////////////////////////////////////////////////////////////////
// Creation
////////////////////////////////////////////////////////////////

  /**
   * Constructor for a history database event.
   */
  public BHistoryEvent(int eventId)
  {
    this.eventId = eventId;
  }

  /**
   * Constructor for a history event.
   */
  public BHistoryEvent(int eventId, BHistoryId historyId)
  {
    this.eventId = eventId;
    this.historyId = historyId;
  }

  /**
   * Public no arg constructor.  Framework use only!
   */
  public BHistoryEvent()
  {
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the event id constant.
   */
  public int getId() { return eventId; }

  /**
   * Get the history Ids for the event.
   */
  public BHistoryId getHistoryId() { return historyId; }

  /**
   * Get the history records for the event.  Only valid
   * for APPENDED or UPDATED history events.
   */
  public BIHistoryRecordSet getRecordSet() { return records; }

  /**
   * Get the clear old time.  Only valid for the
   * CLEARED_OLD_RECORDS history event.
   */
  public BAbsTime getClearOldTime() { return clearOldTime; }

  /**
   * Get the new history name (for a rename, this is the new
   * history name, the history Id will contain the old name).
   * Only valid for the RENAMED history event.
   */
  public String getNewHistoryName() { return newHistoryName; }

  /**
   * Get the new capacity for the history.  Only valid for the
   * RESIZED history event.
   */
  public BCapacity getCapacity() { return capacity; }

  /**
   * Get the new full policy for the history.  Only valid for the
   * RESIZED history event.
   */
  public BFullPolicy getFullPolicy() { return fullPolicy; }

  /**
   * Get the new history config for the history.  Only valid for the
   * CREATED and CONFIG_CHANGED history events.
   *
   * @since Niagara 3.5
   */
  public BHistoryConfig getConfig() { return config; }

  /**
   * Returns whether shorthand encoding is used for the history id
   * of this history event.
   *
   * @since Niagara 3.6
   */
  public boolean getUseShorthandEncoding()
  {
    return useShorthand;
  }

  /**
   * To debug string.
   */
  @Override
  public String toString(Context cx)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(ID_STRINGS[eventId]).append(" ");

    if (historyId != null)
      sb.append(historyId.toString());

    if (records != null)
      sb.append(": ").append(records.toString());

    if (clearOldTime != null)
      sb.append(": ").append(clearOldTime.toString());

    if (newHistoryName != null)
      sb.append(": ").append(newHistoryName);

    if (capacity != null)
      sb.append(": ").append(capacity.toString());

    if (fullPolicy != null)
      sb.append(": ").append(fullPolicy.toString());

    return sb.toString();
  }


////////////////////////////////////////////////////////////////
// BIEncodable
////////////////////////////////////////////////////////////////
  
  /**
   * Encode this BHistoryEvent instance to the provided DataOutput
   * stream.
   *
   * @since Niagara 3.5
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeByte(eventId);
    switch(eventId)
    {
      case APPENDED:
      case UPDATED:             idToShorthand().encode(out);
                                int size = records.getRecordCount();
                                // Since the common case is 1 record, we can save space by
                                // encoding the size in two bytes.
                                if (size <= 127)
                                {
                                  out.writeBoolean(true); // indicates single byte was used to encode size
                                  out.writeByte(size);
                                }
                                else
                                {
                                  out.writeBoolean(false); // indicates full integer was used to encode size
                                  out.writeInt(size);
                                }
                                if (size > 0)
                                {
                                  BHistoryRecord rec = records.getRecord(0);
                                  Type t = rec.getType();
                                  Integer tByte = typeToByte.get(t);
                                  boolean knownType = (tByte != null);
                                  out.writeBoolean(knownType);
                                  if (knownType)
                                    out.writeByte(tByte.intValue());
                                  else
                                    t.getTypeSpec().encode(out);
                                }
                                for (int i = 0; i < size; i++)
                                {
                                  records.getRecord(i).write(out);
                                }
                                // No sequence number necessary, as these can
                                // be safely coalesced (as rare as that might be)
                                break;

      case CREATED:
      case CONFIG_CHANGED:      idToShorthand().encode(out);
                                boolean hasConfig = (config != null);
                                if (hasConfig)
                                {
                                  byte[] bytes = ValueByteBuffer.marshal(config);
                                  size = bytes.length;
                                  out.writeInt(size);
                                  if (size > 0)
                                    out.write(bytes);
                                }
                                else
                                  out.writeInt(0); // no bytes to encode
                                out.writeInt(nextSeqNum()); // add sequence number for uniqueness
                                break;

      case DELETED:
      case CLEARED_ALL_RECORDS: idToShorthand().encode(out);
                                out.writeInt(nextSeqNum()); // add sequence number for uniqueness
                                break;

      case CLEARED_OLD_RECORDS: idToShorthand().encode(out);
                                clearOldTime.encode(out);
                                out.writeInt(nextSeqNum()); // add sequence number for uniqueness
                                break;

      case RENAMED:             idToShorthand().encode(out);
                                out.writeUTF(newHistoryName);
                                out.writeInt(nextSeqNum()); // add sequence number for uniqueness
                                break;

      case RESIZED:             idToShorthand().encode(out);
                                capacity.encode(out);
                                fullPolicy.encode(out);
                                out.writeInt(nextSeqNum()); // add sequence number for uniqueness
                                break;

      default: out.writeInt(nextSeqNum()); // add sequence number for uniqueness
               break;
    }
  }

  /**
   * Decode a BHistoryEvent instance from the provided DataInput
   * stream.
   *
   * @since Niagara 3.5
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    int eId = in.readByte();

    switch(eId)
    { // First check to see if it's one of the singleton events

      case DB_OPENED:  in.readInt(); // Read in the sequence number and throw away
                       return dbOpenedEvent;

      case DB_CLOSED:  in.readInt(); // Read in the sequence number and throw away
                       return dbClosedEvent;

      case DB_FLUSHED: in.readInt(); // Read in the sequence number and throw away
                       return dbFlushedEvent;

      case DB_SAVED: in.readInt(); // Read in the sequence number and throw away
                     return dbSavedEvent;

      default: break;
    }

    // Create a new history event with the decoded values
    BHistoryEvent event = new BHistoryEvent(eId);
    switch(eId)
    {
      case APPENDED:
      case UPDATED:             event.historyId = idFromShorthand((BHistoryId)(BHistoryId.DEFAULT.decode(in)));
                                int size = 0;
                                if (in.readBoolean())
                                  size = in.readByte(); // common case
                                else
                                  size = in.readInt(); // size is greater than 127
                                BHistoryRecord[] recs = (size > 1)?new BHistoryRecord[size]:null;
                                Type t = null;
                                if (size > 0)
                                {
                                  if (in.readBoolean())
                                    t = byteToType[in.readByte()];
                                  else
                                    t = ((BTypeSpec)(BTypeSpec.DEFAULT.decode(in))).getResolvedType();
                                }
                                BHistoryRecord rec = null;
                                for (int i = 0; i < size; i++)
                                {
                                  rec = (BHistoryRecord)t.getInstance();
                                  rec = rec.read(in);
                                  if (recs != null) recs[i] = rec;
                                }

                                if (recs == null)
                                  event.records = rec;
                                else
                                  event.records = new BHistoryRecordGroup(recs);
                                break;

      case CREATED:
      case CONFIG_CHANGED:      event.historyId = idFromShorthand((BHistoryId)(BHistoryId.DEFAULT.decode(in)));
                                size = in.readInt();
                                if (size > 0)
                                {
                                  byte[] bytes = new byte[size];
                                  in.readFully(bytes);
                                  try
                                  {
                                    event.config = (BHistoryConfig)(ValueByteBuffer.unmarshal(bytes));
                                  }
                                  catch (IOException io) { throw io; }
                                  catch (Exception e) { e.printStackTrace(); }
                                }
                                in.readInt(); // Read in the sequence number and throw away
                                break;

      case DELETED:
      case CLEARED_ALL_RECORDS: event.historyId = idFromShorthand((BHistoryId)(BHistoryId.DEFAULT.decode(in)));
                                in.readInt(); // Read in the sequence number and throw away
                                break;

      case CLEARED_OLD_RECORDS: event.historyId = idFromShorthand((BHistoryId)(BHistoryId.DEFAULT.decode(in)));
                                event.clearOldTime = (BAbsTime)(BAbsTime.DEFAULT.decode(in));
                                in.readInt(); // Read in the sequence number and throw away
                                break;

      case RENAMED:             event.historyId = idFromShorthand((BHistoryId)(BHistoryId.DEFAULT.decode(in)));
                                event.newHistoryName = in.readUTF();
                                in.readInt(); // Read in the sequence number and throw away
                                break;

      case RESIZED:             event.historyId = idFromShorthand((BHistoryId)(BHistoryId.DEFAULT.decode(in)));
                                event.capacity = (BCapacity)(BCapacity.DEFAULT.decode(in));
                                event.fullPolicy = (BFullPolicy)(BFullPolicy.roll.decode(in));
                                in.readInt(); // Read in the sequence number and throw away
                                break;

      default: break;
    }

    return event;
  }

  /**
   * Encode this BHistoryEvent instance to a String representation.
   *
   * @since Niagara 3.5
   */
  @Override
  public String encodeToString()
    throws IOException
  {
     ByteBuffer buf = new ByteBuffer();
     encode(buf);
     return java.util.Base64.getEncoder().encodeToString(buf.toByteArray());
  }

  /**
   * Decode a BHistoryEvent instance from its String representation.
   *
   * @since Niagara 3.5
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    ByteBuffer buf = new ByteBuffer(java.util.Base64.getDecoder().decode(s));
    return decode(buf);
  }

  // The sequence number is encoded with some BHistoryEvents so
  // that the data recovery service won't try to coalesce events.
  // That's the only reason for the sequence number, it isn't
  // necessary to encode/decode a BHistoryEvent, but just ensures
  // that an encoded BHistoryEvent is unique.
  private static Object seqLock = new Object();
  private static int seqNum = 0;
  private static int nextSeqNum() { synchronized(seqLock) { return seqNum++; } }

  /**
   * Convenience to convert the history id to its shorthand version.
   *
   * @since Niagara 3.6
   */
  private BIEncodable idToShorthand()
  {
    //NCCB-44646: Allow for data recovery service storage to provide a custom ID encoding
    //            that avoids creating superfluous BHistoryId entries in the intern pool
    if (alternativeEncodable != null)
    {
      //Use the ID string as provided by the user
      return alternativeEncodable;
    }
   
    //Else use the standard ID string mapping
    BHistoryId id = historyId;
    try
    {
      if (getUseShorthandEncoding()) // use shorthand history id in this case
        id = id.toShorthand(Sys.getStation().getStationName());
      else // always use full history id for encoding in this case
        id = id.fromShorthand(Sys.getStation().getStationName());
    }
    catch(Throwable t) {}
    
    return id;
  }

  /**
   * Convenience to convert the history id from its shorthand version.
   *
   * @since Niagara 3.6
   */
  private static BHistoryId idFromShorthand(BHistoryId id)
  {
    try
    {
      id = id.fromShorthand(Sys.getStation().getStationName());
    }
    catch(Throwable t) {}
    return id;
  }

////////////////////////////////////////////////////////////////
//Object
////////////////////////////////////////////////////////////////

  @Override
  public Object clone()
  {
    BHistoryEvent event = new BHistoryEvent();
    event.eventId =  this.eventId;
    event.historyId = this.historyId;
    event.records = this.records;
    event.clearOldTime = this.clearOldTime;
    event.newHistoryName = this.newHistoryName;
    event.capacity = this.capacity;
    event.fullPolicy = this.fullPolicy;
    event.config = this.config;
    
    //NOTE: Should clone include useShorthand? alternativeId?

    return event;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  // Common history record types are encoded in a single byte,
  // so these two statics are used for that type to byte mapping
  private static Hashtable<Type,Integer> typeToByte = new Hashtable<>(6);
  static
  {
    typeToByte.put(BBooleanTrendRecord.TYPE, Integer.valueOf(0));
    typeToByte.put(BEnumTrendRecord.TYPE,    Integer.valueOf(1));
    typeToByte.put(BNumericTrendRecord.TYPE, Integer.valueOf(2));
    typeToByte.put(BStringTrendRecord.TYPE,  Integer.valueOf(3));
    typeToByte.put(BAuditRecord.TYPE,        Integer.valueOf(4));
    typeToByte.put(BLogRecord.TYPE,          Integer.valueOf(5));
    typeToByte.put(BSecurityAuditRecord.TYPE,Integer.valueOf(6));
    
    //TODO: Other "large" history type names to consider that are common in provisioning:
    //com.tridium.batchJob.driver.history.BDeviceStepHistoryRecord
    //com.tridium.batchJob.driver.history.BNetworkStepHistoryRecord
    //com.tridium.batchJob.driver.history.BDeviceNetworkJobHistoryRecord
  }

  private static Type[] byteToType = new Type[] { BBooleanTrendRecord.TYPE,
                                                  BEnumTrendRecord.TYPE,
                                                  BNumericTrendRecord.TYPE,
                                                  BStringTrendRecord.TYPE,
                                                  BAuditRecord.TYPE,
                                                  BLogRecord.TYPE,
                                                  BSecurityAuditRecord.TYPE
                                                };

  // Certain events can be interned (singletons)
  static BHistoryEvent dbOpenedEvent = new BHistoryEvent(DB_OPENED);
  static BHistoryEvent dbClosedEvent = new BHistoryEvent(DB_CLOSED);
  static BHistoryEvent dbFlushedEvent = new BHistoryEvent(DB_FLUSHED);
  static BHistoryEvent dbSavedEvent = new BHistoryEvent(DB_SAVED);

  private int eventId;
  private BHistoryId historyId = null;
  private BIHistoryRecordSet records = null;
  private BAbsTime clearOldTime = null;
  private String newHistoryName = null;
  private BCapacity capacity = null;
  private BFullPolicy fullPolicy = null;
  private BHistoryConfig config = null;
  
  //Meta-informational attributes, affects encoding
  private boolean useShorthand = false;
  private BIEncodable alternativeEncodable = null;
}
