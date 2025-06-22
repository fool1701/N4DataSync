/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import java.util.logging.Level;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BComponentEventMask;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Subscriber;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

/**
 * Reference to another schedule.
 * @creation Aug 2002
 * @version $Revision: 15$ $Date: 6/22/10 1:28:17 PM EDT$
 */
@NiagaraType
@NiagaraProperty(
  name = "ref",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  flags = Flags.USER_DEFINED_1
)
public class BScheduleReference
  extends BAbstractSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BScheduleReference(689831333)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ref"

  /**
   * Slot for the {@code ref} property.
   * @see #getRef
   * @see #setRef
   */
  public static final Property ref = newProperty(Flags.USER_DEFINED_1, BOrd.NULL, null);

  /**
   * Get the {@code ref} property.
   * @see #ref
   */
  public BOrd getRef() { return (BOrd)get(ref); }

  /**
   * Set the {@code ref} property.
   * @see #ref
   */
  public void setRef(BOrd v) { set(ref, v, null); }

  //endregion Property "ref"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BScheduleReference.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BScheduleReference() {}


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  @Override
  public void changed(Property p, Context c)
  {
    if (p == ref)
    {
      if (instance != null)
      {
        if (this.isRunning())
          instance.removeReference(this);
        instance = null;
        subscriber.unsubscribeAll();
        getSchedule();
      }
    }
    super.changed(p,c);
  }

  /**
   * Retrieves the referent schedule from the BScheduleService.
   */
  public BAbstractSchedule getSchedule()
  {
    BAbstractSchedule s = instance;
    boolean running = isRunning();
    try
    {
      if (s == null)
      {
        BOrd tmp = getRef();
        if (tmp.isNull())
          return null;
        if (running)
        {
          s = (BAbstractSchedule) tmp.get(this);
          s.addReference(this);
        }
        else
        {
          BAbstractSchedule root = getRootSchedule();
          BOrd refBase = (BOrd) root.get("refBase");
          if (refBase == null)
            refBase = getAbsoluteOrd();
          tmp = BOrd.make(refBase,tmp);
          s = (BAbstractSchedule) tmp.get();
          s.lease(Integer.MAX_VALUE);
        }
        instance = s;
        subscribeToCalendar();
      }
      else if (!running)
      {
        if (!s.isSubscribed())
          s.lease(Integer.MAX_VALUE);
      }
    }
    catch (Exception x)
    {
      if (!errorLogged)
      {
        log.log(Level.SEVERE, "(" + this + ") Bad Reference: " + getRef(),x);
        errorLogged = true;
      }
    }
    if ((s == null) && !getRef().equals(BOrd.NULL) && !errorLogged)
    {
      log.severe("(" + this + ") Bad Reference: " + getRef());
      errorLogged = true; //prevent a flood of error messages.
    }
    else if (s != null)
      errorLogged = false;
    return s;
  }

  @Override
  public boolean isEffective(BAbsTime at)
  {
    BAbstractSchedule s = getSchedule();
    if (s == null)
      return false;
    return s.isEffective(at);
  }

  @Override
  public BAbsTime nextEvent(BAbsTime after)
  {
    BAbstractSchedule s = getSchedule();
    if (s == null)
      return null;
    return s.nextEvent(after);
  }

  @Override
  public void started()
    throws Exception
  {
    super.started();
    if (Sys.isStationStarted())
      getSchedule();
  }

  @Override
  public void stationStarted()
    throws Exception
  {
    super.stationStarted();
    getSchedule();
  }

  @Override
  public void stopped()
    throws Exception
  {
    if (instance != null)
      instance.removeReference(this);
    instance = null;
    subscriber.unsubscribeAll();
    super.stopped();
  }

  @Override
  public String toString(Context cx)
  {
    Lexicon l = Lexicon.make(BAbstractSchedule.class);
    return l.get("type.scheduleReference") + ": " + getRef().toString(cx);
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////
  // Methods - Private and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////
  private void subscribeToCalendar()
  {
    // Unsubscribe first - we only want to be subscribed to a single calendar & its path
    subscriber.unsubscribeAll();

    // If we have a calendar instance, subscribe to it and its component ancestors
    if (instance != null)
    {
      BComplex parent = instance;

      // Go through its ancestors
      while (parent != null)
      {
        if (parent instanceof BComponent)
          subscriber.subscribe((BComponent)parent);

        parent = parent.getParent();
      }
    }
  }

  /////////////////////////////////////////////////////////////////
  // Inner Classes - in alphabetical order by class name.
  /////////////////////////////////////////////////////////////////

  class CalendarSubscriber extends Subscriber
  {
    public CalendarSubscriber()
    {
      setMask(mask);
    }

    @Override
    public void event(BComponentEvent e)
    {
      handleComponentEvent(e);
    }
  }

  void handleComponentEvent(BComponentEvent event)
  {
    // We're interested in renames, so that we can update our reference
    if (event.getId() == BComponentEvent.COMPONENT_RENAMED)
    {
      if (instance != null)
        setRef(instance.getSlotPathOrd());
    }
    else if (event.getId() == BComponentEvent.PROPERTY_REMOVED)
    {
      BValue removed = event.getValue();
      BComplex comp = instance;

      while (comp != null)
      {
        if (comp.equals(removed))
        {
          setRef(BOrd.NULL);
          comp = null;
        }
        else
          comp = comp.getParent();
      }
    }
  }
  /////////////////////////////////////////////////////////////////
  // Constants - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////
  private static final BComponentEventMask mask =
                      BComponentEventMask.make(0x01 << BComponentEvent.COMPONENT_RENAMED |
                                               0x01 << BComponentEvent.PROPERTY_REMOVED);

  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  private BAbstractSchedule instance = null;
  private boolean errorLogged = false;
  Subscriber subscriber = new CalendarSubscriber();


  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//BAbstractSchedule
