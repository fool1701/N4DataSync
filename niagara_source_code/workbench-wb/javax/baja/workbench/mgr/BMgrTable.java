/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import java.util.ArrayList;
import java.util.HashMap;

import javax.baja.collection.BITable;
import javax.baja.collection.Column;
import javax.baja.collection.TableCursor;
import javax.baja.gx.BBrush;
import javax.baja.gx.Graphics;
import javax.baja.naming.BOrd;
import javax.baja.naming.BatchResolve;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.SortUtil;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BMenu;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.table.DynamicTableModel;
import javax.baja.ui.table.TableCellRenderer;
import javax.baja.ui.transfer.TransferContext;
import javax.baja.workbench.component.table.BComponentTable;
import javax.baja.workbench.component.table.ComponentTableCellRenderer;
import javax.baja.workbench.component.table.ComponentTableController;
import javax.baja.workbench.component.table.ComponentTableModel;
import javax.baja.workbench.component.table.ComponentTableSelection;
import javax.baja.workbench.component.table.ComponentTableSubject;
import javax.baja.workbench.mgr.folder.FolderModel;

import com.tridium.workbench.util.WbViewEventWorker;

/**
 * MgrTable is used by BAbstractManager to display the
 * components configured in the station database.
 *
 * @author    Brian Frank
 * @creation  16 Dec 03
 * @version   $Revision: 39$ $Date: 7/30/10 11:10:52 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BMgrTable
  extends BComponentTable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.mgr.BMgrTable(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMgrTable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BMgrTable() {}

  public BMgrTable(MgrModel model)
  {
    this.manager = model.getManager();
    this.model   = model;
    this.componentModel = new Model();
    this.dynamicModel = new DynamicTableModel(componentModel);
    this.folderModel = (model instanceof FolderModel) ? (FolderModel)model : null;
    setModel(dynamicModel);
    setController(new Controller());
    setSelection(new Selection());
    setCellRenderer(new CellRenderer());
    columnsModified();
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the associated manager.
   */
  public BAbstractManager getManager()
  {
    return manager;
  }

  /**
   * Map a visible column index to a MgrColumn.
   */
  public MgrColumn columnIndexToMgrColumn(int column)
  {
    return model.cols[dynamicModel.toRootColumnIndex(column)];
  }

  /**
   * Route to MgrModel.
   */
  protected final boolean include(BComponent c)
  {
    for(int i=0; i<includes.length; ++i)
      if (c.getType().is(includes[i]))
        return true;
    return false;
  }

