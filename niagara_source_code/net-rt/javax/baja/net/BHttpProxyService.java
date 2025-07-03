/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.net;

import java.io.IOException;
import java.net.Authenticator;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.InvalidParameterException;
import java.security.PrivilegedAction;
import java.util.Base64;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.naming.BHost;
import javax.baja.naming.BIpHost;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.IFilter;
import javax.baja.security.BPassword;
import javax.baja.status.BStatus;
import javax.baja.sys.BAbstractService;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BIRestrictedComponent;

import com.tridium.net.BProxyAuthenticationType;
import com.tridium.net.HttpDigestClient;
import com.tridium.net.NiagaraHttpProxySelector;
import com.tridium.net.NiagaraProxyAuthenticator;

/**
 * BProxyService is used to support connections to
 * the Internet through a non-transparent proxy.
 * <p>
 * TODO IPV6
 *
 * @author Lee Adcock on 15 Feb 08
 * @since Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "false",
  override = true
)
@NiagaraProperty(
  name = "server",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "port",
  type = "int",
  defaultValue = "0"
)
@NiagaraProperty(
  name = "exclusions",
  type = "String",
  defaultValue = "10.0.0.0/8, 172.16.0.0/12, 192.168.0.0/16"
)
@NiagaraProperty(
  name = "authenticationScheme",
  type = "BProxyAuthenticationType",
  defaultValue = "BProxyAuthenticationType.none"
)
@NiagaraProperty(
  name = "user",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "password",
  type = "BPassword",
  defaultValue = "BPassword.make(\"\")"
)
@SuppressWarnings({ "squid:S1313", "squid:S1200" })
public class BHttpProxyService
  extends BAbstractService
  implements BIRestrictedComponent
{


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.net.BHttpProxyService(2678241895)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, false, null);

  //endregion Property "enabled"

  //region Property "server"

  /**
   * Slot for the {@code server} property.
   * @see #getServer
   * @see #setServer
   */
  public static final Property server = newProperty(0, "", null);

  /**
   * Get the {@code server} property.
   * @see #server
   */
  public String getServer() { return getString(server); }

  /**
   * Set the {@code server} property.
   * @see #server
   */
  public void setServer(String v) { setString(server, v, null); }

  //endregion Property "server"

  //region Property "port"

  /**
   * Slot for the {@code port} property.
   * @see #getPort
   * @see #setPort
   */
  public static final Property port = newProperty(0, 0, null);

  /**
   * Get the {@code port} property.
   * @see #port
   */
  public int getPort() { return getInt(port); }

  /**
   * Set the {@code port} property.
   * @see #port
   */
  public void setPort(int v) { setInt(port, v, null); }

  //endregion Property "port"

  //region Property "exclusions"

  /**
   * Slot for the {@code exclusions} property.
   * @see #getExclusions
   * @see #setExclusions
   */
  public static final Property exclusions = newProperty(0, "10.0.0.0/8, 172.16.0.0/12, 192.168.0.0/16", null);

  /**
   * Get the {@code exclusions} property.
   * @see #exclusions
   */
  public String getExclusions() { return getString(exclusions); }

  /**
   * Set the {@code exclusions} property.
   * @see #exclusions
   */
  public void setExclusions(String v) { setString(exclusions, v, null); }

  //endregion Property "exclusions"

  //region Property "authenticationScheme"

  /**
   * Slot for the {@code authenticationScheme} property.
   * @see #getAuthenticationScheme
   * @see #setAuthenticationScheme
   */
  public static final Property authenticationScheme = newProperty(0, BProxyAuthenticationType.none, null);

  /**
   * Get the {@code authenticationScheme} property.
   * @see #authenticationScheme
   */
  public BProxyAuthenticationType getAuthenticationScheme() { return (BProxyAuthenticationType)get(authenticationScheme); }

  /**
   * Set the {@code authenticationScheme} property.
   * @see #authenticationScheme
   */
  public void setAuthenticationScheme(BProxyAuthenticationType v) { set(authenticationScheme, v, null); }

  //endregion Property "authenticationScheme"

  //region Property "user"

  /**
   * Slot for the {@code user} property.
   * @see #getUser
   * @see #setUser
   */
  public static final Property user = newProperty(0, "", null);

  /**
   * Get the {@code user} property.
   * @see #user
   */
  public String getUser() { return getString(user); }

  /**
   * Set the {@code user} property.
   * @see #user
   */
  public void setUser(String v) { setString(user, v, null); }

  //endregion Property "user"

  //region Property "password"

  /**
   * Slot for the {@code password} property.
   * @see #getPassword
   * @see #setPassword
   */
  public static final Property password = newProperty(0, BPassword.make(""), null);

  /**
   * Get the {@code password} property.
   * @see #password
   */
  public BPassword getPassword() { return (BPassword)get(password); }

  /**
   * Set the {@code password} property.
   * @see #password
   */
  public void setPassword(BPassword v) { set(password, v, null); }

  //endregion Property "password"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHttpProxyService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// IService
