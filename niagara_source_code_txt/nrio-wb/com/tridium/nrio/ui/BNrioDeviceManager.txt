/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.ui;

import javax.baja.control.BControlPoint;
import javax.baja.driver.BDevice;
import javax.baja.driver.ui.device.BDeviceManager;
import javax.baja.driver.ui.device.DeviceController;
import javax.baja.driver.ui.device.DeviceExtsColumn;
import javax.baja.driver.ui.device.DeviceModel;
import javax.baja.gx.BImage;
import javax.baja.job.BJob;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.registry.TypeInfo;
import javax.baja.space.Mark;
import javax.baja.sys.BBlob;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Subscriber;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BDialog;
import javax.baja.ui.BLabel;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BMenu;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.transfer.TransferContext;
import javax.baja.ui.transfer.TransferFormat;
import javax.baja.ui.treetable.TreeTableSubject;
import javax.baja.ui.util.UiLexicon;
import javax.baja.util.Lexicon;
import javax.baja.workbench.BWbEditor;
import javax.baja.workbench.mgr.BAbstractManager;
import javax.baja.workbench.mgr.BLearnTable;
import javax.baja.workbench.mgr.BMgrTable;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrEdit;
import javax.baja.workbench.mgr.MgrEditRow;
import javax.baja.workbench.mgr.MgrLearn;
import javax.baja.workbench.mgr.MgrModel;
import javax.baja.workbench.mgr.MgrState;
import javax.baja.workbench.mgr.MgrTypeInfo;

import com.tridium.nrio.BNrio16Module;
import com.tridium.nrio.BNrio34Module;
import com.tridium.nrio.BNrio34SecModule;
import com.tridium.nrio.BNrioDevice;
import com.tridium.nrio.BNrioDeviceFolder;
import com.tridium.nrio.BNrioInputOutputModule;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.components.BNrioLearnDeviceEntry;
import com.tridium.nrio.enums.BNrioDeviceTypeEnum;
import com.tridium.nrio.job.BNrioLearnDevicesJob;
import com.tridium.nrio.points.BNrioProxyExt;
import com.tridium.util.ArrayUtil;



/**
 * BNrioDeviceManager defines the default implementation of BDeviceManager
 * used to manage the BNrioDevices under a BNrioNetwork.
 *
 * @author    Andy Saunders
 * @creation  01 Mar 04
 * @version   $Revision$ $Date: 8/29/2005 10:21:13 AM$
 * @since     Niagara 3 Nrio 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "nrio:NrioNetwork",
    requiredPermissions = "r"
  )
)
public class BNrioDeviceManager
  extends BDeviceManager
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.ui.BNrioDeviceManager(980596003)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioDeviceManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BNrioDeviceManager()
  {
    discoveryListSubscriber=new DiscoveryListSubscriber();
  }


  /**
   * Gets the Nrio network that either owns this
   * view or that is the parent of the access device
   * folder that owns this view.
   */
  public BNrioNetwork getNrioNetwork()
  {
    BObject owner = getCurrentValue();
    if (owner instanceof BNrioDeviceFolder)
      return (BNrioNetwork)((BNrioDeviceFolder)owner).getNetwork();
    else if (owner instanceof BNrioNetwork)
      return (BNrioNetwork)owner;
    else
      return null;
  }

  public void doLoadValue(BObject obj, Context cx)
  {
    super.doLoadValue(obj,cx);
    // Sets the discovery data from the pan table entries
    updateLearnData();
  }

  public void paint(javax.baja.gx.Graphics g)
  {
    synchronized(BNrioDeviceManager.this)
    {
      super.paint(g);
    }
  }

  /**
   * This method is called by the doLoadValue method and by a inner class instance
   * of the Updater class when the pan table receives new entries.
   *
   */
  void updateLearnData()
  {
    try
    {
      BNrioLearnDevicesJob learnJob = (BNrioLearnDevicesJob)getLearn().getJob();
      if(learnJob != null)
        getLearn().updateRoots(learnJob.getLearnedDevices().getChildren(BNrioLearnDeviceEntry.class) );
    }
    catch(Exception e)
    {
    }
  }

  public BNrioDevice[] getSelectedDevices()
  {
    if(getModel()==null || getModel().getTable()==null)
    {
      return new BNrioDevice[0];
    }
    else
    {
      BComponent[] selection = getModel().getTable().getSelectedComponents();
      BNrioDevice[] devices = new BNrioDevice[0];
      for (int i=0; i<selection.length; i++)
        if (selection[i].getType().is(BNrioDevice.TYPE))
          devices= ArrayUtil.addOne(devices,(BNrioDevice)selection[i]);
      return devices;
    }
  }

  public void deactivated()
  {
    super.deactivated();
    Object[] learnEntries = this.getLearn().getRoots();
    for(int i = 0; i < learnEntries.length; i++)
    {

      BNrioLearnDeviceEntry entry = (BNrioLearnDeviceEntry) learnEntries[i];
      entry.stopWink();
    }
  }

  public boolean isThisDeviceValid(BNrioLearnDeviceEntry disc)
  {
  	int ordinal = disc.getDeviceType().getOrdinal();
    return ( ordinal == BNrioDeviceTypeEnum.IO_16 ||
             ordinal == BNrioDeviceTypeEnum.IO_34 ||
             ordinal == BNrioDeviceTypeEnum.IO_34SEC ||
             ordinal == BNrioDeviceTypeEnum.IO_16V1 ||
             ordinal == BNrioDeviceTypeEnum.REMOTE_INPUT_OUTPUT ||
              ordinal == BNrioDeviceTypeEnum.REMOTE_READER           );
//    return (ordinal == BNrioDeviceTypeEnum.REMOTE_INPUT_OUTPUT ||
  }



