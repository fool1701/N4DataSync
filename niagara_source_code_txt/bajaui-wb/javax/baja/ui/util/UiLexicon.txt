/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.util;

import java.util.Locale;
import javax.baja.sys.BModule;
import javax.baja.sys.Sys;
import javax.baja.ui.BMenu;
import javax.baja.util.Lexicon;

import com.tridium.ui.util.DiagnosticUiLexicon;
import com.tridium.util.DiagnosticLexicon;

/**
 * UiLexicon provides convenience methods for building
 * localized user interfaces in the Baja architecture.
 * UiLexicon wraps a standard Lexicon with methods used
 * to map lexicon name/value pairs into text, icons, and
 * accelerators.
 *
 * @author    Brian Frank on 23 Jan 02
 * @since     Baja 1.0
 */
public class UiLexicon
  extends Lexicon
{ 

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a UiLexicon for the specified module 
   * using Sys.getLanguage().
   * @deprecated use {@link UiLexicon#makeUiLexicon(BModule)} instead.
   */
  @Deprecated
  public UiLexicon(BModule module)
  {
    super(module, Locale.getDefault().getLanguage(), Locale.getDefault().getCountry(), Locale.getDefault().getVariant());
  }

  /**
   * Construct a UiLexicon for the module used to
   * load the specified class and Sys.getLanguage().
   * @deprecated use {@link UiLexicon#makeUiLexicon(Class)} instead.
   */
  @Deprecated
  public UiLexicon(Class<?> classInModule)
  {
    super(Sys.getModuleForClass(classInModule), Locale.getDefault().getLanguage(), Locale.getDefault().getCountry(), Locale.getDefault().getVariant());
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Get the UiLexicon for the currently configured default locale.
   */
  public static UiLexicon bajaui()
  {                     
    if (!bajaui.language.equals(Locale.getDefault().getLanguage()))
      bajaui = UiLexicon.makeUiLexicon(UiLexicon.class);
    return bajaui;
  }
  
  /**
   * Build a menu instance using the lexicon text
   * specified by the given key.
   */
  public BMenu buildMenu(String key)
  {
    return new BMenu(getText(key));
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * This is the lexicon for the bajaui module.  Note this
   * field is deprecated because the locale of Workbench
   * can be changed after boot in kiosk mode - you should use 
   * bajaui() instead.
   * @deprecated
   */
  @Deprecated
  public static UiLexicon bajaui = UiLexicon.makeUiLexicon(UiLexicon.class);


  /**
   * Get the UiLexicon for the specified module.
   *
   * @since Niagara 4.8
   */
  @SuppressWarnings("deprecation")
  public static UiLexicon makeUiLexicon(Class<?> cls)
  {
    if (DiagnosticLexicon.DIAGNOSTICS)
    {
      return new DiagnosticUiLexicon(cls);
    }
    else
    {
      return new UiLexicon(cls);
    }
  }

  /**
   * Get the UiLexicon for the specified module.
   *
   * @since Niagara 4.8
   */
  @SuppressWarnings("deprecation")
  public static UiLexicon makeUiLexicon(BModule module)
  {
    if (DiagnosticLexicon.DIAGNOSTICS)
    {
      return new DiagnosticUiLexicon(module);
    }
    else
    {
      return new UiLexicon(module);
    }
  }
}

