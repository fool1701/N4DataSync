/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BBacnetTimeValue represents a BacnetTimeValue pair.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 6 June 02
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "time",
  type = "BBacnetTime",
  defaultValue = "BBacnetTime.DEFAULT"
)
@NiagaraProperty(
  name = "value",
  type = "BBacnetAny",
  defaultValue = "new BBacnetAny()"
)
public final class BBacnetTimeValue
  extends BComponent
  implements BIBacnetDataType,
  Comparable<Object>
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetTimeValue(3009577868)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, new BBacnetAny(), null);

  /**
   * Get the {@code value} property.
   * @see #value
   */
  public BBacnetAny getValue() { return (BBacnetAny)get(value); }

  /**
   * Set the {@code value} property.
   * @see #value
   */
  public void setValue(BBacnetAny v) { set(value, v, null); }

  //endregion Property "value"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetTimeValue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetTimeValue()
  {
  }

  /**
   * Full constructor.
   *
   * @param time
   * @param value
   */
  public BBacnetTimeValue(BBacnetTime time, BSimple value)
  {
    setTime(time);
    getValue().setAny(value, null);
  }

  /**
   * Property changed. If running pass changed call up to parent.
   */
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isMounted() || !isRunning()) return;

    getParent().asComponent().changed(getPropertyInParent(), cx);
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
    out.writeTime(getTime());
    getValue().writeAsn(out);
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public void readAsn(AsnInput in)
    throws AsnException
  {
    set(time, in.readTime(), noWrite);
    getValue().readAsn(in);
  }


////////////////////////////////////////////////////////////////
// Comparsion
////////////////////////////////////////////////////////////////

  /**
   * Compare to another BBacnetTimeValue.
   *
   * @return a negative integer, zero, or a
   * positive integer as this object is less
   * than, equal to, or greater than the
   * specified object.
   */
  public int compareTo(Object obj)
  {
    BBacnetTime tOther = ((BBacnetTimeValue)obj).getTime();
    BBacnetTime tThis = getTime();
    return tThis.compareTo(tOther);
  }

  /**
   * @return true if the specified time-value is before this time-value.
   */
  public boolean isBefore(BBacnetTimeValue x)
  {
    return compareTo(x) < 0;
  }

  /**
   * @return true if the specified time-value is after this time-value.
   */
  public boolean isAfter(BBacnetTimeValue x)
  {
    return compareTo(x) > 0;
  }

  /**
   * @return true if the specified time is not before this time-value.
   */
  public boolean isNotBefore(BBacnetTimeValue x)
  {
    return compareTo(x) >= 0;
  }

  /**
   * @return true if the specified time is not after this time-value.
   */
  public boolean isNotAfter(BBacnetTimeValue x)
  {
    return compareTo(x) <= 0;
  }


////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context context)
  {
    if ((context != null) && context.equals(nameContext))
      return getTime().toString(context) + "_" + getValue().toString(context);
    return getTime().toString(context) + ";" + getValue().toString(context);
  }

}
