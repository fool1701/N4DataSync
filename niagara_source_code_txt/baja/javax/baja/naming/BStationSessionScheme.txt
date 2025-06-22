/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * @author    Bill Smith
 * @creation  2 March 2010
 * @version   $Revision: 1$ $Date: 10/1/10 10:02:17 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BStationSessionScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BStationSessionScheme(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStationSessionScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  protected BStationSessionScheme(String schemeId)
  {
    super(schemeId);
  }

  public abstract int getDefaultPort();
}
