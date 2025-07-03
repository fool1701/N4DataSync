/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import java.util.ArrayList;
import javax.baja.gx.BBrush;
import javax.baja.gx.BColor;
import javax.baja.gx.BImage;
import javax.baja.nre.util.Array;
import javax.baja.sync.Transaction;
import javax.baja.sys.BComponent;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.LocalizableException;
import javax.baja.ui.BDialog;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.table.TableCellRenderer;
import javax.baja.ui.table.TableModel;
import com.tridium.ui.theme.Theme;
import com.tridium.util.ObjectUtil;

/**
 * MgrEdit is used by BAbstractManager to add and change one 
 * or more components in the station database.  MgrEdit is modeled
 * as a table using MgrEditRow for the rows and MgrColumn for
 * the columns.  
 *
 * @author    Brian Frank
 * @creation  17 Dec 03
 * @version   $Revision: 30$ $Date: 8/16/10 4:52:36 PM EDT$
 * @since     Baja 1.0
 */
public class MgrEdit    
  extends TableModel
{                   

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public MgrEdit(BAbstractManager manager, String title)
  {
    this.manager = manager;                            
    this.title   = title;
    this.cols    = makeColumns(); 
    this.rows    = new ArrayList<>();
 }
    
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Get the associated BAbstractManager.
   */
  public BAbstractManager getManager()
  {  
    return manager;
  }            

  /**
   * Get the title passed to the constructor.
   */
  public String getTitle()
  {  
    return title;
  }                         
  
  /**
   * Get the container component which new components 
   * will be added into by default.   The default assumes
   * the target of the BAbstractManager.
   */
  public BComponent getAddContainer()
  {      
    return manager.target;
  }  
  
  /**
   * boolean test for usage values (Add, Edit, Match, New)
   * 
   */
  public boolean isAdd()   { return manager.getMgrEditState() == ADD  ; }
  public boolean isEdit()  { return manager.getMgrEditState() == EDIT ; }
  public boolean isMatch() { return manager.getMgrEditState() == MATCH; }
  public boolean isNew()   { return manager.getMgrEditState() == NEW; }
  
  /**
   * Get the columns of the table.  These columns are 
   * initialized by the makeColumns() method.
   */
  public final MgrColumn[] getColumns()  
  {
    return cols.clone();
  }
    
  /**
   * Default returns <code>MgrModel.getEditableColumns()</code>.
   */
  protected MgrColumn[] makeColumns()
  {
    MgrColumn[] modelCols = manager.getModel().getEditableColumns();
    if(!manager.isTaggable())
      return modelCols;

    // tagging is enabled attempt to get editable tags.
    MgrColumn[] tagCols = null;

    switch(manager.getMgrEditState())
    {
      case ADD:
      case NEW:
      case MATCH:
        tagCols = manager.getMgrTagDictionary().makeTagColumns();
        break;

      case EDIT:
        String namespace = manager.getMgrTagDictionary().getNamespace();
        tagCols = manager.getModel().getEditableTagColumns(namespace);
        break;
      default:
    }

    if (tagCols == null) // if no editable tags just return model cols.
    {
      return modelCols;
    }

    Array<MgrColumn> cols = new Array<>(modelCols);
    cols.addAll(tagCols);
    return cols.trim();
  }                          
  
////////////////////////////////////////////////////////////////
// TableModel
////////////////////////////////////////////////////////////////

  /**
   * Get the number of rows.
   */
  public int getRowCount()
  {
    return rows.size();
  }  

  /**
   * Get the number of columns.
   */
  public int getColumnCount()
  {
    return cols.length;
  }  

  /**
   * Get the display name of the column.
   */
  public String getColumnName(int col)
  {
    return cols[col].getDisplayName();
  }  

  /**
   * Get the value of the cell.
   */
  public Object getValueAt(int row, int col)
  {                              
    MgrEditRow r = getRow(row);
    MgrColumn c  = cols[col];
    try
    {
      return c.toDisplayString(r.target, r.getCell(col), manager.getCurrentContext());
    }
    catch(Exception e)
    {            
      e.printStackTrace();
      return "";
    }
  }    
  
  /**
   * Return <code>getRow(row)</code>.
   */
  public Object getSubject(int row)
  {               
    return getRow(row);
  }  

  /**
   * Get the row icon.
   */
  public BImage getRowIcon(int row)
  {
    return BImage.make(getRow(row).getTarget().getIcon());
  }  
  
////////////////////////////////////////////////////////////////
// Component 
////////////////////////////////////////////////////////////////
    
  /**
   * Get the list of rows as an array of MgrEditRows.
   */
  public MgrEditRow[] getRows()
  {
    return rows.toArray(new MgrEditRow[rows.size()]);
  }
  
  /**
   * Get the MgrEditRow in the table at the specified index.
   */
  public MgrEditRow getRow(int index)
  {
    return rows.get(index);
  }

  /**
   * Get the index of the specified MgrEditRow in the 
   * list or return -1 if not found.
   */
  public int indexOf(MgrEditRow row)
  {                       
    for(int i=0; i<rows.size(); ++i)
      if (rows.get(i) == row)
        return i;
    return -1;
  }
  
  /**
   * Add the MgrEditRow to the list at the specified index.
   * @return the row passed as an argument.
   */
  public MgrEditRow addRow(int index, MgrEditRow row)
    throws Exception
  {                         
    if (row.edit != null)
      throw new IllegalArgumentException("Row is already in another table");
    rows.add(index, row);
    row.edit = this;        
    row.loadCells();
    return row;
  }

  /**
   * Convenience for <code>addRow(getRowCount(), row)</code>.
   * @return the row created for the target.
   */
  public MgrEditRow addRow(MgrEditRow row)
    throws Exception
  {                        
    return addRow(getRowCount(), row); 
  }

  /**
   * Convenience for <code>addRow(makeRow(target, null, null))</code>.
   * @return the row created for the target.
   */
  public MgrEditRow addRow(BComponent target)
    throws Exception
  {
    return addRow(makeRow(target, null, null));
  }                                   

  /**
   * Convenience for <code>addRow(makeRow(getManager().getModel().newInstance(types[0]), discovery, types))</code>.
   * @return the row created for the target.
   */
  public MgrEditRow addRow(Object discovery, MgrTypeInfo[] types)
    throws Exception           
  {
    return addRow(makeRow(getManager().getModel().newInstance(types[0]), discovery, types));
  }                                   
  
  /**
   * This is a callback for making an instance of a 
   * MgrEditRow for the specified target.
   */
  protected MgrEditRow makeRow(BComponent target, Object discovery, MgrTypeInfo[] types)
    throws Exception
  {
    return new MgrEditRow(target, discovery, types);
  }

  /**
   * Remove the specified MgrEditRow.
   */
  public void remove(MgrEditRow row)
    throws Exception
  {                                 
    int index = indexOf(row);
    if (index < 0) throw new IllegalArgumentException("Row not in this table");
    rows.remove(index);
    row.edit = null;
  }          

////////////////////////////////////////////////////////////////
// Naming
////////////////////////////////////////////////////////////////
  
  /**
   * Loop through all the rows and insure that each 
   * target has a valid name.
   */
  public void checkTargetNames()
  { 
    // skip if no rows or no name column                   
    MgrEditRow[] rows = getRows();     
    if (rows.length == 0 || rows[0].getNameColumnIndex() < 0) 
      return;
    
    // make sure each row has a name
    for(int i=0; i<rows.length; ++i)
    {
      MgrEditRow row = rows[i];              
      String name = row.getName();
      if (name == null)
        row.setName(getUniqueName(row));
    }
  }

  /**                    
   * Convenience for <code>getUniqueName(row, type.toSlotName())</code>
   */
  public String getUniqueName(MgrEditRow row)
  {                                  
    String name = manager.getModel().toType(row.getTarget()).toSlotName();
    return getUniqueName(row,name);
  }                    

  /**
   * Generate a name for the specified row insuring that
   * it is unique both between the current database configuration
   * and the rows which may soon be committed.  The name returned
   * will be escaped.
   */
  public String getUniqueName(MgrEditRow row, String defaultName)
  {                             
    return ObjectUtil.generateUniqueSlotName(defaultName, new NameContainerImpl(row));
  }
    
  private class NameContainerImpl implements ObjectUtil.NameContainer
  {     
    NameContainerImpl(MgrEditRow row) { this.row = row; }
    public boolean contains(String name) { return !isNameUnique(row, name); }
    MgrEditRow row;
  }
    
  /**
   * Return if the specified name for the row is unique.  This
   * method verifies the name is not already used in any of
   * the existing slots on the row's parent, nor in any of rows
   * in this MgrEdit that have the same parent.
   */
  public boolean isNameUnique(MgrEditRow row, String name)
  {                                           
    // first check the parent
    BComponent parent = row.getTargetParent();
    BValue dup = parent.get(name);   
    
    // If we found a duplicate slot, then the simplest most robust 
    // thing to do is just return false; however there is a special 
    // case I am ignoring where the slot with this name is actually 
    // another row that has also has a pending rename
    if (dup != null && dup != row.getTarget())
      return false;
    
    // otherwise check the all rows which might soon be applied
    for(int i=0; i<rows.size(); ++i)
    {
      MgrEditRow r = getRow(i);                              
      
      // if this row doesn't have same parent then it is moot
      if (parent != r.getTargetParent()) continue;
      
      // check if this row has the same name
      String rn = r.getName();
      if (r != row && rn != null && rn.equals(name)) 
        return false; 
    } 
    
    // if all else fails, it must be unique!
    return true;
  }            
  
////////////////////////////////////////////////////////////////
// Types
////////////////////////////////////////////////////////////////
  
  /**
   * This utility method creates a list of MgrTypeInfos which is 
   * the intersection of each row's <code>getAvailableTypes()</code>.
   * Return array of length 0 if no common intersection.
   */
  public static MgrTypeInfo[] getTypeIntersection(MgrEditRow[] rows)
  {                  
    if (rows == null || rows.length == 0)
      return noTypes;
    
    MgrTypeInfo[] intersection = rows[0].getAvailableTypes();  
    if (intersection == null) intersection = noTypes;
    for(int i=1; i<rows.length; ++i)
      intersection = intersect(intersection, rows[i].getAvailableTypes());
    return intersection;
  }                     
  
  /**
   * Get the intersection of the arrays.
   */
  private static MgrTypeInfo[] intersect(MgrTypeInfo[] a1, MgrTypeInfo[] a2)
  {                           
    if (a1 == null || a1.length == 0 || a2 == null || a2.length == 0)
      return noTypes;
      
    ArrayList<MgrTypeInfo> list = new ArrayList<>();
    int len1 = a1.length;
    int len2 = a2.length;   
    
    for(int i1=0; i1<len1; ++i1)
    {
      MgrTypeInfo t1 = a1[i1];             
      for(int i2=0; i2<len2; ++i2)
        if (t1.equals(a2[i2])) { list.add(t1); break; }
    }                       
    
    if (list.size() == 0) return noTypes;      
    return list.toArray(new MgrTypeInfo[list.size()]);
  }  

  private static MgrTypeInfo[] noTypes = new MgrTypeInfo[0];
  
////////////////////////////////////////////////////////////////
// GUI
////////////////////////////////////////////////////////////////

  /**
   * Get the select all flag which is used by prompt() to 
   * determine if all the MgrEditRows should be selected 
   * by default.
   */
  public boolean getSelectAll()
  {
    return selectAll;
  }
  
  /**
   * Set or clear the select all flag which is used by
   * prompt() to determine if all the MgrEditRows should
   * be selected by default.
   */
  public void setSelectAll(boolean selectAll)
  {
    this.selectAll = selectAll;
  }

  /**
   * Prompt the user with a dialog to make the edits using
   * the prompt() method.  Then if the user applies, commit 
   * the changes to the station database using the commit()
   * method.               
   *
   * @return CommandArtifact to undo the operation.
   */
  public CommandArtifact invoke(Context cx)
    throws Exception
  {
    if (rows.size() == 0) return null;
    checkTargetNames();
    if (cx != MgrController.quickContext)
    {
      if (!prompt()) return null;
    }
    return commit();
  } 
  
  /**
   * Prompt the user with a dialog to review/change 
   * each of the MgrEditRows.                       
   *
   * @return false if cancel or true if user applies
   */
  public boolean prompt()
    throws Exception
  {       
    BMgrEditDialog dialog = new BMgrEditDialog(this, getSelectAll(), new TagTableCellRenderer());
    dialog.setScreenSizeToPreferredSize();
    dialog.setBoundsCenteredOnOwner();
    dialog.open();
    return dialog.getResult() == BDialog.OK; 
  }               
  
  /**
   * Give the MgrEdit a chance to validate any input before the dialog closes.
   */
  public void validate(MgrEditRow[] selection)
    throws Exception
  {
    for(int i=0; i<selection.length; i++)
    {
      try
      {
        validate(selection[i]);
      }
      catch(Exception e)
      {
        throw new LocalizableException("workbench", selection[i].getName() + ": " + e.getMessage(), e);
      }
    }
  }

  /**
   * Give the MgrEdit a chance to validate any input before the dialog closes.
   */
  public void validate(MgrEditRow row)
    throws Exception
  {

  }
  
  /**
   * This method commits each MgrEditRow to the station
   * database by calling MgrEditRow.commit() on each row.
   *
   * @return CommandArtifact to undo the operation (not supported yet).
   */
  public CommandArtifact commit()
    throws Exception
  {    
    // get the rows                           
    MgrEditRow[] rows = getRows();
    if (rows.length == 0) return null;
    
    // first commit each row which configures the 
    // components into their desired states
    BComponent container = getAddContainer();
    Context tx = Transaction.start(container, null);
    for(int i=0; i<rows.length; ++i)
    {
      rows[i].commit(tx);
    }

    Transaction.end(container, tx);
    
//    // tag edit dialogs
//    MgrTag tagManager = manager.getMgrTag();
//    boolean isTagMode = manager.getController().tagMode.isSelected();
//    if( isTagMode && tagManager != null)
//    {
//      for(int i = 0; i<rows.length; ++i)
//      {
//        tagManager.editDialog(rows[i]);
//      }
//    }
//    else
//      System.out.println("MrgTag is NULL !!!!!!!!!!!!!!!!");
    
    // now if the components aren't yet mounted (a new 
    // or add operation) then let's add them in bulk
    boolean needAdd = !rows[0].target.isMounted();
    if (needAdd)
    {            
      return manager.getModel().addInstances(rows, null);
    }
    else
    {
      return null;
    }
  }
  
  /**
   * Return whether the MgrColumn should be readonly
   * <p>
   * By default, the MgrColumn's 'readonly' flag is used
   * to determine whether it should be readonly or not
   * 
   * @param selectedRows  The currently selected rows
   * @param col           The specified MgrColumn
   * @return              Return 'true' if the column should be readonly
   * 
   * @see MgrEdit#getColumns()
   * @see MgrColumn#READONLY
   * 
   * @since Niagara 3.6
   */
  public boolean isReadonly(MgrEditRow[] selectedRows, MgrColumn col)
  {
    return col.isReadonly();
  }
  
  public class TagTableCellRenderer extends TableCellRenderer
  {
    /**
     * Get the background used to paint the cell.  Return 
     * null if the standard background should be used.
     */
    public BBrush getBackground(Cell cell)
    {
      MgrEditRow row = getRow(cell.row);
      MgrColumn col = cols[cell.column];
      if(!col.isCellValid(row))
      {
        return BBrush.makeSolid(BColor.lightGrey);
      }
      return null;
    }

    /**
     * Get the selection background used to paint the cell.
     */
    public BBrush getSelectionBackground(Cell cell)
    {
      MgrEditRow row = getRow(cell.row);
      MgrColumn col = cols[cell.column];
      if(!col.isCellValid(row))
      {
        return BBrush.makeInverse(BColor.lightGray);
      }
      return Theme.table().getSelectionBackground(getTable());
    }


  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  public final static int DEFAULT = 0x0000;   // state when invoked via ? process. 
  public final static int ADD     = 0x0001;   // state when invoked via add process. 
  public final static int EDIT    = 0x0002;   // state when invoked via edit process. 
  public final static int MATCH   = 0x0003;   // state when invoked via match process. 
  public final static int NEW     = 0x0004;   // state when invoked via new process. 
  
  BAbstractManager manager;
  String title;
  MgrColumn[] cols;       
  ArrayList<MgrEditRow> rows;
  boolean selectAll;
//  BIDeployable template;
} 

