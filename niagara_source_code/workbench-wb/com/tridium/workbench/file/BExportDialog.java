
/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javax.swing.*;

import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentList;
import javax.baja.collection.BITable;
import javax.baja.export.BIExportDestinationType;
import javax.baja.export.BITransformOperation;
import javax.baja.file.BExporter;
import javax.baja.file.BajaFileUtil;
import javax.baja.file.BajaFileUtil.BajaFileWriter;
import javax.baja.gx.BImage;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIObject;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.LocalizableException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.transform.BITransformer;
import javax.baja.ui.BButton;
import javax.baja.ui.BDialog;
import javax.baja.ui.BLabel;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BProgressDialog;
import javax.baja.ui.BRadioButton;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.ToggleCommand;
import javax.baja.ui.ToggleCommandGroup;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.enums.BValign;
import javax.baja.ui.file.BFileChooser;
import javax.baja.ui.list.ListSelection;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.pane.BTabbedPane;
import javax.baja.ui.util.UiLexicon;
import javax.baja.ui.wizard.BWizardHeader;
import javax.baja.workbench.BWbEditor;
import javax.baja.workbench.BWbProfile;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.view.BWbView;

import com.tridium.export.ExportContext;
import com.tridium.export.impl.destination.BFileDestinationType;
import com.tridium.export.impl.provider.BExporterTransformer;
import com.tridium.export.ui.impl.destination.BViewInExternalApplicationDestinationType;
import com.tridium.nre.RunnableWithException;
import com.tridium.workbench.export.WorkbenchExportContext;
import com.tridium.workbench.export.destination.BViewInWorkbenchDestinationType;
import com.tridium.workbench.export.provider.BWorkbenchTransformOperationProvider;

/**
 * BExportDialog is the dialog shown in Workbench when the user invokes an
 * export operation.
 *
 * @author    Brian Frank on 22 May 04
 * @version   $Revision: 30$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BExportDialog extends BEdgePane
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.file.BExportDialog(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BExportDialog.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



////////////////////////////////////////////////////////////////
// HasExporters
////////////////////////////////////////////////////////////////

  /**
   * Return true if the current view has any exporters.
   */
  public static boolean hasExporters(BWbShell shell, boolean asPrint)
  {
    BWbView view = shell.getActiveView();
    OrdTarget target = shell.getActiveOrdTarget();
    BExportDialog dlg;
    try
    {
      dlg = new BExportDialog(shell, target, view, asPrint);
    }
    catch (Exception e)
    { // If we get an exception when creating the BExportDialog, typically
      // that means it has no exporters (throws a LocalizableException in
      // that case).  But even if it has another problem, if we can't create
      // the export dialog, then we should just return false.
      return false;
    }

    // Not sure if the check below is really necessary, since this check also happens
    // in the BExportDialog constructor (probably safe to always return true here).
    // But I'll let it ride for now in case I'm overlooking something.
    return !dlg.findTransformOps(shell.getProfile(), target).isEmpty();
  }

////////////////////////////////////////////////////////////////
// Invoke
////////////////////////////////////////////////////////////////

  /**
   * Show the export dialog and process result.
   */
  public static CommandArtifact invoke(BWbShell shell, boolean asPrint)
    throws Exception
  {
    BWbView view = shell.getActiveView();
    OrdTarget target = shell.getActiveOrdTarget();
    return new BExportDialog(shell, target, view, asPrint).invoke();
  }

  /**
   * This is the method called from BTable using reflection.
   */
  public static CommandArtifact invoke(BWidget owner, BITable<BIObject> table, boolean asPrint)
    throws Exception
  {
    // Fix issue 7676 - need to include the table's facets so the timezone info will be passed along
    OrdTarget target = BOrd.make("view:?dummy=dummy").resolve((BObject)table, table.getTableFacets());
    return new BExportDialog(owner, target, null, asPrint).invoke();
  }

  /**
   * This is the method to export an Ord.  {@code BNavMenuAgent}s will
   * typically invoke this to export objects in the NavTree.
   * @param exportOrd The absolute ord for the object we want to export.
   * All registered {@code BExporter}'s will be displayed for this object.
   */
  public static CommandArtifact invoke(BWidget owner, BOrd exportOrd, boolean asPrint)
    throws Exception
  {
    OrdTarget target = exportOrd.resolve();
    return new BExportDialog(owner, target, null, asPrint).invoke();
  }

  /**
   * Command invoke implementation.
   */
  private CommandArtifact invoke() throws Exception
  {
    // loop until we successfully save the
    // setup editor or until the user cancels
    while (true)
    {
      // open dialog
      int r = BDialog.open(owner, LEX.get("export", "Export"), this,
        BDialog.OK_CANCEL);

      if (r != BDialog.OK) { return null; }

      // save the exporter setup
      try
      {
        setup.saveValue();
        break;
      }
      catch(Exception e)
      {
        BDialog.error(owner, LEX.get("exporter.setup.cannotSave", "Cannot save setup"), e);
      }
    }

    actionGroup.getSelected().doExport();
    return null;
  }

  private boolean userWantsToCreateFile() throws IOException
  {
    if (fileWriter.exists(getUserSelectedFileOrd(), null))
    {
      return BDialog.YES == BDialog.confirm(owner,
        LEX.get("fileChooser.fileExistsPrompt",
          "File already exists.  Do you want to overwrite?"));
    }
    else
    {
      return true;
    }
  }

  private BOrd getUserSelectedFileOrd()
  {
    String fileOrd = saveOrd.getText();
    String ext = getCurrentExporter().getFileExtension();
    if (!fileOrd.endsWith('.' + ext)) { fileOrd += '.' + ext; }
    return BOrd.make(fileOrd);
  }

