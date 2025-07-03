/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.util.Date;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.enums.*;
import javax.baja.ui.event.*;

import com.tridium.ui.theme.*;

/**
 * BScrollBar encapsulates a scrolling bar which may be dragged, 
 * or "paged".  A scroll bar tracks its value using the position
 * property.  The extent is used to illustrate how much of the 
 * scrollable region is currently visible (bigger extent means 
 * more is showing).  Position is always between min and max-extent.  
 * The top of the thumb is position and bottom of the thumb is 
 * position+extent.  If snapToUnitIncrement is true then the position
 * is always snapped to the closest unit increment such at 
 * position % unitIncrement is always zero.    
 *
 *
 * @author    Brian Frank       
 * @creation  21 Nov 00
 * @version   $Revision: 38$ $Date: 6/22/11 4:30:12 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "orientation",
  type = "BOrientation",
  defaultValue = "BOrientation.vertical"
)
@NiagaraProperty(
  name = "min",
  type = "int",
  defaultValue = "0"
)
@NiagaraProperty(
  name = "max",
  type = "int",
  defaultValue = "100"
)
/*
 Position is the integer value between min and max
 for the current scroll location.  If snapToUnitIncrement
 is true then position % unitIncrement is always zero.
 Position is always a value between min and max-extent
 inclusive.
 */
@NiagaraProperty(
  name = "position",
  type = "int",
  defaultValue = "0"
)
/*
 Extent is a number between 0 and max-min
 which indicates how large the scroll button
 should be.  It usually corresponds to the
 scrollable dimensions of the viewport.
 */
@NiagaraProperty(
  name = "extent",
  type = "int",
  defaultValue = "10"
)
/*
 The unit increment is the amount the position
 is changed when using the scrollbar's push.
 buttons.
 */
@NiagaraProperty(
  name = "unitIncrement",
  type = "int",
  defaultValue = "1"
)
/*
 The unit increment is the amount the position
 is changed when using the scrollbar's track.
 */
@NiagaraProperty(
  name = "blockIncrement",
  type = "int",
  defaultValue = "10"
)
/*
 When set to true always snap the scroll position
 to an even multiple of the unit increment.
 */
@NiagaraProperty(
  name = "snapToUnitIncrement",
  type = "boolean",
  defaultValue = "true"
)
/*
 Event fired when the position is modified.
 */
