/*
 * Copyright (c) 2017 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.testng;

import static org.testng.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.baja.file.BFileSystem;
import javax.baja.file.BIFile;
import javax.baja.file.FilePath;
import javax.baja.naming.BOrd;
import javax.baja.nre.util.FileUtil;
import javax.baja.registry.Registry;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComplex;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFloat;
import javax.baja.sys.BInteger;
import javax.baja.sys.BLong;
import javax.baja.sys.BNumber;
import javax.baja.sys.Clock;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.ui.BWidget;
import javax.baja.web.WebDev;
import javax.baja.web.js.BJsBuild;

import org.testng.Assert;

import com.tridium.nre.RunnableWithException;
import com.tridium.nre.SupplierWithException;

/**
 * Useful methods for test classes.
 *
 * @author Eric Anderson on 4/19/2017
 * @since Niagara 4.4
 */
@SuppressWarnings("sunapi")
public final class TestUtil
{
  // private constructor
  private TestUtil()
  {
  }

  private static final int WAIT_FOR_TRUE_INTERVAL = 10;
  private static final int WAIT_FOR_TRUE_TIMEOUT = 5000;

  /**
   * @deprecated use {@link #assertWillBeTrue(BooleanSupplier, long, String)} instead; the seconds
   * parameter value will need to be converted to milliseconds
   */
  @Deprecated
  public static void waitFor(int seconds, BooleanSupplier condition, String message)
  {
    //noinspection MagicNumber
    assertTrue(waitFor(condition, seconds * 1000L, WAIT_FOR_TRUE_INTERVAL), message + ": wait time expired");
  }

  /**
   * Tests the supplied condition at the default interval of 10 ms for the default timeout of 5000
   * ms. If the condition is not satisfied before the timeout, {@link Assert#assertTrue} will fail
   * and log the supplied message.
   *
   * @param condition condition to test
   * @param message message to include with the assertTrue if the condition is not met before the
   *                default timeout of 5000 ms
   */
  public static void assertWillBeTrue(BooleanSupplier condition, String message)
  {
    assertTrue(waitFor(condition, WAIT_FOR_TRUE_TIMEOUT, WAIT_FOR_TRUE_INTERVAL), message);
  }

  /**
   * Tests the supplied condition at the default interval of 10 ms for the default timeout of 5000
   * ms. If the condition is not satisfied before the timeout, {@link Assert#fail(String)} with the
   * message supplier.
   *
   * @param condition condition to test
   * @param message message supplier to include with the Assert.fail if the condition is not met
   *                before the default timeout of 5000 ms; evaluated once the timeout is reached
   * @since Niagara 4.11
   * @since Niagara 4.10u5
   */
  public static void assertWillBeTrue(BooleanSupplier condition, Supplier<String> message)
  {
    if (!waitFor(condition, WAIT_FOR_TRUE_TIMEOUT, WAIT_FOR_TRUE_INTERVAL))
    {
      Assert.fail(message.get());
    }
  }

  /**
   * Tests the supplied condition at the default interval of 10 ms for the specified timeout. If the
   * condition is not satisfied before the timeout, {@link Assert#assertTrue} will fail and log the
   * supplied message.
   *
   * @param condition condition to test
   * @param timeout time to wait in milliseconds for condition to be true
   * @param message message to include with the assertTrue if the condition is not met before the
   *                timeout
   */
  public static void assertWillBeTrue(BooleanSupplier condition, long timeout, String message)
  {
    assertTrue(waitFor(condition, timeout, WAIT_FOR_TRUE_INTERVAL), message);
  }

  /**
   * Tests the supplied condition at the default interval of 10 ms for the specified timeout. If the
   * condition is not satisfied before the timeout, {@link Assert#fail(String)} with the message
   * supplier.
   *
   * @param condition condition to test
   * @param timeout time to wait in milliseconds for condition to be true
   * @param message message supplier to include with the Assert.fail if the condition is not met
   *                before the default timeout of 5000 ms; evaluated once the timeout is reached
   * @since Niagara 4.11
   * @since Niagara 4.10u5
   */
  public static void assertWillBeTrue(BooleanSupplier condition, long timeout, Supplier<String> message)
  {
    if (!waitFor(condition, timeout, WAIT_FOR_TRUE_INTERVAL))
    {
      Assert.fail(message.get());
    }
  }

