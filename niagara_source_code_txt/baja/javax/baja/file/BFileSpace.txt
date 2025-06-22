/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.baja.agent.AgentList;
import javax.baja.category.BCategoryMask;
import javax.baja.category.BCategoryService;
import javax.baja.category.BICategorizable;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.security.BIProtected;
import javax.baja.security.BPermissions;
import javax.baja.security.PermissionException;
import javax.baja.space.BSpace;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconText;

/**
 * BFileSpace defines a tree of BIFiles using a specific
 * implementation of BIFilesStore.
 *
 * @author    Brian Frank
 * @creation  24 Jan 03
 * @version   $Revision: 26$ $Date: 10/28/10 2:47:00 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BFileSpace
  extends BSpace
  implements BIFileSpace, BIDirectory, BICategorizable, BIProtected
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BFileSpace(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFileSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public BFileSpace(String name, LexiconText lexText)
  {
    super(name, lexText);
  }

  /**
   * Constructor.
   */
  public BFileSpace(String name)
  {
    super(name);
  }

////////////////////////////////////////////////////////////////
// File Operations
////////////////////////////////////////////////////////////////

  /**
   * Make a directory for the specified path or return
   * the existing directory.  This creates zero or more
   * directories as needed.  Check security permissions
   * if context maps to a user.
   */
  public abstract BDirectory makeDir(FilePath path, Context cx)
    throws IOException;

  /**
   * Convenience for <code>makeDir(path, null)</code>
   */
  @Override
  public final BDirectory makeDir(FilePath path)
    throws IOException
  {
    return makeDir(path, null);
  }

  /**
   * Make a file for the specified path or return the
   * existing file.  This creates zero or more directories
   * as needed.  Check security permissions if context
   * maps to a user.
   */
  public abstract BIFile makeFile(FilePath path, Context cx)
    throws IOException;

  /**
   * Convenience for <code>makeFile(path, null)</code>
   */
  @Override
  public final BIFile makeFile(FilePath path)
    throws IOException
  {
    return makeFile(path, null);
  }

  /**
   * Move/rename the specified file.  If the "to" path is not
   * absolute, then it is relative to the from.getParent().
   * Check security permissions if context maps to a user.
   */
  public abstract void move(FilePath from, FilePath to, Context cx)
    throws IOException;

  /**
   * Convenience for <code>move(from, to, null)</code>
   */
  @Override
  public final void move(FilePath from, FilePath to)
    throws IOException
  {
    move(from, to, null);
  }

  /**
   * Recursively delete the specified file.  Ignore
   * the call if the path doesn't exist.  Check security
   * permissions if context maps to a user.
   */
  public abstract void delete(FilePath path, Context cx)
    throws IOException;

  /**
   * Convenience for <code>delete(path, null)</code>
   */
  @Override
  public final void delete(FilePath path)
    throws IOException
  {
    delete(path, null);
  }

////////////////////////////////////////////////////////////////
// Pathing
////////////////////////////////////////////////////////////////

  /**
   * Get an absolute ord for a file path within this space.
   */
  @Override
  public BOrd getAbsoluteOrd(FilePath filePath)
  {
    return appendFilePathToOrd(getAbsoluteOrd(), filePath);
  }

  /**
   * Get an ord relative to the host for a file path within this space.
   */
  @Override
  public BOrd getOrdInHost(FilePath filePath)
  {
    return appendFilePathToOrd(getOrdInHost(), filePath);
  }

  /**
   * Get an ord relative to the session for a file path within this space.
   */
  @Override
  public BOrd getOrdInSession(FilePath filePath)
  {
    return appendFilePathToOrd(getOrdInSession(), filePath);
  }

  /**
   * Join a file space ord with the given file path.  This relies on
   * the fact that the a file space ord should not end with slash, and
   * a filepath should.
   */
  protected BOrd appendFilePathToOrd(BOrd baseOrd, FilePath filePath)
  {
    if (baseOrd == null) return null;
    if (baseOrd.toString().length() == 0) return BOrd.make(filePath);
    return BOrd.make(baseOrd.toString() + filePath.getBody());
  }

