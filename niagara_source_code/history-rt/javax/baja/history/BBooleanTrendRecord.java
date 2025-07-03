/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIBoolean;
import javax.baja.sys.BIEnum;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * <code>BBooleanTrendRecord</code> is a trend record
 * with a boolean value.
 *
 * @author    John Sublett
 * @creation  08 Apr 2002
 * @version   $Revision: 4$ $Date: 11/18/04 3:13:32 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "value",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.SUMMARY
)
public class BBooleanTrendRecord
  extends BTrendRecord
  implements BIEnum, BIBoolean, BIStatus
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BBooleanTrendRecord(517303212)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(Flags.SUMMARY, false, null);

  /**
   * Get the {@code value} property.
   * @see #value
   */
  public boolean getValue() { return getBoolean(value); }

  /**
   * Set the {@code value} property.
   * @see #value
   */
  public void setValue(boolean v) { setBoolean(value, v, null); }

  //endregion Property "value"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBooleanTrendRecord.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  BBooleanTrendRecord(BAbsTime timestamp,
                      boolean  value,
                      BStatus  status)
  {
    super(timestamp, status);
    setValue(value);
  }

  public BBooleanTrendRecord()
  {
  }

  /**
   * Get the Property instance for accessing the value.
   */
  @Override
  public Property getValueProperty()
  {
    return value;
  }

  /**
   * Return an instance of BBoolean.
   */
  @Override
  public BEnum getEnum()
  {
    return BBoolean.make(getValue());
  }

  /**
   * Return enum range for BBoolean.
   */
  @Override
  public BFacets getEnumFacets()
  {
    return BBoolean.make(getValue()).getEnumFacets();
  }

  /**
   * Return <code>getValue()</code>.
   */
  @Override
  public boolean getBoolean()
  {
    return getValue();
  }

  /**
   * Return <code>BFacets.NULL</code>.
   */
  @Override
  public BFacets getBooleanFacets()
  {
    return BFacets.NULL;
  }

  /**
   * Tests whether this record type has a fixed size.  Boolean records
   * do have a fixed size.
   */
  @Override
  public boolean isFixedSize() { return true; }

  /**
   * Set the values in this record.
   *
   * @return Returns this instance with the new values set.
   */
  public BBooleanTrendRecord set(BAbsTime timestamp,
                                 boolean value,
                                 BStatus status)
  {
    setTimestamp(timestamp);
    setTrendFlags(BTrendFlags.DEFAULT);
    setValue(value);
    setStatus(status);

    return this;
  }

  /**
   * Read the type specific fields from the specified input.
   */
  @Override
  public void doReadTrend(DataInput in)
    throws IOException
  {
    setValue(in.readBoolean());
  }

  /**
   * Write the type specific fields to the specified output.
   */
  @Override
  public void doWriteTrend(DataOutput out)
    throws IOException
  {
    out.writeBoolean(getValue());
  }

  @Override
  public String toString(Context ctx)
  {
    StringBuilder s = new StringBuilder(32);
    s.append(super.toString(ctx));
    s.append(" ");
    s.append(getValue());
    s.append(' ');
    s.append(getStatus());

    return s.toString();
  }
}
