/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import javax.baja.category.BCategoryMask;
import javax.baja.category.BCategoryService;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.UnresolvedException;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.space.BSpace;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BAbstractFile provides a default base class upon which 
 * to build BIFile implementations.
 *
 * @author    Brian Frank       
 * @creation  24 Jan 03
 * @version   $Revision: 31$ $Date: 8/14/09 10:37:29 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BAbstractFile
  extends BObject
  implements BIFile
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BAbstractFile(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BAbstractFile(BIFileStore store)
  {
    setStore(store);
  }

  /**
   * Construct (must set store using setStore())..
   */
  public BAbstractFile()
  {
  }

////////////////////////////////////////////////////////////////
// BIFile
////////////////////////////////////////////////////////////////

  /**
   * Get the file's backing store.  A file store should
   * always be configured either with the constructor or
   * the setStore() method.
   */
  @Override
  public BIFileStore getStore()
  {
    return store;
  }

  /**
   * Set the file's backing store.
   */
  @Override
  public void setStore(BIFileStore store)
  {
    if (store == null) throw new NullPointerException("null store");
    this.store = store;
  }

  /**
   * Return <code>getStore().getFileSpace()</code>.  
   */
  @Override
  public BFileSpace getFileSpace()
  {
    return getStore().getFileSpace();
  }
  
  /**
   * Return <code>getStore().getFilePath()</code>.  
   */
  @Override
  public FilePath getFilePath()
  {
    return getStore().getFilePath();
  }

  /**
   * Return <code>getStore().getFileName()</code>.  
   */
  @Override
  public String getFileName()
  {
    return getStore().getFileName();
  }

  /**
   * Return <code>getStore().getExtension()</code>.  
   */
  @Override
  public String getExtension()
  {
    return getStore().getExtension();
  }

  /**
   * Return <code>getStore().isDirectory()</code>.  
   */
  @Override
  public boolean isDirectory()
  {
    return getStore().isDirectory();
  }

  /**
   * Return <code>getStore().getSize()</code>.  
   */
  @Override
  public long getSize()
  {
    return getStore().getSize();
  }

  /**
   * Return <code>getStore().getLastModified()</code>.  
   */
  @Override
  public BAbsTime getLastModified()
  {
    return getStore().getLastModified();
  }
  
  /**
   * Set the Abstract Store's lastModified absTime to the nearest second.
   * 
   * @since Niagara 3.5
   */
  public boolean setLastModified(BAbsTime absTime) throws IOException
  {
    if (getStore() instanceof BAbstractFileStore)
      return getStore().setLastModified(absTime);
    else
      throw new UnsupportedOperationException();
  }
  
  /**
   * Return <code>getStore().isReadonly()</code>.  
   */ 
  @Override
  public boolean isReadonly()
  {
    return getStore().isReadonly();
  }
  
  /**
   * Return <code>getStore().getInputStream()</code>.  
   */
  @Override
  public InputStream getInputStream()
    throws IOException
  {
    return getStore().getInputStream();
  }
  
  /**
   * Return <code>getStore().read()</code>.  
   */  
  @Override
  public byte[] read()
    throws IOException
  {
    return getStore().read();
  }

  /**
   * Call <code>getFileSpace().delete(getFilePath())</code>.
   */
  @Override
  public void delete()
    throws IOException
  {
    getFileSpace().delete(getFilePath());
  }
  
  /**
   * Return <code>getStore().getOutputStream()</code>.  
   */
  @Override
  public OutputStream getOutputStream()
    throws IOException
  {
    return getStore().getOutputStream();
  }
  
  /**
   * Call <code>getStore().write()</code>.  
   */
  @Override
  public void write(byte[] content)
    throws IOException
  {
    getStore().write(content);
  }

  /**
   * Default returns <code>"application/octet-stream"</code>
   */
  @Override
  public String getMimeType()
  {
    return "application/octet-stream";
  }
  
  /**
   * Return <code>getStore().getCrc()</code>.  
   * 
   * @since Niagara 3.5
   */
  public long getCrc()
    throws IOException
  {
    if (getStore() instanceof BAbstractFileStore)
      return getStore().getCrc();
    else
      throw new UnsupportedOperationException();
  }

////////////////////////////////////////////////////////////////
// BISpaceEntry
////////////////////////////////////////////////////////////////  

  /**
   * Return <code>getFileSpace()</code>.
   */
  @Override
  public BSpace getSpace()
  {
    return getFileSpace();
  }

  /**
   * Return <code>fileSpace.absoluteOrd + filePath</code>.
   */
  @Override
  public BOrd getAbsoluteOrd()
  {
    BFileSpace fs = getFileSpace();
    if (fs == null) return null;
    return fs.getAbsoluteOrd(getFilePath());
  }

  /**
   * Return <code>fileSpace.ordInHost + filePath</code>.
   */
  @Override
  public BOrd getOrdInHost()
  {
    BFileSpace fs = getFileSpace();
    if (fs == null) return null;
    return fs.getOrdInHost(getFilePath());
  }

  /**
   * Return <code>fileSpace.ordInSession + filePath</code>.
   */
  @Override
  public BOrd getOrdInSession()
  {
    BFileSpace fs = getFileSpace();
    if (fs == null) return null;
    return fs.getOrdInSession(getFilePath());
  }
    
  /**
   * Return <code>getOrdInSession()</code>.
   */
  @Override
  public BOrd getOrdInSpace()
  {
    return getOrdInSession();
  }

  /**
   * Return pending move flag.
   */
  @Override
  public boolean isPendingMove()
  {
    return pendingMove;
  }

  /**
   * Return pending move flag.
   */
  @Override
  public void setPendingMove(boolean pendingMove)
  {
    // likely not shared between trees
    this.pendingMove = pendingMove;
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  /**
   * Return <code>getFileName()</code>.
   */
  @Override
  public String getNavName()
  {
    return getFileName();
  }

  /**
   * Return <code>getNavName()</code>.
   */
  @Override
  public String getNavDisplayName(Context cx)
  {
    return getNavName();  
  }

  /**
   * Return <code>fileSpace.findFile(filePath.parent)</code>
   */
  @Override
  public BINavNode getNavParent()
  {
    FilePath parentPath = getFilePath().getParent();
    if (parentPath != null)
      return getFileSpace().findFile(parentPath);
    else return getFileSpace();
  }
  
  /**
   * Call getNavChild and if it returns null then
   * throw UnresolvedException.
   */
  @Override
  public BINavNode resolveNavChild(String navName)
  {
    BINavNode node = getNavChild(navName);
    if (node == null) throw new UnresolvedException(navName);
    return node; 
  }

  /**
   * Return <code>getAbsoluteOrd()</code>.
   */
  @Override
  public BOrd getNavOrd()
  {
    return getAbsoluteOrd();
  }
  
  /**
   * Default is to return getIcon().
   */
  @Override
  public BIcon getNavIcon()
  {
    return getIcon();
  }  

////////////////////////////////////////////////////////////////
// ICategorizable
////////////////////////////////////////////////////////////////
  
  /**
   * Files are mapped to categories by ord in <code>CategoryService.ordMap</code>.
   */
  @Override
  public BCategoryMask getCategoryMask()
  {
    return BCategoryService.getService().getCategoryMask(getNavOrd());
  }  

  /**
   * Files are mapped to categories by ord in <code>CategoryService.ordMap</code>.
   */
  @Override
  public BCategoryMask getAppliedCategoryMask()
  {
    try
    {
      return BCategoryService.getService().getAppliedCategoryMask(getNavOrd());
    }
    catch (ServiceNotFoundException ignore)
    {
      BajaFileUtil.log.log(Level.FINE, () -> String.format("Category service is unavailable, using default category mask for file %s", getNavOrd()));
      return BCategoryMask.DEFAULT;
    }
  }  

////////////////////////////////////////////////////////////////
// Security
////////////////////////////////////////////////////////////////

  /**
   * Call <code>getStore().getPermissions(this, cx)</code>.  
   */
  @Override
  public BPermissions getPermissions(Context cx)
  {                       
    return getStore().getPermissions(this, cx);
  }                            

  /**
   * Return if operator read is enabled.
   */
  @Override
  public boolean canRead(OrdTarget cx)
  {                   
    return cx.getPermissionsForTarget().has(BPermissions.OPERATOR_READ);
  }
  
  /**
   * Return if operator write is enabled.
   */
  @Override
  public boolean canWrite(OrdTarget cx)
  {                         
    return cx.getPermissionsForTarget().has(BPermissions.OPERATOR_WRITE);
  }

  /**
   * Return if operator invoke is enabled.
   */
  @Override
  public boolean canInvoke(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().has(BPermissions.OPERATOR_INVOKE);
  }


////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Get path string, guaranteed not to be null.
   */
  public String toPathString()
  {
    BOrd ord = getOrdInSession();
    if (ord != null) return ord.toString();
    return "?" + getType() + "?";
  }

  /**
   * Return <code>getStore().toString(cx)</code>.  
   */
  @Override
  public String toString(Context cx)
  {
    if (getStore() == null) return super.toString(cx);
    else return getStore().toString(cx);
  }
                                   
  /**
   * Return <code>getStore().hashCode()</code>.  
   */
  public int hashCode()
  {
    return getStore().hashCode();
  }

  /**
   * If object is a BIFile, then return if the stores
   * are equal.  
   */
  public boolean equals(Object object)
  {
    if (object instanceof BIFile)
    {
      BIFile file = (BIFile)object;
      return getStore().equals(file.getStore());
    }
    return false;
  }

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("file.png");

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////  

  /**
   * Dump slots and information common to all BComplex's.
   */
  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    try
    {
      // We need a way to restrict the file system for remote users.  
      // Without this, a remote user could go to the remote spy for 
      // the file system and see everything.
      // In such a case, we need to scope down the visibility to just the 
      // Sys home (and Station home).  getNavChildren() is called in the
      // spy case, so using a thread local variable will tell us when
      // to restrict the visibility.  The SpyWriter's context tells us if 
      // it is a remote user or not
      BFileSystem.threadLocalContext.set(out.getContext());
    
      super.spy(out);
      out.startProps();
      out.trTitle("File", 2);
      out.prop("fileName",       getFileName());
      out.prop("store.type",     getStore().getType());
      out.prop("store.toString", getStore().toString());
      out.prop("isDirectory",    isDirectory());
      out.prop("readonly",       isReadonly());
      out.prop("size",           ""+getSize());
      out.prop("lastModified",   getLastModified());
      out.trTitle("SpaceNode", 2);
      out.prop("isMounted",      isMounted());
      out.prop("host",           getHost());
      out.prop("session",        getSession());
      out.prop("space",          getSpace());
      out.prop("absoluteOrd",    getAbsoluteOrd());
      out.prop("ordInHost",      getOrdInHost());
      out.prop("ordInSession",   getOrdInSession());
      out.prop("ordInSpace",     getOrdInSpace());
      out.prop("navOrd",         getNavOrd());
      out.prop("permissions",    getPermissions(null));   
      if (getStore() instanceof BAbstractFileStore) 
        out.prop("crc", String.valueOf(getCrc()));
      out.endProps();
    
      BINavNode[] kids = getNavChildren();
      if (kids != null && kids.length > 0)
      {
        out.startTable(false);
        out.trTitle("Children", 1);
        for(int i=0; i<kids.length; ++i)
        {
          //out.tr("<a href='" + kids[i].getNavName() + "'>" + kids[i].toString() + "</a>");
          out.tr().td().a(kids[i].getNavName(), kids[i].toString()).endTd().endTr();
        }
        out.endTable();
      }
    }
    finally
    {
      BFileSystem.threadLocalContext.set(null);
    }
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BIFileStore store;
  private boolean pendingMove;

}
