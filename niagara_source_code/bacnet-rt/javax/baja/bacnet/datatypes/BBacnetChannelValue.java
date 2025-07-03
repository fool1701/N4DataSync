/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.enums.*;
import javax.baja.bacnet.enums.access.BBacnetAccessCredentialDisable;
import javax.baja.bacnet.enums.access.BBacnetAccessCredentialDisableReason;
import javax.baja.bacnet.enums.access.BBacnetAccessEvent;
import javax.baja.bacnet.enums.access.BBacnetAccessZoneOccupancyState;
import javax.baja.bacnet.enums.access.BBacnetAuthenticationStatus;
import javax.baja.bacnet.enums.access.BBacnetDoorAlarmState;
import javax.baja.bacnet.enums.access.BBacnetDoorSecuredStatus;
import javax.baja.bacnet.enums.access.BBacnetDoorStatus;
import javax.baja.bacnet.enums.access.BBacnetDoorValue;
import javax.baja.bacnet.enums.access.BBacnetLockStatus;
import javax.baja.bacnet.enums.security.BBacnetSecurityLevel;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.bacnet.virtual.BBacnetVirtualProperty;
import javax.baja.bacnet.virtual.BacnetVirtualUtil;
import javax.baja.category.BCategoryMask;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.*;
import javax.baja.util.Lexicon;

import com.tridium.bacnet.asn.AsnConst;

/**
 * BBacnetChannelValue represents the BACnetChannelValue
 * choice.
 *
 * @author Joseph Chandler
 * @creation 15 Apr 15
 * @since Niagara 4
 */

/*
 * BACnetChannelValue ::= CHOICE {
           null NULL,
           real REAL,
           enumerated ENUMERATED,
           unsigned Unsigned,
           boolean BOOLEAN,
           signed INTEGER,
           double Double,
           time Time,
           characterString CharacterString,
           octetString OCTET STRING,
           bitString BIT STRING,
           date Date,
           objectid BACnetObjectIdentifier,
           lightingCommand [0] BACnetLightingCommand
    } 
 */

@NiagaraType
@NiagaraProperty(
  name = "choice",
  type = "int",
  defaultValue = "0",
  flags = Flags.HIDDEN
)
public class BBacnetChannelValue
  extends BComponent
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetChannelValue(246626917)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "choice"

  /**
   * Slot for the {@code choice} property.
   * @see #getChoice
   * @see #setChoice
   */
  public static final Property choice = newProperty(Flags.HIDDEN, 0, null);

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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetChannelValue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public String toString(Context cx)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getTag());
    int choice = getChoice();
    if (choice > 0 && choice < slotNames.length)
    {
      sb.append(slotNames[choice]).append(":")
        .append(get(slotNames[choice]));
    }
    return sb.toString();
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
    int tag = getChoice();
    switch (tag)
    {
      case REAL_TAG:
        out.writeReal(tag, (BNumber)get("real"));
        break;
      case ENUMERATED_TAG:
        out.writeEnumerated(tag, ((BInteger)get("enumerated")).getInt());
        break;
      case UNSIGNED_TAG:
        out.writeUnsigned(tag, (BBacnetUnsigned)get("unsigned"));
        break;
      case BOOLEAN_TAG:
        out.writeBoolean(tag, (BBoolean)get("boolean"));
        break;
      case SIGNED_TAG:
        out.writeSignedInteger(tag, (BInteger)get("signed"));
        break;
      case DOUBLE_TAG:
        out.writeDouble(tag, (BDouble)get("double"));
        break;
      case TIME_TAG:
        out.writeTime(tag, (BBacnetTime)get("time"));
        break;
      case CHARACTERSTRING_TAG:
        out.writeCharacterString(tag, (BString)get("characterString"));
        break;
      case OCTETSTRING_TAG:
        out.writeOctetString(tag, (BBacnetOctetString)get("octetString"));
        break;
      case BITSTRING_TAG:
        out.writeBitString(tag, (BBacnetBitString)get("bitString"));
        break;
      case DATE_TAG:
        out.writeDate(tag, (BBacnetDate)get("date"));
        break;
      case OBJECTID_TAG:
        out.writeObjectIdentifier(tag, (BBacnetObjectIdentifier)get("objectId"));
        break;
      case LIGHTCOMMAND_TAG:
        BBacnetLightingCommand lightingCommand = (BBacnetLightingCommand)get("lightCommand");
        out.writeOpeningTag(LIGHTCOMMAND_SUB_TAG);
        lightingCommand.writeAsn(out);
        out.writeClosingTag(LIGHTCOMMAND_SUB_TAG);
        break;
      default:
        break;
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
    if ((tag < 0) || (tag > MAX_TAG))
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);

    setInt(choice, tag, noWrite);

    removeAll(noWrite);

    switch (tag)
    {
      case REAL_TAG:
        add("real", BFloat.make(in.readReal(tag)), noWrite);
        break;
      case ENUMERATED_TAG:
        add("enumerated", BInteger.make(in.readEnumerated(tag)), noWrite);
        break;
      case UNSIGNED_TAG:
        add("unsigned", BBacnetUnsigned.make(in.readEnumerated(tag)), noWrite);
        break;
      case BOOLEAN_TAG:
        add("unsigned", BBoolean.make(in.readBoolean(tag)), noWrite);
        break;

      case SIGNED_TAG:
        add("signed", in.readSigned(tag), noWrite);
        break;

      case DOUBLE_TAG:
        add("signed", BDouble.make(in.readDouble(tag)), noWrite);
        break;

      case TIME_TAG:
        add("time", in.readTime(TIME_TAG), noWrite);
        break;

      case CHARACTERSTRING_TAG:
        add("characterString", BString.make(in.readCharacterString(TIME_TAG)), noWrite);
        break;

      case OCTETSTRING_TAG:
        add("octetString", in.readBacnetOctetString(OCTETSTRING_TAG), noWrite);
        break;

      case BITSTRING_TAG:
        add("bitString", in.readBitString(BITSTRING_TAG), noWrite);
        break;

      case DATE_TAG:
        add("date", in.readDate(DATE_TAG), noWrite);
        break;

      case OBJECTID_TAG:
        add("objectId", in.readObjectIdentifier(OBJECTID_TAG), noWrite);
        break;

      case LIGHTCOMMAND_TAG:
        in.skipOpeningTag(LIGHTCOMMAND_SUB_TAG);
        BBacnetLightingCommand lightCommand = new BBacnetLightingCommand();
        lightCommand.readAsn(in);
        add("lightCommand", lightCommand, noWrite);
        in.skipClosingTag(LIGHTCOMMAND_SUB_TAG);
        break;

      default:
        in.skipTag();
        break;
    }

  }

  private final void addEnum(BEnum defEnum, AsnInput in, int tag)
    throws AsnException
  {
    add(slotNames[tag], BDynamicEnum.make(in.readEnumerated(tag), defEnum.getRange()), noWrite);
  }

