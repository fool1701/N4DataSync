/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.pane;

import javax.baja.gx.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.enums.*;

import com.tridium.ui.theme.Theme;

/**
 * BGridPane provides flexible layout based on a grid with a 
 * predefined number of columns. Cells are laid out left to
 * right, flowing to the next row based on the columnCount
 * property.
 * <p>
 * Row height is determined by the max preferred height of the
 * widgets in the row.  If uniformRowHeight is true then
 * all row heights are sized using the max row.
 * <p>
 * Column width is determined by the max preferred width of the
 * widgets in the column.  If uniformColumnWidth is true then
 * all column widths are sized using the max column.
 * <p>
 * <p>
 * If rowAlign is set to fill then all widgets in a given row are 
 * sized to fill the row height.  Otherwise the widgets use their 
 * preferred height and are aligned accordingly.
 * <p>
 * If columnAlign is set to fill then all widgets in a given column 
 * are sized to fill the column width.  Otherwise the widgets use 
 * their preferred width and are aligned accordingly.
 * <p>
 * The columnGap field specifies how many pixels to leave between columns.  
 * The rowGap field specifies how many pixels to leave been rows.
 * <p>
 * By default if the actual space of the pane is bigger than the preferred 
 * size, then the halign and valign fields determine where to put the 
 * extra space.  Or the stretchColumn and stretchRow properties may be 
 * used to indicate that a specific column or row should be stretched to 
 * fill the additional space.  Enabling the stretch feature trumps 
 * pane alignment.
 * <p>
 * If the colorRows field is true then alternating rows
 * are shaded to produce a stripped effect.
 *
 * @author    Brian Frank
 * @creation  7 Feb 02
 * @version   $Revision: 26$ $Date: 4/27/05 9:29:30 AM EDT$
 * @since     Baja 1.0
 */                              
@NiagaraType
/*
 Number of columns in the grid
 */
@NiagaraProperty(
  name = "columnCount",
  type = "int",
  defaultValue = "2",
  facets = @Facet("BFacets.make(BFacets.MIN, BInteger.make(1))")
)
/*
 Determines how to use extra vertical space of entire pane.
 */
@NiagaraProperty(
  name = "valign",
  type = "BValign",
  defaultValue = "BValign.center"
)
/*
 Determines how to use extra horizontal space of entire pane.
 */
@NiagaraProperty(
  name = "halign",
  type = "BHalign",
  defaultValue = "BHalign.center"
)
/*
 Determines how to use extra vertical space within rows.
 */
@NiagaraProperty(
  name = "rowAlign",
  type = "BValign",
  defaultValue = "BValign.center"
)
/*
 Determines how to use extra horizontal space within columns.
 */
@NiagaraProperty(
  name = "columnAlign",
  type = "BHalign",
  defaultValue = "BHalign.left"
)
/*
 Space to leave between rows.
 */
@NiagaraProperty(
  name = "rowGap",
  type = "double",
  defaultValue = "3"
)
/*
 Space to leave between columns.
 */
@NiagaraProperty(
  name = "columnGap",
  type = "double",
  defaultValue = "3"
)
/*
 Make all rows in the pane have the same height.
 */
@NiagaraProperty(
  name = "uniformRowHeight",
  type = "boolean",
  defaultValue = "false"
)
/*
 Make all columns in the pane have the same width.
 */
@NiagaraProperty(
  name = "uniformColumnWidth",
  type = "boolean",
  defaultValue = "false"
)
/*
 If the actual pane height is larger than the preferred
 pane height, and this value is a valid zero indexed row,
 then the specified row is used to fill remaining height.
 Using this feature trumps the valign property.
 Use -1 to disable this feature.
 */
@NiagaraProperty(
  name = "stretchRow",
  type = "int",
  defaultValue = "-1",
  facets = @Facet("BFacets.make(BFacets.MIN, BInteger.make(-1))")
)
/*
 If the actual pane width is larger than the preferred
 pane width, and this value is a valid zero indexed column,
 then the specified column is used to fill remaining width.
 Using this feature trumps the halign property.
 Use -1 to disable this feature.
 */
@NiagaraProperty(
  name = "stretchColumn",
  type = "int",
  defaultValue = "-1",
  facets = @Facet("BFacets.make(BFacets.MIN, BInteger.make(-1))")
)
/*
 Paint color banding on rows.
 */
