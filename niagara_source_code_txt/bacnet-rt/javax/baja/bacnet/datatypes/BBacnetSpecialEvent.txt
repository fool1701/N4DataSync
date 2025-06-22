/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.bacnet.virtual.BBacnetVirtualProperty;
import javax.baja.bacnet.virtual.BacnetVirtualUtil;
import javax.baja.category.BCategoryMask;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnConst;

/**
 * BBacnetSpecialEvent represents the BacnetSpecialEvent sequence.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 6 June 02
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "eventName",
  type = "String",
  defaultValue = "event"
)
@NiagaraProperty(
  name = "periodChoice",
  type = "int",
  defaultValue = "0",
  facets = @Facet("BFacets.makeInt(0,1)")
)
@NiagaraProperty(
  name = "period",
  type = "BValue",
  defaultValue = "new BBacnetCalendarEntry()"
)
@NiagaraProperty(
  name = "eventPriority",
  type = "int",
  defaultValue = "16",
  facets = @Facet("BFacets.makeInt(1, 16)")
)
@NiagaraAction(
  name = "addTimeValue",
  parameterType = "BBacnetTimeValue",
  defaultValue = "new BBacnetTimeValue()"
)
@NiagaraTopic(
  name = "specialEventChanged"
)
public class BBacnetSpecialEvent
  extends BComponent
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetSpecialEvent(1067460910)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "eventName"

  /**
   * Slot for the {@code eventName} property.
   * @see #getEventName
   * @see #setEventName
   */
  public static final Property eventName = newProperty(0, "event", null);

  /**
   * Get the {@code eventName} property.
   * @see #eventName
   */
  public String getEventName() { return getString(eventName); }

  /**
   * Set the {@code eventName} property.
   * @see #eventName
   */
  public void setEventName(String v) { setString(eventName, v, null); }

  //endregion Property "eventName"

  //region Property "periodChoice"

  /**
   * Slot for the {@code periodChoice} property.
   * @see #getPeriodChoice
   * @see #setPeriodChoice
   */
  public static final Property periodChoice = newProperty(0, 0, BFacets.makeInt(0,1));

  /**
   * Get the {@code periodChoice} property.
   * @see #periodChoice
   */
  public int getPeriodChoice() { return getInt(periodChoice); }

  /**
   * Set the {@code periodChoice} property.
   * @see #periodChoice
   */
  public void setPeriodChoice(int v) { setInt(periodChoice, v, null); }

  //endregion Property "periodChoice"

  //region Property "period"

  /**
   * Slot for the {@code period} property.
   * @see #getPeriod
   * @see #setPeriod
   */
  public static final Property period = newProperty(0, new BBacnetCalendarEntry(), null);

  /**
   * Get the {@code period} property.
   * @see #period
   */
  public BValue getPeriod() { return get(period); }

  /**
   * Set the {@code period} property.
   * @see #period
   */
  public void setPeriod(BValue v) { set(period, v, null); }

  //endregion Property "period"

  //region Property "eventPriority"

  /**
   * Slot for the {@code eventPriority} property.
   * @see #getEventPriority
   * @see #setEventPriority
   */
  public static final Property eventPriority = newProperty(0, 16, BFacets.makeInt(1, 16));

  /**
   * Get the {@code eventPriority} property.
   * @see #eventPriority
   */
  public int getEventPriority() { return getInt(eventPriority); }

  /**
   * Set the {@code eventPriority} property.
   * @see #eventPriority
   */
  public void setEventPriority(int v) { setInt(eventPriority, v, null); }

  //endregion Property "eventPriority"

  //region Action "addTimeValue"

  /**
   * Slot for the {@code addTimeValue} action.
   * @see #addTimeValue(BBacnetTimeValue parameter)
   */
  public static final Action addTimeValue = newAction(0, new BBacnetTimeValue(), null);

  /**
   * Invoke the {@code addTimeValue} action.
   * @see #addTimeValue
   */
  public void addTimeValue(BBacnetTimeValue parameter) { invoke(addTimeValue, parameter, null); }

  //endregion Action "addTimeValue"

  //region Topic "specialEventChanged"

  /**
   * Slot for the {@code specialEventChanged} topic.
   * @see #fireSpecialEventChanged
   */
  public static final Topic specialEventChanged = newTopic(0, null);

  /**
   * Fire an event for the {@code specialEventChanged} topic.
   * @see #specialEventChanged
   */
  public void fireSpecialEventChanged(BValue event) { fire(specialEventChanged, event, null); }

  //endregion Topic "specialEventChanged"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetSpecialEvent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  public BBacnetSpecialEvent()
  {
  }


