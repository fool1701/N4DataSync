/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.gx.BFont;
import javax.baja.gx.BImage;
import javax.baja.gx.BInsets;
import javax.baja.gx.Graphics;
import javax.baja.gx.Point;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.event.BInputEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.menu.BIMenuItem;

import com.tridium.ui.UiEnv;
import com.tridium.ui.fx.FxUtil;
import com.tridium.ui.theme.MenuItemTheme;
import com.tridium.ui.theme.Theme;

/**
 * BMenuItem is a "line item" on a BMenu.
 *
 * @author    Brian Frank       
 * @creation  2 Dec 00
 * @version   $Revision: 46$ $Date: 6/20/11 9:34:25 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The text to display as the menu's label.
 */
@NiagaraProperty(
  name = "text",
  type = "String",
  defaultValue = ""
)
/*
 The image to display as the menu's label.
 */
@NiagaraProperty(
  name = "image",
  type = "BImage",
  defaultValue = "BImage.NULL"
)
/*
 The accelerator to display for the menu label.
 */
@NiagaraProperty(
  name = "accelerator",
  type = "BAccelerator",
  defaultValue = "BAccelerator.NULL"
)
public abstract class BMenuItem
  extends BWidget implements BIMenuItem
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BMenuItem(3010341292)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "text"

  /**
   * Slot for the {@code text} property.
   * The text to display as the menu's label.
   * @see #getText
   * @see #setText
   */
  public static final Property text = newProperty(0, "", null);

  /**
   * Get the {@code text} property.
   * The text to display as the menu's label.
   * @see #text
   */
  public String getText() { return getString(text); }

  /**
   * Set the {@code text} property.
   * The text to display as the menu's label.
   * @see #text
   */
  public void setText(String v) { setString(text, v, null); }

  //endregion Property "text"

  //region Property "image"

  /**
   * Slot for the {@code image} property.
   * The image to display as the menu's label.
   * @see #getImage
   * @see #setImage
   */
  public static final Property image = newProperty(0, BImage.NULL, null);

  /**
   * Get the {@code image} property.
   * The image to display as the menu's label.
   * @see #image
   */
  public BImage getImage() { return (BImage)get(image); }

  /**
   * Set the {@code image} property.
   * The image to display as the menu's label.
   * @see #image
   */
  public void setImage(BImage v) { set(image, v, null); }

  //endregion Property "image"

  //region Property "accelerator"

  /**
   * Slot for the {@code accelerator} property.
   * The accelerator to display for the menu label.
   * @see #getAccelerator
   * @see #setAccelerator
   */
  public static final Property accelerator = newProperty(0, BAccelerator.NULL, null);

  /**
   * Get the {@code accelerator} property.
   * The accelerator to display for the menu label.
   * @see #accelerator
   */
  public BAccelerator getAccelerator() { return (BAccelerator)get(accelerator); }

  /**
   * Set the {@code accelerator} property.
   * The accelerator to display for the menu label.
   * @see #accelerator
   */
  public void setAccelerator(BAccelerator v) { set(accelerator, v, null); }

  //endregion Property "accelerator"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMenuItem.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a menu item with specified label 
   * text and accelerator.
   */
  public BMenuItem(String text, BAccelerator accelerator)
  {
    setText(text);
    setAccelerator(accelerator);
  }

  /**
   * Construct a menu item with specified label text.
   */
  public BMenuItem(String text)
  {
    setText(text);
  }

  /**
   * Construct a menu item with specified command, using
   * the command's text, accelerator, and icon.
   */
  public BMenuItem(Command command)
  {
    setCommand(command);
  }

  /**
   * No argument constructor.
   */
  public BMenuItem()
  {
  }
  
////////////////////////////////////////////////////////////////
//BIMenuItem
////////////////////////////////////////////////////////////////

  @Override
  public BWidget asWidget()
  {
    return this;
  }
  
