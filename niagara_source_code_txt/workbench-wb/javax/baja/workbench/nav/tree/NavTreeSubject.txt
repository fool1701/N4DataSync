/*
 * Copyright 2005, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.workbench.nav.tree;

import javax.baja.ui.tree.TreeNode;
import javax.baja.ui.tree.TreeSubject;

/**
 * NavTreeSubject
 * 
 * @author    Brian Frank
 * @creation  3 May 05
 * @version   $Revision: 2$ $Date: 6/11/07 12:41:49 PM EDT$
 * @since     Baja 1.0
 */
public class NavTreeSubject
  extends TreeSubject
{            

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
               
  /**
   * Convenience for <code>this(tree, nodes, null)</code>
   */
  public NavTreeSubject(BNavTree tree, TreeNode[] nodes)
  {                                         
    this(tree, nodes, null);
  }
  
  /**
   * Construct with specified list of row indices and active row index.
   */
  public NavTreeSubject(BNavTree tree, TreeNode[] nodes, TreeNode activeNode)
  {                                  
    super(tree, nodes, activeNode);
  }                     

  
}