@NiagaraProperty(
  name = "colorRows",
  type = "boolean",
  defaultValue = "false"
)
/*
 The brush used to paint colored rows.
 */
@NiagaraProperty(
  name = "bandBrush",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
public class BGridPane
  extends BPane
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BGridPane(1314430958)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "columnCount"

  /**
   * Slot for the {@code columnCount} property.
   * Number of columns in the grid
   * @see #getColumnCount
   * @see #setColumnCount
   */
  public static final Property columnCount = newProperty(0, 2, BFacets.make(BFacets.MIN, BInteger.make(1)));

  /**
   * Get the {@code columnCount} property.
   * Number of columns in the grid
   * @see #columnCount
   */
  public int getColumnCount() { return getInt(columnCount); }

  /**
   * Set the {@code columnCount} property.
   * Number of columns in the grid
   * @see #columnCount
   */
  public void setColumnCount(int v) { setInt(columnCount, v, null); }

  //endregion Property "columnCount"

  //region Property "valign"

  /**
   * Slot for the {@code valign} property.
   * Determines how to use extra vertical space of entire pane.
   * @see #getValign
   * @see #setValign
   */
  public static final Property valign = newProperty(0, BValign.center, null);

  /**
   * Get the {@code valign} property.
   * Determines how to use extra vertical space of entire pane.
   * @see #valign
   */
  public BValign getValign() { return (BValign)get(valign); }

  /**
   * Set the {@code valign} property.
   * Determines how to use extra vertical space of entire pane.
   * @see #valign
   */
  public void setValign(BValign v) { set(valign, v, null); }

  //endregion Property "valign"

  //region Property "halign"

  /**
   * Slot for the {@code halign} property.
   * Determines how to use extra horizontal space of entire pane.
   * @see #getHalign
   * @see #setHalign
   */
  public static final Property halign = newProperty(0, BHalign.center, null);

  /**
   * Get the {@code halign} property.
   * Determines how to use extra horizontal space of entire pane.
   * @see #halign
   */
  public BHalign getHalign() { return (BHalign)get(halign); }

  /**
   * Set the {@code halign} property.
   * Determines how to use extra horizontal space of entire pane.
   * @see #halign
   */
  public void setHalign(BHalign v) { set(halign, v, null); }

  //endregion Property "halign"

  //region Property "rowAlign"

  /**
   * Slot for the {@code rowAlign} property.
   * Determines how to use extra vertical space within rows.
   * @see #getRowAlign
   * @see #setRowAlign
   */
  public static final Property rowAlign = newProperty(0, BValign.center, null);

  /**
   * Get the {@code rowAlign} property.
   * Determines how to use extra vertical space within rows.
   * @see #rowAlign
   */
  public BValign getRowAlign() { return (BValign)get(rowAlign); }

  /**
   * Set the {@code rowAlign} property.
   * Determines how to use extra vertical space within rows.
   * @see #rowAlign
   */
  public void setRowAlign(BValign v) { set(rowAlign, v, null); }

  //endregion Property "rowAlign"

  //region Property "columnAlign"

  /**
   * Slot for the {@code columnAlign} property.
   * Determines how to use extra horizontal space within columns.
   * @see #getColumnAlign
   * @see #setColumnAlign
   */
  public static final Property columnAlign = newProperty(0, BHalign.left, null);

  /**
   * Get the {@code columnAlign} property.
   * Determines how to use extra horizontal space within columns.
   * @see #columnAlign
   */
  public BHalign getColumnAlign() { return (BHalign)get(columnAlign); }

  /**
   * Set the {@code columnAlign} property.
   * Determines how to use extra horizontal space within columns.
   * @see #columnAlign
   */
  public void setColumnAlign(BHalign v) { set(columnAlign, v, null); }

  //endregion Property "columnAlign"

  //region Property "rowGap"

  /**
   * Slot for the {@code rowGap} property.
   * Space to leave between rows.
   * @see #getRowGap
   * @see #setRowGap
   */
  public static final Property rowGap = newProperty(0, 3, null);

  /**
   * Get the {@code rowGap} property.
   * Space to leave between rows.
   * @see #rowGap
   */
  public double getRowGap() { return getDouble(rowGap); }

  /**
   * Set the {@code rowGap} property.
   * Space to leave between rows.
   * @see #rowGap
   */
  public void setRowGap(double v) { setDouble(rowGap, v, null); }

  //endregion Property "rowGap"

  //region Property "columnGap"

  /**
   * Slot for the {@code columnGap} property.
   * Space to leave between columns.
   * @see #getColumnGap
   * @see #setColumnGap
   */
  public static final Property columnGap = newProperty(0, 3, null);

  /**
   * Get the {@code columnGap} property.
   * Space to leave between columns.
   * @see #columnGap
   */
  public double getColumnGap() { return getDouble(columnGap); }

  /**
   * Set the {@code columnGap} property.
   * Space to leave between columns.
   * @see #columnGap
   */
  public void setColumnGap(double v) { setDouble(columnGap, v, null); }

  //endregion Property "columnGap"

  //region Property "uniformRowHeight"

  /**
   * Slot for the {@code uniformRowHeight} property.
   * Make all rows in the pane have the same height.
   * @see #getUniformRowHeight
   * @see #setUniformRowHeight
   */
  public static final Property uniformRowHeight = newProperty(0, false, null);

  /**
   * Get the {@code uniformRowHeight} property.
   * Make all rows in the pane have the same height.
   * @see #uniformRowHeight
   */
  public boolean getUniformRowHeight() { return getBoolean(uniformRowHeight); }

  /**
   * Set the {@code uniformRowHeight} property.
   * Make all rows in the pane have the same height.
   * @see #uniformRowHeight
   */
  public void setUniformRowHeight(boolean v) { setBoolean(uniformRowHeight, v, null); }

  //endregion Property "uniformRowHeight"

  //region Property "uniformColumnWidth"

  /**
   * Slot for the {@code uniformColumnWidth} property.
   * Make all columns in the pane have the same width.
   * @see #getUniformColumnWidth
   * @see #setUniformColumnWidth
   */
  public static final Property uniformColumnWidth = newProperty(0, false, null);

  /**
   * Get the {@code uniformColumnWidth} property.
   * Make all columns in the pane have the same width.
   * @see #uniformColumnWidth
   */
  public boolean getUniformColumnWidth() { return getBoolean(uniformColumnWidth); }

  /**
   * Set the {@code uniformColumnWidth} property.
   * Make all columns in the pane have the same width.
   * @see #uniformColumnWidth
   */
  public void setUniformColumnWidth(boolean v) { setBoolean(uniformColumnWidth, v, null); }

  //endregion Property "uniformColumnWidth"

  //region Property "stretchRow"

  /**
   * Slot for the {@code stretchRow} property.
   * If the actual pane height is larger than the preferred
   * pane height, and this value is a valid zero indexed row,
   * then the specified row is used to fill remaining height.
   * Using this feature trumps the valign property.
   * Use -1 to disable this feature.
   * @see #getStretchRow
   * @see #setStretchRow
   */
  public static final Property stretchRow = newProperty(0, -1, BFacets.make(BFacets.MIN, BInteger.make(-1)));

  /**
   * Get the {@code stretchRow} property.
   * If the actual pane height is larger than the preferred
   * pane height, and this value is a valid zero indexed row,
   * then the specified row is used to fill remaining height.
   * Using this feature trumps the valign property.
   * Use -1 to disable this feature.
   * @see #stretchRow
   */
  public int getStretchRow() { return getInt(stretchRow); }

  /**
   * Set the {@code stretchRow} property.
   * If the actual pane height is larger than the preferred
   * pane height, and this value is a valid zero indexed row,
   * then the specified row is used to fill remaining height.
   * Using this feature trumps the valign property.
   * Use -1 to disable this feature.
   * @see #stretchRow
   */
  public void setStretchRow(int v) { setInt(stretchRow, v, null); }

  //endregion Property "stretchRow"

  //region Property "stretchColumn"

  /**
   * Slot for the {@code stretchColumn} property.
   * If the actual pane width is larger than the preferred
   * pane width, and this value is a valid zero indexed column,
   * then the specified column is used to fill remaining width.
   * Using this feature trumps the halign property.
   * Use -1 to disable this feature.
   * @see #getStretchColumn
   * @see #setStretchColumn
   */
  public static final Property stretchColumn = newProperty(0, -1, BFacets.make(BFacets.MIN, BInteger.make(-1)));

  /**
   * Get the {@code stretchColumn} property.
   * If the actual pane width is larger than the preferred
   * pane width, and this value is a valid zero indexed column,
   * then the specified column is used to fill remaining width.
   * Using this feature trumps the halign property.
   * Use -1 to disable this feature.
   * @see #stretchColumn
   */
  public int getStretchColumn() { return getInt(stretchColumn); }

  /**
   * Set the {@code stretchColumn} property.
   * If the actual pane width is larger than the preferred
   * pane width, and this value is a valid zero indexed column,
   * then the specified column is used to fill remaining width.
   * Using this feature trumps the halign property.
   * Use -1 to disable this feature.
   * @see #stretchColumn
   */
  public void setStretchColumn(int v) { setInt(stretchColumn, v, null); }

  //endregion Property "stretchColumn"

  //region Property "colorRows"

  /**
   * Slot for the {@code colorRows} property.
   * Paint color banding on rows.
   * @see #getColorRows
   * @see #setColorRows
   */
  public static final Property colorRows = newProperty(0, false, null);

  /**
   * Get the {@code colorRows} property.
   * Paint color banding on rows.
   * @see #colorRows
   */
  public boolean getColorRows() { return getBoolean(colorRows); }

  /**
   * Set the {@code colorRows} property.
   * Paint color banding on rows.
   * @see #colorRows
   */
  public void setColorRows(boolean v) { setBoolean(colorRows, v, null); }

  //endregion Property "colorRows"

  //region Property "bandBrush"

  /**
   * Slot for the {@code bandBrush} property.
   * The brush used to paint colored rows.
   * @see #getBandBrush
   * @see #setBandBrush
   */
  public static final Property bandBrush = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code bandBrush} property.
   * The brush used to paint colored rows.
   * @see #bandBrush
   */
  public BBrush getBandBrush() { return (BBrush)get(bandBrush); }

  /**
   * Set the {@code bandBrush} property.
   * The brush used to paint colored rows.
   * @see #bandBrush
   */
  public void setBandBrush(BBrush v) { set(bandBrush, v, null); }

  //endregion Property "bandBrush"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BGridPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor with columnCount.
   */
  public BGridPane(int columnCount)
  {                                
    setColumnCount(columnCount);
  }

  /**
   * Default constructor.
   */
  public BGridPane()
  {
  }

  /**
   * Constructor with columnCount and widgets.
   * Each widget will be added as a child of this pane.
   */
  public BGridPane(int columnCount, BWidget[] childWidgets)
  {                                
    setColumnCount(columnCount);

    for (int i = 0; i < childWidgets.length; i++)
      add(null, childWidgets[i]);
  }

  /**
   * Constructor with widgets.
   * Each widget will be added as a child of this pane.
   */
  public BGridPane(BWidget[] childWidgets)
  {                                
    for (int i = 0; i < childWidgets.length; i++)
      add(null, childWidgets[i]);
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Compute the preferred size of the pane.
   */  
  public void computePreferredSize()
  {
    BWidget[] kids = getChildWidgets();
    double hgap = getColumnGap();
    double vgap = getRowGap();
    
    // row count is always computed
    int columns = getColumnCount();
    if (columns < 1) columns = 1;
    int rows = kids.length/columns;
    if (kids.length % columns > 0) rows++;
    
    cw = new double[columns];
    rh = new double[rows];
    double maxw = 0;
    double maxh = 0;

    int i = 0;
    rowloop: for(int r=0; r<rh.length; ++r)
    {
      for(int c=0; c<cw.length; ++c)
      {
        if (i >= kids.length) break rowloop;
        BWidget kid = kids[i++];
        if (kid.isVisible())
        {
          kid.computePreferredSize();
          cw[c] = Math.max(cw[c], kid.getPreferredWidth());
          rh[r] = Math.max(rh[r], kid.getPreferredHeight());
          maxw = Math.max(cw[c], maxw);
          maxh = Math.max(rh[r], maxh);
        }
      }
    }
    
    // uniform column widths
    if (getUniformColumnWidth())
      for(int c=0; c<cw.length; ++c) cw[c] = maxw;

    // uniform row heights
    if (getUniformRowHeight())
      for(int r=0; r<rh.length; ++r) rh[r] = maxh;
    
    double pw = 0, ph = 0;
    for(i=0; i<cw.length; ++i) { if (i>0) pw += hgap; pw += cw[i]; }
    for(i=0; i<rh.length; ++i) { if (i>0) ph += vgap; ph += rh[i]; }
    
    setPreferredSize(pw, ph);
  }
  
  /**
   * Layout the pane.
   */
  public void doLayout(BWidget[] kids)
  {
    computePreferredSize();
    double pw = getPreferredWidth();
    double ph = getPreferredHeight();
    double w = getWidth();
    double h = getHeight();
    double hgap = getColumnGap();
    double vgap = getRowGap();
    int hlayout = getColumnAlign().getOrdinal();
    int vlayout = getRowAlign().getOrdinal();
    int stretchRow = getStretchRow();
    int stretchCol = getStretchColumn();
    
    // x offset for halign
    xo = 0;
    if (w > pw && stretchCol < 0) 
    {
      int hpalign = getHalign().getOrdinal();
      if (hpalign == BHalign.CENTER) xo = (w-pw)/2; 
      if (hpalign == BHalign.RIGHT) xo = w-pw;
    }

    // y offset for valign
    yo = 0;
    if (h > ph && stretchRow < 0) 
    {
      int vpalign = getValign().getOrdinal();
      if (vpalign == BValign.CENTER) yo = (h-ph)/2; 
      if (vpalign == BValign.BOTTOM) yo = h-ph;
    }
    
    // insert horizontal stretch pixels
    if (stretchCol >= 0 && stretchCol < cw.length && w > pw)
      cw[stretchCol] += w - pw;
      
    // insert vertical stretch pixels
    if (stretchRow >= 0 && stretchRow < rh.length && h > ph)
      rh[stretchRow] += h - ph;
    
    // layout children
    int i = 0;
    double cx = 0, ry = 0;
    rowloop: for(int r=0; r<rh.length; ++r)
    {
      cx = 0;
      for(int c=0; c<cw.length; ++c)
      {
        if (i >= kids.length) break rowloop;
        BWidget kid = kids[i++];
        double colw = cw[c];
        double rowh = rh[r];
        
        double kph = kid.getPreferredHeight();
        double kx = xo + cx;
        double ky = yo + ry;
        double kw = colw;
        double kh = rowh;
                
        if (hlayout != BHalign.FILL)
        {
          kw = kid.getPreferredWidth();
          if (hlayout == BHalign.CENTER) kx = xo + cx + (colw-kw)/2; 
          if (hlayout == BHalign.RIGHT) kx = xo + cx + colw - kw;
        }

        if (vlayout != BValign.FILL)
        {
          kh = kid.getPreferredHeight();
          if (vlayout == BValign.CENTER) ky = yo + ry + (rowh-kh)/2; 
          if (vlayout == BValign.BOTTOM) ky = yo + ry + rowh - kh;
        }
                
        kid.setBounds((int)kx, (int)ky, (int)kw, (int)kh);
        cx += cw[c] + hgap;
      }
      ry += rh[r] + vgap;
    }    
  }

////////////////////////////////////////////////////////////////
// Paint
////////////////////////////////////////////////////////////////  

  public void paint(Graphics g)
  {
    double vgap = getRowGap();
    double w = getWidth();
    
    Theme.pane().paintBackground(g, this);
    
    if (getColorRows())
    {
      g.setBrush(Theme.gridPane().getBandBrush(this)); 
      double vgapHalf = vgap/2;
      double ry = yo;
      for(int r=0; r<rh.length; r++)
      {
        if (r % 2 == 0) g.fillRect(0, ry-vgapHalf, w, rh[r]+vgap);
        ry += rh[r] + vgap;
      }
    } 
    
    paintChildren(g);
  }
  
  public String getStyleSelector() {
    return "pane grid-pane";
  }

////////////////////////////////////////////////////////////////
// Trap
////////////////////////////////////////////////////////////////  

  public void changed(Property prop, Context context) 
  {
    relayout();
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/gridPane.png");  

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  double xo, yo;              // x and y offsets
  double[] cw = new double[0];   // column widths
  double[] rh = new double[0];   // row heights
  
}
