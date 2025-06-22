/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.treetable;

import javax.baja.gx.*;
import javax.baja.ui.table.*;
import javax.baja.ui.table.TableCellRenderer.Cell;

import com.tridium.ui.theme.*;

/**
 * TreeTableCellRenderer is the TableCellRenderer used by BTreeTable
 *
 * @author    Brian Frank
 * @creation  7 Jan 04
 * @version   $Revision: 20$ $Date: 6/8/11 4:22:09 PM EDT$
 * @since     Baja 1.0
 */
public class TreeTableCellRenderer
  extends TableCellRenderer
{

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the table as a BTreeTable.
   */
  public final BTreeTable getTreeTable()
  {
    return (BTreeTable)getTable();
  }

  /**
   * Get the TreeTableModel.
   */
  public final TreeTableModel getTreeTableModel()
  {
    return ((BTreeTable)getTable()).model;
  }

////////////////////////////////////////////////////////////////
// TableCellRenderer
////////////////////////////////////////////////////////////////

  public double getPreferredCellWidth(Cell cell)
  {
    double pw = super.getPreferredCellWidth(cell);
    if (cell.column == 0)
    {
      TreeTableTheme theme = Theme.treeTable();
      TreeTableModel model = getTreeTableModel();
      TreeTableNode node = model.rowToNode(cell.row);
      if (node.isGroup()) return 0; // don't use groups in width calcs
      int depth = node.getDepth();

      // add room for indent and potentially expander
      pw += theme.getIndent(depth);
      if (model.isDepthExpandable(depth))
        pw += theme.getExpanderWidth() + 5;
    }
    return pw;
  }

  public IGeom getClip(Cell cell)
  {
    TreeTableModel model = getTreeTableModel();
    TreeTableNode node = model.rowToNode(cell.row);

    if (node.isGroup())
    {
      if (cell.column == 0)
      {
        double gw = getTable().getWidth() - getTable().getVscrollBar().getWidth();
        clip.set(0, 0, gw-1, cell.height);
        return clip;
      }
      else
      {
        clip.set(0,0,0,0);
        return clip;
      }
    }

    return super.getClip(cell);
  }
  private RectGeom clip = new RectGeom();

  /**
   * Get the foreground used to paint the cell text.
   */
  public BBrush getForeground(Cell cell)
  {
    return Theme.treeTable().getTextBrush();
  }

  public BBrush getBackground(Cell cell)
  {
    if (getTreeTableModel().rowToNode(cell.row).isGroup())
      return Theme.treeTable().getGroupBackground();
    return super.getBackground(cell);
  }

  public void paintCell(Graphics g, Cell cell)
  {
    TreeTableTheme theme = Theme.treeTable();
    TreeTableModel model = getTreeTableModel();
    TreeTableNode node = model.rowToNode(cell.row);
    int depth = node.getDepth();
    boolean group = node.isGroup();

    // if not column 0
    if (cell.column > 0)
    {
      if (!group)
      {
        super.paintCell(g, cell);
        paintVerticalLine(g, cell);
      }
      return;
    }

    // if a group, paint the whole row
    if (group)
    {
      double gw = getTable().getWidth() - getTable().getVscrollBar().getWidth();
      cell.width = gw;
    }

    // paint background
    paintCellBackground(g, cell);
    BBrush fg = g.getBrush();

    // leave whitespace for depth indent
    double x = theme.getIndent(depth);

    // paint expander
    if (model.isDepthExpandable(depth))
    {
      if (node.hasChildren())
      {
        int state = node.isExpanded() ? TreeTableTheme.EXPANDED : TreeTableTheme.COLLAPSED;
        double y = (cell.height - theme.getExpanderHeight())/2;
        theme.paintExpander(g, x, y, cell.selected, state, group);
      }
      x += theme.getExpanderWidth() + 5;
    }

    BImage icon = model.getRowIcon(cell.row);
    if (icon != null)
    {
      double y = (cell.height - 16)/2;
      g.drawImage(icon, x, y);
      x += 18;
    }

    String text = getCellText(cell);
    if (text != null && text.length() > 0)
    {
      BFont font = getFont(cell);
      g.setBrush(fg);
      g.setFont(font);
      g.drawString(text, x, ((cell.height + font.getAscent() - font.getDescent()) / 2));
    }

    if (!group) paintVerticalLine(g, cell);
  }

  /**
   * The BTreeTable relies on its cell renderers to paint
   * the vertical grid (so that groups don't have a grid).
   */
  public void paintVerticalLine(Graphics g, Cell cell)
  {
    BTable table = getTable();
    if (!table.getVgridVisible()) return;
    BBrush c = getTable().getGridBrush();
    if (c.isNull()) c = Theme.table().getGridBrush();
    g.setBrush(c);
    g.strokeLine(cell.width-1, 0, cell.width-1, cell.height);
  }

}
