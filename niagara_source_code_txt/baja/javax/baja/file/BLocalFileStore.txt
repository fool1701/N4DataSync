/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BLocalStore is a BIFileStore implementation for
 * local files using java.io.File.
 *
 * @author    Brian Frank
 * @creation  24 Jan 03
 * @version   $Revision: 10$ $Date: 8/14/09 10:38:00 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BLocalFileStore
  extends BAbstractFileStore
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BLocalFileStore(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLocalFileStore.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public BLocalFileStore(BFileSpace space, FilePath path, File file)
  {
    super(space, path);
    this.file = file;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the java.io.File this instance wraps.
   */
  public File getLocalFile()
  {
    return file;
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Return <code>file.isDirectory()</code>.
   */
  @Override
  public boolean isDirectory()
  {
    if (!initialized) initialize();
    return isDirectory;
  }

  /**
   * Return <code>!file.canWrite()</code>.
   */
  @Override
  public boolean isReadonly()
  {
    if (!initialized) initialize();
    return isReadonly;
  }

  /**
   * Return -1 if a directory, otherwise file.length.
   */
  @Override
  public long getSize()
  {
    if (!initialized) initialize();
    if (isDirectory) return -1;
    return file.length();
  }

  /**
   * Return BAbsTime for File.lastModified().
   */
  @Override
  public BAbsTime getLastModified()
  {
    if (!initialized) initialize();
    if (isRemovable) return BAbsTime.NULL;
    return BAbsTime.make(file.lastModified());
  }

  /**
   * Set File lastModified and return success
   *
   * @since Niagara 3.5
   */
  @Override
  protected boolean doSetLastModified(BAbsTime absTime) throws IOException
  {
    if (!initialized) initialize();
    return file.setLastModified(absTime.getMillis());
  }

  /**
   * Return <code>new FileInputStream(file)</code>.
   */
  @Override
  public InputStream getInputStream()
    throws IOException
  {
    return new FileInputStream(file);
  }

  /**
   * Return <code>new FileOutputStream(file)</code>.
   */
  @Override
  public OutputStream getOutputStream()
    throws IOException
  {
    return new FileOutputStream(file);
  }

  public void delete()
    throws IOException
  {
    try
    {
      BajaFileUtil.delete(file);
    }
    catch (Exception e)
    {
      throw new IOException("Cannot delete: " + file);
    }
  }

  /**
   * Return calculated CRC
   *
   * @since Niagara 3.5
   */
  @Override
  public long getCrc()
    throws IOException
  {
    if (isDirectory()) return -1;
    return BajaFileUtil.getCrc(getInputStream());
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Return <code>file.hashCode()</code>.
   */
  public int hashCode()
  {
    if (!initialized) initialize();
    return hashCode;
  }

  /**
   * Return true if object is a BLocalFileStore and
   * java.io.Files are equal.
   */
  public boolean equals(Object object)
  {
    if (object instanceof BLocalFileStore)
    {
      BLocalFileStore f = (BLocalFileStore)object;
      return file.equals(f.file);
    }
    return false;
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * Lazily access aspects of the file, but cache
   * the result to prevent frequent disk access.
   */
  private void initialize()
  {
    AccessController.doPrivileged(new InitializePrivilegedAction());
  }

  private class InitializePrivilegedAction
    implements PrivilegedAction<Object>
  {
    @Override
    public Object run()
    {
      if (!initialized)
      {
        BLocalFileStore.this.initialized = true;
        BLocalFileStore.this.isRemovable = file.getPath().equals("A:\\");
        if (isRemovable)
        {
          // since just about any method access on a floppy drive
          // causes that immensely annoying dialog on Windows, just
          // assume it is a directory and not readonly
          BLocalFileStore.this.isDirectory = true;
          BLocalFileStore.this.isReadonly = false;
        }
        else
        {
          BLocalFileStore.this.isDirectory = file.isDirectory();
          try
          {
            BLocalFileStore.this.isReadonly = !file.canWrite();
          }
          catch(AccessControlException ace)
          {
            //NCCB-9777: Be wary of silent AccessControlException
            BLocalFileStore.this.isReadonly = true;
          }
        }
        BLocalFileStore.this.hashCode   = file.hashCode();
      }

      return null;
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  File file;
  boolean initialized;
  boolean isDirectory;
  boolean isReadonly;
  boolean isRemovable;  // hack for Window's "There is no disk in drive"
  int hashCode;
}
