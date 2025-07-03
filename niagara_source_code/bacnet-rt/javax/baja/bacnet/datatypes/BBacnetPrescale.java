/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BBacnetPrescale represents the Bacnet Prescale
 * sequence.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 25 Jul 2006
 * @since Niagara 3.2
 */

@NiagaraType
@NiagaraProperty(
  name = "multiplier",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(0)"
)
@NiagaraProperty(
  name = "moduloDivide",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(0)"
)
public final class BBacnetPrescale
  extends BStruct
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetPrescale(4282166838)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "multiplier"

  /**
   * Slot for the {@code multiplier} property.
   * @see #getMultiplier
   * @see #setMultiplier
   */
  public static final Property multiplier = newProperty(0, BBacnetUnsigned.make(0), null);

  /**
   * Get the {@code multiplier} property.
   * @see #multiplier
   */
  public BBacnetUnsigned getMultiplier() { return (BBacnetUnsigned)get(multiplier); }

  /**
   * Set the {@code multiplier} property.
   * @see #multiplier
   */
  public void setMultiplier(BBacnetUnsigned v) { set(multiplier, v, null); }

  //endregion Property "multiplier"

  //region Property "moduloDivide"

  /**
   * Slot for the {@code moduloDivide} property.
   * @see #getModuloDivide
   * @see #setModuloDivide
   */
  public static final Property moduloDivide = newProperty(0, BBacnetUnsigned.make(0), null);

  /**
   * Get the {@code moduloDivide} property.
   * @see #moduloDivide
   */
  public BBacnetUnsigned getModuloDivide() { return (BBacnetUnsigned)get(moduloDivide); }

  /**
   * Set the {@code moduloDivide} property.
   * @see #moduloDivide
   */
  public void setModuloDivide(BBacnetUnsigned v) { set(moduloDivide, v, null); }

  //endregion Property "moduloDivide"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetPrescale.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public BBacnetPrescale()
  {
  }

  /**
   * Fully specified constructor.
   *
   * @param mult
   * @param mod
   */
  public BBacnetPrescale(long mult,
                         long mod)
  {
    setMultiplier(BBacnetUnsigned.make(mult));
    setModuloDivide(BBacnetUnsigned.make(mod));
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
    out.writeUnsigned(MULTIPLIER_TAG, getMultiplier());
    out.writeUnsigned(MODULO_DIVIDE_TAG, getModuloDivide());
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public void readAsn(AsnInput in)
    throws AsnException
  {
    set(multiplier, in.readUnsigned(MULTIPLIER_TAG), noWrite);
    set(moduloDivide, in.readUnsigned(MODULO_DIVIDE_TAG), noWrite);
  }


////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context cx)
  {
    return "Prescale:mult=" + getMultiplier() + " mod=" + getModuloDivide();
  }


////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  public static final int MULTIPLIER_TAG = 0;
  public static final int MODULO_DIVIDE_TAG = 1;

}