  /**
   * Tests the supplied condition at the default interval of 10 ms until the supplied condition is
   * true or until the default timeout of 5000 ms is reached.
   *
   * @param condition condition to test every 10 ms
   * @return true if the condition is met before the default timeout of 5000 ms
   */
  public static boolean waitFor(BooleanSupplier condition)
  {
    return waitFor(condition, WAIT_FOR_TRUE_TIMEOUT, WAIT_FOR_TRUE_INTERVAL);
  }

  /**
   * @deprecated use {@link #waitFor(BooleanSupplier, long)} instead; the timeout parameter value
   * will need to be adjusted from seconds to milliseconds.
   */
  @Deprecated
  public static boolean waitFor(BooleanSupplier condition, double timeout)
  {
    return waitFor(condition, (long)(timeout * 1000), WAIT_FOR_TRUE_INTERVAL);
  }

  /**
   * Tests the supplied condition at the default interval of 10 ms until the supplied condition is
   * true or until the specified timeout is reached.
   *
   * @param condition condition to test every 10 ms
   * @param timeout time to wait in milliseconds for condition to be true
   * @return true if the condition is met before the timeout is reached
   */
  public static boolean waitFor(BooleanSupplier condition, long timeout)
  {
    return waitFor(condition, timeout, WAIT_FOR_TRUE_INTERVAL);
  }

  /**
   * @deprecated use {@link #waitFor(BooleanSupplier, long, long)} instead; the timeout parameter
   * value will need to be adjusted from seconds to milliseconds and the interval and timeout
   * parameters must switch positions
   */
  @Deprecated
  public static boolean waitFor(BooleanSupplier condition, long interval, double timeout)
  {
    return waitFor(condition, (long)(timeout * 1000), interval);
  }

  /**
   * Tests the supplied condition at the specified interval until the supplied condition is true or
   * until the specified timeout is reached.
   *
   * @param condition condition to test
   * @param timeout time to wait in milliseconds for condition to be true
   * @param interval number of milliseconds between condition tests
   * @return true if the condition is met before the timeout is reached
   */
  public static boolean waitFor(BooleanSupplier condition, long timeout, long interval)
  {
    long now = Clock.ticks();
    long end = now + timeout;
    while (now < end)
    {
      if (condition.getAsBoolean())
      {
        return true;
      }

      try
      {
        Thread.sleep(interval);
      }
      catch (InterruptedException e)
      {
        Thread.currentThread().interrupt();
        return false;
      }

      now = Clock.ticks();
    }

    return condition.getAsBoolean();
  }

  /**
   * Waits until there has been at least one millisecond change on the {@link Clock}.
   *
   * @since Niagara 4.13
   * @since Niagara 4.12u2
   * @since Niagara 4.10u5
   */
  public static void waitForClockChange()
  {
    BAbsTime start = Clock.time();
    waitFor(() -> Clock.time().getMillis() > start.getMillis());
  }

  /**
   * Tests if the passed FE Type has a ux_field_editor in its slot facets.
   */
  public static void fieldEditorSlotFacetsToHaveUxFieldEditorDefined(String expectedFeType, String expectedUxFeType)
  {
    assertNotNull(expectedFeType, "expectedFeType argument");
    assertNotNull(expectedUxFeType, "expectedUxFeType argument");

    Registry registry = Sys.getRegistry();

    for (TypeInfo type : registry.getConcreteTypes(BComplex.TYPE.getTypeInfo()))
    {
      // skip interfaces, abstract classes and BWidgets
      if (type.isAbstract() || type.isInterface() || type.is(BWidget.TYPE))
      {
        continue;
      }

      // Types without a default constructor cannot be checked
      BComplex instance;
      try
      {
        instance = type.getInstance().asComplex();
      }
      catch (Throwable ignore)
      {
        continue;
      }

      for (Property property : instance.getProperties())
      {
        BFacets facets = property.getFacets();
        String actualFeType = facets.gets(BFacets.FIELD_EDITOR, null);
        if (expectedFeType.equals(actualFeType))
        {
          String actualUxFeType = facets.gets(BFacets.UX_FIELD_EDITOR, null);
          String message = "actual UX field editor type for " + type.getModuleName() + ':' +
            type.getTypeName() + '.' + property.getName();
          assertNotNull(actualUxFeType, message);
          assertEquals(actualUxFeType, expectedUxFeType, message);
        }
      }
    }
  }

