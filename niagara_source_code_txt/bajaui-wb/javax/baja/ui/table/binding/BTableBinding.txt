/*                          
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.table.binding;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;

/**
 * BTableBinding bindings ITable data as zero or 
 * more rows in a BBoundTable.
 *
 * @author    Brian Frank
 * @creation  31 May 04
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:32 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = {
    @AgentOn(
      types = "bajaui:BoundTable"
    ),
    @AgentOn(
      types = "baja:ITable"
    )
  }
)
public class BTableBinding
  extends BBinding
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.table.binding.BTableBinding(1470850252)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTableBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BICollection
////////////////////////////////////////////////////////////////
  
//  /**
//   * Return <code>cursor()</code> on the bound value.
//   */
//  public Cursor cursor()
//  {
//    return ((BITable)get()).cursor();
//  }
//
//  /**
//   * Return <code>toList()</code> on the bound value.
//   */
//  public BIList toList()
//  {
//    return ((BITable)get()).toList();
//  }
//
//  /**
//   * Return <code>toTable()</code> on the bound value.
//   */
//  public BITable toTable()
//  {
//    return ((BITable)get()).toTable();
//  }
//
//  /**
//   * Return <code>filter(f)</code> on the bound value.
//   */
//  public BICollection filter(IFilter f)
//  {
//    return ((BITable)get()).filter(f);
//  }

////////////////////////////////////////////////////////////////
// BIList
////////////////////////////////////////////////////////////////

//  /**
//   * Return <code>size()</code> on the bound value.
//   */
//  public int size()
//  {
//    return ((BITable)get()).size();
//  }
//
//  /**
//   * Return <code>list()</code> on the bound value.
//   */
//  public BObject[] list()
//  {
//    return ((BITable)get()).list();
//  }
//
//  /**
//   * Return <code>list(in)</code> on the bound value.
//   */
//  public BObject[] list(BObject[] in)
//  {
//    return ((BITable)get()).list(in);
//  }
//  
//  /**
//   * Return <code>get(index)</code> on the bound value.
//   */
//  public BObject get(int index)
//  {
//    return ((BITable)get()).get(index);
//  }

////////////////////////////////////////////////////////////////
// BITable
////////////////////////////////////////////////////////////////

//  /**
//   * Return <code>getColumns()</code> on the bound value.
//   */
//  public ColumnList getColumns()
//  {
//    return ((BITable)get()).getColumns();
//  }
//
//  /**
//   * Return <code>get(row, col)</code> on the bound value.
//   */
//  public BObject get(int row, Column col)
//  {
//    return ((BITable)get()).get(row, col);
//  }
//
//  /**
//   * Return <code>getString(row, col)</code> on the bound value.
//   */
//  public String getString(int row, Column col)
//  {
//    return ((BITable)get()).getString(row, col);
//  }
//
//  /**
//   * Return <code>getFloat(row, col)</code> on the bound value.
//   */
//  public float getFloat(int row, Column col)
//  {
//    return ((BITable)get()).getFloat(row, col);
//  }
//
//  /**
//   * Return <code>getDouble(row, col)</code> on the bound value.
//   */
//  public double getDouble(int row, Column col)
//  {
//    return ((BITable)get()).getDouble(row, col);
//  }
//
//  /**
//   * Return <code>getInt(row, col)</code> on the bound value.
//   */
//  public int getInt(int row, Column col)
//  {
//    return ((BITable)get()).getInt(row, col);
//  }
//
//  /**
//   * Return <code>getLong(row, col)</code> on the bound value.
//   */
//  public long getLong(int row, Column col)
//  {
//    return ((BITable)get()).getLong(row, col);
//  }
//
//  /**
//   * Return <code>getBoolean(row, col)</code> on the bound value.
//   */
//  public boolean getBoolean(int row, Column col)
//  {
//    return ((BITable)get()).getBoolean(row, col);
//  }
//
//  /**
//   * Return <code>getFlags(row, col)</code> on the bound value.
//   */
//  public int getFlags(int row, Column col)
//  {
//    return ((BITable)get()).getFlags(row, col);
//  }
//
//  /**
//   * Return <code>getFacets(row, col)</code> on the bound value.
//   */
//  public BFacets getFacets(int row, Column col)
//  {
//    return ((BITable)get()).getFacets(row, col);
//  }
//
//  /**
//   * Return <code>getTableFacets()</code> on the bound value.
//   */
//  public BFacets getTableFacets()
//  {
//    return ((BITable)get()).getTableFacets();
//  }

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////
  
}
