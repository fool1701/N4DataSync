/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.testng;

import static org.testng.Assert.*;

import static com.tridium.testng.TestUtil.waitFor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;

import javax.baja.alarm.BAlarmService;
import javax.baja.app.BAppContainer;
import javax.baja.category.BCategoryService;
import javax.baja.driver.BDriverContainer;
import javax.baja.naming.BLocalHost;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.role.BRole;
import javax.baja.role.BRoleService;
import javax.baja.search.BSearchService;
import javax.baja.security.BPassword;
import javax.baja.security.BPasswordAuthenticator;
import javax.baja.security.BPbkdf2HmacSha256PasswordEncoder;
import javax.baja.security.BPermissions;
import javax.baja.security.BPermissionsMap;
import javax.baja.sync.BProxyComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.BStation;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.test.BTestNg;
import javax.baja.test.TestException;
import javax.baja.user.BUser;
import javax.baja.user.BUserService;
import javax.baja.util.BServiceContainer;
import javax.baja.web.BWebServer;
import javax.baja.web.BWebService;

import org.eclipse.jetty.server.Server;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.tridium.authn.BAuthenticationService;
import com.tridium.crypto.core.cert.CertUtils;
import com.tridium.fox.sys.BFoxService;
import com.tridium.fox.sys.BFoxSession;
import com.tridium.jetty.BJettyWebServer;
import com.tridium.nd.BNiagaraNetwork;
import com.tridium.sys.Nre;
import com.tridium.sys.station.Station;
import com.tridium.ui.NullUiEnv;
import com.tridium.ui.UiEnv;

/**
 * Test support base class that allows configuring a
 * station for web and fox testing.
 *
 * @author Dan Heine on 2013-06-14
 * @since Niagara 4.0
 */
@NiagaraType
public abstract class BStationTestBase
  extends BTestNg
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.testng.BStationTestBase(2979906276)1.0$ @*/
/* Generated Wed Jan 05 17:05:31 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStationTestBase.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Setup and teardown
////////////////////////////////////////////////////////////////

  /**
   * Defaults to creating a new test station for each test case.
   *
   * @throws Exception
   */
  @BeforeTest(alwaysRun = true, description = "Setup and start test station")
  public void setupStation()
    throws Exception
  {
    cleanStationListeners();

    // Create basic station
    if (stationHandler == null)
    {
      System.out.println("Creating test station for " + getClass());
      makeStationHandler();
      System.out.println("Done creating station for " + getClass());
    }

    BStation station = stationHandler.getStation();

    // Override this method for custom station configuration if needed
    try
    {
      configureTestStation(station, testStationName, webPort, foxPort);
    }
    catch (Exception e)
    {
      // If the configuration fails, we need to ensure that the half-created
      // station is released so that subsequent tests can still run
      stationHandler.stopStation();
      stationHandler.releaseStation();
      stationHandler = null;
      System.out.println("Unable to set up station for " + getClass());
      throw new Exception("Unable to set up station for " + getClass(), e);
    }

    // Start the station
    startStation(station);
    Thread.sleep(500);
    Station.atSteadyState = true;
    Nre.getEngineManager().atSteadyState(station);
  }

  /**
   * By default, creates an empty test station that is later configured by
   * {@link BStationTestBase#configureTestStation(BStation, String, int, int)}. Can be overridden by subclasses to
   * call a different version of createTestStation that, for example, takes a file ord to an xml
   * definition of the station (see {@link TestStationHandler#createTestStation(BOrd)} or
   * {@link TestStationHandler#createTestStation(String)}.
   * <p>
   * Note: If the supplied station definition contains services configured in
   * configureTestStation, that method should probably also be overridden to avoid duplicate slot
   * exceptions.
   * </p>
   */
  protected void makeStationHandler()
    throws Exception
  {
    stationHandler = createTestStation();
  }

  /**
   * Defaults to tearing down the test station after every test case.
   *
   * @throws Exception
   */
  @AfterTest(alwaysRun = true, description = "Teardown test station")
  public void teardownStation()
    throws Exception
  {
    if (stationHandler != null)
    {
      stationHandler.stopStation();

      // Wait until the jetty server stops since this occurs asynchronously. We do not want to
      // proceed to the next test until the webserver has stopped. See BWebServer stopWebServer()
      // function for transitions.
      if (webService != null &&
        webService.getWebServer() != null)
      {
        String[] serverState = { null };
        TestUtil.assertWillBeTrue(
          () -> {
            try
            {
              serverState[0] = webService.getWebServer().getServerState();
              return serverState[0].equals("stopped") ||
                serverState[0].equals("failed");
            }
            catch (Exception ignored)
            {
              // Any exception retrieving the server probably means it shutdown, ignore any errors
              return true;
            }
          },
          () -> "Failed to shutdown webserver in a timely fashion, state is '" + serverState[0] + '\''
        );
      }

      stationHandler.releaseStation();
      stationHandler = null;
      cleanStationListeners();
      Station.station = null;
      Station.stationStarted = false;
      Station.atSteadyState = false;

    }
  }

  private void cleanStationListeners()
  {
    for (Station.SaveListener listener : Station.getSaveListeners())
    {
      Station.removeSaveListener(listener);
    }

    for (Station.RemoteListener listener : Station.getRemoteListeners())
    {
      Station.removeRemoteListener(listener);
    }
  }

