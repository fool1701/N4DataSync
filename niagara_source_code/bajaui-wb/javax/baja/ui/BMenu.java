/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.util.Vector;

import javax.baja.gx.BInsets;
import javax.baja.gx.Graphics;
import javax.baja.gx.RectGeom;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BComplex;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.enums.BOrientation;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.menu.BIMenu;
import javax.baja.ui.menu.BIMenuItem;
import javax.baja.ui.util.UiLexicon;

import com.tridium.sys.schema.Fw;
import com.tridium.ui.UiEnv;
import com.tridium.ui.theme.Theme;

/**
 * BMenu is the container for BMenuItems which are 
 * displayed via a popup window.
 *
 * @author    Brian Frank       
 * @creation  2 Dec 00
 * @version   $Revision: 47$ $Date: 6/20/11 9:34:25 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "visible",
  type = "boolean",
  defaultValue = "false",
  override = true
)
/*
 The text to display as the menu's label when
 used on a MenuBar.
 */
@NiagaraProperty(
  name = "text",
  type = "String",
  defaultValue = ""
)
public class BMenu
  extends BAbstractBar implements BIMenu
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BMenu(1443419128)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "visible"

  /**
   * Slot for the {@code visible} property.
   * @see #getVisible
   * @see #setVisible
   */
  public static final Property visible = newProperty(0, false, null);

  //endregion Property "visible"

  //region Property "text"

  /**
   * Slot for the {@code text} property.
   * The text to display as the menu's label when
   * used on a MenuBar.
   * @see #getText
   * @see #setText
   */
  public static final Property text = newProperty(0, "", null);

  /**
   * Get the {@code text} property.
   * The text to display as the menu's label when
   * used on a MenuBar.
   * @see #text
   */
  public String getText() { return getString(text); }

  /**
   * Set the {@code text} property.
   * The text to display as the menu's label when
   * used on a MenuBar.
   * @see #text
   */
  public void setText(String v) { setString(text, v, null); }

  //endregion Property "text"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMenu.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with text.
   */
  public BMenu(String text)
  {
    setText(text);
  }

  /**
   * No argument constructor.
   */
  public BMenu()
  {
  }

////////////////////////////////////////////////////////////////
//BIMenu
////////////////////////////////////////////////////////////////
  
  @Override
  public BWidget asWidget()
  {
    return this;
  }
  
