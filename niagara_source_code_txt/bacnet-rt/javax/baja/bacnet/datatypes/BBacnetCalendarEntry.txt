/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnConst;

/**
 * This class represents the Bacnet CalendarEntry Choice.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 10 Jun 02
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "choice",
  type = "int",
  defaultValue = "0",
  facets = @Facet("BFacets.makeInt(0,2)")
)
@NiagaraProperty(
  name = "date",
  type = "BBacnetDate",
  defaultValue = "BBacnetDate.DEFAULT"
)
@NiagaraProperty(
  name = "dateRange",
  type = "BBacnetDateRange",
  defaultValue = "new BBacnetDateRange()"
)
@NiagaraProperty(
  name = "weekNDay",
  type = "BBacnetOctetString",
  defaultValue = "BBacnetOctetString.BACNET_WEEK_N_DAY",
  facets = {
    @Facet(name = "BFacets.FIELD_EDITOR", value = "\"bacnet:BacnetWeekNDayFE\""),
    @Facet(name = "BFacets.UX_FIELD_EDITOR", value = "\"bacnet:BacnetWeekNDayEditor\"")
  }
)
public final class BBacnetCalendarEntry
  extends BStruct
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetCalendarEntry(642827777)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "choice"

  /**
   * Slot for the {@code choice} property.
   * @see #getChoice
   * @see #setChoice
   */
  public static final Property choice = newProperty(0, 0, BFacets.makeInt(0,2));

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

  //region Property "date"

  /**
   * Slot for the {@code date} property.
   * @see #getDate
   * @see #setDate
   */
  public static final Property date = newProperty(0, BBacnetDate.DEFAULT, null);

  /**
   * Get the {@code date} property.
   * @see #date
   */
  public BBacnetDate getDate() { return (BBacnetDate)get(date); }

  /**
   * Set the {@code date} property.
   * @see #date
   */
  public void setDate(BBacnetDate v) { set(date, v, null); }

  //endregion Property "date"

  //region Property "dateRange"

  /**
   * Slot for the {@code dateRange} property.
   * @see #getDateRange
   * @see #setDateRange
   */
  public static final Property dateRange = newProperty(0, new BBacnetDateRange(), null);

  /**
   * Get the {@code dateRange} property.
   * @see #dateRange
   */
  public BBacnetDateRange getDateRange() { return (BBacnetDateRange)get(dateRange); }

  /**
   * Set the {@code dateRange} property.
   * @see #dateRange
   */
  public void setDateRange(BBacnetDateRange v) { set(dateRange, v, null); }

  //endregion Property "dateRange"

  //region Property "weekNDay"

  /**
   * Slot for the {@code weekNDay} property.
   * @see #getWeekNDay
   * @see #setWeekNDay
   */
  public static final Property weekNDay = newProperty(0, BBacnetOctetString.BACNET_WEEK_N_DAY, BFacets.make(BFacets.make(BFacets.FIELD_EDITOR, "bacnet:BacnetWeekNDayFE"), BFacets.make(BFacets.UX_FIELD_EDITOR, "bacnet:BacnetWeekNDayEditor")));

  /**
   * Get the {@code weekNDay} property.
   * @see #weekNDay
   */
  public BBacnetOctetString getWeekNDay() { return (BBacnetOctetString)get(weekNDay); }

  /**
   * Set the {@code weekNDay} property.
   * @see #weekNDay
   */
  public void setWeekNDay(BBacnetOctetString v) { set(weekNDay, v, null); }

  //endregion Property "weekNDay"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetCalendarEntry.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetCalendarEntry()
  {
  }

  /**
   * Date constructor.
   *
   * @param date
   */
  public BBacnetCalendarEntry(BBacnetDate date)
  {
    setChoice(DATE_TAG);
    setDate(date);
  }


  /**
   * DateRange constructor.
   *
   * @param dateRange
   */
  public BBacnetCalendarEntry(BBacnetDateRange dateRange)
  {
    setChoice(DATE_RANGE_TAG);
    setDateRange(dateRange);
  }

  /**
   * OctetString constructor.
   *
   * @param weekNDay
   */
  public BBacnetCalendarEntry(BBacnetOctetString weekNDay)
  {
    setChoice(WEEK_N_DAY_TAG);
    setWeekNDay(weekNDay);
  }


