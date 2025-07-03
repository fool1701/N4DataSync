/*                          
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.treetable;

import javax.baja.ui.table.*;

/**
 * TreeTableHeaderRenderer is the TableHeaderRenderer used by BTreeTable
 *
 * @author    Brian Frank 
 * @creation  7 Jan 04
 * @version   $Revision: 4$ $Date: 3/28/05 10:32:42 AM EST$
 * @since     Baja 1.0
 */
public class TreeTableHeaderRenderer
  extends TableHeaderRenderer
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
  
}
