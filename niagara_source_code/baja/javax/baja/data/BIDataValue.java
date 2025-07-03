/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.data;

import javax.baja.io.BIEncodable;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIDataValue is the interface implemented by BSimples that
 * are one of the predefined data types in {@link DataTypes}.
 *
 * @author    Brian Frank
 * @creation  21 Feb 03
 * @version   $Revision: 3$ $Date: 5/6/03 4:03:47 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIDataValue
  extends BIEncodable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.data.BIDataValue(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIDataValue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