////////////////////////////////////////////////////////////////
// Construction
////////////////////////////////////////////////////////////////

  /**
   * @param owner the widget that owns the export operation
   * @param target the thing being exported
   * @param view the current Workbench view being exported; may be null
   * @param asPrint true if we are exporting for printing purposes
   * @throws LocalizableException if no exporters can be found
   */
  private BExportDialog(BWidget owner, OrdTarget target, BWbView view, boolean asPrint)
    throws LocalizableException
  {
    this.owner     = owner;
    this.view      = view;

    transformOps = findTransformOps(getProfile(), target);

    if (transformOps.isEmpty())
    {
      throw new LocalizableException(LEX, "exporter.noExporters");
    }

    setTop(new BWizardHeader(ICON, LEX.getText("commands.export.label")));
    BTabbedPane tabs = new BTabbedPane();
    tabs.addPane(LEX.getText("exporter.action"), buildAction());
    tabs.addPane(LEX.getText("exporter.setup"), setupPane);
    setCenter(new BBorderPane(tabs));

    // if print then select first PDF exporter
    int index = asPrint ? getIndexOfFirstPdfExporter() : 0;
    exporterField.getList().setSelectedIndex(index);
  }

  private BExporter getCurrentExporter()
  {
    return ((BExporterTransformer) selectedTransformOp.getTransformer()).getExporter();
  }

  private BWbProfile getProfile()
  {
    BWbShell shell = getWbShell();
    return shell == null ? new BWbProfile() : shell.getProfile();
  }

  /**
   * Get the WbShell of the owner or return null.
   */
  private BWbShell getWbShell()
  {
    return BWbShell.getWbShell(owner);
  }

  /**
   * Build action tab.
   */
  private BWidget buildAction()
  {
    BGridPane pane = new BGridPane(1);
    pane.setValign(BValign.top);
    pane.setHalign(BHalign.left);
    pane.add(null, buildExporterPane());
    updateFromSelectedTransformOp();
    pane.add(null, new BBorderPane(buildExportDestinationPane(), 10, 0, 0, 0));
    return new BBorderPane(pane);
  }

  private BGridPane buildExporterPane()
  {
    exporterField.getList().setSelection(new ExporterListSelection());
    for (BITransformOperation<?, ?> item : transformOps)
    {
      BITransformer<?, ?> transformer = item.getTransformer();
      exporterField.getList().addItem(BImage.make(transformer.getIcon(null)),
        transformer.getDisplayName(null));
    }

    BGridPane exporterPane = new BGridPane(2);
    exporterPane.add(null, new BLabel(LEX.getText("exporter.select.exporter")));
    exporterPane.add(null, exporterField);

    return exporterPane;
  }

  private BGridPane buildExportDestinationPane()
  {
    BGridPane actionPane = new BGridPane(1);
    WorkbenchExportContext cx = makeExportContext();
    List<ExporterCommand> destinationCmds = new ArrayList<>();

    //go in reverse order so save is at the bottom, closest to save controls
    addDestination(destinationCmds, actionSave, cx);
    addDestination(destinationCmds, actionViewExternal, cx);
    addDestination(destinationCmds, actionViewInternal, cx);
    addDestinationsFromRegistry(destinationCmds, cx);
    Collections.reverse(destinationCmds);

    for (ExporterCommand cmd : destinationCmds)
    {
      actionGroup.add(cmd);
      actionPane.add(null, new BRadioButton(cmd));
    }
    actionViewExternal.setSelected(true);

    BEdgePane savePane = new BEdgePane();
    savePane.setRight(new BButton(saveBrowse));

    actionPane.setColumnAlign(BHalign.fill);
    actionPane.add(null, new BBorderPane(saveOrd, 0, 0, 0, 20));
    actionPane.add(null, new BBorderPane(savePane, 0, 0, 0, 20));

    return actionPane;
  }

  /**
   * Add a command for an export destination, if valid and not already added.
   */
  private void addDestination(
    List<ExporterCommand> exporterCommands,
    ExporterCommand cmd,
    WorkbenchExportContext cx)
  {
    if (BIExportDestinationType.isValid(cmd.destination, cx) &&
      !exporterCommands.contains(cmd))
    {
      exporterCommands.add(cmd);
    }
  }

  private void addDestinationsFromRegistry(
    List<ExporterCommand> exporterCommands,
    WorkbenchExportContext cx)
  {
    for (BIExportDestinationType<? super WorkbenchExportContext> destination :
      getDestinationsFromRegistry())
    {
      addDestination(exporterCommands, new ExporterCommand(destination), cx);
    }
  }

  private static List<BIExportDestinationType<? super WorkbenchExportContext>>
    getDestinationsFromRegistry()
  {
    //noinspection unchecked
    return Arrays.stream(Sys.getRegistry().getTypes(BIExportDestinationType.TYPE.getTypeInfo()))
      .filter(type -> !type.isAbstract())
      .map(type -> (BIExportDestinationType<? super WorkbenchExportContext>) type.getInstance())
      .collect(Collectors.toList());
  }

