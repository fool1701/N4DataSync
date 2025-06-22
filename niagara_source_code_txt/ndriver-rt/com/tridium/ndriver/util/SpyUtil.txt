/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.baja.spy.SpyWriter;

/**
 * SpyUtil contains utilities to create spy output from a Java object using
 * introspection. <p> This utility provides a convenient mechanism to collect
 * statistical data for display in spy pages.  A container object can declare
 * public variables which can be incremented as events occur.  If data must be
 * massaged for display remove the public declaration from the variable and
 * provide a public getter method that returns a String.  The "get" will be
 * removed from the getter method name to provide the table entry name.
 * <p>
 * Example:<p>
 * <pre>
 *   // Container object with startTime set when counts started and
 *   // sendRequest which is incremented as messages sent.
 *   public static class Statistics
 *   {
 *     long startTime = 0;
 *     public String getStartTime() { return BAbsTime.make(startTime).toLocalTime().toString(null); }
 *     public long sendRequest = 0;
 *   }
 *
 *   // Monitored objects spy()
 *   public void spy(SpyWriter out)
 *    throws Exception
 *   {
 *      SpyUtil.spy(out, "Statistics", stats);
 *   }
 *
 *   // Displayed Spy table
 *   Statistics
 *    startTime      13-Jun-12 4:33 PM EDT
 *    sendRequest    5
 *  </pre>
 *
 * @author Robert A Adams
 * @creation June 13, 2012
 */
public final class SpyUtil
{
  private SpyUtil() {}

  /**
   * Write a spy table for the specified object with the specified title. Table
   * will have entry for all public getters (method begins with "get" and takes
   * no arguments) and all public attributes.
   */
  public static void spy(SpyWriter out, String title, Object o)
    throws Exception
  {
    out.startProps(title);

    // Look for getters
    Method[] ma = o.getClass().getMethods();
    for (int i = 0; i < ma.length; ++i)
    {
      Method m = ma[i];
      if (m.getReturnType() != String.class || m.getParameterTypes().length > 0)
      {
        continue;
      }

      String nam = m.getName();
      if (!nam.substring(0, 3).equals("get"))
      {
        continue;
      }
      StringBuilder sb = new StringBuilder(nam.substring(3));
      sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));

      String val;
      try
      {
        val = ma[i].invoke(o, new Object[0]).toString();
      }
      catch (Exception e)
      {
        continue;
      }
      out.prop(sb.toString(), val);
    }

    // Process public attributes
    Field[] fa = o.getClass().getFields();
    for (int i = 0; i < fa.length; ++i)
    {
      String nam = fa[i].getName();
      String val;
      try
      {
        val = fa[i].get(o).toString();
      }
      catch (Exception e)
      {
        continue;
      }
      out.prop(nam, val);
    }

    out.endProps();
  }
}