////////////////////////////////////////////////////////////////
// Command
////////////////////////////////////////////////////////////////

  /**
   * Get the Command installed in this menu item, or
   * null if no Command is installed.
   */
  public Command getCommand()
  {
    return command;
  }
  
  /**
   * Install the specified command using the command's label, 
   * accelerator, and icon (if available).
   */
  public void setCommand(Command command)
  {
    setCommand(command, true, true, true);
  }
  
  /**
   * Install the specified command and optionally use
   * its label, accelerator, and icon.
   */                                 
  public void setCommand(Command command, boolean useLabel, boolean useIcon, boolean useAcc)
  {        
    // unregister from old command        
    if (this.command != null)
      this.command.unregister(this);
    
    if (command == null) return;
  
    if (useLabel)
    {
      String label = command.getLabel();
      if (label == null) label = "No label";
      setText(label);
    }

    if (useIcon && !Theme.javaFx().shouldHideNonessentialIcons())
    {
      BImage icon = command.getIcon();
      if (icon != null) setImage(icon);
    }
    
    if (useAcc)
    {
      BAccelerator acc = command.getAccelerator();
      if (acc != null && !acc.isNull()) setAccelerator(acc);
    }
    
    // register new command    
    this.command = command;
    command.register(this);
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Compute the menu item's preferred size.
   */
  public void computePreferredSize()
  {
    BAccelerator acc = getAccelerator();
    BInsets insets = Theme.menuItem().getInsets();
    BFont textFont = Theme.menuItem().getTextFont();
    BFont accFont = Theme.menuItem().getAcceleratorFont();
    
    double fh = Math.max(textFont.getHeight(), accFont.getHeight());
    textWidth = textFont.width(getText());
    acceleratorWidth = 0;
    if (!acc.isNull()) 
      acceleratorWidth = accFont.width(getAccelerator().toString());
    
    // we don't know width until the parent looks 
    // at every text width and accelerator width
    double w = 0;
    double h = fh + insets.top + insets.bottom;
    baseline = h - textFont.getDescent() - insets.bottom;
    setPreferredSize(w, h);
  }

////////////////////////////////////////////////////////////////
// Paint
////////////////////////////////////////////////////////////////
  
  /**
   * Paint the menu item.
   */
  public void paint(Graphics g)
  {   
    MenuItemTheme theme = Theme.menuItem();
    BAccelerator acc = getAccelerator();
    BInsets insets = Theme.menuItem().getInsets();

    paintBackground(g, theme);
    
    g.setFont(theme.getTextFont());
    if (getEnabled())
    {
      if (isSelected) g.setBrush(theme.getSelectionForeground(this));
      else g.setBrush(theme.getTextBrush());
      
      if(this.shortcut!=0)
      {
        double offset=insets.left;
        int shortcutIndex = getText().toLowerCase().indexOf(Character.toLowerCase(shortcut));
        if(shortcutIndex>0)
        {
          String text = getText().substring(0, shortcutIndex);
          g.drawString(text, offset, baseline);
          offset += g.getFont().width(text);
        }
        if (shortcutIndex > -1)
        {
          String text = getText().substring(shortcutIndex, shortcutIndex+1);
          BFont underline = BFont.make(theme.getTextFont(), theme.getTextFont().getStyle()|BFont.UNDERLINE); 
          g.setFont(underline);
          g.drawString(text, offset, baseline);
          offset += underline.width(text);
        }        
        if(shortcutIndex<getText().length())
        {
          String text = getText().substring(shortcutIndex+1, getText().length());
          g.setFont(theme.getTextFont());
          g.drawString(text, offset, baseline);
        }
      } else
      g.drawString(getText(), insets.left, baseline);
    }
    else
    {
      theme.paintDisabledText(g, this, getText(), insets.left, baseline);
    }
    
    if (showAccelerator && !acc.isNull())
    {
      g.setFont(theme.getAcceleratorFont());
      if (getEnabled())
      {
        if (isSelected) g.setBrush(theme.getSelectionForeground());
        else g.setBrush(theme.getAcceleratorBrush());
        g.drawString(acc.toString(), acceleratorX, baseline);
      }
      else
      {
        theme.paintDisabledText(g, this, acc.toString(), acceleratorX, baseline);
      }
    }

    paintImage(g);
  }
  
  /**
   * Override to paint the icon.
   */
  void paintImage(Graphics g)
  {
    if (Theme.javaFx().shouldHideNonessentialIcons()) {
      return;
    }

    BImage image = getImage();
    if (!image.isNull())
    {
      if (!isEnabled()) image = image.getDisabledImage();
      BInsets insets = Theme.menuItem().getInsets();
      g.drawImage(image, Theme.menuItem().getIconIndent(), insets.top);
    }
  }        
  
  public String getStyleSelector() { return "menu-item"; }
  
////////////////////////////////////////////////////////////////
// Translation
////////////////////////////////////////////////////////////////
  
  /**
   * Get the child at the specified point.
   */
  public BWidget childAt(Point pt)
  {
    return null;
  }

////////////////////////////////////////////////////////////////
// Selection
////////////////////////////////////////////////////////////////  

  /**
   * Callback to reset the item when menu opens.
   */
  void opening(BMenu menu, boolean showAccelerator)
  {
    this.isSelected = false;                           
    this.showAccelerator = showAccelerator;
  }
  
  /**
   * Called when this item is selected.
   */
  void select()
  {
    isSelected = true;
    repaint();
  }
  
  /**
   * Called when this item is unselected.
   */
  void unselect()
  {
    isSelected = false;
    repaint();
  }
  
  /**
   * Process a menu item activation.
   */
  void doClick(BInputEvent event)
  {
  }

  /**
   * Timer expired.
   */
  void timerExpired()
  {
    if (isOver) closeSiblings();
  }

  /**
   * Close all of my BSubMenuItems siblings.
   */
  void closeSiblings()
  {
    // kill any current timer
    stopTimer();
    
    // close siblings
    SlotCursor<Property> c = getParent().getProperties();
    while(c.nextComponent())
    {
      BObject x = c.get();
      if (x != this && x instanceof BSubMenuItem)
        ((BSubMenuItem)x).closeMenu();
    }
  }

////////////////////////////////////////////////////////////////
// Mouse Eventing
////////////////////////////////////////////////////////////////  

  public void mouseEntered(BMouseEvent event)
  {
    ((BMenu)getParent()).select(this); 
    isOver = true;
    startTimer();   
  }

  public void mouseExited(BMouseEvent event)
  {
    ((BMenu)getParent()).unselect(this);
    isOver = false;
    startTimer();   
  }
  
  public void mouseReleased(BMouseEvent event)
  {
    UiEnv.get().closePopup(null);
    if (contains(event.getX(), event.getY()))
      doClick(event);
  }
    
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////  

  /**
   * Override point to make paint the item's 
   * background using the specified theme.
   */
  void paintBackground(Graphics g, MenuItemTheme theme)
  {    
    theme.paintBackground(g, this, isSelected);
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/menuItem.png");
  
////////////////////////////////////////////////////////////////
// Timer
////////////////////////////////////////////////////////////////  
  
  void startTimer()
  {
    synchronized(timerLock)
    {
      stopTimer();
      timed = this;
      timer = new Timer();
      timer.start();
    }
  }

  static void stopTimer()
  {
    synchronized(timerLock)
    {
      timed = null;
      if (timer != null) timer.kill();
    }
  }
  
  static class Timer
    extends Thread
  {
    public Timer() { super("Ui:MenuItemTimer"); }
    public void kill() { dead = true; interrupt(); }
    public void run() 
    {
      try
      {
        sleep(200);
        if (!dead)
        {
          BMenuItem item = timed;
          if (item != null) 
            UiEnv.get().invokeLater(new TimerInvoker(item));
        }
      }
      catch(InterruptedException e)
      {
      }
    }     
    boolean dead;
  }
  
  static class TimerInvoker
    implements Runnable
  {
    TimerInvoker(BMenuItem item) { this.item = item; }
    public void run() { item.timerExpired(); }
    BMenuItem item;
  }

  static Object timerLock = new Object();
  static Timer timer;
  static BMenuItem timed;

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  char shortcut = 0;
  
  double baseline;
  double textWidth;
  double acceleratorWidth;
  double acceleratorX;
  boolean showAccelerator;
  boolean isSelected;
  Command command;
  boolean isOver;
  
}
