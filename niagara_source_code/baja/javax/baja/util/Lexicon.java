/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.baja.file.BIFile;
import javax.baja.file.BIFileStore;
import javax.baja.file.FilePath;
import javax.baja.naming.BModuleScheme;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nre.platform.RuntimeProfile;
import javax.baja.nre.util.TextUtil;
import javax.baja.registry.LexiconInfo;
import javax.baja.registry.RegistryException;
import javax.baja.sys.BModule;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.ModuleNotFoundException;
import javax.baja.sys.Sys;

import org.owasp.encoder.Encode;
import com.tridium.sys.Nre;
import com.tridium.sys.license.Brand;
import com.tridium.util.DiagnosticLexicon;

/**
 * Lexicon is a map of name/value pairs for a specific locale.
 *
 * @author    Brian Frank on 23 Jan 02
 * @version   $Revision: 18$ $Date: 7/7/2011 2:03:50 PM$
 * @since     Baja 1.0
 */
public class Lexicon
{
  /**
   * Niagara's default path for lexicons
   */
  public static final FilePath LEXICON_HOME_PATH = new FilePath("!lexicon");

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Get the lexicon for the specified module and locale information.
   */
  public static Lexicon make(BModule module, String lang, String country, String variant)
  {
    if (DiagnosticLexicon.DIAGNOSTICS)
    {
      return new DiagnosticLexicon(module, lang, country, variant);
    }
    else
    {
      return new Lexicon(module, lang, country, variant);
    }
  }

  /**
   * Get the lexicon for the specified module and language.
   */
  public static Lexicon make(BModule module, String lang)
  {
    Locale locale = Locale.getDefault();
    if (locale.getLanguage().equalsIgnoreCase(lang))
    {
      return make(module, locale.getLanguage(), locale.getCountry(), locale.getVariant());
    }

    locale = LOCALES_BY_LANG.computeIfAbsent(String.valueOf(lang), l -> Locale.forLanguageTag(Context.getLanguageTag(lang)));
    if (!locale.getLanguage().isEmpty())
    {
      return make(module, locale.getLanguage(), locale.getCountry(), locale.getVariant());
    }

    return make(module, lang, "", "");
  }

  /**
   * Get the lexicon for the specified module and locale information.
   */
  public static Lexicon make(String module, String lang, String country, String variant)
  {
    return make(module(module), lang, country, variant);
  }

  /**
   * Get the lexicon for the specified module and language.
   */
  public static Lexicon make(String module, String lang)
  {
    return make(module(module), lang);
  }

  /**
   * Get the lexicon for the specified module and locale.
   */
  public static Lexicon make(BModule module, Locale locale)
  {
    return make(module, locale.getLanguage(), locale.getCountry(), locale.getVariant());
  }

  /**
   * Get the lexicon for the specified module and language
   * configured in the give Context.
   */
  public static Lexicon make(BModule module, Context cx)
  {
    return make(module, lang(cx));
  }

  /**
   * Get the lexicon for the specified module and locale.
   */
  public static Lexicon make(String module, Locale locale)
  {
    return make(module(module), locale.getLanguage(), locale.getCountry(), locale.getVariant());
  }

  /**
   * Get the lexicon for the specified module and language
   * configured in the give Context.
   */
  public static Lexicon make(String module, Context cx)
  {
    return make(module(module), lang(cx));
  }

  /**
   * Get the lexicon for the specified module use Locale.getDefault().
   */
  public static Lexicon make(String module)
  {
    return make(module(module), Locale.getDefault());
  }

  /**
   * Get the lexicon for Sys.getModuleForClass(cls) and Locale.getDefault().
   */
  public static Lexicon make(Class<?> cls)
  {
    return make(Sys.getModuleForClass(cls), Locale.getDefault());
  }

  static BModule module(String module)
  {
    try
    {
      return Sys.loadModule(module);
    }
    catch(ModuleNotFoundException e)
    {
      log.warning("Lexicon module not found: " + module);
    }
    catch(Exception e)
    {
      log.log(Level.WARNING, "Lexicon module failure: " + module, e);
    }
    return null;
  }

  static String lang(Context cx)
  {
    if (cx != null) return cx.getLanguage();
    return Sys.getLanguage();
  }

