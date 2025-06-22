/*
* Copyright 2000 Tridium, Inc. All Rights Reserved.
*/
package javax.baja.ui;

import java.text.*;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.event.*;

import com.tridium.ui.theme.*;

/**
* BProgressBar
*
* @author    Mike Jarmy
* @creation  11 Apr 02
* @version   $Revision: 22$ $Date: 11/22/06 4:14:10 PM EST$
* @since     Baja 1.0
*/
@NiagaraType
/*
 Minimum value of progress bar.
 */
@NiagaraProperty(
  name = "min",
  type = "double",
  defaultValue = "0"
)
/*
 Maximum value of progress bar.
 */
@NiagaraProperty(
  name = "max",
  type = "double",
  defaultValue = "100"
)
/*
 A string which will be used via
 <code>java.text.DecimalFormat.format()</code> to render
 the current value of <code>getPercentComplete()</code> onto
 the progress bar.
 If the textPattern is null or "", then no string will be rendered.
 */
@NiagaraProperty(
  name = "textPattern",
  type = "String",
  defaultValue = "#%"
)
/*
 Defines the font to use to render the progress bars's
 text.  If it is set to BFont.NULL then a context
 sensitive default fallback will be used.
 */
@NiagaraProperty(
  name = "textFont",
  type = "BFont",
  defaultValue = "BFont.NULL"
)
@NiagaraProperty(
  name = "backgroundBrush",
  type = "BBrush",
  defaultValue = "BColor.white.toBrush()"
)
@NiagaraProperty(
  name = "barBrush",
  type = "BBrush",
  defaultValue = "BColor.gray.toBrush()"
)
/*
 Define if this bar's progress is indeterminate.
 */
@NiagaraProperty(
  name = "indeterminate",
  type = "boolean",
  defaultValue = "false"
)
/*
 Fired each time the progress bar's value is changed.
 */
