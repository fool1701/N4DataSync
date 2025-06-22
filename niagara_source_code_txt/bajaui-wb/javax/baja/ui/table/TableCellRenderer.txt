/*
 * Copyright 2001, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.table;

import com.tridium.ui.theme.Theme;

import javax.baja.gx.BBrush;
import javax.baja.gx.BFont;
import javax.baja.gx.BImage;
import javax.baja.gx.Graphics;
import javax.baja.gx.IGeom;
import javax.baja.gx.RectGeom;
import javax.baja.ui.enums.BHalign;

/**
 * TableCellRenderer is used to render the individual 
 * cells of a BTable grid.
 *
 * @author    Brian Frank
 * @creation  3 Aug 01
 * @version   $Revision: 29$ $Date: 6/29/11 12:15:44 PM EDT$
 * @since     Baja 1.0
 */
public class TableCellRenderer
  extends BTable.TableSupport
{
  
  /**
   * Get the foreground used to paint the cell text.
   */
  public BBrush getForeground(Cell cell)
  {
    return Theme.table().getTextBrush(getTable());
  }

  /**
   * Get the background used to paint the cell.  Return 
   * null if the standard background should be used.
   */
  public BBrush getBackground(Cell cell)
  {
    //NCCB-7590 - Check for null table prior to row coloring
    return (getTable() != null && getColorRows()) ? (cell.row % 2 == 0 ? getEvenRowColor() : getOddRowColor()) : null;
  }

  /**
   * Specify whether alternate rows should be colored
   * @return
   */
  public boolean getColorRows()
  {
    return getTable().getColorRows();
  }

  /**
   * Specify the brush used to color even rows in the table
   * @return
   */
  public BBrush getEvenRowColor()
  {
    BBrush brush = getTable().getEvenRowColor();
    return brush.equals(BBrush.NULL) ? null : brush;
  }

  /**
   * Specify the brush used to color odd rows in the table.
   * Note: If unspecified, odd rows default to the standard background brush
   * @return
   */
  public BBrush getOddRowColor()
  {
    BBrush brush = getTable().getOddRowColor();
    return brush.equals(BBrush.NULL) ? Theme.table().getBandBrush(getTable()) : brush;
  }

  /**
   * Get the font used to paint the cell text.
   */
  public BFont getFont(Cell cell)
  {                    
    return Theme.table().getCellFont(getTable());
  }

  /**
   * Get the selection foreground used to paint the cell text.
   */
  public BBrush getSelectionForeground(Cell cell)
  {
    return Theme.table().getSelectionForeground(getTable());
  }

  /**
   * Get the selection background used to paint the cell.
   */
  public BBrush getSelectionBackground(Cell cell)
  {
    return Theme.table().getSelectionBackground(getTable());
  }

  /**
   * Get the fixed height of the table rows.  A given BTable
   * always has the same height across all of its rows based
   * on the result of this method.
   */
  public double getCellHeight()
  {
    return Math.max(Theme.table().getCellHeight(), 16);
  }

  /**
   * Get the preferred width of the specified cell.
   */
  public double getPreferredCellWidth(Cell cell)
  {
    String s = getCellText(cell);
    double w = getFont(cell).width(s) + 12;
    if (cell.column == 0 && getTable().getModel().getRowIcon(cell.row) != null)
      w += 20;
    return w;
  }
  
  /**
   * Get the clipping path to apply to this cell. This path
   * should be relative to (0,0).
   */
  public IGeom getClip(Cell cell)
  {
    clip.set(0, 0, cell.width, cell.height);
    return clip;
  }
  private RectGeom clip = new RectGeom();
  
  /**
   * Get the text to display for the specified item.  Default
   * is to return <code>String.valueOf(cell.value)</code>.
   */
  public String getCellText(Cell cell)
  {
    return String.valueOf(cell.value);
  }
  
  /**
   * Paint the cell to the specified graphics context.  The
   * graphics context will be translated so that the origin 
   * of the cell is 0,0.  This method calls paintCellBackground()
   * to fill the background and set the foreground color.
   * Then the icon and text are painted.
   */
  public void paintCell(Graphics g, Cell cell)
  {
    TableModel model = getTable().getModel();
    
    paintCellBackground(g, cell);
    
    double x = 2;
    double y = (cell.height-16) / 2;
    if (cell.column == 0)
    {
      BImage icon = model.getRowIcon(cell.row);
      if (icon != null)
      {
        g.drawImage(icon, x, y);
        x += 20;
      }
    }

    String s = getCellText(cell);
    BFont font = getFont(cell);
    
    switch(model.getColumnAlignment(cell.column).getOrdinal())
    {
      case BHalign.RIGHT:
        x = cell.width - 2 - font.width(s);
        break;
      case BHalign.CENTER:
        x = (cell.width - font.width(s))/2;
        break;
    }
    
    g.setFont(font);
    if (cell.selected)
      g.setBrush(getSelectionForeground(cell));
    else
      g.setBrush(getForeground(cell));
    g.drawString( s, x, ((cell.height + font.getAscent() - font.getDescent()) / 2) );
  }
  
  /**
   * This is the first method of paintCell() which paints
   * the background and then sets the graphics context with
   * the current foreground color (depending on selection
   * state).
   */
  protected void paintCellBackground(Graphics g, Cell cell)
  {
    if (cell.selected)
    {
      BBrush bg = getSelectionBackground(cell);
      if (bg != null && !bg.isNull())
      {
        g.setBrush(bg);
        g.fillRect(0, 0, cell.width, cell.height);
      }
      g.setBrush(getSelectionForeground(cell));
    }
    else
    {
      BBrush bg = getBackground(cell);
      if (bg != null && !bg.isNull())
      {
        g.setBrush(bg);
        g.fillRect(0, 0, cell.width, cell.height);
      }
      g.setBrush(getForeground(cell));
    }                        
    
    g.setFont(getFont(cell));
  }
  
////////////////////////////////////////////////////////////////
// Cell
////////////////////////////////////////////////////////////////  

  /**
   * This structure is used to pass the cell information 
   * to the the paintCell() method.
   */
  public static class Cell
  {
    /** Zero based index table row of the cell. */
    public int row;
    
    /** Zero based index table column of the cell. */
    public int column;
    
    /** Object value of the cell as returned from TableModel. */
    public Object value;
        
    /** Width in pixels of the cell. */
    public double width;
    
    /** Height in pixels of the cell. */
    public double height;
    
    /** Is the cell currently selected. */
    public boolean selected;
  }

}