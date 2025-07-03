/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.tree;

import java.util.ArrayList;
import javax.baja.gx.BImage;
import javax.baja.gx.Graphics;
import javax.baja.gx.IRectGeom;
import javax.baja.gx.RectGeom;
import javax.baja.gx.Size;
import javax.baja.ui.BWidgetShell;
import com.tridium.ui.theme.Theme;
import com.tridium.ui.theme.TreeTheme;

/**
 * TreeNode is the model for a single node in the BTree.
 *
 * @author    John Sublett
 * @creation  04 Dec 2000
 * @version   $Revision: 34$ $Date: 6/20/11 9:34:25 AM EDT$
 * @since     Niagara 1.0
 */
public abstract class TreeNode
{

  /**
   * Create a root node for the specified tree model and parent.
   */
  public TreeNode(TreeModel model, TreeNode parent)
  {
    this.model = model;
    this.parent = parent;
  }
  
  /**
   * Create a root node for the specified model.
   */
  public TreeNode(TreeModel model)
  {
    this.model = model;
  }
  
  /**
   * Create a tree node with the specified parent.
   */
  public TreeNode(TreeNode parent)
  {
    this.parent = parent;
  }
  
  public TreeModel getModel()
  {
    if (model == null)
    {
      TreeNode parent = getParent();
      if (parent != null)
        model = parent.getModel();
    }
    
    return model;
  }

  /**
   * Get the tree that contains this node.
   */  
  public BTree getTree()
  {
    return getModel().getTree();
  }

  /**
   * Convenience for getTree().getShell().
   */
  public BWidgetShell getShell()
  {
    BTree tree = getTree();
    if (tree != null) return tree.getShell();
    return null;
  }
  
  /**
   * Get the name of the tree node.  This will be the string displayed
   * in the tree.
   */
  public abstract String getText();

  /**
   * Get a string for this node.
   */  
  public String toString()
  {
    return getText();
  }
  
  /**
   * Get the icon for the tree node.  The icon should be no larger
   * than 16x16 pixels.
   */
  public abstract BImage getIcon();

  /**
   * Is the node a leaf node in the tree?
   */
  public boolean isLeaf()
  {
    return !hasChildren();
  }
  
  /**
   * Does this node have any children?
   */
  public boolean hasChildren()
  {
    return getChildCount() != 0;
  }
  
  /**
   * Get the number of children for this node.
   */
  public abstract int getChildCount();
    
  /**
   * Get the child at the specified index.
   */
  public abstract TreeNode getChild(int index);
  
  /**
   * Get the index of the specified child of this node.
   *
   * @param child The child at the returned index.
   * @return Returns the index of the specified child.  If the
   *   node is not a child of this node, then -1 is returned.
   */
  public int getChildIndex(TreeNode child)
  {
    int childCount = getChildCount();
    for (int i = 0; i < childCount; i++)
      if (getChild(i) == child)
        return i;
    
    return -1;
  }              
  
  /**
   * Get the subject of this node.  This object is used to
   * map the selection to a Subject instance.  The default
   * returns <code>getText()</code>, but this method should
   * usually be overridden.
   */
  public Object getSubject()
  {                        
    return getText();
  }  
  
  /**
   * Get the parent of this node.  If this node is a root, null should
   * be returned.
   */
  public TreeNode getParent()
  {
    return parent;
  }

  /**
   * Is this node a descendant of the specified node?
   */  
  public boolean isDescendantOf(TreeNode ancestor)
  {
    TreeNode parent = getParent();
    while((parent != null) && (parent != ancestor))
      parent = parent.getParent();
    
    return parent == ancestor;
  }

  /**
   * Get the path of this tree node starting from the root.
   */  
  public TreeNode[] getPathFromRoot()
  {
    // Add the nodes from here back to the root to the temp array list.
    // The result will be the reverse of the desired result.
    ArrayList<TreeNode> temp = new ArrayList<>(4);
    temp.add(this);
    TreeNode parent = getParent();
    while(parent != null)
    {
      temp.add(parent);
      parent = parent.getParent();
    }
    
    // Reverse the list and return it.
    int count = temp.size();
    TreeNode[] path = new TreeNode[count];
    for (int i = 0; i < count; i++)
      path[i] = temp.get(count - i - 1);
    
    return path;
  }

