/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.envctrldriver.ui;

import java.util.ArrayList;

import javax.baja.control.*;
import javax.baja.driver.point.BPointDeviceExt;
import javax.baja.driver.ui.point.*;
import javax.baja.gx.BImage;
import javax.baja.job.BJob;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.CommandArtifact;
import javax.baja.workbench.mgr.*;

import com.examples.envctrldriver.BEnvCtrlDevice;
import com.examples.envctrldriver.points.*;

/**
 * The Point Manager handles adding, editing, and discovering points under
 * the {@link BPointDeviceExt} for a device.
 *
 * @author J. Spangler on Mar 7, 2013
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "envCtrlDriver:EnvCtrlPointDeviceExt", "envCtrlDriver:EnvCtrlPointFolder" }
  )
)
public class BEnvCtrlPointManager
  extends BPointManager
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.examples.envctrldriver.ui.BEnvCtrlPointManager(242846645)1.0$ @*/
/* Generated Thu Dec 01 20:57:46 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnvCtrlPointManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * This method is called by the doLoadValue method and by a inner class instance
   * of the Updater class when the pan table receives new entries.
   */
  void updateLearnData()
  {
    BPointDiscoveryJob learnJob = (BPointDiscoveryJob)getLearn().getJob();
    if (learnJob != null)
    {
      getLearn().updateRoots(learnJob.getLearnedPoints().getChildren(BPointLearnEntry.class));
    }
  }

  /**
   * This method retrieves the device that this point manager is currently
   * managing points for.
   *
   * @return
   */
  public BEnvCtrlDevice getDevice()
  {
    BComplex p = (BComplex)getCurrentValue();
    while (p != null)
    {
      if (p instanceof BEnvCtrlDevice)
      {
        return (BEnvCtrlDevice)p;
      }
      p = p.getParent();
    }
    throw new IllegalStateException();
  }

/////////////////////////////////////////////////////////////////
//  Support
/////////////////////////////////////////////////////////////////

  protected MgrModel makeModel()
  {
    return new Model(this);
  }

  protected MgrController makeController()
  {
    return new Controller(this);
  }

  protected MgrLearn makeLearn()
  {
    return new Learn(this);
  }

/////////////////////////////////////////////////////////////////
//  Point Model
/////////////////////////////////////////////////////////////////

  /**
   * The model displays our points under our managed device
   * extension in the running station.
   *
   * @author J. Spangler on Mar 7, 2013
   */
  private class Model
    extends PointModel
  {
    public Model(BPointManager manager)
    {
      super(manager);
    }

    /**
     * Create the columns that we want to display in our model
     * table. In our case, we want to use all the pre-defined
     * columns for the PointModel, but add one additional column.
     */
    protected MgrColumn[] makeColumns()
    {
      MgrColumn[] prevCols = super.makeColumns();
      MgrColumn[] cols = new MgrColumn[prevCols.length + 1];

      for (int i = 0; i < cols.length; i++)
      {
        if (i < 2)
        {
          cols[i] = prevCols[i];
        }
        else if (i == 2)
        {
          cols[i] = pointId;
        }
        else
        {
          cols[i] = prevCols[i - 1];
        }
      }

      return cols;
    }

    /**
     * This method determines what types of components we
     * can create in our point model. We can expand this if
     * we want to allow additional point types.
     */
    public MgrTypeInfo[] getNewTypes()
    {
      //limit our new point types to Numeric
      return new MgrTypeInfo[]{ MgrTypeInfo.make(new BNumericPoint()),
        MgrTypeInfo.make(new BNumericWritable()) };
    }

    /**
     * This method returns a list of what Types can be included
     * into our model.
     */
    public Type[] getIncludeTypes()
    {
      return new Type[]{ BNumericPoint.TYPE, BNumericWritable.TYPE };
    }
  }

