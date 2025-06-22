/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.virtual;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.datatypes.*;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BComponentSpace;
import javax.baja.sys.*;
import javax.baja.util.BTypeSpec;
import javax.baja.virtual.BVirtualComponent;
import javax.baja.virtual.BVirtualComponentSpace;

import com.tridium.bacnet.asn.AsnConst;

/**
 * @author cgemmill
 * @deprecated
 */
@NiagaraType
@NiagaraProperty(
  name = "arrayTypeSpec",
  type = "BTypeSpec",
  defaultValue = "BTypeSpec.DEFAULT",
  flags = Flags.HIDDEN
)
@NiagaraProperty(
  name = "size",
  type = "int",
  defaultValue = "-1",
  flags = Flags.HIDDEN
)
@Deprecated
public class BBacnetVirtualArray
  extends BVirtualComponent
  implements BacnetConst,
  BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.virtual.BBacnetVirtualArray(2280537004)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "arrayTypeSpec"

  /**
   * Slot for the {@code arrayTypeSpec} property.
   * @see #getArrayTypeSpec
   * @see #setArrayTypeSpec
   */
  public static final Property arrayTypeSpec = newProperty(Flags.HIDDEN, BTypeSpec.DEFAULT, null);

  /**
   * Get the {@code arrayTypeSpec} property.
   * @see #arrayTypeSpec
   */
  public BTypeSpec getArrayTypeSpec() { return (BTypeSpec)get(arrayTypeSpec); }

  /**
   * Set the {@code arrayTypeSpec} property.
   * @see #arrayTypeSpec
   */
  public void setArrayTypeSpec(BTypeSpec v) { set(arrayTypeSpec, v, null); }

  //endregion Property "arrayTypeSpec"

  //region Property "size"

  /**
   * Slot for the {@code size} property.
   * @see #getSize
   * @see #setSize
   */
  public static final Property size = newProperty(Flags.HIDDEN, -1, null);

  /**
   * Get the {@code size} property.
   * @see #size
   */
  public int getSize() { return getInt(size); }

  /**
   * Set the {@code size} property.
   * @see #size
   */
  public void setSize(int v) { setInt(size, v, null); }

  //endregion Property "size"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetVirtualArray.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


//////////////////////////////////////////////////////////////  //
//   Constructors
//////////////////////////////////////////////////////////////  //

  /**
   * Default constructor.
   */
  public BBacnetVirtualArray()
  {
  }

  /**
   * Constructor with type specification.
   *
   * @param arrayType the type of elements to be contained by this array.
   */
  public BBacnetVirtualArray(Type arrayType)
  {
    setArrayTypeSpec(BTypeSpec.make(arrayType));
  }

  /**
   * Constructor with type specification and fixed size.
   *
   * @param arrayType the type of elements to be contained by this array.
   * @param fixedSize the fixed size of this array.
   */
  public BBacnetVirtualArray(Type arrayType, int fixedSize)
  {
    setArrayTypeSpec(BTypeSpec.make(arrayType));
    setSize(fixedSize);
  }


