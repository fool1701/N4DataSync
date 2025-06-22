/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import javax.baja.gx.BColor;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;

/***************************************************************
 * BHistoryPointList
 *
 * @author    John Huffman
 * @creation  19 Jul 2007
 * @version   $Revision: 6$ $Date: 9/12/07 3:17:07 PM EDT$
 * @since     Baja 1.0
***************************************************************/
@NiagaraType
/*
 The maximum number of samples for each live history component
 */
@NiagaraProperty(
  name = "maxSamples",
  type = "int",
  defaultValue = "5000",
  facets = @Facet("BFacets.makeInt(100, 10000)")
)
/*
 The maximum number of samples to show for each live history component
 when the chart is not zoomed
 */
@NiagaraProperty(
  name = "maxVisible",
  type = "int",
  defaultValue = "5000",
  flags = Flags.TRANSIENT | Flags.HIDDEN,
  facets = @Facet("BFacets.makeInt(100, 10000)")
)
/*
 The size of the visible chart window
 */
@NiagaraProperty(
  name = "timeWindow",
  type = "BRelTime",
  defaultValue = "BRelTime.make(10L * 60L * 1000L)",
  facets = @Facet("BFacets.make( BFacets.SHOW_SECONDS, BBoolean.make(false), BFacets.MIN, BRelTime.makeHours(0) )")
)
/*
 The color of the background
 */
@NiagaraProperty(
  name = "background",
  type = "BColor",
  defaultValue = "BColor.NULL",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"wbutil:ColorFE\"))")
)
/*
 Show Horizontal Grid Lines
 */
@NiagaraProperty(
  name = "showHorizontalGridLines",
  type = "boolean",
  defaultValue = "true"
)
/*
 Show Vertical Grid Lines
 */
