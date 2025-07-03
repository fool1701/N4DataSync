/*
 * Copyright 2005, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.backup;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.baja.dataRecovery.BIDataRecoveryService;
import javax.baja.file.BDirectory;
import javax.baja.file.BFileSpace;
import javax.baja.file.BFileSystem;
import javax.baja.file.BIDirectory;
import javax.baja.file.BIFile;
import javax.baja.file.BLocalFileStore;
import javax.baja.file.BajaFileUtil;
import javax.baja.file.FilePath;
import javax.baja.io.ValueDocDecoder;
import javax.baja.job.BJob;
import javax.baja.job.JobCancelException;
import javax.baja.job.JobLog;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.naming.OrdQuery;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.FileUtil;
import javax.baja.platform.PlatformDaemon;
import javax.baja.platform.RemoteStation;
import javax.baja.platform.install.BVersionRelation;
import javax.baja.security.AuditEvent;
import javax.baja.security.Auditor;
import javax.baja.security.BPermissions;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BAbstractService;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BString;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.timezone.BTimeZone;
import javax.baja.user.BUser;
import javax.baja.util.BIRestrictedComponent;
import javax.baja.util.PatternFilter;

import com.tridium.backup.BAxOfflineBackup;
import com.tridium.backup.BBackupChannel;
import com.tridium.file.types.bog.BBogFile;
import com.tridium.fox.sys.BFoxChannelRegistry;
import com.tridium.install.BDaemonPlatform;
import com.tridium.install.BDependency;
import com.tridium.install.BLocalDaemonPlatform;
import com.tridium.install.BModuleList;
import com.tridium.install.BRemoteDaemonPlatform;
import com.tridium.install.BVersion;
import com.tridium.install.installable.BModuleInstallable;
import com.tridium.install.installable.BundleManifest;
import com.tridium.install.installable.DistributionManifest;
import com.tridium.install.part.BBrandPart;
import com.tridium.install.part.BNrePart;
import com.tridium.install.part.BPart;
import com.tridium.install.part.BVmPart;
import com.tridium.nre.platform.PlatformUtil;
import com.tridium.nre.security.EncryptionKeySource;
import com.tridium.nre.security.KeyRing;
import com.tridium.nre.security.NiagaraBasicPermission;
import com.tridium.nre.security.PBEDecryptingInputStream;
import com.tridium.nre.security.PBEEncodingInfo;
import com.tridium.nre.security.PBEEncodingKey;
import com.tridium.nre.security.SecretChars;
import com.tridium.nre.security.SecurityInitializer;
import com.tridium.nre.security.io.AESStreamEncryption;
import com.tridium.nre.security.io.BogPasswordObjectEncoder;
import com.tridium.nre.security.io.PBEEncryptingInputStream;
import com.tridium.nre.util.BogTranscoderInputStream;
import com.tridium.platform.BHostIdStatus;
import com.tridium.platform.SystemFilePaths;
import com.tridium.platform.daemon.BDaemonSession;
import com.tridium.platform.daemon.BStationSurrogate;
import com.tridium.platform.daemon.DaemonClientEncodingInfo;
import com.tridium.platform.daemon.LocalSessionUtil;
import com.tridium.platform.daemon.NiagaraPlatformDaemon;
import com.tridium.platform.daemon.file.BCachedDaemonFileSpace;
import com.tridium.platform.daemon.message.UpdateStationMessage;
import com.tridium.platform.daemon.task.DaemonSessionTaskListener;
import com.tridium.sys.Nre;

/**
 * BBackupService is used to define the files included in a
 * configuration backup such as config.bog and supporting static
 * files such as px, html, png, and jpegs.  Backups do not include
 * runtime data actively being managed by the station such as the
 * alarm and history databases.
 *
 * @author Brian Frank and Matt Boon on 11 Apr 05
 * @since Baja 1.0
 */
@NiagaraType
/*
 Semicolon separated list of file patterns to exclude from backups that
 are performed while the station is running.
 */
@NiagaraProperty(
  name = "excludeFiles",
  type = "String",
  defaultValue = "*.hdb;*.adb;*.lock;*backup*;console.*;config.bog.b*;config_backup*"
)
/*
 List of station relative file ords to exclude from backups that are
 performed while the station is running.
 */
@NiagaraProperty(
  name = "excludeDirectories",
  type = "BOrdList",
  defaultValue = "BOrdList.make(new BOrd[] { BOrd.make(\"file:^^history\"), BOrd.make(\"file:^^alarm\"), BOrd.make(\"file:^^webFileCache\") })",
  facets = @Facet("BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE, BFacets.TARGET_TYPE, BString.make(\"baja:IDirectory\"))")
)
/*
 Semicolon separated list of file patterns to exclude from backups that
 are performed while the station is stopped.
 */
@NiagaraProperty(
  name = "offlineExcludeFiles",
  type = "String",
  defaultValue = "*.lock;*backup*;console.*;config.bog.b*;config_backup*"
)
/*
 List of station relative file ords to exclude from backups that are
 performed while the station is stopped.
 */
@NiagaraProperty(
  name = "offlineExcludeDirectories",
  type = "BOrdList",
  defaultValue = "BOrdList.NULL",
  facets = @Facet("BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE, BFacets.TARGET_TYPE, BString.make(\"baja:IDirectory\"))")
)
public class BBackupService
  extends BAbstractService
  implements BIRestrictedComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.backup.BBackupService(3624163063)1.0$ @*/
/* Generated Mon Jul 11 17:00:16 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "excludeFiles"

  /**
   * Slot for the {@code excludeFiles} property.
   * Semicolon separated list of file patterns to exclude from backups that
   * are performed while the station is running.
   * @see #getExcludeFiles
   * @see #setExcludeFiles
   */
  public static final Property excludeFiles = newProperty(0, "*.hdb;*.adb;*.lock;*backup*;console.*;config.bog.b*;config_backup*", null);

  /**
   * Get the {@code excludeFiles} property.
   * Semicolon separated list of file patterns to exclude from backups that
   * are performed while the station is running.
   * @see #excludeFiles
   */
  public String getExcludeFiles() { return getString(excludeFiles); }

  /**
   * Set the {@code excludeFiles} property.
   * Semicolon separated list of file patterns to exclude from backups that
   * are performed while the station is running.
   * @see #excludeFiles
   */
  public void setExcludeFiles(String v) { setString(excludeFiles, v, null); }

  //endregion Property "excludeFiles"

  //region Property "excludeDirectories"

  /**
   * Slot for the {@code excludeDirectories} property.
   * List of station relative file ords to exclude from backups that are
   * performed while the station is running.
   * @see #getExcludeDirectories
   * @see #setExcludeDirectories
   */
  public static final Property excludeDirectories = newProperty(0, BOrdList.make(new BOrd[] { BOrd.make("file:^^history"), BOrd.make("file:^^alarm"), BOrd.make("file:^^webFileCache") }), BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE, BFacets.TARGET_TYPE, BString.make("baja:IDirectory")));

  /**
   * Get the {@code excludeDirectories} property.
   * List of station relative file ords to exclude from backups that are
   * performed while the station is running.
   * @see #excludeDirectories
   */
  public BOrdList getExcludeDirectories() { return (BOrdList)get(excludeDirectories); }

  /**
   * Set the {@code excludeDirectories} property.
   * List of station relative file ords to exclude from backups that are
   * performed while the station is running.
   * @see #excludeDirectories
   */
  public void setExcludeDirectories(BOrdList v) { set(excludeDirectories, v, null); }

  //endregion Property "excludeDirectories"

  //region Property "offlineExcludeFiles"

  /**
   * Slot for the {@code offlineExcludeFiles} property.
   * Semicolon separated list of file patterns to exclude from backups that
   * are performed while the station is stopped.
   * @see #getOfflineExcludeFiles
   * @see #setOfflineExcludeFiles
   */
  public static final Property offlineExcludeFiles = newProperty(0, "*.lock;*backup*;console.*;config.bog.b*;config_backup*", null);

  /**
   * Get the {@code offlineExcludeFiles} property.
   * Semicolon separated list of file patterns to exclude from backups that
   * are performed while the station is stopped.
   * @see #offlineExcludeFiles
   */
  public String getOfflineExcludeFiles() { return getString(offlineExcludeFiles); }

  /**
   * Set the {@code offlineExcludeFiles} property.
   * Semicolon separated list of file patterns to exclude from backups that
   * are performed while the station is stopped.
   * @see #offlineExcludeFiles
   */
  public void setOfflineExcludeFiles(String v) { setString(offlineExcludeFiles, v, null); }

  //endregion Property "offlineExcludeFiles"

  //region Property "offlineExcludeDirectories"

  /**
   * Slot for the {@code offlineExcludeDirectories} property.
   * List of station relative file ords to exclude from backups that are
   * performed while the station is stopped.
   * @see #getOfflineExcludeDirectories
   * @see #setOfflineExcludeDirectories
   */
  public static final Property offlineExcludeDirectories = newProperty(0, BOrdList.NULL, BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE, BFacets.TARGET_TYPE, BString.make("baja:IDirectory")));

  /**
   * Get the {@code offlineExcludeDirectories} property.
   * List of station relative file ords to exclude from backups that are
   * performed while the station is stopped.
   * @see #offlineExcludeDirectories
   */
  public BOrdList getOfflineExcludeDirectories() { return (BOrdList)get(offlineExcludeDirectories); }

  /**
   * Set the {@code offlineExcludeDirectories} property.
   * List of station relative file ords to exclude from backups that are
   * performed while the station is stopped.
   * @see #offlineExcludeDirectories
   */
  public void setOfflineExcludeDirectories(BOrdList v) { set(offlineExcludeDirectories, v, null); }

  //endregion Property "offlineExcludeDirectories"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBackupService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Service
