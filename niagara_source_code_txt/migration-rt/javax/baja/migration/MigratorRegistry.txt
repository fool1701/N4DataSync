/**
 * Copyright (c) 2014 Tridium, Inc.  All Rights Reserved.
 */
package javax.baja.migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import javax.baja.file.BajaFileUtil;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.Sys;
import javax.baja.util.Lexicon;

/**
 * MigratorRegistry handles the management of {@link javax.baja.migration.BIFileMigrator}s using
 * the Niagara Type Registry.  Instances of {@link javax.baja.migration.BIFileMigrator} are retrieved
 * from the Registry, and used to initialize a migrator map.  The {@link #lookup(java.io.File)} method
 * attempts to first match the filename exactly by the migrate types assigned for each migrator.  If
 * no match is found, the migratorsByFile' migrate types are used as a regex to match patterns, like
 * *.bog. If nothing matches, the default {@link javax.baja.migration.BFileMigrator} is returned.
 * The migrator types are loaded from a migrator.properties file with a fallback of the types claimed
 * by each migrator class. The migrator properties file allows 3rd party developers to migrate
 * niagara files which have names that are different than the standard niagara file names.
 *
 * @author <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *         Date: 6/11/14
 *         Time: 4:03 PM
 */
public class MigratorRegistry
{
  /**
   * Initialize our registry of migrators with the migrator.properties file.
   * The file is located at {@code <niagara user home>/etc}
   * If the file is not found, the registry is initialized with the default
   * file patterns defined in each migrator class.
   */
  public static void initialize()
  {
    File userHomeEtcDir = new File(Sys.getNiagaraUserHome(), "etc");
    File propsFile = new File(userHomeEtcDir, MIGRATOR_PROP_FILE);

    try (FileInputStream inStrm = new FileInputStream(propsFile))
    {
      initialize(inStrm);
    }
    catch (FileNotFoundException eNotFoundExpt)
    {
      Object[] args = {propsFile.getAbsolutePath()};
      LOG.info(LEX.getText("migrator.properties.find", args));
      initialize(null);
    }
    catch (IOException eIOExpt)
    {
      Object[] args = {eIOExpt.getLocalizedMessage()};
      LOG.warning(LEX.getText("migrator.properties.close", args));
    }
  }

