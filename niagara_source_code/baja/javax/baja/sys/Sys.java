/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.File;
import java.net.InetAddress;
import java.util.Optional;

import javax.baja.license.LicenseException;
import javax.baja.license.LicenseManager;
import javax.baja.nre.platform.RuntimeProfile;
import javax.baja.nre.security.HsmManager;
import javax.baja.registry.Registry;
import javax.baja.security.Auditor;
import javax.baja.security.SecurityAuditor;
import javax.baja.util.BTypeSpec;
import javax.baja.util.Version;

import com.tridium.sys.Nre;
import com.tridium.sys.NreLib;
import com.tridium.sys.module.NModule;
import com.tridium.sys.station.Station;

/**
 * Sys provides a single location for accessing 
 * singletons and framework functionality.
 *
 * @author    Brian Frank
 * @creation  9 Mar 00
 * @version   $Revision: 102$ $Date: 12/19/08 5:42:01 PM EST$
 * @since     Baja 1.0
 */
public final class Sys
{

////////////////////////////////////////////////////////////////
// Environment
////////////////////////////////////////////////////////////////

  /**
   * Get the system's default lexicon language, which is 
   * initialized to {@code Locale.getDefault().getLanguage()}.
   */
  public static String getLanguage()
  {
    return Nre.language;
  }

  /**
   * Set the system's default lexicon language.
   */
  public static void setLanguage(String language)
  {
    if (language == null) throw new NullPointerException();
    Nre.language = language;
  }

  /**
   * Get the default directory to use for user files.
   * @since Niagara 4.0
   */
  public static File getNiagaraUserHome()
  {
    return Nre.niagaraUserHome;
  }

  /**
   * Get the default shared directory to use for user files,
   * which can be accessed by any module.
   */
  public static File getNiagaraSharedUserHome()
  {
    return Nre.niagaraSharedUserHome;
  }

  /**
   * Get the home directory for development files.
   *
   * @since Niagara 4.2
   */
  public static File getNiagaraDevHome() { return Nre.getNiagaraDevHome(); }

  /**
   * Get the default directory to use for user files.
   * @since Niagara 4.0
   */
  public static File getCredentialsHome()
  {
    return Nre.credentialsHome;
  }

  /**
   * Get the station's shared home directory or null if this
   * VM is not running a station application.
   */
  public static File getStationHome()
  {
    return Nre.stationHome;
  }
  
  /**
   * Get the station's protected home directory or null if this
   * VM is not running a station application.
   * @since Niagara 4.0
   */
  public static File getProtectedStationHome()
  {
    return Nre.protectedStationHome;
  }
  
  /**
   * Get the default local host for this platform.
   *
   * @deprecated In "multihomed" environments (multiple IP adapters) this
   *             function may return an InetAddress object defining an
   *             address that is not reachable for a particular network segment.
   *
   *             For example, if connected to 192.168.1.0/24 and 192.168.2.0/24,
   *             using 192.168.1.X to describe ourselves to 192.168.2.X network
   *             segment will result in error.
   *
   *             Use Sys.getLocalHost(InetAddress hintAddress) where hintAddress
   *             is the remote address requesting our local host address to ensure
   *             the appropriate local host definition is returned.
   */
  @Deprecated
  public static InetAddress getLocalHost()
  {
    return NreLib.getLocalHost();
  }

  /**
   * Get the local host address (IPv4 or IPv6) for this platform
   * that is on the same subnet as the provided value. If no addresses
   * for this platform are on the same subnet as the provided value,
   * return the default local host address (getLocalHost())
   *
   * If hintAddress is null, return default local host address (getLocalHost())
   *
   * Useful if platform has multiple internet protocol interfaces.
   *
   * @since Niagara 4.3
   */
  public static InetAddress getLocalHost(InetAddress hintAddress)
  {
    return NreLib.getLocalHost(hintAddress);
  }

  /**
   * Get the host name for this platform.
   *
   * Host domain will be included if this host is a part of a domain.
   *
   * This method should generally be considered more reliable than
   * getLocalHost().getHostName() as not all InetAddress objects registered
   * to this platform will include a host name.
   *
   * @since Niagara 4.3
   */
  public static String getHostName()
  {
    return NreLib.getHostName();
  }

