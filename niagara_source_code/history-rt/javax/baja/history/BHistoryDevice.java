/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import java.util.Comparator;

import javax.baja.agent.AgentList;
import javax.baja.bql.BIRelational;
import javax.baja.category.BCategoryMask;
import javax.baja.collection.BITable;
import javax.baja.collection.ColumnList;
import javax.baja.collection.TableCursor;
import javax.baja.naming.BLocalHost;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.UnresolvedException;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SortUtil;
import javax.baja.security.BIProtected;
import javax.baja.security.BPermissions;
import javax.baja.space.BISpaceNode;
import javax.baja.space.BSpace;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIObject;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bql.collection.TypeColumnList;
import com.tridium.history.BHistoryFolder.HistoryIdTableCursor;
import com.tridium.history.IdCursor;
import com.tridium.util.HistoryCategoryUtil;

/**
 * BHistoryDevice represents a source device for histories.
 *
 * @author    John Sublett
 * @creation  27 Mar 2003
 * @version   $Revision: 21$ $Date: 6/1/10 2:43:29 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BHistoryDevice
  extends BObject
  implements BISpaceNode, BITable<BHistoryId>, BIRelational<BIObject>, BIProtected
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BHistoryDevice(2979906276)1.0$ @*/
/* Generated Thu Jan 27 19:02:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryDevice.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Constructor.
   */
  public BHistoryDevice(BHistorySpace space, String deviceName)
  {
    this.space = space;
    this.deviceName = deviceName;

    ordInSpace = BOrd.make("history:/" + deviceName);
  }

////////////////////////////////////////////////////////////////
// BISpaceNode
////////////////////////////////////////////////////////////////

  /**
   * Get the space for this node.
   */
  @Override
  public BSpace getSpace() { return space; }

  /**
   * Return <code>getOrdInSpace()</code>.
   */
  @Override
  public BOrd getOrdInSession()
  {
    return getOrdInSpace();
  }

  /**
   * Get the ord which identifies this entry in its space.
   * This ord should be relative with the space as the base.
   * If not mounted then return null.
   */
  @Override
  public BOrd getOrdInSpace()
  {
    if (space == null)
      return null;
    else
      return ordInSpace;
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
  public boolean isPendingMove() { return false; }

  /**
   * Set the pending move flag.
   */
  @Override
  public void setPendingMove(boolean pendingMove) {}

////////////////////////////////////////////////////////////////
// Nav
////////////////////////////////////////////////////////////////

  /**
   * Get the device name.
   */
  public String getDeviceName()
  {
    return deviceName;
  }

  @Override
  public BOrd getNavOrd()
  {
    return BOrd.make(space.getNavOrd() + "/" + deviceName);

    //if (navOrd == null)
    //  navOrd = BOrd.make(space.getNavOrd() + "/" + deviceName);

    //return navOrd;
  }

  /**
   * Get the name to use for navigation.
   */
  @Override
  public String getNavName()
  {
    return deviceName;
  }

  /**
   * Get the display name to use for navigation.
   */
  @Override
  public String getNavDisplayName(Context cx)
  {
    return deviceName;
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getNavIcon() { return getIcon(); }

  /**
   * Get the description.
   */
  @Override
  public String getNavDescription(Context cx)
  {
    return null;
  }

  /**
   * Get the parent in the nav tree.
   */
  @Override
  public BINavNode getNavParent()
  {
    BINavNode navParent = space;
    String[] groupNames = BHistoryService.getHistoryGroupNames(this);
    if ((groupNames != null) && (groupNames.length > 0))
      navParent = space.getNavChild("history:///");

    return navParent;
  }

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
    BHistoryId id = BHistoryId.make(deviceName, name);
    try (HistorySpaceConnection conn = space.getConnection(null))
    {
      boolean exists = conn.exists(id);
      if (exists)
      {
          return conn.getHistory(id);
      }
      else
      {
        return null;
      }
    }
  }

  /**
   * Get the child with the specified name.
   */
  @Override
  public BINavNode resolveNavChild(String name)
  {
    BINavNode child = getNavChild(name);
    if (child == null)
      throw new UnresolvedException("/" + deviceName + "/" + name);
    else
      return child;
  }

  /**
   * Get the children of this node.
   */
  @Override
  public BINavNode[] getNavChildren()
  {
    BIHistory[] histories = space.listHistories(this);
    SortUtil.sort(histories, histories, histNameComparator);
    return histories;
  }

  /**
   * Get the hash code.
   */
  public int getNavHashCode() { return deviceName.hashCode(); }

  /**
   * Add the specified BINavNode to this container.
   */
  public void addNavChild(BINavNode child)
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Add the specified BINavNode to this container.
   */
  public void removeNavChild(BINavNode child)
  {
    throw new UnsupportedOperationException();
  }

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
    return cx.getPermissionsForTarget().hasAdminWrite();
  }

  @Override
  public boolean canInvoke(OrdTarget cx)
  {
    return false;
  }

////////////////////////////////////////////////////////////////
// BIRelational
////////////////////////////////////////////////////////////////

  @Override
  public BITable<BIObject> getRelation(String id, Context cx)
  {
    BOrd histOrd = BOrd.make(getOrdInSpace(), new HistoryQuery(id));
    try
    {
      return (BITable<BIObject>)histOrd.get(BLocalHost.INSTANCE, cx);
    }
    catch(UnresolvedException e)
    {
      return null;
    }
  }

  @Override
  public TableCursor<BHistoryId> cursor()
  {
    BIHistory[] histories = space.listHistories(this);
    return new HistoryIdTableCursor(this, new IdCursor(histories, Context.NULL));
  }

  @Override
  public ColumnList getColumns()
  {
    if (columnList == null)
    {
      columnList = new TypeColumnList(BHistoryId.TYPE, Context.NULL);
    }
    return columnList;
  }

  @Override
  public BFacets getTableFacets()
  {
    return BFacets.NULL;
  }

  ////////////////////////////////////////////////////////////////
// Agents
////////////////////////////////////////////////////////////////

  /**
   * Get the list of agents for this BObject.  The
   * default implementation of this method returns
   * <code>Registry.getAgents()</code>
   */
  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.add("workbench:NavContainerView");
    agents.remove("workbench:CollectionTable");
    return agents;
  }

////////////////////////////////////////////////////////////////
// HistoryNameComparator
////////////////////////////////////////////////////////////////

  // TODO NCCB-18025 Override hashCode method when equals method is overriden (history module)
  @SuppressWarnings("overrides")
  private static class HistoryNameComparator
    implements Comparator<BIHistory>
  {
    @Override
    public int compare(BIHistory h1, BIHistory h2)
    {
      return h1.getNavDisplayName(null).compareTo(h2.getNavDisplayName(null));
    }

    @Override
    public boolean equals(Object o)
    {
      return o instanceof HistoryNameComparator;
    }

  }

////////////////////////////////////////////////////////////////
// Icon
////////////////////////////////////////////////////////////////

  private static final BIcon icon = BIcon.std("device.png");
  @Override
  public BIcon getIcon() { return icon; }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static Comparator<BIHistory> histNameComparator = new HistoryNameComparator();

  private BHistorySpace space;
//  private BOrd          navOrd;
  private BOrd          ordInSpace;
  private String        deviceName;
  private ColumnList columnList;
}
