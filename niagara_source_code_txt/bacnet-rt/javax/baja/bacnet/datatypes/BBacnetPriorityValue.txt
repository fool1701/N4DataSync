/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.util.logging.Logger;

import javax.baja.bacnet.enums.BBacnetBinaryPv;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sync.Transaction;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnConst;
import com.tridium.bacnet.asn.AsnUtil;

/**
 * BBacnetPriorityValue represents the BacnetPriorityValue
 * choice.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 28 May 02
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "value",
  type = "BSimple",
  defaultValue = "BBacnetNull.DEFAULT",
  flags = Flags.HIDDEN
)
public final class BBacnetPriorityValue
  extends BStruct
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetPriorityValue(1633137607)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(Flags.HIDDEN, BBacnetNull.DEFAULT, null);

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
  public static final Type TYPE = Sys.loadType(BBacnetPriorityValue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetPriorityValue()
  {
  }

  /**
   * Null constructor.
   */
  public BBacnetPriorityValue(BBacnetNull bacnetNull)
  {
    choice = NULL_TAG;
    setValue(BBacnetNull.DEFAULT);
  }

  /**
   * Real constructor.
   */
  public BBacnetPriorityValue(BFloat real)
  {
    choice = REAL_TAG;
    setValue(real);
  }

  /**
   * Double constructor.
   */
  public BBacnetPriorityValue(BDouble dble)
  {
    choice = DOUBLE_TAG;
    setValue(dble);
  }

  /**
   * Binary constructor.
   */
  public BBacnetPriorityValue(BBacnetBinaryPv binary)
  {
    choice = BINARY_TAG;
    setValue(binary);
  }


  /**
  public BBacnetPriorityValue(BBacnetUnsigned unsigned)
  {
    choice = UNSIGNED_TAG;
    setValue(unsigned);
  }
   * Integer constructor.
   */
  public BBacnetPriorityValue(BInteger integer)
  {
    choice = INTEGER_TAG;
    setValue(integer);
  }

  /**
   * String constructor.
   */
  public BBacnetPriorityValue(BString str)
  {
    choice = STRING_TAG;
    setValue(str);
  }

  /**
  public BBacnetPriorityValue(BBacnetOctetString octetString)
  {
    choice = OCTET_STRING_TAG;
    setValue(octetString);
  }
  public BBacnetPriorityValue(BBacnetBitString bitString)
  {
    choice = BIT_STRING_TAG;
    setValue(bitString);
  }
  public BBacnetPriorityValue(BBacnetDate date)
  {
    choice = DATE_TAG;
    setValue(date);
  }
  public BBacnetPriorityValue(BBacnetTime time)
  {
    choice = TIME_TAG;
    setValue(time);
  }
  public BBacnetPriorityValue(BAbsTime datetime)
  {
    choice = DATE_TIME_TAG;
    setValue(datetime);
  }
   * Constructed value constructor.
   */
  public BBacnetPriorityValue(BSimple constructedValue)
  {
    choice = CONSTRUCTED_VALUE_TAG;
    setValue(constructedValue);
  }


