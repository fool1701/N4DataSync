/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.point;

import javax.baja.driver.point.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.rdb.BRdbms;
import javax.baja.sys.*;

/**
 * BRdbmsPointDeviceExt is the Rdb implementation
 * of BPointDeviceExt.
 *
 * @author    Lee Adcock
 * @creation  18 Dec 07
 * @version   $Revision: 2$ $Date: 6/22/10 1:28:17 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BRdbmsPointDeviceExt
  extends BPointDeviceExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.point.BRdbmsPointDeviceExt(2979906276)1.0$ @*/
/* Generated Sat Jan 29 17:54:41 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRdbmsPointDeviceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public Type getDeviceType()
  {
    return BRdbms.TYPE;
  }

  public Type getProxyExtType()
  {
    return BRdbmsProxyExt.TYPE;
  }

  public Type getPointFolderType()
  {
    return BRdbmsPointQuery.TYPE;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public boolean isChildLegal(BComponent child)
  {
    return (child.getType().equals(BRdbmsPointQuery.TYPE));
  }

}
