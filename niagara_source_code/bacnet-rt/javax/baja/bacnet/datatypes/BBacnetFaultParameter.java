/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.enums.BBacnetLifeSafetyState;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.bacnet.virtual.BacnetVirtualUtil;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BBlob;
import javax.baja.sys.BComponent;
import javax.baja.sys.BString;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.BacUtil;
import com.tridium.bacnet.asn.AsnConst;
import com.tridium.bacnet.asn.AsnInputStream;

/**
 * Created by Sandipan Aich on 3/24/2017.
 */
@NiagaraType
@NiagaraProperty(
  name = "choice",
  type = "int",
  defaultValue = "0"
)
public class BBacnetFaultParameter extends BComponent implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetFaultParameter(946239666)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "choice"

  /**
   * Slot for the {@code choice} property.
   * @see #getChoice
   * @see #setChoice
   */
  public static final Property choice = newProperty(0, 0, null);

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
  public static final Type TYPE = Sys.loadType(BBacnetFaultParameter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  @Override
  public void writeAsn(AsnOutput out)
  {
    int choice = getChoice();

    out.writeOpeningTag(choice);

    try
    {
      switch(choice)
      {
        case NONE:
          out.writeNull();
          break;
        case FAULT_CHARACTERSTRING:
          out.writeOpeningTag(FAULT_CHARACTERSTRING_LIST_OF_FAULT_VALUES);
          ((BBacnetListOf)get("listOfFaultValues")).writeAsn(out);
          out.writeClosingTag(FAULT_CHARACTERSTRING_LIST_OF_FAULT_VALUES);
          break;
        case FAULT_EXTENDED:
          out.writeUnsigned(FAULT_EXTENDED_VENDOR_ID, (BBacnetUnsigned)get("vendorId"));
          out.writeUnsigned(FAULT_EXTENDED_EXTENDED_FAULT_TYPE, (BBacnetUnsigned)get("extendedFaultType"));
          out.writeEncodedValue(FAULT_EXTENDED_PARAMETERS, ((BBlob)get("parameters")).copyBytes());
          break;
        case FAULT_LIFE_SAFETY:
          out.writeOpeningTag(FAULT_LIFE_SAFETY_LIST_OF_FAULT_VALUES);
          ((BBacnetListOf)get("listOfFaultValues")).writeAsn(out);
          out.writeClosingTag(FAULT_LIFE_SAFETY_LIST_OF_FAULT_VALUES);

          out.writeOpeningTag(FAULT_LIFE_SAFETY_MODE_PROPERTY_REFERENCE);
          ((BBacnetDeviceObjectPropertyReference)get("modePropertyReference")).writeAsn(out);
          out.writeClosingTag(FAULT_LIFE_SAFETY_MODE_PROPERTY_REFERENCE);
          break;
        case FAULT_STATE:
          out.writeOpeningTag(FAULT_STATE_LIST_OF_FAULT_VALUES);
          ((BBacnetListOf)get("listOfFaultValues")).writeAsn(out);
          out.writeClosingTag(FAULT_STATE_LIST_OF_FAULT_VALUES);
          break;
        case FAULT_STATUS_FLAGS:
          out.writeOpeningTag(FAULT_STATUS_FLAGS_STATUS_FLAGS_REFERENCE);
          ((BBacnetDeviceObjectPropertyReference)get("statusFlagsReference")).writeAsn(out);
          out.writeClosingTag(FAULT_STATUS_FLAGS_STATUS_FLAGS_REFERENCE);
          break;
      }
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, "BBacnetFaultParameter writeAsn failure", e);
    }

    out.writeClosingTag(choice);
  }

  @Override
  public void readAsn(AsnInput in)
    throws AsnException
  {
    int tag = in.peekTag();
    if (tag < 0)
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG+tag);

    if(tag!=getChoice()) removeAll(noWrite);

    // Set the choice
    setInt(choice, tag, noWrite);
    in.skipTag();

    Property p;
    BBacnetListOf listOfFaultValues = null;
    switch (tag)
    {
      case NONE:
        in.readNull();
        break;

      case FAULT_CHARACTERSTRING:
        listOfFaultValues = new BBacnetListOf(BString.TYPE);
        listOfFaultValues.readAsn(AsnInputStream.make(in.readEncodedValue(FAULT_CHARACTERSTRING_LIST_OF_FAULT_VALUES)));
        BacUtil.setOrAdd(this,"listOfFaultValues", listOfFaultValues, noWrite);
        break;

      case FAULT_EXTENDED:
        BacUtil.setOrAdd(this, "vendorId", BBacnetUnsigned.make(in.readUnsignedInteger(FAULT_EXTENDED_VENDOR_ID)), noWrite);
        BacUtil.setOrAdd(this, "extendedFaultType", BBacnetUnsigned.make(in.readUnsignedInteger(FAULT_EXTENDED_EXTENDED_FAULT_TYPE)), noWrite);
        BacUtil.setOrAdd(this,"parameters", BBlob.make(in.readEncodedValue(FAULT_EXTENDED_PARAMETERS)), noWrite);

        break;

      case FAULT_LIFE_SAFETY:
        listOfFaultValues = new BBacnetListOf(BBacnetLifeSafetyState.TYPE);
        listOfFaultValues.readAsn(AsnInputStream.make(in.readEncodedValue(FAULT_LIFE_SAFETY_LIST_OF_FAULT_VALUES)));
        BacUtil.setOrAdd(this,"listOfFaultValues", listOfFaultValues, noWrite);

        BBacnetDeviceObjectPropertyReference modePropertyReference = new BBacnetDeviceObjectPropertyReference();
        in.skipTag();
        modePropertyReference.readAsn(in);
        in.skipTag();
        BacUtil.setOrAdd(this, "modePropertyReference", modePropertyReference, noWrite);
        break;

      case FAULT_STATE:
        listOfFaultValues = new BBacnetListOf(BBacnetPropertyStates.TYPE);
        listOfFaultValues.readAsn(AsnInputStream.make(in.readEncodedValue(FAULT_STATE_LIST_OF_FAULT_VALUES)));
        BacUtil.setOrAdd(this,"listOfFaultValues", listOfFaultValues, noWrite);
        break;

      case FAULT_STATUS_FLAGS:
        BBacnetDeviceObjectPropertyReference statusFlagsReference = new BBacnetDeviceObjectPropertyReference();
        in.skipTag();
        statusFlagsReference.readAsn(in);
        in.skipTag();
        BacUtil.setOrAdd(this, "statusFlagsReference", statusFlagsReference, noWrite);
        break;
    }
    in.skipTag();
  }

  ////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out) throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetFaultParameter", 2);
    out.prop("virtual", BacnetVirtualUtil.isVirtual(this));
    out.endProps();
  }


