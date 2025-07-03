/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.agent.AgentList;
import javax.baja.gx.Graphics;
import javax.baja.gx.IGeom;
import javax.baja.gx.Point;
import javax.baja.gx.RectGeom;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.util.Array;
import javax.baja.sys.Action;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.ui.event.BFocusEvent;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.event.BMouseWheelEvent;
import javax.baja.ui.style.IStylable;
import javax.baja.util.BTypeSpec;

import com.tridium.sys.schema.Fw;
import com.tridium.ui.ShellManager;
import com.tridium.ui.UiEnv;

/**
 * BWidget the base class for all visual
 * components which are run on the client side.
 *
 * @author    Brian Frank on 19 Nov 00
 * @version   $Revision: 165$ $Date: 6/24/11 11:19:40 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 When the visible property is true the widget is
 rendered to its graphics context and can accept
 user input from the mouse and keyboard.  When visible
 if false the widget is hidden and will not receive
 user input.
 */
@NiagaraProperty(
  name = "visible",
  type = "boolean",
  defaultValue = "true"
)
/*
 All widgets support an enabled field which allows
 the to enable and disable user interaction.
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 Layout stores absolute position.
 */
@NiagaraProperty(
  name = "layout",
  type = "BLayout",
  defaultValue = "BLayout.DEFAULT"
)
/*
 Enables classes for theme styling.
 */
@NiagaraProperty(
  name = "styleClasses",
  type = "String",
  defaultValue = "",
  flags = Flags.HIDDEN
)
/*
 Enables IDs for theme styling.
 */
@NiagaraProperty(
  name = "styleId",
  type = "String",
  defaultValue = "",
  flags = Flags.HIDDEN
)
/*
 This topic is fired whenever keyboard input
 occurs on this component. In order to receive
 keyboard input, the widget must currently have
 focus.
 */
@NiagaraTopic(
  name = "keyEvent",
  eventType = "BKeyEvent"
)
/*
 The mouseEvent topic is fired when the widget
 received mouse input.
 */
@NiagaraTopic(
  name = "mouseEvent",
  eventType = "BMouseEvent"
)
/*
 The focusEvent topic is fired when the widget
 gains or loses focus.
 */
