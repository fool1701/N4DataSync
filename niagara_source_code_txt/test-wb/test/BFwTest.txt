/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package test;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStation;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.test.BTest;
import javax.baja.test.TestException;

import com.tridium.nre.platform.PlatformUtil;

/**
 * @author    Brian Frank
 * @creation  1 Oct 00
 * @version   $Revision: 7$ $Date: 8/13/09 10:56:49 AM EDT$
 * @since     Niagara 3.0
 */
@NiagaraType
public abstract class BFwTest
  extends BTest
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $test.BFwTest(2979906276)1.0$ @*/
/* Generated Wed Jan 05 17:05:31 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFwTest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  public void println(String s)
  {
    System.out.println(s);
  }

  public void pause(int ms)
  {
    try
    {
      System.out.println("      Pausing " + ms + "ms...");
      Thread.sleep(ms);
    }
    catch (InterruptedException e)
    {
      throw new TestException(e.toString());
    }
  }

  public File getTempDir()
  {
    File dir = new File(Sys.getNiagaraUserHome(), "test/temp");
    if (!dir.exists())
    {
      dir.mkdirs();
    }
    return dir;
  }

  public static File getTestDir()
  {
    if (testDir == null)
    {
      testDir = new File(Sys.getNiagaraUserHome() + File.separator + "test" + File.separator + "files");
      if (!testDir.exists())
      {
        testDir.mkdirs();
      }
    }
    return testDir;
  }
  private static File testDir;

  public static boolean isEmbedded()
  {
    return AccessController.doPrivileged((PrivilegedAction<Boolean>)() -> PlatformUtil.getPlatformProvider().isEmbedded());
  }

////////////////////////////////////////////////////////////////
// Reflection
////////////////////////////////////////////////////////////////

  /**
   * Get a private or protected field for whitebox testing.
   */
  public static Object getField(Object o, String fieldName)
    throws Exception
  {
    Class<?> cls = o.getClass();
    Field field = null;
    while (cls != null)
    {
      try
      {
        field = cls.getDeclaredField(fieldName);
      }
      catch (Exception ignored)
      {}

      cls = cls.getSuperclass();
    }
    if (field == null)
    {
      throw new NoSuchFieldException(o.getClass().getName() + "." + fieldName);
    }
    field.setAccessible(true);
    return field.get(o);
  }

  /**
   * Set a private or protected field for whitebox testing.
   */
  public static void setField(Object o, String fieldName, Object value)
    throws Exception
  {
    Class<?> cls = o.getClass();
    Field field = null;
    while (cls != null)
    {
      try
      {
        field = cls.getDeclaredField(fieldName);
      }
      catch (Exception ignored)
      {}

      cls = cls.getSuperclass();
    }
    if (field == null)
    {
      throw new NoSuchFieldException(o.getClass().getName() + "." + fieldName);
    }
    field.setAccessible(true);
    field.set(o, value);
  }

  /**
   * Get a private or protected method for whitebox testing.
   */
  public static Method method(Object o, String methodName, Class<?>[] params)
    throws Exception
  {
    return method(o.getClass(), methodName, params);
  }

  /**
   * Get a private or protected method for whitebox testing.
   */
  public static Method method(Class<?> cls, String methodName, Class<?>[] params)
    throws Exception
  {
    Class<?> originalClass = cls;
    Method method = null;
    while (cls != null)
    {
      try
      {
        method = cls.getDeclaredMethod(methodName, params);
      }
      catch (Exception ignored)
      {}

      cls = cls.getSuperclass();
    }
    if (method == null)
    {
      throw new NoSuchMethodException(originalClass.getName() + "." + method);
    }
    method.setAccessible(true);
    return method;
  }

  /**
   * This was the old method to create a test station - it
   * is now just remapped to the BTest.getTestStation()
   */
  // NCCB-18407: Replace use of deprecated BTest methods in test-se
  @SuppressWarnings("deprecation")
  public BStation station()
  {
    return getTestStation();
  }
}
