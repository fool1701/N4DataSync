/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.pane;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.enums.*;
import javax.baja.ui.event.*;

import com.tridium.ui.theme.*;

/**
 * BScrollPane lays out one child using a viewport
 * which may be scrolled to view the entire child's
 * actual size.
 *
 * @author    Brian Frank
 * @creation  26 Nov 00
 * @version   $Revision: 75$ $Date: 6/20/11 9:34:25 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Content is the widget to be scrolled.
 */
@NiagaraProperty(
  name = "content",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
/*
 The horizontal scroll bar's visibility
 is determined by horizontalScrollBarPolicy.
 */
@NiagaraProperty(
  name = "hscrollBar",
  type = "BScrollBar",
  defaultValue = "new BScrollBar(BOrientation.horizontal, 15, 150, false)",
  flags = Flags.READONLY | Flags.TRANSIENT | Flags.HIDDEN
)
/*
 The vertical scroll bar's visibility
 is determined by verticalScrollBarPolicy.
 */
@NiagaraProperty(
  name = "vscrollBar",
  type = "BScrollBar",
  defaultValue = "new BScrollBar(BOrientation.vertical, 15, 150, false)",
  flags = Flags.READONLY | Flags.TRANSIENT | Flags.HIDDEN
)
/*
 Determines when should the horizontal scroll
 bar be displayed.
 */
@NiagaraProperty(
  name = "hpolicy",
  type = "BScrollBarPolicy",
  defaultValue = "BScrollBarPolicy.asNeeded"
)
/*
 Determines when should the vertical scroll
 bar be displayed.
 */
@NiagaraProperty(
  name = "vpolicy",
  type = "BScrollBarPolicy",
  defaultValue = "BScrollBarPolicy.asNeeded"
)
/*
 Brush used to paint background of viewport.  By default
 it uses a theme based window background brush.
 */
@NiagaraProperty(
  name = "viewportBackground",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
/*
 Determines when should the pane's border be displayed.  If set to
 asNeeded, the border will be displayed only when scroll bars
 are visible
 */
@NiagaraProperty(
  name = "borderPolicy",
  type = "BScrollBarPolicy",
  defaultValue = "BScrollBarPolicy.always"
)
@NiagaraAction(
  name = "hscroll",
  parameterType = "BScrollEvent",
  defaultValue = "new BScrollEvent()"
)
@NiagaraAction(
  name = "vscroll",
  parameterType = "BScrollEvent",
  defaultValue = "new BScrollEvent()"
)
public class BScrollPane
  extends BPane
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BScrollPane(3564556321)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "content"

  /**
   * Slot for the {@code content} property.
   * Content is the widget to be scrolled.
   * @see #getContent
   * @see #setContent
   */
  public static final Property content = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code content} property.
   * Content is the widget to be scrolled.
   * @see #content
   */
  public BWidget getContent() { return (BWidget)get(content); }

  /**
   * Set the {@code content} property.
   * Content is the widget to be scrolled.
   * @see #content
   */
  public void setContent(BWidget v) { set(content, v, null); }

  //endregion Property "content"

  //region Property "hscrollBar"

  /**
   * Slot for the {@code hscrollBar} property.
   * The horizontal scroll bar's visibility
   * is determined by horizontalScrollBarPolicy.
   * @see #getHscrollBar
   * @see #setHscrollBar
   */
  public static final Property hscrollBar = newProperty(Flags.READONLY | Flags.TRANSIENT | Flags.HIDDEN, new BScrollBar(BOrientation.horizontal, 15, 150, false), null);

  /**
   * Get the {@code hscrollBar} property.
   * The horizontal scroll bar's visibility
   * is determined by horizontalScrollBarPolicy.
   * @see #hscrollBar
   */
  public BScrollBar getHscrollBar() { return (BScrollBar)get(hscrollBar); }

  /**
   * Set the {@code hscrollBar} property.
   * The horizontal scroll bar's visibility
   * is determined by horizontalScrollBarPolicy.
   * @see #hscrollBar
   */
  public void setHscrollBar(BScrollBar v) { set(hscrollBar, v, null); }

  //endregion Property "hscrollBar"

  //region Property "vscrollBar"

  /**
   * Slot for the {@code vscrollBar} property.
   * The vertical scroll bar's visibility
   * is determined by verticalScrollBarPolicy.
   * @see #getVscrollBar
   * @see #setVscrollBar
   */
  public static final Property vscrollBar = newProperty(Flags.READONLY | Flags.TRANSIENT | Flags.HIDDEN, new BScrollBar(BOrientation.vertical, 15, 150, false), null);

  /**
   * Get the {@code vscrollBar} property.
   * The vertical scroll bar's visibility
   * is determined by verticalScrollBarPolicy.
   * @see #vscrollBar
   */
  public BScrollBar getVscrollBar() { return (BScrollBar)get(vscrollBar); }

  /**
   * Set the {@code vscrollBar} property.
   * The vertical scroll bar's visibility
   * is determined by verticalScrollBarPolicy.
   * @see #vscrollBar
   */
  public void setVscrollBar(BScrollBar v) { set(vscrollBar, v, null); }

  //endregion Property "vscrollBar"

  //region Property "hpolicy"

  /**
   * Slot for the {@code hpolicy} property.
   * Determines when should the horizontal scroll
   * bar be displayed.
   * @see #getHpolicy
   * @see #setHpolicy
   */
  public static final Property hpolicy = newProperty(0, BScrollBarPolicy.asNeeded, null);

  /**
   * Get the {@code hpolicy} property.
   * Determines when should the horizontal scroll
   * bar be displayed.
   * @see #hpolicy
   */
  public BScrollBarPolicy getHpolicy() { return (BScrollBarPolicy)get(hpolicy); }

  /**
   * Set the {@code hpolicy} property.
   * Determines when should the horizontal scroll
   * bar be displayed.
   * @see #hpolicy
   */
  public void setHpolicy(BScrollBarPolicy v) { set(hpolicy, v, null); }

  //endregion Property "hpolicy"

  //region Property "vpolicy"

  /**
   * Slot for the {@code vpolicy} property.
   * Determines when should the vertical scroll
   * bar be displayed.
   * @see #getVpolicy
   * @see #setVpolicy
   */
  public static final Property vpolicy = newProperty(0, BScrollBarPolicy.asNeeded, null);

  /**
   * Get the {@code vpolicy} property.
   * Determines when should the vertical scroll
   * bar be displayed.
   * @see #vpolicy
   */
  public BScrollBarPolicy getVpolicy() { return (BScrollBarPolicy)get(vpolicy); }

  /**
   * Set the {@code vpolicy} property.
   * Determines when should the vertical scroll
   * bar be displayed.
   * @see #vpolicy
   */
  public void setVpolicy(BScrollBarPolicy v) { set(vpolicy, v, null); }

  //endregion Property "vpolicy"

  //region Property "viewportBackground"

  /**
   * Slot for the {@code viewportBackground} property.
   * Brush used to paint background of viewport.  By default
   * it uses a theme based window background brush.
   * @see #getViewportBackground
   * @see #setViewportBackground
   */
  public static final Property viewportBackground = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code viewportBackground} property.
   * Brush used to paint background of viewport.  By default
   * it uses a theme based window background brush.
   * @see #viewportBackground
   */
  public BBrush getViewportBackground() { return (BBrush)get(viewportBackground); }

  /**
   * Set the {@code viewportBackground} property.
   * Brush used to paint background of viewport.  By default
   * it uses a theme based window background brush.
   * @see #viewportBackground
   */
  public void setViewportBackground(BBrush v) { set(viewportBackground, v, null); }

  //endregion Property "viewportBackground"

  //region Property "borderPolicy"

  /**
   * Slot for the {@code borderPolicy} property.
   * Determines when should the pane's border be displayed.  If set to
   * asNeeded, the border will be displayed only when scroll bars
   * are visible
   * @see #getBorderPolicy
   * @see #setBorderPolicy
   */
  public static final Property borderPolicy = newProperty(0, BScrollBarPolicy.always, null);

  /**
   * Get the {@code borderPolicy} property.
   * Determines when should the pane's border be displayed.  If set to
   * asNeeded, the border will be displayed only when scroll bars
   * are visible
   * @see #borderPolicy
   */
  public BScrollBarPolicy getBorderPolicy() { return (BScrollBarPolicy)get(borderPolicy); }

  /**
   * Set the {@code borderPolicy} property.
   * Determines when should the pane's border be displayed.  If set to
   * asNeeded, the border will be displayed only when scroll bars
   * are visible
   * @see #borderPolicy
   */
  public void setBorderPolicy(BScrollBarPolicy v) { set(borderPolicy, v, null); }

  //endregion Property "borderPolicy"

  //region Action "hscroll"

  /**
   * Slot for the {@code hscroll} action.
   * @see #hscroll(BScrollEvent parameter)
   */
  public static final Action hscroll = newAction(0, new BScrollEvent(), null);

  /**
   * Invoke the {@code hscroll} action.
   * @see #hscroll
   */
  public void hscroll(BScrollEvent parameter) { invoke(hscroll, parameter, null); }

  //endregion Action "hscroll"

  //region Action "vscroll"

  /**
   * Slot for the {@code vscroll} action.
   * @see #vscroll(BScrollEvent parameter)
   */
  public static final Action vscroll = newAction(0, new BScrollEvent(), null);

  /**
   * Invoke the {@code vscroll} action.
   * @see #vscroll
   */
  public void vscroll(BScrollEvent parameter) { invoke(vscroll, parameter, null); }

  //endregion Action "vscroll"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BScrollPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a BScrollPane.
   */
  public BScrollPane(BWidget content)
  {
    this();
    setContent(content);
  }

  /**
   * Construct a BScrollPane.
   */
  public BScrollPane()
  {
    // required for non-mounted panes
    linkTo(getHscrollBar(), BScrollBar.positionChanged, hscroll);
    linkTo(getVscrollBar(), BScrollBar.positionChanged, vscroll);
  }
  
  public void started()
    throws Exception
  {
    // required for issue 13466
    linkTo(getHscrollBar(), BScrollBar.positionChanged, hscroll);
    linkTo(getVscrollBar(), BScrollBar.positionChanged, vscroll);
  }
  