////////////////////////////////////////////////////////////////
// Resolution
////////////////////////////////////////////////////////////////

  /**
   * Routes to findStore() and makeFile().
   */
  @Override
  public BIFile findFile(FilePath path)
  {
    BIFileStore store = findStore(path);
    if (store != null) return makeFile(store);
    return null;
  }

  /**
   * Map a FilePath to an instanceof of BIFileStore.  If the
   * path doesn't map to a file in this space, then return null.
   */
  public abstract BIFileStore findStore(FilePath path);

  /**
   * This method calls findFile(path).  If null is
   * returned then UnresolvedException() is thrown.
   */
  @Override
  public BIFile resolveFile(FilePath path)
  {
    BIFile file = findFile(path);
    if (file == null) throw new UnresolvedException(""+path);
    return file;
  }

  /**
   * Get the child file of the specified parent or
   * return null if not found.
   */
  public abstract BIFile getChild(BIFile parent, String childName);

  /**
   * Get the children files of the specified parent
   * or return an empty array.
   */
  public abstract BIFile[] getChildren(BIFile parent);

  /**
   * Given an implementation of file store, create the proper
   * type of BIFile.  The standard implementation of this method
   * uses the registry to map the store's file extension to
   * a type of BIFile.  Once the instance is created, the store
   * is set using BIFile.setStore().  If the store is a directory
   * then return an instance of BDirectory.  If the file has
   * no extension, then return an instance of BDataFile.
   */
  @Override
  public BIFile makeFile(BIFileStore store)
  {
    if (store.isDirectory())
      return new BDirectory(store);

    String ext = store.getExtension();
    if (ext != null && ext.length() > 0)
    {
      TypeInfo type = Sys.getRegistry().getFileTypeForExtension(ext);
      try
      {
        BIFile file = (BIFile)type.getInstance();
        file.setStore(store);
        return file;
      }
      catch(Throwable e)
      {
        BajaFileUtil.log.log(Level.SEVERE, "Cannot create file " + type + " for ext " + ext, e);
      }
    }

    return new BDataFile(store);
  }

////////////////////////////////////////////////////////////////
// Security
////////////////////////////////////////////////////////////////

  /**
   * Get the permissions for a FilePath.  This method works
   * for both existing paths and non-existing paths making
   * it useful to precheck operations which are going to create
   * new files.
   */
  @Override
  public BPermissions getPermissionsFor(FilePath path, Context cx)
  {
    BPermissions defaultPermissions = BPermissions.all;

    // short circuit if no user
    if (cx == null || cx.getUser() == null)
      return defaultPermissions;

    try
    {
      // create a dummy file with specified FilePath
      BIFileStore store = new BLocalFileStore(this, path, new File("dummy"));
      BIFile file = new BDataFile(store);
      return cx.getUser().getPermissionsFor(file);
    }
    catch(Exception e)
    {
      e.printStackTrace(); // should we dump this?
    }

    return defaultPermissions;
  }

  /**
   * If the result of <code>getPermissionsFor(path, cx)</code>
   * doesn't contain operator read then throw a PermissionException.
   */
  @Override
  public void checkReadPermission(FilePath path, Context cx)
  {
    if (!getPermissionsFor(path, cx).hasOperatorRead())
      throw new PermissionException("Missing operator read");
  }

  /**
   * If the result of <code>getPermissionsFor(path, cx)</code>
   * doesn't contain operator write then throw a PermissionException.
   */
  @Override
  public void checkWritePermission(FilePath path, Context cx)
  {
    if (!getPermissionsFor(path, cx).hasOperatorWrite())
      throw new PermissionException("Missing operator write");
  }

////////////////////////////////////////////////////////////////
// ICategorizable
////////////////////////////////////////////////////////////////

  /**
   * FileSpaces are mapped to categories by ord in <code>CategoryService.ordMap</code>.
   */
  @Override
  public BCategoryMask getCategoryMask()
  {
    return BCategoryService.getService().getCategoryMask(getNavOrd());
  }

  /**
   * FileSpaces are mapped to categories by ord in <code>CategoryService.ordMap</code>.
   */
  @Override
  public BCategoryMask getAppliedCategoryMask()
  {
    return BCategoryService.getService().getAppliedCategoryMask(getNavOrd());
  }

////////////////////////////////////////////////////////////////
// IProtected
////////////////////////////////////////////////////////////////

  @Override
  public BPermissions getPermissions(Context cx)
  {
    if (cx != null && cx.getUser() != null)
      return cx.getUser().getPermissionsFor(this);
    else
      return BPermissions.all;
  }

  @Override
  public boolean canRead(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().hasOperatorRead();
  }

  @Override
  public boolean canWrite(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().hasOperatorWrite();
  }

  @Override
  public boolean canInvoke(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().hasOperatorInvoke();
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.toTop("workbench:DirectoryList");
    agents.toTop("hx:HxDirectoryView");
    agents.toBottom("web:FileUploadView");
    return agents;
  }

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("drive.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final BIFile[] NO_FILES = new BIFile[0];

}
