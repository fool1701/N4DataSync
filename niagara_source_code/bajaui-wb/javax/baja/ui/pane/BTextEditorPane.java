/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.pane;

import javax.baja.gx.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.text.*;

import com.tridium.ui.theme.*;

/**
 * BTextEditorPane is a special BScrollPane which is designed 
 * to provide a scrolling support to a BTextEditor.
 *
 * @author    Brian Frank       
 * @creation  8 Aug 01
 * @version   $Revision: 7$ $Date: 4/27/05 9:29:31 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "content",
  type = "BWidget",
  defaultValue = "new BTextEditor()",
  override = true
)
/*
 Number of rows which should visible when
 computing the panes's preferred layout.
 */
@NiagaraProperty(
  name = "visibleRows",
  type = "int",
  defaultValue = "10",
  facets = @Facet("BFacets.make(BFacets.MIN, BInteger.make(0))")
)
/*
 Number of columns which should visible when
 computing the pane's preferred layout.
 */
@NiagaraProperty(
  name = "visibleColumns",
  type = "int",
  defaultValue = "40",
  facets = @Facet("BFacets.make(BFacets.MIN, BInteger.make(0))")
)
public class BTextEditorPane
  extends BScrollPane
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BTextEditorPane(3238266445)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "content"

  /**
   * Slot for the {@code content} property.
   * @see #getContent
   * @see #setContent
   */
  public static final Property content = newProperty(0, new BTextEditor(), null);

  //endregion Property "content"

  //region Property "visibleRows"

  /**
   * Slot for the {@code visibleRows} property.
   * Number of rows which should visible when
   * computing the panes's preferred layout.
   * @see #getVisibleRows
   * @see #setVisibleRows
   */
  public static final Property visibleRows = newProperty(0, 10, BFacets.make(BFacets.MIN, BInteger.make(0)));

  /**
   * Get the {@code visibleRows} property.
   * Number of rows which should visible when
   * computing the panes's preferred layout.
   * @see #visibleRows
   */
  public int getVisibleRows() { return getInt(visibleRows); }

  /**
   * Set the {@code visibleRows} property.
   * Number of rows which should visible when
   * computing the panes's preferred layout.
   * @see #visibleRows
   */
  public void setVisibleRows(int v) { setInt(visibleRows, v, null); }

  //endregion Property "visibleRows"

  //region Property "visibleColumns"

  /**
   * Slot for the {@code visibleColumns} property.
   * Number of columns which should visible when
   * computing the pane's preferred layout.
   * @see #getVisibleColumns
   * @see #setVisibleColumns
   */
  public static final Property visibleColumns = newProperty(0, 40, BFacets.make(BFacets.MIN, BInteger.make(0)));

  /**
   * Get the {@code visibleColumns} property.
   * Number of columns which should visible when
   * computing the pane's preferred layout.
   * @see #visibleColumns
   */
  public int getVisibleColumns() { return getInt(visibleColumns); }

  /**
   * Set the {@code visibleColumns} property.
   * Number of columns which should visible when
   * computing the pane's preferred layout.
   * @see #visibleColumns
   */
  public void setVisibleColumns(int v) { setInt(visibleColumns, v, null); }

  //endregion Property "visibleColumns"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTextEditorPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with initial text, rows, columns, and editable.
   */
  public BTextEditorPane(String text, int visibleRows, int visibleColumns, boolean editable)
  {
    this(new BTextEditor(text, editable), visibleRows, visibleColumns);
  }

  /**
   * Constructor with initial rows and columns.
   */
  public BTextEditorPane(int visibleRows, int visibleColumns)
  {
    this();
    setVisibleRows(visibleRows);
    setVisibleColumns(visibleColumns);
  }

  /**
   * Constructor with initial editor, rows, and columns.
   */
  public BTextEditorPane(BTextEditor editor, int visibleRows, int visibleColumns)
  {
    this();
    setContent(editor);
    setVisibleRows(visibleRows);
    setVisibleColumns(visibleColumns);
  }

  /**
   * Constructor with initial editor.
   */
  public BTextEditorPane(BTextEditor editor)
  {
    this();
    setContent(editor);
  }

  /**
   * No argument constructor.
   */
  public BTextEditorPane()
  {
    getHscrollBar().setSnapToUnitIncrement(true);
    getVscrollBar().setSnapToUnitIncrement(true);
  }

////////////////////////////////////////////////////////////////
// TextEditor
////////////////////////////////////////////////////////////////

  /**
   * Get the underlying BTextEditor for this pane.
   */
  public BTextEditor getEditor()
  {
    return (BTextEditor)getContent();
  }

  /**
   * Convenience method for <code>getEditor().getText()</code>.
   */
  public String getText()
  {
    return getEditor().getText();
  }

  /**
   * Convenience method for <code>getEditor().setText()</code>.
   */
  public void setText(String text)
  {
    getEditor().setText(text);
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * The preferred size of a BTextField is based on 
   * its visibleColumns property.
   */
  public void computePreferredSize()
  {
    BInsets insets = theme().getInsets();
    double cellHeight = getEditor().getRenderer().getLineHeight();
    double cellWidth = getEditor().getRenderer().getColumnWidth(null, 0);
    
    double pw = cellWidth*getVisibleColumns() + insets.left + insets.right;
    double ph = cellHeight*getVisibleRows() + insets.top + insets.bottom;
    setPreferredSize(pw, ph);
  }
  
  /**
   * Layout the text editor.
   */
  public void doLayout(BWidget[] kids)
  {
    super.doLayout(kids);
    
    BTextEditor editor = getEditor();
    BInsets insets = theme().getInsets();
    double cellHeight = editor.getRenderer().getLineHeight();
    double cellWidth = editor.getRenderer().getColumnWidth(null, 0);
    int visibleLines = (int)((getHeight()-insets.top-insets.bottom) / cellHeight);
    int visibleColumns = (int)((getWidth()-insets.left-insets.right) / cellWidth);

    BScrollBar vsb = getVscrollBar();
// DOGX    
    vsb.setUnitIncrement((int)cellHeight);
    vsb.setBlockIncrement((int)((visibleLines-1)*cellHeight));

    BScrollBar hsb = getHscrollBar();
    hsb.setUnitIncrement((int)cellWidth);
    hsb.setBlockIncrement((int)((visibleColumns-1)*cellHeight));
  }
  
////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Override scroll pane theme.
   */
  ScrollPaneTheme theme()
  {
    return Theme.textEditorPane();
  }
  
  
}
