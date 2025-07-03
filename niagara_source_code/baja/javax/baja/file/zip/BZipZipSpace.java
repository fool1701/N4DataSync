/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.zip;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.zip.*;

import javax.baja.file.BDirectory;
import javax.baja.file.BIFile;
import javax.baja.file.BIFileStore;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BZipZipSpace - space for a zip file in a zip file.
 *
 * @author Robert Adams
 * @creation 11/6/13
 * @since Baja 1.0
 */
@NiagaraType
public class BZipZipSpace
  extends BZipSpace
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.zip.BZipZipSpace(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BZipZipSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BZipZipSpace(BZipFile file)
  {
    super("zip");
    this.parentSpace = file.getSpace();
    this.ordInSession = file.getOrdInSession();
    this.bZipFile = file;
    parentZipentry = getEntry(file.getStore());
    roots();
  }

  ZipEntry parentZipentry;


  /** Return stream position at specified zipentry */
  @Override
  public InputStream getInputStream(ZipEntry zipEntry)
    throws IOException
  {
    ZipInputStream zin = getZipStream();

    // Must walk the stream to this entry
    try
    {
      ZipEntry entry;
      while( (entry = zin.getNextEntry())!=null)
      {
        if(entry.getName().equals(zipEntry.getName()))
        {
          return zin;
        }
      }
      
      throw new IOException("Zip entry not found:" + zipEntry.getName());
    }
    catch (IOException e)
    {
      zin.close();
      throw e;
    }
  }

  private ZipEntry getEntry(BIFileStore store)
  {
    if (store instanceof BZipFileEntry)
    {
      return ((BZipFileEntry)store).getZipEntry();
    }
    else
    {
      System.out.println("store: " + (store==null ? "null" : store.getClass().getName()) );
      throw new IllegalStateException("Only BZipFileEntry supported");
    }
  }

  /** Get input stream for this zip within a zip. Wrap in ZipInputStream. */
  ZipInputStream  getZipStream()
  {
    try
    {
      return new ZipInputStream(((BZipSpace)parentSpace).getZip().getInputStream(parentZipentry));
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return null;
    }

  }

  private synchronized BDirectory roots()
  {
    if (roots != null) return roots;
    roots = new BDirectory(new BZipFileDirectory(this, new ZipPath("/")));

    try (ZipInputStream zin = getZipStream())
    {
//      Enumeration e = zip.entries();
      ZipEntry entry;
      while( (entry = zin.getNextEntry())!=null)
      {

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

        // parse the path
        ZipPath path = new ZipPath("/" + name);

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
      log.log(Level.SEVERE, "Loading zip: " + parentZipentry.getName(), e);
    }
    return roots;
  }
}
