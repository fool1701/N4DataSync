/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.testng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.baja.nre.platform.RuntimeProfile;
import javax.baja.registry.ModuleInfo;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.sys.TypeException;
import javax.baja.test.BISystemTest;
import javax.baja.test.BTestNg;

import org.testng.Assert;
import org.testng.IMethodInstance;
import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.collections.Lists;
import org.testng.reporters.EmailableReporter;
import org.testng.reporters.JUnitReportReporter;
import org.testng.reporters.VerboseReporter;
import org.testng.reporters.XMLReporter;
import org.testng.reporters.jq.Main;

import com.tridium.nre.platform.OperatingSystemEnum;
import com.tridium.nre.platform.PlatformUtil;
import com.tridium.sys.Nre;
import com.tridium.sys.NreLib;
import com.tridium.sys.module.NModule;
import com.tridium.sys.registry.NModuleInfo;
import com.tridium.sys.registry.NRegistry;

//NOTE: Suppress final, static inspections for this class because we will be mocking it
@SuppressWarnings({"FieldMayBeFinal", "MethodMayBeStatic"})
public class TestRunnerNg
{
  /**
   * Run all test methods for the given BTestNg type.
   * @param type test type
   */
  public void run(Type type)
  {
    runFiltered(type, null);
  }

  /**
   * Run only the specified test method for the given BTestNg type.
   * @param type test type
   * @param method method to run, or null to run all methods
   */
  public void run(Type type, Method method)
  {
    Predicate<IMethodInstance> filter = null;
    if (method != null)
    {
      filter = m -> m.getMethod().getMethodName().equals(method.getName());
    }
    runFiltered(type, filter);
  }

  /**
   * Run only the methods matching {@code pattern} for the given BTestNg type.
   * @param type test type
   * @param pattern pattern to match methods against, or null to run all methods
   */
  public void run(Type type, Pattern pattern)
  {
    Predicate<IMethodInstance> filter = null;
    if (pattern != null)
    {
      filter = m -> pattern.matcher(m.getMethod().getMethodName()).find();
    }
    runFiltered(type, filter);
  }

  /**
   * Run only the methods matching the supplied filter predicate, or all methods if
   * {@code filter} is null
   * @param type test type
   * @param filter predicate to match methods against, or null to run all methods
   */
  private void runFiltered(Type type, Predicate<IMethodInstance> filter)
  {
    // Execute TestNG tests on the class for this type
    init();
    testng.setTestClasses(new Class<?>[]{type.getTypeClass()});
    loaders.add(type.getTypeClass().getClassLoader());

    addTestNGClassLoaders();
    if (filter != null)
    {
      testng.setMethodInterceptor((methods, context) ->
        methods.stream()
          .filter(filter)
          .collect(Collectors.toList()));
    }

    loop();
    end();
  }

  /**
   * Run all test methods on all BTestNg classes.
   */
  public void run()
  {
    run((Pattern)null);
  }

  /**
   * Run all BTestNg class test methods that match the given regex, using the
   * format {@code com.package.ClassName#methodName}.
   * @param pattern pattern to match, or null to match all methods
   */
  public void run(Pattern pattern)
  {
    init();
    List<Class<?>> testClasses = Lists.newArrayList();

    TypeInfo[] types = Sys.getRegistry().getConcreteTypes(BTestNg.TYPE.getTypeInfo());
    for (TypeInfo t : types)
    {
      try
      {
        Class<?> clazz = t.getTypeSpec().getResolvedType().getTypeClass();
        if (!t.is(BISystemTest.TYPE) && hasMatchingMethods(clazz, pattern))
        {
          testClasses.add(clazz);
        }
        loaders.add(clazz.getClassLoader());
      }
      catch (TypeException e)
      {
        System.err.println("SEVERE [testrunner] can not resolve type: " + t);
        System.err.println("                    reason: " + e.getCause());

        if (e.getCause() instanceof OutOfMemoryError)
        {
          System.gc();
        }
      }
    }

    addTestNGClassLoaders();
    testng.setTestClasses(testClasses.toArray(new Class<?>[0]));

    if (pattern != null)
    {
      testng.setMethodInterceptor((methods, context) ->
                                    methods.stream()
                                           .filter(m -> matches(m, pattern))
                                           .collect(Collectors.toList()));
    }

    loop();
    end();
  }

