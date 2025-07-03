/*                          
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.treetable;

import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.table.*;

import com.tridium.ui.theme.Theme;

/**
 * BTable displays a grid of rows and columns.  
 *
 * @author    Brian Frank 
 * @creation  7 Jan 04
 * @version   $Revision: 16$ $Date: 6/29/11 4:16:15 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BTreeTable
  extends BTable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.treetable.BTreeTable(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:35 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTreeTable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////  
 
  /**
   * Constructor with empty model.
   */
  public BTreeTable()
  {             
    super(new EmptyModel(), new TreeTableController(), new TreeTableSelection(), 
          new TreeTableHeaderRenderer(), new TreeTableCellRenderer());
    // By default, turn off alternating color rows for tree tables since it is not usually a good fit
    setColorRows(false);
  }

  /**
   * Constructor with model.
   */
  public BTreeTable(TreeTableModel model)
  {                             
    super(model, new TreeTableController(), new TreeTableSelection(), 
          new TreeTableHeaderRenderer(), new TreeTableCellRenderer());
    // By default, turn off alternating color rows for tree tables since it is not usually a good fit
    setColorRows(false);
  }

  public BTreeTable(TreeTableModel model, TreeTableController controller)  
  {
    super(model, controller, new TreeTableSelection(), 
          new TreeTableHeaderRenderer(), new TreeTableCellRenderer());
    // By default, turn off alternating color rows for tree tables since it is not usually a good fit
    setColorRows(false);
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Trap model sets.
   */
  public void setModel(TableModel m)
  {
    super.setModel(m);
    while(m instanceof WrapperTreeTableModel || m instanceof WrapperTableModel)
    {
      if (m instanceof WrapperTreeTableModel)
        m = ((WrapperTreeTableModel)m).getRootModel();
      if (m instanceof WrapperTableModel)
        m = ((WrapperTableModel)m).getRootModel();
    }
    this.model = (TreeTableModel)m;
  }
    
  /**
   * Get the TreeTableModel for this table.
   */
  public final TreeTableModel getTreeTableModel()
  { 
    return model;
  }                   
  
  /**
   * BTreeTable performs its own vertical cell painting.
   */
  protected boolean paintVerticalGrid()
  {
    return false;
  }
  
  protected void paintBackground(Graphics g) {
    Theme.treeTable().paintBackground(g, this);
  }
  
  public String getStyleSelector() { return "table tree-table"; }


////////////////////////////////////////////////////////////////
// EmptyModel
////////////////////////////////////////////////////////////////

  static class EmptyModel extends TreeTableModel
  {
    public int getRootCount() { return 0; }
    public TreeTableNode getRoot(int i) { return null; }
    public int getColumnCount() { return 0; }
    public String getColumnName(int col) { return ""; }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  TreeTableModel model;
}
