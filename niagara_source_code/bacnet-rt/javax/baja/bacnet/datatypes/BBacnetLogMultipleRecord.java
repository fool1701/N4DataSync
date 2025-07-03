/*
 * Copyright 2010 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.history.BHistoryRecord;
import javax.baja.history.BTrendFlags;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.status.BStatus;
import javax.baja.sys.*;
import javax.baja.util.BTypeSpec;

import com.tridium.bacnet.asn.AsnConst;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.datatypes.BTrendEvent;
import com.tridium.bacnet.history.*;

/**
 * BBacnetLogRecord represents the BacnetLogRecord sequence.
 *
 * @author Robert Adams
 * @version $Revision$ $Date$
 * @creation 22 Mar 2010
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "timestamp",
  type = "BBacnetDateTime",
  defaultValue = "new BBacnetDateTime()"
)
@NiagaraProperty(
  name = "statusFlags",
  type = "BSimple",
  defaultValue = "BBacnetNull.DEFAULT"
)
@NiagaraProperty(
  name = "timeChange",
  type = "BSimple",
  defaultValue = "BBacnetNull.DEFAULT"
)
public class BBacnetLogMultipleRecord
  extends BComponent
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetLogMultipleRecord(786413094)1.0$ @*/
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

  //region Property "statusFlags"

  /**
   * Slot for the {@code statusFlags} property.
   * @see #getStatusFlags
   * @see #setStatusFlags
   */
  public static final Property statusFlags = newProperty(0, BBacnetNull.DEFAULT, null);

  /**
   * Get the {@code statusFlags} property.
   * @see #statusFlags
   */
  public BSimple getStatusFlags() { return (BSimple)get(statusFlags); }

  /**
   * Set the {@code statusFlags} property.
   * @see #statusFlags
   */
  public void setStatusFlags(BSimple v) { set(statusFlags, v, null); }

  //endregion Property "statusFlags"

  //region Property "timeChange"

  /**
   * Slot for the {@code timeChange} property.
   * @see #getTimeChange
   * @see #setTimeChange
   */
  public static final Property timeChange = newProperty(0, BBacnetNull.DEFAULT, null);

  /**
   * Get the {@code timeChange} property.
   * @see #timeChange
   */
  public BSimple getTimeChange() { return (BSimple)get(timeChange); }

  /**
   * Set the {@code timeChange} property.
   * @see #timeChange
   */
  public void setTimeChange(BSimple v) { set(timeChange, v, null); }

  //endregion Property "timeChange"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetLogMultipleRecord.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//BIBacnetDataType