  /**
   * You can use this log handler to spy on the last log message
   */
  public static class LatestHandler extends Handler
  {
    String latestMessage;
    Level latestLevel;

    public Level getLatestLevel()
    {
      return latestLevel;
    }

    public String getLatestMessage()
    {
      return latestMessage;
    }

    @Override
    public void publish(LogRecord record)
    {
      latestMessage = record.getMessage();
      latestLevel = record.getLevel();
    }

    @Override
    public void flush()
    {

    }

    @Override
    public void close()
    {

    }

    public static LatestHandler addLogHandler(String logName)
    {
      LatestHandler logHandler = new LatestHandler();
      Logger.getLogger(logName).addHandler(logHandler);
      return logHandler;
    }

    public void removeLogHandler(String logName)
    {
      Logger.getLogger(logName).removeHandler(this);
    }
  }

  /**
   * Retrieve the current value for a private or private-static variable and remove the
   * final modifier so that other unit tests can change them.
   * This is useful for trying out alternate System property values for a test.
   *
   * @param object instance or class which contains the private field.
   *               If the field is not on the class, then super classes will be checked.
   * @param variableName name of the private field.
   */
  public static Object getPrivateField(Object object, String variableName)
  {
    return actOnField(object, variableName, field -> field.get(object));
  }

  /**
   * Set the current value for a private or private-static variable.
   *
   * @param object instance or class which contains the private field.
   *               If the field is not on the class, then super classes will be checked.
   * @param variableName name of the private field.
   * @param value value to set on the private field.
   */
  public static void setPrivateField(Object object, String variableName, Object value)
  {
    actOnField(object, variableName, field -> { setField(object, field, value); return null; });
  }

  private static void setField(Object object, Field field, Object value)
  {
    if (object != null)
    {
      Object previousValue = getPrivateField(object, field.getName());
      if (previousValue != null)
      {
        hashCodes += previousValue.hashCode();
      }
    }
    //Ths sets the field value in a way that is compatible with Java 12+
    //https://stackoverflow.com/questions/61141836/change-static-final-field-in-java-12
    sun.misc.Unsafe unsafe = getUnsafe();
    long offset;
    Object objectForPut;
    boolean isVolatile = Modifier.isVolatile(field.getModifiers());
    if (Modifier.isStatic(field.getModifiers()))
    {
      objectForPut = unsafe.staticFieldBase(field);
      offset = unsafe.staticFieldOffset(field);
    }
    else
    {
      offset = unsafe.objectFieldOffset(field);
      objectForPut = object;
    }

    Class<?> type = field.getType();
    if (type == boolean.class)
    {
      if (isVolatile)
      {
        unsafe.putBooleanVolatile(objectForPut, offset, (boolean) value);
      }
      else
      {
        unsafe.putBoolean(objectForPut, offset, (boolean) value);
      }
    }
    else if (type == int.class)
    {
      if (isVolatile)
      {
        unsafe.putIntVolatile(objectForPut, offset, toNumber(value).getInt());
      }
      else
      {
        unsafe.putInt(objectForPut, offset, toNumber(value).getInt());
      }
    }
    else if (type == long.class)
    {
      if (isVolatile)
      {
        unsafe.putLongVolatile(objectForPut, offset, toNumber(value).getLong());
      }
      else
      {
        unsafe.putLong(objectForPut, offset, toNumber(value).getLong());
      }
    }
    else if (type == double.class)
    {
      if (isVolatile)
      {
        unsafe.putDoubleVolatile(objectForPut, offset, toNumber(value).getDouble());
      }
      else
      {
        unsafe.putDouble(objectForPut, offset, toNumber(value).getDouble());
      }
    }
    else if (type == float.class)
    {
      if (isVolatile)
      {
        unsafe.putFloatVolatile(objectForPut, offset, toNumber(value).getFloat());
      }
      else
      {
        unsafe.putFloat(objectForPut, offset, toNumber(value).getFloat());
      }
    }
    else if (type == short.class)
    {
      if (isVolatile)
      {
        unsafe.putShortVolatile(objectForPut, offset, (short) value);
      }
      else
      {
        unsafe.putShort(objectForPut, offset, (short) value);
      }
    }
    else if (type == char.class)
    {
      if (isVolatile)
      {
        unsafe.putCharVolatile(objectForPut, offset, (char) value);
      }
      else
      {
        unsafe.putChar(objectForPut, offset, (char) value);
      }
    }
    else if (type == byte.class)
    {
      if (isVolatile)
      {
        unsafe.putByteVolatile(objectForPut, offset, (byte) value);
      }
      else
      {
        unsafe.putByte(objectForPut, offset, (byte) value);
      }
    }
    else
    {
      if (isVolatile)
      {
        unsafe.putObjectVolatile(objectForPut, offset, value);
      }
      else
      {
        unsafe.putObject(objectForPut, offset, value);
      }
    }
  }

