/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;

import javax.baja.io.ValueDocDecoder;
import javax.baja.naming.BLocalHost;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFloat;
import javax.baja.sys.BIService;
import javax.baja.sys.BObject;
import javax.baja.sys.BStation;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.Nre;
import com.tridium.sys.station.Station;
import com.tridium.testng.TestRunnerNg;

/**
 * BTest is the base class for all unit tests.  All tests must
 * have a public no argument constructor.  Each method which
 * begins with "test" will be invoked as a test using a fresh
 * instance.  You may use the setup() and cleanup() callbacks
 * to setup the BTest instance being used for a specific test.
 *
 * @author    Brian Frank on 3 Oct 06
 * @version   $Revision: 12$ $Date: 6/25/10 2:12:45 PM EDT$
 * @since     Niagara 3.2
 */
@NiagaraType
public abstract class BTest
  extends BObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.test.BTest(2979906276)1.0$ @*/
/* Generated Wed Jan 05 17:05:31 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  /**
   * Callback to setup an instance of test.
   */
  public void setup()
    throws Exception
  {
  }

  /**
   * Callback to cleanup an instance of test.
   */
  public void cleanup()
    throws Exception
  {
  }

  /**
   * Get the list the test methods to use for this BTest
   * class.  This list is generated using reflection by
   * finding all the methods which begin with "test".
   */
  public Method[] list()
    throws Exception
  {
    return Arrays.stream(getClass().getMethods())
                 .filter(m -> m.getName().startsWith("test"))
                 .toArray(Method[]::new);
  }

