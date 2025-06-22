/*
 * Copyright 2008 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform;
 
import javax.baja.platform.security.PlatformSecurityConfigManager;
import javax.baja.platform.tcpip.TcpIpManager;
import javax.baja.platform.time.TimeManager;
import com.tridium.platform.NiagaraLocalPlatform;


/**
 * LocalPlatform provides a simple interface to platform settings
 * for the computer that's running the current Niagara environment
 * 
 * @author    Frank Smith       
 * @creation  14 Jan 05
 * @version   $Revision: 2$ $Date: 3/20/08 11:45:55 AM EDT$
 * @since     Baja 1.0
 */
public abstract class LocalPlatform
{
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  public static LocalPlatform make()
  {
    return NiagaraLocalPlatform.make();
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Return an object that can be used for managing TCP/IP settings
   */
  public abstract TcpIpManager getTcpIpManager();
  
  /**
   * Return an object that can be used for managing time settings
   */
  public abstract TimeManager getTimeManager();

  /**
   * Return an object that can be used for managing local Platform Security settings.
   * @return
   */
  public abstract PlatformSecurityConfigManager getSecurityConfigurationManager();


  /**
   * Restart the running Niagara environment.
   * <br>
   * <b>Note:</b>For some environments, such as Niagara stations running on QNX devices, this will result
   * in the host being rebooted.
   */
  public abstract void restartNiagaraEnvironment();

}
