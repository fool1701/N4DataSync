/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.baja.category.BCategoryMask;
import javax.baja.category.BICategorizable;
import javax.baja.collection.AbstractCursor;
import javax.baja.dataRecovery.BIDataRecoverySource;
import javax.baja.dataRecovery.IDataRecoveryRecord;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.UnresolvedException;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SortUtil;
import javax.baja.security.BIProtected;
import javax.baja.security.BPermissions;
import javax.baja.space.BSpace;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BIObject;
import javax.baja.sys.BIPropertySpace;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInterface;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Cursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconText;

import com.tridium.history.BRootHistoryFolder;
import com.tridium.history.IdCursor;
import com.tridium.nav.BINavSupport;
import com.tridium.sys.schema.Fw;
import com.tridium.util.HistoryCategoryUtil;

/**
 * BHistorySpace is the space that manages access to histories
 * in a history database.
 *
 * @author    John Sublett
 * @creation  05 Mar 2003
 * @version   $Revision: 37$ $Date: 8/3/10 1:04:04 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@SuppressWarnings("squid:S1448")
public abstract class BHistorySpace
  extends BSpace
  implements BICategorizable, BIProtected, BIPropertySpace, BIDataRecoverySource
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BHistorySpace(2979906276)1.0$ @*/
/* Generated Thu Jan 27 19:02:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistorySpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public BHistorySpace()
  {
    super("history", LexiconText.make("history", "space.history"));
  }

////////////////////////////////////////////////////////////////
// Nav
////////////////////////////////////////////////////////////////

  /**
   * Children are lazily loaded so always return true.
   */
  @Override
  public boolean hasNavChildren()
  {
    return true;
  }

  /**
   * Get the child with the specified name.
   */
  @Override
  public BINavNode getNavChild(String name)
  {
//    if (BHistoryService.getOrganizeHistoriesByProperties(this))
//    {
      String[] groupNames = BHistoryService.getHistoryGroupNames(this);
      if (groupNames != null && groupNames.length > 0)
      {
        boolean init = folderCache == null || folderCache.isEmpty();
        if (init)
        {
          getNavChildren();
        }
        BINavNode node = null;
        try
        {
          Map<String,BINavNode> childFolders = folderCache.get(this);
          node = childFolders.get(name);
          if (node == null && !init && this instanceof javax.baja.history.db.BHistoryDatabase)
          { // If station side, since we aren't listening for nav events, it's
            // possible that this nav child was just created (and hasn't been
            // put into the cache yet), so we just need to requery and repopulate
            // the cache to see if we can find it.
            getNavChildren();
            childFolders = folderCache.get(this);
            node = childFolders.get(name);
          }
        }
        catch (Exception e) {}

        if (node != null)
        {
          return node;
        }
      }
//    }
    return super.getNavChild(name);
  }

  /**
   * Resolve the nav child with the specified name.
   */
  @Override
  public BINavNode resolveNavChild(String name)
  {
    BINavNode child = getNavChild(name);
    if (child == null)
    {
      throw new UnresolvedException(name);
    }
    else
    {
      return child;
    }
  }

  /**
   * Get all of the nav children.
   */
  @Override
  public BINavNode[] getNavChildren()
  {
//    long startTicks = Clock.ticks();
//    try
//    {

      String[] groupNames = BHistoryService.getHistoryGroupNames(this);
      if (groupNames != null && groupNames.length > 0)
      {
        boolean init = folderCache == null;
        if (init)
        {
          folderCache = Collections.synchronizedMap(new HashMap<BInterface, Map<String,BINavNode>>());
        }
        else if ((BINavSupport.getNavSupport() != null) &&
                 BINavSupport.getNavSupport().performingRefresh(this))
        {
          folderCache.clear(); // On a deep refresh, clear the cache!
        }

        Map<String,BINavNode> childFolders = Collections.synchronizedMap(new HashMap<String,BINavNode>());
        int len = groupNames.length;
        List<BINavNode> folders = new ArrayList<BINavNode>(len);
        for (int i = 0; i < len; i++)
        {
          BRootHistoryFolder rootFolder = new BRootHistoryFolder(this, new String[] { groupNames[i] }, this);
          BPermissions p = rootFolder.getPermissions(null);
          if (!p.hasOperatorRead())
          {
            continue;
          }
          folders.add(rootFolder);
          childFolders.put(rootFolder.getNavName(), rootFolder);
        }

        // Don't forget the default nav folder at the end
        BRootHistoryFolder defaultRoot = new BRootHistoryFolder(this, null, this);
        boolean addDefault = defaultRoot.getPermissions(null).hasOperatorRead();
        len = folders.size();
        int resultSize = addDefault ?len+1:len;
        BINavNode[] result = new BINavNode[resultSize];
        for (int i = 0; i < len; i++)
        {
          result[i] = folders.get(i);
        }

        if (addDefault)
        {
          result[len] = defaultRoot; // Add the default nav folder to the end
          childFolders.put(result[len].getNavName(), result[len]);
        }

        folderCache.put(this, childFolders);

        return result;
      }

    if (folderCache != null)
    {
      folderCache.clear();
      folderCache = null;
    }

    BHistoryDevice[] devs = listDevices();
    SortUtil.sort(devs, devs, devNameComparator);
    return devs;

//    }
//    finally
//    {
//      long elapsed = Clock.ticks() - startTicks;
//      System.out.println("******** getNavChildren for historySpace took "+elapsed+" ms");
//    }
  }

  /**
   * The ord in the session is always "history:".
   */
  @Override
  public BOrd getOrdInSession()
  {
    return ordInSession;
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
    return HistoryCategoryUtil
      .getCategoryMask(getOrdInSession(), Sys.getStation().getStationName());
  }

  /**
   * Files are mapped to categories by ord in <code>CategoryService.ordMap</code>.
   */
  @Override
  public BCategoryMask getAppliedCategoryMask()
  {
    return HistoryCategoryUtil.getAppliedCategoryMask(getOrdInSession(), Sys.getStation().getStationName());
  }

