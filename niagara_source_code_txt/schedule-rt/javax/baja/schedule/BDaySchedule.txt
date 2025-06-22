/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import java.util.Vector;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BTime;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A set of BTimeSchedules applicable every day.
 * @author Aaron Hansen
 * @creation Sept 2001
 * @version $Revision: 15$ $Date: 9/21/09 11:42:09 AM EDT$
 */
@NiagaraType
public class BDaySchedule
  extends BCompositeSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BDaySchedule(2979906276)1.0$ @*/
/* Generated Sat Jan 29 19:39:56 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDaySchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BDaySchedule() {}


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /**
   * Only adds if the given schedule doesn't conflict with any children.
   * This assumes that in the parameter, the start is before the finish.
   * @return False if the schedule wasn't added.
   */
  public boolean add(BTimeSchedule range, Context cx)
  {
    BTimeSchedule[] kids = getChildren(BTimeSchedule.class);
    int len = kids.length;
    BTime start = range.getStart();
    BTime finish = range.getFinish();
    boolean midnightFinish = finish.equals(BTime.MIDNIGHT);
    boolean midnightStart  = start.equals(BTime.MIDNIGHT);
    while (--len >= 0)
    {
      BTime kidStart = kids[len].getStart();
      BTime kidFinish = kids[len].getFinish();
      boolean midnightKidFinish = kidFinish.equals(BTime.MIDNIGHT);

      boolean bothBefore =  !midnightFinish &&  start.isBefore(kidStart)  && !finish.isAfter(kidStart);

      boolean bothAfter  =  !midnightStart  && !midnightKidFinish && !start.isBefore(kidFinish)
                                            && (!finish.isBefore(kidFinish) || midnightFinish);
      if(!bothBefore && !bothAfter)
      {
        return false;
      }
    }
    add("time?",range, cx);
    return true;
  }

  public boolean add(BTimeSchedule range)
  {
    return add(range, null);
  }

  public boolean add(BTime start, BTime finish, BStatusValue value)
  {
    return add(start, finish, value, null);
  }

  public boolean add(BTime start, BTime finish, BStatusValue value, Context cx)
  {
    BTimeSchedule sch = new BTimeSchedule(start,finish);
    sch.setEffectiveValue(value);
    return add(sch, cx);
  }

  /**
   * Convenience for programmatically adding a trigger when this
   * is used in a BTriggerSchedule.
   * @param hour Hour of day, 0 - 23
   * @param min Minute of hour, 0 - 59
   */
  public boolean addTrigger(int hour, int min)
  {
    return add(new BTimeSchedule(BTime.make(hour,min,0),
                                 BTime.make(hour,min,20)));
  }

  /**
   * Removes all BTimeSchedule descendents and returns this.
   * @return this
   */
  public BDaySchedule clear()
  {
    Vector<Property> v = new Vector<>();
    SlotCursor<Property> c = getProperties();
    while (c.next(BTimeSchedule.class))
      v.addElement(c.property());
    for (int i = v.size(); --i >= 0; )
      remove(v.elementAt(i));
    return this;
  }

  /**
   * The schedule who is effective at the given time.  If a child is always
   * effective, it may be returned regardless of its configured time.
   * @return Null is there is no child effective at the given time.
   */
  public BTimeSchedule effectiveAt(BTime time)
  {
    Object[] kids = getChildren(BTimeSchedule.class);
    int len = kids.length;
    while (--len >= 0)
    {
      if (cast(kids[len]).isEffective(time))
        return cast(kids[len]);
    }
    return null;
  }

  public BTimeSchedule[] getTimesInOrder()
  {
    BTimeSchedule[] ret = getChildren(BTimeSchedule.class);
    for (int i = ret.length; --i > 0; )
    {
      for (int j = i; --j >= 0; )
      {
        if (ret[i].getStart().isBefore(ret[j].getStart()))
        {
          BTimeSchedule tmp = ret[j];
          ret[j] = ret[i];
          ret[i] = tmp;
        }
      }
    }
    return ret;
  }

  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  private static final BTimeSchedule cast(Object o)
  {
    return (BTimeSchedule) o;
  }

  /////////////////////////////////////////////////////////////////
  // Inner Classes - in alphabetical order by class name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Constants - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//BDaySchedule
