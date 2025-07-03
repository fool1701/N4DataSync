/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import javax.baja.chart.binding.BAxisBound;
import javax.baja.gx.BColor;
import javax.baja.gx.BPen;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BHistoryPointListItem
 *
 * @author    John Huffman
 * @creation  19 Jul 2007
 * @version   $Revision: 6$ $Date: 9/12/07 3:16:01 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The ord to the history point
 */
@NiagaraProperty(
  name = "historyExtension",
  type = "BOrd",
  defaultValue = "BOrd.DEFAULT"
)
/*
 Indicates whether this point should be charted when the view is displayed
 */
@NiagaraProperty(
  name = "displayOnStartup",
  type = "boolean",
  defaultValue = "true"
)
/*
 Offset from the current time
 */
@NiagaraProperty(
  name = "startTime",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0L * 60L * 60L * 1000L)",
  facets = @Facet("BFacets.make( BFacets.SHOW_SECONDS, BBoolean.make(false), BFacets.MIN, BRelTime.makeHours(0) )")
)
/*
 The frequency of live updates
 */
@NiagaraProperty(
  name = "sampleRate",
  type = "BSampleRate",
  defaultValue = "BSampleRate.DEFAULT"
)
/*
 The min and max range for the values of this point
 */
@NiagaraProperty(
  name = "minValueRange",
  type = "BAxisBound",
  defaultValue = "BAxisBound.make( BDouble.make(0))",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"chart:NumericAxisBoundFE\"))")
)
/*
 The min and max range for the values of this point
 */
@NiagaraProperty(
  name = "maxValueRange",
  type = "BAxisBound",
  defaultValue = "BAxisBound.make( BDouble.make(100))",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"chart:NumericAxisBoundFE\"))")
)
/*
 The color of the line on the chart
 */
@NiagaraProperty(
  name = "lineColor",
  type = "BColor",
  defaultValue = "BColor.NULL",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"wbutil:ColorFE\"))")
)
/*
 The Pen
 */
@NiagaraProperty(
  name = "pen",
  type = "BPen",
  defaultValue = "BPen.DEFAULT"
)
public class BHistoryPointListItem
  extends BComponent