////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the choice of Bacnet data types currently being used
   * for this priority value.
   * This forces an update of the type from the value property.
   *
   * @return the choice: null, real, binary, integer, or constructedValue.
   */
  public int choice()
  {
    Type t = getValue().getType();
    if (t == BBacnetNull.TYPE)
      choice = NULL_TAG;
    else if (t == BFloat.TYPE)
      choice = REAL_TAG;
    else if (t == BDouble.TYPE)
      choice = DOUBLE_TAG;
    else if (t == BBacnetBinaryPv.TYPE)
      choice = BINARY_TAG;
    else if (t == BBacnetUnsigned.TYPE)
      choice = UNSIGNED_TAG;
    else if (t == BInteger.TYPE)
      choice = INTEGER_TAG;
    else if (t == BString.TYPE)
      choice = STRING_TAG;
    else if (t == BBacnetOctetString.TYPE)
      choice = OCTET_STRING_TAG;
    else if (t == BBacnetBitString.TYPE)
      choice = BIT_STRING_TAG;
    else if (t == BBacnetDate.TYPE)
      choice = DATE_TAG;
    else if (t == BBacnetTime.TYPE)
      choice = TIME_TAG;
    else if (t == BAbsTime.TYPE)
      choice = DATE_TIME_TAG;
    else
      choice = CONSTRUCTED_VALUE_TAG;
    return choice;
  }

  /**
   * Some types of BObjects are used to indicate
   * a null value.  This method allows those types to
   * declare their null status by overriding this common
   * method.  The default is to return false.
   *
   * @return true if the current choice is NULL.
   */
  public boolean isNull()
  {
    return choice() == NULL_TAG;
  }

  /**
   * Get the priority value as a BValue.
   *
   * @return the value property, as a BValue.
   */
  public BValue getPriorityValue()
  {
    return getValue();
  }

  /**
   * Set the priority value.
   *
   * @param v the priority value.
   */
  public void setPriorityValue(BValue v)
  {
    setPriorityValue(v, null);
  }

  /**
   * Set the priority value.
   *
   * @param v  the priority value.
   * @param cx the context for the set.
   */
  public void setPriorityValue(BValue v, Context cx)
  {
    Context myCx = cx;
    BComponent c = getParentComponent();
    if ((c == null) || !c.isMounted())
    {
      if ((cx != null) && (cx instanceof Transaction))
        myCx = null;
    }
    Type t = v.getType();
    if (t == BBacnetNull.TYPE)
    {
      choice = NULL_TAG;
      set(value, v, myCx);
    }
    else if (t == BFloat.TYPE)
    {
      choice = REAL_TAG;
      set(value, v, myCx);
    }
    else if (t == BDouble.TYPE)
    {
      choice = DOUBLE_TAG;
      set(value, v, myCx);
    }
    else if (t == BBacnetBinaryPv.TYPE)
    {
      choice = BINARY_TAG;
      set(value, v, myCx);
    }
    else if (t == BBacnetUnsigned.TYPE)
    {
      choice = UNSIGNED_TAG;
      set(value, v, myCx);
    }
    else if (t == BInteger.TYPE)
    {
      choice = INTEGER_TAG;
      set(value, v, myCx);
    }
    else if (t == BString.TYPE)
    {
      choice = STRING_TAG;
      set(value, v, myCx);
    }
    else if (t == BBacnetOctetString.TYPE)
    {
      choice = OCTET_STRING_TAG;
      set(value, v, myCx);
    }
    else if (t == BBacnetBitString.TYPE)
    {
      choice = BIT_STRING_TAG;
      set(value, v, myCx);
    }
    else if (t == BBacnetDate.TYPE)
    {
      choice = DATE_TAG;
      set(value, v, myCx);
    }
    else if (t == BBacnetTime.TYPE)
    {
      choice = TIME_TAG;
      set(value, v, myCx);
    }
    else if (t == BAbsTime.TYPE)
    {
      choice = DATE_TIME_TAG;
      set(value, v, myCx);
    }
    else if (v instanceof BSimple)
    {
      choice = CONSTRUCTED_VALUE_TAG;
      set(value, v, myCx);
    }
    else
      throw new IllegalArgumentException("BacnetPriorityValue:Cannot handle constructed types! v=" + v + " [" + v.getType() + "]");
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
    switch (choice())
    {
      case NULL_TAG:
        out.writeNull();
        break;
      case REAL_TAG:
        out.writeReal((BFloat)getValue());
        break;
      case DOUBLE_TAG:
        out.writeDouble((BDouble)getValue());
        break;
      case BINARY_TAG:
        out.writeEnumerated((BBacnetBinaryPv)getValue());
        break;
      case UNSIGNED_TAG:
        out.writeUnsigned((BBacnetUnsigned)getValue());
        break;
      case INTEGER_TAG:
        out.writeSignedInteger((BInteger)getValue());
        break;
      case STRING_TAG:
        out.writeCharacterString((BString)getValue());
        break;
      case OCTET_STRING_TAG:
        out.writeOctetString((BBacnetOctetString)getValue());
        break;
      case BIT_STRING_TAG:
        out.writeBitString((BBacnetBitString)getValue());
        break;
      case DATE_TAG:
        out.writeDate((BBacnetDate)getValue());
        break;
      case TIME_TAG:
        out.writeTime((BBacnetTime)getValue());
        break;
      case DATE_TIME_TAG:
        out.writeOpeningTag(DATE_TIME_TAG);
        BBacnetDateTime dateTime = new BBacnetDateTime((BAbsTime)getValue());
        dateTime.writeAsn(out);
        out.writeClosingTag(DATE_TIME_TAG);
        break;
      case CONSTRUCTED_VALUE_TAG:
        logger.fine("write constructed value in BacnetPriorityValue!");
        out.writeEncodedValue(CONSTRUCTED_VALUE_TAG, AsnUtil.toAsn(getValue()));
        break;
      default:
        throw new IllegalStateException("Invalid priority value type:" + choice);
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
        setPriorityValue(in.readNull(), noWrite);
        break;
      case ASN_REAL:
        setPriorityValue(BFloat.make(in.readReal()), noWrite);
        break;
      case ASN_DOUBLE:
        setPriorityValue(BDouble.make(in.readDouble()), noWrite);
        break;
      case ASN_ENUMERATED:
        setPriorityValue(BBacnetBinaryPv.make(in.readEnumerated()), noWrite);
        break;
      case ASN_UNSIGNED:
        setPriorityValue(in.readUnsigned(), noWrite); break;
      case ASN_INTEGER:
        setPriorityValue(in.readSigned(), noWrite); break;
      case ASN_OCTET_STRING:
        setPriorityValue(in.readBacnetOctetString(), noWrite);break;
      case ASN_CHARACTER_STRING:
        setPriorityValue(BString.make(in.readCharacterString()), noWrite);break;
      case ASN_BIT_STRING:
        setPriorityValue(in.readBitString(),noWrite);break;
      case ASN_DATE:
        setPriorityValue(in.readDate(),noWrite);break;
      case ASN_TIME:
        setPriorityValue(in.readTime(),noWrite);break;
      default:
        if (in.isOpeningTag(CONSTRUCTED_VALUE_TAG))
        {
          BValue o = AsnUtil.asnToValue(null, in.readEncodedValue(CONSTRUCTED_VALUE_TAG));
          if (o instanceof BSimple)
          {
            setPriorityValue(o, noWrite);
          }
          else
          {
            logger.fine("non-BSimple constructed value not supported in BacnetPriorityValue!");
          }
        }
        else if (in.isOpeningTag(DATE_TIME_TAG))
        {
          in.skipOpeningTag(DATE_TIME_TAG);
          BBacnetDateTime dateTime = new BBacnetDateTime();
          dateTime.readAsn(in);
          in.skipClosingTag(DATE_TIME_TAG);
          setPriorityValue(dateTime.toBAbsTime(), noWrite);
        }
        else
          throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);
        break;
    }
  }