  public void run(ModuleInfo moduleInfo)
  {
    run(new ModuleInfo[] { moduleInfo });
  }

  public void run(ModuleInfo [] moduleInfos)
  {
    init();
    List<Class<?>> testClasses = Lists.newArrayList();

    for (ModuleInfo moduleInfo : moduleInfos)
    {
      if (moduleInfo instanceof NModuleInfo)
      {
        if (!((NModuleInfo) moduleInfo).isAutoload())
        {
          // Load and refresh types for a non-autoload module
          try
          {
            ((NRegistry) Sys.getRegistry()).loadModule(moduleInfo.getModuleName(), moduleInfo.getRuntimeProfile());
          }
          catch (Exception ignore)
          {}

          ((NRegistry) Sys.getRegistry()).syncModules();

          // Refresh the current module
          moduleInfo = Sys.getRegistry().getModule(moduleInfo.getModuleName(), moduleInfo.getRuntimeProfile());
        }
      }

      TypeInfo[] types = moduleInfo.getTypes();
      for (TypeInfo t : types)
      {
        if (!t.isAbstract() && t.is(BTestNg.TYPE) && !t.is(BISystemTest.TYPE))
        {
          testClasses.add(t.getTypeSpec().getResolvedType().getTypeClass());
          loaders.add(t.getTypeSpec().getResolvedType().getTypeClass().getClassLoader());
        }
      }
    }

    addTestNGClassLoaders();
    testng.setTestClasses(testClasses.toArray(new Class<?>[0]));

    if (testClasses.size() > 0)
    {
      loop();
    }

    end();
  }

  private void loop()
  {
    if (loopCount > 1)
    {
      int i = 1;
      while (i <= loopCount)
      {
        Instant before = Instant.now();
        testng.run();
        long gap = Duration.between(before, Instant.now()).toMillis();
        LOGGER.log(Level.INFO, "  Completed loop count = " + i + ", millis = " + gap);
        i++;
      }
    }
    else
    {
      testng.run();
    }
  }

