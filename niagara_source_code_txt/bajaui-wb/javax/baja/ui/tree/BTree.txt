/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.tree;

import java.util.*;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.event.*;
import javax.baja.ui.pane.*;
import javax.baja.ui.transfer.*;

import com.tridium.ui.theme.*;

/**
 * BTree is a widget for displaying a tree structure.
 * This is not a reference to the B-Tree data structure.
 *
 * @author    John Sublett
 * @author    Mike Jarmy
 * @creation  04 Dec 2000
 * @version   $Revision: 63$ $Date: 6/20/11 9:34:25 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 If true then multiple rows may be selected, or if
 false then only row at a time may be selected.
 */
@NiagaraProperty(
  name = "multipleSelection",
  type = "boolean",
  defaultValue = "false"
)
/*
 Event fired when the user presses the Enter key
 or selects a row with the mouse.
 */
@NiagaraTopic(
  name = "actionPerformed",
  eventType = "BWidgetEvent"
)
/*
 Fired when the user presses the Esc key.
 */
@NiagaraTopic(
  name = "cancelled",
  eventType = "BWidgetEvent"
)
/*
 Fired when the tree contents are modified.
 */
@NiagaraTopic(
  name = "treeModified",
  eventType = "BWidgetEvent"
)
/*
 Fired when the table selection is modified.
 */
@NiagaraTopic(
  name = "selectionModified",
  eventType = "BWidgetEvent"
)
public class BTree
  extends BTransferWidget
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.tree.BTree(3052419929)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "multipleSelection"

  /**
   * Slot for the {@code multipleSelection} property.
   * If true then multiple rows may be selected, or if
   * false then only row at a time may be selected.
   * @see #getMultipleSelection
   * @see #setMultipleSelection
   */
  public static final Property multipleSelection = newProperty(0, false, null);

  /**
   * Get the {@code multipleSelection} property.
   * If true then multiple rows may be selected, or if
   * false then only row at a time may be selected.
   * @see #multipleSelection
   */
  public boolean getMultipleSelection() { return getBoolean(multipleSelection); }

  /**
   * Set the {@code multipleSelection} property.
   * If true then multiple rows may be selected, or if
   * false then only row at a time may be selected.
   * @see #multipleSelection
   */
  public void setMultipleSelection(boolean v) { setBoolean(multipleSelection, v, null); }

  //endregion Property "multipleSelection"

  //region Topic "actionPerformed"

  /**
   * Slot for the {@code actionPerformed} topic.
   * Event fired when the user presses the Enter key
   * or selects a row with the mouse.
   * @see #fireActionPerformed
   */
  public static final Topic actionPerformed = newTopic(0, null);

  /**
   * Fire an event for the {@code actionPerformed} topic.
   * Event fired when the user presses the Enter key
   * or selects a row with the mouse.
   * @see #actionPerformed
   */
  public void fireActionPerformed(BWidgetEvent event) { fire(actionPerformed, event, null); }

  //endregion Topic "actionPerformed"

  //region Topic "cancelled"

  /**
   * Slot for the {@code cancelled} topic.
   * Fired when the user presses the Esc key.
   * @see #fireCancelled
   */
  public static final Topic cancelled = newTopic(0, null);

  /**
   * Fire an event for the {@code cancelled} topic.
   * Fired when the user presses the Esc key.
   * @see #cancelled
   */
  public void fireCancelled(BWidgetEvent event) { fire(cancelled, event, null); }

  //endregion Topic "cancelled"

  //region Topic "treeModified"

  /**
   * Slot for the {@code treeModified} topic.
   * Fired when the tree contents are modified.
   * @see #fireTreeModified
   */
  public static final Topic treeModified = newTopic(0, null);

  /**
   * Fire an event for the {@code treeModified} topic.
   * Fired when the tree contents are modified.
   * @see #treeModified
   */
  public void fireTreeModified(BWidgetEvent event) { fire(treeModified, event, null); }

  //endregion Topic "treeModified"

  //region Topic "selectionModified"

  /**
   * Slot for the {@code selectionModified} topic.
   * Fired when the table selection is modified.
   * @see #fireSelectionModified
   */
  public static final Topic selectionModified = newTopic(0, null);

  /**
   * Fire an event for the {@code selectionModified} topic.
   * Fired when the table selection is modified.
   * @see #selectionModified
   */
  public void fireSelectionModified(BWidgetEvent event) { fire(selectionModified, event, null); }

  //endregion Topic "selectionModified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTree.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BTree()
  {
    this(new TreeModel.DefaultTreeModel(), new TreeController());
  }

  /**
   * Create a tree with the specified model.
   *
   * @param model The data model that specifies the structure
   *   of the tree.
   */
  public BTree(TreeModel model)
  {
    this(model, new TreeController());
  }

  /**
   * Create a tree with the specified model and controller.
   *
   * @param model The data model that specifies the structure
   *   of the tree.
   * @param controller The input handler for the tree.
   */  
  public BTree(TreeModel model, TreeController controller)
  {
    setModel(model);
    setController(controller);
    setNodeRenderer(new TreeNodeRenderer());
    setSelection(new TreeSelection());
  }

  /**
   * Get the model for this tree.
   */
  public TreeModel getModel()
  {
    return model;
  }

  /**
   * Set the model for this tree.
   */  
  public void setModel(TreeModel model)
  {
    installSupport(this.model, model);
    this.model = model;
    visibleNodes = new ArrayList<>(64);
    toVisible     = null;
    lastExpand    = null;
    toVisibleRect = null;
  }

  /**
   * Get the controller for this tree.
   */
  public TreeController getController()
  {
    return controller;
  }
  
  /**
   * Set the controller for this tree.
   */  
  public void setController(TreeController controller)
  {
    installSupport(this.controller, controller);
    this.controller = controller;
  }

  /**
   * Get the renderer for the nodes in this tree.
   */  
  public TreeNodeRenderer getNodeRenderer()
  {
    return nodeRenderer;
  }

  /**
   * Set the node renderer for this tree.
   */  
  public void setNodeRenderer(TreeNodeRenderer r)
  {
    installSupport(this.nodeRenderer, r);
    nodeRenderer = r;
  }

  /**
   * Get the selection model for this tree.
   */  
  public TreeSelection getSelection()
  {
    return selection;
  }
  
  /**
   * Set the selection model for this tree.
   */
  public void setSelection(TreeSelection selModel)
  {
    installSupport(this.selection, selModel);
    selection = selModel;
  }

  /**
   * Check that the specified support is not null and 
   * not installed on another tree.
   */
  private void installSupport(TreeSupport old, TreeSupport support)
  {
    if (support == null) throw new NullPointerException();
    if (old == support) return;
    if (support.tree != null) throw new IllegalArgumentException("Already installed on another tree");
    if (old != null) old.tree = null;
    support.tree = this;
  }

