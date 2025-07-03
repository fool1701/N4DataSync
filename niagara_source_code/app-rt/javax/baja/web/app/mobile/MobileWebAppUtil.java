/*
 * Copyright 2012, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web.app.mobile;

import javax.baja.app.BApp;
import javax.baja.security.BPermissions;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Utility methods for Mobile Web Apps 
 *
 *
 * @author		gjohnson
 * @creation 	Apr 3, 2012
 * @version 	1
 * @since 		Niagara 3.7
 */
public final class MobileWebAppUtil
{
  /**
   * Return true if the App is currently operational and can be accessed.
   */
  public static boolean isAppOperational(Type type, Context cx)
  {
    // Look up the App and check to see if it's operational
    BApp app = (BApp)Sys.getService(type);
    boolean operational = app.isOperational();

    // Check user has read permissions for this App based on Context (if available)
    if (operational && cx != null && cx.getUser() != null)
      operational = cx.getUser().getPermissionsFor(app).has(BPermissions.operatorRead);
    
    return operational;
  }
}