////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  protected MgrModel makeModel() { return new NrioDeviceModel(this); }
  protected MgrLearn makeLearn() { return new NrioDeviceLearn(); }
  protected MgrController makeController() { return new NrioDeviceController(this); }
  protected MgrState makeState(){ return new NrioDeviceState(); }


////////////////////////////////////////////////////////////////
// NrioDeviceModel
////////////////////////////////////////////////////////////////

  public class NrioDeviceModel extends DeviceModel
  {
    NrioDeviceModel(BNrioDeviceManager mgr) { super(mgr); }

    protected MgrColumn[] makeColumns()
    {
      return new MgrColumn[]
      {
        colName   ,
        colDevType,
        colExts   ,
        colStatus ,
        colEnable ,
        colHealth ,
        colAddress,
        colSecAddr,
        colUid    ,
        colInsVer ,
        colSecVer,
        colAvaVer ,
      };
    }

    public BComponent newInstance(MgrTypeInfo type, int address)
      throws Exception
    {
      BNrioDevice device = (BNrioDevice)(newInstance(type));
      device.setAddress(address);
      return device;
    }

    public void load(BComponent target)
    {
      super.load(target);
      target.lease(2);
    }

  }

  /* special MgrColumn to display BBlob byteArray as a hexString */
  class BlobColumn
    extends MgrColumn.Prop
  {
    BlobColumn(Property prop, int flags)
    {
      super(prop, flags);
    }

    public String toDisplayString(Object row, Object value, Context cx)
    {
      if(value instanceof BBlob)
      {
        byte[] temp = ((BBlob)value).copyBytes();
        byte[] bytes = new byte[temp.length];
        for(int i = 0; i < temp.length; i++)
          bytes[temp.length-1-i] = temp[i];
        return ByteArrayUtil.toHexString(bytes);
      }
      else
        return super.toDisplayString(row, value, cx);
    }
  }