////////////////////////////////////////////////////////////////
// Verifications
////////////////////////////////////////////////////////////////

  /**
   * Verify the specified condition is true.
   */
  public void verify(boolean condition)
  {
    if (condition)
    {
      pass();
    }
    else
    {
      fail("verify(false)");
    }
  }

  /**
   * Verify the specified conition is true.
   */
  public void verify(boolean condition, String failureMessage)
  {
    if (condition)
    {
      pass();
    }
    else
    {
      fail(failureMessage);
    }
  }

  /**
   * Fail the current test.
   */
  public void fail()
  {
    fail("fail()");
  }

  /**
   * Verify that <code>a.equals(b)</code> and
   * <code>a.hashCode() == b.hashCode()</code>.  This method
   * will handle a or b being null.
   */
  public void verifyEq(Object a, Object b)
  {
    if (a == null)
    {
      if (b == null)
      {
        pass();
      }
      else
      {
        fail("null != " + b);
      }
    }
    else
    {
      if (a.equals(b))
      {
        // Make sure the hashCodes are also equivalent
        int hashA = a.hashCode();
        int hashB = b.hashCode();
        if (hashA != hashB)
        {
          fail(a + " != " + b + " (hashCodes not equal, " + hashA + " != " + hashB + ')');
        }
        else
        {
          pass();
        }
      }
      else
      {
        fail(a + " != " + b);
      }
    }
  }

  /**
   * Verify that <code>comp.compare(a, b) == 0</code>.  This method
   * will handle a or b being null.
   */
  public <T> void verifyEq(T a, T b, Comparator<T> comp)
  {
    if (a == null)
    {
      if (b == null)
      {
        pass();
      }
      else
      {
        fail("null != " + b);
      }
    }
    else
    {
      if (b != null && (comp.compare(a, b) == 0))
      {
        pass();
      }
      else
      {
        fail(a + " != " + b);
      }
    }
  }

  /**
   * Verify that <code>a.equivalent(b)</code>.  This method
   * will handle a or b being null.
   */
  public void verifyEquivalent(BObject a, BObject b)
  {
    if (a == null)
    {
      if (b == null)
      {
        pass();
      }
      else
      {
        fail("null != " + b);
      }
    }
    else
    {
      if (b != null && a.equivalent(b))
      {
        pass();
      }
      else
      {
        fail(a + " != " + b);
      }
    }
  }

  /**
   * Verify reference equality: <code>a == b</code>.
   */
  public void verifySame(Object a, Object b)
  {
    if (a == b)
    {
      pass();
    }
    else
    {
      fail(a + " !== " + b);
    }
  }

  /**
   * Verify the two arrays have the exact same contents
   * as defined by Object.equals().  This method will
   * handle a or b being null or any value within a or
   * b being null.
   */
  public void verifyValuesEq(Object[] a, Object[] b)
  {
    if (a == null || b == null)
    {
      if (a == b)
      {
        pass();
      }
      else
      {
        fail(toString(a) + " != " + toString(b));
      }
    }
    else
    {
      boolean same = true;
      if (a.length != b.length)
      {
        same = false;
      }
      else
      {
        for (int i=0; i<a.length; ++i)
        {
          if (a[i] == null || b[i] == null)
          {
            if (a[i] != b[i])
            {
              same = false; break;
            }
          }
          else
          {
            if (!a[i].equals(b[i]))
            {
              same = false; break;
            }
          }
        }
      }
      if (same)
      {
        pass();
      }
      else
      {
        fail(toString(a) + " != " + toString(b));
      }
    }
  }

  /**
   * Verify the two arrays have the exact same contents
   * as defined by reference eqality.  This method
   * will handle a or b being null.
   */
  public void verifyValuesSame(Object[] a, Object[] b)
  {
    if (a == null || b == null)
    {
      if (a == b)
      {
        pass();
      }
      else
      {
        fail(toString(a) + " !== " + toString(b));
      }
    }
    else
    {
      boolean same = true;
      if (a.length != b.length)
      {
        same = false;
      }
      else
      {
        for (int i=0; i<a.length; ++i)
        {
          if (a[i] != b[i])
          {
            same = false;
            break;
          }
        }
      }
      if (same)
      {
        pass();
      }
      else
      {
        fail(toString(a) + " !== " + toString(b));
      }
    }
  }

  /**
   * Verify the two arrays have the exact same contents
   * as defined by BComplex.equivalent().  This method will
   * handle a or b being null or any value within a or
   * b being null.
   */
  @SuppressWarnings("unused")
  public void verifyValuesEquivalent(BComplex[] a, BComplex[] b)
  {
    if (a == null || b == null)
    {
      if (a == b)
      {
        pass();
      }
      else
      {
        fail(toString(a) + " != " + toString(b));
      }
    }
    else
    {
      boolean same = true;
      if (a.length != b.length)
      {
        same = false;
      }
      else
      {
        for (int i=0; i<a.length; ++i)
        {
          if (a[i] == null || b[i] == null)
          {
            if (a[i] != b[i])
            {
              same = false;
              break;
            }
          }
          else
          {
            if (!a[i].equivalent(b[i]))
            {
              same = false;
              break;
            }
          }
        }
      }
      if (same)
      {
        pass();
      }
      else
      {
        fail(toString(a) + " != " + toString(b));
      }
    }
  }

  /**
   * Verify the two arrays have the exact same contents
   * as defined by reference eqality.  This method
   * will handle a or b being null.
   */
  public void verifyValuesSame(int[] a, int[] b)
  {
    if (a == null || b == null)
    {
      if (a == b)
      {
        pass();
      }
      else
      {
        fail(asString(a) + " !== " + asString(b));
      }
    }
    else
    {
      boolean same = true;
      if (a.length != b.length)
      {
        same = false;
      }
      else
      {
        for (int i=0; i<a.length; ++i)
        {
          if (a[i] != b[i])
          {
            same = false;
            break;
          }
        }
      }
      if (same)
      {
        pass();
      }
      else
      {
        fail(asString(a) + " !== " + asString(b));
      }
    }
  }

  /**
   * Verify boolean equality: <code>a == b</code>.
   */
  public void verifyEq(boolean a, boolean b)
  {
    if (a == b)
    {
      pass();
    }
    else
    {
      fail(a + " != " + b);
    }
  }

  /**
   * Verify int equality: <code>a == b</code>.
   */
  public void verifyEq(int a, int b)
  {
    if (a == b)
    {
      pass();
    }
    else
    {
      fail(a + " != " + b);
    }
  }

  /**
   * Verify long equality: <code>a == b</code>.
   */
  public void verifyEq(long a, long b)
  {
    if (a == b)
    {
      pass();
    }
    else
    {
      fail(a + "L != " + b + 'L');
    }
  }

  /**
   * Verify float equality: <code>BFloat.equals(a, b)</code>.
   */
  public void verifyEq(float a, float b)
  {
    if (BFloat.equals(a, b))
    {
      pass();
    }
    else
    {
      fail(a + "f != " + b + 'f');
    }
  }

  /**
   * Verify double equality: <code>BDouble.equals(a, b)</code>.
   */
  public void verifyEq(double a, double b)
  {
    if (BDouble.equals(a, b))
    {
      pass();
    }
    else
    {
      fail(a + " != " + b);
    }
  }