////////////////////////////////////////////////////////////////

  /**
   * Register this component under "backup:BackupService".
   */
  @Override
  public Type[] getServiceTypes()
  {
    return serviceTypes;
  }
  private static Type[] serviceTypes = new Type[] { TYPE };

  /**
   * Service start.
   */
  @Override
  public void serviceStarted()
    throws Exception
  {
    super.serviceStarted();
    registerFoxChannel();

    try
    {
      Sys.getService(BIDataRecoveryService.TYPE);

      //If service resolved, add BOrd.make("file:^^dataRecovery") to exclude list
      BOrd newExclude = BOrd.make("file:^^dataRecovery");

      boolean addToExclude = true;

      for (BOrd anOrdList : getExcludeDirectories().toArray())
      {
        if (anOrdList.equals(newExclude))
        {
          //Already in exclude
          addToExclude = false;
          break;
        }
      }

      if (addToExclude)
      {
        setExcludeDirectories(BOrdList.add(getExcludeDirectories(), newExclude));
      }
    }
    catch (ServiceNotFoundException snfe)
    {
      //Service not found, don't bother adding to exclude
    }
  }

  /**
   * See https://acsjira.honeywell.com/browse/NCCB-24878
   */
  void registerFoxChannel() {
    String foxChannelId = getFoxChannelId();
    if (BFoxChannelRegistry.getPrototype().get(foxChannelId) == null) {
      BFoxChannelRegistry.getPrototype().add(foxChannelId, new BBackupChannel());
    } else {
      log.info("The Fox " + foxChannelId + " channel exists.");
    }
  }