////////////////////////////////////////////////////////////////
//  Overrides
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getEventName())
      .append("[" + getPeriodChoice() + "]: ").append(getPeriod())
      .append(" pri" + getEventPriority() + ":");
    SlotCursor<Property> c = getProperties();
    while (c.next(BBacnetTimeValue.class))
    {
      sb.append(" ").append(c.get().toString(context));
    }
    return sb.toString();
  }

  /**
   * Started.
   * Subclasses <b>MUST</b> call <code>super.started()</code>
   * to ensure that this code is executed.
   */
  public void started()
  {
    if (!BacnetVirtualUtil.isVirtual(this) && (getParent() instanceof BBacnetArray))
    {
      ((BBacnetArray)getParent()).linkTo(this, specialEventChanged, BBacnetArray.arrayPropertyChanged);
    }
    syncPeriod(true);
  }

  /**
   * Property added.
   * Subclasses <b>MUST</b> call <code>super.added()</code>
   * to ensure that this code is executed.
   */
  public void added(Property p, Context cx)
  {
    if (!isMounted())
      return;
    if (!isRunning())
      return;
    if (cx != noWrite)
    {
      sort();
      getParent().asComponent().changed(getPropertyInParent(), cx);
      fireSpecialEventChanged(null);
    }
  }

  /**
   * Property removed.
   * Subclasses <b>MUST</b> call <code>super.removed()</code>
   * to ensure that this code is executed.
   */
  public void removed(Property p, BValue v, Context cx)
  {
    if (!isMounted())
      return;
    if (!isRunning())
      return;
    if (cx != noWrite)
    {
      sort();
      getParent().asComponent().changed(getPropertyInParent(), cx);
      fireSpecialEventChanged(null);
    }
  }

  /**
   * Property changed.
   * Subclasses <b>MUST</b> call <code>super.changed()</code>
   * to ensure that this code is executed.
   */
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isMounted())
      return;
    if (!isRunning())
      return;
    if (p.equals(periodChoice))
    {
      if (cx != noWrite)
      {
        syncPeriod(false);
      }
    }
    else if (p.equals(period))
    {
      if (cx != noWrite)
      {
        syncPeriod(true);
      }
    }
    if (cx != noWrite)
    {
      sort();
      getParent().asComponent().changed(getPropertyInParent(), cx);
      fireSpecialEventChanged(null);
    }
    // vfixx: throw changed w/ GCC context?
  }

  /**
   * Synchronize period and periodChoice.
   *
   * @param fromPeriod if true, set periodChoice from period,
   *                   otherwise set period from periodChoice.
   */
  private void syncPeriod(boolean fromPeriod)
  {
    if (fromPeriod)
    {
      BValue per = getPeriod();
      if (per instanceof BBacnetCalendarEntry)
        setInt(periodChoice, CALENDAR_ENTRY_TAG, noWrite);
      else if (per instanceof BBacnetObjectIdentifier)
        setInt(periodChoice, CALENDAR_REFERENCE_TAG, noWrite);
    }
    else
    {
      int perch = getPeriodChoice();
      if (perch == CALENDAR_ENTRY_TAG)
        set(period, new BBacnetCalendarEntry(), noWrite);
      else if (perch == CALENDAR_REFERENCE_TAG)
        set(period, BBacnetObjectIdentifier.make(BBacnetObjectType.CALENDAR), noWrite);
    }
  }

  /**
   * Callback when the component enters the subscribed state.
   */
  public final void subscribed()
  {
    BBacnetVirtualProperty vp = BacnetVirtualUtil.getVirtualProperty(this);
    if (vp != null) vp.childSubscribed(this);
  }

  /**
   * Callback when the component leaves the subscribed state.
   */
  public final void unsubscribed()
  {
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
//  Actions
////////////////////////////////////////////////////////////////

  public final void doAddTimeValue(BBacnetTimeValue tv)
  {
    add(null, tv);
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
    switch (getPeriodChoice())
    {
      case CALENDAR_ENTRY_TAG:
        out.writeOpeningTag(CALENDAR_ENTRY_TAG);
        ((BBacnetCalendarEntry)getPeriod()).writeAsn(out);
        out.writeClosingTag(CALENDAR_ENTRY_TAG);
        break;
      case CALENDAR_REFERENCE_TAG:
        out.writeObjectIdentifier(CALENDAR_REFERENCE_TAG, (BBacnetObjectIdentifier)getPeriod());
    }
    out.writeOpeningTag(LIST_OF_TIME_VALUES_TAG);
    SlotCursor<Property> c = getProperties();
    while (c.next(BBacnetTimeValue.class))
    {
      ((BBacnetTimeValue)c.get()).writeAsn(out);
    }
    out.writeClosingTag(LIST_OF_TIME_VALUES_TAG);
    out.writeUnsignedInteger(EVENT_PRIORITY_TAG, getEventPriority());
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public final void readAsn(AsnInput in)
    throws AsnException
  {
    int tag = in.peekTag();
    if (in.isOpeningTag(CALENDAR_ENTRY_TAG))
    {
      in.skipTag(); // skip opening tag
      BBacnetCalendarEntry e = new BBacnetCalendarEntry();
      e.readAsn(in);
      setInt(periodChoice, CALENDAR_ENTRY_TAG, noWrite);
      set(period, e, noWrite);
      in.skipTag(); // skip closing tag
    }
    else if (in.isValueTag(CALENDAR_REFERENCE_TAG))
    {
      setInt(periodChoice, CALENDAR_REFERENCE_TAG, noWrite);
      set(period, in.readObjectIdentifier(CALENDAR_REFERENCE_TAG), noWrite);
    }
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);

    tag = in.peekTag();
    if (in.isOpeningTag(LIST_OF_TIME_VALUES_TAG))
      in.skipTag(); // skip opening tag
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);

    tag = in.peekTag();
    while (!in.isClosingTag(LIST_OF_TIME_VALUES_TAG))
    {
      if (tag == AsnInput.END_OF_DATA)
        throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);

      // First read in the time and value.
      BBacnetTime t = in.readTime();
      BSimple v;
      int asnType = in.peekApplicationTag();
      switch (asnType)
      {
        case ASN_NULL:
          v = in.readNull();
          break;
        case ASN_BOOLEAN:
          v = BBoolean.make(in.readBoolean());
          break;
        case ASN_UNSIGNED:
          v = in.readUnsigned();
          break;
        case ASN_INTEGER:
          v = BInteger.make(in.readSignedInteger());
          break;
        case ASN_REAL:
          v = BFloat.make(in.readReal());
          break;
        case ASN_DOUBLE:
          v = BDouble.make(in.readDouble());
          break;
        case ASN_OCTET_STRING:
          v = in.readBacnetOctetString();
          break;
        case ASN_CHARACTER_STRING:
          v = BString.make(in.readCharacterString());
          break;
        case ASN_BIT_STRING:
          v = in.readBitString();
          break;
        case ASN_ENUMERATED:
          v = BDynamicEnum.make(in.readEnumerated());
          break;
        case ASN_DATE:
          v = in.readDate();
          break;
        case ASN_TIME:
          v = in.readTime();
          break;
        case ASN_OBJECT_IDENTIFIER:
          v = in.readObjectIdentifier();
          break;
        default:
          throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + asnType);
      }

      // Then see if we already have this time.  If so, use that TimeValue.
      BBacnetTimeValue tv = getTV(t);
      if (tv == null)
        add(null, new BBacnetTimeValue(t, v), noWrite);
      else
        tv.set(BBacnetTimeValue.value, v, noWrite);
      tag = in.peekTag();
    }
    in.skipTag(); // skip closing tag

    setInt(eventPriority, in.readUnsignedInt(EVENT_PRIORITY_TAG), noWrite);
  }


