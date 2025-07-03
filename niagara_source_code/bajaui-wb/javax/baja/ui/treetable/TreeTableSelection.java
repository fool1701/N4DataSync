/*                          
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.treetable;

import javax.baja.ui.table.*;

/**
 * TreeTableSelection is the TableSelection used by BTreeTable
 *
 * @author    Brian Frank 
 * @creation  7 Jan 04
 * @version   $Revision: 5$ $Date: 5/4/05 8:11:16 PM EDT$
 * @since     Baja 1.0
 */
public class TreeTableSelection
  extends TableSelection
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

  /**
   * Return TreeTableSubject instance.
   */
  public TableSubject getSubject(int activeRow)
  {                   
    return new TreeTableSubject((BTreeTable)getTable(), getRows(), activeRow);                  
  }  
  
}
