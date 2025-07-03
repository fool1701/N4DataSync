/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.nre.util;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Logger;

import com.tridium.nre.util.NiagaraFiles;

/**
 * DefaultFileCopy.java
 *
 * @author Bill Smith
 * @creation 6/17/2014
 */
public class DefaultFileCopy
{
  public static void copyFile(String fileName)
    throws IllegalStateException, IOException
  {
    copyFile(fileName, false);
  }

  public static void copyFile(String fileName, boolean overWrite)
    throws IllegalStateException, IOException
  {
    try
    {
      File src = SecurityUtil.resolveChrootPath(NiagaraFiles.getNiagaraHomeDefaultsPath(), fileName);
      File target = SecurityUtil.resolveChrootPath(NiagaraFiles.getNiagaraUserHomeEtcPath(), fileName);

      // if target already exists, skip
      if (target.exists())
      {
        if (overWrite)
          FileUtil.delete(target);
        else
          return;
      }

      Logger.getLogger("nre").fine("copying default file for '" + target.toString() + "'");

      File parent = target.getParentFile();
      if (!parent.exists())
      {
        if (!parent.mkdirs())
        {
          Logger.getLogger("nre").warning("failed to create directory '" + parent + "', may be unable to copy default file for '" + fileName + "'");
        }
      }

      FileUtil.copyFile(src, target);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      throw e;
    }
  }

  public static void copyDir(String dirName, boolean overWrite)
    throws IllegalStateException, IOException
  {
    try
    {
      String niagaraHome = AccessController.doPrivileged((PrivilegedAction<String>)
        () -> System.getProperty("niagara.home"));
      String niagaraUserHome = AccessController.doPrivileged((PrivilegedAction<String>)
        () -> System.getProperty("niagara.user.home"));

      if (niagaraHome == null || niagaraUserHome == null)
        throw new IllegalStateException("niagara environment variables not properly set");

      File target = SecurityUtil.resolveChrootPath(niagaraUserHome + File.separator + "etc", dirName);
      File src = SecurityUtil.resolveChrootPath(niagaraHome + File.separator + "defaults", dirName);

      // if target already exists, skip
      if (target.exists())
      {
        if (overWrite)
          FileUtil.delete(target);
        else
          return;
      }

      Logger.getLogger("nre").fine("copying default file for '" + target.toString() + "'");

      FileUtil.copyDir(src, target);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

}