////////////////////////////////////////////////////////////////
// Rendering
////////////////////////////////////////////////////////////////

  /**
   * Add a BActionMenuItem or BCheckBoxMenuItem using 
   * the specified Command.
   */
  public BMenuItem add(String name, Command command)
  {
    BMenuItem item;
    if (command instanceof ToggleCommand)
    {
      ToggleCommand toggleCommmand = (ToggleCommand)command;
      if (toggleCommmand.getGroup() != null)
        item = new BRadioButtonMenuItem(toggleCommmand);
      else
        item = new BCheckBoxMenuItem(toggleCommmand);
    }
    else  
    {
      item = new BActionMenuItem(command);
    }
    add(name, item, null);
    return item;
  }

  /**
   * Compute the menu's preferred size.
   */
  public void computePreferredSize()
  {
    fitToScreen();

    double maxText = 0;
    double maxAcc = 0;
    double w, h = 0;
    
    BWidget[] items = getChildWidgets();
    for(int i=0; i<items.length; ++i)
    {
      if (items[i] instanceof BSeparator)
      {
        BSeparator sep = (BSeparator)items[i];
        sep.computePreferredSize();
        h += sep.getPreferredHeight();
      }
      else
      {
        BMenuItem item = (BMenuItem)items[i];
        item.computePreferredSize();
        maxText = Math.max(maxText, item.textWidth);
        maxAcc = Math.max(maxAcc, item.acceleratorWidth);
        h += item.getPreferredHeight();
      }
    }
    
    BInsets mi = Theme.menu().getInsets();
    BInsets ii = Theme.menuItem().getInsets();
    w = maxText + maxAcc + acceleratorGap + mi.left + mi.right + ii.left + ii.right;
    h += mi.top + mi.bottom;
    setPreferredSize(w, h);
    acceleratorX = ii.left + maxText + acceleratorGap;
  }
  
  /**
   * Layout the menu.
   */
  public void doLayout(BWidget[] kids)
  {
    fitToScreen();

    BInsets insets = Theme.menu().getInsets();
    double x = insets.left;
    double y = insets.top;
    double w = getWidth() - insets.left - insets.right;
    
    for(int i=0; i<kids.length; ++i)
    {
      BWidget kid = kids[i];
      kid.setBounds(x, y, w, kid.getPreferredHeight());
      y += kid.getHeight();
      if (kid instanceof BMenuItem)
        ((BMenuItem)kid).acceleratorX = acceleratorX;
      else
        ((BSeparator)kid).setOrientation(BOrientation.horizontal);
    }    
  }
  
  /**
   * Paint the menu.
   */
  public void paint(Graphics g)
  {
    fitToScreen();

    // Compute shortcut keys
    computeShortcutKeys();
      
    Theme.menu().paintBackground(g, this);
    paintChildren(g);
  }
  
  public String getStyleSelector() { return "menu"; }

  /**
   * Determine the shortcut key to be used for quick access to each of
   * the menu items.  The shortcut keys must be unique for ecah item 
   * on the menu.
   */
  private void computeShortcutKeys()
  {
    if(hasFocus())
    {
      BMenuItem[] items = this.getChildren(BMenuItem.class);

      // The algorythm to automatically select shortcut keys
      // for menu items follows three steps:
      // 1) First grant a menu item the same shortcut key as
      //    as the character already used in an accelerator,
      //    if it has one defined.
      // 2) Then try give the menu item a shortcut key matching 
      //    the first letter of a word the menu item's text
      // 3) Then try to give it the shortcut key for any character
      //    contained within the text
      // 4) If that fails, the menu item does not have a shortcut 
      //    key
            
      boolean[] letters = new boolean[26];
      
      // Prime the list with already defined shortcut keys
      for(int i=0; i<items.length; i++)
        if(items[i].shortcut>0)
          letters[items[i].shortcut-'a']=true;              
      
      // try to use accelerator character
      for(int i=0; i<items.length; i++)
      {
        if(!items[i].getAccelerator().isNull())
        {
          int keyCode = items[i].getAccelerator().getKeyCode();
          char character;
          switch(keyCode)
          {
            case BKeyEvent.VK_A: character='a'; break;
            case BKeyEvent.VK_B: character='b'; break;
            case BKeyEvent.VK_C: character='c'; break;
            case BKeyEvent.VK_D: character='d'; break;
            case BKeyEvent.VK_E: character='e'; break;
            case BKeyEvent.VK_F: character='f'; break;
            case BKeyEvent.VK_G: character='g'; break;
            case BKeyEvent.VK_H: character='h'; break;
            case BKeyEvent.VK_I: character='i'; break;
            case BKeyEvent.VK_J: character='j'; break;
            case BKeyEvent.VK_K: character='k'; break;
            case BKeyEvent.VK_L: character='l'; break;
            case BKeyEvent.VK_M: character='m'; break;
            case BKeyEvent.VK_N: character='n'; break;
            case BKeyEvent.VK_O: character='o'; break;
            case BKeyEvent.VK_P: character='p'; break;
            case BKeyEvent.VK_Q: character='q'; break;
            case BKeyEvent.VK_R: character='r'; break;
            case BKeyEvent.VK_S: character='s'; break;
            case BKeyEvent.VK_T: character='t'; break;
            case BKeyEvent.VK_U: character='u'; break;
            case BKeyEvent.VK_V: character='v'; break;
            case BKeyEvent.VK_W: character='w'; break;
            case BKeyEvent.VK_X: character='x'; break;
            case BKeyEvent.VK_Y: character='y'; break;
            case BKeyEvent.VK_Z: character='z'; break;
            default: character=0; break;
          }
          if(character!=0)
          {
            int letterIndex = character-'a'; 
            if(!letters[letterIndex] && items[i].getText().toLowerCase().indexOf(character)>-1)
            {
              items[i].shortcut=character;
              letters[letterIndex] = true;
            }
          }
        }
      }
      
      // try to use the first letter from a word in the menu item text
      for(int i=0; i<items.length; i++)
      {
        if(items[i].shortcut==0)
        {
          String[] words = TextUtil.split(items[i].getText().toLowerCase(), ' ');
          for(int k=0; k<words.length; k++)
          {
            if(words[k].length() > 0 && words[k].charAt(0)>='a' && words[k].charAt(0)<='z')
            {
              int letterIndex = words[k].charAt(0)-'a'; 
              if(letters[letterIndex]==false)
              {
                items[i].shortcut=words[k].charAt(0);
                letters[letterIndex]=true;
                break;
              }
            }
          }
        }
      }          
      
      // try to use any letter from the menu item text
      for(int i=0; i<items.length; i++)
      {
        if(items[i].shortcut==0)
        {
          String text = items[i].getText().toLowerCase();
          for(int k=0; k<text.length(); k++)
          {
            if(text.charAt(k)>='a' && text.charAt(k)<='z')
            {
              int letterIndex = text.charAt(k)-'a'; 
              if(letters[letterIndex]==false)
              {
                items[i].shortcut=text.charAt(k);
                letters[letterIndex]=true;
                break;
              }
            }
          }
        }
      }
    } else {
      BMenuItem[] items = this.getChildren(BMenuItem.class);
      for(int i=0; i<items.length; i++)
        items[i].shortcut=0;
    }    
  }

  /**
   * This method will attempt to detect if the rendered menu
   * will have a height that is too big for the screen, and when
   * detected, it will try to rearrange the menus so that it will
   * fit.
   */
  private void fitToScreen()
  {
    if (fitToScreen) return; // Only do this once per instance
    fitToScreen = true;
    // TODO: Can it be that more menu items could be added to this instance after the first call
    // to this method? If that can happen, then we'd need to detect it so that we can re-compute
    // the "more" sub menus.  During my testing, it appears that a new instance is created each
    // time, so we're probably ok.

    double maxHeight = UiEnv.get().getScreenBounds(null).height(); // Max screen height
    boolean adjustMaxHeight = true; // We'll adjust the max height a little later
    BInsets mi = Theme.menu().getInsets();
    double insetHeight = mi.top + mi.bottom;
    double h = insetHeight; // Height accumulator
    BMenu moreMenu = null;

    BWidget[] items = getChildWidgets();
    for(BWidget item: items)
    {
      item.computePreferredSize();
      double currentHeight = item.getPreferredHeight();
      if (adjustMaxHeight && (item.getType().is(BMenuItem.TYPE)))
      { // Reduce the max screen height by the height of three menu items to give a little extra
        // room near the margins
        adjustMaxHeight = false;
        maxHeight -= (currentHeight * 3);
      }

      if ((currentHeight + h) >= maxHeight)
      { // The menu item won't fit within the max screen height, so create a 'More...' sub menu item
        // where we will move the remaining menu items.
        // I'm using the type the original BMenu to create the new sub menu (may not be necessary).
        // Just thought that a subclass of BMenu (with different styling) might be used, and this
        // would handle that scenario to keep things consistent looking
        BMenu newMoreMenu = (BMenu)getType().getInstance();
        newMoreMenu.setText(UiLexicon.bajaui().getText("menu.more.label"));
        newMoreMenu.fitToScreen = true; // Tell the new more menu to skip fitToScreen since we're doing it now

        if (moreMenu == null)
          add("more?", new BSubMenuItem(newMoreMenu));
        else
          moreMenu.add("more?", new BSubMenuItem(newMoreMenu));

        moreMenu = newMoreMenu;
        h = insetHeight; // reset the height accumulator

        if (item.getType().is(BSeparator.TYPE))
        { // Check for a separator, as it doesn't need to go on the top line of the new more menu
          remove(item.getName());
          continue;
        }
      }

      if (moreMenu != null)
      { // Move the item to the current 'More' menu
        String name = item.getName();
        remove(name);
        moreMenu.add(name, item);
      }

      h += currentHeight;
    }
  }
  
