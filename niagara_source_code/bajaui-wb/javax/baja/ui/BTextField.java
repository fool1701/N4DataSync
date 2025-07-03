/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.gx.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.event.*;
import javax.baja.ui.text.*;

import com.tridium.ui.theme.*;

/**
 * BTextField is a specialized BTextEditor designed to
 * provide single line text entry.
 *
 * @author    Brian Frank       
 * @creation  30 Nov 00
 * @version   $Revision: 42$ $Date: 4/27/05 9:29:30 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Number of columns which should visible when
 computing the text editor's preferred layout.
 */
@NiagaraProperty(
  name = "visibleColumns",
  type = "int",
  defaultValue = "20",
  facets = @Facet("BFacets.make(BFacets.MIN, BInteger.make(0))")
)
/*
 Expands to fill the height of the bounds. If
 false constrain to preferred height.
 */
@NiagaraProperty(
  name = "expandHeight",
  type = "boolean",
  defaultValue = "false"
)
/*
 This topic is fired is the user hits the
 Enter key in the text field.
 */
@NiagaraTopic(
  name = "actionPerformed",
  eventType = "BWidgetEvent"
)
/*
 This topic is fired when the user hits the
 Esc key in the text field.
 */
@NiagaraTopic(
  name = "cancelled",
  eventType = "BWidgetEvent"
)
public class BTextField
  extends BTextEditor
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BTextField(724347109)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "visibleColumns"

  /**
   * Slot for the {@code visibleColumns} property.
   * Number of columns which should visible when
   * computing the text editor's preferred layout.
   * @see #getVisibleColumns
   * @see #setVisibleColumns
   */
  public static final Property visibleColumns = newProperty(0, 20, BFacets.make(BFacets.MIN, BInteger.make(0)));

  /**
   * Get the {@code visibleColumns} property.
   * Number of columns which should visible when
   * computing the text editor's preferred layout.
   * @see #visibleColumns
   */
  public int getVisibleColumns() { return getInt(visibleColumns); }

  /**
   * Set the {@code visibleColumns} property.
   * Number of columns which should visible when
   * computing the text editor's preferred layout.
   * @see #visibleColumns
   */
  public void setVisibleColumns(int v) { setInt(visibleColumns, v, null); }

  //endregion Property "visibleColumns"

  //region Property "expandHeight"

  /**
   * Slot for the {@code expandHeight} property.
   * Expands to fill the height of the bounds. If
   * false constrain to preferred height.
   * @see #getExpandHeight
   * @see #setExpandHeight
   */
  public static final Property expandHeight = newProperty(0, false, null);

  /**
   * Get the {@code expandHeight} property.
   * Expands to fill the height of the bounds. If
   * false constrain to preferred height.
   * @see #expandHeight
   */
  public boolean getExpandHeight() { return getBoolean(expandHeight); }

  /**
   * Set the {@code expandHeight} property.
   * Expands to fill the height of the bounds. If
   * false constrain to preferred height.
   * @see #expandHeight
   */
  public void setExpandHeight(boolean v) { setBoolean(expandHeight, v, null); }

  //endregion Property "expandHeight"

  //region Topic "actionPerformed"

  /**
   * Slot for the {@code actionPerformed} topic.
   * This topic is fired is the user hits the
   * Enter key in the text field.
   * @see #fireActionPerformed
   */
  public static final Topic actionPerformed = newTopic(0, null);

  /**
   * Fire an event for the {@code actionPerformed} topic.
   * This topic is fired is the user hits the
   * Enter key in the text field.
   * @see #actionPerformed
   */
  public void fireActionPerformed(BWidgetEvent event) { fire(actionPerformed, event, null); }

  //endregion Topic "actionPerformed"

  //region Topic "cancelled"

  /**
   * Slot for the {@code cancelled} topic.
   * This topic is fired when the user hits the
   * Esc key in the text field.
   * @see #fireCancelled
   */
  public static final Topic cancelled = newTopic(0, null);

  /**
   * Fire an event for the {@code cancelled} topic.
   * This topic is fired when the user hits the
   * Esc key in the text field.
   * @see #cancelled
   */
  public void fireCancelled(BWidgetEvent event) { fire(cancelled, event, null); }

  //endregion Topic "cancelled"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTextField.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////


  /**
   * Constructor with initial text and visible columns and
   * editable flag.
   */
  public BTextField(String text, int visibleColumns, boolean editable)
  {
    super(text, editable);
    setVisibleColumns(visibleColumns);
    initTextField();
  }

  /**
   * Constructor with initial text and visible columns.
   */
  public BTextField(String text, int visibleColumns)
  {
    super(text, true);
    setVisibleColumns(visibleColumns);
    initTextField();
  }

  /**
   * Constructor with initial text.
   */
  public BTextField(String text)
  {
    super(text, true);
    initTextField();
  }

  /**
   * No argument constructor.
   */
  public BTextField()
  {
    initTextField();
  }
  
  /**
   * Common constructor intialization.
   */
  private void initTextField()
  {
    setParser(new SingleLineParser());
  }