////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * Get the next event defined in this BacnetSpecialEvent,
   * starting at the specified <code>BAbsTime</code>.
   *
   * @param time the starting time.
   * @return the <code>BAbsTime</code> of the next defined event.
   */
  public final BAbsTime nextEvent(BAbsTime time)
  {
    switch (getPeriodChoice())
    {
      case CALENDAR_ENTRY_TAG:
        BBacnetCalendarEntry e = (BBacnetCalendarEntry)getPeriod();
        BAbsTime date = e.nextDate(time);
        if (date == null) return null;
        BTime tod = BTime.make(time);
        BBacnetTimeValue tv;
        BBacnetTimeValue tvnext = null;
        SlotCursor<Property> c = getProperties();
        while (c.next(BBacnetTimeValue.class))
        {
          tv = (BBacnetTimeValue)c.get();
          if ((tv.getTime().isAfter(tod))
            && ((tvnext == null) || (tv.isBefore(tvnext))))
            tvnext = tv;
        }
        if (tvnext == null) return null;
        return BBacnetDateTime.makeBAbsTime(date, tvnext.getTime());
      case CALENDAR_REFERENCE_TAG:
        logger.severe("Calendar reference choice not supported!");
        return null;
      default:
        logger.severe("Calendar tag choice not supported!");
        return null;
    }
  }

  /**
   * Get the value at a specified time.
   *
   * @param time the <code>BAbsTime</code> for which the value is needed.
   * @return the value at that time, as a BSimple.
   */
  public final BSimple getValue(BAbsTime time)
  {
    // if this event is not active at the given time, return null
    if (!isActive(time)) return null;

    // check the time of day against the list of time-values for the event.
    BTime tod = BTime.make(time);
    BBacnetTimeValue tv = null;
    BBacnetTimeValue tvlast = null;
    SlotCursor<Property> c = getProperties();
    while (c.next(BBacnetTimeValue.class))
    {
      tv = (BBacnetTimeValue)c.get();
      if ((tv.getTime().isNotAfter(tod))
        && ((tvlast == null) || (tv.isNotBefore(tvlast))))
        tvlast = tv;
    }
    // if fell through, use the last value.
    if (tvlast == null) tvlast = tv;
    // if no last value, return null.
    if (tvlast == null) return BBacnetNull.DEFAULT;
    // return the value of the correct tv.
    return tvlast.getValue().getAny();
  }