///////////////////////////////////////////////////////////
// Layout
///////////////////////////////////////////////////////////

  /**
   * Compute the preferred size of the tree.
   */
  public void computePreferredSize()
  {
    Size prefSize = new Size(0, 0);
    prefSize = doLayout(null, prefSize);
    setPreferredSize(prefSize.width, prefSize.height);
  }

  /**
   * Layout the tree.
   */
  public void doLayout(BWidget[] children)
  {
    doLayout(children, null);
    if (toVisibleRect != null) scrollToVisible(toVisibleRect);
    lastExpand = null;
    toVisibleRect = null;
  }

  /**
   * Layout the tree and compute the preferred size at the
   * same time.
   */
  public Size doLayout(BWidget[] children, Size prefSize)
  {
    if (prefSize == null)
      prefSize = new Size(0, 0);
    
    double x = Theme.tree().getInsets().left;
    double y = Theme.tree().getInsets().top;
    
    // During layout, I build a flat list of all visible nodes ordered by
    // screen row.  I then use the list for extremely efficient translation
    // from y location to TreeNode.
    visibleNodes.clear();
    
    // Layout the roots.  Layout returns one more than the bottom y
    // location for the subtree rooted by the target node.
    TreeModel model = this.model;
    int rootCount = model.getRootCount();
    for (int i = 0; i < rootCount; i++)
      y = model.getRoot(i).layout(x, y, i == 0, i == rootCount - 1, visibleNodes, prefSize);
    
    prefSize.height = y;

    // always leave room at bottom for scrolling
    if (getParent() instanceof BTreePane) prefSize.height += getRowHeight();
    
    return prefSize;
  }

  /**
   * Get the height of a row in the tree.  This is fixed for all tree rows.
   */  
  public double getRowHeight()
  {
    return Math.max(Theme.tree().getExpanderHeight(), nodeRenderer.getHeight());
  }

  /**
   * Get the row the contains the specified y location.
   */  
  public int getRow(double y)
  {
    double rowHeight = getRowHeight();
    y = y - Theme.tree().getInsets().top;
    if (y < 0) y = 0;
    return (int)(y / rowHeight);
  }

  /**
   * Get the TreeNode that contains the specified y location.
   */
  public TreeNode yToTreeNode(double y)
  {
    int row = getRow(y);
        
    if (row >= visibleNodes.size())
      return null;
    else
      return visibleNodes.get(row);
  }
  
  /**
   * Get the node before the specified start node in the layout.
   *
   * @return Returns the node above the specified start node in
   *   the current layout.  If the node is the first child, the
   *   parent is returned.  Otherwise, the previous sibling is
   *   is returned.  If the node is the first root, null is returned.
   */
  public TreeNode getPrevious(TreeNode startNode)
  {
    double y = startNode.getY();
    int row = getRow(y);
    if (row == 0)
      return null;
    else
      return visibleNodes.get(row - 1);
  }
  
  /**
   * Get the node after the specified start node in the layout.
   *
   * @return Returns the node below the specified start node in
   *   the current layout.  If the node is the last child, the
   *   next sibling of the parent is returned.  Otherwise, the 
   *   next sibling of the start node is returned.
   *   If the node is the last node in the layout, null is returned.
   */
  public TreeNode getNext(TreeNode startNode)
  {
    double y = startNode.getY();
    int row = getRow(y);
    if (row == visibleNodes.size() - 1)
      return null;
    else
      return visibleNodes.get(row + 1);
  }