  @SuppressWarnings("UnusedDeclaration")
  public static String removeUnderscore(String s) {
    StringBuilder r = new StringBuilder(s.length());
    r.setLength(s.length());
    int current = 0;
    for (int i = 0; i < s.length(); i ++) {
       char cur = s.charAt(i);
       if (cur != '_') r.setCharAt(current++, cur);
    }
    return r.toString();
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct and load the lexicon for the specified
   * module and lexicon locale identifiers.
   */
  protected Lexicon(BModule module, String language, String country, String variant)
  {
    if (language == null || language.isEmpty())
      language = Sys.getLanguage();

    if (language == null)
      throw new RuntimeException("Invalid Nre boot sequence, Sys.lang null");
    String localeKey = language;

    if (country == null)
      country = "";
    if (!country.isEmpty())
      localeKey = localeKey + "_" + country;

    if (variant == null)
      variant = "";
    if (!variant.isEmpty())
      localeKey = localeKey + "_" + variant;

    this.module = module;
    this.language = language;
    this.country = country;
    this.variant = variant;

    init(module, language, localeKey);
  }

  /**
   * Initializes the module and language Properties.
   *
   * @since Niagara 4.8
   */
  protected void init(BModule module, String language, String localeKey)
  {

    if (module == null)
    {
      moduleProps = empty;
      langProps = empty;
      return;
    }
    else
    {
      moduleProps = load(module, null);
      langProps = load(module, localeKey);
      // NCCB-17570: Fall back to two-letter language if full locale lexicon could not be found
      if (langProps.isEmpty())
      {
        langProps = load(module, language);
      }
    }

//    if (System.getProperty("niagara.lexicon.extended","false").equalsIgnoreCase("true"))
//    {
    String extendedPropsKey = getCommonKey(module, localeKey);
    if (!extendedPropsModules.contains(module.getModuleName()))
    {
      loadFromModule(module, null);
      extendedPropsModules.add(module.getModuleName());
    }
    if (!extendedPropsModules.contains(extendedPropsKey))
    {
      loadFromModule(module, localeKey);
      extendedPropsModules.add(extendedPropsKey);
    }
//    }
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get an unsafe reference to the module properties.
   */
  public Properties getModuleProperties()
  {
    return moduleProps;
  }

  /**
   * Get an unsafe reference to the lang properties.
   */
  public Properties getLanguageProperties()
  {
    return langProps;
  }

  /**
   * Lookup the value for the specified key.  If the
   * key is not contained by the Lexicon then return
   * the def value.
   */
  public String get(String key, String def)
  {
    String value = get(key);
    if (value == null) return def;
    return value;
  }

  /**
   * Lookup the value for the specified key, or return
   * null if the key is not contained by the Lexicon.
   */
  public String get(String key)
  {
    String value = null;
    if (langProps != null)
      value = langProps.getProperty(key);
    if (value == null && moduleProps != null)
      value = moduleProps.getProperty(key);

    if (value == null)
      return null;

    value = process(value, this);

    return value;
  }

  /**
   * Get a text value from the lexicon.  If not
   * found then return the key as fallback value.
   */
  public String getText(String key)
  {
    return get(key, key);
  }

  /**
   * Get a text value from the lexicon and format it using
   * a MessageFormat with the specified arguments.  If the
   * text is not found then return the key string.
   */
  public String getText(String key, Object... args)
  {
    String value = get(key, null);
    if (value == null) return key;
    return MessageFormat.format(value, args);
  }

  /**
   * This is {@code SlotPath.escape(getText(key))}.
   */
  public String getTextAsName(String key)
  {
    return SlotPath.escape(getText(key));
  }

  /**
   * Lookup the value for the specified key, or return
   * null if the key is not contained by the Lexicon.
   *
   * @param key - The lexicon key
   * @return - The safe lex value
   */
  public String getHtmlSafe(String key)
  {
    String val = get(key);
    return val != null ? Encode.forHtml(val) : val;
  }

  /**
   * Lookup the value for the specified key, or return
   * passed in default value if the key is not contained by the Lexicon.
   *
   * @param key - The lexicon key
   * @param def - Default value
   * @return - The safe lex value
   */
  public String getHtmlSafe(String key, String def)
  {
    String val = getHtmlSafe(key);
    return val != null ? val : Encode.forHtml(def);
  }

  /**
   * Return escaped value of the key which is safe to display
   * @param key - The lexicon key
   * @return The safe String value
   */
  public String getHtmlSafeText(String key)
  {
    return Encode.forHtml(getText(key));
  }

  /**
   * Get a safe text value from the lexicon and format it using
   * a MessageFormat with the specified arguments.  If the
   * text is not found then return the key string.
   * @param key - The lexicon key
   * @param args - Array of formatting objects
   * @return The safe String value
   */
  public String getHtmlSafeText(String key, Object... args)
  {
    String value = get(key);
    if (value == null) return Encode.forHtml(key);
    return Encode.forHtml(MessageFormat.format(value, args));
  }

  /**
   * Get string representation.
   */
  @Override
  public String toString()
  {
    String ret = "Lexicon:" + module + ":" + language;
    if (!country.isEmpty()) ret += "_" + country;
    return ret;
  }

////////////////////////////////////////////////////////////////
// LexiconProcessor
////////////////////////////////////////////////////////////////

  private static abstract class LexiconProcessor
  {
    public abstract String format(String value, Lexicon lexicon);
  }

  private static abstract class LexiconTag
    extends LexiconProcessor
  {
    public abstract String getTagName();
    public abstract String getValue(String param, Lexicon lexicon);

    protected final String tagStart = "{"+getTagName()+":";

    @Override
    public final String format(String value, Lexicon lexicon)
    {
      while(value.contains(tagStart))
      {
        int startIndex = value.indexOf(tagStart);
        int endIndex = value.indexOf("}", startIndex);
        StringBuilder buffer = new StringBuilder((int)(value.length()*1.25));
        buffer.append(value.substring(0, startIndex));
        buffer.append(getValue(value.substring(startIndex+tagStart.length(), endIndex), lexicon));
        buffer.append(value.substring(endIndex+1));
        value = buffer.toString();
      }
      return value;
    }
  }

  private static class NestedLexiconTag
    extends LexiconTag
  {
    private static final String TAG = "lexicon";

    @Override
    public String getTagName()
    {
      return TAG;
    }

    @Override
    public String getValue(String param, Lexicon lexicon)
    {
      String[] lexArray = TextUtil.split(param, ':');
      if(lexArray.length==1)
        return process(lexicon.get(lexArray[0].trim()), lexicon);
      else if (lexArray.length==2)
      {
        Lexicon extLexicon = Lexicon.make(lexArray[0].trim(), lexicon.language, lexicon.country, lexicon.variant);
        // If the referenced module does not exist, return the text of the key
        if (extLexicon.module == null)
          return lexArray[1].trim();
        return process(extLexicon.get(lexArray[1].trim()), extLexicon);
      }
      else
        throw new BajaRuntimeException("Invalid parameters: "+param);
    }
  }

  private static class BrandLexiconTag
    extends LexiconTag
  {
    protected static String TAG = "brand";
    @Override
    public String getTagName() { return TAG; }

    @Override
    public String getValue(String param, Lexicon lexicon)
    {
      // Need to first search the Brand-specific Lexicons for the entry
      // 222567 - GetBrandId throws a FeatureNotLicensedException if running in WbApplet
      String brandId;
      try
      {
        brandId = Brand.getBrandId();
      }
      catch (Exception e)
      {
        return null;
      }


//      BLicensePlatformService licensePlatformService = (BLicensePlatformService)BOrd.make("service:platform:LicensePlatformService").get();
//      licensePlatformService.lease(1);
//      brandId = licensePlatformService.getBrand();

      // retrieve lexicon for the brand-specific entries
      String[] lexArray = TextUtil.split(param, ':');
      if(lexArray.length==3)
      {
        // If the <brand> value matches the brandId, replace or insert
        // the key in the module.
        if (brandId.equalsIgnoreCase(lexArray[0].trim()))
        {
          // Need to load referenced lexicon if not already loaded.
          // Do not get into a circular lexicon loading situation.
          if (!lexArray[1].trim().equals(lexicon.module.getModuleName()))
          {
            Lexicon brandLexicon = Lexicon.make(lexArray[1].trim());
            return brandLexicon.getText(lexArray[2].trim());
          }
          else
            return lexicon.getText(lexArray[2].trim());
        }
        else
          return lexArray[2].trim();
      }
      else if(lexArray.length==2)
      {
        if (brandId.equalsIgnoreCase(lexArray[0].trim()))
          return lexicon.getText(lexArray[1].trim());
        else
          return lexArray[1].trim();
      }
      else
        throw new BajaRuntimeException("Invalid parameters: "+param);
    }
  }

  private static String process(String value, Lexicon lexicon)
  {
    if (value == null) return null;
    for (LexiconProcessor PROCESSOR : PROCESSORS)
      value = PROCESSOR.format(value, lexicon);
    return value;
  }

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Load the properties file for the specified
   * combination of module and lang.  We only ever
   * load a specified properties file once.
   */
  private static Properties load(BModule module, String localeKey)
  {
    String key = getCommonKey(module, localeKey);

    Properties props = common.get(key);
    if (props == null)
      return AccessController.doPrivileged(new LoadPrivilegedAction(key, module, localeKey));

    return props;
  }

  private static class LoadPrivilegedAction
    implements PrivilegedAction<Properties>
  {
    private LoadPrivilegedAction(String key, BModule module, String localeKey)
    {
      this.key = key;
      this.module = module;
      this.localeKey = localeKey;
    }
    @Override
    public Properties run()
    {
      // first time, gotta load it
      Properties props = new Properties();

      // Load any niagara default lexicon elements here, searching the registry
      //   definitions for a matching language default
      if (localeKey != null)
      {
        try(InputStream in = getInputStreamFromModule(module, localeKey, true))
        {
          props.load(in);
        }
        catch(Throwable e)
        {
          if (!"en".equals(localeKey))
          {
            if (log.isLoggable(Level.FINE))
            {
              log.fine("Cannot load niagara default \"" + key + "\": " + e.getLocalizedMessage());
            }
          }
        }
      }

      // Load either the module.lexicon (if localeKey == null) or from
      //   !lexicon (if localeKey != null)
      try(InputStream in = getInputStream(module, localeKey))
      {
        props.load(in);
      }
      catch(Throwable e)
      {
        if (!"en".equals(localeKey))
        {
          if (log.isLoggable(Level.FINE))
          {
            log.log(Level.FINE, "Cannot load lexicon \"" + key + "\"" + ": " + e.getLocalizedMessage());
          }
        }
      }

      // Load any branding elements here
      //TODO: Need to exclude brand module from populating registry?
//        in = getBrandInputStream(module);
//        props.load(in);
//        in.close();

      props = trim(props);
      common.put(key, props);

      return props;
    }

    private final String key;
    private final BModule module;
    private final String localeKey;
  }

  /**
   * Extend the properties file for the specified
   * combination of module and locale from lexicon files
   * contained inside a module.
   */
  private static void loadFromModule(BModule module, String localeKey)
  {
    String key = getCommonKey(module, localeKey);
    Properties props = common.get(key);
    if (props == null) // Should have been initialize by load()
      return;

    // Add to the existing properties.
    try
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("Load extended \"" + key + "\" for module " + module.getModuleName());
      }
      InputStream[] in = getInputStreamsFromModule(module, localeKey);
      for (InputStream anIn : in)
      {
        props.load(anIn);
        anIn.close();
      }
    }
    catch(Throwable ignored)
    { }
    common.put(key, trim(props));
  }

