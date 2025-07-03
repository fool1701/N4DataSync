/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.workbench.bql.table;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.table.BTable;
import javax.baja.ui.table.DynamicTableModel;
import javax.baja.ui.table.TableModel;

/**
 * BqlTable is a table that provides special functionality for displaying
 * results from a BQL query.
 * 
 * @author    John Sublett
 * @creation  03 Dec 2004
 * @version   $Revision: 5$ $Date: 5/4/05 7:21:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The amount of time between queries for the table.  An interval
 of zero disables updates.
 */
@NiagaraProperty(
  name = "updateInterval",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)"
)
/*
 Submit the query and update the table.
 */
@NiagaraAction(
  name = "intervalElapsed"
)
public class BBqlTable
  extends BTable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.bql.table.BBqlTable(1798033312)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "updateInterval"

  /**
   * Slot for the {@code updateInterval} property.
   * The amount of time between queries for the table.  An interval
   * of zero disables updates.
   * @see #getUpdateInterval
   * @see #setUpdateInterval
   */
  public static final Property updateInterval = newProperty(0, BRelTime.make(0), null);

  /**
   * Get the {@code updateInterval} property.
   * The amount of time between queries for the table.  An interval
   * of zero disables updates.
   * @see #updateInterval
   */
  public BRelTime getUpdateInterval() { return (BRelTime)get(updateInterval); }

  /**
   * Set the {@code updateInterval} property.
   * The amount of time between queries for the table.  An interval
   * of zero disables updates.
   * @see #updateInterval
   */
  public void setUpdateInterval(BRelTime v) { set(updateInterval, v, null); }

  //endregion Property "updateInterval"

  //region Action "intervalElapsed"

  /**
   * Slot for the {@code intervalElapsed} action.
   * Submit the query and update the table.
   * @see #intervalElapsed()
   */
  public static final Action intervalElapsed = newAction(0, null);

  /**
   * Invoke the {@code intervalElapsed} action.
   * Submit the query and update the table.
   * @see #intervalElapsed
   */
  public void intervalElapsed() { invoke(intervalElapsed, null, null); }

  //endregion Action "intervalElapsed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBqlTable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  

  public BBqlTable()
  {
    setModel(new BqlTableModel());
    setCellRenderer(new BqlTableCellRenderer());
    setController(new BqlTableController());
    setSelection(new BqlTableSelection());
  }

  public BBqlTable(BqlTableColumn[] columns)
  {
    setModel(new BqlTableModel(columns));
    setCellRenderer(new BqlTableCellRenderer());
    setController(new BqlTableController());
    setSelection(new BqlTableSelection());
  }

  /**
   * Initialize update on startup.
   */
  public void started()
  {
    BRelTime interval = getUpdateInterval();
    if (interval.getMillis() == 0) return;
    
    ticket = Clock.schedule(this, interval, intervalElapsed, null);
  }

  /**
   * Stop updates when the widget stops.
   */
  public void stopped()
  {
    if (ticket != null)
    {
      ticket.cancel();
      ticket = null;
    }
  }

  /**
   * Set the model for this table.  The specified model
   * must be a BqlTableModel.
   * <p>
   * The BqlTableModel will not be returned from getModel().
   * Instead, the BqlTableModel is always wrapped by a
   * DynamicTableModel and the wrapper model is returned
   * from calls to getModel().
   * <p>
   * To get the BqlTableModel, user <code>getBqlModel()</code>.
   */
  public void setModel(TableModel model)
  {
    if (model instanceof BqlTableModel)
    {
      bqlModel = (BqlTableModel)model;
      super.setModel(makeDynamicModel(bqlModel));
    }
    else
      super.setModel(model);
  }

  /**
   * Get the BqlTableModel.  This is provided as a convenience since
   * getModel() will always return a DynamicTableModel.
   */
  public BqlTableModel getBqlModel()
  {
    return bqlModel;
  }

  /**
   * Make the wrapper model for the bql model.  This enforces the
   * column flags.
   */
  protected DynamicTableModel makeDynamicModel(BqlTableModel bqlModel)
  {
    DynamicTableModel dynModel = new DynamicTableModel(bqlModel);
    
    int count = bqlModel.getBqlColumnCount();
    for (int i = 0; i < count; i++)
    {
      BqlTableColumn col = bqlModel.getBqlColumn(i);
      boolean unseen = (col.getFlags() & BqlTableColumn.UNSEEN) != 0;
      boolean hidden = (col.getFlags() & BqlTableColumn.HIDDEN) != 0;
      
      if (unseen || hidden)
        dynModel.setShowColumn(i, false);
      if (hidden)
        dynModel.setColumnShowable(i, false);
    }

    return dynModel;
  }

  /**
   * Submit a new query.
   */
  public void doIntervalElapsed()
  {
    if (bqlModel == null) return;
    if (!bqlModel.isLoaded()) return;
    bqlModel.load();
  }

  /**
   * Receive notification that the query is complete.  If updateInterval
   * is not 0, this schedules another update.
   */
  void queryComplete()
  {
    if (ticket != null)
    {
      ticket.cancel();
      ticket = null;
    }
    
    BRelTime interval = getUpdateInterval();
    if (interval.getMillis() == 0) return;
    ticket = Clock.schedule(this, interval, intervalElapsed, null);
  }

  /**
   * Handle a change to one of the table properties.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if (p.equals(updateInterval))
    {
      queryComplete();
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BqlTableModel bqlModel;
  private Clock.Ticket ticket;
}