////////////////////////////////////////////////////////////////
// Load
////////////////////////////////////////////////////////////////

  /**
   * Reload the manager table.
   *
   * <p>
   *  <b>Note:</b> Subclasses overriding this method should ensure that the row components
   *  included in the component table model are filtered for the correct include types, including calling
   *  the accept() method and checking for the appropriate permissions on the target components
   * </p>
   */
  public void reload()
  {
    // set container on superclass even though we
    // are short circuiting how it does load
    BAbstractManager mgr = manager;
    setContainer(mgr.target);

    // get component rows
    includes = model.getIncludeTypes();
    BComponent[] rows = resolve();

    // now do the subscribe
    subscribe(rows);

    // filter out rows with accept() call now that
    // know we have a subscribe of correct depth
    Array<BComponent> accepted = new Array<>(BComponent.class, rows.length);
    
    for(int i=0; i<rows.length; ++i)
    {
      if (model.accept(rows[i]) && rows[i].getPermissions(manager.getCurrentContext()).hasOperatorRead())
        accepted.add(rows[i]);
    }
    
    rows = accepted.trim();

    // update my table model
    getSelection().deselectAll();
    componentModel.setRows(rows); // Does the same thing as removeAllRows -> addRows
  }

  private BComponent[] resolve()
  {
    // flat means we are just looking for children
    // of the manager's target, but folder manager's
    // may have to recurse down the tree
    if (folderModel == null || !folderModel.isAllDescendants())
      return resolveFlat();
    else
      return resolveDeep();
  }

  private BComponent[] resolveFlat()
  {
    BComponent target = manager.target;

    // find child components to include
    Array<BComponent> acc = new Array<>(BComponent.class);
    SlotCursor<Property> c = target.getProperties();
    while(c.nextComponent())
    {
      Property prop = c.property();
      BComponent comp = (BComponent)c.get();
      if (Flags.isHidden(target, prop)) continue;
      if (include(comp)) acc.add(comp); // check only include types at this point
    }

    return acc.trim();
  }

  private BComponent[] resolveDeep()
  {
    // invoke BQL query to get components
    BComponent target = manager.target;
    String bql = "bql:select slotPath from ";
    for(int i=0; i<includes.length; ++i)
    {
      if (i > 0) bql += ", ";
      bql += includes[i];
    }
    @SuppressWarnings("unchecked")
    BITable<BObject> result = (BITable<BObject>)BOrd.make(bql).get(target);

    // map slot paths into a BOrd list
    try (TableCursor<BObject> cursor = result.cursor())
    {
      ArrayList<BOrd> ords = new ArrayList<>();
      Column col = result.getColumns().get(0);
      while (cursor.next())
      {
        ords.add(BOrd.make(cursor.cell(col).toString()));
      }
      // batch resolve ord list into components
      return new BatchResolve(ords.toArray(new BOrd[ords.size()])).resolve(target).getTargetComponents();
    }
  }

  private void subscribe(BComponent[] rows)
  {
    BAbstractManager mgr = manager;
    BComponent target = mgr.target;
    Array<BComponent> list = new Array<BComponent>(BComponent.class);

    // always make sure we subscribe to the actual
    // target container first time thru (just in
    // case we have zero rows)
    if (subscription.get(target) == null && target != null)
    {
      list.add(target);
      subscription.put(target, target);
    }

    // We want to get a list of the parent components
    // without any duplicates - that is what we will use to
    // actually do the subscribe.  We keep our own cache of
    // of subscription roots since the basic Subscriber class
    // doesn't the know the difference b/w subscription roots
    // versus components that were subscribed via a depth > 0.
    // Keeping this map let's us save doing expensive subscribes
    // on the same object when reload() is called.
    for(int i=0; i<rows.length; ++i)
    {
      BComponent p = (BComponent)rows[i].getParent();
      if (subscription.get(p) != null || p == null) continue;
      list.add(p);
      subscription.put(p, p);
    }

    // short circuit everything is already subscribed
    if (list.size() == 0) return;

    // do a deep batch subscribe
    BComponent[] toSub = list.trim();
    int depth = model.getSubscribeDepth();
    mgr.registerForComponentEvents(toSub, depth);
  }

  /**
   * Must be called if the model's columns are modified in order to 
   * initialize the new columns and updated the MgrTable.
   * 
   * @since Niagara 3.6
   */ 
  public void columnsModified()
  {
    // load columns
    MgrColumnWrapper[] cols = new MgrColumnWrapper[model.cols.length];
    for(int i=0; i<cols.length; ++i)
      cols[i] = new MgrColumnWrapper(model.cols[i]);
    componentModel.setColumns(cols);

    // set initial column visibility
    for(int i=0; i<cols.length; ++i)
       dynamicModel.setShowColumn(i, !model.cols[i].isUnseen());    
    
    dynamicModel.columnsModified();
    sizeColumnsToFit();
  }  
  
////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////

  /**
   * Handle component event.
   */
  public void handleComponentEvent(BComponentEvent event)
  {
    boolean reload = false;
    BValue value = null;    
    
    switch(event.getId())
    {
      case BComponentEvent.PROPERTY_ADDED:
        value = event.getValue();
        if (value != null && value instanceof BComponent)
        {
          // Don't subscribe to descendants of an active mgrTemplate job yet,
          // as that can lead to StackOverflowExceptions.  Instead, skip
          // these for now, and they will be subscribed all at once when the
          // mgrTemplate job completes (see BAbstractManager.handleComponentEvent())
          if ((manager.getLearn() == null) ||
              (manager.getLearn().getJob() == null) ||
              (manager.getLearn().jobComplete) ||
              (manager.getLearn().getJob() != event.getSourceComponent()))
          {
            int depth = model.getSubscribeDepth() - 1; // -1 for comp under container
            WbViewEventWorker.getInstance().registerForComponentEventsLater(manager, 
                                                                            value.asComponent(),
                                                                            depth);
            reload = true;
          }          
        }        
        break;
      case BComponentEvent.PROPERTY_CHANGED:
      case BComponentEvent.PROPERTY_REMOVED:
        value = event.getValue();
        if (value != null && value instanceof BComponent) reload = true;
        break;
      case BComponentEvent.PROPERTIES_REORDERED:
        reload = true;
        break;
    }
    
    if (reload)
      WbViewEventWorker.getInstance().reloadLater(BMgrTable.this);
    
    repaint();
  }

