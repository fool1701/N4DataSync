/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnConst;

/**
 * BBacnetScale represents the BACnetScale data type.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 09 Jan 2006
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "choice",
  type = "int",
  defaultValue = "0",
  facets = @Facet("BFacets.makeInt(0, 1)")
)
@NiagaraProperty(
  name = "scale",
  type = "double",
  defaultValue = "1"
)
public final class BBacnetScale
  extends BStruct
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetScale(116718432)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "choice"

  /**
   * Slot for the {@code choice} property.
   * @see #getChoice
   * @see #setChoice
   */
  public static final Property choice = newProperty(0, 0, BFacets.makeInt(0, 1));

  /**
   * Get the {@code choice} property.
   * @see #choice
   */
  public int getChoice() { return getInt(choice); }

  /**
   * Set the {@code choice} property.
   * @see #choice
   */
  public void setChoice(int v) { setInt(choice, v, null); }

  //endregion Property "choice"

  //region Property "scale"

  /**
   * Slot for the {@code scale} property.
   * @see #getScale
   * @see #setScale
   */
  public static final Property scale = newProperty(0, 1, null);

  /**
   * Get the {@code scale} property.
   * @see #scale
   */
  public double getScale() { return getDouble(scale); }

  /**
   * Set the {@code scale} property.
   * @see #scale
   */
  public void setScale(double v) { setDouble(scale, v, null); }

  //endregion Property "scale"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetScale.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Constructor.
   */
  public BBacnetScale()
  {
  }

  public BBacnetScale(double v)
  {
    setChoice(FLOAT_SCALE_TAG);
    setScale(v);
  }

  public BBacnetScale(int v)
  {
    setChoice(INTEGER_SCALE_TAG);
    setScale((double)v);
  }


////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context context)
  {
    double d = getScale();
    return (getChoice() == FLOAT_SCALE_TAG) ? String.valueOf(d) : String.valueOf((int)d);
  }

  /**
   * Set the scale to an integer scale type.
   */
  public void setScale(double v, Context cx)
  {
    setInt(choice, FLOAT_SCALE_TAG, cx);
    setDouble(scale, v, cx);
  }

  /**
   * Set the scale to an integer scale type.
   */
  public void setScale(int v)
  {
    setScale(v, null);
  }

  /**
   * Set the scale to an integer scale type.
   */
  public void setScale(int v, Context cx)
  {
    setInt(choice, INTEGER_SCALE_TAG, cx);
    setDouble(scale, (double)v, cx);
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
    if (getChoice() == FLOAT_SCALE_TAG)
      out.writeReal(FLOAT_SCALE_TAG, getScale());
    else
      out.writeSignedInteger(INTEGER_SCALE_TAG, (int)getScale());
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
    if (in.isValueTag(FLOAT_SCALE_TAG))
      setScale(in.readReal(FLOAT_SCALE_TAG), noWrite);
    else if (in.isValueTag(INTEGER_SCALE_TAG))
      setScale(in.readSignedInteger(INTEGER_SCALE_TAG), noWrite);
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int FLOAT_SCALE_TAG = 0;
  public static final int INTEGER_SCALE_TAG = 1;

}
