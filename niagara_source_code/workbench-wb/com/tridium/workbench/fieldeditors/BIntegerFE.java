/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.text.DecimalFormat;
import java.text.ParsePosition;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BLong;
import javax.baja.sys.BNumber;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.BTextField;
import javax.baja.ui.pane.BGridPane;
import javax.baja.units.BUnit;
import javax.baja.workbench.CannotSaveException;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BIntegerFE allows viewing and editing of a BInteger and BLong.
 *
 * @author    Brian Frank
 * @creation  22 Jan 01
 * @version   $Revision: 12$ $Date: 11/5/08 4:49:21 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "baja:Integer", "baja:Long" }
  )
)
public class BIntegerFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BIntegerFE(918471914)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BIntegerFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BIntegerFE()
  {
    field.setController(new BFloatFE.Controller());
    BGridPane pane = new BGridPane(3);
    pane.add("prelabel", prelabel);
    pane.add("field", field);
    pane.add("postlabel", postlabel);
    setContent(pane);
    linkTo("lk0", field, BTextField.textModified, setModified);
    linkTo("lk1", field, BTextField.actionPerformed, actionPerformed);
  }

  @Override
  protected void doSetReadonly(boolean readonly)
  {
    field.setEditable(!readonly);
  }

  @Override
  protected void doLoadValue(BObject value, Context cx)
  {
    // int or long?
    if (value instanceof BInteger)
    {
      values = new IntValues(cx);
    }
    else
    {
      values = new LongValues(cx);
    }

    // reset fields
    range = "";
    width = 14;
    String postmeta = "";
    String premeta = "";
    BNumber min = null;
    BNumber max = null;

    // extract context parameters
    if (cx != null)
    {
      BUnit u = (BUnit)cx.getFacet(BFacets.UNITS);
      if (u != null && !u.isNull()) 
      {
        if (u.getIsPrefix())
        {
          premeta = u.getSymbol();
        }
        else
        {
          postmeta = u.getSymbol();
        }
      }
      
      BInteger widthFacet = (BInteger)cx.getFacet(BFacets.FIELD_WIDTH);
      if (widthFacet != null) { width = widthFacet.getInt(); }

      min = (BNumber)cx.getFacet(BFacets.MIN);
      max = (BNumber)cx.getFacet(BFacets.MAX);
    }

    // field width 
    if (width != 14)
    {
      field.setVisibleColumns(width);       
    }  
    
    // radix
    if (values.radix != 10)
    {
      if (!postmeta.isEmpty()) { postmeta += ' '; }
      postmeta += "Radix=" + values.radix;
    }

    // append [min-max]
    if (values.loadFacets(min, max))
    {
      String minStr = (min == null) ? "min" : values.toString(min);
      String maxStr = (max == null) ? "max" : values.toString(max);
      range = '[' + minStr + " - " + maxStr + ']';
      if (!postmeta.isEmpty()) { postmeta += ' '; }
      postmeta += range;
    }

    String s = values.toString(value);
    field.setText(s);
    prelabel.setText(premeta);
    postlabel.setText(postmeta);
  }

  @Override
  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    String s = field.getText();
    BObject saved = values.fromString(s, range);
    field.setText(values.toString(saved));
    return saved;
  }

////////////////////////////////////////////////////////////////
// Values
////////////////////////////////////////////////////////////////

  abstract static class Values
  {
    protected Values(Context cx)
    {
      BFacets facets = (cx == null) ? BFacets.DEFAULT : cx.getFacets();
      
      // init
      radix = facets.geti(BFacets.RADIX, 10);       
      showSeparators = facets.getb(BFacets.SHOW_SEPARATORS, false);
      forceSign = facets.getb(BFacets.FORCE_SIGN, false);
      
      // make format patterns.
      String pattern = showSeparators ? "#,##0" : "#0";
      fromStringFormat = new DecimalFormat(pattern);
 
      pattern = forceSign ? '+' + pattern + ";-" + pattern : pattern;
      toStringFormat= new DecimalFormat(pattern);
    }
    
    abstract boolean loadFacets(BNumber min, BNumber max); // return if range should be specified
    abstract String toString(BObject x);
    abstract BValue fromString(String s, String range) throws CannotSaveException;
    
    final int radix;
    final boolean showSeparators;
    final boolean forceSign;
    final DecimalFormat toStringFormat;
    final DecimalFormat fromStringFormat;
  }

