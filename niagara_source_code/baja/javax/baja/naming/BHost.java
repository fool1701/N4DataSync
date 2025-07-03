/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import javax.net.SocketFactory;

import javax.baja.nav.BINavNode;
import javax.baja.nav.BNavContainer;
import javax.baja.nav.BNavRoot;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SortUtil;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;

import com.tridium.util.IFoxSession;

/**
 * BHost is the navigation node used to organize navigation 
 * by hosts. Hosts represent a physical box. The BLocalHost.INSTANCE singleton
 * is used to represent the local machine. BHosts default to be a child of the
 * first level of the navigation tree mounted directly under BNavRoot.INSTANCE.
 * Starting in Niagara 4.13, they can also live inside a BNavFolder for better
 * organization of the main NavTree.
 *
 * @author    Brian Frank on 14 Jan 03
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BHost
  extends BNavContainer
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BHost(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHost.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Cache
////////////////////////////////////////////////////////////////

  /**
   * Get a BHost by its hostname.  Hostname is case 
   * insensitive.  Return null if no matching hostname 
   * is found.
   */
  public static BHost getHost(String hostname)
  {                         
    if (hostname.isEmpty()) return BLocalHost.INSTANCE;
    String key = TextUtil.toLowerCase(hostname);
    return cache.get(key);
  }

  /**
   * Get all the hosts which is BLocalHost.INSTANCE + getRemoteHosts().
   */
  public static BHost[] getAllHosts()
  {
    BHost[] remote = getRemoteHosts();
    BHost[] all = new BHost[remote.length+1];
    all[0] = BLocalHost.INSTANCE;
    System.arraycopy(remote, 0, all, 1, remote.length);
    return all;
  }
  
  /**
   * Get the remote hosts.  This is all the mounted 
   * hosts exclusing the BLocalHost.INSTANCE.
   */
  public static BHost[] getRemoteHosts()
  {
    BHost[] remote = new BHost[cache.size()-cachedLocalHostCount];
    String[] keys = new String[remote.length];
    Iterator<BHost> it = cache.values().iterator();
    for(int n=0; it.hasNext(); )
    {
      BHost host = it.next();
      if (host != BLocalHost.INSTANCE)
      {
        remote[n] = host;
        keys[n] = host.hostname;
        n++;
      }
    }
    SortUtil.sort(keys, remote);
    return remote;
  }

  /**
   * Mount a host into the nav tree under BNavRoot.INSTANCE and 
   * add it to the host cache by hostname.
   */
  public static void mount(BHost host)
  {
    String key = TextUtil.toLowerCase(host.getHostname());
    if (cache.get(key) != null) throw new IllegalArgumentException("Already mounted " + key);
    cache.put(key, host);
    BNavRoot.INSTANCE.addNavChild(host);
  }
  
  /**
   * Unmount a host from the cache and from its Nav Parent.
   */
  public static void unmount(BHost host)
  {
    String key = TextUtil.toLowerCase(host.getHostname());
    cache.remove(key);
    ((BNavContainer) host.getNavParent()).removeNavChild(host);
  }

  /**
   * Move a host to a new Nav Parent. In workbench, this relationship
   * will be persisted to the navTree xml.
   * @since Niagara 4.13
   */
  public static void move(BHost host, BNavContainer newParent)
  {
    BNavContainer navParent = (BNavContainer) host.getNavParent();
    if (newParent == navParent || navParent == null)
    {
      return;
    }
    navParent.removeNavChild(host);
    newParent.addNavChild(host);
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor with the hostname.
   */
  protected BHost(String hostname)
  {
    super(hostname);
    this.hostname = hostname;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the hostname.
   */
  public String getHostname()
  {
    return hostname;
  }

  /**
   * Set a Display Name for the host as viewed in the Nav Tree.
   * Exiting the workbench will save this to the navTree xml file.
   *
   * @param navDisplayName A string or a BFormat string
   * @since Niagara 4.8
   */
  public void setNavDisplayName(String navDisplayName)
  {
    if (navDisplayName == null || navDisplayName.equals("null") || navDisplayName.trim().isEmpty())
    {
      navDisplayNameFormat = DEFAULT_NAVDISPLAY_FORMAT;
    }
    else
    {
      navDisplayNameFormat = navDisplayName;
    }
  }

  /**
   * Get the nav display name set by the user or the default name
   *
   * @param cx
   * @return
   * @since Niagara 4.8
   */
  @Override
  public String getNavDisplayName(Context cx)
  {
    if (navDisplayNameFormat != null && !navDisplayNameFormat.equals(""))
    {
      BFormat navDisplayFormat = BFormat.make(navDisplayNameFormat);
      return navDisplayFormat.format(this, cx);
    }
    return getDefaultNavDisplayName(cx);
  }

  /**
   * A default display name to show in the nav tree for a host
   *
   * @param cx
   * @return String - Default display name to appear on the nav tree
   * @since Niagara 4.8
   */
  public String getDefaultNavDisplayName(Context cx)
  {
    return super.getNavDisplayName(cx);
  }

  /**
   * Get the BFormat backed by the nav display name for this host
   *
   * @return The nav display name BFormat string
   * @since Niagara 4.8
   */
  public String getNavDisplayFormat()
  {
    return navDisplayNameFormat;
  }

  /**
   * Get the station name of the first station connected to this host
   *
   * @since Niagara 4.8
   * @return The station name string or return null
   */
  public String getStationName()
  {
    try
    {
      return Arrays.stream(getNavChildren())
        .filter(navChild -> {
          return (navChild instanceof IFoxSession) && ((IFoxSession) navChild).getStationName() != null;
        })
        .findFirst()
        .map(navNode -> ((IFoxSession) navNode).getStationName())
        .orElse(null);
    }
    catch (Exception nx)
    {
      return null;
    }
  }

  /**
   * Get all the children sessions mounted under this host.
   */
  public BISession[] getChildSessions()
  {
    List<BISession> v = new ArrayList<>();
    BINavNode[] children = getNavChildren();
    for(int i=0; i<children.length; ++i)
      if (children[i] instanceof BISession)
        v.add((BISession)children[i]);
    return v.toArray(new BISession[v.size()]);
  }
    
  /**
   * Open a TCP socket to this host on the specified port.
   */
  public abstract Socket openSocket(int port)
    throws IOException;
  
  /**
   * Open a TCP socket to this host on the specified port.  Subclasses
   * should override this method with implementation specific timeout
   * behavior. 
   * @param timeout in milliseconds
   * @since Niagara 3.6
   */
  public Socket openSocket(int port, int timeout)
    throws IOException
  {
    return openSocket(port);
  }

  /**
   * Open a TCP socket to this host on the specified port
   * using the provided socket factory.
   * @since Niagara 3.7
   */
  public abstract Socket openSocket(int port, SocketFactory socketFactory)
    throws IOException;

  /**
   * Open a TCP socket to this host on the specified port.  Subclasses
   * should override this method with implementation specific timeout
   * behavior. 
   * @param timeout in milliseconds
   * @since Niagara 3.7
   */
  public abstract Socket openSocket(int port, SocketFactory socketFactory, int timeout)
    throws IOException;
    

  /**
   * Open a UDP datagram socket to this host on the specified port.
   */
  public abstract DatagramSocket openDatagramSocket(int port)
    throws IOException;

  /**
   * Return hostname.
   */
  @Override
  public String toString(Context cx)
  {
    return hostname;
  }

////////////////////////////////////////////////////////////////
// Connection State
////////////////////////////////////////////////////////////////  

  /**
   * The default returns {@code getConnectedSessionCount() > 0}
   */
  public boolean isConnected()
  {
    return getConnectedSessionCount() > 0;
  }

  /**
   * Get the number of sessions which currently return
   * true for {@code BISession.isConnected()}.
   */
  public int getConnectedSessionCount()
  {
    return (int)Stream.of(getNavChildren())
      .filter(session -> session instanceof BISession && ((BISession)session).isConnected())
      .count();
  }
  
  /**
   * Disconnect this host:
   * <ul>
   * <li>disconnectAllSessions()</li>
   * <li>doDisconnect()</li>
   * </ul>
   */
  public final void disconnect()
  {
    disconnectAllSessions();
    doDisconnect();
  }
  
  /**
   * Subclass hook for disconnect.
   */
  protected void doDisconnect()
  {
  }
  
  /**
   * Call {@code disconnect} all the child sessions.
   */
  public void disconnectAllSessions()
  {
    BISession[] sessions = getChildSessions();
    for(int i=0; i<sessions.length; ++i)
      sessions[i].disconnect();
  }

  /**
   * Close this host:
   * <ul>
   * <li>closeAllSessions()</li>
   * <li>doClose()</li>
   * <li>unmount(this)</li>
   * </ul>
   */
  public final void close()
  {
    closeAllSessions();
    doClose();
    unmount(this);
  }
  
  /**
   * Subclass hook for close.
   */
  protected void doClose()
  {
  }

  /**
   * Call {@code close} all the child sessions.
   */
  public void closeAllSessions()
  {
    BISession[] sessions = getChildSessions();
    for(int i=0; i<sessions.length; ++i)
      sessions[i].close();
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  /**
   * Get host's ord.
   */
  public abstract BOrd getAbsoluteOrd();

  /**
   * Return {@code getAbsoluteOrd()}
   */
  @Override
  public BOrd getNavOrd()
  {
    return getAbsoluteOrd();
  }                               

  @Override
  public void addNavChild(BINavNode child)
  {     
    super.addNavChild(child);
    sortNavChildren();
  }

  @Override
  public void removeNavChild(BINavNode child)
  {
    super.removeNavChild(child);
    sortNavChildren();
  }

  void sortNavChildren()
  {
    BINavNode[] kids = getNavChildren();
    String[] keys = new String[kids.length];
    for(int i=0; i<kids.length; ++i)
    {        
      BINavNode kid = kids[i];
      if (kid instanceof BISession)
      {
        keys[i] = kid.getType().getTypeName();
      }
      else
      { 
        // leave at top
        keys[i] = "";
      }
    }
    SortUtil.sort(keys, kids);
    reorderNavChildren(kids);
  }
  
////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////  

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return isConnected() ? iconConnected : iconDisconnected; }
  static final BIcon iconConnected = BIcon.std("host.png");
  static final BIcon iconDisconnected = BIcon.std("hostDisconnected.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  static final Map<String, BHost> cache = new ConcurrentHashMap<>();

  static int cachedLocalHostCount = 0;

  String hostname;

  String navDisplayNameFormat = "";

  /**
   * The BFormat compatible string that formats to the default name of a BHost
   * as seen in the nav tree.
   *
   * @since Niagara 4.8
   */
  public static final String DEFAULT_NAVDISPLAY_FORMAT = "%getDefaultNavDisplayName%";
}