///////////////////////////////////////////////////////////
// Paint
///////////////////////////////////////////////////////////

  /**
   * Paint the tree.
   */
  public void paint(Graphics g)
  {
    IRectGeom clip = g.getClipBounds();
    
    g.setBrush(Theme.tree().getBackground(this));
    g.fillRect(0, 0, getWidth(), getHeight());
    
    int rootCount = model.getRootCount();
    
    // figure out which root to start painting based on the clip bounds
    double height = nodeRenderer.getHeight();
    double viewTop = clip.y();
    double viewBottom = clip.y() + clip.height();
   
    double prevY = 0;
    boolean belowBottom = false;
    for (int i = 0; i < rootCount; i++)
    {
      TreeNode thisRoot = model.getRoot(i);
      if (thisRoot.getY() > viewBottom)
      {
        if(belowBottom)
        break;
        else
          belowBottom = true;        
      }
      
      TreeNode nextRoot = (i == rootCount - 1) ? null : model.getRoot(i + 1);
      if ((nextRoot != null) && (nextRoot.getY() <= viewTop))
        continue;
      
      prevY = thisRoot.paint(g, prevY);
      
      if (toVisible != null)
      {
        RectGeom nodeRect =
          new RectGeom(toVisible.getX(), toVisible.getY(),
                        toVisible.getWidth() + 4, getRowHeight());
        scrollToVisible(nodeRect);
        toVisible = null;
      }
    }
  }
  
  public String getStyleSelector() { return "tree"; }

///////////////////////////////////////////////////////////
// Expansion
///////////////////////////////////////////////////////////

  /**
   * Toggle the expansion state of the specified node.
   */
  public final void toggleExpanded(TreeNode node)
  {
    setExpanded(node, !node.isExpanded());
  }
  
  /**
   * Set the expanded state of the specified node.
   */
  public void setExpanded(TreeNode node, boolean exp)
  {
    if (node.isExpanded() == exp) return;
      
    node.setExpanded(exp);
    
    // collapsed
    if (!exp)
    {
      selection.collapsed(node);
      controller.collapsed(node);
    }
    // expanded
    else
    {
      selection.expanded(node);
      controller.expanded(node);
      lastExpand = node;
    }
    relayout();
  }
  
  /**
   * Expand the tree to the specified node.
   */
  public void expandToNode(TreeNode node)
  {
    expandPath(node.getPathFromRoot());
  }

  /**
   * Expand all nodes in the specified path except for the last one.
   */  
  public void expandPath(TreeNode[] path)
  {
    int count = path.length;
    for (int i = 0; i < count - 1; i++)
      path[i].setExpanded(true);
    relayout();
  }
  
  public void scrollNodeToVisible(TreeNode node)
  {
    scrollPathToVisible(node.getPathFromRoot());
  }
  
  public void scrollPathToVisible(TreeNode[] path)
  {
    toVisible = path[path.length - 1];
    expandPath(path);
/*    
    RectGeom nodeRect =
      new RectGeom(last.getX(), last.getY(),
                    getWidth() - last.getX(), getRowHeight());
    scrollToVisible(nodeRect);
*/
  }

  public Object widgetSupport(Object arg)
  {
    if (arg instanceof TreeNode)
    {
      if (arg == lastExpand)      
        return Boolean.TRUE;
      else
        return null;
    }
    else if (arg instanceof RectGeom)
    {
      toVisibleRect = (RectGeom)arg;
      return null;
    }
    else
      return super.widgetSupport(arg);
  }

