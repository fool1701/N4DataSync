/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.IOException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

import javax.baja.file.BDirectory;
import javax.baja.file.BFileSpace;
import javax.baja.file.BIDirectory;
import javax.baja.file.BIFile;
import javax.baja.file.BIFileStore;
import javax.baja.file.FilePath;
import javax.baja.file.zip.BZipSpace;
import javax.baja.naming.BHost;
import javax.baja.naming.BISession;
import javax.baja.naming.BModuleScheme;
import javax.baja.naming.BOrd;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.platform.RuntimeProfile;
import javax.baja.registry.ModuleInfo;
import javax.baja.space.BISpace;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;
import javax.baja.util.Version;

import com.tridium.sys.Nre;
import com.tridium.sys.module.BModulePaletteNode;
import com.tridium.sys.module.BModuleTypesContainer;
import com.tridium.sys.module.ModuleManager;
import com.tridium.sys.module.NModule;
import com.tridium.sys.schema.Fw;

/**
 * BModule encapsulates a Baja software module which
 * is packaged and delivered as a JAR file with a 
 * "meta-inf/module.xml" description.  Modules are the 
 * unit of software deployment in the Baja architecture.
 * Module names must be one to 25 ASCII characters in 
 * length and globally unique.
 *
 * @author    Brian Frank       
 * @creation  7 Nov 00
 * @version   $Revision: 33$ $Date: 7/6/11 2:10:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public final class BModule
  extends BFileSpace
  implements BIComparable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BModule(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BModule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Framework use only.
   */
  public BModule()
  {
    super(null);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  public static Optional<BModule> getModule(BISpace space)
  {
    if (space == null)
    {
      return Optional.empty();
    }
    else if (space instanceof BModule)
    {
      return Optional.of((BModule)space);
    }
    else if (space instanceof BZipSpace)
    {
      return ((BZipSpace)space).getModule();
    }
    return Optional.empty();
  }

  public static boolean isModuleFileSpace(BISpace space)
  {
    return (space != null) &&
      (space instanceof BModule ||
        ((space instanceof BZipSpace) && ((BZipSpace)space).isModule()));
  }

  public FilePath getRootFilePath()
  {
    return this.rootFilePath;
  }

  /**
   * Returns true if this module has a palette
   */
  public final boolean hasPalette()
  {
    initNavChildren();
    return paletteNode.isPresent();
  }

  /**
   * Name uniquely describes the module.
   */
  public final String getModuleName()
  {
    return this.moduleName;
  }

  /**
   * The preferred symbol is a very compact
   * abbreviation of the name usually between
   * one to four letters.  Note that the preferred
   * symbol is not guaranteed unique between modules
   * like the name, it is merely a suggestion.
   */
  public final String getPreferredSymbol()
  {
    return this.preferredSymbol;
  }

  /**
   * Get the Baja specification version that this
   * module implements.  If it does not implement
   * any Baja specification currently published
   * then return Version.ZERO.
   */
  public final Version getBajaVersion(RuntimeProfile profile)
  {
    return nModulesByProfile.get(profile).getBajaVersion();
  }

  /**
   * Get the vendor name for the module.
   */
  public final String getVendor(RuntimeProfile profile)
  {
    return nModulesByProfile.get(profile).getVendor();
  }

  /**
   * Get the vendor specific version of this module.
   */
  public final Version getVendorVersion(RuntimeProfile profile)
  {
    return nModulesByProfile.containsKey(profile) ? nModulesByProfile.get(profile).getVendorVersion() : null;
  }

  /**
   * Return the version for the module part that provides this class,
   * or null if the class is not provided by a Niagara module.
   *
   * @since Niagara 4.0
   */
  public static Version getClassVersion(Class<?> cls)
  {
    NModule module = AccessController.doPrivileged((PrivilegedAction<ModuleManager>)() -> Nre.getModuleManager()).getModuleForClass(cls);
    return (module == null) ? null : module.getVendorVersion();
  }

  /**
   * Return the vendor for the module part that provides this class,
   * or null if the class is not provided by a Niagara module.
   *
   * @since Niagara 4.0
   */
  public static String getClassVendor(Class<?> cls)
  {
    NModule module = AccessController.doPrivileged((PrivilegedAction<ModuleManager>)() -> Nre.getModuleManager()).getModuleForClass(cls);
    return (module == null) ? null : module.getVendor();
  }

  /**
   * Check that the bajaVersion is equal to or greater
   * than the specified version.  If not then throw a
   * ModuleIncompatibleException.
   *
   * @return this.
   */
  public BModule checkBajaVersion(Version bajaVersion, RuntimeProfile profile)
    throws ModuleIncompatibleException
  {
    nModulesByProfile.get(profile).checkBajaVersion(bajaVersion);
    return this;
  }

  /**
   * Check that the module's vendor name is equal to the specified
   * vendor name (case insensitive) and that the vendorVersion is
   * equal to or greater than the specified vendorVersion.  If not
   * then throw a ModuleIncompatibleException.
   *
   * @return this.
   */
  public BModule checkVendor(String vendor, Version vendorVersion, RuntimeProfile profile)
    throws ModuleIncompatibleException
  {
    nModulesByProfile.get(profile).checkVendor(vendor, vendorVersion);
    return this;
  }

  /**
   * Get a short description of the module.
   */
  public final String getDescription()
  {
    return this.description;
  }

  public RuntimeProfile getRuntimeProfile(FilePath path)
  {
    return runtimeProfileByFilePathBody.get(path.getBody());
  }

  /**
   * Get the ModuleInfo for this module which provides access
   * for registry interrogation.
   */
  public ModuleInfo getModuleInfo(RuntimeProfile profile)
  {
    return Sys.getRegistry().getModule(moduleName, profile);
  }

  /**
   * Get the ModuleInfo for this module which provides access
   * for registry interrogation.
   */
  public ModuleInfo[] getAllModuleInfo()
  {
    return Sys.getRegistry().getModules(moduleName);
  }

  /**
   * Lookup the Type for the specified type name.  This
   * mapping is loaded from &lt;type&gt; elements in
   * the "module.xml" manifest file.
   *
   * @return Type which implements the specified typeName.
   * @throws TypeNotFoundException if the type is not
   *                               found in the module's "module.xml" manifest.
   * @throws TypeException         if there was an error loading
   *                               the type from the module.
   */
  public final Type getType(String typeName)
    throws TypeException
  {
    if (typeKeyByName.containsKey(typeName))
    {
      return nModulesByProfile.get(typeKeyByName.get(typeName)).getType(typeName);
    }
    else
    {
      throw new TypeNotFoundException(moduleName + ":" + typeName);
    }
  }


  /**
   * Load the specified class from this module.
   */
  public final Class<?> loadClass(String name)
    throws ClassNotFoundException
  {
    for (NModule module : nModulesByProfile.values())
    {
      if (Nre.supportsProfile(module.getRuntimeProfile()))
      {
        try
        {
          return module.loadClass(name);
        }
        catch (ClassNotFoundException cnfe)
        {
          // try next module
        }
      }
    }
    throw new ClassNotFoundException(String.format("%s:%s", moduleName, name));
  }

  /**
   * Get a resource from the Module's class loader.
   */
  public final URL getResource(String path)
  {
    for (NModule module : nModulesByProfile.values())
    {
      if (Nre.supportsProfile(module.getRuntimeProfile()))
      {
        URL result = module.getResource(path);
        if (result != null)
        {
          return result;
        }
      }
    }
    return null;
  }

  /**
   * Is this module synthetic.
   */
  public boolean isSynthetic(RuntimeProfile profile)
  {
    return nModulesByProfile.get(profile).isSynthetic();
  }

  /**
   * Is this module synthetic.
   */
  public boolean isSynthetic()
  {
    return isSynthetic;
  }

  /**
   * Is this module transient.
   */
  public boolean isTransient(RuntimeProfile profile)
  {
    return nModulesByProfile.get(profile).isTransient();
  }

  public boolean isTransient()
  {
    return this.isTransient;
  }

  public final Lexicon getLexicon(String lang)
  {
    synchronized (lexiconsByLanguage)
    {
      Lexicon lexicon = lexiconsByLanguage.get(lang);
      if (lexicon == null)
        lexiconsByLanguage.put(lang, lexicon = Lexicon.make(this, lang));
      return lexicon;
    }
  }

  /**
   * @since Niagara 4.0
   */
  public String getModulePartName(RuntimeProfile profile)
  {
    return nModulesByProfile.get(profile).getModulePartName();
  }

  /**
   * @since Niagara 4.0
   */
  public List<RuntimeProfile> getRuntimeProfiles()
  {
    return nModulesByProfile.values().stream().map(NModule::getRuntimeProfile).collect(Collectors.toList());
  }

  /**
   * Framework use only.
   * @since Niagara 4.0
   */
  public void addModulePart(Object modulePartObj)
  {
    NModule modulePart = (NModule)modulePartObj;
    RuntimeProfile profile = modulePart.getRuntimeProfile();

    clearNavChildren();

    if (this.moduleName == null)
    {
      this.rootFilePath = new FilePath(BModuleScheme.INSTANCE.getId(), String.format("//%s", modulePart.getModuleName()));
      this.ordInSession = BOrd.make(rootFilePath);
      this.moduleName = modulePart.getModuleName();
      this.description = modulePart.getDescription();
      this.preferredSymbol = modulePart.getPreferredSymbol();
    }
    else if (!modulePart.getModuleName().equals(this.moduleName))
      throw new IllegalArgumentException();
    nModulesByProfile.put(profile, modulePart);

    if (!modulePart.isTransient())
    {
      this.isTransient = false;
      BZipSpace zipSpace = new BZipSpace(this, modulePart.getZipFile());

      this.zipSpaceByRuntimeProfile.put(profile, zipSpace);
      modulePart.moduleFile.addReopenHandler(zip -> zipSpace.fw(Fw.USER_DEFINED_0, zip, null, null, null));

      // TODO this should be lazy
      for (Enumeration<? extends ZipEntry> entries = modulePart.getZipFile().entries(); entries.hasMoreElements();)
      {
        ZipEntry entry = entries.nextElement();

        // can't count on directories?
        if (entry.isDirectory()) continue;

        // NOTE: ZipPath doesn't allow backslash, and java.util.zip impls interpret backslash as '/',
        // so we should replace backslash in entry name with forward slash here
        String name = entry.getName().replace('\\', '/');
        FilePath path = getRootFilePath().merge(name);

        if (path.depth() > 1)
        {
          BModuleDirectory root = rootDirsByRootDirName.get(path.nameAt(0));
          if (root == null)
          {
            rootDirsByRootDirName.put(path.nameAt(0), root = new BModuleDirectory(this, getRootFilePath().merge(path.nameAt(0)), this));
          }
          root.addFile(path, zipSpace);
        }
        else if ((path.depth() == 1) &&
                 !name.endsWith(".class") &&
                 !name.endsWith(".properties") &&
                 !name.startsWith("META-INF/") &&
                 !name.startsWith("meta-inf/"))
        {
          zipSpaceByRootDirName.put(path.getName(), zipSpace);
          runtimeProfileByFilePathBody.put(path.getBody(), profile);
        }
      }
    }

    if (!modulePart.isSynthetic())
    {
      // any zips have non-synthetic types, this module is not considered synthetic
      this.isSynthetic = false;
    }

    for (String typeName : modulePart.getTypeList())
    {
      typeKeyByName.put(typeName, modulePart.getRuntimeProfile());
    }
  }

////////////////////////////////////////////////////////////////
// Space
////////////////////////////////////////////////////////////////  

  @Override
  public BHost getHost()
  {
    return BModuleSpace.INSTANCE.getHost();
  }

  @Override
  public BISession getSession()
  {
    return BModuleSpace.INSTANCE.getSession();
  }

  /**
   * Get ord passed to constructor.
   */
  @Override
  public BOrd getOrdInSession()
  {
    return ordInSession;
  }

////////////////////////////////////////////////////////////////
// BFileSpace
////////////////////////////////////////////////////////////////

  @Override
  protected BOrd appendFilePathToOrd(BOrd baseOrd, FilePath filePath)
  {
    if (baseOrd == null) return null;
    if (baseOrd.toString().isEmpty()) return BOrd.make(filePath);
    return BOrd.make(baseOrd, filePath);
  }

  /**
   * Map a FilePath to an instanceof of BIFileStore.  If the
   * path doesn't map to a file in this space, then return null.
   */
  @Override
  public BIFileStore findStore(FilePath path)
  {
    if (path.depth() == 0)
    {
      return null;
    }
    else if (path.depth() == 1)
    {
      if (rootDirsByRootDirName.containsKey(path.getName()))
      {
        return rootDirsByRootDirName.get(path.getName());
      }
      else if (zipSpaceByRootDirName.containsKey(path.getName()))
      {
        return zipSpaceByRootDirName.get(path.getName()).findStore(path);
      }
      else
      {
        return null;
      }
    }

    BIDirectory parent = findDirectory(path.getParent());
    if (parent != null)
    {
      return ((BModuleDirectory)parent).getChildStore(path.getName());
    }
    return null;
  }

  /**
   * Get the child file of the specified parent or
   * return null if not found.
   */
  @Override
  public BIFile getChild(BIFile parent, String childName)
  {
    if (parent.getStore() instanceof BModuleDirectory)
    {
      return (BIFile)((BModuleDirectory)parent.getStore()).getNavChild(childName);
    }
    BZipSpace zipSpace = findZipSpace(parent.getFilePath());
    return zipSpace == null ? null : zipSpace.getChild(parent, childName);
  }

  /**
   * Get the children files of the specified parent
   * or return an empty array.
   */
  @Override
  public BIFile[] getChildren(BIFile parent)
  {
    if (parent.getStore() instanceof BModuleDirectory)
    {
      return ((BModuleDirectory)parent.getStore()).listFiles();
    }
    BZipSpace zipSpace = findZipSpace(parent.getFilePath());
    return zipSpace == null ? new BIFile[0] : zipSpace.getChildren(parent);
  }

  /**
   * This is a read-only file space, throws IOException
   */
  @Override
  public BDirectory makeDir(FilePath path, Context cx) throws IOException
  {
    throw new IOException("BModule file space is readonly");
  }

  /**
   * This is a read-only file space, throws IOException
   */
  @Override
  public BIFile makeFile(FilePath path, Context cx) throws IOException
  {
    throw new IOException("BModule file space is readonly");
  }

  /**
   * This is a read-only file space, throws IOException
   */
  @Override
  public void move(FilePath from, FilePath to, Context cx) throws IOException
  {
    throw new IOException("BModule file space is readonly");
  }

  /**
   * This is a read-only file space, throws IOException
   */
  @Override
  public void delete(FilePath path, Context cx) throws IOException
  {
    throw new IOException("BModule file space is readonly");
  }

  /**
   * Compares this object with the specified object for order.  Returns a
   * negative integer, zero, or a positive integer as this object is less
   * than, equal to, or greater than the specified object.
   *
   * @throws NullPointerException if the specified object is null
   * @throws ClassCastException if the specified object's type is not BModule
   * @since Niagara 4.13
   */
  @Override
  public int compareTo(Object other)
  {
    // Allows file comparison between modules.
    return this.getModuleName().compareTo(((BModule)other).getModuleName());
  }

  ////////////////////////////////////////////////////////////////
// BIDirectory
////////////////////////////////////////////////////////////////

  /**
   * Get the list of containing files.
   */
  @Override
  public BIFile[] listFiles()
  {
    initNavChildren();
    return files;
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////  

  /**
   * Get the nav name.
   */
  @Override
  public String getNavName()
  {
    return this.moduleName;
  }

  /**
   * Get the nav parent.
   */
  @Override
  public BINavNode getNavParent() 
  { 
    return BModuleSpace.INSTANCE; 
  }
  
  /**
   * Get a root by name.
   */
  @Override
  public BINavNode getNavChild(String navName)
  {
    initNavChildren();
    return super.getNavChild(navName);
  }

  @Override
  public BINavNode[] getNavChildren()
  {
    initNavChildren();
    return super.getNavChildren();
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * To string.
   */
  @Override
  public String toString(Context cx)
  {
    return "Module \"" + this.moduleName + "\" ";
  }

  @Override
  public final Lexicon getLexicon()
  {
    if (lexicon == null || !lexicon.language.equals(Sys.getLanguage()))
      lexicon = getLexicon(Sys.getLanguage());
    return lexicon;
  }

  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    if(x==Fw.GET_MODULES)
      return nModulesByProfile.values().toArray(new NModule[nModulesByProfile.size()]);
    else
      return super.fw(x, a, b, c, d);
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return isSynthetic()? SYNTH_ICON : ICON; }

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    out.startProps();
    out.trTitle("Module", 2);
    out.prop("Module Name", getModuleName());
    out.prop("Preferred Symbol", getPreferredSymbol());
    out.prop("Description", getDescription());
    out.prop("Synthetic", isSynthetic());
    out.prop("Transient", isTransient());
    out.endProps();

    out.startProps();
    out.trTitle("Module Parts", 4);

    out.w("<tr>").th("Part Name").th("Runtime Profile").th("Vendor").th("Version").th("Release Date").w("</tr>\n");

    nModulesByProfile.values()
      .stream()
      .forEach(m ->
        out.tr(m.getModulePartName(),
          m.getRuntimeProfile(),
          m.getVendor(),
          m.getVendorVersion(),
          m.getReleaseDate().map(millis -> BAbsTime.make(millis).toDateString(null)).orElse("Unreleased")));

    out.endProps();

    super.spy(out);
  }

////////////////////////////////////////////////////////////////
// Implementation
////////////////////////////////////////////////////////////////

  @Override
  protected void clearNavChildren()
  {
    paletteNode = null;
    files = null;
    super.clearNavChildren();
  }

  /**
   * @since Niagara 4.0
   */
  private synchronized void initNavChildren()
  {
    if (inInitNavChildren) return;
    if (files == null)
    {
      try
      {
        inInitNavChildren = true;
        Map<String, BIFile> accum = new TreeMap<>();
        List<BIFile> accumPalettes = new ArrayList<>();
        zipSpaceByRuntimeProfile.values().forEach(zipSpace ->
        {
          for (BIFile file : zipSpace.listFiles())
          {
            if (file.getFileName().equals("module.palette"))
            {
              if (paletteNode == null)
              {
                accumPalettes.add(file);
              }
            }
            else
            {
              accum.put(file.getFileName(), file);
              if (super.getNavChild(file.getNavName()) == null)
              {
                addNavChild(file);
              }
            }
          }
        });
        if (!accumPalettes.isEmpty())
        {
          paletteNode = Optional.of(BModulePaletteNode.make(this, accumPalettes.toArray(new BIFile[accumPalettes.size()]), BOrd.make(ordInSession + "/module.palette")));
          addNavChild(paletteNode.get());
        }
        else
        {
          paletteNode = Optional.empty();
        }
        addNavChild(BModuleTypesContainer.get(this));
        files = accum.values().stream().sorted(Comparator.comparing(BIFile::getFileName)).toArray(BIFile[]::new);
      }
      finally
      {
        inInitNavChildren = false;
      }
    }
  }

  /**
   * @since Niagara 4.0
   */
  private BZipSpace findZipSpace(FilePath path)
  {
    if (path.depth() == 1)
    {
      return zipSpaceByRootDirName.get(path.nameAt(0));
    }
    else
    {
      return rootDirsByRootDirName.get(path.nameAt(0)).findZipSpace(path);
    }
  }

  /**
   * @since Niagara 4.0
   */
  private BIDirectory findDirectory(FilePath path)
  {
    if (path.depth() == 0)
    {
      return this;
    }
    else if (path.depth() == 1)
    {
      return rootDirsByRootDirName.get(path.nameAt(0));
    }
    else
    {
      return rootDirsByRootDirName.get(path.nameAt(0)).findDirectory(path);
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private static final BIcon ICON = BIcon.std("module.png");
  private static final BIcon SYNTH_ICON = BIcon.make(BIcon.std("syntheticModule.png"), BIcon.std("badges/beaker.png"));

  private final Map<RuntimeProfile,NModule> nModulesByProfile = new TreeMap<>();
  private final Map<RuntimeProfile,BZipSpace> zipSpaceByRuntimeProfile = new HashMap<>();
  private final Map<String,BModuleDirectory> rootDirsByRootDirName = new TreeMap<>();
  private final Map<String,BZipSpace> zipSpaceByRootDirName = new TreeMap<>();
  private final Map<String,RuntimeProfile> runtimeProfileByFilePathBody = new TreeMap<>();
  private String moduleName = null;
  private BOrd ordInSession = null;
  private FilePath rootFilePath = null;
  private String preferredSymbol = null;
  private String description = null;
  private Lexicon lexicon;
  private boolean isSynthetic = true;
  private boolean isTransient = true;
  private final Map<String, Lexicon> lexiconsByLanguage = new HashMap<>();
  private final Map<String,RuntimeProfile> typeKeyByName = new HashMap<>();
  private Optional<BModulePaletteNode> paletteNode = null;
  private boolean inInitNavChildren = false;
  private BIFile[] files = null;
}
