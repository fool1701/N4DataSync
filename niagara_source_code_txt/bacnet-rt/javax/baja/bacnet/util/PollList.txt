/**
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.nre.util.Array;

/**
 * PollList.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 11 Oct 2006
 * @since Niagara 3.2
 */
public class PollList
{
  private PollList()// {}
  {
  }

  public PollList(PollListEntry ple)
  {
    addressHash = ple.getAddressHash();
    dataSize = ple.getDataSize();
    device = ple.getDevice();
    add(ple);
  }

  public synchronized void add(PollListEntry ple)
  {
    if (ple == null) return;
    if (ple.getDevice() != device)
      throw new IllegalArgumentException("PollList.add:ple device (" + ple.getDevice() + ") != pl device (" + device + ")");

    ple.setPollList(this);
    dataSize += ple.getDataSize();

    Iterator<PollListEntry> it = entries.iterator();
    boolean added = false;
    int hc = ple.getObjectId().hashCode();
    int ndx = 0;
    while (it.hasNext())
    {
      if (hc == it.next().getObjectId().hashCode())
      {
        entries.add(ndx, ple);
        added = true;
        break;
      }
      ndx++;
    }
    if (!added)
    {
      entries.add(ple);
      dataSize += OBJECT_ID_SIZE;
    }
  }

  public synchronized boolean remove(PollListEntry ple)
  {
    if (ple == null) return false;
    if (ple.getDevice() != device)
      throw new IllegalArgumentException("PollList.remove:ple device (" + ple.getDevice() + ") != pl device (" + device + ")");

    int hc = ple.getObjectId().hashCode();
    int plen = entries.size();
    int ndx = entries.indexOf(ple);

    if (ndx >= 0)
    {
      entries.remove(ndx);
      dataSize -= ple.getDataSize();
      boolean foundAnother = false;
      if (ndx > 0)
      {
        if (entries.get(ndx - 1).getObjectId().hashCode() == hc)
        {
          foundAnother = true;
        }
      }
      if (!foundAnother && ndx < (plen - 1))
      {
        // want to get the entry at ndx+1, but since we removed the entry at ndx,
        // the [ndx+1] entry is now the [ndx] entry.
        if (entries.get(ndx).getObjectId().hashCode() == hc)
        {
          foundAnother = true;
        }
      }
      if (!foundAnother)
        dataSize -= OBJECT_ID_SIZE;
      return true;
    }
    else
      return false;
  }

  /**
   * @return the number of entries in this list.
   * Is this still needed?
   */
  public synchronized int size()
  {
    return entries.size();
  }

  /**
   * @return the address
   */
  public final int getAddressHash()
  {
    return addressHash;
  }

  /**
   * @return the poll list's current expected data size
   */
  public final int getDataSize()
  {
    return dataSize;
  }

  /**
   * @return the device
   */
  public final BBacnetDevice getDevice()
  {
    return device;
  }

  /**
   * Get the entries in this list as an array.
   *
   * @return array of pollListEntry
   */
  public synchronized PollListEntry[] getPollEntries()
  {
    return entries.trim();
  }

  public synchronized final boolean contains(PollListEntry ple)
  {
    return entries.contains(ple);
  }

  public String toString()
  {
    return string(false);
  }

  public String debug()
  {
    return string(true);
  }

  private String string(boolean debug)
  {
    StringBuilder sb = new StringBuilder(pollCount.get() + " PollList [");
    if (device != null)
    {
      sb.append(device.getName()).append(' ')
        .append(device.getObjectId()).append(' ')
        .append(device.getAddress());
    }
    sb.append("] ").append(Integer.toHexString(addressHash)).append(" {").append(dataSize).append("} ");
    sb.append(isPolling() ? "P" : "-");
    sb.append(isDone() ? "D " : "- ");
    sb.append(" [").append(getFailedCount()).append("] ");

    int sz = entries.size();
    sb.append(sz).append((sz == 1) ? " PLE" : " PLEs");
    if (debug)
    {
      sb.append("\n");
      Iterator<PollListEntry> it = entries.iterator();
      while (it.hasNext())
        sb.append("  " + it.next().debugString() + "\n");
    }

    return sb.toString();
  }

  public synchronized int getPollFrequency()  // is synch needed?
  {
    if (entries.size() > 0)
      return entries.get(0).getPollable().getPollFrequency().getOrdinal();
    return -1;
  }

  public void setIsPolling(boolean polling)
  {
    this.polling = polling;
  }

  public boolean isPolling()
  {
    return polling;
  }

  public void setDone(boolean done)
  {
    this.done = done;
  }

  public boolean isDone()
  {
    return done;
  }

  public long getSleep()
  {
    return sleep;
  }

  public void setSleep(long sleep)
  {
    this.sleep = sleep;
  }

  public int getFailedCount()
  {
    return failedCount.get();
  }

  public void incrementFailedCount()
  {
    failedCount.getAndIncrement();
  }

  public void resetFailedCount()
  {
    failedCount.set(0);
  }

  private final AtomicInteger failedCount = new AtomicInteger(0);
  public final AtomicInteger pollCount = new AtomicInteger(0);
  int addressHash = 0;
  int dataSize = 0;
  BBacnetDevice device = null;
  Array<PollListEntry> entries = new Array<>(PollListEntry.class);
  private volatile boolean polling = false;
  private volatile boolean done = false;
  private long sleep = 0;
  private static final int OBJECT_ID_SIZE = 5;
}
