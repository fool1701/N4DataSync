/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web.mobile;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A Mobile Web Operational View
 *
 * @author		gjohnson
 * @creation 	4 Aug 2011
 * @version 	1
 * @since 		Niagara 3.7
 */
@NiagaraType
public interface BIMobileWebOperationalView
    extends BIMobileWebView
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.mobile.BIMobileWebOperationalView(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:44 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIMobileWebOperationalView.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Return true if the view is operational and can be used.
   * <p>
   * Please note, as any concrete implementations should make this method
   * final as license checks could be performed.
   */
  public boolean isOperational(Context cx);
}