////////////////////////////////////////////////////////////////
// Exporter Discovery
////////////////////////////////////////////////////////////////

  private WorkbenchExportContext makeExportContext()
  {
    return new WorkbenchExportContext(getProfile(), view, owner,
      BFacets.make(
        ExportContext.FILE_EXTENSION, BString.make(getCurrentExporter().getFileExtension()),
        ExportContext.FILE_ORD, getUserSelectedFileOrd()));
  }

  /**
   * Query the registry to find all the exporters available
   * for the current view and current target.
   */
  private List<BITransformOperation<? super OrdTarget, ? super WorkbenchExportContext>> findTransformOps(BWbProfile profile, OrdTarget target)
  {
    WorkbenchExportContext cx = new WorkbenchExportContext(profile, view, owner, null);
    return BWorkbenchTransformOperationProvider.INSTANCE.getTransformOperations(target, cx);
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  private int getIndexOfFirstPdfExporter()
  {
    for (int i = 0; i < transformOps.size(); ++i)
    {
      if ("application/pdf".equals(transformOps.get(i).getTransformer().getMimeType()))
      {
        return i;
      }
    }
    return 0;
  }

  /**
   * When the user changes the exporter selection, then we
   * need to update the setup tab with the current exporter
   * and a field editor to configure it.
   */
  private void updateTransformOp(BITransformOperation<? super OrdTarget, ? super WorkbenchExportContext> item)
  {
    try
    {
      selectedTransformOp = item;

      AgentList viewAgents = getCurrentExporter().getAgents().filter(IS_WB_VIEW);

      setup = (BWbEditor) viewAgents.getDefault().getInstance();
      setup.loadValue(getCurrentExporter());
      setupPane.setContent(setup);
      actionViewInternal.setEnabled(viewAgents.size() > 0);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      setup = null;
      setupPane.setContent(new BLabel("ERROR"));
    }
  }

  /**
   * Update the configuration tabs from whatever transform operation is
   * currently selected by the user. If none is selected yet, just use the first
   * one.
   */
  private void updateFromSelectedTransformOp()
  {
    int index = exporterField.getList().getSelectedIndex();
    updateTransformOp(transformOps.get(Math.max(index, 0)));
  }

  private static void post(Runnable todo)
  {
    synchronized(executorMon)
    {
      if (executorSrv == null)
      {
        executorSrv = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "ExportDialogThread"));
      }
    }

    executorSrv.submit(todo);
  }

