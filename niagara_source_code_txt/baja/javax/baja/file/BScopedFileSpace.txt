/*
 * Copyright 2009 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.baja.category.BCategoryMask;
import javax.baja.category.BCategoryService;
import javax.baja.naming.BOrd;
import javax.baja.naming.UnresolvedException;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconText;

/**
 * BScopedFileSpace is a file space for the local machine's
 * file system which is scoped down to a particular directory.
 * All access is restricted to files residing within the scope.
 *
 * @author    Scott Hoye
 * @creation  9 Dec 09
 * @version   $Revision: 3$ $Date: 2/25/10 4:44:57 PM EST$
 * @since     Niagara 3.5
 */
@NiagaraType
public class BScopedFileSpace
  extends BFileSpace
  implements BIScopedFileSpace
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BScopedFileSpace(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BScopedFileSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public static final BScopedFileSpace SYS_HOME = new BScopedFileSpace(new FilePath("!"), "sysFileSpace", LexiconText.make("baja", "nav.sysHome"));
  public static final BScopedFileSpace USER_HOME = new BScopedFileSpace(new FilePath("~"), "userFileSpace", LexiconText.make("baja", "nav.userHome"));
  //STATION_HOME does not exist in non-station VMs
  public static final BScopedFileSpace STATION_HOME;
  public static final BScopedFileSpace PROTECTED_STATION_HOME;
  static
  {
    if (Sys.getStationHome() != null)
    {
      PROTECTED_STATION_HOME = new BScopedFileSpace(new FilePath("^^"), "protectedStationFileSpace", LexiconText.make("baja", "nav.protectedStationHome"));
      STATION_HOME = new BScopedFileSpace(new FilePath("^"), "stationFileSpace", LexiconText.make("baja", "nav.stationHome"));
    }
    else
    {
      PROTECTED_STATION_HOME = null;
      STATION_HOME = null;
    }
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor, the scope of this file system is specified.
   * The scope must resolve to an existing directory within the full
   * file system.  Therefore the provided scope must be a full absolute
   * FilePath resolving to a directory within the full (non-scoped) file system.
   */
  public BScopedFileSpace(FilePath scope, String name, LexiconText lexText)
  {
    super(name, lexText);

    this.scope = scope;

    try
    {
      init();
    }
    catch(Exception e)
    {
      BajaFileUtil.log.log(Level.SEVERE, "Error initializing scoped file space", e);
    }
  }


////////////////////////////////////////////////////////////////
// ScopedFileSpace
////////////////////////////////////////////////////////////////

  /**
   * Initialization of the scope's root directory.
   */
  private void init()
  {
    AccessController.doPrivileged(new InitPrivilegedAction());
  }

  private class InitPrivilegedAction
    implements PrivilegedAction<Object>
  {
    @Override
    public Object run()
    {
      if (root == null)
      {
        BIFile rootFile;
        try
        {
          rootFile = BFileSystem.INSTANCE.resolveFile(scope);
        }
        catch(UnresolvedException e)
        {
          try
          {
            if (BajaFileUtil.log.isLoggable(Level.FINE))
            {
              BajaFileUtil.log.log(Level.FINE, String.format("Scope:%s does not exist. Creating scope.", scope.getBody()));
            }
            rootFile = BFileSystem.INSTANCE.makeDir(scope);
          }
          catch(IOException ex)
          {
            throw new RuntimeException("Unable to create scoped file space", e);
          }

        }

        if (!rootFile.isDirectory())
        {
          throw new IllegalStateException("Illegal scope, not a directory: " + scope.getBody());
        }

        BDirectory rootDir = (BDirectory)rootFile;

        ordInHost = BScopedFileSpace.this.ordInSession = BOrd.make(scope);

        List<BIFile> roots = new ArrayList<>();

        includeSysHome = inDirectory(rootDir, new FilePath("!"));
        includeUserHome = inDirectory(rootDir, new FilePath("~"));
        includeStationHome = includeUserHome || inDirectory(rootDir, new FilePath("^"));

        // add sys home root
        if (includeSysHome)
          roots.add(BFileSystem.INSTANCE.getSysHome());

        if (includeUserHome)
          roots.add(BFileSystem.INSTANCE.getUserHome());

        if (includeStationHome)
          roots.add(BFileSystem.INSTANCE.getStationHome());

        // add local file system roots within scope
        BIFile[] list = rootDir.listFiles();
        for(int i=0; i<list.length; ++i)
        {
          if (!isBlacklisted(list[i]))
            roots.add(list[i]);
        }

        // save roots away
        BScopedFileSpace.this.roots = roots.toArray(new BIFile[roots.size()]);

        root = rootDir;
      }

      return null;
    }
  }

  /**
   * Convenience method to check if the given file path falls within
   * the scope of the given directory.
   */
  private boolean inDirectory(BDirectory dir, FilePath path)
  {
    return AccessController.doPrivileged(new InDirectoryPrivilegedAction(dir, path));
  }

  private class InDirectoryPrivilegedAction
    implements PrivilegedAction<Boolean>
  {
    private InDirectoryPrivilegedAction(BDirectory dir, FilePath path)
    {
      this.dir = dir;
      this.path = path;
    }

    @Override
    public Boolean run()
    {
      FilePath p = path;

      BDirectory sysHome = BFileSystem.INSTANCE.getSysHome();
      BDirectory stationHome = BFileSystem.INSTANCE.getStationHome();
      BDirectory userHome = BFileSystem.INSTANCE.getUserHome();

      boolean checkSysHome = includeSysHome && (sysHome != null);
      boolean checkUserHome = includeUserHome && (userHome != null);
      boolean checkStationHome = includeStationHome && (stationHome != null);

      // Walk up the parentage of the given file path and check to see
      // if it resides under the directory.
      while(p != null)
      {
        BIFile file = null;
        try
        {
          file = BFileSystem.INSTANCE.resolveFile(p); // Is this too expensive?
        }
        catch(Exception e)
        {
          file = null;
        }

        if (file != null)
        {
          if (isBlacklisted(file)) return false;

          if ((dir.equals(file)) ||
                  (checkSysHome && sysHome.equals(file)) ||
                  (checkUserHome && userHome.equals(file)) ||
                  (checkStationHome && stationHome.equals(file)))
            return true;

          p = file.getFilePath().getParent();
        }
        else
          p = p.getParent();
      }

      return false;
    }

    private final BDirectory dir;
    private final FilePath path;
  }

  /**
   * Is the given full (non-scoped) FilePath contained within this scoped file system?
   */
  @Override
  public boolean inScope(FilePath path)
  {
    init();
    return inDirectory(root, path);
  }

  /**
   * Is the given file blacklisted within this scoped file system?
   */
  public boolean isBlacklisted(BIFile file)
  {
    return false;
  }

  /**
   * Given a FilePath relative to the scope of this file space,
   * convert it to a full absolute FilePath valid within the full (non-scoped) file system.
   * If the given FilePath can't be merged with the scope to create an absolute FilePath
   * within the full (non-scoped) file system, null is returned.
   */
  protected FilePath scopedPathToAbsPath(FilePath path)
  {
    // We have to look at the type of the FilePath passed in to determine
    // how to generate its full absolute file path.
    // Switch case has a fall-through between station home and protected station home
    switch(path.getAbsoluteMode())
    {
      case FilePath.RELATIVE:
        return scope.merge(path);
      case FilePath.LOCAL_ABSOLUTE:
        return scope.merge(new FilePath(path.getScheme(), path.getBody().substring(1)));
      case FilePath.SYS_HOME_ABSOLUTE:
      case FilePath.USER_HOME_ABSOLUTE:
      case FilePath.STATION_HOME_ABSOLUTE:
      case FilePath.PROTECTED_STATION_HOME_ABSOLUTE:
        if (inScope(path))
          return path;
    }

    return null;
  }

  /**
   * Returns the scope of this file space.
   * The FilePath returned is valid within the full (non-scoped) file system.
   */
  @Override
  public FilePath getScope()
  {
    return scope;
  }


////////////////////////////////////////////////////////////////
// File Operations
////////////////////////////////////////////////////////////////

  /**
   * Make a directory for the specified path or return
   * the existing directory.  This creates zero or more
   * directories as needed.
   * The given file path will be resolved relative to the
   * scope of this file space.
   */
  @Override
  public BDirectory makeDir(FilePath path, Context cx)
    throws IOException
  {
    FilePath p = scopedPathToAbsPath(path);

    // Sanity check that the path is in scope, since backups could
    // have been specified in the path to get it out of scope...
    if ((p == null) || !inScope(p))
    { // The requested file must be out of scope!
      throw new UnresolvedException(FILE_OUT_OF_SCOPE_ERROR+path.getBody());
    }

    return BFileSystem.INSTANCE.makeDir(p, cx);
  }

  /**
   * Make a file for the specified path or return the
   * existing file.  This creates zero or more directories
   * as needed.
   * The given file path will be resolved relative to the
   * scope of this file space.
   */
  @Override
  public BIFile makeFile(FilePath path, Context cx)
    throws IOException
  {
    FilePath p = scopedPathToAbsPath(path);

    // Sanity check that the path is in scope, since backups could
    // have been specified in the path to get it out of scope...
    if ((p == null) || !inScope(p))
    { // The requested file must be out of scope!
      throw new UnresolvedException(FILE_OUT_OF_SCOPE_ERROR+path.getBody());
    }

    return BFileSystem.INSTANCE.makeFile(p, cx);
  }

  /**
   * Move/rename the specified file.  If the "to" path is not
   * absolute, then it is relative to the from.getParent().
   * The given file paths will be resolved relative to the
   * scope of this file space.
   */
  @Override
  public void move(FilePath from, FilePath to, Context cx)
    throws IOException
  {
    FilePath f = scopedPathToAbsPath(from);

    // Sanity check that the path is in scope, since backups could
    // have been specified in the path to get it out of scope...
    if ((f == null) || !inScope(f))
    { // The requested file must be out of scope!
      throw new UnresolvedException(FILE_OUT_OF_SCOPE_ERROR+from.getBody());
    }

    FilePath t = null;
    // if to is relative, then no need to scope path to abs path
    if ((to.getBody().indexOf('/') < 0) || (to.isRelative()))
      t = to;
    else
    { // to is relative, so scope it!
      t = scopedPathToAbsPath(to);

      // Sanity check that the path is in scope, since backups could
      // have been specified in the path to get it out of scope...
      if ((t == null) || !inScope(t))
      { // The requested file must be out of scope!
        throw new UnresolvedException(FILE_OUT_OF_SCOPE_ERROR+to.getBody());
      }
    }

    BFileSystem.INSTANCE.move(f, t, cx);
  }

  /**
   * Delete the specified file store.
   * The given file path will be resolved relative to the
   * scope of this file space.
   */
  @Override
  public void delete(FilePath path, Context cx)
    throws IOException
  {
    FilePath p = scopedPathToAbsPath(path);

    // Sanity check that the path is in scope, since backups could
    // have been specified in the path to get it out of scope...
    if ((p == null) || !inScope(p))
    { // The requested file must be out of scope!
      return;
    }

    BFileSystem.INSTANCE.delete(p, cx);
  }


////////////////////////////////////////////////////////////////
// BIDirectory
////////////////////////////////////////////////////////////////

  /**
   * Return root files of this scoped file space.
   */
  @Override
  public BIFile[] listFiles()
  {
    init();
    return roots.clone();
  }


////////////////////////////////////////////////////////////////
// BFileSpace
////////////////////////////////////////////////////////////////

  /**
   * Map the file path to an instance of BLocalFileStore
   * or return null if it doesn't exist.
   * The given file path will be resolved relative to the
   * scope of this file space.
   */
  @Override
  public BIFile findFile(FilePath path)
  {
    FilePath p = scopedPathToAbsPath(path);

    // Sanity check that the path is in scope, since backups could
    // have been specified in the path to get it out of scope...
    if ((p == null) || !inScope(p))
    { // The requested file must be out of scope!
      return null;
    }

    return BFileSystem.INSTANCE.findFile(p);
  }

  /**
   * Map the file path to an instance of BLocalFileStore
   * or return null if it doesn't exist.
   * The given file path will be resolved relative to the
   * scope of this file space.
   */
  @Override
  public BIFileStore findStore(FilePath path)
  {
    FilePath p = scopedPathToAbsPath(path);

    // Sanity check that the path is in scope, since backups could
    // have been specified in the path to get it out of scope...
    if ((p == null) || !inScope(p))
    { // The requested file must be out of scope!
      return null;
    }

    return BFileSystem.INSTANCE.findStore(p);
  }

  /**
   * Get the child of the specified directory or return null.
   * The child must reside within the scope of this file space,
   * otherwise null will be returned.
   */
  @Override
  public BIFile getChild(BIFile dir, String name)
  {
    BIFile file = BFileSystem.INSTANCE.getChild(dir, name);

    if (file != null)
    {
      FilePath path = file.getFilePath();
      if (!inScope(path))
      { // The requested file must be out of scope!
        return null;
      }
    }

    return file;
  }

  /**
   * Get the children of the specified directory or
   * return an empty array.  An empty array will also
   * be returned if the directory doesn't reside within
   * the scope of this file space.
   */
  @Override
  public BIFile[] getChildren(BIFile dir)
  {
    FilePath path = dir.getFilePath();
    if (!inScope(path))
    { // The requested file must be out of scope!
      return NO_FILES;
    }

    BIFile[] children = BFileSystem.INSTANCE.getChildren(dir);
    int len = (children != null)?children.length:0;
    if (len < 1) return children;
    List<BIFile> result = new ArrayList<>();
    for (int i = 0; i < len; i++)
    {
      if (!isBlacklisted(children[i]))
        result.add(children[i]);
    }
    return result.toArray(new BIFile[result.size()]);
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  /**
   * Return true.
   */
  @Override
  public boolean hasNavChildren()
  {
    return true;
  }

  /**
   * Get a root by name.
   */
  @Override
  public BINavNode getNavChild(String navName)
  {
    init();
    for(int i=0; i<roots.length; ++i)
      if (roots[i].getFileName().equals(navName))
        return roots[i];
    return null;
  }

  /**
   * Get the scoped file space roots.
   */
  @Override
  public BINavNode[] getNavChildren()
  {
    init();

    return roots.clone();
  }

  /**
   * Get the ord in host.
   */
  @Override
  public BOrd getOrdInHost()
  {
    init();
    return ordInHost;
  }

  /**
   * Get the ord in session.
   */
  @Override
  public BOrd getOrdInSession()
  {
    init();
    return ordInSession;
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
    // It's likely that the scoped file space won't be mounted under the host (because
    // it is scoping access to the full FileSystem which is mounted), but we still need
    // to support a category mask on it to enforce permissions.  So this code checks
    // for the non-mounted case and handles retrieving the category mask from the category service.
    if (getNavOrd() != null)
      return super.getCategoryMask();

    return BCategoryService.getService().getCategoryMask(getOrdInSession());
  }

  /**
   * FileSpaces are mapped to categories by ord in <code>CategoryService.ordMap</code>.
   */
  @Override
  public BCategoryMask getAppliedCategoryMask()
  {
    // It's likely that the scoped file space won't be mounted under the host (because
    // it is scoping access to the full FileSystem which is mounted), but we still need
    // to support a category mask on it to enforce permissions.  So this code checks
    // for the non-mounted case and handles retrieving the category mask from the category service.
    if (getNavOrd() != null)
      return super.getAppliedCategoryMask();

    return BCategoryService.getService().getAppliedCategoryMask(getOrdInSession());
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    init();
    out.startProps("ScopedFileSpace");
    out.prop("scopeHome", ((BLocalFileStore)root.getStore()).getLocalFile());

    if (includeSysHome)
    {
      BDirectory dir = BFileSystem.INSTANCE.getSysHome();
      out.prop("sysHome", (dir == null ? "null" : ""+((BLocalFileStore)dir.getStore()).getLocalFile()));
    }

    if (includeUserHome)
    {
      BDirectory dir = BFileSystem.INSTANCE.getUserHome();
      out.prop("userHome", (dir == null ? "null" : ""+((BLocalFileStore)dir.getStore()).getLocalFile()));
    }

    if (includeStationHome)
    {
      BDirectory dir = BFileSystem.INSTANCE.getStationHome();
      out.prop("stationHome", (dir == null ? "null" : ""+((BLocalFileStore)dir.getStore()).getLocalFile()));
    }

    out.endProps();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  protected static final String FILE_OUT_OF_SCOPE_ERROR = "File path is out of scope: ";

  private BOrd ordInHost;
  private BOrd ordInSession;
  private BIFile[] roots;
  private boolean includeSysHome = false;
  private boolean includeUserHome = false;
  private boolean includeStationHome = false;
  private BDirectory root = null;
  private FilePath scope;
}
