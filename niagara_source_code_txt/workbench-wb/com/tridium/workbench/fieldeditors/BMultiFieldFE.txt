/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BBrush;
import javax.baja.gx.BFont;
import javax.baja.gx.BImage;
import javax.baja.gx.BInsets;
import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BMonth;
import javax.baja.sys.Flags;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BSpinnerButton;
import javax.baja.ui.BWidget;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.event.BFocusEvent;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.event.BWidgetEvent;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.ui.theme.TextFieldTheme;
import com.tridium.ui.theme.Theme;
import com.tridium.workbench.util.BDatePickerCalendar;

/**
 * BMultiFieldFE allows viewing and editing of
 * multiple time fields.
 *
 * NOTE:  You must set the fields class in your
 *   constructor!!!!!!
 *
 * @author    Brian Frank
 * @creation  18 Jul 01
 * @version   $Revision: 33$ $Date: 6/28/11 1:23:39 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraAction(
  name = "increment"
)
@NiagaraAction(
  name = "decrement"
)
@NiagaraAction(
  name = "showDatePicker"
)
public abstract class BMultiFieldFE
  extends BWbFieldEditor
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BMultiFieldFE(2281188380)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "increment"

  /**
   * Slot for the {@code increment} action.
   * @see #increment()
   */
  public static final Action increment = newAction(0, null);

  /**
   * Invoke the {@code increment} action.
   * @see #increment
   */
  public void increment() { invoke(increment, null, null); }

  //endregion Action "increment"

  //region Action "decrement"

  /**
   * Slot for the {@code decrement} action.
   * @see #decrement()
   */
  public static final Action decrement = newAction(0, null);

  /**
   * Invoke the {@code decrement} action.
   * @see #decrement
   */
  public void decrement() { invoke(decrement, null, null); }

  //endregion Action "decrement"

  //region Action "showDatePicker"

  /**
   * Slot for the {@code showDatePicker} action.
   * @see #showDatePicker()
   */
  public static final Action showDatePicker = newAction(0, null);

  /**
   * Invoke the {@code showDatePicker} action.
   * @see #showDatePicker
   */
  public void showDatePicker() { invoke(showDatePicker, null, null); }

  //endregion Action "showDatePicker"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMultiFieldFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BMultiFieldFE()
  {
    spinner = new BSpinnerButton();
    add("spinner", spinner, Flags.TRANSIENT | Flags.HIDDEN);
    linkTo("incrementLink", spinner, BSpinnerButton.increment, increment);
    linkTo("decrementLink", spinner, BSpinnerButton.decrement, decrement);
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Add and setup the DatePicker button.
   */
  protected void addDatePicker()
  {
    if (get("picker") != null) return;

    datePickerButton = new BButton(calendarIcon);
    ((BButton)datePickerButton).setFocusTraversable(false);
    ((BButton)datePickerButton).setButtonStyle(BButtonStyle.toolBar);
    add("picker", datePickerButton);
    linkTo("editButtonLink", datePickerButton, BButton.actionPerformed, showDatePicker);
  }

  /**
   * The layout changes based on readonly state
   * so relayout when the reaonly state changes.
   */
  protected void doSetReadonly(boolean readonly)
  {
    relayout();
  }

  public void computePreferredSize()
  {
    BFont font = Theme.textField().getFont();
    StringBuilder buf = new StringBuilder();
    
    for(int i=0; i<fields.length; ++i)
    {
      buf.append(fields[i].string());
    }
    
    double tw = font.width(buf.toString());

    BInsets insets = Theme.textField().getInsets();
    double pw = tw + insets.left + insets.right + Theme.spinner().getSpinnerWidth();
    double ph = font.getHeight() - textViewFix + insets.top +  insets.bottom;
    if (datePickerButton != null && !isReadonly())
    {
      datePickerButton.computePreferredSize();
      pw += datePickerButton.getPreferredWidth() + 5;
      ph = Math.max(ph, datePickerButton.getPreferredHeight());
    }
    setPreferredSize(pw, ph);
  }

  public void doLayout(BWidget[] kids)
  {
    computePreferredSize();
    BFont font = Theme.textField().getFont();
    BInsets insets = Theme.textField().getInsets();
    this.prefHeight = getPreferredHeight();

    if (isReadonly())
    {
      spinner.setVisible(false);
      spinner.setBounds(-1, -1, 0, 0);
      if (datePickerButton != null)
      {
        datePickerButton.setVisible(false);
        datePickerButton.setBounds(-1, -1, 0, 0);
      }
    }
    else
    {
      double w = getWidth();
      double h = getHeight();
      double sw = Theme.spinner().getSpinnerWidth();
      double dpw = 0;
      double th = font.getHeight() - textViewFix + insets.top +  insets.bottom;
      if (datePickerButton != null)
      {
        datePickerButton.computePreferredSize();
        dpw = datePickerButton.getPreferredWidth();
        datePickerButton.setVisible(true);
        datePickerButton.setBounds(w-dpw, 0, dpw, Math.min(h, prefHeight));
      }
      spinner.setVisible(true);
      spinner.setBounds(w-sw-((dpw == 0) ? 0 : dpw+5), 0, sw, Math.min(th, prefHeight));
    }
  }
  
  int fieldAt(double x, double y)
  {
    TextFieldTheme theme = Theme.textField();
    double currentX = theme.getInsets().left;
    BFont font = theme.getFont();

    for(int i=0; i<fields.length; ++i)
    {
      String str = fields[i].string();
      currentX += font.width(str);
      if (currentX > x) return i;
    }
    return -1;
  }

////////////////////////////////////////////////////////////////
// Paint
////////////////////////////////////////////////////////////////

  public void paint(Graphics g)
  {
    TextFieldTheme theme = Theme.textField();
    BFont font = theme.getFont();
    BInsets insets = theme.getInsets();
    double w = getWidth();
    double h = prefHeight;
    double tw = w - spinner.getWidth();
    double th = font.getHeight() - textViewFix + insets.top +  insets.bottom;
    boolean readonly = isReadonly();
    boolean focus = hasFocus();


    if (datePickerButton != null)
      tw -= datePickerButton.getWidth() + 5;

    BBrush fg = theme.getTextBrush(this);
    BBrush bg = theme.getControlBackground(this);

    BBrush fgSel = theme.getSelectionForeground(this);
    BBrush bgSel= theme.getSelectionBackground(this);

    g.setFont(font);
    g.setBrush(bg);
    g.fillRect(0, 0, tw, th);
    g.setBrush(fg);

    double tx = insets.left;
    double ty = th - insets.bottom - 2;

    if (paintNull && isReadonly())
    {
      g.drawString("null", tx, ty);
    }
    else
    {
      for(int i=0; i<fields.length; ++i)
      {
        Field f = fields[i];
        String str = f.string();
        if (str == null) str = "?";
        double strWidth = font.width(str);

        if (i == selection && focus)
        {
          g.setBrush(bgSel);
          g.fillRect(tx, insets.top, strWidth, th-insets.top-insets.bottom);
          g.setBrush(fgSel);
        }

        g.drawString(str, tx, ty);
        tx += strWidth;
        g.setBrush(fg);
      }
    }

    Theme.textField().paintBorder(g, this, tw, th);
    paintChildren(g);
  }
  
  public String getStyleSelector() { return "text-editor"; }

////////////////////////////////////////////////////////////////
// Mouse Events
////////////////////////////////////////////////////////////////

  public void mousePressed(BMouseEvent event)
  {
    super.mousePressed(event);
    if (isReadonly()) return;
    requestFocus();
    int f = fieldAt(event.getX(), event.getY());
    if (f != -1 && fields[f].isEditable())
      setSelection(f);
  }

////////////////////////////////////////////////////////////////
// Key Events
////////////////////////////////////////////////////////////////

  public void keyTyped(BKeyEvent event)
  {
    int key = event.getKeyChar();
    if (key == '\n' || key == '\r')
    {
      fireActionPerformed(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, this));
      event.consume();
    }
    else if (fields[selection].keyTyped(key))
    {
      event.consume();
    }
  }

  public void keyPressed(BKeyEvent event)
  {
    if (event.getModifiersEx() != 0) return;
    int s = selection;
    switch(event.getKeyCode())
    {
      case BKeyEvent.VK_RIGHT:
        do
        {
          s++; if (s >= fields.length) s = 0;
        }
        while(!fields[s].isEditable());
        setSelection(s);
        event.consume();
        break;
      case BKeyEvent.VK_LEFT:
        do
        {
          s--; if (s < 0) s = fields.length -1;
        }
        while(!fields[s].isEditable());
        setSelection(s);
        event.consume();
        break;
      case BKeyEvent.VK_UP:
        doIncrement();
        event.consume();
        break;
      case BKeyEvent.VK_DOWN:
        doDecrement();
        event.consume();
        break;
    }
  }

