/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.pane;

import javax.baja.gx.BInsets;
import javax.baja.gx.Graphics;
import javax.baja.gx.Point;
import javax.baja.gx.RectGeom;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.BNullWidget;
import javax.baja.ui.BWidget;
import javax.baja.ui.enums.BAlign;
import javax.baja.ui.event.BFocusEvent;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.event.BWidgetEvent;

import com.tridium.ui.theme.TabbedPaneTheme;
import com.tridium.ui.theme.Theme;

/**
 * BTabbedPane is a container which displays a set of BLabelPane
 * children one at a time using a set of "tabs" to select the 
 * currently displayed child.  The BLabelPane's label is used
 * to label each tab, and the content of the current selection
 * fills the rest of the pane.
 *
 * @author    Brian Frank
 * @creation  5 Dec 00
 * @version   $Revision: 57$ $Date: 6/20/11 9:34:25 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 This defines whether the tabs appear on the top, bottom,
 right, or left.  Using BAlignment.center will result
 in an error.
 */
@NiagaraProperty(
  name = "tabPlacement",
  type = "BAlign",
  defaultValue = "BAlign.top"
)
/*
 Display or hide the tab when only one tab is visible on the pane.
 */
@NiagaraProperty(
  name = "showSingleTab",
  type = "boolean",
  defaultValue = "true"
)
/*
 The tabControl is displayed to the right or bottom of all tabs.
 */
@NiagaraProperty(
  name = "tabControl",
  type = "BWidget",
  defaultValue = "new BNullWidget()",
  flags = Flags.TRANSIENT | Flags.HIDDEN
)
/*
 Specifies whether to paint the full beveled border on all
 four sides, or only on the side where the tabs are painted.
 */
@NiagaraProperty(
  name = "paintFullBorder",
  type = "boolean",
  defaultValue = "true"
)
/*
 Fired when the selected tab is modified.
 */
