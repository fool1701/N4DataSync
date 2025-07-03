/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.util.Objects;

/**
 * Localizable is the interface implemented by classes which 
 * can describe themselves as a localized, user friendly string.
 * It is commonly used with Exceptions which may be visible to
 * the user.
 *
 * @author    Brian Frank
 * @creation  23 Jul 02
 * @version   $Revision: 1$ $Date: 7/23/02 3:14:58 PM EDT$
 * @since     Baja 1.0 
 */
@FunctionalInterface
public interface Localizable
{
  /**
  * Get the instance as a localized String for the specified
  * Context.  If context is null then use the Sys.getLanguage().
  */
  String toString(Context context);

  /**
   * Convenience for converting a locale-independent String literal
   * into a Localizable
   *
   * @since Niagara 4.1
   */
  static Localizable fromLiteral(String nonLocalizedLiteral)
  {
    return context -> nonLocalizedLiteral;
  }

  /**
   * Returns a Localizable whose result is a concatenation of the results
   * of other Localizables with a separator between them
   *
   * @since Niagara 4.1
   */
  static Localizable concatenate(String separator, Iterable<Localizable> localizables)
  {
    Objects.requireNonNull(separator);
    return context ->
      {
        StringBuilder result = new StringBuilder();
        for (Localizable localizable : localizables)
        {
          if (result.length() > 0)
          {
            result.append(separator);
          }
          result.append(localizable.toString(context));
        }
        return result.toString();
      };
  }
}