/////////////////////////////////////////////////////////////
// Learn
//////////////////////////////////////////////////////////////
  /**
   * Helps the Niagara AX core perform the learn.
   */
  class NrioDeviceLearn
    extends MgrLearn
  {
    NrioDeviceLearn()
    {
      super(BNrioDeviceManager.this);
    }

    /**
     * Gets the information about the columns to be displayed
     * when in discover mode. The data about these columns is
     * relative to the discovery object type, which for this manager
     * is simply BInteger.
     */
    protected MgrColumn[] makeColumns()
    {
      return new MgrColumn[]{
        new MgrColumn.Prop(BNrioLearnDeviceEntry.address),
        new MgrColumn.Prop(BNrioLearnDeviceEntry.secAddr),
        new MgrColumn.Prop(BNrioLearnDeviceEntry.deviceType),
        new BlobColumn(BNrioLearnDeviceEntry.uid, 0),
        new MgrColumn.Prop(BNrioLearnDeviceEntry.version),
        new MgrColumn.Prop(BNrioLearnDeviceEntry.usedBy),
      };
    }

    public BImage getIcon(Object dis)
    {
    	BNrioLearnDeviceEntry learnEntry = (BNrioLearnDeviceEntry)dis;
    	if(isThisDeviceValid(learnEntry))
    		return stationIcon;
    	return stationIcon.getDisabledImage();
    }

    public MgrTypeInfo[] toTypes(Object discovery)
    {
      BNrioLearnDeviceEntry learnEntry = (BNrioLearnDeviceEntry)discovery;
      if(learnEntry.getDeviceType().equals(BNrioDeviceTypeEnum.remoteInputOutput))
         return MgrTypeInfo.makeArray(BNrioInputOutputModule.TYPE);
      else if(learnEntry.getDeviceType().equals(BNrioDeviceTypeEnum.io16))
        return MgrTypeInfo.makeArray(BNrio16Module.TYPE);
      else if(learnEntry.getDeviceType().equals(BNrioDeviceTypeEnum.io16V1))
        return MgrTypeInfo.makeArray(BNrio16Module.TYPE);
      else if(learnEntry.getDeviceType().equals(BNrioDeviceTypeEnum.io34))
        return MgrTypeInfo.makeArray(BNrio34Module.TYPE);
      else if(learnEntry.getDeviceType().equals(BNrioDeviceTypeEnum.io34sec))
        return MgrTypeInfo.makeArray(BNrio34Module.TYPE);
      else if(learnEntry.getDeviceType().equals(BNrioDeviceTypeEnum.remoteReader))
        return MgrTypeInfo.makeArray(BNrioDevice.TYPE);

      return new MgrTypeInfo[0];
    }
    public void toRow(Object discovery, MgrEditRow row)
    {
      BNrioLearnDeviceEntry learnEntry = (BNrioLearnDeviceEntry)discovery;
      row.setDefaultName(learnEntry.getDefaultAddAddress());
      if(learnEntry.getSecAddrInt()  > 0)
      {
        row.setCell(colSecAddr, BInteger.make(learnEntry.getSecAddrInt()));
      }
      row.setCell(colAddress, BInteger.make(learnEntry.getAddress()) );
      row.setCell(colDevType, learnEntry.getDeviceType());
      //row.setCell(colInsVer , learnEntry.getVersion());
      row.setCell(colUid,     learnEntry.getUid());
    }
    /**
     * Allows the core to ask us if a discovered item is equivalent
     * to a given pre-existing item.
     */
    public boolean isExisting(Object dis, BComponent comp)
    {
      try
      {
        BNrioLearnDeviceEntry learnEntry = (BNrioLearnDeviceEntry)dis;
        if(learnEntry.isWinkActive())
          return true;
        BNrioDevice d = (BNrioDevice)comp;
        return (d.getUid()).equals(learnEntry.getUid());
      }
      catch(Exception e)
      {
      }
      return false;
    }

    public boolean isMatchable(Object dis, BComponent db)
    {
      try
      {
        BNrioNetwork network = ((BNrioDeviceManager)getManager()).getNrioNetwork();
        BNrioLearnDeviceEntry entry = (BNrioLearnDeviceEntry) dis;
        BNrioDevice d = network.getDevice(entry.getAddress());
        if(d != null)
          return false;
        return entry.isMatchable(db);
      }
      catch(Throwable e)
      {
        e.printStackTrace();
        return false;
      }
    }


    /**
     * This callback is automatically invoked when the
     * current job set via <code>setJob()</code> completes.
     */
    public void jobComplete(BJob job)
    {
      super.jobComplete(job);
      if (job instanceof BNrioLearnDevicesJob )
      {
        updateLearnData();
      }
    }
  }