////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context context)
  {
    return getValue().toString(context);
  }

  /**
   * Get the choice name for this BBacnetPriorityValue's choice.
   */
  public String choiceName()
  {
    return choiceName(choice());
  }

  /**
   * Get the choice name for the given choice.
   */
  public static String choiceName(int choice)
  {
    switch (choice)
    {
      case NULL_TAG:                return NULL_CHOICE;
      case REAL_TAG:                return REAL_CHOICE;
      case DOUBLE_TAG:              return DOUBLE_CHOICE;
      case BINARY_TAG:              return BINARY_CHOICE;
      case UNSIGNED_TAG:            return UNSIGNED_CHOICE;
      case INTEGER_TAG:             return INTEGER_CHOICE;
      case STRING_TAG:              return STRING_CHOICE;
      case OCTET_STRING_TAG:        return OCTET_STRING_CHOICE;
      case BIT_STRING_TAG:          return BIT_STRING_CHOICE;
      case DATE_TAG:                return DATE_CHOICE;
      case TIME_TAG:                return TIME_CHOICE;
      case DATE_TIME_TAG:           return DATE_TIME_CHOICE;
      case CONSTRUCTED_VALUE_TAG:   return CONSTRUCTED_VALUE_CHOICE;
      default: return INVALID_CHOICE;
    }
  }

  /**
   * Get the choice for the given choice name.
   */
  public static int choice(String choiceName)
  {
    if (NULL_CHOICE.equals(choiceName)) return NULL_TAG;
    if (REAL_CHOICE.equals(choiceName)) return REAL_TAG;
    if (DOUBLE_CHOICE.equals(choiceName)) return DOUBLE_TAG;
    if (BINARY_CHOICE.equals(choiceName)) return BINARY_TAG;
    if (UNSIGNED_CHOICE.equals(choiceName))    return UNSIGNED_TAG;
    if (INTEGER_CHOICE.equals(choiceName)) return INTEGER_TAG;
    if (STRING_CHOICE.equals(choiceName)) return STRING_TAG;
    if (OCTET_STRING_CHOICE.equals(choiceName))return OCTET_STRING_TAG;
    if (BIT_STRING_CHOICE.equals(choiceName))  return BIT_STRING_TAG;
    if (DATE_CHOICE.equals(choiceName))        return DATE_TAG;
    if (TIME_CHOICE.equals(choiceName))        return TIME_TAG;
    if (DATE_TIME_CHOICE.equals(choiceName))   return DATE_TIME_TAG;
    return CONSTRUCTED_VALUE_TAG;
  }


////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  private int choice;

  private static final Logger logger = Logger.getLogger("bacnet.debug");

////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  public static final String INVALID_CHOICE = "invalid choice";
  public static final String NULL_CHOICE = "NULL";
  public static final String REAL_CHOICE = "Real";
  public static final String DOUBLE_CHOICE = "Double";
  public static final String BINARY_CHOICE = "Binary";
  public static final String UNSIGNED_CHOICE          = "Unsigned";
  public static final String INTEGER_CHOICE = "Integer";
  public static final String STRING_CHOICE = "String";
  public static final String OCTET_STRING_CHOICE      = "OctetString";
  public static final String BIT_STRING_CHOICE        = "BitString";
  public static final String DATE_CHOICE              = "Date";
  public static final String TIME_CHOICE              = "Time";
  public static final String DATE_TIME_CHOICE         = "DateTime";
  public static final String CONSTRUCTED_VALUE_CHOICE = "ConstructedValue";

  public static final int CONSTRUCTED_VALUE_TAG = 0;
  private static final int DATE_TIME_TAG     = 1;

  // Non-Bacnet tags - used for convenience here.
  private static final int NULL_TAG = -1;
  private static final int REAL_TAG = -2;
  private static final int BINARY_TAG = -3;
  private static final int UNSIGNED_TAG      = -4;
  private static final int DOUBLE_TAG = -5;
  private static final int STRING_TAG = -6;
  private static final int INTEGER_TAG       = -7;
  private static final int OCTET_STRING_TAG  = -8;
  private static final int BIT_STRING_TAG    = -9;
  private static final int DATE_TAG          = -10;
  private static final int TIME_TAG          = -11;
}