  /**
   * Initialize our registry of migrators from an InputStream of properties.
   * @param inputStream an input stream from which to load the migrator properties or null to
   *                    load the default migrator patterns defined in each migrator class.
   */
  public static void initialize(InputStream inputStream)
  {
    TypeInfo[] types = Sys.getRegistry().getConcreteTypes(BIFileMigrator.TYPE.getTypeInfo());
    Properties migratorProps = null;

    migratorsByDirName.clear();
    migratorsByFile.clear();
    migratorsByPattern.clear();
    migratorsByExt.clear();

    if (inputStream != null)
    {
      try
      {
        migratorProps = new Properties();
        migratorProps.load(inputStream);
      }
      catch (IOException eIOExpt)
      {
        LOG.severe(LEX.getText("migrator.properties.read"));
      }
    }

    // Initialize by filename.
    for (TypeInfo typeInfo : types)
    {
      BIFileMigrator migrator = (BIFileMigrator)typeInfo.getInstance();

      // get filenames from migrator properties
      String[] migrateFiles = getPatterns(migratorProps, typeInfo.getTypeName() + "." + "files");
      if (migrateFiles == null)
      {
        // default filenames
        migrateFiles = migrator.getMigrateFiles();
        if ((migrateFiles == null) || (migrateFiles.length == 0))
        {
          continue;
        }
      }

      for (String targetFile : migrateFiles)
      {
        if (migratorsByFile.containsKey(targetFile))
        {
          LOG.severe("Attempt to register duplicate Migrator " + migrator.getType()
            + " for target filename " + targetFile
            + "; already registered by " + migratorsByFile.get(targetFile).getTypeName());
        }
        else
        {
          migratorsByFile.put(targetFile, typeInfo);
        }
      }
    }

    // Initialize by pattern.
    for (TypeInfo typeInfo : types)
    {
      BIFileMigrator migrator = (BIFileMigrator)typeInfo.getInstance();

      // get patterns from migrator properties
      String[] migratePatterns = getPatterns(migratorProps, typeInfo.getTypeName() + "." + "patterns");
      if (migratePatterns == null)
      {
        // default patterns
        migratePatterns = migrator.getMigratePatterns();
        if ((migratePatterns == null) || (migratePatterns.length == 0))
        {
          continue;
        }
      }

      for (String targetPattern : migratePatterns)
      {
        if (migratorsByPattern.containsKey(targetPattern))
        {
          LOG.severe("Attempt to register duplicate Migrator " + migrator.getType()
            + " for target pattern " + targetPattern
            + "; already registered by " + migratorsByPattern.get(targetPattern).getTypeName());
        }
        else
        {
          migratorsByPattern.put(targetPattern, typeInfo);
        }
      }
    }

    // Initialize by file extension.
    for (TypeInfo typeInfo : types)
    {
      BIFileMigrator migrator = (BIFileMigrator)typeInfo.getInstance();

      // get extensions from migrator properties
      String[] migrateExts = getPatterns(migratorProps, typeInfo.getTypeName() + "." + "extensions");
      if (migrateExts == null)
      {
        // default extensions
        migrateExts = migrator.getMigrateTypes();
        if ((migrateExts == null) || (migrateExts.length == 0))
        {
          continue;
        }
      }

      for (String targetType : migrateExts)
      {
        if (migratorsByExt.containsKey(targetType))
        {
          LOG.severe("Attempt to register duplicate Migrator " + migrator.getType()
            + " for target file type " + targetType
            + "; already registered by " + migratorsByExt.get(targetType).getTypeName());
        }
        else
        {
          migratorsByExt.put(targetType, typeInfo);
        }
      }
    }

    // Initialize by directory name.
    for (TypeInfo typeInfo : types)
    {
      BIFileMigrator migrator = (BIFileMigrator)typeInfo.getInstance();

      // get directories from migrator properties
      String[] migrateDirs = getPatterns(migratorProps, typeInfo.getTypeName() + "." + "directories");
      if (migrateDirs == null)
      {
        // default migrator patterns
        migrateDirs = migrator.getMigrateDirs();
        if ((migrateDirs == null) || (migrateDirs.length == 0))
        {
          continue;
        }
      }

      for (String targetDir : migrateDirs)
      {
        if (migratorsByDirName.containsKey(targetDir))
        {
          LOG.severe("Attempt to register duplicate Migrator " + migrator.getType()
            + " for target directory name " + targetDir
            + "; already registered by " + migratorsByDirName.get(targetDir).getTypeName());
        }
        else
        {
          migratorsByDirName.put(targetDir, typeInfo);
        }
      }
    }

    initialized = true;
  }