////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out) throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetPropertyStates", 2);
    out.prop("virtual", BacnetVirtualUtil.isVirtual(this));
    out.endProps();
  }


/////////////////////////////////////////////////////////////////
//  Constants
/////////////////////////////////////////////////////////////////

  private static final int MAX_TAG = 254;


  public static final int NULL_TAG = 0;
  public static final int REAL_TAG = 1;
  public static final int ENUMERATED_TAG = 2;
  public static final int UNSIGNED_TAG = 3;
  public static final int BOOLEAN_TAG = 4;
  public static final int SIGNED_TAG = 5;
  public static final int DOUBLE_TAG = 6;
  public static final int TIME_TAG = 7;
  public static final int CHARACTERSTRING_TAG = 8;
  public static final int OCTETSTRING_TAG = 9;
  public static final int BITSTRING_TAG = 10;
  public static final int DATE_TAG = 11;
  public static final int OBJECTID_TAG = 12;
  public static final int LIGHTCOMMAND_TAG = 13;
  public static final int LIGHTCOMMAND_SUB_TAG = 0;

  private static final int MAX_DEFINED_CHOICE = LIGHTCOMMAND_TAG;
  private static final int MAX_ASHRAE_CHOICE = 63;

  private static final Lexicon lex = Lexicon.make("bacnet");

  private static String[] tags = new String[]
    {
/* 0 */   lex.getText("BacnetChannel.null"),
/* 1 */   lex.getText("BacnetChannel.real"),
/* 2 */   lex.getText("BacnetChannel.enumerated"),
/* 3 */   lex.getText("BacnetChannel.unsigned"),
/* 4 */   lex.getText("BacnetChannel.boolean"),
/* 5 */   lex.getText("BacnetChannel.signed"),
/* 6 */   lex.getText("BacnetChannel.double"),
/* 7 */   lex.getText("BacnetChannel.time"),
/* 8 */   lex.getText("BacnetChannel.characterString"),
/* 9 */   lex.getText("BacnetChannel.octetString"),
/* 10 */  lex.getText("BacnetChannel.bitString"),
/* 11 */  lex.getText("BacnetChannel.date"),
/* 12 */  lex.getText("BacnetChannel.objectId"),
/* 13 */  lex.getText("BacnetChannel.lightingCommand"),

//---------------------------------------------------------------------
/* 14 */  lex.getText("BacnetChannel.ashrae"),
/* 15 */  lex.getText("BacnetChannel.proprietary"),
/* 16 */  lex.getText("BacnetChannel.invalid")
    };

  private static String[] slotNames = new String[] {
    /* 0 */   "null",
    /* 1 */   "real",
    /* 2 */   "enumerated",
    /* 3 */   "unsigned",
    /* 4 */   "boolean",
    /* 5 */   "signed",
    /* 6 */   "double",
    /* 7 */   "time",
    /* 8 */   "characterString",
    /* 9 */   "octetString",
    /* 10 */  "bitString",
    /* 11 */  "date",
    /* 12 */  "objectId",
    /* 13 */  "lightingCommand",
  };

  private static final int ASHRAE_CHOICE_INDEX = 14;
  private static final int PROPRIETARY_CHOICE_INDEX = 15;
  private static final int INVALID_CHOICE_INDEX = 16;

  private String getTag()
  {
    int ch = getChoice();
    if ((ch < 0) || (ch > 254)) ch = INVALID_CHOICE_INDEX;
    if (ch <= MAX_DEFINED_CHOICE) return tags[ch];
    ch = (ch <= MAX_ASHRAE_CHOICE) ? ASHRAE_CHOICE_INDEX : PROPRIETARY_CHOICE_INDEX;
    return tags[ch];
  }
}