////////////////////////////////////////////////////////////////
// RestrictedComponent
////////////////////////////////////////////////////////////////

  /**
   * This service type is only allowed to live under the
   * station's frozen ServiceContainer, but multiple instances are allowed.
   * Only Super Users are allowed to add an instance of this type to the station.
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    BIRestrictedComponent.checkParentIsServiceContainer(parent, this);
    BIRestrictedComponent.checkContextForSuperUser(this, cx);
  }

////////////////////////////////////////////////////////////////
// Backup Files
////////////////////////////////////////////////////////////////

  public BIFile[] listStationBackupFiles()
    throws Exception
  {
    Array<FileTuple> acc = new Array<>(FileTuple.class);
    findStationBackupFiles(BFileSystem.INSTANCE.getProtectedStationHome(), Sys.getStation().getStationName(), acc, null);
    return acc.apply(BIFile.class, fileTuple -> ((FileTuple)fileTuple).file).trim();
  }

////////////////////////////////////////////////////////////////
// File restore
////////////////////////////////////////////////////////////////

  /**
   * Restore the station files using the given backup distribution file.
   *
   * If the backup distribution file states dependencies that are not met by
   * the station running this service, the restore fails.
   *
   * If this method completes successfully, backupFile will be deleted.
   *
   * IMPORTANT: the scope of this restore is only the file contents of the
   * distribution file.   This method will not install any software, change
   * a module content filter, or change TCP/IP settings.
   *
   * When a context is provided, adminWrite permissions is required and
   * this operation will be audited.
   *
   * @param backupFile A distribution file produced by the BBackupService.
   * @since Niagara 4.13
   */

  public void restoreFiles(BIFile backupFile, Context cx)
    throws Exception
  {
    restoreFiles(backupFile, false, true, 1500, cx);
  }

  /**
   * Restore the station files using the given backup distribution file.
   *
   * If the backup distribution file states dependencies that are not met by
   * the station running this service, the restore fails.
   *
   * If this method completes successfully, backupFile will be deleted.
   *
   * IMPORTANT: the scope of this restore is only the file contents of the
   * distribution file.   This method will not install any software, change
   * a module content filter, or change TCP/IP settings.
   *
   * @param backupFile A distribution file produced by the BBackupService.
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0. Use {@link #restoreFiles(BIFile, Context)} instead.
   */
  @Deprecated
  public void restoreFiles(BIFile backupFile)
    throws Exception
  {
    restoreFiles(backupFile, null);
  }

  /**
   * Restore the station files using the given backup distribution file.
   *
   * IMPORTANT: the scope of this restore is only the file contents of the
   * distribution file.   This method will not install any software, change
   * a module content filter, or change TCP/IP settings.
   *
   * When a context is provided, adminWrite permissions is required and
   * this operation will be audited.
   *
   * @param backupFile A distribution file produced by the BBackupService
   * @param ignoreDependencies If true, the dependencies in the backup
   *   file will not be verified before starting the file restore
   * @param deleteFile If true, backupFile will be deleted after the
   *   method completes successfully
   * @since Niagara 4.13
   */
  public void restoreFiles(BIFile  backupFile,
    boolean ignoreDependencies,
    boolean deleteFile,
    Context cx)
    throws Exception
  {
    restoreFiles(backupFile, ignoreDependencies, deleteFile, 1500, cx);
  }
  /**
   * Restore the station files using the given backup distribution file.
   *
   * IMPORTANT: the scope of this restore is only the file contents of the
   * distribution file.   This method will not install any software, change
   * a module content filter, or change TCP/IP settings.
   *
   * @param backupFile A distribution file produced by the BBackupService
   * @param ignoreDependencies If true, the dependencies in the backup
   *   file will not be verified before starting the file restore
   * @param deleteFile If true, backupFile will be deleted after the
   *   method completes successfully
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0. Use {@link #restoreFiles(BIFile, boolean, boolean, Context)} instead.
   */
  @Deprecated
  public void restoreFiles(BIFile  backupFile,
                           boolean ignoreDependencies,
                           boolean deleteFile)
    throws Exception
  {
    restoreFiles(backupFile, ignoreDependencies, deleteFile, 1500, null);
  }

  /**
   * Restore the station files using the given backup distribution file.
   *
   * IMPORTANT: the scope of this restore is only the file contents of the
   * distribution file.   This method will not install any software, change
   * a module content filter, or change TCP/IP settings.
   *
   *  When a context is provided, adminWrite permissions is required and
   *  this operation will be audited.
   *
   * @param backupFile A distribution file produced by the BBackupService
   * @param ignoreDependencies If true, the dependencies in the backup
   *   file will not be verified before starting the file restore
   * @param deleteFile If true, backupFile will be deleted after the
   *   method completes successfully
   * @param shutdownDelay millis to wait after returning control to the
   *   caller before station shutdown should commence
   * @since Niagara 4.13
   */
  public void restoreFiles(BIFile  backupFile,
                           boolean ignoreDependencies,
                           boolean deleteFile,
                           long    shutdownDelay,
                           Context cx)
    throws Exception
  {
    checkUserAndAuditForRestore(cx);

    NiagaraBasicPermission restorePermission = new NiagaraBasicPermission("RESTORE_BACKUP");
    SecurityManager sm = System.getSecurityManager();
    if (sm != null) sm.checkPermission(restorePermission);

    AccessController.doPrivileged((PrivilegedExceptionAction<Void>) () ->
    {
      log.info("Restoring from backup file " + backupFile.getFilePath().getBody());

      // op constructor does all the validation
      RestoreOp op = new RestoreOp(backupFile, ignoreDependencies, deleteFile, shutdownDelay);

      // find the log level of backup log is directly set, use default log level otherwise, pass to restore op
      if (log.getLevel() != null)
      {
        op.logLevel = log.getLevel();
      }

      if (!op.ignoreDependencies)
      {
        // ensure that all of the platform & software
        // dependencies in the backup file are met by
        // the local environment
        verifyDependencies(op);
      }

      log.warning("Shutting down the station for file restore.  Please do not kill or reboot while the restore is in progress.");
      log.warning("The station will be restarted automatically once the restore completes.");

      new Thread(op).start();

      return null;
    });
  }

  /**
   * Restore the station files using the given backup distribution file.
   *
   * IMPORTANT: the scope of this restore is only the file contents of the
   * distribution file.   This method will not install any software, change
   * a module content filter, or change TCP/IP settings.
   *
   * @param backupFile A distribution file produced by the BBackupService
   * @param ignoreDependencies If true, the dependencies in the backup
   *   file will not be verified before starting the file restore
   * @param deleteFile If true, backupFile will be deleted after the
   *   method completes successfully
   * @param shutdownDelay millis to wait after returning control to the
   *   caller before station shutdown should commence
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0. Use {@link #restoreFiles(BIFile, boolean, boolean, long, Context)} instead.
   */
  @Deprecated
  public void restoreFiles(
    BIFile backupFile,
    boolean ignoreDependencies,
    boolean deleteFile,
    long shutdownDelay)
    throws Exception
  {
    restoreFiles(backupFile, ignoreDependencies, deleteFile, shutdownDelay, null);
  }

  /**
   * Entry point for the restore thread
   */
  private void doRestore(RestoreOp op)
  {
    String oldStationName = op.stationName;

    // files can't be written while station is running, so shut
    // it down
    Runtime.getRuntime().addShutdownHook(new RestoreThread(op));

    //surrogate stop
    try
    {
      BDaemonSession daemon = AccessController.doPrivileged(((PrivilegedAction<BDaemonSession>)LocalSessionUtil::getLocalSession));

      NiagaraPlatformDaemon niagaraDaemon = NiagaraPlatformDaemon.make(daemon, DaemonSessionTaskListener.NULL_TASK_LISTENER);

      for (RemoteStation station : niagaraDaemon.getStationManager().getAllStations())
      {
        if (station.getName().equals(oldStationName))
        {
          BStationSurrogate stationSurrogate = BStationSurrogate.make(daemon, oldStationName);
          stationSurrogate.stopStation(null, null);
        }
      }
    }
    catch (Exception ex)
    {
      System.out.println("ERROR: Could not commit platform changes.");
      ex.printStackTrace();
    }
  }

  public class RestoreThread
    extends Thread
  {
    public RestoreThread(RestoreOp op)
    {
      this.op = op;
    }

    private final RestoreOp op;

    @Override
    public void run()
    {
      try
      {
        System.out.println();
        System.out.println();

        NiagaraBasicPermission restorePermission = new NiagaraBasicPermission("RESTORE_BACKUP");
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) sm.checkPermission(restorePermission);

        AccessController.doPrivileged((PrivilegedExceptionAction<Void>) () ->
        {
        // restore the files from the backup dist
          restoreFiles(op);

          op.zipFile.close();

          if (op.deleteFile)
          {
            op.consoleMessage("Deleting backup file");
            op.backupFile.delete();
            op.backupFile = null;
          }

          // restart/reboot the niagara environment
          restartStation(op);
          return null;
        });
      }
      catch (Exception e)
      {
        op.consoleError("Restore error", e);
      }
      finally
      {
        // don't leave the backup file around, since storage on
        // the station may be scarce
        if (op.deleteFile && (op.backupFile != null))
        {
          try
          {
            op.backupFile.delete();
          }
          catch (IOException ignored)
          {
          }
        }
      }
    }
  }

  /**
   * Throw an exception if the local platform fails to meet
   * any of the dependencies described in the backup dist file
   */
  private void verifyDependencies(RestoreOp op)
    throws Exception
  {
    log.info("Verifying backup file dependencies");

    Array<BDependency> unmet = new Array<>(BDependency.class);
    for (BDependency dep : op.manifest().getDependencies())
    {
      if (!op.daemonPlatform().meets(dep))
      {
        unmet.add(dep);
      }
    }
    if (unmet.size() > 0)
    {
      StringBuilder message = new StringBuilder();
      for (int i = 0; i < unmet.size(); i++)
      {
        message.append(' ').append(unmet.get(i).toString());
      }
      throw new LocalizableRuntimeException("backup",
                                            "restore.error.unmetDeps",
                                            new Object[] { message });
    }
  }

  /**
   * Copy each file in the backup dist to the local host
   */
  private void restoreFiles(RestoreOp op)
    throws Exception
  {
    String newStationName = null;
    boolean anyErrors = false;

    op.consoleMessage("Cleaning original station folder:" + op.stationName);
    try
    {
      FilePath stationDir = new FilePath("~stations/" + op.stationName);
      BFileSystem.INSTANCE.delete(stationDir);
    }
    catch (Exception e)
    {
      op.consoleError("Cleaning original station folder failed", e);
      anyErrors=true;
    }
    
    // Avoid copying license file / certificates if the platform uses readonly settings
    boolean licenseReadonly = AccessController.doPrivileged(((PrivilegedExceptionAction<Boolean>)() -> PlatformUtil.getPlatformProvider().isLicenseReadonly()));
    
    op.consoleMessage("Restoring files");

    try(SecretChars systemPassword = AccessController.doPrivileged(((PrivilegedExceptionAction<SecretChars>)() ->
        PlatformUtil.getPlatformProvider().getSystemPassword()));
        PBEEncodingKey encodingKey = op.manifest.getPBEEncodingInfo().makePBEKey(systemPassword))
    {
      for (Enumeration<? extends ZipEntry> eEntry = op.zipFile.entries(); eEntry.hasMoreElements(); )
      {
        ZipEntry entry = eEntry.nextElement();

        if (entry.getName().toLowerCase().startsWith("meta-inf"))
        {
          //Never restore any meta-informational files about the distribution itself
          continue;
        }
        else if (licenseReadonly &&
                 (entry.getName().toLowerCase().endsWith(".license") ||
                  entry.getName().toLowerCase().endsWith(".certificate")))
        {
          if (op.isLoggable(Level.FINE))
          {
            op.consoleTrace("Skipping file '" + entry.getName() + "' because host licenses are readonly");
          }
          
          //Never restore licenses and certificates if the readonly
          continue;
        }

        if (getPathForEntry(entry.getName(), op).getName().equals(".kr"))
        {
          op.consoleMessage("Importing Key Ring Data");
          InputStream in = op.zipFile.getInputStream(entry);
          SecurityInitializer.getInstance().getSecurityInfoProvider().getKeyRing().importKeyData(in, (int)entry.getSize(), encodingKey);
          continue;
        }

        InputStream in = null;
        OutputStream out = null;

        try
        {
          BIFile outputFile = BFileSystem.INSTANCE.makeFile(getPathForEntry(entry.getName(), op));

          op.consoleMessage("Restoring file " + outputFile.getFilePath().getBody() + "...");

          //Obtain the output stream
          out = outputFile.getOutputStream();

          //Obtain the input stream
          if (matchesAnyPattern(outputFile.getFilePath(), PROTECTED_STATION_PATHS))
          {
            if (op.isLoggable(Level.FINE))
            {
              op.consoleTrace("Restore file '" + outputFile.getFilePath().getBody() + "' matched PROTECTED_STATION_PATH pattern");
            }
            //Use PBEDecryptingInputStream to decrypt entry when written using system passphrase
            in = new PBEDecryptingInputStream(op.zipFile.getInputStream(entry), encodingKey);
          }
          else if (matchesAnyPattern(outputFile.getFilePath(), KEYRING_ENCRYPTED_STATION_PATHS))
          {
            if (op.isLoggable(Level.FINE))
            {
              op.consoleTrace("Restore file '" + outputFile.getFilePath().getBody() + "' matched KEYRING_ENCRYPTED_STATION_PATH pattern");
            }
            //Use AESStreamEncryption to decrypt entry when written using key ring
            in = AESStreamEncryption.pbeToKeyRing(op.zipFile.getInputStream(entry), encodingKey, SecurityInitializer.getInstance().getSecurityInfoProvider());
          }
          else if (outputFile instanceof BBogFile)
          {
            ValueDocDecoder.BogDecoderPlugin decoderPlugin = new ValueDocDecoder.BogDecoderPlugin(op.zipFile.getInputStream(entry));
            decoderPlugin.readHeader();
            BogPasswordObjectEncoder passwordObjectEncoder = decoderPlugin.getPasswordObjectEncoder();
            if ((passwordObjectEncoder != null) && EncryptionKeySource.external.equals(passwordObjectEncoder.getKeySource()))
            {
              if (op.isLoggable(Level.FINE))
              {
                op.consoleTrace("Restore file '" + outputFile.getFilePath().getBody() + "' matched external EncryptionKeySource requirements");
              }
              //Transcode the bog file so that its passwords are protected with a key from the keyring and not the system passphrase
              in = new BogTranscoderInputStream(AccessController.doPrivileged(((PrivilegedExceptionAction<KeyRing>)() -> SecurityInitializer.getInstance().getSecurityInfoProvider().getKeyRing())),
                                                op.zipFile.getInputStream(entry),
                                                !op.isLoggable(Level.FINEST),
                                                encodingKey,
                                                EncryptionKeySource.keyring);
            }
            else
            {
              //Standard input stream
              in = op.zipFile.getInputStream(entry);
            }
          }
          else
          {
            //Standard input stream
            in = op.zipFile.getInputStream(entry);
          }

          // validate that input stream was created
          if (in == null)
          {
            if (outputFile instanceof BBogFile)
            {
              // presume that any failure to obtain bog input stream is probably a key encoding error
              throw new LocalizableRuntimeException("backup",
                                                    "restore.error.noInputStreamBog",
                                                    new Object[] { outputFile.getFilePath().getBody() });
            }
            else
            {
              throw new LocalizableRuntimeException("backup",
                                                    "restore.error.noInputStream",
                                                    new Object[] { outputFile.getFilePath().getBody() });
            }
          }

          FileUtil.pipe(in, out);

          FilePath outPath = outputFile.getFilePath();
          if (outPath.getName().equals("config.bog") &&
             (outPath.getParent().getParent().getBody().equals("~stations") ||
              outPath.getParent().getParent().getBody().equals("/niagara/stations")))
          {
            newStationName = outPath.getParent().getName();
          }
        }
        catch (Exception e)
        {
          // allow any single file copy to fail; I think that's less likely
          // to leave a restored station in an unusable state
          op.consoleError("File copy failed", e);
          anyErrors = true;
        }
        finally
        {
          //Ensure streams are closed at completion
          try
          {
            if (in != null)
            {
              in.close();
            }
          }
          catch (Exception ignored)
          {}

          try
          {
            if (out != null)
            {
              out.close();
            }
          }
          catch (Exception ignored)
          {}
        }
      }
    }

    try
    {
      //station name fix
      if (newStationName != null && op.stationName != null && !newStationName.equals(op.stationName))
      {
        op.consoleMessage("stationName is changing from " + op.stationName + " to " + newStationName);

        BDaemonSession daemon = AccessController.doPrivileged(((PrivilegedAction<BDaemonSession>)LocalSessionUtil::getLocalSession));

        BStationSurrogate station = BStationSurrogate.make(daemon, op.stationName);
        boolean autoStart = station.getIsAutoStart();
        boolean autoRestart = station.getIsAutoRestart();

        daemon.sendMessage(new UpdateStationMessage());
        BStationSurrogate newStationSurrogate = BStationSurrogate.make(daemon, newStationName);
        daemon.sendMessage(new UpdateStationMessage(newStationName, false, autoStart, autoRestart, true));

        //Windows / Linux Supervisor needs station start here or it hangs, JACE complains
        if (Nre.getHostModel().equals(WORKSTATION_HOST_MODEL))
        {
          newStationSurrogate.startStationAsync();
        }

        //stop old station from auto-starting
        daemon.sendMessage(new UpdateStationMessage(op.stationName, true, false, false, true));

        op.newStationName = newStationName;
      }
      else if (op.stationName != null && newStationName != null && newStationName.equals(op.stationName))
      {
        op.consoleMessage("stationName is still " + op.stationName);
      }
      else
      {
        op.consoleMessage("unable to determine station name difference; oldName = '" + op.stationName + "', newName = '" + newStationName + "'");
      }
    }
    catch (Exception e)
    {
      // allow any single file copy to fail; I think that's less likely
      // to leave a restored station in an unusable state
      op.consoleError("Station Name Analysis Failed", e);
      anyErrors = true;
    }

    if (anyErrors)
    {
      op.consoleWarning("Restore completed with errors");
    }
    else
    {
      op.consoleMessage("Restore completed successfully");
    }
  }

  /**
   * Translate the given zip entry path into a file path
   */
  private FilePath getPathForEntry(String entryPath, RestoreOp op)
    throws Exception
  {
    if (op.manifest().useAbsoluteElementPaths())
    {
      return new FilePath('/' + entryPath);
    }
    else
    {
      int firstSlash = entryPath.indexOf('/');
      if (firstSlash > 0)
      {
        String relativePath = entryPath.substring(0, firstSlash);
        String body = entryPath.substring(firstSlash+1);
        if (BundleManifest.NIAGARA_HOME_RELATIVE_PATH.equals(relativePath))
          return new FilePath('!' + body);
        else if (BundleManifest.NIAGARA_USER_HOME_RELATIVE_PATH.equals(relativePath))
          return new FilePath('~' + body);
      }
      return new FilePath('!' + entryPath);
    }
  }

  /**
   * Restart the station.  This may involve rebooting the host
   * if the platform doesn't allow starting halted stations.
   */
  private void restartStation(RestoreOp op)
    throws Exception
  {
    if (op.isRestartEnabled)
    {
      op.consoleMessage("Requesting station restart from niagarad");

      op.stationSurrogate().restartStationAsync(true); // must kill because the
                                                // console isn't taking
                                                // input from stdin any
                                                // more
    }
    else
    {
      op.consoleMessage("Requesting reboot from niagarad");
      // We're on a QNX host so we can't start a halted station.
      // we must reboot instead.  Use the "force" version of the request
      // to make sure it doesn't get hung up in the shutdown sequence
      op.daemonPlatform().getDaemonSession().sendForceRebootRequest();
    }
  }

