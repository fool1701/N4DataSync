/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.workbench.bql.table;

import javax.baja.collection.BIRandomAccessTable;
import javax.baja.collection.BITable;
import javax.baja.collection.ColumnList;
import javax.baja.collection.Tables;
import javax.baja.gx.BImage;
import javax.baja.naming.BOrd;
import javax.baja.sys.BIObject;
import javax.baja.sys.BObject;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.ui.table.TableModel;

/**
 * BqlTableModel is a table model which provides a convenient interface for
 * presenting a BQL query result as a table.
 * 
 * @author    John Sublett
 * @creation  06 Dec 2004
 * @version   $Revision: 6$ $Date: 4/20/06 9:27:34 AM EDT$
 * @since     Baja 1.0
 */
public class BqlTableModel
  extends TableModel
{
  /**
   * Default constructor.
   */
  public BqlTableModel()
  {
    columns = new BqlTableColumn[0];
  }

  /**
   * Constructor with a column list.
   */
  public BqlTableModel(BqlTableColumn[] columns)
  {
    setColumns(columns);
  }

  /**
   * Get the base object.
   */
  public BObject getBase()
  {
    return base;
  }

  /**
   * Set the base that the ord is resolved against.
   *
   * @param base The base object that is passed to
   *   resolve() when the BQL ord is resolved.
   */
  public void setBase(BObject base)
  {
    setBase(base, (BOrd)null);
  }

  /**
   * Set the base that the ord is resolved against.
   *
   * @param base The base object that is passed to
   *   resolve() when the BQL ord is resolved.
   * @param prefix The prefix that is added before the
   *   BQL ord before resolving it.  At resolve time this
   *   is equivalent to <code>BOrd.make(prefix, bql).resolve(base)</code>
   *   where bql is the ord constructed from the columns, extent, etc.
   */
  public void setBase(BObject base, BOrd prefix)
  {
    this.base = base;
    this.prefix = prefix;
  }

  /**
   * Set the base that the ord is resolved against.
   *
   * @param base The base object that is passed to
   *   resolve() when the BQL ord is resolved.
   * @param prefix The prefix that is added before the
   *   BQL ord before resolving it.  At resolve time this
   *   is equivalent to <code>BOrd.make(BOrd.make(prefix), bql).resolve(base)</code>
   *   where bql is the ord constructed from the columns, extent, etc.
   */
  public void setBase(BObject base, String prefix)
  {
    setBase(base, BOrd.make(prefix));
  }

  /**
   * Get the extent.
   */
  public String getExtent()
  {
    return extent;
  }

  /**
   * Set the extent.  This is the "from" clause without
   * the "from".
   */
  public void setExtent(String extent)
  {
    this.extent = extent;
  }

  /**
   * Get the predicate.
   */
  public String getPredicate()
  {
    return predicate;
  }

  /**
   * Set the predicate for the query.  This is the "where" clause
   * without the "where".
   */
  public void setPredicate(String predicate)
  {
    this.predicate = predicate;
  }

  /**
   * Get the ord that will be resolved to fetch the table data.
   */
  public BOrd getOrd()
  {
    if (ord == null) updateOrd();
    return ord;
  }

  /**
   * Get the Status column.  If more than one Status column has been defined
   * it is undefined as to which one will be returned.
   */
  public BqlTableColumn.Status getStatusColumn()
  {
    return statusColumn;
  }

  /**
   * Get the Icon column.  If more than one Icon column has been defined
   * it is undefined as to which one will be returned.
   */
  public BqlTableColumn.Icon getIconColumn()
  {
    return iconColumn;
  }

  /**
   * Get the Nav column.  If more than one Nav column has been defined
   * it is undefined as to which one will be returned.
   */
  public BqlTableColumn.Nav getNavColumn()
  {
    return navColumn;
  }

////////////////////////////////////////////////////////////////
// Columns
////////////////////////////////////////////////////////////////

  /**
   * Set the columns for the query result.
   */
  public void setColumns(BqlTableColumn[] columns)
  {
    this.columns = new BqlTableColumn[columns.length];
    System.arraycopy(columns, 0, this.columns, 0, columns.length);
    
    statusColumn = null;
    iconColumn = null;
    for (int i = 0; i < columns.length; i++)
    {
      if (columns[i] instanceof BqlTableColumn.Status)
        statusColumn = (BqlTableColumn.Status)columns[i];
      else if (columns[i] instanceof BqlTableColumn.Icon)
        iconColumn = (BqlTableColumn.Icon)columns[i];
      else if (columns[i] instanceof BqlTableColumn.Nav)
        navColumn = (BqlTableColumn.Nav)columns[i];
    }
  }

  /**
   * Get the number of BQL table columns.  This doesn't necessarily map
   * directly to the number of columns in the result.  Some columns
   * insert two columns into the query.
   */
  public int getBqlColumnCount()
  {
    return columns.length;
  }

  /**
   * Get the BQL table column at the specified index.
   */
  public BqlTableColumn getBqlColumn(int index)
  {
    return columns[index];
  }

////////////////////////////////////////////////////////////////
// TableModel
////////////////////////////////////////////////////////////////

  /**
   * Get the number of rows in the table.
   */
  public int getRowCount()
  {
    if (!loaded) return 0;
    return resultTable.size();
  }

  /**
   * Get the column count.
   */
  public int getColumnCount()
  {
    if (columns == null)
      return 0;
    else
      return columns.length;
  }

  /**
   * Get the name of the column at the specified index.
   */
  public String getColumnName(int index)
  {
    return resultColumns.get(index).getDisplayName(null);
  }

  /**
   * Get the value at the specified cell location.
   */
  public Object getValueAt(int row, int col)
  {
    return columns[col].getValueAt(row);
  }

  /**
   * Get the icon for the specified row.
   */
  public BImage getRowIcon(int row)
  {
    if (iconColumn == null) return null;
    return (BImage)iconColumn.getValueAt(row);
  }

////////////////////////////////////////////////////////////////
// Load
////////////////////////////////////////////////////////////////

  /**
   * Build the ord from the current configuration.
   */
  private BOrd updateOrd()
  {
    StringBuilder s = new StringBuilder(128);
    s.append("bql:select ");
    int count = columns.length;
    for (int i = 0; i < count; i++)
    {
      if (i != 0) s.append(", ");
      s.append(columns[i].getProjection());
    }
    
    if (extent != null)
      s.append(" from ").append(extent);

    if (predicate != null)
      s.append(" where ").append(predicate);

    return ord = BOrd.make(prefix, s.toString());
  }

  /**
   * Reset the result.  This forces a new resolve.
   */
  private void reset()
  {
    resultTable = null;
    resultColumns = null;
    loaded = false;
    updateTable();
  }

  /**
   * Has the model been loaded with a call to load().
   */
  public boolean isLoaded()
  {
    return loaded;
  }

  /**
   * Load the table data.  This method MUST be called before
   * any data will appear in the table.  If the BqlTable is
   * configured to auto-update, updates will not begin
   * until load() is called at least once directly.
   */
  @SuppressWarnings("unchecked")
  public void load()
  {
    synchronized (loadLock)
    {
      if (loading) return;
      loading = true;
    }

    try
    {
      if (ord == null) ord = updateOrd();
        
      BObject o = ord.resolve(base).get();
      if (!(o instanceof BITable))
        throw new BajaRuntimeException("Ord does not resolve to a table.");
      
      resultTable = Tables.slurp((BITable<BIObject>)o);
      resultColumns = resultTable.getColumns();
      
      int count = resultColumns.size();
      for (int i = 0; i < count; i++)
        columns[i].load(resultTable, resultColumns.get(i));
      
      updateTable();
      synchronized (loadLock)
      {
        loaded = true;
        loading = false;
      }
      // Notify the table that a query has completed.  This provides
      // the hook for the table to schedule the next query.
      notifyTable();
    }
    catch(RuntimeException e)
    {
      synchronized (loadLock)
      {
        loaded = true;
        loading = false; 
      }
      notifyTable();
      throw e;
    }
  }

  /**
   * Notify the table that query processing is complete.
   */
  private void notifyTable()
  {
    BBqlTable table = (BBqlTable)getTable();
    if (table == null) return;
    table.queryComplete();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BObject base;
  private BOrd prefix;
  private BOrd ord;

  private BIRandomAccessTable<BIObject> resultTable;
  private ColumnList resultColumns;
  private String predicate;
  private String extent = "baja:Component";
  
  private BqlTableColumn[] columns;
  
  private BqlTableColumn.Status statusColumn;
  private BqlTableColumn.Icon iconColumn;
  private BqlTableColumn.Nav navColumn;
  
  private Object loadLock = new Object();
  private boolean loading = false;
  private boolean loaded = false;
}
