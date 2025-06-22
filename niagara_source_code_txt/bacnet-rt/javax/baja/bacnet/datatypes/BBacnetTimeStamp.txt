/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.logging.Logger;

import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnConst;

/**
 * This class represents the Bacnet Timestamp Choice.
 *
 * @author Craig Gemmill
 * @version $Revision: 4$ $Date: 11/28/01 6:13:55 AM$
 * @creation 26 Feb 01
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "choice",
  type = "int",
  defaultValue = "1",
  flags = Flags.HIDDEN,
  facets = @Facet("BFacets.makeInt(0,2)")
)
@NiagaraProperty(
  name = "time",
  type = "BBacnetTime",
  defaultValue = "BBacnetTime.DEFAULT"
)
@NiagaraProperty(
  name = "sequenceNumber",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.DEFAULT"
)
@NiagaraProperty(
  name = "dateTime",
  type = "BBacnetDateTime",
  defaultValue = "new BBacnetDateTime()"
)
public final class BBacnetTimeStamp
  extends BStruct
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetTimeStamp(1091575907)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "choice"

  /**
   * Slot for the {@code choice} property.
   * @see #getChoice
   * @see #setChoice
   */
  public static final Property choice = newProperty(Flags.HIDDEN, 1, BFacets.makeInt(0,2));

  /**
   * Get the {@code choice} property.
   * @see #choice
   */
  public int getChoice() { return getInt(choice); }

  /**
   * Set the {@code choice} property.
   * @see #choice
   */
  public void setChoice(int v) { setInt(choice, v, null); }

  //endregion Property "choice"

  //region Property "time"

  /**
   * Slot for the {@code time} property.
   * @see #getTime
   * @see #setTime
   */
  public static final Property time = newProperty(0, BBacnetTime.DEFAULT, null);

  /**
   * Get the {@code time} property.
   * @see #time
   */
  public BBacnetTime getTime() { return (BBacnetTime)get(time); }

  /**
   * Set the {@code time} property.
   * @see #time
   */
  public void setTime(BBacnetTime v) { set(time, v, null); }

  //endregion Property "time"

  //region Property "sequenceNumber"

  /**
   * Slot for the {@code sequenceNumber} property.
   * @see #getSequenceNumber
   * @see #setSequenceNumber
   */
  public static final Property sequenceNumber = newProperty(0, BBacnetUnsigned.DEFAULT, null);

  /**
   * Get the {@code sequenceNumber} property.
   * @see #sequenceNumber
   */
  public BBacnetUnsigned getSequenceNumber() { return (BBacnetUnsigned)get(sequenceNumber); }

  /**
   * Set the {@code sequenceNumber} property.
   * @see #sequenceNumber
   */
  public void setSequenceNumber(BBacnetUnsigned v) { set(sequenceNumber, v, null); }

  //endregion Property "sequenceNumber"

  //region Property "dateTime"

  /**
   * Slot for the {@code dateTime} property.
   * @see #getDateTime
   * @see #setDateTime
   */
  public static final Property dateTime = newProperty(0, new BBacnetDateTime(), null);

  /**
   * Get the {@code dateTime} property.
   * @see #dateTime
   */
  public BBacnetDateTime getDateTime() { return (BBacnetDateTime)get(dateTime); }

  /**
   * Set the {@code dateTime} property.
   * @see #dateTime
   */
  public void setDateTime(BBacnetDateTime v) { set(dateTime, v, null); }

  //endregion Property "dateTime"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetTimeStamp.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetTimeStamp()
  {
  }

  /**
   * Time constructor.
   *
   * @param time
   */
  public BBacnetTimeStamp(BBacnetTime time)
  {
    setChoice(TIME_TAG);
    setTime(time);
  }

  /**
   * Sequence Number constructor.
   *
   * @param sequenceNumber
   */
  public BBacnetTimeStamp(BBacnetUnsigned sequenceNumber)
  {
    setChoice(SEQUENCE_NUMBER_TAG);
    setSequenceNumber(sequenceNumber);
  }

  /**
   * DateTime constructor.
   *
   * @param dateTime
   */
  public BBacnetTimeStamp(BBacnetDateTime dateTime)
  {
    setChoice(DATE_TIME_TAG);
    setDateTime(dateTime);
  }

  /**
   * Create a BBacnetTimeStamp from a BAbsTime.
   */
  public BBacnetTimeStamp(BAbsTime babsTime)
  {
    fromBAbsTime(babsTime);
  }