////////////////////////////////////////////////////////////////

  /**
   * Return the types to be registered under.
   */
  @Override
  public Type[] getServiceTypes()
  {
    return new Type[]{ TYPE };
  }

////////////////////////////////////////////////////////////////
// BIRestrictedComponent
////////////////////////////////////////////////////////////////

  /**
   * Only one allowed to live under the station's BServiceContainer.
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    BIRestrictedComponent.checkParentForRestrictedComponent(parent, this);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * If a proxy service exists that is configured to route connections
   * to the provided host, return the proxy service.  If none exists,
   * return null.
   */
  public static BHttpProxyService get(BHost host)
  {
    //check for null/instanceof in the correct order
    if (!(host instanceof BIpHost))
    {
      return null;
    }

    // fail fast if no proxies defined
    if (!Sys.findService(TYPE).isPresent())
    {
      return null;
    }

    // Find all proxy services
    BComponent[] candidates;
    try
    {
      candidates = Sys.getServices(TYPE);
    }
    catch (ServiceNotFoundException snf)
    {
      log.log(Level.FINEST, "no services found", snf);
      return null;
    }

    // Cycle through all available proxy services
    for (BComponent candidate : candidates)
    {
      BHttpProxyService proxy = (BHttpProxyService)candidate;
      if (proxy.getEnabled((BIpHost)host))
      {
        log.fine(() -> String.format("Connecting to %s through proxy %s", host.toString(null), proxy.getServer()));
        return proxy;
      }
    }
    return null;
  }

  /**
   * Find a matching BProxyService that has the same hostname and port
   *
   * @param hostname The hostname of the proxy service being searched
   * @param port     The port of the proxy service being searched
   * @return The matching proxy service or null of no match found
   */
  @SuppressWarnings("squid:S1166")
  public static BHttpProxyService find(String hostname, int port)
  {
    // fail fast if no proxies defined
    if (!Sys.findService(TYPE).isPresent())
    {
      return null;
    }

    // Find all proxy services
    BComponent[] candidates;
    try
    {
      candidates = Sys.getServices(TYPE);
    }
    catch (ServiceNotFoundException snf)
    {
      log.finest("no proxy services configured");
      return null;
    }

    log.fine(() -> String.format("looking for proxy authentication for requestor %s:%d", hostname, port));

    // Cycle through all available proxy services
    for (BComponent candidate : candidates)
    {
      BHttpProxyService proxy = (BHttpProxyService)candidate;
      if (hostname.equalsIgnoreCase(proxy.getServer()) && port == proxy.getPort())
      {
        log.fine(() -> String.format("authenticating to %s", proxy.getServer()));
        return proxy;
      }
    }
    return null;
  }

  /**
   * Creates and returns a java Proxy object that maps to this proxy service.
   *
   * @return the created Proxy object
   */
  public Proxy getProxy()
  {
    InetSocketAddress sockAddr = new InetSocketAddress(getServer(), getPort());
    return new Proxy(Proxy.Type.HTTP, sockAddr);
  }

  /**
   * Returns true if this proxy service is enabled and the provided
   * host does not match an item on the exclusion list.
   */
  private boolean getEnabled(BIpHost host)
  {
    // return false if this proxy is disabled
    if (!getEnabled())
    {
      return false;
    }

    if (exclusionList == null)
    {
      loadExclusionList();
    }

    // return false if host matches exclusion list
    for (IFilter iFilter : exclusionList)
    {
      if (iFilter.accept(host))
      {
        if (log.isLoggable(Level.FINE))
        {
          log.fine("Host " + host.toString(null) + " matches exclusion '" + iFilter + "' for proxy " + getServer());
        }
        return false;
      }
    }

    return true;
  }

  /**
   * Open a socket to the proxy server.
   */
  public Socket openSocket()
    throws IOException
  {
    try
    {
      // Open a socket to the proxy server
      return new BIpHost(getServer()).openSocket(getPort());
    }
    catch (ConnectException e)
    {
      setStatus(BStatus.makeFault(getStatus(), true));
      setFaultCause("Unable to connect to proxy server.");
      log.log(Level.SEVERE, "Unable to connect to proxy server at " + getServer(), e);
      throw e;
    }
    catch (UnknownHostException e)
    {
      setStatus(BStatus.makeFault(getStatus(), true));
      setFaultCause("Unknown host: " + e.getMessage());
      log.log(Level.SEVERE, "Unable to resolve proxy server " + getServer(), e);
      throw e;
    }
    catch (IOException e)
    {
      setStatus(BStatus.makeFault(getStatus(), true));
      setFaultCause(e.getMessage());
      log.log(Level.SEVERE, "Unable to connect to proxy server.", e);
      throw e;
    }
  }

  /**
   * Open a socket to the proxy server.
   *
   * @param timeout in milliseconds
   */
  public Socket openSocket(int timeout)
    throws IOException
  {
    try
    {
      // Open a socket to the proxy server
      return new BIpHost(getServer()).openSocket(getPort(), timeout);
    }
    catch (ConnectException e)
    {
      setStatus(BStatus.makeFault(getStatus(), true));
      setFaultCause("Unable to connect to proxy server.");
      log.log(Level.SEVERE, "Unable to connect to proxy server at " + getServer(), e);
      throw e;
    }
    catch (UnknownHostException e)
    {
      setStatus(BStatus.makeFault(getStatus(), true));
      setFaultCause("Unknown host: " + e.getMessage());
      log.log(Level.SEVERE, "Unable to resolve proxy server " + getServer(), e);
      throw e;
    }
    catch (IOException e)
    {
      setStatus(BStatus.makeFault(getStatus(), true));
      setFaultCause(e.getMessage());
      log.log(Level.SEVERE, "Unable to connect to proxy server.", e);
      throw e;
    }
  }

  /**
   * Returns the value for the HTTP authentication header, or
   * null if no authentication is required.
   *
   * @return the authorization header, or null
   */
  public String getAuthentication()
  {
    return getAuthentication(null, null, null);
  }

  /**
   * Returns the value for the HTTP authentication header, or
   * null if no authentication is required.
   *
   * @param authenticateHeader the value of the Proxy-Authenticate header
   * @param method             the request method
   * @param uri                the request uri
   * @return the authorization header, or null
   */
  public String getAuthentication(String authenticateHeader, String method, String uri)
  {
    StringBuilder out = new StringBuilder();

    if (getAuthenticationScheme().getOrdinal() == BProxyAuthenticationType.BASIC)
    {
      out.append("Basic ");
      out.append(new String(Base64.getEncoder()
        .encode((getUser() + ':' + AccessController.doPrivileged((PrivilegedAction<String>)getPassword()::getValue))
          .getBytes(StandardCharsets.UTF_8))));
      return out.toString();
    }
    else if (getAuthenticationScheme().getOrdinal() == BProxyAuthenticationType.DIGEST)
    {
      if (digestClient == null)
      {
        digestClient = new HttpDigestClient(getUser(), getPassword());
      }
      try
      {
        return digestClient.getAuthentication(authenticateHeader, method, uri);
      }
      catch (Exception e)
      {
        log.log(Level.SEVERE, "Proxy authentication failed.", e);
      }
    }
    return null;
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.make("module://net/icons/proxy.png");

  @Override
  public void started()
    throws Exception
  {
    loadExclusionList();
    super.started();

    synchronized (DEFAULT_PROXY_SELECTOR)
    {
      AccessController.doPrivileged((PrivilegedAction<Object>)() -> {
        // only ever want to do this once
        if (ProxySelector.getDefault() == DEFAULT_PROXY_SELECTOR)
        {
          ProxySelector.setDefault(new NiagaraHttpProxySelector(DEFAULT_PROXY_SELECTOR));
          Authenticator.setDefault(new NiagaraProxyAuthenticator());
          System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
        }
        return null; // nothing to return
      });
    }
  }

  @Override
  public void changed(Property property, Context context)
  {
    if (!isRunning())
    {
      return;
    }
    if (property.equals(exclusions))
    {
      loadExclusionList();
    }
    if (property.equals(user) || property.equals(password))
    {
      // null the digest client, it will be recreated at next authentication if necessary.
      digestClient = null;
    }
  }

////////////////////////////////////////////////////////////////
// Exclusion Filters
////////////////////////////////////////////////////////////////

  /**
   * Compile the exclusion list into an array of
   * exclusion filters
   */
  private void loadExclusionList()
  {
    Array<IFilter> exclusions = new Array<>(IFilter.class);
    StringBuilder exclusionText = new StringBuilder();
    boolean isHostName = false;

    // Run through each character in exclusionText
    for (int i = 0; i < getExclusions().length(); i++)
    {
      char c = getExclusions().charAt(i);
      // If this isn't a delimiter
      if (!(c == ' ' || c == ',' || c == ';'))
      {
        // Add character to the current filter's buffer
        exclusionText.append(c);
        // Test if we have evidence this is a host name, not an IP address
        if (!isHostName && !(c == '.' || c == '/' || c == '\\' || c >= 48 && c <= 57))
        {
          isHostName = true;
        }
      }

      // If this is a delimiter
      if (c == ' ' || c == ',' || c == ';' || i == getExclusions().length() - 1)
      {
        if (exclusionText.length() > 1)
        {
          if (isHostName)
          {
            // Add new host filter
            try
            {
              exclusions.add(new HostNameFilter(exclusionText.toString()));
            }
            catch (InvalidParameterException e)
            {
              // Will fail if host can not be resolved to an IP
              // have an invalid value - this is a configuration failure
              setFaultCause(e.getMessage());
              setStatus(BStatus.fault);
              log.log(Level.SEVERE, "Invalid exclusion", e);
            }
          }
          else
          {
            // Add new Ip filter
            try
            {
              exclusions.add(new Ip4Filter(exclusionText.toString()));
            }
            catch (InvalidParameterException e)
            {
              // Will fail if IP or subnet are in an invalid format or
              // have an invalid value - this is a configuration failure
              setFaultCause(e.getMessage());
              setStatus(BStatus.fault);
              log.log(Level.SEVERE, "Invalid exclusion", e);
            }
          }
          // Reset
          exclusionText = new StringBuilder();
          isHostName = false;
        }
      }
    }

    //13251 exclude connections to local host
    exclusions.add(new HostNameFilter("localhost"));
    exclusions.add(new Ip4Filter("127.0.0.1/8"));

    exclusionList = exclusions.trim();
  }

  /**
   * Used to filter ip addresses, constructor takes ip address
   * and optional subnet mask.  If no subnet mask is provided, the
   * maximum number of bits, 32, is assumed.
   * <p>
   * ex:
   * - '127.0.0.1' match only '127.0.0.1' and 'localhost'
   * - '192.168.1.1' match only '192.168.1.1'
   * - '192.168.1.1/32' match only '192.168.1.1'
   * - '192.168.1.1/24' matches IPs between '192.168.1.0' and '192.168.1.255'
   * - '192.168.1.1/12' matches IPs between '192.160.0.0' and '192.175.255.255'
   */
  private static class Ip4Filter
    implements IFilter
  {
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public Ip4Filter(String ip)
      throws InvalidParameterException
    {
      this.ip = ip;
      try
      {
        // Parse ip and subnet mask from string
        if (ip.indexOf('/') > -1)
        {
          filterAddr = InetAddress.getByName(ip.substring(0, ip.indexOf('/'))).getAddress();
          subnet = Integer.parseInt(ip.substring(ip.indexOf('/') + 1));
        }
        else if (ip.indexOf('\\') > -1)
        {
          filterAddr = InetAddress.getByName(ip.substring(0, ip.indexOf('\\'))).getAddress();
          subnet = Integer.parseInt(ip.substring(ip.indexOf('\\') + 1));
        }
        else
        {
          // No subnet specified
          filterAddr = InetAddress.getByName(ip).getAddress();
          subnet = 32;
        }
        // Ensure we have a reasonable subnet mask
        if (subnet > 32 || subnet < 1)
        {
          throw new InvalidParameterException("Invalid number of bits in subnet: " + subnet);
        }
      }
      catch (NumberFormatException e)
      {
        throw new InvalidParameterException("Incorrect subnet format.");
      }
      catch (UnknownHostException e)
      {
        log.log(Level.SEVERE, "unknown host: " + ip, e);
        throw new InvalidParameterException("Host could not be resolved.");
      }
    }

    @Override
    public boolean accept(Object obj)
    {
      BIpHost host = (BIpHost)obj;
      try
      {
        InetAddress addr = host.getInetAddress();
        byte[] checkAddr = addr.getAddress();
        int mask = subnet;
        // For each byte in the IP4 address
        for (int j = 0; j < 4; j++)
        {
          // If no bits in this byte are significant, save a few cycles and don't
          // bother comparing this or the following bytes
          if (mask - j * 8 > 0)
          {
            // Compare the significant bits in this byte
            int check = checkAddr[j] >>> 8 - Math.min(8, mask - j * 8) & 255;
            int filter = filterAddr[j] >>> 8 - Math.min(8, mask - j * 8) & 255;
            if (check != filter)
            {
              return false;
            }
          }
          else
          {
            break;
          }
        }
        return true;
      }
      catch (IOException ioe)
      {
        log.log(Level.FINE, String.format(Locale.ENGLISH, "unable to resolve %s", host), ioe);
        return false;
      }
    }

    @Override
    public String toString()
    {
      return ip;
    }

    byte[] filterAddr;
    int subnet;
    String ip;
  }

  /**
   * Used to filter hostnames, constructor takes match string
   * <p>
   * ex:
   * - '.abc.com' match on 'www.abc.com' and 'abc.com' and any ip address that resolves
   * to the domain or a sub-domain of 'abc.com'
   * - 'abc.com' matches only on 'cnn.com' and the ip address
   */
  private static class HostNameFilter
    implements IFilter
  {

    public HostNameFilter(String filter)
    {
      this.filter = filter.toLowerCase(Locale.ENGLISH);
    }

    @Override
    public boolean accept(Object obj)
    {
      BIpHost host = (BIpHost)obj;
      if (host.isNumericAddress())
      {
        try
        {
          InetAddress addr = host.getInetAddress();
          host = new BIpHost(addr.getHostName());
        }
        catch (Exception e)
        {
          log.log(Level.SEVERE, "Error resolving " + host, e);
        }
      }

      // Is this a sub-domain inclusive filter?
      if (filter.charAt(0) == '.')
      {
        // If the provided host is a sub domain, it will
        // match the filter cleanly ('www.abc.com' ends with '.abc.com')
        if (host.getHostname().endsWith(filter))
        {
          return true;
        }

        // The provided host appears to be the root domain itself
        // ('.abc.com' matches 'abc.com')
        if (host.getHostname().endsWith(filter.substring(1)))
        {
          if (host.getHostname().length() == filter.length() - 1)
          {
            return true;
          }
        }

        // no luck, must not be a match
        return false;
      }
      else
      {
        if (host.getHostname().equalsIgnoreCase(filter))
        {
          return true;
        }
        return false;
      }
    }

    @Override
    public String toString()
    {
      return filter;
    }

    String filter;
  }

  public static final ProxySelector DEFAULT_PROXY_SELECTOR;

  static
  {
    DEFAULT_PROXY_SELECTOR = AccessController.doPrivileged((PrivilegedAction<ProxySelector>)ProxySelector::getDefault);
  }

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private HttpDigestClient digestClient;
  private IFilter[] exclusionList;
  public static final Logger log = Logger.getLogger("http.proxy");
}
