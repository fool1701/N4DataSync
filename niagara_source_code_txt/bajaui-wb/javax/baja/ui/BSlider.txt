  /*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.text.NumberFormat;

import javax.baja.gx.BBrush;
import javax.baja.gx.Graphics;
import javax.baja.gx.RectGeom;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.ui.enums.BOrientation;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.event.BSliderEvent;

import com.tridium.ui.UiEnv;
import com.tridium.ui.theme.SliderTheme;
import com.tridium.ui.theme.Theme;

/**
 * BSlider provides a visual slider which is used to
 * select and integer or doubleing point value between
 * a fixed range.
 *
 * @author    Andy Frank
 * @creation  16 May 01
 * @version   $Revision: 30$ $Date: 5/12/05 3:59:58 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The minumim value the slider can move to.
 */
@NiagaraProperty(
  name = "min",
  type = "double",
  defaultValue = "0"
)
/*
 The max value the slider can move to.
 */
@NiagaraProperty(
  name = "max",
  type = "double",
  defaultValue = "100"
)
/*
 The smallest possible value that the slider
 can increment by.
 */
@NiagaraProperty(
  name = "increment",
  type = "double",
  defaultValue = "1"
)
/*
 The currently selected value on the slider bar.
 */
@NiagaraProperty(
  name = "value",
  type = "double",
  defaultValue = "50"
)
/*
 The orientation of the slider bar.
 */
@NiagaraProperty(
  name = "orientation",
  type = "BOrientation",
  defaultValue = "BOrientation.horizontal"
)
/*
 The color to paint the track or null to use default.
 */
