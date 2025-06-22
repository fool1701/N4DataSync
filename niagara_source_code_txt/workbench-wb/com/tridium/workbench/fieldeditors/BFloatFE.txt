/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BBrush;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFloat;
import javax.baja.sys.BInteger;
import javax.baja.sys.BNumber;
import javax.baja.sys.BObject;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.BTextField;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.text.BTextEditor;
import javax.baja.ui.text.Segment;
import javax.baja.ui.text.TextController;
import javax.baja.ui.text.TextRenderer;
import javax.baja.units.BUnit;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.sys.schema.Fw;
import com.tridium.ui.KeyboardLayout;
import com.tridium.ui.UiEnv;
import com.tridium.workbench.util.FloatingPointHelper;


/**
 * BFloatFE allows viewing and editing of a BFloat and a BDouble.
 *
 * @author    Brian Frank
 * @creation  22 Jan 01
 * @version   $Revision: 18$ $Date: 11/5/08 4:49:21 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "baja:Float", "baja:Double" }
  )
)
public class BFloatFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BFloatFE(2527294369)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFloatFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BFloatFE()
  {
    field.setRenderer(new Renderer());
    field.setController(new Controller());

    BGridPane pane = new BGridPane(3);
    pane.add("pre", prelabel);
    pane.add("field", field);
    pane.add("post", postlabel);
    setContent(pane);
    linkTo("lk0", field, BTextField.textModified, setModified);
    linkTo("lk1", field, BTextField.actionPerformed, actionPerformed);
  }

////////////////////////////////////////////////////////////////
// WbEditor
////////////////////////////////////////////////////////////////

  @Override
  protected void doSetReadonly(boolean readonly)
  {
    field.setEditable(!readonly);
  }

  @Override
  protected void doLoadValue(BObject value, Context cx)
  {
    isFloat = (value instanceof BFloat);
    helper = FloatingPointHelper.makeFor(value, cx);
    width = 10;
    double v = ((BNumber)value).getDouble();
    
    if (cx != null)
    {
      BInteger widthFacet = (BInteger)cx.getFacet(BFacets.FIELD_WIDTH);
      if (widthFacet != null) { width = widthFacet.getInt(); }
    }
    
    if (width != 10)
    {
      field.setVisibleColumns(width);
    }
    
    // build meta label "units [min-max]"
    String postmeta = "";
    String premeta = "";
    BUnit displayUnits = helper.getDisplayUnits();
    if (displayUnits != null) 
    {
      if (displayUnits.getIsPrefix())
      {
        premeta = displayUnits.getSymbol();
      }
      else
      {
        postmeta = displayUnits.getSymbol();
      }
    }

    range = helper.getRangeDisplay();
    if (!range.isEmpty())
    {
      if (!postmeta.isEmpty()) { postmeta += ' '; }
      postmeta += range;
    }
    
    // update widgets
    field.setText(helper.format(v));
    prelabel.setText(premeta);
    postlabel.setText(postmeta);
  }

  @Override
  protected BObject doSaveValue(BObject value, Context cx)
  throws Exception
  {
    String text = field.getText();
    double v = helper.parseAndValidate(text);
    field.setText(helper.format(v));
    return isFloat ? BFloat.make((float) v) : BDouble.make(v);
  }

  @Override
  public void requestFocus()
  {
    field.requestFocus();
  }

////////////////////////////////////////////////////////////////
// Fw
////////////////////////////////////////////////////////////////

  @Override
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
    @Override
    public BBrush getBackground()
    {
      if (bg.isNull()) { return super.getBackground(); }
      return bg;
    }

    @Override
    public BBrush getForeground(Segment seg)
    {
      if (fg.isNull()) { return super.getForeground(seg); }
      return fg;
    }
  }
  
////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////

  public static final class Controller extends TextController
  {
    @Override
    public void mouseReleased(BMouseEvent event)
    {
      BTextEditor editor = getEditor();
      if (!UiEnv.get().hasKeyboard() && editor.isEditable())
      {
        UiEnv.get().input(editor, numberpad);
      }
    }

    private static final BOrd defNumberpad = 
      BOrd.make("module://bajaui/com/tridium/ui/numberpad.xml");
    private static KeyboardLayout numberpad;

    static
    {
      String lang = Sys.getLanguage();
      try
      {
        if ("en".equals(lang))
        {
          numberpad = KeyboardLayout.make(defNumberpad);
        }
        else
        {
          numberpad = KeyboardLayout.make(
            BOrd.make("file:~lexicon/" + lang + "/numberpad.xml"));
        }
      }
      catch(Exception x)
      {
        try
        {
          // bummer - no numberpad defined, try default full keyboard
          numberpad = KeyboardLayout.make();
        }
        catch(Exception xx)
        {
          // give up.
          throw new BajaRuntimeException(xx);
        }
      }
    }
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private int width = 10;
  private final BTextField field = new BTextField("", width);
  private final BLabel postlabel = new BLabel("");
  private final BLabel prelabel = new BLabel("");
  private String range = "";
  private boolean isFloat = true;
  BBrush fg = BBrush.NULL;
  BBrush bg = BBrush.NULL;
  private FloatingPointHelper helper;
}
