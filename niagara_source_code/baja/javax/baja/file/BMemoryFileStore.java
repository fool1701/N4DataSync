/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.ByteBuffer;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BMemoryStore is a BIFileStore implementation which uses
 * a memory buffer for reading and writing.
 *
 * @author    Brian Frank       
 * @creation  17 Mar 03
 * @version   $Revision: 2$ $Date: 3/28/05 9:22:56 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BMemoryFileStore
  extends BAbstractFileStore
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BMemoryFileStore(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMemoryFileStore.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public BMemoryFileStore(BFileSpace space, FilePath path)
  {
    super(space, path);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  /**
   * Store file object associated with this memory store.
   */
  public void setFile(BIFile f)
  {
    this.f = f;
  }

  /**
   * Get file object associated with this memory store.
   */
  public BIFile getFile()
  {
    return f;
  }


  /**
   * Return false.
   */
  @Override
  public boolean isDirectory()
  {
    return false;
  }
  
  /**
   * Return false.
   */
  @Override
  public boolean isReadonly()
  {
    return false;
  }

  /**
   * Return size of memory buffer.
   */
  @Override
  public long getSize()
  {   
    return buffer.getLength();
  }

  /**
   * Return BAbsTime for last time the buffer was written to.
   */
  @Override
  public BAbsTime getLastModified()
  {
    return lastModified ;
  }

  /**
   * Return buffer contents.
   */
  @Override
  public byte[] read()
    throws IOException
  {
    return buffer.toByteArray();
  }
  
  /**
   * Return InputStream for buffer.
   */
  @Override
  public final InputStream getInputStream()
    throws IOException
  {
    return new ByteArrayInputStream(read());
  }  
  
  /**
   * Get output stream to write new buffer.
   */
  @Override
  public final OutputStream getOutputStream()
     throws IOException
  {
    lastModified = BAbsTime.make();
    buffer = new ByteBuffer();
    return buffer.getOutputStream();
  }
    
////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////  


  /**
   * Return true if object same instance.
   */
  public boolean equals(Object object)
  {
    return this == object;
  }

  @Override
  public int hashCode()
  {
    return super.hashCode();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  BAbsTime lastModified = BAbsTime.now();
  ByteBuffer buffer = new ByteBuffer();
  BIFile f = null;
}
