/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.testng;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.baja.file.BFileSystem;
import javax.baja.io.ValueDocEncoder;
import javax.baja.naming.BLocalHost;
import javax.baja.nre.security.ClientTlsParameters;
import javax.baja.nre.security.ExemptionApprover;
import javax.baja.nre.security.ExemptionHandler;
import javax.baja.security.crypto.CertManagerFactory;
import javax.baja.sys.BComponent;
import javax.baja.sys.BStation;
import javax.baja.web.BWebService;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import com.tridium.crypto.core.cert.CertValidationResult;
import com.tridium.file.types.bog.BBogFile;
import com.tridium.fox.sys.BFoxService;
import com.tridium.fox.sys.BFoxSession;
import com.tridium.fox.sys.broker.BBrokerChannel;
import com.tridium.sys.station.Station;
import com.tridium.util.FilePathUtil;

/**
 * Run a Niagara station as a separate process.
 * <p>
 *   This allows full integration testing of a Niagara station, by spawning a
 *   completely independent VM to run the station in. A separate niagara user home
 *   is used to avoid cluttering the development environment's NUH.
 * </p>
 * <p>
 *   This should *not* be used in normal CI tests--the spinup and teardown time for
 *   a separate process isn't trivial, and our CI plan is slow enough as it is.
 * </p>
 * <p>
 *   Example usage:
 *   <pre>
 *     BStation station = ...
 *     StationRunner runner = StationRunner.make()
 *       .withNiagaraUserHome(Files.createTempDirectory("station"))
 *       .withStation(station)
 *       .addExemptions()
 *       .start();
 *       ...
 *       BStation remoteStation = runner.getRemoteStation()
 *       ...
 *       runner.stop();
 *   </pre>
 * </p>
 * <p>
 *   Fairly heavily inspired by Gradle's GradleRunner. Blame them.
 * </p>
 *
 * @author Garrett L. Ward on 01 Aug 2017
 */
public final class StationRunner
{
  private StationRunner() {}

  /**
   * Make a new StationRunner
   *
   * @return A new StationRunner.
   */
  public static StationRunner make()
  {
    return new StationRunner();
  }

////////////////////////////////////////////////////////////////
// Builder
////////////////////////////////////////////////////////////////

  /**
   * Set the Niagara User Home to be used by the spawned station.
   * <p>
   *   See {@link Files#createTempDirectory(String, FileAttribute[])} and friends
   *   for an easy way to create a temporary niagara_user_home.
   * </p>
   * @param niagaraUserHome Path to niagara user home
   * @return This runner.
   */
  public StationRunner withNiagaraUserHome(Path niagaraUserHome)
  {
    this.niagaraUserHome = niagaraUserHome;
    return this;
  }

  /**
   * Convenience for {@code withNiagaraUserHome(File.toPath())}
   *
   * @param niagaraUserHome Path to niagara user home
   * @return This runner
   */
  public StationRunner withNiagaraUserHome(File niagaraUserHome)
  {
    this.niagaraUserHome = niagaraUserHome.toPath();
    return this;
  }

  /**
   * Specify a bog file to run. Not fully baked yet.
   *
   * @param bogFile Path to bogfile
   * @return This runner
   */
  public StationRunner withBogFile(Path bogFile)
  {
    this.bogFile = bogFile;
    return this;
  }

  /**
   * Specify a bog file to run. Not fully baked yet.
   *
   * @param bogFile Path to bogfile
   * @return This runner
   */
  public StationRunner withBogFile(File bogFile)
  {
    this.bogFile = bogFile.toPath();
    return this;
  }

  /**
   * Specify a BStation to run. The station will be serialized to disk
   * prior to running.
   *
   * @param station Station component
   * @return This runner
   */
  public StationRunner withStation(BStation station)
  {
    this.station = station;
    return this;
  }

