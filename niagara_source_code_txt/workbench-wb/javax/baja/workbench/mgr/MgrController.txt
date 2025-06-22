/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import javax.baja.job.BJob;
import javax.baja.naming.SlotPath;
import javax.baja.space.Mark;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.tag.TagGroupInfo;
import javax.baja.tag.TagInfo;
import javax.baja.ui.BAbstractButton;
import javax.baja.ui.BDialog;
import javax.baja.ui.BLabel;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BMenu;
import javax.baja.ui.BNullWidget;
import javax.baja.ui.BSeparator;
import javax.baja.ui.BToolBar;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.ToggleCommand;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.table.BTable;
import javax.baja.ui.transfer.TransferContext;
import javax.baja.ui.transfer.TransferFormat;
import javax.baja.ui.treetable.BTreeTable;
import javax.baja.ui.treetable.TreeTableSubject;
import javax.baja.ui.util.UiLexicon;
import javax.baja.util.Lexicon;
import javax.baja.workbench.component.table.ComponentTableSubject;
import com.tridium.workbench.fieldeditors.BIntegerFE;

/**
 * MgrController is used to manage the Commands of BAbstractManager.
 *
 * @author    Brian Frank
 * @creation  15 Dec 03
 * @version   $Revision: 51$ $Date: 7/27/10 7:11:31 AM EDT$
 * @since     Baja 1.0
 */
public class MgrController
  extends MgrSupport
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////


  /**
   * Construct an MColumn.
   */
  public MgrController(BAbstractManager manager)
  {
    super(manager);
    this.newCommand      = new New(manager);
    this.edit            = new Edit(manager);
    this.learnMode       = new LearnMode(manager);
    this.tagMode         = new TagMode(manager);
    this.templateMode = new TemplateMode(manager);
    this.discover        = new Discover(manager);
    this.cancel          = new Cancel(manager);
    this.add             = new Add(manager);
    this.match           = new Match(manager);
    this.quickAdd        = new QuickAdd(manager);
    this.quickMatch      = new QuickMatch(manager);
    this.tagIt           = new TagIt(manager);
    this.deploy          = new Deploy(manager);

    newCommand.setFlags(BARS | POPUP);
    edit.setFlags(BARS | POPUP);
    if (manager.isLearnable())
    {
      learnMode.setFlags(MENU_BAR | TOOL_BAR);
      discover.setFlags(BARS);
      cancel.setFlags(BARS);
      add.setFlags(BARS | LEARN_POPUP);
      quickAdd.setFlags(MENU_BAR);
      match.setFlags(BARS | LEARN_POPUP);
      quickMatch.setFlags(MENU_BAR);
    }
    if (manager.isTaggable())
    {
      tagMode.setFlags(MENU_BAR | TOOL_BAR);
      tagIt.setFlags(BARS | MENU_BAR);
    }
    if (manager.isTemplatable())
    {
      templateMode.setFlags(MENU_BAR | TOOL_BAR);
      templateMode.setEnabled(false);
    }

  }

  /**
   * Init is called once from {@code BAbstractManager.init()}
   * Must call {@code super.init()} if overridden.
   */
  @Override
  public void init()
  {
    updateCommands();
  }

////////////////////////////////////////////////////////////////
// Menu, ToolBar, ActionBar
////////////////////////////////////////////////////////////////

  /**
   * Make the toolbar to merge into the workbench shell.  The
   * default implementation builds a toolbar using the result
   * of getCommands() for each command which has the TOOL_BAR
   * flag set.
   */
  public BToolBar makeToolBar()
  {
    BToolBar toolBar = new BToolBar();
    IMgrCommand[] cmds = getCommands();
    for(int i=0; i<cmds.length; ++i)
    {
      IMgrCommand cmd = cmds[i];
      if ((cmd.getFlags() & TOOL_BAR) != 0)
      {
        String name = "cmd"+i;
        toolBar.add(name, (Command)cmd);
        BAbstractButton b = (BAbstractButton)toolBar.get(name);
        IMgrCommand[] drop = getDropDownCommands(cmd);
        if (drop != null && drop.length > 0)
        {
          b.setMenuController(new MenuController());
        }
      }
    }
    return toolBar;
  }

  /**
   * Make the menus to merge into the workbench shell.  The
   * default implementation builds a menu using the result
   * of getCommands() for each command which has the MENU_BAR
   * flag set.
   */
  public BMenu[] makeMenus()
  {
    BMenu menu = new BMenu(lex.getText("manager"));
    IMgrCommand[] cmds = getCommands();
    for(int i=0; i<cmds.length; ++i)
    {
      if ((cmds[i].getFlags() & MENU_BAR) != 0)
      {
        menu.add("cmd" + i, (Command)cmds[i]);
      }
    }
    return new BMenu[] { menu };
  }

  /**
   * Make the action bar to display along bottom of manager.
   * The default implementation builds a menu using the result
   * of getCommands() for each command which has the ACTION_BAR
   * flag set.
   */
  public BWidget makeActionBar()
  {
    return makeActionPane(getCommands());
  }

  /**
   * Layout action pane with buttons for each of the specified
   * commands which has the ACTION_BAR flag set.
   */
  protected BWidget makeActionPane(IMgrCommand[] cmds)
  {
    if(cmds.length==0)
    {
      return new BNullWidget();
    }
    int n = 0;
    BGridPane pane = new BGridPane();
    for(int i=0; i<cmds.length; ++i)
    {
      IMgrCommand cmd = cmds[i];
      if ((cmd.getFlags() & ACTION_BAR) != 0)
      {
        BAbstractButton b = BAbstractButton.make((Command)cmd);
        IMgrCommand[] drop = getDropDownCommands(cmd);
        if (drop != null && drop.length > 0)
        {
          b.setMenuController(new MenuController());
        }
        pane.add("cmd"+i, b);
        n++;
      }
    }
    pane.setColumnCount(n);
    return pane;
  }

  /**
   * This guy is used by makeToolBar and makeActionBar for
   * commands which have getDropDownCommands
   */
  class MenuController implements BAbstractButton.MenuController
  {
    @Override
    public boolean isMenuDistinct() { return true; }

    @Override
    public BMenu getMenu(BAbstractButton button)
    {
      IMgrCommand[] cmds = getDropDownCommands((IMgrCommand)button.getCommand());
      BMenu menu = new BMenu();
      for(int i=0; i<cmds.length; ++i)
      {
        menu.add(null, (Command)cmds[i]);
      }
      return menu;
    }
  }

  /**
   * This is used by makeToolbar and makeActionBar to
   * get subcommands for the specified commands.  These
   * subcommands are added as a drop down the primary
   * command button.  Return null for no drop down.
   */
  public IMgrCommand[] getDropDownCommands(IMgrCommand command)
  {
    return null;
  }