////////////////////////////////////////////////////////////////
// IProtected
////////////////////////////////////////////////////////////////

  @Override
  public BPermissions getPermissions(Context cx)
  {
    if (cx != null && cx.getUser() != null)
    {
      return cx.getUser().getPermissionsFor(this);
    }
    else
    {
      return BPermissions.all;
    }
  }

  @Override
  public boolean canRead(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().hasOperatorRead();
  }

  @Override
  public boolean canWrite(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().hasAdminWrite();
  }

  @Override
  public boolean canInvoke(OrdTarget cx)
  {
    return false;
  }

////////////////////////////////////////////////////////////////
// HistorySpaceConnection
////////////////////////////////////////////////////////////////

  public abstract HistorySpaceConnection getConnection(Context cx);

  /**
   * Create a connection to the history space, specifying whether or not
   * to exclude archive history data (e.g. history data archived to the cloud
   * or some other archive provider) during any subsequent queries run by the
   * connection.
   *
   * @param excludeArchiveData When true, archive history data will not be used
   *                          by any queries run by the returned connection
   *                          (only local history data will be considered). When
   *                          false, archive history data will be used to
   *                          supplement local data for any queries run by the
   *                          returned connection.
   * @param cx The Context to use in association with the connection.
   * @return A connection to the history space.
   *
   * @since Niagara 4.11
   */
  public final HistorySpaceConnection getConnection(boolean excludeArchiveData, Context cx)
  {
    Context context = excludeArchiveData?HistoryQuery.makeExcludeArchiveDataContext(cx):cx;
    return getConnection(context);
  }