//  extends BStruct  // The problem with extending BStruct is you can't add/remove/hide/unhide slots on the fly.
                     // I want to be able to add a slot for the user to be able to enter the point name
                     // when the "addPoint" action is invoked in BHistoryPointList but not be visible on the property sheet view.
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BHistoryPointListItem(2339710509)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "historyExtension"

  /**
   * Slot for the {@code historyExtension} property.
   * The ord to the history point
   * @see #getHistoryExtension
   * @see #setHistoryExtension
   */
  public static final Property historyExtension = newProperty(0, BOrd.DEFAULT, null);

  /**
   * Get the {@code historyExtension} property.
   * The ord to the history point
   * @see #historyExtension
   */
  public BOrd getHistoryExtension() { return (BOrd)get(historyExtension); }

  /**
   * Set the {@code historyExtension} property.
   * The ord to the history point
   * @see #historyExtension
   */
  public void setHistoryExtension(BOrd v) { set(historyExtension, v, null); }

  //endregion Property "historyExtension"

  //region Property "displayOnStartup"

  /**
   * Slot for the {@code displayOnStartup} property.
   * Indicates whether this point should be charted when the view is displayed
   * @see #getDisplayOnStartup
   * @see #setDisplayOnStartup
   */
  public static final Property displayOnStartup = newProperty(0, true, null);

  /**
   * Get the {@code displayOnStartup} property.
   * Indicates whether this point should be charted when the view is displayed
   * @see #displayOnStartup
   */
  public boolean getDisplayOnStartup() { return getBoolean(displayOnStartup); }

  /**
   * Set the {@code displayOnStartup} property.
   * Indicates whether this point should be charted when the view is displayed
   * @see #displayOnStartup
   */
  public void setDisplayOnStartup(boolean v) { setBoolean(displayOnStartup, v, null); }

  //endregion Property "displayOnStartup"

  //region Property "startTime"

  /**
   * Slot for the {@code startTime} property.
   * Offset from the current time
   * @see #getStartTime
   * @see #setStartTime
   */
  public static final Property startTime = newProperty(0, BRelTime.make(0L * 60L * 60L * 1000L), BFacets.make( BFacets.SHOW_SECONDS, BBoolean.make(false), BFacets.MIN, BRelTime.makeHours(0) ));

  /**
   * Get the {@code startTime} property.
   * Offset from the current time
   * @see #startTime
   */
  public BRelTime getStartTime() { return (BRelTime)get(startTime); }

  /**
   * Set the {@code startTime} property.
   * Offset from the current time
   * @see #startTime
   */
  public void setStartTime(BRelTime v) { set(startTime, v, null); }

  //endregion Property "startTime"

  //region Property "sampleRate"

  /**
   * Slot for the {@code sampleRate} property.
   * The frequency of live updates
   * @see #getSampleRate
   * @see #setSampleRate
   */
  public static final Property sampleRate = newProperty(0, BSampleRate.DEFAULT, null);

  /**
   * Get the {@code sampleRate} property.
   * The frequency of live updates
   * @see #sampleRate
   */
  public BSampleRate getSampleRate() { return (BSampleRate)get(sampleRate); }

  /**
   * Set the {@code sampleRate} property.
   * The frequency of live updates
   * @see #sampleRate
   */
  public void setSampleRate(BSampleRate v) { set(sampleRate, v, null); }

  //endregion Property "sampleRate"

  //region Property "minValueRange"

  /**
   * Slot for the {@code minValueRange} property.
   * The min and max range for the values of this point
   * @see #getMinValueRange
   * @see #setMinValueRange
   */
  public static final Property minValueRange = newProperty(0, BAxisBound.make( BDouble.make(0)), BFacets.make(BFacets.FIELD_EDITOR, BString.make("chart:NumericAxisBoundFE")));

  /**
   * Get the {@code minValueRange} property.
   * The min and max range for the values of this point
   * @see #minValueRange
   */
  public BAxisBound getMinValueRange() { return (BAxisBound)get(minValueRange); }

  /**
   * Set the {@code minValueRange} property.
   * The min and max range for the values of this point
   * @see #minValueRange
   */
  public void setMinValueRange(BAxisBound v) { set(minValueRange, v, null); }

  //endregion Property "minValueRange"

  //region Property "maxValueRange"

  /**
   * Slot for the {@code maxValueRange} property.
   * The min and max range for the values of this point
   * @see #getMaxValueRange
   * @see #setMaxValueRange
   */
  public static final Property maxValueRange = newProperty(0, BAxisBound.make( BDouble.make(100)), BFacets.make(BFacets.FIELD_EDITOR, BString.make("chart:NumericAxisBoundFE")));

  /**
   * Get the {@code maxValueRange} property.
   * The min and max range for the values of this point
   * @see #maxValueRange
   */
  public BAxisBound getMaxValueRange() { return (BAxisBound)get(maxValueRange); }

  /**
   * Set the {@code maxValueRange} property.
   * The min and max range for the values of this point
   * @see #maxValueRange
   */
  public void setMaxValueRange(BAxisBound v) { set(maxValueRange, v, null); }

  //endregion Property "maxValueRange"

  //region Property "lineColor"

  /**
   * Slot for the {@code lineColor} property.
   * The color of the line on the chart
   * @see #getLineColor
   * @see #setLineColor
   */
  public static final Property lineColor = newProperty(0, BColor.NULL, BFacets.make(BFacets.FIELD_EDITOR, BString.make("wbutil:ColorFE")));

  /**
   * Get the {@code lineColor} property.
   * The color of the line on the chart
   * @see #lineColor
   */
  public BColor getLineColor() { return (BColor)get(lineColor); }

  /**
   * Set the {@code lineColor} property.
   * The color of the line on the chart
   * @see #lineColor
   */
  public void setLineColor(BColor v) { set(lineColor, v, null); }

  //endregion Property "lineColor"

  //region Property "pen"

  /**
   * Slot for the {@code pen} property.
   * The Pen
   * @see #getPen
   * @see #setPen
   */
  public static final Property pen = newProperty(0, BPen.DEFAULT, null);

  /**
   * Get the {@code pen} property.
   * The Pen
   * @see #pen
   */
  public BPen getPen() { return (BPen)get(pen); }

  /**
   * Set the {@code pen} property.
   * The Pen
   * @see #pen
   */
  public void setPen(BPen v) { set(pen, v, null); }

  //endregion Property "pen"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryPointListItem.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Default constructor.
   */
  public BHistoryPointListItem()
  {
  }

  /**
   * Overrides isParentLegal method.  BHistoryPointListItem
   * must reside under a BHistoryPointList.
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return (parent instanceof BHistoryPointList);
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("monitor.png");
}
