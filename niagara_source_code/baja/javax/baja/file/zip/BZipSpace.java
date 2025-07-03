/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.zip;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.baja.file.BDirectory;
import javax.baja.file.BFileSpace;
import javax.baja.file.BIDirectory;
import javax.baja.file.BIFile;
import javax.baja.file.BIFileStore;
import javax.baja.file.BLocalFileStore;
import javax.baja.file.FilePath;
import javax.baja.naming.BHost;
import javax.baja.naming.BISession;
import javax.baja.naming.BModuleScheme;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdQuery;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BSpace;
import javax.baja.sys.BModule;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.schema.Fw;

/**
 * BZipSpace is a BFileSpace for a directory tree within BZipFile.
 *
 * @author    Brian Frank
 * @creation  24 Jan 03
 * @version   $Revision: 16$ $Date: 11/12/07 12:46:14 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BZipSpace
  extends BFileSpace
  implements BIDirectory
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.zip.BZipSpace(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BZipSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BZipSpace(String zip)
  {
    super("zip");
    this.rootPath = new ZipPath("/");
  }

  /**
   * Construct for the specified BZipFile.
   */
  public BZipSpace(BZipFile file)
  {
    this(file, null);
  }

  /**
   * Construct for the specified BZipFile and charset.
   * @param file the zip file for this space
   * @param charset the charset of the zip file, defaults to UTF-8 in the underlying java ZipFile if null
   * @since Niagara 4.10u8 / 4.13u3 / 4.14
   */
  public BZipSpace(BZipFile file, Charset charset)
  {
    super("zip");
    this.charset = charset;
    parentSpace = file.getSpace();
    ordInSession = file.getOrdInSession();
    bZipFile = file;
    zip = open(file.getStore(), charset);
    rootPath = new ZipPath("/");
  }

  /**
   * Construct for the specified ZipFile.
   */
  public BZipSpace(BOrd ordInSession, ZipFile zip)
  {
    super("zip");
    this.ordInSession = ordInSession;
    this.zip = zip;
    isModule = isModuleOrd(ordInSession);
    rootPath = new ZipPath("/");
  }

  /**
   * Construct for the specified ZipFile.
   */
  public BZipSpace(BModule module, ZipFile zip)
  {
    super("zip");
    ordInSession = module.getOrdInSession();
    this.zip = zip;
    isModule = true;
    parentSpace = module;
    rootPath = module.getRootFilePath();
  }


  ZipFile getZip()
  {
    if (zip == null && bZipFile != null)
    {
      zip = open(bZipFile.getStore(), charset);
    }
    return zip;
  }


  @Override
  public Object fw(int code, Object a, Object b, Object c, Object d)
  {
    switch (code)
    {
      /*
       * Manually set the ZipFile backing this BZipSpace. Use with care, as the
       * existing zip file will *not* be closed (you may want to call close()
       * first).
       */
      case Fw.USER_DEFINED_0:
        this.zip = (ZipFile) a;
        break;
    }
    return null;
  }

  protected boolean isModuleOrd(BOrd ord)
  {
    for (OrdQuery q : getOrdInSession().parse())
    {
      if (q.getScheme().equals(BModuleScheme.INSTANCE.getId()))
      {
        return true;
      }
    }
    return false;
  }

  public Optional<BModule> getModule()
  {
    if (parentSpace instanceof BModule)
    {
      return Optional.of((BModule)parentSpace);
    }
    else
    {
      for (OrdQuery q : getOrdInSession().parse())
      {
        if (q.getScheme().equals(BModuleScheme.INSTANCE.getId()))
        {
          return Optional.of(Sys.loadModule(((FilePath)q).getAuthority()));
        }
      }
    }
    return Optional.empty();
  }

  /**
   * Close the underlying ZipFile, rendering the BZipSpace instance unuseable
   * if the BZipSpace(BOrd, ZipFile) constructor was used.
   *
   * If the BZipSpace(BZipFile) constructor was used, then the instance is
   * still useable.
   */
  void close()
  {
    try
    {
      zip.close();
    }
    catch(IOException e)
    {
      throw new BajaRuntimeException(e);
    }
    finally
    {
      zip = null;
    }
  }

  static ZipFile open(BIFileStore store, Charset charset)
  {
    try
    {
      if (store instanceof BLocalFileStore)
      {
        File f = ((BLocalFileStore)store).getLocalFile();
        return charset != null ? new ZipFile(f, charset) : new ZipFile(f);
      }
      else
      {
        throw new IllegalStateException("Only BLocalFileStore supported");
      }
    }
    catch(IOException e)
    {
      throw new BajaRuntimeException(e);
    }
  }

  public InputStream getInputStream(ZipEntry zipEntry)
    throws IOException
  {
    return getZip().getInputStream(zipEntry);
  }

  /** Get top level directory of files in zip. */
  public BDirectory getDirectory()
  {
    return roots();
  }

  public boolean isModule()
  {
    return isModule;
  }

