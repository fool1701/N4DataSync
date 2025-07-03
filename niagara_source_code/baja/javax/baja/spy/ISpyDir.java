/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.spy;

/**
 * ISpyDir is implemented by Spy pages which contain
 * child Spy pages.
 *
 * @author    Brian Frank
 * @creation  5 Mar 03
 * @version   $Revision: 2$ $Date: 3/28/05 9:23:04 AM EST$
 * @since     Baja 1.0
 */
public interface ISpyDir
{

  /**
   * List the child spy names.
   */
  public String[] list();

  /**
   * Get the specified child by name or return null.
   */
  public Spy find(String name);

  /**
   * Some spy children names contain an appended CSRF token.
   * In the case of a CSRF validation failure, the error spy page
   * attempts to provide a 'retry' name containing a proper CSRF
   * token for the current session.  This method is given the
   * invalid name that failed the CSRF validation and should return
   * a new name containing a proper CSRF token for the current session.
   *
   * The default implementation replaces the CSRF token at the end
   * of the given invalid name with a new token.
   *
   * @param invalidName A child name that failed during CSRF token validation.
   * @return A new child name containing a properly formed CSRF token for the
   * current session.
   *
   * @since Niagara 4.3
   */
  default String regenerateNameWithCsrfToken(String invalidName)
  {
    String name = invalidName;
    int idx = invalidName.indexOf(SpyWriter.CSRF_TOKEN_PREFIX);
    if (idx >= 0)
    {
      name = invalidName.substring(0, idx);
    }

    return SpyWriter.addCsrfToken(name);
  }
  
}
