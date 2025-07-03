/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BSize;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BDouble;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.pane.BGridPane;
import javax.baja.util.Lexicon;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BSizeFE 
 *
 * @author    Mike Jarmy
 * @creation  20 Jan 03
 * @version   $Revision: 9$ $Date: 5/24/05 3:19:11 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "gx:Size"
  )
)
public class BSizeFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BSizeFE(2593098671)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSizeFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BSizeFE()
  {
    BGridPane pane = new BGridPane(2);
    pane.add("lbl0", new BLabel(text("width")));  pane.add("fld0", widthFE);
    pane.add("lbl1", new BLabel(text("height"))); pane.add("fld1", heightFE);
    
    linkTo(widthFE,  BFloatFE.setModified, setModified);
    linkTo(heightFE, BFloatFE.setModified, setModified);
    
    setContent(pane);
  }

  protected void doSetReadonly(boolean readonly)
  {
    widthFE.setReadonly(readonly);
    heightFE.setReadonly(readonly);
  }
  
  protected void doLoadValue(BObject value, Context cx)
  {
    BSize dim = (BSize) value;
    widthFE .loadValue(BDouble.make(dim.width), cx);
    heightFE.loadValue(BDouble.make(dim.height), cx);
  }

  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    return BSize.make(
      ((BDouble) widthFE.saveValue()).getDouble(),
      ((BDouble) heightFE.saveValue()).getDouble());
  }

////////////////////////////////////////////////////////////////
// Attribs
////////////////////////////////////////////////////////////////

  static Lexicon lex = Lexicon.make("workbench");
  static String text(String s) { return lex.getText("sizeFE." + s); }

  private BFloatFE widthFE = new BFloatFE();
  private BFloatFE heightFE = new BFloatFE();
}