////////////////////////////////////////////////////////////////
// Commands
////////////////////////////////////////////////////////////////

  /**
   * Get the list of commands for the manager.  These commands
   * are used for the menu, toolbar, action bar, and popups.
   * This list is initialized by the makeCommands() method.
   * This method returns a copy of the commands, not the actual
   * command array.
   */
  public final IMgrCommand[] getCommands()
  {
    if (baseCommands == null)
    {
      baseCommands = makeCommands();
    }
    if (tagCommands == null)
    {
      tagCommands = makeTagCommands();
    }
    if(templateCommands == null)
    {
      templateCommands = makeTemplateCommands();
    }
    if(commands == null)
    {
      commands = append(baseCommands, tagCommands);
      commands = append(commands, templateCommands);
    }
    
    return commands.clone();
  }
  
  /**
   * Get the number of commands for the manager.
   * 
   * @since Niagara 3.6
   */
  public final int getCommandCount()
  {
    return commands.length;
  }  
  
  /**
   * Get the commands at the specified index.  Returns
   * the actual command, not a copy.
   * 
   * @since Niagara 3.6
   */  
  public final IMgrCommand getCommand(int index)
  {
    if (baseCommands == null)
    {
      baseCommands = makeCommands();
    }
    if (tagCommands == null )
    {
      tagCommands = makeTagCommands();
    }
    if(templateCommands == null)
    {
      templateCommands = makeTemplateCommands();
    }
    if(commands == null)
    {
      commands = append(baseCommands, tagCommands);
      commands = append(commands, templateCommands);
    }
    
    return commands[index];
  }  
  
  /**
   * Initialize the list of commands for the manager.  These
   * commands are used for the menu, toolbar, action bar,
   * and popups.  The default list includes all the commands
   * declared on MgrController as public fields.  Use the
   * append() method to safely add to the base class list.
   */
  protected IMgrCommand[] makeCommands()
  {
    return new IMgrCommand[]
    {
      newCommand,
      edit,
      learnMode,
      discover,
      cancel,
      add,
      quickAdd,
      match,
      quickMatch,
      templateMode,
    };
  }

  /**
   * Initialize the list of commands for the manager used for tagging.
   */
  protected IMgrCommand[] makeTagCommands()
  {
    return new IMgrCommand[]
    {
      tagMode,
      tagIt,
    };
  }

  /**
   * Initialize the list of commands for the manager used for templates.
   */
  protected IMgrCommand[] makeTemplateCommands()
  {
    return new IMgrCommand[0];
  }


  /**
   * Create a new array of commands by appending b to a.
   */
  public static IMgrCommand[] append(IMgrCommand[] a, IMgrCommand[] b)
  {
    IMgrCommand[] r = new IMgrCommand[a.length + b.length];
    System.arraycopy(a, 0, r, 0, a.length);
    System.arraycopy(b, 0, r, a.length, b.length);
    return r;
  }

  /**
   * Explicitly disable quick commands (those with a non-modified accelerator).
   * This is used to prevent quick commands from being fired when a text box has focus
   * @param enabled The new enabled value for quick commands
   */
  public void setQuickCommandsEnabled(boolean enabled)
  {
    quickCommandsEnabled = enabled;
    updateCommands();
  }

  /**
   * This callback is made when commands should update
   * their enable/disable state based on current conditions.
   */
  public void updateCommands()
  {
    int[] sel = getSelectedRows();
    edit.setEnabled(sel.length > 0);

    if (manager.learn != null)
    {
      BJob job = manager.learn.getJob();
      discover.setEnabled(job == null || !job.getJobState().isRunning());
      cancel.setEnabled(job != null && job.getJobState().isRunning());
    }
    else
    {
      discover.setEnabled(false);
      cancel.setEnabled(false);
    }

    if (learnMode.isSelected())
    {
      int[] learnSel = getLearnSelectedRows();
      add.setEnabled(learnSel.length > 0);
      match.setEnabled(checkMatch(learnSel, sel));
      // NCCB-8928: Only enable quick commands when we want them.
      quickAdd.setEnabled(add.isEnabled() && quickCommandsEnabled);
      quickMatch.setEnabled(match.isEnabled() && quickCommandsEnabled);
    }
    else
    {
      add.setEnabled(false);
      match.setEnabled(false);
      quickAdd.setEnabled(false);
      quickMatch.setEnabled(false);
    }

    if(tagMode.isSelected())
    {
      Object[] tagSel =
        manager.getMgrTagDictionary().getSelectedObjects();
      tagIt.setEnabled(checkTag(tagSel, sel));
    }
    else
    {
      tagIt.setEnabled(false);
    }
  }

  /**
   * Match is enabled only if exactly one row is selected
   * in both tables and the subclass returns true for isMatchable.
   */
  private boolean checkMatch(int[] learnSel, int[] dbSel)
  {
    try
    {
      if (learnSel.length == 1 && dbSel.length == 1)
      {
        Object dis = getLearnTable().getObjectAt(learnSel[0]);
        BComponent db = getTable().getComponentModel().getComponentAt(dbSel[0]);
        return manager.getLearn().isMatchable(dis, db);
      }
      return false;
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Match is enabled only if exactly one tag row is selected and 
   * one or more db rows is selected and the subclass returns true for isTagable.
   */
  private boolean checkTag(Object[] tagSel, int[] dbSel)
  {
    if ((tagSel.length == 0) || (dbSel.length == 0))
    {
      return false;
    }

    for (int t = 0; t < tagSel.length; t++)
    {
      Object tagObject = tagSel[t];
      for (int d = 0; d < dbSel.length; d++)
      {
        BComponent dbObject =
          getTable().getComponentModel().getComponentAt(dbSel[d]);

        if (tagObject instanceof TagInfo)
        {
          if (!getManager().getMgrTagDictionary().isTaggable((TagInfo)tagObject, dbObject))
          {
            return false;
          }
        }
        else if (tagObject instanceof TagGroupInfo)
        {
          if (!getManager().getMgrTagDictionary().isTaggable((TagGroupInfo)tagObject, dbObject))
          {
            return false;
          }
        }
        else
        {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * Make an instance of MgrEdit to use.
   */
  public MgrEdit makeEdit(String label)
  {
    return new MgrEdit(getManager(), label);
  }

////////////////////////////////////////////////////////////////
// Table Utils
////////////////////////////////////////////////////////////////

  /**
   * Convenience for {@code getManager().getModel().getTable()}
   */
  public final BMgrTable getTable()
  {
    return manager.model.table;
  }

  /**
   * Convenience for {@code getManager().getLearn().getTable()}.
   * Return null if learn not supported.
   */
  public final BLearnTable getLearnTable()
  {
    if (manager.learn == null)
    {
      return null;
    }
    return manager.learn.table;
  }

  /**
   * Convenience for {@code getManager().getDevTemplate().getTable()}.
   * Return null if templates are not supported in this manager.
   */
  public final BTemplateTable getDevTemplateTable()
  {
    if (manager.template == null)
    {
      return null;
    }
    return manager.template.table;
  }

  /**
  public final BTemplateTable getDevTemplateTable()
    return manager.template.table;
   * Convenience for {@code getTable().getSelection().getRows()}
   */
  public final int[] getSelectedRows()
  {
    BTable table = getTable();
    if (table == null)
    {
      return emptySelection;
    }
    return table.getSelection().getRows();
  }

  /**
   * Callback when the selection has been modified in the learn table.
   */
  public void learnSelectionChanged()
  {
    if (manager.template != null)
    {
      manager.template.learnSelected();
    }
  }

  /**
   * Convenience for {@code getLearnTable().getSelection().getRows()}
   * Return array of length 0 if learn not supported.
   */
  public int[] getLearnSelectedRows()
  {
    BLearnTable table = getLearnTable();
    if (table == null)
    {
      return emptySelection;
    }
    return table.getSelection().getRows();
  }

  /**
   * Callback when the selection has been modified in the
   * tag dictionary table.
   */
  public void tagSelectionChanged()
  {
    updateCommands();
  }

  /**
   * Callback when the selection has been modified in the
   * db table.
   */
  public void dbSelectionChanged()
  {
    updateCommands();
  }

  /**
   * Convenience for {@code getTable().getSelectedComponents()}
   */
  public final BComponent[] getSelectedComponents()
  {
    BMgrTable table = getTable();
    if (table == null)
    {
      return emptyComponentTable;
    }
    return table.getSelectedComponents();
  }

////////////////////////////////////////////////////////////////
// Table Control
////////////////////////////////////////////////////////////////

  /**
   * This is a hook for building the popup menu used by the BMgrTable.
   * New commands should be appended to the default menu.  The default
   * implementation adds all commands with the POPUP flag set.
   */
  public BMenu makePopup(BMgrTable table, ComponentTableSubject subject, BMenu menu)
  {
    if (menu == null)
    {
      menu = new BMenu();
    }

    menu.add(null, new BSeparator());
    IMgrCommand[] cmds = getCommands();
    for(int i=0; i<cmds.length; ++i)
    {
      IMgrCommand cmd = cmds[i];
      if ((cmd.getFlags() & POPUP) != 0)
      {
        menu.add("mgr_" + cmd.getName(), (Command)cmd);
      }
    }

    return menu;
  }

  /**
   * This is a hook for building the popup menu used by the BLearnTable.
   * New commands should be appended to the default menu.  The default
   * implementation adds all commands with the LEARN_POPUP flag set.
   */
  public BMenu makePopup(BLearnTable table, TreeTableSubject subject, BMenu menu)
  {
    if (menu == null)
    {
      menu = new BMenu();
    }

    IMgrCommand[] cmds = getCommands();
    for(int i=0; i<cmds.length; ++i)
    {
      IMgrCommand cmd = cmds[i];
      if ((cmd.getFlags() & LEARN_POPUP) != 0)
      {
        menu.add("mgr_" + cmd.getName(), (Command)cmd);
      }
    }

    if (subject.size() == 1)
    {
      BComponent existing = getManager().getLearn().getExisting(subject.get(0));
      menu.add("existing", new ShowExisting(table, existing));
    }

    menu.add("selectAll", new SelectAll(table));

    return menu;
  }

  /**
   * Handle a double click on the manager table.  Default implementation
   * opens edit dialog for specified row.  Note the column index is the
   * visible column index, not necessarily the MgrColumn index, see
   * BMgrTable.columnIndexToMgrColumn().
   */
  public void cellDoubleClicked(BMgrTable table, BMouseEvent event, int row, int col)
  {
    if(edit.isEnabled())
    {
      manager.setMgrEditState(MgrEdit.EDIT);
      edit.invoke();
      manager.setMgrEditState(MgrEdit.DEFAULT);
    }
  }

  /**
   * Handle a double click on the discovery table.  Default implementation
   * performs an add.  Note the column index is the visible column index, not
   * necessarily the MgrColumn index, see BLearnTable.columnIndexToMgrColumn().
   */
  public void cellDoubleClicked(BLearnTable table, BMouseEvent event, int row, int col)
  {
    if(add.isEnabled())
    {
      manager.setMgrEditState(MgrEdit.ADD);
      add.invoke();
      manager.setMgrEditState(MgrEdit.DEFAULT);
    }
  }

  /**
   * Handle a double click on the tag table.  Default implementation
   * will tag the selected database component.  
   */
  public void cellDoubleClicked(BTreeTable table, BMouseEvent event, int row, int col)
  {
    if(tagIt.isEnabled())
    {
      tagIt.invoke();
    }
  }


  /**
   * Handle a double click on the device template table.  Default implementation
   * will add the selected device template to the station.
   */
  public void cellDoubleClicked(BTemplateTable table, BMouseEvent event, int row, int col)
  {
    if(deploy.isEnabled())
    {
      deploy.invoke();
    }
  }

  /**
   * This is a hook for building the options menu used by the BMgrTable.
   */
  public BMenu makeOptionsMenu(BMgrTable table, BMenu menu)
  {
    return menu;
  }

  /**
   * This is a hook for building the options menu used by the BLearnTable.
   */
  public BMenu makeOptionsMenu(BLearnTable table, BMenu menu)
  {
    return menu;
  }

  /**
   * This is a hook for building the options menu used by the BTemplateTable.
   */
  public BMenu makeOptionsMenu(BTemplateTable table, BMenu menu)
  {
    return menu;
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
      if (data.equals("dragFromLearn:" + mgr.hashCode()))
      {
        return add.doInvoke();
      }
      else if (data.equals("dragFromTag:" + mgr.hashCode()))
      {
        return add.doInvoke();
      }
      else if (data.equals("dragFromTemplate:" + mgr.hashCode()))
      {
        return deploy.doInvoke();
      }
      else if (data.startsWith("file:") && data.endsWith(".ntpl"))
      {
        return doDeploy(cx);
      }
    }
    catch(Exception ignored)
    {
    }

    // use standard ComponentTable behavior
    return table.superDrop(cx);
  }

////////////////////////////////////////////////////////////////
//LearnMode
////////////////////////////////////////////////////////////////

  class LearnMode extends MgrToggleCommand
  {
    LearnMode(BWidget owner) { super(owner, lex, "learnMode"); }
    @Override
    public void setSelected(boolean sel)
    {
      super.setSelected(sel);
      doLearnMode(sel);
    }
  }

  /**
   * This is the callback when the learn mode is changed.
   */
  public void doLearnMode(boolean learnMode)
  {
    manager.updateContent();
    updateCommands();
  }

////////////////////////////////////////////////////////////////
//TagMode
////////////////////////////////////////////////////////////////

  class TagMode extends MgrToggleCommand
  {
    TagMode(BWidget owner) { super(owner, lex, "tagMode"); }
    @Override
    public void setSelected(boolean sel)
    {
      super.setSelected(sel);
      doTagMode(sel);
    }
  }

  /**
   * This is the callback when the tag mode is changed.
   */
  public void doTagMode(boolean tagMode)
  {
    manager.updateContent();
    updateCommands();
  }

////////////////////////////////////////////////////////////////
//TemplateMode
////////////////////////////////////////////////////////////////

  class TemplateMode extends MgrToggleCommand
  {
    TemplateMode(BWidget owner) { super(owner, lex, "templateMode"); }
    @Override
    public void setSelected(boolean sel)
    {
      super.setSelected(sel);
      doTemplateMode(sel);
    }
  }

  /**
   * This is the callback when the tag mode is changed.
   */
  public void doTemplateMode(boolean templateMode)
  {
    manager.updateContent();
    updateCommands();
  }

////////////////////////////////////////////////////////////////
// Discover
////////////////////////////////////////////////////////////////

  class Discover extends MgrCommand
  {
    Discover(BWidget owner) { super(owner, lex, "discover"); }
    @Override
    public CommandArtifact doInvoke() throws Exception { return doDiscover(null); }
  }

  /**
   * This is the callback when the discover command is invoked.
   * Discovery should always set the manager into learn mode.
   * If discovery options are available, then convention is to
   * prompt the user with a dialog for the options before kicking
   * off the discovery.  Standard practice is to use a Job to run
   * the discovery in the background by submiting the job via an
   * action that returns an BOrd and then calling MgrLearn.setJob().
   */
  public CommandArtifact doDiscover(Context cx)
    throws Exception
  {
    learnMode.setSelected(true);
    return null;
  }

////////////////////////////////////////////////////////////////
// Cancel
////////////////////////////////////////////////////////////////

  class Cancel extends MgrCommand
  {
    Cancel(BWidget owner) { super(owner, lex, "cancel"); }
    @Override
    public CommandArtifact doInvoke() throws Exception { return doCancel(null); }
  }

  /**
   * This is the callback when the cancel command is invoked.  The
   * default implementation will call cancel on MgrLearn.getJob().
   */
  public CommandArtifact doCancel(Context cx)
    throws Exception
  {
    if (manager.getLearn() != null)
    {
      BJob job = manager.getLearn().getJob();
      if (job != null)
      {
        job.cancel();
      }
    }
    return null;
  }

////////////////////////////////////////////////////////////////
// New
////////////////////////////////////////////////////////////////

  class New extends MgrCommand
  {
    New(BWidget owner) { super(owner, lex, "new"); }
    @Override
    public CommandArtifact doInvoke() throws Exception
    {
      manager.setMgrEditState(MgrEdit.NEW);
      CommandArtifact artifact =doNew(null);
      manager.setMgrEditState(MgrEdit.DEFAULT);
      return artifact;
    }
    
    @Override
    public Command doMerge(Command c) { return this; }
  }

  /**
   * This is the callback when the new command is invoked.
   */
  public CommandArtifact doNew(Context cx)
    throws Exception
  {
    // prompt user to pick a type and count
    MgrEdit edit = promptForNew(cx);
    if (edit == null)
    {
      return null;
    }

    // let user edit list
    return edit.invoke(null);
  }

  /**
   * Prompt the user with the list of new types returned by
   * BAbstractManager.getNewTypes() and return a MgrEdit
   * with the default instances.
   */
  public MgrEdit promptForNew(Context cx)
    throws Exception
  {
    MgrModel model = manager.getModel();

    MgrTypeInfo[] types = model.getNewTypes();
    if (types == null)
    {
      BDialog.error(manager, "Must override MgrModel.getNewTypes()");
      return null;
    }

    // Flag any duplicate entries
    MgrTypeInfo.markDuplicates(types);
    
    // build input fields
    BListDropDown typeField = new BListDropDown();
    for(int i=0; i<types.length; ++i)
    {
      typeField.getList().addItem(types[i].getIcon(), types[i]);
    }
    typeField.setSelectedIndex(0);
    BIntegerFE countField = new BIntegerFE();
    countField.loadValue(BInteger.make(1), BFacets.make(
      BFacets.MIN, BInteger.make(1),
      BFacets.MAX, BInteger.make(model.getMaxNewInstances()),
      BFacets.FIELD_WIDTH, BInteger.make(3)
    ));

    // build input pane
    BGridPane grid = new BGridPane(2);
    grid.add(null, new BLabel(lex.getText("add.type")));
    grid.add(null, typeField);
    grid.add(null, new BLabel(lex.getText("add.count")));
    grid.add(null, countField);

    // prompt
    String title = newCommand.getLabel();
    int r = BDialog.open(manager, title, grid, BDialog.OK_CANCEL);
    if (r == BDialog.CANCEL)
    {
      return null;
    }

    // extract input
    MgrTypeInfo type = (MgrTypeInfo)typeField.getSelectedItem();
    int count = ((BInteger) countField.saveValue()).getInt();

    // create edit list
    MgrEdit edit = makeEdit(title);
    for(int i=0; i<count; ++i)
    {
      edit.addRow(edit.makeRow(model.newInstance(type), null, types));
    }
    return edit;
  }

////////////////////////////////////////////////////////////////
// Edit
////////////////////////////////////////////////////////////////

  class Edit extends MgrCommand
  {
    Edit(BWidget owner) { super(owner, lex, "edit"); }
    @Override
    public CommandArtifact doInvoke() throws Exception
    { 
      manager.setMgrEditState(MgrEdit.EDIT);
      CommandArtifact artifact =doEdit(manager.getCurrentContext());
      manager.setMgrEditState(MgrEdit.DEFAULT);
      return artifact;
    }
    @Override
    public Command doMerge(Command c) { return this; }
  }

  /**
   * This is the callback when the edit command is invoked.  The
   * default implementation prompts the user with a MgrEdit of
   * all the selected rows in the database table.
   */
  public CommandArtifact doEdit(Context cx)
    throws Exception
  {
    // get selected components
    BComponent[] sel = getSelectedComponents();
    if (sel.length == 0)
    {
      return null;
    }

    // create edit list
    MgrEdit edit = makeEdit(this.edit.getLabel());
    for(int i=0; i<sel.length; ++i)
    {
      edit.addRow(sel[i]);
    }

    // prompt user with edit dialog
    edit.setSelectAll(true);
    return edit.invoke(cx);
  }

////////////////////////////////////////////////////////////////
// Add
////////////////////////////////////////////////////////////////

  class Add extends MgrCommand
  {
    Add(BWidget owner) { super(owner, lex, "add"); }
    @Override
    public CommandArtifact doInvoke() throws Exception
    { 
      manager.setMgrEditState(MgrEdit.ADD);
      Context cx = null;

      // If add as initiated with template selected use quickContext to bypass first edit dialog -
      // add will do a addInstance with a match that will generate edit dialog
      if(getManager().isTemplatable() && getManager().getMgrTemplate().getTable().getSelectedObjects().length == 1)
      {
        cx = quickContext;
      }

      CommandArtifact artifact = doAdd(cx);
      manager.setMgrEditState(MgrEdit.DEFAULT);
      return artifact;
    }
    
    @Override
    public Command doMerge(Command c) { return this; }
  }


  class QuickAdd extends MgrCommand
  {
    QuickAdd(BWidget owner) { super(owner, lex, "quickAdd"); }
    @Override
    public CommandArtifact doInvoke() throws Exception
    {
      manager.setMgrEditState(MgrEdit.ADD);
      CommandArtifact artifact = doAdd(quickContext);
      manager.setMgrEditState(MgrEdit.DEFAULT);
      return artifact;
    }

    @Override
    public Command doMerge(Command c) { return this; }
  }

  /**
   * This is the callback when the add command is invoked.
   * This routes to doAdd(Object[] sel, cx).
   */
  public CommandArtifact doAdd(Context cx)
    throws Exception
  {
    if (manager.getLearn() != null && (getLearnTable().getSelectedObjects().length > 0))
    {
      Object[] dis = getLearnTable().getSelectedObjects();
      if (dis.length > 0)
      {
        return doAdd(dis, cx);
      }
    }
    if(manager.getMgrTemplate() != null)
    {
      Object[] templates = manager.template.table.getSelectedObjects();

      if(templates.length > 0)
      {
        return doAddTemplates(templates, cx);
      }
    }
    return null;
  }

  /**
   * This is called from doAdd() to add the discovered objects
   * to the station database.  The default implementation method
   * calls {@code BAbstractManager.discoveryToTypes()} and
   * {@code BAbstractManager.discoveryToRow} and then prompts
   * the user with MgrEdit dialog.
   */
  protected CommandArtifact doAdd(Object[] discovery, Context cx)
      throws Exception
  {
    MgrEdit edit = makeEdit(add.getLabel());
    for(int i=0; i<discovery.length; ++i)
    {
      Object dis = discovery[i];
      MgrTypeInfo[] types = manager.getLearn().toTypes(dis);
      if (types == null || types.length == 0)
      {
        continue;
      }
      MgrEditRow row = edit.addRow(dis, types);
      manager.getLearn().toRow(dis, row);
    }
    return edit.invoke(cx);
  }

  protected CommandArtifact doAddTemplates(Object[] templates, Context cx)
    throws Exception
  {

    MgrEdit edit = makeEdit(add.getLabel());
    for(int i=0; i<templates.length; ++i)
    {
      Object template = templates[i];
      MgrTypeInfo[] types = manager.getMgrTemplate().toTypes(template);
      if (types == null || types.length == 0)
      {
        continue;
      }
      MgrEditRow row = edit.addRow(template, types);
      manager.getMgrTemplate().toRow(template, row);
    }
    try{return edit.invoke(cx);}
    catch(Exception e){ return null; }
  }

////////////////////////////////////////////////////////////////
// Match
////////////////////////////////////////////////////////////////

  class Match extends MgrCommand
  {
    Match(BWidget owner) { super(owner, lex, "match"); }
    @Override
    public CommandArtifact doInvoke() throws Exception
    {
      manager.setMgrEditState(MgrEdit.MATCH);
      CommandArtifact artifact = doMatch(null);
      manager.setMgrEditState(MgrEdit.DEFAULT);
      return artifact;
    }
    
    @Override
    public Command doMerge(Command c) { return this; }
  }

  class QuickMatch extends MgrCommand
  {
    QuickMatch(BWidget owner) { super(owner, lex, "quickMatch"); }
    @Override
    public CommandArtifact doInvoke() throws Exception
    { 
      manager.setMgrEditState(MgrEdit.MATCH);
      CommandArtifact artifact = doMatch(quickContext); 
      manager.setMgrEditState(MgrEdit.DEFAULT);
      return artifact;
    }
    
    @Override
    public Command doMerge(Command c) { return this; }
  }

  /**
   * This is the callback when the match command is invoked, it
   * routes to {@code doMatch(Object, BComponent, Context)}.
   */
  public CommandArtifact doMatch(Context cx)
          throws Exception
  {
    // get learn
    if (!manager.isLearnable())
    {
      return null;
    }

    // get the selected discovery row
    Object dis = getLearnTable().getSelectedObject();
    if (dis == null)
    {
      return null;
    }

    // get the selected table row
    BComponent db = getTable().getSelectedComponent();
    if (db == null)
    {
      return null;
    }

    return doMatch(dis, db, cx);
  }

  /**
   * Perform the match between the discovered object and
   * the existing database object.  The default implementation
   * calls {@code BAbstractManager.discoveryToRow()} and
   * then prompts the user with MgrEdit dialog.
   */
  public CommandArtifact doMatch(Object discovery, BComponent database, Context cx)
          throws Exception
  {
    MgrEdit edit = makeEdit(match.getLabel());
    MgrEditRow row = edit.addRow(database);
    manager.getLearn().toRow(discovery, row);
    return edit.invoke(cx);
  }


////////////////////////////////////////////////////////////////
// TagIt
////////////////////////////////////////////////////////////////

  class TagIt extends MgrCommand
  {
    TagIt(BWidget owner) { super(owner, lex, "tagIt"); }
    @Override
    public CommandArtifact doInvoke() throws Exception
    {
      manager.setMgrEditState(MgrEdit.EDIT);
      CommandArtifact artifact = doTagIt(null);
      manager.setMgrEditState(MgrEdit.DEFAULT);
      return artifact;
    }
    @Override
    public Command doMerge(Command c) { return this; }
  }

  /**
   * This is the callback when the TagIt command is invoked, it
   * routes to {@code MgrTagDictionary.doTagIt()}.
   */
  public CommandArtifact doTagIt(Context cx)
    throws Exception
  {
    if (manager.isTaggable())
    {
      return manager.getMgrTagDictionary().doTagIt(cx);
    }
    else
    {
      return null;
    }
  }

////////////////////////////////////////////////////////////////
// Deploy
////////////////////////////////////////////////////////////////
  class Deploy extends MgrCommand
  {
    Deploy(BWidget owner) { super(owner, lex, "deploy"); }
    @Override
    public CommandArtifact doInvoke() throws Exception { return doDeploy(null); }
    @Override
    public Command doMerge(Command c) { return this; }
  }

  /**
   * This is the callback when the Deploy command is invoked, it
   * routes to {@code doDeploy(Object, Context)}.
   */
  public CommandArtifact doDeploy(Context cx)
    throws Exception
  {
    // get learn
    if (!manager.isTemplatable())
    {
      return null;
    }

    // get the selected template row
    Object template = getDevTemplateTable().getSelectedObject();
    if (template == null)
    {
      if(cx instanceof TransferContext)
      {
        Mark mark = (Mark)((TransferContext)cx).getEnvelope().getData(TransferFormat.mark);
        return doDeploy(mark.getValue(0), cx);
      }

      return null;
    }

    return doDeploy(template, cx);
  }

  /**
   * Called when a drag from templateTable to MgrTable is performed.  Subclasses should override.
   */
  protected CommandArtifact doDeploy(Object discovery, Context cx)
    throws Exception
  {
    return null;
  }


  /**
   * Handle a header menu on the device template table. Default implementation does nothing,
   * DeviceController implementation enables bulk device template deployment.
   */
  public void headerPopup(BTemplateTable table, BMouseEvent event, int column)
  {
    return;
  }


////////////////////////////////////////////////////////////////
// ShowExisting
////////////////////////////////////////////////////////////////

  class ShowExisting extends MgrCommand
  {
    ShowExisting(BWidget owner, BComponent existing)
    {
      super(owner, lex, "showExisting");
      this.existing = existing;
      setEnabled(existing != null);
    }

    @Override
    public CommandArtifact doInvoke()
      throws Exception
    {
      BDialog.message(getOwner(), getLabel(), existing.toDisplayPathString(null));
      return null;
    }

    BComponent existing;
  }

////////////////////////////////////////////////////////////////
// SelectAll
////////////////////////////////////////////////////////////////

  class SelectAll extends MgrCommand
  {
    SelectAll(BLearnTable owner)
    {
      super(owner, UiLexicon.bajaui(), "commands.selectAll");
    }

    @Override
    public CommandArtifact doInvoke()
      throws Exception
    {
      ((BLearnTable)getOwner()).getSelection().selectAll();
      return null;
    }
  }

////////////////////////////////////////////////////////////////
// MgrCommand
////////////////////////////////////////////////////////////////

  /**
   * IMgrCommand is the common interface for
   * MgrCommand and MgrToggleCommand.
   */
  public static interface IMgrCommand
  {
    /**
     * Get the unique command name.  This string
     * must match to a valid slot name.
     */
    public String getName();

    /**
     * Get the flags which defines a bitmask of where
     * this command is used (MENU, TOOL_BAR, etc).
     */
    public int getFlags();

    /**
     * Set the flags which defines a bitmask of where
     * this command is used (MENU, TOOL_BAR, etc).
     */
    public void setFlags(int flags);
  }

////////////////////////////////////////////////////////////////
// MgrCommand
////////////////////////////////////////////////////////////////

  /**
   * MgrCommand extends Command to provide some
   * additional meta-data used by MgrController.
   */
  public static class MgrCommand
    extends Command
    implements IMgrCommand
  {
    /**
     * See {@code Command(BWidget, Lexicon, String)}
     */
    public MgrCommand(BWidget owner, Lexicon lexicon, String keyBase)
    {
      super(owner, lexicon, keyBase);
      this.name = SlotPath.escape(keyBase);
    }

    /**
     * See {@code Command(BWidget, String)}
     */
    public MgrCommand(BWidget owner, String label)
    {
      super(owner, label);
      this.name = SlotPath.escape(label);
    }

    @Override
    public String getName() { return name; }
    @Override
    public int getFlags() { return flags; }
    @Override
    public void setFlags(int flags) { this.flags = flags; }

    private String name;
    private int flags;
  }

////////////////////////////////////////////////////////////////
// MgrToggleCommand
////////////////////////////////////////////////////////////////

  /**
   * MgrToggleController  extends ToggleCommand to provide some
   * additional meta-data used by MgrController.
   */
  public static class MgrToggleCommand
    extends ToggleCommand
    implements IMgrCommand
  {
    /**
     * See {@code ToggleCommand(BWidget, Lexicon, String)}
     */
    public MgrToggleCommand(BWidget owner, Lexicon lexicon, String keyBase)
    {
      super(owner, lexicon, keyBase);
      this.name = SlotPath.escape(keyBase);
    }

    /**
     * See {@code ToggleCommand(BWidget, String)}
     */
    public MgrToggleCommand(BWidget owner, String label)
    {
      super(owner, label);
      this.name = SlotPath.escape(label);
    }

    @Override
    public String getName() { return name; }
    @Override
    public int getFlags() { return flags; }
    @Override
    public void setFlags(int flags) { this.flags = flags; }

    private String name;
    private int flags;
  }

////////////////////////////////////////////////////////////////
// MgrCommand Flags
////////////////////////////////////////////////////////////////

  /** Makes the command be available in the main menu */
  public static final int MENU_BAR = 0x0001;

  /** Makes the command be available in the main toolbar */
  public static final int TOOL_BAR = 0x0002;

  /** Makes the command be available in the action bar */
  public static final int ACTION_BAR = 0x0004;

  /** Makes the command be available in the table popup menu */
  public static final int POPUP = 0x0008;

  /** Makes the command be available in the learn table popup menu */
  public static final int LEARN_POPUP = 0x0010;

  /** Convenience for {@code MENU_BAR | TOOL_BAR | ACTION_BAR} */
  public static final int BARS = MENU_BAR | TOOL_BAR | ACTION_BAR;

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final int[] emptySelection = new int[0];
  static final BComponent[] emptyComponentTable = new BComponent[0];
  public static final Context quickContext = new BasicContext();
  final Lexicon lex = Lexicon.make(MgrController.class);

  static boolean quickCommandsEnabled = true;

  IMgrCommand[] commands;
  IMgrCommand[] baseCommands;
  IMgrCommand[] tagCommands;
  IMgrCommand[] templateCommands;
  public final MgrCommand newCommand;
  public final MgrCommand edit;
  public final MgrToggleCommand learnMode;
  public final MgrToggleCommand tagMode;
  public final MgrToggleCommand templateMode;
  public final MgrCommand discover;
  public final MgrCommand cancel;
  public final MgrCommand add;
  public final MgrCommand match;
  public final MgrCommand quickAdd;
  public final MgrCommand quickMatch;
  public final MgrCommand tagIt;
  public final MgrCommand deploy;

}

