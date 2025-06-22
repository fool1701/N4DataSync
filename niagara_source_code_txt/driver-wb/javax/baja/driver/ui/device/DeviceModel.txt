/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.ui.device;

import javax.baja.driver.BDevice;
import javax.baja.driver.BDeviceFolder;
import javax.baja.driver.BIDeviceFolder;
import javax.baja.space.Mark;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Type;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.transfer.TransferConst;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrEditRow;
import javax.baja.workbench.mgr.MgrTypeInfo;
import javax.baja.workbench.mgr.folder.FolderModel;
import com.tridium.sys.transfer.DeployToComp;
import com.tridium.sys.transfer.TransferResult;
import com.tridium.template.file.BNtplFile;
import com.tridium.template.file.TemplateManager.TemplateInfo;
import com.tridium.template.ui.file.BWbDeployableNtplFile;
import com.tridium.workbench.transfer.TransferArtifact;

/**
 * DeviceModel is the MgrModel to be used for BDeviceManagers.
 *
 * @author    Brian Frank
 * @creation  15 Dec 03
 * @version   $Revision: 9$ $Date: 3/21/05 2:07:49 PM EST$
 * @since     Baja 1.0
 */
public class DeviceModel
  extends FolderModel
{                 

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Constructor.
   */
  public DeviceModel(BDeviceManager manager) 
  { 
    super(manager); 
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  
  /**
   * Return folder type.
   */
  public Type getFolderType()
  {
    return folderType;
  }

  /**
   * Get the base type supported by the new operation.
   */
  public Type  getBaseNewType()
  {
    return BDevice.TYPE;
  }


  /**
   * Return the concrete types of <code>network.deviceType</code>.
   */
  public MgrTypeInfo[] getNewTypes()
  {
    return MgrTypeInfo.makeArray(deviceType);
  }  
  
  /**
   * The default columns are name, type, status, and health.
   */
  protected MgrColumn[] makeColumns()
  {                       
    return new MgrColumn[] 
    { 
      new MgrColumn.Path(MgrColumn.UNSEEN),
      new MgrColumn.Name(),
      new MgrColumn.Type(),
      new MgrColumn.Prop(BDevice.status),
      new MgrColumn.Prop(BDevice.enabled, MgrColumn.EDITABLE | MgrColumn.UNSEEN),
      new MgrColumn.Prop(BDevice.health),
      new MgrColumn.Prop(BDevice.faultCause),
    };
  }

  /**
   * Return true if device of device folder type.
   */
  public Type[] getIncludeTypes()
  {                                   
    return new Type[] { deviceType, folderType };
  }

  /**
   * Run DeviceModel specific code, then call super.
   */
  public void load(BComponent target)
  {              
    folder = (BIDeviceFolder)target;
    try
    {
      deviceType = folder.getDeviceType();
      folderType = folder.getDeviceFolderType();
    }
    catch(Exception e)
    {
      // this occurs when using offline
    }
    super.load(target);         
  }

  /*
   * This callback is made by addDevTemplateInstances() when a template is being added via the DeviceManagerView
   * At this point the device templates have been added to the station, and it is prepairing to match the
   * discovered objects to the added devices.
   * It provides a chance for the driver to prepare the discovered objects and/or
   * the template added devices to the station.
   *
   * Default iimplementation just returns the given discovered objects.
   */
  public Object[] prepareDeployMatch(Object[] discObj, BDevice[] addedDevices, Context cx)
  {
    return discObj;
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
    if(getManager().getController().templateMode.isSelected())
    {
      if(getManager().getMgrTemplate().getTable().getSelectedObjects().length == 1)
        return addDevTemplateInstances(rows, cx);
    }
    return super.addInstances(rows, cx);
  }

  public CommandArtifact addDevTemplateInstances(MgrEditRow[] rows, Context cx)
  {

    TemplateInfo templateInfo = (TemplateInfo)getManager().getMgrTemplate().getTable().getSelectedObject();
    BNtplFile selDevTemplate = templateInfo.getNtplFile();
    BWbDeployableNtplFile selDevDeployableTemplate = BWbDeployableNtplFile.make(selDevTemplate);
    BComponent target = getManager().getTarget();
    String deployName = selDevDeployableTemplate.getDeployName();

    BDevice[] addedDevices = new BDevice[rows.length];
    String[] addedNames = new String[addedDevices.length];
    String[] deployNames = new String[addedDevices.length];
    BWbDeployableNtplFile[] templates = new BWbDeployableNtplFile[rows.length];
    Object[] discoverObjs = new Object[rows.length];
    for(int i=0; i <rows.length; ++i)
    {
      templates[i] = selDevDeployableTemplate;
      deployNames[i] = deployName;
      discoverObjs[i] = rows[i].getDiscovery();
    }
    try
    {
      Mark mark = new Mark(templates, deployNames);
      // use TransferArtifact to do all the real work
      TransferArtifact art = new TransferArtifact(getManager(), TransferConst.ACTION_COPY, mark, target, null, DeployToComp.NoPostConfigEdit);
      art.redo();
      if (art.getResult() == null)
        return null;

      TransferResult tr = art.getResult();
      addedNames = tr.getInsertNames();

      for(int i=0; i < addedDevices.length; ++i)
      {
        addedDevices[i] = (BDevice)target.get(addedNames[i]);
      }
      Object[] processedObjects = prepareDeployMatch(discoverObjs, addedDevices, cx);
      DeviceMatchArtifact matchArt = new DeviceMatchArtifact((BDeviceManager)getManager(), processedObjects, addedDevices, cx);
      matchArt.redo();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

    getManager().getModel().getTable().selectByName(addedNames);

    try
    {
      final MgrController mgrController = getManager().getController();
      if(mgrController instanceof DeviceController)
      {
        ((DeviceController)mgrController).doEditTemplate(cx);
      }
      mgrController.doEdit(cx);
    }
    catch(Exception e) {e.printStackTrace();}
    return null;
  }


  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BIDeviceFolder folder;
  Type deviceType = BDevice.TYPE;
  Type folderType = BDeviceFolder.TYPE;
  
} 


