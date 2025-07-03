/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.BBacnetObject;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.bacnet.util.GrandchildChangedContext;
import javax.baja.bacnet.virtual.BBacnetVirtualProperty;
import javax.baja.bacnet.virtual.BacnetVirtualUtil;
import javax.baja.category.BCategoryMask;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.spy.SpyWriter;
import javax.baja.sync.Transaction;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFloat;
import javax.baja.sys.BInteger;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Subscriber;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.util.BTypeSpec;

import com.tridium.bacnet.asn.AsnConst;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.datatypes.BAddArrayElementAction;
import com.tridium.bacnet.datatypes.BRemoveArrayElementAction;

/**
 * BBacnetArray represents a Bacnet Array, which contains an indexed
 * sequence of objects of a particular Bacnet data type.
 * <p>
 * The elements are named "element1" through "elementN" by default.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 08 May 02
 * @since Niagara 3 Bacnet 1.0
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
  defaultValue = "0",
  flags = Flags.HIDDEN
)
/*
 is the size fixed?
 */
@NiagaraProperty(
  name = "fixedSize",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "addElement",
  parameterType = "BValue",
  defaultValue = "BBacnetUnsigned.DEFAULT",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "removeElement",
  parameterType = "BInteger",
  defaultValue = "BInteger.make(0)",
  flags = Flags.HIDDEN
)
@NiagaraTopic(
  name = "arrayPropertyChanged"
)
public class BBacnetArray
  extends BComponent
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetArray(1211426721)1.0$ @*/
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
  public static final Property size = newProperty(Flags.HIDDEN, 0, null);

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

  //region Property "fixedSize"

  /**
   * Slot for the {@code fixedSize} property.
   * is the size fixed?
   * @see #getFixedSize
   * @see #setFixedSize
   */
  public static final Property fixedSize = newProperty(Flags.HIDDEN, false, null);

  /**
   * Get the {@code fixedSize} property.
   * is the size fixed?
   * @see #fixedSize
   */
  public boolean getFixedSize() { return getBoolean(fixedSize); }

  /**
   * Set the {@code fixedSize} property.
   * is the size fixed?
   * @see #fixedSize
   */
  public void setFixedSize(boolean v) { setBoolean(fixedSize, v, null); }

  //endregion Property "fixedSize"

  //region Action "addElement"

  /**
   * Slot for the {@code addElement} action.
   * @see #addElement(BValue parameter)
   */
  public static final Action addElement = newAction(Flags.HIDDEN, BBacnetUnsigned.DEFAULT, null);

  /**
   * Invoke the {@code addElement} action.
   * @see #addElement
   */
  public void addElement(BValue parameter) { invoke(addElement, parameter, null); }

  //endregion Action "addElement"

  //region Action "removeElement"

  /**
   * Slot for the {@code removeElement} action.
   * @see #removeElement(BInteger parameter)
   */
  public static final Action removeElement = newAction(Flags.HIDDEN, BInteger.make(0), null);

  /**
   * Invoke the {@code removeElement} action.
   * @see #removeElement
   */
  public void removeElement(BInteger parameter) { invoke(removeElement, parameter, null); }

  //endregion Action "removeElement"

  //region Topic "arrayPropertyChanged"

  /**
   * Slot for the {@code arrayPropertyChanged} topic.
   * @see #fireArrayPropertyChanged
   */
  public static final Topic arrayPropertyChanged = newTopic(0, null);

  /**
   * Fire an event for the {@code arrayPropertyChanged} topic.
   * @see #arrayPropertyChanged
   */
  public void fireArrayPropertyChanged(BValue event) { fire(arrayPropertyChanged, event, null); }

  //endregion Topic "arrayPropertyChanged"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetArray.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetArray()
  {
  }

  /**
   * Constructor with type specification.
   *
   * @param arrayType the type of elements to be contained by this array.
   */
  public BBacnetArray(Type arrayType)
  {
    setArrayTypeSpec(BTypeSpec.make(arrayType));
  }

  /**
   * Constructor with type specification and fixed size.
   *
   * @param arrayType the type of elements to be contained by this array.
   * @param fixedSize the fixed size of this array.
   */
  public BBacnetArray(Type arrayType, int fixedSize)
  {
    setArrayTypeSpec(BTypeSpec.make(arrayType));
    setFixedSize(true);
    setSize(fixedSize);
    checkSize(true, null);
  }


