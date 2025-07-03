/*                          
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.treetable;

import javax.baja.ui.BMenu;
import javax.baja.ui.BSeparator;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.ToggleCommand;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.table.BTable;
import javax.baja.ui.table.TableController;
import javax.baja.ui.table.TableModel;
import javax.baja.ui.table.TableSubject;
import javax.baja.ui.util.UiLexicon;

import com.tridium.ui.theme.Theme;
import com.tridium.ui.theme.TreeTableTheme;

/**
 * TreeTableController is the TableController used by BTreeTable
 *
 * @author    Brian Frank 
 * @creation  7 Jan 04
 * @version   $Revision: 8$ $Date: 6/28/11 8:34:58 AM EDT$
 * @since     Baja 1.0
 */
public class TreeTableController
  extends TableController
{

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the table as a BTreeTable.
   */
  public final BTreeTable getTreeTable()
  {
    return (BTreeTable)getTable();
  }

  /**
   * Get the TreeTableModel.
   */
  public final TreeTableModel getTreeTableModel()
  {
    return ((BTreeTable)getTable()).model;
  }                  

  
////////////////////////////////////////////////////////////////
// Cell MouseEvents
////////////////////////////////////////////////////////////////

  public void cellPressed(BMouseEvent event, int row, int col)
  {            
    TreeTableModel model = getTreeTableModel();      
    TreeTableNode node = model.rowToNode(row);
    
    if (col == 0 || (node != null && node.isGroup()))
    {                          
      TreeTableTheme theme = Theme.treeTable();                
      int depth = node.getDepth();
      
      double x = theme.getIndent(depth);
      double x1 = x - 1;
      double x2 = x + theme.getExpanderWidth() + 1;
      double ex = event.getX();

      // if event click inside expander, then toggle expansion
      // and make sure selection model remains accurate
      if (x1 <= ex && ex <= x2)  
      {
        if (model.isDepthExpandable(depth) && node.hasChildren())
        {
          int[] sel = getSelection().getRows();
          int size  = node.getChildCount();
          int delta = node.isExpanded() ? -size : size;

          getSelection().deselectAll();
          node.setExpanded(!node.isExpanded());
          
          for (int i=0; i<sel.length; i++)
            if (sel[i] > row)
              getSelection().select(sel[i] + delta);
            else
              getSelection().select(sel[i]);
            
          return;
        } 
      }
    } 
    
    super.cellPressed(event, row, col);
  }

  public void expandNode(int row)
  {
    TreeTableModel model = getTreeTableModel();      
    TreeTableNode node = model.rowToNode(row);
    if(node.isExpanded())
      return;
    int depth = node.getDepth();
    if (model.isDepthExpandable(depth) && node.hasChildren())
    {
      int[] sel = getSelection().getRows();
      int size  = node.getChildCount();
      int delta = node.isExpanded() ? -size : size;

      getSelection().deselectAll();
      node.setExpanded(!node.isExpanded());
      
      for (int i=0; i<sel.length; i++)
      {
        if (sel[i] > row)
          getSelection().select(sel[i] + delta);
        else
          getSelection().select(sel[i]);
      }
    } 
    
  }
  
  /**
   * Build the options menu which displays with the 
   * options button is displayed.
   */      
  protected BMenu makeOptionsMenu()
  {
    BTable table = getTable();
    TableModel model = getModel();
    
    BMenu menu = new BMenu();
    
    menu.add("resizeColumns", new ResizeColumnsCommand(table));
    menu.add("export",        new ExportCommand(table));
    
    if (model instanceof DynamicTreeTableModel)
    {                                       
      DynamicTreeTableModel dynamicModel = (DynamicTreeTableModel)model;
      menu.add("showSep", new BSeparator());
      int count = 0;
      for(int i=0; i<dynamicModel.getRootColumnCount(); ++i)
      {
        if (dynamicModel.isColumnShowable(i))
        {
          menu.add(null, new ShowColumnCommand(dynamicModel, i));
          count++;
        }
      }
      if (count == 0) menu.remove("showSep");
    }
    return menu;
  }                       

  /**
   * Route to <code>makePopup(TreeTableSubject)</code>.
   */
  protected final BMenu makePopup(TableSubject subject)
  {
    return makePopup((TreeTableSubject)subject);
  }

  /**
   * Make popup using TreeTableSubject or return null
   * for no popup.
   */
  protected BMenu makePopup(TreeTableSubject subject)
  {         
    return null;
  }

  /**
   * Call for popup trigger over header.
   */
  protected void headerPopup(BMouseEvent event, int column)
  {
    TableModel model = getModel();
    
    if (model instanceof DynamicTreeTableModel)
    {
      DynamicTreeTableModel dynamicModel = (DynamicTreeTableModel)model;
      column = dynamicModel.toRootColumnIndex(column);
      if (dynamicModel.isColumnShowable(column))
      {
        BMenu menu = new BMenu();
        menu.add(null, new HideColumnCommand(dynamicModel, column));
        menu.open(getTable(), event.getX(), event.getY());
      }
    }
  }
  
  class HideColumnCommand extends Command
  {
    HideColumnCommand(DynamicTreeTableModel model, int column)
    {                                
      super(model.getTable(), UiLexicon.bajaui().module, "commands.table.hideColumn");
      this.model = model;
      this.column = column;             
    }                                
    
    public CommandArtifact doInvoke()
    {
      model.setShowColumn(column, false);
      return null;
    }            
    
    DynamicTreeTableModel model;
    int column;
  }

  class ShowColumnCommand extends ToggleCommand
  {     
    ShowColumnCommand(DynamicTreeTableModel model, int column)
    {                                
      super(model.getTable(), model.getRootColumnName(column));
      this.model = model;
      this.column = column;             
      setSelected(model.showColumn(column));
    }                                
    
    public void setSelected(boolean sel)
    {                                  
      super.setSelected(sel);
      model.setShowColumn(column, sel);
    }            
    
    DynamicTreeTableModel model;
    int column;
  }

  
}
