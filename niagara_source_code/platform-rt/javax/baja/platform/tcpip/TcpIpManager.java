/*
 * Copyright 2008 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform.tcpip;

/**
 * TcpIpManager provides an interface for managing a computer's TCP/IP settings
 * <br>
 * Some settings are applied at the host level on some operating systems and
 * at the adapter level on other operating systems.   For such settings, an adapter ID
 * is always taken as a parameter in the functions that access them.   On the operating
 * systems where the setting is managed at the host level, the adapter ID parameter
 * will not be used - but consider:
 * <ul>
 * <li>Your code is more portable if it always provides a valid adapter ID, even if you're
 * confident that it isn't important for the platform you're writing for.</li>
 * <li>You shouldn't be surprised if you call a setter twice with two different adapter IDs,
 * providing two different values, and a getter returns only the most recently set value for
 * both adapters afterward.</li>
 * </ul>
 * <br>
 * A TcpIpManager can be obtained by calling <code>LocalPlatform.getTcpIpManager()</code>
 * <br>
 * Each new TcpIpManager will load the current TcpIpService properties.
 * 
 * @author    Frank Smith       
 * @creation  16 Jan 05
 * @version   $Revision: 3$ $Date: 9/11/09 1:58:18 PM EDT$
 * @since     Baja 1.0
 */
public interface TcpIpManager
{
////////////////////////////////////////////////////////////////
// Adapters
////////////////////////////////////////////////////////////////
  
  /**
   * Return an array of IDs for this computer's network adapters which support TCP/IP
   */
  String[] getAdapterIds();

////////////////////////////////////////////////////////////////
// Default Gateway
////////////////////////////////////////////////////////////////
  
  /**
   * If the platform usesAdapterLevelSettings property is true
   * <ul>
   * <li>return the specified adapter's defaultGateway if the adapterId is valid</li>
   * <li>otherwise return the first adapter's defaultGateway</li>
   * </ul>
   * Else
   * <ul>
   * <li>return the platform's default gateway</li>
   * </ul>
   *
   * @param adapterId identifies the target adapter
   */
  String getDefaultGateway(String adapterId);
    
  /**
   * If the platform usesAdapterLevelSettings property is true
   * <ul>
   * <li>set the specified adapter's defaultGateway if the adapterId is valid</li>
   * <li>otherwise set the first adapter's defaultGateway</li>
   * </ul>
   * Else
   * <ul>
   * <li>set the platform's default gateway</li>
   * </ul>
   *
   * @param adapterId identifies the target adapter
   * @param gateway is the new default
   * @throws IllegalStateException if DHCP is enabled
   */
  void setDefaultGateway(String adapterId, String gateway)
    throws IllegalStateException;

////////////////////////////////////////////////////////////////
// DHCP
////////////////////////////////////////////////////////////////
  
  /**
   * Return the isDhcpEnabled property of the adapter with the specified id
   * <br>
   * If the adapterId is invalid, return the first adapter's isDhcpEnabled property
   *
   * @param adapterId identifies the target adapter
   */
  boolean isDhcpEnabled(String adapterId);
    
  /**
   * Set the isDhcpEnabled property of the adapter with the specified id
   * <br>
   * If the adapterId is invalid, set the first adapter's isDhcpEnabled property
   *
   * @param adapterId contains the target adapterId
   * @param enable is the new setting
   * @throws IllegalStateException if DHCP is not supported or the settings are readonly
   */
  void enableDhcp(String adapterId, boolean enable);
    
////////////////////////////////////////////////////////////////
// DNS Server
////////////////////////////////////////////////////////////////

  /**
   * If the platform usesAdapterLevelSettings property is true
   * <ul>
   * <li>return the maximum allowable number of the specified adapter's dnsHosts if the adapterId is valid</li>
   * <li>otherwise return the maximum allowable number of the first adapter's dnsHosts</li>
   * </ul>
   * Else
   * <ul>
   * <li>return the maximum allowable number of the platform's dnsHosts</li>
   * </ul>
   *
   * @param adapterId contains the target adapterId
   */
  int getDnsServerLimit(String adapterId);
    
  /**
   * If the platform usesAdapterLevelSettings property is true
   * <ul>
   * <li>return an array of the specified adapter's dnsHosts if the adapterId is valid</li>
   * <li>otherwise return an array of the first adapter's dnsHosts</li>
   * </ul>
   * Else
   * <ul>
   * <li>return the platform's dnsHosts<li>
   * </ul>
   *
   * @param adapterId contains the target adapterId
   */
  String[] getDnsServers(String adapterId);
    