@NiagaraTopic(
  name = "focusEvent",
  eventType = "BFocusEvent"
)
@NoSlotomatic //custom fireKeyEvent, fireMouseEvent, fireFocusEvent implementations
public class BWidget
  extends BComponent implements IStylable
{
  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BWidget(2966182647)1.0$ @*/
/* Generated Thu Nov 18 14:02:04 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Property "visible"

  /**
   * Slot for the {@code visible} property.
   * When the visible property is true the widget is
   * rendered to its graphics context and can accept
   * user input from the mouse and keyboard.  When visible
   * if false the widget is hidden and will not receive
   * user input.
   * @see #getVisible
   * @see #setVisible
   */
  public static final Property visible = newProperty(0, true, null);

  /**
   * Get the {@code visible} property.
   * When the visible property is true the widget is
   * rendered to its graphics context and can accept
   * user input from the mouse and keyboard.  When visible
   * if false the widget is hidden and will not receive
   * user input.
   * @see #visible
   */
  public boolean getVisible() { return getBoolean(visible); }

  /**
   * Set the {@code visible} property.
   * When the visible property is true the widget is
   * rendered to its graphics context and can accept
   * user input from the mouse and keyboard.  When visible
   * if false the widget is hidden and will not receive
   * user input.
   * @see #visible
   */
  public void setVisible(boolean v) { setBoolean(visible, v, null); }

  //endregion Property "visible"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * All widgets support an enabled field which allows
   * the to enable and disable user interaction.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * All widgets support an enabled field which allows
   * the to enable and disable user interaction.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * All widgets support an enabled field which allows
   * the to enable and disable user interaction.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "layout"

  /**
   * Slot for the {@code layout} property.
   * Layout stores absolute position.
   * @see #getLayout
   * @see #setLayout
   */
  public static final Property layout = newProperty(0, BLayout.DEFAULT, null);

  /**
   * Get the {@code layout} property.
   * Layout stores absolute position.
   * @see #layout
   */
  public BLayout getLayout() { return (BLayout)get(layout); }

  /**
   * Set the {@code layout} property.
   * Layout stores absolute position.
   * @see #layout
   */
  public void setLayout(BLayout v) { set(layout, v, null); }

  //endregion Property "layout"

  //region Property "styleClasses"

  /**
   * Slot for the {@code styleClasses} property.
   * Enables classes for theme styling.
   * @see #getStyleClasses
   * @see #setStyleClasses
   */
  public static final Property styleClasses = newProperty(Flags.HIDDEN, "", null);

  /**
   * Get the {@code styleClasses} property.
   * Enables classes for theme styling.
   * @see #styleClasses
   */
  public String getStyleClasses() { return getString(styleClasses); }

  /**
   * Set the {@code styleClasses} property.
   * Enables classes for theme styling.
   * @see #styleClasses
   */
  public void setStyleClasses(String v) { setString(styleClasses, v, null); }

  //endregion Property "styleClasses"

  //region Property "styleId"

  /**
   * Slot for the {@code styleId} property.
   * Enables IDs for theme styling.
   * @see #getStyleId
   * @see #setStyleId
   */
  public static final Property styleId = newProperty(Flags.HIDDEN, "", null);

  /**
   * Get the {@code styleId} property.
   * Enables IDs for theme styling.
   * @see #styleId
   */
  public String getStyleId() { return getString(styleId); }

  /**
   * Set the {@code styleId} property.
   * Enables IDs for theme styling.
   * @see #styleId
   */
  public void setStyleId(String v) { setString(styleId, v, null); }

  //endregion Property "styleId"

  //region Topic "keyEvent"

  /**
   * Slot for the {@code keyEvent} topic.
   * This topic is fired whenever keyboard input
   * occurs on this component. In order to receive
   * keyboard input, the widget must currently have
   * focus.
   * @see #fireKeyEvent
   */
  public static final Topic keyEvent = newTopic(0, null);

  //endregion Topic "keyEvent"

  //region Topic "mouseEvent"

  /**
   * Slot for the {@code mouseEvent} topic.
   * The mouseEvent topic is fired when the widget
   * received mouse input.
   * @see #fireMouseEvent
   */
  public static final Topic mouseEvent = newTopic(0, null);

  //endregion Topic "mouseEvent"

  //region Topic "focusEvent"

  /**
   * Slot for the {@code focusEvent} topic.
   * The focusEvent topic is fired when the widget
   * gains or loses focus.
   * @see #fireFocusEvent
   */
  public static final Topic focusEvent = newTopic(0, null);

  //endregion Topic "focusEvent"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWidget.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a widget.
   */
  public BWidget()
  {
  }

////////////////////////////////////////////////////////////////
// Model
////////////////////////////////////////////////////////////////

  /**
   * This is a BWidget!
   */
  public final boolean isWidget()
  {
    return true;
  }

  /**
   * BWidget is not null.
   */
  public boolean isNull()
  {
    return false;
  }

  /**
   * Get the parent BWidgetShell this widget is 
   * rooted in, or return null the widget is not
   * inside a BWidgetShell.
   */
  public final BWidgetShell getShell()
  {
    if (shellManager == null) return null;
    else return (BWidgetShell)shellManager.getShell();
  }                 

  public final ShellManager getShellManager()
  {
    return shellManager;
  }
  
  /**
   * Return whether this widget is currently
   * being edited or not.
   * @since     Niagara 3.3
   */
  public final boolean isDesignTime()
  {
    return shellManager != null && shellManager.isDesignTime();
  }
      
  /**
   * Get the BWidgetApplication singleton.
   */
  public static BWidgetApplication getApplication()
  {
    return UiEnv.app;
  }

  /**
   * Get this component's parent as a BWidget.  If
   * this component doesn't have a parent, or it has
   * a non-BWidget parent then return null.
   */
  public final BWidget getParentWidget()
  {
    BComplex parent = getParent();
    if (parent instanceof BWidget) return (BWidget)parent;
    else return null;
  }

  /**
   * Get the list of child widgets.
   */
  public final BWidget[] getChildWidgets()
  {
     return getChildren(BWidget.class);
  }
  
  /**
   * Get the appropriate selector for an NSS file for this widget type.
   * BButton would override to return "button", while BRadioButton would
   * return "button radio", for instance.
   * @return NSS selector
   */
  public String getStyleSelector() { return ""; }
  

////////////////////////////////////////////////////////////////
// Mouse Cursor
////////////////////////////////////////////////////////////////

  /**
   * Get the mouse cursor for the widget.
   */
  public MouseCursor getMouseCursor()
  {                    
    return mouseCursor;
  }

  /**
   * Set the mouse cursor for this widget.  If null is
   * passed then use {@code MouseCursor.normal}.
   * Note that the busy state trumps local widget 
   * cursor changes.  Return the old MouseCursor.
   */
  public MouseCursor setMouseCursor(MouseCursor c)
  { 
    // null -> normal
    if (c == null) c = MouseCursor.normal;
    
    // set field and save old into temp variable
    MouseCursor old = mouseCursor;
    mouseCursor = c;
    
    // let shell manager know
    if (shellManager != null) 
      shellManager.updateMouseCursor();
    
    return old;
  }
  
  /**
   * Enter a section of code which could potentially
   * require a lengthy amount of time.  This changes
   * the shell's cursor to a busy cursor, and may also
   * provide additional visual feedback to the user
   * that the shell is "working".  It is critical that
   * the caller of this method also call exitBusy() the
   * same number of times that enterBusy was called.
   */
  public void enterBusy()
  {
    if (shellManager != null) 
    {
      busy=true;
      shellManager.enterBusy(this);
    }
  }

  /**
   * Exit a busy section of code.  This call should be
   * called exactly once for every call to enterBusy.
   *
   * NOTE: if there is a chance that this widget has
   * been unmounted from the shell since the call to
   * enterBusy(), then use the shell for enterBusy()
   * and exitBusy(), not the widget.
   */
  public void exitBusy()
  {
    busy=false;
    if (shellManager != null) 
      shellManager.exitBusy(this);    
  }

////////////////////////////////////////////////////////////////
// Framework Callbacks
////////////////////////////////////////////////////////////////
  
  /**
   * Default implementation of the property 
   * change callback is to call repaint.
   */
  public void changed(Property prop, Context context)
  {
    repaint();
  }

  /**
   * Framework support access; this method should
   * never be used by developers.
   */
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {   
      case Fw.CHANGED:      fwChanged((Property)a, (Context)b); break;
      case Fw.REORDERED:    relayout(); break;
      case Fw.PARENTED:     fwParented((Property)a, (BValue)b); break;
      case Fw.UNPARENTED:   fwUnparented((Property)a, (BValue)b); break;
      case Fw.INVOKED:      fireInvokedOnBindings((Action)a, (BValue)b, (Context)c); break;
      case Fw.FIRED:        fireFiredOnBindings((Topic)a, (BValue)b, (Context)c); break;
      case Fw.GET_OVERRIDE: return getOverride((Property)a);
      case Fw.IS_WIDGET:    return this;
    }
    return super.fw(x, a, b, c, d);
  }  

  /**
   * Framework callback for changed
   */
  void fwChanged(Property prop, Context cx)
  {                                 
    if (prop == enabled && shellManager != null) 
      shellManager.checkMouseOver();
      
    fireChangedOnBindings(prop, cx);
  }
  
  /**
   * Framework callback for childParented
   */
  void fwParented(Property prop, BValue newChild)
  {
    if (this.shellManager != null && newChild instanceof BWidget)
    {
      // recursively initialize the shell manager
      BWidget child = (BWidget)newChild;
      child.initShell(this.shellManager);      
    }                     
    
    if (newChild instanceof BBinding)
      bindingAdd((BBinding)newChild);
  }

  /**
   * Framework callback for childUnparented
   */
  void fwUnparented(Property prop, BValue oldChild)
  {
    if (oldChild instanceof BWidget) 
    {
      // recursively cleanup the shell manager
      BWidget child = (BWidget)oldChild;
      child.cleanupShell();
      child.needsLayout = false;
      
      // relayout myself
      relayout();
    } 
    
    if (oldChild instanceof BBinding)
      bindingRemove((BBinding)oldChild);
  }

  /**
   * Recursively initialize the shell manager.
   */
  void initShell(ShellManager shellManager)
  {
    if (this.shellManager == shellManager) throw new IllegalStateException("Shell already initialized");
    
    
    // cache shell manager reference
    this.shellManager = shellManager;
    
    // force fresh layout to get onto layout queue
    needsLayout = false;
    if(shellManager.forceFreshLayout())
      relayout();
    
    // recurse
    BWidget[] kids = getChildWidgets();
    for (BWidget kid : kids) kid.initShell(shellManager);
  }

  /**
   * Recursively cleanup the shell.
   */
  void cleanupShell()
  {    
    if(shellManager != null && busy)
      exitBusy();
    
    busy = false;
    shellManager = null;
    BWidget[] kids = getChildWidgets();
    for (BWidget kid : kids) kid.cleanupShell();
  }