////////////////////////////////////////////////////////////////
// BComponent overrides
////////////////////////////////////////////////////////////////

  /**
   * Started.
   * Validate the size.
   */
  public final void started()
  {
    if (getParent() instanceof BBacnetObject)
      config = true;
    checkSize(true, null);
    if (!getFixedSize())
    {
      if (get("addArrayElement") == null)
      {
        BAddArrayElementAction addElement = new BAddArrayElementAction();
        addElement.setParameterTypeSpec(getArrayTypeSpec());
        add("addArrayElement", addElement, Flags.TRANSIENT);
      }
      if (get("removeArrayElement") == null)
      {
        BRemoveArrayElementAction removeElement = new BRemoveArrayElementAction();
        //      removeElement.setParameterTypeSpec(BInteger.TYPE.getTypeSpec());
        removeElement.setParameterTypeSpec(BDynamicEnum.TYPE.getTypeSpec());
        add("removeArrayElement", removeElement, Flags.TRANSIENT);
      }
    }
  }

  /**
   * Property changed.
   * We may need to write the new value to a remote device.
   */
  public final void changed(Property p, Context cx)
  {
    if (!isRunning()) return;
    if (p.equals(size))
    {
      Property e0 = getProperty(ELEMENT_0);
      if (e0 != null)
        setInt(e0, getSize(), cx);
      if (cx != noWrite)
        checkSize(true, cx);
    }
    else if ((p.equals(fixedSize)) || (p.equals(arrayTypeSpec)))
    {
    }
    else if (p.getName().equals(ELEMENT_0))
    {
    }

    // if not the noWrite context, write just this index using the parent's
    // writeProperty() method.
    else if (p.getName().startsWith("element"))
    {
      if (cx != noWrite)
      {
        if (config)
        {
          try
          {
            ((BBacnetObject)getParent()).writeProperty(getPropertyInParent(),
              index(p),
              AsnUtil.toAsn(asnType(), get(p)));
          }
          catch (BacnetException e)
          {
            loggerBacnetClient.warning("Unable to write array element " + index(p)
              + " in property " + getPropertyInParent() + " of " + getParent() + ":" + e);
          }
        }
        else if (BacnetVirtualUtil.isVirtual(this))
        {
          getParent().asComponent().changed(getPropertyInParent(),
            new GrandchildChangedContext(index(p),
              AsnUtil.toAsn(asnType(), get(p))));
        }
      }
    }
  }

  /**
   * Callback when the component enters the subscribed state.
   */
  public final void subscribed()
  {
    if (config && !getParent().asComponent().isSubscribed())
      getParent().asComponent().subscribed();
    BBacnetVirtualProperty vp = BacnetVirtualUtil.getVirtualProperty(this);
    if (vp != null) vp.childSubscribed(this);
  }

  /**
   * Callback when the component enters the subscribed state.
   */
  public void unsubscribed()
  {
    if (config && !getParent().asComponent().isSubscribed())
      getParent().asComponent().unsubscribed();
    BBacnetVirtualProperty vp = BacnetVirtualUtil.getVirtualProperty(this);
    if (vp != null) vp.childUnsubscribed(this);
  }

  /**
   * Only children of the specified array type are allowed.
   */
  public boolean isChildLegal(BComponent child)
  {
    // only check this in the station?
    if (!isRunning()) return true;
    return child.getType().is(getArrayTypeSpec().getTypeInfo());
  }

  /**
   * Override to route to the virtual parent when we are in a virtual space.
   */
  public BCategoryMask getAppliedCategoryMask()
  {
    if (BacnetVirtualUtil.isVirtual(this))
      return getParent().asComponent().getAppliedCategoryMask();
    return super.getAppliedCategoryMask();
  }

  /**
   * Override to route to the virtual parent when we are in a virtual space.
   */
  public BCategoryMask getCategoryMask()
  {
    if (BacnetVirtualUtil.isVirtual(this)) return getParent().asComponent().getCategoryMask();
    return super.getCategoryMask();
  }

  /**
   * Override to route to the virtual parent when we are in a virtual space.
   */
  public BPermissions getPermissions(Context cx)
  {
    if (BacnetVirtualUtil.isVirtual(this)) return getParent().asComponent().getPermissions(cx);
    return super.getPermissions(cx);
  }


