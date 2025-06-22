/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.ui.history;

import javax.baja.nav.BINavNode;
import javax.baja.sys.Flags;
import javax.baja.workbench.BWbEditor;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrEditRow;

/**
 * NavNameColumn is a MgrColumn for accessing the nave name of a BINavNode.
 *
 * @author    John Sublett
 * @creation  12 Jan 2004
 * @version   $Revision: 2$ $Date: 5/26/04 7:20:18 AM EDT$
 * @since     Baja 1.0
 */
public class NavNameColumn
  extends MgrColumn
{
  public NavNameColumn(String displayName)
  {
    super(displayName, Flags.READONLY);
  }
  
  /**
   * Given a row, extract the column value.
   */
  public Object get(Object row)
  {
    if (row instanceof BINavNode)
      return ((BINavNode)row).getNavName();
    else
      return row.toString();
  }
  
  public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
    throws Exception
  {
    return null;
  }
}