////////////////////////////////////////////////////////////////
// IntValues
////////////////////////////////////////////////////////////////

  static class IntValues extends Values
  {
    public IntValues(Context cx)
    {
      super(cx);
    }

    @Override
    boolean loadFacets(BNumber min, BNumber max)
    {
      if (min != null) { this.min = min.getInt(); }
      if (max != null) { this.max = max.getInt(); }
      return this.min != Integer.MIN_VALUE ||
             this.max != Integer.MAX_VALUE;
    }

    @Override
    String toString(BObject x)
    {
      return toString(((BNumber)x).getInt());
    }

    String toString(int x)
    {
      if (x == Integer.MIN_VALUE) { return "min"; }
      else if (x == Integer.MAX_VALUE) { return "max"; }
      else if (x == 0 || radix != 10) { return Long.toString((long)x & 0xFFFFFFFFL, radix); } // treat anything not base 10 as unsigned int
      else { return toStringFormat.format(x); }
    }

    @Override
    BValue fromString(String s, String range)
      throws CannotSaveException
    {
      int x;
      try
      {
        if ("min".equals(s)) { x = Integer.MIN_VALUE; }
        else if ("max".equals(s)) { x = Integer.MAX_VALUE; }
        else if (radix != 10) { x = (int)Long.parseLong(s, radix); }
        else
        {
          // always pull of "+" for decoding
          String stripped = s.startsWith("+") ? s.substring(1) : s;
          ParsePosition pos = new ParsePosition(0);
          x = fromStringFormat.parse(stripped, pos).intValue();
          if (pos.getIndex() != stripped.length())
          {
            throw new CannotSaveException("Invalid format: " + s);
          }
        }
      }
      catch (CannotSaveException e)
      {
        throw e;
      }
      catch (Exception e)
      {
        throw new CannotSaveException("Invalid format: " + s);
      }

      if (x < min) { throw new CannotSaveException(toString(x) + " < " + toString(min) + ' ' + range); }
      if (x > max) { throw new CannotSaveException(toString(x) + " > " + toString(max) + ' ' + range); }

      return BInteger.make(x);
    }

    int min = Integer.MIN_VALUE;
    int max = Integer.MAX_VALUE;
  }

////////////////////////////////////////////////////////////////
// LongValues
////////////////////////////////////////////////////////////////

  static class LongValues extends Values
  {
    public LongValues(Context cx)
    {
      super(cx);
    }
    
    @Override
    boolean loadFacets(BNumber min, BNumber max)
    {
      if (min != null) { this.min = min.getLong(); }
      if (max != null) { this.max = max.getLong(); }
      return this.min != Long.MIN_VALUE ||
             this.max != Long.MAX_VALUE;
    }

    @Override
    String toString(BObject x)
    {
      return toString(((BNumber)x).getLong());
    }

    String toString(long x)
    {
      if (x == Long.MIN_VALUE) { return "min"; }
      else if (x == Long.MAX_VALUE) { return "max"; }
      else if (x == 0 || radix != 10) { return Long.toString(x, radix); }
      else { return toStringFormat.format(x); }
    }

    @Override
    BValue fromString(String s, String range)
      throws CannotSaveException
    {
      long x;
      try
      {
        if ("min".equals(s)) { x = Long.MIN_VALUE; }
        else if ("max".equals(s)) { x = Long.MAX_VALUE; }
        else if (radix != 10) { x = Long.parseLong(s, radix); }
        else
        {
          // always pull of "+" for decoding
          String stripped = s.startsWith("+") ? s.substring(1) : s;
          ParsePosition pos = new ParsePosition(0);
          x = fromStringFormat.parse(stripped, pos).longValue();
          if (pos.getIndex() != stripped.length())
          {
            throw new CannotSaveException("Invalid format: " + s);
          }
        }
      }
      catch (CannotSaveException e)
      {
        throw e;
      }
      catch (Exception e)
      {
        throw new CannotSaveException("Invalid format: " + s);
      }

      if (x < min) { throw new CannotSaveException(toString(x) + " < " + toString(min) + ' ' + range); }
      if (x > max) { throw new CannotSaveException(toString(x) + " > " + toString(max) + ' ' + range); }

      return BLong.make(x);
    }

    long min = Long.MIN_VALUE;
    long max = Long.MAX_VALUE;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private int width = 14;
  private final BTextField field = new BTextField("", width);
  private final BLabel postlabel = new BLabel("");
  private final BLabel prelabel = new BLabel("");
  private String range = "";
  private Values values;

}