////////////////////////////////////////////////////////////////
// Read
////////////////////////////////////////////////////////////////

  /**
   * Get the name of the device that owns this history space.
   */
  public abstract String getDeviceName();

  /**
   * Get a cursor of the history ids for the corresponding ord.
   * If the ord is to a history, then the only id in the cursor
   * is the history ord.  If the ord is to a history device,
   * then the cursor includes the ids of all histories for the device.
   *
   * @param ord An ord the specifies a set of ids either by
   *   device or history id.
   * @return Returns a cursor of the history ids for the ord.
   */
  public Cursor<BHistoryId> getHistoryIds(BOrd ord)
  {
    OrdQuery[] q = ord.parse();
    if (q.length != 1 || !(q[0] instanceof HistoryQuery))
    {
      throw new HistoryException("Ord must contain only a history query.");
    }

    HistoryQuery hq = (HistoryQuery)q[0];

    if (hq.isHistoryQuery())
    {
      return new IdCursor(hq.getHistoryId(), null);
    }
    else if (hq.isDeviceQuery())
    {
      BHistoryDevice dev = new BHistoryDevice(this, hq.getDeviceName());
      return new IdCursor(listHistories(dev), null);
    }
    else if (hq.isHistoryFolderQuery())
    {
      String[] groupNames = BHistoryService.getHistoryGroupNames(this);
      if (groupNames != null && groupNames.length > 0)
      {
        String[] rootPath = hq.getHistoryFolderPath();

        // TODO FIXX How do I know if rootPath[0] is the default case??
        String[] annotationNames = BHistoryService.getSortPropertiesForGroup(this, rootPath[0]);

        if (annotationNames != null && rootPath.length > 1)
        {
          // Find all histories below this folder root
          List<BIHistory> histories = new ArrayList<BIHistory>();
          BHistoryDevice[] devs = listDevices();
          for (int i = 0; i < devs.length; i++)
          {
            BIHistory[] h = listHistories(devs[i]);
            for (int j = 0; j < h.length; j++)
            {
              boolean match = true;
              for (int k = 1; k < rootPath.length; k++)
              {
                BValue val = h[j].get(annotationNames[k-1]);
                if (val == null || !val.toString().equals(rootPath[k]))
                {
                  match = false;
                  break;
                }
              }
              if (match)
              {
                histories.add(h[j]);
              }
            }
          }
          return new IdCursor(histories.toArray(new BIHistory[histories.size()]), null);
        }
      }
    }

    throw new UnsupportedOperationException("Unrecognized history set: " + ord.toString());
  }

  /**
   * List the source devices for the histories in this space.
   *
   * @return Returns an array of the names for devices that have
   *   histories in this space.
   */
  public abstract BHistoryDevice[] listDevices()
    throws HistoryException;

  /**
   * List this histories that are stored in this space for
   * the specified device.
   *
   * @return Returns an array of history ids for the histories
   *   that originated from the specified device.
   */
  public abstract BIHistory[] listHistories(BHistoryDevice device)
    throws HistoryException;

  /**
   * Determine whether this space contains histories from the
   * specified device.
   *
   * @param deviceName The name of the device.
   */
  public abstract boolean deviceExists(String deviceName)
    throws HistoryException;

  /**
   * Get the history device with the specified name.
   *
   * @param deviceName The name of the device.
   */
  public abstract BHistoryDevice getDevice(String deviceName)
    throws HistoryException;

  /**
   * Get the configuration for the history with the specified id.
   *
   * @param id The unique identifier for the target history.
   *
   * @return Returns the configuration for the specified history
   *   or null if the history is not found.
   */
  public abstract BHistoryConfig getConfig(BHistoryId id)
    throws HistoryException;

////////////////////////////////////////////////////////////////
// DeviceNameComparator
////////////////////////////////////////////////////////////////

  // TODO NCCB-18025 Override hashCode method when equals method is overriden (history module)
  @SuppressWarnings("overrides")
  private static class DeviceNameComparator
    implements Comparator<BHistoryDevice>
  {
    @Override
    public int compare(BHistoryDevice dev1, BHistoryDevice dev2)
    {
      return dev1.getDeviceName().compareTo(dev2.getDeviceName());
    }

    @Override
    public boolean equals(Object o)
    {
      return o instanceof DeviceNameComparator;
    }
  }

////////////////////////////////////////////////////////////////
// Display
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("navOnly/historyService.png");


