/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.enums.*;
import javax.baja.ui.options.*;
import javax.baja.ui.pane.*;
import javax.baja.util.*;

import com.tridium.workbench.fieldeditors.*;
import com.tridium.workbench.fieldeditors.facets.*;

/**
 * BFlexFacetsFE allows viewing and editing of BFacets for FlexSerial.
 *
 * @author    Andy Saunders       
 * @creation  18 Dec 06
 * @version   $Revision$ $Date: 5/19/2005 6:11:00 PM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BFlexFacetsFE
  extends BFacetsFE
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.ui.BFlexFacetsFE(2979906276)1.0$ @*/
/* Generated Thu Sep 30 12:51:30 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexFacetsFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  protected void doLoadValue(BObject facetsValue, Context cx)
  {
    facets = (BFacets)facetsValue;
    super.doLoadValue(facetsValue, cx);
  }  

////////////////////////////////////////////////////////////////
// Events
////////////////////////////////////////////////////////////////

  public void doEditPressed()
  {
    try
    { 
      BFacets temp = BFlexFacetsEditor.open(this, facets, isReadonly());
      if (temp != null)
      {
        loadValue(temp, getCurrentContext());
        setModified();
      }
    }
    catch (Exception e) { BDialog.error(this, "Error", "Error", e); }
  }
  

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private BFacets facets;
}
