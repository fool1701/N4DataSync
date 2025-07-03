/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.BBacnetObject;
import javax.baja.bacnet.BacnetConfirmedServiceChoice;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.export.BIBacnetExportObject;
import javax.baja.bacnet.io.*;
import javax.baja.bacnet.virtual.BBacnetVirtualProperty;
import javax.baja.bacnet.virtual.BacnetVirtualUtil;
import javax.baja.category.BCategoryMask;
import javax.baja.driver.loadable.BUploadParameters;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.security.PermissionException;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.*;
import javax.baja.util.BTypeSpec;

import com.tridium.bacnet.BacUtil;
import com.tridium.bacnet.asn.AsnConst;
import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.datatypes.BAddListElementAction;
import com.tridium.bacnet.datatypes.BRemoveListElementAction;
import com.tridium.bacnet.datatypes.ListManipulation;
import com.tridium.bacnet.services.error.NChangeListError;

/**
 * BBacnetListOf represents a Bacnet ListOf sequence, which contains a non-indexed
 * sequence of objects of a particular Bacnet data type.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 08 May 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "listTypeSpec",
  type = "BTypeSpec",
  defaultValue = "BTypeSpec.DEFAULT",
  flags = Flags.HIDDEN
)
@NiagaraTopic(
  name = "listPropertyChanged"
)
public class BBacnetListOf
  extends BComponent
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetListOf(3038711072)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "listTypeSpec"

  /**
   * Slot for the {@code listTypeSpec} property.
   * @see #getListTypeSpec
   * @see #setListTypeSpec
   */
  public static final Property listTypeSpec = newProperty(Flags.HIDDEN, BTypeSpec.DEFAULT, null);

  /**
   * Get the {@code listTypeSpec} property.
   * @see #listTypeSpec
   */
  public BTypeSpec getListTypeSpec() { return (BTypeSpec)get(listTypeSpec); }

  /**
   * Set the {@code listTypeSpec} property.
   * @see #listTypeSpec
   */
  public void setListTypeSpec(BTypeSpec v) { set(listTypeSpec, v, null); }

  //endregion Property "listTypeSpec"

  //region Topic "listPropertyChanged"

  /**
   * Slot for the {@code listPropertyChanged} topic.
   * @see #fireListPropertyChanged
   */
  public static final Topic listPropertyChanged = newTopic(0, null);

  /**
   * Fire an event for the {@code listPropertyChanged} topic.
   * @see #listPropertyChanged
   */
  public void fireListPropertyChanged(BValue event) { fire(listPropertyChanged, event, null); }

  //endregion Topic "listPropertyChanged"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetListOf.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetListOf()
  {
  }

  /**
   * Constructor with type specification.
   *
   * @param listType the type of elements to be contained by this list.
   */
  public BBacnetListOf(Type listType)
  {
    setListTypeSpec(BTypeSpec.make(listType));
  }


