/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * This interface imposes a total ordering on the objects of each 
 * class that implements it.
 *
 * @author    Brian Frank
 * @creation  12 Dec 02
 * @version   $Revision: 3$ $Date: 3/11/03 4:20:36 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIComparable
  extends BInterface, java.lang.Comparable<Object>
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BIComparable(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIComparable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
