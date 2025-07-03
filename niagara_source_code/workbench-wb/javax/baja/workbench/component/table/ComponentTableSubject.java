/*
 * Copyright 2005, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.workbench.component.table;

import javax.baja.sys.BComponent;
import javax.baja.ui.table.TableSubject;

/**
 * ComponentTableSubject
 * 
 * @author    Brian Frank
 * @creation  3 May 05
 * @version   $Revision: 2$ $Date: 6/11/07 12:41:49 PM EDT$
 * @since     Baja 1.0
 */
public class ComponentTableSubject
  extends TableSubject
{            

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
               
  /**
   * Convenience for <code>this(table, rows, -1)</code>
   */
  public ComponentTableSubject(BComponentTable table, int[] rows)
  {                                         
    this(table, rows, -1);
  }
  
  /**
   * Construct with specified list of row indices and active row index.
   */
  public ComponentTableSubject(BComponentTable table, int[] rows, int activeRow)
  {                                  
    super(table, rows, activeRow);
  }                     

////////////////////////////////////////////////////////////////
// ComponentTable
////////////////////////////////////////////////////////////////
  
  /**
   * Get the selection as components.
   */
  public BComponent[] getComponents()
  {                                         
    return (BComponent[])get(new BComponent[size()]);
  }                               
  
  /**
   * Get the selection at given index as a component.
   */
  public BComponent getComponent(int index)
  {                                         
    return (BComponent)get(index);
  }                               

  /**
   * Get the active object as a component.
   */
  public BComponent getActiveComponent()
  {                                         
    return (BComponent)getActive();
  }                               
  
}
