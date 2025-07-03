/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.point;

import java.util.logging.Logger;
import javax.baja.driver.BDeviceNetwork;
import javax.baja.naming.SlotPath;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;

/**
 * Tuning is the support class used by BITunables.  The
 * parent BITunable is required to make callbacks to this class
 * under specific conditions.  Refer to BITunable for details.
 *
 * @author    Brian Frank
 * @creation  23 Jun 04
 * @version   $Revision: 20$ $Date: 3/2/06 9:23:59 AM EST$
 * @since     Baja 1.0
 */
public class Tuning
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a BTuning with the specified BITunable parent.
   */
  public Tuning(BITunable parent)
  {
    this.parent = parent;
    state(STOP);
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the parent network.
   */
  public final BDeviceNetwork getNetwork()
  {
    BComplex parent = (BComplex)this.parent;
    if (parent instanceof BProxyExt)
    {
      return ((BProxyExt)parent).getNetwork();
    }
    else
    {
      while(parent != null)
      {
        if (parent instanceof BDeviceNetwork) return (BDeviceNetwork)parent;
        parent = parent.getParent();
      }
      return null;
    }
  }

  /**
   * Get the BTuningPolicyMap on the parent network.
   */
  public final BTuningPolicyMap getPolicyMap()
  {
    BTuningPolicyMap map = (BTuningPolicyMap)getNetwork().get("tuningPolicies");
    if (map != null) return map;
    throw new IllegalStateException("Network missing tuningPolicies property");
  }

  /**
   * Get the BTuningPolicy configured by policyName.  If the
   * policyName doesn't map to a valid policy then log a warning
   * and use the defaultPolicy.
   */
  public BTuningPolicy getPolicy()
  {
    String currentPolicyName = parent.getTuningPolicyName();
    if (cachedPolicy == null || cachedPolicyName != currentPolicyName)
    {
      BTuningPolicyMap map = getPolicyMap();
      cachedPolicyName = currentPolicyName;
      BValue x = map.get(cachedPolicyName);
      if (x instanceof BTuningPolicy)
      {
        cachedPolicy  = (BTuningPolicy)x;
      }
      else
      {
        log.warning("TuningPolicy not found: " + cachedPolicyName);
        cachedPolicy = map.getDefaultPolicy();
      }
    }
    return cachedPolicy;
  }

  /**
   * Get the parent ITunable.
   */
  public final BITunable getTunable()
  {
    return parent;
  }

  /**
   * Get the ticks of the last successful read or 0 if
   * no reads have occured yet.
   */
  public final long getLastReadTicks()
  {
    return readTicks;
  }

  /**
   * Get the ticks of the last successful write or 0 if
   * no writes have occured yet.
   */
  public final long getLastWriteTicks()
  {
    return writeTicks;
  }

  /**
   * Convenience for translating getLastReadTicks() into a BAbsTime.
   * Return BAbsTime.NULL if no reads have occurred yet.
   */
  public final BAbsTime getLastReadTime()
  {
    if (readTicks == 0) return BAbsTime.NULL;
    return BAbsTime.make(System.currentTimeMillis() - (Clock.ticks() - readTicks));
  }

  /**
   * Convenience for translating getLastWriteTicks() into a BAbsTime.
   * Return BAbsTime.NULL if no writes have occurred yet.
   */
  public final BAbsTime getLastWriteTime()
  {
    if (writeTicks == 0) return BAbsTime.NULL;
    return BAbsTime.make(System.currentTimeMillis() - (Clock.ticks() - writeTicks));
  }

  /**
   * To string.
   */
  public String toString(Context cx)
  {
    String name = parent.getTuningPolicyName();
    if (name.equals("defaultPolicy"))
      return BTuningPolicyMap.defaultPolicy.getDefaultDisplayName(cx);
    else
      return SlotPath.unescape(name);
  }

////////////////////////////////////////////////////////////////
// Callbacks from ITunable
////////////////////////////////////////////////////////////////

  /**
   * The ITunable should call this method whenever it performs
   * a state transition: started, atSteadyState, stopped, status
   * change, subscribed, or unsubscribed.
   */
  public void transition()
  {
    // check to make sure we are registered
    // for background processing
    if (processId() == 0) registerForProcessing();

    BITunable tunable = getTunable();
    boolean callSub = false, callUnsub = false;

    synchronized(this)
    {
      // compute new state
      int newState = toState(tunable);

      // if state hasn't changed then bail
      int oldState = this.state();
      if (newState == oldState) return;
      this.state(newState);

      // check transitions which require a write
      if (isOperational(newState) && tunable.getMode().isWrite())
      {
        // check if we should issue an write; if so, then schedule a
        // write in a few seconds (don't do it immediately in case we
        // need to let the engine run a couple cycles)
        BTuningPolicy policy = getPolicy();

        if (isDownToUp(oldState, newState) && policy.getWriteOnUp())
        {
          writeDesiredTicks = Clock.ticks() + writeOnDelay;
          writeDesiredContext = BTuningPolicy.writeOnUpContext;
        }

        else if (isDisabledToEnabled(oldState, newState) && policy.getWriteOnEnabled())
        {
          writeDesiredTicks = Clock.ticks() + writeOnDelay;
          writeDesiredContext = BTuningPolicy.writeOnEnabledContext;
        }

        else if (isStopToStart(oldState, newState) && policy.getWriteOnStart())
        {
          writeDesiredTicks = Clock.ticks() + writeOnDelay;
          writeDesiredContext = BTuningPolicy.writeOnStartContext;
        }

      }

      // check transitions which require sub/unsub (note
      // we do the actual callbacks outside of the lock)
      boolean oldSub = this.subscribed();
      boolean newSub = shouldBeSubscribed(newState);
      if (oldSub != newSub)
      {
        this.subscribed(newSub);
        if (newSub)
        {
          callSub = true;
        }
        else
        {
          callUnsub = true;
        }
      }
    }

    // do these callbacks outside of the lock
    if (callSub) readSubscribed();
    if (callUnsub) readUnsubscribed();
  }

  /**
   * This method is called when a write is desired.  This method
   * should be called whenever the value to be written is changed.
   */
  public void writeDesired()
  {
    synchronized(this)
    {
      // if not enabled, just bail
      if (!isOperational(state())) return;

      // if minWriteTime is enabled (non-zero) then
      // just mark our desired write ticks and let
      // the background processing thread determine
      // when to do the actual write
      long minWrite = getPolicy().getMinWriteTime().getMillis();
      if (minWrite > 0)
      {
        // if we already have marked a write
        // desired, then use the first time
        if (writeDesiredTicks < 0) writeDesiredTicks = Clock.ticks();
        return;
      }
    }

    // write immediately
    write(null);
  }

  /**
   * The ITunable should call this method when a read succeeds.
   */
  public void readOk()
  {
    synchronized(this)
    {
      readTicks = Clock.ticks();
    }
  }

  /**
   * The ITunable should call this method when a read fails.
   */
  public void readFail()
  {
  }

  /**
   * The ITunable should call this method when a write succeeds.
   */
  public void writeOk()
  {
    synchronized(this)
    {
      writePending(false);
      writeTicks = Clock.ticks();
    }
  }

  /**
   * The ITunable should call this method when a write fails.
   */
  public void writeFail()
  {
    synchronized(this)
    {
      writePending(false);
    }
  }

////////////////////////////////////////////////////////////////
// Callbacks to ITunable
////////////////////////////////////////////////////////////////

  /**
   * Invoke ITuanble.write() callback.
   */
  void write(Context cx)
  {
    BITunable tunable = getTunable();

    // lock and check/setup state
    synchronized(this)
    {
      // never call write() if
      //  - write is already pending
      //  - the state is unoperational
      //  - the point is not writable
      if (writePending() || !isOperational(state()) || !tunable.getMode().isWrite())
        return;

      // setup variables to start write
      writePending(true);
      writeDesiredTicks = -1;
      writeDesiredContext = null;
    }


    // invoke callback outside of lock
    try
    {
      // invoke ITunable.write() callback
      boolean pending = tunable.write(cx);

      // if callback reported not pending, then clear pending state
      if (!pending)
      {
        synchronized(this) { writePending(false); }
      }
    }
    catch(Throwable e)
    {
      e.printStackTrace();

      // if write() throws an exception we assume that it
      // isn't pending and we explictly call writeFail
      writePending(false);
      tunable.writeFail("write(): " + e);
    }

  }

  /**
   * Invoke ITuanble.readSubscribed() callback.
   */
  void readSubscribed()
  {
    try
    {
      getTunable().readSubscribed(null);
    }
    catch(Throwable e)
    {
      e.printStackTrace();

      // if readSubscribed() throws an exception, we
      // assume something is really wrong and explicitly
      // mark the point as read fault
      getTunable().readFail("readSubscribed(): " + e);
    }
  }

  /**
   * Invoke ITuanble.readUnsubscribed() callback.
   */
  void readUnsubscribed()
  {
    try
    {
      getTunable().readUnsubscribed(null);
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Invoke ITuanble.stale() callback.
   */
  void stale()
  {
    try
    {
      getTunable().setStale(true, null);
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }
  }

////////////////////////////////////////////////////////////////
// Background Processing
////////////////////////////////////////////////////////////////

  /**
   * Register this instance with the TuningPolicyMap so
   * that we get our background process callback.
   */
  void registerForProcessing()
  {
    try
    {
      BTuningPolicyMap map = getPolicyMap();
      if (map != null) processId(map.register(this));
    }
    catch(Exception e)
    {
      // just ignore
    }
  }

  /**
   * Do background processing.  Return false
   * if I should no longer be registered.
   */
  boolean process()
  {
    // check that I am still valid, if not then unregister
    // myself so that I am not a memory leak
    if (!((BComponent)parent).isMounted())
      return false;

    BTuningPolicy policy = getPolicy();
    long minWrite  = policy.getMinWriteTime().getMillis();
    long maxWrite  = policy.getMaxWriteTime().getMillis();
    long staleTime = policy.getStaleTime().getMillis();
    long now = Clock.ticks();

    // figure out if we need to write inside locked section
    boolean write = false;
    Context writeContext = null;
    synchronized(this)
    {
      // check if a write is desired
      if (writeDesiredTicks != -1 && writeDesiredTicks <= now)
      {
        // check if our min write time has elapsed
        if (now - writeTicks >= minWrite)
        {
          write = true;
          writeContext = writeDesiredContext;
        }
      }

      // check for max write
      else if (maxWrite > 0 && now - writeTicks >= maxWrite)
      {
        write = true;
        writeContext = BTuningPolicy.maxWriteTimeContext;
      }
    }

    // do write outside of lock
    if (write) write(writeContext);

    // check for stale (do this outside lock since it
    // often results in property changes and all sorts of
    // cascading locks)
    if (staleTime > 0)
    {
      // if write only point, then use last writeOk time
      // to check stale, otherwise use last readOk time
      long okTicks;
      if (parent.getMode().isWriteonly())
        okTicks = writeTicks;
      else
        okTicks = readTicks;

      if (now - okTicks > staleTime)
        stale();
    }

    // still alive and kicking
    return true;
  }

////////////////////////////////////////////////////////////////
// State Utils
////////////////////////////////////////////////////////////////

  /**
   * Compute the current state bits of the tunable.
   */
  static int toState(BITunable tunable)
  {
    BStatus status = tunable.getStatus();

    boolean fatal      = tunable.isFatalFault();
    boolean down       = status.isDown();
    boolean disabled   = status.isDisabled();
    boolean stop       = !tunable.isRunning() || !Sys.atSteadyState();
    boolean desiredSub = tunable.isSubscribedDesired();

    int state = 0;
    state = set(state, FATAL, fatal);
    state = set(state, DOWN,  down);
    state = set(state, DISABLED, disabled);
    state = set(state, STOP,  stop);
    state = set(state, DESIRED_SUB, desiredSub);
    return state;
  }

  /**
   * Return if the specified bit is set.
   */
  static boolean get(int state, int bit)
  {
    return (state & bit) != 0;
  }

  /**
   * Set or clear the specified bit.
   */
  static int set(int state, int bit, boolean value)
  {
    if (value) return state |= bit;
    else return state &= ~bit;
  }

  /**
   * Return false if any state bits indicate a short circuit
   * of all reads and writes (fatal, down, out, or stopped).
   */
  static boolean isOperational(int state)
  {
    return (state & UNOPERATIONAL) == 0;
  }

  /**
   * Did we transitions from down to up?
   */
  static boolean isDownToUp(int old, int cur)
  {
    return get(old, DOWN) && !get(cur, DOWN);
  }

  /**
   * Did we transitions from disabled to enabled?
   */
  static boolean isDisabledToEnabled(int old, int cur)
  {
    return get(old, DISABLED) && !get(cur, DISABLED);
  }

  /**
   * Did we transitions from stopped to running?
   */
  static boolean isStopToStart(int old, int cur)
  {
    return get(old, STOP) && !get(cur, STOP);
  }

  /**
   * Return if the state bits indicate if the tunable
   * should be read subscribed - all the bad bits should
   * be clear and only the desired sub bit set.
   */
  static boolean shouldBeSubscribed(int state)
  {
    return state == DESIRED_SUB;
  }

  /**
   * Convert to debug string.
   */
  static String toString(int state)
  {
    StringBuilder s = new StringBuilder();
    if (get(state, FATAL))       s.append("fatal ");
    if (get(state, DOWN))        s.append("down ");
    if (get(state, DISABLED))    s.append("disabled ");
    if (get(state, STOP))        s.append("stop ");
    if (get(state, DESIRED_SUB)) s.append("desiredSub ");
    return "{" + s.toString().trim() + "}";
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("wrench.png");

////////////////////////////////////////////////////////////////
// Packed
////////////////////////////////////////////////////////////////

  //
  // To save memory space, we pack many discrete fields into
  // a single 32-bit int called "packed".  The bottom byte is
  // used to store various flags, and the top three bytes the
  // processId (which gives us 16.8 millions processIds):
  //   - state:        bottom five bits is used to store state bits
  //   - subscribed:   sixth bit used to store subscribed flag
  //   - writePending: seventh bit used to store store writePending flag
  //   - processId:    top three bytes, where 0 means uninitialized
  //

  static final int FATAL         = 0x01;  // in fatal fault
  static final int DOWN          = 0x02;  // status.down
  static final int DISABLED      = 0x04;  // status.disable
  static final int STOP          = 0x08;  // !isRunning()
  static final int DESIRED_SUB   = 0x10;  // isSubscribedDesired
  static final int UNOPERATIONAL = FATAL | DOWN | DISABLED | STOP;
  static final int STATE         = FATAL | DOWN | DISABLED | STOP | DESIRED_SUB;

  static final int SUBSCRIBED    = 0x20;  // readSub until readUnsub
  static final int WRITE_PENDING = 0x40;  // have I already called write

  final int state() { return packed & STATE; }
  final void state(int state) { packed = (packed & ~STATE) | state; }

  final boolean subscribed() { return (packed & SUBSCRIBED) != 0; }
  final void subscribed(boolean b) { if (b) packed |= SUBSCRIBED; else packed &= ~SUBSCRIBED; }

  final boolean writePending() { return (packed & WRITE_PENDING) != 0; }
  final void writePending(boolean b) { if (b) packed |= WRITE_PENDING; else packed &= ~WRITE_PENDING; }

  final int processId() { return (packed >> 8) & 0xFFFFFF; }
  final void processId(int id)
  {
    if (id > 0xFFFFFF) throw new IllegalStateException();
    packed = (packed & 0xFF) | (id << 8);
  }

  /* packed test code
  public static void main(String[] args)
  {
    Tuning t = new Tuning(null);
    t.state(FATAL); verify(t.state() == FATAL);
    t.state(DOWN);  verify(t.state() == DOWN);
    t.state(DISABLED); verify(t.state() == DISABLED);
    t.processId(1); verify(t.state() == DISABLED); verify(t.processId() == 1);
    t.processId(0x700000); verify(t.state() == DISABLED); verify(t.processId() == 0x700000);
    t.writePending(true); verify(t.writePending()); verify(t.state() == DISABLED); verify(t.processId() == 0x700000);
    t.subscribed(true); verify(t.subscribed()); verify(t.writePending()); verify(t.state() == DISABLED); verify(t.processId() == 0x700000);

    t.state(FATAL | DOWN | STOP);
    t.processId(8400000);
    verify(t.subscribed());
    verify(t.writePending());
    verify(t.state() == (FATAL | DOWN | STOP));
    verify(t.processId() == 8400000);

    t.processId(0xFFFFFF);
    t.subscribed(false);
    verify(t.processId() == 0xFFFFFF);
    verify(!t.subscribed());
    verify(t.writePending());
    verify(t.state() == (FATAL | DOWN | STOP));

    System.out.println("ok");

    t.processId(0x1000000);
  }
  static void verify(boolean v) { if (!v) throw new IllegalStateException(); }
  */
////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////
  public void spy(SpyWriter out)
    throws Exception
  {
    out.startProps();
    out.trTitle("Tuning", 2);
    if(parent instanceof BComplex) out.prop("parent", ((BComplex)parent).getDisplayName(null));
    out.prop("parent.isRunning", parent.isRunning());
    out.prop("parent.status", parent.getStatus());
    out.prop("parent.isSubscribedDesired", parent.isSubscribedDesired());
    out.prop("parent.mode", parent.getMode());
    out.prop("cachedPolicyName", cachedPolicyName);
    out.prop("state", toString(packed));
    out.prop("isOperational", isOperational(packed));
    out.prop("subscribed", subscribed());
    out.prop("writePending", writePending());
    out.prop("processId", processId());
    out.prop("readTicks", timestr(readTicks));
    out.prop("writeTicks", timestr(writeTicks));
    out.prop("writeDesiredTicks", timestr(writeDesiredTicks));
    out.prop("writeDesiredContext", writeDesiredContext);
    out.endProps();
  }

  static String timestr(long ticks)
  {
    if (ticks == 0) return "null";
    long now = Clock.ticks();
    long diff = now-ticks;
    if (diff < 1000) return ""+diff+"ms";
    return (diff/1000)+"sec";
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final Logger log = Logger.getLogger("driver.tuning");

  // configurable by test code
  static long writeOnDelay = 5000;

  BITunable parent;            // parent BITunable
  String cachedPolicyName;     // used in case policyName changes
  BTuningPolicy cachedPolicy;  // cached TuningPolicy
  int packed;                  // packed = state + subscribed + writePending + processId
  long readTicks;              // last readOk
  long writeTicks;             // last writeOk
  long writeDesiredTicks = -1; // time write was request or -1
  Context writeDesiredContext; // context for desired write

}


