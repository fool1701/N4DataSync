/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control.ext;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BNullProxyExt is the default implement BAbstractProxyExt
 * used to indicate that a point is not a proxy.
 *
 * @author    Brian Frank
 * @creation  1 May 02
 * @version   $Revision: 7$ $Date: 3/28/05 11:40:33 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public final class BNullProxyExt
  extends BAbstractProxyExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.ext.BNullProxyExt(2979906276)1.0$ @*/
/* Generated Wed Jan 26 11:36:16 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNullProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  public boolean isNull() { return true; }
  
  public String toString(Context cx) { return "null"; }
  
}