////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Add an element to end of the array.
   */
  public final void doAddElement(BValue arrayElement)
  {
    if (getFixedSize())
    {
      loggerBacnetDebug.severe(this + ".doAddElement:Fixed size array; can't add element!");
      return;
    }

    int size = getSize();
    if (arrayElement.getType() == getArrayType())
    {
      add(name(size), arrayElement);
      setSize(++size);
      BComplex parent = getParent();
      if (parent instanceof BComponent)
      {
        ((BComponent) parent).changed(getPropertyInParent(), null);
      }
    }
    else if (arrayElement instanceof BOrd)
    {
      add(name(size), arrayElement);
      setSize(++size);
    }
    else
    {
      loggerBacnetDebug.severe(this + ".doAddElement:Wrong element type: this is an array of " + getArrayType().getTypeName());
    }
  }

  /**
   * Remove an element from the array.
   *
   * @param index the (zero-based) index of the element to be removed.
   */
  public final void doRemoveElement(BInteger index)
  {
    if (getFixedSize())
    {
      loggerBacnetDebug.severe(this + ".doRemoveElement:Fixed size array; can't remove element!");
      return;
    }
    remove(name(index.getInt()));
    reIndex();
    setSize(getSize() - 1);
//  ((BBacnetObject)getParent()).changed(getPropertyInParent(), null);
    getParent().asComponent().changed(getPropertyInParent(), null);
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context cx)
  {
    loadSlots();  // ok?
    // try to handle PropertySheet with just the top level string
    if ((cx != null) && (cx instanceof BasicContext))
    {
      return "BacnetARRAY[" + getSize() + "] of " + getArrayTypeSpec();
    }

    // regular way.
    StringBuilder sb = new StringBuilder("{");
    int len = getSize();
    for (int i = 1; i <= len; i++)
    {
      sb.append(this.getElement(i)).append(',');
    }
    if (sb.length() == 1) return "{}";
    sb.setCharAt(sb.length() - 1, '}');
    return sb.toString();
  }

  /**
   * Get the element at this index.
   * If index is zero, return the array size.
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
   *
   * @param index the array element to set; this starts with one, according to BACnet.
   * @param value the new array element.
   */
  public final void setElement(int index, BValue value)
  {
    if (value.getType() == getArrayType())
    {
      if ((index > 0) && (index <= getSize()))
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

//  public void setReadonly(boolean readonly)
//  {
//    SlotCursor sc = getProperties();
//    while (sc.next())
//    {
//      if (sc.property().isDynamic())
//        setFlags(sc.property(),
//          (readonly ? (getFlags(sc.property()) | Flags.READONLY)
//                    : (getFlags(sc.property()) & ~(Flags.READONLY))));
//    }
//  }


////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

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
      loggerBacnet.log(Level.SEVERE, "Exception occurred in getArrayType", e);
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
   * Remove all array elements.

   private void removeAllElements()
   {
   // Don't remove if fixed size.
   if (getFixedSize()) return;

   SlotCursor c = getProperties();
   while (c.next())
   {
   BValue o = (BValue)c.get(); //FIXXSlotCursor
   if ((o instanceof BOrd) || (o.getType() == getArrayType()))
   remove(c.property());
   }
   }
   */
  /**
   * Check the size against the actual count of array elements.
   * If trim is true, differences between size and count will be fixed
   * by adding or removing elements as necessary.  If trim is false,
   * differences will be fixed by adjusting size.
   *
   * @param trim
   * @param cx   Context for trimming, or setting size.
   */
  private synchronized void checkSize(boolean trim, Context cx)
  {
    SlotCursor<Property> c = getProperties();
    int count = 0;
    while (c.next(getArrayType().getTypeClass()))
    {
      if (c.property().isDynamic())
        count++;
    }

    if (trim)
    {
      trimToSize(count, cx);
    }
    else
    {
      setInt(size, count, cx);
      Property p = getProperty(ELEMENT_0);
      if (p != null) setInt(p, count, cx);
    }
  }

  /**
   * "Trim" the array to the size specified in the <code>size</code>
   * Property.  Elements are added or removed as needed.
   */
  private void trimToSize(int count, Context cx)
  {
    int siz = getSize();
    if (siz < count)
    {
      for (int i = siz; i < count; i++)
      {
        remove(name(i), cx);
      }
    }
    else if (siz > count)
    {
      for (int i = count; i < siz; i++)
      {
        add(name(i), (BValue)getArrayType().getInstance(), cx);
      }
    }
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
//    int elem = ndx+1;
//    if (getElementNames().isOrdinal(elem))
//      return getElementNames().getTag(elem);
//    return "element"+(elem);
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
//    Property[] pa = getPropertiesArray();
//    for (int i=4 (5?) ; i<pa.length; i++)
//      if (pa[i] == p) return (i-3);
//    return -1;
    return (Integer.parseInt(p.getName().substring(7)));
  }

  /**
   * Use this to copy component elements.
   */
  private static void copyFrom(BComponent src, BComponent dst, Context cx)
  {
    Context txn = Transaction.start(dst, cx);
    Property[] props = dst.getPropertiesArray();
    int i = props.length;
    while (--i >= 0)
    {
      if (props[i].isDynamic())
      {
        dst.remove(props[i], cx);
      }
    }
    SlotCursor<Property> c = src.getProperties();
    BValue o;
    Property p;
    while (c.next())
    {
      p = c.property();
      o = c.get();
      if (o instanceof BComplex)
        o = o.newCopy(true);
      if (dst.get(p.getName()) != null)
        dst.set(p, o, cx);
      else
        dst.asComponent().add(p.getName(), o, cx);
    }
    try
    {
      Transaction.end(dst, txn);
    }
    catch (Exception x)
    {
      throw new BajaRuntimeException(x);
    }
  }

  private void reIndex()
  {
    if (getFixedSize()) return;
    int siz = getSize();
    for (int ndx = 1; ndx < siz; ++ndx)
    {
      BValue elem = getElement(ndx);

      // check for empty slot
      if (elem == null)
      {
        int i = ndx;

        // find next filled slot
        while (elem == null)
        {
          if (i >= siz) break;
          elem = getElement(++i);
        }

        // move to this slot by adding [ndx] & removing [i]
        add(name(ndx - 1), elem);
        remove(name(i - 1));
      }
    }
  }

////////////////////////////////////////////////////////////////
//  BIBacnetDataType
////////////////////////////////////////////////////////////////

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
//              case ASN_CONSTRUCTED_DATA:
              default:
                ((BIBacnetDataType)c.get()).writeAsn(out);
                break;
            }
          }
        }
        catch (Exception e)
        {
          loggerBacnet.log(Level.WARNING, getName() + ":" + this + ": writeAsn: Exception!", e);
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
    // If this has been polled only because of the link to element0 due
    // to virtual property polling and does not have any subscribers,
    // then remove the element0 property to unsubscribe the array.
    Subscriber[] subs = getSubscribers();
    if ((subs == null) || (subs.length == 0))
    {
      BComponent[] ckids = getChildComponents();
      boolean keepSub = false;
      for (int i = 0; i < ckids.length; i++)
      {
        if (ckids[i].isSubscribed())
        {
          keepSub = true;
          break;
        }
      }
      if (!keepSub)
      {
        Property p = getProperty(ELEMENT_0);
        if (p != null)
          remove(p);
      }
    }

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
      {
        if (p.getType().is(BSimple.TYPE))
          set(p, val, noWrite);
        else if (p.getType().is(BComponent.TYPE))
          copyFrom(val.asComponent(), get(p).asComponent(), noWrite);
        else
          ((BComplex)get(p)).copyFrom(val.asComplex(), noWrite);
      }
      else
        add(name, val, noWrite);
    }
    Property p = getProperty(name(ndx++));
    while (p != null)
    {
      remove(p, noWrite);
      p = getProperty(name(ndx++));
    }

    checkSize(false, noWrite);
  }


////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out) throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetArray", 2);
    out.prop("asnType", asnType);
    out.prop("config", config);
    out.prop("virtual", BacnetVirtualUtil.isVirtual(this));
    out.endProps();
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private int asnType;// = ASN_CONSTRUCTED_DATA;
  private boolean config;
  private static final Logger loggerBacnetClient = Logger.getLogger("bacnet.client");
  private static final Logger loggerBacnet = Logger.getLogger("bacnet");
  private static final Logger loggerBacnetDebug = Logger.getLogger("bacnet.debug");

////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  public static final String ELEMENT_0 = "element0";
}