////////////////////////////////////////////////////////////////
//   BObject/BComponent overrides
////////////////////////////////////////////////////////////////

  public void added(Property p, Context cx)
  {
    super.added(p, cx);
    if (cx == noWrite) return;
    BComponentSpace space = getComponentSpace();
    if (space instanceof BVirtualComponentSpace)
    {
      int ndx = index(p.getName());
      getParent().asComponent().added(getPropertyInParent(),
        BFacets.make(BBacnetVirtualComponent.INDEX,
          BInteger.make(ndx)));
    }
  }

  public void removed(Property p, BValue oldValue, Context cx)
  {
    super.removed(p, oldValue, cx);
    if (cx == noWrite) return;
    BComponentSpace space = getComponentSpace();
    if (space instanceof BVirtualComponentSpace)
    {
      int ndx = index(p.getName());
      getParent().asComponent().removed(getPropertyInParent(),
        oldValue,
        BFacets.make(BBacnetVirtualComponent.INDEX,
          BInteger.make(ndx)));
    }
  }

  /**
   * Property changed.
   * We may need to write the new value to a remote device.
   */
  public final void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning()) return;
    if (p.equals(size))
    {
    }
    else if (p.equals(arrayTypeSpec))
    {
    }

    // If not the noWrite context, inform the parent virtual component
    // that the property changed.
    else
    {
      BComponentSpace space = getComponentSpace();
      if (space instanceof BVirtualComponentSpace)
      {
        if (cx != noWrite)
        {
          getParent().asComponent().changed(getPropertyInParent(),
            BFacets.make(BBacnetVirtualComponent.INDEX,
              BInteger.make(index(p))));
        }
        else
        {
          getParent().asComponent().changed(getPropertyInParent(),
            cx);
        }
      }
    }
  }

  /**
   * Callback when the component enters the subscribed state.
   */
  public final void subscribed()
  {
    BComponentSpace space = getComponentSpace();
    if (space instanceof BVirtualComponentSpace)
    {
      BComponent c = getParent().asComponent();
      if (!c.isSubscribed())
        c.subscribed();
    }

  }

  /**
   * Callback when the component leaves the subscribed state.
   */
  public void unsubscribed()
  {
    BComponentSpace space = getComponentSpace();
    if (space instanceof BVirtualComponentSpace)
    {
      BComponent c = getParent().asComponent();
      if (!c.isSubscribed())
        c.unsubscribed();
    }
  }

//    /**
//     * Only children of the specified array type are allowed.
//     */
//    public boolean isChildLegal(BComponent child)
//    {
//      // no more children...
//      return false;
//    }

  /**
   * VirtualArrays can only be children of BacnetVirtualComponents.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return (parent instanceof BBacnetVirtualComponent);
  }


////////////////////////////////////////////////////////////////
//   Actions
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
//   Access
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context cx)
  {
    int size = getSize();
    String sz = String.valueOf(size);
    if (size < 0)
      sz = "N";
    return "BacnetVirtualARRAY[" + sz + "] of " + getArrayTypeSpec();
  }

  /**
   * Get the element at this index.
   * If index is zero, return the array size.
   * Note the size will, in general, not be accurate, because
   * all of the array elements are not usually loaded in the virtual space.
   *
   * @param index the (1-N) array index of the object requested.
   * @return the object at this index, or null.
   */
  public final BValue getElement(int index)
  {
    if (index == 0) return get(size);
    // This is one-based, so we need to decrement index prior to calling name().
    return get(name(index - 1));
  }

  /**
   * Set the element at this index.
   * May not be possible if the virtual slot for the
   * array element has not yet been loaded.
   *
   * @param index the array element to set.
   * @param value the new array element.
   */
  public final void setElement(int index, BValue value)
  {
    if (value.getType() == getArrayType())
    {
      if ((index > 0)/* && (index <= getSize())*/)
      {
        set(name(index - 1), value);
      }
    }
  }

  public static int index(String propName)
  {
    propName = SlotPath.unescape(propName);
    int sc = propName.indexOf(";");
    if (sc < 0)
      return Integer.parseInt(propName.substring(7));
    else
      return Integer.parseInt(propName.substring(7, sc));
  }

//    public void setReadonly(boolean readonly)
//    {
//      SlotCursor sc = getProperties();
//      while (sc.next())
//      {
//        if (sc.property().isDynamic())
//          setFlags(sc.property(),
//            (readonly ? (getFlags(sc.property()) | Flags.READONLY)
//                      : (getFlags(sc.property()) & ~(Flags.READONLY))));
//      }
//    }


