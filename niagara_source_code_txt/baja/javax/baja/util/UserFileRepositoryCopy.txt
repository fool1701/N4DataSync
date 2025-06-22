/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.util;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Logger;
import javax.baja.nre.util.FileUtil;

/**
 * Copies the files over from {@code <niagara_home>/userFileRepo} to
 * {@code <niagara_user_home>/fileRepo}
 *
 * @author Melanie Coggan
 * @creation 2015-05-01
 * @since Niagara 4.0
 */
public class UserFileRepositoryCopy
{
////////////////////////////////////////////////////////////////
// File copying
////////////////////////////////////////////////////////////////
  public static void copyFileRepo()
    throws IllegalStateException
  {
    Logger.getLogger("nre").fine("Copying files from niagara.home's userFileRepo");
    String niagaraHome = AccessController.doPrivileged((PrivilegedAction<String>) () ->
      System.getProperty("niagara.home"));
    String niagaraUserHome = AccessController.doPrivileged((PrivilegedAction<String>) () ->
      System.getProperty("niagara.user.home"));

    if (niagaraHome == null || niagaraUserHome == null)
      throw new IllegalStateException("Niagara environment variables not properly set");

    File source = new File(niagaraHome + NHOME_REPO_NAME);
    File target = new File(niagaraUserHome + NUSERHOME_REPO_NAME);

    try
    {
      if (source.exists())
        copy(source, target);
    }
    catch (IOException e)
    {
      Logger.getLogger("nre").warning("Error copying files from niagara.home's userFileRepo");
    }
  }

  private static void copy(File source, File target)
    throws IOException
  {
    Logger.getLogger("nre").fine("Copying user repo file: \n\t" + source.getCanonicalPath() + "\n\t -> " + target.getCanonicalPath());

    if (source.isDirectory())
      copyDir(source, target);
    else if (!target.exists())
      FileUtil.copyFile(source, target);
    else
      Logger.getLogger("nre").fine("File already exists. Skipping copy.");
  }

  private static void copyDir(File source, File target)
    throws IOException
  {
    if (!target.exists() && !target.mkdirs())
      throw new IOException("Cannot make dir: " + target);

    File[] children = source.listFiles();
    if (children != null)
    {
      for (File child : children)
        copy(child, new File(target, child.getName()));
    }

    //noinspection ResultOfMethodCallIgnored
    target.setLastModified(source.lastModified());
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////
  private static final String NHOME_REPO_NAME = File.separator + "userFileRepo";
  private static final String NUSERHOME_REPO_NAME = File.separator + "shared" + File.separator + "fileRepo";
}