  public void init()
  {
    // NCCB-40859: Try to load Mockito with the correct classloader
    try
    {
      AccessController.doPrivileged((PrivilegedExceptionAction<Void>)() -> {
        NModule tridiumTest = Nre.getModuleManager().loadModule("tridiumTest", RuntimeProfile.wb);
        Class<?> cls = tridiumTest.loadClass("com.tridium.tridiumtest.util.MockitoUtils");
        Method m = cls.getMethod("loadMockito");
        m.invoke(null);
        return null;
      });
    }
    // tridiumTest-wb may not be present at all -- just continue on if the load failed.
    catch (Exception e)
    {
      LOGGER.log(Level.FINE, "[testrunner]: Error loading Mockito", e);
    }

    if (testng == null)
    {
      testng = new CustomTestNG(false);
    }
    loaders = new HashSet<>();
    start = BAbsTime.now();

    testng.setOutputDirectory(getOutputDirectory());

    if (AccessController.doPrivileged((PrivilegedAction<Boolean>)() -> PlatformUtil.getPlatformProvider().isEmbedded()))
    {
      testng.setUseDefaultListeners(false);
      TestListenerAdapter tla = new SimpleTestListener();
      testng.addListener((ITestNGListener)tla);

      // Initialize the logger to write to a file
      try
      {
        File testNgDirectory = new File(Nre.niagaraUserHome.getPath() + File.separatorChar + "reports" + File.separatorChar + "testng");
        if (!testNgDirectory.exists())
        {
          if (!testNgDirectory.mkdirs())
          {
            throw new IOException("Failed to create testNG directory \"" + testNgDirectory.getPath() + "\"");
          }
        }

        FileHandler fh = new FileHandler(testNgDirectory.getPath() + File.separatorChar + "TestNG.%g.log", logFileSize, logFileCount, true);
        fh.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(fh);
      }
      catch (IOException ioe)
      {
        System.err.println("SEVERE [testrunner] ***LOGGER INIT FAILED*** IO Exception: " + ioe.getMessage());
      }
      catch (SecurityException se)
      {
        System.err.println("SEVERE [testrunner] ***LOGGER INIT FAILED*** Security Exception: " + se.getMessage());
      }

      // Turn off TestNG output
      testng.setVerbose(0);
    }
    else
    {
      TestListenerAdapter tla = new TestListener();
      testng.addListener((ITestNGListener)tla);
      testng.addListener((ITestNGListener)new RequiresListener(OperatingSystemEnum.getOS()));
      testng.addListener(new NiagaraAnnotationTransformer());
      testng.addListener((ITestNGListener)new BenchmarkTestListener());
      if (!SKIP_HTML_REPORT && !skipHtmlReport)
      {
        // The nice HTML/JS/CSS report
        testng.addListener((ITestNGListener)new Main());
        testng.addListener((ITestNGListener)new EmailableReporter());
      }
      // Generating the JUnit report can be very slow, but is needed for some cases (notably, SonarQube).
      // Make it opt-in.
      if (GENERATE_JUNIT_REPORT || generateJunitReport)
      {
        testng.addListener((ITestNGListener) new JUnitReportReporter());
      }
      testng.addListener((ITestNGListener)new XMLReporter());
      if (verbosity > 4)
      {
        testng.addListener((ITestNGListener)new VerboseReporter("[TestNG] "));
      }
    }
    // TODO: Add an additional listener for Sprint Demo recording, set it
    //       active at a specific verbosity level

    if (verbosity != 0)
    {
      testng.setVerbose(verbosity);
    }
    if (groups != null && groups.length() > 0)
    {
      testng.setGroups(groups);
    }
    if (excludegroups != null && excludegroups.length() > 0)
    {
      testng.setExcludedGroups(excludegroups);
    }
  }

