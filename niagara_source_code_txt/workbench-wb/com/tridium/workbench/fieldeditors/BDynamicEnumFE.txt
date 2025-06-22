/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BBrush;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BTextDropDown;
import javax.baja.ui.text.Segment;
import javax.baja.ui.text.TextRenderer;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.sys.schema.Fw;

/**
 * BDynamicEnumFE allows editing of a BDynamicEnum.
 *
 * @author    Brian Frank       
 * @creation  13 Dec 01
 * @version   $Revision: 20$ $Date: 10/19/10 4:51:32 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:DynamicEnum"
  )
)
public class BDynamicEnumFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BDynamicEnumFE(3408357737)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDynamicEnumFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BDynamicEnumFE()
  {    
    combo.getEditor().setRenderer(new Renderer());
    setContent(combo);
    linkTo("lk0", combo, BTextDropDown.valueModified, setModified);
    linkTo("lk1", combo, BTextDropDown.actionPerformed, actionPerformed);
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {
    combo.getEditor().setEditable(!readonly);
    combo.setDropDownEnabled(!readonly);
  }  

  protected void doLoadValue(BObject v, Context cx)
  {
    this.val = (BEnum)v;    
    this.range = val.getRange();
    
    // if range specified via context, then 
    // that trumps range on enum itself
    if (cx != null)
    {
      BEnumRange r = (BEnumRange)cx.getFacet(BFacets.RANGE);
      if (r != null) this.range = r;
    }
    
    // populate list with range
    int maxLen = 5;
    combo.getList().removeAllItems();
    int[] ordinals = range.getOrdinals();
    for(int i=0; i<ordinals.length; ++i)
    {
      int ordinal = ordinals[i];
      String displayTag = range.getDisplayTag(ordinal, null);
      combo.getList().addItem(displayTag);
      maxLen = Math.max(maxLen, displayTag.length());
    }
    combo.getEditor().setVisibleColumns(maxLen);
    combo.setText(range.getDisplayTag(val.getOrdinal(), null)); 
    doSetReadonly(isReadonly());
  }
  
  protected BObject doSaveValue(BObject v, Context cx)
  {                
    int ordinal = saveToOrdinal();
    return BDynamicEnum.make(ordinal, range);
  }               
  
  int saveToOrdinal()
  {
    String tag = combo.getText();
    
    // map tag to ordinal using range
    int[] ordinals = range.getOrdinals();
    for(int i=0; i<ordinals.length; ++i) 
    {
      int ordinal = ordinals[i];
      String displayTag = range.getDisplayTag(ordinal, null);
      if (tag.equals(displayTag))
        return ordinal;
    }
    
    // allow hex entry
    if (tag.startsWith("0x"))
      return Integer.parseInt(tag.substring(2), 16);
    
    return Integer.parseInt(tag);
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
  
  class Renderer extends TextRenderer  
  {
    public BBrush getBackground() 
    { 
      if (bg.isNull()) return super.getBackground();
      return bg;
    }
    
    public BBrush getForeground(Segment seg)
    {
      if (fg.isNull()) return super.getForeground(seg);
      return fg;
    }
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private BTextDropDown combo = new BTextDropDown("", 15, true);
  private BEnum val;
  private BEnumRange range;
  BBrush fg = BBrush.NULL;
  BBrush bg = BBrush.NULL;
  
}