////////////////////////////////////////////////////////////////
// IPropertySpace
////////////////////////////////////////////////////////////////

  /**
   * Find the histories with a property with the specified name.
   *
   * @param objectType The common record type of the histories to return in the result.
   *   If null, the result is not filtered by history record type.
   * @param baseOrd The base ord from which to start
   *   searching (this must be an Ord resolving to a particular history device, or single history).
   *   Only histories that are descendants of this base history device will be included in the result.
   *   If null, the search will include the entire history space and span all history devices.
   * @param propertyName The name of the property to search for.
   * @param cx The context for the search.
   *
   * @since Niagara 3.5
   */
  @Override
  public Cursor<? extends BIObject> findObjects(Type objectType, BOrd baseOrd, String propertyName, Context cx)
  {
    return findObjects(objectType, baseOrd, propertyName, (Type)null, cx);
  }

  /**
   * Find the histories with a property with the specified name and type.
   *
   * @param objectType The common record type of the histories to return in the result.
   *   If null, the result is not filtered by history record type.
   * @param baseOrd The base ord from which to start
   *   searching (this must be an Ord resolving to a particular history device, or single history).
   *   Only histories that are descendants of this base history device will be included in the result.
   *   If null, the search will include the entire history space and span all history devices.
   * @param propertyName The name of the property to search for.
   * @param propertyType The type of the property to search for.  If null,
   *   any types will be included that have the given propertyName.
   * @param cx The context for the search.
   *
   * @since Niagara 3.5
   */
  @Override
  public Cursor<? extends BIObject> findObjects(Type objectType, BOrd baseOrd, String propertyName, Type propertyType, Context cx)
  {
    return new ContainerCursor(findHistories(objectType, baseOrd, cx), propertyName, propertyType, cx);
  }

  /**
   * Find the histories with the specified property name and value.
   *
   * @param objectType The common record type of the histories to return in the result.
   *   If null, the result is not filtered by history record type.
   * @param baseOrd The base ord from which to start
   *   searching (this must be an Ord resolving to a particular history device, or single history).
   *   Only histories that are descendants of this base history device will be included in the result.
   *   If null, the search will include the entire history space and span all history devices.
   * @param propertyName The name of the property to search for.
   * @param propertyValue The value of the property to search for.
   * @param cx The context for the search.
   *
   * @since Niagara 3.5
   */
  @Override
  public Cursor<? extends BIObject> findObjects(Type objectType, BOrd baseOrd, String propertyName, BValue propertyValue, Context cx)
  {
    return new ContainerCursor(findHistories(objectType, baseOrd, cx), propertyName, propertyValue, cx);
  }

  /**
   * Find the distinct values for the specified property name within the history space.
   *
   * @param objectType The common record type of the histories used to compute the result.
   *   If null, the result is not filtered by history record type.
   * @param baseOrd The base ord from which to start
   *   searching (this must be an Ord resolving to a particular history device, or single history).
   *   Only histories that are descendants of this base history device will be included in the result.
   *   If null, the search will include the entire history space and span all history devices.
   * @param propertyName The name of the property to search for.
   * @param cx The context for the search.
   *
   * @since Niagara 3.5
   */
  @Override
  public Cursor<? extends BIObject> findDistinctValues(Type objectType, BOrd baseOrd, String propertyName, Context cx)
  {
    return findDistinctValues(objectType, baseOrd, propertyName, null, cx);
  }

  /**
   * Find the distinct values for the specified property name and type within the history space.
   *
   * @param objectType The common record type of the histories used to compute the result.
   *   If null, the result is not filtered by history record type.
   * @param baseOrd The base ord from which to start
   *   searching (this must be an Ord resolving to a particular history device, or single history).
   *   Only histories that are descendants of this base history device will be included in the result.
   *   If null, the search will include the entire history space and span all history devices.
   * @param propertyName The name of the property to search for.
   * @param propertyType The type of the property to search for.  If null,
   *   any types will be included that have the given propertyName.
   * @param cx The context for the search.
   *
   * @since Niagara 3.5
   */
  @Override
  public Cursor<? extends BIObject> findDistinctValues(Type objectType, BOrd baseOrd, String propertyName, Type propertyType, Context cx)
  {
    return new DistinctPropertyCursor(findHistories(objectType, baseOrd, cx), propertyName, propertyType, cx);
  }

  /**
   * Index the specified property.  Indexed propertys should be more efficient to search.
   * Implementation of indexing is optional.
   *
   * @param propertyName The name of the property to index.
   * @param cx The context for the operation.
   *
   * @return Returns true if the requested index was created, false otherwise.
   *
   * @since Niagara 3.5
   */
  @Override
  public boolean addIndex(String propertyName, Context cx)
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Remove the index for the specified property.
   *
   * @param propertyName The name of the property that should no longer be indexed.
   * @param cx The context for the operation.
   *
   * @since Niagara 3.5
   */
  @Override
  public void removeIndex(String propertyName, Context cx)
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns an array of histories that have a record type matching the
   * given objectType, and which reside within the given base ord.
   *
   * @since Niagara 3.5
   */
  private BIHistory[] findHistories(Type objectType, BOrd baseOrd, Context cx)
  {
    List<BIHistory> matches = new ArrayList<BIHistory>();
    if (baseOrd != null && !baseOrd.isNull())
    {
      try (Cursor<BHistoryId> ids = getHistoryIds(baseOrd);
           HistorySpaceConnection conn = getConnection(null))
      {
        while (ids.next())
        {
          BIHistory history = conn.getHistory(ids.get());
          if (history != null &&
              (objectType == null || history.getRecordType().getResolvedType().is(objectType)))
          {
            matches.add(history);
          }
        }
      }
    }
    else
    {
      BHistoryDevice[] devices = listDevices();
      if (devices != null)
      {
        for (int i = 0; i < devices.length; i++)
        {
          BIHistory[] histories = listHistories(devices[i]);
          for (int j = 0; j < histories.length; j++)
          {
            if (histories[j] != null &&
                (objectType == null || histories[j].getRecordType().getResolvedType().is(objectType)))
            {
              matches.add(histories[j]);
            }
          }
        }
      }
    }

    return matches.toArray(new BIHistory[matches.size()]);
  }


