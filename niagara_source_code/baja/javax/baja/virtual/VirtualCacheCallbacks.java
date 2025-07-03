/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.virtual;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.SortUtil;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;

/**
 * VirtualCacheCallbacks provides a BVirtualComponentSpace the hooks to
 * manage how virtual components are cached in the space.
 *
 * @author    Scott Hoye
 * @creation  06 Nov 06
 * @version   $Revision: 13$ $Date: 8/5/10 4:35:17 PM EDT$
 * @since     Niagara 3.2
 */
public class VirtualCacheCallbacks
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor specifies the virtual component space.
   */
  public VirtualCacheCallbacks(BVirtualComponentSpace space)
  {
    this.space = space;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Starts the execution of this virtual cache callbacks.
   * This method is called by the virtual component space.
   */
  public synchronized void start()
  {
    // First check to see if we need to initialize and start the
    // virtual threshold checking thread
    if (vThresholdThread == null)
    {
      vThresholdThread = new VirtualThresholdThread();
      vThresholdThread.start();
      log.fine("Virtual:Threshold Thread started");
    }
    vThresholdThread.register(space, this);

    // Now assign this virtual space to a virtual cleanup thread
    // from the available thread pool.
    // What we want to do here is spread out the management
    // of the virtual spaces among the thread pool as evenly
    // as possible.  First we want to try to fill each thread
    // to the optimum level, and then if that level is exceeded
    // for each thread, then we want to spread the rest evenly
    // over the thread pool.
    int currentMinSize = Integer.MAX_VALUE;
    int minIdx = 0;
    for (int i = 0; i < THREAD_POOL_SIZE; i++)
    {
      if (vcThreadPool[i] == null)
      {
        vcThreadPool[i] = new VirtualCleanupThread(i);
        vcThreadPool[i].start();
        log.fine("Virtual:Cleanup"+i+" Thread started");
      }

      int size = vcThreadPool[i].size();
      if (size < SPACES_PER_THREAD)
      {
        vcThreadPool[i].register(space, this);
        return;
      }
      else if (size < currentMinSize)
      {
        currentMinSize = size;
        minIdx = i;
      }
    }

    // if we got here, then all threads are full,
    // so assign to the least full thread
    vcThreadPool[minIdx].register(space, this);
  }

  /**
   * Stops the execution of this virtual cache callbacks.
   * This method is called by the virtual component space.
   */
  public synchronized void stop()
  {
    // We must find the virtual cleanup thread where the space
    // was registered and remove it from that one.
    for (int i = 0; i < THREAD_POOL_SIZE; i++)
    {
      if ((vcThreadPool[i] != null) &&
          (vcThreadPool[i].contains(space)))
      {
        if (vcThreadPool[i].unregister(space) <= 0)
        { // if there are no more registered spaces on this thread,
          // then it is safe to kill the thread
          vcThreadPool[i].kill();
          vcThreadPool[i] = null;
          log.fine("Virtual:Cleanup"+i+" Thread stopped");
        }
        break;
      }
    }

    // Also unregister from the virtual threshold checking thread.
    if ((vThresholdThread != null) && (vThresholdThread.unregister(space) <= 0))
    { // if there are no more registered spaces on this thread,
      // then it is safe to kill the thread
      vThresholdThread.kill();
      vThresholdThread = null;
      log.fine("Virtual:Threshold Thread stopped");
    }
  }

  /**
   * The relative time returned from this callback specifies the maximum
   * amount of time a virtual component should remain in memory after it is
   * no longer needed (ie. it has been unsubscribed, it has no active children,
   * and it is a virtual component subject to auto removal).  If the virtual
   * component is not re-subscribed within this max cache life time, it
   * will be automatically removed from the virtual space.
   */
  public BRelTime getMaxVirtualCacheLife()
  {
    return MAX_CACHE_LIFE; // use default max cache life
  }

  /**
   * The relative time returned from this callback specifies the minimum
   * amount of time a virtual component should remain in memory after it is
   * no longer needed (ie. it has been unsubscribed, it has no active children,
   * and it is a virtual component subject to auto removal).  This minimum life is
   * only enforced if the virtual threshold limit has been exceeded for the
   * station.  If this situation exists, if the virtual
   * component is not re-subscribed within this min cache life time, it
   * may be automatically removed from the virtual space if the virtual threshold
   * limit determines that it can be removed.
   */
  public BRelTime getMinVirtualCacheLife()
  {
    return MIN_CACHE_LIFE; // use default min cache life
  }

  /**
   * Return the virtual component space for this virtual cache callbacks implementation
   */
  public BVirtualComponentSpace getSpace()
  {
    return space;
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  /**
   * Supply virtual spy info.
   */
  public void spy(SpyWriter out)
    throws Exception
  {
    out.startProps();
    out.trTitle("VirtualCacheCallbacks", 2);
    out.prop("MAX_CACHE_LIFE", MAX_CACHE_LIFE);
    out.prop("MIN_CACHE_LIFE", MIN_CACHE_LIFE);
    out.prop("THREAD_POOL_SIZE", THREAD_POOL_SIZE);
    out.prop("SPACES_PER_THREAD", SPACES_PER_THREAD);
    out.prop("VIRTUAL_THRESHOLD", VIRTUAL_THRESHOLD);
    if (VIRTUAL_THRESHOLD_SCAN_RATE <= 0L) // disabled if scan rate is zero or negative)
      out.prop("VIRTUAL_THRESHOLD_SCAN_RATE", VIRTUAL_THRESHOLD_SCAN_RATE+" (disabled)");
    else
      out.prop("VIRTUAL_THRESHOLD_SCAN_RATE", VIRTUAL_THRESHOLD_SCAN_RATE+" ("+BRelTime.make(VIRTUAL_THRESHOLD_SCAN_RATE)+")");
    out.prop("maxVirtualCacheLife", getMaxVirtualCacheLife());
    out.prop("minVirtualCacheLife", getMinVirtualCacheLife());
    out.prop("vThresholdThread", vThresholdThread.toString());
    out.prop("__totalSpacesManaged", ""+vThresholdThread.size());
    // print out scan statistics for the virtual threshold thread
    synchronized(vThresholdThread.lock)
    {
      out.prop("__numScans", ""+vThresholdThread.numScans);
      out.prop("__lastScanTicks", vThresholdThread.lastScanTicks+" ("+BRelTime.make(vThresholdThread.lastScanTicks)+")");
      out.prop("__minScanTicks", vThresholdThread.minScanTicks+" ("+BRelTime.make(vThresholdThread.minScanTicks)+")");
      out.prop("__maxScanTicks", vThresholdThread.maxScanTicks+" ("+BRelTime.make(vThresholdThread.maxScanTicks)+")");
      if (vThresholdThread.numScans > 0)
      {
        double avgScan = (double)vThresholdThread.sumScanTicks / (double)vThresholdThread.numScans;
        out.prop("__avgScanTicks", avgScan+" ("+BRelTime.make((long)avgScan)+")");
      }
    }

    for (int i = 0; i < THREAD_POOL_SIZE; i++)
    {
      if (vcThreadPool[i] != null)
      {
        out.prop("Thread '"+vcThreadPool[i].getName()+"'", "Spaces managed: "+vcThreadPool[i].size()+((vcThreadPool[i].contains(space))?" (includes this space)":""));
        StringBuilder sb = new StringBuilder();
        VirtualCacheCallbacks[] caches = null;
        synchronized(vcThreadPool[i].map)
        {
          caches = vcThreadPool[i].map.values().toArray(new VirtualCacheCallbacks[vcThreadPool[i].map.size()]);
        }
        if (caches != null)
        {
          for (int j = 0; j < caches.length; j++)
          {
            BVirtualGateway gateway = caches[j].space.getVirtualGateway();
            if (gateway != null)
            {
              if (j>0) sb.append("; ");
              sb.append(gateway.getSlotPath().getBody());
            }
          }
        }
        out.prop("__spaces", sb.toString());
        // print out scan statistics for each thread
        synchronized(vcThreadPool[i].lock)
        {
          out.prop("__numScans", ""+vcThreadPool[i].numScans);
          out.prop("__lastScanTicks", vcThreadPool[i].lastScanTicks+" ("+BRelTime.make(vcThreadPool[i].lastScanTicks)+")");
          out.prop("__minScanTicks", vcThreadPool[i].minScanTicks+" ("+BRelTime.make(vcThreadPool[i].minScanTicks)+")");
          out.prop("__maxScanTicks", vcThreadPool[i].maxScanTicks+" ("+BRelTime.make(vcThreadPool[i].maxScanTicks)+")");
          if (vcThreadPool[i].numScans > 0)
          {
            double avgScan = (double)vcThreadPool[i].sumScanTicks / (double)vcThreadPool[i].numScans;
            out.prop("__avgScanTicks", avgScan+" ("+BRelTime.make((long)avgScan)+")");
          }
          out.prop("__lastSleepTicks", vcThreadPool[i].lastSleepTicks+" ("+BRelTime.make(vcThreadPool[i].lastSleepTicks)+")");
          if (vcThreadPool[i].numScans > 0)
          {
            double avgSleep = (double)vcThreadPool[i].sumSleepTicks / (double)vcThreadPool[i].numScans;
            out.prop("__avgSleepTicks", avgSleep+" ("+BRelTime.make((long)avgSleep)+")");
          }
        }
      }
    }
    out.endProps();
  }

////////////////////////////////////////////////////////////////
// Convenience for normal virtual cleanup (max cache life)
////////////////////////////////////////////////////////////////

  /**
   * This method removes any expired Virtual Components.
   * The algorithm starts with the tree leafs and works back up the tree, checking for
   * expired virtuals (subject to the max cache life) and cleaning them up as needed.
   * The VirtualChildInfo result returns the amount of ticks until the next cleanup check
   * should occur, or -1 if the default sleep ticks should be used.  Also returns a boolean
   * indicating whether there are any active child components.
   */
  private static VirtualChildInfo cleanupExpiredVirtuals(BComponent root, BComponent comp, long cacheLife)
  {
    long shortestTicksTillCleanup = -1L;
    boolean hasActiveChildren = false;

    if (comp != null)
    {
      BComponent[] children = comp.getChildComponents();  // slot cursor method caused nullPointerExceptions!
      if (children != null)
      {
        for (int i = 0; i < children.length; i++)
        {
          BComponent kid = children[i];

          VirtualChildInfo info = cleanupExpiredVirtuals(root, kid, cacheLife);
          long childrenCleanupTicks = info.cleanupTicks;
          hasActiveChildren = hasActiveChildren | info.hasActiveChildren;

          if (childrenCleanupTicks >= 0L)
            shortestTicksTillCleanup = (shortestTicksTillCleanup < 0L)?childrenCleanupTicks:Math.min(shortestTicksTillCleanup, childrenCleanupTicks);

          if (!kid.isSubscribed())
          {
            // Check for case of a running child virtual component that is not
            // set for auto removal.
            if (//(kid.isRunning()) &&
                (kid != root) &&
                (kid instanceof BVirtualComponent) &&
                (((BVirtualComponent)kid).performAutoRemoval()) &&
                (!info.hasActiveChildren))
            {
              long expiredTicksSinceLastUnsubscribed = Clock.ticks() - ((BVirtualComponent)kid).getLastActiveTicks();
              if (expiredTicksSinceLastUnsubscribed >= cacheLife)
              {
                try
                {
                  BComponent parent = (kid.getParent().asComponent());
                  if (parent != null)
                  {
                    log.fine("Virtual cache max life expired, auto removing "+kid.getNavOrd());
                    parent.remove(kid.getPropertyInParent(), virtualRemoveContext);
                    // Remember to indicate that the parent has gone back to a
                    // partial loaded state, due to the auto removal of one of
                    // its children.
                    ((ComponentSlotMap)parent.fw(Fw.SLOT_MAP)).setBrokerPropsLoaded(false);
                  }
                }
                catch (Exception e) { } // shouldn't ever get here.
              }
              else if (expiredTicksSinceLastUnsubscribed >= 0L)
              {
                long ticksTillNextCleanup = cacheLife - expiredTicksSinceLastUnsubscribed;
                shortestTicksTillCleanup = (shortestTicksTillCleanup < 0L)?ticksTillNextCleanup:Math.min(ticksTillNextCleanup, shortestTicksTillCleanup);
                hasActiveChildren = true;
              }
            }
            else if (kid instanceof BVirtualComponent) hasActiveChildren = true;
          }
          else
            hasActiveChildren = true;
        }
      }
    }
    return new VirtualChildInfo(shortestTicksTillCleanup, hasActiveChildren);
  }

  /**
   * The VirtualCleanupThread is a thread that manages a group of virtual spaces
   * and checks for expired Virtual Components that should be auto removed.
   * For each virtual space in its managed group, the algorithm starts with the
   * tree leafs and works back up the tree, checking for
   * expired virtuals (subject to the max cache life) and cleaning them up as needed.
   */
  class VirtualCleanupThread
    extends Thread
  {

    /**
     * Specify an id in the constructor since this thread is just one in a thread pool.
     */
    VirtualCleanupThread(int id)
    {
      super("Virtual:Cleanup"+id);
    }

    @SuppressWarnings("squid:S2142")
    void kill()
    {
      synchronized(lock)
      {
        if(alive)
        {
          alive = false;
          interrupt();
          do
          {
            // We should wait and make sure the thread is stopped before returning
            try
            {
              lock.wait(TIMEOUT_ON_KILL);
            }
            catch(InterruptedException ignore) {}
          // Only execute once (while loop makes SonarQube happy)
          // since we're not concerned about spurious wakeups
          } while (false);
        }
      }
    }

    void register(BVirtualComponentSpace vSpace, VirtualCacheCallbacks vcc)
    {
      log.fine(getName()+" registering " +vSpace.getNavOrd());
      synchronized(map)
      {
        map.put(vSpace, vcc);
      }
    }

    int unregister(BVirtualComponentSpace vSpace)
    {
      log.fine(getName()+" unregistering " +vSpace.getNavOrd());
      synchronized(map)
      {
        map.remove(vSpace);
        return map.size();
      }
    }

    /**
     * Convenience method to return the current size of the virtual space map.
     *
     * @since Niagara 3.4
     */
    int size()
    {
      synchronized(map)
      {
        return map.size();
      }
    }

    /**
     * Convenience method to return whether the given virtual space
     * is already contained in the virtual space map.
     *
     * @since Niagara 3.4
     */
    boolean contains(BVirtualComponentSpace vSpace)
    {
      synchronized(map)
      {
        return map.containsKey(vSpace);
      }
    }

    @Override
    public void run()
    {
      long sleepTicks = 1000L;
      while(alive)
      {
        try
        {
          long scanStartTicks = Clock.ticks();
//          synchronized(map)
//          {
            // scan all spaces in the map, and clean up expired virtuals
            // Also keep track of the amount of time to sleep until the next
            // iteration.
            VirtualCacheCallbacks[] caches = null;
            synchronized(map)
            {
              caches = map.values().toArray(new VirtualCacheCallbacks[map.size()]);
            }
            long ticksTillNextCleanup = -1L;
            if (caches != null)
            {
              for (int i = 0; i < caches.length; i++)
              {
                BComponent root = caches[i].space.getRootComponent();
                long cacheLife = caches[i].getMaxVirtualCacheLife().getMillis();
                if (i > 0)
                  sleepTicks = Math.min(cacheLife, sleepTicks);
                else
                  sleepTicks = cacheLife;
                VirtualChildInfo info = cleanupExpiredVirtuals(root, root, cacheLife);
                long currentTicksTillNextCleanup = info.cleanupTicks;
                if (currentTicksTillNextCleanup >= 0L)
                {
                  if (ticksTillNextCleanup >= 0L)
                    ticksTillNextCleanup = Math.min(ticksTillNextCleanup, currentTicksTillNextCleanup);
                  else
                    ticksTillNextCleanup = currentTicksTillNextCleanup;
                }
                else if (!info.hasActiveChildren)
                {
                  // If the root is empty, we can reset the BrokerPropsLoaded flag
                  ((ComponentSlotMap)root.fw(Fw.SLOT_MAP)).setBrokerPropsLoaded(false);
                }
              }
            }
            if (ticksTillNextCleanup >= 0L)
              sleepTicks = ticksTillNextCleanup + 1000L; // Give myself a 1 second buffer time,
                                                         // in case there are a bunch of removes scheduled
                                                         // to happen around the same time (ie. could be off
                                                         // by a few millis
//          }

          // update spy info
          synchronized(lock)
          {
            lastScanTicks = Clock.ticks() - scanStartTicks;
            minScanTicks = Math.min(lastScanTicks, minScanTicks);
            maxScanTicks = Math.max(lastScanTicks, maxScanTicks);
            sumScanTicks += lastScanTicks;
            lastSleepTicks = sleepTicks;
            sumSleepTicks += lastSleepTicks;
            numScans++;
          }

          // sleep for a while
          Thread.sleep(sleepTicks);
        }
        catch(InterruptedException e)
        {
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
      }
      synchronized(lock)
      {
        lock.notifyAll();
      }
    }

    boolean alive = true;
    // stores the list of virtual cache callbacks to manage, keyed by virtual space
    final HashMap<BVirtualComponentSpace, VirtualCacheCallbacks> map = new HashMap<>();
    final Object lock = new Object();

    // for spy debug
    long lastScanTicks = 0L;
    long minScanTicks = Long.MAX_VALUE;
    long maxScanTicks = Long.MIN_VALUE;
    long sumScanTicks = 0L;
    long numScans = 0L;
    long lastSleepTicks = 0L;
    long sumSleepTicks = 0L;
  }

  /**
   * Stores virtual cleanup info for a virtual component's child
   */
  static class VirtualChildInfo
  {
    VirtualChildInfo(long cleanupTicks, boolean hasActiveChildren)
    {
      this.cleanupTicks = cleanupTicks;
      this.hasActiveChildren = hasActiveChildren;
    }
    long cleanupTicks;
    boolean hasActiveChildren;
  }


////////////////////////////////////////////////////////////////
// Convenience for threshold virtual cleanup (min cache life)
////////////////////////////////////////////////////////////////

  /**
   * This method maps all the virtual components by tier level into an int hash map.
   * This example shows how the tier levels should look (tier level in parenthesis).
   * Tier levels start at the leaves (tier 0) and increment up to the root.
   *
   *                                    root(3) (not included in hash map)
   *                                    /    \
   *                                   /      \
   *                                  /        \
   *                                 /          \
   *                                /            \
   *                               /              \
   *                             vA(1)            vB(2)
   *                             /  \             /  \
   *                            /    \           /    \
   *                         vC(0)  vD(0)     vE(0)   vF(1)
   *                                                  /  \
   *                                                 /    \
   *                                              vG(0)  vH(0)
   */
  private static VirtualThresholdInfo mapVirtualsIntoTierBuckets(HashMap<Integer, Array<VirtualComponentInfo>> tierBuckets, BComponent root, BComponent comp, long minLife)
  {
    int tier = 0;
    int size = 0;
    if (comp != null)
    {
      BComponent[] children = comp.getChildComponents();  // slot cursor method caused nullPointerExceptions!
      if ((children != null) && (children.length > 0))
      {
        tier++;
        int highestChildTier = 0;
        for (int i = 0; i < children.length; i++)
        {
          BComponent kid = children[i];
          VirtualThresholdInfo info = mapVirtualsIntoTierBuckets(tierBuckets, root, kid, minLife);
          highestChildTier = Math.max(highestChildTier, info.tier);
          size += info.size;
        }
        tier += highestChildTier;
      }
    }
    if ((comp instanceof BVirtualComponent) &&
        (comp != root))
    {
      BVirtualComponent vComp = (BVirtualComponent)comp;
      size++;
      Array<VirtualComponentInfo> bucket = tierBuckets.get(tier);
      if (bucket == null)
        tierBuckets.put(tier, bucket = new Array<>(VirtualComponentInfo.class));
      bucket.add(new VirtualComponentInfo(vComp, minLife));
    }
    return new VirtualThresholdInfo(tier, size);
  }

  /**
   * The VirtualThresholdThread is a thread that manages all registered virtual spaces
   * and checks to see if the global virtual threshold level has been exceeded.  If the level
   * has been exceeded, then the virtual components in any of the registered virtual spaces
   * will be checked for auto cleanup, subject to the min virtual cache life.
   */
  class VirtualThresholdThread
    extends Thread
  {
    VirtualThresholdThread()
    {
      super("Virtual:Threshold");
    }

    @SuppressWarnings("squid:S2142")
    void kill()
    {
      synchronized(lock)
      {
        if(alive)
        {
          alive = false;
          interrupt();
          do
          {
            // We should wait and make sure the thread is stopped before returning
            try
            {
              lock.wait(TIMEOUT_ON_KILL);
            }
            catch(InterruptedException ignore) {}
          // Only execute once (while loop makes SonarQube happy)
          // since we're not concerned about spurious wakeups
          } while (false);
        }
      }
    }

    void register(BVirtualComponentSpace vSpace, VirtualCacheCallbacks vcc)
    {
      log.fine(getName()+" registering " +vSpace.getNavOrd());
      synchronized(map)
      {
        map.put(vSpace, vcc);
      }
    }

    int unregister(BVirtualComponentSpace vSpace)
    {
      log.fine(getName()+" unregistering " +vSpace.getNavOrd());
      synchronized(map)
      {
        map.remove(vSpace);
        return map.size();
      }
    }

    /**
     * Convenience method to return the current size of the virtual space map.
     *
     * @since Niagara 3.4
     */
    int size()
    {
      synchronized(map)
      {
        return map.size();
      }
    }

    /**
     * Convenience method to return whether the given virtual space
     * is already contained in the virtual space map.
     *
     * @since Niagara 3.4
     */
    boolean contains(BVirtualComponentSpace vSpace)
    {
      synchronized(map)
      {
        return map.containsKey(vSpace);
      }
    }

    @Override
    public void run()
    {
      if (VIRTUAL_THRESHOLD_SCAN_RATE <= 0L) return; // disable if scan rate is zero or negative)

      while(alive)
      {
        try
        {
          long scanStartTicks = Clock.ticks();
//          synchronized(map)
//          {
            // scan all spaces in the map, and check to see if the global
            // virtual threshold level has been exceeded.  If so, clean up
            // virtuals on an oldest to youngest basis (and from tree leaf up),
            // subject to the min virtual cache life.
            VirtualCacheCallbacks[] caches = null;
            synchronized(map)
            {
              caches = map.values().toArray(new VirtualCacheCallbacks[map.size()]);
            }
            if (caches != null)
            {
              // First hash all virtuals from all spaces into the appropriate buckets
              // based on tier level
              HashMap<Integer, Array<VirtualComponentInfo>> tierBuckets = new HashMap<>();
              int maxTier = -1;
              int size = 0;
              for (int i = 0; i < caches.length; i++)
              {
                BComponent root = caches[i].space.getRootComponent();
                VirtualThresholdInfo info = mapVirtualsIntoTierBuckets(tierBuckets, root, root, caches[i].getMinVirtualCacheLife().getMillis());
                size += info.size;
                maxTier = Math.max(info.tier, maxTier);
              }

              // Now check to see if we have exceeded the global virtual threshold limit
              int numToClean = size - VIRTUAL_THRESHOLD;
              if ((numToClean > 0) && (maxTier >= 0))
              {
                // We have exceeded the global virtual threshold limit, so
                // cleanup by tier level (ie start with leaves and work back up)
                // cleanup by oldest unsubscribed to newest, subject to the min virtual cache life.
                for (int i = 0; i < maxTier; i++) // use '<' here since maxTier is for root component, which shouldn't be in hash map
                {
                  Array<VirtualComponentInfo> bucket = tierBuckets.get(i);
                  VirtualComponentInfo[] vInfo = bucket.trim();
                  int len = vInfo.length;
                  Long[] lastActiveTicks = new Long[len];

                  // First loop through all entries for the given tier level, and determine
                  // which virtual components are ripe for auto removal (keep track of their
                  // last active ticks)
                  for (int j = 0; j < len; j++)
                  {
                    long ticks = -1L; // -1 indicates the component shouldn't be removed

                    // if no children and unsubscribed and performAutoRemoval enabled
                    // and the min life has expired, then give a positive lastActiveTicks.
                    if (!vInfo[j].vComp.isSubscribed() &&
                        vInfo[j].vComp.performAutoRemoval())
                    {
                      long expiredTicksSinceLastUnsubscribed = Clock.ticks() - vInfo[j].vComp.getLastActiveTicks();
                      if (expiredTicksSinceLastUnsubscribed >= vInfo[j].minLife)
                      {
                        BComponent[] children = vInfo[j].vComp.getChildComponents();
                        if ((children == null) || (children.length == 0))
                          ticks = vInfo[j].vComp.getLastActiveTicks();
                      }
                    }
                    lastActiveTicks[j] = Long.valueOf(ticks);
                  }

                  // Sort in ascending order by last active ticks, so that the oldest
                  // virtuals will be removed first
                  SortUtil.sort(lastActiveTicks, vInfo);

                  // Now loop through the sorted list and remove virtuals until we have
                  // crossed back under the global virtual threshold level
                  for (int j = 0; j < vInfo.length; j++)
                  {
                    if ((lastActiveTicks[j].longValue() >= 0) && vInfo[j].vComp.isRunning())
                    { // this virtual can be removed
                      try
                      {
                        BComponent parent = (vInfo[j].vComp.getParent().asComponent());
                        if (parent != null)
                        {
                          log.fine("Virtual cache min life expired, auto removing "+vInfo[j].vComp.getNavOrd());
                          parent.remove(vInfo[j].vComp.getPropertyInParent(), virtualRemoveContext);
                          // Remember to indicate that the parent has gone back to a
                          // partial loaded state, due to the auto removal of one of
                          // its children.
                          ((ComponentSlotMap)parent.fw(Fw.SLOT_MAP)).setBrokerPropsLoaded(false);
                          numToClean--;
                          if (numToClean <= 0) break; // We've done enough cleanup, so get out

                          // If the parent is now a leaf due to removing a child virtual, then add
                          // it to this bucket for cleanup
                          BComponent[] children = parent.getChildComponents();
                          if ((parent instanceof BVirtualComponent) &&
                              !parent.isSubscribed() &&
                              ((BVirtualComponent)parent).performAutoRemoval() &&
                              ((children == null) || (children.length == 0)))
                          {
                            // Be sure to check the min life of the parent first
                            long expiredTicksSinceLastUnsubscribed = Clock.ticks() - ((BVirtualComponent)parent).getLastActiveTicks();
                            if (expiredTicksSinceLastUnsubscribed >= vInfo[j].minLife)
                            {
                              // This parent has been determined to be suitable for auto
                              // cleanup, so insert it into this bucket's array
                              // based on its lastActiveTicks order
                              int insertIdx = vInfo.length;
                              int newSize = insertIdx + 1;
                              long ticks = ((BVirtualComponent)parent).getLastActiveTicks();

                              // Find the index where it should be inserted into the list
                              for (int k = j+1; k < vInfo.length; k++)
                              {
                                if (lastActiveTicks[k].longValue() > ticks)
                                { // found it
                                  insertIdx = k;
                                  break;
                                }
                              }

                              // Insert it into the list
                              VirtualComponentInfo[] tmpInfo = new VirtualComponentInfo[newSize];
                              Long[] tmpTicks = new Long[newSize];
                              for (int k = 0; k < insertIdx; k++)
                              {
                                tmpInfo[k] = vInfo[k];
                                tmpTicks[k] = lastActiveTicks[k];
                              }
                              tmpInfo[insertIdx] = new VirtualComponentInfo(((BVirtualComponent)parent), vInfo[j].minLife);
                              tmpTicks[insertIdx] = Long.valueOf(ticks);
                              for (int k = insertIdx; k < (newSize-1); k++)
                              {
                                tmpInfo[k+1] = vInfo[k];
                                tmpTicks[k+1] = lastActiveTicks[k];
                              }
                              vInfo = tmpInfo;
                              lastActiveTicks = tmpTicks;
                            }
                          }
                        }
                      }
                      catch (Exception e) { } // shouldn't ever get here.
                    }
                  }
                  if (numToClean <= 0) break; // We've done enough cleanup, so get out
                }
              }
            }
//          }

          // update spy info
          synchronized(lock)
          {
            lastScanTicks = Clock.ticks() - scanStartTicks;
            minScanTicks = Math.min(lastScanTicks, minScanTicks);
            maxScanTicks = Math.max(lastScanTicks, maxScanTicks);
            sumScanTicks += lastScanTicks;
            numScans++;
          }

          // sleep for a while
          Thread.sleep(VIRTUAL_THRESHOLD_SCAN_RATE);
        }
        catch(InterruptedException e)
        {
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
      }
      synchronized(lock)
      {
        lock.notifyAll();
      }
    }

    boolean alive = true;
    // stores the list of virtual cache callbacks to manage, keyed by virtual space
    final HashMap<BVirtualComponentSpace, VirtualCacheCallbacks> map = new HashMap<>();
    final Object lock = new Object();

    // for spy debug
    long lastScanTicks = 0L;
    long minScanTicks = Long.MAX_VALUE;
    long maxScanTicks = Long.MIN_VALUE;
    long sumScanTicks = 0L;
    long numScans = 0L;
  }

  /**
   * Stores virtual threshold info for a virtual component (tier level and size)
   */
  static class VirtualThresholdInfo
  {
    VirtualThresholdInfo(int tier, int size)
    {
      this.tier = tier;
      this.size = size;
    }
    int tier;
    int size;
  }

  /**
   * Stores virtual component info (the virtual component and its min virtual cache life)
   */
  static class VirtualComponentInfo
  {
    VirtualComponentInfo(BVirtualComponent vComp, long minLife)
    {
      this.vComp = vComp;
      this.minLife = minLife;
    }
    BVirtualComponent vComp;
    long minLife;
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  // The default virtual cache life maximum.
  // default 45 seconds (assuming touch implemented).
  public static final BRelTime MAX_CACHE_LIFE = BRelTime.make(AccessController.doPrivileged((PrivilegedAction<Long>)
    () -> Long.getLong("niagara.virtual.cache.maxLifeDefault", 45000L).longValue()));

  // The default virtual cache life minimum.
  // default 25 seconds.
  public static final BRelTime MIN_CACHE_LIFE = BRelTime.make(AccessController.doPrivileged((PrivilegedAction<Long>)
    () -> Long.getLong("niagara.virtual.cache.minLifeDefault", 25000L).longValue()));

  // The global virtual threshold limit, above which
  // virtuals will start being removed quicker as space
  // is needed (ie. uses the virtual minLife instead of maxLife).
  // default 1000 global virtual threshold level
  public static final int VIRTUAL_THRESHOLD = AccessController.doPrivileged((PrivilegedAction<Integer>)
    () -> Integer.getInteger("niagara.virtual.cache.threshold", 1000).intValue());

  // The default time (in milliseconds) in which to perform a full
  // virtual scan for threshold level checking.  A value of zero
  // disables the virtual threshold checking feature.
  public static final long VIRTUAL_THRESHOLD_SCAN_RATE = AccessController.doPrivileged((PrivilegedAction<Long>)
    () -> Long.getLong("niagara.virtual.cache.thresholdScanRate", 1000L).longValue());

  // The maximum number of worker threads that the virtual cache
  // can have in its thread pool.
  // At most, allow this many virtual cleanup threads (10 default)
  public static final int THREAD_POOL_SIZE = AccessController.doPrivileged((PrivilegedAction<Integer>)
    () -> Integer.getInteger("niagara.virtual.cache.threadPoolSize", 10).intValue());

  // The ideal number of virtual spaces managed per worker thread
  // in the virtual cache's thread pool (this limit can be exceeded
  // if all threads in the pool are already at capacity).
  // The default is 5 virtual spaces (optimum) per thread.
  public static final int SPACES_PER_THREAD = AccessController.doPrivileged((PrivilegedAction<Integer>)
    () -> Integer.getInteger("niagara.virtual.cache.spacesPerThread", 5)).intValue();

  // Timeout to wait for a thread to finish stopping upon a kill (interrupt)
  private static final long TIMEOUT_ON_KILL = 5000L;

   // shared thread pool for virtual cache life cleanup
  static VirtualCleanupThread[] vcThreadPool = new VirtualCleanupThread[THREAD_POOL_SIZE];

  // shared thread for virtual threshold checking
  static VirtualThresholdThread vThresholdThread = null;

  // context passed when a virtual cache auto removal occurs
  static Context virtualRemoveContext = BFacets.make("virtualCacheRemove", BBoolean.TRUE);

  // virtual space for this virtual cache callbacks instance
  BVirtualComponentSpace space;

  // log
  static final Logger log = Logger.getLogger("virtual.cache");
}
