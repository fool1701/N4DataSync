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
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * <code>BStringTrendRecord</code> is a trend record with
 * a string value.
 *
 * @author    John Sublett
 * @creation  08 Apr 2002
 * @version   $Revision: 3$ $Date: 11/18/04 3:13:32 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "value",
  type = "String",
  defaultValue = "",
  flags = Flags.SUMMARY
)
public class BStringTrendRecord
  extends BTrendRecord
  implements BIStatus
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BStringTrendRecord(3207801849)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(Flags.SUMMARY, "", null);

  /**
   * Get the {@code value} property.
   * @see #value
   */
  public String getValue() { return getString(value); }

  /**
   * Set the {@code value} property.
   * @see #value
   */
  public void setValue(String v) { setString(value, v, null); }

  //endregion Property "value"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStringTrendRecord.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BStringTrendRecord()
  {
  }

  public BStringTrendRecord(BAbsTime timestamp, String s, BStatus status)
  {
    super(timestamp, status);
    setValue(s);
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
  public boolean isFixedSize() { return false; }

  /**
   * Read the type specific fields from the specified input.
   */
  @Override
  protected void doReadTrend(DataInput in)
    throws IOException
  {
    setValue(in.readUTF());
  }

  /**
   * Write the type specific fields to the specified output.
   */
  @Override
  protected void doWriteTrend(DataOutput out)
    throws IOException
  {
    out.writeUTF(getValue());
  }

  /**
   * Set the values in this record.
   *
   * @return Returns this instance with the new values set.
   */
  public BStringTrendRecord set(BAbsTime timestamp,
                                String value,
                                BStatus status)
  {
    setTimestamp(timestamp);
    setTrendFlags(BTrendFlags.DEFAULT);
    setValue(value);
    setStatus(status);

    return this;
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