////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context cx)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("BBacnetCalendarEntry:").append(getChoice()).append(" ");
    switch (getChoice())
    {
      case DATE_TAG:
        sb.append(getDate().toString(cx));
        break;
      case DATE_RANGE_TAG:
        sb.append(getDateRange().toString(cx));
        break;
      case WEEK_N_DAY_TAG:// sb.append(getWeekNDay().toString(cx)); break;
        if (cx != null)
          cx = new BasicContext(cx, BFacets.make(BBacnetOctetString.BACNET_OCTET_STRING, BString.make("weekNDay")));
        else
          cx = BFacets.make(BBacnetOctetString.BACNET_OCTET_STRING, BString.make("weekNDay"));
        sb.append(getWeekNDay().toString(cx));
        break;
    }
    return sb.toString();
  }

  /**
   * Get the calendar entry, returned as a BValue.
   *
   * @return the slot value for the current value of choice.
   */
  public BValue getCalendarEntry()
  {
    switch (getChoice())
    {
      case DATE_TAG:
        return getDate();
      case DATE_RANGE_TAG:
        return getDateRange();
      case WEEK_N_DAY_TAG:
        return getWeekNDay();
      default:
        throw new IllegalStateException();
    }
  }

  /**
   * Set the calendar entry.
   *
   * @param e the new calendar entry.
   */
  public void setCalendarEntry(BValue e)
  {
    setCalendarEntry(e, null);
  }

  /**
   * Set the calendar entry.
   *
   * @param e  the new calendar entry.
   * @param cx the context for the set.
   */
  public void setCalendarEntry(BValue e, Context cx)
  {
    Type t = e.getType();
    if (t == BBacnetDate.TYPE)
    {
      setInt(choice, DATE_TAG, cx);
      set(date, e, cx);
    }
    else if (t == BBacnetDateRange.TYPE)
    {
      setInt(choice, DATE_RANGE_TAG, cx);
      set(dateRange, e.newCopy(), cx);
    }
    else if (t == BBacnetOctetString.TYPE)
    {
      setInt(choice, WEEK_N_DAY_TAG, cx);
      set(weekNDay, e, cx);
    }
  }

  /**
   * Is this calendar entry active at the specified time?
   *
   * @param at the BAbsTime to check.
   * @return true if the calendar entry is active at this time.
   */
  public boolean isActive(BAbsTime at)
  {
    BBacnetDate d = BBacnetDate.make(at);
    switch (getChoice())
    {
      case DATE_TAG:
        return getDate().dateEquals(d);
      case DATE_RANGE_TAG:
        return (getDateRange().getStartDate().isNotAfter(d)
          && getDateRange().getEndDate().isNotBefore(d));
      case WEEK_N_DAY_TAG:
        int month = at.getMonth().getOrdinal() + 1; // Niagara uses 0-11, Bacnet uses 1-12.
        int dayOfMonth = at.getDay();
        int dayOfWeek = at.getWeekday().getOrdinal();
        if (dayOfWeek == NIAGARA_SUNDAY) dayOfWeek = BAC_SUNDAY;
        byte[] weekNDay = getWeekNDay().getBytes();
        boolean result = true;
        if (weekNDay.length < 3) throw new IllegalStateException();
        if ((weekNDay[0] != (byte)0xFF) && (weekNDay[0] != month))
          result = false;
        if (weekNDay[1] != (byte)0xFF)
        {
          switch (weekNDay[1])
          {
            case FIRST_WEEK:
              return ((dayOfMonth >= 1) && (dayOfMonth <= 7));
            case SECOND_WEEK:
              return ((dayOfMonth >= 8) && (dayOfMonth <= 14));
            case THIRD_WEEK:
              return ((dayOfMonth >= 15) && (dayOfMonth <= 21));
            case FOURTH_WEEK:
              return ((dayOfMonth >= 22) && (dayOfMonth <= 28));
            case FIFTH_WEEK:
              return ((dayOfMonth >= 29) && (dayOfMonth <= 31));
            case LAST_SEVEN_DAYS:
              int maxDOM = GREG.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
              return ((dayOfMonth > (maxDOM - 7)) && (dayOfMonth <= maxDOM));
            default:
              logger.severe("Incorrect weekOfMonth configuration for BBacnetWeekNDay in BBacnetCalendarEntry!");
//              result = false;
//              break;
              throw new IllegalStateException();
          }
        }
        if ((weekNDay[2] != (byte)0xFF) && (weekNDay[2] != dayOfWeek))
          result = false;
        return result;
      default:
        throw new IllegalArgumentException("Invalid calendar entry type:" + getChoice());
    }
  }

  /**
   * Get the next time after the given time at which this calendar entry
   * becomes active.  The returned BAbsTime will be 00:00:00.000
   * (12:00 midnight) on the returned date.
   * If the calendar entry will never become active after the given date,
   * this returns null.
   *
   * @param time the starting time.
   * @return a BAbsTime at which this calendar entry will become active.
   */
  public BAbsTime nextDate(BAbsTime time)
  {
    BBacnetDate d = BBacnetDate.make(time);
    switch (getChoice())
    {
      case DATE_TAG:
        if (d.isNotAfter(getDate()))
          return getDate().makeBAbsTime(time);
        break;

      case DATE_RANGE_TAG:
        if (d.isNotAfter(getDateRange().getEndDate()))
        {
          if (d.isNotBefore(getDateRange().getStartDate()))
            return time;
          else
            return getDateRange().getStartDate().makeBAbsTime(time);
        }
        break;

      case WEEK_N_DAY_TAG:
        // This is a brute-force method to find the next time.  Simply search
        // through the days following the given time for the next active time.
        // Since year is not specified, this should occur in the next 366 days,
        // if it will occur at all.
        for (int i = 0; i < MAX_ITERATIONS; i++)
        {
          if (isActive(time))
            return time;
          time = time.nextDay();
        }
        break;

      default:
        throw new IllegalStateException();
    }
    return null;
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
      case DATE_TAG:
        out.writeDate(DATE_TAG, getDate());
        break;
      case DATE_RANGE_TAG:
        out.writeOpeningTag(DATE_RANGE_TAG);
        getDateRange().writeAsn(out);
        out.writeClosingTag(DATE_RANGE_TAG);
        break;
      case WEEK_N_DAY_TAG:
        out.writeOctetString(WEEK_N_DAY_TAG, getWeekNDay());
        break;
      default:
        throw new IllegalStateException("Invalid calendar entry type:" + getChoice());
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
    if (in.isValueTag(DATE_TAG))
    {
      setCalendarEntry(in.readDate(DATE_TAG), noWrite);
    }
    else if (in.isOpeningTag(DATE_RANGE_TAG))
    {
      in.skipTag();  // skip opening tag
      getDateRange().readAsn(in);
      setInt(choice, DATE_RANGE_TAG, noWrite);
      in.skipTag();  // skip closing tag
    }
    else if (in.isValueTag(WEEK_N_DAY_TAG))
    {
      setCalendarEntry(in.readBacnetOctetString(WEEK_N_DAY_TAG), noWrite);
    }
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);
  }


/////////////////////////////////////////////////////////////////
//  Constants
/////////////////////////////////////////////////////////////////

  /**
   * BBacnetCalendarEntry Asn Context Tags
   * See Bacnet Clause 21
   */
  public static final int DATE_TAG = 0;
  public static final int DATE_RANGE_TAG = 1;
  public static final int WEEK_N_DAY_TAG = 2;

  // GregorianCalendar for use in determining max # days in month
  private static GregorianCalendar GREG = new GregorianCalendar();

  private static final Logger logger = Logger.getLogger("bacnet.debug");

  // Number of days ahead to check for a match in the WeekNDay case.
  // Since year is not specified, 366 should catch all cases.
  // February 29?
  private static int MAX_ITERATIONS = 366;

  public static final int MAX_ENCODED_SIZE = 12;
}
