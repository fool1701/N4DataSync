/*
 * Copyright 2008, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.user;

import javax.baja.user.*;
import javax.baja.security.*;

/**
 * PermissionsManager is an interface for providing an alternate
 * permissions map for a user accessing a protected object.  It
 * is used by applications that need to extend or override the
 * default permissions model.
 * <p>
 * An application can install its own PermissionsManager on the
 * UserService.
 * 
 * @author    John Sublett
 * @creation  25 Mar 2008
 * @version   $Revision: 1$ $Date: 4/3/08 1:27:20 PM EDT$
 * @since     Baja 1.0
 */
public interface PermissionsManager
{
  /**
   * Get the permissions map to use for determining permissions for
   * the specified user on the specified protected object.  If null
   * is returned, then the permissions map defined by BUser.permissions
   * is used.
   */
  public BPermissionsMap getPermissionsMap(BUser user, BIProtected target);
}