///////////////////////////////////////////////////////////
// Focus
///////////////////////////////////////////////////////////

  /**
   * Route to TreeController.
   */
  public boolean isFocusTraversable()
  {
    return controller.isFocusTraversable();
  }

  /**
   * Route to TreeController.
   */
  public void focusGained(BFocusEvent event)
  {
    controller.focusGained(event);
  }

  /**
   * Route to TreeController.
   */
  public void focusLost(BFocusEvent event)
  {
    controller.focusLost(event);
  }

///////////////////////////////////////////////////////////
// Mouse input
///////////////////////////////////////////////////////////

  public void mousePressed(BMouseEvent evt) { controller.mousePressed(evt); }
  public void mouseReleased(BMouseEvent evt) { controller.mouseReleased(evt); }
  public void mouseEntered(BMouseEvent evt) { controller.mouseEntered(evt); }
  public void mouseExited(BMouseEvent evt) { controller.mouseExited(evt); }
  public void mousePulsed(BMouseEvent evt) { controller.mousePulsed(evt); }
  public void mouseMoved(BMouseEvent evt) { controller.mouseMoved(evt); }
  public void mouseDragged(BMouseEvent evt) { controller.mouseDragged(evt); }
  public void mouseWheel(BMouseWheelEvent evt) { controller.mouseWheel(evt); }

///////////////////////////////////////////////////////////
// Keyboard input
///////////////////////////////////////////////////////////

  public void keyPressed(BKeyEvent evt) { controller.keyPressed(evt); }
  public void keyReleased(BKeyEvent evt) { controller.keyReleased(evt); }
  public void keyTyped(BKeyEvent evt) { controller.keyTyped(evt); }

////////////////////////////////////////////////////////////////
// Transfer
////////////////////////////////////////////////////////////////

  /** Throw UnsupportedOperationException */
  public TransferEnvelope getTransferData() 
    throws Exception 
  { 
    throw new UnsupportedOperationException();
  }
    
  /** Throw UnsupportedOperationException */
  public CommandArtifact insertTransferData(TransferContext cx) 
    throws Exception
  { 
    throw new UnsupportedOperationException();
  }

  /** Throw UnsupportedOperationException */
  public CommandArtifact removeTransferData(TransferContext cx)
    throws Exception
  { 
    throw new UnsupportedOperationException();
  }                                     
  
  public int dragOver(TransferContext cx)
  {               
    if (cx.isPulse()) controller.pulseViewport(cx.getX(), cx.getY());
    return super.dragOver(cx);
  }

///////////////////////////////////////////////////////////
// Access
///////////////////////////////////////////////////////////

  /**
   * Get the list of all currently visible nodes.
   */
  public TreeNode[] getVisibleNodes()
  {
    TreeNode[] arr = new TreeNode[visibleNodes.size()];
    for (int i = 0; i < visibleNodes.size(); i++)
    {
      arr[i] = visibleNodes.get(i);
    }
    return arr;  
  }
  
////////////////////////////////////////////////////////////////
// TreeSupport
////////////////////////////////////////////////////////////////  

  /**
   * Abstract base class for support classes.
   */
  public static abstract class TreeSupport
  {
    /**
     * Get the tree the support instance is installed on.
     */
    public final BTree getTree()
    {
      return tree;
    }

    public final TreeModel getModel() { return tree.model; }
    public final TreeController getController() { return tree.controller; }
    public final TreeNodeRenderer getNodeRenderer() { return tree.nodeRenderer; }
    public final TreeSelection getSelection() { return tree.selection; }
    public final BWidgetShell getShell() { return tree.getShell(); }
    
    BTree tree;
  }
  
///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  TreeModel           model;
  TreeController      controller;
  TreeNodeRenderer    nodeRenderer;
  TreeSelection       selection;
  
  ArrayList<TreeNode> visibleNodes = new ArrayList<>(64);
  TreeNode            toVisible = null;
  TreeNode            lastExpand = null;
  RectGeom            toVisibleRect = null;

///////////////////////////////////////////////////////////
// Test
///////////////////////////////////////////////////////////
//  
//  public static void main(String[] args)
//  {
//    TreeModel model = new TreeModel.TestTreeModel(10, 5, 6);    
//    BTree tree = new BTree(model);
//    tree.setSelectionModel(new MultiSelectionModel());
//    BScrollPane scroll = new BTreePane(tree);
//    BFrame f = new BFrame("Tree Test", scroll);
//    f.open(200, 200, 200, 400);
//    
//    tree.expandToNode(model.getRoot(1).getChild(1).getChild(2).getChild(3));
//    TreeNode scrollNode = model.getRoot(7).getChild(1).getChild(2);
//    tree.scrollNodeToVisible(scrollNode);
//    tree.addSelection(scrollNode);
//  }
}