  /**
   * Make sure this node is still a valid member of the tree.  This
   * checks up the ancestry and back down to make sure all parent
   * child links are still intact.  It also verifies that the
   * root ancestor of this node is still a root in the tree.
   */  
  public boolean isValid()
  {
    TreeNode child = this;
    TreeNode parent = child.getParent();
    
    while(parent != null)
    {
      if (parent.getChildIndex(this) == -1)
        return false;
      
      child = parent;
      parent = child.getParent();
    }
    
    return model.getRootIndex(child) != -1;
  }

///////////////////////////////////////////////////////////
// Expansion
///////////////////////////////////////////////////////////
  
  /**
   * Set the expanded state of this node.
   */
  public final void setExpanded(boolean expanded)
  {
    this.expanded = expanded;
    if (this.expanded)
      expanded();
    else
      collapsed();
  }

  /**
   * Test the expanded state of this node.
   */  
  public final boolean isExpanded()
  {
    return expanded;
  }
  
  /**
   * Callback when the node is expanded.
   */
  public void expanded() {}
  
  /**
   * Callback when the node is collapsed.
   */
  public void collapsed() {}

///////////////////////////////////////////////////////////
// Selection
///////////////////////////////////////////////////////////  

  /**
   * Test the selected state of this node.
   */  
  public boolean isSelected()
  {
    return selected;
  }
  
  /**
   * Set the selected state of this node.
   */
  public void setSelected(boolean s)
  {
    selected = s;
  }

///////////////////////////////////////////////////////////
// Focus
///////////////////////////////////////////////////////////

  /**
   * Test the focus state of this node.
   */  
  public boolean hasFocus()
  {
    return focus;
  }
  
  /**
   * Set the focus state of this node.
   */
  void setFocus(boolean f)
  {
    focus = f;
  }


///////////////////////////////////////////////////////////
//Mouse Over
///////////////////////////////////////////////////////////

  public boolean isMouseOver()
  {
    return mouseOver;
  }

  public void setMouseOver(boolean over)
  {
    this.mouseOver = over;
  }

///////////////////////////////////////////////////////////
// Layout
///////////////////////////////////////////////////////////

  /**
   * Get the x location of this node.
   */
  public double getX()
  {
    return x;
  }
  
  /**
   * Get the y location of this node.
   */
  public double getY()
  {
    return y;
  }

  /**
   * Layout this node and all children.
   *
   * @param x The x location of the left edge of the display area for
   *   this node.
   * @param y The y location of the top edge of the display area for
   *   this node.
   * @param isTop Is this node the first child of its parent.
   * @param isLast Is this node the last child of its parent.
   */
  double layout(double x, double y,
                boolean isTop, boolean isLast,
                ArrayList<TreeNode> visibleNodes,
                Size prefSize)
  {
    this.x = x;
    this.y = y;
    this.isTop = isTop;
    this.isLast = isLast;
    
    visibleNodes.add(this);
    
    BTree tree = getTree();
    if (tree == null)
    {
      System.out.println("ERROR: TreeNode.layout no tree: " + getClass().getName());
      return 0;
    }
    
    TreeTheme theme = Theme.tree();
    TreeNodeRenderer renderer = tree.getNodeRenderer();
    
    double nodeHeight = renderer.getHeight();
    
    height = getTree().getRowHeight();
    expanderWidth = theme.getExpanderWidth();
    expanderHeight = theme.getExpanderHeight();
    expanderX = x;
    expanderY = y + (height - expanderHeight) / 2;
    nodeX = expanderX + expanderWidth + MARGIN;
    nodeY = y + (height - nodeHeight) / 2;
    
    y += height;
    if (expanded)
    {
      RectGeom visRect = null;
      if (getTree().widgetSupport(this) != null)
        visRect = new RectGeom(0, y - height, getTree().getWidth(), height);

      double childX = nodeX + (renderer.getIconWidth() - expanderWidth) / 2;
      int childCount = getChildCount();
      for (int i = 0; i < childCount; i++)
        y = getChild(i).layout(childX, y,
                               false, i == childCount - 1,
                               visibleNodes, prefSize);
      
      if (visRect != null)
      {
        visRect.height = y - visRect.y;
        getTree().widgetSupport(visRect);
      }
    }
    
    prefSize.width = Math.max(prefSize.width, nodeX + renderer.getWidth(this));
    
    return y;
  }

