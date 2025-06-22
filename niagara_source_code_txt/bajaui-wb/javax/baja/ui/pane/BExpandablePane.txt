/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.pane;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.enums.*;
import javax.baja.ui.event.*;

import com.tridium.ui.theme.*;

/**
 * BExpandablePane contains two widgets.  A summary widget
 * is displayed all the time, with an expander button
 * used to expand and collapse the pane.  When expanded, the
 * expansion widget is displayed under the summary.
 *
 * @author    Brian Frank on 30 Jan 02
 * @version   $Revision: 19$ $Date: 8/8/07 4:28:42 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Widget displayed all the time.
 */
@NiagaraProperty(
  name = "summary",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
/*
 Widget displayed under summary when expanded.
 */
@NiagaraProperty(
  name = "expansion",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
/*
 Whether the expander button is placed to the left
 or the right of the summary. By default it is placed
 to the right.
 */
@NiagaraProperty(
  name = "expanderHalign",
  type = "BHalign",
  defaultValue = "BHalign.right"
)
/*
 Brush used to paint background of the summary.  By default
 it is transparent.
 */
@NiagaraProperty(
  name = "summaryBackground",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
/*
 Fired when the expander button is expanded or collapsed.
 */
@NiagaraTopic(
  name = "expanderEvent",
  eventType = "BWidgetEvent"
)
public class BExpandablePane
  extends BPane
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BExpandablePane(2746690066)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "summary"

  /**
   * Slot for the {@code summary} property.
   * Widget displayed all the time.
   * @see #getSummary
   * @see #setSummary
   */
  public static final Property summary = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code summary} property.
   * Widget displayed all the time.
   * @see #summary
   */
  public BWidget getSummary() { return (BWidget)get(summary); }

  /**
   * Set the {@code summary} property.
   * Widget displayed all the time.
   * @see #summary
   */
  public void setSummary(BWidget v) { set(summary, v, null); }

  //endregion Property "summary"

  //region Property "expansion"

  /**
   * Slot for the {@code expansion} property.
   * Widget displayed under summary when expanded.
   * @see #getExpansion
   * @see #setExpansion
   */
  public static final Property expansion = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code expansion} property.
   * Widget displayed under summary when expanded.
   * @see #expansion
   */
  public BWidget getExpansion() { return (BWidget)get(expansion); }

  /**
   * Set the {@code expansion} property.
   * Widget displayed under summary when expanded.
   * @see #expansion
   */
  public void setExpansion(BWidget v) { set(expansion, v, null); }

  //endregion Property "expansion"

  //region Property "expanderHalign"

  /**
   * Slot for the {@code expanderHalign} property.
   * Whether the expander button is placed to the left
   * or the right of the summary. By default it is placed
   * to the right.
   * @see #getExpanderHalign
   * @see #setExpanderHalign
   */
  public static final Property expanderHalign = newProperty(0, BHalign.right, null);

  /**
   * Get the {@code expanderHalign} property.
   * Whether the expander button is placed to the left
   * or the right of the summary. By default it is placed
   * to the right.
   * @see #expanderHalign
   */
  public BHalign getExpanderHalign() { return (BHalign)get(expanderHalign); }

  /**
   * Set the {@code expanderHalign} property.
   * Whether the expander button is placed to the left
   * or the right of the summary. By default it is placed
   * to the right.
   * @see #expanderHalign
   */
  public void setExpanderHalign(BHalign v) { set(expanderHalign, v, null); }

  //endregion Property "expanderHalign"

  //region Property "summaryBackground"

  /**
   * Slot for the {@code summaryBackground} property.
   * Brush used to paint background of the summary.  By default
   * it is transparent.
   * @see #getSummaryBackground
   * @see #setSummaryBackground
   */
  public static final Property summaryBackground = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code summaryBackground} property.
   * Brush used to paint background of the summary.  By default
   * it is transparent.
   * @see #summaryBackground
   */
  public BBrush getSummaryBackground() { return (BBrush)get(summaryBackground); }

  /**
   * Set the {@code summaryBackground} property.
   * Brush used to paint background of the summary.  By default
   * it is transparent.
   * @see #summaryBackground
   */
  public void setSummaryBackground(BBrush v) { set(summaryBackground, v, null); }

  //endregion Property "summaryBackground"

  //region Topic "expanderEvent"

  /**
   * Slot for the {@code expanderEvent} topic.
   * Fired when the expander button is expanded or collapsed.
   * @see #fireExpanderEvent
   */
  public static final Topic expanderEvent = newTopic(0, null);

  /**
   * Fire an event for the {@code expanderEvent} topic.
   * Fired when the expander button is expanded or collapsed.
   * @see #expanderEvent
   */
  public void fireExpanderEvent(BWidgetEvent event) { fire(expanderEvent, event, null); }

  //endregion Topic "expanderEvent"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BExpandablePane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////  

  /**
   * Constructor with summary, expansion, and expander location
   */
  public BExpandablePane(BWidget summary, BWidget expansion, BHalign align)
  {
    setSummary(summary);
    setExpansion(expansion);
    setExpanderHalign(align);
  }

  /**
   * Constructor with summary and expansion.
   */
  public BExpandablePane(BWidget summary, BWidget expansion)
  {
    setSummary(summary);
    setExpansion(expansion);
  }

  /**
   * Default constructor.
   */
  public BExpandablePane()
  {
  }

