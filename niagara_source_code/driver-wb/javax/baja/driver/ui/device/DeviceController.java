/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.ui.device;


import javax.baja.driver.BDevice;
import javax.baja.space.Mark;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.ui.BDialog;
import javax.baja.ui.BLabel;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.transfer.TransferConst;
import javax.baja.util.Lexicon;
import javax.baja.workbench.mgr.BMgrTable;
import javax.baja.workbench.mgr.BTemplateTable;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrEdit;
import javax.baja.workbench.mgr.folder.FolderController;

import com.tridium.driver.util.DrUtil;
import com.tridium.sys.transfer.DeployToComp;
import com.tridium.sys.transfer.DeployToComp.DeployTransferResult;
import com.tridium.template.BTemplateConfig;
import com.tridium.template.file.BNtplFile;
import com.tridium.template.file.TemplateManager.TemplateInfo;
import com.tridium.template.ui.file.BWbDeployableNtplFile;
import com.tridium.workbench.transfer.TransferArtifact;

/**
 * DeviceController is the MgrModel to be used for BDeviceManagers.
 *
 * @author    Brian Frank
 * @creation  15 Dec 03
 * @version   $Revision: 2$ $Date: 11/9/04 9:08:35 AM EST$
 * @since     Baja 1.0
 */
public class DeviceController
  extends FolderController
{

  /**
   * Constructor.
   */
  public DeviceController(BDeviceManager manager)
  {
    super(manager);
    this.editTemplate = new EditTemplate(manager);
    editTemplate.setFlags(BARS);
    editTemplate.setEnabled(false);
    templateMode.setEnabled(true);
    // NCCB-34577: Disable menu to run Bulk Deploy Action from Niagara Network Device
    // this.bulkDeploy = new DeviceBulkDeploy(manager);

  }

////////////////////////////////////////////////////////////////
// Bulk Deploy
////////////////////////////////////////////////////////////////

  // NCCB-34577: Disable menu to run Bulk Deploy Action from Niagara Network Device
  /**
   * Handle a header menu on the device template table. This will load devices templates
   * configured in a selected Excel file into the station's Niagara Network.
   */
  public void headerPopup(BTemplateTable table, BMouseEvent event, int column)
  {

    // super.headerPopup() is only relevant for a DynamicTreeTableModel, so no need to call it here
//    BMenu menu = new BMenu();
//    menu.add(null, bulkDeploy);
//    menu.open(table, event.getX(), event.getY());
  }

//  class DeviceBulkDeploy
//    extends BulkDeploy
//  {
//    public DeviceBulkDeploy()
//    {
//      super(DeviceController.this.getManager(), lex.getText("template.bulkDeploy"));
//    }
//    public DeviceBulkDeploy(BAbstractManager manager)
//    {
//      super(manager, lex.getText("template.bulkDeploy"));
//    }
//
//    @Override
//    public CommandArtifact doInvoke()
//      throws Exception
//    {
//      // TODO: Any checks needed that would prevent deployment?
//
//      return doDeploy();
//    }
//  }

//  public final Command bulkDeploy;


////////////////////////////////////////////////////////////////
// EditTemplate
////////////////////////////////////////////////////////////////
  class EditTemplate extends MgrCommand
  {
    EditTemplate(BWidget owner) { super(owner, lex, "editTemplate"); }
    @Override
    public CommandArtifact doInvoke() throws Exception
    {
      getManager().setMgrEditState(MgrEdit.EDIT);
      CommandArtifact artifact =doEditTemplate(getManager().getCurrentContext());
      getManager().setMgrEditState(MgrEdit.DEFAULT);
      return artifact;
    }
    @Override
    public Command doMerge(Command c) { return this; }
  }

  protected IMgrCommand[] makeTemplateCommands()
  {
    IMgrCommand[] superCommands = super.makeTemplateCommands();
    IMgrCommand[] devCommands =  new IMgrCommand[] { editTemplate, };
    return MgrController.append(superCommands, devCommands);
  }

  /**
   * This callback is made when commands should update
   * their enable/disable state based on current conditions.
   */
  public void updateCommands()
  {
    super.updateCommands();
    // edit only works for non-folders
    BComponent[] sel = getSelectedComponentsWithoutFolders();
    boolean allSameTemplate = true;
    String tmplName = "";
    for (BComponent comp : sel)
    {
      BTemplateConfig tc = BTemplateConfig.getConfigForRoot(comp);
      if(tc == null)
      {
        allSameTemplate = false;
        break;
      }

      if(tmplName.isEmpty())
      {
        tmplName = tc.getTemplateName();
      }
      else
      {
        if(!tmplName.equals(tc.getTemplateName()))
        {
          allSameTemplate = false;
          break;
        }
      }
    }
    editTemplate.setEnabled( !tmplName.isEmpty() && allSameTemplate);
  }

  public void cellDoubleClicked(BMgrTable table, BMouseEvent event, int row, int col)
  {
    MgrColumn mgrCol = table.columnIndexToMgrColumn(col);
    if (mgrCol instanceof DeviceExtsColumn)
      ((DeviceExtsColumn)mgrCol).cellDoubleClicked(table, event, row, col);
    else
    {
      if(event.isControlDown() && editTemplate.isEnabled())
      {
        getManager().setMgrEditState(MgrEdit.EDIT);
        editTemplate.invoke();
        getManager().setMgrEditState(MgrEdit.DEFAULT);
      }
      else
        super.cellDoubleClicked(table, event, row, col);
    }
  }

  /**
   * Called when a drag from templateTable to MgrTable is performed.  Subclasses should override.
   */
  protected CommandArtifact doEditTemplate(Object discovery, Context cx)
    throws Exception
  {
    return null;
  }



  public CommandArtifact doEditTemplate(Context cx)
    throws Exception
  {
    // get selected components
    BComponent[] sel = getSelectedComponents();
    if (sel.length == 0)
    {
      return null;
    }

    // create edit list
    DeviceTemplateEdit edit = new DeviceTemplateEdit(getManager(), this.editTemplate.getLabel());
    for(int i=0; i<sel.length; ++i)
    {
      edit.addRow(sel[i]);
    }

    // prompt user with edit dialog
    edit.setSelectAll(true);
    return edit.invoke(cx);
  }

  /**
   * Implementation of deploy action resulting from drag of object from templateTable to mgrTable.
   * Deploy the specified BIDeployable
   */
  protected CommandArtifact doDeploy(Object template, Context cx)
    throws Exception
  {
    BNtplFile ntplFile = null;
    BWbDeployableNtplFile deployable;
    if (template instanceof BWbDeployableNtplFile)
      deployable = (BWbDeployableNtplFile)template;
    else if(template instanceof BNtplFile)
    {
      ntplFile = (BNtplFile)template;
      deployable = BWbDeployableNtplFile.make(ntplFile);
    }
    else
    {
      TemplateInfo templateInfo = (TemplateInfo)template;
      ntplFile = templateInfo.getNtplFile();
      deployable = BWbDeployableNtplFile.make(ntplFile);
    }
    String deployName = deployable.getDeployName();

    // prompt for deploy count
    // build input pane
    BTextField nameField = new BTextField(deployName);
    BTextField countField = new BTextField("1", 6);
    BGridPane grid = new BGridPane(2);
    grid.add(null, new BLabel(lex.getText("template.deploy.name")));
    grid.add(null, nameField);
    grid.add(null, new BLabel(lex.getText("template.deploy.count")));
    grid.add(null, countField);
    // prompt
    String title = lex.getText("template.deploy");
    int r = BDialog.open(getManager(), title, grid, BDialog.OK_CANCEL);
    if (r == BDialog.CANCEL)
    {
      return null;
    }
    // extract deployCount
    int deployCount = Integer.parseInt(countField.getText());
    deployName = nameField.getText();

    BDevice[] addedDevices = new BDevice[deployCount];
    String[] addedNames = new String[deployCount];
    String[] deployNames = new String[deployCount];
    BWbDeployableNtplFile[] templates = new BWbDeployableNtplFile[deployCount];
    for(int i=0; i < deployCount; ++i)
    {
      templates[i] = deployable;
      deployNames[i] = deployName;
    }

    // Copy will implement deployable steps without popup
    Mark mark = new Mark(templates, deployNames);
    BComponent target = getManager().getTarget();

    // use TransferArtifact to do all the real work
    TransferArtifact art = new TransferArtifact(getManager(), TransferConst.ACTION_COPY, mark, target, null, DeployToComp.NoPostConfigEdit);
    art.redo();
    if(art.getResult() == null)
      return null;

    DeployTransferResult transferResult = (DeployTransferResult)art.getResult();

    // Wait for changes to communication to table
    DrUtil.wait(250);

    // Use insert name to select component to edit
    getTable().selectByName(transferResult.getInsertNames());
    return doEditTemplate(cx);
  }



  static boolean TEST_ONLY = true;
  static boolean COMMIT = false;

  final Lexicon lex = Lexicon.make(DeviceController.class);

  public final MgrCommand editTemplate;


}


