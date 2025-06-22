/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import java.text.DecimalFormat;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusString;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BBlob;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFloat;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInteger;
import javax.baja.sys.BNumber;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.DuplicateSlotException;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.user.BUser;

import com.tridium.flexSerial.enums.BDataTypeEnum;
import com.tridium.flexSerial.enums.BEncodeTypeEnum;
import com.tridium.program.BProgram;

/**
 * BFlexMessageElement defines a primitive message element.  
 * <P>The purpose of the FlexMessageElement is to completely define a primitive element
 * of a message.  It contain enough information to serialize this element value to and 
 * from a native byte array.  The FlexSerial driver provides a special view and editor 
 * that can be used to create FlexMessageElements.</P>
 *
 * @author    Andy Saunders
 * @creation  14 Sept 2005
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "offset",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
/*
 This is the byte size of this message element.  It is automatically set
 based on the dataType selected.
 */
@NiagaraProperty(
  name = "size",
  type = "int",
  defaultValue = "1",
  flags = Flags.READONLY
)
/*
 This specifies the primitive data type of this message element.
 Valid dataTypes are:
 <P> <B>byte:</B> a single byte (8 bits) of data.</P>
 <P> <B>word:</B> two bytes of data (16 bits).</P>
 <P> <B>integer:</B> 4 bytes of data (32 bits).</P>
 <P> <B>float:</B> a 32 bit IEEE encoded floating point number.</P>
 <P> <B>string:</B> String data</P>
 <P> <B>marker:</B> a a special named placeholder and consumes no space in the message
 but defines an offset into the message. "cksumStart" is a special named marker
 that defines the message offset at which cksum calculations start. </P>
 */
@NiagaraProperty(
  name = "dataType",
  type = "BDataTypeEnum",
  defaultValue = "BDataTypeEnum.Byte"
)
/*
 This specifies that the value of this message is "indirect" and is to be read
 from the object pointed to by this Ord.
 <P>If this Ord is pointing to a Program object it is assumed to be a program object
 that is being used to calculate a cksum or CRC message element. </P>
 */
@NiagaraProperty(
  name = "source",
  type = "BOrd",
  defaultValue = "BOrd.NULL"
)
/*
 This will cause the value to be exposed in the parent Flex message.
 */
@NiagaraProperty(
  name = "exposeInParent",
  type = "boolean",
  defaultValue = "false"
)
/*
 This is the value of this message element and is dependent on the dataType
 specified.  This value can be a constant or can be a "indirect" value from
 the object defined in the source property.
 */
@NiagaraProperty(
  name = "value",
  type = "BValue",
  defaultValue = "BInteger.make(0)"
)
/*
 This is used to select special encoding used.  Encoding supported are:
 <P>   <B>none:</B> raw binary
 <P>   <B>ascii:</B> The value data is converted to an ascii string.
 <P>   <B>asciiHex:</B> The value data is converted to an ascii hex string.
 */
