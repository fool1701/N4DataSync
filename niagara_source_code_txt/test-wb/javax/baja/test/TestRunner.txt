/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.baja.nre.platform.RuntimeProfile;
import javax.baja.registry.ModuleInfo;
import javax.baja.sys.ModuleNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.Nre;
import com.tridium.sys.module.NModule;
import com.tridium.testng.TestRunnerNg;
import com.tridium.util.ArrayUtil;
import com.tridium.util.CommandLineArguments;

/**
 * TestRunner is used to run a set of BTests.
 *
 * @author    Brian Frank on 3 Oct 06
 * @version   $Revision: 8$ $Date: 6/15/11 11:00:00 AM EDT$
 * @since     Niagara 3.2
 */
//NOTE: Suppress final, static inspections for this class because we will be mocking it
@SuppressWarnings({"FieldMayBeFinal", "MethodMayBeStatic"})
public class TestRunner
{

////////////////////////////////////////////////////////////////
// Run
///////////////////////////////////////////////////////////////

  /**
   * Run the target formatted as:
   *   - all
   *   - {module}
   *   - {module-runtimeProfile}
   *   - {module}:{type}
   *   - {module}:{type}.{method}
   *   - {com.package}.{BTestClass}
   *   - {com.package}.{BTestClass}#{method}
   *   - /regex to match against the com.package.BTestClass#method format/
   */
  public void run(String target)
    throws NoSuchMethodException
  {
    // Issue 19765 - Check for module "<module>Test" first,
    //    then look for base module name.

    Matcher matcher;
    // all
    if (target.equals("all"))
    {
      runAll();
    }
    // {module}
    else if (MODULE_PATTERN.matcher(target).find())
    {
      ModuleInfo[] allMi;
      try
      {
        allMi = Sys.getRegistry().getModules(target + "Test");
      }
      catch (ModuleNotFoundException mnfe)
      {
        allMi = Sys.getRegistry().getModules(target);
      }
      run(allMi);
    }
    // {module-runtimeProfile}
    else if ((matcher = MODULE_PART_PATTERN.matcher(target)).find())
    {
      String moduleName = matcher.group(1);
      RuntimeProfile runtimeProfile = RuntimeProfile.valueOf(matcher.group(2));
      ModuleInfo mi;
      try
      {
        mi = Sys.getRegistry().getModule(moduleName + "Test", runtimeProfile);
      }
      catch (ModuleNotFoundException mnfe)
      {
        mi = Sys.getRegistry().getModule(moduleName, runtimeProfile);
      }
      run(mi);
    }
    // {module}:{type}
    else if ((matcher = TYPE_PATTERN.matcher(target)).find())
    {
      String moduleName = matcher.group(1);
      String typeName = matcher.group(2);
      String testTypeSpec = moduleName + "Test:" + typeName;
      Type type;
      try
      {
        type = Sys.getType(testTypeSpec);
      }
      catch (ModuleNotFoundException mnfe)
      {
        type = Sys.getType(target);
      }
      run(type);
    }
    // {module}:{type}.{method}
    else if ((matcher = TYPE_METHOD_PATTERN.matcher(target)).find())
    {
      String typeSpec = matcher.group(1);
      String moduleName = matcher.group(2);
      String typeName = matcher.group(3);
      String methodName = matcher.group(4);

      Type type;
      try
      {
        type = Sys.getType(moduleName + "Test:" + typeName);
      }
      catch (ModuleNotFoundException mnfe)
      {
        type = Sys.getType(typeSpec);
      }
      Method method = getAnyMethodNamed(type.getTypeClass(), methodName);
      run(type, method);
    }
    // {module}:{type}./regex/
    else if ((matcher = TYPE_METHOD_REGEX_PATTERN.matcher(target)).find())
    {
      String typeSpec = matcher.group(1);
      String moduleName = matcher.group(2);
      String typeName = matcher.group(3);
      String pattern = matcher.group(4);

      Type type;
      try
      {
        type = Sys.getType(moduleName + "Test:" + typeName);
      }
      catch (ModuleNotFoundException mnfe)
      {
        type = Sys.getType(typeSpec);
      }
      run(type, Pattern.compile(pattern));
    }
    // com.tridium.ClassName
    else if (CLASS_PATTERN.matcher(target).find())
    {
      run(Sys.loadType(findClassFromUnknownModule(target)));
    }
    //com.tridium.ClassName#method
    else if ((matcher = CLASS_METHOD_PATTERN.matcher(target)).find())
    {
      Class<?> clazz = findClassFromUnknownModule(matcher.group(1));
      Method method = getAnyMethodNamed(clazz, matcher.group(3));
      run(Sys.loadType(clazz), method);
    }
    //com.tridium.ClassName#/regex/
    else if ((matcher = CLASS_METHOD_REGEX_PATTERN.matcher(target)).find())
    {
      Class<?> clazz = findClassFromUnknownModule(matcher.group(1));
      Pattern pattern = Pattern.compile(matcher.group(3));
      run(Sys.loadType(clazz), pattern);
    }
    else if ((matcher = REGEX_PATTERN.matcher(target)).find())
    {
      runMatching(Pattern.compile(matcher.group(1)));
    }
    else
    {
      throw new IllegalArgumentException("Unrecognized target " + target);
    }
  }

