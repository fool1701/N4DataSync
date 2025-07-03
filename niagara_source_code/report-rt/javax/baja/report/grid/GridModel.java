/*
 * Copyright 2006, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.report.grid;

import java.util.*;
import javax.baja.naming.*;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * GridModel represents the resolved model of a BGrid.
 *
 * @author    Andy Frank
 * @creation  1 Nov 06
 * @version   $Revision: 3$ $Date: 11/9/06 10:18:17 AM EST$
 * @since     Niagara 3.2
 */
public abstract class GridModel
{

////////////////////////////////////////////////////////////////
// Grid
////////////////////////////////////////////////////////////////

  /**
   * Get the number of rows.
   */
  public abstract int getRowCount();
  
  /**
   * Get the number of columns.
   */
  public abstract int getColumnCount();
  
  /**
   * Get the display name for this column.
   */
  public abstract String getColumnName(int col);
  
  /**
   * Get the BFormat for this column, or null if it 
   * does not have one.
   */
  public BFormat getColumnFormat(int col)
  {
    return null;
  } 
  
  /**
   * Get the OrdTarget at this grid position. If this
   * grid cell failed to be resolved then returns null.
   */ 
  public abstract OrdTarget getTargetAt(int row, int col);   
   
  /**
   * Get the BObject at this grid position.  If this
   * grid cell failed to be resolved then returns null.
   */
  public abstract BObject getObjectAt(int row, int col);
  
  /**
   * Get all the BComponents in this model. If no
   * components are found, return an empty array.
   */
  public BComponent[] getComponents()
  {
    if (components == null)
    {
      ArrayList<BComponent> list = new ArrayList<>();
      for (int i=0; i<getRowCount(); i++)
        for (int j=0; j<getColumnCount(); j++)
        {
          BObject obj = getObjectAt(i, j);
          if (obj instanceof BComponent) list.add((BComponent)obj);
        }
      components = list.toArray(new BComponent[list.size()]);
    }
    return components;
  }

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private BComponent[] components = null;
  
}



