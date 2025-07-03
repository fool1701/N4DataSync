/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.testng;

import java.util.Objects;
import javax.baja.category.BCategoryMask;
import javax.baja.file.BFileSystem;
import javax.baja.file.FilePath;
import javax.baja.firewall.BServerPort;
import javax.baja.security.BPassword;
import javax.baja.security.BPasswordAuthenticator;
import javax.baja.security.BPbkdf2HmacSha256PasswordEncoder;
import javax.baja.sys.BStation;
import javax.baja.user.BUser;
import javax.baja.user.BUserService;
import javax.baja.web.BWebService;
import com.tridium.fox.sys.BFoxService;
import com.tridium.template.file.BINtplFile;

/**
 * Build a station, based on one of the new station templates.
 * <p>
 *   This is designed to be used as a builder pattern, like so:
 *   <pre>
 *     BStation station = StationBuilder.builder()
 *       .withTemplateFile("!defaults/workbench/newStations/NewSupervisorStationLinux.ntpl")
 *       .withStationName("foo")
 *       .withAdminCredentials("user", "password")
 *       .build()
 *   </pre>
 * </p>
 *
 * @author Garrett L. Ward on 01 Aug 2017
 */
public final class StationBuilder
{
  private StationBuilder() {}

  /**
   * Get a StationBuilder
   * 
   * @return New instance of StationBuilder
   */
  public static StationBuilder builder() {
    return new StationBuilder();
  }

  /**
   * Build a BStation based on the configuration of this builder.
   * 
   * @return BStation component.
   */
  public BStation build()
  {
    Objects.requireNonNull(templatePath);
    Objects.requireNonNull(stationName);
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);

    BStation station;
    try (BINtplFile stationTemplate = (BINtplFile)BFileSystem.INSTANCE.findFile(new FilePath(templatePath)))
    {
      station = (BStation) stationTemplate.getBaseComponent().newCopy(COPY_EXACT);
    }

    station.setCategoryMask(BCategoryMask.make("1"), null);
    station.setStationName(stationName);

    BUserService userService = station.getServices().getChildren(BUserService.class)[0];
    BUser adminUser = userService.getUser(username);
    if (adminUser == null)
    {
      adminUser = new BUser();
      userService.add(username, adminUser);
    }
    adminUser.setAuthenticator(new BPasswordAuthenticator(BPassword.make(password, BPbkdf2HmacSha256PasswordEncoder.ENCODING_TYPE)));
    adminUser.setRoles("admin");
    

    // Configure Fox
    BFoxService foxService = station.getServices().getChildren(BFoxService.class)[0];
    foxService.setFoxEnabled(useFox);
    foxService.setFoxPort(new BServerPort(foxPort));
    foxService.setFoxsPort(new BServerPort(foxsPort));
    
    // Configure Https
    BWebService webService = station.getServices().getChildren(BWebService.class)[0];
    webService.setHttpEnabled(useHttp);
    webService.setHttpPort(new BServerPort(httpPort));
    webService.setHttpsPort(new BServerPort(httpsPort));

    return station;
  }

  /**
   * Set the path to the template to use for this station. Will be evaluated
   * as per {@link FilePath#FilePath(String)}, so shortcuts like "!" will work. 
   * 
   * @param templatePath Path of the template file
   *                     
   * @return This builder.
   */
  public StationBuilder withTemplateFile(String templatePath)
  {
    this.templatePath = templatePath;
    return this;
  }

  /**
   * Set the name of the station to be built.
   * 
   * @param stationName Station name
   *                    
   * @return This builder. 
   */
  public StationBuilder withStationName(String stationName)
  {
    this.stationName = stationName;
    return this;
  }

  /**
   * Set the default administrator username and password for the station to be
   * built
   * 
   * @param username Username
   * @param password Password
   *                 
   * @return This builder. 
   */
  public StationBuilder withAdminCredentials(String username, String password)
  {
    this.username = username;
    this.password = password;
    return this;
  }

  /**
   * Set the http port to be used by the built station. Note this does not
   * enable HTTP--see {@link #useHttp()}
   * 
   * @param httpPort HTTP port to use.
   *                 
   * @return This builder
   */
  public StationBuilder withHttpPort(int httpPort)
  {
    this.httpPort = httpPort;
    return this;
  }

  /**
   * Set the https port to be used by the built station. N
   *
   * @param httpsPort HTTPS port to use.
   *
   * @return This builder
   */
  public StationBuilder withHttpsPort(int httpsPort)
  {
    this.httpsPort = httpsPort;
    return this;
  }

  /**
   * Enable HTTP in the built station
   * 
   * @return This builder.
   */
  public StationBuilder useHttp()
  {
    this.useHttp = true;
    return this;
  }

  /**
   * Set the FOX port to be used by the built station. Note this does not enable 
   * FOX--see {@link #useFox()}
   * 
   * @param foxPort FOX port to use
   *                
   * @return This builder
   */
  public StationBuilder withFoxPort(int foxPort)
  {
    this.foxPort = foxPort;
    return this;
  }

  /**
   * Set the FOXS port to be used by the built station. 
   *
   * @param foxsPort FOXS port to use
   *
   * @return This builder
   */
  public StationBuilder withFoxsPort(int foxsPort)
  {
    this.foxsPort = foxsPort;
    return this;
  }

  /**
   * Enable FOX in the built station
   *
   * @return This builder.
   */
  public StationBuilder useFox()
  {
    this.useFox = true;
    return this;
  }

  private static final boolean COPY_EXACT = true;

  private String templatePath = null;
  private String stationName = null;
  private String username = null;
  private String password = null;
  private boolean useHttp = false;
  private int httpPort = 8080;
  private int httpsPort = 8443;
  private boolean useFox = false;
  private int foxPort = 1911;
  private int foxsPort = 4911;
}