////////////////////////////////////////////////////////////////
// Drag and Drop
////////////////////////////////////////////////////////////////

  public CommandArtifact drop(TransferContext cx)
    throws Exception
  {
    dropActive = false;
    return getManager().getController().drop(this, cx);
  }

  CommandArtifact superDrop(TransferContext cx)
    throws Exception
  {
    return super.drop(cx);
  }

////////////////////////////////////////////////////////////////
// MColumnWrapper
////////////////////////////////////////////////////////////////

  static class MgrColumnWrapper implements ComponentTableModel.Column
  {
    MgrColumnWrapper(MgrColumn mgrCol) { this.mgrCol = mgrCol; }

    public String getName() { return mgrCol.getDisplayName(); }

    public Object getValue(BComponent c)
    {
      try
      {
        return mgrCol.get(c);
      }
      catch(Exception e)
      {
        return "";
      }
    }

    MgrColumn mgrCol;
  }

////////////////////////////////////////////////////////////////
// Model
////////////////////////////////////////////////////////////////

  class Model extends ComponentTableModel
  {
    public synchronized void sortByColumn(int c, boolean ascending)
    {
      MgrColumn col = model.cols[c];

      BComponent[] rows = getRows();
      Object[] keys = new Object[rows.length];
      for(int i=0; i<keys.length; ++i)
      {
        try
        {
          keys[i] = col.toSortKey(rows[i]);
        }
        catch(Exception e)
        {
          // ignore and leave null
        }
      }

      SortUtil.sort(keys, rows, ascending);

      setRows(rows);
    }
  }

////////////////////////////////////////////////////////////////
// CellRenderer
////////////////////////////////////////////////////////////////

  // NOTE: If this class is modified, then you should update
  //  DeviceExtsColumn
  public class CellRenderer extends ComponentTableCellRenderer
  {
    public BBrush getBackground(Cell cell)
    {
      if (folderModel != null)
      {
        BComponent row = getComponentAt(cell.row);
        if (row.getType().is(folderModel.getFolderType()))
          return com.tridium.ui.theme.Theme.table().getGridBrush();
      }
      return super.getBackground(cell);
    }

    public double getPreferredCellWidth(Cell cell)
    {
      TableCellRenderer r = columnIndexToMgrColumn(cell.column).getCellRenderer();
      if (r != null)
        return r.getPreferredCellWidth(cell);
      else
        return super.getPreferredCellWidth(cell);
    }

    public void paintCell(Graphics g, Cell cell)
    {
      TableCellRenderer r = columnIndexToMgrColumn(cell.column).getCellRenderer();
      if (r != null)
        r.paintCell(g, cell);
      else
        super.paintCell(g, cell);
    }

    public String getCellText(Cell cell)
    {
      try
      {
        BComponent component = getComponentTable().getComponentModel().getComponentAt(cell.row);
        MgrColumn col = columnIndexToMgrColumn(cell.column);
        return col.toDisplayString(component, cell.value, manager.getCurrentContext());
      }
      catch(Exception e)
      {
        return "";
      }
    }
  }

////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////

  class Controller extends ComponentTableController
  {
    public void cellDoubleClicked(BMouseEvent event, int row, int col)
    {
      getManager().getController().cellDoubleClicked(BMgrTable.this, event, row, col);
    }

    public BMenu makePopup(ComponentTableSubject subject)
    {
      BMenu menu = super.makePopup(subject);
      return getManager().getController().makePopup(BMgrTable.this, subject, menu);
    }

    public BMenu makeOptionsMenu()
    {
      BMenu menu = super.makeOptionsMenu();
      return getManager().getController().makeOptionsMenu(BMgrTable.this, menu);
    }
    
  }

////////////////////////////////////////////////////////////////
// Selection
////////////////////////////////////////////////////////////////

  class Selection extends ComponentTableSelection
  {
    public void updateTable()
    {
      super.updateTable();
      manager.controller.updateCommands();
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BAbstractManager manager;
  MgrModel model;
  FolderModel folderModel;
  ComponentTableModel componentModel;
  DynamicTableModel dynamicModel;
  Type[] includes;
  HashMap<BComponent, BComponent> subscription = new HashMap<>();
}