////////////////////////////////////////////////////////////////
// Backup Stream
////////////////////////////////////////////////////////////////

  /**
   * Given an output stream, write a zip file containing all
   * the backup files defined by this BackupService.  If the
   * close argument is true then close the output stream when
   * done, otherwise just finish it.  If the job argument is
   * non-null, then it is used to update progress, logging,
   * and to check for cancellation.
   *
   * When a context is provided, adminWrite permissions is required and
   * this operation will be audited.
   *
   * @since Niagara 4.13
   */
  public void zip(BJob job, OutputStream out, boolean close, Context cx)
    throws Exception
  {
    checkUserAndAuditForBackup(cx);

    BLocalDaemonPlatform instance;
    try
    {
      instance = AccessController.doPrivileged(((PrivilegedExceptionAction<BLocalDaemonPlatform>)BLocalDaemonPlatform::getInstance));
    }
    catch (PrivilegedActionException pae)
    {
      throw pae.getException();
    }

    try (BackupOp backupOp = new BackupOp(job, makeCanceler(job), instance))
    {
      zip(out, close, backupOp);
    }
    finally
    {
      try
      {
        if (close)
        {
          out.close();
        }
      }
      catch (Exception ignored)
      {
      }
    }
  }

  /**
   * Given an output stream, write a zip file containing all
   * the backup files defined by this BackupService.  If the
   * close argument is true then close the output stream when
   * done, otherwise just finish it.  If the job argument is
   * non-null, then it is used to update progress, logging,
   * and to check for cancellation.
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0. Use {@link #zip(BJob, OutputStream, boolean, Context)} instead.
   */
  @Deprecated
  public void zip(BJob job, OutputStream out, boolean close)
    throws Exception
  {
    zip(job, out, close, null);
  }

  /**
   * Given an output stream, write a zip file containing all the backup
   * files for the given remote host.  If the close argument is true then
   * close the output stream when done, otherwise just finish it.  If the
   * job argument is non-null, then it is used to update progress, logging,
   * and to check for cancelation.
   *
   * When a context is provided, adminWrite permissions is required and
   * this operation will be audited.
   *
   * @since Niagara 4.13
   */
  public void zip(BJob job, PlatformDaemon platformDaemon, OutputStream out, boolean close, Context cx)
    throws Exception
  {
    checkUserAndAuditForBackup(cx);

    try
    {
      BDaemonPlatform daemonPlatform = ((NiagaraPlatformDaemon)platformDaemon).getDaemonPlatform(null);
      //NCCB-9875: Enabling backups for N4 supervisor to backup AX and N4
      //noinspection ConstantConditions
      if(!daemonPlatform.getIsNiagara4() && daemonPlatform instanceof BRemoteDaemonPlatform)
      {
        new BAxOfflineBackup().zip(job, (BRemoteDaemonPlatform) daemonPlatform, out, close);
      }
      else
      {
        try(BackupOp backupOp = new BackupOp(job, makeCanceler(job), daemonPlatform))
        {
          zip(out, close, backupOp);
        }
      }
    }
    finally
    {
      try { if (close) out.close(); } catch(Exception ignored) {}
    }
  }

  /**
   * Given an output stream, write a zip file containing all the backup
   * files for the given remote host.  If the close argument is true then
   * close the output stream when done, otherwise just finish it.  If the
   * job argument is non-null, then it is used to update progress, logging,
   * and to check for cancelation.
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0. Use {@link #zip(BJob, PlatformDaemon, OutputStream, boolean, Context)} instead.
   */
  @Deprecated
  public void zip(BJob job, PlatformDaemon platformDaemon, OutputStream out, boolean close)
    throws Exception
  {
    zip(job, platformDaemon, out, close, null);
  }

  /**
   * Given an output stream, write a zip file containing all the backup
   * files for the given remote host.  If the close argument is true then
   * close the output stream when done, otherwise just finish it.  If the
   * log argument is non-null, then it is used for logging.
   *
   * When a context is provided, adminWrite permissions is required and
   * this operation will be audited.
   * @since Niagara 4.13
   */
  public void zip(JobLog log, PlatformDaemon platformDaemon, OutputStream out, boolean close, Context cx)
    throws Exception
  {
    zip(log, NULL_CANCELER, platformDaemon, out, close, cx);
  }

  /**
   * Given an output stream, write a zip file containing all the backup
   * files for the given remote host.  If the close argument is true then
   * close the output stream when done, otherwise just finish it.  If the
   * log argument is non-null, then it is used for logging.
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0. Use {@link #zip(JobLog, PlatformDaemon, OutputStream, boolean, Context)} instead.
   */
  @Deprecated
  public void zip(JobLog log, PlatformDaemon platformDaemon, OutputStream out, boolean close)
    throws Exception
  {
    zip(log, NULL_CANCELER, platformDaemon, out, close, null);
  }

  /**
   * Given an output stream, write a zip file containing all the backup
   * files for the given remote host.  If the close argument is true then
   * close the output stream when done, otherwise just finish it.  If the
   * log argument is non-null, then it is used for logging.
   *
   * When a context is provided, adminWrite permissions is required and
   * this operation will be audited.
   *
   * @since Niagara 4.13
   */
  public void zip(JobLog log, ICanceler canceler, PlatformDaemon platformDaemon, OutputStream out, boolean close, Context cx)
    throws Exception
  {
    checkUserAndAuditForBackup(cx);

    try
    {
      BDaemonPlatform daemonPlatform = ((NiagaraPlatformDaemon)platformDaemon).getDaemonPlatform(null);

      //NCCB-9875: Enabling backups for N4 supervisor to backup AX and N4
      //noinspection ConstantConditions
      if(!daemonPlatform.getIsNiagara4() && daemonPlatform instanceof BRemoteDaemonPlatform)
      {
        new BAxOfflineBackup().zip(log, canceler, (BRemoteDaemonPlatform)daemonPlatform, out, close);
      }
      else
      {
        try(BackupOp backupOp = new BackupOp(log, canceler, daemonPlatform))
        {
          zip(out, close, backupOp);
        }
      }
    }
    finally
    {
      try { if (close) out.close(); } catch(Exception ignored) {}
    }
  }

  /**
   * Given an output stream, write a zip file containing all the backup
   * files for the given remote host.  If the close argument is true then
   * close the output stream when done, otherwise just finish it.  If the
   * log argument is non-null, then it is used for logging.
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0. Use {@link #zip(JobLog, ICanceler, PlatformDaemon, OutputStream, boolean, Context)} instead.
   */
  @Deprecated
  public void zip(JobLog log, ICanceler canceler, PlatformDaemon platformDaemon, OutputStream out, boolean close)
    throws Exception
  {
    zip(log, canceler, platformDaemon, out, close, null);
  }

  /**
   * When a context is provided, adminWrite permissions is required and
   * this operation will be audited.
   * @since Niagara 4.13
   */
  private void checkUserAndAuditForBackup(Context cx)
  {
    checkUserAndAudit(getLexicon().get("backup"), cx);
  }

  /**
   * When a context is provided, adminWrite permissions is required and
   * this operation will be audited.
   * @since Niagara 4.13
   */
  private void checkUserAndAuditForRestore(Context cx)
  {
    checkUserAndAudit(getLexicon().get("restore"), cx);
  }



  /**
   * When a context is provided, adminWrite permissions is required and
   * this operation will be audited.
   * @since Niagara 4.13
   */
  private void checkUserAndAudit(String operation, Context cx)
  {
    String userName = "";
    if ((cx != null) && (cx.getUser() != null))
    {
      BUser user = cx.getUser();
      //see BFoxBackupJob.java, adminWrite is required for a fox backup so the same thing is required here.
      user.check(this, BPermissions.adminWrite);
      userName = cx.getUser().getUsername();
    }

    Auditor auditor = Nre.auditor;
    if (auditor != null && isMounted() && !userName.isEmpty())
    {
      auditor.audit(new AuditEvent(
        AuditEvent.INVOKED,
        getSlotPath().getBody(),
        operation,
        "",
        "",
        userName));
    }
  }

  private void zip(OutputStream out, boolean close, BackupOp op)
    throws Exception
  {
    long    t1           = Clock.millis();

    BBackupService.log.info("Backup starting...");

    if (op.log() != null) op.log().message("Backup starting...");


    // open zip stream
    op.zip = new ZipOutputStream(out);

    op.platform().checkSession();
    op.checkCanceled();

    // set up the dist manifest
    processManifest(op);

    // find the non-station files to be included
    findPlatformBackupFiles(op);

    // find station files to be included
    if (op.offline())
    {
      findRemoteStationFiles(op);
    }
    else
    {
      findStationBackupFiles(BFileSystem.INSTANCE.getProtectedStationHome(), Sys.getStation().getStationName(), op.files(), op);
    }

    try
    {
      // put the contents of the included files in the dist zip
      addFilesToDist(op);

      // write the dist.xml manifest
      if (op.log() != null) op.log().start("Adding distribution manifest");
      op.zip.putNextEntry(new ZipEntry("META-INF/dist.xml"));
      op.manifest().write(op.zip);
      if (op.log() != null) op.log().endSuccess();
    }
    catch (Exception e)
    {
      if (BBackupService.log.isLoggable(Level.FINE))
      {
        BBackupService.log.log(Level.WARNING, "Exception occurred while adding files to backup distribution: " + e, e);
      }
      else
      {
        BBackupService.log.warning("Exception occurred while adding files to backup distribution: " + e);
      }
      throw e;
    }
    finally
    {
      // finish and potentially close zip
      op.zip.finish();
      if (close)
      {
        op.zip.flush();
        op.zip.close();
      }
    }

    if (op.log() != null) op.log().success("Backup successful");
    if (op.job() != null)
    {
      op.job().setProgress(100);
    }

    // system log
    long t2 = Clock.millis();
    BBackupService.log.info("Finished backup, " + op.totalFilesCreated() + " files [" + (t2-t1) + "ms]");
  }

  /**
   * Define the distribution manifest for this backup
   */
  private void processManifest(BackupOp op)
    throws Exception
  {
    op.manifest().setInstallableName("backupdist");
    op.manifest().setInstallableVersion(new BVersion("Tridium", "0"));
    op.manifest().setBuildDate(BAbsTime.now());
    op.manifest().setBuildHost(Sys.getHostName());

    //NCCB-1029 Only require a reboot if we have to, don't hardcode it.
    //If the platform can not restart a station without a reboot...
    if (!op.platform().getAllowStationRestart())
    {
      //Require a reboot
      op.manifest().setRebootRequired(true);
    }

    op.manifest().setNoRunningApp(true);
    op.manifest().setUpdatedRuntimeProfilesEnabled(op.enabledRuntimeProfileNames());

    op.manifest().setTargetOs(op.platform().getOsPart());
    op.manifest().setHostId(op.platform().getHostId());
    op.manifest().setLicenseMode(getLicenseMode(op));

    //always use relative paths for backups - must be called after setTargetOs()
    op.manifest().setUseAbsoluteElementPaths(false);

    BTimeZone osTz = op.platform().getOsTimeZone();
    if (!osTz.isNull())
    {
      op.manifest().setNewOsTimeZone(osTz);
    }

    op.manifest().addDir(op.manifest().getEntryPath(new FilePath("~stations")), true);
    op.manifest().addDir(op.manifest().getEntryPath(new FilePath("~lexicon")), true);

    if (op.offline())
    {
      op.manifest().setDescription("Offline backup of host \"" + op.platform().getHostName() + "\"");
    }
    else
    {
      op.manifest().setDescription("Online backup of station \"" + Sys.getStation().getStationName() + "\" on \"" + Sys.getHostName() + "\"");
    }

    // platform.bog
    try
    {
      AccessController.doPrivileged((PrivilegedExceptionAction<Void>)() ->
      {
        BIFile result = op.fileSpace().findFile(
          SystemFilePaths.getPlatformBogPath(op.platform().getIsNiagara4(),
          op.platform().getIsNiagaraHomeReadonly()));

        if (result != null)
        {
          op.manifest().setPlatformBog(result, false);
        }
        return null;
      });
    }
    catch (PrivilegedActionException pae)
    {
      throw pae.getException();
    }

    // TCP/IP
    if (op.offline())
    {
      op.manifest().setDaemonTcpIpSettings(op.session());
    }
    else
    {
      op.manifest().setStationTcpIpSettings();
    }

    // NCCB-10536: stuff platform users in the distribution manifest
    if (op.offline())
    {
      //Use the operation daemon session to obtain settings
      op.manifest().setPlatformUsers(op.session());
    }
    else
    {
      //station backup has to be done through the local daemon session
      BDaemonSession daemon = AccessController.doPrivileged(((PrivilegedAction<BDaemonSession>)LocalSessionUtil::getLocalSession));

      op.manifest().setPlatformUsers(daemon);
    }

    // Dependencies
    //   The backup states an exact dependency on each of the platform's
    //   current parts.  That means that when the dist is installed to restore
    //   from the backup, the platform must either match those parts already,
    //   or the installer must be able to find the correct versions in
    //   files and install them

    op.manifest().addDependency(getExactDependency(op.platform().getOsPart()));
    op.manifest().addDependency(getExactDependency(op.platform().getArchPart()));

    //NCB-1031, do not allow backup dist if nreVersion is missing, invalid
    BNrePart nre = op.platform().getNrePart();
    if (!nre.isNull())
    {
      op.manifest().addDependency(getExactDependency(nre));
    }
    else
    {
      //STOP! We can not backup a platform where we do not know what the NRE may be!
      throw new LocalizableRuntimeException("backup",
                                            "backup.error.badNRE",
                                            new Object[] { nre.getPartName() });
    }

    //NCB-1031, do not include a malformed jreVersion.xml into the dist as a dependency
    BVmPart vm = op.platform().getVmPart();
    if ((!vm.isNull()) && (!vm.getUnspecified()))
    {
      op.manifest().addDependency(getExactDependency(op.platform().getVmPart()));
    }

    for (SlotCursor<Property> c = op.platform().getOtherParts().getProperties(); c.next(BPart.class);)
    {
      // Issue 15134 - system board dependencies should be "max" and not "exact"
      // but niagarad doesn't give us enough information to discriminate between
      // <part> for system board or any other type of generic part.   It would
      // be a mistake to assume "max" dependency for a generic part that isn't
      // a system board.

      // Using a versionless dependency for system board and other parts is a
      // decent compromise - the OS distribution will specify the correct
      // max dependency on system board version
      BPart part = (BPart)c.get();
      op.manifest().addDependency(new BDependency(part.getPartName(),
                                                  BVersion.makeZero(),
                                                  part.getType().getTypeSpec()));
    }

    // Brand is a dependency if it's not null or disabled.  That way,
    // this dist won't be installable to a host w/a different brand
    if (op.brandId() != null)
    {
      op.manifest().addDependency(getExactDependency(op.platform().getBrandPart()));
    }

    // now spin through all of our modules and create a dependency item
    // for each
    if (op.log() != null) op.log().start("Analyzing modules");
    BModuleList modules = op.platform().getModuleList();
    op.checkCanceled();
    modules.init();
    op.checkCanceled();
    for (SlotCursor<Property> c = modules.getModules().getProperties(); c.next(BModuleInstallable.class);)
    {
      BModuleInstallable mi = (BModuleInstallable)c.get();
      op.manifest().addDependency(getExactDependency(mi.getModulePart()));
    }
    if (op.log() != null) op.log().endSuccess();
  }

  private BDependency getExactDependency(BPart part)
  {
    return new BDependency(part.getPartName(),
                           part.getVersion(),
                           BVersionRelation.exact,
                           part.getType().getTypeSpec());
  }


  /**
   * Generate the list of files which should be backed up using
   * this service's backup configuration against the station home
   * directory.
   */
  private void findStationBackupFiles(BIFile           stationHome,
                                      String           stationName,
                                      Array<FileTuple> acc,
                                      BackupOp         op)
    throws Exception
  {
    String filesToParse;
    BOrdList dirsToSplit;
    if (op != null && op.offline())
    {
      filesToParse = getOfflineExcludeFiles();
      dirsToSplit = getOfflineExcludeDirectories();
    }
    else
    {
      filesToParse = getExcludeFiles();
      dirsToSplit = getExcludeDirectories();
    }
    PatternFilter[] excludeFiles = PatternFilter.parseList(filesToParse, ";");
    Set<FilePath> excludePaths = new TreeSet<>();
    for (BOrd excludeDir : dirsToSplit)
    {
      excludePaths.add(getStationFilePath(excludeDir));
    }
    listBackupFiles(stationHome, stationName, excludeFiles, excludePaths, acc);
  }

  /**
   * Find the appropriate files for each station and add them to the BackupOp's
   * files list
   */
  private void findRemoteStationFiles(BackupOp op)
    throws Exception
  {
    // do stations individually
    BDirectory stationsDir = (BDirectory)op.fileSpace().findFile(new FilePath("~stations"));

    if (stationsDir != null)
    {
      for (BIFile kid : stationsDir.listFiles())
      {
        if (kid instanceof BDirectory)
        {
          BDirectory stationDir = (BDirectory)kid;
          BBogFile configBog = (BBogFile)stationDir.getNavChild("config.bog");
          if (configBog == null) continue;

          // Find the backup service in the station's database, if there is one.  We need
          // it if it specifies exclusion ords/exts that are different from the defaults
          BBackupService backupService = null;
          try
          {
            BComponent root = (BComponent)new ValueDocDecoder(configBog).decodeDocument();
            backupService = findUnmountedBackupService(root);
          }
          catch (Exception e)
          {
            // invalid config.bog, use default backup service settings
          }
          if (backupService == null)
          {
            // use the default backup settings if there's no backup service
            // on the station
            backupService = new BBackupService();
          }

          backupService.findStationBackupFiles(stationDir, stationDir.getFileName(), op.files(), op);
        }
      }
    }
  }

  /**
   * Find the appropriate platform files and add them to the BackupOp's
   * files list
   */
  private void findPlatformBackupFiles(BackupOp op)
    throws Exception
  {
    String filesToParse;
    if (op.offline())
    {
      filesToParse = getOfflineExcludeFiles();
    }
    else
    {
      filesToParse = getExcludeFiles();
    }

    filesToParse +=";platform.bog;.data";

    PatternFilter[] excludeFiles = PatternFilter.parseList(filesToParse, ";");
    Set<FilePath> excludePaths = new TreeSet<>();

    // NCCB-57645: Note that while we are excluding ~security, and therefore the files in that
    // directory, in the case of a read-only Niagara Home, we will be explicitly including certain
    // subdirectories (namely ~security/licenses, ~security/certificates, and
    // ~security/subscription). This path exclusion will not prevent those inclusions.
    excludePaths.add(new FilePath("~security"));
    excludePaths.add(new FilePath("~stations"));
  
    // Avoid copying license file / certificates if the platform uses readonly settings
    boolean licenseReadonly = op.offline() ? op.session().getHostProperties().getIsLicenseReadonly() :
      AccessController.doPrivileged(((PrivilegedExceptionAction<Boolean>)() -> PlatformUtil.getPlatformProvider().isLicenseReadonly()));

    boolean niagaraHomeReadonly = op.offline() ? op.session().getHostProperties().getIsNiagaraHomeReadonly() :
      AccessController.doPrivileged(((PrivilegedExceptionAction<Boolean>)() -> PlatformUtil.getPlatformProvider().isNiagaraHomeReadonly()));

    for (BOrd platformOrd : op.platformBackupOrds(licenseReadonly, niagaraHomeReadonly))
    {
      BIFile file = findFile(op, getPlatformFilePath(platformOrd, op));
      listBackupFiles(file, null, excludeFiles, excludePaths, op.files());

      if (file instanceof BDirectory)
      {
        if (file.getFileName().equals("licenses") ||
          file.getFileName().equals("certificates") ||
          file.getFileName().equals("subscription"))
        {
          //Never replace licenses or certificates if one is already available on the platform
          op.manifest().addDir(
            op.manifest().getEntryPath(file.getFilePath()),
            DistributionManifest.REPLACE_NEVER,
            false);
        }
        else if (file.getFileName().equals("platform"))
        {
          //Replace or add items under platform directory if required but never delete them
          op.manifest().addDir(
            op.manifest().getEntryPath(file.getFilePath()),
            DistributionManifest.REPLACE_DEFAULT,
            false);
        }
        else
        {
          op.manifest().addDir(op.manifest().getEntryPath(file.getFilePath()),
            DistributionManifest.REPLACE_DEFAULT,
            true);
        }
      }
    }

    // NCCB-57645: In a read-only Niagara Home case, some subdirectories of ~security may exist that
    // otherwise would be in !security (namely ~security/licenses, ~security/certificates, and
    // ~security/subscription). Those subdirectories were handled above, so we exclude them here.
    excludePaths.clear();
    excludePaths.add(new FilePath("~security/licenses"));
    excludePaths.add(new FilePath("~security/certificates"));
    excludePaths.add(new FilePath("~security/subscription"));

    for (BOrd securityOrd : DEFAULT_SECURITY_BACKUP)
    {
      BIFile file = findFile(op, getPlatformFilePath(securityOrd, op));
      if (file != null)
      {
        listBackupFiles(file, null, new PatternFilter[] { new PatternFilter(".km*") }, excludePaths, op.files());

        if (file.getFileName().contains("ssl.tks"))
        {
          op.manifest().addFile(op.manifest().getEntryPath(file.getFilePath()),
            DistributionManifest.REPLACE_NEVER);
        }
        else
        {
          op.manifest().addFile(op.manifest().getEntryPath(file.getFilePath()),
            DistributionManifest.REPLACE_ALWAYS);
        }
      }
    }
  }

  private BIFile findFile(BackupOp op, FilePath path)
    throws Exception
  {
    return AccessController.doPrivileged((PrivilegedExceptionAction<BIFile>) () -> op.fileSpace().findFile(path));
  }

  /**
   * Add file info to the given array of FileTuple
   */
  private void listBackupFiles(BIFile           file,
                               String           stationName,
                               PatternFilter[]  excludeFiles,
                               Set<FilePath>    excludePaths,
                               Array<FileTuple> acc)
    throws Exception
  {
    if (file == null) return;

    // if not a directory
    if (!file.isDirectory())
    {
      // check exclude list
      String name = file.getFileName();
      for (PatternFilter excludeFile : excludeFiles)
      {
        if (excludeFile.accept(name)) return;
      }

      // add it to the backup list
      acc.add(new FileTuple(stationName, file));
      return;
    }
    if (excludePaths.contains(file.getFilePath())) return;

    // this is a directory
    final BIDirectory fDir = (BIDirectory)file;

    // need to backup it, so recurse through it's files
    BIFile[] files = AccessController.doPrivileged((PrivilegedAction<BIFile[]>)fDir::listFiles);
    for (BIFile dirFile : files)
    {
      listBackupFiles(dirFile, stationName, excludeFiles, excludePaths, acc);
    }
  }

  /**
   * Find a backup service instance in an unmounted component
   */
  private BBackupService findUnmountedBackupService(BComponent component)
  {
    if (component instanceof BBackupService)
    {
      return (BBackupService)component;
    }
    SlotCursor<Property> c = component.getProperties();
    // search for direct BBackupService children first (breadth-first)
    if (c.next(BBackupService.class))
    {
      return (BBackupService)c.get();
    }
    // now search each component child
    for (c = component.getProperties(); c.next(BComponent.class);)
    {
      BBackupService result = findUnmountedBackupService((BComponent)c.get());
      if (result != null)
      {
        return result;
      }
    }
    return null;
  }

  private void addFilesToDist(BackupOp op)
    throws Exception
  {
    // zip backup files

    // make a local key for encoding these files that is compatible with
    // the platform undergoing the backup operation
    final PBEEncodingKey localFileEncodingKey;
    if (op.fileSpace() instanceof BFileSystem)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("Backup running with local file store, creating local file encoding key for protected files");
      }

      // interacting with the local file system, make new key from provided parameters
      // that uses the local environment's system passphrase
      localFileEncodingKey = op.encodingInfo.makePBEKey(AccessController.doPrivileged((PrivilegedExceptionAction<SecretChars>)() ->
                                                                                       PlatformUtil.getPlatformProvider().getSystemPassword()));
    }
    else
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("Backup running with remote file store, using remote file encoding key for protected files");
      }

      // the platform is remote, stream returned by the Niagara Daemon will already be in "export" mode
      localFileEncodingKey = null;
    }

    try
    {
      for (FileTuple file : op.files().trim())
      {
        op.checkCanceled();

        // open file input stream
        BIFile f = file.file;
        InputStream in = null;

        if (f.getFilePath().getBody().equals("~security/.kr"))
        {
          if (localFileEncodingKey != null)
          {
            if (log.isLoggable(Level.FINE))
            {
              log.fine("Exporting Key Ring Data");
            }

            // export the local key ring data instead of reading it
            in = new ByteArrayInputStream(AccessController.doPrivileged((PrivilegedExceptionAction<byte[]>)()->
                                                                        SecurityInitializer.getInstance().getSecurityInfoProvider().getKeyRing().exportKeyData(localFileEncodingKey)))
                                                                        {
                                                                          public void close()
                                                                          {
                                                                            Arrays.fill(buf, (byte)0x0);
                                                                          }
                                                                        };
          }
          // if localFileEncodingKey is null, the input stream is already an export created by niagarad
        }
        else
        {
          if (log.isLoggable(Level.FINE))
          {
            log.fine("Backing up file '" + f.getFilePath().getBody() + "'...");
          }
        }

        // Determine what type of input stream should be used to decode this file if not exporting key ring
        if (in == null)
        {
          in = AccessController.doPrivileged((PrivilegedExceptionAction<InputStream>)f::getInputStream);

          if (localFileEncodingKey != null)
          {
            // If we're not getting the file from niagarad we have to process protected files ourselves
            if (matchesAnyPattern(f.getFilePath(), PROTECTED_STATION_PATHS))
            {
              if (log.isLoggable(Level.FINE))
              {
                log.fine("Backup file '" + f.getFilePath().getBody() + "' matched PROTECTED_STATION_PATH pattern");
              }
              //Use PBEDecryptingInputStream to entry entry when written using system passphrase
              in = new PBEEncryptingInputStream(in, localFileEncodingKey);
            }
            else if (matchesAnyPattern(f.getFilePath(), KEYRING_ENCRYPTED_STATION_PATHS))
            {
              if (log.isLoggable(Level.FINE))
              {
                log.fine("Backup file '" + f.getFilePath().getBody() + "' matched KEYRING_ENCRYPTED_STATION_PATH pattern");
              }
              //Use AESStreamEncryption to entry entry when written using key ring
              in = AESStreamEncryption.keyRingToPBE(in, SecurityInitializer.getInstance().getSecurityInfoProvider(), localFileEncodingKey);
            }
            else if ((f instanceof BBogFile) &&
                     AccessController.doPrivileged((PrivilegedExceptionAction<Boolean>)((BBogFile)f)::usesKeyRingEncryption))
            {
              if (log.isLoggable(Level.FINE))
              {
                log.fine("Backup file '" + f.getFilePath().getBody() + "' matched external EncryptionKeySource requirements");
              }
              final InputStream finalIn = in;
              //Transcode the bog file so that its passwords are protected with the system pass phrase and not a key from the key ring
              in = AccessController.doPrivileged((PrivilegedExceptionAction<InputStream>)() -> new BogTranscoderInputStream(SecurityInitializer.getInstance().getSecurityInfoProvider().getKeyRing(),
                                                                                                                            finalIn,
                                                                                                                            !log.isLoggable(Level.FINEST),
                                                                                                                            localFileEncodingKey,
                                                                                                                            EncryptionKeySource.external,
                                                                                                                            f.getFilePath().getBody()));
            }
            //else, file did not match any encoding pattern, must be raw stream
          }
          //else, if localFileEncodingKey is null, the input stream is already an export created by niagarad
        }

        // determine where the file will be written in distribution file
        FilePath path = f.getFilePath();
        String entryPath = op.manifest().getEntryPath(path, file.stationName, true);

        // print log before input stream validating to better associate any failure with this message
        if (op.log() != null)
        {
          op.log().start("Writing : " + entryPath);
        }

        // validate that input stream was created
        if (in == null)
        {
          if (f instanceof BBogFile)
          {
            // presume that any failure to obtain bog input stream is probably a key encoding error
            throw new LocalizableRuntimeException("backup",
                                                  "backup.error.noInputStreamBog",
                                                  new Object[] { entryPath });
          }
          else
          {
            throw new LocalizableRuntimeException("backup",
                                                  "backup.error.noInputStream",
                                                  new Object[] { entryPath });
          }
        }

        // create zip entry
        ZipEntry entry = new ZipEntry(entryPath);
        entry.setTime(AccessController.doPrivileged((PrivilegedExceptionAction<BAbsTime>)f::getLastModified).getMillis());

        // write file into zip stream
        op.zip.putNextEntry(entry);
        BajaFileUtil.pipe(in, op.zip);
        op.zip.closeEntry();
        in.close();
        op.notifyFileCreated();

        if (op.log() != null)
        {
          op.log().endSuccess();
        }
      }
    }
    finally
    {
      if (localFileEncodingKey != null)
      {
        localFileEncodingKey.close();
      }
    }
  }

  private FilePath getPlatformFilePath(BOrd ord, BackupOp op)
    throws Exception
  {
    OrdQuery[] q = ord.parse();
    for (int i = q.length - 1; i >= 0; i--)
    {
      if (q[i] instanceof FilePath)
      {
        FilePath p = (FilePath)q[i];
        if (p.isStationHomeAbsolute() || p.isProtectedStationHomeAbsolute())
        {
          throw new IllegalStateException(p.getBody() + " cannot be station home absolute");
        }
        if (op == null)
        {
          return p;
        }
        else
        {
          return op.manifest().getNormalFilePath(p, true);
        }
      }
    }
    return null;
  }
  private FilePath getStationFilePath(BOrd ord)
    throws Exception
  {
    OrdQuery[] q = ord.parse();
    for (int i = q.length - 1; i >= 0; i--)
    {
      if (q[i] instanceof FilePath)
      {
        FilePath p = (FilePath)q[i];
        if (!p.isStationHomeAbsolute() && !p.isProtectedStationHomeAbsolute())
        {
          throw new IllegalStateException(p.getBody() + " cannot be station home absolute");
        }
        return p;
      }
    }
    return null;
  }

  private String getLicenseMode(BackupOp op)
  {
    BHostIdStatus hostIdStatus = op.platform().getDaemonSession().getHostProperties().getHostIdSettings().getHostIdStatus();
    return (!hostIdStatus.isPerpetual()) ? "Subscription" : hostIdStatus.toString();
  }

  public static ICanceler makeCanceler(BJob job)
  {
    return (job == null) ? NULL_CANCELER : new JobCanceler(job);
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("navOnly/backupService.png");

////////////////////////////////////////////////////////////////
// Debug
////////////////////////////////////////////////////////////////

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);

    BIFile[] files = listStationBackupFiles();
    out.w("<hr><b>List Station Backup Files [" + files.length + "]</b>");
    out.w("<pre>");
    for (BIFile file : files)
    {
      out.w("  ").safe(file.getOrdInSpace()).nl();
    }
    out.w("</pre>");
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static Logger log = Logger.getLogger("backup");
  private DateFormat logDateFormat = new SimpleDateFormat("HH:mm:ss dd-MMM-yy z");

  // General platform directories to be included in every backup.
  private static final BOrd[] COMMON_PLATFORM_BACKUP = new BOrd[] {
    BOrd.make("file:~daemon"),
    BOrd.make("file:~etc"),
    BOrd.make("file:~logging")
  };

  //NCCB-57205: Note that in Niagara 4.13 the "file:!security/policy" policy directory referenced below
  //            has moved to "file:!bin/policy" but also that policy files are no longer necessary to
  //            capture in the backup distribution file in that release as it will be captured in the
  //            nre-core-* dependency for the platform. The reference below remains in place so that when
  //            targeting older platforms, where the directory does exit, will continue to capture the files
  //            in the backup. The operation is a no-op when the folder is absent.

  // General directories to be included whenever the target platform's Niagara Home is not read-only.
  private static final BOrd[] WRITABLE_HOME_PLATFORM_BACKUP_ADDER = new BOrd[] {
    BOrd.make("file:!defaults"),
    BOrd.make("file:!fips"),
    BOrd.make("file:!platform"),
    BOrd.make("file:!lexicon"),
    BOrd.make("file:!lib"),
    BOrd.make("file:!etc"),
    BOrd.make("file:!security/policy")
  };

  // Licencing directories to be included when licencing is not read-only and when Niagara Home is
  // not read-only for the target platform.
  private static final BOrd[] WRITABLE_LICENSE_WITH_WRITABLE_HOME_PLATFORM_BACKUP_ADDER = new BOrd[] {
    BOrd.make(SystemFilePaths.getCertificatesDirPath(true, true, false)),
    BOrd.make(SystemFilePaths.getLicensesDirPath(true, true, false)),
    BOrd.make(SystemFilePaths.getSubscriptionDirPath(false)),
  };

  // Licencing directories to be included when licencing is not read-only and when Niagara Home is
  // read-only for the target platform.
  private static final BOrd[] WRITABLE_LICENSE_WITH_READONLY_HOME_PLATFORM_BACKUP_ADDER = new BOrd[] {
    BOrd.make(SystemFilePaths.getCertificatesDirPath(true, true, true)),
    BOrd.make(SystemFilePaths.getLicensesDirPath(true, true, true)),
    BOrd.make(SystemFilePaths.getSubscriptionDirPath(true)),
  };

  // Licencing directories to be included when licencing is read-only and when Niagara Home is not
  // read-only for the target platform. Note that read-only licensing does not apply to subscriptions.
  private static final BOrd[] READONLY_LICENSE_WITH_WRITABLE_HOME_PLATFORM_BACKUP_ADDER = new BOrd[] {
    BOrd.make(SystemFilePaths.getSubscriptionDirPath(false)),
  };

  // Licencing directories to be included when licencing is read-only and when Niagara Home is
  // read-only for the target platform. Note that read-only licensing does not apply to subscriptions.
  private static final BOrd[] READONLY_LICENSE_WITH_READONLY_HOME_PLATFORM_BACKUP_ADDER = new BOrd[] {
    BOrd.make(SystemFilePaths.getSubscriptionDirPath(true)),
  };

  private static final BOrdList DEFAULT_SECURITY_BACKUP = BOrdList.make(BOrd.make("file:~security"));

  protected String getFoxChannelId() { return "backup"; }