////////////////////////////////////////////////////////////////
// Station
////////////////////////////////////////////////////////////////

  /**
   * Create a station handler with the supplied xml definition.
   * @param bog xml string that represents a station
   * @return a station handler mounted as "local:|station:", which may used to simulate running in a
   * station environment.
   */
  public static TestStationHandler createTestStation(String bog)
    throws Exception
  {
    //System.err.println("createTestStation called");
    synchronized(testStationMonitor)
    {
      while (BTest.stationHandler != null)
      {
        testStationMonitor.wait();
      }
      BTest.stationHandler = new TestStationHandler(bog);
    }
    return BTest.stationHandler;
  }

  /**
   * Create a station handler with the xml definition in the file pointed to by supplied file ord.
   * @param ord file ord to the xml definition of a station
   * @return a station handler mounted as "local:|station:", which may used to simulate running in a
   * station environment.
   */
  public static TestStationHandler createTestStation(BOrd ord)
    throws Exception
  {
    //System.err.println("createTestStation called");
    synchronized(testStationMonitor)
    {
      while (BTest.stationHandler != null)
      {
        testStationMonitor.wait();
      }
      BTest.stationHandler = new TestStationHandler(ord);
    }
    return BTest.stationHandler;
  }

  /**
   * Creates an empty station handler.
   * @return a station handler mounted as "local:|station:", which may used to simulate running in a
   * station environment.
   */
  public static TestStationHandler createTestStation()
    throws Exception
  {
    return createTestStation("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                             "<bajaObjectGraph version=\"1.0\">\r\n" +
                             "<p h=\"1\" m=\"b=baja\" t=\"b:Station\">\r\n" +
                             "</p>\r\n" +
                             "</bajaObjectGraph>");
  }

  public static class TestStationHandler
    implements AutoCloseable
  {
    private TestStationHandler(String bog)
      throws Exception
    {
      this(new ValueDocDecoder(new ByteArrayInputStream(bog.getBytes(StandardCharsets.UTF_8))));
    }

    private TestStationHandler(BOrd ord)
      throws Exception
    {
      this(new ValueDocDecoder(ord));
    }

    private TestStationHandler(ValueDocDecoder d)
      throws Exception
    {
      // setup component space first so services get registered correctly
      BComponentSpace space = new BComponentSpace("station", null, BOrd.make("station:"));
      BLocalHost.INSTANCE.addNavChild(space);

      try
      {
        Station.space = space;

        // create a new station
        station = new BStation();
        station.setStationName("test");

        if (d != null)
        {
          BComponent graph = (BComponent)d.decodeDocument();
          if (graph instanceof BStation)
          {
            station = (BStation)graph;
          }
          else
          {
            station.add("test", graph, null);
          }
        }

        // store in hidden Station API so that
        // the baja runtime is psyched out
        Station.station = station;

        // put station into component space and mount
        space.setRootComponent(station);

        Nre.protectedStationHome = new File(Sys.getNiagaraUserHome() + File.separator + "test" + File.separator + "station");
        Nre.stationHome = new File(Nre.protectedStationHome, "shared");
      }
      catch (Exception e)
      {
        // Clear the station space from LocalHost so subsequent tests can load their own.  Release
        // station will not be called because no station handler has been constructed.
        BLocalHost.INSTANCE.removeNavChild(space);
        throw e;
      }
    }

    /**
     * Starts the instance of the test station
     */
    public void startStation()
    {
      if (station.isRunning())
      {
        return;
      }
      Nre.clearPlatform();
      Nre.loadPlatform();
      Nre.getServiceManager().startAllServices();
      station.start();
      Station.stationStarted = true;
      Station.atSteadyState = true;
    }

    /**
     * Stop the instance of the test station
     */
    public void stopStation()
    {
      if (station == null || !station.isRunning())
      {
        return;
      }
      station.stop();
      Nre.getServiceManager().stopAllServices();
      Nre.clearPlatform();
      Station.stationStarted = false;
      Station.atSteadyState = false;
    }

    /**
     * Releases the test station functionality so other tests
     * can utilize it. NOTE: You MUST call releaseStation() in
     * order to allow other tests in other modules that use
     * test stations to continue.
     */

    public void releaseStation()
    {
      stopStation();

      synchronized(testStationMonitor)
      {
        if (station != null)
        {
          BLocalHost.INSTANCE.removeNavChild(station.getSpace());

          for (BComponent service : Nre.getServiceManager().getAllServices())
          {
            Nre.getServiceManager().unregister((BComponent & BIService)service);
          }
          Nre.getServiceManager().clearAllServices();

          Station.stationStarted = false;

          if (station.getSlot("test") != null)
          {
            station.remove("test");
          }

          station = null;
          BTest.stationHandler = null;
        }

        testStationMonitor.notify();
      }
    }

    public BStation getStation()
    {
      return station;
    }

    private BStation station;

    @Override
    public void close()
    {
      releaseStation();
    }
  }

  /**
   * This method returns an empty BStation mounted as "local:|station:",
   * which may used to simulate running in a station environment.
   * Every test method gets its own fresh BStation which lasts the
   * lifetime of the test method.  The station is not started by default -
   * use <code>startTestStation()</code> and <code>stopTestStation()</code>
   * to run through station startup and shutdown.
   *
   * @deprecated Use createTestStation(...)
   */
  @Deprecated
  public BStation getTestStation()
  {
    if (station != null)
    {
      return station;
    }

    // create a new station
    station = new BStation();
    station.setStationName("test");

    // put station into component space and mount
    BComponentSpace space = new BComponentSpace("station", null, BOrd.make("station:"));
    BLocalHost.INSTANCE.addNavChild(space);
    space.setRootComponent(station);

    // store in hidden Station API so that
    // the baja runtime is psyched out
    Station.station = station;
    Station.space = space;
    Nre.protectedStationHome = new File(Sys.getNiagaraUserHome() + File.separator + "test" + File.separator + "station");
    Nre.stationHome = new File(Nre.protectedStationHome, "shared");

    return station;
  }

  /**
   * Start the test station.  Starting a station includes calling
   * serviceStarted and started, but excludes the steady state
   * callback.
   *
   * @deprecated Use a TestStation object returned from createTestStation(...)
   */
  @Deprecated
  @SuppressWarnings("deprecation")
  public void startTestStation()
  {
    BStation station = getTestStation();
    if (station.isRunning())
    {
      return;
    }
    Nre.getServiceManager().startAllServices();
    station.start();
    Station.stationStarted = true;
  }

  /**
   * Stop the test station.  Starting a station includes calling
   * serviceStopped and stopped, but leaves the station mounted
   * as currently configured.
   *
   * @deprecated Use a TestStation object returned from createTestStation(...)
   */
  @Deprecated
  @SuppressWarnings("deprecation")
  public void stopTestStation()
  {
    BStation station = getTestStation();
    if (!station.isRunning())
    {
      return;
    }
    Nre.getServiceManager().stopAllServices();
    station.stop();
    Station.stationStarted = false;
  }

  /**
   * @deprecated Use a TestStation object returned from createTestStation(...)
   */
  @Deprecated
  @SuppressWarnings("deprecation")
  public void clearTestStation()
  {
    stopTestStation();
    privateCleanup();
    station = null;
  }

  /**
   * Package private cleanup so that we don't have to rely
   * on subclasses calling super.cleanup().
   *
   * @deprecated Use a TestStation object returned from createTestStation(...)
   */
  @Deprecated
  void privateCleanup()
  {
    if (station != null)
    {
      BLocalHost.INSTANCE.removeNavChild(station.getSpace());
      Nre.getServiceManager().clearAllServices();
    }
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * Print an array to a string.
   */
  public static String toString(Object[] a)
  {
    if (a == null)
    {
      return "null";
    }
    StringBuilder s = new StringBuilder();
    s.append('[');
    for (int i=0; i<a.length; ++i)
    {
      if (i > 10)
      {
        s.append(", ...");
        break;
      }
      if (i > 0)
      {
        s.append(", ");
      }
      s.append(a[i]);
    }
    return s.append(']').toString();
  }

  public boolean isJunit()
  {
    return junit;
  }

  public void setJunit(boolean value)
  {
    junit = value;
  }

////////////////////////////////////////////////////////////////
// Implementation
////////////////////////////////////////////////////////////////

  private void pass()
  {
    if (junit)
    {
      TestRunnerNg.pass();
    }
    else
    {
      verifyCount++;
    }
  }

  private void fail(String msg)
  {
    if (junit)
    {
      TestRunnerNg.fail(msg);
    }
    else
    {
      throw new TestException(msg);
    }
  }

  private static String asString(int[] a)
  {
    if (a == null)
    {
      return "null";
    }
    StringBuilder sb = new StringBuilder();
    sb.append('[');
    for (int i = 0; i < a.length; i++)
    {
      if (i > 0)
      {
        sb.append(',');
      }
      sb.append(a[i]);
    }
    sb.append(']');
    return sb.toString();
  }

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  int verifyCount;
  protected BStation station;
  private static TestStationHandler stationHandler;
  private static final Object testStationMonitor = new Object();

  private boolean junit;
}
