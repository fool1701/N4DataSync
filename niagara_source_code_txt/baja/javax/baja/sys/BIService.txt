/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.CompletableFuture;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.Nre;
import com.tridium.sys.service.ServiceManager;

/**
 * BIService is the interface implemented by BComponents which
 * wish to be managed by the framework's service registry.
 *
 * @author    Brian Frank
 * @creation  2 May 02
 * @version   $Revision: 4$ $Date: 3/28/05 9:23:10 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIService 
  extends BInterface
{   
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BIService(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Return the types to be registered under.  The service 
   * will be automatically registered during station bootstrap, 
   * and unregistered if unmounted.  The component must extend or 
   * implement all of the types returned by this method.  The 
   * result of this method should be static, that is it should never
   * change across instances nor over time.
   */
  public Type[] getServiceTypes();
  
  /**
   * This method is called during station bootstrap for the service
   * to initialize itself.  At this point the service may lookup
   * other services using {@code Sys.getService()}, but general
   * components have not been started.
   */
  public void serviceStarted()
    throws Exception;

  /**
   * This method is called when a registered service is 
   * unmounted from the namespace.
   */
  public void serviceStopped()
    throws Exception;

  /**
   * If true, {@link #whenServiceStarted()} is expected
   * to be completed for this service by the service itself, otherwise
   * the service manager will complete it after it invokes
   * {@link #serviceStarted()}.
   *
   * @since Niagara 4.0
   */
  default boolean completesStarted() { return false; }

  /**
   * @since Niagara 4.0
   *
   * @return a future that completes when this service's start finishes
   *
   * @throws javax.baja.sys.ServiceNotFoundException if this service is not
   * registered with the service manager, or it is a proxy for a service running
   * elsewhere
   */
  default CompletableFuture<Void> whenServiceStarted()
  {
    return AccessController.doPrivileged((PrivilegedAction<ServiceManager>)() -> Nre.getServiceManager()).defaultWhenServiceStarted(this);
  }

  /**
   * If true, {@link #whenServiceStopped()} is expected
   * to be completed for this service by the service itself, otherwise
   * the service manager will complete it after it
   * invokes {@link #serviceStopped()}.
   *
   * @since Niagara 4.0
   */
  default boolean completesStopped() { return false; }

  /**
   * @since Niagara 4.0
   *
   * @return a future that completes when this service's stop finishes
   *
   * @throws javax.baja.sys.ServiceNotFoundException if this service is not
   * registered with the service manager, or it is a proxy for a service running
   * elsewhere
   */
  default CompletableFuture<Void> whenServiceStopped()
  {
    return AccessController.doPrivileged((PrivilegedAction<ServiceManager>)() -> Nre.getServiceManager()).defaultWhenServiceStopped(this);
  }
}
