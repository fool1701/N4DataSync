/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BBacnetSetpointReference represents the BacnetSetpointReference
 * sequence.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 09 Sep 2004
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "referenceUsed",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.HIDDEN
)
@NiagaraProperty(
  name = "reference",
  type = "BBacnetObjectPropertyReference",
  defaultValue = "new BBacnetObjectPropertyReference()"
)
public final class BBacnetSetpointReference
  extends BStruct
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetSetpointReference(420304586)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "referenceUsed"

  /**
   * Slot for the {@code referenceUsed} property.
   * @see #getReferenceUsed
   * @see #setReferenceUsed
   */
  public static final Property referenceUsed = newProperty(Flags.HIDDEN, false, null);

  /**
   * Get the {@code referenceUsed} property.
   * @see #referenceUsed
   */
  public boolean getReferenceUsed() { return getBoolean(referenceUsed); }

  /**
   * Set the {@code referenceUsed} property.
   * @see #referenceUsed
   */
  public void setReferenceUsed(boolean v) { setBoolean(referenceUsed, v, null); }

  //endregion Property "referenceUsed"

  //region Property "reference"

  /**
   * Slot for the {@code reference} property.
   * @see #getReference
   * @see #setReference
   */
  public static final Property reference = newProperty(0, new BBacnetObjectPropertyReference(), null);

  /**
   * Get the {@code reference} property.
   * @see #reference
   */
  public BBacnetObjectPropertyReference getReference() { return (BBacnetObjectPropertyReference)get(reference); }

  /**
   * Set the {@code reference} property.
   * @see #reference
   */
  public void setReference(BBacnetObjectPropertyReference v) { set(reference, v, null); }

  //endregion Property "reference"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetSetpointReference.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetSetpointReference()
  {
  }

  /**
   * Standard constructor.
   */
  public BBacnetSetpointReference(BBacnetObjectPropertyReference setpointReference)
  {
    setSetpointReference(setpointReference);
  }


////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder("setpointReference:");
    if (getReferenceUsed())
      sb.append(getReference().toString(context));
    else
      sb.append("empty");
    return sb.toString();
  }

  public BBacnetObjectPropertyReference getSetpointReference()
  {
    return getReferenceUsed() ? getReference() : null;
  }

  public void setSetpointReference(BBacnetObjectPropertyReference setpointReference)
  {
    setSetpointReference(setpointReference, null);
  }

  public void setSetpointReference(BBacnetObjectPropertyReference setpointReference, Context cx)
  {
    if (setpointReference == null)
      setBoolean(referenceUsed, false, cx);
    else
    {
      setBoolean(referenceUsed, true, cx);
      set(reference, setpointReference, cx);
    }
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
    if (getReferenceUsed())
    {
      out.writeOpeningTag(SETPOINT_REFERENCE_TAG);
      getReference().writeAsn(out);
      out.writeClosingTag(SETPOINT_REFERENCE_TAG);
    }
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public void readAsn(AsnInput in)
    throws AsnException
  {
    in.peekTag();
    if (in.isOpeningTag(SETPOINT_REFERENCE_TAG))
    {
      in.skipTag();
      in.peekTag();
      if (in.isClosingTag(SETPOINT_REFERENCE_TAG))
      {
        setSetpointReference(null, noWrite);
        setBoolean(referenceUsed, false, noWrite);
      }
      else
      {
        setBoolean(referenceUsed, true, noWrite);
        getReference().readAsn(in);
      }
      in.skipTag();
    }
    else
    {
      setSetpointReference(null, noWrite);
      setBoolean(referenceUsed, false, noWrite);
    }
  }


////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  public static final int SETPOINT_REFERENCE_TAG = 0;

}
