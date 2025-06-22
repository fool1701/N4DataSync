/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.history.BHistoryRecord;
import javax.baja.history.BTrendFlags;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.sys.*;
import javax.baja.util.BTypeSpec;
import javax.baja.util.Lexicon;

import com.tridium.bacnet.asn.AsnConst;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.datatypes.BTrendEvent;
import com.tridium.bacnet.history.*;

/**
 * BBacnetLogRecord represents the BacnetLogRecord sequence.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 18 Sep 02
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "timestamp",
  type = "BBacnetDateTime",
  defaultValue = "new BBacnetDateTime()"
)
@NiagaraProperty(
  name = "logDatum",
  type = "BSimple",
  defaultValue = "BBacnetNull.DEFAULT"
)
@NiagaraProperty(
  name = "statusFlags",
  type = "BBacnetBitString",
  defaultValue = "BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength(\"BacnetStatusFlags\"))",
  facets = @Facet("BacnetBitStringUtil.BACNET_STATUS_FLAGS_FACETS")
)
public final class BBacnetLogRecord
  extends BStruct
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetLogRecord(2386279730)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "timestamp"

  /**
   * Slot for the {@code timestamp} property.
   * @see #getTimestamp
   * @see #setTimestamp
   */
  public static final Property timestamp = newProperty(0, new BBacnetDateTime(), null);

  /**
   * Get the {@code timestamp} property.
   * @see #timestamp
   */
  public BBacnetDateTime getTimestamp() { return (BBacnetDateTime)get(timestamp); }

  /**
   * Set the {@code timestamp} property.
   * @see #timestamp
   */
  public void setTimestamp(BBacnetDateTime v) { set(timestamp, v, null); }

  //endregion Property "timestamp"

  //region Property "logDatum"

  /**
   * Slot for the {@code logDatum} property.
   * @see #getLogDatum
   * @see #setLogDatum
   */
  public static final Property logDatum = newProperty(0, BBacnetNull.DEFAULT, null);

  /**
   * Get the {@code logDatum} property.
   * @see #logDatum
   */
  public BSimple getLogDatum() { return (BSimple)get(logDatum); }

  /**
   * Set the {@code logDatum} property.
   * @see #logDatum
   */
  public void setLogDatum(BSimple v) { set(logDatum, v, null); }

  //endregion Property "logDatum"

  //region Property "statusFlags"

  /**
   * Slot for the {@code statusFlags} property.
   * @see #getStatusFlags
   * @see #setStatusFlags
   */
  public static final Property statusFlags = newProperty(0, BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength("BacnetStatusFlags")), BacnetBitStringUtil.BACNET_STATUS_FLAGS_FACETS);

  /**
   * Get the {@code statusFlags} property.
   * @see #statusFlags
   */
  public BBacnetBitString getStatusFlags() { return (BBacnetBitString)get(statusFlags); }

  /**
   * Set the {@code statusFlags} property.
   * @see #statusFlags
   */
  public void setStatusFlags(BBacnetBitString v) { set(statusFlags, v, null); }

  //endregion Property "statusFlags"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetLogRecord.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  public BBacnetLogRecord()
  {
  }

  public BBacnetLogRecord(BBacnetDateTime dt, BSimple s)
  {
    setTimestamp(dt);
    setLogDatum(s);
  }

  public BBacnetLogRecord(BAbsTime bt, BSimple s)
  {
    getTimestamp().fromBAbsTime(bt);
    setLogDatum(s);
  }


