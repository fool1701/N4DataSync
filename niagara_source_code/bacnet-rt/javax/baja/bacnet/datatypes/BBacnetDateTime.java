/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BBacnetDateTime represents a BacnetDateTime value in a Bacnet property.
 *
 * @author Craig Gemmill
 * @version $Revision: 3$ $Date: 11/6/01 2:50:13 PM$
 * @creation 09 Aug 01
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "date",
  type = "BBacnetDate",
  defaultValue = "BBacnetDate.DEFAULT"
)
@NiagaraProperty(
  name = "time",
  type = "BBacnetTime",
  defaultValue = "BBacnetTime.DEFAULT"
)
public class BBacnetDateTime
  extends BStruct
  implements BIBacnetDataType,
  Comparable<Object>
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetDateTime(1860055357)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetDateTime.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  public BBacnetDateTime()
  {
  }

  public BBacnetDateTime(BBacnetDate date, BBacnetTime time)
  {
    setDate(date);
    setTime(time);
  }

  public BBacnetDateTime(BAbsTime bt)
  {
    setDate(BBacnetDate.make(bt));
    setTime(BBacnetTime.make(bt));
  }


////////////////////////////////////////////////////////////////
//  BIBacnetDataType
////////////////////////////////////////////////////////////////

  /**
   * Write the value to the Asn output stream.
   *
   * @param out the AsnOutput stream.
   */
  public final void writeAsn(AsnOutput out)
  {
    out.writeDate(getDate());
    out.writeTime(getTime());
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public final void readAsn(AsnInput in)
    throws AsnException
  {
    set(date, in.readDate(), noWrite);
    set(time, in.readTime(), noWrite);
  }


////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context context)
  {
    if ((context != null) && context.equals(BacnetConst.facetsContext))
      return getDate().toString(context, false) + ' ' + getTime().toString(context);
    else
      return getDate().toString(context) + '_' + getTime().toString(context);
  }

  /**
   * Is any field in either the date or the time UNPSECIFIED?
   *
   * @return true if any field in either the date or the time in unspecified.
   */
  public final boolean isAnyUnspecified()
  {
    return getDate().isAnyUnspecified() || getTime().isAnyUnspecified();
  }

  /**
   * Return a BAbsTime from this BacnetDateTime object.
   * No needed fields may be UNPSECIFIED.
   *
   * @throws IllegalStateException if any field is UNSPECIFIED.
   * @return a BAbsTime representing the same absolute time.
   */
  public final BAbsTime toBAbsTime()
  {
    return makeBAbsTime(getDate(), getTime());
  }

  /**
   * Set this BBacnetDateTime from the given BAbsTime.
   *
   * @param t the BAbsTime.
   */
  public final void fromBAbsTime(BAbsTime t)
  {
    setDate(BBacnetDate.make(t));
    setTime(BBacnetTime.make(t));
  }


////////////////////////////////////////////////////////////////
// Comparison
////////////////////////////////////////////////////////////////

  /**
   * BBacnetDateTime equivalence is based on all values being equal,
   * or unspecified.
   * <B>NOTE</B>: This is the method to determine DateTime equivalence according
   * to BACnet, <B>not</B> the equals() method, which requires UNSPECIFIED values
   * to match <B>only</B> with UNSPECIFIED values.
   *
   * @param obj the comparison object.
   * @see equals
   */
  public final boolean dateTimeEquals(Object obj)
  {
    return compareTo(obj) == 0;
  }

  /**
   * Compare to another BBacnetDate.
   *
   * @param obj the comparison object.
   * @return a negative integer, zero, or a
   * positive integer as this object is less
   * than, equal to, or greater than the
   * specified object.
   */
  public final int compareTo(Object obj)
  {
    if (obj == null) throw new ClassCastException();
    BBacnetDateTime other = (BBacnetDateTime)obj;

    // Check date first.
    int ret = getDate().compareTo(other.getDate());
    if (ret != 0)
      return ret;

    return getTime().compareTo(other.getTime());
  }

  /**
   * @return true if the specified date is before this date.
   */
  public final boolean isBefore(Object x)
  {
    return compareTo(x) < 0;
  }

  /**
   * @return true if the specified date is after this date.
   */
  public final boolean isAfter(Object x)
  {
    return compareTo(x) > 0;
  }

  /**
   * @return true if the specified date is not before this date.
   */
  public final boolean isNotBefore(Object x)
  {
    return compareTo(x) >= 0;
  }

  /**
   * @return true if the specified date is not after this date.
   */
  public final boolean isNotAfter(Object x)
  {
    return compareTo(x) <= 0;
  }


////////////////////////////////////////////////////////////////
//  Utility
////////////////////////////////////////////////////////////////

  /**
   * Read the date and time values from the
   * given String and return a new BBacnetDateTime.
   *
   * @param s the input string.
   * @return a BBacnetDateTime read from the string.
   */
  public static final BBacnetDateTime fromString(String s)
  {
    BBacnetDate d = BBacnetDate.fromString(s.substring(0, BBacnetDate.TEXT_LENGTH));
    BBacnetTime t = BBacnetTime.fromString(s.substring(BBacnetDate.TEXT_LENGTH + 1,
      BBacnetDate.TEXT_LENGTH + 1 + BBacnetTime.TEXT_LENGTH));
    return new BBacnetDateTime(d, t);
  }

  /**
   * Return a BAbsTime from the given BBacnetDate and BBacnetTime.
   *
   * @param d date
   * @param t time
   * @return a BAbsTime representing the same absolute time.
   */
  public static final BAbsTime makeBAbsTime(BBacnetDate d, BBacnetTime t)
  {
    int y = d.isYearUnspecified() ? 1900 : d.getYear();
    BMonth m = d.isMonthUnspecified() ? BMonth.january : d.getBMonth();
    int a = d.isDayOfMonthUnspecified() ? 1 : d.getDayOfMonth();
    int h = t.isHourUnspecified() ? 0 : t.getHour();
    int n = t.isMinuteUnspecified() ? 0 : t.getMinute();
    if (t.isSecondUnspecified() || t.isHundredthUnspecified())
      return BAbsTime.make(y, m, a, h, n);
    else
      return BAbsTime.make(y, m, a, h, n, t.getSecond(), t.getHundredth() * 10);
  }

  /**
   * Return a BAbsTime from the given BBacnetDate and BBacnetTime.
   *
   * @param d date
   * @param t time
   * @return a BAbsTime representing the same absolute time.
   */
  public static final BAbsTime makeBAbsTime(BAbsTime d, BBacnetTime t)
  {
    int h = t.isHourUnspecified() ? 0 : t.getHour();
    int n = t.isMinuteUnspecified() ? 0 : t.getMinute();

    if (t.isSecondUnspecified() || t.isHundredthUnspecified())
      return BAbsTime.make(d.getYear(), d.getMonth(), d.getDay(), h, n);
    else
      return BAbsTime.make(d.getYear(), d.getMonth(), d.getDay(),
        t.getHour(), t.getMinute(), t.getSecond(), t.getHundredth() * 10);
  }
}
