/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.table.binding;

import java.util.HashMap;
import java.util.Map;
import javax.baja.collection.BIRandomAccessTable;
import javax.baja.collection.BITable;
import javax.baja.collection.Column;
import javax.baja.collection.ColumnList;
import javax.baja.collection.Tables;
import javax.baja.nre.util.SortUtil;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIObject;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.ui.BBinding;
import javax.baja.ui.table.TableModel;
import javax.baja.units.BUnit;
import javax.baja.units.BUnitConversion;

/**
 * BoundTableModel is used with BBoundTable.
 *
 * @author    Brian Frank
 * @creation  31 May 04
 * @version   $Revision: 3$ $Date: 3/28/05 10:32:32 AM EST$
 * @since     Baja 1.0
 */
public class BoundTableModel
  extends TableModel
{ 
   
////////////////////////////////////////////////////////////////
// TableModel
////////////////////////////////////////////////////////////////

  public int getRowCount() 
  { 
    synchronized(monitor)
    {
      if (rows == null) return 0;
      return rows.length; 
    }
  }
   
  public int getColumnCount() 
  {
    synchronized(monitor)
    {
      if (cols == null) return 0;
      return cols.size(); 
    }
  }
   
  public String getColumnName(int col) 
  { 
    synchronized(monitor)
    {
      ColumnInfo colInfo = cols.get(Integer.valueOf(col));
      return colInfo.getDisplayName();
    }
  } 
   
  public Object getValueAt(int row, int col) 
  {        
    synchronized(monitor)
    {
      return rows[row].get(row, col);
    }
  }           

  public boolean isColumnSortable(int col)
  {
    return true;
  }

  public void sortByColumn(int col, boolean ascending)
  {       
    synchronized(monitor)
    {
      Object[] keys = new Object[rows.length];
      for(int i=0; i<keys.length; ++i)
        keys[i] = rows[i].get(i, col);
      SortUtil.sort(keys, rows, ascending);    
    }
  }
      
////////////////////////////////////////////////////////////////
// Binding
////////////////////////////////////////////////////////////////
  
  /**
   * Rebuild the table data structures from the configured binding.
   */
  void rebind()
  {               
    // get all bindings on table
    BBinding[] bindings = getTable().getBindings();
    
    Context cx = null;
    
    // map all valid table bindings to row sets
    int n = 0;
    RowSet[] sets = new RowSet[bindings.length];
    for(int i=0; i<bindings.length; ++i)
    {
      if (bindings[i] instanceof BTableBinding)
      {                                       
        BTableBinding binding = (BTableBinding)bindings[i];
        if (binding.isBound())
        {
          @SuppressWarnings("unchecked")
          BITable<BIObject> t = (BITable<BIObject>)binding.get();
          if (cx == null) cx = new BasicContext(binding.getTarget().getUser(), binding.getTarget().getLanguage());
          sets[n++] = new RowSet(Tables.slurp(t));
        }
      }
    }
    
    // compute total rows and offsets
    int rowCount = 0;
    for(int i=0; i<n; ++i)
    {                
      RowSet set = sets[i];
      set.offset = rowCount;
      set.size   = set.table.size();
      rowCount  += set.size;
    }  
    
    // map model row lookup to rowsets
    Row[] rows = new Row[rowCount];
    for(int i=0; i<n; ++i)
    {               
      RowSet set = sets[i];
      for(int j=0; j<set.size; ++j)
        rows[set.offset + j] = new Row(set, j);
    }        
    
    // find all columns by display name and 
    // map to an model based col index
    HashMap<String, ColumnInfo> colsByName = new HashMap<>();
    HashMap<Integer, ColumnInfo> colsByIndex = new HashMap<>();
    
    for(int i=0; i<n; ++i)
    {
      ColumnList colList = sets[i].table.getColumns();
      for(int j=0; j<colList.size(); ++j)
      {        
        Column col = colList.get(j);
        String name = col.getName();
                
        if (colsByName.get(name) == null)
        {
          ColumnInfo colInfo = new ColumnInfo(col, colsByName.size(), cx);
          colsByName.put(name, colInfo);
          colsByIndex.put(Integer.valueOf(colInfo.index), colInfo);
        }
      }
    }      
    
    // map allCols hashmap into array                            
    int colCount = colsByName.size();                                       

    // map model col lookup to rowsets
    for(int i=0; i<n; ++i)
    {
      RowSet set = sets[i];              
      set.colMap = new ColumnInfo[colCount];
      ColumnList colList = sets[i].table.getColumns();
      for(int j=0; j<colList.size(); ++j)
      {                     
        Column column = colList.get(j);
        String name = column.getName();
        ColumnInfo colInfo = colsByName.get(name);
        set.colMap[colInfo.index] = colInfo;
      }
    }
    
    synchronized(monitor)
    {
      // update table
      this.rows = rows;
      this.cols = colsByIndex;
    }
    
    updateTable();
  }
      
  private static final class ColumnInfo
  {
    ColumnInfo(Column col, int index, Context cx)
    {
      this.col = col;
      this.index = index;
      this.cx = new BasicContext(cx, col.getFacets());
    }
    
    String getDisplayName()
    {
      String displayName = col.getDisplayName(cx);
      
      // If available, apply units (mimic behavior of CollectionTable)
      BFacets facets = col.getFacets();
      if ((facets != null) && !facets.isNull())
      {
        BUnit unit = (BUnit)facets.get(BFacets.UNITS);
        if ((unit != null) && !unit.isNull())
        {
          int convert = com.tridium.sys.Nre.unitConversion;
          convert = facets.geti(BFacets.UNIT_CONVERSION, convert);
          if (convert != 0)
            unit = BUnitConversion.make(convert).getDesiredUnit(unit);
          displayName += " (" + unit.getSymbol() + ")";
        }
      }
      
      return displayName;
    }
    
    private int index;
    private Column col;
    private Context cx;
  }
   
////////////////////////////////////////////////////////////////
// RowSet
////////////////////////////////////////////////////////////////
  
  /**
   * Set stores the ITable of one binding and how it 
   * is merged into the union table model.
   */
  private static class RowSet
  {           
    RowSet(BIRandomAccessTable<BIObject> table)
    {
      this.table = table;
    }          
    
    BIRandomAccessTable<BIObject> table;     // this row set's table
    int      size;     // table.size()
    int      offset;   // model.row - offset -> table.row (without sorting)
    ColumnInfo[] colMap;   // colMap[model.col]  -> table column information
  }

////////////////////////////////////////////////////////////////
// Row
////////////////////////////////////////////////////////////////
  
  /**
   * Row maps a single row of a RowSet so that we can
   * sort at will without loosing our row set indices. 
   */        
  private static class Row
  {               
    Row(RowSet set, int rowIndex)
    {
      this.set = set;
      this.rowIndex = rowIndex;
    }
    
    public final Object get(int modelRow, int modelCol)
    {
      ColumnInfo tableColInfo = set.colMap[modelCol];
      if (tableColInfo == null) return "";
      BIObject obj = set.table.get(rowIndex).cell(tableColInfo.col);
      if (obj != null)  return obj.toString(tableColInfo.cx);
      return obj;
    }
    
    RowSet set;   // row set this row maps to
    int rowIndex; // row index into set.table
  }
  
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private Row[] rows;
  private Map<Integer, ColumnInfo> cols;
  
  private Object monitor = new Object();
}