////////////////////////////////////////////////////////////////
// Bindings
////////////////////////////////////////////////////////////////
  
  /**
   * Get an array of child bindings or return an empty
   * array if no bindings are installed on this widget.
   */                         
  public final BBinding[] getBindings()
  {  
    if (bindings.length == 0) return noBindings;
    return bindings.clone();
  }                           
  
  /**
   * Return true if there are one or more bindings on this widget.
   */
  public final boolean hasBindings()
  {                                                               
    return bindings.length > 0;
  }                
  
  /**
   * This is callback is invoked when a binding is added, 
   * removed, or its target is changed.
   */
  public void bindingsChanged()
  {
  }
  
  /**
   * Called when parenting a binding property.
   */
  private void bindingAdd(BBinding b)
  {                  
    // add to binding array
    BBinding[] temp = new BBinding[bindings.length+1];
    System.arraycopy(bindings, 0, temp, 0, bindings.length);
    temp[bindings.length] = b;
    bindings = temp;        
    
    try { bindingsChanged(); } catch(Throwable e) { e.printStackTrace(); }
  }

  /**
   * Called when unparenting a binding property.
   */
  private void bindingRemove(BBinding b)
  {
    // remove from binding array
    if (bindings.length == 1 && bindings[0] == b) 
    {
      bindings = noBindings;
    }
    else 
    {
      for(int i=0; i<bindings.length; ++i)
        if (bindings[i] == b)
        {
          BBinding[] temp = new BBinding[bindings.length-1];
          System.arraycopy(bindings, 0, temp, 0, i);
          if (i < bindings.length)
            System.arraycopy(bindings, i+1, temp, i, bindings.length-i-1);
          bindings = temp;
          break;
        }
    }
    
    try { bindingsChanged(); } catch(Throwable e) { e.printStackTrace(); }
  }             
  
  /**
   * Return true if the specified property is overridden
   * by a child binding via the BBinding.getOnWidget() method.
   */
  public boolean isOverriddenByBinding(Property prop)
  {
    return getOverride(prop) != null;
  }
  
  /**
   * Called when getting a widget property value.  Give
   * each binding a chance to perform an override.
   */
  private BValue getOverride(Property prop)
  {                 
    for (BBinding binding : bindings)
    {
      if (binding.isBound())
      {
        BValue value = binding.getOnWidget(prop);
        if (value != null) return value;
      }
    }                                 
    return null;
  }

  /**
   * Invoke BBinding.changedOnWidget callback
   */
  private void fireChangedOnBindings(Property prop, Context cx)
  {
    for (BBinding binding : bindings)
    {
      try
      {
        binding.changedOnWidget(prop, cx);
      }
      catch (Throwable e)
      {
        e.printStackTrace();
      }
    }                    
  }

  /**
   * Invoke BBinding.invokedOnWidget callback
   */
  private void fireInvokedOnBindings(Action action, BValue value, Context cx)
  {              
    for (BBinding binding : bindings)
    {
      try
      {
        boolean consumed = binding.invokedOnWidget(action, value, cx);
        if (consumed) return;
      }
      catch (Throwable e)
      {
        e.printStackTrace();
      }
    }                    
  }

  /**
   * Invoke BBinding.firedOnWidget callback
   */
  private void fireFiredOnBindings(Topic topic, BValue value, Context cx)
  {                              
    for (BBinding binding : bindings)
    {
      try
      {
        boolean consumed = binding.firedOnWidget(topic, value, cx);
        if (consumed) return;
      }
      catch (Throwable e)
      {
        e.printStackTrace();
      }
    }                    
  }

