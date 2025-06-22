/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.ui.history;

import javax.baja.history.BIHistory;
import javax.baja.sys.Flags;
import javax.baja.workbench.mgr.MgrColumn;

/**
 * HistoryNameColumn is a MgrColumn for accessing the name of a BIHistory.
 *
 * @author    John Sublett
 * @creation  12 Jan 2004
 * @version   $Revision: 2$ $Date: 5/26/04 7:20:07 AM EDT$
 * @since     Baja 1.0
 */
public class HistoryNameColumn
  extends MgrColumn
{
  public HistoryNameColumn(String displayName)
  {
    super(displayName, Flags.READONLY);
  }
  
  /**
   * Given a row, extract the column value.
   */
  public Object get(Object row)
  {
    return ((BIHistory)row).getId().getHistoryName();
  }
}