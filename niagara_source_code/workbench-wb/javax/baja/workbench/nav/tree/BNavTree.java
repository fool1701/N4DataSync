/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.nav.tree;

import java.util.ArrayList;

import javax.baja.naming.BHost;
import javax.baja.naming.BOrd;
import javax.baja.nav.BINavNode;
import javax.baja.nav.BNavFolder;
import javax.baja.nav.BNavRoot;
import javax.baja.nav.NavEvent;
import javax.baja.nav.NavListener;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BComponentSpace;
import javax.baja.space.BISpaceNode;
import javax.baja.space.Mark;
import javax.baja.sys.BComponent;
import javax.baja.sys.BModule;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BDialog;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.transfer.SimpleDragRenderer;
import javax.baja.ui.transfer.TransferContext;
import javax.baja.ui.transfer.TransferEnvelope;
import javax.baja.ui.tree.BTree;
import javax.baja.ui.tree.TreeNode;

import com.tridium.fox.sys.BFoxSession;
import com.tridium.fox.sys.file.BFoxFileSpace;
import com.tridium.ui.theme.Theme;
import com.tridium.workbench.transfer.TransferUtil;

/**
 * BNavTree is a BTree designed to display a tree 
 * modeled using the BINavNode API.
 *
 * @author    Brian Frank       
 * @creation  14 Jan 03
 * @version   $Revision: 31$ $Date: 6/20/11 9:34:25 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BNavTree
  extends BTree
  implements NavListener
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.nav.tree.BNavTree(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNavTree.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Constructor with specified model.
   */
  public BNavTree(NavTreeModel model)
  {
    super(model, new NavTreeController());
    setNodeRenderer(new NavTreeNodeRenderer());
    setSelection(new NavTreeSelection());
    
    setCutEnabled(true);
    setCopyEnabled(true);
    setPasteEnabled(true);
    setPasteSpecialEnabled(true);
    setDuplicateEnabled(true);
    setDeleteEnabled(true);
    setRenameEnabled(true);
  }

  /**
   * Convenience for <code>this(new DefaultNavTreeModel(root)</code>.
   */
  public BNavTree(BINavNode root)
  {
    this(new DefaultNavTreeModel(root));
  }

  /**
   * Convenience for <code>this(BNavRoot.INSTANCE)</code>.
   */
  public BNavTree()
  {
    this(BNavRoot.INSTANCE);
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Get the selected nav node or return null if not selected.
   */
  public BObject getSelectedObject()
  {
    NavTreeNode node = (NavTreeNode)getSelection().getNode();
    if (node != null)
      return (BObject)node.getNavNode();
    return null;
  }
  
  /**
   * Get the selected nav nodes or empty array if nothing selected.
   */
  public BObject[] getSelectedObjects()
  {
    TreeNode[] nodes = getSelection().getNodes();
    BObject[] objs = new BObject[nodes.length];
    for (int i=0; i<nodes.length; i++)
      objs[i] = (BObject)((NavTreeNode)nodes[i]).getNavNode();
    return objs;
  }

  /**
   * Get the parent object of the selection. Returns null
   * if a common parent does not exist, or if there is no 
   * selection.
   */
  public BObject getSelectionParent()
  {
    TreeNode[] nodes = getSelection().getNodes();
    if (nodes == null || nodes.length == 0) return null;

    if (nodes[0].getParent() == null) return null;
    BObject parent = (BObject)((NavTreeNode)nodes[0].getParent()).getNavNode();

    for (int i=1; i<nodes.length; i++)
    {
      BObject temp = (BObject)((NavTreeNode)nodes[i].getParent()).getNavNode();
      if (temp != parent) return null;
    }           
    return parent;
  }

  /**
   * Set navTree to read only.
   */
  public void setReadonly(boolean b) { readonly = b; }

  /**
   * Return true if read only, false otherwise.
   */
  public boolean getReadonly() { return readonly; }

  /**
   * Expand to this ord.
   */
  public void expandToOrd(BObject base, BOrd ord)
  {
    try
    {
      BINavNode node = (BINavNode)ord.resolve(base).get();
      expandToNavNode(node);
    }
    catch (Exception e) { BDialog.error(this, "Error", "Could not expandTo: " + ord, e); }
  }

  /**
   * Expand to this nav node.
   */
  public void expandToNavNode(BINavNode node)
    throws Exception
  {
    NavTreeModel model = (NavTreeModel)getModel();
    NavTreeNode root = null;

    ArrayList<BINavNode> path = new ArrayList<>();
    while (node != null)
    {
      for (int i=0; i<model.getRootCount(); i++)
      {
        BINavNode temp = ((NavTreeNode)model.getRoot(i)).getNavNode();
        if (node.getNavOrd().equals(temp.getNavOrd())) 
        {
          root = (NavTreeNode)model.getRoot(i);
          break;
        }
      }
      if (root != null) break;
      
      path.add(0, node);
      node = node.getNavParent();
    }

    if (root == null) return;
    if (path.size() > 0) root.setExpanded(true);

    String oldname = "";
    for (int i=0; i<path.size()-1; i++)
    {
      node = path.get(i);
      
      if (oldname.equals(node.getNavName())) continue;
      if (node.getNavName() == null) continue;
      if (node instanceof BModule) 
      {
        root = root.getChild(((BModule)node).getModuleName(), true);
        i++;
      }
      else root = root.getChild(node.getNavName(), true);
      root.setExpanded(true);
      oldname = root.getNavNode().getNavName();
    }
    
    if (path.size() > 0)
    {
      node = path.get(path.size()-1);
      if (node.getNavName() != null)
      { 
        NavTreeNode temp = root.getChild(node.getNavName(), true);
        if (temp != null) root = temp;
      }
    }
    getSelection().deselectAll();
    if (root != null)
    {
      getSelection().select(root);
//      To Fix the Issue NCCB-13331   
//      getController().setFocus(root);
      scrollNodeToVisible(root);
    	 
    }

    relayout();
  }

  public void setExpanded(TreeNode node, boolean exp)
  {
    super.setExpanded(node, exp);
    ((NavTreeNode) node).resetSession();
  }
////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////  

  /**
   * Register for NavEvents.
   */
  public void started()
    throws Exception
  {
    super.started();
    BNavRoot.INSTANCE.addNavListener(this);    
  }

  /**
   * Unregister for NavEvents.
   */
  public void stopped()
    throws Exception
  {
    super.stopped();
    BNavRoot.INSTANCE.removeNavListener(this);    
  }
  
  /**
   * Route to the event to the NavTreeModel.
   */
  public void navEvent(NavEvent event)
  {
    ((NavTreeModel)getModel()).navEvent(event);
  }

////////////////////////////////////////////////////////////////
// Transfer
////////////////////////////////////////////////////////////////

  /** 
   * Return an envelope for the currently selected node as a Mark.
   */
  public TransferEnvelope getTransferData() 
    throws Exception 
  { 
    BObject[] obj = getSelectedObjects();
    if (obj == null) return null;
    return TransferEnvelope.make(new Mark(obj));
  }
      
  /** 
   * Insert the data.
   */
  public CommandArtifact insertTransferData(TransferContext cx) 
    throws Exception
  { 
    return TransferUtil.insert(this, cx, getSelectedObject(), null);
  }

  /** 
   * Do nothing, we rely on nav event callbacks
   * to keep the tree synchronized.  Return null.
   */
  public CommandArtifact removeTransferData(TransferContext cx)
    throws Exception
  { 
    return null;
  }

  /**
   * Duplicate currently selected node.
   */
  public CommandArtifact doDuplicate()
    throws Exception
  {
    BObject[] selection = getSelectedObjects();
    BObject parent = getSelectionParent();
    if (selection.length == 0 || parent == null) return null;
    
    Mark mark = new Mark(selection);
    return TransferUtil.insert(this, ACTION_COPY, mark, parent, null, null);
  }

  /**
   * Delete currently selected node.
   */
  public CommandArtifact doDelete()
    throws Exception
  {
    BObject[] selection = getSelectedObjects();
    if (selection.length == 0) return null;
    
    Mark mark = new Mark(selection);
    return TransferUtil.delete(this, mark);
  }

  /**
   * Rename currently selected nodes.
   */
  public CommandArtifact doRename()
    throws Exception
  {
    BObject[] selection = getSelectedObjects();
    if (selection.length == 0) return null;
    
    Mark mark = new Mark(selection);
    return TransferUtil.rename(this, mark);
  }

////////////////////////////////////////////////////////////////
// Drag and Drop
////////////////////////////////////////////////////////////////

  /**
   * If the drag is over a tree node with a non-null
   * component, then call startDrag with the a 
   * MarkTransferable.
   */
  public void mouseDragStarted(BMouseEvent event)
  {
    if (readonly) return;

    double x = event.getX();
    double y = event.getY();

    NavTreeNode node = transferNodeAt(x, y, true);
    if (node == null) return;
    
    SimpleDragRenderer dragRenderer = new SimpleDragRenderer(node.getIcon(), node.getText());
    dragRenderer.font = Theme.tree().getFont(this);
    dragRenderer.xCursorOffset = node.getX() + Theme.tree().getExpanderWidth() + 4 - x;
    dragRenderer.yCursorOffset = node.getY() - y;
     
    // Determine if selection shares a common parent
    boolean copyOnly = false;
    TreeNode[] nodes = getSelection().getNodes();
    TreeNode parent = nodes[0].getParent(); 
    for (int i=1; i<nodes.length; i++)
      if (parent != nodes[i].getParent()) 
      { 
        copyOnly = true; 
        break; 
      }

    TransferEnvelope envelope = TransferEnvelope.make(new Mark(getSelectedObjects()));
    if (copyOnly)
      startDrag(event, makeTransferContext(envelope), dragRenderer);
    else
      startDrag(event, envelope, dragRenderer);
  }  
  
  /**
   * Check if the drop is over a valid node.
   */
  public int dragOver(TransferContext cx)
  { 
    // super handles pulsing for me                    
    super.dragOver(cx);
    
    if (readonly) return 0;

    NavTreeNode node = transferNodeAt(cx.getX(), cx.getY(), false);
    
    if (node != dragOver)
    {
      if (dragOver != null) dragOver.isDragOver = false;
      dragOver = node;
      if (dragOver != null) dragOver.isDragOver = true;
      repaint();
    }
    
    if (node == null) return 0;
    int mask = ACTION_COPY | ACTION_MOVE;
    return mask;
  }

  /**
   * Check if the drop is over a valid node.
   */
  public void dragExit(TransferContext cx)
  {
    if (dragOver != null) 
    {
      dragOver.isDragOver = false;
      dragOver = null;
      repaint();
    }
  }
    
  /**
   * Handle a drop.  If the drop node has a non-null 
   * component then use insertDynamicProperties().
   */
  public CommandArtifact drop(TransferContext cx)
    throws Exception
  {
    if (dragOver != null)
    {
      NavTreeNode node = dragOver;
      
      dragOver.isDragOver = false;
      dragOver = null;
      repaint();

      if (node != null)
      {
        // Issue 22275
        if(node.getNavNode() instanceof BComponent)
          if(node.getNavNode() != null)
            ((BComponent)node.getNavNode()).lease();
        TransferUtil.insert(this, cx, (BObject)node.getNavNode(), null);
      }
    }    
    
    return null;
  }
  
  /**
   * Get the node at the coordinate if it is mapped to an 
   * instance of BITransferNode, otherwise return null.
   */
  NavTreeNode transferNodeAt(double x, double y, boolean isDragStart)
  {
    NavTreeNode node = (NavTreeNode)yToTreeNode(y);
    if (node != null && node.isSelection(x, y))
    {
      Object object = node.getNavNode();
      
      // this is hardcoded to filter out most of the stuff that
      // should be dragged or dropped onto. A BHost can be dragged, but not dropped.
      if (object instanceof BISpaceNode || 
          object instanceof BFoxSession ||
          object instanceof BFoxFileSpace ||
          object instanceof BComponentSpace ||
          object instanceof BNavFolder ||
          (object instanceof BHost && isDragStart))
        return node;
    }
    return null;
  }
  
  NavTreeNode dragOver;
  boolean readonly = false;
}