////////////////////////////////////////////////////////////////
// BIMenu
////////////////////////////////////////////////////////////////

  /**
   * Get an array of all the child menu items.
   */
  public BMenuItem[] getMenuItems()
  {
    Vector<BObject> v = new Vector<>();
    SlotCursor<Property> c = getProperties();
    while(c.nextComponent())
    {
      BObject obj = c.get();
      if (obj instanceof BMenuItem)
        v.addElement(obj);
    }
    
    BMenuItem[] items = new BMenuItem[v.size()];
    v.copyInto(items);
    return items;
  }

  @Override
  public int getItemCount()
  {
    return getMenuItems().length;
  }

  @Override
  public void addItem(String menuItemName, BIMenuItem menuItem)
  {
    add(menuItemName, menuItem.asWidget());
  }

  @Override
  public void addItemToFront(String menuItemName, BIMenuItem menuItem)
  {
     reorderToTop(add(menuItemName, menuItem.asWidget()));
  }

  @Override
  public BIMenuItem addItem(String menuItemName, Command menuItemCommand)
  {
    return add(menuItemName, menuItemCommand);
  }

  /**
   * Convenience for <code>((BSubMenuItem)get(name)).getMenu()</code>. 
   */
  public BMenu getSubMenu(String name)
  {                            
    return ((BSubMenuItem)get(name)).getMenu();
  }  

  /**
   * This method removes a SubMenuItem and returns it's menu
   * free to be mounted elsewhere.  It is a convenience method
   * often used with WbProfiles to move sub-menus around.
   */
  public BMenu removeSubMenu(String name)
  {                  
    BSubMenuItem item = (BSubMenuItem)get(name);
    BMenu menu = item.getMenu();
    remove(item);
    item.setMenu(new BMenu());
    return menu;          
  }

  /**
   * This method removes a MenuItem and returns it free 
   * to be mounted elsewhere.  It is a convenience method
   * often used with WbProfiles to move menu items around.
   */
  public BIMenuItem removeItem(String name)
  {
    BIMenuItem item = getItem(name);

    if(item!=null)
      remove(item.asWidget());

    return item;          
  }

  @Override
  public BIMenuItem getItem(String menuItemName)
  {
    return (BIMenuItem)get(menuItemName);
  }

  @Override
  public void retainItems(String[] menuItems)
  {
    keep(menuItems);
  }

  @Override
  public void addSeparator()
  {
    add("sep?", new BSeparator());
  }

  @Override
  public void addSeparatorToFront()
  {
    reorderToTop(add("sep?", new BSeparator()));
  }

  @Override
  public void update(){}

  @Override
  public boolean isDynamic()
  {
    return false;
  }