////////////////////////////////////////////////////////////////
// NrioDeviceController
////////////////////////////////////////////////////////////////

  public class NrioDeviceController extends DeviceController

  {
    /**
     * Construct a NrioDeviceController instance.
     */
    public NrioDeviceController(BDeviceManager manager)
    {
      super(manager);
      this.installFirmware = new InstallFirmware(manager);
      installFirmware.setFlags(BARS | POPUP);
      installFirmware.setEnabled(false);

      this.addOfflineHardware = new AddOfflineHardware(manager);
      addOfflineHardware.setFlags(ACTION_BAR);
      addOfflineHardware.setEnabled(false);
    }


    /**
     * This is a hook for building the popup menu used by the BLearnTable.
     * New commands should be appended to the default menu.  The default
     * implementation adds all commands with the LEARN_POPUP flag set.
     */
    public BMenu makePopup(BLearnTable table, TreeTableSubject subject, BMenu menu)
    {
      menu = super.makePopup(table, subject, menu);
      if (subject.size() == 1)
      {
        BNrioLearnDeviceEntry subjectEntry = (BNrioLearnDeviceEntry)subject.get(0);

        if(subjectEntry.getAddress() > 0 )
        {
          boolean isInStation =  ( getManager().getLearn().getExisting(subject.get(0)) ) != null;
          boolean isWinkActive = subjectEntry.isWinkActive();
          menu.add("wink", new Wink(table, !isInStation & !isWinkActive, "wink"));
        }
      }
      return menu;
    }



    protected IMgrCommand[] makeCommands()
    {

      allDescendants.setSelected(true);
      return new IMgrCommand[]
      {
        edit,
        learnMode,
        discover,
        cancel,
        add,
        quickAdd,
        match,
        quickMatch,
        templateMode,
        installFirmware,
        addOfflineHardware
      };
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
      synchronized(BNrioDeviceManager.this) //must be synchronized to keep add button from flashing enabled!
      {

        super.updateCommands();
        BNrioNetwork network = getNrioNetwork();
        int maxDevicesCount = network.getMaxDevices();
        BComponent[] selectedRows = getSelectedComponents();
        boolean enableInstallFirmware = false;
        for(int i = 0; i < selectedRows.length; i++)
        {
          BNrioDevice device = (BNrioDevice)selectedRows[i];
          if(device.isFault() || device.isDown() || device.isDown())
            break;
          if( !device.isFirmwareUptodate() )
          {
            enableInstallFirmware = true;
            break;
          }
        }

        installFirmware.setEnabled(enableInstallFirmware);
        boolean enableAdd = false;

        if(getLearnTable() != null)
        {
          Object[] disSelRows = getLearnTable().getSelectedObjects();
          enableAdd = disSelRows.length > 0;

          for(int i = 0; i < disSelRows.length; i++)
          {
            if(getLearn().getExisting(disSelRows[i]) != null)
            {
              enableAdd = false;
              break;
            }
          }
        }



        int moduleCount = 0;
        if(getTable() != null && getTable().getComponentModel() != null)
        {
          BComponent[] rows = getTable().getComponentModel().getRows();
          for(int i = 0; i < rows.length; i++)
          {
            BComplex entry = rows[i];
            moduleCount++;
          }
        }

        if (learnMode.isSelected())
        {
          //System.out.println("learnMode.isSelected()");
          Object[] disSelRows = getLearnTable().getSelectedObjects();
          for(int i = 0; i < disSelRows.length; i++)
          {
            BNrioLearnDeviceEntry entry = (BNrioLearnDeviceEntry)disSelRows[i];
            if(isThisDeviceValid(entry))
            {
                moduleCount++;
            }
            else
              enableAdd=false;
          }

//          System.out.println("moduleCount = " + moduleCount);
//          System.out.println("maxDevicesCount  = " + maxDevicesCount);
          if(moduleCount > maxDevicesCount)
          {
            enableAdd = false;
          }
        }

        add.setEnabled(enableAdd);
        quickAdd.setEnabled(enableAdd);
        addOfflineHardware.setEnabled(moduleCount <= maxDevicesCount);
      }
    }

    public boolean canAdd(BObject[] values)
    {

      int moduleCount = 0;
      BComponent[] rows = getTable().getComponentModel().getRows();
      for(int i = 0; i < rows.length; i++)
      {
        BComplex entry = rows[i];
        moduleCount++;
      }

      boolean canAdd = true;
      for(int i = 0; i < values.length; i++)
      {
        BComplex entry = (BComplex)values[i];
        moduleCount++;
      }

      int maxDevicesCount = getNrioNetwork().getMaxDevices();
      if(moduleCount > maxDevicesCount)
        return false;
      else
        return canAdd;
    }
    /**
     * Process a drop on the MgrTable.
     */
    public CommandArtifact drop(BMgrTable table, TransferContext cx)
      throws Exception
    {

      try
      {
        // we just use a special string to indicate when
        // this is a drop from our own learn table
        BAbstractManager mgr = getManager();

        String data = (String)cx.getEnvelope().getData(TransferFormat.string);
        //must be from top
        String learnData = "dragFromLearn:" + mgr.hashCode();
        if(data.trim().equals(learnData.trim()))
        {
          if(!add.isEnabled())
            return null;
          else
            return add.doInvoke();
        }
        else //drag from bottom table to bottom table
        {
          Mark mark = (Mark)cx.getEnvelope().getData(TransferFormat.mark);
          BObject[] values = mark.getValues();
          if(canAdd(values))
            return add.doInvoke();
          else
            return null;
        }
      }
      catch(Exception e)
      {
      }
      // use standard ComponentTable behavior
      return super.drop(table, cx);
    }

////////////////////////////////////////////////////////////////
//  InstallFirmware
////////////////////////////////////////////////////////////// //

     class InstallFirmware extends MgrCommand
     {
       InstallFirmware(BWidget owner) { super(owner, lexicon, "upgradeFirmware"); }
       public CommandArtifact doInvoke() throws Exception { return doInstallFirmware(); }
       public Command doMerge(Command c) { return this; }
     }

     /**
      * This is the callback when the new folder command is invoked.
      */
     public CommandArtifact doInstallFirmware()
       throws Exception
     {
       BNrioNetwork accessNet = getNrioNetwork();
       BOrd ordToUpgradeJob = accessNet.upgradeFirmware();
       getLearn().setJob(ordToUpgradeJob);
       return null;
     }

////////////////////////////////////////////////////////////////
//  AddOfflineHardware
////////////////////////////////////////////////////////////// //

     class AddOfflineHardware extends MgrCommand
     {
       AddOfflineHardware(BWidget owner) { super(owner, lexicon, "addOfflineHardware"); }
       public CommandArtifact doInvoke() throws Exception { return doAddOfflineHardware(); }
       public Command doMerge(Command c) { return this; }
     }

     /**
      * This is the callback when the new folder command is invoked.
      */
     public CommandArtifact doAddOfflineHardware()
       throws Exception
     {
       MgrTypeInfo[] types = MgrTypeInfo.makeArray( new TypeInfo[] { BNrio16Module.TYPE.getTypeInfo(), BNrio34Module.TYPE.getTypeInfo() } );

        // build type fields
        BListDropDown typeField = new BListDropDown();
        for(int i=0; i<types.length; ++i)
          typeField.getList().addItem(types[i].getIcon(), types[i]);

         typeField.setSelectedIndex(0);

        // build type fields

        BTextField nameField = new BTextField("");

        // build input pane
        BGridPane grid = new BGridPane(2);

        grid.add(null, new BLabel(lex.getText("add.name")));
        grid.add(null, nameField);

        grid.add(null, new BLabel(lex.getText("add.type")));
        grid.add(null, typeField);

        // prompt
        String title = addOfflineHardware.getLabel();
        int r = BDialog.open(getManager(), title, grid, BDialog.OK_CANCEL);
        if (r == BDialog.CANCEL) return null;


        MgrTypeInfo type = (MgrTypeInfo)typeField.getSelectedItem();

        String name = nameField.getText();
        BNrioDevice device = (BNrioDevice) getManager().getModel().newInstance(type);

        getNrioNetwork().add(SlotPath.escape(name), device);
        return null;
     }

////////////////////////////////////////////////////////////////
//   wink device in discovery frame
//////////////////////////////////////////////////////////////  //

      class Wink extends MgrCommand
      {
        Wink(BWidget owner, boolean enable, String label)
        {
          super(owner, lexicon, label);
          setEnabled(enable);
        }
        public CommandArtifact doInvoke() throws Exception { return doWink(); }
        public Command doMerge(Command c) { return this; }
      }

      /**
       * This is the callback when the wink command is invoked.
       */
      public CommandArtifact doWink()
        throws Exception
      {
        BNrioLearnDeviceEntry discoveryDevice = (BNrioLearnDeviceEntry)this.getLearnTable().getSelectedObject();
        BNrioNetwork accessNet = getNrioNetwork();
        discoveryDevice.doWinkDevice(accessNet, BBoolean.make(true));
        getManager().getLearn().updateTable();
        return null;
      }

    /**
     * Prompt the user with the list of new types returned by
     * BAbstractManager.getNewTypes() and return a MgrEdit
     * with the default instances.
     * Overridden to provide a starting address for
     * automatically making the device addresses increase
     * consecutively.
     */
    public MgrEdit promptForNew(Context cx)
      throws Exception
    {
      MgrTypeInfo[] types = getManager().getModel().getNewTypes();
      if (types == null)
      {
        BDialog.error(getManager(), "Must override BAbstractManager.getNewTypes()");
        return null;
      }

      // build input fields
      BListDropDown typeField = new BListDropDown();
      for(int i=0; i<types.length; ++i)
        typeField.getList().addItem(types[i]);
      typeField.setSelectedIndex(0);
      BTextField countField = new BTextField("1", 6);
      BTextField startAddressField = new BTextField("1", 6);

    // build input pane
      BGridPane grid = new BGridPane(2);
      grid.add(null, new BLabel(superLexicon.getText("add.type")));
      grid.add(null, typeField);
      grid.add(null, new BLabel(superLexicon.getText("add.count")));
      grid.add(null, countField);
      grid.add(null, new BLabel(lexicon.getText("devManager.add.startAddress")));
      grid.add(null, startAddressField);

      // prompt
      String title = newCommand.getLabel();
      MgrTypeInfo type = null;
      int count = 0;
      int startAddress = 1;
      boolean done = false;
      while(!done)
      {
        int r = BDialog.open(getManager(), title, grid, BDialog.OK_CANCEL);
        if (r == BDialog.CANCEL) return null;

        // extract input
        type = (MgrTypeInfo)typeField.getSelectedItem();
        count = Integer.parseInt(countField.getText());
        startAddress = Integer.parseInt(startAddressField.getText());

        if (count > 247)
          BDialog.error(getManager(), lexicon.getText("devManager.error.invalidCount"));
        else
          done = true;
      }

      // create edit list
      MgrEdit edit = makeEdit(title);
      for(int i=0; i<count; ++i)
      {
        if ((startAddress < 1) || (startAddress > 247)) startAddress = 1;
        BComponent comp = ((NrioDeviceModel)(getManager().getModel())).newInstance(type, startAddress);
        MgrEditRow row = new MgrEditRow(comp, null, types);
        edit.addRow(row);
        row.setDefaultName(SlotPath.escape(type.getDisplayName() + startAddress));
        startAddress++;
      }
      return edit;
    }

    /**
     * This callback is made when the user asks the device
     * manager to discover the hardware on the fieldbus.
     */
    public CommandArtifact doDiscover(Context cx)
      throws Exception
    {
      BNrioNetwork network = getNrioNetwork();
      if(network.isDisabled() || network.isFault())
      {
        throw(new RuntimeException("Network is disabled or configured incorrectly"));
      }

      try
      {
        Object[] learnEntries = getLearn().getRoots();
        for(int i = 0; i < learnEntries.length; i++)
        {
          BNrioLearnDeviceEntry entry = (BNrioLearnDeviceEntry) learnEntries[i];
          entry.stopWink();
        }
      }
      catch(Exception e){}
      super.doDiscover(cx);
      BOrd ordToDiscoveryJob = network.submitDeviceDiscoveryJob();
      getLearn().setJob(ordToDiscoveryJob);
      return null;
    }

    protected CommandArtifact doAdd(Object[] discovery, Context cx)
    throws Exception
    {
      for(int i = 0; i < discovery.length; i++)
        ((BNrioLearnDeviceEntry)discovery[i]).stopWink();
      return super.doAdd(discovery, cx);

    }

    public CommandArtifact doMatch(Object discovery, BComponent database, Context cx)
    {
      BNrioLearnDeviceEntry discoverComp = (BNrioLearnDeviceEntry) discovery;
      BNrioDevice stationComp = (BNrioDevice)database;
      int addr = discoverComp.getAddress();
      matchDevice(discoverComp, stationComp, addr, true);
      if(stationComp instanceof BNrio34Module)
      {
        BNrio34SecModule io34Sec = ((BNrio34Module)stationComp).getIo34Sec();
        matchDevice(discoverComp, io34Sec, discoverComp.getSecAddrInt(), false);
      }
      try{Thread.sleep(500l);} catch(Exception e){}
//      try {doDiscover(cx); }
//      catch(Exception e) {}
      return null;
    }

    private void matchDevice(BNrioLearnDeviceEntry discoverComp, BNrioDevice stationComp, int addr, boolean setType)
    {
      stationComp.setUid(discoverComp.getUid());
      if(setType)
        stationComp.setDeviceType(discoverComp.getDeviceType());
      if(stationComp.getAddress() == 0)
      {
        stationComp.setAddress(addr);
      }
    }

    public final MgrCommand installFirmware;
    public final MgrCommand addOfflineHardware;
  }