//==============================================================
//  Inner classes follow
//==============================================================

////////////////////////////////////////////////////////////////
// FileTuple class
////////////////////////////////////////////////////////////////

  private static class FileTuple
  {
    public FileTuple(String stationName, BIFile file)
    {
      this.file        = file;
      this.stationName = stationName;
    }
    public BIFile file;
    public String stationName;
  }

////////////////////////////////////////////////////////////////
// BackupOp class
////////////////////////////////////////////////////////////////

  private class BackupOp
    implements AutoCloseable
  {
    @SuppressWarnings("unused")
    public BackupOp(BJob job, BDaemonPlatform platform)
    {
      this(job, makeCanceler(job), platform);
    }

    public BackupOp(BJob job, ICanceler canceler, BDaemonPlatform platform)
    {
      this(canceler, platform);
      this.job = job;
    }

    public BackupOp(JobLog log, ICanceler canceler, BDaemonPlatform platform)
    {
      this(canceler, platform);
      this.log = log;
    }

    private BackupOp(ICanceler canceler, BDaemonPlatform platform)
    {
      this.canceler = (canceler == null) ? NULL_CANCELER : canceler;
      this.platform = platform;
      if (platform == null)
      {
        throw new IllegalStateException("platform is unavailable");
      }
      if (platform instanceof BRemoteDaemonPlatform)
      {
        //Obtain PBE information from the remote client,
        //it might be different from parameters used in our environment
        this.encodingInfo = DaemonClientEncodingInfo.make(platform.getDaemonSession());
      }
      else
      {
        //The platform and service are running on the same host, use our local PBE (same System Passphrase) information
        this.encodingInfo = LocalEncodingInfo.make();
      }
      this.manifest = DistributionManifest.make();
      this.manifest.setPBEEncodingInfo(this.encodingInfo);
    }

    public String brandId()
      throws Exception
    {
      BBrandPart part = platform.getBrandPart();
      return (part.isNull()) ? null : part.getPartName();
    }
    public String enabledRuntimeProfileNames()
    {
      return platform().getEnabledRuntimeProfileNames();
    }
    public BDaemonSession session()
    {
      return platform().getDaemonSession();
    }
    public BFileSpace fileSpace()
    {
      if (fileSpace == null)
      {
        if (offline())
        {
          fileSpace = new BCachedDaemonFileSpace(session(), true, false, false, null, null, (DaemonClientEncodingInfo)encodingInfo);
        }
        else
        {
          fileSpace = BFileSystem.INSTANCE;
        }
      }
      return fileSpace;
    }
    public BJob job()
    {
      return job;
    }
    public JobLog log()
    {
      if (log == null)
      {
        if (job != null)
        {
          log = job.log();
        }
      }
      return log;
    }
    public BDaemonPlatform platform()
    {
      return platform;
    }
    public void checkCanceled()
    {
      if (canceler.isCanceled())
      {
        throw new JobCancelException();
      }
      else if (job != null)
      {
        job.heartbeat();
      }
    }

    public BOrdList platformBackupOrds(boolean licenseReadonly, boolean niagaraHomeReadonly)
    {
      Property p = getProperty("platformBackupFiles");
      return (p == null) ? defaultPlatformBackupOrds(licenseReadonly, niagaraHomeReadonly) : (BOrdList)get(p);
    }

    public int totalFilesCreated()
    {
      return totalFilesCreated;
    }

    public void notifyFileCreated()
    {
      totalFilesCreated++;
      if (job != null)
      {
        job.setProgress(10 + (90 * totalFilesCreated / files.size()));
      }
    }

    public DistributionManifest manifest()
    {
      return manifest;
    }

    public Array<FileTuple>  files()
    {
      return files;
    }

    public boolean offline()
    {
      return (platform() instanceof BRemoteDaemonPlatform);
    }

    @Override
    public void close()
    {
      //Clean up the remote PBE transaction
      if (encodingInfo instanceof DaemonClientEncodingInfo)
      {
        ((DaemonClientEncodingInfo)encodingInfo).close();
      }
    }

    private final DistributionManifest     manifest;
    private final PBEEncodingInfo          encodingInfo;
    private final BDaemonPlatform          platform;
    private final ICanceler                canceler;
    private final Array<FileTuple>         files                = new Array<>(FileTuple.class);
    private BJob                           job                  = null;
    private JobLog                         log                  = null;
    private BFileSpace                     fileSpace            = null;
    private int                            totalFilesCreated    = 0;
    public  ZipOutputStream                zip                  = null;
  }

  private static BOrdList defaultPlatformBackupOrds(boolean licenseReadonly, boolean niagaraHomeReadonly)
  {
    if (licenseReadonly && niagaraHomeReadonly)
    {
      return ReadonlyLicenseAndReadonlyHomePlatformBackup.ords;
    }
    if (licenseReadonly)
    {
      return ReadonlyLicenseAndWritableHomePlatformBackup.ords;
    }
    if (niagaraHomeReadonly)
    {
      return WritableLicenseAndReadonlyHomePlatformBackup.ords;
    }
    return WritableLicenseAndWritableHomePlatformBackup.ords;
  }

  private static class WritableLicenseAndWritableHomePlatformBackup
  {
    public static final BOrdList ords = BOrdList.make(Stream.of(
        Arrays.stream(WRITABLE_HOME_PLATFORM_BACKUP_ADDER),
        Arrays.stream(COMMON_PLATFORM_BACKUP),
        Arrays.stream(WRITABLE_LICENSE_WITH_WRITABLE_HOME_PLATFORM_BACKUP_ADDER)
      )
      .flatMap(s -> s)
      .toArray(BOrd[]::new));
  }

  private static class WritableLicenseAndReadonlyHomePlatformBackup
  {
    public static final BOrdList ords = BOrdList.make(Stream.of(
        Arrays.stream(COMMON_PLATFORM_BACKUP),
        Arrays.stream(WRITABLE_LICENSE_WITH_READONLY_HOME_PLATFORM_BACKUP_ADDER)
      )
      .flatMap(s -> s)
      .toArray(BOrd[]::new));
  }

  private static class ReadonlyLicenseAndWritableHomePlatformBackup
  {
    public static final BOrdList ords = BOrdList.make(Stream.of(
        Arrays.stream(WRITABLE_HOME_PLATFORM_BACKUP_ADDER),
        Arrays.stream(COMMON_PLATFORM_BACKUP),
        Arrays.stream(READONLY_LICENSE_WITH_WRITABLE_HOME_PLATFORM_BACKUP_ADDER)
      )
      .flatMap(s -> s)
      .toArray(BOrd[]::new));
  }

  private static class ReadonlyLicenseAndReadonlyHomePlatformBackup
  {
    public static final BOrdList ords = BOrdList.make(Stream.of(
        Arrays.stream(COMMON_PLATFORM_BACKUP),
        Arrays.stream(READONLY_LICENSE_WITH_READONLY_HOME_PLATFORM_BACKUP_ADDER)
      )
      .flatMap(s -> s)
      .toArray(BOrd[]::new));
  }