  /**
   * Get the local station component, or null if the station has not yet been
   * fully decoded from the BOG file (or if a non-Station environment, like
   * Workbench).
   */
  public static BStation getStation()
  {
    return Station.station;
  }

  /**
   * Return true if the VM is running a Station application. Note that this will
   * still return {@code false} if the station has not yet been fully decoded
   * from the BOG file.
   *
   * @return true if running a Station.
   */
  public static boolean isStation()
  {
    return Station.station != null;
  }
  
  /**
   * Return if the VM's current station has recursively 
   * completed starting its entire component tree.
   */
  public static boolean isStationStarted()
  {
    return Station.stationStarted;
  }

  /**
   * Return if the station VM has completed it's steady state
   * wait period.  This is a built-in timer that allows the
   * station to reach steady state after station startup.  If the
   * VM is not running a station then always return false.
   * (Note default behavior for non-Stations changed as part of NCCB-15399)
   */
  public static boolean atSteadyState()
  {
    return Station.atSteadyState;
  }
    
  /**
   * Get the registry which provides the ability to 
   * interrogate what is installed on the system.
   */
  public static Registry getRegistry()
  {
    return Nre.getRegistryManager();
  }
  
  /**
   * Get the system's license manager which provides
   * access to the license database.
   */
  public static LicenseManager getLicenseManager()
  {
    LicenseManager lm = Nre.getLicenseManager();
    if (!lm.getClass().getName().equals("com.tridium.sys.license.NodeLockedLicenseManager") &&
        !lm.getClass().getName().equals("com.tridium.sys.license.subscription.SubscriptionLicenseManager"))
    {
      throw new LicenseException();
    }
    return lm;
  }

  /**
   * Get the host identifier string which uniquely 
   * identifies the machine running this VM.   
   *
   * @since Niagara 3.2
   */
  public static String getHostId()
  {
    return NreLib.getHostId();
  }

  /**
   * Returns a reference to the Hsm Manager which can be used to determine
   * if there is a supported Hsm implementation.
   *
   * @since Niagara 4.6
   */
  public static HsmManager getHsmManager()
  {
    return Nre.getHsmManager();
  }

////////////////////////////////////////////////////////////////
// Modules
////////////////////////////////////////////////////////////////

  /**
   * Get the BModule for the core "baja" module.
   */
  public static BModule getBajaModule()
  {
    return BBoolean.TYPE.getModule();
  }

  /**
   * Get the version for the core "baja" module.
   *
   * @since Niagara 4.0
   */
  public static Version getBajaVersion() { return getBajaModule().getVendorVersion(RuntimeProfile.rt);}

  /**
   * Get the vendor for the core "baja" module.
   *
   * @since Niagara 4.0
   */
  public static String getBajaVendor() { return getBajaModule().getVendor(RuntimeProfile.rt);}

  /**
   * Get the module which was used to load the
   * specified class.  If the class was loaded
   * by the system classloader and not included
   * in the baja module, then return null. 
   */
  public static BModule getModuleForClass(Class<?> cls)
  {
    NModule module = Nre.getModuleManager().getModuleForClass(cls);
    if (module != null) return module.bmodule();
    else return null;
  }
  
  /**
   * Load a module into the VM.  This loads the latest
   * and (hopefully) greatest module available for the
   * specified name.
   *
   * @throws ModuleNotFoundException if a suitable module
   *    is not found.
   * @throws ModuleException if there was an error loading
   *    the module into the VM.
   */
  public static BModule loadModule(String moduleName)
    throws ModuleNotFoundException, ModuleException
  {
    NModule[] moduleParts = Nre.getModuleManager().loadModuleParts(moduleName);
    if (moduleParts.length == 0)
    {
      throw new ModuleNotFoundException(moduleName);
    }
    return moduleParts[0].bmodule();
  }
    
