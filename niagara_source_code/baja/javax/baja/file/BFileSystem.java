/*
 * Copyright 2020 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.naming.BLocalHost;
import javax.baja.naming.BOrd;
import javax.baja.naming.UnresolvedException;
import javax.baja.nav.BINavNode;
import javax.baja.nav.BNavRoot;
import javax.baja.nav.NavEvent;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SortUtil;
import javax.baja.nre.util.TextUtil;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BString;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconText;

import com.tridium.nre.util.NiagaraFiles;
import com.tridium.sys.Nre;
import com.tridium.util.ArrayUtil;

/**
 * BFileSystem is a BFileSpace for the local machine's
 * file system.
 *
 * @author    Brian Frank
 * @creation  24 Jan 03
 * @version   $Revision: 42$ $Date: 9/2/10 1:52:08 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:LocalHost"
  )
)
@NiagaraSingleton
public class BFileSystem
  extends BLocalizedFileSpace
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BFileSystem(178501969)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BFileSystem INSTANCE = new BFileSystem();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFileSystem.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  static
  {
    BLocalHost.INSTANCE.addNavChild(INSTANCE);
    BLocalHost.INSTANCE.mountSpace(INSTANCE);
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BFileSystem()
  {
    super("file", LexiconText.make("baja", "nav.fileSystem"));
    init();
  }

  /**
   * This is pulled out into an init() method so
   * that we can do call from the test suite
   */
  private void init()
  {
    this.ordInHost = this.ordInSession = BOrd.make("file:");

    ArrayList<BDirectory> roots = new ArrayList<>();
    specials = new HashMap<>();

    // add niagara home root
    File sysHomeFile = Nre.getNiagaraHome();
    BLocalFileStore sysHomeStore = new BLocalFileStore(this, new FilePath("!"), sysHomeFile);
    this.sysHome = new BDirectory(sysHomeStore, LexiconText.make("baja", "nav.sysHome"));
    sysHome.icon = BIcon.std("home.png");
    roots.add(sysHome);
    specials.put("!", sysHome);
    baseOrdSysHome = localFileToOrd(sysHomeFile).toString();

    // add niagara user home root
    File userHomeFile = Sys.getNiagaraUserHome();
    BLocalFileStore userHomeStore = new BLocalFileStore(this, new FilePath("~"), userHomeFile);
    this.userHome = new BDirectory(userHomeStore, LexiconText.make("baja", "nav.userHome"));
    sysHome.icon = BIcon.std("home.png");
    roots.add(userHome);
    specials.put("~", userHome);
    baseOrdUserHome = localFileToOrd(userHomeFile).toString();

    // add protected station home root
    File protectedStationHomeFile = Sys.getProtectedStationHome();
    if (protectedStationHomeFile != null)
    {
      BLocalFileStore protectedStationHomeStore = new BLocalFileStore(this, new FilePath("^^"), protectedStationHomeFile);
      this.protectedStationHome = new BDirectory(protectedStationHomeStore, LexiconText.make("baja", "nav.protectedStationHome"));
      protectedStationHome.icon = BIcon.std("database.png");
      specials.put("^^", protectedStationHome);
      
      // get the mapping from station home to baja home
      String homeDiff = protectedStationHomeFile.getPath().substring(userHomeFile.getPath().length()+1);
      homeDiff = homeDiff.replace('\\', '/');
      baseOrdProtectedStationHome = localFileToOrd(protectedStationHomeFile).toString();
      baseOrdProtectedStationToUserHome = "local:|file:~" + homeDiff;
   }
    
    // add station home root
    File stationHomeFile = Sys.getStationHome();
    if (stationHomeFile != null)
    {
      BLocalFileStore stationHomeStore = new BLocalFileStore(this, new FilePath("^"), stationHomeFile);
      this.stationHome = new BDirectory(stationHomeStore, LexiconText.make("baja", "nav.stationHome"));
      stationHome.icon = BIcon.std("database.png");
      roots.add(stationHome);
      specials.put("^", stationHome);

      // get the mapping from station home to baja home
      String homeDiff = stationHomeFile.getPath().substring(userHomeFile.getPath().length()+1);
      homeDiff = homeDiff.replace('\\', '/');
      baseOrdStationHome = localFileToOrd(stationHomeFile).toString();
      baseOrdStationToUserHome = "local:|file:~" + homeDiff;
    }

    // add local file system roots
    File[] list = AccessController.doPrivileged(new PrivilegedAction<File[]>() {
      @Override
      public File[] run()
      {
        return File.listRoots();
      }
    });

    // Under QNX listRoots returns a single entry with empty string as a name
    // (not "/").  We want to actually get the filesystem under / (ffs0, etc)
    // Behavior under other *nix's has not been tested, although it is documented
    // as similar.
    //
    // Issue 12594, use path name to recognize a unix filesystem, as both windows
    // and *nix return an empty string for a root.getName() call.
    if (list.length == 1 && list[0].getPath().equals("/"))
    {
      list = list[0].listFiles();
    }

    for (File file : list)
    {
      BDirectory root = toRoot(file);
      if (root == null)
      {
        //NCCB-44342: Be very noisy about when non-niagara root file system doesn't use a valid FilePath, but don't prevent boot
        BajaFileUtil.log.log(Level.WARNING, "Invalid root file path, ignoring root directory '" + file + "'");
        continue;
      }
      root.icon = getIcon();   // use drive icon
      root.navParent = this;   // nav parent always me
      roots.add(root);
      specials.put("/" + TextUtil.toLowerCase(root.getFileName()), root);
    }

    // save roots away
    this.roots = roots.toArray(new BDirectory[roots.size()]);
  }

  /**
   * Determines if the target system has a read-only Niagara Home directory.
   *
   * @since Niagara 4.13
   */
  @Override
  public boolean isNiagaraHomeReadOnly()
  {
    return NiagaraFiles.isNiagaraHomeReadonly();
  }

  /**
   * Convert a root java.io.File into a BDirectory using a
   * BLocalFileStore.  Since Windows has such screwy roots with
   * driver letter we may have to tweak to get a valid FilePath.
   */
  private BDirectory toRoot(File file)
  {
    try
    {
      String name = file.getPath();
      name = name.replace('\\', '/');
      if (name.endsWith("/")) name = name.substring(0, name.length()-1);
      if (!name.startsWith("/")) name = "/" + name;
      FilePath filePath = new FilePath(name);
      BLocalFileStore store = new BLocalFileStore(this, filePath, file);
      return new BDirectory(store);
    }
    catch(Exception e)
    {
      BajaFileUtil.log.log(Level.SEVERE, "Cannot create root " + file, e);
      return null;
    }
  }

  /**
   * Ensure the cache of file system roots is up to date with the
   * live values.  This is particularly needed for transient drives,
   * like removable flash storage.
   * @since Niagara 3.7
   */
  private void syncRoots()
  {
    // Create list of current root directories
    File[] list = AccessController.doPrivileged(new PrivilegedAction<File[]>() {
      @Override
      public File[] run()
      {
        return File.listRoots();
      }
    });

    // Under QNX listRoots returns a single entry with empty string as a name
    // (not "/").  We want to actually get the filesystem under / (ffs0, etc)
    // Behavior under other *nix's has not been tested, although it is documented
    // as similar.
    //
    // Issue 12594, use path name to recognize a unix filesystem, as both windows
    // and *nix return an empty string for a root.getName() call.
    if (list.length == 1 && list[0].getPath().equals("/"))
      list = list[0].listFiles();

    ArrayList<BDirectory> dirListArray = new ArrayList<>();
    for (File file : list)
    {
      BDirectory root = toRoot(file);
      if (root == null)
      {
        //NCCB-44342: Be very noisy about when non-niagara root file system doesn't use a valid FilePath, but don't prevent boot
        BajaFileUtil.log.log(Level.WARNING, "Invalid root file path, ignoring root directory '" + file + "'");
        continue;
      }
      dirListArray.add(root);
    }
    BDirectory[] dirList = dirListArray.toArray(new BDirectory[0]);

    // Iterate through our cached list, removing ones that no longer exist
    for (Map.Entry<String,BDirectory> entry : specials.entrySet())
    {
      BDirectory directory = entry.getValue();
      String key = entry.getKey();
      boolean found = false;
      for (BDirectory aDirList : dirList)
      {
        if (aDirList.equals(directory))
        {
          found = true;
          break;
        }
      }

      if (!found && !key.equals("!") && !key.equals("~"))
      {
        // Remove root
        roots = ArrayUtil.removeOne(roots, directory);
        specials.remove(key);
        fireNavEvent(NavEvent.makeRemoved(this, directory.getFileName(), null));
      }
    }

    // Iterate through the current list, adding new ones to the cache
    for (BDirectory aDirList : dirList)
    {
      if (specials.get("/" + TextUtil.toLowerCase(aDirList.getFileName())) == null)
      {
        // Add root
        aDirList.icon = getIcon();   // use drive icon
        aDirList.navParent = this;   // nav parent always me
        roots = ArrayUtil.addOne(roots, aDirList);
        specials.put("/" + TextUtil.toLowerCase(aDirList.getFileName()), aDirList);
        fireNavEvent(NavEvent.makeAdded(this, aDirList.getFileName(), null));
      }
    }
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get <code>Nre.getNiagaraHome()</code> as a BDirectory.
   */
  public BDirectory getSysHome()
  {
    return sysHome;
  }
  
  /**
   * Get <code>Sys.getNiagaraUserHome()</code> as a BDirectory.
   */
  public BDirectory getUserHome()
  {
    return userHome;
  }

  /**
   * Get <code>Sys.getStationHome()</code> as a BDirectory or null
   * if this is not a station VM.
   */
  public BDirectory getStationHome()
  {
    return stationHome;
  }
  
  /**
   * Get <code>Sys.getProtectedStationHome()</code> as a BDirectory or null
   * if this is not a station VM.
   */
  public BDirectory getProtectedStationHome()
  {
    return protectedStationHome;
  }

////////////////////////////////////////////////////////////////
// File Operations
////////////////////////////////////////////////////////////////

  /**
   * Make a directory for the specified path or return
   * the existing directory.  This creates zero or more
   * directories as needed.
   */
  @Override
  public BDirectory makeDir(FilePath path, Context cx)
    throws IOException
  {
    path = getLocalizedFilePath(path);
    checkWritePermission(path, cx);

    File file = pathToLocalFile(path);
    NavEvent event = precheckAddEvent(file);

    if (file.exists())
    {
      if (!file.isDirectory())
        throw new IOException("Exists as file: " + path.getBody());
    }
    else
    {
      file.mkdirs();
    }

    if (event != null) fireNavEvent(event);

    return new BDirectory(new BLocalFileStore(INSTANCE, path, file));
  }

  /**
   * Make a file for the specified path or return the
   * existing file.  This creates zero or more directories
   * as needed.
   */
  @Override
  public BIFile makeFile(FilePath path, Context cx)
    throws IOException
  {
    path = getLocalizedFilePath(path);
    checkWritePermission(path, cx);

    File file = pathToLocalFile(path);
    NavEvent event = precheckAddEvent(file);

    if (file.exists())
    {
      if (file.isDirectory())
        throw new IllegalArgumentException("Exists as dir: " + path.getBody());
    }
    else
    {
      File parent = new File(file.getParent());
      if (!parent.exists()) parent.mkdirs();
    }

    if (!file.exists())
    {
      FileOutputStream out = new FileOutputStream(file);
      out.close();
    }

    if (event != null) fireNavEvent(event);

    return makeFile(new BLocalFileStore(INSTANCE, path, file));
  }

  /**
   * Move/rename the specified file.  If the "to" path is not
   * absolute, then it is relative to the from.getParent().
   */
  @Override
  public void move(FilePath from, FilePath to, Context cx)
    throws IOException
  {
    from = getLocalizedFilePath(from);
    to = getLocalizedFilePath(to);

    checkWritePermission(from, cx);
    checkWritePermission(to, cx);

    // we use to figure out how to fire nav events
    boolean isNameOnly = to.getBody().indexOf('/') < 0;

    // ensure to absolute
    if (to.isRelative()) to = from.getParent().merge(to);

    // map to files
    File fromFile = pathToLocalFile(from);
    File toFile = pathToLocalFile(to);

    // do rename
    if (!fromFile.renameTo(toFile))
      throw new IOException("Move: " + fromFile + " -> " + toFile);

    // if the to path was name only, then we can fire
    // rename, otherwise we have to fire remove/add
    if (isNameOnly)
    {
      BOrd parent = BOrd.make("local:|" + from.getParent());
      fireNavEvent(NavEvent.makeRenamed(parent, from.getName(), to.getName(), null));
    }
    else
    {
      BOrd oldParent = BOrd.make("local:|" + from.getParent());
      fireNavEvent(NavEvent.makeRemoved(oldParent, from.getName(), null));

      BOrd newParent = BOrd.make("local:|" + to.getParent());
      fireNavEvent(NavEvent.makeAdded(newParent, to.getName(), null));
    }
  }

  /**
   * Delete the specified file store.
   */
  @Override
  public void delete(FilePath path, Context cx)
    throws IOException
  {
    path = getLocalizedFilePath(path);
    checkWritePermission(path, cx);

    // map to file
    BIFile file = findFile(path);
    if (file == null) return;

    // extract to local file
    BLocalFileStore store = (BLocalFileStore)file.getStore();
    File f = store.getLocalFile();

    BajaFileUtil.delete(f);

    // make child and parent
    File        parentFile = new File(f.getParent());
    FilePath    parentPath = file.getFilePath().getParent();
    BDirectory  parent     = new BDirectory(new BLocalFileStore(this, parentPath, parentFile));

    // fire removed event
    NavEvent event = NavEvent.makeRemoved(parent.getNavOrd(), file.getFileName(), null);
    fireNavEvent(event);
  }

  /**
   * When Niagara Home is read-only, redirect certain system file paths to an alternate writable
   * location. For example, !modules and !security will be redirected to ~modules and ~security
   * respectively.
   * @param path the original file path
   * @return the redirected file path if the target system requires redirection;
   * otherwise the original file path
   * @since Niagara 4.13
   */
  @Override
  protected FilePath getLocalizedFilePath(FilePath path)
  {
    FilePath localizedPath = super.getLocalizedFilePath(path);
    if (log.isLoggable(Level.FINE) && !Objects.equals(path, localizedPath))
    {
      log.fine("Target system has read-only Niagara Home, redirecting " + path + " to " + localizedPath);
    }
    return localizedPath;
  }

////////////////////////////////////////////////////////////////
// Event Support
////////////////////////////////////////////////////////////////

  /**
   * We are getting ready to create the file or dir and
   * potentially an entire directory tree above.  Based
   * on the current state of files that exist *before*
   * this operation get an event for the first file which
   * will be created or return null.
   */
  private NavEvent precheckAddEvent(File file)
  {
    // if the file already exists, there will be no event
    if (file.exists()) return null;

    // walk from file up looking for the parent
    // that exists and the child to create
    File parent = file;
    File child = null;
    while(!parent.exists())
    {
      child = parent;
      parent = new File(parent.getParent());
    }

    // compute parent ord
    BOrd parentOrd = localFileToOrd(parent);
    String newChildName = child.getName();
    return NavEvent.makeAdded(parentOrd, newChildName, null);
  }

  /**
   * Sometimes an event on a file under ^ or ! results
   * in two or three events.  This whole design assumes
   * that the station home is properly mounted under sys home!
   */
  @Override
  protected void fireNavEvent(NavEvent event)
  {
    // fire the standard event
    BNavRoot.INSTANCE.fireNavEvent(event);

    String ord = event.getParentOrd().toString();

    // Make a "duplicate file nav event" context so listeners can
    // detect duplicate events
    Context cx  = new BasicContext(event.getContext(), BFacets.make("dupFileNavEvent", true));

    // if a station VM then we have to deal with ^
    if (baseOrdStationHome != null)
    {
      // check case of ^^ which results in two other events
      if (ord.startsWith("local:|file:^^"))
      {
        String x = ord.substring("local:|file:^^".length());
        if (x.length() > 0) x = "/" + x;

        // fire for /{protectedStationHome}
        BOrd alt1 = BOrd.make(baseOrdProtectedStationHome + x);
        BNavRoot.INSTANCE.fireNavEvent(NavEvent.make(event, alt1, cx));

        // fire for ~
        BOrd alt2 = BOrd.make(baseOrdProtectedStationToUserHome + x);
        BNavRoot.INSTANCE.fireNavEvent(NavEvent.make(event, alt2));
        return;
      }
      
      // check case of ^ which results in two other events
      if (ord.startsWith("local:|file:^"))
      {
        String x = ord.substring("local:|file:^".length());
        if (x.length() > 0) x = "/" + x;

        // fire for /{stationHome}
        BOrd alt1 = BOrd.make(baseOrdStationHome + x);
        BNavRoot.INSTANCE.fireNavEvent(NavEvent.make(event, alt1, cx));

        // fire for ~
        BOrd alt2 = BOrd.make(baseOrdStationToUserHome + x);
        BNavRoot.INSTANCE.fireNavEvent(NavEvent.make(event, alt2));
        return;
      }

      // check case of ~ which results in one or two other events
      if (ord.startsWith(baseOrdStationToUserHome))
      {
        // fire for /{userHome}
        BOrd alt1 = BOrd.make(baseOrdUserHome + "/" + ord.substring("local:|file:~".length()));
        BNavRoot.INSTANCE.fireNavEvent(NavEvent.make(event, alt1));

        // fire for ^
        BOrd alt2;
        if (ord.length() == baseOrdStationToUserHome.length())
          alt2 = BOrd.make("local:|file:^");
        else
          alt2 = BOrd.make("local:|file:^" + ord.substring(baseOrdStationToUserHome.length()+1));
        BNavRoot.INSTANCE.fireNavEvent(NavEvent.make(event, alt2));
        return;
      }

      // check case of baseOrdStationHome which results in two other events
      if (ord.startsWith(baseOrdStationHome))
      {
        // fire for ^
        BOrd alt1;
        if (ord.length() == baseOrdStationHome.length())
          alt1 = BOrd.make("local:|file:^");
        else
          alt1 = BOrd.make("local:|file:^" + ord.substring(baseOrdStationHome.length()+1));
        BNavRoot.INSTANCE.fireNavEvent(NavEvent.make(event, alt1, cx));

        // fire for ~
        BOrd alt2 = BOrd.make("local:|file:~" + ord.substring(baseOrdUserHome.length()+1));
        BNavRoot.INSTANCE.fireNavEvent(NavEvent.make(event, alt2, cx));
        return;
      }
    }

    // check case of ~ which results in one event (two was previous)
    if (ord.startsWith("local:|file:~"))
    {
      // fire for /{userHome}
      String x = ord.substring("local:|file:~".length());
      if (x.length() > 0) x = "/" + x;
      BOrd alt1 = BOrd.make(baseOrdUserHome + x);
      BNavRoot.INSTANCE.fireNavEvent(NavEvent.make(event, alt1, cx));
      return;
    }

    // check case of baseOrdSysHome which results in one other events (two was previous)
    if (ord.startsWith(baseOrdUserHome))
    {
      // fire for !
      BOrd alt1;
      if (ord.length() == baseOrdUserHome.length())
        alt1 = BOrd.make("local:|file:~");
      else
        alt1 = BOrd.make("local:|file:~" + ord.substring(baseOrdUserHome.length()+1));
      BNavRoot.INSTANCE.fireNavEvent(NavEvent.make(event, alt1, cx));
      return;
    }
    
    // check case of ! which results in one event (two was previous)
    if (ord.startsWith("local:|file:!"))
    {
      // fire for /{sysHome}
      String x = ord.substring("local:|file:!".length());
      if (x.length() > 0) x = "/" + x;
      BOrd alt1 = BOrd.make(baseOrdSysHome + x);
      BNavRoot.INSTANCE.fireNavEvent(NavEvent.make(event, alt1, cx));
      return;
    }

    // check case of baseOrdSysHome which results in one other events (two was previous)
    if (ord.startsWith(baseOrdSysHome))
    {
      // fire for !
      BOrd alt1;
      if (ord.length() == baseOrdSysHome.length())
        alt1 = BOrd.make("local:|file:!");
      else
        alt1 = BOrd.make("local:|file:!" + ord.substring(baseOrdSysHome.length()+1));
      BNavRoot.INSTANCE.fireNavEvent(NavEvent.make(event, alt1, cx));
    }
  }

////////////////////////////////////////////////////////////////
// BIDirectory
////////////////////////////////////////////////////////////////

  /**
   * Return root files.
   */
  @Override
  public BIFile[] listFiles()
  {
    return roots.clone();
  }

////////////////////////////////////////////////////////////////
// BFileSpace
////////////////////////////////////////////////////////////////

  /**
   * Given a file path, map it to a java.io.File.
   * This doesn't guarantee that the file exists.
   */
  public File pathToLocalFile(FilePath path)
  {
    // NCCB-57144: Detect writable system file exceptions.
    path = getLocalizedFilePath(path);
    String body = path.getBody();
    if (path.isSysHomeAbsolute())
    {
      return new File(Nre.getNiagaraHome(), body.substring(1));
    }
    if (path.isUserHomeAbsolute())
    {
      return new File(Sys.getNiagaraUserHome(), body.substring(1));
    }
    else if (path.isStationHomeAbsolute())
    {
      File f = Sys.getStationHome();
      if (f == null) throw new UnresolvedException("Not a station VM");
      return new File(f, body.substring(1));
    }
    else if (path.isProtectedStationHomeAbsolute())
    {
      File f = Sys.getProtectedStationHome();
      if (f == null) throw new UnresolvedException("Not a station VM");
      return new File(f, body.substring(2));
    }
    else if (path.isLocalAbsolute())
    {
      // if root, use roots array
      if (path.depth() == 1)
      {
        String name = path.nameAt(0);
        for (BDirectory root : roots)
        {
          if (root.getFileName().equalsIgnoreCase(name))
          {
            return ((BLocalFileStore) root.getStore()).getLocalFile();
          }
        }
      }

      return new File(body);
    }
    else
    {
      throw new UnresolvedException("Relative path: " + body);
    }
  }

  /**
   * Map a local file to a FilePath.
   */
  public FilePath localFileToPath(File file)
  {
    try
    {
      String path = file.getCanonicalPath();
      path = path.replace('\\', '/');
      if (path.endsWith("/")) path = path.substring(0, path.length()-1);
      if (!path.startsWith("/")) path = "/" + path;
      return new FilePath(path);
    }
    catch(Exception e)
    {
      throw new UnresolvedException("Cannot map to path: " + file, e);
    }
  }

  /**
   * Map a local file to an ord.
   */
  public BOrd localFileToOrd(File file)
  {
    return BOrd.make("local:|file:" + localFileToPath(file).getBody());
  }

  /**
   * Map the file path to an instance of BLocalFileStore
   * or return null if it doesn't exist.
   */
  @Override
  public BIFile findFile(FilePath path)
  {
    BIFile special = specials.get(TextUtil.toLowerCase(path.getBody()));
    if (special != null) return special;
    return super.findFile(getLocalizedFilePath(path));
  }

  /**
   * Map the file path to an instance of BLocalFileStore
   * or return null if it doesn't exist.
   */
  @Override
  public BIFileStore findStore(FilePath path)
  {
    File file = pathToLocalFile(path);
    if (file.exists()) return new BLocalFileStore(this, path, file);
    return null;
  }

  /**
   * Get the child of the specified directory or return null.
   */
  @Override
  public BIFile getChild(BIFile dir, String name)
  {
    BLocalFileStore store = (BLocalFileStore)dir.getStore();
    if (store.isDirectory())
    {
      File childFile = new File(store.getLocalFile(), name);
      if (childFile.exists())
      {
        FilePath childPath = store.getFilePath().merge(name);
        return makeFile(new BLocalFileStore(this, childPath, childFile));
      }
    }
    return null;
  }

  /**
   * Get the children of the specified directory or
   * return an empty array.
   */
  @Override
  public BIFile[] getChildren(BIFile dir)
  {
    BLocalFileStore store = (BLocalFileStore)dir.getStore();
    File[] list = store.getLocalFile().listFiles();
    if (list == null || list.length == 0) return NO_FILES;
    ArrayList<BIFile> v = new ArrayList<>(list.length);
    for (File childFile : list)
    {
      String childName = childFile.getName();
      if (!FilePath.isValidName(childName))
      {
        log.warning("Unsupported file name \"" + childName + "\"");
        continue;
      }
      FilePath childPath = store.getFilePath().merge(childName);
      v.add(makeFile(new BLocalFileStore(this, childPath, childFile)));
    }
    BIFile[] kids = v.toArray(new BIFile[v.size()]);
    SortUtil.sort(kids);
    return kids;
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
    for (BDirectory root : roots)
    {
      if (root.getFileName().equals(navName))
      {
        return root;
      }
    }
    return null;
  }

  /**
   * Get the file system roots.
   */
  @Override
  public BINavNode[] getNavChildren()
  {
    syncRoots();

    try
    { // We need a way to restrict the file system for remote users.
      // Without this, a remote user could go to the remote spy for
      // the file system and see everything.
      // In such a case, we need to scope down the visibility to just the
      // Sys home (and Station home).  getNavChildren() is called in the
      // spy case, so using a thread local variable will tell us when
      // to restrict the visibility.  The SpyWriter's context tells us if
      // it is a remote user or not
      Context cx = threadLocalContext.get();
      if ((cx != null) && ((cx.getUser() != null) || (cx.getFacet("username") instanceof BString)))
        return new BINavNode[] { sysHome, stationHome };
    }
    catch (Throwable ignored) { }

    return roots.clone();
  }

  /**
   * Get the ord in host.
   */
  @Override
  public BOrd getOrdInHost()
  {
    return ordInHost;
  }

  /**
   * Get the ord in session.
   */
  @Override
  public BOrd getOrdInSession()
  {
    return ordInSession;
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

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
      threadLocalContext.set(out.getContext());

      if (BFileSystem.INSTANCE == this) // add context check
      {
        return;
      }

      super.spy(out);
      out.startProps("FileSystem");
      out.prop("sysHome", ((BLocalFileStore)getSysHome().getStore()).getLocalFile());
      out.prop("stationHome", (stationHome == null ? "null" : ""+((BLocalFileStore)getStationHome().getStore()).getLocalFile()));
      out.endProps();
    }
    finally
    {
      threadLocalContext.set(null);
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final Logger log = Logger.getLogger("sys.file");
  static ThreadLocal<Context> threadLocalContext = new ThreadLocal<>();

  private BOrd ordInHost;
  private BOrd ordInSession;
  private HashMap<String,BDirectory> specials;
  private BDirectory[] roots;

  private String baseOrdSysHome;              // local:|file:/C:/Niagara/Niagara-4.0.0.308
  private String baseOrdStationHome;          // local:|file:/C:/Users/<user>/Niagara4/stations/demo/shared
  private String baseOrdProtectedStationHome; // local:|file:/C:/Users/<user>/Niagara4/stations/demo
  private String baseOrdStationToUserHome;    // local:|file:~stations/demo/shared
  private String baseOrdProtectedStationToUserHome; // local:|file:~stations/demo
  private String baseOrdUserHome;             // local:|file:/C:/Users/<user>/Niagara4
  private BDirectory sysHome;
  private BDirectory userHome;
  private BDirectory stationHome;
  private BDirectory protectedStationHome;

}