@NiagaraTopic(
  name = "valueChanged",
  eventType = "BWidgetEvent"
)
public class BProgressBar
  extends BWidget
{                    

////////////////////////////////////////////////////////////////
// frozen slots
////////////////////////////////////////////////////////////////

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BProgressBar(997748185)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "min"

  /**
   * Slot for the {@code min} property.
   * Minimum value of progress bar.
   * @see #getMin
   * @see #setMin
   */
  public static final Property min = newProperty(0, 0, null);

  /**
   * Get the {@code min} property.
   * Minimum value of progress bar.
   * @see #min
   */
  public double getMin() { return getDouble(min); }

  /**
   * Set the {@code min} property.
   * Minimum value of progress bar.
   * @see #min
   */
  public void setMin(double v) { setDouble(min, v, null); }

  //endregion Property "min"

  //region Property "max"

  /**
   * Slot for the {@code max} property.
   * Maximum value of progress bar.
   * @see #getMax
   * @see #setMax
   */
  public static final Property max = newProperty(0, 100, null);

  /**
   * Get the {@code max} property.
   * Maximum value of progress bar.
   * @see #max
   */
  public double getMax() { return getDouble(max); }

  /**
   * Set the {@code max} property.
   * Maximum value of progress bar.
   * @see #max
   */
  public void setMax(double v) { setDouble(max, v, null); }

  //endregion Property "max"

  //region Property "textPattern"

  /**
   * Slot for the {@code textPattern} property.
   * A string which will be used via
   * <code>java.text.DecimalFormat.format()</code> to render
   * the current value of <code>getPercentComplete()</code> onto
   * the progress bar.
   * If the textPattern is null or "", then no string will be rendered.
   * @see #getTextPattern
   * @see #setTextPattern
   */
  public static final Property textPattern = newProperty(0, "#%", null);

  /**
   * Get the {@code textPattern} property.
   * A string which will be used via
   * <code>java.text.DecimalFormat.format()</code> to render
   * the current value of <code>getPercentComplete()</code> onto
   * the progress bar.
   * If the textPattern is null or "", then no string will be rendered.
   * @see #textPattern
   */
  public String getTextPattern() { return getString(textPattern); }

  /**
   * Set the {@code textPattern} property.
   * A string which will be used via
   * <code>java.text.DecimalFormat.format()</code> to render
   * the current value of <code>getPercentComplete()</code> onto
   * the progress bar.
   * If the textPattern is null or "", then no string will be rendered.
   * @see #textPattern
   */
  public void setTextPattern(String v) { setString(textPattern, v, null); }

  //endregion Property "textPattern"

  //region Property "textFont"

  /**
   * Slot for the {@code textFont} property.
   * Defines the font to use to render the progress bars's
   * text.  If it is set to BFont.NULL then a context
   * sensitive default fallback will be used.
   * @see #getTextFont
   * @see #setTextFont
   */
  public static final Property textFont = newProperty(0, BFont.NULL, null);

  /**
   * Get the {@code textFont} property.
   * Defines the font to use to render the progress bars's
   * text.  If it is set to BFont.NULL then a context
   * sensitive default fallback will be used.
   * @see #textFont
   */
  public BFont getTextFont() { return (BFont)get(textFont); }

  /**
   * Set the {@code textFont} property.
   * Defines the font to use to render the progress bars's
   * text.  If it is set to BFont.NULL then a context
   * sensitive default fallback will be used.
   * @see #textFont
   */
  public void setTextFont(BFont v) { set(textFont, v, null); }

  //endregion Property "textFont"

  //region Property "backgroundBrush"

  /**
   * Slot for the {@code backgroundBrush} property.
   * @see #getBackgroundBrush
   * @see #setBackgroundBrush
   */
  public static final Property backgroundBrush = newProperty(0, BColor.white.toBrush(), null);

  /**
   * Get the {@code backgroundBrush} property.
   * @see #backgroundBrush
   */
  public BBrush getBackgroundBrush() { return (BBrush)get(backgroundBrush); }

  /**
   * Set the {@code backgroundBrush} property.
   * @see #backgroundBrush
   */
  public void setBackgroundBrush(BBrush v) { set(backgroundBrush, v, null); }

  //endregion Property "backgroundBrush"

  //region Property "barBrush"

  /**
   * Slot for the {@code barBrush} property.
   * @see #getBarBrush
   * @see #setBarBrush
   */
  public static final Property barBrush = newProperty(0, BColor.gray.toBrush(), null);

  /**
   * Get the {@code barBrush} property.
   * @see #barBrush
   */
  public BBrush getBarBrush() { return (BBrush)get(barBrush); }

  /**
   * Set the {@code barBrush} property.
   * @see #barBrush
   */
  public void setBarBrush(BBrush v) { set(barBrush, v, null); }

  //endregion Property "barBrush"

  //region Property "indeterminate"

  /**
   * Slot for the {@code indeterminate} property.
   * Define if this bar's progress is indeterminate.
   * @see #getIndeterminate
   * @see #setIndeterminate
   */
  public static final Property indeterminate = newProperty(0, false, null);

  /**
   * Get the {@code indeterminate} property.
   * Define if this bar's progress is indeterminate.
   * @see #indeterminate
   */
  public boolean getIndeterminate() { return getBoolean(indeterminate); }

  /**
   * Set the {@code indeterminate} property.
   * Define if this bar's progress is indeterminate.
   * @see #indeterminate
   */
  public void setIndeterminate(boolean v) { setBoolean(indeterminate, v, null); }

  //endregion Property "indeterminate"

  //region Topic "valueChanged"

  /**
   * Slot for the {@code valueChanged} topic.
   * Fired each time the progress bar's value is changed.
   * @see #fireValueChanged
   */
  public static final Topic valueChanged = newTopic(0, null);

  /**
   * Fire an event for the {@code valueChanged} topic.
   * Fired each time the progress bar's value is changed.
   * @see #valueChanged
   */
  public void fireValueChanged(BWidgetEvent event) { fire(valueChanged, event, null); }

  //endregion Topic "valueChanged"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BProgressBar.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// constructors
////////////////////////////////////////////////////////////////  

  /**
  * default constructor
  */
  public BProgressBar()
  {                      
  }
  
  /**
  * construct with minimum and maximum
  */
  public BProgressBar(double min, double max)
  {
    this();
    setMin(min);
    setMax(max);
  }
  
////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////  

  /**
   * Compute the preferred size.
   */
  public void computePreferredSize()
  { 
    setPreferredSize(200, 20);
  }
  
////////////////////////////////////////////////////////////////
// paint
////////////////////////////////////////////////////////////////  
  
  /**
   * Animate.
   */
  public void animate()
  {
    if (getIndeterminate()) 
    {
      inOff += 4;
      if (inOff >= 16) inOff = 0;
      repaint();
    }
  }

  /**
   * Paint the label.
   */
  public void paint(Graphics g)
  {    
    double w = getWidth();
    double h = getHeight();
    int pw = 0;
    
    BBrush bg = getBackgroundBrush();
    BBrush fg = getBarBrush();
    
    // background
    g.setBrush(bg);
    g.fillRect(0, 0, w, h);

    if (getIndeterminate())
    {
      // draw stripes
      g.setBrush(fg);
      for (int i=-(int)(h+16); i<w; i+=16)      
      {        
        int x = i + inOff;
        
        g.strokeLine(x,   h, x+h,   0);
        g.strokeLine(x+1, h, x+h+1, 0);
        g.strokeLine(x+2, h, x+h+2, 0);
        g.strokeLine(x+3, h, x+h+3, 0);
        g.strokeLine(x+4, h, x+h+4, 0);
        g.strokeLine(x+5, h, x+h+5, 0);
        g.strokeLine(x+6, h, x+h+6, 0);
        g.strokeLine(x+7, h, x+h+7, 0);
        g.strokeLine(x+8, h, x+h+8, 0);
      }
    }
    else
    {
      // draw the bar
      pw = (int) (getPercentComplete() * w);    
      g.setBrush(fg);
      g.fillRect(0, 0, pw, h);    
    }
    
    // outline
    g.setBrush(BColor.black);
    g.strokeRect(0, 0, w-1, h-1);

    if (getIndeterminate()) return;

    String pattern = getTextPattern();
    if (!pattern.equals(""))
    {    
      // get the string
      DecimalFormat df = new DecimalFormat(pattern);
      String text = df.format(getPercentComplete());
      
      // get measurements
      BFont font = getTextFont();
      if (font.isNull()) font = Theme.label().getTextFont();
      g.setFont(font);

      double fontHeight = font.getHeight();
      double fontDescent = font.getDescent();
      double textWidth = font.width(text);

      double tx = (w - textWidth)/2;
      double ty = (h - fontHeight)/2;

      // draw 
      g.push();
      try
      {
        g.clip(0, 0, pw, h);
        g.setBrush(bg);
        g.drawString(text, tx, ty + fontHeight - fontDescent);
      }
      finally 
      {
        g.pop(); 
      }
      
      g.push();
      try
      {
        g.clip(pw, 0, w-pw, h);
        g.setBrush(fg);
        g.drawString(text, tx, ty + fontHeight - fontDescent);
      }
      finally 
      { 
        g.pop(); 
      }
    }
  }

////////////////////////////////////////////////////////////////
// public
////////////////////////////////////////////////////////////////  

  /**
   * Get the current value of progress bar.
   */
  public double getValue() { return value; }
  
  /**
   * Set the current value of progress bar. If this value is set
   * lower than the minimum value, it will be changed to
   * the minimum value instead. If this value
   * is set higher than the maximum value, it will be changed
   * to the maximum value.
   */
  public void setValue(double v) 
  { 
    if (v < getMin()) v = getMin();
    else if (v > getMax()) v = getMax();
    
    value = v;
    fireValueChanged(null);
    repaint();
  }

  /**
  * Get the percent complete.
  */
  public double getPercentComplete()
  {
    return 
      (getValue() - getMin())/
      (getMax() - getMin());
  }

////////////////////////////////////////////////////////////////
// attributes
////////////////////////////////////////////////////////////////  

  private double value;
  private int inOff = 0;

}