@NiagaraProperty(
  name = "showVerticalGridLines",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraAction(
  name = "addItem",
  parameterType = "BHistoryPointListItem",
  defaultValue = "new BHistoryPointListItem()"
)
public class BHistoryPointList
 extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BHistoryPointList(3963243242)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "maxSamples"

  /**
   * Slot for the {@code maxSamples} property.
   * The maximum number of samples for each live history component
   * @see #getMaxSamples
   * @see #setMaxSamples
   */
  public static final Property maxSamples = newProperty(0, 5000, BFacets.makeInt(100, 10000));

  /**
   * Get the {@code maxSamples} property.
   * The maximum number of samples for each live history component
   * @see #maxSamples
   */
  public int getMaxSamples() { return getInt(maxSamples); }

  /**
   * Set the {@code maxSamples} property.
   * The maximum number of samples for each live history component
   * @see #maxSamples
   */
  public void setMaxSamples(int v) { setInt(maxSamples, v, null); }

  //endregion Property "maxSamples"

  //region Property "maxVisible"

  /**
   * Slot for the {@code maxVisible} property.
   * The maximum number of samples to show for each live history component
   * when the chart is not zoomed
   * @see #getMaxVisible
   * @see #setMaxVisible
   */
  public static final Property maxVisible = newProperty(Flags.TRANSIENT | Flags.HIDDEN, 5000, BFacets.makeInt(100, 10000));

  /**
   * Get the {@code maxVisible} property.
   * The maximum number of samples to show for each live history component
   * when the chart is not zoomed
   * @see #maxVisible
   */
  public int getMaxVisible() { return getInt(maxVisible); }

  /**
   * Set the {@code maxVisible} property.
   * The maximum number of samples to show for each live history component
   * when the chart is not zoomed
   * @see #maxVisible
   */
  public void setMaxVisible(int v) { setInt(maxVisible, v, null); }

  //endregion Property "maxVisible"

  //region Property "timeWindow"

  /**
   * Slot for the {@code timeWindow} property.
   * The size of the visible chart window
   * @see #getTimeWindow
   * @see #setTimeWindow
   */
  public static final Property timeWindow = newProperty(0, BRelTime.make(10L * 60L * 1000L), BFacets.make( BFacets.SHOW_SECONDS, BBoolean.make(false), BFacets.MIN, BRelTime.makeHours(0) ));

  /**
   * Get the {@code timeWindow} property.
   * The size of the visible chart window
   * @see #timeWindow
   */
  public BRelTime getTimeWindow() { return (BRelTime)get(timeWindow); }

  /**
   * Set the {@code timeWindow} property.
   * The size of the visible chart window
   * @see #timeWindow
   */
  public void setTimeWindow(BRelTime v) { set(timeWindow, v, null); }

  //endregion Property "timeWindow"

  //region Property "background"

  /**
   * Slot for the {@code background} property.
   * The color of the background
   * @see #getBackground
   * @see #setBackground
   */
  public static final Property background = newProperty(0, BColor.NULL, BFacets.make(BFacets.FIELD_EDITOR, BString.make("wbutil:ColorFE")));

  /**
   * Get the {@code background} property.
   * The color of the background
   * @see #background
   */
  public BColor getBackground() { return (BColor)get(background); }

  /**
   * Set the {@code background} property.
   * The color of the background
   * @see #background
   */
  public void setBackground(BColor v) { set(background, v, null); }

  //endregion Property "background"

  //region Property "showHorizontalGridLines"

  /**
   * Slot for the {@code showHorizontalGridLines} property.
   * Show Horizontal Grid Lines
   * @see #getShowHorizontalGridLines
   * @see #setShowHorizontalGridLines
   */
  public static final Property showHorizontalGridLines = newProperty(0, true, null);

  /**
   * Get the {@code showHorizontalGridLines} property.
   * Show Horizontal Grid Lines
   * @see #showHorizontalGridLines
   */
  public boolean getShowHorizontalGridLines() { return getBoolean(showHorizontalGridLines); }

  /**
   * Set the {@code showHorizontalGridLines} property.
   * Show Horizontal Grid Lines
   * @see #showHorizontalGridLines
   */
  public void setShowHorizontalGridLines(boolean v) { setBoolean(showHorizontalGridLines, v, null); }

  //endregion Property "showHorizontalGridLines"

  //region Property "showVerticalGridLines"

  /**
   * Slot for the {@code showVerticalGridLines} property.
   * Show Vertical Grid Lines
   * @see #getShowVerticalGridLines
   * @see #setShowVerticalGridLines
   */
  public static final Property showVerticalGridLines = newProperty(0, true, null);

  /**
   * Get the {@code showVerticalGridLines} property.
   * Show Vertical Grid Lines
   * @see #showVerticalGridLines
   */
  public boolean getShowVerticalGridLines() { return getBoolean(showVerticalGridLines); }

  /**
   * Set the {@code showVerticalGridLines} property.
   * Show Vertical Grid Lines
   * @see #showVerticalGridLines
   */
  public void setShowVerticalGridLines(boolean v) { setBoolean(showVerticalGridLines, v, null); }

  //endregion Property "showVerticalGridLines"

  //region Action "addItem"

  /**
   * Slot for the {@code addItem} action.
   * @see #addItem(BHistoryPointListItem parameter)
   */
  public static final Action addItem = newAction(0, new BHistoryPointListItem(), null);

  /**
   * Invoke the {@code addItem} action.
   * @see #addItem
   */
  public void addItem(BHistoryPointListItem parameter) { invoke(addItem, parameter, null); }

  //endregion Action "addItem"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryPointList.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  // Default Constructor //
  public BHistoryPointList()
  {
  }

  @Override
  public void added(Property prop, Context cx )
  {
    super.added( prop, cx );

    if ( !isRunning() )
    {
        return;
    }

    if (prop.getType().is(BHistoryPointListItem.TYPE))
    {
      BHistoryPointListItem item = (BHistoryPointListItem) get( prop );
      if ( item.getLineColor().isNull() )
      {
        BHistoryPointListItem[] historyPointListItems = null;
        if ( ( historyPointListItems = getChildren( BHistoryPointListItem.class )) != null )
        {
          item.setLineColor( DEFAULT_COLORS[( historyPointListItems.length - 1 ) % DEFAULT_COLORS.length] );
        }
      }
    }
  }

  public void doAddItem( BHistoryPointListItem item )
  {
    String itemName = null;

    // Get the name the user wants to call the item
    if ( ( itemName = resolveItemName( item ) ) == null )
    {
      return;
    }

    // make sure the name is unique
    if ( get( SlotPath.escape( itemName ) ) == null )
    {
      // remove the point name slot
      item.remove( SlotPath.escape( ITEM_SLOT_NAME ) );

      // add the point
      add( SlotPath.escape( itemName ), item );
    }
  }

  @Override
  public BValue getActionParameterDefault(Action action )
  {
    BHistoryPointListItem item = null;
    BHistoryPointListItem[] historyPointListItems = null;

    try
    {
      if ( action == addItem )
      {
        // create a new history container point
        item = new BHistoryPointListItem();

        if ( ( historyPointListItems = getChildren( BHistoryPointListItem.class )) != null )
          item.setLineColor( DEFAULT_COLORS[historyPointListItems.length % DEFAULT_COLORS.length] );

        // add the slot for the user to enter the name of the history list item
        item.add( SlotPath.escape( ITEM_SLOT_NAME ), BFormat.make( DEFAULT_ITEM_NAME ) );
        return ( item );
      }
    }

    catch ( Exception e )
    {
      e.printStackTrace();
    }

    return super.getActionParameterDefault( action );
  }

  /**
   * Resolve the history point list item name pattern to a string.
   */
  public String resolveItemName( BHistoryPointListItem item )
  {
    BComponent component = null;
    BFormat pattern = null;

    pattern = (BFormat) item.get( SlotPath.escape( ITEM_SLOT_NAME ) );

    // This is a tricky issue. It looks funny but it's correct.
    // The resolved pattern must be unescaped because it may include
    // slot names of ancestors (ex. "%parent.name%") which are already
    // escaped. Sometimes the result needs to be escaped because
    // it may just be static test (ex. "My History"). By unescaping
    // the result first, the escape will always return the right result.
    try
    {
      component = (BComponent) item.getHistoryExtension().resolve().get();
    }

    catch ( Exception e )
    {
      return ( null );
    }

    return SlotPath.escape( SlotPath.unescape( pattern.format( component ) ) );
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("folder.png");


  ////////////////////////////////////////////////////////////////
  // Attributes
  ////////////////////////////////////////////////////////////////

  private static BColor[] DEFAULT_COLORS =
  {
    BColor.blue,
    BColor.red,
    BColor.lightGreen,
    BColor.magenta,
    BColor.orange,
    BColor.yellow,
    BColor.cyan,
    BColor.olive,
    BColor.brown,
    BColor.green,
    BColor.indianRed,
    BColor.salmon
  };

  private static final String ITEM_SLOT_NAME = "Item Name";
  private static final String DEFAULT_ITEM_NAME = "%parent.name%";
}
