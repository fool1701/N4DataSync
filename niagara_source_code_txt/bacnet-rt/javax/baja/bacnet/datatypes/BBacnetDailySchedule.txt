/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.bacnet.virtual.BBacnetVirtualProperty;
import javax.baja.bacnet.virtual.BacnetVirtualUtil;
import javax.baja.category.BCategoryMask;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnConst;

/**
 * BBacnetDailySchedule represents the BacnetDailySchedule sequence.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 6 June 02
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraAction(
  name = "addTimeValue",
  parameterType = "BBacnetTimeValue",
  defaultValue = "new BBacnetTimeValue()"
)
@NiagaraAction(
  name = "removeTimeValue",
  parameterType = "BString",
  defaultValue = "BString.make(\"\")"
)
@NiagaraTopic(
  name = "dailyScheduleChanged"
)
public class BBacnetDailySchedule
  extends BComponent
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetDailySchedule(796351094)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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

  //region Action "removeTimeValue"

  /**
   * Slot for the {@code removeTimeValue} action.
   * @see #removeTimeValue(BString parameter)
   */
  public static final Action removeTimeValue = newAction(0, BString.make(""), null);

  /**
   * Invoke the {@code removeTimeValue} action.
   * @see #removeTimeValue
   */
  public void removeTimeValue(BString parameter) { invoke(removeTimeValue, parameter, null); }

  //endregion Action "removeTimeValue"

  //region Topic "dailyScheduleChanged"

  /**
   * Slot for the {@code dailyScheduleChanged} topic.
   * @see #fireDailyScheduleChanged
   */
  public static final Topic dailyScheduleChanged = newTopic(0, null);

  /**
   * Fire an event for the {@code dailyScheduleChanged} topic.
   * @see #dailyScheduleChanged
   */
  public void fireDailyScheduleChanged(BValue event) { fire(dailyScheduleChanged, event, null); }

  //endregion Topic "dailyScheduleChanged"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetDailySchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $javax.baja.bacnet.datatypes.BBacnetDailySchedule(1163415162)1.0$ @*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetDailySchedule()
  {
  }


////////////////////////////////////////////////////////////////
//  Actions
////////////////////////////////////////////////////////////////

  /**
   * Add a Time-Value pair to this daily schedule.
   *
   * @param tv the BacnetTimeValue to add.
   */
  public final void doAddTimeValue(BBacnetTimeValue tv)
  {
    add(null, tv);
  }

  /**
   * Remove the named Time-Value pair.
   *
   * @param tvName the name of the BacnetTimeValue to remove.
   */
  public final void doRemoveTimeValue(BString tvName)
  {
    Property property = getProperty(tvName.getString());
    if (property != null)
      remove(property, null);
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
// 2002-06-07 CPG
// The ASN.1 production for BacnetDailySchedule contains a context-tag of 0
// for the "day-schedule" attribute.  I think this should be included, but
// VTS 3.1.5 is not expecting to see this tag.
    out.writeOpeningTag(DAY_SCHEDULE_TAG);
    SlotCursor<Property> c = getProperties();
    while (c.next(BBacnetTimeValue.class))
    {
      ((BBacnetTimeValue)c.get()).writeAsn(out);
    }
    out.writeClosingTag(DAY_SCHEDULE_TAG);
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public final void readAsn(AsnInput in)
    throws AsnException
  {
// 2002-06-07 CPG
// The ASN.1 production for BacnetDailySchedule contains a context-tag of 0
// for the "day-schedule" attribute.  I think this should be included, but
// VTS 3.1.5 is not expecting to see this tag.
    int tag = in.peekTag();
    if (in.isOpeningTag(DAY_SCHEDULE_TAG))
      in.skipTag(); // skip opening tag
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);

    tag = in.peekTag();
    while (!in.isClosingTag(DAY_SCHEDULE_TAG))
    {
      if (tag == AsnInput.END_OF_DATA)
        throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);

      // Read the time and value.
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
          throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);
      }

      // Determine if we already have a slot at this time.
      // If so, just use that instead of creating a new one.
      BBacnetTimeValue tv = getTV(t);
      if (tv == null)
        add(null, new BBacnetTimeValue(t, v), noWrite);
      else
//        tv.set(BBacnetTimeValue.value, v, noWrite);
        tv.getValue().setAny(v, noWrite);
      tag = in.peekTag();
    }
    in.skipTag(); // skip closing tag
  }


////////////////////////////////////////////////////////////////
//  Overrides
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context cx)
  {
    // try to handle PropertySheet with just a description
    if ((cx != null) && (cx instanceof BasicContext))
    {
      return "BacnetDailySchedule{" + getPropertyInParent() + "}";
    }

    loadSlots();
    StringBuilder sb = new StringBuilder("{");
    SlotCursor<Property> sc = getProperties();
    while (sc.next())
      sb.append(sc.get()).append(',');
    if (sb.length() == 1) return "{}";
    sb.setCharAt(sb.length() - 1, '}');
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
      ((BBacnetArray)getParent()).linkTo(this, dailyScheduleChanged, BBacnetArray.arrayPropertyChanged);
    }
  }

  /**
   * Property added.
   * Subclasses <b>MUST</b> call <code>super.added()</code>
   * to ensure that this code is executed.
   *
   * @param p  the property.
   * @param cx the context.
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
      fireDailyScheduleChanged(null);
    }
  }

  /**
   * Property removed.
   * Subclasses <b>MUST</b> call <code>super.removed()</code>
   * to ensure that this code is executed.
   *
   * @param p  the property.
   * @param cx the context.
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
      fireDailyScheduleChanged(null);
    }
  }

  /**
   * Property changed.
   * Subclasses <b>MUST</b> call <code>super.changed()</code>
   * to ensure that this code is executed.
   *
   * @param p  the property.
   * @param cx the context.
   */
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isMounted())
      return;
    if (!isRunning())
      return;
    if (cx != noWrite)
    {
      sort();
      getParent().asComponent().changed(getPropertyInParent(), cx);
      fireDailyScheduleChanged(null);
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
//  Access
////////////////////////////////////////////////////////////////

  /**
   * Get the value at a particular time.
   * This scans through all of the time-values.  For each time-value,
   * check if it is before the given time, and after the last
   * time encountered so far.  If so, mark it as the last time.
   * When all pairs have been checked, return the value of the last
   * time-value pair.
   *
   * @param at the time at which the value of the daily schedule is desired.
   * @return the value at the specified time.
   */
  public final BSimple getValue(BTime at)
  {
    BBacnetTimeValue tv = null;
    BBacnetTimeValue tvlast = null;
    SlotCursor<Property> c = getProperties();
    while (c.next(BBacnetTimeValue.class))
    {
      tv = (BBacnetTimeValue)c.get();
      if ((tv.getTime().toBTime().isBefore(at))
        && ((tvlast == null) || (tv.isAfter(tvlast))))
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
    Property[] tvs = getPropertiesArray();
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

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out) throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetDailySchedule", 2);
    out.prop("virtual", BacnetVirtualUtil.isVirtual(this));
    out.endProps();
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int DAY_SCHEDULE_TAG = 0;

}
