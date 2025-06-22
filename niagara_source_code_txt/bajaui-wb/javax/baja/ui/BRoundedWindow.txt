/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.gx.BInsets;
import javax.baja.gx.IRectGeom;
import javax.baja.gx.Insets;
import javax.baja.gx.Point;
import javax.baja.gx.RectGeom;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.event.BWindowEvent;

import com.tridium.ui.ShellPeer;
import com.tridium.ui.UiEnv;
import com.tridium.ui.WindowPeer;

/**
 * 
 *
 * @author 		J. Spangler
 * @creation 	Apr 19, 2011
 * @version		1
 * @since			Niagara 3.7
 *
 */
@NiagaraType
@NiagaraAction(
  name = "handleMouseEvent",
  parameterType = "BMouseEvent",
  defaultValue = "new BMouseEvent()"
)
public class BRoundedWindow
    extends BWindow
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BRoundedWindow(2195786058)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "handleMouseEvent"

  /**
   * Slot for the {@code handleMouseEvent} action.
   * @see #handleMouseEvent(BMouseEvent parameter)
   */
  public static final Action handleMouseEvent = newAction(0, new BMouseEvent(), null);

  /**
   * Invoke the {@code handleMouseEvent} action.
   * @see #handleMouseEvent
   */
  public void handleMouseEvent(BMouseEvent parameter) { invoke(handleMouseEvent, parameter, null); }

  //endregion Action "handleMouseEvent"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRoundedWindow.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  
////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor
   * 
   * @param owner - {@link BWidget} owner of the window.
   * @param content - {@link BWidget} content of the new shaped window.
   */
  public BRoundedWindow(BWidget owner, BWidget content)
  {
    this(owner,content,DEFAULT_ARC_WIDTH,DEFAULT_ARC_WIDTH);
  }

  /**
   * Construct window with specified owner and content
   * 
   * @param owner - {@link BWidget} owner of the window.
   * @param content - {@link BWidget} content of the new shaped window.
   * @param arcWidth - Width of the arc to use when setting rounded edges.
   * @param arcHeight - Height of arc to use when setting rounded edges.
   */
  public BRoundedWindow(BWidget owner, BWidget content,float arcWidth, float arcHeight)
  {
    super(UiEnv.get().makeRoundedWindowPeer(owner,arcWidth,arcHeight));
    setContent(content);  
    this.arcWidth = arcWidth;
    this.arcHeight = arcHeight;

  }
  
  /**
   * Constructor 
   * 
   * @param owner - {@link BWidget} owner of the window.
   */
  public BRoundedWindow(BWidget owner)
  {
    super(UiEnv.get().makeRoundedWindowPeer(owner, DEFAULT_ARC_WIDTH,DEFAULT_ARC_WIDTH));
  }

  /**
   * Constructor 
   * 
   * @param owner - {@link BWidget} owner of the window.
   * @param arcWidth - width of the arc to use when setting rounded edges.
   * @param arcHeight - height of the arc to use when setting rounded edges.
   */
  public BRoundedWindow(BWidget owner, float arcWidth, float arcHeight)
  {
    super(UiEnv.get().makeRoundedWindowPeer(owner, arcWidth, arcHeight));
    this.arcWidth = arcWidth;
    this.arcHeight = arcHeight;
  }

  
  /**
   * Default constructor 
   */
  public BRoundedWindow()
  {
    super(UiEnv.get().makeRoundedWindowPeer(null,DEFAULT_ARC_WIDTH,DEFAULT_ARC_HEIGHT));
  }
  
  BRoundedWindow(WindowPeer peer)
  {
    super(peer);
  }
  