@NiagaraProperty(
  name = "encode",
  type = "BEncodeTypeEnum",
  defaultValue = "BEncodeTypeEnum.None"
)
/*
 This is used to support other special name-value pairs  used to support this
 message element.  Facets used are:
 <P>   <B>"activeText", String:</B> used with boolean values and ascii encoding. </P>
 <P>   <B>"activeValue", integer:</B> defines the numeric value used to represent a boolean
 active value in the byte array. </P>
 <P>   <B>"bigEndian", boolean:</B> defines how multi-byte numbers are placed in the
 byte array. </P>
 <P>   <B>"decimalFormat", String:</B> defines format to be used when encoding a numeric value
 to a ascii encoded string.  Uses same pattern string as defined in the Java DecimalFormat
 class.
 <P>   <B>"endDelimiter", String:</B> defines a one character string that delimits this message element.
 <P>   <B>"fieldWidth", integer:</B> used with String values to define that the string is
 a fixed length.  The "padwith" facet defines the value used for padding. </P>
 <P>   <B>"inactiveValue", integer:</B> defines the numeric value used to represent a boolean
 inactive value in the byte array. </P>
 <P>   <B>"inactiveText", String:</B> used with boolean values and ascii encoding.</P>
 <P>   <B>"max", float:</B> used with numeric values and ascii encoding.  Can be used
 to control the maximum number that numeric value will be converted to.</P>
 <P>   <B>"min", float:</B> used with numeric values and ascii encoding.  Can be used
 to control the minimum number that numeric value will be converted to.</P>
 <P>   <B>"nullTerminate", boolean:</B> used with String values to define that the string is
 to be null terminated. </P>
 <P>   <B>"padWith", integer:</B> used with String values to define value used to pad out a
 fixed length string message element.</P>
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"flexSerial:FlexFacetsFE\"))")
)
public abstract class BFlexMessageElement
  extends BComponent
  implements BIFlexMessageElement
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexMessageElement(1146336791)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "offset"

  /**
   * Slot for the {@code offset} property.
   * @see #getOffset
   * @see #setOffset
   */
  public static final Property offset = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code offset} property.
   * @see #offset
   */
  public int getOffset() { return getInt(offset); }

  /**
   * Set the {@code offset} property.
   * @see #offset
   */
  public void setOffset(int v) { setInt(offset, v, null); }

  //endregion Property "offset"

  //region Property "size"

  /**
   * Slot for the {@code size} property.
   * This is the byte size of this message element.  It is automatically set
   * based on the dataType selected.
   * @see #getSize
   * @see #setSize
   */
  public static final Property size = newProperty(Flags.READONLY, 1, null);

  /**
   * Get the {@code size} property.
   * This is the byte size of this message element.  It is automatically set
   * based on the dataType selected.
   * @see #size
   */
  public int getSize() { return getInt(size); }

  /**
   * Set the {@code size} property.
   * This is the byte size of this message element.  It is automatically set
   * based on the dataType selected.
   * @see #size
   */
  public void setSize(int v) { setInt(size, v, null); }

  //endregion Property "size"

  //region Property "dataType"

  /**
   * Slot for the {@code dataType} property.
   * This specifies the primitive data type of this message element.
   * Valid dataTypes are:
   * <P> <B>byte:</B> a single byte (8 bits) of data.</P>
   * <P> <B>word:</B> two bytes of data (16 bits).</P>
   * <P> <B>integer:</B> 4 bytes of data (32 bits).</P>
   * <P> <B>float:</B> a 32 bit IEEE encoded floating point number.</P>
   * <P> <B>string:</B> String data</P>
   * <P> <B>marker:</B> a a special named placeholder and consumes no space in the message
   * but defines an offset into the message. "cksumStart" is a special named marker
   * that defines the message offset at which cksum calculations start. </P>
   * @see #getDataType
   * @see #setDataType
   */
  public static final Property dataType = newProperty(0, BDataTypeEnum.Byte, null);

  /**
   * Get the {@code dataType} property.
   * This specifies the primitive data type of this message element.
   * Valid dataTypes are:
   * <P> <B>byte:</B> a single byte (8 bits) of data.</P>
   * <P> <B>word:</B> two bytes of data (16 bits).</P>
   * <P> <B>integer:</B> 4 bytes of data (32 bits).</P>
   * <P> <B>float:</B> a 32 bit IEEE encoded floating point number.</P>
   * <P> <B>string:</B> String data</P>
   * <P> <B>marker:</B> a a special named placeholder and consumes no space in the message
   * but defines an offset into the message. "cksumStart" is a special named marker
   * that defines the message offset at which cksum calculations start. </P>
   * @see #dataType
   */
  public BDataTypeEnum getDataType() { return (BDataTypeEnum)get(dataType); }

  /**
   * Set the {@code dataType} property.
   * This specifies the primitive data type of this message element.
   * Valid dataTypes are:
   * <P> <B>byte:</B> a single byte (8 bits) of data.</P>
   * <P> <B>word:</B> two bytes of data (16 bits).</P>
   * <P> <B>integer:</B> 4 bytes of data (32 bits).</P>
   * <P> <B>float:</B> a 32 bit IEEE encoded floating point number.</P>
   * <P> <B>string:</B> String data</P>
   * <P> <B>marker:</B> a a special named placeholder and consumes no space in the message
   * but defines an offset into the message. "cksumStart" is a special named marker
   * that defines the message offset at which cksum calculations start. </P>
   * @see #dataType
   */
  public void setDataType(BDataTypeEnum v) { set(dataType, v, null); }

  //endregion Property "dataType"

  //region Property "source"

  /**
   * Slot for the {@code source} property.
   * This specifies that the value of this message is "indirect" and is to be read
   * from the object pointed to by this Ord.
   * <P>If this Ord is pointing to a Program object it is assumed to be a program object
   * that is being used to calculate a cksum or CRC message element. </P>
   * @see #getSource
   * @see #setSource
   */
  public static final Property source = newProperty(0, BOrd.NULL, null);

  /**
   * Get the {@code source} property.
   * This specifies that the value of this message is "indirect" and is to be read
   * from the object pointed to by this Ord.
   * <P>If this Ord is pointing to a Program object it is assumed to be a program object
   * that is being used to calculate a cksum or CRC message element. </P>
   * @see #source
   */
  public BOrd getSource() { return (BOrd)get(source); }

  /**
   * Set the {@code source} property.
   * This specifies that the value of this message is "indirect" and is to be read
   * from the object pointed to by this Ord.
   * <P>If this Ord is pointing to a Program object it is assumed to be a program object
   * that is being used to calculate a cksum or CRC message element. </P>
   * @see #source
   */
  public void setSource(BOrd v) { set(source, v, null); }

  //endregion Property "source"

  //region Property "exposeInParent"

  /**
   * Slot for the {@code exposeInParent} property.
   * This will cause the value to be exposed in the parent Flex message.
   * @see #getExposeInParent
   * @see #setExposeInParent
   */
  public static final Property exposeInParent = newProperty(0, false, null);

  /**
   * Get the {@code exposeInParent} property.
   * This will cause the value to be exposed in the parent Flex message.
   * @see #exposeInParent
   */
  public boolean getExposeInParent() { return getBoolean(exposeInParent); }

  /**
   * Set the {@code exposeInParent} property.
   * This will cause the value to be exposed in the parent Flex message.
   * @see #exposeInParent
   */
  public void setExposeInParent(boolean v) { setBoolean(exposeInParent, v, null); }

  //endregion Property "exposeInParent"

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * This is the value of this message element and is dependent on the dataType
   * specified.  This value can be a constant or can be a "indirect" value from
   * the object defined in the source property.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, BInteger.make(0), null);

  /**
   * Get the {@code value} property.
   * This is the value of this message element and is dependent on the dataType
   * specified.  This value can be a constant or can be a "indirect" value from
   * the object defined in the source property.
   * @see #value
   */
  public BValue getValue() { return get(value); }

  /**
   * Set the {@code value} property.
   * This is the value of this message element and is dependent on the dataType
   * specified.  This value can be a constant or can be a "indirect" value from
   * the object defined in the source property.
   * @see #value
   */
  public void setValue(BValue v) { set(value, v, null); }

  //endregion Property "value"

  //region Property "encode"

  /**
   * Slot for the {@code encode} property.
   * This is used to select special encoding used.  Encoding supported are:
   * <P>   <B>none:</B> raw binary
   * <P>   <B>ascii:</B> The value data is converted to an ascii string.
   * <P>   <B>asciiHex:</B> The value data is converted to an ascii hex string.
   * @see #getEncode
   * @see #setEncode
   */
  public static final Property encode = newProperty(0, BEncodeTypeEnum.None, null);

  /**
   * Get the {@code encode} property.
   * This is used to select special encoding used.  Encoding supported are:
   * <P>   <B>none:</B> raw binary
   * <P>   <B>ascii:</B> The value data is converted to an ascii string.
   * <P>   <B>asciiHex:</B> The value data is converted to an ascii hex string.
   * @see #encode
   */
  public BEncodeTypeEnum getEncode() { return (BEncodeTypeEnum)get(encode); }

  /**
   * Set the {@code encode} property.
   * This is used to select special encoding used.  Encoding supported are:
   * <P>   <B>none:</B> raw binary
   * <P>   <B>ascii:</B> The value data is converted to an ascii string.
   * <P>   <B>asciiHex:</B> The value data is converted to an ascii hex string.
   * @see #encode
   */
  public void setEncode(BEncodeTypeEnum v) { set(encode, v, null); }

  //endregion Property "encode"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * This is used to support other special name-value pairs  used to support this
   * message element.  Facets used are:
   * <P>   <B>"activeText", String:</B> used with boolean values and ascii encoding. </P>
   * <P>   <B>"activeValue", integer:</B> defines the numeric value used to represent a boolean
   * active value in the byte array. </P>
   * <P>   <B>"bigEndian", boolean:</B> defines how multi-byte numbers are placed in the
   * byte array. </P>
   * <P>   <B>"decimalFormat", String:</B> defines format to be used when encoding a numeric value
   * to a ascii encoded string.  Uses same pattern string as defined in the Java DecimalFormat
   * class.
   * <P>   <B>"endDelimiter", String:</B> defines a one character string that delimits this message element.
   * <P>   <B>"fieldWidth", integer:</B> used with String values to define that the string is
   * a fixed length.  The "padwith" facet defines the value used for padding. </P>
   * <P>   <B>"inactiveValue", integer:</B> defines the numeric value used to represent a boolean
   * inactive value in the byte array. </P>
   * <P>   <B>"inactiveText", String:</B> used with boolean values and ascii encoding.</P>
   * <P>   <B>"max", float:</B> used with numeric values and ascii encoding.  Can be used
   * to control the maximum number that numeric value will be converted to.</P>
   * <P>   <B>"min", float:</B> used with numeric values and ascii encoding.  Can be used
   * to control the minimum number that numeric value will be converted to.</P>
   * <P>   <B>"nullTerminate", boolean:</B> used with String values to define that the string is
   * to be null terminated. </P>
   * <P>   <B>"padWith", integer:</B> used with String values to define value used to pad out a
   * fixed length string message element.</P>
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.DEFAULT, BFacets.make(BFacets.FIELD_EDITOR, BString.make("flexSerial:FlexFacetsFE")));

  /**
   * Get the {@code facets} property.
   * This is used to support other special name-value pairs  used to support this
   * message element.  Facets used are:
   * <P>   <B>"activeText", String:</B> used with boolean values and ascii encoding. </P>
   * <P>   <B>"activeValue", integer:</B> defines the numeric value used to represent a boolean
   * active value in the byte array. </P>
   * <P>   <B>"bigEndian", boolean:</B> defines how multi-byte numbers are placed in the
   * byte array. </P>
   * <P>   <B>"decimalFormat", String:</B> defines format to be used when encoding a numeric value
   * to a ascii encoded string.  Uses same pattern string as defined in the Java DecimalFormat
   * class.
   * <P>   <B>"endDelimiter", String:</B> defines a one character string that delimits this message element.
   * <P>   <B>"fieldWidth", integer:</B> used with String values to define that the string is
   * a fixed length.  The "padwith" facet defines the value used for padding. </P>
   * <P>   <B>"inactiveValue", integer:</B> defines the numeric value used to represent a boolean
   * inactive value in the byte array. </P>
   * <P>   <B>"inactiveText", String:</B> used with boolean values and ascii encoding.</P>
   * <P>   <B>"max", float:</B> used with numeric values and ascii encoding.  Can be used
   * to control the maximum number that numeric value will be converted to.</P>
   * <P>   <B>"min", float:</B> used with numeric values and ascii encoding.  Can be used
   * to control the minimum number that numeric value will be converted to.</P>
   * <P>   <B>"nullTerminate", boolean:</B> used with String values to define that the string is
   * to be null terminated. </P>
   * <P>   <B>"padWith", integer:</B> used with String values to define value used to pad out a
   * fixed length string message element.</P>
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * This is used to support other special name-value pairs  used to support this
   * message element.  Facets used are:
   * <P>   <B>"activeText", String:</B> used with boolean values and ascii encoding. </P>
   * <P>   <B>"activeValue", integer:</B> defines the numeric value used to represent a boolean
   * active value in the byte array. </P>
   * <P>   <B>"bigEndian", boolean:</B> defines how multi-byte numbers are placed in the
   * byte array. </P>
   * <P>   <B>"decimalFormat", String:</B> defines format to be used when encoding a numeric value
   * to a ascii encoded string.  Uses same pattern string as defined in the Java DecimalFormat
   * class.
   * <P>   <B>"endDelimiter", String:</B> defines a one character string that delimits this message element.
   * <P>   <B>"fieldWidth", integer:</B> used with String values to define that the string is
   * a fixed length.  The "padwith" facet defines the value used for padding. </P>
   * <P>   <B>"inactiveValue", integer:</B> defines the numeric value used to represent a boolean
   * inactive value in the byte array. </P>
   * <P>   <B>"inactiveText", String:</B> used with boolean values and ascii encoding.</P>
   * <P>   <B>"max", float:</B> used with numeric values and ascii encoding.  Can be used
   * to control the maximum number that numeric value will be converted to.</P>
   * <P>   <B>"min", float:</B> used with numeric values and ascii encoding.  Can be used
   * to control the minimum number that numeric value will be converted to.</P>
   * <P>   <B>"nullTerminate", boolean:</B> used with String values to define that the string is
   * to be null terminated. </P>
   * <P>   <B>"padWith", integer:</B> used with String values to define value used to pad out a
   * fixed length string message element.</P>
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexMessageElement.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// 
////////////////////////////////////////////////////////////////
  
  public BFacets getSlotFacets(Slot slot)
  {
    if(slot.equals(value))
      return getFacets();
    else
      return super.getSlotFacets(slot);
  }

  public void changed(Property property, Context context)
  {
    if(isRunning())
    {
      if(property.equals(value))
        exposeInParent();
  
      if(context != null && context.equals(Context.decoding))
      {
        exposeInParent();
        return;
      }
    }
    //System.out.println("*** " + this.getName() + ".changed with: " + property);
    if(property.equals(dataType) || property.equals(source))
    {
      switch(getDataType().getOrdinal())
      {
      case BDataTypeEnum.BYTE:
        setValue(BInteger.make(0));
        setSize(1);
        break;
      case BDataTypeEnum.WORD:
        setValue(BInteger.make(0));
        setSize(2);
        break;
      case BDataTypeEnum.INTEGER:
        setValue(BInteger.make(0));
        setSize(4);
        break;
      case BDataTypeEnum.FLOAT:
        setValue(BFloat.make(0f));
        setSize(4);
        break;
      case BDataTypeEnum.STRING:
        //System.out.println("datatype changed to String!!");
        setValue(BString.make(""));
        break;
      case BDataTypeEnum.MARKER:
        setValue(BString.make(""));
        setSize(0);
        break;
      }
    }
    else if(property.equals(value) || property.equals(facets) )
    {
      if(getDataType().equals(BDataTypeEnum.String))
      {
        setSize(convertInputToString(getValue()).length());
      }
    }
    //else if(property.equals(size))
    BIFlexMessageBlock pmc = getParentMessageComponent();
    if(pmc != null)
      pmc.calculateItemOffsets();
  }

  protected void exposeInParent()
  {
//    System.out.println("exposeInParent called");
//    BComplex ggParent = getParent().getParent();
//    System.out.println("ggParent = " + ggParent.getName());
//    System.out.println("element = " + getName());
    if(getExposeInParent())
    {
      BComplex ggParent = getParent().getParent();
//      System.out.println("ggParent = " + ggParent.getName());
      if(ggParent instanceof BFlexMessageSelect)
      {      
//        System.out.println("exposeInParent 111111");
        BFlexMessageSelect parentResp = (BFlexMessageSelect)ggParent;
        String valueName = getName();
        BStatusValue newValue = new BStatusNumeric();
        BValue rawValue = getValue().newCopy();
        switch(getDataType().getOrdinal())
        {
        case BDataTypeEnum.BYTE:
        case BDataTypeEnum.WORD:
        case BDataTypeEnum.INTEGER:
          int rawInt = ((BInteger)rawValue).getInt();
          int activeValue = getFacets().geti(ACTIVE_VALUE, -1);
          int inactiveValue = getFacets().geti(INACTIVE_VALUE, -1);
          if(activeValue != -1)
            newValue = new BStatusBoolean(rawInt == activeValue);
          else if(inactiveValue != -1)
            newValue = new BStatusBoolean(rawInt != inactiveValue);
          else
            newValue = new BStatusNumeric((double)rawInt);
          break;
        case BDataTypeEnum.STRING:
          String rawString = ((BString)rawValue).getString(); 
          String activeText = getFacets().gets(ACTIVE_TEXT, null);
          String inactiveText = getFacets().gets(INACTIVE_TEXT, null);
          if(activeText != null)
            newValue = new BStatusBoolean(rawString.equals(activeText));
          else if(inactiveText != null)
            newValue = new BStatusBoolean( ! rawString.equals(inactiveText));
          else
            newValue = new BStatusString(((BString)rawValue).getString());
          break;
        case BDataTypeEnum.FLOAT:
          if(rawValue instanceof BStatusNumeric)
            newValue = (BStatusNumeric)rawValue;
          else if(rawValue instanceof BDouble)
            newValue = new BStatusNumeric( ((BDouble)rawValue).getDouble() );
          else if(rawValue instanceof BFloat)
            newValue = new BStatusNumeric(((BFloat)rawValue).getDouble());
          break;
        }
        //System.out.println("valueName = " + valueName + " value = " + newValue); 
        try
        {
          parentResp.add(valueName, newValue, Flags.SUMMARY | Flags.READONLY);
        }
        catch(DuplicateSlotException e)
        {
          parentResp.set(valueName, newValue);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
      }
    }
  }
  
  protected BIFlexMessageBlock getParentMessageComponent()
  {
    BComplex parent = getParent();
    //System.out.print("" + this.getName() + " parent = ");
    while (parent != null) 
    {
      //System.out.print("" + parent.getName() + ".");
      if(parent instanceof BIFlexMessageBlock)
      {
        return (BIFlexMessageBlock)parent;
      }
      parent = parent.getParent();
    }
    return null;
  }

  public void readFrom(BObject baseObj, FlexInputStream inBytes)
  {
    //System.out.println("BFlexMessageBlock.readFrom(): " + this.getName());
    //if(isIndirect())
    //  readFromIndirect(baseObj, inBytes);
    //else
      readFromDirect(baseObj, inBytes);
  }

  public void readFromDirect(BObject baseObj, FlexInputStream inBytes)
  {
    try
    {
      switch(getDataType().getOrdinal())
      {
      case BDataTypeEnum.BYTE:
        set( value, BInteger.make(inBytes.read(getEncode(), getFacets()) & 0x0ff), Context.decoding);
        //System.out.println ("set value to: " + getValue());
        break;

      case BDataTypeEnum.WORD:
        set( value, BInteger.make(inBytes.readWord(getEncode(), getFacets())), Context.decoding);
        //System.out.println ("set value to: " + getValue());
        break;
      case BDataTypeEnum.INTEGER:
        set( value, BInteger.make(inBytes.readInt(getSize(), getEncode(), getFacets())), Context.decoding);
        //System.out.println ("set value to: " + getValue());
        break;
      case BDataTypeEnum.STRING:
        set( value, BString.make(inBytes.readString(getEncode(), getFacets())), Context.decoding);
        //System.out.println ("set value to: " + getValue());
        break;
      case BDataTypeEnum.FLOAT:
        set( value, BFloat.make(inBytes.readFloat(getEncode(), getFacets())), Context.decoding);
        //System.out.println ("set value to: " + getValue());
        break;
      default:
      }
      //exposeInParent();
    }
    catch(Exception e)
    {
      System.out.println(" ******************** invalid data type conversion *************");
      System.out.println("source object type = " + baseObj.getType());
      switch(getDataType().getOrdinal())
      {
      default:
      }
    }
  }

  public void readFromIndirect(BObject baseObj, FlexInputStream inBytes)
  {
    BOrd sourceOrd = getSource();
    BObject sourceObj = sourceOrd.get(baseObj);
    //System.out.println("**** got object: " + sourceObj);
    readFrom(inBytes, baseObj);
    //setValue(((BValue)sourceObj).newCopy());
  }

  // FIXX need to add a lot more stuff here to do data type coversions.
  public void readFrom(FlexInputStream inBytes, BObject object)
  {
    try
    {
      switch(getDataType().getOrdinal())
      {
      case BDataTypeEnum.BYTE:
        //System.out.println(" target dataType = " + object.getType());
        if(object instanceof BStatusNumeric)
          ((BStatusNumeric)object).setValue((double)( (inBytes.read(getEncode(), getFacets()) & 0x0ff)));
        break;

      default:
      }
    }
    catch(Exception e)
    {
      System.out.println(" ******************** invalid data type conversion *************");
      System.out.println("source object type = " + object.getType());
      switch(getDataType().getOrdinal())
      {
      default:
      }
    }
  }

  public void writeTo(BObject baseObj, FlexOutputStream out)
  {
    //System.out.println("BFlexMessageBlock.writeTo(): " + this.getName());
    if(isIndirect())
      writeToIndirect(baseObj, out);
    else
      writeToDirect(baseObj, out);
  }

  public void writeToDirect(BObject baseObj, FlexOutputStream out)
  {
    writeTo(out, getValue());
  }

  public void writeToIndirect(BObject baseObj, FlexOutputStream out)
  {
    BOrd sourceOrd = getSource();
    //System.out.println("********* source object = " +baseObj);
    BObject sourceObj = sourceOrd.get(baseObj);
    //System.out.println("**** got object: " + sourceObj);
    if(sourceObj instanceof BProgram)
    {
      BProgram program = (BProgram)sourceObj;
      program.set("byteArray", BBlob.make(out.toByteArray()));
      program.set("offset", BInteger.make(out.getCksumStart()));
      program.doExecute();
      //System.out.println("calculated cksum = " + program.get("cksumResults"));
      writeTo(out, program.get("cksumResults"));
      setValue(program.get("cksumResults"));
    }
    else
    {
      writeTo(out, sourceObj);
      if(sourceObj instanceof BComplex)
       set(value, ((BComplex)sourceObj).newCopy(), Context.decoding );
      else
        set(value, (BValue)sourceObj, Context.decoding );
        
    }
  }

  public void writeTo(FlexOutputStream out, BObject object)
  {

    try
    {
      switch(getDataType().getOrdinal())
      {
      case BDataTypeEnum.BYTE:
        out.write( convertInputToInt(object), getEncode(), getFacets() );
        break;
      case BDataTypeEnum.WORD:
        out.writeInt( convertInputToInt(object), 2, getEncode(), getFacets() );
        break;
      case BDataTypeEnum.INTEGER:
        out.writeInt( convertInputToInt(object), getSize(), getEncode(), getFacets() );
        break;
      case BDataTypeEnum.FLOAT:
        out.writeInt( Float.floatToIntBits( convertInputToFloat(object) ), 4, getEncode(), getFacets() );
        setSize(4);
        break;
      case BDataTypeEnum.STRING:
        out.writeString( convertInputToString(object), getEncode(), getFacets());
        if(getFacets().getb(NULL_TERMINATE, false))
          out.write(0x00);
        break;
      case BDataTypeEnum.MARKER:
        //System.out.println("           messageItem marker found: " + getName());
        //System.out.println("           out.size() = " + out.size());
        if(getName().equals(CKSUM_START_MARKER_NAME))
        {
          out.setCksumStart(out.size());
        }
        break;
      default:
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
      System.out.println(" ******************** invalid data type conversion *************");
      System.out.println("source object type = " + object.getType());
      switch(getDataType().getOrdinal())
      {
      case BDataTypeEnum.BYTE:
        out.write( -1 );
        break;
      case BDataTypeEnum.WORD:
        out.writeInt( -1, 2, getEncode(), getFacets() );
        break;
      case BDataTypeEnum.INTEGER:
        out.writeInt( -1, getSize(), getEncode(), getFacets() );
        break;
      case BDataTypeEnum.FLOAT:
        out.writeInt( Float.floatToIntBits( Float.NaN), 4, getEncode(), getFacets() );
        setSize(4);
        break;
      case BDataTypeEnum.STRING:
        out.writeString("Invalid", getEncode(), getFacets());
        break;
      default:
      }
    }
  }

  public int getMarker(String markerName)
  {
    //System.out.println("BFlexMessageBlock.getMarker(): " + this.getName());
    if( this.getName().equals(markerName)) //&&getDataType().getOrdinal() == BDataTypeEnum.MARKER )
    {
      //System.out.println("    found: returning: " + getOffset());
      return getOffset();
    }
    return -1;
  }
  
  public boolean isIndirect()
  {
    return !getSource().equals(BOrd.NULL);
  }

  public String toString(Context cx)
  {
    return getValue().toString(cx);
  }

  public int convertInputToInt(BObject inputObject)
  {
    try
    {
      //System.out.println("input object = " + inputObject);
      if(inputObject instanceof BString)
        return BInteger.decode( ((BString)inputObject).getString());  
      else if(inputObject instanceof BInteger)
        return ((BInteger)inputObject).getInt();
      else if(inputObject instanceof BFloat)
        return (int)((BFloat)inputObject).getFloat();
      else if(inputObject instanceof BDouble)
        return (int)((BDouble)inputObject).getDouble();
      else if(inputObject instanceof BBoolean)
      {
        int activeValue = 1;
        int inactiveValue = 0;
        BObject activeFacet = getFacets().getFacet(ACTIVE_VALUE);
        try
        {
          activeValue = ((BInteger)activeFacet).getInt();
        }
        catch(Exception e) {}
        BObject inactiveFacet = getFacets().getFacet(INACTIVE_VALUE);
        try
        {
          inactiveValue = ((BInteger)inactiveFacet).getInt();
        }
        catch(Exception e) {}
        if( ((BBoolean)inputObject).getBoolean() ) 
          return activeValue;
        else
          return inactiveValue;
      }
      else if(inputObject instanceof BEnum)
        return ((BEnum)inputObject).getOrdinal();
      return 0;
    }
    catch(Exception e)
    {
      return 0;
    }
  }

  public float convertInputToFloat(BObject inputObject)
  {
    try
    {
      if(inputObject instanceof BString)
        return BFloat.decode( ((BString)inputObject).getString());  
      else if(inputObject instanceof BNumber)
        return ((BNumber)inputObject).getFloat() ;
    }
    catch(Exception e)
    {
      return 0f;
    }
    return 0f;
  }

  public String convertInputToString(BObject inputObject)
  {
    try
    {
      String returnString = "";
      if(inputObject instanceof BString)
        return ((BString)inputObject).getString();  
      else if(inputObject instanceof BNumber)
      {
        BNumber inputNumber = (BNumber)inputObject;
        
        BFacets facets = getFacets();
        String decimalFormat = facets.gets(DECIMAL_FORMAT, "");
        int padWith    = facets.geti(PAD_WITH, -1);
        int fieldWidth = facets.geti(FIELD_WIDTH, -1);
        float maxValue = facets.getf(MAX, Float.POSITIVE_INFINITY);
        float minValue = facets.getf(MIN, Float.NEGATIVE_INFINITY);
        boolean nullTerminate = facets.getb(NULL_TERMINATE, false);
        double dValue = inputNumber.getNumeric();
        if(dValue > (double)maxValue)
          dValue = (double)maxValue;
        else if(dValue < (double)minValue)
          dValue = (double)minValue;
        
        // if the decimalFormat pattern starts with 0 and the 
        // number is negative remove a fixed digit so the "-" 
        // can take the first character position.
        if(decimalFormat.length() > 0)
        {
          if(dValue < 0 && decimalFormat.startsWith("0"))
            decimalFormat = decimalFormat.substring(1);
          DecimalFormat format = new DecimalFormat(decimalFormat);
          returnString = format.format(dValue);
        }
        else
        {
          returnString = inputNumber.toString(new DataContext(facets));
        }
        if(fieldWidth > 0)
        {
          if(nullTerminate)
            fieldWidth--;
          if(returnString.length() > fieldWidth)
            returnString = returnString.substring(0, fieldWidth);
        }
        return returnString;
      }
      
      else if(inputObject instanceof BBoolean)
      {
        String activeText = getFacets().gets(ACTIVE_TEXT, "1");
        String inactiveText = getFacets().gets(INACTIVE_TEXT, "0");
        if( ((BBoolean)inputObject).getBoolean() )
          return activeText;
        return inactiveText;
      }
      else if(inputObject instanceof BEnum)
      {
        return ((BEnum)inputObject).toString(new DataContext(getFacets()));
      }
    }
    catch(Exception e)
    {
      return "";
    }
    return "";
  }


  public boolean getBooleanValue()
  {
    if(getValue() instanceof BString)
    {
      String thisValue = ((BString)getValue()).getString().trim();
      String trueText = getFacets().gets(ACTIVE_TEXT, "");
      String falseText = getFacets().gets(INACTIVE_TEXT, "");
      if(trueText.length() > 0)
        return thisValue.equals(trueText);
      if(falseText.length() > 0)
        return ! thisValue.equals(falseText);
    }
    if(getValue() instanceof BNumber)
    {
      int intValue = ((BNumber)getValue()).getInt();
      return intValue != 0;
    }
    return false;
  }


  public String getStringValue()
  {
    if(getValue() instanceof BString)
      return ((BString)getValue()).getString();
    else
      return getValue().toString( new DataContext(getFacets()) ) ;
  }

  public double getDoubleValue()
  {
    if(getValue() instanceof BNumber)
      return ((BNumber)getValue()).getDouble();
    else
      return Double.NaN ;
  }


  public BEnum getEnumValue()
  {
    int ordinal = -1;
    if(getValue() instanceof BNumber)
      ordinal = ((BNumber)getValue()).getInt();
    return BDynamicEnum.make(ordinal);
  }


  public static boolean isBigEndian(BFacets facets)
  {
    boolean isBigEndian = false;
    BObject bigEndian = facets.getFacet(BIG_ENDIAN);
    try
    {
      isBigEndian = ((BBoolean)bigEndian).getBoolean();
    }
    catch(Exception e) {}
    return isBigEndian;
  }

  class DataContext
    implements Context
  {
    public DataContext(BFacets facets)
    {
      this.facets = facets;
    }
    
    public Context getBase()
    {
      return null;
    }
    
    public BUser getUser()
    {
      return new BUser();
    }
    
    public BFacets getFacets()
    {
      return facets;
    }
    
    public BObject getFacet(String name)
    {
      return getFacets().get(name);
    }
    
    public String getLanguage()
    {
      return Sys.getLanguage();
    }
    
    BFacets facets;
  }
  
////////////////////////////////////////////////////////////////
//Presentation
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make("module://flexSerial/com/tridium/flexSerial/icons/flexMessageElement.png");


  // facet string definitions
  public static String BIG_ENDIAN     = "bigEndian";
  public static String ACTIVE_VALUE   = "activeValue";
  public static String INACTIVE_VALUE = "inactiveValue";
  public static String ACTIVE_TEXT    = "activeText";
  public static String INACTIVE_TEXT  = "inactiveText";
  public static String PAD_WITH       = "padWith";
  public static String NULL_TERMINATE = "nullTerminate";
  public static String DECIMAL_FORMAT = "decimalFormat";
  public static String FIELD_WIDTH    = "fieldWidth";
  public static String MAX            = "max";
  public static String MIN            = "min";
  public static String SHOW_ASCII     = "showAscii";
  
  // cksumStart marker name
  public static String CKSUM_START_MARKER_NAME = "cksumStart";


}