////////////////////////////////////////////////////////////////
// Show
////////////////////////////////////////////////////////////////

  /**
   * Is this menu currently opened?
   */
  public boolean isOpen()
  {
    return UiEnv.get().isPopupOpen(this);
  }

  /**
   * Show the menu at the given coordinate relative to
   * to the specified widget's coordinate space.  If
   * the specified widget implements MenuCloseListener,
   * then it will get a callback when the menu is closed.
   */
  public void open(BWidget owner, double x, double y)
  {
    update();

    // figure out if we should show accelerators based owner
    boolean showAccelerators = false;
    BComplex p = owner;
    while(p != null)
    {                                             
      if (p.fw(Fw.SHOW_ACCELERATORS) != null) { showAccelerators = true; break; }
      p = p.getParent();
    }
    
    // make environment specific call to open popup
    UiEnv.get().openPopup(this, owner, x, y);
    UiEnv.get().setPopupOpacity(this, Theme.menu().getFrameOpacity());
    
    // reset the menu items
    selected = null;
    BMenuItem[] items = getMenuItems();
    for(int i=0; i<items.length; ++i)
      items[i].opening(this, showAccelerators);
    
    // select the first enabled one
    for (int i=0; i<items.length; ++i)
      if (items[i].isEnabled())
        { select(items[i]); break; }
    
    this.owner = owner;
  }
 
  /**
   * Convenience for <code>open(event.getWidget(), event.getX(), event.getY())</code>.
   */
  public void open(BMouseEvent event)
  {
    open(event.getWidget(), event.getX(), event.getY());
  }
      
  /**
   * Close the popup menu.
   */
  void close()
  {
    UiEnv.get().closePopup(this);
    if(owner instanceof BMenuBar)
      owner.requestFocus();
  }
  
  /**
   * Select the specified item.
   */
  void select(BMenuItem item)
  {
    if (selected != null) selected.unselect();
    item.select();
    selected = item;
    insureSubItemPathSelected();
  }

  /**
   * Unselect the specified item.
   */
  void unselect(BMenuItem item)
  {       
    item.unselect();
    selected = null;
  }
  
  void insureSubItemPathSelected()
  {
    BObject parent = getParent();
    if (parent instanceof BSubMenuItem)
    {
      BSubMenuItem item = (BSubMenuItem)parent;
      BMenu menu = (BMenu)item.getParent();
      menu.select(item);
    }
  }
  
  /**
   * Move the current selection up.
   */
  void moveSelectionUp()
  {
    BMenuItem[] items = getMenuItems();
    int s = itemIndexOf(items, selected);
    for(int i=s-1; i>= 0; --i)
      if (items[i].isEnabled())
        { select(items[i]); return; }
    close();
  }

  /**
   * Move the current selection down.
   */
  void moveSelectionDown()
  {
    BMenuItem[] items = getMenuItems();
    int s = itemIndexOf(items, selected);
    for(int i=s+1; i<items.length; ++i)
      if (items[i].isEnabled())
        { select(items[i]); break; }
  }
  
  /**
   * Get the item index of the specified item.
   */
  int itemIndexOf(BMenuItem[] items, BMenuItem item)
  {
    for(int i=0; i<items.length; ++i)
      if (items[i] == item) return i;
    return -1;
  }
  
  /**
   * Move selection left.
   */
  void moveSelectionLeft()
  {
    BWidget parent = getParentWidget();
    if (parent instanceof BSubMenuItem)
    {
      ((BSubMenuItem)parent).closeMenu();
    }
    else if (parent instanceof BMenuBar)
    {
      ((BMenuBar)parent).openLeft();
    }
  }

  /**
   * Move selection right.
   */
  void moveSelectionRight()
  {
    BWidget parent = getParentWidget();
    if (selected instanceof BSubMenuItem)
    {
      ((BSubMenuItem)selected).openMenu();
    }
    else if (parent instanceof BMenuBar)
    {
      ((BMenuBar)parent).openRight();
    }
  }
  
