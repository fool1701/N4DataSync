/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnInputStream;

/**
 * BBacnetAny represents the Bacnet ANY type.
 * This is a special type used in property definitions to indicate
 * that the value of the property can be any primitive data type.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 22 Oct 03
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "choice",
  type = "int",
  defaultValue = "ASN_NULL",
  flags = Flags.HIDDEN,
  facets = @Facet("BFacets.makeInt(0,12)")
)
@NiagaraProperty(
  name = "value",
  type = "BSimple",
  defaultValue = "BBacnetNull.DEFAULT"
)
public final class BBacnetAny
  extends BComponent
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetAny(670405905)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "choice"

  /**
   * Slot for the {@code choice} property.
   * @see #getChoice
   * @see #setChoice
   */
  public static final Property choice = newProperty(Flags.HIDDEN, ASN_NULL, BFacets.makeInt(0,12));

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

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, BBacnetNull.DEFAULT, null);

  /**
   * Get the {@code value} property.
   * @see #value
   */
  public BSimple getValue() { return (BSimple)get(value); }

  /**
   * Set the {@code value} property.
   * @see #value
   */
  public void setValue(BSimple v) { set(value, v, null); }

  //endregion Property "value"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetAny.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetAny()
  {
  }

  /**
   * Constructor.
   *
   * @param s
   */
  public BBacnetAny(BSimple s)
  {
    setAny(s);
  }

  /**
   * Asn Factory.
   *
   * @param encodedValue
   */
  public static BBacnetAny make(byte[] encodedValue)
    throws AsnException
  {
    BBacnetAny any = new BBacnetAny();
    synchronized (asnIn)
    {
      asnIn.setBuffer(encodedValue);
      any.readAsn(asnIn);
    }
    return any;
  }

  /**
   * Property changed. If running pass value changed call up to parent.
   */
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isMounted() || !isRunning()) return;

    // If choice was changed then value will be changed also
    if (p == choice) return;

    getParent().asComponent().changed(getPropertyInParent(), cx);
  }


////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the any value as a BValue.
   *
   * @return the value property, as a BSimple.
   */
  public BSimple getAny()
  {
    return getValue();
  }

  /**
   * Set the any value.
   *
   * @param v the any value.
   */
  public void setAny(BSimple v)
  {
    setAny(v, null);
  }

  /**
   * Set the any value.
   *
   * @param v  the any value.
   * @param cx the context for the set.
   */
  public void setAny(BValue v, Context cx)
  {
    if (v == null) v = BBacnetNull.DEFAULT;
    Type t = v.getType();
    if (t == BBacnetNull.TYPE)
      setInt(choice, ASN_NULL, cx);
    else if (t == BBoolean.TYPE)
      setInt(choice, ASN_BOOLEAN, cx);
    else if (t == BBacnetUnsigned.TYPE)
      setInt(choice, ASN_UNSIGNED, cx);
    else if (t == BInteger.TYPE)
      setInt(choice, ASN_INTEGER, cx);
    else if (t == BFloat.TYPE)
      setInt(choice, ASN_REAL, cx);
    else if (t == BDouble.TYPE)
      setInt(choice, ASN_DOUBLE, cx);
    else if (t == BBacnetOctetString.TYPE)
      setInt(choice, ASN_OCTET_STRING, cx);
    else if (t == BString.TYPE)
      setInt(choice, ASN_CHARACTER_STRING, cx);
    else if (t == BBacnetBitString.TYPE)
      setInt(choice, ASN_BIT_STRING, cx);
    else if (t.is(BEnum.TYPE))
      setInt(choice, ASN_ENUMERATED, cx);
    else if (t == BBacnetDate.TYPE)
      setInt(choice, ASN_DATE, cx);
    else if (t == BBacnetTime.TYPE)
      setInt(choice, ASN_TIME, cx);
    else if (t == BBacnetObjectIdentifier.TYPE)
      setInt(choice, ASN_OBJECT_IDENTIFIER, cx);
    else
      throw new IllegalArgumentException("Invalid type for BBacnetAny:" + t);

    set(value, v, cx);
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
    switch (getChoice())
    {
      case ASN_NULL:
        out.writeNull();
        break;
      case ASN_BOOLEAN:
        out.writeBoolean((BBoolean)getValue());
        break;
      case ASN_UNSIGNED:
        out.writeUnsigned((BBacnetUnsigned)getValue());
        break;
      case ASN_INTEGER:
        out.writeSignedInteger((BInteger)getValue());
        break;
      case ASN_REAL:
        out.writeReal((BFloat)getValue());
        break;
      case ASN_DOUBLE:
        out.writeDouble((BDouble)getValue());
        break;
      case ASN_OCTET_STRING:
        out.writeOctetString((BBacnetOctetString)getValue());
        break;
      case ASN_CHARACTER_STRING:
        out.writeCharacterString((BString)getValue());
        break;
      case ASN_BIT_STRING:
        out.writeBitString((BBacnetBitString)getValue());
        break;
      case ASN_ENUMERATED:
        out.writeEnumerated((BEnum)getValue());
        break;
      case ASN_DATE:
        out.writeDate((BBacnetDate)getValue());
        break;
      case ASN_TIME:
        out.writeTime((BBacnetTime)getValue());
        break;
      case ASN_OBJECT_IDENTIFIER:
        out.writeObjectIdentifier((BBacnetObjectIdentifier)getValue());
        break;
      default:
        throw new IllegalStateException("Invalid any type:" + getChoice());
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
    int tag = in.peekTag();
    switch (tag)
    {
      case ASN_NULL:
        setAny(in.readNull(), noWrite);
        break;
      case ASN_BOOLEAN:
        setAny(BBoolean.make(in.readBoolean()), noWrite);
        break;
      case ASN_UNSIGNED:
        setAny(in.readUnsigned(), noWrite);
        break;
      case ASN_INTEGER:
        setAny(in.readSigned(), noWrite);
        break;
      case ASN_REAL:
        setAny(in.readFloat(), noWrite);
        break;
      case ASN_DOUBLE:
        setAny(BDouble.make(in.readDouble()), noWrite);
        break;
      case ASN_OCTET_STRING:
        setAny(in.readBacnetOctetString(), noWrite);
        break;
      case ASN_CHARACTER_STRING:
        setAny(BString.make(in.readCharacterString()), noWrite);
        break;
      case ASN_BIT_STRING:
        setAny(in.readBitString(), noWrite);
        break;
      case ASN_ENUMERATED:
        setAny(BDynamicEnum.make(in.readEnumerated()), noWrite);
        break;
      case ASN_DATE:
        setAny(in.readDate(), noWrite);
        break;
      case ASN_TIME:
        setAny(in.readTime(), noWrite);
        break;
      case ASN_OBJECT_IDENTIFIER:
        setAny(in.readObjectIdentifier(), noWrite);
        break;
      default:
        logger.severe("Unknown any type: " + tag);
    }
  }


////////////////////////////////////////////////////////////////
//  Utility Methods
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context cx)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(tag(getChoice(), cx)).append(':')
      .append(getAny().toString(cx));
    return sb.toString();
  }

  /**
   * To String.
   */
  public String toDebugString()
  {
    StringBuilder sb = new StringBuilder();

    sb.append(tag(getChoice(), null));
    sb.append(getAny().toString());
    return sb.toString();
  }

  private static String tag(int choice, Context cx)
  {
    if (choice < 0) return "INVALID";
    return ASN_PRIMITIVE_TAGS[choice];
  }


////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  private static final AsnInputStream asnIn = new AsnInputStream();
  private static final Logger logger = Logger.getLogger("bacnet");
}
