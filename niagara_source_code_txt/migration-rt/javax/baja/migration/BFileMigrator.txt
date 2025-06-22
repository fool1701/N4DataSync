/**
 * Copyright (c) 2014 Tridium, Inc.  All Rights Reserved.
 */
package javax.baja.migration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.FileUtil;
import javax.baja.security.BPassword;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.install.installable.DistributionManifest;
import com.tridium.util.CommandLineArguments;

/**
 * BFileMigrator
 *
 * @author <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *         Date: 5/22/14
 *         Time: 3:44 PM
 */
@NiagaraType
public class BFileMigrator
  extends BObject
  implements BIFileMigrator
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.migration.BFileMigrator(2979906276)1.0$ @*/
/* Generated Tue Jan 18 15:16:47 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFileMigrator.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BFileMigrator()
  {
  }

  public void initialize(File source, File target, Supplier<BPassword> passwordSupplier, DistributionManifest distManifest)
  {
    Objects.requireNonNull(source);
    this.source = source;
    this.target = (target == null) ?
      new File(source.getParentFile(), "migrated_" + source.getName()) :
      target;
    this.distManifest = distManifest;

    log.config(getClass().getSimpleName() + " initialized with source=" + source + "; target=" + this.target);
  }

  /**
   * Migrate the file associated with this migrator.
   * @throws Exception
   */
  public Optional<String> migrate()
    throws Exception
  {
    try
    {
      File tgtdir = target.isDirectory() ? target : target.getParentFile();
      if ((tgtdir != null) && !tgtdir.exists() && !tgtdir.mkdirs())
      {
        throw new IOException("Could not create parent directories for target");
      }
      FileUtil.copy(source, target);
      return Optional.empty();
    }
    catch (Exception e)
    {
      return Optional.of("Error migrating "+source+" to "+target+":"+e.toString());
    }
  }

  /**
   * Set the log level on this migrator.
   * @param level desired logging level
   */
  public void setLogLevel(Level level)
  {
    log.setLevel(level);
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public void setArgs(CommandLineArguments args)
  {
    this.args = args;
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public final Logger log = Logger.getLogger("migration");


////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  /** Input file containing a station bog to be converted. */
  protected File source;

  /** Optional target file.  Used by automated test tool. */
  protected File target;

  /** DistributionManifest containing source version information. */
  protected DistributionManifest distManifest;

  /** Command line arguments from the Migrate tool. */
  protected CommandLineArguments args;

}
