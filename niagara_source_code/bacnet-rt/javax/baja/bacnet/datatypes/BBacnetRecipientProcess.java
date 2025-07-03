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

import com.tridium.bacnet.asn.AsnConst;

/**
 * BBacnetRecipientProcess represents the Bacnet RecipientProcess
 * sequence, used in Cov notifications.
 *
 * @author Craig Gemmill
 * @version $Revision: 5$ $Date: 12/10/01 9:26:07 AM$
 * @creation 31 Jul 01
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
/*
 the recipient address information.
 */
@NiagaraProperty(
  name = "recipient",
  type = "BBacnetRecipient",
  defaultValue = "new BBacnetRecipient()"
)
/*
 a numeric "handle" meaningful to the subscriber; used to identify
 the process within the Cov client that should receive the notification.
 */
@NiagaraProperty(
  name = "processIdentifier",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(0)"
)
public final class BBacnetRecipientProcess
  extends BStruct
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetRecipientProcess(454713035)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "recipient"

  /**
   * Slot for the {@code recipient} property.
   * the recipient address information.
   * @see #getRecipient
   * @see #setRecipient
   */
  public static final Property recipient = newProperty(0, new BBacnetRecipient(), null);

  /**
   * Get the {@code recipient} property.
   * the recipient address information.
   * @see #recipient
   */
  public BBacnetRecipient getRecipient() { return (BBacnetRecipient)get(recipient); }

  /**
   * Set the {@code recipient} property.
   * the recipient address information.
   * @see #recipient
   */
  public void setRecipient(BBacnetRecipient v) { set(recipient, v, null); }

  //endregion Property "recipient"

  //region Property "processIdentifier"

  /**
   * Slot for the {@code processIdentifier} property.
   * a numeric "handle" meaningful to the subscriber; used to identify
   * the process within the Cov client that should receive the notification.
   * @see #getProcessIdentifier
   * @see #setProcessIdentifier
   */
  public static final Property processIdentifier = newProperty(0, BBacnetUnsigned.make(0), null);

  /**
   * Get the {@code processIdentifier} property.
   * a numeric "handle" meaningful to the subscriber; used to identify
   * the process within the Cov client that should receive the notification.
   * @see #processIdentifier
   */
  public BBacnetUnsigned getProcessIdentifier() { return (BBacnetUnsigned)get(processIdentifier); }

  /**
   * Set the {@code processIdentifier} property.
   * a numeric "handle" meaningful to the subscriber; used to identify
   * the process within the Cov client that should receive the notification.
   * @see #processIdentifier
   */
  public void setProcessIdentifier(BBacnetUnsigned v) { set(processIdentifier, v, null); }

  //endregion Property "processIdentifier"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetRecipientProcess.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public BBacnetRecipientProcess()
  {
  }

  /**
   * Fully specified constructor.
   *
   * @param recipient
   * @param processIdentifier
   */
  public BBacnetRecipientProcess(BBacnetRecipient recipient,
                                 BBacnetUnsigned processIdentifier)
  {
    setRecipient(recipient);
    setProcessIdentifier(processIdentifier);
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
    out.writeOpeningTag(RECIPIENT_TAG);
    getRecipient().writeAsn(out);
    out.writeClosingTag(RECIPIENT_TAG);
    out.writeUnsigned(PROCESS_ID_TAG, getProcessIdentifier());
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
    if (in.isOpeningTag(RECIPIENT_TAG))
    {
      in.skipTag();   // skip opening tag
      getRecipient().readAsn(in);
      in.skipTag();   // skip closing tag
    }
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);
    set(processIdentifier, in.readUnsigned(PROCESS_ID_TAG), noWrite);
  }


////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context cx)
  {
    if ((cx != null) && cx.equals(BacnetConst.nameContext))
      return getRecipient().toString(cx) + "_id_" + getProcessIdentifier().toString();
    else
      return "recip:" + getRecipient().toString(cx) + "; procID:" + getProcessIdentifier().toString();
  }


////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  public static final int RECIPIENT_TAG = 0;
  public static final int PROCESS_ID_TAG = 1;

}
