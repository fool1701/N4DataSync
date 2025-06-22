/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.component.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.baja.gx.BImage;
import javax.baja.nre.util.SortUtil;
import javax.baja.sys.BComponent;
import javax.baja.sys.Property;
import javax.baja.ui.table.TableModel;
import javax.baja.ui.util.UiLexicon;

/**
 * ComponentTableModel is the specialization of TableModel
 * for the BComponentTable class.  It provides a table model
 * where the rows are a list of BComponents.
 * <p>
 * The columns are configurable using the Column callback 
 * interface.  For example to configure the model to dislay 
 * three columns which are the component name, property x, and 
 * property y:
 * <pre>
 *   model.setColumns(new ComponentTableModel.Column[]
 *    {
 *      new ComponentTableModel.DisplayNameColumn(),
 *      new ComponentTableModel.PropertyColumn(x),
 *      new ComponentTableModel.PropertyColumn(y)
 *    });
 * </pre>
 *
 * @author    Brian Frank
 * @creation  21 Mar 02
 * @version   $Revision: 13$ $Date: 12/19/07 4:13:50 PM EST$
 * @since     Baja 1.0
 */
public class ComponentTableModel
  extends TableModel
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor with columns.
   */
  public ComponentTableModel(Column[] columns)
  {
    this.columns = columns;
  }

  /**
   * Default constructor creates one column using DisplayNameColumn.
   */
  public ComponentTableModel()
  {
    this.columns = new Column[] { new DisplayNameColumn() };
  }

////////////////////////////////////////////////////////////////
// Component Access
////////////////////////////////////////////////////////////////

  /**
   * Get the component for the specified row.
   */
  public BComponent getComponentAt(int row)
  {
    synchronized(this)
    {
      return rows.get(row).component;
    }
  }
  
  /**
   * Get the list of all components in the model.
   */
  public BComponent[] getRows()
  {
    return rows.stream().map(row -> row.component).toArray(BComponent[]::new);
  }
  
  /**
   * Get a row number for the BComponent with 
   * the specified name, or -1 if no row exists
   * with that name.
   */
  public int getRowByName(String name)
  {
    return getRowsByName(new String[] { name })[0];
  }
  
  /**
   * Get the row number for the BComponent's with 
   * the specified names.
   */
  public int[] getRowsByName(String[] names)
  {
    int[] indices = new int[rows.size()];
    for(int i=0; i<indices.length; ++i) 
      indices[i] = -1;
      
    int count = 0;
    
    synchronized(this)
    {
      for(int i=0; i<rows.size(); ++i)
      {
        BComponent component = rows.get(i).component;
        for (String name : names)
        {
          if (component.getName().equals(name))
          {
            indices[count++] = i;
            break;
          }
        }
      }
    }
    
    int[] result = new int[count];
    System.arraycopy(indices, 0, result, 0, count);
    return result;
  }
  
  /**
   * Add a new row using the specified component.
   */
  public void addRow(BComponent component)
  {
    boolean wasEmpty = rows.size() == 0;
    synchronized(this)
    {
      rows.add(new Row(component));
    }
    updateTable(wasEmpty);
  }

  /**
   * Add a row for each specified component.
   */
  public void addRows(BComponent[] components)
  {
    boolean wasEmpty = rows.size() == 0;
    synchronized(this)
    {
      for (BComponent component : components)
      {
        rows.add(new Row(component));
      }
    }
    updateTable(wasEmpty);
  }
  
  /**
   * Remove a row by property name.
   *
   * @return true if a row was found and removed for the
   *   specified component name, false otherwise.
   */
  public boolean removeRow(String name)
  {
    boolean found = false;
    
    synchronized(this)
    {
      for(int i=0; i<rows.size(); ++i)
      {
        Row row = rows.get(i);
        if (row.name.equals(name)) 
        {
          rows.remove(i);
          found = true;
          break;
        }
      }
    }

    if (found) updateTable();
    return found;
  }

  /**
   * Remove the row for the specified component.
   *
   * @return true if a row was found and removed for the
   *   specified component, false otherwise.
   */                 
  public boolean removeRow(BComponent component)
  {
    boolean found = false;
    
    synchronized(this)
    {
      for(int i=0; i<rows.size(); ++i)
      {
        Row row = rows.get(i);
        if (row.component == component) 
        {
          rows.remove(i);
          found = true;
          break;
        }
      }
    }
    
    if (found) updateTable();
    return found;
  }

  /**
   * Remove all rows.
   */ 
  public void removeAllRows()
  {
    synchronized(this)
    {
      rows = new ArrayList<>();
    }
    updateTable();
  }

  /**
   * Remove all current rows, and add specified rows.
   */
  public void setRows(BComponent[] components)
  {
    synchronized(this)
    {
      rows = new ArrayList<>(Math.max(components.length*2, 10));
      for (BComponent component : components)
      {
        rows.add(new Row(component));
      }
    }
    updateTable(true); // always resize columns since we just replaced all rows
  }

  /**
   * This method should be called when a component is renamed.
   *
   * @return Returns true if a row was found for the component
   *   with the old name, false otherwise.
   */
  public synchronized boolean renameRow(String oldName, String newName)
  {
    for (Row row : rows)
    {
      if (row.name.equals(oldName))
      {
        row.name = newName;
        return true;
      }
    }
    
    return false;
  }
  
  /**
   * Set the columns.
   */
  public void setColumns(Column[] columns)
  {
    synchronized(this)
    {
      this.columns = columns;
    }
    updateTable();
  }
  
  /**
   * Reorder the rows with the specified indices 
   * of the current ordering.
   */
  public void reorderRows(int[] origIndices)
  {
    List<Row> temp = new ArrayList<>();
    List<Row> orig = this.rows;
    
    if (origIndices.length != orig.size())
    {
      throw new IllegalArgumentException("invalid length");
    }

    for (int origIndex : origIndices)
    {
      temp.add(orig.get(origIndex));
    }
      
    rows = temp;
    updateTable();
  }