  private Class<?> findClassFromUnknownModule(String className)
  {
    for (ModuleInfo info : Sys.getRegistry().getModules())
    {
      Sys.loadModule(info.getModuleName());
      for (NModule module : Nre.getModuleManager().loadModuleParts(info.getModuleName()))
      {
        try
        {
          return module.loadClass(className);
        }
        catch (Exception ignore) {}
      }
    }
    throw new IllegalArgumentException("could not find class " + className);
  }

  /**
   * Run all the tests on installed on this VM.
   */
  public void runAll()
  {
    ngRunner.run();
  }

  /**
   * Run all TestNg tests matching the given regex, using the format
   * {@code com.package.ClassName#methodName}.
   * @param pattern pattern to match
   */
  public void runMatching(Pattern pattern)
  {
    ngRunner.run(pattern);
  }

  /**
   * Run all the tests in the specified module.
   */
  public void run(ModuleInfo moduleInfo)
  {
    ngRunner.run(moduleInfo);
  }

  /**
   * Run all the tests in the specified module set.
   */
  public void run(ModuleInfo [] moduleInfos)
  {
    ngRunner.run(moduleInfos);
  }

  /**
   * Run all the methods of the specified test type.
   */
  public void run(Type type)
  {
    ngRunner.run(type);
  }

  /**
   * Run the specified test method.
   */
  public void run(Type type, Method method)
  {
    ngRunner.run(type, method);
  }

  /**
   * Run all methods matching {@code pattern} on the specified type
   *
   * @param type Type to run
   * @param pattern Pattern to match methods against
   *
   * @since Niagara 4.10U8, Niagara 4.13U3, Niagara 4.14
   */
  public void run(Type type, Pattern pattern)
  {
    ngRunner.run(type, pattern);
  }

  /**
   * Run the targets specified in order.
   *
   * @param targets List of targets accepted by {@link #run(String)}
   *
   * @throws NoSuchMethodException If any of {@code targets} is invalid
   */
  public void run(String... targets)
    throws NoSuchMethodException
  {
    int targetCount = targets.length;
    if (targetCount == 0)
    {
      throw new IllegalArgumentException("Must provide at least one target");
    }
    if (targetCount == 1)
    {
      run(targets[0]);
    }
    else
    {
      // For multiple targets, we don't want to kill threads or exit on failure regardless
      // of configuration until the last one
      boolean exitOnFailure = ngRunner.isExitOnFailure();
      boolean killThreadsOnExit = ngRunner.isKillThreadsOnExit();
      ngRunner.setExitOnFailure(false);
      ngRunner.setKillThreadsOnExit(false);


      // NCCB-61258: If multiple targets are specified, they need to output into
      // unique directories. Capture the default directory and output each target
      // into a subdirectory
      String currentOutputDirectory = ngRunner.getOutputDirectory();

      for (int i = 0; i < targetCount - 1; i++)
      {
        ngRunner.setReportDirectory(currentOutputDirectory + File.separatorChar + "target." + i);
        run(targets[i]);
      }

      ngRunner.setExitOnFailure(exitOnFailure);
      ngRunner.setKillThreadsOnExit(killThreadsOnExit);
      ngRunner.setReportDirectory(currentOutputDirectory + File.separatorChar + "target." + (targetCount - 1));
      run(targets[targetCount - 1]);
    }
  }

////////////////////////////////////////////////////////////////
// Main
////////////////////////////////////////////////////////////////

  /**
   * Dump command line usage.
   */
  public static void usage()
  {
    System.out.println();
    System.out.println("usage:");
    System.out.println("  test <target> [target ... target] [testng options]");

    System.out.println("target:");
    System.out.println("  all");
    System.out.println("  <module>");
    System.out.println("  <module-runtimeProfile>");
    System.out.println("  <module>:<type>");
    System.out.println("  <module>:<type>.<method>");
    System.out.println("  <module>:<type>./regex to match against methods of <type>/");
    System.out.println("  <com.package>.<BTestClass>");
    System.out.println("  <com.package>.<BTestClass>#<method>");
    System.out.println("  <com.package>.<BTestClass>#/regex to match against methods of <BTestClass>/");
    System.out.println("  /<regex to match against the com.package.BTestClass#method format>/");

    System.out.println("testng options:");
    System.out.println("  -v:<n>                  Set TestNG output verbosity level (1 - 10)");
    System.out.println("  -output:<path>          Set the location for TestNG output");
    System.out.println("  -groups:<a,b,c>         Comma-separated list of TestNG group names to test");
    System.out.println("  -excludegroups:<a,b,c>  Comma-separated list of TestNG group names to skip");
    System.out.println("  -skipHtmlReport         Flag to disable HTML report generation");
    System.out.println("  -generateJunitReport    Flag to enable JUnit XML report generation");
    System.out.println("  -benchmark              Print the 50 highest duration tests and test suites on exit");
    System.out.println("  -loopCount:<n>          Run target(s) in a loop n times (1 - 1000000)");
    System.out.println();
  }

