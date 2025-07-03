/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.ui;

import java.util.ArrayList;

import javax.baja.collection.BITable;
import javax.baja.collection.Column;
import javax.baja.collection.TableCursor;
import javax.baja.driver.point.BIPointFolder;
import javax.baja.driver.point.BProxyExt;
import javax.baja.driver.ui.point.BPointManager;
import javax.baja.driver.ui.point.PointController;
import javax.baja.driver.ui.point.PointModel;
import javax.baja.gx.BImage;
import javax.baja.job.BJob;
import javax.baja.log.Log;
import javax.baja.naming.BOrd;
import javax.baja.naming.BatchResolve;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.Mark;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.util.Lexicon;
import javax.baja.workbench.mgr.BAbstractManager;
import javax.baja.workbench.mgr.BLearnTable;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrEditRow;
import javax.baja.workbench.mgr.MgrLearn;
import javax.baja.workbench.mgr.MgrModel;
import javax.baja.workbench.mgr.MgrState;
import javax.baja.workbench.mgr.MgrTypeInfo;

import com.tridium.nrio.components.BUIPointEntry;
import com.tridium.nrio.points.BNrio16PointFolder;
import com.tridium.nrio.points.BNrioPointDeviceExt;


/**
 * BNrioIOPointManager uses the BAbstractLearn framework to
 * provide a way for the user to create proxy points within
 * a Nrio Input Output Module
 *
 * @author    Andy Saunders
 * @creation  12 Jan 2006
 * @version   $Revision$ $Date$
 * @since     Niagara 3
 */