////////////////////////////////////////////////////////////////
// File Operations
////////////////////////////////////////////////////////////////

  /**
   * Throw IOException
   */
  @Override
  public BDirectory makeDir(FilePath path, Context cx)
    throws IOException
  {
    throw new IOException("ZipSpace is readonly");
  }

  /**
   * Throw IOException
   */
  @Override
  public BIFile makeFile(FilePath path, Context cx)
    throws IOException
  {
    throw new IOException("ZipSpace is readonly");
  }

  /**
   * Throw IOException
   */
  @Override
  public void move(FilePath from, FilePath to, Context cx)
    throws IOException
  {
    throw new IOException("ZipSpace is readonly");
  }

  /**
   * Throw IOException
   */
  @Override
  public void delete(FilePath path, Context cx)
    throws IOException
  {
    throw new IOException("ZipSpace is readonly");
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
    return getChildren(roots());
  }

////////////////////////////////////////////////////////////////
// BFileSpace
////////////////////////////////////////////////////////////////

  /**
   * Map the file path to an instance of BZipFileStore.
   */
  @Override
  public BIFile findFile(FilePath path)
  {
    BIFile file = roots();
    for(int i=0; i<path.depth(); ++i)
    {
      file = getChild(file, path.nameAt(i));
      if (file == null) return null;
    }
    return file;
  }

  /**
   * Map the file path to an instance of BZipFileStore.
   */
  @Override
  public BIFileStore findStore(FilePath path)
  {
    BIFile file = findFile(path);
    if (file == null) return null;
    return file.getStore();
  }

  /**
   * Get the child of the specified directory or return null.
   */
  @Override
  public BIFile getChild(BIFile dir, String name)
  {
    BIFileStore store = dir.getStore();
    if (store instanceof BZipFileDirectory)
    {
      BZipFileDirectory zipStore = (BZipFileDirectory)store;
      return zipStore.get(name);
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
    BIFileStore store = dir.getStore();
    if (store instanceof BZipFileDirectory)
    {
      BZipFileDirectory zipStore = (BZipFileDirectory)store;
      return zipStore.list();
    }
    return new BIFile[0];
  }

  /**
   * Join a file space ord with the given file path.  This relies on
   * the fact that the a file space ord should not end with slash, and
   * a filepath should.
   */
  @Override
  protected BOrd appendFilePathToOrd(BOrd baseOrd, FilePath filePath)
  {
    if (baseOrd == null) return null;
    if (baseOrd.toString().isEmpty()) return BOrd.make(filePath);
    return BOrd.make(baseOrd, filePath);
  }

////////////////////////////////////////////////////////////////
// Space
////////////////////////////////////////////////////////////////

  /**
   * Get ord passed to constructor.
   */
  @Override
  public BOrd getOrdInSession()
  {
    return ordInSession;
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  /**
   * Get short description.
   */
  @Override
  public String getNavDescription(Context cx)
  {
    return null;
  }

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
    return getChild(roots(), navName);
  }

  /**
   * Return <code>listFiles()</code>
   */
  @Override
  public BINavNode[] getNavChildren()
  {
    return listFiles();
  }

////////////////////////////////////////////////////////////////
// Load
////////////////////////////////////////////////////////////////

  /**
   * Load a jar file directory.  This is rather a pain
   * in the ass because we have to imply the directory
   * structure from the entries.
   */
  private synchronized BDirectory roots()
  {
    if (roots != null) return roots;

    roots = new BDirectory(new BZipFileDirectory(this, rootPath));
    try
    {
      Enumeration<? extends ZipEntry> e = zip.entries();
      while(e.hasMoreElements())
      {
        ZipEntry entry = e.nextElement();

        // can't count on directories?
        if (entry.isDirectory()) continue;

        // if this is a module filter out these files

        // NOTE: ZipPath doesn't allow backslash, and java.util.zip impls interpret backslash as '/',
        // so we should replace backslash in entry name with forward slash here
        String name = entry.getName().replace('\\', '/');
        if (isModule)
        {
          if (name.endsWith(".class")) continue;
          if (name.endsWith(".properties")) continue;
          if (name.startsWith("META-INF/")) continue;
          if (name.startsWith("meta-inf/")) continue;
        }

        FilePath path = rootPath.merge(name);

        // map to a BIFile instance
        BZipFileEntry store = new BZipFileEntry(this, path, entry);
        BIFile file = makeFile(store);

        // map to a directory, and add to list
        BDirectory dir = mapToDirectory(path);
        BZipFileDirectory dirStore = (BZipFileDirectory)dir.getStore();
        dirStore.add(file);
      }
    }
    catch(Exception e)
    {
      log.log(Level.SEVERE, "Loading zip: " + zip, e);
    }
    return roots;
  }

  @Override
  public BHost getHost()
  {
    return (parentSpace == null) ? null : parentSpace.getHost();
  }

  @Override
  public BISession getSession()
  {
    return (parentSpace == null) ? null : parentSpace.getSession();
  }

  /**
   * Walk the path until we get to the parent directory.
   */
  protected BDirectory mapToDirectory(FilePath path)
  {
    BDirectory dir = this.roots;
    for(int i=0; i<path.depth()-1; ++i)
    {
      BZipFileDirectory store = (BZipFileDirectory)dir.getStore();
      dir = store.getOrMakeDir(path.nameAt(i));
    }
    return dir;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  protected static final Logger log = Logger.getLogger("file");

  BSpace parentSpace;
  BOrd ordInSession;
  private ZipFile zip;
  BZipFile bZipFile;
  BDirectory roots;
  FilePath rootPath;
  boolean isModule;
  Charset charset;   // @since 4.10u8 / 4.13u3 / 4.14

}
