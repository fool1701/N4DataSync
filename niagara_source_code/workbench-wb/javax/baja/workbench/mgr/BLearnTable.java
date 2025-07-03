/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import javax.baja.gx.BImage;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SortUtil;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BMenu;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.transfer.SimpleDragRenderer;
import javax.baja.ui.transfer.TransferEnvelope;
import javax.baja.ui.treetable.BTreeTable;
import javax.baja.ui.treetable.DynamicTreeTableModel;
import javax.baja.ui.treetable.TreeTableCellRenderer;
import javax.baja.ui.treetable.TreeTableController;
import javax.baja.ui.treetable.TreeTableModel;
import javax.baja.ui.treetable.TreeTableNode;
import javax.baja.ui.treetable.TreeTableSelection;
import javax.baja.ui.treetable.TreeTableSubject;
import javax.baja.workbench.BWbShell;

import com.tridium.ui.theme.Theme;

/**
 * BLearnTable by BAbstractManager to display the objects
 * found during the discovery process.
 *
 * @author    Brian Frank
 * @creation  16 Dec 03
 * @version   $Revision: 19$ $Date: 2/4/08 11:31:55 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BLearnTable
  extends BTreeTable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.mgr.BLearnTable(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLearnTable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Framework use only
   */
  public BLearnTable()
  {
    throw new IllegalStateException();
  }
  
  public BLearnTable(MgrLearn learn)
  {                                      
    this.manager = learn.getManager(); 
    this.learn   = learn;    
    
    this.treeTableModel = new Model();
    this.dynamicModel = new DynamicTreeTableModel(treeTableModel);   

    updateColumns();
    
    setModel(dynamicModel);         
    setSelection(new Selection());
    setController(new Controller());
    setCellRenderer(new Renderer());
  }
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the manager passed to the constructor.
   */
  public BAbstractManager getManager()
  {
    return manager;
  }                      

  /**
   * Map a visible column index to a MgrColumn. 
   */
  public MgrColumn columnIndexToMgrColumn(int column)
  {                                            
    return learn.cols[dynamicModel.toRootColumnIndex(column)];
  }                               
  
  /**
   * Get the discovery object at the specified row index.
   */
  public Object getObjectAt(int row)
  {               
    Node node = (Node)treeTableModel.rowToNode(row);     
    if (node != null) return node.discovery;
    return null;
  }
  
  /**
   * Get the selected object or null.
   */
  public Object getSelectedObject()
  {                                  
    int sel = getSelection().getRow();
    if (sel < 0) return null;
    return getObjectAt(sel); 
  }                      

  /**
   * Get the selected objects or an empty array.
   */
  public Object[] getSelectedObjects()
  {                                  
    int sel[] = getSelection().getRows();
    Object[] obj = new Object[sel.length];
    for(int i=0; i<sel.length; ++i)
      obj[i] = getObjectAt(sel[i]);
    return obj;
  }                

////////////////////////////////////////////////////////////////
// Drag and Drop
////////////////////////////////////////////////////////////////

  public void mouseDragStarted(BMouseEvent event)
  {                                
    int[] rows= getSelection().getRows();
    if (rows.length == 0) return;
    
    // if click not in selection, then don't start drag
    int rowY = getRowAt(event.getY());
    boolean found = false;
    for(int i=0; i<rows.length; ++i)
      if (rowY == rows[i]) { found = true; break; }
    if (!found) return;    
    
    // build drag renderer
    BImage[] icons = new BImage[rows.length];
    String[] text = new String[rows.length]; 
    for(int i=0; i<rows.length; ++i)
    {            
      int row = rows[i];
      Node node = (Node)treeTableModel.rowToNode(row);     
      icons[i] = node.getIcon();
      text[i]  = ""+node.getValueAt(0);
    }
    SimpleDragRenderer dragRenderer = new SimpleDragRenderer(icons, text);
    dragRenderer.font = Theme.table().getCellFont();
    
    // start drag, we use a special hashed string 
    // key which the MgrTable knows to look for
    String payload = "dragFromLearn:" + getManager().hashCode();
    startDrag(event, TransferEnvelope.make(payload), dragRenderer);
  }
    
////////////////////////////////////////////////////////////////
// Selection
////////////////////////////////////////////////////////////////

  class Selection extends TreeTableSelection
  {
    public void updateTable()
    {
      super.updateTable();
      manager.controller.updateCommands();
    }
  }

////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////

  class Controller extends TreeTableController
  {                  
    public void cellDoubleClicked(BMouseEvent event, int row, int col)
    {
      getManager().getController().cellDoubleClicked(BLearnTable.this, event, row, col);
    }
      
    protected BMenu makePopup(TreeTableSubject subject)
    {                                        
      BMenu menu = super.makePopup(subject);
      return manager.getController().makePopup(BLearnTable.this, subject, menu);
    }                     
    
    public BMenu makeOptionsMenu()
    {                           
      BMenu menu = super.makeOptionsMenu();
      return getManager().getController().makeOptionsMenu(BLearnTable.this, menu);
    }
    
  }
  
