/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr.folder;

import javax.baja.nre.util.Array;
import javax.baja.space.Mark;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.HyperlinkInfo;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.util.Lexicon;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.mgr.BMgrTable;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrEdit;
import com.tridium.workbench.transfer.TransferUtil;

/**
 * FolderController is a MgrController to be used with BFolderManager.
 *
 * @author    Brian Frank
 * @creation  29 Jun 04
 * @version   $Revision: 10$ $Date: 11/22/06 5:25:45 PM EST$
 * @since     Baja 1.0
 */
public class FolderController
  extends MgrController
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public FolderController(BFolderManager manager)
  {
    super(manager);

    this.newFolder = new NewFolder(manager);
    this.allDescendants = new AllDescendants(manager);

    newFolder.setFlags(BARS | POPUP);
    allDescendants.setFlags(MENU_BAR | TOOL_BAR);
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the current selection but filter out any folders.
   */
  public BComponent[] getSelectedComponentsWithoutFolders()
  {
    FolderModel model = (FolderModel)getManager().getModel();
    Type folderType = model.getFolderType();

    BComponent[] all = getSelectedComponents();
    Array<BComponent> acc = new Array<BComponent>(BComponent.class, all.length);
    for(int i=0; i<all.length; ++i)
      if (!all[i].getType().is(folderType))
        acc.add(all[i]);
    return acc.trim();
  }

////////////////////////////////////////////////////////////////
// MgrController
////////////////////////////////////////////////////////////////

  protected IMgrCommand[] makeCommands()
  {
    return append(
      new IMgrCommand[] { newFolder, allDescendants },
      super.makeCommands());
  }

  public void updateCommands()
  {
    super.updateCommands();
    
    // edit only works for non-folders
    BComponent[] sel = getSelectedComponentsWithoutFolders();
    edit.setEnabled(sel.length > 0);
  }

  public void cellDoubleClicked(BMgrTable table, BMouseEvent event, int row, int col)
  {
    FolderModel model = (FolderModel)getManager().getModel();
    Type folderType = model.getFolderType();

    // if a folder, then hyperlink
    BComponent comp = getTable().getComponentAt(row);
    BWbShell shell = getManager().getWbShell();
    if (comp != null && comp.getType().is(folderType) && shell != null)
    {
      shell.hyperlink(new HyperlinkInfo(comp.getNavOrd(), event));
      return;
    }

    super.cellDoubleClicked(table, event, row, col);
  }

  public CommandArtifact doEdit(Context cx)
    throws Exception
  {
    // only get selection without folders for edit
    BComponent[] sel = getSelectedComponentsWithoutFolders();
    if (sel.length == 0) return null;

    // create edit list
    MgrEdit edit = makeEdit(this.edit.getLabel());
    for(int i=0; i<sel.length; ++i)
      edit.addRow(sel[i]);

    // prompt user with edit dialog
    edit.setSelectAll(true);
    return edit.invoke(cx);
  }

////////////////////////////////////////////////////////////////
// New Folder
////////////////////////////////////////////////////////////////

  class NewFolder extends MgrCommand
  {
    NewFolder(BWidget owner) { super(owner, lex, "newFolder"); }
    public CommandArtifact doInvoke() throws Exception { return doNewFolder(); }
    public Command doMerge(Command c) { return this; }
  }

  /**
   * This is the callback when the new folder command is invoked.
   */
  public CommandArtifact doNewFolder()
    throws Exception
  {
    BFolderManager mgr = (BFolderManager)getManager();
    FolderModel model = (FolderModel)mgr.getModel();
    BComponent parent = (BComponent)mgr.getCurrentValue();
    BComponent child  = (BComponent)model.getFolderType().getInstance();
    String name       = "Folder";
    return TransferUtil.insert(mgr, TransferUtil.ACTION_COPY, new Mark(child, name), parent, null, null);
  }

////////////////////////////////////////////////////////////////
// All Descendants
////////////////////////////////////////////////////////////////

  class AllDescendants extends MgrToggleCommand
  {
    AllDescendants(BWidget owner) { super(owner, lex, "allDescendants"); }
    public void setSelected(boolean sel)
    {
      boolean old = isSelected();
      super.setSelected(sel);
      if (old != sel) doAllDescendants(sel);
    }
    public Command doMerge(Command c) { return this; }
  }

  /**
   * This is the callback when the all descendants command is toggled.
   */
  public void doAllDescendants(boolean selected)
  {
    ((BFolderManager)getManager()).reloadNewDepth();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  Lexicon lex = Lexicon.make(BFolderManager.class);

  public final MgrCommand newFolder;
  public final MgrToggleCommand allDescendants;

}


