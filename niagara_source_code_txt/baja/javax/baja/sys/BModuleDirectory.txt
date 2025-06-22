/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.baja.category.BCategoryMask;
import javax.baja.file.BFileSpace;
import javax.baja.file.BIDirectory;
import javax.baja.file.BIFile;
import javax.baja.file.BIFileStore;
import javax.baja.file.FilePath;
import javax.baja.file.zip.BZipSpace;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.UnresolvedException;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.FileUtil;
import javax.baja.security.BPermissions;
import javax.baja.space.BSpace;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

@NiagaraType
/**
 * Represents a directory in a BModule whose file children are elements in
 * one of its NModules' zip space and whose directory children are other
 * BModuleDirectory instances.
 *
 * @author Matt Boon
 * @since Niagara 4.0
 */
public class BModuleDirectory
  extends BObject
  implements BIFileStore, BIFile, BIDirectory
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BModuleDirectory(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BModuleDirectory.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BModuleDirectory(BModule module, FilePath filePath, BIDirectory parent)
  {
    this.module = module;
    this.filePath = filePath;
    this.parent = parent;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public String getModuleName()
  {
    return module.getModuleName();
  }

  public void addFile(FilePath pathToAdd, BZipSpace zipSpace)
  {
    int depth = 0;
    if (pathToAdd.depth() < this.filePath.depth() + 1)
    {
      throw new IllegalArgumentException(String.format("Depth of path to is too short %s(%d) < %s(%d)", pathToAdd.toString(), pathToAdd.depth(), this.filePath.toString(), this.filePath.depth() + 1));
    }
    while(depth < this.filePath.depth())
    {
      if (!pathToAdd.nameAt(depth).equals(this.filePath.nameAt(depth)))
      {
        throw new IllegalArgumentException();
      }
      depth++;
    }
    BModuleDirectory addTo = this;
    while (depth < pathToAdd.depth() - 1)
    {
      addTo = addTo.makeDirectoryChild(pathToAdd.nameAt(depth));
      depth++;
    }
    if (!addTo.zipSpaceByChildFileName.containsKey(pathToAdd.getName()))
    {
      addTo.zipSpaceByChildFileName.put(pathToAdd.getName(), zipSpace);
    }
  }

  public BModuleDirectory findDirectory(FilePath path)
  {
    if (path.depth() < this.filePath.depth())
    {
      return null;
    }

    int depth = 0;

    // skip past parents of my file path
    while(depth < this.filePath.depth())
    {
      if (!path.nameAt(depth).equals(this.filePath.nameAt(depth)))
      {
        return null;
      }
      depth++;
    }

    // given path matches my path up to my path's depth, now walk
    // the rest of the given path
    BModuleDirectory dir = this;
    while (depth < path.depth())
    {
      dir = dir.childDirByName.get(path.nameAt(depth));
      if (dir == null)
      {
        return null;
      }
      depth++;
    }
    return dir;
  }

  public BZipSpace findZipSpace(FilePath path)
  {
    if (path.depth() < this.filePath.depth() + 1)
    {
      return null;
    }
    BModuleDirectory parentDir = findDirectory(path.getParent());
    if (parentDir == null)
    {
      return null;
    }
    if (parentDir.zipSpaceByChildFileName.containsKey(path.getName()))
    {
      return parentDir.zipSpaceByChildFileName.get(path.getName());
    }
    return null;
  }

  public BIFileStore getChildStore(String fileName)
  {
    if (childDirByName.containsKey(fileName))
    {
      return childDirByName.get(fileName);
    }
    else if (zipSpaceByChildFileName.containsKey(fileName))
    {
      return zipSpaceByChildFileName.get(fileName).findStore(filePath.merge(fileName));
    }
    return null;
  }

////////////////////////////////////////////////////////////////
// Implementation
////////////////////////////////////////////////////////////////

  private BModuleDirectory makeDirectoryChild(String childName)
  {
    if (childDirByName.containsKey(childName))
    {
      return childDirByName.get(childName);
    }
    else
    {
      BModuleDirectory result = new BModuleDirectory(module, filePath.merge(childName), this);
      childDirByName.put(childName, result);
      return result;
    }
  }

////////////////////////////////////////////////////////////////
// Object
////////////////////////////////////////////////////////////////

  @Override
  public String toString(Context cx)
  {
    return String.format("%s %s",super.toString(cx), filePath.toString());
  }

////////////////////////////////////////////////////////////////
// BIDirectory
////////////////////////////////////////////////////////////////

  /**
   * Get the list of containing files.
   */
  @Override
  public BIFile[] listFiles()
  {
    List<BIFile> results = new ArrayList<>();

    results.addAll(childDirByName.values());

    zipSpaceByChildFileName.entrySet()
      .stream()
      .map(entry -> entry.getValue().findFile(filePath.merge(entry.getKey())))
      .forEach(results::add);

    return results.toArray(new BIFile[results.size()]);
  }

////////////////////////////////////////////////////////////////
// BIProtected
////////////////////////////////////////////////////////////////

  /**
   * Get the set of permissions available based on the
   * specified context.  If the context is non-null and
   * has a non-null user then this method must return
   * {@code cx.getUser().getPermissionsFor(this)}.
   * If the context is null then typically this method
   * should return {@code BPermissions.all}.  If this
   * happens to be a proxy object within a remote session,
   * then this method should return a cached instance
   * of BPermissions based on the credentials used to
   * establish the session.  Under no circumstances should
   * this method return null or make a network call.
   */
  @Override
  public BPermissions getPermissions(Context cx)
  {
    return module.getPermissions(cx);
  }

  /**
   * Return true if the specified target object has read permission.
   * It is up to the implementation to decide whether operator or
   * admin read permission should be checked.
   */
  @Override
  public boolean canRead(OrdTarget cx)
  {
    return module.canRead(cx);
  }

  /**
   * Return true if the specified target object has write permission.
   * It is up to the implementation to decide whether operator or
   * admin write permission should be checked.
   */
  @Override
  public boolean canWrite(OrdTarget cx)
  {
    return false;
  }

  /**
   * Return true if the specified target object has invoke permission.
   * It is up to the implementation to decide whether operator or
   * admin invoke permission should be checked.
   */
  @Override
  public boolean canInvoke(OrdTarget cx)
  {
    return false;
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  /**
   * Get the name which uniquely identifies this node in its
   * parent.  This name must be a valid slot name.
   */
  @Override
  public String getNavName()
  {
    return filePath.getName();
  }

  /**
   * Get the display text of the navigation node.
   */
  @Override
  public String getNavDisplayName(Context cx)
  {
    return filePath.getName();
  }

  /**
   * Get a short description of the nav node.  Return
   * null if no description is available.
   */
  @Override
  public String getNavDescription(Context cx)
  {
    return filePath.getName();
  }

  /**
   * Get the parent navigation node.
   */
  @Override
  public BINavNode getNavParent()
  {
    return parent;
  }

  /**
   * If this node knows that it has no children, then
   * return false.  If the node has children or will
   * find out during a call to getNavChildren(), then
   * return true.
   */
  @Override
  public boolean hasNavChildren()
  {
    return true;
  }

  /**
   * Get the child by the specified name, or
   * return null if not found.
   */
  @Override
  public BINavNode getNavChild(String navName)
  {
    if (childDirByName.containsKey(navName))
    {
      return childDirByName.get(navName);
    }
    else if (zipSpaceByChildFileName.containsKey(navName))
    {
      return zipSpaceByChildFileName.get(navName).findFile(filePath.merge(navName));
    }
    return null;
  }

  /**
   * Get the child by the specified name, or throw
   * UnresolvedException if not found.
   */
  @Override
  public BINavNode resolveNavChild(String navName)
  {
    BINavNode result = getNavChild(navName);
    if (result == null)
    {
      throw new UnresolvedException();
    }
    return result;
  }

  /**
   * Get the children nodes for this navigation node.  Return
   * an array of length zero if there are no children.  Note
   * that this method does not take a Context, so it is possible
   * that the resulting list doesn't take security permissions
   * into account.  Use {@code BNavContainer.filter()} to
   * filter out nodes based on permissions; this is typically
   * done when using this method to do server side processing
   * such as a web servlet.
   */
  @Override
  public BINavNode[] getNavChildren()
  {
    return listFiles();
  }

  /**
   * Get the primary ord used to navigate to a view on
   * this object.  This should be an normalized absolute
   * ord.
   */
  @Override
  public BOrd getNavOrd()
  {
    return getAbsoluteOrd().normalize();
  }

  /**
   * Get the icon for the navigation node.
   */
  @Override
  public BIcon getNavIcon()
  {
    return ICON;
  }

////////////////////////////////////////////////////////////////
// BICategorizable
////////////////////////////////////////////////////////////////

  /**
   * Get the category mask to actually use for this object.  This
   * may be different from {@code getCategoryMask()} if using
   * category inheritance.
   */
  @Override
  public BCategoryMask getAppliedCategoryMask()
  {
    return module.getAppliedCategoryMask();
  }

  /**
   * Get the raw category mask for this object.  If the object
   * supports category inheritance, then this method should return
   * {@code BCategoryMask.NULL} and return the inherited
   * mask for {@code getAppliedCategoryMask()}.
   */
  @Override
  public BCategoryMask getCategoryMask()
  {
    return module.getCategoryMask();
  }

////////////////////////////////////////////////////////////////
// BISpaceNode
////////////////////////////////////////////////////////////////

  /**
   * Get the space containing this node, or null if unmounted.
   */
  @Override
  public BSpace getSpace()
  {
    return module;
  }

  /**
   * A space node is mounted if it is parented by a mounted
   * space.  Mounted nodes have a host absolute ord and return
   * non-null for getSpace(), getSession(), and getHost().
   */
  @Override
  public boolean isMounted()
  {
    return true;
  }

  /**
   * Get an ord relative to the session.  Return null if not
   * mounted.  This should be space.ordInSession + ordInSpace.
   */
  @Override
  public BOrd getOrdInSession()
  {
    return BOrd.make(module.getOrdInSession(), getOrdInSpace());
  }

  /**
   * Get the ord which identifies this entry in its space.
   * This ord should be relative with the space as the base.
   * If not mounted then return null.
   */
  @Override
  public BOrd getOrdInSpace()
  {
    return BOrd.make(filePath);
  }

  /**
   * If this object is currently contained by the current Mark
   * and the mark is set to pending move, then this method returns
   * true.  In user interfaces this flag should be used to render
   * the object grayed out to illustrate that a cut operation has
   * been performed on the object, but that a paste operation
   * is needed to complete the move.
   */
  @Override
  public boolean isPendingMove()
  {
    return false;
  }

  /**
   * Set the pending move flag.
   */
  @Override
  public void setPendingMove(boolean pendingMove)
  {
    throw new UnsupportedOperationException();
  }

////////////////////////////////////////////////////////////////
// BIFile
////////////////////////////////////////////////////////////////

  /**
   * Get the file's backing store.
   */
  @Override
  public BIFileStore getStore()
  {
    return this;
  }

  /**
   * Set the file's backing store.
   */
  @Override
  public void setStore(BIFileStore store)
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Get the mime type string for this file.
   */
  @Override
  public String getMimeType()
  {
    return null;
  }

  /**
   * Get the size of the file in bytes, or
   * return -1 if not a data file.
   */
  @Override
  public long getSize()
  {
    return -1;
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
    return module;
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
   * the same as {@code getFilePath().getName()}.
   */
  @Override
  public String getFileName()
  {
    return filePath.getName();
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
    return true;
  }

  /**
   * Get the last modification time of this
   * file as a BAbsTime instance.  Return
   * BAbsTime.NULL if last modified unknown.
   */
  @Override
  public BAbsTime getLastModified()
  {
    return BAbsTime.NULL;
  }

  /**
   * Sets file's lastModified absTime to nearest second.
   *
   * @since Niagara 4.0
   */
  @Override
  public boolean setLastModified(BAbsTime absTime) throws IOException
  {
    return false;
  }

  /**
   * Is the file readonly.
   */
  @Override
  public boolean isReadonly()
  {
    return true;
  }

  /**
   * Get the permissions for the specified file using the user
   * from the specified context.  This is a delegation from
   * BIFile's implementation of BIProtected.getPermissions().
   */
  @Override
  public BPermissions getPermissions(BIFile file, Context cx)
  {
    return module.getPermissions(cx);
  }

  /**
   * Get an input stream to read the contents
   * of this file.
   *
   * @throws java.io.IOException if the file is not
   *                             not readable.
   */
  @Override
  public InputStream getInputStream() throws IOException
  {
    throw new IOException("Can't get input stream from directory");
  }

  /**
   * Read the contents of this file fully into a
   * byte array.  Implementers should use FileUtil.read().
   *
   * @throws java.io.IOException if this file is
   *                             not readable or an error occurs
   *                             during the read.
   */
  @Override
  public byte[] read() throws IOException
  {
    throw new IOException("Can't get file contents for directory");
  }

  /**
   * Call {@code getFileSpace().delete(getFilePath())}.
   */
  @Override
  public void delete() throws IOException
  {
    throw new IOException("Directory not writable");
  }

  /**
   * Get an output stream to write the file.
   * The caller is responsible for invoking close()
   * on the OutputStream.
   *
   * @throws java.io.IOException if this file is
   *                             not writable.
   */
  @Override
  public OutputStream getOutputStream() throws IOException
  {
    throw new IOException("Directory not writable");
  }

  /**
   * Write the specified contents to this file.
   * Implementers should use FileUtil.write().
   *
   * @throws java.io.IOException if this file is
   *                             not writable or an error occurs
   *                             during the write.
   */
  @Override
  public void write(byte[] content) throws IOException
  {
    throw new IOException("Directory not writable");
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
    throw new IOException("Directory has no CRC");
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final BModule module;
  private final FilePath filePath;
  private final BIDirectory parent;

  private final Map<String, BModuleDirectory> childDirByName = new TreeMap<>();
  private final Map<String, BZipSpace> zipSpaceByChildFileName = new TreeMap<>();

  private static final BIcon ICON = BIcon.std("folder.png");

}
