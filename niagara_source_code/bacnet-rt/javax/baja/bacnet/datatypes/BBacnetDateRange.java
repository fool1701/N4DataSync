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
 * BBacnetDateRange represents a BacnetDateRange value in a Bacnet property.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 19 Mar 01
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "startDate",
  type = "BBacnetDate",
  defaultValue = "BBacnetDate.DEFAULT"
)
@NiagaraProperty(
  name = "endDate",
  type = "BBacnetDate",
  defaultValue = "BBacnetDate.DEFAULT"
)
public class BBacnetDateRange
  extends BStruct
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetDateRange(2386649594)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "startDate"

  /**
   * Slot for the {@code startDate} property.
   * @see #getStartDate
   * @see #setStartDate
   */
  public static final Property startDate = newProperty(0, BBacnetDate.DEFAULT, null);

  /**
   * Get the {@code startDate} property.
   * @see #startDate
   */
  public BBacnetDate getStartDate() { return (BBacnetDate)get(startDate); }

  /**
   * Set the {@code startDate} property.
   * @see #startDate
   */
  public void setStartDate(BBacnetDate v) { set(startDate, v, null); }

  //endregion Property "startDate"

  //region Property "endDate"

  /**
   * Slot for the {@code endDate} property.
   * @see #getEndDate
   * @see #setEndDate
   */
  public static final Property endDate = newProperty(0, BBacnetDate.DEFAULT, null);

  /**
   * Get the {@code endDate} property.
   * @see #endDate
   */
  public BBacnetDate getEndDate() { return (BBacnetDate)get(endDate); }

  /**
   * Set the {@code endDate} property.
   * @see #endDate
   */
  public void setEndDate(BBacnetDate v) { set(endDate, v, null); }

  //endregion Property "endDate"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetDateRange.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  public BBacnetDateRange()
  {
  }

  public BBacnetDateRange(BBacnetDate startDate, BBacnetDate endDate)
  {
    setStartDate(startDate);
    setEndDate(endDate);
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
    out.writeDate(getStartDate());
    out.writeDate(getEndDate());
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public final void readAsn(AsnInput in)
    throws AsnException
  {
    set(startDate, in.readDate(), noWrite);
    set(endDate, in.readDate(), noWrite);
  }


////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context context)
  {
    char sep = ';';
    if ((context != null) && context.equals(BacnetConst.facetsContext))
      sep = '_';
    return getStartDate().toString(context) + sep + getEndDate().toString(context);
  }

  /**
   * Read the startDate and endDate values from the
   * given String and return a new BBacnetDateRange.
   *
   * @param s the input string.
   * @return a BBacnetDateRange read from the string.
   */
  public static BBacnetDateRange fromString(String s)
  {
    BBacnetDate sd = BBacnetDate.fromString(s.substring(0, BBacnetDate.TEXT_LENGTH));
    BBacnetDate ed = BBacnetDate.fromString(s.substring(BBacnetDate.TEXT_LENGTH + 1,
      BBacnetDate.TEXT_LENGTH + 1 + BBacnetDate.TEXT_LENGTH));
    return new BBacnetDateRange(sd, ed);
  }

}