////////////////////////////////////////////////////////////////
//  BIBacnetDataType
////////////////////////////////////////////////////////////////

  /**
   * Write the value to the Asn output stream.
   *
   * @param out the AsnOutput stream.
   */
  public void writeAsn(AsnOutput out)
  {
    out.writeOpeningTag(TIMESTAMP_TAG);
    getTimestamp().writeAsn(out);
    out.writeClosingTag(TIMESTAMP_TAG);
    out.writeOpeningTag(LOG_DATUM_TAG);
    writeLogDatum(out,
      getLogDatum(),
      getLogDatumType(),
      getLogDatumEvent().getLong(),
      BacnetBitStringUtil.getBStatus(getStatusFlags()));
    out.writeClosingTag(LOG_DATUM_TAG);
    out.writeBitString(STATUS_FLAGS_TAG, getStatusFlags());
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public void readAsn(AsnInput in)
    throws AsnException
  {
    recType = null;
    int tag = in.peekTag();
    if (in.isOpeningTag(TIMESTAMP_TAG))
    {
      in.skipTag();
      getTimestamp().readAsn(in);
      in.skipTag();
    }
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);
    tag = in.peekTag();
    if (in.isOpeningTag(LOG_DATUM_TAG))
    {
      in.skipTag();
      int logDatumChoice = in.peekTag();
      switch (logDatumChoice)
      {
        case BOOLEAN_VALUE_TAG:
          set(logDatum, BBoolean.make(in.readBoolean(BOOLEAN_VALUE_TAG)), noWrite);
          recType = BTypeSpec.make("bacnet", "BacnetBooleanTrendRecord");
          break;
        case REAL_VALUE_TAG:
          set(logDatum, BFloat.make(in.readReal(REAL_VALUE_TAG)), noWrite);
          recType = BTypeSpec.make("bacnet", "BacnetNumericTrendRecord");
          break;
        case LOG_STATUS_TAG:
          set(logDatum, BTrendEvent.makeLogStatus(in.readBitString(LOG_STATUS_TAG)), noWrite);
          break;
        case ENUM_VALUE_TAG:
          set(logDatum, BDynamicEnum.make(in.readEnumerated(ENUM_VALUE_TAG)), noWrite);
          recType = BTypeSpec.make("bacnet", "BacnetEnumTrendRecord");
          break;
        case UNSIGNED_VALUE_TAG:
          set(logDatum, in.readUnsigned(UNSIGNED_VALUE_TAG), noWrite);
          recType = BTypeSpec.make("bacnet", "BacnetNumericTrendRecord");
          break;
        case SIGNED_VALUE_TAG:
          set(logDatum, in.readSigned(SIGNED_VALUE_TAG), noWrite);
          recType = BTypeSpec.make("bacnet", "BacnetNumericTrendRecord");
          break;
        case BITSTRING_VALUE_TAG:
          set(logDatum, in.readBitString(BITSTRING_VALUE_TAG), noWrite);
          recType = BTypeSpec.make("bacnet", "BacnetStringTrendRecord");
          break;
        case FAILURE_TAG:
          // Create a new NErrorType, and then cast to a string using encodeToString.

          //  Skip the opening tag
          in.skipTag();
          NErrorType failure = new NErrorType();
          failure.readEncoded(in);

          //  Skip the closing tag
          in.skipTag();
          set(logDatum, BTrendEvent.makeFailure(failure), noWrite);
          break;
        case TIME_CHANGE_TAG:
          set(logDatum, BTrendEvent.makeTimeChange((long)in.readReal(TIME_CHANGE_TAG)), noWrite);
          break;
        case NULL_VALUE_TAG:
          set(logDatum, in.readNull(NULL_VALUE_TAG), noWrite);
//          recType = BTypeSpec.make("bacnet", "BacnetStringTrendRecord");
          break;
        case ANY_VALUE_TAG:
          //  Skip the opening tag
          in.skipOpeningTag(ANY_VALUE_TAG);
          if (in.peekApplicationTag() == ASN_CHARACTER_STRING)
          {
            set(logDatum, BString.make(in.readCharacterString()), noWrite);
          }
          else
          {
            while (in.available() > 0 && !in.isClosingTag(ANY_VALUE_TAG))
              in.skipTag();

            loggerBacnetDebug.info(this + ".readAsn:logDatumChoice " + logDatumChoice + " not yet supported");
            set(logDatum, BBacnetNull.DEFAULT, noWrite);
          }
          in.skipClosingTag(ANY_VALUE_TAG);
          recType = BTypeSpec.make("bacnet", "BacnetStringTrendRecord");
          break;
        default:
          loggerBacnet.info("Invalid logDatumChoice!");
      }
      in.skipTag();
    }
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);

    int statusChoice = in.peekTag();
    if (statusChoice == STATUS_FLAGS_TAG)
    {
      set(statusFlags, in.readBitString(STATUS_FLAGS_TAG), noWrite);
    }
  }


