/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
 
package javax.baja.platform;
 
/**
 * Manages the authentication and access control for a PlatformDaemon
 */
public interface DaemonSecurityManager
{
  /**
   * Returns true if the platform daemon authentication settings are readonly for the connected platform.
   *
   * @since Niagara 4.9
   */
  boolean isAuthenticationReadonly()
    throws Exception;
  
  /**
   * @deprecated use {@link #useSingleAdminAccount(String, char[])}
   */
  @Deprecated
  default void useSingleAdminAccount(String adminUser, String adminPassword)
    throws Exception
  {
    useSingleAdminAccount(adminUser, adminPassword.toCharArray());
  }

  /**
   * Returns true if single-user authentication and authorization function
   * {@link #useSingleAdminAccount(String, char[])} is supported for the connected
   * platform.
   *
   * @since Niagara 4.1
   */
  boolean isSingleUserAuthorizationSupported()
    throws Exception;

  /**
   * Update the platform daemon's access control settings so that it uses
   * digest authentication (RFC 2617) to challenge clients, and uses
   * a single user name and password to check their credentials
   * against.
   *
   * Not supported for Niagara 4 hosts.
   *
   * @since Niagara 4.1
   *
   * @param adminUser user name for the platform daemon's admin
   * @param adminPassword password for the platform daemon's admin
   *
   * @see #isSingleUserAuthorizationSupported()
   * @see <a href='https://www.ietf.org/rfc/rfc2617.txt'>RFC 2617</a>
   *
   * @throws UnsupportedOperationException if Niagara 4 host
   */
  void useSingleAdminAccount(String adminUser, char[] adminPassword)
    throws Exception;

  /**
   * Returns true if OS group-based authorization checks are supported on
   * the connected platform:
   * <ul>
   *   <li>{@link #useAdminGroup(String)} </li>
   *   <li>{@link #useOsGroups(String, String)} </li>
   * </ul>
   *
   * @since Niagara 4.1
   */
  boolean isOsGroupAuthorizationSupported()
    throws Exception;

  /**
   * Update the platform daemon's access control settings so that is uses
   * basic authentication (RFC 2617) to challenge clients, uses
   * the remote host's OS security API to validate clients' credentials,
   * and uses authenticated users' group memberships to determine which
   * level of access to permit to platform daemon functions.
   *
   * @since Niagara 4.1
   *
   * @param adminGroupName OS group which should have privileges to do all operations with the
   *                       platform daemon.
   *
   * @see #isOsGroupAuthorizationSupported()
   *
   * @throws UnsupportedOperationException if OS groups are not configurable for
   *           daemons on the host platform(currently it's supported only on Windows platforms),
   *           or if the host is running Niagara AX
   */
  void useAdminGroup(String adminGroupName)
    throws Exception;

  /**
   * Update the platform daemon's access control settings so that is uses
   * basic authentication (RFC 2617) to challenge clients, uses
   * the remote host's OS security API to validate clients' credentials,
   * and uses authenticated users' group memberships to determine which
   * level of access to permit to platform daemon functions.
   * 
   * @param userGroupName For supported Niagara AX platforms, OS group which should have
   *                      privileges to do most operations with the platform daemon, except
   *                      for rebooting, applying TCP/IP changes, and updating the system clock.
   *                      This parameter will be ignored for Niagara 4 platforms.
   * @param adminGroupName OS group which should have privileges to do all operations with the
   *                       platform daemon.
   *
   * @see #isOsGroupAuthorizationSupported()
   *
   * @throws UnsupportedOperationException if OS groups are not configurable for
   *           daemons on the host platform(currently it's supported only on Windows platforms)
   */
  void useOsGroups(String userGroupName, String adminGroupName)
    throws Exception;