//////////////////////////////////////////////////////////////  //
//   Support
//////////////////////////////////////////////////////////////  //

  /**
   * Get the Niagara <code>Type</code> of the Bacnet data type
   * of this array.
   *
   * @return the <code>Type</code> of objects in this array.
   */
  private Type getArrayType()
  {
    try
    {
      return getArrayTypeSpec().getResolvedType();
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, "BacnetVirtualArray:Unable to get resolved Type for " + getArrayTypeSpec(), e);
      return null;
    }
  }

  /**
   * Get the Asn type of objects in this array.
   *
   * @return 0-12 for primitives, or negative numbers for constructed types.
   */
  private int asnType()
  {
    Type t = getArrayType();
    if (t == BBacnetNull.TYPE)
      asnType = ASN_NULL;
    else if (t == BBoolean.TYPE)
      asnType = ASN_BOOLEAN;
    else if (t == BBacnetUnsigned.TYPE)
      asnType = ASN_UNSIGNED;
    else if (t == BInteger.TYPE)
      asnType = ASN_INTEGER;
    else if (t == BFloat.TYPE)
      asnType = ASN_REAL;
    else if (t == BDouble.TYPE)
      asnType = ASN_DOUBLE;
    else if (t == BBacnetOctetString.TYPE)
      asnType = ASN_OCTET_STRING;
    else if (t == BString.TYPE)
      asnType = ASN_CHARACTER_STRING;
    else if (t == BBacnetBitString.TYPE)
      asnType = ASN_BIT_STRING;
    else if (BEnum.class.isAssignableFrom(t.getTypeClass()))
      asnType = ASN_ENUMERATED;
    else if (t == BBacnetDate.TYPE)
      asnType = ASN_DATE;
    else if (t == BBacnetTime.TYPE)
      asnType = ASN_TIME;
    else if (t == BBacnetObjectIdentifier.TYPE)
      asnType = ASN_OBJECT_IDENTIFIER;
    else
      asnType = AsnConst.ASN_CONSTRUCTED_DATA;
    return asnType;
  }

  /**
   * Get the slot name for the array element at this index.
   * Note that the index is zero-based, and Bacnet arrays are one-based,
   * so we need to add one to the index to create an appropriate name.
   *
   * @param ndx the array index (0 to N-1).
   * @return the slot name for this element.
   */
  private String name(int ndx)
  {
//      int elem = ndx+1;
//      if (getElementNames().isOrdinal(elem))
//        return getElementNames().getTag(elem);
//      return "element"+(elem);
    return "element" + (ndx + 1);
  }

  /**
   * Get the array index that this Property represents.
   * Note that Bacnet arrays are one-based, so our slot names will
   * also be one-based.  Because the slot is already named appropriately,
   * we DO NOT need to subtract one to get back to the right index.
   *
   * @param p the array element property.
   * @return the array index for this element.
   */
  private int index(Property p)
  {
    return index(p.getName());
  }


