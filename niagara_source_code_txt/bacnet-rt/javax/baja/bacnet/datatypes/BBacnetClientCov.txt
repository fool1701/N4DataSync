/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnConst;

/**
 * BBacnetClientCov represents the choice for the COV increment to
 * be used in acquiring data for a trend log via COV.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 18 Nov 2004
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
/*
 if null, then the default-increment choice is used.
 if non-null, then the real-increment choice is used.
 */
@NiagaraProperty(
  name = "increment",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()"
)
public final class BBacnetClientCov
  extends BStruct
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetClientCov(1707097048)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "increment"

  /**
   * Slot for the {@code increment} property.
   * if null, then the default-increment choice is used.
   * if non-null, then the real-increment choice is used.
   * @see #getIncrement
   * @see #setIncrement
   */
  public static final Property increment = newProperty(0, new BStatusNumeric(), null);

  /**
   * Get the {@code increment} property.
   * if null, then the default-increment choice is used.
   * if non-null, then the real-increment choice is used.
   * @see #increment
   */
  public BStatusNumeric getIncrement() { return (BStatusNumeric)get(increment); }

  /**
   * Set the {@code increment} property.
   * if null, then the default-increment choice is used.
   * if non-null, then the real-increment choice is used.
   * @see #increment
   */
  public void setIncrement(BStatusNumeric v) { set(increment, v, null); }

  //endregion Property "increment"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetClientCov.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetClientCov()
  {
    getIncrement().setStatusNull(true);
  }

  /**
   * Object ID constructor.
   *
   * @param realIncrement
   */
  public BBacnetClientCov(double realIncrement)
  {
    setRealIncrement(realIncrement);
  }


////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  /**
   * Set the real-increment.
   *
   * @param v the new increment.
   */
  public void setRealIncrement(double v)
  {
    getIncrement().setValue(v);
    getIncrement().setStatusNull(false);
  }

  public void setDefaultIncrement()
  {
    getIncrement().setStatusNull(true);
  }

//  /**  DO I NEED CONTEXT VERSION?
//   * Set the recipient.
//   * @param v the new recipient.
//   * @param cx the context for the set.
//   */
//  public void setRecipient(BValue v, Context cx)
//  {
//    Type t = v.getType();
//    if (t == BBacnetObjectIdentifier.TYPE)
//    {
//      setInt(choice, DEVICE_TAG, cx);
//      set(device, v, cx);
//    }
//    else if (t == BBacnetAddress.TYPE)
//    {
//      setInt(choice, ADDRESS_TAG, cx);
//      getAddress().copyFrom((BBacnetAddress)v, cx);
//    }
//  }


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
    if (getIncrement().getStatus().isNull())
      out.writeNull();
    else
      out.writeReal(getIncrement().getValue());
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public void readAsn(AsnInput in)
    throws AsnException
  {
    int tag = in.peekApplicationTag();

    switch (tag)
    {
      case ASN_NULL:
        setDefaultIncrement();
        break;
      case ASN_REAL:
        setRealIncrement(in.readReal());
        break;
      default:
        throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);
    }
  }


////////////////////////////////////////////////////////////////
//  Utility
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context context)
  {
    return getIncrement().toString(context);
  }
}
