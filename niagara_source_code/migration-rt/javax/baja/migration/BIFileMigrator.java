/**
 * Copyright (c) 2014 Tridium, Inc.  All Rights Reserved.
 */
package javax.baja.migration;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPassword;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.install.installable.DistributionManifest;

/**
 * BIFileMigrator manages the migration of a specific file type (identified
 * by the file extension) from Niagara AX format to Niagara 4 format.
 *
 * @author     <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 * creation    June 11 2014
 *
 */
@NiagaraType
public interface BIFileMigrator
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.migration.BIFileMigrator(2979906276)1.0$ @*/
/* Generated Tue Jan 18 15:16:47 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIFileMigrator.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the directory names that this Migrator handles.
   * This is used first, for exact directory name matches.
   * @return an array of String with the directory names handled by this {@code BIFileMigrator}.
   */
  default String[] getMigrateDirs()
  {
    return new String[0];
  }

  /**
   * Get the file names that this Migrator handles.
   * This is used first, for exact filename matches.
   * @return an array of String with the file names handled by this {@code BIFileMigrator}.
   */
  default String[] getMigrateFiles()
  {
    return new String[0];
  }

  /**
   * Get the file name patterns that this Migrator handles.
   * This is used second, to match filename patterns.
   * @return an array of String with the file name patterns handled by this {@code BIFileMigrator}.
   */
  default String[] getMigratePatterns()
  {
    return new String[0];
  }

  /**
   * Get the file types that this Migrator handles.
   * This is used third, to match by extension.
   * @return an array of String with the file extension types handled by this {@code BIFileMigrator}.
   */
  default String[] getMigrateTypes()
  {
    return new String[0];
  }

  /**
   * Initialize this {@code BIFileMigrator}.
   * <p>
   * Most implementations will need to cache the
   * source and target, as these will be needed in most
   * cases to perform the migration.
   * @param source the source file to be migrated.
   * @param target the target file location for the migrated file.
   * @param passPhraseSupplier supplies a pass phrase that can be used for decoding the source and
   *                         encoding the target
   * @param distManifest the {@link com.tridium.install.installable.DistributionManifest} containing version information for the source station.
   */
  void initialize(File source, File target, Supplier<BPassword> passPhraseSupplier, DistributionManifest distManifest);

  /**
   * Migrate the file associated with this migrator.
   * <p>
   * @return a failure message if something went wrong, or an empty message if the migration succeeded.
   * @throws Exception
   */
  Optional<String> migrate() throws Exception;

  /**
   * Set the log level on this migrator.
   * @param level desired logging level
   */
  void setLogLevel(Level level);

  /**
   * Check made during station migration for converters that desire to receive call to
   * updateOrds once other files are processed.
   * @return  true to indicate files may contain ords. Default returns false.
   */
  default boolean mayContainOrds() { return false; }

  /**
   * Update any ords which need to be changed during station migration.  This call is made
   * during migration after all files are converted if mayContainOrds() returned true.
   * @param dat is OrdConverter containing data needed to resolve ords.
   * @param setZipped flag to indicate whether the file should be zipped.
   * @return  a failure message if something went wrong, or an empty message if the migration succeeded.
   */
  default Optional<String> updateOrds(IOrdConverter dat, boolean setZipped) { return Optional.empty(); }

  /**
   * Upon the completion of the migration tool, important messages like removed modules
   * are displayed.  This provides the ability for file migrators to provide important high-level
   * messages at the end.
   * @param messages the message list.
   */
  default void addCompletionMessage(List<String> messages) { }
}
