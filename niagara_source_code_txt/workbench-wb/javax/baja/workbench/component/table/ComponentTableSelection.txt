/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.component.table;

import javax.baja.ui.table.TableSelection;
import javax.baja.ui.table.TableSubject;

/**
 * ComponentTableSelection is the specialization of 
 * TableSelection for the BComponentTable class.
 *
 * @author    Brian Frank
 * @creation  21 Mar 02
 * @version   $Revision: 4$ $Date: 5/4/05 8:28:25 PM EDT$
 * @since     Baja 1.0
 */
public class ComponentTableSelection
  extends TableSelection
{  

  /**
   * Return ComponentTableSubject instance.
   */
  public TableSubject getSubject(int activeRow)
  {                   
    return new ComponentTableSubject((BComponentTable)getTable(), getRows(), activeRow);                  
  }  
  
}