/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.nav.tree;

import javax.baja.gx.BBrush;
import javax.baja.gx.BColor;
import javax.baja.gx.BImage;
import javax.baja.space.BISpaceNode;
import javax.baja.ui.tree.TreeNode;
import javax.baja.ui.tree.TreeNodeRenderer;
import com.tridium.ui.theme.Theme;

/**
 * TreeNodeRenderer for BNavTree.
 *
 * @author    Brian Frank       
 * @creation  7 Mar 02
 * @version   $Revision: 6$ $Date: 6/22/11 3:44:46 PM EDT$
 * @since     Baja 1.0
 */
public class NavTreeNodeRenderer
  extends TreeNodeRenderer
{   

  public BBrush getForeground(TreeNode node)
  {
    NavTreeNode n = (NavTreeNode)node;
    
    // gray text if pending move
    if (n.getNavNode() instanceof BISpaceNode &&
        ((BISpaceNode)n.getNavNode()).isPendingMove())
      return cutFg;

    if (n.isDragOver) return Theme.widget().getDropOkForeground();
    return super.getForeground(node);
  }

  /**
   * Get the node's icon
   */
  public BImage getIcon(TreeNode node)
  {
    NavTreeNode n = (NavTreeNode)node;
    return node.getIcon();
  } 
  
  public BBrush getBackground(TreeNode node)
  {
    NavTreeNode n = (NavTreeNode)node;
    if (n.isDragOver) return Theme.widget().getDropOkBackground();
    return super.getBackground(node);
  }

  public BBrush getSelectionForeground(TreeNode node)
  {
    NavTreeNode n = (NavTreeNode)node;
    if (n.isDragOver) return Theme.widget().getDropOkForeground();
    return super.getSelectionForeground(node);
  }

  public BBrush getSelectionBackground(TreeNode node)
  {
    NavTreeNode n = (NavTreeNode)node;
    if (n.isDragOver) return Theme.widget().getDropOkBackground();
    return super.getSelectionBackground(node);
  }
  
  static final BBrush cutFg = BColor.make(100,100,100).toBrush();      
  
}
