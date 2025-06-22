/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import java.io.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.*;
import javax.baja.sys.*;

/**
 * BIFileStore is a pluggable implementation of file storage.  
 * It encapsulates implementation details for file I/O storage 
 * and meta-data.  BIFileStore implementations are bound to 
 * BFileSpace implementations.
 *
 * @author    Brian Frank       
 * @creation  24 Jan 03
 * @version   $Revision: 7$ $Date: 3/28/05 9:22:56 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIFileStore
  extends BInterface
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BIFileStore(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIFileStore.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the file space containing this file.  
   * Return null if not mounted.
   */
  BFileSpace getFileSpace();
  
  /**
   * Get the file path of this file in its space.  The
   * result of BISpaceEntry.getOrdInSpace() should match
   * this path query.  Return null if not mounted.
   */
  FilePath getFilePath();

  /**
   * Get the simple file name.  This name should be
   * the same as <code>getFilePath().getName()</code>.
   */
  String getFileName();

  /**
   * Get the extension for this file. The extension
   * appears after the last '.' in the file name.
   * Return null if no '.' appears in the file name.
   * Implementers should use FileUtil.getExtension().
   */
  String getExtension();

  /**
   * Return true if this a file that contains other files.
   */
  boolean isDirectory();

  /**
   * Get the size of the file in bytes, or 
   * return -1 if not a data file.
   */
  long getSize();

  /**
   * Get the last modification time of this
   * file as a BAbsTime instance.  Return
   * BAbsTime.NULL if last modified unknown.
   */
  BAbsTime getLastModified();

  /**
   * Sets file's lastModified absTime to nearest second.
   *
   * @since Niagara 4.0
   */
  boolean setLastModified(BAbsTime absTime)
    throws IOException;

  /**
   * Is the file readonly.
   */
  boolean isReadonly();

  /**
   * Get the permissions for the specified file using the user 
   * from the specified context.  This is a delegation from 
   * BIFile's implementation of BIProtected.getPermissions().  
   */
  BPermissions getPermissions(BIFile file, Context cx);
  
  /**
   * Get an input stream to read the contents
   * of this file.
   *
   * @throws IOException if the file is not
   *    not readable.
   */
  InputStream getInputStream()
    throws IOException;

  /**
   * Read the contents of this file fully into a
   * byte array.  Implementers should use FileUtil.read().
   *
   * @throws IOException if this file is
   *    not readable or an error occurs
   *    during the read.
   */
  byte[] read()
    throws IOException;

  /**
   * Get an output stream to write the file.
   * The caller is responsible for invoking close() 
   * on the OutputStream.
   *
   * @throws IOException if this file is 
   *    not writable.
   */
  OutputStream getOutputStream()
    throws IOException;
  
  /**
   * Write the specified contents to this file.
   * Implementers should use FileUtil.write().
   *
   * @throws IOException if this file is 
   *    not writable or an error occurs
   *    during the write.
   */
  void write(byte[] content)
    throws IOException;

  /**
   * Get the CRC of the contents of this file.
   *
   * @return CRC
   * @throws IOException
   * @since Niagara 4.0
   */
  long getCrc()
    throws IOException;
}