////////////////////////////////////////////////////////////////
// Expansion
////////////////////////////////////////////////////////////////  

  /**
   * Get the expanded state.
   */
  public boolean isExpanded()
  {
    return isExpanded;
  }
  
  /**
   * Set the expanded state.
   */
  public void setExpanded(boolean isExpanded)
  {
    if (this.isExpanded != isExpanded)
    {
      this.isExpanded = isExpanded;
      if (isExpanded)
      {
        relayoutSync();
        scrollToVisible(new RectGeom(0, 0, getWidth(), getHeight()));
      }
      else
      {
        relayout();
      }
    }
  }

  /**
   * Set the expander button's visiblity.
   */
  public void setExpanderVisible(boolean expanderVisible)
  {
    if (this.expanderVisible != expanderVisible)
    {
      this.expanderVisible = expanderVisible;
      relayout();
    }
  }


  /**
   * @since Niagara 4.7
   */
  public boolean isExpanderVisible()
  {
    return this.expanderVisible;
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Compute the preferred size of the pane.
   */  
  public void computePreferredSize()
  {
    // get summary
    BWidget summary = getSummary();
    summary.computePreferredSize();
    double pw = summary.getPreferredWidth();
    double ph = summary.getPreferredHeight();
    
    // add button
    if (expanderVisible)
    {
      pw += GAP + Theme.expandablePane().getExpanderWidth();
      ph = Math.max(ph, MIN_EXPANDER_HEIGHT);
    }
    
    // add expansion
    if (isExpanded)
    {
      BWidget expansion = getExpansion();
      expansion.computePreferredSize();
      pw = Math.max(pw, expansion.getPreferredWidth());
      ph += expansion.getPreferredHeight();
    }
    
    setPreferredSize(pw, ph);
  }
  
  /**
   * Layout the pane.
   */
  public void doLayout(BWidget[] kids)
  {
    double w = getWidth();
    double h = getHeight();
    
    BWidget summary = getSummary();
    summary.computePreferredSize();
    double sw = summary.getPreferredWidth();
    double sh = summary.getPreferredHeight();
    double seh = Math.max(sh, MIN_EXPANDER_HEIGHT);
    
    double gap = summary.isNull() ? 0 : GAP;
    if (expanderVisible)
    {
      double ew = Theme.expandablePane().getExpanderWidth();
      double yoffset = (sh >= seh) ? seh - sh : 0.0;
      if (getExpanderHalign() == BHalign.right)
      {
        summary.setBounds(0, (seh-sh)/2, Math.min(sw, w-gap-ew), sh);
        button.set(sw+gap, yoffset, ew, seh);
      }
      else
      {
        button.set(0, yoffset, ew, seh);
        summary.setBounds(ew + gap, (seh-sh)/2, Math.min(sw, w-gap-ew), sh);
      }
    }
    else
    {
      summary.setBounds(0, (seh-sh)/2, sw, sh);
      button.set(0,0,0,0);
    }
    
    BWidget expansion = getExpansion();
    if (isExpanded)
      expansion.setBounds(0,seh,w,h-seh);
    else
      expansion.setBounds(0,0,0,0);    
  }

////////////////////////////////////////////////////////////////
// Paint
////////////////////////////////////////////////////////////////  

  /**
   * Paint the pane.
   */
  public void paint(Graphics g)
  {
    BBrush bg = getSummaryBackground();
    if (!bg.isNull())
    {
      g.setBrush(bg);
      g.fillRect(0, 0, getWidth(), Math.max(getSummary().getHeight(), MIN_EXPANDER_HEIGHT));
    }

    paintChild(g, getSummary());
    if (isExpanded) paintChild(g, getExpansion());
    if (expanderVisible)
      Theme.expandablePane().paintButton(g, this, button, isExpanded, overButton);
  }

////////////////////////////////////////////////////////////////
// Mouse Eventing
////////////////////////////////////////////////////////////////  
  
  public void mouseReleased(BMouseEvent event)
  {
    if(!getEnabled()) return;
    if (button.contains(event.getX(), event.getY()))
    {
      setExpanded(!isExpanded);
      fireExpanderEvent(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, this));
    }
  }

  public void mouseEntered(BMouseEvent event)
  {
    if(!getEnabled()) return;
    super.mouseEntered(event);
    checkOverButton(event);
  }

  public void mouseExited(BMouseEvent event)
  {
    if(!getEnabled()) return;
    overButton = false;
    repaint();
  }

  public void mouseMoved(BMouseEvent event)
  {
    if(!getEnabled()) return;
    super.mouseMoved(event);
    checkOverButton(event);
  }
  
  private void checkOverButton(BMouseEvent event)
  {
    double mx = event.getX();
    double my = event.getY();
    boolean nowOverButton = button.contains(mx, my);
    if (nowOverButton != overButton)
    {
      overButton = nowOverButton;
      repaint();
    }
  }

  /**
   * @since Niagara 4.7
   */
  public RectGeom getButtonGeom()
  {
    return button;
  }

////////////////////////////////////////////////////////////////
// Presentation
//////////////////////////////////////////////////////////////// 
  
  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/expandablePane.png");
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  // button size
  private static final double GAP  = 2;
  private static final double MIN_EXPANDER_HEIGHT = 17.0;

  private boolean isExpanded = false;
  private boolean expanderVisible = true;
  private RectGeom button = new RectGeom();
  private boolean overButton;

}