////////////////////////////////////////////////////////////////

  public BBacnetLogMultipleRecord()
  {
  }

  public BBacnetLogMultipleRecord(AsnInput in)
    throws AsnException
  {
    readAsn(in);
  }

  /**
   * Write the value to the Asn output stream.
   *
   * @param out the AsnOutput stream.
   */
  public void writeAsn(AsnOutput out)
  {
    //  out.writeOpeningTag(TIMESTAMP_TAG);
    //  getTimestamp().writeAsn(out);
    //  out.writeClosingTag(TIMESTAMP_TAG);
    //  out.writeOpeningTag(LOG_DATUM_TAG);
    //  writeLogDatum(out,
    //                getLogDatum(),
    //                getLogDatumType(),
    //                getLogDatumEvent().getLong(),
    //                BacnetBitStringUtil.getBStatus(getStatusFlags()));
    //  out.writeClosingTag(LOG_DATUM_TAG);
    //  out.writeBitString(STATUS_FLAGS_TAG, getStatusFlags());
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
    if (in.isOpeningTag(LOG_DATA_TAG))
    {
      in.skipTag();
      logDataChoice = in.peekTag();
      switch (logDataChoice)
      {
        case LOG_STATUS_TAG:
          set(statusFlags, BTrendEvent.makeLogStatus(in.readBitString(LOG_STATUS_TAG)), noWrite);
          break;
        case LOG_DATA_SEQ_TAG:
          in.skipTag();
          processDataSeq(in);
          in.skipTag();
          break;
        case TIME_CHANGE_TAG:
          set(timeChange, BTrendEvent.makeTimeChange((long)in.readReal(TIME_CHANGE_TAG)), noWrite);
          break;
      }
      in.skipTag();
    }
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);
  }

  private void processDataSeq(AsnInput in)
    throws AsnException
  {
    Array<BTypeSpec> al = new Array<>(BTypeSpec.class);
    int n = 0;
    // process until end of sequence
    int tag = in.peekTag();
    while (!in.isClosingTag(LOG_DATA_SEQ_TAG))
    {
      BTypeSpec ts = null;
      switch (tag)
      {
        case BOOLEAN_VALUE_TAG:
          addData(BBoolean.make(in.readBoolean(BOOLEAN_VALUE_TAG)), n);
          ts = BTypeSpec.make("bacnet", "BacnetBooleanTrendRecord");
          break;
        case REAL_VALUE_TAG:
          addData(BFloat.make(in.readReal(REAL_VALUE_TAG)), n);
          ts = BTypeSpec.make("bacnet", "BacnetNumericTrendRecord");
          break;
        case ENUM_VALUE_TAG:
          addData(BDynamicEnum.make(in.readEnumerated(ENUM_VALUE_TAG)), n);
          ts = BTypeSpec.make("bacnet", "BacnetEnumTrendRecord");
          break;
        case UNSIGNED_VALUE_TAG:
          addData(in.readUnsigned(UNSIGNED_VALUE_TAG), n);
          ts = BTypeSpec.make("bacnet", "BacnetNumericTrendRecord");
          break;
        case SIGNED_VALUE_TAG:
          addData(in.readSigned(SIGNED_VALUE_TAG), n);
          ts = BTypeSpec.make("bacnet", "BacnetNumericTrendRecord");
          break;
        case BITSTRING_VALUE_TAG:
          addData(in.readBitString(BITSTRING_VALUE_TAG), n);
          ts = BTypeSpec.make("bacnet", "BacnetStringTrendRecord");
          break;
        case FAILURE_TAG:
          // Create a new NErrorType, and then cast to a string using encodeToString.

          //  Skip the opening tag
          in.skipTag();
          NErrorType failure = new NErrorType();
          failure.readEncoded(in);

          //  Skip the closing tag
          in.skipTag();
          addData(BTrendEvent.makeFailure(failure), n);
          break;
        case NULL_VALUE_TAG:
          addData(in.readNull(NULL_VALUE_TAG), n);
          ts = BTypeSpec.make("bacnet", "BacnetNull");
          break;
        case ANY_VALUE_TAG:
          loggerBacnetDebug.info(this + ".readAsn:logDataChoice " + tag + " not yet supported");
          addData(BBacnetNull.DEFAULT, n);
          ts = BTypeSpec.make("bacnet", "BacnetStringTrendRecord");
          break;
        default:
          loggerBacnet.info("Invalid logDatumChoice!");
      } //switch
      al.add(ts);
      n++;
      tag = in.peekTag();
    }
    recType = al.trim();
  }

  private void addData(BSimple d, int n)
  {
    add(getSeqName(n), d, noWrite);
  }

  private String getSeqName(int n)
  {
    return "data" + n;
  }

  public BTypeSpec getNiagaraRecordType(int n)
  {
    return recType[n];
  }

  ////////////////////////////////////////////////////////////////