////////////////////////////////////////////////////////////////
//  Shaped Window
////////////////////////////////////////////////////////////////

 /**
  * Get the owner passed to the constructor which is
  * usually a widget in the dialog's parent window.
  */
  public BWidget getOwner()
  {
    return owner;
  }
  
  /**
   * Returns the width of the arc used when setting the rounded edges.
   * 
   * @return float 
   */
  public float getArcWidth()
  {
    return arcWidth;
  }
  
  /**
   * Returns the height of the arc used when setting the rounded corners.
   *  
   * @return float
   */
  public float getArcHeight()
  {
    return arcHeight;
  }

  
  /**
   * This method is called when escape is pressed
   * in the window.  The default implementation
   * calls <code>windowClosing(null)</code>.
   */
  public void handleEscape()
  {
    windowClosing(null);
  }

  /**
   * Set the window bounds to be centered on 
   * its owner window.
   */
  public void setBoundsCenteredOnOwner()
  {
    try
    {                         
      ShellPeer peer = getOwner().getShell().getShellPeer();
      setBoundsCenteredOn(peer.getScreenBounds());
    }
    catch(RuntimeException e)
    {                                   
      setBoundsCenteredOnScreen();
    }
  }
  
  /*
   * (non-Javadoc)
   * @see javax.baja.ui.BWindow#setBoundsCenteredOn(javax.baja.gx.IRectGeom)
   */
  public void setBoundsCenteredOn(IRectGeom rect)
  {                              
    // get window insets
    Insets insets = UiEnv.get().getWindowInsets(this);
    
    // compute preferred size
    computePreferredSize();
    double w = getPreferredWidth() + insets.left + insets.right;
    double h = getPreferredHeight() + insets.top + insets.bottom;
    double x = rect.x() + (rect.width()-w)/2;
    double y = rect.y() + (rect.height()-h)/2;

    // ensure on screen  
    IRectGeom screen = UiEnv.get().getScreenBounds(this);
    double sx = screen.x();
    double sy = screen.y();
    double sw = screen.width();
    double sh = screen.height();  

    // if bigger than screen, then clip to screen size
    if (w > sw) w = sw;
    if (h > sh) h = sh;

    // if we are off the edge, then we
    // need to reposition ourselves 
    if (x+w > sx+sw) x = (sx+sw)-w;
    if (y+h > sy+sh) y = sh-h;    
    if (x < 0) x = 0;
    if (y < sy) y = sy;

    // set the bounds
    setScreenBounds(x, y, w, h);
  }
  
////////////////////////////////////////////////////////////////
//  Events
////////////////////////////////////////////////////////////////
  
  public void windowClosing(BWindowEvent event)
  {
    close();
  }
  
  public void mouseDragged(BMouseEvent e)
  {
    if( null == pressed)return;
    Point absMousePos = this.translateToScreen(new Point(e.getX(),e.getY()));
    IRectGeom rect = new RectGeom(absMousePos.x - pressed.x, absMousePos.y - pressed.y, getWidth(),getHeight());
    setScreenBounds(rect.x(),rect.y(),rect.width(),rect.height());
  }
  
  public void mousePressed(BMouseEvent e)
  {
    double width  = getWidth()  - dragInsets.left - dragInsets.right;
    double height = getHeight() - dragInsets.top - dragInsets.bottom;
    RectGeom rect = new RectGeom(dragInsets.left, dragInsets.top,width,height);

    if( rect.contains(e.getX(),e.getY()))
    {
      pressed = new Point(e.getX(),e.getY());
      e.getWidget().setMouseCursor(MouseCursor.grabHand);
    }
  }
  
  public void mouseReleased(BMouseEvent event)
  {
    event.getWidget().setMouseCursor(MouseCursor.normal);
  }
  
  
////////////////////////////////////////////////////////////////
//  actions
////////////////////////////////////////////////////////////////
  
  public void doHandleMouseEvent(BMouseEvent event)
  {
    //distribute to our event system based on event ID
    fireMouseEvent(event);
  }
  
////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  protected static final float DEFAULT_ARC_WIDTH = 10f;
  protected static final float DEFAULT_ARC_HEIGHT = 10f;

////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  private BWidget owner;  
  protected float arcWidth = DEFAULT_ARC_WIDTH;
  protected float arcHeight = DEFAULT_ARC_HEIGHT;
  
  private Point pressed;
  private BInsets dragInsets = BInsets.make(5, 5, 5, 5);
}