////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  public void started()
  {
    if (getParent() instanceof BBacnetObject)
      config = true;
    if (getParent() instanceof BIBacnetExportObject)
      export = true;
    if (addActions)
    {
      if (get("addElement") == null)
      {
        BAddListElementAction addElement = new BAddListElementAction();
        addElement.setParameterTypeSpec(getListTypeSpec());
        add("addElement", addElement, Flags.TRANSIENT);
      }
      if (get("removeElement") == null)
      {
        BRemoveListElementAction removeElement = new BRemoveListElementAction();
        removeElement.setParameterTypeSpec(getListTypeSpec());
        add("removeElement", removeElement, Flags.TRANSIENT);
      }
    }
  }

  /**
   * Changed.
   */
  public final void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning()) return;
    if (config || export || BacnetVirtualUtil.isVirtual(this))
      getParent().asComponent().changed(getPropertyInParent(), cx);
    // vfixx: throw changed w/ GCC context?
  }

  /**
   * Added.
   */
  public final void added(Property p, Context cx)
  {
    super.added(p, cx);
    if (cx == noWrite) return;
    if (export/* || BacnetVirtualUtil.isVirtual(this)*/)
      getParent().asComponent().changed(getPropertyInParent(), cx);
  }

  /**
   * Removed.
   */
  public final void removed(Property p, BValue old, Context cx)
  {
    super.removed(p, old, cx);
    if (cx == noWrite) return;
    if (export/* || BacnetVirtualUtil.isVirtual(this)*/)
      getParent().asComponent().changed(getPropertyInParent(), cx);
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
   * Callback when the component leaves the subscribed state.
   */
  public final void unsubscribed()
  {
    if (config && !getParent().asComponent().isSubscribed())
      getParent().asComponent().unsubscribed();
    BBacnetVirtualProperty vp = BacnetVirtualUtil.getVirtualProperty(this);
    if (vp != null) vp.childUnsubscribed(this);
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
// List Manipulation
////////////////////////////////////////////////////////////////

  /**
   * Add element(s) to the list.
   *
   * @param encodedElements
   * @return null if successful, or an appropriate error if not.
   */
  public final ChangeListError addElements(byte[] encodedElements, Context cx)
  {
    //Values are entered into the ArrayList as:
    //
    //  BObject (with implicit BIBacnetDataType assertion)
    //  BSimple
    //
    //Values are extracted from the ArrayList as:
    //
    //  BValue
    //
    //BObject is the common ancestor; however,
    //blanket extraction as BValue and assertion
    //as BIBacnetDataType on insert, and observation
    //that only BValue+ implements BIBacnetDataType,
    //should indicate that it is safe use BValue as ArrayList
    //baseline.
    ArrayList<BValue> v = new ArrayList<>();
    int ffen = 1; // first failed element number (1-based)
    try
    {
      AsnInputStream in = new AsnInputStream(encodedElements);
      int tag = in.peekTag();
      Type t = getListType();
      if (t.is(BIBacnetDataType.TYPE))
      {
        while (tag != AsnInputStream.END_OF_DATA)
        {
          BObject o = t.getInstance();
          ((BIBacnetDataType)o).readAsn(in);
          v.add((BValue)o);
          ffen++;   // increment failure counter
          tag = in.peekTag();
        }
      }
      else
      {
        // This is a list of primitive datatypes.  Use the tag to decode.
        while (tag != AsnInputStream.END_OF_DATA)
        {
          BSimple s = in.readAsn();
          if (t.is(BEnum.TYPE))
            s = ((BEnum)t.getInstance()).getRange().get(((BInteger)s).getInt());
          v.add(s);
          ffen++;
          tag = in.peekTag();
        }
      }
    }
    catch (AsnException e)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
        new NErrorType(BBacnetErrorClass.PROPERTY,
          BBacnetErrorCode.INVALID_DATA_TYPE),
        ffen);
    }

    // Now add the decoded elements to the list,
    // using ffen again to track.
    try
    {
      // Note internal list element access is zero-based.
      for (ffen = 0; ffen < v.size(); ffen++)
        addListElement(v.get(ffen), cx);
      return null;
    }
    catch (PermissionException e)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
        new NErrorType(BBacnetErrorClass.PROPERTY,
          BBacnetErrorCode.WRITE_ACCESS_DENIED),
        0);
    }
  }

  /**
   * Remove element(s) from the list.
   *
   * @param encodedElements
   * @return true if successful.
   */
  public final ChangeListError removeElements(byte[] encodedElements, Context cx)
  {
    //Values are entered into the ArrayList as:
    //
    //  BObject (with implicit BIBacnetDataType assertion)
    //  BSimple
    //
    //Values are extracted from the ArrayList as:
    //
    //  BValue
    //
    //BObject is the common ancestor; however,
    //blanket extraction as BValue and assertion
    //as BIBacnetDataType on insert, and observation
    //that only BValue+ implements BIBacnetDataType,
    //should indicate that it is safe use BValue as ArrayList
    //baseline.
    ArrayList<BValue> v = new ArrayList<>();
    int ffen = 1; // first failed element number (1-based)
    try
    {
      AsnInputStream in = new AsnInputStream(encodedElements);
      int tag = in.peekTag();
      Type t = getListType();
      if (t.is(BIBacnetDataType.TYPE))
      {
        while (tag != AsnInputStream.END_OF_DATA)
        {
          BObject o = t.getInstance();
          ((BIBacnetDataType)o).readAsn(in);
          v.add((BValue)o);
          ffen++;   // increment failure counter
          tag = in.peekTag();
        }
      }
      else
      {
        // This is a list of primitive datatypes.  Use the tag to decode.
        while (tag != AsnInputStream.END_OF_DATA)
        {
          BSimple s = in.readAsn();
          if (t.is(BEnum.TYPE))
            s = ((BEnum)t.getInstance()).getRange().get(((BInteger)s).getInt());
          v.add(s);
          ffen++;
          tag = in.peekTag();
        }
      }
    }
    catch (AsnException e)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
        new NErrorType(BBacnetErrorClass.PROPERTY,
          BBacnetErrorCode.INVALID_DATA_TYPE),
        ffen);
    }

    // Now remove the decoded elements from the list,
    // using ffen again to track.  Do this in two steps, so
    // we can abort the remove if one isn't found, without
    // having to restore the list (which might cause UI issues).
    try
    {
      // Note internal list element access is zero-based.
      for (ffen = 1; ffen <= v.size(); ffen++)
        if (!contains(v.get(ffen - 1)))
          return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
            new NErrorType(BBacnetErrorClass.SERVICES,
              BBacnetErrorCode.LIST_ELEMENT_NOT_FOUND),
            ffen);

      for (ffen = 0; ffen < v.size(); ffen++)
        removeListElement(v.get(ffen), cx);
      return null;
    }
    catch (PermissionException e)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
        new NErrorType(BBacnetErrorClass.PROPERTY,
          BBacnetErrorCode.WRITE_ACCESS_DENIED),
        0);
    }
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Does the list contain the given element?
   *
   * @param element
   * @return true if there is an element in the list that is <code>equivalent</code>
   * to the given element.
   */
  public final boolean contains(BValue element)
  {
    SlotCursor<Property> c = getProperties();
    while (c.next())
    {
      if (c.get().equivalent(element))
        return true;
    }
    return false;
  }

  /**
   * Get the Baja type of objects contained by this list.
   */
  public final Type getListType()
  {
    try
    {
      return getListTypeSpec().getResolvedType();
    }
    catch (Exception e)
    {
      log.info("Exception resolving list type for " + getName() + ":" + e.getMessage());
      return BBacnetNull.TYPE;
    }
  }

  /**
   * To String.
   */
  public String toString(Context cx)
  {
    // try to handle PropertySheet with just a description
    if ((cx != null) && (cx instanceof BasicContext))
    {
      return "List of " + getListTypeSpec();
    }

    loadSlots();
    StringBuilder sb = new StringBuilder("{");
    SlotCursor<Property> sc = getProperties();
    while (sc.next())
    {
      if (sc.slot().isProperty()
        && (sc.property().getType().getTypeSpec().equals(getListTypeSpec())
        || sc.property().getType().is(BOrd.TYPE)))
        sb.append(sc.get()).append(',');
    }
    if (sb.length() == 1) return "{}";
    sb.setCharAt(sb.length() - 1, '}');
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
  public final void writeAsn(AsnOutput out)
  {
    synchronized (out)
    {
      SlotCursor<Property> c = getProperties();
      c.next(); // skip the listTypeSpec property.
      while (c.next())
      {
        try
        {
          BObject listElement = c.get();

          // Skip the add/remove actions.
          if (listElement instanceof BAction)
            continue;

          // For ords, get the resolved target.
          if (listElement instanceof BOrd)
          {
            listElement = ((BOrd)listElement).get(this);
          }

          if ((listElement != null) && (listElement.getType() == getListType()))
          {
            switch (asnType())
            {
              case ASN_NULL:
                out.writeNull();
                break;
              case ASN_BOOLEAN:
                out.writeBoolean((BBoolean)listElement);
                break;
              case ASN_UNSIGNED:
                out.writeUnsigned((BBacnetUnsigned)listElement);
                break;
              case ASN_INTEGER:
                out.writeSignedInteger((BInteger)listElement);
                break;
              case ASN_REAL:
                out.writeReal((BFloat)listElement);
                break;
              case ASN_DOUBLE:
                out.writeDouble((BDouble)listElement);
                break;
              case ASN_OCTET_STRING:
                out.writeOctetString((BBacnetOctetString)listElement);
                break;
              case ASN_CHARACTER_STRING:
                out.writeCharacterString((BString)listElement);
                break;
              case ASN_BIT_STRING:
                out.writeBitString((BBacnetBitString)listElement);
                break;
              case ASN_ENUMERATED:
                out.writeEnumerated((BEnum)listElement);
                break;
              case ASN_DATE:
                out.writeDate((BBacnetDate)listElement);
                break;
              case ASN_TIME:
                out.writeTime((BBacnetTime)listElement);
                break;
              case ASN_OBJECT_IDENTIFIER:
                out.writeObjectIdentifier((BBacnetObjectIdentifier)listElement);
                break;
              default:
                ((BIBacnetDataType)listElement).writeAsn(out);
                if (log.isLoggable(Level.FINE))
                {
                  log.fine(getName() + ": writeAsn: constructed data type: listElem="
                  + listElement + " t=" + listElement.getType() + ", list type=" + getListType());
                }
                break;
            }
          }
          else
          {
            log.warning(getName() + ": writeAsn: listElem is null or type mismatch!");
          }
        }
        catch (Exception e)
        {
          log.warning(getName() + ": writeAsn: Exception! " + e.getMessage());
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
        BValue listElement = null;
        switch (asnType())
        {
          case ASN_NULL:
            listElement = in.readNull();
            break;
          case ASN_BOOLEAN:
            listElement = BBoolean.make(in.readBoolean());
            break;
          case ASN_UNSIGNED:
            listElement = in.readUnsigned();
            break;
          case ASN_INTEGER:
            listElement = BInteger.make(in.readSignedInteger());
            break;
          case ASN_REAL:
            listElement = BFloat.make(in.readReal());
            break;
          case ASN_DOUBLE:
            listElement = BDouble.make(in.readDouble());
            break;
          case ASN_OCTET_STRING:
            listElement = BBacnetOctetString.make(in.readOctetString());
            break;
          case ASN_CHARACTER_STRING:
            listElement = BString.make(in.readCharacterString());
            break;
          case ASN_BIT_STRING:
            listElement = in.readBitString();
            break;
          case ASN_ENUMERATED:
            BEnum d = (BEnum)getListType().getInstance();
            listElement = d.getRange().get(in.readEnumerated());
            break;
          case ASN_DATE:
            listElement = in.readDate();
            break;
          case ASN_TIME:
            listElement = in.readTime();
            break;
          case ASN_OBJECT_IDENTIFIER:
            listElement = in.readObjectIdentifier();
            break;
          default:
            listElement = (BValue)getListType().getInstance();
            ((BIBacnetDataType)listElement).readAsn(in);
            break;
        }
        if (listElement != null)
        {
          if (listElement.getType().getTypeName().equals(getListTypeSpec().getTypeName()))
            v.add(listElement);
          else
            throw new AsnException("Invalid data type for list element: expected="
              + getListTypeSpec().getTypeName() + " actual=" + listElement.getType().getTypeName());
        }
      }
    }

    int ndx = 0;
    Iterator<BValue> it = v.iterator();
    while (it.hasNext())
    {
      BValue val = it.next();
      String name = name(ndx++);
      BacUtil.setOrAdd(this, name, val, noWrite);
    }
    Property p = getProperty(name(ndx++));
    while (p != null)
    {
      remove(p, noWrite);
      p = getProperty(name(ndx++));
    }
  }


////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  public Property addListElement(BValue listElement, Context cx)
  {
    // If we are in a config object, we need to write this to the remote device.
    if (config)
    {
      BBacnetObject o = (BBacnetObject)getParent();
      o.postAsync(new ListManipulation(o, getPropertyInParent(), listElement, true));

      if(o.getObjectId().getInstanceNumber() == -1)
      {
        BacUtil.setOrAdd(this, listElement.getClass().getSimpleName()+"?", listElement, noWrite);
        return null;
      }
      o.upload(new BUploadParameters());
      return null;  // return value is not relevant here.
    }

    // Check parameter type.
    if (listElement.getType().is(getListType()))
    {
      if (!contains(listElement))
        return add(null, listElement, cx);
      else
        return null;
    }
    else if (listElement instanceof BOrd)
    {
      return add(null, listElement, Flags.TRANSIENT, cx);
    }
    else
    {
      log.severe(this + ".addListElement:Wrong element type: this is a list of " + getListType().getTypeName());
      return null;
    }
  }

  public void removeListElement(BValue listElement, Context cx)
  {
    // If we are in a config object, we need to write this to the remote device.
    if (config)
    {
      BBacnetObject o = (BBacnetObject)getParent();
      if (o.getObjectId().getInstanceNumber() != -1)
      {
        o.postAsync(new ListManipulation(o, getPropertyInParent(), listElement, false));
        o.upload(new BUploadParameters());
        return;
      }
    }

    // Remove.
    SlotCursor<Property> c = getProperties();
    while (c.next())
    {
      if (c.get().equivalent(listElement))
      {
        remove(c.property(), cx);
        return;
      }
    }
  }

  /**
   * Get the Asn type of objects contained by this list.
   */
  private int asnType()
  {
    Type t = getListType();
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

//  /**
//   * Remove all elements from the list.
//   */
//  private void removeAllElements()
//  {
//    SlotCursor c = getProperties();
//    while (c.next())
//    {
//      BObject o = c.get();
//      if ((o instanceof BOrd) || (o.getType() == getListType()))
//        remove(c.property());
//    }
//  }

//  /**
//   * Get the list element that is equivalent to this potential element,
//   * or null if there is no existing equivalent element.
//   */
//  private BValue getElement(BValue elem)
//  {
//    if (elem == null) return null;
//    SlotCursor c = getProperties();
//    while (c.next())
//    {
//      if (c.get().equivalent(elem))
//      {
//        return (BValue)c.get(); //FIXXSlotCursor
//      }
//    }
//    return null;
//  }

  /**
   * Get the slot name for the list element at this index.
   * Note that BACnet Lists do not provide access to the individual
   * elements, so this is not for BACnet access, but may make it
   * easier for Niagara classes to manipulate the ListOf.
   *
   * @param ndx the array index (0 to N-1).
   * @return the slot name for this element.
   */
  private String name(int ndx)
  {
    String s = getListTypeSpec().getTypeName();
    if (ndx == 0) return s;
    return s + ndx;
  }

//  /**
//   * Get the list element index that this Property represents.
//   * Note that BACnet Lists do not provide access to the individual
//   * elements, so this is not for BACnet access, but may make it
//   * easier for Niagara classes to manipulate the ListOf.
//   * @param p the list element property.
//   * @returns the list index for this element.
//   */
//  private int index(Property p)
//  {
//    String s = p.getName().substring(getListTypeSpec().getTypeName().length());
//    if (s.equals("")) return 0;
//    return Integer.parseInt(s);
//  }


////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out) throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetListOf", 2);
    out.prop("asnType", asnType);
    out.prop("config", config);
    out.prop("export", export);
    out.prop("virtual", BacnetVirtualUtil.isVirtual(this));
    out.endProps();
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  protected boolean addActions = true;
  private int asnType;
  private boolean config;
  private boolean export;

  protected static final Logger log = Logger.getLogger("bacnet");
}