//Attributes
////////////////////////////////////////////////////////////////
  public BHistoryRecord initializeNiagaraRecord(BHistoryRecord record, long seqNum, int ndx)
  {
    BBacnetTrendRecord rec = (BBacnetTrendRecord)record;
    rec.setTimestamp(getTimestamp().toBAbsTime());
// TODO    rec.setStatus(BacnetBitStringUtil.getBStatus(getStatusFlags()));
    rec.setSequenceNumber(seqNum);

    if (!isLogData())
    {
      if (isLogStatus())
        rec.setLogEvent((BTrendEvent)getStatusFlags());
      else
        rec.setLogEvent((BTrendEvent)getTimeChange());
      rec.setTrendFlags(rec.getTrendFlags().set(BTrendFlags.HIDDEN, true)); // Always set the hidden flag for events!
      return rec;
    }

    BSimple o = (BSimple)get(getSeqName(ndx));
    rec.setLogEvent(BTrendEvent.DEFAULT);
    Type t = rec.getType();
    if (t == BBacnetBooleanTrendRecord.TYPE)
    {
      if (o instanceof BBacnetNull)
      {
        ((BBacnetBooleanTrendRecord)rec).setValue(false);
        rec.setLogEvent(BTrendEvent.DEFAULT);
        rec.setStatus(BStatus.makeNull(rec.getStatus(), true));
      }
      else
      {
        ((BBacnetBooleanTrendRecord)rec).setValue(((BBoolean)o).getBoolean());
      }
    }
    else if (t == BBacnetNumericTrendRecord.TYPE)
    {
      if (o instanceof BBacnetNull)
      {
        ((BBacnetNumericTrendRecord)rec).setValue(0);
        rec.setLogEvent(BTrendEvent.DEFAULT);
        rec.setStatus(BStatus.makeNull(rec.getStatus(), true));
      }
      else
      {
        BSimple ld = o;
        if (ld.getType().is(BBacnetUnsigned.TYPE))
          ((BBacnetNumericTrendRecord)rec).setValue(((BBacnetUnsigned)o).getLong());
        else
          ((BBacnetNumericTrendRecord)rec).setValue(((BNumber)o).getDouble());
      }
    }
    else if (t == BBacnetEnumTrendRecord.TYPE)
    {
      if (o instanceof BBacnetNull)
      {
        ((BBacnetEnumTrendRecord)rec).setValue(BDynamicEnum.make(0));
        rec.setLogEvent(BTrendEvent.DEFAULT);
        rec.setStatus(BStatus.makeNull(rec.getStatus(), true));
      }
      else
      {
        ((BBacnetEnumTrendRecord)rec).setValue((BDynamicEnum)o);
      }
    }
    else if (t == BBacnetStringTrendRecord.TYPE)
    {
      try
      {
        if (o instanceof BBacnetNull)
        {
          ((BBacnetStringTrendRecord)rec).setValue("");
          rec.setLogEvent(BTrendEvent.DEFAULT);
          rec.setStatus(BStatus.makeNull(rec.getStatus(), true));
        }
        else
        {
          ((BBacnetStringTrendRecord)rec).setValue(o.encodeToString());
        }
      }
      catch (Exception e)
      {
        loggerBacnet.log(Level.INFO, "Error, could not encode logDatum to a string (" + o.toString() + ")", e);
        ((BBacnetStringTrendRecord)rec).setValue("Error, could not encode " + o.toString());
      }
    }

    return rec;
  }

  public boolean isLogStatus()
  {
    return logDataChoice == LOG_STATUS_TAG;
  }

  public boolean isLogData()
  {
    return logDataChoice == LOG_DATA_SEQ_TAG;
  }

  public int getLogDataChoice()
  {
    return logDataChoice;
  }

  public BTypeSpec[] getTypeSpecs()
  {
    return recType;
  }

////////////////////////////////////////////////////////////////
//Attributes
////////////////////////////////////////////////////////////////

  private BTypeSpec[] recType = null;
  private int logDataChoice;

  private static final Logger loggerBacnetDebug = Logger.getLogger("bacnet.debug");
  private static final Logger loggerBacnet = Logger.getLogger("bacnet");

////////////////////////////////////////////////////////////////
//Constants
////////////////////////////////////////////////////////////////

  // BACnetLogMultipleRecord tags
  public static final int TIMESTAMP_TAG = 0;
  public static final int LOG_DATA_TAG = 1;

  // BACnetLogData choice tags
  public static final int LOG_STATUS_TAG = 0;
  public static final int LOG_DATA_SEQ_TAG = 1;
  public static final int TIME_CHANGE_TAG = 2;

  // BACnetLogData.log-data sequence of choice tags
  public static final int BOOLEAN_VALUE_TAG = 0;
  public static final int REAL_VALUE_TAG = 1;
  public static final int ENUM_VALUE_TAG = 2;
  public static final int UNSIGNED_VALUE_TAG = 3;
  public static final int SIGNED_VALUE_TAG = 4;
  public static final int BITSTRING_VALUE_TAG = 5;
  public static final int NULL_VALUE_TAG = 6;
  public static final int FAILURE_TAG = 7;
  public static final int ANY_VALUE_TAG = 8;


}