@NiagaraTopic(
  name = "selectionModified",
  eventType = "BWidgetEvent"
)
public class BTabbedPane
  extends BLabelPaneContainer
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BTabbedPane(1021638027)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "tabPlacement"

  /**
   * Slot for the {@code tabPlacement} property.
   * This defines whether the tabs appear on the top, bottom,
   * right, or left.  Using BAlignment.center will result
   * in an error.
   * @see #getTabPlacement
   * @see #setTabPlacement
   */
  public static final Property tabPlacement = newProperty(0, BAlign.top, null);

  /**
   * Get the {@code tabPlacement} property.
   * This defines whether the tabs appear on the top, bottom,
   * right, or left.  Using BAlignment.center will result
   * in an error.
   * @see #tabPlacement
   */
  public BAlign getTabPlacement() { return (BAlign)get(tabPlacement); }

  /**
   * Set the {@code tabPlacement} property.
   * This defines whether the tabs appear on the top, bottom,
   * right, or left.  Using BAlignment.center will result
   * in an error.
   * @see #tabPlacement
   */
  public void setTabPlacement(BAlign v) { set(tabPlacement, v, null); }

  //endregion Property "tabPlacement"

  //region Property "showSingleTab"

  /**
   * Slot for the {@code showSingleTab} property.
   * Display or hide the tab when only one tab is visible on the pane.
   * @see #getShowSingleTab
   * @see #setShowSingleTab
   */
  public static final Property showSingleTab = newProperty(0, true, null);

  /**
   * Get the {@code showSingleTab} property.
   * Display or hide the tab when only one tab is visible on the pane.
   * @see #showSingleTab
   */
  public boolean getShowSingleTab() { return getBoolean(showSingleTab); }

  /**
   * Set the {@code showSingleTab} property.
   * Display or hide the tab when only one tab is visible on the pane.
   * @see #showSingleTab
   */
  public void setShowSingleTab(boolean v) { setBoolean(showSingleTab, v, null); }

  //endregion Property "showSingleTab"

  //region Property "tabControl"

  /**
   * Slot for the {@code tabControl} property.
   * The tabControl is displayed to the right or bottom of all tabs.
   * @see #getTabControl
   * @see #setTabControl
   */
  public static final Property tabControl = newProperty(Flags.TRANSIENT | Flags.HIDDEN, new BNullWidget(), null);

  /**
   * Get the {@code tabControl} property.
   * The tabControl is displayed to the right or bottom of all tabs.
   * @see #tabControl
   */
  public BWidget getTabControl() { return (BWidget)get(tabControl); }

  /**
   * Set the {@code tabControl} property.
   * The tabControl is displayed to the right or bottom of all tabs.
   * @see #tabControl
   */
  public void setTabControl(BWidget v) { set(tabControl, v, null); }

  //endregion Property "tabControl"

  //region Property "paintFullBorder"

  /**
   * Slot for the {@code paintFullBorder} property.
   * Specifies whether to paint the full beveled border on all
   * four sides, or only on the side where the tabs are painted.
   * @see #getPaintFullBorder
   * @see #setPaintFullBorder
   */
  public static final Property paintFullBorder = newProperty(0, true, null);

  /**
   * Get the {@code paintFullBorder} property.
   * Specifies whether to paint the full beveled border on all
   * four sides, or only on the side where the tabs are painted.
   * @see #paintFullBorder
   */
  public boolean getPaintFullBorder() { return getBoolean(paintFullBorder); }

  /**
   * Set the {@code paintFullBorder} property.
   * Specifies whether to paint the full beveled border on all
   * four sides, or only on the side where the tabs are painted.
   * @see #paintFullBorder
   */
  public void setPaintFullBorder(boolean v) { setBoolean(paintFullBorder, v, null); }

  //endregion Property "paintFullBorder"

  //region Topic "selectionModified"

  /**
   * Slot for the {@code selectionModified} topic.
   * Fired when the selected tab is modified.
   * @see #fireSelectionModified
   */
  public static final Topic selectionModified = newTopic(0, null);

  /**
   * Fire an event for the {@code selectionModified} topic.
   * Fired when the selected tab is modified.
   * @see #selectionModified
   */
  public void fireSelectionModified(BWidgetEvent event) { fire(selectionModified, event, null); }

  //endregion Topic "selectionModified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTabbedPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with tab placement.
   */
  public BTabbedPane(BAlign tabPlacement)
  {
    setTabPlacement(tabPlacement);
  }

  /**
   * No argument constructor.
   */
  public BTabbedPane()
  {
  }