////////////////////////////////////////////////////////////////
// Model
////////////////////////////////////////////////////////////////
  
  void updateColumns()
  {
    this.cols = learn.getColumns();                        
    for(int i=0; i<cols.length; ++i)
      dynamicModel.setShowColumn(i, !cols[i].isUnseen());
  }
  
  void resetIcons()
  {     
    Node[] roots = treeTableModel.roots;
    for(int i=0; i<roots.length; ++i) resetIcons(roots[i]);
    repaint();
  }      

  void resetIcons(Node node)
  {                         
    node.resetIcon = true;
    if (node.children != null)
      for(int i=0; i<node.children.length; ++i)
        resetIcons(node.children[i]);
  }      

  class Model extends TreeTableModel
  {                   
    public void updateTreeTable(boolean resize)
    {               
      BWbShell shell = getManager().getWbShell();
      shell.enterBusy();
      try
      {
        Object[] discovery = learn.getRoots();
        
        // create new roots from discovery, but try to reuse existing roots      
        Node[] roots = new Node[discovery.length];
        for(int i=0; i<roots.length; ++i)
        {
          // attemp to use existing root, otherwise create new one
          Node root = discoveryToRoot(discovery[i]);
          if (root == null) root = new Node(this, discovery[i]);
          roots[i] = root;
        }                 
        
        // sort if a sort column is in effect
        int sortCol = dynamicModel.toRootColumnIndex(getSortColumn());
        if (sortCol >= 0)
          roots = sort(roots, sortCol, isSortAscending());          
        
        // update tree table
        this.roots = roots;
        super.updateTreeTable(resize);
      }
      finally
      {
        shell.exitBusy();
      }
    }                        
      
    public int getRootCount() 
    { 
      return roots.length; 
    }                      
    
    public TreeTableNode getRoot(int index)
    {
      return roots[index];
    }

    public boolean isDepthExpandable(int depth)
    {
      return learn.isDepthExpandable(depth);
    }
    
    public int getColumnCount() 
    {
      return cols.length;
    }                              
    
    public String getColumnName(int col) 
    {
      return cols[col].getDisplayName();
    }
        
    public boolean isColumnSortable(int col)
    {
      return true;
    }

    public void sortByColumn(int col, boolean ascending)
    {                         
      getSelection().deselectAll();
      roots = sort(roots, col, ascending);          
      super.updateTreeTable(false);
    }     
        
    Node discoveryToRoot(Object discovery)
    {                    
      Node[] roots = this.roots;
      for(int i=0; i<roots.length; ++i)
        if (roots[i].discovery == discovery)
          return roots[i];
      return null;
    }
    
    Node[] roots = new Node[0];
  }                     

  static Node[] sort(Node[] roots, int col, boolean ascending)
  {                         
    Node[] sorted = roots.clone();
    Object[] keys = new Object[sorted.length];
    for(int i=0; i<keys.length; ++i)
      keys[i] = sorted[i].getValueAt(col);
      
    SortUtil.sort(keys, sorted, ascending);     
    
    return sorted;
  }       

////////////////////////////////////////////////////////////////
// Renderer
////////////////////////////////////////////////////////////////

  class Renderer extends TreeTableCellRenderer
  {
    public String getCellText(Cell cell)
    {                       
      try
      {
        Object obj = getObjectAt(cell.row);
        MgrColumn col = columnIndexToMgrColumn(cell.column);
        return col.toDisplayString(obj, cell.value, manager.getCurrentContext());
      }
      catch(Exception e)
      {
        return "";
      }
    }   
  }
  
////////////////////////////////////////////////////////////////
// Node
////////////////////////////////////////////////////////////////

  class Node extends TreeTableNode
  {          
    Node(Model model, Object discovery) 
    { 
      super(model); 
      this.discovery = discovery;
    }
    
    Node(Node parent, Object discovery) 
    { 
      super(parent); 
      this.discovery = discovery;
    }               
    
    public Object getSubject()
    {
      return discovery;
    }   

    public boolean isGroup()
    {
      return learn.isGroup(discovery);
    }
    
    public boolean hasChildren()
    {                       
      return learn.hasChildren(discovery);
    }                                                

    public int getChildCount()
    {
      return getChildren().length;
    }
    
    public TreeTableNode getChild(int index)
    {
      return getChildren()[index];
    }
    
    TreeTableNode[] getChildren()
    {                                 
      if (children == null)
      {             
        Object[] kidDis = learn.getChildren(discovery);
        Node[] kidNodes = new Node[kidDis.length];
        for(int i=0; i<kidNodes.length; ++i)
          kidNodes[i] = new Node(this, kidDis[i]);
        children = kidNodes;
      }
      return children;
    }

    public Object getValueAt(int col) 
    {                            
      return cols[col].get(discovery);
    }
    
    @Override
    public BImage getIcon()
    {
      //If the icon for a row has not been obtained, or an event
      //has occurred that might change the icon's value...
      if (icon == null ||
          resetIcon)
      {
        //Find the icon associated with this discovery object
        BImage icon = learn.getIcon(discovery);

        //If an icon was found and the discovery object is presently
        //in the database...
        if (icon != null &&
            learn.getExisting(discovery) != null)
        {
          //Replace the icon with its disabled image to visually
          //indicate its presence in the database
          icon = icon.getDisabledImage();
        }

        //Store references to avoid looking up the icon again
        this.icon = icon;
        this.resetIcon = false;
      }

      return icon;
    }            
    
    Object discovery; 
    Node[] children;
    BImage icon;
    boolean resetIcon;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BAbstractManager manager; 
  MgrColumn[] cols;
  MgrLearn learn;
  DynamicTreeTableModel dynamicModel;
  Model treeTableModel;
  
}
