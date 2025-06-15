/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.envctrldriver.ui;

import javax.baja.driver.BDevice;
import javax.baja.driver.ui.device.*;
import javax.baja.gx.BImage;
import javax.baja.job.BJob;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.CommandArtifact;
import javax.baja.workbench.mgr.*;

import com.examples.envctrldriver.*;

import com.tridium.util.ArrayUtil;

/**
 * The Device Manager handles adding, editing, and discovering devices
 * underneath our network.
 *
 * @author J. Spangler on Mar 7, 2013
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "envCtrlDriver:EnvCtrlDeviceNetwork", "envCtrlDriver:EnvCtrlDeviceFolder" }
  )
)
public class BEnvCtrlDeviceManager
  extends BDeviceManager
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.examples.envctrldriver.ui.BEnvCtrlDeviceManager(3121667497)1.0$ @*/
/* Generated Thu Dec 01 20:57:46 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnvCtrlDeviceManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Gets the Access network that either owns this
   * view or that is the parent of the access device
   * folder that owns this view.
   */
  public BEnvCtrlDeviceNetwork getNetwork()
  {
    BObject owner = getCurrentValue();
    if (owner instanceof BEnvCtrlDeviceFolder)
    {
      return (BEnvCtrlDeviceNetwork)((BEnvCtrlDeviceFolder)owner).getNetwork();
    }
    else if (owner instanceof BEnvCtrlDeviceNetwork)
    {
      return (BEnvCtrlDeviceNetwork)owner;
    }
    else
    {
      return null;
    }
  }

  public void doLoadValue(BObject obj, Context cx)
  {
    super.doLoadValue(obj, cx);
    // Sets the discovery data from the learn table entries
    updateLearnData();
  }

  /**
   * This method is called by the doLoadValue method and by an
   * inner class instance of the Updater class when the pan
   * table receives new entries.
   */
  void updateLearnData()
  {
    BDeviceDiscoveryJob learnJob = (BDeviceDiscoveryJob)getLearn().getJob();
    if (learnJob != null)
    {
      getLearn().updateRoots(learnJob.getLearnedDevices().getChildren(BDeviceLearnEntry.class));
    }
  }

  /**
   * Create an instance of our Manager model to use.
   */
  protected MgrModel makeModel()
  {
    return new Model(this);
  }

  protected MgrLearn makeLearn()
  {
    return new Learn(this);
  }

  protected MgrController makeController()
  {
    return new Controller(this);
  }

////////////////////////////////////////////////////////////////
// Model
////////////////////////////////////////////////////////////////

  /**
   * This Simple model will make use of our columns to display
   * the Device data in the manager.
   *
   * @author J. Spangler
   * @creation Mar 1, 2013
   */
  class Model
    extends DeviceModel
  {
    Model(BDeviceManager manager)
    {
      super(manager);
    }

    protected MgrColumn[] makeColumns()
    {
      return cols;
    }
  }

/////////////////////////////////////////////////////////////////
// Learn
/////////////////////////////////////////////////////////////////

  /**
   * The Learn is used to display and represent devices that have
   * not yet been added into our station but are availabe on the
   * remote device network.
   *
   * @author J. Spangler on Mar 7, 2013
   */
  class Learn
    extends MgrLearn
  {

    public Learn(BAbstractManager manager)
    {
      super(manager);
    }

    /**
     * Creates the columns for the learn table.
     */
    protected MgrColumn[] makeColumns()
    {
      return new MgrColumn[]{
        new MgrColumn.Prop(BDeviceLearnEntry.deviceName),
        new MgrColumn.Prop(BDeviceLearnEntry.deviceId)
      };
    }

    /**
     * Create an icon to display in the learn table next to our discovered
     * object.
     */
    public BImage getIcon(Object dis)
    {
      return BImage.make("module://icons/x16/device.png");
    }

    /**
     * Declared what type of component that our discovered object may
     * be converted to in the Model.
     */
    public MgrTypeInfo[] toTypes(Object discovery)
    {
      return MgrTypeInfo.makeArray(getNetwork().getDeviceType());
    }

    /**
     * Convert the discovered object into a row in the Model table.
     */
    public void toRow(Object discovery, MgrEditRow row)
    {
      BDeviceLearnEntry learnEntry = (BDeviceLearnEntry)discovery;
      String deviceName = learnEntry.getDeviceName();
      int deviceId = learnEntry.getDeviceId();

      row.setCell(colName, BString.make(deviceName));
      row.setCell(colId, BInteger.make(deviceId));
      row.setDefaultName("Device" + deviceId);
    }

    /**
     * Allows the core to ask us if a discovered item is equivalent
     * to a given pre-existing item.
     */
    public boolean isExisting(Object dis, BComponent comp)
    {
      BDeviceLearnEntry learnEntry = (BDeviceLearnEntry)dis;
      BEnvCtrlDevice d = (BEnvCtrlDevice)comp;
      return d.getDeviceId() == learnEntry.getDeviceId();
    }

    /**
     * This callback is automatically invoked when the current job
     * set via <code>setJob()</code> completes.
     */
    public void jobComplete(BJob job)
    {
      super.jobComplete(job);
      if (job instanceof BDeviceDiscoveryJob)
      {
        updateLearnData();
      }
    }
  }

/////////////////////////////////////////////////////////////////
// Controller
/////////////////////////////////////////////////////////////////

  /**
   * The Controller class extends {@link DeviceController} to
   * provide a discover command for our device manager view.
   *
   * @author J. Spangler
   */
  class Controller
    extends DeviceController
  {
    /**
     * Constructor
     *
     * @param mgr - {@link BDeviceManager} for controller.
     */
    public Controller(BDeviceManager mgr)
    {
      super(mgr);
    }

    /**
     * Enable the discover command and launch our device
     * discovery job.
     */
    public CommandArtifact doDiscover(Context cx)
      throws Exception
    {
      super.doDiscover(cx);
      BEnvCtrlDeviceNetwork network = getNetwork();
      BOrd ordToJob = network.submitDeviceDiscoveryJob();
      getLearn().setJob(ordToJob);

      return null;
    }
  }
/////////////////////////////////////////////////////////////////
// Attributes
/////////////////////////////////////////////////////////////////

  MgrColumn colName = new MgrColumn.Name();
  MgrColumn colType = new MgrColumn.Type();
  MgrColumn colStatus = new MgrColumn.Prop(BDevice.status);
  MgrColumn colEnabled = new MgrColumn.Prop(BDevice.enabled, MgrColumn.EDITABLE | MgrColumn.UNSEEN);
  MgrColumn colHealth = new MgrColumn.Prop(BDevice.health, 0);

  /**
   * Special column with our device ID value
   */
  MgrColumn colId = new MgrColumn.Prop(BEnvCtrlDevice.deviceId, MgrColumn.EDITABLE);

  MgrColumn[] cols =
    {
      colName, colType, colId,
      colStatus, colEnabled, colHealth
    };
}
