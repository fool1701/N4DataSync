/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.nav.tree;

import javax.baja.naming.BOrd;
import javax.baja.nav.BINavNode;
import javax.baja.ui.BHyperlinkMode;
import javax.baja.ui.BMenu;
import javax.baja.ui.BSeparator;
import javax.baja.ui.BWidget;
import javax.baja.ui.BWidgetShell;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.HyperlinkInfo;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.event.BMouseWheelEvent;
import javax.baja.ui.tree.BTree;
import javax.baja.ui.tree.TreeController;
import javax.baja.ui.tree.TreeNode;
import javax.baja.ui.tree.TreeSubject;
import javax.baja.ui.util.UiLexicon;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.nav.menu.NavMenuUtil;
import com.tridium.fox.sys.BFoxSession;
import com.tridium.workbench.shell.WbCommands.RefreshCommand;

/**
 * TreeController for BNavTree.
 *
 * @author    Brian Frank       
 * @creation  7 Mar 02
 * @version   $Revision: 13$ $Date: 11/22/06 5:04:22 PM EST$
 * @since     Baja 1.0
 */
public class NavTreeController
  extends TreeController
{   

////////////////////////////////////////////////////////////////
// TreeController
////////////////////////////////////////////////////////////////

  protected void doSelectAction(TreeNode target, double x, double y)
  {
    getTree().setExpanded(target, true);

    BWidgetShell shell = getShell();
    NavTreeNode n = (NavTreeNode)target;
    BOrd ord = n.navNode.getNavOrd();
    if (shell instanceof BWbShell && ord != null)
    {
      ((BWbShell)shell).hyperlink(new HyperlinkInfo(ord, BHyperlinkMode.DEFAULT));
    }
  }

  /**
   * Overridden so we can refresh the session as well as perform
   * the actions of the superclass method.
   */
  @Override
  public void mousePressed(BMouseEvent event)
  {
    super.mousePressed(event);
    double y = event.getY();
    NavTreeNode target = (NavTreeNode) getTree().yToTreeNode(y);
    if (target != null)
    {
      target.resetSession();
    }
  }

  public void keyPressed(BKeyEvent event)
  {
    super.keyPressed(event);
    if (event.getKeyCode() == BKeyEvent.VK_F10)
      ((NavTreeModel)getModel()).dumpOrdMap();
  }

  /**
   * Use <code>makePopup(NavTreeSubject)</code>.
   */              
  protected final BMenu makePopup(TreeSubject subject)
  {                                    
    return makePopup((NavTreeSubject)subject);
  }

  /**
   * Create popup menu for specified selection.
   */              
  protected BMenu makePopup(NavTreeSubject subject)
  {    
    // get menu for selection                               
    BTree tree = getTree();
    BMenu menu = NavMenuUtil.makeMenu(tree, subject);
    
    // only only one item in selection, provide refresh 
    if (subject.size() == 1)
    {
      if (menu == null) menu = new BMenu();
      else menu.add(null, new BSeparator());
      menu.add(null, new RefreshCommand(tree, (NavTreeNode)subject.getNode(0)));
    }
    
    return menu;
  }

  /**
   * Double click performs a hyperlink. 
   */
  public void nodeDoubleClicked(BMouseEvent event, TreeNode node) 
  {                            
    BWidgetShell shell = getShell();
    NavTreeNode n = (NavTreeNode)node;
    BOrd ord = n.navNode.getNavOrd();
    if (shell instanceof BWbShell && ord != null)
    {
      ((BWbShell)shell).hyperlink(new HyperlinkInfo(ord, event));
    }
    n.resetSession();
  }

  /** If the user scrolls, refresh the session and perform the action
   * in the superclass.
   */
   @Override
  public boolean pulseViewport(double x, double y)
  {
    NavTreeNode n = (NavTreeNode) getTree().yToTreeNode(y);
    n.resetSession();
    return super.pulseViewport(x, y);
  }

////////////////////////////////////////////////////////////////
// Commands
////////////////////////////////////////////////////////////////

  class RefreshCommand extends Command
  {
    RefreshCommand(BWidget owner, NavTreeNode node) 
    { 
      super(owner, UiLexicon.bajaui().module, "tree.refresh"); 
      this.node = node;
    }
    
    public CommandArtifact doInvoke()
      throws Exception
    {
      node.refresh();
      node.resetSession();
      return null;
    }
    
    NavTreeNode node;
  }

  public void setFocus(TreeNode node)
  {
    super.setFocus(node);
    if (node != null)
    {
      ((NavTreeNode) node).resetSession();
    }
  }
}