////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getTimestamp().toString(context)).append('_')
      .append(getLogDatum().toString(context)).append('_')
      .append(getStatusFlags().toString(getSlotFacets(statusFlags)));
    return sb.toString();
  }


////////////////////////////////////////////////////////////////
//  Support
////////////////////////////////////////////////////////////////

  private int getLogDatumType()
  {
    Type t = getLogDatum().getType();
    if (t.is(BBoolean.TYPE)) return BOOLEAN_VALUE_TAG;
    if ((t.is(BDouble.TYPE)) || (t.is(BFloat.TYPE))) return REAL_VALUE_TAG;
    if (getLogDatum() instanceof BEnum) return ENUM_VALUE_TAG;
    if (t.is(BBacnetUnsigned.TYPE)) return UNSIGNED_VALUE_TAG;
    if (t.is(BInteger.TYPE)) return SIGNED_VALUE_TAG;
    if (t.is(BBacnetBitString.TYPE)) return BITSTRING_VALUE_TAG;
    if (t.is(BBacnetNull.TYPE)) return NULL_VALUE_TAG;
    if (t.is(BTrendEvent.TYPE))
    {
      BTrendEvent evt = (BTrendEvent)getLogDatum();
      if (evt.isLogStatus())
        return LOG_STATUS_TAG;
      if (evt.isFailure())
        return FAILURE_TAG;
      if (evt.isTimeChange())
        return TIME_CHANGE_TAG;
    }
    return ANY_VALUE_TAG; // FIXX: handle special cases.
  }

  private BTrendEvent getLogDatumEvent()
  {
    Type t = getLogDatum().getType();
    if (t.is(BTrendEvent.TYPE))
    {
      return (BTrendEvent)getLogDatum();
    }
    return BTrendEvent.DEFAULT;
  }


