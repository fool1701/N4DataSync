/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import java.io.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.*;
import javax.baja.sys.*;
import javax.baja.user.*;

/**
 * BAbstractFileStore provides a default base class upon 
 * which to build BIFileStore implementations.
 *
 * @author    Brian Frank       
 * @creation  24 Jan 03
 * @version   $Revision: 10$ $Date: 8/14/09 10:37:46 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BAbstractFileStore
  extends BObject
  implements BIFileStore
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BAbstractFileStore(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractFileStore.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file in the specified space.
   */
  public BAbstractFileStore(BFileSpace space, FilePath path)
  {
    /* it seems useful to allow null spaces and paths
       especially with BMemoryFileStore
    if (space == null) throw new NullPointerException("null space");
    if (path == null) throw new NullPointerException("null path");
    */
    this.space = space;
    this.path = path;
  }

////////////////////////////////////////////////////////////////
// BIFile
////////////////////////////////////////////////////////////////

  /**
   * Return the space passed to the constructor.
   */
  @Override
  public BFileSpace getFileSpace()
  {
    return space;
  }
  
  /**
   * Get the file path passed to the constructor.
   */
  @Override
  public FilePath getFilePath()
  {
    return path;
  }

  /**
   * Return <code>getFilePath().getName()</code>.
   */
  @Override
  public String getFileName()
  {
    return getFilePath().getName();
  }

  /**
   * Defaults to <code>FileUtil.getExtension(getFileName())</code>.
   */
  @Override
  public String getExtension()
  {
    return BajaFileUtil.getExtension(getFileName());
  }

  /**
   * Defaults to false.
   */
  @Override
  public boolean isDirectory()
  {
    return false;
  }

  /**
   * Defaults to return -1.
   */
  @Override
  public long getSize()
  {
    return -1;
  }

  /**
   * Defaults to return BAbsTime.NULL.
   */
  @Override
  public BAbsTime getLastModified()
  {
    return BAbsTime.NULL;
  }
  
  /**
   * Sets file's lastModified absTime to nearest second.
   * 
   * @since Niagara 3.5
   */
  @Override
  public final boolean setLastModified(BAbsTime absTime) throws IOException
  {
    absTime = BAbsTime.make(absTime.getYear(), 
                            absTime.getMonth(), 
                            absTime.getDay(), 
                            absTime.getHour(), 
                            absTime.getMinute(), 
                            absTime.getSecond(), 
                            0, 
                            absTime.getTimeZone());
    
    return doSetLastModified(absTime);
  }
  
  /**
   * Sets file's lastModified absTime.
   * 
   * Defaults to <code>throw new IOException</code>
   */
  protected boolean doSetLastModified(BAbsTime absTime) throws IOException
  {
    throw new IOException();
  }
  
    
  /**
   * Defaults to <code>throw new IOException()</code>.
   */
  @Override
  public InputStream getInputStream()
    throws IOException
  {
    throw new IOException();
  }
  
  /**
   * Defaults to <code>FileUtil.read(this)</code>
   */  
  @Override
  public byte[] read()
    throws IOException
  {
    return BajaFileUtil.read(this);
  }
  
  /**
   * Defaults to <code>throw new IOException()</code>.
   */
  @Override
  public OutputStream getOutputStream()
    throws IOException
  {
    throw new IOException();
  }
  
  /**
   * Defaults to <code>FileUtil.write(this, content)</code>
   */
  @Override
  public void write(byte[] content)
    throws IOException
  {
    BajaFileUtil.write(this, content);
  }

  /**
   * If there is a user associated with the specified context then
   * return {@code cx.getUser().getPermissionsFor(file)}.  This
   * method automatically always takes into account the current
   * value of <code>isReadonly()</code>.
   */
  @Override
  public BPermissions getPermissions(BIFile file, Context cx)
  {   
    // assume full access                    
    BPermissions permissions = BPermissions.all;
    
    // check if we have a user
    if (cx != null && cx.getUser() != null)  
    { 
      // get user from context
      BUser user = cx.getUser();    
      
      // If we have a user, then we apply the following rules
      //  1) files in a BModule are automatically granted opRead
      //  2) if the user is not a super user, automatically deny
      //     any permissions outside of the station home
      //  3) any remaining cases map to user's configured permissions  
      //     via the file's categories
      if (BModule.isModuleFileSpace(space))
        permissions = BPermissions.operatorRead;
      else if ((!user.getPermissions().isSuperUser() && path.getAbsoluteMode() != FilePath.STATION_HOME_ABSOLUTE))
        permissions = BPermissions.none;
      else
        permissions = cx.getUser().getPermissionsFor(file);                        
    }
    
    // if the file is readonly, then take away write permission
    if (isReadonly())
      permissions = BPermissions.make(permissions.getMask() & READONLY_MASK);
    
    // return calculated permissions    
    return permissions;
  }

  private static final int READONLY_MASK = ~(BPermissions.OPERATOR_WRITE | BPermissions.ADMIN_WRITE);

  /**
   * To string.
   */
  @Override
  public String toString(Context cx)
  {
    return String.valueOf(path);
  }
  
  /**
   * Return calculated CRC
   * 
   * Defaults to return -1
   */
  @Override
  public long getCrc()
    throws IOException
  {
    return -1;
  }
        
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BFileSpace space;
  private FilePath path;
  
}