  /**
   * Command line entry point.
   */
  public static void main(String[] rawArgs)
  {
    Nre.boot();

    if (rawArgs.length > 0 && rawArgs[0].equals(TestRunner.class.getName()))
    {
      rawArgs = ArrayUtil.removeOne(rawArgs, 0);
    }
    CommandLineArguments args = new CommandLineArguments(rawArgs);

    if (args.hasHelpOption() || args.parameters.length == 0)
    {
      usage();
      throw new RuntimeException();
    }

    runTests(args);
  }

  public static void runTests(CommandLineArguments args)
  {
    runTests(args, false);
  }

  public static synchronized void runTests(CommandLineArguments args, boolean watchMode)
  {
    instance = new TestRunner();
    try
    {
      // Check for and set TestNG options
      setTestNGOptions(args, watchMode);
      instance.run(args.parameters);
    }
    catch (Throwable e)
    {
      String target = String.join(", ", args.parameters);
      System.err.println();
      System.err.println("SEVERE [testrunner] invalid test target(s): " + target);
      e.printStackTrace();
      System.err.println();
    }
    finally
    {
      instance = null;
    }
  }

  private static void setTestNGOptions(CommandLineArguments args, boolean watchMode)
  {
    if (args.hasOption("skipHtmlReport"))
    {
      ngRunner.setSkipHtmlReport(true);
    }

    if (args.hasOption("generateJunitReport"))
    {
      ngRunner.setGenerateJunitReport(true);
    }

    if (args.hasOption("groups"))
    {
      String groups = args.getOption("groups");
      if (groups != null)
      {
        ngRunner.setGroups(groups);
      }
    }

    if (args.hasOption("excludegroups"))
    {
      String excludegroups = args.getOption("excludegroups");
      if (excludegroups != null)
      {
        ngRunner.setExcludeGroups(excludegroups);
      }
    }

    if (args.hasOption("v"))
    {
      int level = 1;
      // Use the verbosity value if we want to vary the level
      String verbosity = args.getOption("v");
      if (verbosity != null)
      {
        level = Integer.parseInt(verbosity);
        if (level < 0)
        {
          level = 0;
        }
        if (level > 10)
        {
          level = 10;
        }
      }

      ngRunner.setVerbosity(level);
    }

    if (args.hasOption("loopCount"))
    {
      int loopCount = 1;
      String loops = args.getOption("loopCount");
      if (loops != null)
      {
        loopCount = Integer.parseInt(loops);
        if (loopCount < 0)
        {
          loopCount = 0;
        }
        if (loopCount > 1000000)
        {
          loopCount = 1000000;
        }
      }

      ngRunner.setLoopCount(loopCount);
    }

    if (args.hasOption("output"))
    {
      String output = args.getOption("output");
      if (output != null)
      {
        ngRunner.setReportDirectory(output);
      }
    }

    if (watchMode)
    {
      ngRunner.setKillThreadsOnExit(false);
      ngRunner.setExitOnFailure(false);
    }

    if (args.hasOption("benchmark"))
    {
      ngRunner.setBenchmark(true);
    }
  }
  
  private static Method getAnyMethodNamed(Class<?> cls, String methodName) 
    throws NoSuchMethodException
  {
    return Arrays.stream(cls.getMethods())
                 .filter(it -> it.getName().equals(methodName))
                 .findAny()
                 .orElseThrow(() -> new NoSuchMethodException(cls.getName() + "." + methodName));
  }

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private static TestRunnerNg ngRunner = new TestRunnerNg();
  private static TestRunner instance;

  private static final Pattern MODULE_PATTERN = Pattern.compile("^\\w+$");
  private static final Pattern MODULE_PART_PATTERN = Pattern.compile("^(\\w+)-(\\w+)$");
  private static final Pattern TYPE_PATTERN = Pattern.compile("^(\\w+):(\\w+)$");
  private static final Pattern TYPE_METHOD_PATTERN = Pattern.compile("^((\\w+):(\\w+))\\.(\\w+)$");
  private static final Pattern TYPE_METHOD_REGEX_PATTERN = Pattern.compile("^((\\w+):(\\w+))\\./([^/]+)/$");
  private static final Pattern CLASS_PATTERN = Pattern.compile("^([\\w]+\\.)+\\w+$");
  private static final Pattern CLASS_METHOD_PATTERN = Pattern.compile("^(([\\w]+\\.)+\\w+)#(\\w+)$");
  private static final Pattern CLASS_METHOD_REGEX_PATTERN = Pattern.compile("^(([\\w]+\\.)+\\w+)#/([^/]+)/$");
  private static final Pattern REGEX_PATTERN = Pattern.compile("^/([^/]+)/$");
}