  /**
   * Construct the key used in the top-level lexicon cache
   */
  private static String getCommonKey(BModule module, String localeKey)
  {
    StringBuilder s = new StringBuilder();
    s.append(module.getModuleName());
    if (localeKey != null)
      s.append('-').append(localeKey);
    return s.toString();
  }

  /**
   * Trim all the values since Properties doesn't do it automatically
   */
  private static Properties trim(Properties props)
  {
    Properties trim = new Properties();
    props.entrySet().forEach(entry -> trim.setProperty((String)entry.getKey(), ((String) entry.getValue()).trim()));
    return trim;
  }

  /**
   * Get an input stream from the default container module
   */
  public static InputStream getInputStreamFromModule(BModule module, String localeKey, boolean isDefault)
    throws Exception
  {
    // Iterate through lexRegistry, looking for the default container module.
    //   Only one default is expected, so return the first one found.

    for (LexiconInfo aLexRegistry : Sys.getRegistry().getLexicons(module.getModuleName(), localeKey))
    {
      if (isDefault && aLexRegistry.isDefault())
      {
        InputStream in = getInputStreamFromModule(aLexRegistry);
        if (in != null)
        {
          return in;
        }
      }
    }
    throw new BajaRuntimeException("No default lexicons found for " + module.getModuleName() + " for locale key " + localeKey);
  }

