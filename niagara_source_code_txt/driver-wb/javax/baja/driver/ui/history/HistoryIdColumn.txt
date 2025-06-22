/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.ui.history;

import javax.baja.history.BHistoryId;
import javax.baja.history.BIHistory;
import javax.baja.sys.BObject;
import javax.baja.sys.Flags;
import javax.baja.workbench.BWbEditor;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrEditRow;

/**
 * HistoryIdColumn is a MgrColumn for accessing the id of a BIHistory.
 *
 * @author    John Sublett
 * @creation  12 Jan 2004
 * @version   $Revision: 3$ $Date: 5/26/04 7:19:56 AM EDT$
 * @since     Baja 1.0
 */
public class HistoryIdColumn
  extends MgrColumn
{
  public HistoryIdColumn(String displayName)
  {
    super(displayName, Flags.READONLY);
  }
  
  /**
   * Given a row, extract the column value.
   */
  public Object get(Object row)
  {
    return ((BIHistory)row).getId();
  }
  
  public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
    throws Exception
  {
    if (currentEditor == null)
      currentEditor = BWbFieldEditor.makeFor(BHistoryId.DEFAULT);
    
    if (rows.length > 1)
    {
      currentEditor.loadValue(BHistoryId.NULL);
      currentEditor.setReadonly(true);
    }
    else
    {
      currentEditor.loadValue((BObject)get(rows[0].getCell(colIndex)));
    }
    
    return currentEditor;
  }
}