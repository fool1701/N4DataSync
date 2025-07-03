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

import com.tridium.bacnet.asn.*;


/**
 * BReadAccessResult represents the ReadAccessResult sequence.
 * This is used in the Group object.  It is not the class used for handling
 * ReadAccessResults in the usage of ReadPropertyMultiple during
 * polling.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 26 Jul 2006
 * @since Niagara 3.1
 */
@NiagaraType
@NiagaraProperty(
  name = "objectIdentifier",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.DEFAULT"
)
public class BReadAccessResult
  extends BComponent
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BReadAccessResult(1187579084)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectIdentifier"

  /**
   * Slot for the {@code objectIdentifier} property.
   * @see #getObjectIdentifier
   * @see #setObjectIdentifier
   */
  public static final Property objectIdentifier = newProperty(0, BBacnetObjectIdentifier.DEFAULT, null);

  /**
   * Get the {@code objectIdentifier} property.
   * @see #objectIdentifier
   */
  public BBacnetObjectIdentifier getObjectIdentifier() { return (BBacnetObjectIdentifier)get(objectIdentifier); }

  /**
   * Set the {@code objectIdentifier} property.
   * @see #objectIdentifier
   */
  public void setObjectIdentifier(BBacnetObjectIdentifier v) { set(objectIdentifier, v, null); }

  //endregion Property "objectIdentifier"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BReadAccessResult.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BReadAccessResult()
  {
  }

  /**
   * Single result constructor.
   */
  public BReadAccessResult(BReadPropertyResult res)
  {
    // FIXX: will determine if we need to do newCopy(), or if use cases
    // will not require it.
    add(null, res);
  }

  /**
   * Single result constructor.
   */
  public BReadAccessResult(BReadPropertyResult[] res)
  {
    // FIXX: will determine if we need to do newCopy(), or if use cases
    // will not require it.
    if (res != null)
    {
      for (int i = 0; i < res.length; i++)
        add(null, res[i]);
    }
  }


////////////////////////////////////////////////////////////////
//  BComponent
////////////////////////////////////////////////////////////////

  /**
   * No component children are allowed.  The only valid child is a
   * BReadPropertyResult.
   */
  public boolean isChildLegal(BComponent child)
  {
    return child instanceof BReadPropertyResult;
  }

  public String toString(Context cx)
  {
    loadSlots();
    Object[] a = getChildren(BReadPropertyResult.class);

    StringBuilder sb = new StringBuilder(getObjectIdentifier().toString(cx));
    sb.append("{");
    for (int i = 0; i < a.length; i++)
    {
      sb.append(((BObject)a[i]).toString(cx)).append(',');
    }
    sb.append('}');
    return sb.toString();
  }

////////////////////////////////////////////////////////////////
//  Conversion
////////////////////////////////////////////////////////////////

  /**
   * Write the value to the Asn output stream.
   *
   * @param out the AsnOutput stream.
   */
  public void writeAsn(AsnOutput out)
  {
    out.writeObjectIdentifier(OBJECT_ID_TAG, getObjectIdentifier());
    out.writeOpeningTag(LIST_OF_RESULTS_TAG);

    SlotCursor<Property> sc = getProperties();
    while (sc.next(BReadPropertyResult.class))
    {
      BReadPropertyResult propRef = (BReadPropertyResult)sc.get();
      propRef.writeAsn(out);
    }

    out.writeClosingTag(LIST_OF_RESULTS_TAG);
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public void readAsn(AsnInput in)
    throws AsnException
  {
    set(objectIdentifier, in.readObjectIdentifier(OBJECT_ID_TAG), noWrite);

    int tag = in.peekTag();
    if (in.isOpeningTag(LIST_OF_RESULTS_TAG))
      in.skipTag();
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);

    tag = in.peekTag();
    while (!in.isClosingTag(LIST_OF_RESULTS_TAG))
    {
      if (tag == AsnInput.END_OF_DATA)
        throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);

      BReadPropertyResult res = new BReadPropertyResult();

      res.readAsn(in);

      add(null, res, noWrite);

      tag = in.peekTag();
    }
    in.skipTag();
  }


////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  public static final int OBJECT_ID_TAG = 0;
  public static final int LIST_OF_RESULTS_TAG = 1;


}