@NiagaraType
public class BNrioPointManager extends BPointManager
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.ui.BNrioPointManager(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:26 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioPointManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected void init()
  {
    super.init();
    try{
      deviceExt = (BNrioPointDeviceExt) ((BIPointFolder) getCurrentValue()).getDeviceExt();
      if (getCurrentValue() != deviceExt)
        registerForComponentEvents(deviceExt);
    }
    catch(IllegalStateException e){}
  }

  public Class<?> getPointEntryClass()
  {
    return null;
  }
////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  protected MgrModel makeModel() { return new Model(this); }
  protected MgrLearn makeLearn() { return new Learn(this); }
  protected MgrState makeState() { return new State(); }
  protected MgrController makeController() { return new Controller(this); }

////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////

  public class Controller extends PointController
  {
    Controller(BNrioPointManager manager)
    {
      super(manager);
    }

    protected IMgrCommand[] makeCommands()
    {
      return new IMgrCommand[]{
//          newCommand,
          newFolder,
          allDescendants,
          edit,
          learnMode,
          discover,
          add};
    }

    public IMgrCommand[] getDropDownCommands(IMgrCommand command)
    {
      return null;
    }

    /**
     * Handle a double click on the discovery table.  Default implementation
     * performs an add.  Note the column index is the visible column index, not
     * necessarily the MgrColumn index, see BLearnTable.columnIndexToMgrColumn().
     */
    public void cellDoubleClicked(BLearnTable table, BMouseEvent event, int row, int col)
    {
      if(add.isEnabled())
        add.invoke();
    }

    /**
     * This callback is made when commands should update
     * their enable/disable state based on current conditions.
     */
    public void updateCommands()
    {
      super.updateCommands();
      try
      {
        Object[] selectedRows = getLearnTable().getSelectedObjects();
        boolean enableAdd = selectedRows.length > 0;
        for(int i = 0; i < selectedRows.length; i++)
        {
          if(getLearn().getExisting(selectedRows[i]) != null)
          {
            enableAdd = false;
            break;
          }
        }
        add.setEnabled(enableAdd);
      }
      catch(Exception e)
      {
        //e.printStackTrace();
      }
    }

    public CommandArtifact doAdd(Context cx)
    throws Exception
    {
      existingPoints = null;
      return super.doAdd(cx);
    }

  }

  static final Lexicon lex = Lexicon.make(MgrController.class);

////////////////////////////////////////////////////////////////
// Model
////////////////////////////////////////////////////////////////
  public class Model extends PointModel
  {
    Model(BNrioPointManager mgr) { super(mgr); }

    /**
     * Call <code>getTable().reload()</code>.
     */
    public void load(BComponent target)
    {
      super.load(target);
      try{ getController().doDiscover(null); }
      catch( Exception e) { log.trace("init exception", e); /*System.out.println("init exception e: " + e);*/}

    }



    /**
     * This callback is made by MgrEdit when commit() requires
     * adding a new components into the database.  Subclasses may
     * override this method to control how the add is performed.
     * The default implementation uses Mark.moveTo().
     */
    public CommandArtifact addInstances(MgrEditRow[] rows, Context cx)
      throws Exception
    {
      // build a Mark out of the rows
      String[] names = new String[rows.length];
      BComponent[] values = new BComponent[rows.length];
      for(int i=0; i<rows.length; ++i)
      {
        names[i]  = rows[i].getName();
        values[i] = rows[i].getTarget();
      }
      Mark mark = new Mark(values, names);

      // add to the database - eventually we might be
      // adding to multiple containers, in which case
      // we need to break the problem up into multiple
      // Marks and separate operations
      BComponent container = rows[0].getEdit().getAddContainer();
      BComponent selComponent = getTable().getSelectedComponent();
      if(selComponent != null && selComponent instanceof BNrio16PointFolder)
      {
//        System.out.println("SelComponent = " + selComponent.getName());
        container = selComponent;
      }
      mark.moveTo(container, null);
      return null;
    }

  }

////////////////////////////////////////////////////////////////
// Learn
////////////////////////////////////////////////////////////////
  class Learn extends MgrLearn
  {
    Learn(BNrioPointManager mgr) { super(mgr); }

    public String makeTableTitle() { return "Hardware Points"; }

    protected MgrColumn[] makeColumns()
    {
      System.out.println("!!!!!! makeColumns() subclass should override !!!!!!!!!!");
      return null;
    }


    public boolean isMatchable(Object dis, BComponent db)
    {
      return false;
    }

    public void jobComplete(BJob job)
    {
      super.jobComplete(job);
      updateDiscoveryRows(job);
    }

    public void toRow(Object discovery, MgrEditRow row)
    throws Exception
    {
      System.out.println("!!!!!! toRow() subclass should override !!!!!!!!!!");
    }

    public MgrTypeInfo[] toTypes(Object discovery)
    throws Exception
    {
      System.out.println("!!!!!! toTypes() subclass should override !!!!!!!!!!");
      return null;
    }

    /**
     * If the specified discovery object is already mapped into
     * the station database as an existing component then return
     * it, otherwise return null.  Subclasses should override
     * <code>isExisting(Object, BComponent)</code>.
     */
    public BComponent getExisting(Object discovery)
    {
      try
      {
        if(getManager().getCurrentValue() instanceof BNrioPointDeviceExt)
        {
          if(getManager().getModel().getTable().getComponentModel().getRowCount() == 0)
            ((BUIPointEntry) discovery).setUsedByPoint("");
          return super.getExisting(discovery);
        }
        if(existingPoints == null || existingPoints.length == 0)
          existingPoints = resolveDeep(deviceExt, deviceExt.getProxyExtType());
        if(existingPoints == null || existingPoints.length == 0)
        {
          if(discovery instanceof BUIPointEntry)
            ((BUIPointEntry) discovery).setUsedByPoint("");
        }
        for(int i=0; i<existingPoints.length; ++i)
        {
          BComponent comp = ((BProxyExt)existingPoints[i]).getParentPoint();
          if (isExisting(discovery, comp)) return comp;
        }
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
      return null;
    }

    private BComponent[] resolveDeep(BComponent target, Type include)
    {
      // invoke BQL query to get components
      String bql = "bql:select slotPath from ";
      bql += include;
      BITable<?> result = (BITable<?>) BOrd.make(bql).get(target);

      // map slot paths into an BOrd list
      ArrayList<BOrd> alist = new ArrayList<>();
      try (TableCursor<?> cursor = result.cursor())
      {
        Column col = result.getColumns().get(0);
        while (cursor.next())
        {
          alist.add(BOrd.make(cursor.cell(col).toString()));
        }
      }
      BOrd[] ords = alist.toArray(new BOrd[alist.size()]);

      // batch resolve ord list into components
      return new BatchResolve(ords).resolve(target).getTargetComponents();
    }

  }

////////////////////////////////////////////////////////////////
// State
////////////////////////////////////////////////////////////////

  static class State extends MgrState
  {
    protected void saveForOrd(BAbstractManager m)
    {
      super.saveForOrd(m);
      BNrioPointManager manager = (BNrioPointManager)m;
      lastLearn = manager.lastLearn;
    }

    protected void restoreForOrd(BAbstractManager m)
    {
      super.restoreForOrd(m);
      BNrioPointManager manager = (BNrioPointManager)m;
      if (lastLearn != null)
      {
        manager.lastLearn = lastLearn;
        manager.getLearn().updateRoots(lastLearn);
      }
    }

    BComponent[] lastLearn = null;
  }

//////////////////////////////////////////////////////////////
// Implementation
//////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////

  /**
   * Called to update the inventory table field
   * with the given value.
   */
  protected void updateDiscoveryRows(BComponent event)
  {
    existingPoints = null;
    BComponent[] rows = (BComponent[])event.getChildren(getPointEntryClass());
    lastLearn = rows;
    for (int i = 0; i < rows.length; i++)
      rows[i].loadSlots();
    getLearn().updateRoots(rows);
  }

//////////////////////////////////////////////////////////////
// Constants
//////////////////////////////////////////////////////////////

  protected static Lexicon abstractLex = Lexicon.make(BPointManager.class);
  protected static Lexicon gpioLex = Lexicon.make("nrio");
  protected static BImage booleanIcon = BImage.make("module://icons/x16/statusBoolean.png");
  protected static BImage floatIcon = BImage.make("module://icons/x16/statusNumeric.png");
  protected static BImage mixIcon = BImage.make("module://icons/x16/statusMixed.png");

//////////////////////////////////////////////////////////////
// Attributes
//////////////////////////////////////////////////////////////

  static final Log log = Log.getLog("nrio");

  protected BNrioPointDeviceExt deviceExt = null;
  protected BComponent[] lastLearn = null;
  protected BComponent[] existingPoints;
}
