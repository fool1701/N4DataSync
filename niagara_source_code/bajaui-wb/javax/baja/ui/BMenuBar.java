/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.gx.BFont;
import javax.baja.gx.BInsets;
import javax.baja.gx.Graphics;
import javax.baja.gx.Point;
import javax.baja.gx.RectGeom;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.event.BFocusEvent;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.menu.BIMenu;
import javax.baja.ui.menu.BIMenuBar;

import com.tridium.sys.schema.Fw;
import com.tridium.ui.theme.MenuBarTheme;
import com.tridium.ui.theme.Theme;

/**
 * BMenuBar is bar of buttons designed to expose a
 * set of BMenu's.
 *
 * @author    Brian Frank       
 * @creation  29 Dec 00
 * @version   $Revision: 36$ $Date: 6/21/11 11:28:52 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BMenuBar
  extends BAbstractBar
  implements BMenu.MenuCloseListener, BIMenuBar
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BMenuBar(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMenuBar.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Component
////////////////////////////////////////////////////////////////

  public void started() throws Exception
  {
    getShell().setMenuBar(this);
  }

  public void stopped() throws Exception
  {
    getShell().setMenuBar(null);
  }

////////////////////////////////////////////////////////////////
// BIMenuBar
////////////////////////////////////////////////////////////////
  
  public void setId(String id) {}
  
  public BWidget asWidget()
  {
    return this;
  }

  /**
   * Get the child menus.
   */
  public BIMenu[] getMenus()
  {
    return getChildren(BMenu.class);
  }

  /**
   * Add a menu
   * @param menuName
   * @param menu
   */
  public void addMenu(String menuName, BIMenu menu)
  {
    add(menuName, menu.asWidget());
  }

  public void setMenu(String menuName, BIMenu menu)
  {
    set(menuName, menu.asWidget());
  }

  /**
   * Get a child menu by name
   *
   * @param menuName
   * @return
   */
  public BIMenu getMenu(String menuName)
  {
    return (BIMenu)get(menuName);
  }

  /**
   * Remove a child menu by name
   * @param menuName
   * @return
   */
  public BIMenu removeMenu(String menuName)
  {
    BIMenu menu = getMenu(menuName);
    removeMenu(menu);
    return menu;
  }

  /**
   * Remove a child menu by reference
   * @param menu
   */
  public void removeMenu(BIMenu menu)
  {
    remove(menu.asWidget());
  }

  /**
   * Remove all menus
   */
  public void removeAllMenus()
  {
    removeAll();
  }

  /**
   * Add a quick search to this menu bar.
   * @param quickSearch the quick search widget to add
   */
  public void addQuickSearch(BWidget quickSearch)
  {
    throw new UnsupportedOperationException("QuickSearch not supported on BMenuBar");
  }

  /**
   * Remove the quick search from this menu bar.
   */
  public void removeQuickSearch()
  {
    throw new UnsupportedOperationException("QuickSearch not supported on BMenuBar");
  }
  
