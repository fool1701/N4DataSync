/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.sys.*;

import javax.baja.nre.util.Array;
import javax.baja.spy.SpyWriter;

/**
 * LocalBacnetPoll
 * This base class provides a mechanism for performing local polls of BACnet
 * properties from BACnet export objects in Niagara's export table.  Specific
 * implementations will need to provide customization of how the polling is
 * done for their needs.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation Nov 11, 2008
 * @since NiagaraAX 3.5
 */
abstract public class LocalBacnetPoll
  implements Runnable
{
  protected LocalBacnetPoll()
  {
  }

  /**
   * Poll service execution engine.
   */
  public void run()
  {
    while (alive)
    {
      //Avoid the initial subscription by skipping a poll
      //time at the beginning. The initial notification
      //will be sent by BBacnetPointDescriptor.startCovTimer()
      long now = Clock.ticks();
      long nextPollTime = now + getPollRate().getMillis();
      long sleepTime = nextPollTime - now;
      if (sleepTime > 0)
      {
        try
        {
          Thread.sleep(sleepTime);
        }
        catch (InterruptedException e)
        {
        }
      }

      try
      {
        // Note that this is unsynchronized.
        Iterator<BObject> it = subs.iterator();
        while (it.hasNext())
        {
          BObject o = it.next();
          if ((o != null) && o.getType().is(getPolledType()))
            if (!poll(o))
              subs.remove(o);
        }

      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, "Exception occurred in LocalBacnetPoll runnable", e);
      }
    }
  }

  /**
   * Poll an object in the local station.
   *
   * @param o
   * @return true if ok, or false if this item should be removed from the list
   */
  abstract protected boolean poll(BObject o)
    throws Exception;

  /**
   * Get the relevant poll rate for this poll thread.
   *
   * @return BRelTime
   */
  abstract protected BRelTime getPollRate();

  /**
   * Get the thread name for this poll thread.
   *
   * @return threadName
   */
  abstract protected String getThreadName();

  /**
   * Get the Niagara Type of the objects that are being polled
   * by this poll thread.
   *
   * @return polledType
   */
  abstract protected Type getPolledType();

  /**
   * Subscribe an object to the poll service.
   *
   * @param o
   */
  public synchronized void subscribe(BObject o)
  {
    if (o == null) return;
    if (!o.getType().is(getPolledType()))
      throw new IllegalArgumentException("wrong type " + o.getType() + " for local poll subscribe (" + getPolledType() + ")");
    subs.add(o);
    if (!alive)
      startThread();
  }

  /**
   * Unsubscribe an object from the poll service.
   *
   * @param o
   */
  public synchronized void unsubscribe(BObject o)
  {
    if (o == null) return;
    if (!o.getType().is(getPolledType()))
      throw new IllegalArgumentException("wrong type " + o.getType() + " for local poll unsubscribe (" + getPolledType() + ")");
    subs.remove(o);
    if (subs.size() == 0)
      stopThread();
  }

  /**
   * Start the poll thread.
   */
  private void startThread()
  {
    alive = true;
    thread = new Thread(this, getThreadName());
    thread.start();
  }

  /**
   * Stop the poll thread.
   */
  private void stopThread()
  {
    alive = false;
    thread.interrupt();
    thread = null;
  }


////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out)
  {
    out.prop("alive", alive);
    out.trTitle(getThreadName(), 2);
    out.prop("subs", subs.size());
    Iterator<BObject> it = subs.iterator();
    int i = 0;
    while (it.hasNext())
      out.prop("  subs[" + (i++) + "]", it.next());
  }


////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  private boolean alive = false;
  private Array<BObject> subs = new Array<>(BObject.class);
  private Thread thread = null;
  private static final Logger logger = Logger.getLogger("bacnet.util");

}