@NiagaraTopic(
  name = "positionChanged",
  eventType = "BScrollEvent"
)
public class BScrollBar
  extends BWidget
{           

// DOGX - should this be floats or doubles?
//  table and text use it for storing int based line/rows

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BScrollBar(2303956231)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "orientation"

  /**
   * Slot for the {@code orientation} property.
   * @see #getOrientation
   * @see #setOrientation
   */
  public static final Property orientation = newProperty(0, BOrientation.vertical, null);

  /**
   * Get the {@code orientation} property.
   * @see #orientation
   */
  public BOrientation getOrientation() { return (BOrientation)get(orientation); }

  /**
   * Set the {@code orientation} property.
   * @see #orientation
   */
  public void setOrientation(BOrientation v) { set(orientation, v, null); }

  //endregion Property "orientation"

  //region Property "min"

  /**
   * Slot for the {@code min} property.
   * @see #getMin
   * @see #setMin
   */
  public static final Property min = newProperty(0, 0, null);

  /**
   * Get the {@code min} property.
   * @see #min
   */
  public int getMin() { return getInt(min); }

  /**
   * Set the {@code min} property.
   * @see #min
   */
  public void setMin(int v) { setInt(min, v, null); }

  //endregion Property "min"

  //region Property "max"

  /**
   * Slot for the {@code max} property.
   * @see #getMax
   * @see #setMax
   */
  public static final Property max = newProperty(0, 100, null);

  /**
   * Get the {@code max} property.
   * @see #max
   */
  public int getMax() { return getInt(max); }

  /**
   * Set the {@code max} property.
   * @see #max
   */
  public void setMax(int v) { setInt(max, v, null); }

  //endregion Property "max"

  //region Property "position"

  /**
   * Slot for the {@code position} property.
   * Position is the integer value between min and max
   * for the current scroll location.  If snapToUnitIncrement
   * is true then position % unitIncrement is always zero.
   * Position is always a value between min and max-extent
   * inclusive.
   * @see #getPosition
   * @see #setPosition
   */
  public static final Property position = newProperty(0, 0, null);

  /**
   * Get the {@code position} property.
   * Position is the integer value between min and max
   * for the current scroll location.  If snapToUnitIncrement
   * is true then position % unitIncrement is always zero.
   * Position is always a value between min and max-extent
   * inclusive.
   * @see #position
   */
  public int getPosition() { return getInt(position); }

  /**
   * Set the {@code position} property.
   * Position is the integer value between min and max
   * for the current scroll location.  If snapToUnitIncrement
   * is true then position % unitIncrement is always zero.
   * Position is always a value between min and max-extent
   * inclusive.
   * @see #position
   */
  public void setPosition(int v) { setInt(position, v, null); }

  //endregion Property "position"

  //region Property "extent"

  /**
   * Slot for the {@code extent} property.
   * Extent is a number between 0 and max-min
   * which indicates how large the scroll button
   * should be.  It usually corresponds to the
   * scrollable dimensions of the viewport.
   * @see #getExtent
   * @see #setExtent
   */
  public static final Property extent = newProperty(0, 10, null);

  /**
   * Get the {@code extent} property.
   * Extent is a number between 0 and max-min
   * which indicates how large the scroll button
   * should be.  It usually corresponds to the
   * scrollable dimensions of the viewport.
   * @see #extent
   */
  public int getExtent() { return getInt(extent); }

  /**
   * Set the {@code extent} property.
   * Extent is a number between 0 and max-min
   * which indicates how large the scroll button
   * should be.  It usually corresponds to the
   * scrollable dimensions of the viewport.
   * @see #extent
   */
  public void setExtent(int v) { setInt(extent, v, null); }

  //endregion Property "extent"

  //region Property "unitIncrement"

  /**
   * Slot for the {@code unitIncrement} property.
   * The unit increment is the amount the position
   * is changed when using the scrollbar's push.
   * buttons.
   * @see #getUnitIncrement
   * @see #setUnitIncrement
   */
  public static final Property unitIncrement = newProperty(0, 1, null);

  /**
   * Get the {@code unitIncrement} property.
   * The unit increment is the amount the position
   * is changed when using the scrollbar's push.
   * buttons.
   * @see #unitIncrement
   */
  public int getUnitIncrement() { return getInt(unitIncrement); }

  /**
   * Set the {@code unitIncrement} property.
   * The unit increment is the amount the position
   * is changed when using the scrollbar's push.
   * buttons.
   * @see #unitIncrement
   */
  public void setUnitIncrement(int v) { setInt(unitIncrement, v, null); }

  //endregion Property "unitIncrement"

  //region Property "blockIncrement"

  /**
   * Slot for the {@code blockIncrement} property.
   * The unit increment is the amount the position
   * is changed when using the scrollbar's track.
   * @see #getBlockIncrement
   * @see #setBlockIncrement
   */
  public static final Property blockIncrement = newProperty(0, 10, null);

  /**
   * Get the {@code blockIncrement} property.
   * The unit increment is the amount the position
   * is changed when using the scrollbar's track.
   * @see #blockIncrement
   */
  public int getBlockIncrement() { return getInt(blockIncrement); }

  /**
   * Set the {@code blockIncrement} property.
   * The unit increment is the amount the position
   * is changed when using the scrollbar's track.
   * @see #blockIncrement
   */
  public void setBlockIncrement(int v) { setInt(blockIncrement, v, null); }

  //endregion Property "blockIncrement"

  //region Property "snapToUnitIncrement"

  /**
   * Slot for the {@code snapToUnitIncrement} property.
   * When set to true always snap the scroll position
   * to an even multiple of the unit increment.
   * @see #getSnapToUnitIncrement
   * @see #setSnapToUnitIncrement
   */
  public static final Property snapToUnitIncrement = newProperty(0, true, null);

  /**
   * Get the {@code snapToUnitIncrement} property.
   * When set to true always snap the scroll position
   * to an even multiple of the unit increment.
   * @see #snapToUnitIncrement
   */
  public boolean getSnapToUnitIncrement() { return getBoolean(snapToUnitIncrement); }

  /**
   * Set the {@code snapToUnitIncrement} property.
   * When set to true always snap the scroll position
   * to an even multiple of the unit increment.
   * @see #snapToUnitIncrement
   */
  public void setSnapToUnitIncrement(boolean v) { setBoolean(snapToUnitIncrement, v, null); }

  //endregion Property "snapToUnitIncrement"

  //region Topic "positionChanged"

  /**
   * Slot for the {@code positionChanged} topic.
   * Event fired when the position is modified.
   * @see #firePositionChanged
   */
  public static final Topic positionChanged = newTopic(0, null);

  /**
   * Fire an event for the {@code positionChanged} topic.
   * Event fired when the position is modified.
   * @see #positionChanged
   */
  public void firePositionChanged(BScrollEvent event) { fire(positionChanged, event, null); }

  //endregion Topic "positionChanged"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BScrollBar.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////


  /**
   * Constructor with orientation, unit, and block increment.
   */
  public BScrollBar(BOrientation o, int unitIncrement, int blockIncrement, boolean snapToUnit)
  {
    setOrientation(o);
    setUnitIncrement(unitIncrement);
    setBlockIncrement(blockIncrement);
    setSnapToUnitIncrement(snapToUnit);
  }

  /**
   * Constructor with orientation.
   */
  public BScrollBar(BOrientation o)
  {
    setOrientation(o);
  }

  /**
   * Default constructor.
   */
  public BScrollBar()
  {
  }

////////////////////////////////////////////////////////////////
// Position
////////////////////////////////////////////////////////////////

  public void changed(Property prop, Context context)
  {
    super.changed(prop, context);
    if (context == resetContext) return;
    if (prop == orientation)
    {
      relayout();
    }
    else
    {
      resetPosition(getPosition(), BScrollEvent.OTHER);
    }
  }
  
  /**
   * The thumb position is the exact location of the thumb.
   */
  public double getThumbPosition()
  {
    return thumbPosition;
  }
  
  /**
   * Reset the scroll bar position with the specified thumb 
   * position.  The thumb position is clipped to be between 
   * min and max-extent inclusive.  If snapToUnitIncrement
   * is true then the position will be snapped to the closest 
   * unit increment, otherwise the position is set to the 
   * closest integer.
   */  
  public void resetPosition(double thumbPosition, int adjustment)
  {  
    // clip
    double min = getMin();
    double max = getMax();
    double range = Math.max(max, min);
    double extent = getExtent();
    if (extent > range) extent = range;
    double bottom = max-extent;
    if (thumbPosition > bottom) thumbPosition = bottom;
    if (thumbPosition < min) thumbPosition = min;
    this.thumbPosition = thumbPosition;

    // snap to unit increment
    double pos = thumbPosition;
    if (getSnapToUnitIncrement())
    {
      double inc = getUnitIncrement();            // increment
      double rem = Math.IEEEremainder(pos, inc);  // remainder
      double lo  = pos - rem;                     // low snap to
      double hi  = lo + inc;                      // high snap to
      if (rem != 0)
      {
        double half = inc / 2;
        if (rem > half) pos = hi;
        else pos = lo;
      }
      // clip just to be sure
      if (pos > bottom) pos = bottom;
      if (pos < min) pos = min;
    }
        
    // update ourselves
    layoutThumb();
    
    // fire an event
    if (getPosition() != pos)
    {
      setInt(position, (int)pos, resetContext);    
      firePositionChanged(new BScrollEvent(BScrollEvent.POSITION_CHANGED,
        this, (int)pos, adjustment));
    }
    
    // repaint always
    repaint();
  }

  /**
   * Scroll up by one unit increment.
   */
  public void decrementByUnit()
  {
    scrollByUnits(-1);
  }

  /**
   * Scroll down by one unit increment.
   */
  public void incrementByUnit()
  {
    scrollByUnits(1);
  }

  /**
   * @param units the number of unit increments to scroll
   * @since Niagara 4.8
   */
  public void scrollByUnits(double units) {
    scrollByPixels(units * getUnitIncrement(), BScrollEvent.UNIT_INCREMENT);
  }

  /**
   * @param px number of pixels to scroll
   * @param adjustment what kind of adjustment caused the scroll (unit or block)
   */
  private void scrollByPixels(double px, int adjustment) {
    long now = new Date().getTime();

    // why reset scroll after a period of inactivity? because accumulatedScroll
    // is an instance variable, while the actual position is represented by
    // getPosition(). if setPosition() is directly called (e.g.
    // BScrollPane#scrollToVisible) then the slot and variable can get out of
    // sync. so as soon as the user takes their fingers off the touchpad or
    // scroll wheel, forget about the fact we were scrolling and reset to the
    // real source of truth: getPosition().
    if (now - lastScrollTimestamp <= 100)
    {
      accumulatedScroll += px;
    }
    else
    {
      scrollStartPosition = getPosition();
      accumulatedScroll = px;
    }
    accumulatedScroll = Math.max(accumulatedScroll, getMin() - scrollStartPosition);
    accumulatedScroll = Math.min(accumulatedScroll, getMax() - getExtent() - scrollStartPosition);
    double pos = scrollStartPosition + accumulatedScroll;
    lastScrollTimestamp = now;
    resetPosition(pos, adjustment);
  }

  /**
   * Scroll up by one block increment.
   */
  public void decrementByBlock()
  {
    scrollByPixels(-getBlockIncrement(), BScrollEvent.BLOCK_DECREMENT);
  }
  
  /**
   * Scroll down by one block increment.
   */
  public void incrementByBlock()
  {
    scrollByPixels(getBlockIncrement(), BScrollEvent.BLOCK_INCREMENT);
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////  

  public void computePreferredSize()
  {    
    double size = Theme.scrollBar().getFixedWidth();
    if (getOrientation() == BOrientation.horizontal)
      setPreferredSize(100, size);
    else
      setPreferredSize(size, 100);
  }

  public void doLayout(BWidget[] children)
  {
    if (getOrientation() == BOrientation.horizontal)
      layoutHorizontal();
    else
      layoutVertical();
  }
  
  private void layoutHorizontal()
  {
    double size = Theme.scrollBar().getFixedWidth();
    double w = getWidth();
    
    // shrink buttons if we are tight on space
    double buttonWidth = size;
    if (w < size*2) buttonWidth = w/2;
    
    // "up" if scroll left
    up.x = 0;  up.y = 0;
    up.width = buttonWidth;  up.height = size;
    
    // "down" if scroll right
    down.x = w-buttonWidth;  down.y = 0;
    down.width = buttonWidth;  down.height = size;
    
    // track is what is left over
    track.x = buttonWidth; track.y = 0;
    track.width = w - buttonWidth*2; track.height = size;

    // layout the thumb
    layoutThumb();
  }

  private void layoutVertical()
  {
    double size = Theme.scrollBar().getFixedWidth();
    double h = getHeight();
    
    // shrink buttons if we are tight on space
    double buttonHeight = size;
    if (h < size*2) buttonHeight = h/2;
    
    // "up" if scroll up
    up.x = 0;  up.y = 0;
    up.width = size; up.height = buttonHeight;  
    
    // "down" if scroll down
    down.x = 0; down.y = h-buttonHeight;
    down.width = size;  down.height = buttonHeight;
    
    // track is what is left over
    track.x = 0; track.y = buttonHeight;
    track.width = size; track.height = h - buttonHeight*2;

    // layout the thumb
    layoutThumb();
  }
  
  private void layoutThumb()
  {
    double pos = thumbPosition;
    double size = Theme.scrollBar().getFixedWidth();
    double trackLength = getOrientation() == BOrientation.horizontal ? (int)track.width : (int)track.height;
    double min    = getMin();
    double max    = getMax();
    double range  = Math.abs(max - min);    
    double extent = getExtent();
    if (extent > range) extent = range;
    
    // if the track length is smaller
    // than SIZE, than we don't even bother
    if (trackLength < size)
      { thumb.width = 0; thumb.height = 0; return; }
    
    // compute length of the thumb
    double len = (int)(trackLength * extent/(range)) + 1;
    
    // force the thumb to have a min length; if we have
    // to make the thumb bigger than the size which truly
    // reflects the extent, then we store the number of 
    // extra pixels in the thumbPadding field
    thumbPadding = 0;
    if (len < minThumbSize) 
    {
      thumbPadding = minThumbSize - len;
      len = minThumbSize; 
    }
    
    // now we can compute the thumb's offset 
    // from the start of the track
    double offset = (int)((trackLength-thumbPadding) * (pos-min)/(range));

    // compute thumb bounds
    if (getOrientation() == BOrientation.horizontal)
    {
      thumb.x = track.x + offset; thumb.y = 0;
      thumb.width = len; thumb.height = size;
    }
    else
    {
      thumb.x = 0; thumb.y = track.y + offset;
      thumb.width = size; thumb.height = len;
    }
  }
  
  private void dragThumb(double mx, double my)
  {
    // compute thumb bounds
    double trackLength;       // length of the track
    double offset;            // the desired position of thumb
    boolean maxed = false; // is thumb at max position
    if (getOrientation() == BOrientation.horizontal)
    {
      trackLength = track.width; 
      offset = mx - track.x - thumbGrab;
      if (trackLength <= offset + thumb.width)
        { offset = trackLength - thumb.width; maxed = true; }
    }
    else
    {
      trackLength = track.height; 
      offset = my - track.y - thumbGrab;
      if (trackLength <= offset + thumb.height)
        { offset = trackLength - thumb.height; maxed = true; }
    }
    
    // if the thumb is at the at the end of the track 
    // then max out position, otherwise compute the 
    // position value which would put the thumb grab 
    // point directly under the current mouse position
    if (maxed)
    {
      resetPosition( getMax() - getExtent(), BScrollEvent.TRACK );
    }
    else
    {
      double min = getMin();
      double max = getMax();
      double range = Math.abs(max - min);
      double extent = getExtent();
      double pos = min + (range / (trackLength-thumbPadding) * offset);
      if (pos < min) pos = min;
      if (pos > max-extent) pos = max - extent;
      resetPosition(pos, BScrollEvent.TRACK);
    }
  }
  
////////////////////////////////////////////////////////////////
// Paint
////////////////////////////////////////////////////////////////

  public void paint(Graphics g)
  { 
    ScrollBarTheme theme = Theme.scrollBar();
    if (getOrientation() == BOrientation.horizontal)
    {
      theme.paintButton(g, this, up, LEFT, up == inside && up == armed);
      theme.paintButton(g, this, down, RIGHT, down == inside && down == armed);
    }
    else
    {
      theme.paintButton(g, this, up, UP, up == inside && up == armed);
      theme.paintButton(g, this, down, DOWN, down == inside && down == armed);
    }
    theme.paintTrack(g, this, track);
    theme.paintThumb(g, this, thumb);
  }

  public String getStyleSelector() { return "scroll-bar"; }

////////////////////////////////////////////////////////////////
// Mouse Eventing
////////////////////////////////////////////////////////////////

  public void mouseReleased(BMouseEvent event)
  {
    armed = null;
    thumbGrab = -1;
    layoutThumb();
    repaint();
  }

  public void mousePressed(BMouseEvent event)
  {
    armed = buttonAt(event);
    
    // if we grabbed the thumb, then we need to 
    // keep track of the distance from the thumb's 
    // pointer position and the actual grab point
    if (armed == thumb) 
    {
      thumbGrab = (getOrientation() == BOrientation.horizontal) ? 
                  (int)(event.getX() - thumb.x) : 
                  (int)(event.getY() - thumb.y);
      repaint();
    }
    
    // mouse pulsed will handle the press
  }

  public void mouseExited(BMouseEvent event)
  {
    inside = buttonAt(event);
    repaint();
  }

  public void mouseEntered(BMouseEvent event)
  {
    inside = buttonAt(event);
    repaint();
  }

  public void mouseMoved(BMouseEvent event)
  {
    RectGeom oldInside = inside;
    inside = buttonAt(event);
    if (oldInside != inside) repaint();
  }

  public void mouseDragged(BMouseEvent event)
  {
    RectGeom oldInside = inside;
    inside = buttonAt(event);
    if (armed == thumb)
      dragThumb(event.getX(), event.getY());
    else if (oldInside != inside)
      repaint();
  }

  public void mousePulsed(BMouseEvent event)
  {
    if (armed == up && inside == up) 
      {  decrementByUnit(); return; }    
      
    if (armed == down && inside == down)
      { incrementByUnit(); return; }
    
    // if the pulse is over the track, but
    // not over the thumb (and the thumb is
    // actually showing) then we block 
    // increment or decrement
    double x = event.getX(), y = event.getY();
    if (track.contains(x,y) && !thumb.contains(x,y) && thumb.width > 0)
    {
      if (getOrientation() == BOrientation.horizontal)
      {
        if (x < thumb.x) decrementByBlock();
        else if ( x > thumb.x + thumb.width) incrementByBlock();
      }
      else
      {
        if (y < thumb.y) decrementByBlock();
        else if ( y > thumb.y + thumb.height) incrementByBlock();
      }
    }
  }
  
  private RectGeom buttonAt(BMouseEvent event)
  {
    double x = event.getX(), y = event.getY();
    if (up.contains(x,y)) return up;
    if (down.contains(x,y)) return down;
    if (thumb.contains(x,y)) return thumb;
    return null;
  }

///////////////////////////////////////////////////////////
// Overrides
/////////////////////////////////////////////////////////// 

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/scrollBar.png");

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  // this is the width of a vertical scrollbar,
  // or height of a horizontal scrollbar - it is
  // also the square for the buttons
  private static final int SIZE = 13;
  
  private static final int UP    = 0;
  private static final int DOWN  = 1;
  private static final int LEFT  = 2;
  private static final int RIGHT = 3;

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private static Context resetContext = new BasicContext();
  private static final int minThumbSize = 7;

  private RectGeom up    = new RectGeom();
  private RectGeom down  = new RectGeom();
  private RectGeom thumb = new RectGeom();
  private RectGeom track = new RectGeom();
  private double thumbGrab;
  private RectGeom inside;               
  private RectGeom armed;   
  private double thumbPosition;
  private double thumbPadding;   // when thumb size bigger than true extent

  private long lastScrollTimestamp;
  private long scrollStartPosition;
  private double accumulatedScroll;
}
