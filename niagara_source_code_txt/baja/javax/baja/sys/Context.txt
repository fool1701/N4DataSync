/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.user.BUser;

/**
 * The Context interface is used in operations which
 * may perform differently depending on their context.
 * Examples include operations which may require auditing
 * or localization.
 *
 * @author    Brian Frank on 20 Feb 00
 * @version   $Revision: 14$ $Date: 4/18/05 4:59:00 PM EDT$
 * @since     Baja 1.0
 */
public interface Context
{

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * If this Context wraps another Context, then return
   * the base Context.  Otherwise return null.
   */
  Context getBase();

  /**
   * Get the user for the context of this operation,
   * or null if no user information is available.
   */
  BUser getUser();

  /**
   * Get the facets table.  This should never be null.
   * Return BFacets.DEFAULT if no facets are available.
   */
  BFacets getFacets();

  /**
   * Get a facet value by name, or null.
   */
  BObject getFacet(String name);

  /**
   * Get the language code for the context operation.
   * This method should never return null.  As a default
   * return {@link Sys#getLanguage()}.
   * <p>
   * Note that the returned language is not guaranteed to be in IETF format and
   * should not be naively passed to methods expecting such. See
   * {@link #getLanguageTag(Context)}.
   */
  String getLanguage();

  /**
   * Return the IETF language tag that is the best match for the context's
   * current locale. If the input Context or its language are null, this
   * defaults to using {@link Sys#getLanguage()}.
   *
   * @param cx context to retrieve a language tag from
   * @return the IETF language tag that is the best match for the context's
   * current locale. This can be passed to {@code Locale.forLanguageTag()} and
   * similar methods.
   * @since Niagara 4.6
   */
  static String getLanguageTag(Context cx)
  {
    return getLanguageTag(cx == null ? null : cx.getLanguage());
  }

  /**
   * Return the IETF language tag that is the best match for the given Niagara
   * language code. If the given language is null, defaults to using
   * {@link Sys#getLanguage()}.
   *
   * @param language a language code in a format that Niagara understands,
   *                 e.g. {@code fr} or {@code en_US}
   * @return the IETF language tag that is the best match for the given language
   * code.
   * @since Niagara 4.6
   */
  static String getLanguageTag(String language)
  {
    return (language == null ? Sys.getLanguage() : language).replace("_", "-");
  }

  /**
   * Compares one context with another to determine if the context equals the other
   * or wraps the other.
   *
   * @param context the context being compared
   * @param other the other context to compare
   * @return true when context equals other or wraps other as a base
   * @since Niagara 4.13
   */
  static boolean includes(Context context, Context other)
  {
    if (context == null || other == null)
    {
      return false;
    }
    boolean result = context.equals(other);
    if (!result)
    {
      Context base = context.getBase();
      if (base != null)
      {
        // RECURSIVE
        result = includes(base, other);
      }
    }
    return result;
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  /**
   * This instance of context is passed when decoding
   * a component from a persistent data source (usually
   * a bog file).
   */
  Context decoding = new BasicContext()
  {
    public boolean equals(Object obj) { return this == obj; }
    public int hashCode() { return 31; }
    public String toString() { return "Context.decoding"; }
  };

  /**
   * This instance of context is passed when copying
   * a component such as during newCopy() or copyFrom().
   */
  Context copying = new BasicContext()
  {
    public boolean equals(Object obj) { return this == obj; }
    public int hashCode() { return 37; }
    public String toString() { return "Context.copying"; }
  };

  /**
   * This instance of context is passed when components
   * should commit the change instead of routing it to
   * their space.
   */
  Context commit = new BasicContext()
  {
    public boolean equals(Object obj) { return this == obj; }
    public int hashCode() { return 39; }
    public String toString() { return "Context.commit"; }
  };

  /**
   * This instance of context is passed into a BComplex {@code set()}
   * method when the {@code getPropertyValidator()} callback on the BComplex
   * should be skipped entirely.  It indicates that property validation is not
   * necessary for a pending property change.  Note that it is only valid
   * in the current VM.  This means that if you use this context in a proxy
   * component space, it will not be used on the remote (master) component
   * space (validation may still occur on the remote VM).
   *
   * @since Niagara 4.0
   */
  Context skipValidate = new BasicContext()
  {
    public boolean equals(Object obj) { return this == obj; }
    public int hashCode() { return 43; }
    public String toString() { return "Context.skipValidate"; }
  };

  /**
   * This instance of context is passed into a BComplex {@code set()}
   * method when the {@code getPropertyValidator()} callback on the BComplex
   * should be called.  It indicates that property validation is
   * necessary for a pending property change.  Note that it is only valid
   * in the current VM.  This means that if you use this context in a proxy
   * component space, it will not be used on the remote (master) component
   * space.  However, this context simply forces validation to occur, but
   * there are other conditions that also cause validation to occur (see
   * method comments on {@code BComplex.getPropertyValidator()}
   *
   * @since Niagara 4.0
   */
  Context forceValidate = new BasicContext()
  {
    public boolean equals(Object obj) { return this == obj; }
    public int hashCode() { return 47; }
    public String toString() { return "Context.forceValidate"; }
  };

  /**
   * This instance of context is passed when decoding
   * a component from a persistent data source (usually
   * a bog file).
   *
   * @since Niagara 4.7
   */
  Context skipRemoveCheck = new BasicContext()
  {
    public boolean equals(Object obj) { return this == obj; }
    public int hashCode() { return 51; }
    public String toString() { return "Context.skipRemoveCheck"; }
  };

  /**
   * This instance of context can be used instead of {@code null} so that
   * the resulting code is more readable and strongly typed.
   *
   * @since 3.8
   */
  Context NULL = null;

}
