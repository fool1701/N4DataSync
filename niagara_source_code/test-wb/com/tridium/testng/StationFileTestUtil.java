/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.testng;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Objects;
import java.util.function.Consumer;

import javax.baja.category.BCategoryMask;
import javax.baja.file.BDirectory;
import javax.baja.file.BFileSystem;
import javax.baja.file.BIFile;
import javax.baja.file.FilePath;
import javax.baja.file.types.image.BImageFile;
import javax.baja.io.ValueDocEncoder;
import javax.baja.naming.SlotPath;
import javax.baja.security.BPassword;
import javax.baja.security.BPasswordAuthenticator;
import javax.baja.security.BPbkdf2HmacSha256PasswordEncoder;
import javax.baja.security.PasswordEncodingContext;
import javax.baja.space.Mark;
import javax.baja.sys.BObject;
import javax.baja.sys.BStation;
import javax.baja.sys.Context;
import javax.baja.test.BTest;
import javax.baja.user.BUserService;
import javax.baja.util.BServiceContainer;

import com.tridium.nre.security.PBEEncodingKey;
import com.tridium.nre.security.SecretChars;
import com.tridium.template.file.BINtplFile;
import com.tridium.template.file.PxFileRef;
import com.tridium.template.manifest.TemplateManifest;

/**
 * Contains useful methods for creating station files for testing under user home with a template.
 *
 * It does not have code for simulating the behavior of a station,
 * use {@link BTest#createTestStation()} or {@link BStationTestBase} for that.
 *
 * @author Matt Boon
 * @creation Octopber 19, 2015
 *
 * @since Niagara 4.1
 */
public class StationFileTestUtil
{
  /**
   * Creates a station with the given name using the default controller template under ~stations.
   * If a station with that name already exists, the existing one is first deleted.
   *
   * @param stationName Name of the test station. Must be a valid station name.
   * @param adminPassword Password for the station's builtin admin user.  May not be null.
   * @param configurator If present, {@link Consumer#accept(Object)} will be called on the station to make configuration
   *                     changes before it's saved
   * @param bogFilePassphrase If present, is used as the passphrase for the config.bog file, otherwise the file
   *                          will be encoded with {@link com.tridium.nre.security.EncryptionKeySource#none} and can't
   *                          contain any reversibly encoded password values
   *
   * @return Station directory
   */
  public static BDirectory createUserHomeStationFiles(String stationName,
                                                      BPassword adminPassword,
                                                      Consumer<BStation> configurator,
                                                      String bogFilePassphrase)
    throws IOException
  {
    // The implementation here is copied & adapted from wbutil-wb:com.tridium.workbench.tools.NewStationWizard.  If that
    // code changes significantly, changes here may become necessary.

    // It might be useful in the future to extend this method:
    // - support making a station under !stations (encoded with keyring key source) so the test can run it
    // - pass in a different template. Would need to figure out how to get template configuration parameters from the caller

    Objects.requireNonNull(stationName);
    Objects.requireNonNull(adminPassword);

    if (!SlotPath.isValidName(stationName) || stationName.length() > 32)
    {
      throw new IllegalArgumentException(String.format("Invalid station name '%s'", stationName));
    }

    if (configurator == null)
    {
      configurator = station -> {};
    }
    // NOTE: we don't deal with template configuration parameters because NewControllerStation doesn't have any
    try(BINtplFile templateFile = (BINtplFile) BFileSystem.INSTANCE.findFile(new FilePath("!defaults/workbench/newStations/NewControllerStation.ntpl")))
    {
      BStation station = (BStation)templateFile.getBaseComponent();
      station.setCategoryMask(BCategoryMask.make("1"), null);
      station.setStationName(stationName);
      configurator.accept(station);

      // save admin password
      BServiceContainer[] serviceContainers = station.getChildren(BServiceContainer.class);
      BUserService[] userServices = serviceContainers[0].getChildren(BUserService.class);
      userServices[0].get("admin").asComplex().set("authenticator", new BPasswordAuthenticator(BPassword.make(AccessController.doPrivileged((PrivilegedAction<String>)adminPassword::getValue), BPbkdf2HmacSha256PasswordEncoder.ENCODING_TYPE)));
      userServices[0].get("guest").asComplex().set("authenticator", new BPasswordAuthenticator(BPassword.make("", BPbkdf2HmacSha256PasswordEncoder.ENCODING_TYPE)));

      Context encodingContext;
      if (bogFilePassphrase == null)
      {
        encodingContext = PasswordEncodingContext.makeNone();
      }
      else
      {
        encodingContext = PasswordEncodingContext.updateForExternal(null, new PBEEncodingKey(
          SecretChars.fromString(bogFilePassphrase)));
      }

      FilePath dirPath = new FilePath(String.format("~stations/%s", stationName));
      BDirectory stationDirectory = (BDirectory)BFileSystem.INSTANCE.findFile(dirPath);

      if (stationDirectory != null)
      {
        stationDirectory.delete();
      }

      stationDirectory = BFileSystem.INSTANCE.makeDir(dirPath);

      BIFile stationFile = BFileSystem.INSTANCE.makeFile(dirPath.merge("config.bog"));
      ValueDocEncoder encoder = new ValueDocEncoder(new BufferedOutputStream(stationFile.getOutputStream()), encodingContext);
      encoder.setZipped(true);
      encoder.encodeDocument(station);
      encoder.close();

      TemplateManifest tm =  templateFile.getTemplateManifest();
      FilePath sharedPath = dirPath.merge("shared");

      // copy px files
      for(PxFileRef pxrf : templateFile.getPxFiles())
      {
        copyFile(pxrf.getPxFile(), TemplateManifest.PX, sharedPath, tm);
      }

      // copy images
      for(BImageFile imag : templateFile.getPxImageFiles())
      {
        copyFile(imag, TemplateManifest.IMAGE, sharedPath, tm);
      }

      return stationDirectory;
    }
  }

  // Copied from wbutil-wb:com.tridium.workbench.tools.NewStationWizard
  public static void copyFile(BIFile f, String fileType, FilePath sharedPath, TemplateManifest tm)
    throws IOException
  {
    TemplateManifest.Resource res = tm.getResource(f.getFileName(), fileType);
    if(!res.sourceOrd.startsWith("file:^")) return;
    FilePath imDirPath = sharedPath.merge(res.sourceOrd.substring(6)).getParent();
    BDirectory imDir = BFileSystem.INSTANCE.makeDir(imDirPath);
    Mark mark = new Mark((BObject)f, f.getFileName());
    try
    {
      mark.copyTo(imDir, null);
    }
    catch (IOException rethrow)
    {
      throw rethrow;
    }
    catch (Exception e)
    {
      throw new IOException(e);
    }
  }
}
