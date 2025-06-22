/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.net.SocketFactory;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nre.util.IPAddressUtil;
import com.tridium.util.IFoxSession;

/**
 * BIpHost is used to represent a host machine that is identified
 * with an IP address.  The hostname of an BIpHost is either a
 * a name resolvable via DNS or is a raw numeric IP address.
 *
 * @author    Brian Frank
 * @creation  15 May 03
 * @version   $Revision: 28$ $Date: 10/1/10 10:55:28 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BIpHost
  extends BHost
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BIpHost(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BIpHost.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct an BIpHost with the specified hostname,
   * raw numeric IPv4 address or raw IPv6 address.
   */
  public BIpHost(String hostname)
  {
    super(hostname);
    
    this.ord = BOrd.make("ip:" + hostname);
	  this.isNumericAddr = IPAddressUtil.isNumericAddr(hostname);
  }
  
////////////////////////////////////////////////////////////////
// BHost
////////////////////////////////////////////////////////////////

  /**
   * Return true if this hostname is specified as a raw
   * numeric IP address or false if this IpHost is specified
   * as hostname which requires DNS resolution.
   */
  public boolean isNumericAddress()
  {
    return isNumericAddr;
  }

  /**
   * If this address is a raw numeric IP address then
   * return it as is.
   *
   * Otherwise if the address is a hostname and resolve is false, this method will return
   * the numeric address resolved by the most recent call to {@link #getInetAddress()}
   * or null if no previous calls to {@link #getInetAddress()} were made.
   *
   * If the address is a hostname and resolve is true, this method will call {@link #getInetAddress()}
   * to obtain the address.
   *
   * @param resolve false if no attempt should be made to lookup the IP address using DNS. true if DNS
   *                may be used (note: even if true, this method may returned a cached address)
   * @return a string representation of the numeric address of this BIpHost
   * @throws IOException if resolve was true, and the DNS lookup failed
   */
  public String getNumericAddress(boolean resolve)
    throws IOException
  {
    // hostname is already the numeric address if isNumericAddr == true
    if (isNumericAddr)
    {
      return hostname;
    }
    // if we're not allowed to resolve, then just return the most recently resolved inetAddress, if there is one
    if (!resolve)
    {
      if (inetAddress != null)
      {
        return inetAddress.getHostAddress();
      }
      else
      {
        return null;
      }
    }
    // Otherwise, lookup the numeric address for the host name
    return getInetAddress().getHostAddress();
  }

  /**
   * Get the java.net.InetAddress for this host.  This
   * call might result in an DNS resolution which will
   * block the calling thread an indeterminate amount
   * of time.
   *
   * The result of the address lookup is cached for use
   * by {@link #getNumericAddress(boolean)} when it
   * is called with resolve = false.
   *
   * @throws IOException if the DNS lookup failed
   */
  public InetAddress getInetAddress()
    throws IOException
  {

    // Lookup the numeric address for the given hostname. Note that this will not always do a DNS lookup as
    // InetAddress maintains it's own internal cache of addresses, which expires based on the values of the
    // networkaddress.cache.ttl and networkaddress.cache.negative.ttl values.
    InetAddress newInetAddress = InetAddress.getByName(getHostname());

    // If this address is different than the one that we got last time, clear the cached navDisplayName
    // and cache the new address
    if (!newInetAddress.equals(inetAddress))
    {
      inetAddress = newInetAddress;
      navDisplayName = null;
    }
    
    return inetAddress;
  }
  
  /**
   * Return "ip:hostname"
   */
  @Override
  public BOrd getAbsoluteOrd()
  {
    return ord;
  }

  /**
   * Open a TCP socket to this host on the specified port.
   */
  @Override
  public Socket openSocket(int port)
    throws IOException
  {
    SocketFactory socketFactory = SocketFactory.getDefault();
    return socketFactory.createSocket(getInetAddress(), port);
  }
  
  /**
   * Open a TCP socket to this host on the specified port.
   * @param timeout in milliseconds
   * @since Niagara 3.6
   */
  @Override
  public Socket openSocket(int port, int timeout)
    throws IOException
  {
    SocketFactory socketFactory = SocketFactory.getDefault();
    Socket socket = socketFactory.createSocket();
    try
    {
      socket.connect(new InetSocketAddress(getInetAddress(), port), timeout);
    }
    catch (Exception e)
    {
      socket.close();
      throw e;
    }
    return socket;
  }  

  /**
   * Open a TCP socket to this host on the specified port
   * using the provided socket factory.
   * @since Niagara 3.7
   */
  @Override
  public Socket openSocket(int port, SocketFactory socketFactory)
    throws IOException
  {
    return socketFactory.createSocket(getInetAddress(), port);
  }

  /**
   * Open a TCP socket to this host on the specified port.  Subclasses
   * should override this method with implementation specific timeout
   * behavior. 
   * @param timeout in milliseconds
   * @since Niagara 3.7
   */
  @Override
  public Socket openSocket(int port, SocketFactory socketFactory, int timeout)
    throws IOException
  {
    Socket socket = socketFactory.createSocket();
    try
    {
      socket.connect(new InetSocketAddress(getInetAddress(), port), timeout);
    }
    catch (Exception e)
    {
      // Any Error that is thrown will leave the socket open; a finally block cannot be used
      // because we must return the socket.
      socket.close();
      throw e;
    }
    return socket;
  }

  /**
   * Open a UDP socket to this host on the specified port.
   */
  @Override
  public DatagramSocket openDatagramSocket(int port)
    throws IOException
  {
    return new DatagramSocket(port, getInetAddress());
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  /**
   * Get the nav display name.
   */
  @Override
  public String getDefaultNavDisplayName(Context cx)
  {
    // if we have cached the name, use that
    if (navDisplayName != null)
      return appendStationName(navDisplayName);

    // don't attempt to use full display name
    // until some other code has done the
    // expensive call to alloc the InetAddress
    if (inetAddress == null)
      return appendStationName(hostname);

    // use InetAddress to cache nice display name
    try
    {
      // if the address is raw IP, don't ever lookup
      // the host name because it typically requires
      // yet another network lookup
      if (isNumericAddr)
      {
        navDisplayName = hostname;
      }
      // otherwise use "hostname (ip)"
      else
      {
        String host = inetAddress.getHostName();
        String ip = inetAddress.getHostAddress();
        if (host.equals(ip))
          navDisplayName = host;
        else
          navDisplayName = host + " : " + ip;
      }
    }
    catch(Exception e)
    {
      navDisplayName = hostname;
    }
    return appendStationName(navDisplayName);
  }

  /**
   * If there is a station session under this host, then
   * display it in my display name, so that I don't have to
   * exand the navtree to figure out what station is running.
   */
  String appendStationName(String name)
  {
    try
    {
      Object[] kids = getNavChildren();
      for (int i=0; i<kids.length; ++i)
      {
        if (kids[i] instanceof IFoxSession)
        {
          String stationName = ((IFoxSession)kids[i]).getStationName();
          if (stationName != null)
            return name + " (" + stationName + ")";
        }
      }
      return name;
    }
    catch(Exception e)
    {
      return name;
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BOrd ord;
  InetAddress inetAddress;
  String navDisplayName;
  boolean isNumericAddr;
  static final int CONNECTION_TIMEOUT = Integer.parseInt(AccessController.doPrivileged((PrivilegedAction<String>) () ->
    System.getProperty("ipHost.connectionTimeout", "15000")));
}