//////////////////////////////////////////////////////////////  //
//    BIBacnetDataType
//////////////////////////////////////////////////////////////  //

  /**
   * Write the value to the Asn output stream.
   *
   * @param out the AsnOutput stream.
   */
  public final void writeAsn(AsnOutput out)
  {
    synchronized (out)
    {
      SlotCursor<Property> c = getProperties();
      while (c.next())
      {
        try
        {
          BObject arrayElement = c.get();  //FIXXSlotCursor
          if (arrayElement instanceof BOrd)
          {
            arrayElement = ((BOrd)arrayElement).get(this);
          }
          if ((arrayElement != null) && (arrayElement.getType() == getArrayType()))
          {
            switch (asnType())
            {
              case ASN_NULL:
                out.writeNull();
                break;
              case ASN_BOOLEAN:
                out.writeBoolean((BBoolean)c.get());
                break;
              case ASN_UNSIGNED:
                out.writeUnsigned((BBacnetUnsigned)c.get());
                break;
              case ASN_INTEGER:
                out.writeSignedInteger((BInteger)c.get());
                break;
              case ASN_REAL:
                out.writeReal((BFloat)c.get());
                break;
              case ASN_DOUBLE:
                out.writeDouble((BDouble)c.get());
                break;
              case ASN_OCTET_STRING:
                out.writeOctetString((BBacnetOctetString)c.get());
                break;
              case ASN_CHARACTER_STRING:
                out.writeCharacterString((BString)c.get());
                break;
              case ASN_BIT_STRING:
                out.writeBitString((BBacnetBitString)c.get());
                break;
              case ASN_ENUMERATED:
                out.writeEnumerated((BEnum)c.get());
                break;
              case ASN_DATE:
                out.writeDate((BBacnetDate)c.get());
                break;
              case ASN_TIME:
                out.writeTime((BBacnetTime)c.get());
                break;
              case ASN_OBJECT_IDENTIFIER:
                out.writeObjectIdentifier((BBacnetObjectIdentifier)c.get());
                break;
//                case ASN_CONSTRUCTED_DATA:
              default:
                ((BIBacnetDataType)c.get()).writeAsn(out);
                break;
            }
          }
        }
        catch (Exception e)
        {
          logger.log(Level.INFO, getName() + ":" + this + ": writeAsn: Exception!", e);
        }
      } // while
    } // synchronized
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public final void readAsn(AsnInput in)
    throws AsnException
  {
    ArrayList<BValue> v = new ArrayList<>();
    synchronized (in)
    {
      while (in.peekTag() != AsnInput.END_OF_DATA)
      {
        BValue arrayElement;
        switch (asnType())
        {
          case ASN_NULL:
            arrayElement = in.readNull();
            break;
          case ASN_BOOLEAN:
            arrayElement = BBoolean.make(in.readBoolean());
            break;
          case ASN_UNSIGNED:
            arrayElement = in.readUnsigned();
            break;
          case ASN_INTEGER:
            arrayElement = BInteger.make(in.readSignedInteger());
            break;
          case ASN_REAL:
            arrayElement = BFloat.make(in.readReal());
            break;
          case ASN_DOUBLE:
            arrayElement = BDouble.make(in.readDouble());
            break;
          case ASN_OCTET_STRING:
            arrayElement = BBacnetOctetString.make(in.readOctetString());
            break;
          case ASN_CHARACTER_STRING:
            arrayElement = BString.make(in.readCharacterString());
            break;
          case ASN_BIT_STRING:
            arrayElement = in.readBitString();
            break;
          case ASN_ENUMERATED:
            BEnum d = (BEnum)getArrayType().getInstance();
            arrayElement = d.getRange().get(in.readEnumerated());
            break;
          case ASN_DATE:
            arrayElement = in.readDate();
            break;
          case ASN_TIME:
            arrayElement = in.readTime();
            break;
          case ASN_OBJECT_IDENTIFIER:
            arrayElement = in.readObjectIdentifier();
            break;
          //        case ASN_CONSTRUCTED_DATA:
          default:
            arrayElement = (BValue)getArrayType().getInstance();
            ((BIBacnetDataType)arrayElement).readAsn(in);
            break;
        }

        if (arrayElement != null)
          v.add(arrayElement);
      }
    }

//    // Remove old elements.
//    removeAll(noWrite);
//
//    // Add in new elements.
//    int ndx = 0;
//    Iterator it = v.iterator();
//    while (it.hasNext())
//    {
//      BValue val = (BValue)it.next();
//      add(name(ndx++), val, noWrite);
//    }

    // Simply set elements if possible.
    // Then remove leftovers.
    int ndx = 0;
    Iterator<BValue> it = v.iterator();
    while (it.hasNext())
    {
      BValue val = it.next();
      String name = name(ndx++);
      Property p = getProperty(name);
      if (p != null)
        set(p, val, noWrite);
      else
        add(name, val, noWrite);
    }
    Property p = getProperty(name(ndx++));
    while (p != null)
    {
      remove(p, noWrite);
      p = getProperty(name(ndx++));
    }
  }

  public void readAsn(AsnInput in, int index)
    throws AsnException
  {
    synchronized (in)
    {
      BValue arrayElement;
      switch (asnType())
      {
        case ASN_NULL:
          arrayElement = in.readNull();
          break;
        case ASN_BOOLEAN:
          arrayElement = BBoolean.make(in.readBoolean());
          break;
        case ASN_UNSIGNED:
          arrayElement = in.readUnsigned();
          break;
        case ASN_INTEGER:
          arrayElement = BInteger.make(in.readSignedInteger());
          break;
        case ASN_REAL:
          arrayElement = BFloat.make(in.readReal());
          break;
        case ASN_DOUBLE:
          arrayElement = BDouble.make(in.readDouble());
          break;
        case ASN_OCTET_STRING:
          arrayElement = BBacnetOctetString.make(in.readOctetString());
          break;
        case ASN_CHARACTER_STRING:
          arrayElement = BString.make(in.readCharacterString());
          break;
        case ASN_BIT_STRING:
          arrayElement = in.readBitString();
          break;
        case ASN_ENUMERATED:
          BEnum d = (BEnum)getArrayType().getInstance();
          arrayElement = d.getRange().get(in.readEnumerated());
          break;
        case ASN_DATE:
          arrayElement = in.readDate();
          break;
        case ASN_TIME:
          arrayElement = in.readTime();
          break;
        case ASN_OBJECT_IDENTIFIER:
          arrayElement = in.readObjectIdentifier();
          break;
        //        case ASN_CONSTRUCTED_DATA:
        default:
          arrayElement = (BValue)getArrayType().getInstance();
          ((BIBacnetDataType)arrayElement).readAsn(in);
          break;
      }

      Property p = getProperty("element" + index);
      if ((p != null) && (arrayElement != null))
        set(p, arrayElement, noWrite);
    }
  }

  /**
   * Write the value to the Asn output stream.
   *
   * @param out the AsnOutput stream.
   */
  public final void writeAsn(AsnOutput out, int index)
  {
    synchronized (out)
    {
      try
      {
        BValue arrayElement = getElement(index);
        if ((arrayElement != null) && (arrayElement.getType() == getArrayType()))
        {
          switch (asnType())
          {
            case ASN_NULL:
              out.writeNull();
              break;
            case ASN_BOOLEAN:
              out.writeBoolean((BBoolean)arrayElement);
              break;
            case ASN_UNSIGNED:
              out.writeUnsigned((BBacnetUnsigned)arrayElement);
              break;
            case ASN_INTEGER:
              out.writeSignedInteger((BInteger)arrayElement);
              break;
            case ASN_REAL:
              out.writeReal((BFloat)arrayElement);
              break;
            case ASN_DOUBLE:
              out.writeDouble((BDouble)arrayElement);
              break;
            case ASN_OCTET_STRING:
              out.writeOctetString((BBacnetOctetString)arrayElement);
              break;
            case ASN_CHARACTER_STRING:
              out.writeCharacterString((BString)arrayElement);
              break;
            case ASN_BIT_STRING:
              out.writeBitString((BBacnetBitString)arrayElement);
              break;
            case ASN_ENUMERATED:
              out.writeEnumerated((BEnum)arrayElement);
              break;
            case ASN_DATE:
              out.writeDate((BBacnetDate)arrayElement);
              break;
            case ASN_TIME:
              out.writeTime((BBacnetTime)arrayElement);
              break;
            case ASN_OBJECT_IDENTIFIER:
              out.writeObjectIdentifier((BBacnetObjectIdentifier)arrayElement);
              break;
//                case ASN_CONSTRUCTED_DATA:
            default:
              ((BIBacnetDataType)arrayElement).writeAsn(out);
              break;
          }
        }
      }
      catch (Exception e)
      {
        logger.log(Level.INFO, getName() + ":" + this + ": writeAsn: Exception!", e);
      }
    } // synchronized
  }


////////////////////////////////////////////////////////////////
//   Attributes
////////////////////////////////////////////////////////////////

  private int asnType;// = ASN_CONSTRUCTED_DATA;
  boolean elementsLoaded = false;

////////////////////////////////////////////////////////////////
//    Constants
////////////////////////////////////////////////////////////////

  private static final Logger logger = Logger.getLogger("bacnet");
}
