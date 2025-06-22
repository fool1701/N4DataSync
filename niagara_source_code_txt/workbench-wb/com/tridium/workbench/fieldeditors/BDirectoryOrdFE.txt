/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.workbench.ord.BDirectoryOrdChooser;

/**
 * This field editor forces ORD selection to be confined to Directory ORD
 * values only.
 *
 * @author 		J. Spangler
 * @creation 	Jun 25, 2010
 * @version		1
 * @since			Niagara 3.5
 *
 */
@NiagaraType
public class BDirectoryOrdFE
    extends BOrdFE
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BDirectoryOrdFE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDirectoryOrdFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public TypeInfo[] loadTypes()
  {
    return new TypeInfo[] { BDirectoryOrdChooser.TYPE.getTypeInfo()};
  }

  protected void doLoadValue(BObject v, Context cx)
  {
    super.doLoadValue(v, cx);
    defaultBrowse.info = BDirectoryOrdChooser.TYPE.getTypeInfo();
  }
}
