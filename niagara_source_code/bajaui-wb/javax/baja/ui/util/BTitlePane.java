/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.util;

import javax.baja.gx.BColor;
import javax.baja.gx.BFont;
import javax.baja.gx.BInsets;
import javax.baja.gx.BSize;
import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BString;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBorder;
import javax.baja.ui.BLabel;
import javax.baja.ui.BNullWidget;
import javax.baja.ui.BWidget;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.table.BTable;

import com.tridium.ui.theme.Theme;

/**
 * BTitlePane is a widget.
 *
 * @author    Andy Frank on 11 Sep 03
 * @version   $Revision: 14$$Date: 6/23/11 9:25:57 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Type name for rows if a table is being used.This appears at the
 right side of the pane in the form of [row_count] [type name]
 */
@NiagaraProperty(
  name = "typeName",
  type = "BString",
  defaultValue = "BString.make(UiLexicon.bajaui().getText(\"titlePane.objects\"))"
)
@NiagaraAction(
  name = "expansionChanged"
)
@NiagaraAction(
  name = "tableModified"
)
public class BTitlePane
  extends BEdgePane
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.util.BTitlePane(2498729693)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "typeName"

  /**
   * Slot for the {@code typeName} property.
   * Type name for rows if a table is being used.This appears at the
   * right side of the pane in the form of [row_count] [type name]
   * @see #getTypeName
   * @see #setTypeName
   */
  public static final Property typeName = newProperty(0, BString.make(UiLexicon.bajaui().getText("titlePane.objects")), null);

  /**
   * Get the {@code typeName} property.
   * Type name for rows if a table is being used.This appears at the
   * right side of the pane in the form of [row_count] [type name]
   * @see #typeName
   */
  public String getTypeName() { return getString(typeName); }

  /**
   * Set the {@code typeName} property.
   * Type name for rows if a table is being used.This appears at the
   * right side of the pane in the form of [row_count] [type name]
   * @see #typeName
   */
  public void setTypeName(String v) { setString(typeName, v, null); }

  //endregion Property "typeName"

  //region Action "expansionChanged"

  /**
   * Slot for the {@code expansionChanged} action.
   * @see #expansionChanged()
   */
  public static final Action expansionChanged = newAction(0, null);

  /**
   * Invoke the {@code expansionChanged} action.
   * @see #expansionChanged
   */
  public void expansionChanged() { invoke(expansionChanged, null, null); }

  //endregion Action "expansionChanged"

  //region Action "tableModified"

  /**
   * Slot for the {@code tableModified} action.
   * @see #tableModified()
   */
  public static final Action tableModified = newAction(0, null);

  /**
   * Invoke the {@code tableModified} action.
   * @see #tableModified
   */
  public void tableModified() { invoke(tableModified, null, null); }

  //endregion Action "tableModified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTitlePane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Create a new BTitlePane and set up the specified
   * table to be used for row counting.
   */
  public static BTitlePane makePane(String title, BTable table)
  {
    BTitlePane pane = new BTitlePane(title, table);
    pane.setTable(table);
    return pane;
  }

  /**
   * Create a new BTitlePane and set up the specified
   * table to be used for row counting.
   */
  public static BTitlePane makePane(String title, BTable table, String typeName)
  {
    BTitlePane pane = new BTitlePane(title, table);
    pane.setTypeName(typeName);
    pane.setTable(table);
    return pane;
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTitlePane(String title, BWidget content)
  {
    BLabel label = new BLabel(title);
    BEdgePane top = new BEdgePane();
    top.setLeft(label);

    BBorderPane topBorder = new BBorderPane(top, BBorder.none, withoutIcon);
    topBorder.setStyleClasses("title");
    topBorder.setFill(Theme.titlePane().getControlBackground(topBorder));

    label.setFont(Theme.titlePane().getTextFont(topBorder));
    label.setForeground(Theme.titlePane().getTextBrush(topBorder));
    label.setBackground(Theme.titlePane().getControlBackground());
    label.setPadding(BInsets.make(3, 3, 3, 0));

    BEdgePane edge = new BEdgePane();
    edge.setTop(topBorder);
    edge.setCenter(content);
    setCenter(edge);
  }

  /**
   * Framework use only.
   */
  public BTitlePane()
  {
    // framework use only
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  private boolean topIsEdge()
  {
    return getTopContent() instanceof BEdgePane;
  }

  private BEdgePane topAsEdge()
  {
    return (BEdgePane) getTopContent();
  }

  private boolean centerIsEdge()
  {
    return getCenter() instanceof BEdgePane;
  }

  private BEdgePane centerAsEdge()
  {
    return (BEdgePane) getCenter();
  }

  public BWidget getContent()
  {
    if (centerIsEdge())
    {
      return centerAsEdge().getCenter();
    }
    return null;
  }

  public BWidget getInfo()
  {
    if (topIsEdge())
    {
      return topAsEdge().getRight();
    }
    return null;
  }

  public void setInfo(BLabel newInfo)
  {
    if (topIsEdge())
    {
      topAsEdge().setRight(newInfo);
    }
  }

  public BWidget getLabel()
  {
    if (topIsEdge())
    {
      return topAsEdge().getLeft();
    }
    return null;
  }

  public BWidget getTopContent()
  {
    return getTopBorder().getContent();
  }

  public BBorderPane getTopBorder()
  {
    return (BBorderPane) ((BEdgePane) getCenter()).getTop();
  }

  public BTable getTable()
  {
    if (centerIsEdge())
    {
      return (BTable) centerAsEdge().getCenter();
    }
    return null;
  }

  /**
   * Get the title text.
   */
  public String getTitle()
  {
    if (getLabel() instanceof BLabel)
    {
      return ((BLabel) getLabel()).getText();
    }
    return "";
  }

  /**
   * Set the title text.
   */
  public void setTitle(String title)
  {
    if (getLabel() instanceof BLabel)
    {
      ((BLabel) getLabel()).setText(title);
    }
  }

  /**
   * Explicitly set the count of the info label.
   */
  public void setCount(int count)
  {
    if (topIsEdge())
    {
      BEdgePane edgePane = topAsEdge();
      if (getInfo() == null || getInfo() instanceof BNullWidget)
      {
        setInfo(new BLabel(""));
        BLabel info = (BLabel) getInfo();
        info.setFont(BFont.make(Theme.titlePane().getTextFont(getTopBorder()), 13.5));
        info.setForeground(Theme.titlePane().getTextBrush(getTopBorder()));
        edgePane.setRight(getInfo());
      }
      if (getInfo() instanceof BLabel)
      {
        ((BLabel) getInfo()).setText(count + " " + getTypeName());
      }
    }
  }

  /**
   * Set this table to be used for the row counter
   * in the title bar.
   */
  public void setTable(BTable table)
  {
    if (centerIsEdge())
    {
      centerAsEdge().setCenter(table);
      if (get("tableLink") != null) remove("tableLink");
      linkTo("tableLink", table, BTable.tableModified, tableModified);
      doTableModified();
    }
  }

  /**
   * Enable/disable the pane to collapse its content widget.
   */
  public void setExpandable(boolean expandable)
  {
    if (topIsEdge())
    {
      BEdgePane top = topAsEdge();
      if (expandable)
      {
        top.setLeft(new BNullWidget());
        BGridPane grid = new BGridPane(2);
        grid.add(null, new BAuxWidget(new AuxSupport()));
        grid.add(null, getLabel());
        top.setLeft(grid);
        ((BBorderPane)top.getParent()).setPadding(withIcon);
      }
      else
      {
        BWidget p = getLabel().getParentWidget();
        if (p != null) p.remove(getLabel().getPropertyInParent());
        top.setLeft(getLabel());
        ((BBorderPane)top.getParent()).setPadding(withoutIcon);
      }
    }
  }

  /**
   * Get the expand state.
   */
  public boolean getExpanded()
  {
    return getContent().isVisible();
  }

  /**
   * Set the expand state.
   */
  public void setExpanded(boolean b)
  {
    getContent().setVisible(b);
    relayout();
    expansionChanged();
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  @SuppressWarnings("WeakerAccess")
  public void doTableModified()
  {
    if (getContent() instanceof BTable)
    {
      int count = 0;
      try
      {
        count = getTable().getModel().getRowCount();
      }
      catch(Exception ignore) { }
      setCount(count);
      relayout();
    }
  }

  @SuppressWarnings("unused")
  public void doExpansionChanged()
  {
  }

////////////////////////////////////////////////////////////////
// AuxWidget
////////////////////////////////////////////////////////////////

  class AuxSupport extends BAuxWidget.Support
  {
    final BSize size = BSize.make(10,16);

    @Override
    public BSize getPreferredSize() { return size; }

    @Override
    public void paint(Graphics g, double w, double h)
    {
      g.setBrush(BColor.white);
      double x = 2;
      double y = 5;

      if (getContent().isVisible())
      {
        g.strokeLine(x, y+2, x+2, y);
        g.strokeLine(x+2, y, x+4, y+2);
        g.strokeLine(x+1, y+2, x+2, y+1);
        g.strokeLine(x+2, y+1, x+3, y+2);

        g.strokeLine(x, y+6, x+2, y+4);
        g.strokeLine(x+2, y+4, x+4, y+6);
        g.strokeLine(x+1, y+6, x+2, y+5);
        g.strokeLine(x+2, y+5, x+3, y+6);
      }
      else
      {
        g.strokeLine(x, y, x+2, y+2);
        g.strokeLine(x+2, y+2, x+4, y);
        g.strokeLine(x+1, y, x+2, y+1);
        g.strokeLine(x+2, y+1, x+3, y);

        g.strokeLine(x, y+4, x+2, y+6);
        g.strokeLine(x+2, y+6, x+4, y+4);
        g.strokeLine(x+1, y+4, x+2, y+5);
        g.strokeLine(x+2, y+5, x+3, y+4);
      }
    }

    @Override
    public void mousePressed(BMouseEvent event)
    {
      getContent().setVisible(!getContent().getVisible());
      relayout();
      expansionChanged();
    }
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  static final BInsets withIcon = BInsets.make(1,4,1,2);
  static final BInsets withoutIcon = BInsets.make(2,4,2,4);

////////////////////////////////////////////////////////////////
// Main
////////////////////////////////////////////////////////////////

//  public static void main(String[] args)
//  {
//    // To launch this method run, uncomment build and run...
//    // nre bajaui:javax.baja.ui.util.BTitlePane
//
//    TableModel model = new TableModel()
//    {
//      @Override
//      public int getRowCount() { return 10; }
//
//      @Override
//      public int getColumnCount() { return 4; }
//
//      @Override
//      public String getColumnName(int col) { return "Column: " + col; }
//
//      @Override
//      public Object getValueAt(int row, int col) { return "row: " + row + ", " + col; }
//    };
//
//    BTable table = new BTable(model);
//    BTitlePane titlePane = new BTitlePane("This is a title", table);
//
//    BFrame frame = new BFrame();
//
//    frame.setContent(titlePane);
//    frame.setScreenBounds(200, 10, 700, 500);
//    frame.open();
//  }
}