@NiagaraProperty(
  name = "trackBrush",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
/*
 The color to paint the thumb or null to use default.
 */
@NiagaraProperty(
  name = "thumbBrush",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
/*
 Event fired when the position is modified.
 */
@NiagaraTopic(
  name = "valueModified",
  eventType = "BSliderEvent"
)
/*
 Event fired when the mouse is released after dragging slider.
 */
@NiagaraTopic(
  name = "actionPerformed",
  eventType = "BSliderEvent"
)
public class BSlider
  extends BWidget
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BSlider(4293090293)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "min"

  /**
   * Slot for the {@code min} property.
   * The minumim value the slider can move to.
   * @see #getMin
   * @see #setMin
   */
  public static final Property min = newProperty(0, 0, null);

  /**
   * Get the {@code min} property.
   * The minumim value the slider can move to.
   * @see #min
   */
  public double getMin() { return getDouble(min); }

  /**
   * Set the {@code min} property.
   * The minumim value the slider can move to.
   * @see #min
   */
  public void setMin(double v) { setDouble(min, v, null); }

  //endregion Property "min"

  //region Property "max"

  /**
   * Slot for the {@code max} property.
   * The max value the slider can move to.
   * @see #getMax
   * @see #setMax
   */
  public static final Property max = newProperty(0, 100, null);

  /**
   * Get the {@code max} property.
   * The max value the slider can move to.
   * @see #max
   */
  public double getMax() { return getDouble(max); }

  /**
   * Set the {@code max} property.
   * The max value the slider can move to.
   * @see #max
   */
  public void setMax(double v) { setDouble(max, v, null); }

  //endregion Property "max"

  //region Property "increment"

  /**
   * Slot for the {@code increment} property.
   * The smallest possible value that the slider
   * can increment by.
   * @see #getIncrement
   * @see #setIncrement
   */
  public static final Property increment = newProperty(0, 1, null);

  /**
   * Get the {@code increment} property.
   * The smallest possible value that the slider
   * can increment by.
   * @see #increment
   */
  public double getIncrement() { return getDouble(increment); }

  /**
   * Set the {@code increment} property.
   * The smallest possible value that the slider
   * can increment by.
   * @see #increment
   */
  public void setIncrement(double v) { setDouble(increment, v, null); }

  //endregion Property "increment"

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * The currently selected value on the slider bar.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, 50, null);

  /**
   * Get the {@code value} property.
   * The currently selected value on the slider bar.
   * @see #value
   */
  public double getValue() { return getDouble(value); }

  /**
   * Set the {@code value} property.
   * The currently selected value on the slider bar.
   * @see #value
   */
  public void setValue(double v) { setDouble(value, v, null); }

  //endregion Property "value"

  //region Property "orientation"

  /**
   * Slot for the {@code orientation} property.
   * The orientation of the slider bar.
   * @see #getOrientation
   * @see #setOrientation
   */
  public static final Property orientation = newProperty(0, BOrientation.horizontal, null);

  /**
   * Get the {@code orientation} property.
   * The orientation of the slider bar.
   * @see #orientation
   */
  public BOrientation getOrientation() { return (BOrientation)get(orientation); }

  /**
   * Set the {@code orientation} property.
   * The orientation of the slider bar.
   * @see #orientation
   */
  public void setOrientation(BOrientation v) { set(orientation, v, null); }

  //endregion Property "orientation"

  //region Property "trackBrush"

  /**
   * Slot for the {@code trackBrush} property.
   * The color to paint the track or null to use default.
   * @see #getTrackBrush
   * @see #setTrackBrush
   */
  public static final Property trackBrush = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code trackBrush} property.
   * The color to paint the track or null to use default.
   * @see #trackBrush
   */
  public BBrush getTrackBrush() { return (BBrush)get(trackBrush); }

  /**
   * Set the {@code trackBrush} property.
   * The color to paint the track or null to use default.
   * @see #trackBrush
   */
  public void setTrackBrush(BBrush v) { set(trackBrush, v, null); }

  //endregion Property "trackBrush"

  //region Property "thumbBrush"

  /**
   * Slot for the {@code thumbBrush} property.
   * The color to paint the thumb or null to use default.
   * @see #getThumbBrush
   * @see #setThumbBrush
   */
  public static final Property thumbBrush = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code thumbBrush} property.
   * The color to paint the thumb or null to use default.
   * @see #thumbBrush
   */
  public BBrush getThumbBrush() { return (BBrush)get(thumbBrush); }

  /**
   * Set the {@code thumbBrush} property.
   * The color to paint the thumb or null to use default.
   * @see #thumbBrush
   */
  public void setThumbBrush(BBrush v) { set(thumbBrush, v, null); }

  //endregion Property "thumbBrush"

  //region Topic "valueModified"

  /**
   * Slot for the {@code valueModified} topic.
   * Event fired when the position is modified.
   * @see #fireValueModified
   */
  public static final Topic valueModified = newTopic(0, null);

  /**
   * Fire an event for the {@code valueModified} topic.
   * Event fired when the position is modified.
   * @see #valueModified
   */
  public void fireValueModified(BSliderEvent event) { fire(valueModified, event, null); }

  //endregion Topic "valueModified"

  //region Topic "actionPerformed"

  /**
   * Slot for the {@code actionPerformed} topic.
   * Event fired when the mouse is released after dragging slider.
   * @see #fireActionPerformed
   */
  public static final Topic actionPerformed = newTopic(0, null);

  /**
   * Fire an event for the {@code actionPerformed} topic.
   * Event fired when the mouse is released after dragging slider.
   * @see #actionPerformed
   */
  public void fireActionPerformed(BSliderEvent event) { fire(actionPerformed, event, null); }

  //endregion Topic "actionPerformed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSlider.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * No argument constructor.
   */
  public BSlider()
  {
  }

  /**
   * Sets the min, max values.
   */
  public BSlider(double min, double max)
  {
    setMin(min);
    setMax(max);

    if (getValue() < min || getValue() > max) setValue((max + min) / 2);
  }

  /**
   * Sets the min, max, increment, and initial values.
   */
  public BSlider(double min, double max, double inc, double val)
  {
    setMin(min);
    setMax(max);
    setIncrement(inc);
    setValue(val);

    if (getValue() < min || getValue() > max) setValue((max + min) / 2);
  }

  /**
   * Sets the min, max, increment, initial and
   * orienation values.
   */
  public BSlider(BOrientation orient, double min, double max, double inc, double val)
  {
    setOrientation(orient);
    setMin(min);
    setMax(max);
    setIncrement(inc);
    setValue(val);

    if (getValue() < min || getValue() > max) setValue((max + min) / 2);
  }