////////////////////////////////////////////////////////////////
// Commands
////////////////////////////////////////////////////////////////

  private class ExporterListSelection extends ListSelection
  {
    @Override
    public void updateList()
    {
      super.updateList();
      updateFromSelectedTransformOp();
    }
  }

  /**
   * ToggleCommand for performing the currently selected export operation.
   */
  private class ExporterCommand extends ToggleCommand
  {
    /**
     * @param destination where this command will send the transformed data.
     */
    private ExporterCommand(BIExportDestinationType<? super WorkbenchExportContext> destination)
    {
      this(destination, destination.getDisplayName(null));
    }

    /**
     * @param destination where this command will send the transformed data.
     * @param label display label for the command
     */
    private ExporterCommand(BIExportDestinationType<? super WorkbenchExportContext> destination,
                            String label)
    {
      super(BExportDialog.this, label);
      this.destination = destination;
    }

    protected void doExport() throws IOException
    {
      showProgressUntilDone(() ->
        destination.transform(selectedTransformOp, makeExportContext()));
    }

    private void showProgressUntilDone(RunnableWithException<Exception> r)
    {
      post(()->{
        BProgressDialog.openIndeterminate(
          owner,
          LEX.get("pleaseWait", "Please wait"),
          dlg -> {
            try
            {
              r.run();
            }
            catch(Exception e)
            {
              throw new BajaRuntimeException(e);
            }
            return null;
          });
      });
    }

    /**
     * Two ExporterCommands are equal if they share the same destination.
     */
    @Override
    public boolean equals(Object o)
    {
      return o instanceof ExporterCommand &&
        ((ExporterCommand) o).destination == destination;
    }

    @Override
    public int hashCode() { return destination.hashCode(); }

    private final BIExportDestinationType<? super WorkbenchExportContext> destination;
  }

  private class SaveBrowse extends Command
  {
    SaveBrowse(BWidget owner)
    {
      super(owner, LEX, "exporter.save.browse");
      setEnabled(false);
    }

    @Override
    public CommandArtifact doInvoke()
    {
      BFileChooser chooser = BFileChooser.makeSave(getOwner());
      chooser.setCreateFileOnSave(false);
      chooser.setConfirmOverwrite(false);

      BOrd ord = chooser.show();
      if (ord != null)
      {
        ord = ord.relativizeToSession();
        saveOrd.setText(ord.toString());
      }
      return null;
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final BImage ICON = BImage.make("module://icons/x32/export.png");
  private static final UiLexicon LEX = UiLexicon.bajaui();
  private static final AgentFilter IS_WB_VIEW = AgentFilter.is(BWbView.TYPE.getTypeInfo());

  private final BWidget owner;
  private final BWbView view;

  private final ExporterCommand actionViewInternal =
    new ExporterCommand(BViewInWorkbenchDestinationType.INSTANCE);

  private final ExporterCommand actionViewExternal =
    new ExporterCommand(BViewInExternalApplicationDestinationType.INSTANCE);

  // reuse the bajaui label instead of using the label from the export module,
  // to reduce the urgency to translate the new key
  private final ExporterCommand actionSave =
    new ExporterCommand(BFileDestinationType.INSTANCE, LEX.get("exporter.action.save.label"))
  {
    @Override
    protected void doExport() throws IOException
    {
      //only save if the user elects to create a new file.
      if (userWantsToCreateFile()) { super.doExport(); }
    }

    @Override
    public void setSelected(boolean sel)
    {
      super.setSelected(sel);
      // Must use isSelected() - not sel! Not always the same!
      saveOrd.setEditable(isSelected());
      saveBrowse.setEnabled(isSelected());
    }
  };

  private final BajaFileWriter fileWriter = BajaFileUtil.getDefaultFileWriter();

  private final ToggleCommandGroup<ExporterCommand> actionGroup = new ToggleCommandGroup<>();
  private final BListDropDown exporterField  = new BListDropDown();
  private final Command saveBrowse = new SaveBrowse(this);
  private final BTextField saveOrd = new BTextField("", 55, false);
  private final BBorderPane setupPane = new BBorderPane();
  private final List<BITransformOperation<? super OrdTarget, ? super WorkbenchExportContext>> transformOps;

  private BITransformOperation<? super OrdTarget, ? super WorkbenchExportContext> selectedTransformOp;
  private BWbEditor setup;
  private static final Object executorMon = new Object();
  private static volatile ExecutorService executorSrv;
}