////////////////////////////////////////////////////////////////
// TableModel
////////////////////////////////////////////////////////////////

  public synchronized int getRowCount() 
  { 
    return rows.size(); 
  }
  
  public synchronized int getColumnCount() 
  { 
    try
    {
      return columns.length; 
    }
    catch(NullPointerException e)
    {
      // don't know how this happens, but it does
      return 0;
    }
  }
  
  public synchronized String getColumnName(int index) 
  { 
    return columns[index].getName(); 
  }

  public synchronized BImage getRowIcon(int row) 
  { 
    return rows.get(row).getIcon();
  }

  public Object getSubject(int row)
  {
    return getComponentAt(row);
  }
    
  public Object getValueAt(int row, int col) 
  { 
    BComponent component =null; 
    synchronized(this)
    {
      component = rows.get(row).component; 
    }
    return columns[col].getValue(component);
  }

////////////////////////////////////////////////////////////////
// Sorting
////////////////////////////////////////////////////////////////
  
  /**
   * All columns are sortable.
   */
  public boolean isColumnSortable(int col)
  {
    return true;
  }
  
  /**
   * Sort the specified column.  If the column contains
   * Objects which implement the Comparable interface then
   * that is how the sort is performed.  Otherwise the sort
   * is performed on the result of the toString() method
   * of the Object value.
   */
  public synchronized void sortByColumn(int col, boolean ascending)
  {
    Object[] keys = getColumnValues(col);
    Row[] temp = rows.toArray(new Row[rows.size()]);
    
    SortUtil.sort(keys, temp, ascending);     
    
    rows = new ArrayList<>();
    Collections.addAll(rows, temp);
  }

////////////////////////////////////////////////////////////////
// Column
////////////////////////////////////////////////////////////////  

  /**
   * The Column interface is used to configure how
   * to get column values from a BComponent row.
   */
  public interface Column
  {
    /** Get the column name. */
    public String getName();
    
    /** Get the column value for the specified component.*/
    public Object getValue(BComponent component);    
  }

////////////////////////////////////////////////////////////////
// DisplayNameColumn
////////////////////////////////////////////////////////////////  

  /**
   * DisplayNameColumn provides a default implementation
   * of Column that returns the display name.
   */
  public static class DisplayNameColumn
    implements Column
  {
    public String getName() { return name; }
    public Object getValue(BComponent c) { return c.getDisplayName(null); }
    String name = UiLexicon.bajaui().getText("name");
  }

////////////////////////////////////////////////////////////////
// PropertyColumn
////////////////////////////////////////////////////////////////  

  /**
   * PropertyColumn provides a default implementation
   * of Column that returns the value of a property.
   */
  public static class PropertyColumn
    implements Column
  {
    public PropertyColumn(Property prop) { this(prop.getDefaultDisplayName(null), prop); }
    public PropertyColumn(String name, Property prop) { this.name = name; this.prop = prop; }
    public String getName() { return name; }
    public Object getValue(BComponent c) { return c.get(prop); }
    protected String name;
    protected Property prop;
  }

////////////////////////////////////////////////////////////////
// Row
////////////////////////////////////////////////////////////////

  static class Row
  {
    Row(BComponent component)
    {
      this.component = component;
      this.name = component.getName();
      this.icon = BImage.make(component.getIcon());
    }

    BImage getIcon()
    {
      if (component.isPendingMove())
        return icon.getDisabledImage();
      else 
        return icon;
    }
    
    String name;
    BImage icon;
    BComponent component;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  List<Row> rows = new ArrayList<>();
  Column[] columns = new Column[0];
  
}