////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Compute preferred size.
   */
  public void computePreferredSize()
  {
    double size = Theme.slider().getFixedWidth(this);

    if (isHorizontal())
      setPreferredSize(100, size);
    else
      setPreferredSize(size, 100);
  }

  /**
   * Layout component.
   */
  public void doLayout(BWidget[] kids)
  {
    if (isHorizontal())
      layoutHorizontally();
    else
      layoutVertically();

  }

  /**
   * Layout the component horizontally.
   */
  public void layoutHorizontally()
  {
    thumb.set(0, 0, Theme.slider().getThumbWidth(this), getHeight()-1);
    track.set(thumb.width / 2, 4, getWidth() - thumb.width, 6);

    thumbPoint = (int)(thumb.width / 2.0 + 0.5);
    viewSize = getWidth() - thumb.width;
    realSize = getMax() - getMin();
  }

  /**
   * Layout the component vertically.
   */
  public void layoutVertically()
  {
    thumb.set(0, 0, getWidth()-1, Theme.slider().getThumbWidth(this));
    track.set(4, thumb.height / 2, 6, getHeight() - thumb.height);

    thumbPoint = (int)(thumb.height / 2.0 + 0.5);
    viewSize = getHeight() - thumb.height;
    realSize = getMax() - getMin();
  }

  public boolean isHorizontal() { return getOrientation() == BOrientation.horizontal; }

////////////////////////////////////////////////////////////////
// Paint
////////////////////////////////////////////////////////////////

  /**
   * Paint the slider component.
   */
  public void paint(Graphics g)
  {
    double v = getValue();
    if (v < getMin()) v = getMin();
    if (v > getMax()) v = getMax();
    
    // Relocate the thumb if necessary
    int pos = (int)((v - getMin()) / realSize * viewSize);
    if (!isHorizontal()) pos = (int)(viewSize - pos);
    
    if (isHorizontal())
      thumb.set(pos,0,thumb.width,thumb.height);
    else
      thumb.set(0,pos,thumb.width,thumb.height);

    SliderTheme theme = Theme.slider();
    theme.paintTrack(g, this, track);
    theme.paintThumb(g, this, thumb);
  }

////////////////////////////////////////////////////////////////
// Events
////////////////////////////////////////////////////////////////

  /**
   * If the mouse is pressed on the thumb, the thumb becomes
   * "hot" and needs to be moved as the mouse is moved. If the
   * mouse was pressed on the track, then the thumb needs to
   * "paged" in the direction.
   */
  public void mousePressed(BMouseEvent event)
  {
    double v = getValue();
    if (v < getMin()) v = getMin();
    if (v > getMax()) v = getMax();
    
    String s = valueToString(v);    
    UiEnv.get().openBubbleHelp(this, event.getX(), event.getY()+24, s);
  }

  public void mouseReleased(BMouseEvent event)
  {
    UiEnv.get().closeBubbleHelp();
    fireActionPerformed(new BSliderEvent(BSliderEvent.VALUE_CHANGED, this, getValue()));
  }

  public void mouseDragged(BMouseEvent event)
  {
    double viewPos = (isHorizontal()) ? event.getX() - thumbPoint : (getHeight() - event.getY()) - thumbPoint;
    if (viewPos < 0) viewPos = 0;
    if (viewPos > viewSize) viewPos = viewSize;
    
    double newValue = (viewPos / viewSize) * realSize + getMin();

    // Do snap-to
    double inc = getIncrement();
    double temp = newValue % inc;

    if (temp > (inc / 2))
      newValue = newValue - temp + inc;
    else
      newValue = newValue - temp;

    setValue(newValue);
    fireValueModified(new BSliderEvent(BSliderEvent.VALUE_CHANGED, this, getValue()));
    repaint();
    
    // Update bubble help
    String s = valueToString(newValue);    
    UiEnv.get().updateBubbleHelp(this, event.getX(), event.getY()+24, s);
  }
  
  private String valueToString(double v)
  {
    NumberFormat format = NumberFormat.getInstance();
    format.setMaximumFractionDigits(3);
    return format.format(v);    
  }
  
///////////////////////////////////////////////////////////
// Overrides
/////////////////////////////////////////////////////////// 

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/slider.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  protected double thumbPoint;
  protected double viewSize;
  protected double realSize;

  protected RectGeom thumb = new RectGeom();
  protected RectGeom track = new RectGeom();
}