  /** 
   * If the platform usesAdapterLevelSettings property is true
   * <ul>
   * <li>set the specified adapter's list of DNS servers to the one provided if the adapterId is valid</li>
   * <li>otherwise set the first adapter's list of DNS servers to the one provided</li>
   * </ul>
   * Else
   * <ul>
   * <li>set the platform's list of DNS servers to the one provided</li>
   * </ul>
   *
   * @param adapterId contains the target adapterId
   * @param servers is the new list of DNS servers
   * @throws IllegalArgumentException if servers array is larger than the maximum size allowed
   * @throws IllegalStateException if DHCP is enabled or the settings are readonly
   */
  void setDnsServers(String adapterId, String[] servers);
    
////////////////////////////////////////////////////////////////
// Domain Name
////////////////////////////////////////////////////////////////

  /**
   * If the platform usesAdapterLevelSettings property is true
   * <ul>
   * <li>return the specified adapter's domain if the adapterId is valid</li>
   * <li>otherwise return the first adapter's domain</li>
   * </ul>
   * Else
   * <ul>
   * <li>return the platform's domain</li>
   * </ul>
   *
   * @param adapterId contains the target adapterId
   */
  String getDomain(String adapterId);
    
  /**
   * If the platform usesAdapterLevelSettings property is true
   * <ul>
   * <li>set the specified adapter's domain if the adapterId is valid</li>
   * <li>otherwise set the first adapter's domain</li>
   * </ul>
   * Else
   * <ul>
   * <li>set the platform's domain</li>
   * </ul>
   *
   * @param adapterId contains the target adapterId
   * @param domain is the new domain
   * @throws IllegalStateException if DHCP is enabled or the settings are readonly
   */
  void setDomain(String adapterId, String domain);

////////////////////////////////////////////////////////////////
// Host Name
////////////////////////////////////////////////////////////////
  
  /**
   * Return the host name
   */
  String getHostName();
    
  /**
   * Set the host name
   * <br>
   * <b>Note:</b> On Win32 systems, host name is an important part of a computer's
   * domain/workgroup membership.  Callers should consider all consequences,
   * especially with respect to authentication, before changing a Win32 system's 
   * host name.
   *
   * @param newName is the new host name
   * @throws IllegalStateException if the settings are readonly
   */
  void setHostName(String newName);

////////////////////////////////////////////////////////////////
// IP Address
////////////////////////////////////////////////////////////////
  
  /**
   * Return the ipAddress of the adapter with the specified id
   * <br>
   * If the adapterId is invalid, return the first adapter's ipAddress
   *
   * @param adapterId contains the target adapterId
   */
  String getIpAddress(String adapterId);
    
  /**
   * Set the ipAddress of the adapter with the specified id
   * <br>
   * If the adapterId is invalid, set the first adapter's ipAddress
   *
   * @param adapterId contains the target adapterId
   * @param ipAddress is the new address
   * @throws IllegalStateException if DHCP is enabled or the settings are readonly
   *
   */
  void setIpAddress(String adapterId, String ipAddress);
    
////////////////////////////////////////////////////////////////
// Subnet Mask
////////////////////////////////////////////////////////////////
  
  /**
   * Return the subnetMask of the adapter with the specified id
   * <br>
   * If the adapterId is invalid, return the first adapter's subnetMask
   *
   * @param adapterId contains the target adapterId
   */
  String getSubnetMask(String adapterId);
    
  /**
   * Set the subnetMask of the adapter with the specified id
   * <br>
   * If the adapterId is invalid, set the first adapter's subnetMask
   *
   * @param adapterId contains the target adapterId
   * @param newMask is the new subnetMask
   * @throws IllegalStateException if DHCP is enabled or the settings are readonly
   */
  void setSubnetMask(String adapterId, String newMask);
    
////////////////////////////////////////////////////////////////
// MAC Address
////////////////////////////////////////////////////////////////
  
  /**
   * Return the MAC address of the adapter with the specified id
   * <br>
   * If the adapterId is invalid, return the MAC of the first adapter
   *
   * @param adapterId contains the target adapterId
   *
   */
  String getMediaAccessControlAddress(String adapterId);

////////////////////////////////////////////////////////////////
//Save the platform properties
////////////////////////////////////////////////////////////////  
  
  /**
   * Save the platform network properties
   * @throws IllegalStateException if the settings are readonly
   */
  void saveProperties();
}