////////////////////////////////////////////////////////////////
// Utilities and Helpers
////////////////////////////////////////////////////////////////

  public BUser getTestSuperUser()
  {
    BUserService userService = BUserService.getService();
    return userService.getUser(TEST_SUPER_USER);
  }

  public BUser getTestAdminUser()
  {
    BUserService userService = BUserService.getService();
    return userService.getUser(TEST_ADMIN_USER);
  }

  public BUser getTestOperatorUser()
  {
    BUserService userService = BUserService.getService();
    return userService.getUser(TEST_OPERATOR_USER);
  }

  public BUser getTestVisitorUser()
  {
    BUserService userService = BUserService.getService();
    return userService.getUser(TEST_VISITOR_USER);
  }

  /**
   * Connect to the local (in-process) test station via Fox using the specified account.
   *
   * @return Fox session
   * @throws Exception
   */
  protected BFoxSession connect(String userName, String password)
    throws Exception
  {
    return connect(userName, password, MAX_FOX_CONNECTION_ATTEMPTS);
  }

  /**
   * Connect to the local (in-process) test station via Fox using the specified account
   * with the specified number of attempts.
   *
   * @return Fox session
   * @throws Exception
   */
  protected BFoxSession connect(String userName, String password, int maxAttempts)
    throws Exception
  {
    BFoxSession foxSession = BFoxSession.make(BLocalHost.INSTANCE, foxPort, false);
    TestAuthenticationClient client = new TestAuthenticationClient(userName, password);
    foxSession.getConnection().setAuthenticationClient(client);

    // Attempt the FOX client connection, and if there are any failures, retry
    // up to the maximum number of connection attempts allowed
    Exception e = null;
    int attempts = 0;
    while (!foxSession.getConnection().isConnected() && attempts < maxAttempts)
    {
      try
      {
        ++attempts;
        foxSession = BFoxSession.connect(foxSession);
        e = null;
      }
      catch (Exception ex)
      {
        // Stash the exception to be thrown later if all connection attempts fail
        e = ex;
      }

      if (!foxSession.getConnection().isConnected() && attempts < maxAttempts)
      {
        // Give some time for the server side to settle out before attempting
        // the next FOX client connection retry
        Thread.sleep(Long.getLong("SERVER_STARTUP_SLEEP", DEFAULT_FOX_CONNECTION_REATTEMPT_DELAY));
      }
    }

    if (!foxSession.getConnection().isConnected() && e != null)
    {
      throw e;
    }

    Assert.assertTrue(
      foxSession.getConnection().isConnected(),
      "failed to connect fox client connection (" + foxSession.getLastFailureCause() + ")"
    );

    return foxSession;
  }

  protected BOrd localOrd(String query)
  {
    return BOrd.make(String.format("local:|%s", query));
  }

  protected BOrd remoteOrd(String query)
  {
    return BOrd.make(String.format("local:|fox:%d|%s", foxPort, query));
  }

  protected BOrd remoteSlotPath(String slotPath)
  {
    return BOrd.make(String.format("local:|fox:%d|station:|slot:%s", foxPort, slotPath));
  }

  protected BOrd localSlotPath(String slotPath)
  {
    return BOrd.make(String.format("local:|station:|slot:%s", slotPath));
  }

  protected void sync()
    throws Exception
  {
    BOrd.make(String.format("local:|fox:%d|station:", foxPort)).get().as(BProxyComponentSpace.class).sync();
  }

  protected void pause(int ms)
  {
    try
    {
      System.out.println("      Pausing " + ms + "ms...");
      Thread.sleep(ms);
    }
    catch (InterruptedException e)
    {
      throw new TestException(e.toString());
    }
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * Configures a standard test station that provides at least a Niagara Network.
   * The default implementation here also (for convenience) provides several other
   * common-used services.  However, subclasses can override this behavior if desired.
   *
   * @throws Exception
   */
  protected void configureTestStation(BStation station, String stationName, int webPort, int foxPort)
    throws Exception
  {
    // Create station db with two containers
    BComponent drivers = addOrSetContainer(station, DRIVERS, BDriverContainer.TYPE);
    addOrSetContainer(station, APPS, BAppContainer.TYPE);

    // Add non-driver services
    BComponent services = station.getServices();

    services.add(ROLE_SERVICE, makeRoleService());
    services.add(USER_SERVICE, makeUserService());

    // Add the minimum services
    services.add(ALARM_SERVICE, newInstance("alarm:AlarmService"));
    services.add(HISTORY_SERVICE, newInstance("history:HistoryService"));
    services.add(JOB_SERVICE, newInstance("baja:JobService"));
    services.add(CATEGORY_SERVICE, newInstance("baja:CategoryService"));
    services.add(BOX_SERVICE, newInstance("box:BoxService"));
    services.add(AUTH_SERVICE, makeAuthService());
    services.add(FOX_SERVICE, makeFoxService(foxPort));

    if (isSearchServiceEnabled())
    {
      services.add(SEARCH_SERVICE, newInstance("search:SearchService"));
    }

    if (isWebServiceEnabled())
    {
      webService = makeWebService(webPort);
      services.add(WEB_SERVICE, webService);
    }

    // Add drivers
    if (drivers.get(NIAGARA_NETWORK) == null)
    {
      drivers.add(NIAGARA_NETWORK, newInstance("niagaraDriver:NiagaraNetwork"));
    }

    // Establish the history warmup property
    // Users should set enableHistoryWarmup = true before calling configureTestStation(...)
    // if their tests require history warmup to be active.
    System.setProperty("niagara.history.warmup", String.valueOf(enableHistoryWarmup));

    // Name the station
    station.setStationName(stationName);
  }

  private static BComponent addOrSetContainer(BStation station, String name, Type type)
  {
    BComponent container = (BComponent)station.get(name);

    if (container == null)
    {
      // container is not present already
      container = (BComponent)type.getInstance();
      station.add(name, container);
    }
    else if (!container.getType().is(type))
    {
      // container that is present is not the correct type; replace it
      container = (BComponent)type.getInstance();
      station.set(name, container, null);
    }

    return container;
  }

  /**
   * Start a station that has a Niagara Network and make sure both station and the Fox service
   * are running prior to return.
   *
   * @throws Exception
   */
  protected void startStation(BStation station)
    throws Exception
  {
    BFoxService stationFoxService = station.getChildren(BServiceContainer.class)[0]
      .getChildren(BFoxService.class)[0];

    if (!station.isRunning())
    {
      Nre.clearPlatform();
      Nre.loadPlatform();
      Nre.getServiceManager().startAllServices();
      station.start();
      Station.stationStarted = true;

      Nre.getEngineManager().stationStarted(station);
    }

    int maxAttempts = 10;
    while (maxAttempts-- >= 0 && !station.isRunning())
    {
      Thread.sleep(100);
    }
    if (!station.isRunning())
    {
      throw new Exception("Station did not start in time");
    }

    // Increased attempts from 10 to 50 to prevent intermittent CI test failures
    maxAttempts = 50;
    while (maxAttempts-- >= 0 && !stationFoxService.isServing())
    {
      Thread.sleep(100);
    }
    if (!stationFoxService.isServing())
    {
      throw new Exception("Fox service did not start in time");
    }
  }

  /**
   * Create an authentication service with a Digest, LegacyDigest,
   * and Basic authentication scheme
   */
  protected BAuthenticationService makeAuthService()
  {
    BAuthenticationService authService = new BAuthenticationService();
    authService.get("authenticationSchemes");
    return authService;
  }

  /**
   * Creates a "web:WebService"
   *
   * @throws Exception
   */
  @SuppressWarnings("deprecation")
  protected BWebService makeWebService(int port)
    throws Exception
  {
    BWebService service = new BWebService();
    service.setHttpsCert(CertUtils.FACTORY_CERT_ALIAS);
    service.getMainCertAliasAndPassword().resetAliasAndPassword();
    service.getHttpPort().setPublicServerPort(port);

    BWebServer server = new BJettyWebServer();
    service.add("JettyWebServer", server);

    return service;
  }

  protected boolean isWebServiceEnabled()
  {
    //NCCB-59914: Make WebService opt-in to improve Station startup times and reduce noise
    return false;
  }

  protected boolean isSearchServiceEnabled()
  {
    //Make SearchService opt in since it prints a warning each time you start it
    return false;
  }

  /**
   * Create a RoleService with roles for each of the test users.
   *
   * @return BRoleService with 'admin', 'TestAdmin, 'TestOperator', and 'TestVisitor'.
   * @throws Exception
   */
  public BComponent makeRoleService()
    throws Exception
  {
    BRoleService roleService = new BRoleService();

    addRole(roleService, TEST_SUPER_USER, BPermissionsMap.SUPER_USER);
    addRole(roleService, TEST_ADMIN_USER, map(BPermissions.make("rwiRWI")));
    addRole(roleService, TEST_OPERATOR_USER, map(BPermissions.make("rwi")));
    addRole(roleService, TEST_VISITOR_USER, map(BPermissions.make("r")));
    addRole(roleService, TEST_DELIMITED_USER, BPermissionsMap.SUPER_USER);

    return roleService;
  }

  /**
   * Create a user service with test users of various privileges.
   *
   * @return BUserService with 'admin', 'TestAdmin, 'TestOperator', and 'TestVisitor'.
   * All four passwords are set to 'Test@1234'. Except the TEST_DELIMITED_USER which
   * has a password containing a colon (see: TEST_DELIMITED_PASS)
   * @throws Exception
   */
  public BComponent makeUserService()
    throws Exception
  {
    // Create service
    BUserService userService = new BUserService();

    addUser(userService, TEST_SUPER_USER);
    addUser(userService, TEST_ADMIN_USER);
    addUser(userService, TEST_OPERATOR_USER);
    addUser(userService, TEST_VISITOR_USER);
    addUser(userService, TEST_DELIMITED_USER, TEST_DELIMITED_PASS);

    return userService;
  }

  private static BPermissionsMap map(BPermissions permissions)
  {
    // The null likely serves to offset the index mismatch between zero-based java arrays and 
    // one-based category indexes. Yes, you can create a category with a "zero" index but
    // the BPermissionsMap only encodes from "i=1" so using one is probably not a good idea.
    return BPermissionsMap.make(new BPermissions[]{ null, permissions });
  }

  protected static BPermissionsMap map(BPermissions... permissions)
  {
    BPermissions[] mappedPermissions = new BPermissions[permissions.length + 1];
    mappedPermissions[0] = null;
    System.arraycopy(permissions, 0, mappedPermissions, 1, permissions.length);
    return BPermissionsMap.make(mappedPermissions);
  }

  protected static BPermissionsMap map(String... permissions)
  {
    BPermissions[] mappedPermissions = new BPermissions[permissions.length + 1];
    mappedPermissions[0] = null;
    for (int i = 0; i < permissions.length; ++i)
    {
      BPermissions permissionsValue = BPermissions.none;
      try
      {
        permissionsValue = BPermissions.make(permissions[i]);
      }
      catch (IOException ignore)
      {
      }
      mappedPermissions[i + 1] = permissionsValue;
    }
    return BPermissionsMap.make(mappedPermissions);
  }

  private static void addRole(BRoleService roleService, String name, BPermissionsMap permissions)
  {
    BRole role = new BRole();
    role.setPermissions(permissions);
    roleService.add(name, role);
  }

  protected BRole addRole(String name, BPermissionsMap permissionsMap)
  {
    BRole role = new BRole();
    role.setPermissions(permissionsMap);
    getRoleService().add(name, role);
    return role;
  }

  private static void addUser(BUserService userService, String name)
  {
    addUser(userService, name, TEST_PASSWORD);
  }

  private static void addUser(BUserService userService, String name, String password)
  {
    userService.add(name, makeUser(Collections.singleton(name), password));
  }

  protected BUser addUser(String userName, Set<String> roleNames)
  {
    BUser user = makeUser(roleNames, TEST_PASSWORD);
    getUserService().add(userName, user);
    return user;
  }

  protected BUser addUser(String userName, Set<String> roleNames, String password)
  {
    BUser user = makeUser(roleNames, password);
    getUserService().add(userName, user);
    return user;
  }

  private static BUser makeUser(Set<String> roleNames, String password)
  {
    BUser user = new BUser();
    roleNames.forEach(roleName -> user.addRole(roleName, Context.skipValidate));
    BPassword encodedPassword = BPassword.make(password, BPbkdf2HmacSha256PasswordEncoder.ENCODING_TYPE);
    BPasswordAuthenticator pAuth = new BPasswordAuthenticator(encodedPassword);
    user.setAuthenticator(pAuth);
    return user;
  }

  protected BDriverContainer getDrivers()
  {
    return (BDriverContainer)stationHandler.getStation().get(DRIVERS);
  }

  protected BNiagaraNetwork getNiagaraNetwork()
  {
    return (BNiagaraNetwork)getDrivers().get(NIAGARA_NETWORK);
  }

  protected BServiceContainer getServices()
  {
    return stationHandler.getStation().getServices();
  }

  protected BRoleService getRoleService()
  {
    return (BRoleService)getServices().get(ROLE_SERVICE);
  }

  protected BUserService getUserService()
  {
    return (BUserService)getServices().get(USER_SERVICE);
  }

  protected BWebService getWebService()
  {
    if (isWebServiceEnabled())
    {
      return (BWebService)getServices().get(WEB_SERVICE);
    }

    return null;
  }

  protected BSearchService getSearchService()
  {
    if (isSearchServiceEnabled())
    {
      return (BSearchService)getServices().get(SEARCH_SERVICE);
    }

    return null;
  }

  protected BFoxService getFoxService()
  {
    return (BFoxService)getServices().get(FOX_SERVICE);
  }
  
  protected BCategoryService getCategoryService()
  {
    return (BCategoryService)getServices().get(CATEGORY_SERVICE);
  }

  protected BAuthenticationService getAuthService()
  {
    return (BAuthenticationService)getServices().get(AUTH_SERVICE);
  }

  protected BAlarmService getAlarmService()
  {
    return (BAlarmService)getServices().get(ALARM_SERVICE);
  }

  protected String getBaseURI()
  {
    return "http://localhost:" + webPort + '/';
  }

  /**
   * Create a Fox service. This will have the fox
   * authentication policy set to "BASIC" to work in tandem with webservice
   * "cookie digest" authentication scheme
   *
   * @return - Fox service set to the specified port and BASIC authentication
   * @throws Exception
   */
  @SuppressWarnings("deprecation")
  public BComponent makeFoxService(int foxPort)
    throws Exception
  {
    BFoxService foxSvc = new BFoxService();
    foxSvc.setFoxsCert(CertUtils.FACTORY_CERT_ALIAS);
    foxSvc.getCertAliasAndPassword().resetAliasAndPassword();
    foxSvc.getFoxPort().setPublicServerPort(foxPort);
    return foxSvc;
  }

  public BComponent newInstance(String type)
    throws Exception
  {
    return (BComponent)Sys.getType(type).getInstance();
  }

  protected int defaultUserCount()
  {
    return NUMBER_OF_USERS;
  }

  protected String getSuperUsername()
  {
    return TEST_SUPER_USER;
  }

  protected String getSuperUserPassword()
  {
    return TEST_PASSWORD;
  }

  private static boolean hasUi()
  {
    UiEnv uiEnv = UiEnv.get();
    return uiEnv != null && !(uiEnv instanceof NullUiEnv);
  }

  protected void waitForWebServerStart()
  {
    //Wait for the webserver to complete its startup sequence
    try { Thread.sleep(1000); } catch (Exception ignored) {}

    assertTrue(waitFor(() -> getWebService().getWebServer() != null, 10000, 100),
      "Failed to start Jetty webserver in a timely manner");
    BJettyWebServer jettyWebServer = (BJettyWebServer)getWebService().getWebServer();
    assertTrue(waitFor(() -> "started".equalsIgnoreCase(jettyWebServer.getServerState()), 10000, 100),
      "Failed to start Jetty webserver in a timely manner");

    // can't use PA here so have to do this the hard way
    Server jettyServer;
    try
    {
      Field jettyField = BJettyWebServer.class.getDeclaredField("jetty");
      jettyField.setAccessible(true);
      jettyServer = (Server)jettyField.get(jettyWebServer);
    }
    catch(Exception e)
    {
      throw new IllegalArgumentException("can't get value of jetty", e);
    }
    assertTrue(waitFor(jettyServer::isRunning, 10000, 100), "Failed to start Jetty webserver in a timely manner");
  }

  protected void startWebServer()
  {
    //Enabled and start the server

    getWebService().setEnabled(true);
    //NOTE: Toggling the enable state of the server back on will automatically schedule a "start"
    //jettyWebServer.doStartWebServer();
    //Wait for the scheduled action to fire
    try { Thread.sleep(2500); } catch (Exception ignored) {}

    assertTrue(waitFor(() -> getWebService().getWebServer() != null, 10000, 100),
      "Failed to start Jetty webserver in a timely manner");
    BJettyWebServer jettyWebServer = (BJettyWebServer)getWebService().getWebServer();
    assertTrue(waitFor(() -> "started".equalsIgnoreCase(jettyWebServer.getServerState()), 10000, 100),
      "Failed to start Jetty webserver in a timely manner");

    // can't use PA here so have to do this the hard way
    Server jettyServer;
    try
    {
      Field jettyField = BJettyWebServer.class.getDeclaredField("jetty");
      jettyField.setAccessible(true);
      jettyServer = (Server)jettyField.get(jettyWebServer);
    }
    catch(Exception e)
    {
      throw new IllegalArgumentException("can't get value of jetty", e);
    }
    assertTrue(waitFor(jettyServer::isRunning, 10000, 100), "Failed to start Jetty webserver in a timely manner");
  }

  protected void stopWebServer()
  {
    //Disable and stop the server

    //Retain a reference to the jetty server before it shuts down so we can get status, avoid NPE
    BJettyWebServer jettyWebServer = (BJettyWebServer)getWebService().getWebServer();

    // can't use PA here so have to do this the hard way
    Server jettyServer;
    try
    {
      Field jettyField = BJettyWebServer.class.getDeclaredField("jetty");
      jettyField.setAccessible(true);
      jettyServer = (Server)jettyField.get(jettyWebServer);
    }
    catch(Exception e)
    {
      throw new IllegalArgumentException("can't get value of jetty", e);
    }

    getWebService().setEnabled(false);
    //NOTE: Toggling the enable state of the server back on will automatically schedule a "stop",
    // babysit the shutdown so we don't wait forever
    //PA.invokeMethod(jettyWebServer, "stopWebServer()");
    //Wait for the scheduled action to fire
    try { Thread.sleep(2500); } catch (Exception ignored) {}

    //Make sure the internal Jetty server has stopped
    if (jettyServer != null)
    {
      if (!waitFor(() -> !jettyServer.isRunning(), 10000, 100))
      {
        jettyServer.destroy();
        Assert.fail("Failed to stop the Jetty WebServer in a timely fashion");
      }
    }

    //Make sure the BObject version of the server has shutdown
    if (jettyWebServer != null)
    {
      if (!waitFor(() -> "stopped".equalsIgnoreCase(jettyWebServer.getServerState()), 10000, 100))
      {
        Assert.fail("Failed to stop the Jetty WebServer in a timely fashion");
      }
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /*
   * Certain tests rely on the number of users, make sure to update
   * the number of users when a new user is added to the default
   * test station
   */
  public static final int NUMBER_OF_USERS = 5;

  public static final String TEST_SUPER_USER = "TestSuper";
  public static final String TEST_ADMIN_USER = "TestAdmin";
  public static final String TEST_OPERATOR_USER = "TestOperator";
  public static final String TEST_VISITOR_USER = "TestVisitor";
  public static final String TEST_PASSWORD = "Test@1234_5678";
  public static final String TEST_DELIMITED_USER = "username";
  public static final String TEST_DELIMITED_PASS = "user:password1";

  protected static final String testStationName = "test";

  protected boolean enableHistoryWarmup = false;

  protected static final String USER_SERVICE = "UserService";
  protected static final String ROLE_SERVICE = "RoleService";
  protected static final String AUTH_SERVICE = "AuthenticationService";
  protected static final String CATEGORY_SERVICE = "CategoryService";
  protected static final String ALARM_SERVICE = "AlarmService";
  protected static final String WEB_SERVICE = "WebService";
  protected static final String SEARCH_SERVICE = "SearchService";
  protected static final String HISTORY_SERVICE = "History";
  protected static final String JOB_SERVICE = "Job";
  protected static final String BOX_SERVICE = "BoxService";
  protected static final String FOX_SERVICE = "FoxService";
  protected static final String DRIVERS = "Drivers";
  protected static final String NIAGARA_NETWORK = "NiagaraNetwork";
  protected static final String APPS = "Apps";

  private BWebService webService = null;

  /**
   * Max number of attempts (including retries of failed attempts) for making a
   * fox connection to the test station.
   */
  protected static final int MAX_FOX_CONNECTION_ATTEMPTS = 3;
  protected static final long DEFAULT_FOX_CONNECTION_REATTEMPT_DELAY = 1000L;

  protected TestStationHandler stationHandler;
  protected int foxPort = 1911;
  protected int webPort = 9090;
}
