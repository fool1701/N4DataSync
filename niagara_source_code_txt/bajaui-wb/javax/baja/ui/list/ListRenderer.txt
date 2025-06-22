/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.list;

import javax.baja.gx.*;
import javax.baja.ui.*;
import com.tridium.ui.theme.*;

/**
 * ListRenderer is used to render the individual items of a BList.
 *
 * @author    Brian Frank
 * @creation  9 Jul 02
 * @version   $Revision: 22$ $Date: 6/29/11 12:15:44 PM EDT$
 * @since     Baja 1.0
 */
public class ListRenderer
  extends BList.ListSupport
{
  
  /**
   * Get the foreground used to paint the item text.
   */
  public BBrush getForeground(Item item)
  {
    BList list = getList();
    BWidget parent = list.getParentWidget();    
    if (getList().isEnabled() && (parent == null || parent.isEnabled()))
      return Theme.dropDown().getTextBrush(list);
    else
      return Theme.textEditor().getDisabledTextBrush(list);
  }

  /**
   * Get the background used to paint the item.  Return 
   * null if the standard background should be used.
   */
  public BBrush getBackground(Item item)
  {
    return null;
  }

  /**
   * Get the selection foreground used to paint the item text.
   */
  public BBrush getSelectionForeground(Item item)
  {
    return Theme.dropDown().getSelectionForeground(getList());
  }

  /**
   * Get the selection background used to paint the item.
   */
  public BBrush getSelectionBackground(Item item)
  {
    return Theme.dropDown().getSelectionBackground(getList());
  }

  /**
   * Get the fixed height of the item cells.  A given BList
   * always has the same height across all of its items based
   * on the result of this method.
   */
  public double getItemHeight()
  {
    return Math.max(Theme.dropDown().getTextFont(getList()).getHeight() + 5, 18);
  }

  /**
   * Get the preferred width of the specified item.
   */
  public double getPreferredItemWidth(Item item)
  {
    String s = getItemText(item);
    double w = Theme.table().getCellFont().width(s) + 12;
    if (item.icon != null) w += 18;
    return w;
  }
  
  /**
   * Paint the item to the specified graphics context.  The
   * graphics context will be translated so that the origin 
   * of the item is 0,0.  This method calls paintItemBackground()
   * to fill the background and set the foreground color.
   * Then the icon and text are painted.
   */
  public void paintItem(Graphics g, Item item)
  {
    paintItemBackground(g, item);

    double x = 4;            
    if (item.icon != null)
    {
      g.drawImage(item.icon, x, (item.height - item.icon.getHeight()) / 2);
      x += 20;
    }

    String s = getItemText(item);
    
    /*
    switch(model.getColumnAlignment(item.column).getOrdinal())
    {
      case BHalign.RIGHT:
        x = item.width - 2 - Theme.table().getItemFontMetrics().stringWidth(s);
        break;
      case BHalign.CENTER:
        x = (item.width - Theme.table().getItemFontMetrics().stringWidth(s))/2;
        break;
    }
    */

    BFont font = Theme.dropDown().getTextFont(this.list);
    double th = font.getHeight();
    double ta = font.getAscent();
    double ty = (item.height - th) / 2;
    
    g.setFont(font);
    //g.drawString(s, x, Theme.table().getCellFont().getAscent()+2);//item.height-3);
    g.drawString(s, x, ty + ta);
  }

  /**
   * Get the text to display for the specified item.  Default
   * is to return <code>String.valueOf(item.value)</code>.
   */
  public String getItemText(Item item)
  {
    return String.valueOf(item.value);
  }
  
  /**
   * This is the first method of paintItem() which paints
   * the background and then sets the graphics context with
   * the current foreground color (depending on selection
   * state).
   */
  protected void paintItemBackground(Graphics g, Item item)
  {
    if (item.selected)
    {
      BBrush bg = getSelectionBackground(item);
      if (bg != null)
      {
        g.setBrush(bg);
        g.fillRect(0, 0, item.width - 1, item.height - 1);
      }
      g.setBrush(getSelectionForeground(item));
    }
    else
    {
      BBrush bg = getBackground(item);
      if (bg != null)
      {
        g.setBrush(bg);
        g.fillRect(0, 0, item.width, item.height);
      }
      g.setBrush(getForeground(item));
    }
    
    g.setFont(Theme.table().getCellFont());
  }
  
////////////////////////////////////////////////////////////////
// Item
////////////////////////////////////////////////////////////////  

  /**
   * This structure is used to pass the item information 
   * to the the paintItem() method.
   */
  public static class Item
  {
    /** Zero based item index. */
    public int index;

    /** Icon of the item as returned from ListModel. */
    public BImage icon;
    
    /** Item value as returned from ListModel. */
    public Object value;
        
    /** Width in pixels of the item item. */
    public double width;
    
    /** Height in pixels of the item item. */
    public double height;
    
    /** Is the item currently selected. */
    public boolean selected;
  }

}