  /**
   * Is the specified location in the expander for this node.
   */  
  public boolean inExpander(double x, double y)
  {
    return Theme.tree().inExpander(expanderX, expanderY, x, y);
  }

  /**
   * Is the specified location in the selection bounds for this node.
   */  
  public boolean isSelection(double x, double y)
  {
    BTree tree = getTree();
    TreeNodeRenderer renderer = tree.getNodeRenderer();
    double nodeWidth = renderer.getWidth(this);
    return (x >= nodeX) && (x <= nodeX + nodeWidth);
  }

  /**
   * Get the width of this node including the expander, icon, and text.
   */  
  public double getWidth()
  {
    return expanderWidth + MARGIN + getTree().getNodeRenderer().getWidth(this);
  }

///////////////////////////////////////////////////////////
// Paint
///////////////////////////////////////////////////////////

  /**
   * Paint this node.
   */
  double paint(Graphics g, double prevY)
  {
    // wrap in a try block since there are lots of places
    // we access client hooks which could throw an exception
    try
    {
      TreeTheme theme = Theme.tree();
      TreeNodeRenderer renderer = getTree().getNodeRenderer();

      //render the node (excluding the expander icon)
      renderer.paintNode(g, this, nodeX, nodeY);

      //overlay the expander icon to the left of the rendered node
      //the overlay occurs after painting the node to prevent
      //mouseover effects from obscuring the expander icon
      boolean leaf = isLeaf();
      if (!leaf)
        theme.paintExpander(g, getTree(), expanderX, expanderY, expanded);

      if (expanded)
      {
        IRectGeom clip = g.getClipBounds();
        double viewTop = clip.y();
        double viewBottom = clip.y() + clip.height();
        
        prevY = y + height;
        int childCount = getChildCount();
        boolean belowBottom = false;
        for (int i = 0; i < childCount; i++)
        {
          TreeNode thisChild = getChild(i);
          
          // don't paint children below the bottom of the clip bounds
          if (thisChild.getY() > viewBottom)
          {
            // always draw the first child below the bottom, so 
            // we get the dotted line, but stop after the first. 
            if(belowBottom)
              break;
            else
              belowBottom = true;
          }
          
          // don't paint children whose entire subtree is above the
          // top of the clip bounds
          TreeNode nextChild = (i == childCount - 1) ? null : getChild(i + 1);
          if ((nextChild != null) && (nextChild.getY() <= viewTop))
            continue;
            
          prevY = getChild(i).paint(g, prevY);
        }
      }
      
      return y + height;
    }
    catch(Throwable e)
    {
      e.printStackTrace();
      return y + height;
    }
  }

///////////////////////////////////////////////////////////
// Constants
///////////////////////////////////////////////////////////

  private static final double MARGIN = 10;

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  private TreeModel model;
  private TreeNode  parent;

  private double x;
  private double y;
  private boolean isTop = true;
  private boolean isLast = true;
  
  private double height;
  private double expanderX;
  private double expanderWidth;
  private double expanderY;
  private double expanderHeight;
  private double nodeX;
  private double nodeY;
  
  private boolean expanded = false;
  private boolean selected = false;
  private boolean focus    = false;
  private boolean mouseOver = false;

}