  /**
   * Get an input stream from alternate container modules
   */
  public static InputStream[] getInputStreamsFromModule(BModule module, String localeKey)
    throws Exception
  {
    String m = module.getModuleName();

    // Iterate through lexRegistry, return all files identified.
    List<InputStream> l = new ArrayList<>();

    for (LexiconInfo aLexRegistry : Sys.getRegistry().getLexicons(m, localeKey))
    {
      // Skip this lexicon registry entry if it is a default lexicon
      if (!aLexRegistry.isDefault())
      {
        InputStream in = getInputStreamFromModule(aLexRegistry);
        if (in != null)
        {
          if (log.isLoggable(Level.FINE))
          {
            log.fine("  Adding module lexicon " + aLexRegistry.getContainerModuleName() + " to input stream list");
          }
          l.add(in);
        }
      }
    }
    if (l.isEmpty())
      throw new BajaRuntimeException("No lexicons found for " + m);

    return l.toArray(new InputStream[l.size()]);
  }

  /**
   * Get an input stream for the specified LexiconInfo.
   */
  public static InputStream getInputStreamFromModule(LexiconInfo li)
    throws Exception
  {
    String lexModule = li.getModuleName();
    String lexModulePath = li.getResourcePath();
    String lexContainer = li.getContainerModuleName();

    // Verify the brand for this lexicon
    String lexBrandPattern = li.getBrandPattern();
    String brandId = null;
    // 222567 - GetBrandId throws a FeatureNotLicensedException if running in WbApplet
    try
    {
      brandId = Brand.getBrandId();
    }
    catch (Exception ignored)
    { }

    //  BLicensePlatformService licensePlatformService = (BLicensePlatformService)BOrd.make("service:platform:LicensePlatformService").get();
    //  licensePlatformService.lease(1);
    //  brandId = licensePlatformService.getBrand();

    // If lexicon brand pattern is empty, any brand is allowed.
    if (!lexBrandPattern.isEmpty() && brandId != null)
    {
      PatternFilter brandMatch = new PatternFilter(lexBrandPattern);
      if (!brandMatch.accept(brandId))
      {
        log.warning("Lexicon loading: No brand match in module" + lexModule);
        return null;
      }
    }

    if (lexModule == null || lexModule.isEmpty() ||
        lexModulePath == null || lexModulePath.isEmpty() ||
        lexContainer == null || lexContainer.isEmpty())
      throw new RegistryException("Unknown registry lexicon for " + lexModule);

    BIFile lexFile = getExtendedFile(li);
    if (lexFile != null)
    {
      li.setLastModified(lexFile.getLastModified());
      return lexFile.getInputStream();
    }

    StringBuilder ordPath = new StringBuilder("module://");
    ordPath.append(lexContainer).append("/").append(lexModulePath);

    BOrd ord = BOrd.make(ordPath.toString());
    // Reset the lastModified attribute
    BIFile file = (BIFile)ord.resolve().get();
    li.setLastModified(file.getLastModified());
    return file.getInputStream();
  }