////////////////////////////////////////////////////////////////
// ContainerCursor
////////////////////////////////////////////////////////////////

  /**
   * A cursor of histories that have the given property name and type.
   *
   * @since Niagara 3.5
   */
  static class ContainerCursor extends AbstractCursor<BIHistory>
  {
    ContainerCursor(BIHistory[] histories, String propertyName, Type propertyType, Context cx)
    {
      this.histories = histories;
      this.propertyName = propertyName;
      this.propertyType = propertyType;
      this.cx = cx;
      idx = -1;
    }

    ContainerCursor(BIHistory[] histories, String propertyName, BValue propertyValue, Context cx)
    {
      this(histories, propertyName, propertyValue.getType(), cx);
      this.propertyValue = propertyValue;
    }

    /**
     * If there is a context associated with the Cursor, then
     * return the Context instance.  Otherwise return null.
     */
    @Override
    public Context getContext() { return cx; }

    /**
     * The cursor is initially placed before the first slot.
     * Calling next() advances the cursor to the next slot, and
     * returns true if it is positioned on a valid slot, or
     * false if the cursor has reached the end of the iteration.
     */
    @Override
    protected boolean advanceCursor()
    {
      return next(null);
    }

    /**
     * Like next(), but the cursor advances to the next object
     * that is an instance of the specified class.
     */
    public boolean next(Class<?> cls)
    {
      while(idx < histories.length-1)
      {
        idx++;
        BIHistory history = histories[idx];
        if (history != null &&
            (cls == null || cls.isInstance(history)))
        {
          BValue val = history.get(propertyName);
          if (val == null)
          {
            continue;
          }

          if ((propertyType == null || val.getType().is(propertyType)) &&
              (propertyValue == null || val.equals(propertyValue)))
          {
            return true;
          }
        }
      }

      return false;
    }

    /**
     * Get the object at the current cursor position.
     */
    @Override
    public BIHistory doGet()
    {
      return histories[idx];
    }

    BIHistory[] histories;
    String propertyName;
    Type propertyType;
    BValue propertyValue;
    Context cx;
    int idx;
  }


