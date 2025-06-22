/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.kiosk;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BFrame;

import com.tridium.workbench.kiosk.BKioskService;

/**
 * BKioskSplash is the base class for building kiosk login screens.
 *
 * @author    Brian Frank
 * @creation  14 Nov 06
 * @version   $Revision: 1$ $Date: 11/15/06 11:16:46 AM EST$
 * @since     Niagara 3.2
 */
@NiagaraType
public abstract class BKioskSplash
  extends BFrame
{          
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.kiosk.BKioskSplash(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BKioskSplash.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    
  /**
   * Log into the kiosk with the specified username and password.
   * Return true if logged on successfully (in which case this
   * screen is automatically closed) or false if logon fails.
   */
  public boolean login(String username, String password)
    throws Exception
  {                        
    BKioskService service = (BKioskService)Sys.getService(BKioskService.TYPE);
    boolean success = service.login(username, password);
    if (success) close();
    return success;
  }

}