////////////////////////////////////////////////////////////////
//  Utility
////////////////////////////////////////////////////////////////

  public static void writeLogRecord(BBacnetDateTime timestamp,
                                    BSimple logDatum,
                                    int logDatumChoice,
                                    BBacnetBitString statusFlags,
                                    long trendEvent,
                                    AsnOutput out)
  {
    out.writeOpeningTag(TIMESTAMP_TAG);
    timestamp.writeAsn(out);
    out.writeClosingTag(TIMESTAMP_TAG);
    out.writeOpeningTag(LOG_DATUM_TAG);
    writeLogDatum(out,
      logDatum,
      logDatumChoice,
      trendEvent,
      BacnetBitStringUtil.getBStatus(statusFlags));
    out.writeClosingTag(LOG_DATUM_TAG);
    out.writeBitString(STATUS_FLAGS_TAG, statusFlags);
  }

  /**
   * Write a BACnetLogRecord to the stream.
   * <p>
   * The encoded size of a BACnetLogRecord is:
   * 1 (timestamp opening tag)
   * 4 (date)
   * 4 (time)
   * 1 (timestamp closing tag)
   * 1 (log datum opening tag)
   * X (log datum)
   * 1 (log datum closing tag)
   * +   3 (status flags)
   * ------------------------------
   * = 15 + X where X is the encoded logDatum size
   *
   * @param timestamp
   * @param logDatum
   * @param logDatumChoice
   * @param statusFlags
   * @param trendEvent
   * @param out
   */
  public static void writeLogRecord(BAbsTime timestamp,
                                    BSimple logDatum,
                                    int logDatumChoice,
                                    BStatus statusFlags,
                                    long trendEvent,
                                    AsnOutput out)
  {
    out.writeOpeningTag(TIMESTAMP_TAG);
    out.writeDate(timestamp);
    out.writeTime(timestamp);
    out.writeClosingTag(TIMESTAMP_TAG);
    out.writeOpeningTag(LOG_DATUM_TAG);
    writeLogDatum(out, logDatum, logDatumChoice, trendEvent, statusFlags);
    out.writeClosingTag(LOG_DATUM_TAG);
    out.writeBitString(STATUS_FLAGS_TAG, BacnetBitStringUtil.getBacnetStatusFlags(statusFlags)); // fixx?
  }

  private static void writeLogDatum(AsnOutput out,
                                    BSimple logDatum,
                                    int logDatumChoice,
                                    long trendEvent,
                                    BStatus status)
  {
    try
    {
      if (status.isNull())
      {
        out.writeNull(NULL_VALUE_TAG);
        return;
      }



      if((trendEvent & BBacnetErrorCode.NULL_VALUE_EVENT) == BBacnetErrorCode.NULL_VALUE_EVENT)
      {
        trendEvent = BTrendEvent.NO_EVENT;
        logDatumChoice = BBacnetLogRecord.NULL_VALUE_TAG;
      }


      switch (logDatumChoice)
      {
        case BOOLEAN_VALUE_TAG:
          if (logDatum instanceof BBoolean)
            out.writeBoolean(BOOLEAN_VALUE_TAG, (BBoolean)logDatum);
          else if (logDatum instanceof BEnum)
            out.writeBoolean(BOOLEAN_VALUE_TAG, ((BEnum)logDatum).isActive());
          //out.writeNull();
          break;
        case REAL_VALUE_TAG:
          out.writeReal(REAL_VALUE_TAG, (BNumber)logDatum);
          break;
        case LOG_STATUS_TAG:
          out.writeBitString(LOG_STATUS_TAG, BTrendEvent.getLogStatus(trendEvent));
          break;
        case ENUM_VALUE_TAG:
          out.writeEnumerated(ENUM_VALUE_TAG, (BEnum)logDatum);
          break;
        case FAILURE_TAG:
          // must remember to write enclosing tags.
          NErrorType failure = BTrendEvent.getFailure(trendEvent);
          out.writeOpeningTag(FAILURE_TAG);
          out.writeEnumerated(failure.getErrorClass());
          out.writeEnumerated(failure.getErrorCode());
          out.writeClosingTag(FAILURE_TAG);
          break;
        case TIME_CHANGE_TAG:
          out.writeReal(TIME_CHANGE_TAG, BTrendEvent.getTimeChange(trendEvent));
          break;
        case UNSIGNED_VALUE_TAG:
          if (logDatum instanceof BNumber)
            out.writeUnsignedInteger(UNSIGNED_VALUE_TAG, ((BNumber)logDatum).getLong());
          else if (logDatum instanceof BEnum)
            out.writeUnsignedInteger(UNSIGNED_VALUE_TAG, ((BEnum)logDatum).getOrdinal());
          break;
        case SIGNED_VALUE_TAG:
          if (logDatum instanceof BNumber)
            out.writeSignedInteger(SIGNED_VALUE_TAG, ((BNumber)logDatum).getInt());
          else if (logDatum instanceof BEnum)
            out.writeSignedInteger(SIGNED_VALUE_TAG, ((BEnum)logDatum).getOrdinal());
          break;
        case BITSTRING_VALUE_TAG:
          if (logDatum instanceof BBacnetBitString)
            out.writeBitString(BITSTRING_VALUE_TAG, (BBacnetBitString)logDatum);
          else if (logDatum instanceof BString)
          {
            try
            {
              BBacnetBitString bs = (BBacnetBitString)BBacnetBitString.DEFAULT.decodeFromString(logDatum.toString());
              out.writeBitString(BITSTRING_VALUE_TAG, bs);
            }
            catch (IOException e)
            {
              out.writeOpeningTag(FAILURE_TAG);
              out.writeEnumerated(BBacnetErrorClass.OBJECT);
              out.writeEnumerated(BBacnetErrorCode.OTHER);
              out.writeClosingTag(FAILURE_TAG);
            }
          }
          break;
        case NULL_VALUE_TAG:
          out.writeNull(NULL_VALUE_TAG);
          break;
        case ANY_VALUE_TAG:
          if (logDatum instanceof BString)
          {
            out.writeOpeningTag(ANY_VALUE_TAG);
            out.writeCharacterString((BString)logDatum);
            out.writeClosingTag(ANY_VALUE_TAG);
          }
          else
          {
            loggerBacnetDebug.info("BacnetLogRecord.writeLogDatum:logDatumChoice " +
              logDatumChoice + " not yet supported: logDatum=" + logDatum + " [" + logDatum.getType() + "]");
          }
          break;
        default:
          loggerBacnet.info("Invalid logDatumChoice!");
      }
    }
    catch (ClassCastException e)
    {
      loggerBacnet.log(Level.INFO, "Incompatible logDatum/logDatumChoice!", e);
    }
  }

  public BTypeSpec getNiagaraRecordType()
  {
    return recType;
  }

  public BHistoryRecord initializeNiagaraRecord(BHistoryRecord record, long seqNum)
  {
    BBacnetTrendRecord rec = (BBacnetTrendRecord)record;
    rec.setTimestamp(getTimestamp().toBAbsTime());
    rec.setStatus(BacnetBitStringUtil.getBStatus(getStatusFlags()));
    rec.setSequenceNumber(seqNum);

    if (getLogDatum() instanceof BBacnetNull)
    {
      rec.setLogEvent(BTrendEvent.DEFAULT);
      rec.setStatus(BStatus.makeNull(rec.getStatus(), true));
      return rec;
    }

    if (recType == null) // This indicates an event
    {
      rec.setLogEvent((BTrendEvent)getLogDatum());
      // Always set the hidden flag for events
      rec.setTrendFlags(rec.getTrendFlags().set(BTrendFlags.HIDDEN, true));
      return rec;
    }
    Type t = rec.getType();
    if (t == BBacnetBooleanTrendRecord.TYPE)
    {
      BSimple ld = getLogDatum();
      if (ld.getType().is(BDynamicEnum.TYPE))
      {
        boolean value = false;
        boolean trueOrFalse = false;

        //This is for the Binary-I/O/V case where present
        //value should be and enumerated true or false
        //if we are attempting to convert a enumerated value
        //to a BooleanHistoryRecord that is not either 0 or 1, log the error.

        BDynamicEnum datum = (BDynamicEnum)ld;
        int val = datum.getOrdinal();
        if (val == 0 || val == 1)
        {
          value = val != 0;
          trueOrFalse = true;
        }

        if (trueOrFalse)
          ((BBacnetBooleanTrendRecord)rec).setValue(value);
        else
          loggerBacnet.info("Error, could not encode non-zero/one enumerated logDatum to a BBooleanHistoryRecord (" + (getLogDatum()).toString() + ")");

      }
      else if (ld.getType().is(BBoolean.TYPE))
      {
        ((BBacnetBooleanTrendRecord)rec).setValue(((BBoolean)getLogDatum()).getBoolean());
      }
      else
      {
        loggerBacnet.info("Error, could not encode logDatum to a BBooleanHistoryRecord (" + (getLogDatum()).toString() + ")");
      }
    }
    else if (t == BBacnetNumericTrendRecord.TYPE)
    {
      BSimple ld = getLogDatum();
      if (ld.getType().is(BBacnetUnsigned.TYPE))
        ((BBacnetNumericTrendRecord)rec).setValue((double)((BBacnetUnsigned)getLogDatum()).getLong());
      else
        ((BBacnetNumericTrendRecord)rec).setValue(((BNumber)getLogDatum()).getDouble());
    }
    else if (t == BBacnetEnumTrendRecord.TYPE)
    {
      BSimple ld = getLogDatum();
      if (ld.getType().is(BBacnetUnsigned.TYPE))
      {
        ((BBacnetEnumTrendRecord)rec).setValue(BDynamicEnum.make(((BBacnetUnsigned)getLogDatum()).getInt()));

      }
      else if (ld.getType().is(BDynamicEnum.TYPE))
      {
        ((BBacnetEnumTrendRecord)rec).setValue((BDynamicEnum)getLogDatum());
      }
      else
      {
        loggerBacnet.info("Error, could not encode logDatum to a BEnumTrendRecord (" + getLogDatum().toString() + ")");
      }

    }
    else if (t == BBacnetStringTrendRecord.TYPE)
    {
      try
      {
        ((BBacnetStringTrendRecord)rec).setValue(getLogDatum().encodeToString());
      }
      catch (Exception e)
      {
        loggerBacnet.log(Level.INFO, "Error, could not encode logDatum to a string (" + (getLogDatum()).toString() + ")", e);
        ((BBacnetStringTrendRecord)rec).setValue("Error, could not encode " + getLogDatum().toString());
      }
    }
    return rec;
  }

