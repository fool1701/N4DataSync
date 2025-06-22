/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.nav.tree;

import javax.baja.ui.tree.TreeNode;
import javax.baja.ui.tree.TreeSelection;
import javax.baja.ui.tree.TreeSubject;

/**
 * TreeSelection for BNavTree.
 *
 * @author    Brian Frank       
 * @creation  7 Mar 02
 * @version   $Revision: 4$ $Date: 11/21/06 12:24:19 PM EST$
 * @since     Baja 1.0
 */
public class NavTreeSelection
  extends TreeSelection
{   

  /**
   * Return NavTreeSubject instance.
   */
  public TreeSubject getSubject(TreeNode activeNode)
  {                   
    return new NavTreeSubject((BNavTree)getTree(), getNodes(), activeNode);                  
  }  

  public void updateTree()
  {
    super.updateTree();
    
    BNavTree tree = (BNavTree)getTree();
    boolean v = tree.getSelectionParent() != null;

    tree.setCutEnabled(v);
    tree.setPasteEnabled(v);
    tree.setPasteSpecialEnabled(v);
    tree.setDuplicateEnabled(v);
    tree.setDeleteEnabled(v);
  }
}