/////////////////////////////////////////////////////////////////
//  Constants
/////////////////////////////////////////////////////////////////

  public static final int NONE = 0;


  public static final int FAULT_CHARACTERSTRING = 1;
  public static final int FAULT_CHARACTERSTRING_LIST_OF_FAULT_VALUES    = 0;



  public static final int FAULT_EXTENDED = 2;
  public static final int FAULT_EXTENDED_VENDOR_ID                     = 0;
  public static final int FAULT_EXTENDED_EXTENDED_FAULT_TYPE           = 1;
  public static final int FAULT_EXTENDED_PARAMETERS                    = 2;

  public static final int FAULT_LIFE_SAFETY = 3;
  public static final int FAULT_LIFE_SAFETY_LIST_OF_FAULT_VALUES       = 0;
  public static final int FAULT_LIFE_SAFETY_MODE_PROPERTY_REFERENCE    = 1;

  public static final int FAULT_STATE = 4;
  public static final int FAULT_STATE_LIST_OF_FAULT_VALUES             = 0;

  public static final int FAULT_STATUS_FLAGS = 5;
  public static final int FAULT_STATUS_FLAGS_STATUS_FLAGS_REFERENCE    = 0;

  private static final Logger logger = Logger.getLogger("bacnet.datatypes");

}
