/*
 * Copyright 2012, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web.mobile;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.web.BWebProfileConfig;

/**
 * An interface implemented by all Themes used with Mobile.
 *
 * @author JJ Frankovich
 * @since Niagara 4.6
 */
@NiagaraType
public interface BIMobileTheme
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.mobile.BIMobileTheme(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:44 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIMobileTheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Override this method to choose the desired bajaux theme. Defaults to Zebra
   */
  default String getBajauxThemeName(BWebProfileConfig mobileConfig)
  {
    return "Zebra";
  }

}
