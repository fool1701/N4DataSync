/*
 * Copyright 2001, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.tree;

import javax.baja.gx.BBrush;
import javax.baja.gx.BFont;
import javax.baja.gx.BImage;
import javax.baja.gx.Graphics;
import javax.baja.gx.RectGeom;
import com.tridium.ui.PaintUtil;
import com.tridium.ui.theme.Theme;

/**
 * TreeNodeRenderer is the renderer for a node in the BTree.
 *
 * @author    John Sublett
 * @creation  06 Dec 2001
 * @version   $Revision: 22$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
public class TreeNodeRenderer
  extends BTree.TreeSupport
{
  /**
   * Get the default icon to use if the node does not provide an icon.
   */
  public BImage getDefaultIcon()
  {
    if (defaultIcon == null)
      defaultIcon = BImage.make("module://icons/x16/folder.png");
    
    return defaultIcon;
  }
  
  /**
   * Get the node's icon
   */
  public BImage getIcon(TreeNode node)
  {
    return node.getIcon();
  }  

  /**
   * Set the default icon for this renderer.
   */  
  public void setDefaultIcon(BImage def)
  {
    defaultIcon = def;
  }
  
  /**
   * Get the foreground brush.  This is the text brush for unselected nodes.
   */
  public BBrush getForeground(TreeNode node)
  {
    return Theme.tree().getForeground(getTree());
  }

  /**
   * Get the background brush for an unselected node.  By default this
   * returns null.  If null, the background defaults to the tree
   * background.
   */  
  public BBrush getBackground(TreeNode node)
  {
    return null;
  }

  /**
   * Get the selection foreground.  This is the text brush for selected nodes.
   */  
  public BBrush getSelectionForeground(TreeNode node)
  {
    return Theme.tree().getSelectionForeground();
  }

  /**
   * Get the selection background.
   */  
  public BBrush getSelectionBackground(TreeNode node)
  {
    return Theme.tree().getSelectionBackground();
  }
  
  /**
   * Get the width of the display area for the specified node.
   */  
  public double getWidth(TreeNode node)
  {
    return ICON_WIDTH + MARGIN + getTextFont().width(getNodeText(node)) + 2;
  }
  
  /**
   * Get the height of the display area for this renderer.  This must be
   * constant for all nodes.
   */
  public double getHeight()
  {
    BFont font = getTextFont();
    double textHeight = font.getAscent() + font.getDescent();
    
    // for certain themes (e.g. Curium) it is possible that the expander
    // height might be larger than both the icon and the font.  we need
    // to take this into account.
    double themeTreeHeight = Theme.tree().getExpanderHeight() + 
      Theme.tree().getInsets().top + Theme.tree().getInsets().bottom + 2;
    return Math.max(Math.max(ICON_HEIGHT, themeTreeHeight), textHeight - 1);
  }

  /**
   * Get the icon width.
   */  
  public double getIconWidth()
  {
    return ICON_WIDTH;
  }

  /**
   * Get the icon height.
   */  
  public double getIconHeight()
  {
    return ICON_HEIGHT;
  }
  
  /**
   * Get the text to display for the specified node.  This can be used
   * instead of node.getText() to gracefully handle the null text case.
   */
  public static String getNodeText(TreeNode node)
  {
    String text = node.getText();
    if (text == null)
      return "";
    else
      return text;
  }

  /**
  * Get the text <code>Font</code>.
  */
  public BFont getTextFont()
  {
    return Theme.tree().getFont(getTree());
  }
  
  /**
   * Paint the specified node.
   */
  public void paintNode(Graphics g, TreeNode node, double x, double y)
  {
    BFont font = getTextFont();
    BBrush background = getBackground(node);
    String text = getNodeText(node);
    double height = getHeight();
    double iconY = y + (height - ICON_HEIGHT) / 2;
    double baseline = y + (height - font.getAscent()) / 2 + font.getAscent() - 1;
    double textX = x + ICON_WIDTH + MARGIN;
    boolean selected = node.isSelected();
    boolean hasFocus = node.hasFocus();
    boolean isMouseOver   = node.isMouseOver();
    
    // if the node is not selected and the background brush is
    // not the tree background brush, then paint the node background    
    if (!selected && (background != null))
    {
      if (nodeBounds == null)
        nodeBounds = new RectGeom(x, y, getWidth(node), getHeight());
      else
        nodeBounds.set(x, y, getWidth(node), getHeight());

      g.setBrush(background);
      g.fillRect(nodeBounds.x, nodeBounds.y, nodeBounds.width, nodeBounds.height);
    }

    //NCCB-12548 - if this node is selected, fill the selection bounds of the node
    if(selectionBounds == null)
      selectionBounds = new RectGeom(0, node.getY(), tree.getWidth(), height);

    else selectionBounds.set(0, node.getY(), tree.getWidth(), height);

    if(selected)
      Theme.tree().paintFocus(g, selectionBounds);

    else if(isMouseOver)
    {
      PaintUtil.paintGlassRect(g,
        (int)selectionBounds.x, (int)selectionBounds.y,
        (int)selectionBounds.width, (int)selectionBounds.height, true);
    }

    // paint the icon.  If the node does not provide an icon, use the default.
    BImage icon = getIcon(node);
    if (icon == null)
      icon = getDefaultIcon();
    if (icon != null)
      g.drawImage(icon, x, iconY);
    
    //paint the node text
    g.setFont(font);
    g.setBrush(selected ? getSelectionForeground(node) : getForeground(node));
    g.drawString(text, textX, baseline);
  }

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  private static final double ICON_WIDTH  = 16;
  private static final double ICON_HEIGHT = 16;
  private static final double MARGIN      = 4;
  
  private BImage defaultIcon;
  
  private RectGeom nodeBounds;
  private RectGeom textBounds;
  private RectGeom selectionBounds;
  public boolean isMouseOver;
}