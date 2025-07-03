/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.workbench.bql.table;

import javax.baja.naming.BOrd;
import javax.baja.naming.BatchResolve;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.ui.table.TableSubject;

/**
 * BqlTableSubject
 * 
 * @author    Brian Frank
 * @creation  3 May 05
 * @version   $Revision: 3$ $Date: 1/14/11 2:02:39 PM EST$
 * @since     Baja 1.0
 */
public class BqlTableSubject
  extends TableSubject
{            

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
               
  /**
   * Convenience for <code>this(table, rows, -1)</code>
   */
  public BqlTableSubject(BBqlTable table, int[] rows)
  {                                         
    this(table, rows, -1);
  }
  
  /**
   * Construct with specified list of row indices and active row index.
   */
  public BqlTableSubject(BBqlTable table, int[] rows, int activeRow)
  {                                  
    super(table, rows, activeRow);
  }                     

////////////////////////////////////////////////////////////////
// Subject Overrides
////////////////////////////////////////////////////////////////
  
  /**
   * Return <code>resolve()</code>.
   */
  public Object[] get()
  {              
    return resolve();
  }  

  /**
   * Return <code>resolve(index)</code>.
   */
  public Object get(int index)
  {              
    return resolve(index);
  }  

////////////////////////////////////////////////////////////////
// Resolve
////////////////////////////////////////////////////////////////
  
  /**
   * If there is an Nav column, then resolve the ords, 
   * otherwise return null.
   */
  public BObject[] resolve()
  {                
    if (resolved == null)
    {
      BqlTableModel model = ((BBqlTable)getTable()).getBqlModel();
      BqlTableColumn.Nav nav = model.getNavColumn();
      if (nav == null) return null;
      
      BOrd[] ords = new BOrd[size()];
      for(int i=0; i<ords.length; ++i)
      {
        String ordString = ((BString)nav.getValueAt(getRow(i))).getString();
        ords[i] = BOrd.make(ordString);        
      }                                
      
      resolved = new BatchResolve(ords).resolve(model.getBase()).getTargetObjects();
    }
    return resolved.clone();
  }                               
  
  /**
   * Get the resolved object at the specified index.
   */
  public BObject resolve(int index)
  {
    //Issue 11245
    BObject [] resolved = resolve();
    
    if (resolved == null) return null;
    if (index < 0 || index > resolved.length) return null;
    
    return resolved[index];
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BObject[] resolved;       
}