  /**
   * For supported Niagara 4 platforms (Windows platforms are not supported), add a new OS user account
   * with administrative privileges and the given username and password.
   *
   * If the PlatformDaemon is currently using the factory default username and password to connect,
   * it will start using the given userName and password after this invocation is complete, and
   * the factory default user will be removed.
   *
   * @since Niagara 4.1
   *
   * @param userName The username is required, and may not be any of the following reserved names:
   * <ul>
   *   <li>root</li>
   *   <li>sshd</li>
   *   <li>daemon</li>
   *   <li>niagarad</li>
   *   <li>station</li>
   *   <li>auth</li>
   *   <li>niagarad_admin</li>
   *   <li>niagarad_owners</li>
   *   <li>station_owners</li>
   *   <li>sshd</li>
   * </ul>
   * @param password password for the new user. Must include only ASCII characters, be at most 64
   *                 characters in length, and comply with default
   *                 {@link javax.baja.user.BPasswordStrength#DEFAULT} requirements
   *
   * @see #supportsPlatformAccountManagement()
   *
   * @throws UnsupportedOperationException if the remote platform is running Niagara AX or running on Windows
   *
   */
  void addPlatformUser(String userName, char[] password)
    throws Exception;

  /**
   * Return true if account management functions are supported on the connected platform:
   * <ul>
   *   <li>{@link #addPlatformUser(String, char[])}</li>
   *   <li>{@link #updatePlatformUserPassword(String, char[], char[])}</li>
   *   <li>{@link #removePlatformUser(String)} (String, char[], char[])}</li>
   *   <li>{@link #listPlatformUsers()} (String, char[], char[])}</li>
   * </ul>
   *
   * @since Niagara 4.1
   *
   * @throws Exception
   */
  boolean supportsPlatformAccountManagement()
    throws Exception;


  /**
   * For supported Niagara 4 platforms (Windows platforms are not supported), change the password
   * for the given userName from the given old password to the given new password.
   *
   * If the PlatformDaemon is connecting with the given user name, its session will be updated to
   * connect with the new password once the change is successful.
   *
   * @since Niagara 4.1
   *
   * @see #supportsPlatformAccountManagement()
   *
   * @throws Exception if userName doesn't exist, if the old password isn't valid, if the new password
   *                   doesn't meet strength requirements
   *
   * @throws UnsupportedOperationException if the remote platform is running Niagara AX or on Windows host
   */
  void updatePlatformUserPassword(String userName,
                                  char[] oldPassword,
                                  char[] newPassword)
    throws Exception;


  /**
   * For supported Niagara 4 platforms (Windows platforms are not supported) remove the OS user account
   * with the given name.
   *
   * @since Niagara 4.1
   *
   * @see #supportsPlatformAccountManagement()
   *
   * @throws Exception if userName doesn't exist or deleting it isn't allowed
   *
   * @throws UnsupportedOperationException if the remote platform is running Niagara AX or on Windows host
   */
  void removePlatformUser(String userName)
    throws Exception;
  
  /**
   * Returns true if the platform system passphrase is readonly for the connected platform.
   *
   * @since Niagara 4.9
   */
  boolean isSystemPassphraseReadonly()
    throws Exception;

  /**
   * Update the system passphrase used by the remote Niagara 4 host
   *
   * @since Niagara 4.1
   *
   * @throws Exception if the current passphrase is incorrect, if the new passphrase doesn't
   *                   meet strength requirements, if the passphrase values are the same, or
   *                   if the remote host does not run Niagara 4
   *
   * @throws UnsupportedOperationException if remote host is running Niagara AX
   */
  void updateSystemPassphrase(char[] currentPassphrase, char[] newPassphrase)
    throws Exception;

  /**
   * For supported Niagara 4 platforms (Windows platforms are not supported) return a list of OS user
   * accounts visible to the authenticated user.
   *
   * @since Niagara 4.1
   *
   * @see #supportsPlatformAccountManagement()
   *
   * @throws UnsupportedOperationException if the remote host is running Niagara AX
   */
  String[] listPlatformUsers();

}

