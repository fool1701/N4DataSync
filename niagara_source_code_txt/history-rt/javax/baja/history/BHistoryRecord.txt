/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BStruct;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTypeSpec;

/**
 * BHistoryRecord is the base class for records that can
 * be stored in the history database.
 *
 * @author    John Sublett
 * @creation  19 Mar 2003
 * @version   $Revision: 21$ $Date: 3/4/10 3:08:06 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "timestamp",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.SUMMARY,
  facets = @Facet("BFacets.make(BFacets.SHOW_SECONDS, BBoolean.TRUE)")
)
public abstract class BHistoryRecord
  extends BStruct
  implements Externalizable, BIHistoryRecordSet
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BHistoryRecord(1272328336)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "timestamp"

  /**
   * Slot for the {@code timestamp} property.
   * @see #getTimestamp
   * @see #setTimestamp
   */
  public static final Property timestamp = newProperty(Flags.SUMMARY, BAbsTime.NULL, BFacets.make(BFacets.SHOW_SECONDS, BBoolean.TRUE));

  /**
   * Get the {@code timestamp} property.
   * @see #timestamp
   */
  public BAbsTime getTimestamp() { return (BAbsTime)get(timestamp); }

  /**
   * Set the {@code timestamp} property.
   * @see #timestamp
   */
  public void setTimestamp(BAbsTime v) { set(timestamp, v, null); }

  //endregion Property "timestamp"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryRecord.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  //NCCB-42576: Leave serialVersionUID, possibly used for serialization
  private static final long serialVersionUID = 647382510540763639L;

  protected BHistoryRecord()
  {
  }

  protected BHistoryRecord(BAbsTime timestamp)
  {
    setTimestamp(timestamp);
  }

  public BHistorySchema getSchema()
  {
    BHistorySchema schema = new BHistorySchema();
    SlotCursor<Property> props = getProperties();
    while (props.next())
    {
      Property prop = props.property();
      schema.addColumn(prop.getName(), prop.getType().getTypeSpec());
    }

    schema.getColumnCount(); // go ahead and freeze it
    return schema;
  }

  /**
   * Tests whether this record type has a fixed size.
   */
  public abstract boolean isFixedSize();

  @Override
  public void readExternal(ObjectInput in)
    throws IOException
  {
    read(in);
  }

  /**
   * Get the size of this record.  This is a potentially
   * expensive operation.  The default behavior is to
   * serialize the record to a buffer and return the
   * resulting number of bytes.
   *
   * @return Returns the size of the record in bytes or
   *   -1 if the size cannot be determined.
   */
  public int getRecordSize()
  {
    ByteArrayOutputStream bOut = new ByteArrayOutputStream(96);
    DataOutputStream dataOut = new DataOutputStream(bOut);
    try
    {
      write(dataOut);
      dataOut.flush();
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return -1;
    }

    return bOut.size();
  }

  /**
   * Read this instance from the specified input.
   *
   * @param in A DataInput for reading the record data.
   *
   * @return For convenience, always returns a reference to this record.
   */
  public final BHistoryRecord read(DataInput in)
    throws IOException
  {
    setTimestamp(((version == VERSION_1) ?
      (BAbsTime)BAbsTime.DEFAULT.decode(in) :
      (BAbsTime)BAbsTime.DEFAULT.decode48(in)));
    doRead(in);
    return this;
  }

  /**
   * Subclasses must override this method to read all of the record
   * fields except for the first timestamp.
   */
  protected abstract void doRead(DataInput in)
    throws IOException;

  @Override
  public void writeExternal(ObjectOutput out)
    throws IOException
  {
    write(out);
  }

  /**
   * Write this instance to the specified output.
   *
   * @param out A DataOutput for writing the record data.
   *
   * @return For convenience, always returns a reference to this record.
   */
  public final BHistoryRecord write(DataOutput out)
    throws IOException
  {
    if (version == VERSION_1)
      getTimestamp().encode(out);
    else
      getTimestamp().encode48(out);
    doWrite(out);
    return this;
  }

  /**
   * Subclasses must override this method to write all of the record fields except
   * for the first timestamp.
   */
  protected abstract void doWrite(DataOutput out)
    throws IOException;

  /**
   * Get the string representation of this record.
   */
  @Override
  public String toString(Context cx)
  {
    return getTimestamp().toString(cx);
  }

  /**
   * Get a summary string for this record.
   */
  public final String toSummary(Context cx)
  {
    StringBuffer s = new StringBuffer(96);
    s.append(getTimestamp().toString(cx));
    s.append(' ');
    toDataSummary(s, cx);
    return s.toString();
  }

  /**
   * Get a summary string for the data in this record not
   * including the timestamp.
   */
  public final String toDataSummary(Context cx)
  {
    StringBuffer s = new StringBuffer(64);
    toDataSummary(s, cx);
    return s.toString();
  }

  /**
   * Get a summary string without the timestamp.
   */
  public void toDataSummary(StringBuffer s, Context cx)
  {
    SlotCursor<Property> c = getProperties();
    boolean first = true;
    while (c.next())
    {
      if (!first) s.append(' ');
      first = false;
      s.append(c.get().toString(cx));
    }
  }

////////////////////////////////////////////////////////////////
// BHistoryRecordSet
////////////////////////////////////////////////////////////////

  /**
   * Only one record in this set.
   */
  @Override
  public int getRecordCount()
  {
    return 1;
  }

  /**
   * Get the record at the specified index.
   */
  @Override
  public BHistoryRecord getRecord(int index)
  {
    if (index != 0)
      throw new ArrayIndexOutOfBoundsException(index + "!=0");

    return this;
  }

  /**
   * Get an array of the records in the set.
   */
  @Override
  public BHistoryRecord[] getRecords()
  {
    return new BHistoryRecord[] { this };
  }

  /**
   * This is the last and only record in the set.
   */
  @Override
  public BHistoryRecord getLastRecord()
  {
    return this;
  }

  /**
   * Get the type spec for the record type.
   */
  @Override
  public BTypeSpec getRecordTypeSpec()
  {
    return getType().getTypeSpec();
  }

  public int getHistoryVersion()
  {
    return version;
  }

  public void setHistoryVersion(int version)
  {
    this.version = version;
  }

  public static final BFacets TIMESTAMP_FACETS =
    BFacets.make(BFacets.SHOW_SECONDS, BBoolean.TRUE, BFacets.SHOW_MILLISECONDS, BBoolean.TRUE);

  public static final int VERSION_1 = 1;
  public static final int VERSION_2 = 2;
  protected int version = VERSION_2;
}
