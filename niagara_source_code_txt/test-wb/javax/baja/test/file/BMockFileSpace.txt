/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.test.file;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.baja.file.BDirectory;
import javax.baja.file.BFileSpace;
import javax.baja.file.BIFile;
import javax.baja.file.BIFileStore;
import javax.baja.file.FilePath;
import javax.baja.naming.BHost;
import javax.baja.naming.BISession;
import javax.baja.naming.BOrd;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.test.BMockHost;
import javax.baja.test.BMockSession;

@NiagaraType(
  agent = @AgentOn(
    types = "test:MockSession"
  )
)
/**
 * BMockFileSpace provides a way to simulate the state of a {@link javax.baja.file.BFileSpace}
 * for testing.   Files in the file space will have {@link javax.baja.test.file.BMockFileStore}
 * stores, which may or may not have data in them.
 *
 * @author Matt Boon
 * @since Niagara 4.0
 */
public class BMockFileSpace
  extends BFileSpace
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.test.file.BMockFileSpace(68220079)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMockFileSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BMockFileSpace(BMockSession session)
  {
    super("file");
    this.session = session;
  }

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  public static BMockFileSpace makeFileSpace(String sessionName)
  {
    BMockSession session = BMockHost.INSTANCE.makeSession(sessionName);
    BMockFileSpace result = (BMockFileSpace)session.getNavChild("file");
    if (result == null)
    {
      result = new BMockFileSpace(session);
      session.mountSpace(result);
    }
    return result;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public void clear()
  {
    mapsLock.lock();
    try
    {
      childNodesByParentPath.clear();
      fileByPath.clear();
      rootPaths.clear();
    }
    finally
    {
      mapsLock.unlock();
    }
  }

  public BIFile[] getChildren(FilePath parentPath)
  {
    if (parentPath.isRelative())
    {
      throw new IllegalArgumentException("Relative path: " + parentPath.getBody());
    }
    mapsLock.lock();
    try
    {
      if (childNodesByParentPath.containsKey(parentPath))
      {
        Collection<BIFile> results = childNodesByParentPath.get(parentPath).values();
        return results.toArray(new BIFile[results.size()]);
      }
      else
      {
        return NO_FILES;
      }
    }
    finally
    {
      mapsLock.unlock();
    }
  }

  public BIFile mockFile(FilePath path, boolean isDirectory)
  {
    return makeFile(mockStore(path, isDirectory));
  }

  public BIFile mockFile(String pathBody)
  {
    return mockFile(new FilePath(pathBody), false);
  }

  public BDirectory mockDirectory(String pathBody)
  {
    return (BDirectory)mockFile(new FilePath(pathBody), true);
  }

  public void mockFiles(String... paths)
  {
    for (String path : paths)
    {
      mockFile(new FilePath(path), false);
    }
  }

  private BMockFileStore mockStore(FilePath path, boolean isDirectory)
  {
    if (path.isRelative())
    {
      throw new IllegalArgumentException("Relative path: " + path.getBody());
    }

    BIFileStore result = findStore(path);
    if (result == null)
    {
      if (path.depth() == 0)
      {
        // new root
        if (!isDirectory)
        {
          throw new IllegalArgumentException(String.format("makeStore isDirectory expected true but got false for %s", path.getBody()));
        }
        mapsLock.lock();
        try
        {
          result = new BMockFileStore(this, path.getBody(), true);
          BDirectory dir = new BDirectory(result);
          rootPaths.add(path);
          fileByPath.put(path, dir);
          childNodesByParentPath.put(path, new TreeMap<>());
        }
        finally
        {
          mapsLock.unlock();
        }
      }
      else
      {
        // ensure the parent dir's store exists
        mockStore(path.getParent(), true);
        mapsLock.lock();
        try
        {
          result = new BMockFileStore(this, path.getBody(), isDirectory);
          BIFile file = makeFile(result);
          childNodesByParentPath.get(path.getParent()).put(path.getName(), file);
          if (isDirectory)
          {
            childNodesByParentPath.put(path, new TreeMap<>());
          }
          fileByPath.put(path, file);
        }
        finally
        {
          mapsLock.unlock();
        }
      }
    }
    else if (result.isDirectory() != isDirectory)
    {
      throw new IllegalArgumentException(String.format("makeStore isDirectory expected %s but got %s for %s", result.isDirectory(), isDirectory, path.getBody()));
    }
    return (BMockFileStore)result;
  }

////////////////////////////////////////////////////////////////
// BNavContainer
////////////////////////////////////////////////////////////////

  /**
   * Return is any children have been added.
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
    FilePath navChildPath = new FilePath(navName);
    mapsLock.lock();
    try
    {
      if (rootPaths.contains(navChildPath))
      {
        return fileByPath.get(navChildPath);
      }
      return null;
    }
    finally
    {
      mapsLock.unlock();
    }
  }

  /**
   * Get the children nodes for this navigation node.
   * Return an array of length zero if there are no
   * children.
   */
  @Override
  public BINavNode[] getNavChildren()
  {
    return listFiles();
  }

  /**
   * If this instance has been mounted under another
   * BNavContainer, then return it.  Otherwise the default
   * implementation is to return null.
   */
  @Override
  public BINavNode getNavParent()
  {
    return session;
  }

////////////////////////////////////////////////////////////////
// BSpace
////////////////////////////////////////////////////////////////

  /**
   * Get the ord of this space relative to its session.
   */
  @Override
  public BOrd getOrdInSession()
  {
    return BOrd.make("file:");
  }

  /**
   * Get the ord of this space relative to its host.
   */
  @Override
  public BOrd getOrdInHost()
  {
    return BOrd.make(getSession().getOrdInHost(), "file:");
  }

  /**
   * If this space is mounted, then return its parent
   * host, otherwise return null.
   */
  @Override
  public BHost getHost()
  {
    return BMockHost.INSTANCE;
  }

  /**
   * If this space is mounted, then return its parent
   * session, otherwise return null.
   */
  @Override
  public BISession getSession()
  {
    return session;
  }

////////////////////////////////////////////////////////////////
// BFileSpace
////////////////////////////////////////////////////////////////

  /**
   * Make a directory for the specified path or return
   * the existing directory.  This creates zero or more
   * directories as needed.  Check security permissions
   * if context maps to a user.
   */
  @Override
  public BDirectory makeDir(FilePath path, Context cx)
    throws IOException
  {
    return (BDirectory)mockFile(path, true);
  }

  /**
   * Make a file for the specified path or return the
   * existing file.  This creates zero or more directories
   * as needed.  Check security permissions if context
   * maps to a user.
   */
  @Override
  public BIFile makeFile(FilePath path, Context cx)
    throws IOException
  {
    return mockFile(path, false);
  }

  /**
   * Unsupported
   */
  @Override
  public void move(FilePath from, FilePath to, Context cx)
    throws IOException
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Recursively delete the specified file.  Ignore
   * the call if the path doesn't exist.  Check security
   * permissions if context maps to a user.
   */
  @Override
  public void delete(FilePath path, Context cx)
    throws IOException
  {
    if (path.isRelative())
    {
      throw new IllegalArgumentException("Relative path: " + path.getBody());
    }
    mapsLock.lock();
    try
    {
      if (childNodesByParentPath.containsKey(path))
      {
        for (String nodeName : childNodesByParentPath.get(path).keySet())
        {
          delete(path.merge(nodeName), cx);
        }
      }
      fileByPath.remove(path);
      rootPaths.remove(path);
    }
    finally
    {
      mapsLock.unlock();
    }
  }

  /**
   * Map a FilePath to an instanceof of BIFileStore.  If the
   * path doesn't map to a file in this space, then return null.
   */
  @Override
  public BIFileStore findStore(FilePath path)
  {
    BIFile file = findFile(path);
    return file == null ? null : file.getStore();
  }

  @Override
  public BIFile findFile(FilePath path)
  {
    if (path.isRelative())
    {
      throw new IllegalArgumentException("Relative path: " + path.getBody());
    }
    mapsLock.lock();
    try
    {
      return fileByPath.get(path);
    }
    finally
    {
      mapsLock.unlock();
    }
  }

  /**
   * Get the child file of the specified parent or
   * return null if not found.
   */
  @Override
  public BIFile getChild(BIFile parent, String childName)
  {
    return findFile(parent.getFilePath().merge(childName));
  }

  /**
   * Get the children files of the specified parent
   * or return an empty array.
   */
  @Override
  public BIFile[] getChildren(BIFile parent)
  {
    return getChildren(parent.getFilePath());
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
    mapsLock.lock();
    try
    {
      return rootPaths.stream().map(fileByPath::get).toArray(BIFile[]::new);
    }
    finally
    {
      mapsLock.unlock();
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final Map<FilePath,Map<String,BIFile>> childNodesByParentPath = new TreeMap<>();
  private final Map<FilePath,BIFile> fileByPath = new TreeMap<>();
  private final Set<FilePath> rootPaths = new TreeSet<>();
  private final Lock mapsLock = new ReentrantLock();

  private final BMockSession session;

  public static final BIFile[] NO_FILES = new BIFile[0];
}
