/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.workbench.bql.table;

import javax.baja.ui.table.TableSelection;
import javax.baja.ui.table.TableSubject;

/**
 * BqlTableSelection is a selection for a BqlTable.
 * 
 * @author    Brian Frank
 * @creation  3 May 05
 * @version   $Revision: 3$ $Date: 6/11/07 12:41:48 PM EDT$
 * @since     Baja 1.0
 */
public class BqlTableSelection
  extends TableSelection
{

  /**
   * Return BqlTableSubject instance.
   */
  public TableSubject getSubject(int activeRow)
  {                   
    return new BqlTableSubject((BBqlTable)getTable(), getRows(), activeRow);                  
  }  
  
}