////////////////////////////////////////////////////////////////
// DiscoveryListSubscriber
////////////////////////////////////////////////////////////////
  class DiscoveryListSubscriber extends Subscriber
  { // NOTE: The NrioDeviceState subscribes and unsubscribes
    public void event(BComponentEvent event)
    {
      updateLearnData();
    }
  }

  /**
   * The MgrColumn.PropPath implementation is used to display
   * and manage a property of a row component.
   */
  public static class PropPath extends MgrColumn.PropPath
  {
    public PropPath(Property props[])
    {
      super(props, 0);
    }
    public PropPath(String name, Property props[], int flags)
    {
      super(name, props, flags);
      this.props = props;
    }
    /*

    public PropPath(Property props[], int flags)
    {
      this(props[props.length-1].getDefaultDisplayName(null), props, flags);
    }


    protected BComplex getRowBase(Object row)
    {
      return (BComplex)row;
    }

    public Object get(Object row)
    {
      BComplex val = getRowBase(row);
      for(int i=0; i<props.length-1; ++i)
        val = (BComplex)val.get(props[i]);
      Property prop = props[props.length-1];
      return val.get(prop);
    }
    protected BComplex getTargetBase(MgrEditRow row)
    {
      return row.getTarget();
    }
*/
    public void save(MgrEditRow row, BValue value, Context cx)
    {
      BComplex target = getTargetBase(row);

      int len = props.length;
      for(int i=0; i<len-1; ++i)
      {
        try{target = (BComplex)target.get(props[i]);}
        catch(Exception e)
        {
          return;
        }
      }

      Property prop = props[len-1];
      BValue old = target.get(prop);
      if (!old.equivalent(value)) target.set(prop, value.newCopy(), cx);
    }


    public String toDisplayString(Object row, Object value, Context cx)
    {
      BComplex val = getRowBase(row);
      for(int i=0; i<props.length-1; ++i)
      {
        try{val = (BComplex)val.get(props[i]);}
        catch(Exception e)
        {
          return "";
        }
      }
      Property prop = props[props.length-1];
      BFacets facets = val.getSlotFacets(prop);

      if (!facets.isNull()) cx = new BasicContext(cx, facets);

      return ((BObject)value).toString(cx);
    }


    public BValue load(MgrEditRow row)
    {
      BValue val = getTargetBase(row);

      for(int i=0; i<props.length; ++i)
      {
        try{ val = ((BComplex)val).get(props[i]); }
        catch(Exception e)
        {
          val = BString.make("");
          break;
        }
      }
      return val.newCopy();
    }
/*
    public void save(MgrEditRow row, BValue value, Context cx)
    {
      BComplex target = getTargetBase(row);

      int len = props.length;
      for(int i=0; i<len-1; ++i)
        target = (BComplex)target.get(props[i]);

      Property prop = props[len-1];
      BValue old = target.get(prop);
      if (!old.equivalent(value)) target.set(prop, value.newCopy(), cx);
    }

    public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
    {
      return toFieldEditor(rows, colIndex, currentEditor, props);
    }

    public void fromEditor(MgrEditRow[] rows, int colIndex, BWbEditor editor)
      throws Exception
    {
      fromFieldEditor(rows, colIndex, editor, props);
    }

    protected Property[] props;
    */
  }

  static String lexName = UiLexicon.bajaui().getText("name");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  DiscoveryListSubscriber discoveryListSubscriber;

