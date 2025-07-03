/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.baja.naming.BOrd;
import javax.baja.nav.BINavNode;
import javax.baja.nav.BNavRoot;
import javax.baja.nav.NavEvent;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BSpace;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BSubSpaceFile is a data file that contains a sub-space with
 * its own object hierarchy and navigation scheme.
 *
 * @author    Brian Frank
 * @creation  24 Jan 03
 * @version   $Revision: 8$ $Date: 6/15/10 9:51:14 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BSubSpaceFile
  extends BDataFile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BSubSpaceFile(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSubSpaceFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BSubSpaceFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BSubSpaceFile()
  {
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Return true if there is an open space for this file.
   */
  public boolean isOpen()
  {
    return cache.get(getAbsoluteOrd()) != null;
  }

  /**
   * Get the space which is contained within this file.
   */
  public BSpace getSubSpace()
  {
    CacheItem item = cache.get(getAbsoluteOrd());
    return (item != null) ? item.space : null;
  }

  /**
   * Get the list of open files.
   */
  public static BSubSpaceFile[] listOpen()
  {
    synchronized (cache)
    {
      CacheItem[] items = cache.values().toArray(new CacheItem[cache.size()]);
      BSubSpaceFile[] files = new BSubSpaceFile[items.length];
      for(int i=0; i<files.length; i++)
      {
        log.fine("ListIsOpen[" + i + "]: " + items[i].file.getAbsoluteOrd());
        files[i] = items[i].file;
      }
      return files;
    }
  }

  /**
   * Open the sub space for this file.
   */
  public BSpace open()
  {
    // Every ord resolution of a BIFile usually results in a
    // new instance being created.  This means we can't use
    // the BIFile itself to cache the open space.  Rather we
    // map to the absolute ords.

    synchronized (cache)
    {
      BOrd key = getAbsoluteOrd();
      CacheItem item = cache.get(key);
      if (item != null)
      {
        // check if the timestamp has changed which means
        // that someone has changed the file beneath us
        if (item.openTimestamp != this.getLastModified().getMillis())
        {
          log.warning("File has been modified: " + item.file);
          close(false);
        }
        else
        {
          return item.space;
        }
      }

      // first access, we need to open it
      log.fine("Open: " + key);

      item = new CacheItem();
      item.space = doOpen();
      item.file = this;
      item.openTimestamp = getLastModified().getMillis();
      cache.put(key, item);
      return item.space;
    }
  }

  /**
   * Save this file.
   */
  public void save() throws Exception
  {
    CacheItem item;
    synchronized (cache)
    {
      BOrd key = getAbsoluteOrd();
      log.fine("Save: " + key);
      item = cache.get(key);
    }
    if (item != null)
    {
      item.file.doSave();
      item.openTimestamp = item.file.getLastModified().getMillis();
    }
  }

  /**
   * Reload is basically a close and then open.
   */
  public void reload() throws Exception
  {
    close(false);
    open();
  }

  /**
   * Close the specified file.
   */
  public void close()
  {
    close(true);
  }

  void close(boolean close)
  {
    CacheItem item;
    synchronized (cache)
    {
      BOrd key = getAbsoluteOrd();
      log.fine("Close: " + key);
      item = cache.get(key);
      if (item != null)
      {
        log.fine("isModified: " + item.file.isModified());
        item.file.doClose();
        item.openTimestamp = 0;
        cache.remove(key);
      }
    }

    // fire replaced nav event to force all
    // nav tree's to collapse their children
    if (item != null)
    {
      BINavNode navParent = item.file.getNavParent();
      if(navParent != null)
      {
        BOrd parent = navParent.getNavOrd();
        String filename = item.file.getFileName();
        Context context = close ? BFacets.make("close", BBoolean.TRUE) : BFacets.NULL;
        BNavRoot.INSTANCE.fireNavEvent(NavEvent.makeReplaced(parent, filename, context));
      }
    }
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Return if the associated space is modified.
   */
  public abstract boolean isModified();

  /**
   * Open the associated space.
   */
  protected abstract BSpace doOpen();

  /**
   * Save the associated space.
   */
  protected abstract void doSave()
    throws Exception;

  /**
   * Close the associated space.
   */
  protected abstract void doClose();

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
   * Return <code>open().getNavChild(name)</code>.
   */
  @Override
  public BINavNode getNavChild(String name)
  {
    return open().getNavChild(name);
  }

  /**
   * Return <code>open().getNavChildren()</code>.
   */
  @Override
  public BINavNode[] getNavChildren()
  {
    return open().getNavChildren();
  }

////////////////////////////////////////////////////////////////
// CacheItem
////////////////////////////////////////////////////////////////

  static class CacheItem
  {
    public long openTimestamp;
    public BSubSpaceFile file;
    public BSpace space;
  }

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  public static final Logger log = Logger.getLogger("subSpaceFile");
  /*static { log.setSeverity(Log.TRACE); }*/

  private static final Map<BOrd, CacheItem> cache = new HashMap<>();

}