////////////////////////////////////////////////////////////////
// Focus
////////////////////////////////////////////////////////////////

  public boolean isFocusTraversable()
  {
    return !isReadonly();
  }

  public void focusGained(BFocusEvent event)
  {
    repaint();
  }

  public void focusLost(BFocusEvent event)
  {
    repaint();
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  public void doIncrement()
  {
    fields[selection].increment();
  }

  public void doDecrement()
  {
    fields[selection].decrement();
  }

  public void doShowDatePicker()
  {
    Field year = null;
    Field month = null;
    Field day = null;
    for(int i=0; i<fields.length; ++i)
    {
      if (fields[i] instanceof YearField) year = fields[i];
      if (fields[i] instanceof MonthField) month = fields[i];
      if (fields[i] instanceof DayField) day = fields[i];
    }
    if (year == null || month == null || day == null) return;

    BDatePickerCalendar picker = BDatePickerCalendar.dialog(this, year.value, BMonth.make(month.value));
    if (picker == null) return;

    year.set(picker.year);
    month.set(picker.month.getOrdinal());
    day.set(picker.day);
    setModified();
    repaint();
  }

////////////////////////////////////////////////////////////////
// Field
////////////////////////////////////////////////////////////////

  void setSelection(int f)
  {
    if (!fields[f].isEditable()) throw new IllegalStateException();
    fields[selection].typeCount = 0;
    selection = f;
    repaint();
  }

  protected void fieldModified(Field f)
  {
    setModified();
    repaint();
  }

  public abstract class Field
  {
    Field() { set(min()); }

    int min() { return 0; }
    abstract int max();
    abstract int length();
    boolean isEditable() { return true; }
    String string() { return string; }

    public void set(int v)
    {
      value = v;
      string = String.valueOf(v);
      for(int i=string.length(); i<length(); ++i)
        string = "0" + string;
      fieldModified();
    }

    void fieldModified()
    {
      // don't callback on parent editor until finished setting up
      if (!isModifiedStateLocked()) BMultiFieldFE.this.fieldModified(this);
    }

    void increment()
      { int v = value+1; if (v > max()) v = min(); set(v); }

    void decrement()
      { int v = value-1; if (v < min()) v = max(); set(v); }

    boolean keyTyped(int key)
    {
      if (Character.isDigit((char)key))
      {
        int k = key - '0';
        int v;
        if (typeCount == 0)
        {
          v = k;
        }
        else
        {
          int pow = 1;
          for(int i=0; i<length()-1; ++i) pow *= 10;
          v = (value % pow)*10 + k;
        }
        if (v <= max()) set(v);
        typeCount++;
        return true;
      }
      return false;
    }

    public int value;
    String string;
    int typeCount;
  }

////////////////////////////////////////////////////////////////
// HourField
////////////////////////////////////////////////////////////////

  public class HourField extends Field
  {
    int length() { return 2; }
    int max() { return 23; }
  }

////////////////////////////////////////////////////////////////
// TwelveHourField
////////////////////////////////////////////////////////////////

  public class TwelveHourField extends HourField
  {
    int length() { return 2; }
    int min() { return 1; }
    int max() { return 12; }
    public void set(int v)
    {
      value = v;
      string = (v == 0) ? "12" : String.valueOf(v);
      for(int i=string.length(); i<length(); ++i)
        string = "0" + string;
      if (BMultiFieldFE.this != null)
        fieldModified();
    }
  }

////////////////////////////////////////////////////////////////
// BigHourField
////////////////////////////////////////////////////////////////

  public class BigHourField extends Field
  {
    int length() { return 5; }
    int max() { return 99999; }
  }

////////////////////////////////////////////////////////////////
// MinuteField
////////////////////////////////////////////////////////////////

  public class MinuteField extends Field
  {
    int length() { return 2; }
    int max() { return 59; }
  }

////////////////////////////////////////////////////////////////
// SecondField
////////////////////////////////////////////////////////////////

  public class SecondField extends Field
  {
    int length() { return 2; }
    int max() { return 59; }
  }

////////////////////////////////////////////////////////////////
// MillisecondField
////////////////////////////////////////////////////////////////

  public class MillisecondField extends Field
  {
    int length() { return 3; }
    int max() { return 999; }
  }

////////////////////////////////////////////////////////////////
// DayField
////////////////////////////////////////////////////////////////

  public class DayField extends Field
  {
    int length() { return 2; }
    int min() { return 1; }
    int max() { return maxDay; }
    void setMax(int newMax)
    {
      maxDay = newMax;
      if (value > maxDay) set(maxDay);
    }

    int maxDay = 31;
  }

////////////////////////////////////////////////////////////////
// MonthField
////////////////////////////////////////////////////////////////

  public class MonthField extends Field
  {
    int length() { return 2; }
    int max() { return 11; }
    public void set(int v)
    {
      value = v;
      string = String.valueOf(v+1);
      for(int i=string.length(); i<length(); ++i)
        string = "0" + string;
      if (BMultiFieldFE.this != null)
        fieldModified();
    }
    boolean keyTyped(int key)
    {
      if (Character.isDigit((char)key))
      {
        int k = key - '0';
        int v;
        if (typeCount == 0)
        {
          v = k;
        }
        else
        {
          int pow = 1;
          for(int i=0; i<length()-1; ++i) pow *= 10;
          v = ((value+1) % pow)*10 + k;
        }
        if (v > 0 && v <= max()+1) set(v-1);
        typeCount++;
        return true;
      }
      return false;
    }
  }

////////////////////////////////////////////////////////////////
// MonthTagField
////////////////////////////////////////////////////////////////

  public class MonthTagField extends MonthField
  {
    int length() { return 3; }
    int max() { return 11; }
    public void set(int v)
    {
      BMonth m = BMonth.make(v);
      this.value = v;
      this.string = m.getShortDisplayTag(null);

      // Force string to be three characters
      while (this.string.length() < 3) this.string += " ";
      if (this.string.length() > 3) this.string = this.string.substring(0,3);

      // this weird check for null was necessary because, for some reason,
      // the outer class instance isn't available in the constructor
      // of this instance, and the Field constructor calls set().
      if (BMultiFieldFE.this != null)
        fieldModified();
    }

    boolean keyTyped(int key) { return false; } // do nothing for now
  }

////////////////////////////////////////////////////////////////
// YearField
////////////////////////////////////////////////////////////////

  public class YearField extends Field
  {
    int length() { return 4; }
    int max() { return 2100; }
  }

////////////////////////////////////////////////////////////////
// LabelField
////////////////////////////////////////////////////////////////

  public class LabelField extends Field
  {
    LabelField(String s) { if (s == null) throw new NullPointerException(); string = s; }
    int length() { return string.length(); }
    boolean isEditable() { return false; }
    int max() { return 1; }
  }

////////////////////////////////////////////////////////////////
// AmPmField
////////////////////////////////////////////////////////////////

  public class AmPmField extends Field
  {
    String string() { return (value == 0) ? "AM" : "PM"; }
    int length() { return 2; }
    int max() { return 1; }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final BImage calendarIcon = BImage.make("module://icons/x16/calendar.png");

  protected Field[] fields = new Field[0]; // set by subclass ctor

  BSpinnerButton spinner;
  BWidget datePickerButton;
  int selection = 0;   // field index
  boolean paintNull;   // paint "null"
  double prefHeight;
  static final double textViewFix = 4;
}
