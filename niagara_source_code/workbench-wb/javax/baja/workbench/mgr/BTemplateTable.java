/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
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
import javax.baja.workbench.BWbShell;

import com.tridium.ui.theme.Theme;

/**
 * BTemplateTable by BAbstractManager to display the device templates
 * from a template directory.
 *
 * @author    Andy Saunders
 * @creation  16 Dec 13
 * @since     Baja 4.0
 */
@NiagaraType
public class BTemplateTable
  extends BTreeTable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.mgr.BTemplateTable(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTemplateTable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Framework use only
   */
  public BTemplateTable()
  {
    throw new IllegalStateException();
  }

  public BTemplateTable(MgrTemplate template)
  {                                      
    this.manager = template.getManager();
    this.mgrTemplate = template;
    
    this.treeTableModel = new TemplateTreeModel();
    this.dynamicModel = new DynamicTreeTableModel(treeTableModel);

    updateColumns();
    
    setModel(dynamicModel);         
    setSelection(new Selection());
    setController(new Controller());
    setCellRenderer(new Renderer());
    setMultipleSelection(false);
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
    return mgrTemplate.cols[dynamicModel.toRootColumnIndex(column)];
  }                               
  
  /**
   * Get the discovery object at the specified row index.
   */
  public Object getObjectAt(int row)
  {
    TemplateTableNode node = (TemplateTableNode)treeTableModel.rowToNode(row);
    if (node != null) return node.template;
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
      TemplateTableNode node = (TemplateTableNode)treeTableModel.rowToNode(row);
      icons[i] = node.getIcon();
      text[i]  = ""+node.getValueAt(0);
    }
    SimpleDragRenderer dragRenderer = new SimpleDragRenderer(icons, text);
    dragRenderer.font = Theme.table().getCellFont();
    
    // start drag, we use a special hashed string 
    // key which the MgrTable knows to look for
    String payload = "dragFromTemplate:" + getManager().hashCode();
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
      manager.getController().updateCommands();
    }
  }

////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////

  class Controller extends TreeTableController
  {                  
    public void cellDoubleClicked(BMouseEvent event, int row, int col)
    {
      getManager().getController().cellDoubleClicked(BTemplateTable.this, event, row, col);
    }

    public BMenu makeOptionsMenu()
    {
      BMenu menu = super.makeOptionsMenu();
      return getManager().getController().makeOptionsMenu(BTemplateTable.this, menu);
    }

    @Override
    protected void headerPopup(BMouseEvent event, int column)
    {
      getManager().getController().headerPopup(BTemplateTable.this, event, column);
    }
  }

////////////////////////////////////////////////////////////////
// Model
////////////////////////////////////////////////////////////////
  
  void updateColumns()
  {
    this.cols = mgrTemplate.getColumns();
    for(int i=0; i<cols.length; ++i)
      dynamicModel.setShowColumn(i, !cols[i].isUnseen());
  }
  
  void resetIcons()
  {
    TemplateTableNode[] roots = treeTableModel.roots;
    for(int i=0; i<roots.length; ++i) resetIcons(roots[i]);
    repaint();
  }      

  void resetIcons(TemplateTableNode node)
  {                         
    node.icon = null; 
    if (node.children != null) 
      for(int i=0; i<node.children.length; ++i)
        resetIcons(node.children[i]);
  }      

  class TemplateTreeModel extends TreeTableModel
  {                   
    public void updateTreeTable(boolean resize)
    {               
      BWbShell shell = getManager().getWbShell();
      if(shell == null) //  NCCB-13730:  running in a px view?
        return;
      shell.enterBusy();
      try
      {
        Object[] tmpls = mgrTemplate.getRoots();
//        if(tmpls == null || tmpls.length == 0)
//          mgrTemplate.updateTemplateTable(mgrTemplate.getTarget(), null);
//        tmpls = mgrTemplate.getRoots();
        
        // create new roots from tmpls, but try to reuse existing roots
        TemplateTableNode[] roots = new TemplateTableNode[tmpls.length];
        for(int i=0; i<roots.length; ++i)
        {
          // attempt to use existing root, otherwise create new one
          TemplateTableNode root = discoveryToRoot(tmpls[i]);
          if (root == null) root = new TemplateTableNode(this, tmpls[i]);
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
      return mgrTemplate.isDepthExpandable(depth);
    }
    
    public int getColumnCount() 
    {
      return cols.length;
    }                              
    
    public String getColumnName(int col) 
    {
      return mgrTemplate.cols[col].getDisplayName();
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

    TemplateTableNode discoveryToRoot(Object discovery)
    {
      TemplateTableNode[] roots = this.roots;
      for(int i=0; i<roots.length; ++i)
        if (roots[i].template == discovery)
          return roots[i];
      return null;
    }

    TemplateTableNode[] roots = new TemplateTableNode[0];
  }                     

  static TemplateTableNode[] sort(TemplateTableNode[] roots, int col, boolean ascending)
  {
    TemplateTableNode[] sorted = roots.clone();
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
        e.printStackTrace();
        return "";
      }
    }   
  }
  
////////////////////////////////////////////////////////////////
// Node
////////////////////////////////////////////////////////////////

  class TemplateTableNode extends TreeTableNode
  {
    TemplateTableNode(TemplateTreeModel model, Object discovery)
    { 
      super(model); 
      this.template = discovery;
    }

    TemplateTableNode(TemplateTableNode parent, Object discovery)
    { 
      super(parent); 
      this.template = discovery;
    }               
    
    public Object getSubject()
    {
      return template;
    }   

    public boolean isGroup()
    {
      return mgrTemplate.isGroup(template);
    }
    
    public boolean hasChildren()
    {                       
      return mgrTemplate.hasChildren(template);
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
        Object[] kidDis = mgrTemplate.getChildren(template);
        TemplateTableNode[] kidNodes = new TemplateTableNode[kidDis.length];
        for(int i=0; i<kidNodes.length; ++i)
          kidNodes[i] = new TemplateTableNode(this, kidDis[i]);
        children = kidNodes;
      }
      return children;
    }


    public Object getValueAt(int col)
    {
      return cols[col].get(template);
    }
    
    public BImage getIcon()
    {                 
      if (icon == null)
      {
        icon = mgrTemplate.getIcon(template);
      }
      return icon;
    }            
    
    Object template;
    TemplateTableNode[] children;
    BImage icon;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BAbstractManager manager; 
  MgrColumn[] cols;
  MgrTemplate mgrTemplate;
  DynamicTreeTableModel dynamicModel;
  TemplateTreeModel treeTableModel;
  
}
