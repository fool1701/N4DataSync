/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.lighting.BBacnetLightingOperation;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.bacnet.util.worker.IBacnetAddress;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnConst;

/**
 * BBacnetGroupChannelValue represents the BACnetGroupChannelValue
 * sequence.
 *
 * @author Joseph Chandler
 * @creation 15 Apr 15
 * @since Niagara 4
 */

/*
 * BACnetGroupChannelValue ::= SEQUENCE {
 *    channel                [0] Unsigned16,
 *    overridingPriority     [1] Unsigned (1..16) OPTIONAL,
 *    value                  BACnetChannelValue
 * }
 */

@NiagaraType
@NiagaraProperty(
  name = "channel",
  type = "int",
  defaultValue = "-1"
)
@NiagaraProperty(
  name = "value",
  type = "BBacnetChannelValue",
  defaultValue = "new BBacnetChannelValue()"
)
public class BBacnetGroupChannelValue
  extends BComponent
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetGroupChannelValue(4082701764)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "channel"

  /**
   * Slot for the {@code channel} property.
   * @see #getChannel
   * @see #setChannel
   */
  public static final Property channel = newProperty(0, -1, null);

  /**
   * Get the {@code channel} property.
   * @see #channel
   */
  public int getChannel() { return getInt(channel); }

  /**
   * Set the {@code channel} property.
   * @see #channel
   */
  public void setChannel(int v) { setInt(channel, v, null); }

  //endregion Property "channel"

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, new BBacnetChannelValue(), null);

  /**
   * Get the {@code value} property.
   * @see #value
   */
  public BBacnetChannelValue getValue() { return (BBacnetChannelValue)get(value); }

  /**
   * Set the {@code value} property.
   * @see #value
   */
  public void setValue(BBacnetChannelValue v) { set(value, v, null); }

  //endregion Property "value"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetGroupChannelValue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetGroupChannelValue()
  {
  }


  public BBacnetGroupChannelValue(int channel,
                                  Integer overridingPriority,
                                  BBacnetChannelValue value)
  {
    setChannel(channel);

    if (overridingPriority != null)
      setOverridingPriority(overridingPriority);

    setValue(value);
  }

////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////


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
    out.writeUnsignedInteger(CHANNEL_TAG, getChannel());

    Integer oPri = getOverridingPriority();
    if (oPri != null)
      out.writeUnsignedInteger(OVERRIDING_PRIORITY_TAG, oPri);

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
    set(channel, BLong.make(in.readUnsignedInteger(CHANNEL_TAG)), noWrite);

    if (in.peekTag() == OVERRIDING_PRIORITY_TAG)
      setOverridingPriority(in.readUnsignedInt(OVERRIDING_PRIORITY_TAG));

    getValue().readAsn(in);

  }

////////////////////////////////////////////////////////////////
// Optional fields
////////////////////////////////////////////////////////////////

  public Integer getOverridingPriority()
  {
    BInteger priority = (BInteger)get("overridingPriority");
    if (priority != null)
      return priority.getInt();

    return null;
  }

  public void setOverridingPriority(Integer overridingPriority)
  {
    add("overridingPriority", BInteger.make(overridingPriority));
  }

////////////////////////////////////////////////////////////////
//  Utility
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder("Channel: ").append(getChannel());
    sb.append("\n\tOverridingPriority: ").append(getOverridingPriority());
    return sb.toString();
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int CHANNEL_TAG = 0;
  public static final int OVERRIDING_PRIORITY_TAG = 1;

}
