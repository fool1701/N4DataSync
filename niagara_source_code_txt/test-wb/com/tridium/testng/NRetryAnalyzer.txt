/*
 * Copyright 2019 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.testng;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * A ready-made implementation of the TestNg @Test(retryAnalyzer) interface
 * that can be used to retry transiently failing tests. Good candidates for this
 * annotation would be tests that are only failing because of tight timing constraints /
 * timeouts that are not present in production environments, but might regularly occur
 * in the CI environment.
 *
 * The standard implementation employs a 3-strikes and you're out metric. More sophisticated
 * retry analyzers should be delegated to individual tests.
 *
 * If there is demand for it, we can make a more sophisticated implementation that uses
 * a custom annotation to define the retry count (or use PA to find some well known
 * static in the result).
 *
 * See https://testng.org/doc/documentation-main.html#rerunning for more information.
 */
public class NRetryAnalyzer implements IRetryAnalyzer
{
  @Override
  public boolean retry(ITestResult result)
  {
    if (currentRetryCount.incrementAndGet() >= MAX_ATTEMPTS)
    {
      currentRetryCount.decrementAndGet();
      return false;
    }
  
    LOGGER.log(Level.WARNING, () -> "NRetryAnalyzer retrying test '" + result + "'...");
    return true;
  }
  
  static
  {
    //Default to 3 attempts
    int maxAttemptsValue = 3;
  
    //Initialize from system if available
    try
    {
      String maxAttemptsProperty = AccessController.doPrivileged((PrivilegedAction<String>) () -> System.getProperty("niagara.testng.retryAnalyzer.maxAttempts"));
      if (maxAttemptsProperty != null)
      {
        maxAttemptsValue = Integer.parseInt(maxAttemptsProperty);
      }
    }
    catch (NumberFormatException ignored)
    {}
  
    //Require at least 1 attempt
    MAX_ATTEMPTS = Math.max(1, maxAttemptsValue);
  }
  
  private final AtomicInteger currentRetryCount = new AtomicInteger(0);
  public static final int MAX_ATTEMPTS;
  private static final Logger LOGGER = Logger.getLogger("TestNG");
}

