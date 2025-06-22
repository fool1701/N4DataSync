/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.testng;

import java.util.ArrayList;
import java.util.List;

import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.test.BTestNg;

import org.testng.TestNG;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.Utils;
import org.testng.internal.annotations.IAnnotationFinder;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/**
 * Subclass of TestNG to perform some preprocessing of Niagara test module
 * classes before they are loaded.
 *
 * @author Logan Byam
 * @creation Feb 21, 2013
 * @since Niagara 3.8
 */
public class CustomTestNG
    extends TestNG
{
  public CustomTestNG(boolean useDefaultListeners) {
    this(useDefaultListeners, BTestNg.class);
  }

  public CustomTestNG(boolean useDefaultListeners, Class<?> testClass) {
    super(useDefaultListeners);
    this.testClass = testClass;
  }

  private XmlClass toXmlClass(Class<?> clazz) {
    Class<?>[] classes = new Class<?>[] { clazz };
    XmlClass[] xmlClasses = Utils.classesToXmlClasses(classes);
    return xmlClasses[0];
  }

  /**
   * Convert the test class into an XmlSuite, with a suite name derived
   * from the test class type spec.

   * @throws IllegalArgumentException is input class does not extend BTestNg
   */
  public XmlSuite createXmlSuite(Class<?> clazz) {
    if (!testClass.isAssignableFrom(clazz))
    {
      throw new IllegalArgumentException(
        String.format("Only %s subclasses are permissible", testClass.getName()));
    }

    XmlSuite xmlSuite = new XmlSuite();
    XmlClass xmlClass = toXmlClass(clazz);

    String suiteName;
    String testName = getDefaultTestName();

    Type type = Sys.getType(clazz);
    suiteName = type.getModule().getModuleName() + "_" + type.getTypeName();

    IAnnotationFinder finder = getConfiguration().getAnnotationFinder();
    ITestAnnotation test = finder.findAnnotation(clazz, ITestAnnotation.class);

    if (test != null) {
      suiteName = Utils.defaultIfStringEmpty(test.getSuiteName(), suiteName);
      testName = Utils.defaultIfStringEmpty(test.getTestName(), testName);
    }

    xmlSuite.setName(suiteName);

    XmlTest xmlTest = new XmlTest(xmlSuite);
    xmlTest.setName(testName);
    xmlTest.getXmlClasses().add(xmlClass);

    return xmlSuite;
  }

  /**
   * Converts each input test class into an individual XmlSuite. This ensures
   * that when loading multiple classes, each class is run to completion
   * (befores-tests-afters) before starting on the next test class.
   *
   * <p>(The default behavior of TestNG is to run all class befores, all class
   * tests, and all class afters.)
   */
  @Override
  @SuppressWarnings("rawtypes")
  public void setTestClasses(Class[] classes) {
    List<XmlSuite> suites = new ArrayList<>(classes.length);

    for (Class<?> clazz : classes) {
      suites.add(createXmlSuite(clazz));
    }

    setXmlSuites(suites);
  }

  private final Class<?> testClass;
}