////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Compute the menu bar's preferred size.
   */
  public void computePreferredSize()
  {
    MenuBarTheme theme = Theme.menuBar();
    BInsets bi = theme.getBarInsets();
    BInsets ii = theme.getItemInsets();
    BFont font = theme.getItemFont(this);
    double x = bi.left + itemGap, y = bi.top;
    double w = 0, h = font.getHeight() + ii.top + ii.bottom;
    this.ty = bi.top + ii.top + font.getHeight() - font.getDescent();
    
    BWidget[] items = getChildWidgets();
    for(int i=0; i<items.length; ++i)
    {
      BMenu item = (BMenu)items[i];
      w = font.width(item.getText()) + ii.left + ii.right;
      item.menuBarRect = new RectGeom(x, y, w, h);
      x += (w + itemGap);
    }
    
    setPreferredSize(x+w+bi.right, y+h+bi.top);
  }
  
  /**
   * Layout the menu bar.
   */
  public void doLayout(BWidget[] kids)
  { 
    computePreferredSize();
  }

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
// Paint
////////////////////////////////////////////////////////////////
  
  /**
   * Paint the menu bar.
   */ 
  public void paint(Graphics g)
  {
    MenuBarTheme theme = Theme.menuBar();
    BInsets insets = theme.getItemInsets();
    theme.paintBackground(g, this);

    computeShortcutKeys();
    
    BMenu[] items = getChildren(BMenu.class);
    g.setFont(theme.getTextFont(this));
    for(int i=0; i<items.length; ++i)
    {
      BMenu item = items[i];
      String text = item.getText();
      double tx = item.menuBarRect.x + insets.right;

      int state = MenuBarTheme.DEFAULT;
      if (item == open) state = MenuBarTheme.SELECTED;
      else if ((open==null || !open.isOpen()) && (hasFocus() || mouseOver) && item == over) state = MenuBarTheme.MOUSE_OVER;
      
      theme.paintItemBackground(g, this, item.menuBarRect, state);
      g.setBrush(theme.getItemTextBrush(state, this));
      Point offset = theme.getItemTextOffset(state);
      
      if(!hasFocus())
        g.drawString(text, tx + offset.x, ty + offset.y);
      else
      {
        double textOffset=tx + offset.x;
        int shortcutIndex = text.toLowerCase().indexOf(Character.toLowerCase(items[i].shortcut));
        if(shortcutIndex>0)
        {
          String textPart = text.substring(0, shortcutIndex);
          g.drawString(textPart, textOffset, ty + offset.y);
          textOffset += g.getFont().width(textPart);
        }
        if (shortcutIndex > -1)
        {
          String textPart = text.substring(shortcutIndex, shortcutIndex+1);
          BFont underline = BFont.make(theme.getTextFont(), theme.getTextFont().getStyle()|BFont.UNDERLINE); 
          g.setFont(underline);
          g.drawString(textPart, textOffset, ty + offset.y);
          textOffset += underline.width(textPart);
        }        
        if(shortcutIndex<text.length())
        {
          String textPart = text.substring(shortcutIndex+1, text.length());
          g.setFont(theme.getTextFont());
          g.drawString(textPart, textOffset, ty + offset.y);
        }      
      }      
    }
  }
  
  public String getStyleSelector() { return "menu-bar"; }

  /**
   * Determine the shortcut key to be used for quick access to each of
   * the menu items.  The shortcut keys must be unique for ecah item 
   * on the menu.
   */ 
  private void computeShortcutKeys()
  {
    if(hasFocus())
    {
      BMenu[] items = this.getChildren(BMenu.class);

      // The algorythm to automatically select shortcut keys
      // for menu items follows three steps:
      // 1) First try give the menu item a shortcut key matching 
      //    the first letter of a word the menu item's text
      // 2) Then try to give it the shortcut key for any character
      //    contained within the text
      // 3) If that fails, the menu item does not have a shortcut 
      //    key
            
      boolean[] letters = new boolean[26];

      // Prime the list with already defined shortcut keys
      for(int i=0; i<items.length; i++)
        if(items[i].shortcut>0)
          letters[items[i].shortcut-'a']=true;
      
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
  
////////////////////////////////////////////////////////////////
// Open
////////////////////////////////////////////////////////////////  

  /**
   * Called by open menu when the left key is pressed.
   */
  void openLeft()
  {
    BWidget[] children = getChildWidgets();
    for(int i=0; i<children.length-1; ++i)
      if (children[i+1] == open)
        { open( (BMenu)children[i] ); return; }
    if (children.length > 0)
      open( (BMenu)children[children.length-1] );      
  }

  /**
   * Called by open menu when the right key is pressed.
   */
  void openRight()
  {
    BWidget[] children = getChildWidgets();
    for(int i=1; i<children.length; ++i)
      if (children[i-1] == open)
        { open( (BMenu)children[i] ); return; }
    if (children.length > 0)
      open( (BMenu)children[0] );    
  }
  
  /**
   * Open the given menu.
   */
  private void open(BMenu menu)
  {
    if (open != null) open.close();
    RectGeom r = menu.menuBarRect;
    menu.open(this, r.x, r.y + r.height);
    menu.requestFocus();
    over = open = menu;
  }
  
  /**
   * Open the given menu.
   */
  public void focus(BMenu menu)
  {
    requestFocus();
    over = menu;
  }  

////////////////////////////////////////////////////////////////
//Selected
////////////////////////////////////////////////////////////////
  
  /**
   * Called by open menu when the left key is pressed.
   */
  void moveSelectionLeft()
  {
    BWidget[] children = getChildWidgets();
    for(int i=0; i<children.length-1; ++i)
      if (children[i+1] == over)
        { over = (BMenu)children[i]; return; }
    if (children.length > 0)
      over = (BMenu)children[children.length-1];      
  }

  /**
   * Called by open menu when the right key is pressed.
   */
  void moveSelectionRight()
  {
    BWidget[] children = getChildWidgets();
    for(int i=1; i<children.length; ++i)
      if (children[i-1] == over)
        { over = (BMenu)children[i]; return; }
    if (children.length > 0)
      over = (BMenu)children[0];    
  }  
  
////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////  
  
  public void focusGained(BFocusEvent event)
  {
    repaint();
    super.focusGained(event);
  }  
  
  public void focusLost(BFocusEvent event)
  {
    repaint();
    super.focusLost(event);
  }  
  
  public void menuClosed(BMenu menu)
  {
    open = null;
    repaint();
  }

  public void mouseEntered(BMouseEvent event)
  {
    mouseOver = true;
    over = itemAt(event);
    repaint();
  }

  public void mouseExited(BMouseEvent event)
  {
    mouseOver = false;
    repaint();
  }

  public void mouseMoved(BMouseEvent event)
  {
    BMenu last = over;
    over = itemAt(event);
    if (over != null && open != null && open != over) open(over);      
    if (last != over) repaint();
  }

  public void mousePressed(BMouseEvent event)
  {
    if (over != null) open(over);
  }

  public void mouseReleased(BMouseEvent event)
  {
    if(open!=null && open.isOpen()) open.requestFocus();
  }
    
  private BMenu itemAt(BMouseEvent event)
  {
    double x = event.getX(), y = event.getY();
    BWidget[] items = getChildWidgets();
    for(int i=0; i<items.length; ++i)
    {
      BMenu item = (BMenu)items[i];
      if (item.menuBarRect.contains(x, y))
        return item;
    }
    return null;
  }

  public void keyReleased(BKeyEvent event)
  {
    if (event.getModifiersEx() != 0) return;
    switch(event.getKeyCode())
    {
      case BKeyEvent.VK_DOWN:  open(over); event.consume(); break;
      case BKeyEvent.VK_LEFT:  moveSelectionLeft(); event.consume(); repaint(); break;
      case BKeyEvent.VK_RIGHT: moveSelectionRight(); event.consume(); repaint(); break;
      default:
        if(event.getKeyChar()>='a' && event.getKeyChar()<='z')
        {
          BMenu[] items = this.getChildren(BMenu.class);
          for(int i=0; i<items.length; i++)
          {
            if(items[i].shortcut==event.getKeyChar())
            {
              open(items[i]);
              event.consume();
              break;
            }
          }          
        }    
      }
  }  
  
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {   
      case Fw.SHOW_ACCELERATORS: return this;
    }
    return super.fw(x, a, b, c, d);
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/menuBar.png");
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static double itemGap = 4;
  
  private boolean mouseOver = false;
  private double ty;   // text y
  private BMenu over;  // last item mouse was over
  private BMenu open;  // open menu if there is one
  
}
