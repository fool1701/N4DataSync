/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.io.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BErrorType represents the Error sequence.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 28 Jul 2006
 * @since Niagara 3.2
 */

@NiagaraType
@NiagaraProperty(
  name = "errorClass",
  type = "int",
  defaultValue = "-1",
  facets = @Facet("BFacets.makeInt(0, 65535)")
)
@NiagaraProperty(
  name = "errorCode",
  type = "int",
  defaultValue = "-1",
  facets = @Facet("BFacets.makeInt(0, 65535)")
)
public final class BErrorType
  extends BStruct
  implements BIBacnetDataType, ErrorType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BErrorType(3377868232)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "errorClass"

  /**
   * Slot for the {@code errorClass} property.
   * @see #getErrorClass
   * @see #setErrorClass
   */
  public static final Property errorClass = newProperty(0, -1, BFacets.makeInt(0, 65535));

  /**
   * Get the {@code errorClass} property.
   * @see #errorClass
   */
  public int getErrorClass() { return getInt(errorClass); }

  /**
   * Set the {@code errorClass} property.
   * @see #errorClass
   */
  public void setErrorClass(int v) { setInt(errorClass, v, null); }

  //endregion Property "errorClass"

  //region Property "errorCode"

  /**
   * Slot for the {@code errorCode} property.
   * @see #getErrorCode
   * @see #setErrorCode
   */
  public static final Property errorCode = newProperty(0, -1, BFacets.makeInt(0, 65535));

  /**
   * Get the {@code errorCode} property.
   * @see #errorCode
   */
  public int getErrorCode() { return getInt(errorCode); }

  /**
   * Set the {@code errorCode} property.
   * @see #errorCode
   */
  public void setErrorCode(int v) { setInt(errorCode, v, null); }

  //endregion Property "errorCode"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BErrorType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public BErrorType()
  {
  }

  /**
   * Fully specified constructor.
   *
   * @param errClass
   * @param errCode
   */
  public BErrorType(int errClass, int errCode)
  {
    setErrorClass(errClass);
    setErrorCode(errCode);
  }


////////////////////////////////////////////////////////////////
//  ErrorType
////////////////////////////////////////////////////////////////

  /**
   * Encode the property value data to Asn.
   *
   * @param out the Asn encoder.
   */
  public void writeEncoded(AsnOutput out)
  {
    writeAsn(out);
  }

  /**
   * Decode the property value data from Asn.
   *
   * @param in the Asn decoder.
   * @throws AsnException if there is an Asn error.
   */
  public void readEncoded(AsnInput in)
    throws AsnException
  {
    readAsn(in);
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
    out.writeEnumerated(getErrorClass());
    out.writeEnumerated(getErrorCode());
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public void readAsn(AsnInput in)
    throws AsnException
  {
    setInt(errorClass, in.readEnumerated(), noWrite);
    setInt(errorCode, in.readEnumerated(), noWrite);
  }


////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  public boolean isDefault()
  {
    return (getErrorClass() < 0) && (getErrorCode() < 0);
  }

  public void setToDefault(Context cx)
  {
    setInt(errorClass, -1, cx);
    setInt(errorCode, -1, cx);
  }

  /**
   * To String.
   */
  public String toString(Context cx)
  {
    // If default values return empty string
    if (getErrorClass() == -1 && getErrorCode() == -1) return "";

    try
    {
      return "Error:class=" + BBacnetErrorClass.tag(getErrorClass()) + " code=" + BBacnetErrorCode.tag(getErrorCode());
    }
    catch (Exception e)
    {
      return "Invalid Error " + getErrorClass() + ":" + getErrorCode();
    }
  }

}