////////////////////////////////////////////////////////////////
// Tab Access
////////////////////////////////////////////////////////////////  


  /**
   * Return visible label panes
   */
  private BLabelPane[] getLabelPanes()
  {
    BWidget[] widgets = getChildWidgets();
    Array<BLabelPane> visibleWidgets = new Array<>(BLabelPane.class);
    for(int i=0; i<widgets.length; i++)
      if(widgets[i] instanceof BLabelPane && widgets[i].getVisible())
        visibleWidgets.add((BLabelPane)widgets[i]);
    return visibleWidgets.trim();
  }

  /**
   * Get the selected label pane, or return null if no panes.
   */
  public BLabelPane getSelectedLabelPane()
  {
    if (selection == null) return null;
    if (!selection.isVisible())
    {
      BLabelPane[] kids = this.getLabelPanes();
      if(kids.length>0) return kids[0];
      return null;
    }
    return selection;
  }

  /**
   * Get the selected content widget, or null if no panes.
   */
  public BWidget getSelectedPane()
  {
    if (selection == null) return null;
    return selection.getContent();
  }
  
  /**
   * Select the specified label pane.
   */
  public void selectLabelPane(BLabelPane pane)
  {
    setSelected(pane);
  }

  /**
   * Selects the tab with the specified content widget.
   */
  public void selectPane(BWidget content)
  {
    setSelected((BLabelPane)content.getParent());
  }
  
  /**
   * Install the specified TabSupport.
   */
  public void setTabSupport(TabSupport support)
  {
    this.support = support;
  }

  /**
   * Get the currently installed TabSupport.
   */
  public TabSupport getTabSupport()
  {
    return support;
  }

  /**
   * Select the specified label pane.
   */
  public void setEnabledLabelPane(BLabelPane pane, boolean enabled)
  {
    if (pane == null) return;
    pane.setEnabled(enabled);
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////
  
  /**
   * Compute the tabbed pane's preferred size.
   */
  public void computePreferredSize()
  {
    BLabelPane[] kids = getLabelPanes();
    if (kids.length == 0) { setPreferredSize(200,200); return; }

    // If only one tab and !showSingleTab
    if (kids.length == 1 && !getShowSingleTab())
    {
      BWidget w = kids[0].getContent();
      w.computePreferredSize();
      setPreferredSize(w.getPreferredWidth(), w.getPreferredHeight());
      return;
    }

    double w  = 0;
    double h  = 0;
    
    for(int i=0; i<kids.length; i++)
    {
      BWidget c = kids[i].getContent();
      c.computePreferredSize();
      w = Math.max(w, c.getPreferredWidth());
      h = Math.max(h, c.getPreferredHeight());
    }

    BAlign placement = tabPlacement();    
    BInsets ti = Theme.tabbedPane().getTabInsets(placement);
    BInsets ci = Theme.tabbedPane().getContentInsets(placement, getPaintFullBorder());

    double lw = 0;
    double lh = 0;
    if(kids.length>0)
    {
      BLabel label = kids[0].getLabel();
      label.computePreferredSize();
      lw = label.getPreferredWidth();
      lh = label.getPreferredHeight();
    }

    if (placement == BAlign.top || placement == BAlign.bottom)
      h += ti.top + lh + ti.bottom;
    else
      w += ti.left + lw + ti.right;

    w += ci.left + ci.right;
    h += ci.top + ci.bottom;

    setPreferredSize(w,h);
  }

  /**
   * Layout the tabbed pane's children.
   */
  public void doLayout(BWidget[] widgets)
  {

    // Filter out invisible label panes
    Array<BWidget> visibleWidgets = new Array<>(BWidget.class);
    for(int i=0; i<widgets.length; i++)
      if(widgets[i].getVisible() || !widgets[i].getType().is(BLabelPane.TYPE))
        visibleWidgets.add(widgets[i]);
    BWidget[] kids = visibleWidgets.trim();

    BWidget control = getTabControl();

    // check for null selection and pick
    // the first tab, or bomb out
    if (selection == null) 
    {
      if (kids.length == 1) return;
      else setSelection((BLabelPane)kids[1]);
    }
    
    // If only one tab and !showSingleTab
    if (kids.length == 2 && !getShowSingleTab())
    {
      kids[1].setBounds(0,0,getWidth(),getHeight());
      BWidget w = ((BLabelPane)kids[1]).getLabel();
      w.setVisible(false);
      w.setBounds(0,0,0,0);
      w = ((BLabelPane)kids[1]).getContent();
      w.setVisible(true);
      w.setBounds(0,0,getWidth(),getHeight());
      control.setBounds(0,0,0,0);
      control.setVisible(false);
      return;
    }
    
    BAlign placement = tabPlacement();    
    BInsets ti = Theme.tabbedPane().getTabInsets(placement);
    double w = getWidth(), h = getHeight();  // my dimensions
    double x = 0, y = 0;                // next tab placement
    fixedHeight = fixedWidth = 0;    // reset fixed dimensions
    
    double cw = 0;
    double ch = 0;

    if (control instanceof BNullWidget)
    { 
      control.setBounds(0,0,0,0);
      control.setVisible(false);
    }
    else 
    {
      control.computePreferredSize();
      cw = control.getPreferredWidth();
      ch = control.getPreferredHeight();
      double cx = 0;
      double cy = 0;

      switch(placement.getOrdinal())
      {
        case BAlign.TOP:    cx = w - cw; break;
        case BAlign.BOTTOM: cx = w - cw; cy = h - ch; break;
        case BAlign.LEFT:   cy = h - ch; break;
        case BAlign.RIGHT:  cx = w - cw; cy = h - ch; break;
        default: throw new IllegalStateException();
      }    
      
      control.setBounds(cx, cy, cw, ch);
      control.setVisible(true);
      
      if (placement == BAlign.top || placement == BAlign.bottom)
        fixedHeight = (int)ch;
      else
        fixedWidth = (int)cw;
    }
    
    // figure the bounds for each tab and make 
    // each content widget invisible; for horizontal
    // we set "y" to row number not actual coordinate
    // and likewise for "x" on vertical
    for(int i=1; i<kids.length; i++)
    {
      BLabelPane kid = (BLabelPane)kids[i];
      kid.setBounds(0,0,getWidth(),getHeight());
      
      BWidget content = kid.getContent();
      content.setBounds(0,0,0,0);
      content.setVisible(selection.getContent().equals(content));
      BLabel label = kid.getLabel();
      label.setVisible(true);
      
      // compute the label's preferred size and use that 
      // to figure out what the size of the tab should be
      label.computePreferredSize();
      double lw = label.getPreferredWidth();
      double lh = label.getPreferredHeight();
      double tw = lw + ti.left + ti.right;
      double th = lh + ti.top + ti.bottom;
      
      // now place it next in the row or column 
      // and determine if we need to wrap
      if (placement == BAlign.top || placement == BAlign.bottom)
      {
        if (x + tw > w-cw) { y++; x = 0; }
        label.setBounds(x+ti.left, y, lw, lh);
        x += tw; 
        fixedHeight = (int)Math.max(fixedHeight, th);
      }
      else
      {
        if (y + th > h-ch) { x++; y = 0; }
        label.setBounds(x, y+ti.top, lw, lh);
        y += th; 
        fixedWidth = (int)Math.max(fixedWidth, tw);
      }
    }
    
    // compute the aggregate area consumed by all tabs
    int rowCount = (int)y+1, colCount = (int)x+1;
    double tabAreaHeight = rowCount*fixedHeight;
    double tabAreaWidth = colCount*fixedWidth;
    BInsets ci = Theme.tabbedPane().getContentInsets(placement, getPaintFullBorder());
    switch(placement.getOrdinal())
    {
      case BAlign.TOP:
      case BAlign.BOTTOM:
        layoutRows(kids, rowCount);
        break;
      case BAlign.LEFT:
      case BAlign.RIGHT:
        layoutColumns(kids, colCount);
        break;
    }

    for(int i=1; i<kids.length; i++)
    {
      BLabelPane kid = (BLabelPane)kids[i];
      BWidget content = kid.getContent();

      // now we know how many rows or columns we need 
      // and we can compute their actual bounds and then
      // give the content pane the remaining room
      switch(placement.getOrdinal())
      {
        case BAlign.TOP:
          content.setBounds(ci.left, tabAreaHeight+ci.top, w-ci.left-ci.right, h-tabAreaHeight-ci.top-ci.bottom);
          break;
        case BAlign.BOTTOM:
          content.setBounds(ci.left, ci.top, w-ci.left-ci.right, h-tabAreaHeight-ci.top-ci.bottom);
          break;
        case BAlign.LEFT:
          content.setBounds(ci.left+tabAreaWidth, ci.top, w-ci.left-ci.right-tabAreaWidth, h-ci.top-ci.bottom);
          break;
        case BAlign.RIGHT:
          content.setBounds(ci.left, ci.top, w-ci.left-ci.right-tabAreaWidth, h-ci.top-ci.bottom);
          break;
        default:
          throw new IllegalStateException();
      }    
      
      // make the label pane "fit" the content
      kid.setBounds(0, 0, content.getWidth(), content.getHeight());
    }
    
    //selection.getContent().setVisible(true);
  }
  
  /**
   * Now we have an inital layout of the labels, but
   * the "y" coordinate are set to row position.
   */
  private void layoutRows(BWidget[] kids, int rowCount)
  {
    BInsets ti = Theme.tabbedPane().getTabInsets(tabPlacement());
    
    // compute the total width of every row
    int[] tabsInRow = new int[rowCount];
    int[] rowTotal = new int[rowCount];
    int[] rowAdder = new int[rowCount];
// DOGX - what the heck?!?
    for(int i=1; i<kids.length; ++i)
    {
      BLabel label = ((BLabelPane)kids[i]).getLabel();
      tabsInRow[(int)label.getY()] += 1;
      rowTotal[(int)label.getY()] += label.getWidth() + ti.left + ti.right;
    }
    
    // now compute the amount we need to add to 
    // each tab in each row (except the last one) 
    // to make it fit the exact width of the pane
    for(int i=0; i<rowCount-1; ++i)
    {
      int div = tabsInRow[i];
      if (div == 0)
        rowAdder[i] = 0;
      else
        rowAdder[i] = ((int)getWidth() - rowTotal[i]) / div;
    }
    
    // walk through now every label and update
    // its x and y coordinate to make it pretty!
    int curRow = 0;
    int x = 0;
    int y = rowY(rowCount,curRow);   
    for(int i=1; i<kids.length; ++i)
    {
      BLabel label = ((BLabelPane)kids[i]).getLabel();      
      if (curRow != label.getY()) 
      { 
        // give the last guy on the next row enough
        // more width to make up for rounding errors
        if (i > 1) 
        {
          BLabel last = ((BLabelPane)kids[i-1]).getLabel();
          int toAdd = ((int)getWidth() - rowTotal[curRow]) % tabsInRow[curRow];
          last.setSize(last.getWidth()+toAdd, last.getHeight());
        }
        
        // move to next row          
        curRow++;
        x = 0; 
        y = rowY(rowCount,curRow);
      }
      
      int lx = x + (int)ti.left;
      int ly = y + (int)ti.top;
      int lw = (int)label.getWidth() + rowAdder[curRow];
      int lh = fixedHeight - (int)ti.top - (int)ti.bottom;
      label.setBounds(lx, ly, lw, lh);
      x = lx + lw + (int)ti.right;
    }
  }
  
  /**
   * Get the specified row's "y" coordinate given
   * the current alignment.
   */
  private int rowY(int rowCount, int row)
  {
    if (tabPlacement() == BAlign.top)
      return (rowCount - row - 1) * fixedHeight;
    else
      return (int)getHeight() - (rowCount - row) * fixedHeight;
  }

  /**
   * Now we have an inital layout of the labels, but
   * the "x" coordinate are set to column position.
   */
  private void layoutColumns(BWidget[] kids, int colCount)
  {
    BInsets ti = Theme.tabbedPane().getTabInsets(tabPlacement());
    
    // compute the total height of every row
    int[] tabsInCol = new int[colCount];
    int[] colTotal = new int[colCount];
    int[] colAdder = new int[colCount];
    for(int i=1; i<kids.length; ++i)
    {
      BLabel label = ((BLabelPane)kids[i]).getLabel();
      tabsInCol[(int)label.getX()] += 1;
      colTotal[(int)label.getX()] += (int)label.getHeight() + ti.top + ti.bottom;
    }
    
    // now compute the amount we need to add to 
    // each tab in each column (except the last one) 
    // to make it fit the exact height of the pane
    for(int i=0; i<colCount-1; ++i)
    {
      if (tabsInCol[i] == 0) colAdder[i] = 0;
      else colAdder[i] = ((int)getHeight() - colTotal[i]) / tabsInCol[i];
    }
    
    // walk through now every label and update
    // its x and y coordinate to make it pretty!
    int curCol = 0;
    int x = colX(colCount,curCol);
    int y = 0;   
    for(int i=1; i<kids.length; ++i)
    {
      BLabel label = ((BLabelPane)kids[i]).getLabel();      
      if (curCol != label.getX()) 
      { 
        // give the last guy on the next col enough
        // more height to make up for rounding errors
        if (i > 1) 
        {
          BLabel last = ((BLabelPane)kids[i-1]).getLabel();
          int toAdd = ((int)getHeight() - colTotal[curCol]) % tabsInCol[curCol];
          last.setSize((int)last.getWidth(), (int)last.getHeight()+toAdd);
        }
        
        // move to next col
        curCol++;
        x = colX(colCount,curCol);
        y = 0;
      }
      
      int lx = x + (int)ti.left;
      int ly = y + (int)ti.top;
      int lw = fixedWidth - (int)ti.left - (int)ti.right;
      int lh = (int)label.getHeight() + colAdder[curCol];
      label.setBounds(lx, ly, lw, lh);
      y = ly + lh + (int)ti.top;
    }
  }
  
  /**
   * Get the specified column's "x" coordinate given
   * the current alignment.
   */
  private int colX(int colCount, int col)
  {
    if (tabPlacement() == BAlign.left)
      return (colCount - col - 1) * fixedWidth;
    else
      return (int)getWidth() - (colCount - col) * fixedWidth;
  }

////////////////////////////////////////////////////////////////
// Paint
////////////////////////////////////////////////////////////////  

  /**
   * When the currently selected pane is hidden, we should paint the next
   * visible pane instead. This will find the next visible pane and set it as
   * the currently selected pane.
   */
  private void resetSelection() {
    int selectedIndex = -1;
    BWidget[] childWidgets = getChildWidgets();
    
    for (int i = 0; i < childWidgets.length; i++) {
      if (selection == childWidgets[i]) {
        selectedIndex = i;
        break;
      }
    }
    
    if (selectedIndex >= 0) {
      int desiredIndex = selectedIndex + 1;
      //loop through until we find the next visible pane
      for (int i = 0; i < childWidgets.length; i++) {
        BWidget kid = childWidgets[(desiredIndex + i) % childWidgets.length];
        if (kid instanceof BLabelPane && kid.isVisible()) {
          //found a visible pane
          setSelected((BLabelPane) kid);
          break;
        }
      }
    }
  }
  
  /**
   * Paint the tabbed pane.
   */
  public void paint(Graphics g)
  {
    if (selection == null) return;
    
    TabbedPaneTheme theme = Theme.tabbedPane();
    BAlign tabPlacement = tabPlacement();
    boolean fullBorder = getPaintFullBorder();
    
    theme.paintContentBackground(g, this, tabPlacement,
      selection.getContent(), fullBorder);
    
    BLabelPane[] kids = getLabelPanes();
    boolean paintTabBackground = getShowSingleTab() || kids.length > 1;
    for(int i=kids.length-1; i>=0; --i)
    {
      if (!(kids[i] instanceof BLabelPane)) continue;
      BLabelPane kid = kids[i];
      BLabel label = kid.getLabel();
      boolean selected = selection == kid;

      if(paintTabBackground)
      {
        theme.paintTabBackground(g, this, tabPlacement,
          label, selected, selection.getContent(), fullBorder);
      }
      
      paintChild(g, label);
    }
    
    paintChild(g, getTabControl());
    
    if (!selection.isVisible()) 
    {
      //our currently selected pane was set to invisible - see 23207
      resetSelection();
    }

    if (getLabelPanes().length > 0) 
    {
      paintChild(g, selection.getContent());
    }
    else 
    {
      //we have NO visible label panes - so blank out the tabbed pane body
      paintChild(g, new BLabelPane());
    }
  }
  
  public String getStyleSelector() { return "pane tabbed-pane"; }

////////////////////////////////////////////////////////////////
// BWidget Overrides
////////////////////////////////////////////////////////////////

  public BWidget childAt(Point pt)
  {
    if (selection != null)
    {
      BWidget c = selection.getContent();
      if (c.contains(pt.x - c.getX(),pt.y - c.getY())) return c;
    }
    
    BWidget c = getTabControl();
    if (c.contains(pt.x - c.getX(),pt.y - c.getY())) return c;
    
    return null;
  }
  
  public void added(Property prop, Context ctx)
  {
    super.added(prop, ctx);
    BObject value = get(prop);
    if (value instanceof BLabelPane)
    {
      BLabelPane pane = (BLabelPane) value;
      if (selection == null && pane.getEnabled() && pane.isVisible())
      {
        setSelection(pane);
      }
      else
      {
        updateTabFromTheme(pane, pane == selection);
      }
    }
  }
  
  
////////////////////////////////////////////////////////////////
// Tab Management
////////////////////////////////////////////////////////////////

  private void setSelected(BLabelPane tab)
  {
    if (selection == tab) return;
    
    // set the selected tab
    setSelection(tab);
    
    // relayout the children
    relayout();
    
    // give the focus to the first focusable child 
    // on this new tab unless I have the current focus
    if (!hasFocus())
    {
      BWidget w = selection.getContent();
      giveFocus(w);
    }
    
    fireSelectionModified(new BWidgetEvent(BWidgetEvent.MODIFIED, this));
    selectionModified(tab);
  }
  
  private void setSelection(BLabelPane tab)
  {
    updateTabFromTheme(selection, false);
    updateTabFromTheme(tab, true);
    selection = tab;
  }
  
  private void updateTabFromTheme(BLabelPane tab, boolean isSelected)
  {
    if (tab == null) return;
    
    BLabel label = tab.getLabel();
    //add transient flag to prevent it from saving into .px file
    label.setFlags(BWidget.styleClasses, label.getFlags(BWidget.styleClasses) | Flags.TRANSIENT);
    
    if (isSelected)
    {
      label.setStyleClasses(SELECTED_TAB_CLASS);
    }
    else
    {
      label.setStyleClasses(TAB_CLASS);
    }
  }
  
  /**
   * Callback for when current selected tab is modified.
   */
  protected void selectionModified(BLabelPane tab)
  {      
    // Check if the tab is no longer visible. If so, change the selection to a visible tab.
    if ( !tab.isVisible() )
    {
      BLabelPane[] children = getLabelPanes();
      // Go through all the children and set the first visible one
      // to be the selection
      for(int i = 0; i < children.length; i++)
      {
        if (!(children[i] instanceof BLabelPane))
          continue;
        
        BLabelPane child = children[i];
        if (child.isVisible())
        {
          setSelection(child);
          return;
        }
      }
    }
  }
  
  private boolean giveFocus(BWidget w)
  {
    if (w.isFocusTraversable()) { w.requestFocus(); return true; }
    BWidget[] kids = w.getChildWidgets();
    for(int i=1; i<kids.length; ++i)
      if (giveFocus(kids[i])) return true;
    return false;
  }
  
  protected BLabelPane tabAt(double x, double y)
  {
    // try every tab
    RectGeom r = new RectGeom();
    BInsets ti = Theme.tabbedPane().getTabInsets(tabPlacement());
    BWidget[] kids = getLabelPanes();
    for(int i=0; i<kids.length; ++i)
    {
      BLabel label = ((BLabelPane)kids[i]).getLabel();
      r.x = label.getX() - ti.left;
      r.y = label.getY() - ti.top;
      r.width = label.getWidth() + ti.left + ti.right;
      r.height = label.getHeight() + ti.top + ti.bottom;
      if (r.contains(x, y)) return (BLabelPane)kids[i];
    }
    return null;
  }
  
  private void traverseLeft()
  {
    BLabelPane[] kids = getLabelPanes();
    // Get the index of the selected pane
    int oldIndex = -1;
    for(int i=0; i<kids.length; ++i)
    {
      if (selection == kids[i])
      {
        oldIndex = i;
        break;
      }
    }
    // return if no selection is found
    if (oldIndex == -1) return;
    // find the next lesser index whose tab is enabled,
    // wrapping to the end if needed.
    int newIndex = oldIndex - 1;
    if (newIndex < 0) newIndex = kids.length - 1;

    for(int i=1; i<kids.length; ++i)
    {
      if (kids[newIndex].getEnabled())
      {
        selectLabelPane(kids[newIndex]);
        return;
      }
      newIndex = newIndex - 1;
      if (newIndex < 0) newIndex = kids.length-1;
    }
  }

  private void traverseRight()
  {
    BLabelPane[] kids = getLabelPanes();
    // Get the index of the selected pane
    int oldIndex = -1;
    for(int i=0; i<kids.length; ++i)
    {
      if (selection == kids[i])
      {
        oldIndex = i;
        break;
      }
    }
    // return if no selection is found
    if (oldIndex == -1) return;
    // find the next greater index whose tab is enabled,
    // wrapping to the beginning if needed.
    int newIndex = oldIndex + 1;
    if (newIndex == kids.length) newIndex = 0;
    for (int i=1; i<kids.length; ++i)
    {
      if (kids[newIndex].getEnabled())
      {
        selectLabelPane(kids[newIndex]);
        return;
      }
      if (newIndex == kids.length-1) newIndex = 0;
      else newIndex = newIndex + 1;
    }
  }

  public boolean isAnyTabEnabled()
  {
    BLabelPane[] kids = getLabelPanes();
    for (int i=0; i<kids.length; i++)
      if (kids[i].getEnabled()) return true;
    return false;
  }
////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////  

  public boolean isFocusTraversable() { return true; }
  
  public void focusGained(BFocusEvent event) { repaint(); }

  public void focusLost(BFocusEvent event) { repaint(); }
  
  public void keyPressed(BKeyEvent event)
  {
    if (event.getModifiersEx() != 0) return;
    if (event.getKeyCode() == BKeyEvent.VK_LEFT)
    {
      event.consume();
      traverseLeft();
    }
    else if (event.getKeyCode() == BKeyEvent.VK_RIGHT)
    {
      event.consume();
      traverseRight();
    }
  }

  public void mousePressed(BMouseEvent event)
  {                          
    BLabelPane tab = tabAt(event.getX(), event.getY());
    if (tab != null) 
    {
      if (!tab.getEnabled()) return;
      if (event.isPopupTrigger()) support.tabPopup(tab, event);
      else if (event.isButton1Down()) setSelected(tab);
    }
  }

  public void mouseReleased(BMouseEvent event)
  {
    BLabelPane tab = tabAt(event.getX(), event.getY());
    if (tab != null)
    {
      if (!tab.getEnabled()) return;
      if (event.isPopupTrigger()) support.tabPopup(tab, event);
    }
  }

////////////////////////////////////////////////////////////////
// TabPopup
////////////////////////////////////////////////////////////////

  /**
   * TabSupport defines customizable behavoir for BTabbedPane.
   */
  public static class TabSupport
  {
    /**
     * Callback for Tab popup.
     */
    public void tabPopup(BLabelPane tab, BMouseEvent event) {}
  }

////////////////////////////////////////////////////////////////
// Util
////////////////////////////////////////////////////////////////
  
  /**
   * Convience to check for BAlign.center.  Center is not
   * a valid setting, so if found, default back to top.
   */  
  private BAlign tabPlacement()
  {      
    BAlign placement = getTabPlacement();    
    if (placement == BAlign.center) 
      placement = BAlign.top;
    return placement;
  }

///////////////////////////////////////////////////////////
// Overrides
/////////////////////////////////////////////////////////// 

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/tabbedPane.png");

////////////////////////////////////////////////////////////////
// Test Driver
////////////////////////////////////////////////////////////////
  
  /*
  public static void main(String[] args)
  {
    BTabbedPane tab = new BTabbedPane();
    tab.setTabPlacement(BAlign.right);
    //tab.setShowSingleTab(false);
    //tab.setPaintFullBorder(false);
    tab.addPane("TestA", new BButton("contentA"));
    tab.addPane("TestB", new BButton("contentB"));
    tab.addPane("TestC", new BButton("contentC"));
    tab.addPane("TestD", new BButton("contentD"));
    //tab.setTabControl(new BButton("Test"));

    BFrame frame = new BFrame("TabbedPane Test");
    frame.setContent(tab);
    frame.setScreenBounds(100,100,400,300);
    frame.open();
  }   
  */
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private static final String SELECTED_TAB_CLASS = "selected-tab";
  private static final String TAB_CLASS = "tab";
  
  private BLabelPane selection;  // current selection
  private int fixedHeight;    // used for top/bottom placement
  private int fixedWidth;     // used for left/right placement
  private TabSupport support = new TabSupport();
}
