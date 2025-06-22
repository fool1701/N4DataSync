/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.baja.category.BICategorizable;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BIProtected;
import javax.baja.space.BISpaceNode;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BIComparable;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * <p>
 * BIFile is the interface implemented by BObjects which
 * have file like semantics.  Some files are directories
 * which organize a BFileSpace into a tree.  Some files
 * contain data which can streamed, read, and written.  A
 * BIFile doesn't necessarily exclude the possibility that
 * a file is both a directory and a data container.  In
 * general directory/data semantics should be defined by
 * implementing BIDataFile and/or BIDirectory.
 * </p>
 * <p>
 * The class hierarchy of BIFile is used to define file
 * content types such as BTextFile, BHtmlFile, etc.  This
 * provides the ability to use the registry for discovery
 * of user agents.
 * </p>
 * <p>
 * Every BIFile has a matching implementation of BIFileStore
 * which provides pluggable functionality for file I/O.  All
 * the methods which are implementated by BIFileStore are also
 * provided on BIFile as a convenience.
 * </p>
 *
 * @author    Brian Frank on 24 Jan 03
 * @version   $Revision: 16$ $Date: 9/17/07 3:23:22 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIFile
  extends BINavNode, BISpaceNode, BIComparable, BICategorizable, BIProtected
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BIFile(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the file's backing store.
   */
  BIFileStore getStore();

  /**
   * Set the file's backing store.
   */
  void setStore(BIFileStore store);

  /**
   * Return {@code getStore().getFileSpace()}.
   */
  BFileSpace getFileSpace();

  /**
   * Return {@code getStore().getFilePath()}.
   */
  FilePath getFilePath();

  /**
   * Return {@code getStore().getFileName()}.
   */
  String getFileName();

  /**
   * Return {@code getStore().getExtension()}.
   */
  String getExtension();

  /**
   * Return {@code getStore().isDirectory()}.
   */
  boolean isDirectory();

  /**
   * Get the mime type string for this file.
   */
  String getMimeType();

  /**
   * Return {@code getStore().getSize()}.
   */
  long getSize();

  /**
   * Return {@code getStore().getLastModified()}.
   */
  BAbsTime getLastModified();

  /**
   * Return {@code getStore().isReadonly()}.
   */
  boolean isReadonly();

  /**
   * Return {@code getStore().getInputStream()}.
   */
  InputStream getInputStream()
    throws IOException;

  /**
   * Return {@code getStore().read()}.
   */
  byte[] read()
    throws IOException;

  /**
   * Call {@code getFileSpace().delete(getFilePath())}.
   */
  void delete()
    throws IOException;

  /**
   * Return {@code getStore().getOutputStream()}.
   */
  OutputStream getOutputStream()
    throws IOException;

  /**
   * Call {@code getStore().write()}.
   */
  void write(byte[] content)
    throws IOException;

  /**
   * Return {@code getStore().equals()}.
   */
  boolean equals(Object object);

  /**
   * Return comparison of file name with directories
   * taking precedence over data files.
   * @since Niagara 4.13
   */
  @Override
  default int compareTo(Object other)
  {
    if (this == other)
    {
      return 0;
    }

    final BIFile otherFile = (BIFile)other;
    final BIFileStore thisStore = getStore();
    final BIFileStore otherStore = otherFile.getStore();
    if (thisStore != otherStore)
    {
      BFileSpace thisSpace = thisStore.getFileSpace();
      BFileSpace otherSpace = otherStore.getFileSpace();
      if (thisSpace != otherSpace)
      {
        // Most file spaces cannot be cast to Comparable; a ClassCastException is expected.
        @SuppressWarnings("unchecked")
        int result = ((Comparable<BFileSpace>)thisSpace).compareTo(otherSpace);
        if (result != 0)
        {
          return result;
        }
      }
    }
    final boolean otherIsDirectory = otherFile.isDirectory();
    if (isDirectory() != otherIsDirectory)
    {
      return otherIsDirectory ? 1 : -1;
    }
    return getFilePath().compareTo(otherFile.getFilePath());
  }
}
