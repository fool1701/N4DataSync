/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.net;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BInternetAddress models an Internet address which is a
 * composite of a hostname (or raw IP address) and a port
 * number.  
 *
 * @author    Brian Frank       
 * @creation  12 Jan 01
 * @version   $Revision: 4$ $Date: 4/29/08 10:29:23 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BInternetAddress
  extends BSimple
{
  /**
   * The null address.
   */
  public static final BInternetAddress NULL = new BInternetAddress("null");

  /**
   * The default address is the null address.
   */
  public static final BInternetAddress DEFAULT = NULL;
  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.net.BInternetAddress(2979906276)1.0$ @*/
/* Generated Tue Sep 14 16:05:54 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BInternetAddress.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with host and port.  A port number
   * of -1 indicates to use the default port.
   */
  public BInternetAddress(String host, int port)
  {
    //this method needs to check to see if the host is an IPv6 numeric string,
    //if so, you need to wrap it with brackets if it does not have them so that
    //url and authority are correct. Precedence for this behavior is set by the
    //constructor for URL(), which will wrap un bracketed v6 hosts, if one is passed.
    
    if (host.indexOf(':') != -1)
    {
      //host contains a ':', so lets assume its a v6 address..
      if (!host.startsWith("[") && !host.endsWith("]"))
      {
         //if not already wrapped by '[' ']', do it for them, as this will mess up URLs if not
         host = "[" + host + "]";
      }        
    }    
    
    this.host = TextUtil.toLowerCase(host);
    this.port = port;
    this.authority = (port == -1) ? host : host + ":" + port;
  }
  
  /**
   * Construct an internet address from the specified
   * address which may be "host" or "host:port".
   * If there is no port, then the port will be set
   * to -1. Also, this method needs to be aware of numeric IPv6 addressing schemes.
   * Therefore, the input could be [host]:port OR host:port
   */
  public BInternetAddress(String authority)
  {
    try
    {
      this.authority = authority;
      if (authority.equals("null"))
      {
        this.host = null;
        this.port = -1;
      }
      else
      {
        int lastColon = authority.lastIndexOf(':'),
            firstColon = authority.indexOf(':');
    
        if (firstColon < 0)
        {
          //no ports are specified, and this is not a IPv6 numeric address
          //so it must be a hostname, or a IPv4 numeric address without a port specified
          this.host = TextUtil.toLowerCase(authority);
          this.port = -1;
        }
        else
        {
          if (firstColon != lastColon)
          {
            //there is more than one colon in this authority, so this must be a numeric IPv6 address,
            //but we don't know if there is a port specified yet, so let's figure that out
            
            int openBracket = authority.indexOf('['),
                closeBracket = authority.lastIndexOf(']');
          
            if (openBracket < 0 || closeBracket < 0)
            {
              
              //this is an incorrect format, there HAS to be a opening and closing brace
              throw new IllegalArgumentException("Can't determine host from port for " + authority + ", IPv6 Numeric Hosts need to be wrapped by '[' ']' ");
            }
      
            this.host = TextUtil.toLowerCase(authority.substring(0, closeBracket+1));
                        
            //now if the index of the lastColon is greater than that of the closing brace, then they must have specified a port, since
            //that colon needs to come after the closing brace
            if (lastColon > closeBracket)
            {
              this.port = Integer.parseInt(authority.substring(lastColon+1));
            }
            else if (closeBracket < (authority.length() - 1))
            {
              //this is an incorrect format, they can't specify more after the ] without :
              throw new IllegalArgumentException("Can't determine host from port for " + authority + ", IPv6 Numeric Hosts need ':' after ']' to specify port");
            }
            else
            {
              this.port = -1;
            }
          }
          else
          {
            //there is only one colon in this address, meaning it must be either a hostname, or a numeric IPv4 address, so parse as before
            this.host = TextUtil.toLowerCase(authority.substring(0, firstColon));
            this.port = Integer.parseInt(authority.substring(firstColon+1));
          }
        }
      }
    }
    catch(Exception e)
    {
      throw new IllegalArgumentException("Invalid BInternetAddress: " + authority);
    }
  }
  
////////////////////////////////////////////////////////////////
// BInternetAddress
////////////////////////////////////////////////////////////////  

  /**
   * Get the authority string which is the host if port 
   * is -1 or the "host:port" if the port is not -1.
   */
  public String getAuthority()
  {
    return authority;
  }

  /**
   * Get the host for this internet address 
   * of null if the address is null.
   */
  public String getHost()
  {
    return host;
  }
  
  /**
   * Get the port number for this internet address
   * of -1 if the default port should be used based
   * on the address's context.
   */
  public int getPort()
  {
    return port;
  }
  
  /**
   * Get the java.net.InetAddress for the host if
   * it can be resolved.  This method always attempts
   * to resolve the host into an InetAddress (as
   * opposed to caching the result).
   *
   * @throws UnknownHostException if the host cannot
   *    be mapped into a valid IP address in this VM.
   */
  public InetAddress getAddress()
    throws UnknownHostException
  {
    return InetAddress.getByName(host);
  }
  
  /**
   * Get this internet address as a URL.
   */
  public URL getURL(String protocol, String path)
  {
    try
    {
      return new URL(protocol, host, port, path);
    }
    catch(MalformedURLException e)
    {
      throw new IllegalArgumentException(e.toString());
    }
  }
  
  /**
   * The equivalent method is a form of equals which
   * compares actual IP addresses by resolving the 
   * hostname to its InetAddress. 
   */
  public boolean equivalent(InetAddress host, int port)
  {
    if (this.port != port) return false;
    if (this.host.equalsIgnoreCase(host.getHostName())) return true;
    
    try
    {
      return host.equals(getAddress());
    }
    catch(UnknownHostException e)
    {
      return false;
    }
  }

  /**
   * The equivalent method is a form of equals which
   * compares actual IP addresses by resolving the 
   * hostname to its InetAddress. 
   */
  public boolean equivalent(BInternetAddress addr)
  {
    if (this.port != addr.port) return false;
    if (this.host.equalsIgnoreCase(addr.host)) return true;
    
    try
    {
      return equivalent(addr.getAddress(), addr.port);
    }
    catch(UnknownHostException e)
    {
      return false;
    }
  }

////////////////////////////////////////////////////////////////
// BSimple Defaults
////////////////////////////////////////////////////////////////

  /**
   * Return true if this address is null.
   */
  public boolean isNull()
  {
    return host == null;
  }

  /**
   * Default implementation of hashCode is based
   * on the authority string.
   */
  public int hashCode()
  {
    return authority.hashCode();
  }

  /**
   * Equality of BInternetAddresses is based on 
   * identical ports and case insensitive equal
   * host Strings.  Since a given host may have
   * multiple aliases, this equals method does not
   * return whether two BInternetAddresses point
   * to the same IP address.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BInternetAddress)
    {
      BInternetAddress x = (BInternetAddress)obj;
      if (host == null) return (x.host == null);
      if (x.host == null) return false;
      if (port != x.port) return false;
      return host.equals(x.host);
    }
    return false;
  }
      
  /**
   * Default implementation is serialized using 
   * writeUTF() of encodeToString().
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF( encodeToString() );
  }
  
  /**
   * Default implementation is unserialized 
   * using readUTF() and decodeFromString().
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString( in.readUTF() );
  }

  /**
   * The string encoding is "hostname[:port]".
   */
  public String encodeToString()
  {
    return authority;
  }

  /**
   * The string encoding is "hostname[:port]".
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      return new BInternetAddress(s);
    }
    catch(IllegalArgumentException e)
    { 
      throw new IOException(e.getMessage());
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private String authority;
  private String host;
  private int port;
  
}