////////////////////////////////////////////////////////////////
// RestoreOp class
////////////////////////////////////////////////////////////////

  private class RestoreOp
    implements Runnable
  {
    public RestoreOp(BIFile  backupFile,
                     boolean ignoreDependencies,
                     boolean deleteFile,
                     long    shutdownDelay)
      throws Exception
    {
      this.backupFile         = backupFile;
      this.ignoreDependencies = ignoreDependencies;
      this.deleteFile         = deleteFile;
      this.shutdownDelay      = shutdownDelay;

      if(Sys.getStation() != null)
        this.stationName        = Sys.getStation().getStationName();

      if (!(backupFile.getStore() instanceof BLocalFileStore))
      {
        throw new LocalizableRuntimeException("backup",
                                              "restore.error.backupNotLocal");
      }
      zipFile = new ZipFile(((BLocalFileStore)backupFile.getStore()).getLocalFile());

      manifest(); // force a check that the zip file is a valid backup file
      daemonPlatform(); // force a check for local daemon platform availability
      if (stationSurrogate() == null)
      {
        throw new LocalizableRuntimeException("backup",
                                              "restore.error.noSurrogate");
      }
      this.isRestartEnabled = stationSurrogate().isRestartEnabled();
    }

    @Override
    public void run()
    {
      try
      {
        Thread.sleep(shutdownDelay);
      }
      catch (InterruptedException ignored) {}
      doRestore(RestoreOp.this);
    }

    public DistributionManifest manifest()
      throws Exception
    {
      if (manifest == null)
      {
        ZipEntry manifestEntry = zipFile.getEntry("META-INF/dist.xml");
        if (manifestEntry == null) manifestEntry = zipFile.getEntry("meta-inf/dist.xml");
        if (manifestEntry == null)
        {
          throw new LocalizableRuntimeException("backup",
                                                "restore.error.noManifest");
        }
        manifest = DistributionManifest.make(zipFile.getInputStream(manifestEntry));

        if (!"backupdist".equals(manifest.getInstallableName()))
        {
          throw new LocalizableRuntimeException("backup",
                                                "restore.error.invalidName");
        }
      }
      return manifest;
    }

    public BDaemonPlatform daemonPlatform()
      throws Exception
    {
      BLocalDaemonPlatform result;
      try
      {
        result = AccessController.doPrivileged(((PrivilegedExceptionAction<BLocalDaemonPlatform>)BLocalDaemonPlatform::getInstance));
      }
      catch (PrivilegedActionException pae)
      {
        throw pae.getException();
      }
      if (result == null)
      {
        throw new LocalizableRuntimeException("backup",
                                              "restore.error.noDaemonSession");
      }
      result.checkSession();
      return result;
    }

    public BStationSurrogate stationSurrogate()
      throws Exception
    {
      if(newStationName == null) //in case the station name changes
        return BStationSurrogate.make(daemonPlatform().getDaemonSession(), stationName);
      else
        return BStationSurrogate.make(daemonPlatform().getDaemonSession(), newStationName);
    }

    ////////////////////////////////////////////////////////////////
    // Console Logging
    ////////////////////////////////////////////////////////////////

    // We can log only to System.out once the station is shut down.
    // The methods in this section write messages in a format consistent
    // with the javax.baja.log API

    //Determine if log message is loggable (copied and simplified from Logger.isLoggable())
    public boolean isLoggable(Level requestedLevel)
    {
      return logLevel != null && requestedLevel.intValue() >= logLevel.intValue() && logLevel != Level.OFF;
    }
    public void consoleError(String message, Exception exception)
    {
      consoleLog(Level.SEVERE, message, exception);
    }
    public void consoleWarning(String message)
    {
      consoleLog(Level.WARNING, message, null);
    }
    public void consoleMessage(String message)
    {
      consoleLog(Level.INFO, message, null);
    }
    public void consoleTrace(String message)
    {
      consoleLog(Level.FINE, message, null);
    }
    public void consoleLog(Level severity, String message, Exception exception)
    {
      //No-op on logs below configured level
      if (!isLoggable(severity)) return;

      if (severity == Level.SEVERE)
        System.out.print("SEVERE [");
      else if (severity == Level.WARNING)
        System.out.print("WARNING [");
      else if (severity == Level.INFO)
        System.out.print("INFO [");
      else if (severity == Level.CONFIG)
        System.out.print("CONFIG [");
      else if (severity == Level.FINE)
        System.out.print("FINE [");
      else if (severity == Level.FINER)
        System.out.print("FINER [");
      else if (severity == Level.FINEST)
        System.out.print("FINEST [");

      // timestamp
      System.out.print(logDateFormat.format(new Date()));
      System.out.print("][backup] ");

      // message
      System.out.println(message);

      // exception
      if (exception != null)
      {
        exception.printStackTrace();
      }
    }

    private DistributionManifest manifest = null;
    public  ZipFile              zipFile;
    public  boolean              ignoreDependencies;
    public  boolean              deleteFile;
    public  BIFile               backupFile;
    private final long           shutdownDelay;
    private String               stationName;
    public  boolean              isRestartEnabled;
    private String               newStationName;
    private Level                logLevel = Level.INFO;
  }

  private static boolean matchesAnyPattern(FilePath input, List<Pattern>patterns)
  {
    if (input == null) return false;

    for(Pattern p : patterns)
    {
      if (p.matcher(input.getBody()).matches()) return true;
    }
    return false;
  }

