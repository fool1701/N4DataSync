/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * @author    Lee Adcock
 * @creation  26 Jan 2012
 * @version   $Revision: 2$ $Date: 9/17/07 8:50:34 AM EDT$
 * @since     Niagara 3.7
 */
@NiagaraType
public interface BIAlias
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BIAlias(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIAlias.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  ////////////////////////////////////////////////////////////////
  //Methods
  ////////////////////////////////////////////////////////////////
  
  public BOrd getOrd();
}
