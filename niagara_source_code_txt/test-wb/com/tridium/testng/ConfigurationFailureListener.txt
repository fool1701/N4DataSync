/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.testng;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ConfigurationFailureListener extends TestListenerAdapter
{
  @Override
  public void onConfigurationFailure(ITestResult tr)
  {
    System.err.println("Configuration failure: " + tr.getMethod());
    tr.getThrowable().printStackTrace();
  }
}
