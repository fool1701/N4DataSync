/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnConst;

/**
 * BReadAccessSpecification represents the ReadAccessSpecification sequence.
 * This is used in the Group object.  It is not the class used for handling
 * ReadAccessSpecifications in the usage of ReadPropertyMultiple during
 * polling.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 26 Jul 2005
 * @since Niagara 3.1
 */
@NiagaraType
@NiagaraProperty(
  name = "objectIdentifier",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.DEFAULT"
)
public class BReadAccessSpecification
  extends BComponent
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BReadAccessSpecification(1187579084)1.0$ @*/
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
  public static final Type TYPE = Sys.loadType(BReadAccessSpecification.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BReadAccessSpecification()
  {
  }

  /**
   * Single reference constructor.
   */
  public BReadAccessSpecification(BBacnetPropertyReference ref)
  {
    // FIXX: will determine if we need to do newCopy(), or if use cases
    // will not require it.
    add(null, ref);
  }

  /**
   * Single reference constructor.
   */
  public BReadAccessSpecification(BBacnetPropertyReference[] refs)
  {
    // FIXX: will determine if we need to do newCopy(), or if use cases
    // will not require it.
    if (refs != null)
    {
      for (int i = 0; i < refs.length; i++)
        add(null, refs[i]);
    }
  }


////////////////////////////////////////////////////////////////
//  BComponent
////////////////////////////////////////////////////////////////

  /**
   * No component children are allowed.  The only valid child is a
   * BBacnetObjectPropertyReference.
   */
  public boolean isChildLegal(BComponent child)
  {
    return false;
  }

  public String toString(Context cx)
  {
    loadSlots();
    Object[] a = getChildren(BBacnetPropertyReference.class);

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
    out.writeOpeningTag(LIST_OF_PROPERTY_REFERENCES_TAG);

    SlotCursor<Property> sc = getProperties();
    while (sc.next(BBacnetObjectPropertyReference.class))
    {
      BBacnetPropertyReference propRef = (BBacnetPropertyReference)sc.get();
      propRef.writeAsn(out);
    }

    out.writeClosingTag(LIST_OF_PROPERTY_REFERENCES_TAG);
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
    if (in.isOpeningTag(LIST_OF_PROPERTY_REFERENCES_TAG))
      in.skipTag();
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);

    tag = in.peekTag();
    while (!in.isClosingTag(LIST_OF_PROPERTY_REFERENCES_TAG))
    {
      if (tag == AsnInput.END_OF_DATA)
        throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);

      BBacnetPropertyReference propRef = new BBacnetPropertyReference();

      propRef.readAsn(in);

      add(null, propRef, noWrite);

      tag = in.peekTag();
    }
    in.skipTag();

  }


////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  public static final int OBJECT_ID_TAG = 0;
  public static final int LIST_OF_PROPERTY_REFERENCES_TAG = 1;


}
