/*
 * Copyright 2008, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BTextField;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * A readonly editor to be used for display purposes.  It displays the field value
 * in a readonly field using the toString() value for the text.  To use it
 * the fieldEditor facet must be set to workbench:ToStringFE on the property in
 * the parent.
 * 
 * @author    John Sublett
 * @creation  07 Jan 2008
 * @version   $Revision: 1$ $Date: 1/7/08 11:12:35 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BToStringFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BToStringFE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BToStringFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  public void doLoadValue(BObject o, Context cx)
  {
    int fieldWidth = 40;
    BInteger fw = (BInteger)cx.getFacet(BFacets.FIELD_WIDTH);
    if (fw != null) fieldWidth = Math.max(fw.getInt(), 1);

    BTextField field = new BTextField(o.toString(cx), fieldWidth);
    field.setEditable(false);
    setContent(field);
  }




}