  /**
   * Convert any primitive Number to a BNumber to help prevent ClassCastExceptions.
   */
  private static BNumber toNumber(Object value)
  {
    if (value instanceof Double)
    {
      return BDouble.make((Double) value);
    }
    else if (value instanceof Integer)
    {
      return BInteger.make((Integer) value);
    }
    else if (value instanceof Float)
    {
      return BFloat.make((Float) value);
    }
    else if (value instanceof Long)
    {
      return BLong.make((Long) value);
    }
    else if(value instanceof Number)
    {
      //non-standard Number like BigDecimal
      Number number = (Number) value;
      return BDouble.make(number.doubleValue());
    }
    else
    {
      throw new RuntimeException("Currently unsupported conversion type: " + value.getClass().getName());
    }
  }

  private interface ExceptionFunction<T, R, E extends Exception> {
    R apply(T t) throws E;
  }

  private static <T> T actOnField(Object object,
                                  String variableName,
                                  ExceptionFunction<Field, T, IllegalAccessException> function)
  {
    Field field = null;
    boolean fieldAccessible = false;
    try
    {
      Class<?> cls = object instanceof Class ? (Class<?>) object : object.getClass();
      field = getField(cls, variableName);
      fieldAccessible = field.isAccessible();
      field.setAccessible(true);

      return function.apply(field);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Cannot manipulate field: " + e.getMessage(), e);
    }
    finally
    {
      try
      {
        if (field != null)
        {
          field.setAccessible(fieldAccessible);
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * `getField` checks the `declaringClass` and all it's super classes for the desired `fieldName`.
   *
   * @since Niagara 4.13
   */
  private static Field getField(Class<?> declaringClass, String fieldName)
    throws NoSuchFieldException
  {
    if (declaringClass == null)
    {
      throw new RuntimeException("Can't get field on null object/class");
    }
    else
    {

      try
      {
        Field field = declaringClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
      }
      catch (NoSuchFieldException e)
      {
        Class<?> declaringSuperClass = declaringClass.getSuperclass();
        if (declaringSuperClass == null)
        {
          throw e;
        }
        else
        {
          return getField(declaringSuperClass, fieldName);
        }
      }
    }
  }

  /**
   * Run your test with an alternate static final variable, then change the value back to the given original value.
   * This is useful for trying out alternate System properties.
   */
  public static <E extends Exception> void runWithAlternateValue(Object object,
                                                                 String variableName,
                                                                 Object alternateValue,
                                                                 RunnableWithException<E> r)
    throws E
  {
    Object originalValue = getPrivateField(object, variableName);
    setPrivateField(object, variableName, alternateValue);

    try
    {
      r.run();
    }
    finally
    {
      setPrivateField(object, variableName, originalValue);
    }
  }


  /**
   * Run your test with an alternate static final variable, then change the value back to the given original value.
   * This is useful for trying out alternate System properties.
   * @return the return value of the Supplier
   * @since Niagara 4.13
   */
  public static <T, E extends Exception> T callWithAlternateValue(
    Object object,
    String variableName,
    Object alternateValue,
    SupplierWithException<T, E> testSupplier
  )
    throws E
  {
    Object originalValue = getPrivateField(object, variableName);
    setPrivateField(object, variableName, alternateValue);

    try
    {
      return testSupplier.get();
    }
    finally
    {
      setPrivateField(object, variableName, originalValue);
    }
  }

  // region webdev

  public static class WebDevSnapshot
  {
    private WebDevSnapshot()
    {
      @SuppressWarnings("unchecked")
      Map<String, WebDev> map = (Map<String, WebDev>) getPrivateField(WebDev.class, "webDevs");

      map.forEach((name, webDev) -> {
        webdevStatuses.put(name, webDev.isEnabled());
      });
    }

    public void restore()
    {
      webdevStatuses.forEach((name, enabled) -> {
        WebDev.get(name).setEnabled(enabled);
      });
    }
    private final Map<String, Boolean> webdevStatuses = new HashMap<>();
  }

  /**
   * Take a snapshot of the current WebDev status. You can now set whatever WebDev values you like
   * for your test, and then restore them afterwards.
   * @return a WebDev snapshot that can be restored later
   * @since Niagara 4.13
   */
  public static WebDevSnapshot webDevSnapshot()
  {
    return new WebDevSnapshot();
  }

  public static class WebDevScenario
  {
    private WebDevScenario(String... webDevIds)
    {
      this.webDevIds = Arrays.asList(webDevIds);
    }

    /**
     * @param runnable procedure to run with webdev on
     * @return this
     */
    public <E extends Exception> WebDevScenario on(RunnableWithException<E> runnable)
      throws E
    {
      withWebDevEnabled(true, runnable, webDevIds);
      return this;
    }

    /**
     * @param runnable procedure to run with webdev off
     * @return this
     */
    public <E extends Exception> WebDevScenario off(RunnableWithException<E> runnable)
      throws E
    {
      withWebDevEnabled(false, runnable, webDevIds);
      return this;
    }

    private static <E extends Exception> void withWebDevEnabled(
      boolean enabled,
      RunnableWithException<E> runnable,
      List<String> webDevIds
    )
      throws E
    {
      if (webDevIds.isEmpty())
      {
        runnable.run();
        return;
      }

      String webDevId = webDevIds.get(0);
      WebDev webDev = WebDev.get(webDevId);
      boolean wasEnabled = webDev.isEnabled();
      List<String> rest = webDevIds.subList(1, webDevIds.size());

      webDev.setEnabled(enabled);
      try
      {
        withWebDevEnabled(enabled, runnable, rest);
      }
      finally
      {
        webDev.setEnabled(wasEnabled);
      }
    }

    private final List<String> webDevIds;
  }

  /**
   * @param webDevIds WebDev IDs you wish to turn on or off while a procedure runs
   * @since Niagara 4.13
   */
  public static WebDevScenario withWebDev(String... webDevIds)
  {
    return new WebDevScenario(webDevIds);
  }

  /**
   * @param builds JsBuilds whose WebDev you wish to turn on or off while a procedure runs
   * @since Niagara 4.13
   */
  public static WebDevScenario withWebDev(BJsBuild... builds)
  {
    return new WebDevScenario(Arrays.stream(builds).map(BJsBuild::getId).toArray(String[]::new));
  }

  // endregion webdev

  /**
   * Invoke a private method and then restore to inaccessible.
   *
   * @param obj        instance which contains the private method.
   * @param methodName name of the private method.
   * @param args       arguments for the private method.
   * @return the return value of the private method, or null if void.
   * @throws Throwable any exception throw from the private methods' logic.
   */
  public static <T> T invokePrivate(Object obj, String methodName, Object... args)
    throws Throwable
  {
    return invokePrivate(obj, obj.getClass(), methodName, args);
  }

  /**
   * Invoke a private method and then restore to inaccessible.
   *
   * @param obj        instance which contains the private method.
   * @param declaringClass class which declares the private field.
   * @param methodName name of the private method.
   * @param args       arguments for the private method.
   * @return the return value of the private method, or null if void.
   * @throws Throwable any exception throw from the private methods' logic.
   */
  @SuppressWarnings("unchecked")
  public static <T> T invokePrivate(Object obj,
                                     Class<?> declaringClass,
                                     String methodName,
                                     Object... args)
    throws Throwable
  {
    Method method = null;
    try
    {
      Class<?>[] argClasses = Arrays.stream(args)
        .map(Object::getClass)
        .toArray(Class[]::new);
      method = declaringClass.getDeclaredMethod(methodName, argClasses);
      method.setAccessible(true);
      return (T) method.invoke(obj, args);
    }
    catch (NoSuchMethodException | IllegalAccessException e)
    {
      fail("Failed to invoke " + methodName, e);
    }
    catch (InvocationTargetException e)
    {
      throw e.getTargetException();
    }
    finally
    {
     if (method != null)
     {
       method.setAccessible(true);
     }
    }
    return null;
  }

  /**
   * Copy a file from one location to the desired FilePath.
   */
  public static void copyFile(BOrd original, FilePath destination)
    throws IOException
  {
    BIFile inFile = (BIFile) original.get();
    BIFile outFile = BFileSystem.INSTANCE.makeFile(destination);
    try (InputStream in = inFile.getInputStream(); OutputStream out = outFile.getOutputStream())
    {
      FileUtil.pipe(in, out);
      out.flush();
    }
  }

  /**
   * Unpack the module resource to a temp file that deletes on exit.
   *
   * @param moduleFilePath the full path within the module e.g. bajaTest/resources/zip/x.zip
   * @return a reference to the temp file
   * @throws IOException if the module source could not be found, or the tmp file could not be created
   * @since Niagara 4.10u8 / 4.13u3 / 4.14
   */
  public static BIFile unpackModuleFileAsTempFile(String moduleFilePath)
    throws IOException
  {
    try
    {
      BOrd moduleOrd = BOrd.make("module://" + moduleFilePath);
      BIFile moduleFile = (BIFile)moduleOrd.get();
      File tempFile = File.createTempFile(moduleFile.getFileName(), '.' + moduleFile.getExtension());
      tempFile.deleteOnExit();

      copyFile(moduleOrd, BFileSystem.INSTANCE.localFileToPath(tempFile));

      return BFileSystem.INSTANCE.localFileToOrd(tempFile)
        .get()
        .as(BIFile.class);
    }
    catch (Exception e)
    {
      // TestNg is fairly unhelpful printing exception details so catch and release here
      System.err.println("Failed to unpack file '" + moduleFilePath + "', exception '" + e.getMessage() + '\'');
      e.printStackTrace();
      throw e;
    }
  }

  private static sun.misc.Unsafe getUnsafe()
  {
    if (unsafe != null)
    {
      return unsafe;
    }

    try
    {
      final Field unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
      unsafeField.setAccessible(true);
      unsafe = (sun.misc.Unsafe) unsafeField.get(null);
      return unsafe;
    }
    catch (Exception e)
    {
      throw new RuntimeException("Cannot initialize sun.misc.Unsafe ", e);
    }
  }

  /**
   * Here is a test utility where it will run the provided runnables at the same time and ensure that all Runnables
   * complete without any Exception. This test can be helpful when looking for Concurrency problems.
   *
   * @param threadsPerRunnable Number of Threads per Runnable.
   * @param iterationsPerRunnable Number of iterations on each Thread for same Runnable.
   * @param timeout The time to wait in milliseconds for all work to complete.
   * @param runnables The work to run on a separate Thread.
   * @since Niagara 4.13
   */
  public static void assertCanRunConcurrently(int threadsPerRunnable, int iterationsPerRunnable, long timeout, Runnable... runnables)
  {
    List<Thread> threads = new ArrayList<>();
    AtomicInteger counter = new AtomicInteger(0);
    AtomicReference<Exception> exception = new AtomicReference<>();
    int totalThreads = threadsPerRunnable * runnables.length;
    for (Runnable runnable : runnables)
    {
      for (int i = 0; i < threadsPerRunnable; i++)
      {
        threads.add(new Thread(() -> {
          try
          {
            for (int j = 0; j < iterationsPerRunnable; j++)
            {
              runnable.run();
            }
          }
          catch (Exception e)
          {
            exception.set(e);
            throw e;
          }
          counter.incrementAndGet();
        }, "assertCanRunConcurrently"));
      }
    }

    for (Thread thread : threads)
    {
      thread.start();
    }

    TestUtil.assertWillBeTrue(() -> {

      Exception e = exception.get();
      if (e != null)
      {
        throw new RuntimeException("Failure on thread", e);
      }
      return counter.get() == totalThreads;
    }, timeout, "not all threads completed successfully");
  }

  private static sun.misc.Unsafe unsafe;

  //a private static to help private fields enforce initialization
  @SuppressWarnings("unused")
  private static int hashCodes = 0;
}