  /**
   * Get an input stream for the specified combo.
   */
  public static InputStream getInputStream(BModule module, String localeKey)
    throws Exception
  {
    // TODO: looking for lexicon files with well known name here is inelegant.   Consider having
    // registry build LexiconInfo for /{partname}.lexicon members and always use
    // getInputStreamsFromModule() instead of this method


    if (localeKey != null)
    {
      return Nre.bootEnv.read("/lexicon/" + localeKey + "/" + module.getModuleName() + ".lexicon");
    }

    ConcatenatedInputStream result = new ConcatenatedInputStream();
    boolean anyStores = false;

    for (RuntimeProfile profile : module.getRuntimeProfiles())
    {
      BIFileStore store = getFileStore(module, profile);
      if (store != null)
      {
        anyStores = true;
        result.add(store::getInputStream);

        // append a final newline so last line of this one and first line of next aren't
        // treated as a single line
        result.add(() -> new ByteArrayInputStream("\n".getBytes("UTF-8")));
      }
    }
    if (!anyStores)
    {
      result.add(() -> new ByteArrayInputStream("\n".getBytes("UTF-8")));
    }
    return result;
  }

  /**
   * Get a BIFileStore for the desired module and RuntimeProfile.
   *
   * @since Niagara 4.8
   */
  protected static BIFileStore getFileStore(BModule module, RuntimeProfile profile)
  {
    String moduleName = module.getModuleName();
    if (BModuleScheme.isModuleDevEnabled())
    {
      try
      {
        String body = "module://" + moduleName + "/" + module.getModulePartName(profile) + ".lexicon";
        BIFile file = (BIFile) BOrd.make(body).get();
        return file.getStore();
      }
      catch (Exception e)
      {
        log.log(Level.FINER, "Lexicon module scheme lookup failure for " + module, e);
      }
    }

    return module.findStore(new FilePath("module", String.format("/%s.lexicon", module.getModulePartName(profile))));
  }

  /**
   * Get a BIFile for the desired LexiconInfo
   *
   * @since Niagara 4.8
   */

