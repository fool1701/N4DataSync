/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.job;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BLong;
import javax.baja.sys.BStruct;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Struct encapsulating a number of encoded JobLogItems and associated sequence numbers
 * for the first and last log items in the contained sequence of items. The sequence numbers
 * are used to identify the overall position of the records in a JobLog that may have its size
 * limited and have removed old items. Values of -1 are used for the first and last sequence numbers
 * to indicate an empty sequence, such as one requested from an empty log, or a request for a
 * sequence beyond the end of the log. The initial sequence number of the current JobLog instance
 * is also provided as a property.
 *
 * @see javax.baja.job.BJob#readLogFrom(BLong)
 * @see javax.baja.job.JobLog
 * @see javax.baja.job.JobLogItem
 *
 * @since Niagara 4.3
 */
@NiagaraType
/*
 Property indicating the initial sequence number of the current JobLog. For a new job, this
 will be 0. For jobs that have reset their logs, this will be the first sequence number
 after the log was reset. A client user interface could use a change in the value of
 this property to update itself accordingly.
 */
@NiagaraProperty(
  name = "initialSequenceNumber",
  type = "long",
  defaultValue = "0",
  flags = Flags.READONLY
)
/*
 Property indicating the zero based sequence number of the first log item
 in the encoded items, or -1 if the sequence is empty.
 */
@NiagaraProperty(
  name = "firstSequenceNumber",
  type = "long",
  defaultValue = "-1",
  flags = Flags.READONLY
)
/*
 Property indicating the zero based sequence number of the last log item
 in the encoded items, or -1 if the sequence is empty.
 */
@NiagaraProperty(
  name = "lastSequenceNumber",
  type = "long",
  defaultValue = "-1",
  flags = Flags.READONLY
)
/*
 A string property containing zero or more encoded log items. Client code
 is not expected to access this property directly, rather it should call
 the getLogItems() method, which will decode the string into an array of
 JobLogItem instances.
 */