////////////////////////////////////////////////////////////////
//  Support
////////////////////////////////////////////////////////////////

  /**
   * Is this special event active at the given time?
   */
  private boolean isActive(BAbsTime time)
  {
    switch (getPeriodChoice())
    {
      case CALENDAR_ENTRY_TAG:
        return ((BBacnetCalendarEntry)getPeriod()).isActive(time);
      case CALENDAR_REFERENCE_TAG:
        logger.severe("Calendar reference choice not supported!");
        return false;
      default:
        logger.severe("Calendar tag choice not supported!");
        return false;
    }
  }

  /**
   * Get the BBacnetTimeValue child for this time, if one exists.
   */
  private BBacnetTimeValue getTV(BBacnetTime t)
  {
    SlotCursor<Property> c = getProperties();
    while (c.next(BBacnetTimeValue.class))
    {
      if (((BBacnetTimeValue)c.get()).getTime().equals(t))
        return (BBacnetTimeValue)c.get();
    }
    return null;
  }

  /**
   * Sort the time-value pairs.
   */
  private void sort()
  {
    Property[] props = getPropertiesArray();
    if (props.length > 4)
    {
      Property[] tvs = new Property[props.length - 4];
      System.arraycopy(props, 4, tvs, 0, tvs.length);
      Property temp;
      for (int i = 0; i < tvs.length - 1; i++)
      {
        int small = i;
        for (int j = i + 1; j < tvs.length; j++)
        {
          BBacnetTimeValue tvj = (BBacnetTimeValue)get(tvs[j]);
          BBacnetTimeValue tvsmall = (BBacnetTimeValue)get(tvs[small]);
          if (tvj.isBefore(tvsmall))
          {
            small = j;
          }
        }
        temp = tvs[i];
        tvs[i] = tvs[small];
        tvs[small] = temp;
      }
      reorder(tvs, null);
    }
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out) throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetSpecialEvent", 2);
    out.prop("virtual", BacnetVirtualUtil.isVirtual(this));
    out.endProps();
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static final Logger logger = Logger.getLogger("bacnet.debug");

  public static final int CALENDAR_ENTRY_TAG = 0;
  public static final int CALENDAR_REFERENCE_TAG = 1;
  public static final int LIST_OF_TIME_VALUES_TAG = 2;
  public static final int EVENT_PRIORITY_TAG = 3;
}
