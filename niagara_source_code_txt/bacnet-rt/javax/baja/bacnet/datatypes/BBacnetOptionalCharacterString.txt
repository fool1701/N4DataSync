/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BString;
import javax.baja.sys.BStruct;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.asn.AsnConst;


/**
 * Recipient for an alarm to be exported to Bacnet.
 * <p>
 * BBacnetRecipient represents the BacnetRecipient
 * choice.
 *
 * @author Joseph Chandler
 * @version $Revision: 3$ $Date: 12/10/01 9:26:16 AM$
 * @since Niagara 4.0.1
 */

@NiagaraType
@NiagaraProperty(
  name = "choice",
  type = "int",
  defaultValue = "0",
  facets = @Facet("BFacets.makeInt(0,1)")
)
@NiagaraProperty(
  name = "characterString",
  type = "String",
  defaultValue = ""
)
public final class BBacnetOptionalCharacterString
  extends BStruct
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetOptionalCharacterString(3028754927)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "choice"

  /**
   * Slot for the {@code choice} property.
   * @see #getChoice
   * @see #setChoice
   */
  public static final Property choice = newProperty(0, 0, BFacets.makeInt(0,1));

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

  //region Property "characterString"

  /**
   * Slot for the {@code characterString} property.
   * @see #getCharacterString
   * @see #setCharacterString
   */
  public static final Property characterString = newProperty(0, "", null);

  /**
   * Get the {@code characterString} property.
   * @see #characterString
   */
  public String getCharacterString() { return getString(characterString); }

  /**
   * Set the {@code characterString} property.
   * @see #characterString
   */
  public void setCharacterString(String v) { setString(characterString, v, null); }

  //endregion Property "characterString"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetOptionalCharacterString.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default (NULL) constructor
   */
  public BBacnetOptionalCharacterString()
  {
    setChoice(NULL_TAG);
    setCharacterString("");
  }

  /**
   * String ID constructor.
   *
   * @param string String to wrap in an optional character string value object
   */
  public BBacnetOptionalCharacterString(String string)
  {
    if (string == null)
    {
      setChoice(NULL_TAG);
      setCharacterString("");
    }else
    {
      setChoice(CHARACTER_STRING_TAG);
      setCharacterString(string);
    }

  }

////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  /**
   * Is this a device-type recipient?
   *
   * @return true if device, false if address.
   */
  public boolean isNull()
  {
    return getChoice() == NULL_TAG;
  }

  /**
   * Is this a address-type recipient?
   *
   * @return true if address, false if device.
   */
  public boolean isCharacterString()
  {
    return getChoice() == CHARACTER_STRING_TAG;
  }

  /**
   * Get the recipient as a BValue.
   *
   * @return the recipient.
   */
  public BValue getCharacterStringValue()
  {
    if (getChoice() == NULL_TAG)
      return null;
    else
      return BString.make(getCharacterString());
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
      case NULL_TAG:
        out.writeNull();
        break;
      case CHARACTER_STRING_TAG:
        out.writeOpeningTag(CHARACTER_STRING_TAG);
        out.writeCharacterString(getCharacterString());
        out.writeClosingTag(CHARACTER_STRING_TAG);
        break;
      default:
        throw new IllegalStateException("Invalid recipient type:" + getChoice());
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
      case NULL_TAG:
        break;
      case CHARACTER_STRING_TAG:
        in.skipTag();  // skip opening tag
        setCharacterString(in.readCharacterString(CHARACTER_STRING_TAG));
        in.skipTag();  // skip closing tag
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
    return getCharacterString();
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int NULL_TAG = 0;
  public static final int CHARACTER_STRING_TAG = 1;

}
