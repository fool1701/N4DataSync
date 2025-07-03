/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.testng;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.TestException;
import com.tridium.nre.platform.OperatingSystemEnum;
import com.tridium.testng.annotation.Requires;
import com.tridium.testng.annotation.Requires.OsType;

/**
 * Custom TestNG listener to skip tests if the test indicates it requires an 
 * OS other than the current OS. 
 *
 * @author Garrett L. Ward on 25 Apr 2017
 * @since Niagara 4.4
 */
public class RequiresListener implements IInvokedMethodListener
{
  public RequiresListener()
  {
    this(OperatingSystemEnum.getOS());
  }
  
  public RequiresListener(OperatingSystemEnum currentOs)
  {
    this.currentOs = currentOs;
  }

  @Override
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult)
  {
    if (!method.isTestMethod() && !method.isConfigurationMethod())
    {
      return;
    }

    if (testResult.getStatus() == ITestResult.CREATED)
    {
      // Likely this test was skipped due to a configuration failure, don't try to
      // apply the listener to it
      return;
    }

    // always means always
    if (method.getTestMethod().isAlwaysRun())
    {
      return;
    }

    Method testMethod = method.getTestMethod().getConstructorOrMethod().getMethod();
    if (testMethod == null)
    {
      return;
    }

    Class<?> cls = testMethod.getDeclaringClass();
    Object testInstance = method.getTestMethod().getInstance();
    
    // Check class for requires annotation
    Requires testRequires = testMethod.getDeclaringClass().getAnnotation(Requires.class);
    if (testRequires != null)
    {
      checkPredicate(cls, testInstance, testRequires);
      checkOs(testRequires);
    }
    
    // Check method for requires annotation
    Requires methodRequires = testMethod.getAnnotation(Requires.class);
    if (methodRequires != null)
    {
      checkPredicate(cls, testInstance, methodRequires);
      checkOs(methodRequires);
    }
    
    // If we got this far, we're probably good. 
  }

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult) {}

  ////////////////////////////////////////////////////////////////
// Requirement checks
////////////////////////////////////////////////////////////////

  private void checkOs(Requires r)
  {
    if (r.os() == null || r.os().length == 0)
    {
      // We have no os requirement, so the OS requirement is always satisfied. 
      return;
    }
    
    for (OsType os : r.os())
    {
      if (currentOs == mapOsToOs(os))
      {
        // We're running on a supported OS platform, hurray?
        return;
      }
    }
    throw new SkipException("Skipping test because requested OS is not available");
  }
  
  private static void checkPredicate(Class<?> cls, Object instance, Requires r)
  {
    String predicate = r.predicate();
    if (predicate == null || predicate.isEmpty())
    {
      // we have no predicate, so the predicate is always satisfied. 
      return;
    }
    
    try
    {
      //Object instance = invokedMethod.getTestMethod().getInstance();
      Method predicateMethod = findMethod(cls, predicate);
      if (!predicateMethod.getReturnType().isAssignableFrom(Boolean.TYPE) 
        && !predicateMethod.getReturnType().isAssignableFrom(Boolean.class))
      {
        throw new TestException("Predicate " + predicate + " does not return boolean");
      }
      predicateMethod.setAccessible(true);
      Object result = predicateMethod.invoke(instance);
      if (result == null)
      {
        throw new TestException("Unexpected null result from predicate");
      }
      boolean res = (Boolean)result;
      if (!res)
      {
        throw new SkipException("Skipping test because predicate returned false");
      }
    }
    catch (NoSuchMethodException e)
    {
      throw new TestException("Invalid predicate " + predicate);
    }
    catch (IllegalAccessException|InvocationTargetException e)
    {
      throw new TestException("Unable to invoke method " + predicate + " on class " + cls.getCanonicalName(), e);
    }
  }
    
////////////////////////////////////////////////////////////////
// Utility methods
////////////////////////////////////////////////////////////////
 
  // If OperatingSystemEnum was a real enum, we could just use it directly
  // on the annotation. Oh well. 
  private static OperatingSystemEnum mapOsToOs(OsType os)
  {
    switch(os) {
      case QNX: return OperatingSystemEnum.qnx;
      case WIN32: return OperatingSystemEnum.windows;
      case LINUX: return OperatingSystemEnum.linux;
      default: return OperatingSystemEnum.unknown;
    }
  }
  
  /*
  Find a method by looking at a class and all its parent classes. 
   */
  private static Method findMethod(Class<?> cls, String methodName)
    throws NoSuchMethodException
  {
    Class<?> start = cls;
    Method m = null;
    do {
      try
      {
        m = start.getDeclaredMethod(methodName);
        break;
      }
      catch (NoSuchMethodException e)
      {
        start = start.getSuperclass();
      }
    } while (start != null);
    
    if (m == null)
    {
      throw new NoSuchMethodException();
    }
    return m;
  }
  
  private final OperatingSystemEnum currentOs;
}