////////////////////////////////////////////////////////////////
// BTextEditor
////////////////////////////////////////////////////////////////

  /**
   * This most certainly is a single line text editor.
   */
  public boolean isSingleLine()
  {
    return true;
  }

  /**
   * Scroll the specified position visible.
   */
  public void scrollToVisible(Position pos)
  {
    // NCCB-856  Asian Char: Unable to view characters entered when length of name exceeds the editor fixed length

    // compute (c)urrent and (d)esired rectangle
    BInsets insets = Theme.textField().getInsets();
    Line line = getRenderer().getModel().getLine(pos.line);
    double lineWidth = getRenderer().getLineWidth(line,0,pos.column);
    double dx1 = lineWidth - insets.left;
    // ensure index passed to getColumnWidth() !< 0
    int index = (pos.column > 0)?1:0;
    double cellWidth = getRenderer().getColumnWidth(line, pos.column-index);
    
    // This original computation did not handle CJK character fonts (variable width)
    // double cellWidth =  getRenderer().getColumnWidth(null, 0);
    // double dx1 = pos.column*cellWidth - insets.left;
    
    double dx2 = dx1 + cellWidth;
    double width = getWidth() - insets.left - insets.right;
    double cx1 = scrollOffset;
    double cx2 = cx1 + width;

    // compute necessary shifts
    if (cx2 < dx2) cx1 += dx2-cx2;
    if (cx1 > dx1) cx1 = dx1;

    // ensure we didn't shift out of bounds
    if (cx1 < 0) cx1 = 0;

    // do shift
    scrollOffset = cx1;
    repaint();
  }

  /**
   * Translate the specified pixel position to 
   * the a logical document position.
   */
  public Position getPositionAt(double x, double y)
  {
    return super.getPositionAt(x+scrollOffset, y);
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
    double cellHeight = getRenderer().getLineHeight();
    double cellWidth = getRenderer().getColumnWidth(null, 0);
    
    BInsets insets = getInsets();
    double pw = cellWidth*getVisibleColumns() + insets.left + insets.right;
    double ph = cellHeight + insets.top + insets.bottom;
    setPreferredSize(pw, ph);
  }
  
  /**
   * Get the text editor insets for the border.
   */
  protected BInsets getInsets()
  {
    if (getParent() instanceof BDropDown)
      return dropDownInsets;
    else
      return Theme.textField().getInsets();
  }

////////////////////////////////////////////////////////////////
// Paint
////////////////////////////////////////////////////////////////  

  public void paint(Graphics g)
  {
    BInsets insets = getInsets();
    double ph = getRenderer().getLineHeight() + insets.top + insets.bottom;
    double ty = 0;

    if (getExpandHeight())
    {
      g.setBrush(Theme.textField().getTextBackground(this));
      g.fillRect(0,0,getWidth(),getHeight());
      ty = (getHeight() - ph) / 2;
    }

    g.translate(-scrollOffset, ty);
    super.paint(g);
    g.translate(scrollOffset, -ty);
    if (!(getParent() instanceof BDropDown))
      paintBorder(g);
  }
  
  protected void paintBorder(Graphics g)
  {
    double h = getHeight();
    if (!getExpandHeight())
    {
      BInsets insets = getInsets();
      h = getRenderer().getLineHeight() + insets.top + insets.bottom;
    }
    Theme.textField().paintBorder(g, this, getWidth(), h);    
  }

////////////////////////////////////////////////////////////////
// Keyboard Input
////////////////////////////////////////////////////////////////

  /**
   * This sets a flag so that the BKeyEvent used to fire
   * the action performed topic is consumed, rather than
   * allowed to propogate (such as closing a dialog).
   */
  public void setConsumeActionKeyEvent(boolean consume)
  {
    consumeActionKeyEvent = consume;
  }

  public void keyPressed(BKeyEvent event)
  {
    if(event.getKeyCode() == BKeyEvent.VK_ENTER)
    {
      // do nothing
    }
    else if(event.getKeyCode() == BKeyEvent.VK_ESCAPE)
    {
      // do nothing
    }
    else if(event.getKeyCode() == BKeyEvent.VK_DOWN)
    {
      BObject parent = getParent();
      if (parent instanceof BTextDropDown)
        ((BTextDropDown)parent).openDropDown();
    }
    else
    {
      super.keyPressed(event);
    }
  }

  public void keyTyped(BKeyEvent event)
  {
    char key = event.getKeyChar();
    if (key == '\n' || key == '\r') return;
    if (event.getKeyCode() == BKeyEvent.VK_ESCAPE) return;
    super.keyTyped(event);
  }

  public void keyReleased(BKeyEvent event)
  {
    if(event.getKeyCode() == BKeyEvent.VK_ENTER)
    {
      fireActionPerformed(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, this));
      if (consumeActionKeyEvent) event.consume();
    }
    else if (event.getKeyCode() == BKeyEvent.VK_ESCAPE)
    {
      fireCancelled(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, this));
      if (consumeActionKeyEvent) event.consume();
    }
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/textField.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private static BInsets dropDownInsets = BInsets.make(1, 1, 1, 1);

  private double scrollOffset;
  private boolean consumeActionKeyEvent;
}