@NiagaraProperty(
  name = "encodedItems",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.HIDDEN
)
public final class BJobLogSequence extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.job.BJobLogSequence(1758137989)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "initialSequenceNumber"

  /**
   * Slot for the {@code initialSequenceNumber} property.
   * Property indicating the initial sequence number of the current JobLog. For a new job, this
   * will be 0. For jobs that have reset their logs, this will be the first sequence number
   * after the log was reset. A client user interface could use a change in the value of
   * this property to update itself accordingly.
   * @see #getInitialSequenceNumber
   * @see #setInitialSequenceNumber
   */
  public static final Property initialSequenceNumber = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code initialSequenceNumber} property.
   * Property indicating the initial sequence number of the current JobLog. For a new job, this
   * will be 0. For jobs that have reset their logs, this will be the first sequence number
   * after the log was reset. A client user interface could use a change in the value of
   * this property to update itself accordingly.
   * @see #initialSequenceNumber
   */
  public long getInitialSequenceNumber() { return getLong(initialSequenceNumber); }

  /**
   * Set the {@code initialSequenceNumber} property.
   * Property indicating the initial sequence number of the current JobLog. For a new job, this
   * will be 0. For jobs that have reset their logs, this will be the first sequence number
   * after the log was reset. A client user interface could use a change in the value of
   * this property to update itself accordingly.
   * @see #initialSequenceNumber
   */
  public void setInitialSequenceNumber(long v) { setLong(initialSequenceNumber, v, null); }

  //endregion Property "initialSequenceNumber"

  //region Property "firstSequenceNumber"

  /**
   * Slot for the {@code firstSequenceNumber} property.
   * Property indicating the zero based sequence number of the first log item
   * in the encoded items, or -1 if the sequence is empty.
   * @see #getFirstSequenceNumber
   * @see #setFirstSequenceNumber
   */
  public static final Property firstSequenceNumber = newProperty(Flags.READONLY, -1, null);

  /**
   * Get the {@code firstSequenceNumber} property.
   * Property indicating the zero based sequence number of the first log item
   * in the encoded items, or -1 if the sequence is empty.
   * @see #firstSequenceNumber
   */
  public long getFirstSequenceNumber() { return getLong(firstSequenceNumber); }

  /**
   * Set the {@code firstSequenceNumber} property.
   * Property indicating the zero based sequence number of the first log item
   * in the encoded items, or -1 if the sequence is empty.
   * @see #firstSequenceNumber
   */
  public void setFirstSequenceNumber(long v) { setLong(firstSequenceNumber, v, null); }

  //endregion Property "firstSequenceNumber"

  //region Property "lastSequenceNumber"

  /**
   * Slot for the {@code lastSequenceNumber} property.
   * Property indicating the zero based sequence number of the last log item
   * in the encoded items, or -1 if the sequence is empty.
   * @see #getLastSequenceNumber
   * @see #setLastSequenceNumber
   */
  public static final Property lastSequenceNumber = newProperty(Flags.READONLY, -1, null);

  /**
   * Get the {@code lastSequenceNumber} property.
   * Property indicating the zero based sequence number of the last log item
   * in the encoded items, or -1 if the sequence is empty.
   * @see #lastSequenceNumber
   */
  public long getLastSequenceNumber() { return getLong(lastSequenceNumber); }

  /**
   * Set the {@code lastSequenceNumber} property.
   * Property indicating the zero based sequence number of the last log item
   * in the encoded items, or -1 if the sequence is empty.
   * @see #lastSequenceNumber
   */
  public void setLastSequenceNumber(long v) { setLong(lastSequenceNumber, v, null); }

  //endregion Property "lastSequenceNumber"

  //region Property "encodedItems"

  /**
   * Slot for the {@code encodedItems} property.
   * A string property containing zero or more encoded log items. Client code
   * is not expected to access this property directly, rather it should call
   * the getLogItems() method, which will decode the string into an array of
   * JobLogItem instances.
   * @see #getEncodedItems
   * @see #setEncodedItems
   */
  public static final Property encodedItems = newProperty(Flags.READONLY | Flags.HIDDEN, "", null);

  /**
   * Get the {@code encodedItems} property.
   * A string property containing zero or more encoded log items. Client code
   * is not expected to access this property directly, rather it should call
   * the getLogItems() method, which will decode the string into an array of
   * JobLogItem instances.
   * @see #encodedItems
   */
  public String getEncodedItems() { return getString(encodedItems); }

  /**
   * Set the {@code encodedItems} property.
   * A string property containing zero or more encoded log items. Client code
   * is not expected to access this property directly, rather it should call
   * the getLogItems() method, which will decode the string into an array of
   * JobLogItem instances.
   * @see #encodedItems
   */
  public void setEncodedItems(String v) { setString(encodedItems, v, null); }

  //endregion Property "encodedItems"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BJobLogSequence.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  public BJobLogSequence()
  {
  }

  /**
   * Constructor taking the items to be encoded and the sequence numbers
   * of the first and last items in the array.
   *
   * @param items an array of JobLogItems to be encoded.
   * @param initialSequenceNumber the sequence number for the first item in the current JobLog instance.
   * @param firstSequenceNumber the log sequence number of the array's first item.
   * @param lastSequenceNumber the log sequence number of the array's last item.
   */
  BJobLogSequence(JobLogItem[] items,
                  long initialSequenceNumber,
                  long firstSequenceNumber,
                  long lastSequenceNumber)
  {
    setInitialSequenceNumber(initialSequenceNumber);
    setFirstSequenceNumber(firstSequenceNumber);
    setLastSequenceNumber(lastSequenceNumber);

    StringBuilder sb = new StringBuilder();
    for (JobLogItem item : items)
    {
      if (sb.length() > 0) sb.append('\n');
      sb.append(item.encode());
    }

    setEncodedItems(sb.toString());
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Decode the contents of the encodedItems property and return an
   * array of JobLogItems.
   *
   * @return an array of zero or more JobLogItem instances
   */
  public JobLogItem[] getLogItems() throws Exception
  {
    if (items == null)
    {
      String encoded = getEncodedItems().trim();
      if (!encoded.isEmpty())
      {
        String[] encodedItems = encoded.split("\n");
        items = new JobLogItem[encodedItems.length];
        for (int i = 0; i < encodedItems.length; i++)
        {
          items[i] = JobLogItem.decode(encodedItems[i]);
        }
      }
      else
      {
        items = new JobLogItem[0];
      }
    }

    return items;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private JobLogItem[] items;
}