  /**
   * Lookup a {@link javax.baja.migration.BIFileMigrator} that can handle a given AX file type.
   * <p>
   * <b>NOTE:</b> Only a single migrator is allowed for a particular type.
   * Possible future improvement is to allow for dealing with
   * multiple migratorsByFile that claim to migrate a single type.
   * @param f a Niagara file to be migrated
   * @return the appropriate migrator for this file type.
   */
  public static BIFileMigrator lookup(File f)
  {
    if (!initialized) initialize();

    String filename = f.getName();

    BIFileMigrator migrator = null;

    // If this is a directory, check for a directory migrator.
    // Return the migrator, or null.
    if (f.isDirectory())
    {
      TypeInfo ti = migratorsByDirName.get(filename);
      return (ti != null ? (BIFileMigrator)ti.getInstance() : null);
    }

    // For Files:
    //
    // First try for an exact match: i.e., "config.bog"
    TypeInfo ti = migratorsByFile.get(filename);
    if (ti != null)
    {
      migrator = (BIFileMigrator)ti.getInstance();
    }

    // Next try for a pattern match: i.e., "config*.bog"
    if (migrator == null)
    {
      for (String key : migratorsByPattern.keySet())
      {
        if (filename.matches(key))
        {
          migrator = (BIFileMigrator)migratorsByPattern.get(key).getInstance();
        }
      }
    }

    // Then try for a file type match by extension: i.e., "*.bog"
    if (migrator == null)
    {
      String ext = BajaFileUtil.getExtension(filename);
      for (String key : migratorsByExt.keySet())
      {
        if (key.equals(ext))
        {
          migrator = (BIFileMigrator)migratorsByExt.get(key).getInstance();
        }
      }
    }

    // Fallback to BFileMigrator if nothing matches.
    if (migrator == null)
    {
      migrator = new BFileMigrator();
    }

    return migrator;
  }

  /**
   * Get the migrator patterns for the specified property name.
   * @param properties the migrator properties
   * @param propertyName the name of the migrator pattern property
   * @return the String[] containing the patterns or null if no patterns were found
   */
  static String[] getPatterns(Properties properties, String propertyName)
  {
    if ((properties == null))
    {
      return null;
    }

    String propertyValue = properties.getProperty(propertyName);
    if ((propertyValue == null) || propertyValue.isEmpty())
    {
      return null;
    }

    String[] patterns = propertyValue.split("\\s*,\\s*");
    // remove blank patterns
    List<String> tmpList = new LinkedList<String>(Arrays.asList(patterns));
    tmpList.removeIf(p -> p.isEmpty());
    patterns = tmpList.toArray(new String[tmpList.size()]);

    return (patterns.length == 0) ? null : patterns;
  }

  /**
   * Command line check for the list of available migrators, and to identify the migrator
   * for a particular file.
   * If invoked with no arguments, this will list all the migrators registered along with the
   * files they claim to migrate.
   * If invoked with one or more arguments, each argument is interpreted as a file name
   * to be used to lookup a migrator, and the migrator for that name is displayed.
   * @param args list of file names.
   */
  public static void main(String... args)
  {
    if (args.length == 0)
    {
      initialize();
      System.out.println("\nRegistered Migrators by Filename");
      for (String filename : migratorsByFile.keySet())
      {
        System.out.println(filename + "\t" + migratorsByFile.get(filename));
      }
      System.out.println("\nRegistered Migrators by Pattern");
      for (String filename : migratorsByPattern.keySet())
      {
        System.out.println(filename + "\t" + migratorsByPattern.get(filename));
      }
      System.out.println("\nRegistered Migrators by Extension");
      for (String filename : migratorsByExt.keySet())
      {
        System.out.println(filename + "\t" + migratorsByExt.get(filename));
      }
      System.out.println("\nRegistered Migrators by Directory");
      for (String filename : migratorsByDirName.keySet())
      {
        System.out.println(filename + "\t" + migratorsByDirName.get(filename));
      }

    }
    else
    {
      for (String s : args)
      {
        File f = new File(s);
        BIFileMigrator migrator = lookup(f);
        System.out.println("Filename: "+s+" -> Migrator: "+migrator);
      }
    }
  }

  private static final Logger LOG = Logger.getLogger("migration.registry");
  private static final Lexicon LEX = Lexicon.make("migration");
  private static final String MIGRATOR_PROP_FILE = "migrator.properties";

  private static boolean initialized = false;
  private static Map<String,TypeInfo> migratorsByDirName = new HashMap<>();
  private static Map<String,TypeInfo> migratorsByFile = new HashMap<>();
  private static Map<String,TypeInfo> migratorsByPattern = new HashMap<>();
  private static Map<String,TypeInfo> migratorsByExt = new HashMap<>();
}