  public void end()
  {
    System.out.println("===============================================");
    System.out.println("Results of all run tests");
    System.out.println("Total tests run: " + (passed + failed) + ", Failures: " + failed + ", Skips: " + skipped);
    System.out.println("===============================================");
    System.out.println();

    if (benchmark)
    {
      end = BAbsTime.now();
      System.out.println("Total Time: " + start.delta(end).toString(null));
      System.out.println();
      System.out.println("High impact tests:");
      sortAndPrintTestTimes(getTestCounts());
      System.out.println();
      System.out.println("High impact suites:");
      sortAndPrintTestTimes(getSuiteCounts());
      System.out.println();
    }

    if (killThreadsOnExit)
    {
      killUserThreads();
    }

    try
    {
      wipeOldTestNGClassLoaders();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    testng = null;
    loaders = null;

    if (exitOnFailure &&
        failed > 0)
    {
      System.exit(-1);
    }
  }

  private void sortAndPrintTestTimes(Map<String, Long> counts)
  {
    counts.entrySet()
          .stream()
          .sorted(Comparator.comparingLong(Entry<String, Long>::getValue).reversed())
          .limit(50)
          .forEachOrdered(e -> System.out.println("  " + e.getKey() + ": " + e.getValue() + "ms"));
  }

  // NCCB-18400: Fix use of deprecated Thread.stop() method in TestRunnerNg.end()
  @SuppressWarnings({"deprecation", "CallToThreadStopSuspendOrResumeManager"})
  private void killUserThreads()
  {
    // get root thread group
    ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
    ThreadGroup parentGroup;
    while ((parentGroup = rootGroup.getParent()) != null)
    {
      rootGroup = parentGroup;
    }

    Thread[] threads = new Thread[rootGroup.activeCount()];
    while (rootGroup.enumerate(threads, true) == threads.length)
    {
      threads = new Thread[ threads.length * 2 ];
    }

    // loop looking for user threads
    Predicate<Thread> needsTermination = t ->
                                         t != null && t
                                         != Thread.currentThread() && !t.isDaemon() &&
                                         !"DestroyJavaVM".equals(t.getName());

    Arrays.stream(threads).filter(needsTermination).findFirst().ifPresent(t ->
    {
      // found a user thread, sleeping to allow time for cleanup?
      System.out.println("INFO [testrunner] found running user threads, sleeping to allow potential cleanup");
      try
      {
        Thread.sleep(5000);
      }
      catch (Exception ignore)
      {}
    });

    // recreate our thread list in case some cleaned up
    threads = new Thread[ rootGroup.activeCount() ];
    while (rootGroup.enumerate(threads, true) == threads.length)
    {
      threads = new Thread[threads.length * 2];
    }

    Arrays.stream(threads).filter(needsTermination).forEach(t ->
    {
      // user thread still found, BAD, forcing to stop
      if (t.getName().equals("JavaFX Application Thread"))
      {
        try
        {
          System.err.println("SEVERE [testrunner] forcing user thread " + t.getName() + " to terminate specifically.");

          //Use reflection to call these methods in case we are in compact3 environment
          //com.sun.javafx.application.PlatformImpl.tkExit();
          //javafx.application.Platform.exit();

          Class<?> platformImpl = Class.forName("com.sun.javafx.application.PlatformImpl");
          platformImpl.getDeclaredMethod("tkExit").invoke(null);

          Class<?> platform = Class.forName("javafx.application.Platform");
          platform.getDeclaredMethod("exit").invoke(null);

          if (t.isAlive())
          {
            Thread.sleep(3000);
          }
          else
          {
            System.out.println("INFO [testrunner] " + t.getName() + " has been terminated successfully.");
          }
          if (t.isAlive())
          {
            System.err.println("SEVERE [testrunner] " + t.getName() + " will not shutdown, terminating VM with System.exit(-1)");
            System.exit(-1);
          }
          else
          {
            System.out.println("INFO [testrunner] " + t.getName() + " has been terminated successfully.");
          }
        }
        catch (Exception e)
        {
          System.err.println("SEVERE [testrunner] cannot cleanup " + t.getName() + " due to Exception");
          e.printStackTrace();
          NreLib.dumpThreads();
        }
      }
      else
      {
        System.err.println("SEVERE [testrunner] forcing user thread " + t.getName() + " to terminate with Thread.interrupt().");
        StackTraceElement[] st = Thread.getAllStackTraces().get(t);
        if (st != null)
        {
          StringJoiner joiner = new StringJoiner(System.lineSeparator() + "\t");
          joiner.add("SEVERE [testrunner] stack trace of " + t.getName() + " :");
          for (StackTraceElement element : st)
          {
            joiner.add(element.toString());
          }
          System.err.println(joiner);
        }
        try
        {
          //Use interrupt() as a first attempt as stop() is deprecated
          t.interrupt();
          Thread.sleep(100);
          if (t.isAlive())
          {
            Thread.sleep(2000);

            if (t.isAlive())
            {
              System.err.println("SEVERE [testrunner] cannot cleanup " + t.getName() + " with Thread.interrupt");
              NreLib.dumpThreads();
              //wait for dumpThreads
              Thread.sleep(2000);
              //Attempt at interrupt() failed, use deprecated stop as a fallback
              System.err.println("SEVERE [testrunner] forcing user thread " + t.getName() + " to terminate with Thread.stop() which may cause other problems.");
              t.stop();
            }
            else
            {
              System.out.println("INFO [testrunner] " + t.getName() + " has been interrupted successfully after a small wait.");
            }
          }
          else
          {
            System.out.println("INFO [testrunner] " + t.getName() + " has been interrupted successfully.");
          }
        }
        catch (Exception e)
        {
          System.err.println("SEVERE [testrunner] cannot cleanup " + t.getName() + " due to Exception");
          e.printStackTrace();
          NreLib.dumpThreads();
        }
      }
    });
  }

  private void addTestNGClassLoaders()
  {
    if (loaders == null)
    {
      return;
    }

    loaders.forEach(testng::addClassLoader);
  }

  /**
   * The class loaders we add actually go into static collections, and so can't
   * be garbage collected in between test runs unless we wipe them manually.
   *
   * @throws Exception if our reflection hijinx go awry
   */
  @SuppressWarnings("rawtypes")
  private void wipeOldTestNGClassLoaders()
    throws Exception
  {
    try
    {
      Class<?> classHelperClass = Class.forName("org.testng.internal.ClassHelper");
      Field listField = classHelperClass.getDeclaredField("classLoaders");
      listField.setAccessible(true);
      List<?> list = (List)listField.get(classHelperClass);
      list.clear();

      Class<?> methodHelperClass = Class.forName("org.testng.internal.MethodHelper");
      Field nameCacheField = methodHelperClass.getDeclaredField("CANONICAL_NAME_CACHE");
      nameCacheField.setAccessible(true);
      Map<?, ?> map = (Map)nameCacheField.get(methodHelperClass);
      map.clear();
    }
    catch (Exception e)
    {
      System.err.println("SEVERE [testrunner] unable to read properties fro internal TestNg classes clearing old class loaders.");
      e.printStackTrace();
      throw e;
    }
  }

  private boolean hasMatchingMethods(Class<?> clazz, Pattern pattern)
  {
    return Arrays.stream(clazz.getMethods())
                 .anyMatch(m -> pattern == null || matches(clazz, m.getName(), pattern));
  }

  private boolean matches(IMethodInstance m, Pattern pattern)
  {
    return matches(m.getInstance().getClass(), m.getMethod().getMethodName(), pattern);
  }

  private boolean matches(Class<?> clazz, String methodName, Pattern pattern)
  {
    return pattern.matcher(clazz.getName() + "#" + methodName).find();
  }

  private void loadTestProperties()
  {
    File testPropsFile = new File(Nre.niagaraHome, "lib" + File.separator + "testng.properties");

    try (FileInputStream in = new FileInputStream(testPropsFile))
    {
      Properties testProps = new Properties();
      testProps.load(in);

      for (Object o : testProps.keySet())
      {
        final String key = (String) o;
        if (AccessController.doPrivileged((PrivilegedAction<String>) () -> System.getProperty(key)) == null)
        {
          AccessController.doPrivileged((PrivilegedAction<Object>) () -> System.getProperties().setProperty(key, testProps.getProperty(key).trim()));
        }
      }
    }
    catch (FileNotFoundException fnfe)
    {
      System.err.println("WARNING [testrunner] cannot load " + testPropsFile + ": File not found");
    }
    catch (Throwable e)
    {
      System.err.println("SEVERE [testrunner] cannot load " + testPropsFile + ": " + e);
    }
  }

  /**
   * Get the output directory -- either {@link #getReportDirectory()} if one has been specified, or
   * the default
   *
   * @return Output directory for TestNG
   */
  public String getOutputDirectory()
  {
    // TODO: Clean out existing reports?
    if (reportDirectory != null)
    {
      File outputLocation = new File(reportDirectory);
      if (!outputLocation.exists())
      {
        if (!(outputLocation.mkdirs()))
        {
          System.err.println("SEVERE [testrunner] unable to create output directory for reports");
        }
      }

      // TODO: Better handling if report directory cannot be created
      return reportDirectory;
    }
    else
    {
      File defaultOutputLocation = new File(Nre.niagaraUserHome, "reports"+ File.separator + "testng");
      if (!defaultOutputLocation.exists())
      {
        if (!(defaultOutputLocation.mkdirs()))
        {
          System.err.println("SEVERE [testrunner] unable to create output directory for reports");
        }
      }

      String defaultOutput = defaultOutputLocation.getAbsolutePath();
      return defaultOutput;
    }
  }

////////////////////////////////////////////////////////////////
//TestNG TestListener class
////////////////////////////////////////////////////////////////

  public class TestListener
    extends TestListenerAdapter
  {
    @Override
    public void onTestFailure(ITestResult failure)
    {
      if (verbosity != 0)
      {
        System.out.println(" TestNG Test Failed: " + failure.getName() + " - " + failure.getThrowable().getMessage());
      }
    }

    @Override
    public void onTestSkipped(ITestResult skip)
    {
      if (verbosity != 0)
      {
        System.out.println(" TestNG Test Skipped: " + skip.getName());
      }
    }

    @Override
    public void onTestSuccess(ITestResult success)
    {
      if (verbosity != 0)
      {
        System.out.println(" TestNG Test Ended: " + success.getName());
      }
    }

    @Override
    public void onFinish(ITestContext tc)
    {
      IResultMap failedMap = tc.getFailedTests();

      // Set the return values for passed, failed, skipped.
      failed += failedMap.size();
      passed += tc.getPassedTests().size();
      skipped += tc.getSkippedTests().size();

      // Print a failure report
      Set<ITestResult> failures = failedMap.getAllResults();
      if (failures.isEmpty())
      {
        return;
      }

      System.out.println("\n*** TestNG Failure Summary ***");
      beep();

      for (ITestResult result : failures)
      {
        Throwable err = result.getThrowable();
        System.out.print(" TestNG Test Failed: " + result.getName());
        if (err == null)
        {
          System.out.println();
        }
        else
        {
          System.out.println(" - " + err.getMessage());
        }

        if (verbosity != 0)
        {
          if (err != null)
          {
            System.out.println(" TestNG Error Stack Trace: ");
            err.printStackTrace();
          }
        }
      }
    }
  }

  private static void beep()
  {
    if (BEEP_ON_FAIL)
    {
      System.out.println("\007");
    }
  }

////////////////////////////////////////////////////////////////
//TestNG SimpleTestListener class
////////////////////////////////////////////////////////////////

  public class SimpleTestListener
    extends TestListenerAdapter
  {
    @Override
    public void onFinish(ITestContext tc)
    {
      IResultMap failedMap = tc.getFailedTests();

      // Set the return values for passed, failed, skipped.
      failed += failedMap.size();
      passed += tc.getPassedTests().size();

      // Print a summary
      if (verbosity != 0)
      {
        LOGGER.log(Level.FINE, "TestNG Summary: passed = " + passed + ", failed = " + failed);
      }
    }
  }

  public static void pass()
  {
    Assert.assertTrue(true);
  }

  public static void fail(String msg)
  {
    Assert.fail(msg);
  }

  public String getTestJar()
  {
    return testJar;
  }

  public void setTestJar(String testJar)
  {
    this.testJar = testJar;
  }

  public String getXmlPathInJar()
  {
    return xmlPathInJar;
  }

  public void setXmlPathInJar(String xmlPathInJar)
  {
    this.xmlPathInJar = xmlPathInJar;
  }

  public int getVerbosity()
  {
    return verbosity;
  }

  public void setVerbosity(int verbosity)
  {
    this.verbosity = verbosity;
  }

  public String getGroups()
  {
    return groups;
  }

  public void setGroups(String groups)
  {
    this.groups = groups;
  }

  public String getExcludeGroups()
  {
    return excludegroups;
  }

  public void setExcludeGroups(String excludegroups)
  {
    this.excludegroups = excludegroups;
  }

  public String getReportDirectory()
  {
    return reportDirectory;
  }

  public void setReportDirectory(String reportDirectory)
  {
    this.reportDirectory = reportDirectory;
  }

  public int getLoopCount()
  {
    return loopCount;
  }

  public void setLoopCount(int loopCount)
  {
    this.loopCount = loopCount;
  }

  public boolean isKillThreadsOnExit()
  {
    return killThreadsOnExit;
  }

  public void setKillThreadsOnExit(boolean killThreadsOnExit)
  {
    this.killThreadsOnExit = killThreadsOnExit;
  }

  public boolean isExitOnFailure()
  {
    return exitOnFailure;
  }

  public void setExitOnFailure(boolean exitOnFailure)
  {
    this.exitOnFailure = exitOnFailure;
  }

  public long getFailed()
  {
    return failed;
  }

  public long getPassed()
  {
    return passed;
  }

  public long getSkipped()
  {
    return skipped;
  }

  public void setSkipHtmlReport(boolean skipHtmlReport)
  {
    this.skipHtmlReport = skipHtmlReport;
  }

  public void setGenerateJunitReport(boolean generateJunitReport)
  {
    this.generateJunitReport = generateJunitReport;
  }

  public void setBenchmark(boolean benchmark)
  {
    this.benchmark = benchmark;
  }

  private class BenchmarkTestListener
    implements ITestListener
  {
    long start, end;

    @Override
    public void onTestStart(ITestResult iTestResult)
    {
      start = iTestResult.getStartMillis();
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult)
    {
      endTest(iTestResult);
    }

    @Override
    public void onTestFailure(ITestResult iTestResult)
    {
      endTest(iTestResult);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult)
    {
      endTest(iTestResult);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult)
    {
      endTest(iTestResult);
    }

    private void endTest(ITestResult result)
    {
      long time = result.getEndMillis() - start;
      testCounts.compute(result.getMethod().getQualifiedName(), (k, v) -> v == null ? time : time + v);
    }

    @Override
    public void onStart(ITestContext iTestContext)
    {
    }

    @Override
    public void onFinish(ITestContext iTestContext)
    {
      Date end = iTestContext.getEndDate();
      Date start = iTestContext.getStartDate();
      long delta = end.getTime() - start.getTime();
      suiteCounts.compute(iTestContext.getSuite().getName(), (k, v) -> v == null ? delta : delta + v);
    }
  }

  public Map<String, Long> getTestCounts()
  {
    return Collections.unmodifiableMap(testCounts);
  }

  public Map<String, Long> getSuiteCounts()
  {
    return Collections.unmodifiableMap(suiteCounts);
  }

  private CustomTestNG testng = null;

  // List of class loaders used for test classes. TestNG needs to know about
  //  class loaders in order for its internal method lookup to work correctly
  //  with group definitions, etc.
  HashSet<ClassLoader> loaders = null;

  // Set up placeholders for test xml, test jar, and xml path in jar.
  //  Command line arguments can be defined and cached here for use in
  //  the various run() methods.
  private String testJar = null;           // testng.setTestJar(String jarPath)
  private String xmlPathInJar = null;      // testng.setXmlPathInJar(String xmlPathInJar)
  private int verbosity = 0;               // testng.setVerbosity(verbosity)
  private String groups = null;            // the list of groups to execute
  private String excludegroups = null;     // the list of groups to exclude from this run
  private String reportDirectory = null;   // the location of the TestNG reports
  private int loopCount = 1;               // testng.setLoopCount(loopCount)
  private boolean skipHtmlReport = false;
  private boolean generateJunitReport = false;
  private boolean benchmark = false;

  // Logger info
  private static Logger LOGGER = Logger.getLogger("TestNG");
  private static final int logFileSize = 10000;
  private static final int logFileCount = 5;

  //CI Server looks at the exit code to determine test failure
  private long failed = 0;
  private long passed = 0;
  private long skipped = 0;

  // Benchmarking data
  private BAbsTime start, end;
  private HashMap<String, Long> testCounts = new HashMap<>();
  private HashMap<String, Long> suiteCounts = new HashMap<>();

  private boolean killThreadsOnExit = true;
  private boolean exitOnFailure = true;

  private static final boolean BEEP_ON_FAIL = AccessController.doPrivileged((PrivilegedAction<Boolean>)() -> Boolean.getBoolean("niagara.test.beepOnFailure"));
  private static final boolean SKIP_HTML_REPORT = AccessController.doPrivileged((PrivilegedAction<Boolean>)() -> Boolean.getBoolean("niagara.test.skipHtmlReport"));
  private static final boolean GENERATE_JUNIT_REPORT = AccessController.doPrivileged((PrivilegedAction<Boolean>)() -> Boolean.getBoolean("niagara.test.generateJunitReport"));
}