////////////////////////////////////////////////////////////////
// DistinctPropertyCursor
////////////////////////////////////////////////////////////////

  /**
   * A cursor of distinct values with the given property name.
   *
   * @since Niagara 3.5
   */
  static class DistinctPropertyCursor extends AbstractCursor<BValue>
  {
    DistinctPropertyCursor(BIHistory[] histories, String propertyName, Type propertyType, Context cx)
    {
      this.histories = histories;
      this.propertyName = propertyName;
      this.propertyType = propertyType;
      this.cx = cx;
      idx = -1;
    }

    /**
     * If there is a context associated with the Cursor, then
     * return the Context instance.  Otherwise return null.
     */
    @Override
    public Context getContext()
    {
      return cx;
    }

    /**
     * The cursor is initially placed before the first slot.
     * Calling next() advances the cursor to the next slot, and
     * returns true if it is positioned on a valid slot, or
     * false if the cursor has reached the end of the iteration.
     */
    @Override
    protected boolean advanceCursor()
    {
      return next(null);
    }

    /**
     * Like next(), but the cursor advances to the next object
     * that is an instance of the specified class.
     */
    public boolean next(Class<?> cls)
    {
      while (idx < histories.length - 1)
      {
        idx++;
        BIHistory history = histories[idx];
        if (history != null)
        {
          BValue val = history.get(propertyName);
          if (val == null)
          {
            continue;
          }

          if ((cls == null || cls.isInstance(val)) &&
            (propertyType == null || val.getType().is(propertyType)) &&
            !propertyValues.contains(val))
          {
            propertyValues.add(val);
            current = val;
            return true;
          }
        }
      }

      return false;
    }

    /**
     * Get the object at the current cursor position.
     */
    @Override
    public BValue doGet()
    {
      return current;
    }

    BIHistory[] histories;
    String propertyName;
    Type propertyType;
    List<BValue> propertyValues = new ArrayList<BValue>();
    Context cx;
    BValue current;
    int idx;
  }

////////////////////////////////////////////////////////////////
// BIDataRecoverySource
////////////////////////////////////////////////////////////////
  
  /**
   * Called by the DataRecoveryService when the service enters the reserve state (save started).
   *
   * @since Niagara 4.10
   */
  @Override
  public void dataRecoveryReserve()
    throws Exception
  {
    // Only the local history database will override this method
  }

  /**
   * Called by the DataRecoveryService when a record should be restored.
   *
   * @since Niagara 3.6
   */
  @Override
  public boolean dataRecoveryRestore(IDataRecoveryRecord rec)
    throws Exception
  {
    // Only the local history database will override this method
    return false;
  }

  /**
   * Callback to indicate that no further records will be restored to this source.
   *
   * @since Niagara 3.6
   */
  @Override
  public void dataRecoveryRestoreComplete() { }

  /**
   * Provide spy information for the history space's data recovery.
   * The default implementation is to do nothing.
   *
   * @since Niagara 3.6
   */
  @Override
  public void dataRecoverySpy(SpyWriter out, Iterator<IDataRecoveryRecord> recoveryData)
    throws Exception
  {
    // Only the local history database will override this method
  }

////////////////////////////////////////////////////////////////
// Framework use only
////////////////////////////////////////////////////////////////

  /**
   * Framework support access; this method should
   * never be used by developers.
   */
  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    if (x == Fw.USER_DEFINED_4)
    {
      return folderCache;
    }
    return super.fw(x, a, b, c, d);
  }


////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  /**
   * Dump spy info.
   */
  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);

    // Dump out the contents of the folder cache for the history space
    out.startProps();
    out.trTitle("FolderCache", 2);
    if (folderCache != null)
    {
      Map<String,BINavNode> map = folderCache.get(this);
      if (map != null)
      {
        synchronized(map)
        {
          Iterator<String> i = map.keySet().iterator();
          while(i.hasNext())
          {
            Object nKey = i.next();
            out.prop(nKey.toString(), map.get(nKey).getNavOrd().toString());
          }
        }
      }
      else
      {
        out.prop("History nav children not lazy loaded yet.", "");
      }
    }
    else
    {
      out.prop("FolderCache is null", "");
    }

    out.endProps();
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static BOrd ordInSession = BOrd.make("history:");
  static Comparator<BHistoryDevice> devNameComparator = new DeviceNameComparator();

  Map<BInterface, Map<String,BINavNode>> folderCache;
}