  /**
   * Start the station
   *
   * @return This runner
   * @throws Exception If station startup fails or this runner is misconfigured.
   */
  public StationRunner start()
    throws Exception
  {
    Objects.requireNonNull(niagaraUserHome);

    if (bogFile != null && station != null)
    {
      throw new IllegalStateException("Cannot specify both station bog and station component");
    }

    if (bogFile != null)
    {
      // Read in the bog file
      BBogFile bog = null;
      try
      {
        bog = (BBogFile)BFileSystem.INSTANCE.findFile(FilePathUtil.makeFromPathAbsolute(bogFile));
        BStation bogStation = (BStation)bog.getBogSpace().getRootComponent();
        getPortsFromStation(bogStation);
      }
      finally
      {
        if (bog != null)
        {
          bog.close();
        }
      }
    }
    else
    {
      getPortsFromStation(station);
    }

    String stationName = null;
    if (station != null)
    {
      // Serialize station
      stationName = station.getStationName();
      Files.createDirectory(niagaraUserHome.resolve("stations"));
      Path stationDir = Files.createDirectory(niagaraUserHome.resolve("stations/" + stationName));
      Path stationBog = stationDir.resolve("config.bog");

      BBogFile stationFile = (BBogFile)BFileSystem.INSTANCE.makeFile(FilePathUtil.makeFromPathAbsolute(stationBog));
      ValueDocEncoder encoder = new ValueDocEncoder(new BufferedOutputStream(stationFile.getOutputStream()));
      encoder.encodeDocument(station);
      encoder.close();
    }
    else
    {
      throw new UnsupportedOperationException("Raw config.bog support not fully implemented yet");
    }

    // Now for the fun part!
    ProcessBuilder processBuilder = new ProcessBuilder();
    Map<String, String> environment = processBuilder.environment();
    environment.put("niagara_user_home", niagaraUserHome.toAbsolutePath().toString());
    if (environment.containsKey("NIAGARA_USER_HOME"))
    {
      environment.put("NIAGARA_USER_HOME", niagaraUserHome.toAbsolutePath().toString());
    }
    // We want simple KM in all cases, so it doesn't mess with /etc or the windows registry.
    environment.put("NIAGARA_USE_SIMPLE_KM", "1");
    environment.put("NIAGARA_USE_SIMPLE_SP", "1");

    // lord help us all
    processBuilder.command("station", stationName);
    processBuilder.redirectErrorStream(true);
    stationProcess = processBuilder.start();

    reader = new BufferedReader(new InputStreamReader(stationProcess.getInputStream()));
    writer = new BufferedWriter(new OutputStreamWriter(stationProcess.getOutputStream()));

    // Wait for station startup.
    // TODO what if the station fails?

    Collection<String> requiredMatches = new ArrayList<>(Arrays.asList(STATION_STARTED_REGEX, FOX_STARTED_REGEX));

    while (!requiredMatches.isEmpty())
    {
      String line = reader.readLine();
      stationLog(line);
      requiredMatches.removeIf(requiredMatch -> line.matches(requiredMatch));
    }

    if (!stationProcess.isAlive())
    {
      throw new RuntimeException("Could not start station?");
    }
    return this;
  }


////////////////////////////////////////////////////////////////
// Interactions
////////////////////////////////////////////////////////////////

  /**
   * Stop the running station by writing "quit" to its console.
   *
   * @return The exit value of the station process
   * @throws IOException If writing to the process fails
   * @throws InterruptedException If stopping the process fails
   */
  public int stop() throws IOException, InterruptedException
  {
    writer.write("quit\n");
    writer.flush();
    reader.lines().forEach(StationRunner::stationLog);

    if (!stationProcess.waitFor(5, TimeUnit.SECONDS))
    {
      stationProcess.destroy();
    }
    return stationProcess.exitValue();
  }

  /**
   * Get a FoxSession to the spawned remote station
   *
   * @param username Username to log in with
   * @param password Password to log in with
   * @param useFoxs Whether to connect with FOXS or not
   * @return A connected FOX(S) session.
   * @throws Exception If connection fails
   */
  public BFoxSession getFoxSession(String username, String password, boolean useFoxs)
    throws Exception
  {
    BFoxSession session = BFoxSession.make(BLocalHost.INSTANCE, useFoxs ? foxsPort : foxPort, useFoxs);
    TestAuthenticationClient client = new TestAuthenticationClient(username, password);
    session.getConnection().setAuthenticationClient(client);
    SocketFactory factory = CertManagerFactory.getInstance().getClientSocketFactory(ClientTlsParameters.DEFAULT);
    if (factory instanceof ExemptionHandler)
    {
      ((ExemptionHandler)factory).setExemptionApprover(new ExemptionApprover()
      {
        @Override
        public boolean approveExemption(CertValidationResult result)
        {
          return true;
        }

        @Override
        public boolean isTransientApproval()
        {
          return true;
        }
      });
    }
    session.getConnection().setSSLSocketFactory((SSLSocketFactory)factory);
    BFoxSession foxSession = BFoxSession.connect(session);
    return foxSession;
  }

  /**
   * Connect to the remote station and mount its root station component
   *
   * @param username Username to log in with
   * @param password Password to log in with
   * @param useFoxs Whether to connect with FOXS or not
   * @return The remote station
   * @throws Exception If connection fails
   */
  public BStation getRemoteStation(String username, String password, boolean useFoxs)
    throws Exception
  {
    BFoxSession foxSession = getFoxSession(username, password, useFoxs);
    // Get the broker channel
    BBrokerChannel channel = (BBrokerChannel)foxSession.getConnection().getChannels().get("station", BBrokerChannel.TYPE);
    BComponent remoteStationComp = channel.fromOrd("slot:/");
    if (!(remoteStationComp instanceof BStation))
    {
      throw new RuntimeException("Could not get remote station component");
    }
    return (BStation)remoteStationComp;
  }

////////////////////////////////////////////////////////////////
// Utility methods
////////////////////////////////////////////////////////////////

  private static void stationLog(String msg)
  {
    System.out.println("[station] " + msg);
  }

  private void getPortsFromStation(BStation station)
  {
    httpsPort = station.getServices().getChildren(BWebService.class)[0].getHttpsPort().getPublicServerPort();
    BFoxService foxService = station.getServices().getChildren(BFoxService.class)[0];

    foxPort = foxService.getFoxPort().getPublicServerPort();
    foxsPort = foxService.getFoxsPort().getPublicServerPort();
  }

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private Path niagaraUserHome;
  private Path bogFile;
  private BStation station;
  private boolean addExemptions;

  private int foxPort;
  private int foxsPort;
  private int httpsPort;

  private Process stationProcess;
  private BufferedReader reader;
  private BufferedWriter writer;

  private static final String FOX_STARTED_REGEX = ".*FOX.? server started on port \\[[0-9]+\\].*";
  private static final String STATION_STARTED_REGEX = ".*\\*\\*\\* Station Started.*";
}
