/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.ui;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.HashMap;

import javax.baja.gx.BBrush;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
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
import javax.baja.units.BUnitConversion;
import javax.baja.workbench.CannotSaveException;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.sys.schema.Fw;
import com.tridium.ui.KeyboardLayout;
import com.tridium.ui.UiEnv;
// for inner-class Controller


/**
 * BAoDefaultValueFE allows viewing and editing of a BFloat and a BDouble supporting a "hold" value.
 *
 * @author    Andy Saunders
 * @creation  28 Oct 16
 */
@NiagaraType
public class BAoDefaultValueFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.ui.BAoDefaultValueFE(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:26 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAoDefaultValueFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BAoDefaultValueFE()
  {
    field.setRenderer(new Renderer());
    field.setController(new BAoDefaultValueFE.Controller());

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

  protected void doSetReadonly(boolean readonly)
  {
    field.setEditable(!readonly);
  }
  
  protected void doLoadValue(BObject value, Context cx)
  {
    // init
    isFloat = (value instanceof BFloat);
    if (isFloat)
    {
      MIN = Float.NEGATIVE_INFINITY;
      MAX = Float.POSITIVE_INFINITY;
    }
    else
    {
      MIN = Double.NEGATIVE_INFINITY;
      MAX = Double.POSITIVE_INFINITY;
    }
    min = MIN;
    max = MAX;
    width = 10;
    double v = ((BNumber)value).getDouble();
    realUnits = null;
    int prec = 2;
    boolean showSeparators = false;
    forceSign = false;
    
    // extract facets
    int convert = com.tridium.sys.Nre.unitConversion;
    if (cx != null)
    {
      BFacets facets = cx.getFacets();
      
      realUnits = (BUnit)cx.getFacet(BFacets.UNITS);
      if (realUnits != null && realUnits.isNull()) realUnits = null;
      
      BInteger widthFacet = (BInteger)cx.getFacet(BFacets.FIELD_WIDTH);
      if (widthFacet != null) width = widthFacet.getInt();
      
      BNumber minFacet = (BNumber)cx.getFacet(BFacets.MIN);
      if (minFacet != null) min = (isFloat) ? minFacet.getFloat() : minFacet.getDouble();
      
      BNumber maxFacet = (BNumber)cx.getFacet(BFacets.MAX);
      if (maxFacet != null) max = (isFloat) ? maxFacet.getFloat() : maxFacet.getDouble();
      
      convert = cx.getFacets().geti(BFacets.UNIT_CONVERSION, convert);
      prec = facets.geti(BFacets.PRECISION, prec);
      showSeparators = facets.getb(BFacets.SHOW_SEPARATORS, showSeparators);
      forceSign = facets.getb(BFacets.FORCE_SIGN, forceSign);
    }
    
    //set width
    if (width != 10)
    {
      field.setVisibleColumns(width);
    }
    
    // set formatter
    format = getFormat(prec, showSeparators);
    
    // check if we need to perform auto-convert
    displayUnits = realUnits;
    if (convert != 0 && realUnits != null)
    {
      BUnitConversion c = BUnitConversion.make(convert);
      displayUnits = c.getDesiredUnit(realUnits);
      if (displayUnits != realUnits)
      {
        v     = realUnits.convertTo(displayUnits, v);
        min   = realUnits.convertTo(displayUnits, min);
        max   = realUnits.convertTo(displayUnits, max);
      }
    }
    
    // build meta label "units [min-max]"
    String postmeta = "";
    String premeta = "";
    if (displayUnits != null) 
    {
      if(displayUnits.getIsPrefix())
        premeta = displayUnits.getSymbol();
      else
        postmeta = displayUnits.getSymbol();
    }
    if (min != MIN || max != MAX)
    {
      range = '[' + toString(min) + " - " + toString(max) + ']';
      if (postmeta.length() > 0) postmeta += ' ';
      postmeta += range;
    }
    
    // update widgets
    field.setText(toString(v));
    prelabel.setText(premeta);
    postlabel.setText(postmeta);
  }
  
  protected BObject doSaveValue(BObject value, Context cx)
  throws Exception
  {
    // decode into a double
    String text = field.getText();
    double v = 0;
    try
    {
      text = TextUtil.toLowerCase(text);
      if (text.equals("-inf")) v = Double.NEGATIVE_INFINITY;
      else if (text.equals("+inf")) v = Double.POSITIVE_INFINITY;
      else if (text.equals("nan")) v = Double.NaN;
      else if (text.equals("hold")) v = Double.NaN;
      else
      {      
        // strip off leading '+' before continuing.
        String stripped = text.startsWith("+") ? text.substring(1) : text;
        ParsePosition pos = new ParsePosition(0);
        if (stripped.indexOf('e') != -1) 
          v = fSN.parse(TextUtil.toUpperCase(stripped), pos).doubleValue();
        else 
          v = format.parse(stripped, pos).doubleValue();
        if (pos.getIndex() != stripped.length())
          throw new CannotSaveException("Invalid format: " + text);
      }
    }
    catch(NumberFormatException nfe)
    {
      throw new CannotSaveException("Invalid format: " + nfe.getMessage());
    }
    catch (Exception e)
    {
      throw new CannotSaveException("Invalid format: " + text);
    }
    
    // check min and max
    if (v < min) throw new CannotSaveException(toString(v) + " < " + toString(min) + " " + range);
    if (v > max) throw new CannotSaveException(toString(v) + " > " + toString(max) + " " + range);
    
    // auto-convert back to real units
    if (realUnits != displayUnits)
    {
      v = displayUnits.convertTo(realUnits, v);
    }
    
    BObject savedValue = isFloat 
      ? BFloat.make((float)v)
      : BDouble.make(v);
    field.setText(toString(v));
    return savedValue;
  }

  private String toString(double d)
  {
    if (d == MIN) return "-inf";
    if (d == MAX) return "+inf";
    if (isFloat && Float.isNaN((float)d)) return "hold";
    if (!isFloat && Double.isNaN(d)) return "hold";
    
    String prefix = (forceSign && (d>0.0d)) ? "+" : "";
    if (Math.IEEEremainder(d, 1) == 0)
    {
      if((long)(Math.abs(d)) >= MAX_LONG_PREC)
        return prefix + fSN.format(d);
      else
        return prefix + format.format(d);
    }
    
    // don't put a sign in front of zero ever.
    return (d == 0.0d) ? format.format(d) : prefix + format.format(d);
  }

  public void requestFocus()
  {
    field.requestFocus();
  }

////////////////////////////////////////////////////////////////
// Format
////////////////////////////////////////////////////////////////

  private static DecimalFormat getFormat(int precision, boolean showSeparators)
  {
    String key = "" + precision + showSeparators;
    DecimalFormat formatter = formatters.get(key);
    if (formatter == null)
    {
      StringBuilder pattern = new StringBuilder(16);

      if (showSeparators)
        pattern.append("#,##0");
      else
        pattern.append("#0");

      if (precision > 0)
      {
        pattern.append('.');
        for(int i=0; i<precision; ++i) pattern.append('0');
      }
      formatter = new DecimalFormat(pattern.toString());
      formatters.put(key, formatter);
    }
    return formatter;
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
// Controller
////////////////////////////////////////////////////////////////

  public static final class Controller extends TextController
  {
    public void mouseReleased(BMouseEvent event)
    {
      BTextEditor editor = getEditor();
      if (!UiEnv.get().hasKeyboard() && editor.isEditable())
      {
        UiEnv.get().input(editor, numberpad);
        return;
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
        if(lang.equals("en"))
          numberpad = KeyboardLayout.make(defNumberpad);
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

  private static HashMap<String, DecimalFormat> formatters = new HashMap<>();
  private static DecimalFormat fSN = new DecimalFormat("0.###############E0");
  
  // this is the largest whole number that can be represented by a double
  // without losing any precision.  If you add one to a double that contains
  // this value, you will get this value back.
  private static final long MAX_LONG_PREC = 9007199254740992L;

  private int width = 10;
  private boolean forceSign = false;
  private BTextField field = new BTextField("", width);
  private BLabel postlabel = new BLabel("");
  private BLabel prelabel = new BLabel("");
  private String range = "";
  private DecimalFormat format = getFormat(2, false);
  private boolean isFloat = true;
  private BUnit realUnits;
  private BUnit displayUnits;
  private double min = Double.NEGATIVE_INFINITY;
  private double max = Double.POSITIVE_INFINITY;
  private double MIN = Double.NEGATIVE_INFINITY;
  private double MAX = Double.POSITIVE_INFINITY;
  BBrush fg = BBrush.NULL;
  BBrush bg = BBrush.NULL;
}