  protected static BIFile getExtendedFile(LexiconInfo li)
  {
    String lexModule = li.getModuleName();
    String lexModulePath = li.getResourcePath();
    String lexContainer = li.getContainerModuleName();
    BModule containerModule = module(lexContainer);

    if (BModuleScheme.isModuleDevEnabled())
    {
      try
      {
        String moduleName = containerModule.getModuleName();
        String body = "module://" + moduleName + "/" + lexModulePath;
        return (BIFile) BOrd.make(body).get();
      }
      catch (Exception e)
      {
        log.log(Level.FINER, "Lexicon module scheme lookup failure for " + li, e);
      }
    }
    return containerModule.findFile(new FilePath("module", lexModulePath));
  }

  /*
   * Get the Tridium branding elements input stream for the specified module.
  private static InputStream getBrandInputStream(BModule module)
    throws Exception
  {
    String m = module.getModuleName();

    BOrd ord = BOrd.make("local:|module://brand/" + m +"_brand.lexicon");
    BIFile file = (BIFile)ord.resolve().get();
    return file.getInputStream();
  }
   */

////////////////////////////////////////////////////////////////
//Lexicon maintenance
////////////////////////////////////////////////////////////////
  /**
   * Clear the existing lexicon cache.
   */
  public static void invalidateCache()
  {
    log.fine("Entire lexicon cache is being cleared");
    common.clear();
    extendedPropsModules.clear();
  }

  /**
   * Search the current lexicon cache to find entries whose value matches
   * a given string. Return the target module, the property key, and the
   * lexicon container. Possible lexicon containers are the default module
   * ({@code module.lexicon}), the Niagara default lexicon module
   * ({@code "niagaraLexicon<Locale>"}), the
   * {@code !lexicon/<locale>/<module>.lexicon} file, or a separate lexicon
   * module.
   */
  public static List<String[]> searchLexiconCache(String search)
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine("  Searching for Lexicon entries containing " + search);
    }

    List<String[]> result = new ArrayList<>();

    for (String key : common.keySet())
    {
      Properties props = common.get(key);
      for (String propKey : props.stringPropertyNames())
      {
        String value = props.getProperty(propKey);
        // Need a separate class to contain Lexicon info for the set of elements?
        if (value.contains(search))
        {
          result.add(new String[] { key, propKey, value });
        }
      }
    }
    return result;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static Logger log = Logger.getLogger("sys.lexicon");
  private static final Map<String,Properties> common = new ConcurrentHashMap<>();
  private static Properties empty = new Properties();
  /** Need to track the extended lexicons as they are loaded */
  private static final Set<String> extendedPropsModules = new HashSet<>();

  /** Module of lexicon, this could be null if the module was not found */
  public final BModule module;

  /** Language of lexicon */
  public final String language;
  public final String country;
  public final String variant;

  private Properties moduleProps;
  private Properties langProps;

  // TODO: If the {brand:...} syntax proves not useful, simply remove the
  //   entry in the PROCESSORS list and it will not be used.
  private static final LexiconProcessor[] PROCESSORS = { new NestedLexiconTag(), new BrandLexiconTag() };

  private static final Map<String, Locale> LOCALES_BY_LANG = new ConcurrentHashMap<>();

  /**
   * Looks like a {@code Supplier<InputStream>} except that the get method can
   * throw IOException
   */
  public interface InputStreamExceptionSupplier
  {
    InputStream get()
      throws IOException;
  }

  public static class ConcatenatedInputStream
    extends InputStream
  {
    public void add(InputStreamExceptionSupplier supplier)
    {
      suppliers.add(supplier);
    }

    @Override
    public int read()
      throws IOException
    {
      while (true)
      {
        while (current != null)
        {
          int result = current.read();
          if (result == -1)
          {
            current.close();
            if (supplierIterator.hasNext())
            {
              current = supplierIterator.next().get();
            }
            else
            {
              current = null;
              return -1;
            }
          }
          else
          {
            return result;
          }
        }

        if (supplierIterator == null)
        {
          supplierIterator = suppliers.iterator();
        }
        if (supplierIterator.hasNext())
        {
          current = supplierIterator.next().get();
        }
        else
        {
          return -1;
        }
      }
    }

    @Override
    public void close()
      throws IOException
    {
      if (current != null)
      {
        current.close();
      }
    }

    private List<InputStreamExceptionSupplier> suppliers = new ArrayList<>();
    private Iterator<InputStreamExceptionSupplier> supplierIterator = null;
    private InputStream current = null;
  }
}
