/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.pane;

import java.awt.Graphics2D;

import javax.baja.gx.BBrush;
import javax.baja.gx.BBrush.Solid;
import javax.baja.gx.BColor;
import javax.baja.gx.BFont;
import javax.baja.gx.BImage;
import javax.baja.gx.BPen;
import javax.baja.gx.Graphics;
import javax.baja.gx.Point;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.BMenu;
import javax.baja.ui.BWidget;
import javax.baja.ui.MouseCursor;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BMouseEvent;

import com.tridium.gx.awt.AwtGraphics;
import com.tridium.ui.theme.JavaFxTheme;
import com.tridium.ui.theme.Theme;

/**
 * BToolPane.
 *
 * @author    Andy Frank       
 * @version   $Revision: 47$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BToolPane
  extends BLabelPaneContainer
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BToolPane(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:35 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BToolPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  

////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  /**
   * Get the menu controller class or null.
   */
  public MenuController getMenuController() 
  { 
    return menuController; 
  }
  
  /**
   * Set the controller class or pass null for no menu.
   */
  public void setMenuController(MenuController menuController) 
  { 
    this.menuController = menuController; 
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////  
  
  @Override
  public void computePreferredSize()
  { 
    setPreferredSize(150, 200);
  }
  
  @Override
  public void doLayout(BWidget[] kids)
  {
    if (kids.length == 0)
    {
      return;
    }
    
    int w = (int)getWidth();
    int h = (int)getHeight();

    // Child added or removed since last layout
    if (sizes.length != kids.length)
    {
      sizes = new int[kids.length];
      int ds = MAX / sizes.length;
      for (int i=0; i<sizes.length; i++)
      {
        sizes[i] = ds;
      }
      maximized = false;
    }

    // Layout children
    int ph = 0, dh = 0, dy = 0, sum = 0;

    for (int i=0; i<kids.length; i++)
    {
      BLabelPane pane = (BLabelPane)kids[i];
      BLabel label = pane.getLabel();
      configureLabel(label);
      BWidget content = pane.getContent();

      //compute preferred height for all labels on initial iteration
      if(i == 0)
      {
        label.computePreferredSize();
        ph = (int)label.getPreferredHeight() + 4;
        dh = h - ph * kids.length;  // The height excluding the "bars"
        dh = dh > 0 ? dh : 0;
      }

      int sz = (int)(dh * (sizes[i] / (float)MAX));
      if (i == kids.length-2 && sz > 0 && sizes[kids.length-1] == 0)
      {
        sz = dh - sum;
      }


      int lx = menuController == null ? 5 : 20;
      int lw = w - lx - 20;

      label.setBounds(lx, dy, lw, ph);
      label.layout();
      content.setBounds(0, dy+ph, w, i == kids.length-1 ? h-(dy+ph): sz);
      content.layout();

      dy += ph + sz;
      sum += sz;
    }
  }

  /**
   * Apply theme properties to label
   * @param label
   */
  private void configureLabel(BLabel label)
  {
    label.setFont(Theme.toolPane().getHeaderFont(this, label == armed));
    label.setForeground(Theme.toolPane().getForeground(this, label == armed));
    label.setPadding(Theme.toolPane().getLabelInsets(this));
  }

////////////////////////////////////////////////////////////////
// Painting
////////////////////////////////////////////////////////////////

  /**
   * Paint component.
   */
  @Override
  public void paint(Graphics g)
  {
    int w = (int) getWidth();
    int h = (int) getHeight();

    g.setBrush(Theme.toolPane().getWindowBackground(this));
    g.fillRect(0,0,w,h);

    BWidget[] kids = getChildWidgets();

    /*
    Paint child panes in reverse order; otherwise paintHeaderBackground will
    paint over the drop shadow of the previous widget.
     */
    for(int i = kids.length - 1; i >= 0; i--)
    {
      BLabelPane kid = (BLabelPane)kids[i];
      paintLabel(g, kid.getLabel(), sizes[i] == MAX && maximized, w, kids.length > 1);
      paintChild(g, kid.getContent());

      if(i < kids.length - 1 && Theme.javaFx().isEnabled())
      {
        BWidget content = kid.getContent();
        paintDropShadow(g, content);
      }
    }

    g.setBrush(Theme.toolPane().getControlForeground(this));
    g.strokeRect(0,0,w-1,h-1);
  }

  //mostly c&p'ed from BWidget.paintChild
  private void paintHeaderBackground(Graphics g, BLabel label)
  {
    g.push();
    try
    {
      //fill header background
      BBrush background = Theme.toolPane().getBackground(this, label == armed);
      g.setBrush(background);
      g.clip(0, label.getY(), getWidth(), label.getHeight());
      g.translate(0, label.getY());
      g.fillRect(0, 0, getWidth(), label.getHeight());

      //determine label background width and cache max label width
      BFont font = !label.getFont().isNull() ? label.getFont() : Theme.label().getTextFont(label);
      BImage labelImage = label.getImage();
      double labelWidth = font.width(label.getText()) + 2 * (labelImage.getWidth() + label.getX());
      maxLabelWidth = labelWidth > maxLabelWidth ? labelWidth : maxLabelWidth;

      if (Theme.javaFx().isEnabled())
      {
        int cornerRadius = (int) Theme.javaFx().getBorderRadius(this);
        
        //paint rounded corner if supported by Graphics object
        if (cornerRadius > 0 && g instanceof AwtGraphics)
        {
          //fill label background for contrast against header background
          g.setBrush(Theme.toolPane().getControlBackground(this));
          Graphics2D g2d = ((AwtGraphics)g).getAwtGraphics();
          g2d.fillArc(0, 0, 2 * cornerRadius, 2 * cornerRadius, 90, 90);
          g.fillRect(0, cornerRadius, cornerRadius, label.getHeight() - cornerRadius);
          g.fillRect(cornerRadius, 0, maxLabelWidth - cornerRadius, label.getHeight());

          g.setPen(BPen.make(2));

          //paint label underline
          g.setBrush(Theme.toolPane().getControlHighlight(this));
          g.strokeLine(0, label.getHeight(), maxLabelWidth, label.getHeight());

          g.setBrush(Theme.toolPane().getControlShadow(this));

          //paint horizontal line after label underline
          g.strokeLine(maxLabelWidth, label.getHeight(), getWidth(), label.getHeight());

          //paint vertical line separating label and header background
          g.setPen(BPen.make(1));
          g.strokeLine(maxLabelWidth, 0, maxLabelWidth, label.getHeight());
        }
      }

      else
      {
        g.fillRect(0, 0, maxLabelWidth, label.getHeight());
      }

    }
    finally
    {
      g.pop();
    }
  }

  /**
   * Paint label.
   */
  void paintLabel(Graphics g, BLabel label, boolean max, int w, boolean drawIcon)
  {
    paintHeaderBackground(g, label);

    paintChild(g, label);
    
    // paint restore
    if (drawIcon)
    {
      int ix = w - 18;
      int iy = (int)label.getY() + ((int)label.getHeight() - 16) / 2;
      g.drawImage(max ? restoreIcon : maximizeIcon, ix, iy);
    }

    // paint menu arrow
    if (menuController != null) 
    {
      int ay = (int)label.getY() + ((int)label.getHeight() - 5) / 2 - 1;
      g.setBrush(Theme.toolPane().getForeground(this, label == armed));
      paintMenuArrow(g, 10, ay);
    }
  }
  
  /**
   * Paint menu arrow.
   */
  void paintMenuArrow(Graphics g, int x, int y)
  {
    int arrowWidth = 3;
    int mid = x + 10 - arrowWidth - 4;
    int bottom = y + arrowWidth + 2;
    for (int i=0; i<arrowWidth; ++i)
    {
      g.strokeLine(mid - i, bottom - i, mid + i, bottom - i);
    }
  }

  /**
   * Paint drop shadow
   */
  private void paintDropShadow(Graphics g, BWidget content)
  {
    JavaFxTheme theme = Theme.javaFx();
    double spread = theme.getDropShadowWidth(this);
    BBrush shadowBrush = theme.getDropShadow(this);
    int borderRadius = (int) theme.getBorderRadius(this);

    if (spread == 0 || shadowBrush == BBrush.DEFAULT)
    {
      return;
    }

    g.push();

    try
    {
      Graphics2D g2d = g instanceof AwtGraphics ? ((AwtGraphics)g).getAwtGraphics() : null;

      double x = content.getX();
      double y = content.getY();
      double cw = content.getWidth();
      double ch = content.getHeight();
      double currY = y + ch;
      
      BColor shadowColor = ((Solid) shadowBrush.getPaint()).getColor();
      int red = shadowColor.getRed();
      int green = shadowColor.getGreen();
      int blue = shadowColor.getBlue();
      double alpha = shadowColor.getAlpha();
      double alphaIncrement = alpha / spread;

      //match drop shadow alpha at first; decrease alpha as we paint further
      //away from the content edge
      for (int i = 0; i < spread; i++, alpha -= alphaIncrement, currY--)
      {
        g.setBrush(BColor.make(red, green, blue, (int) alpha));

        if (borderRadius > 0 && g2d != null) //we have the ability to paint arcs
        {
          int diameter = 2 * (borderRadius + i);
          int cx = (int)x - i - 1;
          int cy = (int)currY;
          //straight horizontal line
          g.strokeLine(borderRadius, currY, borderRadius + cw, currY);
          //rounded shadow edge
          g2d.drawArc(cx, cy, diameter, diameter, 90, 90);
        }
        else
        {
          //no arcs allowed - straight horizontal shadow only
          g.strokeLine(0, currY, cw, currY);
        }
      }
    }

    finally
    {
      g.pop();
    }
  }
////////////////////////////////////////////////////////////////
// BWidget Overrides
////////////////////////////////////////////////////////////////

  @Override
  public void added(Property prop, Context context) 
  {
    super.added(prop, context);
    BObject value = get(prop);
    if (value instanceof BLabelPane)
    {
      BLabel label = ((BLabelPane)value).getLabel();
      label.setForeground(textBrush);
      label.setFont(Theme.toolPane().getTextFont(this));
      label.setHalign(BHalign.left);      
    }    
  }

  @Override
  public BWidget childAt(Point pt)
  {
    BWidget[] kids = getChildWidgets();
    for (int i=0; i<kids.length; i++)
    {
      BLabelPane kid = (BLabelPane)kids[i];
      BWidget w = kid.getContent();
      int dx = (int)pt.x - (int)w.getX();
      int dy = (int)pt.y - (int)w.getY();
      if (w.isVisible() && w.contains(dx, dy))
      {
        return w;
      }
    }
    return null;    
  }

  @Override
  public String getStyleSelector() {
    return "pane tool-pane";
  }

////////////////////////////////////////////////////////////////
// Key Events
////////////////////////////////////////////////////////////////

  @Override
  public void keyPressed(BKeyEvent event)
  {
    if (resizing && event.getKeyCode() == BKeyEvent.VK_ESCAPE)
    {
      // Cancel resize operation
      System.arraycopy(restore, 0, sizes, 0, sizes.length);
      resizing = false;
      relayout();
      restoreCursor();
    }
  }

////////////////////////////////////////////////////////////////
// Mouse events
////////////////////////////////////////////////////////////////

  @Override
  public void mousePressed(BMouseEvent event)
  {
    requestFocus();
    int mx = (int)event.getX();
    int my = (int)event.getY();
    int index = getLabelPaneAt(mx, my);
    if (index < 0)
    {
      return;
    }

    if (mx > getWidth() - 20)
    {
      // Maximize or Restore pressed
      if (!maximized)
      {
        restore = new int[sizes.length];
        System.arraycopy(sizes, 0, restore, 0, sizes.length);
        maximized = true;
      }

      if (maximized && sizes[index] == MAX)
      {
        // Restore original state
        System.arraycopy(restore, 0, sizes, 0, sizes.length);
        maximized = false;
      }
      else
      {
        // Maximize a different palette
        for (int i=0; i<sizes.length; i++)
        {
          sizes[i] = i == index ? MAX : 0;
        }
      }
      
      relayout();
    }
    else if (menuController != null && mx < 20)
    {
      // Display menu
      BMenu menu = menuController.getMenu(this, getLabelPane(index).getContent());
      if (menu != null)
      {
        menu.open(armed, -armed.getX(), armed.getHeight());
      } 
    }
    else if (!maximized && index > 0)
    {
      // Initiate a resize command
      restore = new int[sizes.length];
      System.arraycopy(sizes, 0, restore, 0, sizes.length);
      
      resizing = true;
      anchor = resize = (int)event.getY();
      resizeIndex = index-1;
    }
    else if (maximized)
    {
      // Restore original state.
      System.arraycopy(restore, 0, sizes, 0, sizes.length);
      maximized = false;
      relayout();
    }
  }

  @Override
  public void mouseReleased(BMouseEvent event)
  {
    if (resizing) 
    { 
      // Complete resize opertaion
      resizing = false; 
      restoreCursor();
      relayout();
    }
  }

  @Override
  public void mouseMoved(BMouseEvent event)
  {
    if (resizing)
    {
      return;
    }

    int mx = (int)event.getX();
    int my = (int)event.getY();
    BLabel old = armed;
    int index = getLabelPaneAt(mx, my);
    
    armed = index < 0 ? null : getLabelPane(index).getLabel();
    if (old != armed && index >= 0)
    {
      armed = getLabelPane(index).getLabel();
      repaint();
    }
    
    if (!maximized && index > 0 && armed != null && inResizeZone(armed, mx, my))
    {
      setCursor();
    }
    else
    {
      restoreCursor();
    }
  }

  @Override
  public void mouseEntered(BMouseEvent event)
  {
    mouseMoved(event);
  }

  @Override
  public void mouseExited(BMouseEvent event)
  {
    if (resizing)
    {
      return;
    }
    restoreCursor();
    armed = null;
    repaint();
  }

  @Override
  public void mouseDragged(BMouseEvent event)
  {
    if (resizing)
    {
      resize = (int)event.getY();
      resize();
      relayout();
    }
  }

////////////////////////////////////////////////////////////////
// Utility Methods
////////////////////////////////////////////////////////////////

  /**
   * Convience method to get BLabelPane child by index.
   */
  private BLabelPane getLabelPane(int index)
  {
    return (BLabelPane)getChildWidgets()[index];
  }

  /**
   * Return the BLabelPane that contains x,y
   * in the header - NOT the content.
   */
  private int getLabelPaneAt(int x, int y)
  {
    BWidget[] kids = getChildWidgets();
    for (int i=0; i<kids.length; i++)
    {
      BLabelPane kid = (BLabelPane)kids[i];
      BLabel label = kid.getLabel();
      int dx = (int)label.getX(); //x - label.getX();
      int dy = y - (int)label.getY();
      if (label.contains(dx,dy))
      {
        return i; //kid;
      }
    }
    return -1; //null;
  }

  /**
   * Return true if (x,y) is in a resize zone.
   */
  private static boolean inResizeZone(BLabel label, int x, int y)
  {
    return label.contains(x-label.getX(), y-label.getY());
  }

  /**
   * Returns the BLabelPane that comes directly
   * before this one or null if one does not.
   */
  private BLabelPane getPrevious(BLabelPane pane)
  {
    BWidget[] kids = getChildWidgets();
    for (int i=0; i<kids.length-1; i++)
    {
      if (kids[i + 1] == pane)
      {
        return (BLabelPane)kids[i];
      }
    }
    return null;
  }

  /**
   * Convience method to set the cursor.
   */
  private void setCursor()
  {
    if (origCursor == null)
    {
      origCursor = getMouseCursor();
      setMouseCursor(MouseCursor.nResize);
    }
  }
  
  /**
   * Convience method to restore original cursor.
   */
  private void restoreCursor()
  {
    if (origCursor != null)
    {
      setMouseCursor(origCursor);
      origCursor = null;
    }
  }

////////////////////////////////////////////////////////////////
// Resizing
////////////////////////////////////////////////////////////////

  /**
   * Resize the panes.
   */
  private void resize()
  {
    float delta = (resize - anchor) / (float)getHeight();
    int r = resizeIndex;

    int temp = 0;
    for (int i=0; i<r; i++)
    {
      temp += sizes[i];
    }

    sizes[r] = restore[r] + (int)(delta * MAX);
    if (sizes[r] < 0)
    {
      sizes[r] = 0;
    }
    
    if (sizes[r] + temp > MAX)
    {
      sizes[r] -= sizes[r] + temp - MAX;
    }
    
    temp += sizes[r];
    
    for (int i=r+1; i<sizes.length-1; i++)
    {
      if (sizes[r] > 0)
      {
        sizes[i] = restore[i] + (int)(restore[i] * -delta);
      }

      if (sizes[i] < 0)
      {
        sizes[i] = 0;
      }
      
      if (temp + sizes[i] > MAX)
      {
        sizes[i] -= sizes[i] + temp - MAX;
      }

      temp += sizes[i];
    }

    sizes[sizes.length-1] = MAX - temp;
  }

////////////////////////////////////////////////////////////////
// MenuController
////////////////////////////////////////////////////////////////

  /**
   * MenuController is used to register a drop 
   * down menu for a content pane.
   */
  public interface MenuController
  {
    /**
     * Get the menu to display or return null.
     */
    BMenu getMenu(BToolPane parent, BWidget content);
  }
  
////////////////////////////////////////////////////////////////
// Serialization
////////////////////////////////////////////////////////////////

  /**
   * Save state. Returns a string in the format of
   * "toolpane=25;75;25" where each number indicates
   * the percentage size of the respective palette.
   */
  public String pickle()
  {
    StringBuilder buf = new StringBuilder("toolpane=");
    
    BWidget[] kids = getChildWidgets();  
    
    if (kids.length != sizes.length)
    {
      return buf.toString();
    }
    
    for (int i=0; i<kids.length; i++)
    {
      if (i > 0)
      {
        buf.append(";");
      }
      buf.append(sizes[i]);
    }
    return buf.toString();
  }
  
  /**
   * Restore state.
   */
  public void unpickle(String str)
  {
    str = str.substring("toolpane=".length());
    String[] sub = TextUtil.split(str, ';');
    BWidget[] kids = getChildWidgets();
    if (sub.length != kids.length)
    {
      return;
    }
    
    int[] old = sizes; // make backup in case of error
    try
    {
      sizes = new int[sub.length];
      for (int i=0; i<sub.length; i++)
      {
        sizes[i] = Integer.parseInt(sub[i]);
        if (sizes[i] == MAX && sizes.length > 1)
        {
          restore = new int[sizes.length];
          int ds = MAX / sizes.length;
          for (int j=0; j<restore.length; j++)
          {
            restore[j] = ds;
          }
          maximized = true;
        }
      }
    }
    catch (Exception e) { sizes = old; }
  }
  
////////////////////////////////////////////////////////////////
// Test Driver
////////////////////////////////////////////////////////////////  

  /*
  public static void main(String[] args)
  {
    BImage icon = IconManager.getImage("x16/computer.png");
    
    BToolPane pane = new BToolPane();
    pane.setMenuController(new Menu(pane));
    pane.addPane(new BLabel(icon, "pane1"), new BScrollPane(new BLabel("Content #1")));
    pane.addPane(new BLabel(icon, "pane2"), new BScrollPane(new BLabel("Content #2")));
    pane.addPane(new BLabel(icon, "pane3"), new BScrollPane(new BLabel("Content #3")));
    pane.addPane(new BLabel(icon, "pane4"), new BScrollPane(new BLabel("Content #4")));
    
    BFrame f = new BFrame("BToolPane Test");
    f.setContent(new BBorderPane(pane,10,10,10,10));
    f.setScreenBounds(100,100,400,500);
    f.open();

    pane.unpickle("toolpane=10000;0;0;0");
    pane.relayout();

    System.out.println(pane.pickle());
  }

  static class Menu implements MenuController
  {
    public Menu(BWidget owner) { this.owner = owner; }

    public BMenu getMenu(BToolPane parent, BWidget content)
    {
      BMenu menu = new BMenu();
      menu.add(null, new Dummy("Alpha"));
      menu.add(null, new Dummy("Beta"));
      menu.add(null, new Dummy("Gamma"));
      return menu;
    }

    class Dummy extends Command
    {
      public Dummy(String label) { super(owner, label); }
      public CommandArtifact doInvoke() { return null; }
    }

    BWidget owner;
  }
  */

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final BBrush textBrush = BColor.white.toBrush();
  
  private static final BImage restoreIcon  = BImage.make("module://icons/x16/restoreWhite.png");
  private static final BImage maximizeIcon = BImage.make("module://icons/x16/maximizeWhite.png");

  private static final int MAX = 10000;
  
  private MenuController menuController;

  private int[] sizes   = new int[0];
  private int[] restore = new int[0];
  private BLabel armed;
  private MouseCursor origCursor;
  
  private boolean maximized;
  private boolean resizing;
  
  private int anchor;
  private int resize;
  private int resizeIndex;

  private double maxLabelWidth;
}