////////////////////////////////////////////////////////////////
// ICanceler Interface
////////////////////////////////////////////////////////////////

  public interface ICanceler
  {
    boolean isCanceled();
  }

  public static final ICanceler NULL_CANCELER = () -> false;

////////////////////////////////////////////////////////////////
// JobCanceler Class
////////////////////////////////////////////////////////////////

  public static class JobCanceler
    implements ICanceler
  {
    public JobCanceler(BJob job)
    {
      this.job = job;
    }
    @Override
    public boolean isCanceled()
    {
      return !job.isAlive();
    }
    private final BJob job;
  }

////////////////////////////////////////////////////////////////
// Local PBE Encoding Information Class
////////////////////////////////////////////////////////////////

  private static class LocalEncodingInfo
    extends PBEEncodingInfo
  {
    /**
     * Private constructor
     */
    private LocalEncodingInfo(String encodedValidator, String encodingSaltHex, int encodingIterationCount) throws IOException
    {
      super(encodedValidator, encodingSaltHex, encodingIterationCount);
    }

    /**
     * Factory
     */
    private static LocalEncodingInfo make()
    {
      //NCCB-27513: Backup sometimes fails due to Invalid csrf token for platform connection
      //
      //Obtain the local PBE key and use its encoding information.
      //This should only be used when the backup operation and the backup
      //target are running on the same platform. (System passphrases are known to be equal).
      //
      //The purpose of this is to avoid creating unnecessary Niagara Daemon traffic to obtain
      //the host's (which is the same as us) PBE information. See FileServlet.java FILE_TRANSACTION_INITIALIZE_PBE
      //handling for how this is achieved. The creation of this key occurs within a FileStore transaction, which
      //is a state change for the Niagara Daemon and must therefore contain a CSRF token. All of this completely
      //unnecessary since the transaction is then aborted at the end of the backup operation, having never
      //been used.
      //
      //An alternative to this, and requiring much more changes, would be to add a new Niagara Daemon message,
      //like InitializePBEMessage, but without the request to create/maintain a transaction. The message would just
      //return the default encoding algorithm of the platform, eliminating the state change that occurs in the
      //Niagara Daemon and thus eliminating the need of a CSRF token. I still consider the current solution,
      //to use the local PBE information, since it avoids the calls the Niagara Daemon entirely.
      //
      //NOTE: The only difference between the remote and local platform encoding information would be the
      //salt used to derive the key presently in use; however, since the BBackupService *DOES NOT* use the created transaction
      //to GET files (it uses BFileSystem, see fileSpace()), this is not a problem. We are only interested in using the
      //information to describe how the information is stored in the distribution file we are creating, *NOT* how
      //information is stored on the target platform.
      try (SecretChars secretChars = AccessController.doPrivileged(((PrivilegedExceptionAction<SecretChars>)() ->
                                                                     PlatformUtil.getPlatformProvider().getSystemPassword()));
           PBEEncodingKey pbeEncodingKey = new PBEEncodingKey(secretChars))
      {
        return new LocalEncodingInfo(pbeEncodingKey.getEncodedValidator(),
                                     pbeEncodingKey.getEncodingSaltHex(),
                                     pbeEncodingKey.getEncodingIterationCount());
      }
      catch (RuntimeException re)
      {
        throw re;
      }
      catch (Exception e)
      {
        throw new BajaRuntimeException(e);
      }
    }
  }

  private static final String WORKSTATION_HOST_MODEL = "Workstation";

  // The files in the following locations must be decrypted to plaintext on restore.  These paths must refer to the same files as niagarad's
  // FileServlet._IS_PROTECTED_PATH set, excluding any that aren't station files
  private static final List<Pattern> PROTECTED_STATION_PATHS = Stream.of(".*\\!platform/wifi/.+|.*\\!platform/ieee8021x/.+")
                                                                     .map(Pattern::compile)
                                                                     .collect(Collectors.toList());

  // The files in the following locations must be converted from passphrase encryption to keyring encryption on restore.
  // These paths must refer to the same files as niagarad's FileServlet._KEYRING_ENCRYPTED_PATHS set, excluding any that aren't station files
  private static final List<Pattern> KEYRING_ENCRYPTED_STATION_PATHS = Stream.of(".*\\^\\^ldap/(?!krb5\\.conf$).+")
                                                                             .map(Pattern::compile)
                                                                             .collect(Collectors.toList());

}