////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////

  public boolean isFocusTraversable()
  {
    return true;
  }

  public void keyReleased(BKeyEvent event)
  {
    if (event.getModifiersEx() != 0) return;
    switch(event.getKeyCode())
    {
      case BKeyEvent.VK_ESCAPE:close();  event.consume(); break;
      case BKeyEvent.VK_UP:    moveSelectionUp();  event.consume(); break;
      case BKeyEvent.VK_DOWN:  moveSelectionDown(); event.consume(); break;
      case BKeyEvent.VK_LEFT:  moveSelectionLeft(); event.consume(); break;
      case BKeyEvent.VK_RIGHT: moveSelectionRight(); event.consume(); break;
      case BKeyEvent.VK_ENTER: 
        if (selected != null) selected.doClick(event); 
        event.consume();
        break;
      default:
        if(event.getKeyChar()>='a' && event.getKeyChar()<='z')
        {
          BMenuItem[] items = this.getChildren(BMenuItem.class);
          for(int i=0; i<items.length; i++)
          {
            if(items[i].shortcut==event.getKeyChar())
            {
              select(items[i]);
              items[i].doClick(event);
              event.consume();
              break;
            }
          }          
        }
    }
  }

////////////////////////////////////////////////////////////////
// Debugging
////////////////////////////////////////////////////////////////

  public String getDebugString()
  {
    return "\"" + getText() + "\"; ";
  }

////////////////////////////////////////////////////////////////
// MenuCloseListener
////////////////////////////////////////////////////////////////  

  /**
   * If the BWidget passed to the open() method implements
   * this interface, then it will receive the menuClosed
   * callback when the BMenu is closed.
   */
  public static interface MenuCloseListener
  {
    public void menuClosed(BMenu menu);
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/menu.png");
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static double acceleratorGap = 6;
  
  RectGeom menuBarRect = new RectGeom(0,0,0,0);   // set by menu bar in its layout
  /*pkg*/ char shortcut = 0;

  private BWidget owner;
  private double acceleratorX;
  private BMenuItem selected;
  private boolean fitToScreen = false;
    
}
