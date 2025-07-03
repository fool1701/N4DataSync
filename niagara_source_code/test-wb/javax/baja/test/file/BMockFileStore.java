/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.test.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.baja.file.BFileSpace;
import javax.baja.file.BIFile;
import javax.baja.file.BIFileStore;
import javax.baja.file.FilePath;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.FileUtil;
import javax.baja.security.BPermissions;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BObject;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

@NiagaraType
/**
 * A file store for testing that may have no actual data behind it
 *
 * @author Matt Boon
 * @since Niagara 4.0
 */
public class BMockFileStore
  extends BObject
  implements BIFileStore
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.test.file.BMockFileStore(2979906276)1.0$ @*/
/* Generated Wed Jan 05 17:05:31 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMockFileStore.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BMockFileStore(BMockFileSpace space, String pathBody, boolean isDirectory)
  {
    this.filePath = new FilePath(pathBody);
    this.space = space;
    this.isDirectory = isDirectory;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public void setSize(long value)
  {
    this.size = value;
  }

  public void setReadonly(boolean value)
  {
    this.readonly = value;
  }

  public void setCrc(long value)
  {
    this.crc = value;
  }

  public void setPermissions(BPermissions value)
  {
    this.permissions = value;
  }

  /**
   * Set the input stream supplier for this file
   *
   * @param value supplies the input stream for this file
   */
  public void setInputStreamSupplier(Supplier<InputStream> value)
  {
    inputStreamSupplier = Optional.of(value);
  }

  /**
   * Set the input stream supplier for this file using an OutputStream consumer.
   */
  public void setInputStreamSupplier(Consumer<OutputStream> value)
  {
    setInputStreamSupplier(() ->
      {
        try
        {
          PipedOutputStream outputStream = new PipedOutputStream();
          PipedInputStream inputStream = new PipedInputStream(outputStream);
          value.accept(outputStream);
          return inputStream;
        }
        catch (IOException ioe)
        {
          throw new BajaRuntimeException(ioe);
        }
      });
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  @Override
  public String toString(Context cx)
  {
    return String.valueOf(filePath);
  }

////////////////////////////////////////////////////////////////
// BIFileStore
////////////////////////////////////////////////////////////////

  /**
   * Get the file space containing this file.
   * Return null if not mounted.
   */
  @Override
  public BFileSpace getFileSpace()
  {
    return space;
  }

  /**
   * Get the file path of this file in its space.  The
   * result of BISpaceEntry.getOrdInSpace() should match
   * this path query.  Return null if not mounted.
   */
  @Override
  public FilePath getFilePath()
  {
    return filePath;
  }

  /**
   * Get the simple file name.  This name should be
   * the same as <code>getFilePath().getName()</code>.
   */
  @Override
  public String getFileName()
  {
    return getFilePath().getName();
  }

  /**
   * Get the extension for this file. The extension
   * appears after the last '.' in the file name.
   * Return null if no '.' appears in the file name.
   * Implementers should use FileUtil.getExtension().
   */
  @Override
  public String getExtension()
  {
    return FileUtil.getExtension(getFileName());
  }

  /**
   * Return true if this a file that contains other files.
   */
  @Override
  public boolean isDirectory()
  {
    return isDirectory;
  }

  /**
   * Get the size of the file in bytes, or
   * return -1 if not a data file.
   */
  @Override
  public long getSize()
  {
    return size;
  }

  /**
   * Get the last modification time of this
   * file as a BAbsTime instance.  Return
   * BAbsTime.NULL if last modified unknown.
   */
  @Override
  public BAbsTime getLastModified()
  {
    return lastModified;
  }

  /**
   * Sets file's lastModified absTime to nearest second.
   *
   * @since Niagara 4.0
   */
  @Override
  public boolean setLastModified(BAbsTime absTime)
  {
    lastModified = absTime;
    return true;
  }

  /**
   * Is the file readonly.
   */
  @Override
  public boolean isReadonly()
  {
    return readonly;
  }

  /**
   * Get the permissions for the specified file using the user
   * from the specified context.  This is a delegation from
   * BIFile's implementation of BIProtected.getPermissions().
   */
  @Override
  public BPermissions getPermissions(BIFile file, Context cx)
  {
    return permissions;
  }

  /**
   * Returns an input stream for the file, if one has been set up using
   * {@link #setInputStreamSupplier(java.util.function.Supplier)}, otherwise throws
   * UnsupportedOperationException.
   *
   * @throws java.lang.UnsupportedOperationException
   */
  @Override
  public InputStream getInputStream() throws IOException
  {
    if (inputStreamSupplier.isPresent())
    {
      return inputStreamSupplier.get().get();
    }
    throw new UnsupportedOperationException();
  }

  @Override
  public byte[] read() throws IOException
  {
    return filePath.getBody().getBytes();
  }

  /**
   * @throws java.lang.UnsupportedOperationException
   */
  @Override
  public OutputStream getOutputStream() throws IOException
  {
    throw new UnsupportedOperationException();
  }

  /**
   * @throws java.lang.UnsupportedOperationException
   */
  @Override
  public void write(byte[] content) throws IOException
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Get the CRC of the contents of this file.
   *
   * @return CRC
   * @throws java.io.IOException
   * @since Niagara 4.0
   */
  @Override
  public long getCrc() throws IOException
  {
    return crc;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final FilePath filePath;
  private final boolean isDirectory;
  private long size = 0L;
  private long crc = 0L;
  private BAbsTime lastModified = BAbsTime.NULL;
  private boolean readonly = true;
  private BPermissions permissions = BPermissions.none;
  private BMockFileSpace space;
  private Optional<Supplier<InputStream>> inputStreamSupplier = Optional.empty();
}
