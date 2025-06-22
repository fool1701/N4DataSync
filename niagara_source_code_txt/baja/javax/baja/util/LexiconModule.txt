/*
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.baja.sys.BModule;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import org.owasp.encoder.Encode;

/**
 * LexiconModule is similar to a Lexicon and LexiconText, but it is most useful for when only the module name is known at the
 * time of construction. The key and the Context are required for all lexicon 'get', 'getText' or 'getHtmlSafe' look ups.
 * The last used language is cached in each LexiconModule, so it is optimized to be reused in scenarios where there could be multiple
 * calls with the same language. LexiconText will create a new lexicon on each 'getText' call, so LexiconModule is more efficient
 * in that scenario.
 *
 * @author JJ Frankovich on 9/5/2018
 * @since Niagara 4.8
 */
public class LexiconModule
{

  /**
   * Convenience for {@code make(typeInModule.getModule())}
   */
  public static LexiconModule make(Type typeInModule)
  {
    return make(typeInModule.getModule());
  }

  /**
   * Get a place holder for the specified module.
   */
  public static LexiconModule make(BModule module)
  {
    return new LexiconModule(module);
  }

  /**
   * Get a place holder for the specified module.
   */
  public static LexiconModule make(String module)
  {
    return make(Lexicon.module(module));
  }

  /**
   * Get the LexiconModule for Sys.getModuleForClass(cls)
   */
  public static LexiconModule make(Class<?> cls)
  {
    return make(Sys.getModuleForClass(cls));
  }


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct for the specified module.
   */
  protected LexiconModule(BModule module)
  {
    this.module = module;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Lookup the value for the specified key and context.  If the
   * key is not contained by the Lexicon then return
   * the def value.
   */
  public String get(String key, Context cx)
  {
    return get(key, cx, null);
  }

  /**
   * Lookup the value for the specified key and context.  If the
   * key is not contained by the Lexicon then return
   * the def value.
   */
  public String get(String key, Context cx, String def)
  {
    return getOrChangeCurrentLexicon(cx).get(key, def);
  }

  private Lexicon getOrChangeCurrentLexicon(Context cx)
  {
    Lexicon lexicon = currentLexicon;

    if (!sameLanguage(lexicon, cx))
    {
      lexicon = Lexicon.make(module, cx);
      currentLexicon = lexicon;
    }

    return lexicon;
  }

  private static boolean sameLanguage(Lexicon lexicon, Context cx)
  {

    if (lexicon == null)
    {
      return false;
    }

    String contextLang = Context.getLanguageTag(cx);
    String lexiconLang = getLanguageTag(lexicon);
    if (lexiconLang.equals(contextLang))
    {
      return true;
    }

    if (log.isLoggable(Level.FINE))
    {
      log.fine("LexiconModule language change for " + lexicon.module + ": " + lexiconLang + " != " + contextLang);
    }
    return false;
  }

  private static String getLanguageTag(Lexicon lex)
  {

    StringBuilder localeKey = new StringBuilder();
    localeKey.append(lex.language);
    if (!lex.country.isEmpty())
    {
      localeKey.append('-').append(lex.country);
    }

    if (!lex.variant.isEmpty())
    {
      localeKey.append('-').append(lex.variant);
    }
    return localeKey.toString();
  }

  /**
   * Get a text value from the lexicon and context.  If not
   * found then return the key as fallback value.
   */
  public String getText(String key, Context cx)
  {
    return get(key, cx, key);
  }

  /**
   * Get a text value from the lexicon and format it using
   * a MessageFormat with the specified arguments.  If the
   * text is not found then return the key string.
   */
  public String getText(String key, Context cx, Object... args)
  {
    String value = get(key, cx, null);
    if (value == null) { return key; }
    return MessageFormat.format(value, args);
  }

  /**
   * Lookup the value for the specified key, or return
   * null if the key is not contained by the Lexicon.
   *
   * @param key - The lexicon key
   * @param cx  - The Context
   * @return - The safe lex value
   */
  public String getHtmlSafe(String key, Context cx)
  {
    String val = get(key, cx);
    return val != null ? Encode.forHtml(val) : val;
  }

  /**
   * Lookup the value for the specified key, or return
   * passed in default value if the key is not contained by the Lexicon.
   *
   * @param key - The lexicon key
   * @param cx  - The Context
   * @param def - Default value
   * @return - The safe lex value
   */
  public String getHtmlSafe(String key, Context cx, String def)
  {
    String val = getHtmlSafe(key, cx);
    return val != null ? val : Encode.forHtml(def);
  }

  /**
   * Return escaped value of the key which is safe to display
   *
   * @param key - The lexicon key
   * @param cx  - The Context
   * @return The safe String value
   */
  public String getHtmlSafeText(String key, Context cx)
  {
    return Encode.forHtml(getText(key, cx));
  }

  /**
   * Get a safe text value from the lexicon and format it using
   * a MessageFormat with the specified arguments.  If the
   * text is not found then return the key string.
   *
   * @param key  - The lexicon key
   * @param cx   - The Context
   * @param args - Array of formatting objects
   * @return The safe String value
   */
  public String getHtmlSafeText(String key, Context cx, Object... args)
  {
    String value = get(key, cx);
    if (value == null) { return Encode.forHtml(key); }
    return Encode.forHtml(MessageFormat.format(value, args));
  }

  /**
   * Get the Module of LexiconModule, this could be null if the module was not found
   */
  public BModule getModule()
  {
    return module;
  }

  private final BModule module;

  private Lexicon currentLexicon;
  private static final Logger log = Logger.getLogger("sys.lexicon");
}

