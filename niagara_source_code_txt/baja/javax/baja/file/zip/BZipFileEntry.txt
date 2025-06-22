/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.zip;

import java.io.*;
import java.util.zip.*;

import javax.baja.file.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BZipFileEntry is a BIFileStore implementation for 
 * local files using java.util.zip.ZipEntry.
 *
 * @author    Brian Frank       
 * @creation  24 Jan 03
 * @version   $Revision: 5$ $Date: 8/14/09 10:38:47 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BZipFileEntry
  extends BAbstractFileStore
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.zip.BZipFileEntry(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BZipFileEntry.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct.
   */
  public BZipFileEntry(BZipSpace space, FilePath path, ZipEntry zipEntry)
  {
    super(space, path);
    this.zipEntry = zipEntry;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Get the ZipEntry this instance wraps.
   */
  public ZipEntry getZipEntry()
  {
    return zipEntry;
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Return true.
   */
  @Override
  public boolean isReadonly()
  {
    return true;
  }

  /**
   * Return <code>getZipEntry().getSize()</code>.
   */
  @Override
  public long getSize()
  {   
    return zipEntry.getSize();
  }

  /**
   * Return BAbsTime for File.lastModified().
   */
  @Override
  public BAbsTime getLastModified()
  {
    if (modified == null) 
      modified = BAbsTime.make(zipEntry.getTime());
    return modified;
  }
    
  /**
   * Return InputStream for ZipEntry.
   */
  @Override
  public InputStream getInputStream()
    throws IOException
  {
    BZipSpace space = (BZipSpace)getFileSpace();
    return space.getInputStream(zipEntry);
  }
  
  /**
   * Return generated CRC
   *
   * @since Niagara 3.5
   */
  @Override
  public long getCrc()
    throws IOException
  {
    return zipEntry.getCrc();
  }
    
////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////  

  /**
   * Return hash code.
   */
  public int hashCode()
  {
    return zipEntry.hashCode();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  ZipEntry zipEntry;
  BAbsTime modified;

}