////////////////////////////////////////////////////////////////
// Translations
////////////////////////////////////////////////////////////////

  /**
   * Get the immediate child of this widget which
   * is located at the specified coordinates relative
   * to this widget's coordinate space.  This method
   * automatically excludes all children widgets which
   * are not visible regardless of their current bounds.
   *
   * @param pt point in this widget's coordinate
   *    system.  The point is not guaranteed to
   *    remain immutable.
   * @return null if no immediate children
   *    contain the specified point.
   */
  public BWidget childAt(Point pt)
  {
    // start at top of z-order and work down
    BWidget[] children = getChildWidgets();
    for(int i=children.length-1; i>=0; --i)
    {
      // skip widgets which aren't visible
      BWidget child = children[i];
      if (!child.isVisible()) continue;

      // does the child contain this widget
      if (child.contains(pt.x-child.x, pt.y-child.y))
        return child;
    }
    return null;
  }

  /**
   * Recursively get the deepest descendent of
   * of this widget which contains the given
   * point relative to this widget's coordinate
   * space.  Unlike childAt(), this method
   * recursively searches nested widgets to find
   * the deepest one.
   *
   * @param pt point in this widget's coordinate
   *    system.  The point is not guaranteed to
   *    remain immutable.
   * @return null if no descendents contain
   *    the specified point.
   */
  public final BWidget descendentAt(Point pt)
  {
    BWidget child = childAt(pt);
    if (child != null)
    {
      translateToChild(child, pt);
      BWidget d = child.descendentAt(pt);
      return (d != null) ? d : child;
    }
    return null;
  }

  /**
   * Translate a point from this widget's coordinate
   * system to the given child's coordinate system.
   *
   * @return point argument
   */
  public Point translateToChild(BWidget child, Point pt)
  {
    pt.translate( -child.x , -child.y);
    return pt;
  }

  /**
   * Translate a point from the specified child
   * widget's coordinate space to this widget's
   * coordinate system.
   * @return point argument
   */
  public Point translateFromChild(BWidget child, Point pt)
  {
    double x = pt.x, y = pt.y;
    translateToChild(child, pt);
    pt.x = x - (pt.x - x);
    pt.y = y - (pt.y - y);
    return pt;
  }

  /**
   * Given a point relative to ancestor's
   * coordinate space, translate it to this
   * widget's coordinate space.
   * @return point argument.
   */
  public final Point translateFromAncestor(BWidget ancestor, Point pt)
  {    
    Array<BWidget> widgets = new Array<>(BWidget.class);
    
    if (ancestor != this)
    {
      // Get a list of all the parent Widgets.
      BWidget w = this;
      while(w != null)
      {
        widgets.add(w);
        if (w == ancestor) break;
        w = w.getParentWidget();
      }
    }
        
    // In the correct order, iterate through the Widgets performing
    // the translation.
    BWidget w = null;   
    BWidget p = null;
    
    for (int i = widgets.size() - 1; i >= 0 ; --i)
    {
      w = widgets.get(i);
      if (w != null && p != null) p.translateToChild(w, pt);
      p = w;
    }
    
    return pt;
  }
  
  /**
   * Given a point relative to this widget's
   * coordinate space, translate it to the specified
   * ancestor's coordinate space.
   * @return point argument
   */
  public final Point translateToAncestor(BWidget ancestor, Point pt)
  {
    BWidget p = getParentWidget(), w = this;
    while(w != ancestor)
    {
      p.translateFromChild(w, pt);
      w = p;
      p = p.getParentWidget();
    }
    return pt;
  }

  /**
   * Translate the point relative to this widget's
   * coordinate space to the screen coordinate space.
   */
  public final Point translateToScreen(Point pt)
  {
    return shellManager.translateToScreen(this, pt);
  }