////////////////////////////////////////////////////////////////
// Translation
////////////////////////////////////////////////////////////////

  /**
   * Get the visible viewport of the scroll pane.
   */
  public RectGeom getViewport()
  {
    return new RectGeom(viewport);
  }

  public BWidget childAt(Point pt)
  {
    BInsets insets = theme().getInsets();
    if (pt.x - insets.left < viewport.width && pt.y - insets.top < viewport.height)
    {
      return getContent();
    }
    else
    {
      BScrollBar hsb = getHscrollBar();
      if (hsb.contains(pt.x-hsb.getX(), pt.y-hsb.getY())) return hsb;

      BScrollBar vsb = getVscrollBar();
      if (vsb.contains(pt.x-vsb.getX(), pt.y-vsb.getY())) return vsb;
    }
    return null;
  }

  public Point translateToChild(BWidget child, Point pt)
  {
    if (child == getContent())
    {
      BInsets insets = theme().getInsets();
      pt.translate( viewport.x - insets.left, viewport.y - insets.top );
      return pt;
    }
    return super.translateToChild(child, pt);
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * BScrollPane has a preferred size equal to
   * its child content component plus insets.
   */
  public void computePreferredSize()
  {
    BInsets insets = theme().getInsets();
    BWidget c = getContent();
    c.computePreferredSize();
    double w = c.getPreferredWidth() + insets.left + insets.right;
    double h = c.getPreferredHeight() + insets.top + insets.bottom;
    setPreferredSize(w, h);
  }

  /**
   * Layout the scroll pane, depending on the scrollbar
   * policy this may add or remove scroll bars.
   */
  public void doLayout(BWidget[] kids)
  {
    // bail if no child component
    BWidget c = getContent();
    if (c == null) return;

    // get my size
    BInsets insets = theme().getInsets(this);
    double w = getWidth() - insets.left - insets.right;
    double h = getHeight() - insets.top - insets.bottom;

    // compute the child's preferred size
    c.computePreferredSize();
    double cw = c.getPreferredWidth();
    double ch = c.getPreferredHeight();

    // get the scroll bars and policies
    // and compute their preferred size
    BScrollBar hsb = getHscrollBar();
    BScrollBar vsb = getVscrollBar();
    BScrollBarPolicy hPolicy = getHpolicy();
    BScrollBarPolicy vPolicy = getVpolicy();
    hsb.computePreferredSize();
    vsb.computePreferredSize();

    // do we need a horizontal scrollbar
    hsbShow = false;
    switch(hPolicy.getOrdinal())
    {
      case BScrollBarPolicy.AS_NEEDED: hsbShow = cw > w; break;
      case BScrollBarPolicy.ALWAYS: hsbShow = true; break;
      case BScrollBarPolicy.NEVER: hsbShow = false; break;
    }

    // do we need a vertical scrollbar
    vsbShow = false;
    switch(vPolicy.getOrdinal())
    {
      case BScrollBarPolicy.AS_NEEDED:  vsbShow = ch > h; break;
      case BScrollBarPolicy.ALWAYS: vsbShow = true; break;
      case BScrollBarPolicy.NEVER: vsbShow = false; break;
    }

    // at this point we need to re-evalaute our asNeeded
    // scrollbars in case the addition of the one scrollbar
    // caused us to need the other one
    if (hPolicy == BScrollBarPolicy.asNeeded && vsbShow)
      hsbShow = cw > w - vsb.getPreferredWidth();
    if (vPolicy == BScrollBarPolicy.asNeeded && hsbShow)
      vsbShow = ch > h - hsb.getPreferredHeight();
    if (hPolicy == BScrollBarPolicy.asNeeded && vsbShow)
      hsbShow = cw > w - vsb.getPreferredWidth();

    // layout horizonal scroll bar
    if (!hsbShow) hsb.setBounds(0,0,0,0);
    else
    {
      // compute bounds
      double hsbWidth = vsbShow ? w - vsb.getPreferredWidth() : w;
      double hsbHeight = hsb.getPreferredHeight();
      hsb.setBounds(insets.left, h - hsbHeight + insets.top, hsbWidth, hsbHeight);

      // compute extent
      hsb.setMin(0);
      hsb.setMax((int)cw);
      hsb.setExtent((int)Math.min(cw, w - (vsbShow ? vsb.getPreferredWidth() : 0) ));

      // check position
      if (hsb.getPosition() > cw - hsb.getExtent())
        hsb.setPosition((int)(viewport.x = cw - hsb.getExtent()));
      else
        viewport.x = hsb.getPosition();
    }

    // layout vertical scroll bar
    if (!vsbShow) vsb.setBounds(0,0,0,0);
    else
    {
      // compute bounds
      double vsbWidth = vsb.getPreferredWidth();
      double vsbHeight = hsbShow ? h - hsb.getPreferredHeight() : h;
      vsb.setBounds(w - vsbWidth + insets.right, insets.top, vsbWidth, vsbHeight);

      // compute extent
      vsb.setMin(0);
      vsb.setMax((int)ch);
      vsb.setExtent((int)Math.min( ch, h - hsb.getHeight() ));

      // check position
      if (vsb.getPosition() > ch - vsb.getExtent())
        vsb.setPosition((int)(viewport.y = ch - vsb.getExtent()));
      else
        viewport.y = vsb.getPosition();
    }

    // compute the viewport dimensions
    viewport.width = vsbShow ? w - vsb.getWidth() : w;
    viewport.height = hsbShow ? h - hsb.getHeight() : h;

    // ensure valid horizontal position
    if (viewport.width < 0) { viewport.x = 0; hsb.setPosition(0); }
    else if (viewport.width > cw) {viewport.x = 0; cw = viewport.width; }

    // ensure valid vertical position
    if (viewport.height < 0) { viewport.y = 0; vsb.setPosition(0); }
    else if (viewport.height > ch) { viewport.y = 0; ch = viewport.height; }

    // set the child bounds
    c.setBounds(0, 0, cw, ch);

    // scroll to if necessary
    if (scrollTo != null)
    {
      scrollToVisible(scrollTo);
      scrollTo = null;
    }
/*
System.out.println("   final say:");
System.out.println("     bounds:   " + boundsToString());
System.out.println("     content:  " + c.boundsToString());
System.out.println("     viewport: " + viewport);
System.out.println("     hsb:  pos=" + hsb.getPosition() + " extent=" + hsb.getExtent() + " max=" + hsb.getMax());
*/
  }

////////////////////////////////////////////////////////////////
// Painting
////////////////////////////////////////////////////////////////

  public void paint(Graphics g)
  {
    BInsets insets = theme().getInsets();
    BWidget c = getContent();
    BScrollBar hsb = getHscrollBar();
    BScrollBar vsb = getVscrollBar();

    // translate by insets
    double itx = insets.left, ity = insets.top;
    g.translate(itx, ity);
    IRectGeom clip = g.getClipBounds();

    // repaint my viewport background
    BBrush bg = getViewportBackground();
    if (bg.isNull())
      g.setBrush(theme().getWindowBackground(this));
    else 
      g.setBrush(bg);
    g.fill(clip);

    // paint viewport
    if (clip.intersects(0, 0, viewport.width, viewport.height))
    {
      g.push();
      try
      {
        double vtx = viewport.x, vty = viewport.y;
        g.clip(0, 0, viewport.width, viewport.height);
        g.translate(-vtx, -vty);
        c.paint(g);
      }
      finally
      {
        g.pop();
      }
    }

    // if both scroll bars are showing then paint the corner
    if (hsb.getHeight() > 0 && vsb.getWidth() > 0)
    {
      g.setBrush(theme().getControlBackground());
      g.fillRect(hsb.getWidth(), vsb.getHeight(), vsb.getWidth(), hsb.getHeight());
    }

    // translate by insets and paint border
    g.translate(-itx, -ity);

    // paint scroll bars
    if (hsbShow)
      paintChild(g, hsb);
    if (vsbShow)
      paintChild(g, vsb);

    if ((getBorderPolicy() == BScrollBarPolicy.always) ||
      ((getBorderPolicy() == BScrollBarPolicy.asNeeded) && (vsbShow || hsbShow)))
    {
      theme().paintBorder(g, this);
    }
  }

////////////////////////////////////////////////////////////////
// Event Handling
////////////////////////////////////////////////////////////////

  public void doHscroll(BScrollEvent event)
  {
    viewport.x = event.getPosition();
    repaint();
  }

  public void doVscroll(BScrollEvent event)
  {
    viewport.y = event.getPosition();
    repaint();
  }

  public void scrollToVisible(RectGeom rect)
  {
    BScrollBar hsb = getHscrollBar();
    BScrollBar vsb = getVscrollBar();
    BWidget c = getContent();
    
    // do in layout if no viewport 
    if (viewport.width == 0)
    {
      scrollTo = rect;
      return;
    }

    // short circuit if it's already visible
    if (viewport.contains(rect))
      return;

    // compute (c)urrent and (d)esired rectangle
    double cx1 = viewport.x; double cx2 = cx1 + viewport.width;
    double cy1 = viewport.y; double cy2 = cy1 + viewport.height;
    double dx1 = rect.x; double dx2 = dx1 + rect.width;
    double dy1 = rect.y; double dy2 = dy1 + rect.height;

/*
System.out.println("scrollToVisible: " + rect);
System.out.println("  content:  " + c.getWidth() + "," + getHeight() + " needLayout: " + c.needsLayout());
System.out.println("  viewport: " + viewport);
System.out.println("  current:  " + cx1 + "," + cy1 + " - " + cx2 + "," + cy2);
System.out.println("  desired:  " + dx1 + "," + dy1 + " - " + dx2 + "," + dy2);
*/

    // compute necessary shifts
    if (cx2 < dx2) cx1 += dx2-cx2;
    if (cy2 < dy2) cy1 += dy2-cy2;
    if (cx1 > dx1) cx1 = dx1;
    if (cy1 > dy1) cy1 = dy1;

    // ensure we didn't shift out of bounds
    if (cx1+viewport.width > c.getWidth()) cx1 = c.getWidth() - viewport.width;
    if (cy1+viewport.height > c.getHeight()) cy1 = c.getHeight() - viewport.height;
    if (cx1 < 0) cx1 = 0;
    if (cy1 < 0) cy1 = 0;

    // do shift
    viewport.x = cx1;
    viewport.y = cy1;    
    hsb.resetPosition((int)cx1, BScrollEvent.OTHER);
    vsb.resetPosition((int)cy1, BScrollEvent.OTHER);
/*    
System.out.println("  result:   " + viewport.x + "," + viewport.y + " - " + (viewport.x+viewport.width) + "," + (viewport.y+viewport.height));
*/
  }
  
  /**
   * Given a point in the content's coordinate system, determine
   * if the viewport should be "pulsed" if the user is dragging
   * near one of the scroll pane edges.              
   *
   * @return true if pulse was performed on viewport.
   */
  public boolean pulseViewport(Point contentPt, double pulsePixels)
  {
    // get current mouse position in my coordinates
    Point pt = translateFromChild(getContent(), new Point(contentPt));
    double x = pt.x;
    double y = pt.y;        
    
    if (!contains(x, y)) return false;
    
    // get canvasPane size
    double edge   = 16;
    double left   = edge;
    double right  = getWidth() - edge - getVscrollBar().getWidth();
    double top    = edge;
    double bottom = getHeight() - edge - getHscrollBar().getHeight();
    
    // compute viewport delta
    double dx = 0, dy = 0;
    if (x < left) dx = -pulsePixels;
    else if (x > right) dx = pulsePixels;
    if (y < top) dy = -pulsePixels;
    else if (y > bottom) dy = pulsePixels;
    
    if (dx != 0 || dy != 0)
    {
      RectGeom viewport = getViewport();
      viewport.x += dx;
      viewport.y += dy;
      scrollToVisible(viewport);
      return true;
    }
    
    return false;
  }

////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////  

  public void mouseWheel(BMouseWheelEvent event)
  {
    BScrollBar sb = getVscrollBar();
    if (!sb.isVisible() || sb.getWidth() == 0) return;
    int wheelUnits = getWheelUnits();
    sb.scrollByUnits(event.getPreciseWheelRotation() * wheelUnits);
    event.consume();
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Get the number of units to scroll when the mouse wheel is used.
   */
  protected int getWheelUnits()
  {
    return 3;
  }

  /**
   * Get the widget's theme.
   */
  ScrollPaneTheme theme()
  {
    return Theme.scrollPane();
  }
  
  public String getStyleSelector() {
    return "pane scroll-pane";
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/scrollPane.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  protected RectGeom viewport = new RectGeom();
  
  private RectGeom scrollTo;
  private boolean   vsbShow = false;
  private boolean   hsbShow = false;
}
