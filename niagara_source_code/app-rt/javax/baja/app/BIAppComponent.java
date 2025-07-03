/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.app;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInterface;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Interface for all App related Components
 *
 * @author		gjohnson
 * @creation 	11 Aug 2011
 * @version 	1
 * @since 		Niagara 3.7
 */
@NiagaraType
public interface BIAppComponent
    extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.app.BIAppComponent(2979906276)1.0$ @*/
/* Generated Fri Jan 14 13:34:19 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIAppComponent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Return the App Display Name.
   */
  public String getAppDisplayName(Context cx);
  
  /**
   * Return the App Display Icon.
   */
  public BIcon getAppDisplayIcon();
}