////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  public BValue getTimeStamp()
  {
    switch (getChoice())
    {
      case TIME_TAG:
        return getTime();
      case SEQUENCE_NUMBER_TAG:
        return getSequenceNumber();
      case DATE_TIME_TAG:
        return getDateTime();
      default:
        throw new IllegalStateException();
    }
  }

  public void setTimeStamp(BValue ts)
  {
    setTimeStamp(ts, null);
  }

  public void setTimeStamp(BValue ts, Context cx)
  {
    if (ts.getType() == BBacnetTime.TYPE)
    {
      setInt(choice, TIME_TAG, cx);
      set(time, ts, cx);
    }
    else if (ts.getType() == BBacnetUnsigned.TYPE)
    {
      setInt(choice, SEQUENCE_NUMBER_TAG, cx);
      set(sequenceNumber, ts, cx);
    }
    else if (ts.getType() == BBacnetDateTime.TYPE)
    {
      setInt(choice, DATE_TIME_TAG, cx);
      set(dateTime, ts, cx);
    }
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
    switch (getChoice())
    {
      case TIME_TAG:
        out.writeTime(TIME_TAG, getTime());
        break;
      case SEQUENCE_NUMBER_TAG:
        out.writeUnsigned(SEQUENCE_NUMBER_TAG, getSequenceNumber());
        break;
      case DATE_TIME_TAG:
        out.writeOpeningTag(DATE_TIME_TAG);
        getDateTime().writeAsn(out);
        out.writeClosingTag(DATE_TIME_TAG);
        break;

      default:
        throw new IllegalStateException("Invalid timestamp type:" + getChoice());
    }
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public void readAsn(AsnInput in)
    throws AsnException
  {
    int tag = in.peekTag();
    if (in.isValueTag(TIME_TAG))
      setTimeStamp(in.readTime(TIME_TAG), noWrite);
    else if (in.isValueTag(SEQUENCE_NUMBER_TAG))
      setTimeStamp(in.readUnsigned(SEQUENCE_NUMBER_TAG), noWrite);
    else if (in.isOpeningTag(DATE_TIME_TAG))
    {
      in.skipTag();  // skip opening tag
      getDateTime().readAsn(in);
      setInt(choice, DATE_TIME_TAG, noWrite);
      in.skipTag();  // skip closing tag
    }
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);
  }


/////////////////////////////////////////////////////////////////
//  Utility Methods
/////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context cx)
  {
    StringBuilder sb = new StringBuilder();

    sb.append(tag(getChoice(), cx));
    sb.append(getTimeStamp().toString(cx));

    return sb.toString();
  }

  /**
   * To String.
   */
  public String toDebugString()
  {
    StringBuilder sb = new StringBuilder();

    sb.append(tag(getChoice(), BacnetConst.debugContext));
    sb.append(getTime().toString()).append(";")
      .append(getSequenceNumber().toString()).append(";")
      .append(getDateTime().toString());

    return sb.toString();
  }

  /**
   * Set the values from a <code>String</code>.
   *
   * @param text the <code>String</code>.
   */
  public static BBacnetTimeStamp fromText(String text)
  {
    if (text.startsWith(FACET_TAGS[0]))
      return new BBacnetTimeStamp(BBacnetTime.fromString(text.substring(FACET_TAGS[0].length())));
    else if (text.startsWith(FACET_TAGS[1]))
      return new BBacnetTimeStamp(BBacnetUnsigned.make(Long.parseLong(text.substring(FACET_TAGS[1].length()))));
    else if (text.startsWith(FACET_TAGS[2]))
      return new BBacnetTimeStamp(BBacnetDateTime.fromString(text.substring(FACET_TAGS[2].length())));
    else
      throw new IllegalArgumentException(text);
  }

  private static String tag(int choice, Context cx)
  {
    if (cx == null) return "";
    if (cx.equals(BacnetConst.facetsContext))
      return FACET_TAGS[choice];
    return DEBUG_TAGS[choice];
  }