////////////////////////////////////////////////////////////////
// Bounds
////////////////////////////////////////////////////////////////

  /** Get the view's current x location in its parent. */
  public final double getX() { return x; }
  /** Get the view's current y location in its parent. */
  public final double getY() { return y; }
  /** Get the view's current width in pixels. */
  public final double getWidth() { return width; }
  /** Get the view's current height in pixels. */
  public final double getHeight() { return height; }
  /** Get the view's preferred width in pixels. */
  public final double getPreferredWidth() { return prefWidth; }
  /** Get the view's preferred height in pixels. */
  public final double getPreferredHeight() { return prefHeight; }

  /**
   * Does this widget contain specified point
   * which is relative to this widget's coordinate
   * space.
   */
  public boolean contains(double x, double y)
  {
    return(x >= 0) && (x < width) && (y >= 0) && (y < height);
  }

  /**
   * Set the view's location in its parent coordinate space.
   */
  public void setLocation(double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  /**
   * Set the view's size.
   */
  public void setSize(double width, double height)
  {
    if (this.width != width || this.height != height)
    {
      this.width = width;
      this.height = height;
      relayout();
    }
  }

  /**
   * Set the view's bounds which includes both its
   * location and size.
   */
  public void setBounds(double x, double y, double width, double height)
  {
    setLocation(x, y);
    setSize(width, height);
  }

  /**
   * Set the view's preferred size.
   */
  public final void setPreferredSize(double width, double height)
  {
    this.prefWidth = width;
    this.prefHeight = height;
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////
    
  /**
   * Get the needsLayout flag to determine if
   * layout needs to be called before the view
   * painted.  
   */
  public final boolean needsLayout()
  {
    return needsLayout;
  }

  /**
   * Relayout is invoked to request that this widget, and
   * potentially its ancestors have their position and size
   * recalculated.  This method sets the needsLayout flag to
   * true and calls parent.childCalledRelayout().  When this
   * method returns nothing has actually been laid out yet,
   * rather the actual layout will occur on the next paint.
   */
  public final void relayout()
  {
    relayout(false);
  }

  /**
   * This method is similar to {@code relayout()}, however
   * it blocks until the actual layout is finished.  It is useful
   * if you immediately need to know position and size information
   * and you don't want to wait for the next paint.  However it should
   * be used with caution since it can quickly degrade performance.
   */
  public final void relayoutSync()
  {
    relayout(true);
  }
  
  /**
   * Implementation of relayout() and relayoutSync().
   */ 
  private void relayout(boolean sync)
  {
    if (!needsLayout || sync)
    {
      needsLayout = true;
      
      BWidget parent = getParentWidget();
      if (parent != null) parent.childCalledRelayout(this);
      
      if (shellManager != null)
        shellManager.relayout(this, sync);
    }
    else if ((needsLayout) && (shellManager != null))
    {
      if (shellManager.enqueueRelayout(this))
      {
        BWidget parent = getParentWidget();
        if (parent != null) parent.childCalledRelayout(this);
      
        shellManager.relayout(this, sync);
      }
    }
  }
  
  /**
   * This method is invoked when a child of this widget makes
   * a call to relayout().  It gives this parent widget a chance 
   * to decide if a relayout on the child would require a relayout
   * on this widget.  In general a relayout on the child means
   * that its preferred size has changed.  If this widget's
   * preferred size is based on the child's preferred size, then
   * this method should call relayout on itself.  The default
   * implementation is to call relayout.
   */
  public void childCalledRelayout(BWidget child)
  {
    this.relayout();
  }
  
  /**
   * Layout is called on this component whenever the widget is 
   * under going a layout.  It should never be called directly, 
   * but rather subclasses should override the doLayout() method.
   */
  public final void layout()
  {
    if (needsLayout)
    {
      if (width > 0 && height > 0)
      {
        // get my children
        BWidget[] children = getChildWidgets();

        // give the widget a chance to
        // set bounds on its children
        try
        {
          doLayout(children);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }

        // layout all the children
        for (BWidget child : children) child.layout();
      }

      // I should be laid out now
      needsLayout = false;
    }
  }

  /**
   * The doLayout method is the hook to perform
   * layout on the widget's children as well as
   * compute information needed to paint the widget.
   * If the widget contains children, it should call
   * setBounds() on each child to position the child
   * correctly.
   */
  public void doLayout(BWidget[] children)
  {
  }

  /**
   * Calculate this components preferred size.
   * This method should be overridden to calculate
   * the desired size using the specified graphics
   * context, then cache it by calling setPreferredSize().
   */
  public void computePreferredSize()
  {
    setPreferredSize(10,10);
  }

  /**
   * This method forwards the scroll to visible message
   * to its parent.  If the widget is contained by a
   * BScrollBar ancestor, then this rect specified relative
   * to this widget's coordinate space will be made visible.
   */
  public void scrollToVisible(RectGeom rect)
  {
    BWidget parent = getParentWidget();
    if (parent != null)
    {
      rect.x += x; rect.y +=y;
      parent.scrollToVisible(rect);
    }
  }

////////////////////////////////////////////////////////////////
// Paint
////////////////////////////////////////////////////////////////

  /**
   * This is a convenience method for getVisible().
   */
  public final boolean isVisible()
  {
    return getBoolean(visible);
  }

  /**
   * This is a convenience method for getEnabled().
   */
  public final boolean isEnabled()
  {
    return getBoolean(enabled);
  }

  /**
   * Repaint this component which will invoke
   * the paint method in the near future.
   */
  public void repaint()
  {
    if (shellManager != null)
      shellManager.repaint(this, 0, 0, (int)width, (int)height);
  }

  /**
   * Repaint this component which will invoke
   * the paint method in the near future.
   */
  public void repaint(double x, double y, double width, double height)
  {
    if (shellManager != null)
      shellManager.repaint(this, (int)x, (int)y, (int)width, (int)height);
  }

  /**
   * Paint this component to the specified Graphics.
   */
  public void paint(Graphics g)
  {
    paintChildren(g);
  }

  /**
   * Paint the children views of this BWidget.
   */
  public void paintChildren(Graphics g)
  {
    IGeom clip = g.getClip();

    // walk through all my BWidget children
    for (BWidget child : getChildWidgets())
    {
      if (!child.isVisible()) continue;

      // check if the clip intersects child's bounds
      if (!clip.intersects(child.x, child.y, child.width, child.height))
        continue;

      // Push copy of current grpahics state so that each
      // widget gets a clean slate without any left over 
      // state from previous widget paint code
      g.push();
      try
      {
        // we now need to paint this child
        g.clip(child.x, child.y, child.width, child.height);
        g.translate(child.x, child.y);
        child.paint(g);
      }
      catch (Throwable e)
      {
        e.printStackTrace();
      }
      finally
      {
        g.pop();
      }
    }
  }

  /**
   * Paint a child widget.
   */
  public void paintChild(Graphics g, BWidget child)
  {
    if (!child.isVisible()) return;
    if (g.getClip().intersects(child.x, child.y, child.width, child.height))
    {
      // Push copy of current grpahics state so that each
      // widget gets a clean slate without any left over 
      // state from previous widget paint code
      g.push();
      try
      {
        // paint the child
        g.clip(child.x, child.y, child.width, child.height);
        g.translate(child.x, child.y);
        child.paint(g);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      finally
      {
        g.pop();
      }
    }
  }       
  
  /**
   * Animate is the callback invoked at a standard 
   * frame rate of 10/sec (once every 100ms).  The
   * default implementation paints the children widgets.
   */
  public void animate()
  {
    animateChildren();
  }  
  
  /**
   * Call animate on all visible child widgets.
   */
  public void animateChildren()
  {
    for (BWidget child : getChildWidgets())
      if (child.isVisible())
        child.animate();
  }

////////////////////////////////////////////////////////////////
// Focus
////////////////////////////////////////////////////////////////

  /**
   * Return true if this widget can gain focus
   * by traversing focus with the Tab or Shift+Tab
   * keys.
   */
  public boolean isFocusTraversable()
  {
    return false;
  }

  /**
   * Does this widget currently have the focus.
   */
  public final boolean hasFocus()
  {
    return shellManager != null && shellManager.hasFocus(this);
  }

  /**
   * Request the focus for this component.
   */
  public void requestFocus()
  {
    if (shellManager != null)
      shellManager.requestFocus(this);
  }

  /**
   * Return true if this widget should receive input
   * events.  By default this is true and all keyboard
   * or mouse input events are routed directly to this
   * widget.  If this method returns false then input
   * events targeted to this widget will be directed to
   * to the parent widget.
   */
  public boolean receiveInputEvents()
  {
    return true;
  }

  /**
   * Fire an event for the {@code focusEvent} topic.
   * This method first routes the event to one the widget
   * focusX(BFocusEvent) callbacks, then fires it for any
   * potential links.
   *
   * @see javax.baja.ui.BWidget#focusEvent
   */
  public final void fireFocusEvent(BFocusEvent event)
  {
    switch(event.getId())
    {
      case BFocusEvent.FOCUS_LOST:   focusLost(event); break;
      case BFocusEvent.FOCUS_GAINED: focusGained(event); break;
      default: throw new IllegalStateException();
    }
    fire(focusEvent, event, null);
  }

  /**
   * This callback is invoked when the fireFocusEvent() is
   * invoked with a BFocusEvent with an id of FOCUS_GAINED.
   * The default implementation is to call scrollToVisible()
   * and repaint().
   */
  public void focusGained(BFocusEvent event)
  {
    scrollToVisible(new RectGeom(0, 0, width, height));
    repaint();
  }

  /**
   * This callback is invoked when the fireFocusEvent() is
   * invoked with a BFocusEvent with an id of FOCUS_LOST.
   * The default implementation is to call repaint().
   */
  public void focusLost(BFocusEvent event)
  {
    repaint();
  }

////////////////////////////////////////////////////////////////
// Mouse
////////////////////////////////////////////////////////////////

  /**
   * Fire an event for the {@code mouseEvent} topic.
   * This method first routes the event to one the widget
   * mouseX(BMouseEvent) callbacks, then fires it for any
   * potential links.
   *
   * @param event the mouse event that is being fired
   * @see javax.baja.ui.BWidget#mouseEvent
   */
  public void fireMouseEvent(BMouseEvent event)
  {
    switch(event.getId())
    {
      case BMouseEvent.MOUSE_PRESSED:  mousePressed(event); break;
      case BMouseEvent.MOUSE_RELEASED: mouseReleased(event); break;
      case BMouseEvent.MOUSE_ENTERED:  mouseEntered(event); break;
      case BMouseEvent.MOUSE_EXITED:   mouseExited(event); break;
      case BMouseEvent.MOUSE_MOVED:    mouseMoved(event); break;
      case BMouseEvent.MOUSE_DRAGGED:  mouseDragged(event); break;
      case BMouseEvent.MOUSE_WHEEL:    mouseWheel((BMouseWheelEvent)event); break;
      case BMouseEvent.MOUSE_PULSED:   mousePulsed(event); break;
      case BMouseEvent.MOUSE_DRAG_STARTED: mouseDragStarted(event); break;
      case BMouseEvent.MOUSE_HOVER:    mouseHover(event); break;
      default: throw new IllegalStateException();
    }
    fire(mouseEvent, event, null);
  }

  /**
   * This callback is invoked when the fireMouseEvent() is
   * invoked with a BMouseEvent with an id of MOUSE_PRESSED.
   */
  public void mousePressed(BMouseEvent event) {}

  /**
   * This callback is invoked when the fireMouseEvent() is
   * invoked with a BMouseEvent with an id of MOUSE_RELEASED.
   */
  public void mouseReleased(BMouseEvent event) {}

  /**
   * This callback is invoked when the fireMouseEvent() is
   * invoked with a BMouseEvent with an id of MOUSE_ENTERED.
   */
  public void mouseEntered(BMouseEvent event) {}

  /**
   * This callback is invoked when the fireMouseEvent() is
   * invoked with a BMouseEvent with an id of MOUSE_EXITED.
   */
  public void mouseExited(BMouseEvent event) {}

  /**
   * This callback is invoked when the fireMouseMotionEvent()
   * is invoked with a BMouseEvent with an id of MOUSE_MOVED.
   */
  public void mouseMoved(BMouseEvent event) {}

  /**
   * This callback is invoked when the fireMouseMotionEvent()
   * is invoked with a BMouseEvent with an id of MOUSE_DRAGGED.
   */
  public void mouseDragged(BMouseEvent event) {}

  /**
   * This callback is invoked when the fireMouseEvent() is
   * invoked with a BMouseEvent with an id of MOUSE_PULSED.
   */
  public void mousePulsed(BMouseEvent event) {}

  /**
   * This callback is invoked when the fireMouseEvent() is
   * invoked with a BMouseEvent with an id of MOUSE_DRAG_STARTED.
   */
  public void mouseDragStarted(BMouseEvent event) {}

  /**
   * This callback is invoked when the fireMouseEvent() is
   * invoked with a BMouseWheelEvent with an id of MOUSE_WHEEL.
   * Unlike other mouse events, the framework will propagate a 
   * wheel event up the widget's ancestor tree until it is
   * consumed.  So it is possible that the source of the event
   * is not this widget.
   */
  public void mouseWheel(BMouseWheelEvent event) {}

  /**
   * This callback is invoked when the fireMouseEvent() is
   * invoked with a BMouseEvent with an id of MOUSE_HOVER.
   */
  public void mouseHover(BMouseEvent event) {}
  
  /**
   * Reset the hover timer to receive another mouseHover() callback.
   */
  public void resetHover()
  {
    if (shellManager != null)
      shellManager.resetHover(this);    
  }

////////////////////////////////////////////////////////////////
// Keyboard
////////////////////////////////////////////////////////////////

  /**
   * Fire an event for the {@code keyEvent} topic.
   * This call first routes the event to one the widget
   * keyX(BKeyEvent) callbacks, then fires it for any
   * potential links.
   *
   * @see javax.baja.ui.BWidget#keyEvent
   */
  public void fireKeyEvent(BKeyEvent event)
  {
    switch(event.getId())
    {
      case BKeyEvent.KEY_PRESSED:  keyPressed(event); break;
      case BKeyEvent.KEY_RELEASED: keyReleased(event); break;
      case BKeyEvent.KEY_TYPED:    keyTyped(event); break;
    }
    fire(keyEvent, event, null);
  }

  /**
   * This callback is invoked when the fireKeyEvent() is
   * invoked with a BKeyEvent with an id of KEY_PRESSED.
   */
  public void keyPressed(BKeyEvent event) {}

  /**
   * This callback is invoked when the fireKeyEvent() is
   * invoked with a BKeyEvent with an id of KEY_RELEASED.
   */
  public void keyReleased(BKeyEvent event) {}

  /**
   * This callback is invoked when the fireKeyEvent() is
   * invoked with a BKeyEvent with an id of KEY_TYPED.
   */
  public void keyTyped(BKeyEvent event) {}

////////////////////////////////////////////////////////////////
// Agents
////////////////////////////////////////////////////////////////

  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.toTop("bajaui:ValueBinding");
    return agents;
  }

////////////////////////////////////////////////////////////////
// Misc
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/widget.png");
  
  /**
   * For framework use only.
   */
  public Object widgetSupport(Object x)
  {
    if (x != null)
      initShell((ShellManager)x);
    return shellManager;
  }

  /**
  * Get the UndoManager associated with this widget.
  */
  public UndoManager getUndoManager()    
  {
    BWidget w = this;
    do
    {
      if (w instanceof UndoManager.Scope)
      {
        UndoManager um = ((UndoManager.Scope) w).getInstalledUndoManager();
        if (um != null) return um;
      }
      w = w.getParentWidget();
    } while (w != null);
    
    return null;
  } 
  
  /**
   * Causes 'event.run()' to be called on bajaui's event dispatch thread.
   * This will happen after all pending events have been processed. This method should
   * be used when another thread needs to update the GUI.
   * 
   * @param event  The Runnable event to be invoked on the event dispatch thread. 
   * 
   * @since Niagara 3.6
   */
  public static final void invokeLater(Runnable event)
  {
    UiEnv.get().invokeLater(event);
  }
  
////////////////////////////////////////////////////////////////
// Debugging
////////////////////////////////////////////////////////////////

  public String toDebugString()
  {
    return super.toDebugString() + "[" + getDebugString() + boundsToString() + "]";
  }

  public String getDebugString()
  {
    return "";
  }

  public String boundsToString()
  {
    return "" + x + ',' + y + ',' + width + ',' + height;
  }

  public String preferredSizeToString()
  {
    return "" + prefWidth + ',' + prefHeight;
  }

  static
  {
    try
    {                       
      // by default we don't create a WidgetApp in a station,
      // although it is possible one will get created by by the 
      // WbLocalDisplayService
      synchronized (UiEnv.appLock)
      {
        if (Sys.getStation() == null && UiEnv.app == null)
        {                                                 
          UiEnv.app = (BWidgetApplication)(BTypeSpec.make("workbench", "WbApplication").getInstance());
          UiEnv.app.start();      
        }
      }
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final BBinding[] noBindings = new BBinding[0];

  ShellManager shellManager;
  private double x;
  private double y;
  private double width;
  private double height;
  private double prefWidth;
  private double prefHeight;
  private boolean needsLayout = true;
  private boolean busy = false;
  private MouseCursor mouseCursor = MouseCursor.normal;
  private BBinding[] bindings = noBindings;
  
}
