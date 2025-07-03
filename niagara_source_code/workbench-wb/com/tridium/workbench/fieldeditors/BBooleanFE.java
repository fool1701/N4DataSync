/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BBrush;
import javax.baja.gx.BImage;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIBoolean;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.list.ListRenderer;
import javax.baja.util.BFormat;
import javax.baja.util.LexiconModule;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.sys.schema.Fw;

/**
 * BBooleanFE allows viewing and editing of a BBoolean 
 * using a combo box.
 *
 * @author    Brian Frank       
 * @creation  22 Jan 01
 * @version   $Revision: 13$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:Boolean"
  )
)
public class BBooleanFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BBooleanFE(2443993093)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBooleanFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  public BBooleanFE()
  {
    BListDropDown combo = new BListDropDown();
    combo.getList().setRenderer(new Renderer());
    setContent(combo);
    linkTo("lk0", combo, BListDropDown.valueModified, setModified);
    linkTo("lk1", combo, BListDropDown.actionPerformed, actionPerformed);
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////
  
  protected void doSetReadonly(boolean readonly)
  {
    BListDropDown combo = (BListDropDown)getContent();
    combo.setDropDownEnabled(!readonly);
  }  

  @Override
  protected void doLoadValue(BObject value, Context cx)
  {
    BImage trueIcon = null;
    BImage falseIcon  = null;
    if (cx != null)
    {
      BObject t = cx.getFacet(BFacets.TRUE_TEXT);
      BObject f = cx.getFacet(BFacets.FALSE_TEXT);
      if (t != null) trueText = BFormat.format(t.toString(), null, cx);
      if (f != null) falseText = BFormat.format(f.toString(), null, cx);

      BObject ti = cx.getFacet(TRUE_ICON_FACET_KEY );
      BObject fi = cx.getFacet(FALSE_ICON_FACET_KEY );
      if (ti != null) trueIcon = BImage.make(BFormat.format(ti.toString(), null, cx));
      if (fi != null) falseIcon = BImage.make(BFormat.format(fi.toString(), null, cx));
    }

    if(trueIcon == null)
    {
      trueIcon = BImage.make(LEX.getText(TRUE_ICON_LEX_KEY, cx));
    }

    if(falseIcon == null)
    {
      falseIcon = BImage.make(LEX.getText(FALSE_ICON_LEX_KEY, cx));
    }



    boolean b = ((BIBoolean) value).getBoolean();
    
    BListDropDown combo = (BListDropDown)getContent();
    combo.getList().removeAllItems();
    combo.getList().addItem(falseIcon, falseText);
    combo.getList().addItem(trueIcon, trueText);
    combo.setSelectedItem(b ? trueText : falseText);
  }
  
  protected BObject doSaveValue(BObject value, Context cx)
  {
    BListDropDown combo = (BListDropDown)getContent();
    boolean b = combo.getSelectedItem().equals(trueText);
    return BBoolean.make(b);
  }

////////////////////////////////////////////////////////////////
// Fw
////////////////////////////////////////////////////////////////

  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.UPDATE_COLORS:
        fg = (BBrush)a;
        bg = (BBrush)b;
        repaint();
        return null;
    }
    return super.fw(x, a, b, c, d);
  }

////////////////////////////////////////////////////////////////
// Renderer
////////////////////////////////////////////////////////////////
  
  class Renderer extends ListRenderer  
  {
    public BBrush getBackground(Item item)
    {
      if (bg.isNull()) return super.getBackground(item);
      return bg;
    }
    
    public BBrush getForeground(Item item)
    {
      if (fg.isNull()) return super.getForeground(item);
      return fg;
    }
  }
    
////////////////////////////////////////////////////////////////
// Private
////////////////////////////////////////////////////////////////

  private static final LexiconModule LEX = LexiconModule.make("baja");
  private static final String TRUE_ICON_FACET_KEY = "trueIcon";
  private static final String FALSE_ICON_FACET_KEY = "falseIcon";
  private static final String TRUE_ICON_LEX_KEY = "true.icon";
  private static final String FALSE_ICON_LEX_KEY = "false.icon";
  String trueText = BBoolean.toString(true, null);
  String falseText = BBoolean.toString(false, null);
  BBrush fg = BBrush.NULL;
  BBrush bg = BBrush.NULL;
}