/////////////////////////////////////////////////////////////////
//  BAbsTime conversion
/////////////////////////////////////////////////////////////////

  /**
   * Return a BAbsTime equivalent to this BBacnetTimeStamp.
   * Note that some conversions need to be applied to handle the differences
   * between Bacnet date and time and Niagara date and time.
   *
   * @return a BAbsTime matching the timestamp value.
   */
  public BAbsTime toBAbsTime()
  {
    switch (getChoice())
    {
      case TIME_TAG:
        logger.fine("converting BBacnetTime > BAbsTime using Niagara date");
        return BAbsTime.make(BAbsTime.now(), getTime().toBTime());

      case SEQUENCE_NUMBER_TAG:
        logger.fine("using Niagara time to replace Sequence Number Timestamp!");
        return BAbsTime.now();

      case DATE_TIME_TAG:
        BBacnetDate d = getDateTime().getDate();
        BBacnetTime t = getDateTime().getTime();
        if (isMin(d, t))
        {
          return BAbsTime.NULL;
        }
        else
        {
          return BAbsTime.make(
            d.getYear(),
            d.getBMonth(),
            d.getDayOfMonth(),
            t.getHour(),
            t.getMinute(),
            t.getSecond(),
            t.getHundredth() * 10);
        }

      default:
        return BAbsTime.make();
    }
  }

  /**
   * @return true if the date and time are at minimum BACnet values. This almost certainly suggests
   * that the equivalent BAbsTime is NULL and not 1900-Jan-01 00:00:00.000
   *
   * @since Niagara 4.10u4
   * @since Niagara 4.12
   */
  private static boolean isMin(BBacnetDate date, BBacnetTime time)
  {
    return date.getYear()       == 1900 &&
           date.getMonth()      == 1 &&
           date.getDayOfMonth() == 1 &&
           time.getHour()       == 0 &&
           time.getMinute()     == 0 &&
           time.getSecond()     == 0 &&
           time.getHundredth()  == 0;
  }

  /**
   * Set this BBacnetTimeStamp from a Niagara BAbsTime.
   * Note that some conversions need to be applied to handle the differences
   * between Bacnet date and time and Niagara date and time.
   *
   * @param babsTime the Niagara time.
   */
  public void fromBAbsTime(BAbsTime babsTime)
  {
    fromBAbsTime(babsTime, DATE_TIME_TAG);
  }

  /**
   * Set thie BBacnetTimeStamp from a Niagara BAbsTime.
   * Note that some conversions need to be applied to handle the differences
   * between Bacnet date and time and Niagara date and time.
   *
   * @param babsTime      the Niagara time.
   * @param timestampType the desired timestamp type - cannot be SEQUENCE_NUMBER_TAG.
   */
  public void fromBAbsTime(BAbsTime babsTime, int timestampType)
  {
    setChoice(timestampType);
    switch (timestampType)

    {
      case TIME_TAG:
        setChoice(TIME_TAG);
        setTime(BBacnetTime.make(babsTime));
        break;
      case SEQUENCE_NUMBER_TAG:
        throw new IllegalArgumentException("Cannot convert BAbsTime to Bacnet Sequence Number!");
      case DATE_TIME_TAG:
        setChoice(DATE_TIME_TAG);
        setDateTime(new BBacnetDateTime(babsTime));
        break;
    }
  }

  /**
   * Write a BAbsTime to the output stream as a BBacnetTimeStamp,
   * using the DATE_TIME_TAG.
   *
   * @param t   BAbsTime to be written
   * @param out AsnOutputStream to which the timestamp is written.
   */
  public static void encodeTimeStamp(BAbsTime t, AsnOutput out)
  {
    out.writeOpeningTag(DATE_TIME_TAG);
    out.writeDate(t);
    out.writeTime(t);
    out.writeClosingTag(DATE_TIME_TAG);
  }

  /**
   * Serialize
   */
  public void encode(DataOutput out)
    throws IOException
  {
    int choice = getChoice();
    out.writeInt(choice);
    switch (choice)
    {
      case TIME_TAG:
        getTime().encode(out);
        break;
      case SEQUENCE_NUMBER_TAG:
        getSequenceNumber().encode(out);
        break;
      case DATE_TIME_TAG:
        getDateTime().getDate().encode(out);
        getDateTime().getTime().encode(out);
        break;
    }
  }

  /**
   * Unserialized
   */
  public void decode(DataInput in)
    throws IOException
  {
    int choice = in.readInt();
    setChoice(choice);
    switch (choice)
    {
      case TIME_TAG:
        setTime((BBacnetTime)getTime().decode(in));
        break;
      case SEQUENCE_NUMBER_TAG:
        setSequenceNumber((BBacnetUnsigned)getSequenceNumber().decode(in));
        break;
      case DATE_TIME_TAG:
        BBacnetDateTime dt = getDateTime();
        dt.setDate((BBacnetDate)dt.getDate().decode(in));
        dt.setTime((BBacnetTime)dt.getTime().decode(in));
        break;
    }
  }


/////////////////////////////////////////////////////////////////
//  Constants
/////////////////////////////////////////////////////////////////

  /**
   * BBacnetTimeStamp Asn Context Tags
   * See Bacnet Clause 21
   */
  public static final int TIME_TAG = 0;
  public static final int SEQUENCE_NUMBER_TAG = 1;
  public static final int DATE_TIME_TAG = 2;
  public static final int MAX_SEQUENCE_NUMBER = 65535;

  private static final Logger logger = Logger.getLogger("bacnet");

  private static final String[] FACET_TAGS = new String[]
    { "ts_t.", "ts_sn.", "ts_dt." };
  private static final String[] DEBUG_TAGS = new String[]
    { "timeStamp{time}:", "timeStamp{sequenceNumber}:", "timeStamp{dateTime}:" };

}
