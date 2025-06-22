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
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * <code>BEnumTrendRecord</code> is a trend record
 * with a multistate value.
 *
 * @author    John Sublett
 * @creation  08 Apr 2002
 * @version   $Revision: 8$ $Date: 11/18/04 3:13:32 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "value",
  type = "BDynamicEnum",
  defaultValue = "BDynamicEnum.DEFAULT",
  flags = Flags.SUMMARY
)
public class BEnumTrendRecord
  extends BTrendRecord
  implements BIStatus
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BEnumTrendRecord(289604549)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(Flags.SUMMARY, BDynamicEnum.DEFAULT, null);

  /**
   * Get the {@code value} property.
   * @see #value
   */
  public BDynamicEnum getValue() { return (BDynamicEnum)get(value); }

  /**
   * Set the {@code value} property.
   * @see #value
   */
  public void setValue(BDynamicEnum v) { set(value, v, null); }

  //endregion Property "value"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumTrendRecord.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  BEnumTrendRecord(BAbsTime timestamp, BDynamicEnum value, BStatus status)
  {
    super(timestamp, status);
    setValue(value);
  }

  public BEnumTrendRecord()
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
   * Tests whether this record type has a fixed size.  Multistate records
   * do have a fixed size.
   */
  @Override
  public boolean isFixedSize() { return true; }

  /**
   * Set the values in this record.
   *
   * @return Returns this instance with the new values set.
   */
  public BEnumTrendRecord set(BAbsTime     timestamp,
                              BDynamicEnum value,
                              BStatus      status)
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
  protected void doReadTrend(DataInput in)
    throws IOException
  {
    setValue(BDynamicEnum.make(in.readInt()));
  }

  /**
   * Write the type specific fields to the specified output.
   */
  @Override
  protected void doWriteTrend(DataOutput out)
    throws IOException
  {
    out.writeInt(getValue().getOrdinal());
  }

  @Override
  public String toString(Context ctx)
  {
    StringBuilder s = new StringBuilder(32);
    s.append(super.toString(ctx));
    s.append(" ");
    s.append(getValue().toString(ctx));
    s.append(' ');
    s.append(getStatus());

    return s.toString();
  }
}