/////////////////////////////////////////////////////////////////
// Learn
/////////////////////////////////////////////////////////////////

  /**
   * The MgrLearn handles our discovery table updating and selection.
   *
   * @author J. Spangler on Mar 7, 2013
   */
  class Learn
    extends MgrLearn
  {
    public Learn(BAbstractManager mgr)
    {
      super(mgr);
    }

    /**
     * Creates the columns for the learn table.
     */
    protected MgrColumn[] makeColumns()
    {
      return new MgrColumn[]{
        new MgrColumn.Prop(BPointLearnEntry.pointName),
        new MgrColumn.Prop(BPointLearnEntry.pointId)
      };
    }

    /**
     * Our toTypes method will need to be more specific than our
     * device manager. Here we will specify the different point types
     * that the discovered object may be converted to. We can
     * make this more specific based on the information we have about
     * our discovered object as returned from the remote network.
     */
    public MgrTypeInfo[] toTypes(Object discovery)
      throws Exception
    {
      //cast the discovery object to our point learn entry
      BPointLearnEntry entry = (BPointLearnEntry)discovery;

      //get the point type, which is a single character representing
      //the data symbol for the type of point value
      String pointType = entry.getPointType();
      char symbol = pointType.charAt(0);
      ArrayList<MgrTypeInfo> list = new ArrayList<>();

      //based on the point type symbol, we will allow converting
      //this discovered component to a numeric point, boolean point,
      //string point, or enumerated point.
      switch (symbol)
      {
        case 'i':
        case 'l':
        case 'f':
        case 'd':
        {
          list.add(MgrTypeInfo.make(BNumericWritable.TYPE));
          list.add(MgrTypeInfo.make(BNumericPoint.TYPE));
          break;
        }
        case 'b':
          list.add(MgrTypeInfo.make(BBooleanWritable.TYPE));
          list.add(MgrTypeInfo.make(BBooleanPoint.TYPE));
          break;
        case 's':
          list.add(MgrTypeInfo.make(BStringWritable.TYPE));
          list.add(MgrTypeInfo.make(BStringPoint.TYPE));
          break;
        default:
          list.add(MgrTypeInfo.make(BEnumWritable.TYPE));
          list.add(MgrTypeInfo.make(BEnumPoint.TYPE));
      }

      return list.toArray(new MgrTypeInfo[list.size()]);
    }

    /**
     * This method retrieves an icon for the discovered object. In the
     * case of the point manager, we want a point icon that corresponds
     * with the type of point we're creating. Our discovered object
     * includes information about the data type, so we can use that
     * to determine what type of icon we need to represent.
     *
     * @param discovery - The object created to represent the discovered item
     *                  of interest.
     */
    public BImage getIcon(Object discovery)
    {
      //cast the discovery object to our point learn entry
      BPointLearnEntry entry = (BPointLearnEntry)discovery;

      //get the point type, which is a single character representing
      //the data symbol for the type of point value
      String pointType = entry.getPointType();
      char symbol = pointType.charAt(0);

      //based on the point type symbol, we will allow converting
      //this discovered component to a numeric point, boolean point,
      //string point, or enumerated point.
      switch (symbol)
      {
        case 'i':
        case 'l':
        case 'f':
        case 'd':
          return numericIcon;
        case 'b':
          return booleanIcon;
        case 's':
          return stringIcon;
        default:
          return enumIcon;
      }
    }

    /**
     * Map the configuration of discovery object to the specified
     * MgrEditRow.  Configuration changes should be made to the row,
     * not the component (so that the changes aren't applied until
     * the user commits).
     * <p>
     * This method is used by the add and match commands.  For the
     * match command the row maps to the component already in the
     * database.  For the add command the component is not created
     * until commit time.
     */
    public void toRow(Object discovery, MgrEditRow row)
      throws Exception
    {
      //cast the discovery object to our point learn entry
      BPointLearnEntry entry = (BPointLearnEntry)discovery;

      //set the column data in our row
      row.setCell(pointId, BString.make(entry.getPointId()));
      row.setDefaultName(entry.getPointName());
    }

    /**
     * Allows the core to ask us if a discovered item is equivalent
     * to a given pre-existing item.
     */
    public boolean isExisting(Object dis, BComponent comp)
    {
      BPointLearnEntry learnEntry = (BPointLearnEntry)dis;
      String pointId = learnEntry.getPointId();
      BControlPoint point = (BControlPoint)comp;
      BEnvCtrlPointProxyExt proxyExt = (BEnvCtrlPointProxyExt)point.getProxyExt();
      return proxyExt.getPointId().equals(pointId);
    }

    /**
     * This callback is automatically invoked when the current job
     * set via <code>setJob()</code> completes.
     */
    public void jobComplete(BJob job)
    {
      super.jobComplete(job);
      if (job instanceof BPointDiscoveryJob)
      {
        updateLearnData();
      }
    }
  }

/////////////////////////////////////////////////////////////////
//  Controller
/////////////////////////////////////////////////////////////////

  class Controller
    extends PointController
  {
    public Controller(BPointManager mgr)
    {
      super(mgr);
    }

    /**
     * The discovery command enables the Discover button in our
     * controller. We will launch a Discover job for our points.
     */
    public CommandArtifact doDiscover(Context cx)
      throws Exception
    {
      super.doDiscover(cx);
      BEnvCtrlDevice device = getDevice();
      BOrd ordToJob = device.submitPointDiscoveryJob();
      getLearn().setJob(ordToJob);

      return null;
    }
  }

/////////////////////////////////////////////////////////////////
//  Attributes
/////////////////////////////////////////////////////////////////

  MgrColumn pointId = new MgrColumn.PropPath(new Property[]{ BControlPoint.proxyExt, BEnvCtrlPointProxyExt.pointId }, MgrColumn.EDITABLE);

  static BImage numericIcon = BImage.make("module://icons/x16/control/numericPoint.png");
  static BImage booleanIcon = BImage.make("module://icons/x16/control/booleanPoint.png");
  static BImage stringIcon = BImage.make("module://icons/x16/control/stringPoint.png");
  static BImage enumIcon = BImage.make("module://icons/x16/control/enumPoint.png");
}
