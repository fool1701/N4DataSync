/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.text.*;
import javax.baja.sys.*;

import org.owasp.encoder.Encode;

/**
 * LexiconText is a place holder for a module name and 
 * lexicon property name used to provide context sensitive 
 * text.
 *
 * @author    Brian Frank
 * @creation  23 Jan 02
 * @version   $Revision: 5$ $Date: 11/22/06 5:05:02 PM EST$
 * @since     Baja 1.0
 */
public class LexiconText
{ 

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>make(typeInModule.getModule(), key)</code>
   */
  public static LexiconText make(Type typeInModule, String key)
  {
    return make(typeInModule.getModule(), key);
  }

  /**
   * Get a place holder for the specified module and key.
   */
  public static LexiconText make(BModule module, String key)
  {
    return new LexiconText(module, key);
  }

  /**
   * Get a place holder for the specified module and key.
   */
  public static LexiconText make(String module, String key)
  {
    return make(Lexicon.module(module), key);
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct for the speicifed module and key.
   */
  protected LexiconText(BModule module, String key)
  {
    this.module = module;
    this.key = key;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Lookup the value for the specified key.  If the
   * key is not contained by the Lexicon then return 
   * the def value.
   */
  public String get(Context cx, String def)
  {       
    // optimize for null context                   
    if (cx == null)
    { 
      if (!defaultLang.equals(Sys.getLanguage()))
      {                 
        defaultLang = Sys.getLanguage();
        defaultContext = Lexicon.make(module, cx).get(key);         
      } 
      if (defaultContext == null) return def;
      return defaultContext;
    }                       
    
    // otherwise must lookup Lexicon
    else
    {             
      String value = Lexicon.make(module, cx).get(key);
      if (value == null) return def;
      return value;
    }
  }

  /**
   * Get a text value from the lexicon.  If not 
   * found then return the key as fallback value.
   */
  public String getText(Context cx)
  {
    return get(cx, key);
  }

  /**
   * Get a text value from the lexicon and format it using 
   * a MessageFormat with the specified arguments.  If the 
   * text is not found then return the key string.
   */
  public String getText(Context cx, Object[] args)
  {
    String value = get(cx, null);
    if (value == null) return key;
    return MessageFormat.format(value, args);
  }

  /**
   * Get an escape text value.
   *
   * @return The safe String value
   * @since Niagara 4.8
   */
  public String getHtmlSafeText(Context cx)
  {
    return Encode.forHtml(getText(cx));
  }

  /**
   * Get an escape text value from the lexicon and format it using
   * a MessageFormat with the specified arguments.  If the
   * text is not found then return the escaped key string.
   *
   * @return The safe String value
   * @since Niagara 4.8
   */
  public String getHtmlSafeText(Context cx, Object... args)
  {
    return Encode.forHtml(getText(cx, args));
  }

////////////////////////////////////////////////////////////////
// Localizable
////////////////////////////////////////////////////////////////

  /**
   * Return this LexiconText as a Localizable
   *
   * @since Niagara 4.1
   */
  public Localizable toLocalizable(Object... args)
  {
    return context -> getText(context, args);
  }

  /**
   * Return a localizable message with the given module name, lexicon key and lexicon args
   *
   * @since Niagara 4.1
   */
  public static Localizable toLocalizable(Type typeInModule, String key, Object... args)
  {
    return make(typeInModule.getModule(), key).toLocalizable(args);
  }

  /**
   * Return a localizable message with the given module name, lexicon key and lexicon args
   *
   * @since Niagara 4.1
   */
  public static Localizable toLocalizable(BModule module, String key, Object... args)
  {
    return new LexiconText(module, key).toLocalizable(args);
  }

  /**
   * Return a localizable message with the given module name, lexicon key and lexicon args
   *
   * @since Niagara 4.1
   */
  public static Localizable toLocalizable(String module, String key, Object... args)
  {
    return make(Lexicon.module(module), key).toLocalizable(args);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  /** Module */
  public final BModule module;

  /** Key of text */
  public final String key;
  
  private String defaultLang = "???";
  private String defaultContext;
} 

