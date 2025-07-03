/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BBrush;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BNumber;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.pane.BTextEditorPane;
import javax.baja.ui.text.BTextEditor;
import javax.baja.ui.text.Segment;
import javax.baja.ui.text.TextRenderer;
import javax.baja.workbench.CannotSaveException;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.sys.schema.Fw;

/**
 * BStringFE allows viewing and editing of a BString 
 * using a text field.
 * <p>
 * Supports the {@link BIFieldValidator#VALIDATOR_FACET} facet.
 *
 * @author    Brian Frank       
 * @creation  22 Jan 01
 * @version   $Revision: 8$ $Date: 9/22/09 4:24:00 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:String"
  )
)
public class BStringFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BStringFE(3828781905)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStringFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  
  protected void doSetReadonly(boolean readonly)
  {
    if (text != null) text.setEditable(!readonly);
  }  

  protected void doLoadValue(BObject value, Context cx)
  {
    if (text == null)
    {
      boolean multiLine = false;
      int fieldWidth = 40;
      min = 0;
      max = Integer.MAX_VALUE;
      
      if (cx != null)
      {
        BBoolean m = (BBoolean)cx.getFacet(BFacets.MULTI_LINE);
        if (m != null) multiLine = m.getBoolean();
        
        BNumber maxn = (BNumber)cx.getFacet(BFacets.MAX);
        if (maxn != null) max = maxn.getInt();
        
        BNumber minn = (BNumber)cx.getFacet(BFacets.MIN);
        if (minn != null) min = minn.getInt();
        
        BInteger fw = (BInteger)cx.getFacet(BFacets.FIELD_WIDTH);
        if (fw != null) fieldWidth = Math.max(fw.getInt(), 1);
        
        BBoolean slotName = (BBoolean)cx.getFacet("mustBeSlotName");
        if (slotName != null) mustBeSlotName = slotName.getBoolean();
      }
      
      if (multiLine)
      {
        BTextEditorPane pane = new BTextEditorPane(10,fieldWidth);
        this.text = pane.getEditor();
        this.text.setRenderer(new Renderer());
        setEditor(pane);
      }
      else
      {
        BTextField field = new BTextField("", fieldWidth);
        linkTo(field, BTextField.actionPerformed, actionPerformed);
        this.text = field;
        this.text.setRenderer(new Renderer());
        setEditor(text);
      }      
      
      linkTo(text, BTextEditor.textModified, setModified);
      text.setEditable(!isReadonly());
    }
    
    text.setText(value.toString());
  }
  
  protected BObject doSaveValue(BObject value, Context cx)
    throws CannotSaveException
  {
    String s = text.getText();
    if (s.length() < min) throw new CannotSaveException("length " + s.length() + " < " + min);
    if (s.length() > max) throw new CannotSaveException("length " + s.length() + " > " + max);
    if (mustBeSlotName) SlotPath.verifyValidName(s);
    return BString.make(s);
  }          
  
  protected void setEditor(BWidget editor)
  {           
    setContent(editor);
  }

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
  
  BTextEditor text;
  int min = 0;
  int max = Integer.MAX_VALUE;
  boolean mustBeSlotName = false;
  BBrush fg = BBrush.NULL;
  BBrush bg = BBrush.NULL;
  
}