  /**
   * Dynamically load the specified class from the 
   * given module.
   *
   * @throws ClassNotFoundException if the class was
   *    not found in the specified module.
   * @throws ModuleNotFoundException if a suitable module
   *    is not found.
   * @throws ModuleException if there was an error loading
   *    the module into the VM.
   */
  public static Class<?> loadClass(String module, String classname)
    throws ClassNotFoundException, ModuleException
  {
    return loadModule(module).loadClass(classname);
  }

  /**
   * This is a convenience for loadClass().newInstance().
   */
  public static Object newInstance(String module, String classname)
    throws ClassNotFoundException, ModuleException,
           InstantiationException, IllegalAccessException
  {
    return loadClass(module, classname).newInstance();
  }

////////////////////////////////////////////////////////////////
// Services
////////////////////////////////////////////////////////////////  

  /**
   * Get the service registered for the specified class.
   * If more than one service is registered then return
   * the first one registered.  The reference to the
   * service should never be stored, but rather looked up
   * everytime it is needed.  This allows services to be
   * unregistered and re-registered cleanly.
   * <p>
   * This method only returns services registered in the
   * local VM.  To lookup a service in a remote session
   * use the "service" ord scheme.
   *
   * @throws ServiceNotFoundException if no service has
   *    been registered for the specified type.
   */
  public static BComponent getService(Type type)
    throws ServiceNotFoundException
  {
    return Nre.getServiceManager().getService(type.getTypeSpec().toString(null));
  }

  public static Optional<BIService> findService(Type type)
  {
    return Nre.getServiceManager().findService(type.getTypeSpec().toString(null));
  }

  /**
   * Get all the services registered for the specified class.
   * The references to the services should never be stored, 
   * but rather looked up everytime they are needed.  This 
   * allows services to be unregistered and re-registered 
   * cleanly.
   *
   * @throws ServiceNotFoundException if no service has
   *    been registered for the specified type.
   */
  public static BComponent[] getServices(Type type)
    throws ServiceNotFoundException
  {
    return Nre.getServiceManager().getServices(type.toString());
  }
          
////////////////////////////////////////////////////////////////
// Type Management
////////////////////////////////////////////////////////////////

  /**
   * Get a Type by its integer identifer.
   *
   * @param id int identifier for Type
   * @return Type for id
   * @throws TypeNotFoundException if no type registered for id
   */
  public static Type getType(int id)
    throws TypeNotFoundException
  {
    return Nre.getSchemaManager().getType(id);
  }

  /**
   * Get a Type by an implementation class.
   *
   * @param cls Class for type implementation
   * @return Type for cls
   * @throws TypeNotFoundException if no type registered for class
   */
  public static Type getType(Class<?> cls)
    throws TypeNotFoundException
  {
    return Nre.getSchemaManager().getType(cls);
  }

  /**
   * A type by its type spec "module:typename".
   */
  public static Type getType(String typeSpec)
    throws ModuleException, TypeException
  {
    return BTypeSpec.make(typeSpec).getResolvedType();
  }
  
  /**
   * Get a list of all the registered types.
   */
  public static Type[] getTypes()
  {
    return Nre.getSchemaManager().getTypes();
  }
  
  /**
   * Load a type for the given class.
   *
   * @param cls Class to use for slot introspection.
   * @return Type which tags the Class with Baja slot meta-data.
   * @throws TypeIntrospectionException if the class 
   *    violates Baja introspection rules.
   */
  public static Type loadType(final Class<?> cls)
  {
    return Nre.getSchemaManager().load(cls);
  }

////////////////////////////////////////////////////////////////
// Security
////////////////////////////////////////////////////////////////  

  /**
   * Get the VM auditor.
   */
  public static Auditor getAuditor()
  {
    return Nre.auditor;
  }

  /**
   * Set the VM auditor.
   */
  public static void setAuditor(Auditor auditor)
  {
    Nre.auditor = auditor;
  }

  /**
   * Get the VM SecurityAuditor
   *
   * @return the SecurityAuditor
   * @since Niagara 4.9
   */
  public static SecurityAuditor getSecurityAuditor()
  {
    return Nre.getSecurityAuditor();
  }
}