//////////////////////////////////////////////////////////////
// Constants
//////////////////////////////////////////////////////////////

  static final Lexicon superLexicon = Lexicon.make(MgrController.class);
  static final UiLexicon lexicon = UiLexicon.makeUiLexicon(BNrioDeviceManager.class);
  static final Lexicon lex = Lexicon.make(BNrioDeviceManager.class);
  static BImage stationIcon = BImage.make("module://icons/x16/device.png");
  // base class columns
  MgrColumn colName    = new MgrColumn.Name();
  MgrColumn colDevType = new MgrColumn.Prop(BNrioDevice.deviceType, MgrColumn.EDITABLE | MgrColumn.READONLY);
  MgrColumn colExts    = new DeviceExtsColumn(new BNrioDevice());
  MgrColumn colStatus  = new MgrColumn.Prop(BDevice.status);
  MgrColumn colEnable  = new MgrColumn.Prop(BDevice.enabled, MgrColumn.EDITABLE | MgrColumn.UNSEEN);
  MgrColumn colHealth  = new MgrColumn.Prop(BDevice.health, MgrColumn.UNSEEN);
  MgrColumn colAddress = new MgrColumn.Prop(BNrioDevice.address, MgrColumn.EDITABLE | MgrColumn.READONLY );
  MgrColumn colSecAddr = new PropPath("SecAddr", new Property[] { BNrio34Module.io34Sec, BNrioDevice.address},  MgrColumn.EDITABLE | MgrColumn.READONLY);
  MgrColumn colUid     = new BlobColumn(BNrioDevice.uid, MgrColumn.EDITABLE | MgrColumn.READONLY );
  MgrColumn colInsVer  = new MgrColumn.Prop(BNrioDevice.installedVersion);
  MgrColumn colSecVer  = new MgrColumn.Prop(BNrio34Module.secVersion);
  MgrColumn colAvaVer  = new MgrColumn.Prop(BNrioDevice.availableVersion);

}