////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  private BTypeSpec recType = null;

  private static final Logger loggerBacnetDebug = Logger.getLogger("bacnet.debug");
  private static final Logger loggerBacnet = Logger.getLogger("bacnet");

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int TIMESTAMP_TAG = 0;
  public static final int LOG_DATUM_TAG = 1;
  public static final int STATUS_FLAGS_TAG = 2;

  public static final int LOG_STATUS_TAG = 0;
  public static final int BOOLEAN_VALUE_TAG = 1;
  public static final int REAL_VALUE_TAG = 2;
  public static final int ENUM_VALUE_TAG = 3;
  public static final int UNSIGNED_VALUE_TAG = 4;
  public static final int SIGNED_VALUE_TAG = 5;
  public static final int BITSTRING_VALUE_TAG = 6;
  public static final int NULL_VALUE_TAG = 7;
  public static final int FAILURE_TAG = 8;
  public static final int TIME_CHANGE_TAG = 9;
  public static final int ANY_VALUE_TAG = 10;

  private static Lexicon lex = Lexicon.make("bacnet");
  public static final String LOG_STATUS_STRING = lex.getText("BacnetLogRecord.status");
  public static final String FAILURE_STRING = lex.getText("BacnetLogRecord.failure");
  public static final String TIME_CHANGE_STRING = lex.getText("BacnetLogRecord.timeChange");
  public static final String EVENT_STRING = lex.getText("BacnetLogRecord.event");
  public static final String INVALID_STRING = lex.getText("BacnetLogRecord.invalid");
  public static final String UNKNOWN_STRING = lex.getText("BacnetLogRecord.unknown");
  public static final String LOG_ENABLED_STRING = lex.getText("BacnetLogRecord.enabled");
  public static final String LOG_DISABLED_STRING = lex.getText("BacnetLogRecord.disabled");
  public static final String LOG_BUFFER_PURGED_STRING = lex.getText("BacnetLogRecord.purged");
  public static final String LOG_INTERRUPTED_STRING = lex.getText("BacnetLogRecord.interrupted");
  public static final String SECONDS_STRING = lex.getText("BacnetLogRecord.seconds");

//    public static final String LOG_STATUS_RECORD_ID = "Log Status: ";
//    public static final String FAILURE_RECORD_ID = "Error: ";
//    public static final String TIME_CHANGE_RECORD_ID = "Time Change: ";
//    public static final String LOG_STATUS_ENABLED = LOG_STATUS_RECORD_ID + "true"; // Scott FIXX: Should these be in a lexicon?
//    public static final String LOG_STATUS_DISABLED = LOG_STATUS_RECORD_ID + "false";
//    public static final String LOG_STATUS_ENABLED_BUFFER_PURGED = LOG_STATUS_RECORD_ID + "true, buffer_purged";
//    public static final String LOG_STATUS_DISABLED_BUFFER_PURGED = LOG_STATUS_RECORD_ID + "false, buffer_purged";

}
