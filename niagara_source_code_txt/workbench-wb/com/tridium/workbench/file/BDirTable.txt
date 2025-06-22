/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.file;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.baja.file.BDirectory;
import javax.baja.file.BFileSpace;
import javax.baja.file.BIDirectory;
import javax.baja.file.BIFile;
import javax.baja.gx.BBrush;
import javax.baja.gx.BColor;
import javax.baja.gx.BImage;
import javax.baja.naming.BOrd;
import javax.baja.nav.BNavRoot;
import javax.baja.nav.NavEvent;
import javax.baja.nav.NavListener;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SortUtil;
import javax.baja.space.Mark;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BActionMenuItem;
import javax.baja.ui.BMenu;
import javax.baja.ui.BSeparator;
import javax.baja.ui.BSubMenuItem;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.HyperlinkInfo;
import javax.baja.ui.Subject;
import javax.baja.ui.commands.CopyCommand;
import javax.baja.ui.commands.CutCommand;
import javax.baja.ui.commands.DeleteCommand;
import javax.baja.ui.commands.DuplicateCommand;
import javax.baja.ui.commands.PasteCommand;
import javax.baja.ui.commands.RenameCommand;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.event.BWidgetEvent;
import javax.baja.ui.file.BFileChooser;
import javax.baja.ui.table.BTable;
import javax.baja.ui.table.DynamicTableModel;
import javax.baja.ui.table.TableCellRenderer;
import javax.baja.ui.table.TableController;
import javax.baja.ui.table.TableHeaderRenderer;
import javax.baja.ui.table.TableModel;
import javax.baja.ui.table.TableSelection;
import javax.baja.ui.table.TableSubject;
import javax.baja.ui.transfer.BTransferWidget;
import javax.baja.ui.transfer.SimpleDragRenderer;
import javax.baja.ui.transfer.TransferContext;
import javax.baja.ui.transfer.TransferEnvelope;
import javax.baja.ui.util.UiLexicon;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.nav.menu.NavMenuUtil;

import com.tridium.ui.theme.Theme;
import com.tridium.workbench.nav.BFileMenuAgent;
import com.tridium.workbench.shell.BNiagaraWbShell;
import com.tridium.workbench.transfer.TransferUtil;

/**
 * BDirTable displays the files within a directory;
 *
 * @author    Brian Frank       
 * @creation  17 Apr 03
 * @version   $Revision: 30$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BDirTable
  extends BTable
  implements NavListener
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.file.BDirTable(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDirTable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor
   */
  public BDirTable()
  {
    setModel(new DynamicTableModel(new Model()));
    setSelection(new Selection());
    setController(new Controller());
    setHeaderRenderer(new HeaderRenderer());
    setCellRenderer(new CellRenderer());    
  }

////////////////////////////////////////////////////////////////
// Load
////////////////////////////////////////////////////////////////  

  /**
   * Load
   */
  public void load(BIDirectory dir, Context context)
  {
    boolean first = this.dir == null;
    

    this.enableEdits = false;
    if (dir instanceof BDirectory)
      enableEdits = !((BDirectory)dir).isReadonly();

    // set up the rows
    BIFile[] files = dir.listFiles();
    Row[] rows = new Row[files.length];
    for(int i=0; i<rows.length; ++i)
      rows[i] = new Row(files[i], context);
    
    this.dir = dir;                  
    this.context = context;
    this.isSpace = (dir instanceof BFileSpace);
    this.dirOrd = dir.getNavOrd();
    this.rows = rows;
    this.origRowOrder = rows.clone();
        
    if (first) sortByColumn(COL_NAME, true);
    
    //Issue 15268, this will cause relayout() to be called with sizeColumns=true
    sizeColumnsToFit();
    fireTableModified(new BWidgetEvent(BWidgetEvent.MODIFIED, this));
    updateTransferCommands();
  }
  
  /**
   * Refresh updates the rows, but doesn't alter any 
   * ordering by putting any new rows at the bottom (we
   * also select those rows).
   */
  public void refresh()
  {
    BIFile[] files = dir.listFiles();
    Row[] newRows = new Row[files.length];
    Row[] oldRows = this.rows;
    int n = 0;
    
    // first put files we have a match for
    for(int i=0; i<files.length; ++i)
    {
      String filename = files[i].getFileName();
      for(int j=0; j<oldRows.length; ++j)
      {
        if (filename.equals(oldRows[j].file.getFileName()))
        {
          files[i] = null;
          newRows[n++] = oldRows[j];
          break;
        }
      }
    }
    
    // now any files in files array not null must 
    // be added to let's add them
    int selectStart = n;
    for(int i=0; i<files.length; ++i)
    {
      if (files[i] != null)
        newRows[n++] = new Row(files[i], context);
    }
    
    this.rows = newRows;
    
    // update selection
    getSelection().deselectAll();
    if (selectStart < newRows.length)
      getSelection().select(selectStart, rows.length-1);
      
    //Issue 15268, this will cause relayout() to be called with sizeColumns=true
    sizeColumnsToFit(); 
    fireTableModified(new BWidgetEvent(BWidgetEvent.MODIFIED, this));
  }
  
////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////  

  public void started()
    throws Exception
  {
    super.started();
    BNavRoot.INSTANCE.addNavListener(this);
  }

  public void stopped()
    throws Exception
  {
    super.stopped();
    BNavRoot.INSTANCE.removeNavListener(this);
  }
  
  public void navEvent(NavEvent event)
  {
    if (dirOrd != null && dirOrd.equals(event.getParentOrd()))
      refresh();
  }
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the shell as a WbShell
   */
  public BWbShell getWbShell()
  {
    Object shell = getShell();
    if (shell instanceof BWbShell) return (BWbShell)shell;
    return null;
  }

  /**
   * Get the selected row's BIFile or null if 
   * no selection.
   */
  public BIFile getSelectedFile()
  {
    int sel= getSelection().getRow();
    if (sel == -1) return null;
    return rows[sel].file;
  }

  /**
   * Get the selected rows' files or return an empty 
   * array if no selection.
   */
  public BIFile[] getSelectedFiles()
  {
    int[] sel = getSelection().getRows();
    BIFile[] files = new BIFile[sel.length];
    for(int i=0; i<sel.length; ++i)
      files[i] = rows[sel[i]].file;
    return files;
  }

  /**
   * Get the selected components are a transfer Mark
   * or if nothing is selected return null.
   */
  public Mark getSelectedFilesAsMark()
  {
    if (dir == null) return null;
    
    BIFile[] f = getSelectedFiles();
    if (f.length == 0) return null;

    String[] names = new String[f.length];
    BObject[] objects = new BObject[f.length];
    for(int i=0; i<names.length; ++i)
    {
      names[i] = f[i].getFileName();
      objects[i] = (BObject)f[i];
    }
    return new Mark(objects, names);
  }
  
////////////////////////////////////////////////////////////////
// Transfer
////////////////////////////////////////////////////////////////

  public void updateTransferCommands()
  {                                     
    BIFile[] files = getSelectedFiles();
    int count = files.length;   
    boolean filesEditable = true;
    for(int i=0; i<count; ++i)
      if (files[i].isReadonly()) { filesEditable = false; break; }
    
    setCutEnabled(count > 0 && filesEditable && enableEdits);
    setCopyEnabled(count > 0);
    setPasteEnabled(enableEdits);
    setDuplicateEnabled(count > 0 && enableEdits);
    setDeleteEnabled(count > 0 && filesEditable && enableEdits);
    setRenameEnabled(count > 0 && filesEditable && enableEdits);
  }

  public TransferEnvelope getTransferData() 
    throws Exception 
  { 
    if (copyFrom != null)
    {
      String[] names = new String[] { copyFrom.getFileName() };
      BObject[] objects = new BObject[] { (BObject)copyFrom };
      return TransferEnvelope.make(new Mark(objects, names));
    }  
    Mark mark = getSelectedFilesAsMark();
    if (mark == null) return null;
    return TransferEnvelope.make(mark);
  }
    
  public CommandArtifact insertTransferData(TransferContext cx) 
    throws Exception
  { 
    return TransferUtil.insert(this, cx, (BObject)dir, null);
  }

  public CommandArtifact removeTransferData(TransferContext cx)
    throws Exception
  { 
    return null;
  }

  public CommandArtifact doDelete()
    throws Exception
  {    
    Mark mark = getSelectedFilesAsMark();
    if (mark == null) return null;
    return TransferUtil.delete(this, mark);
  }

  public CommandArtifact doRename()
    throws Exception
  {    
    Mark mark = getSelectedFilesAsMark();
    if (mark == null) return null;
    return TransferUtil.rename(this, mark);
  }

////////////////////////////////////////////////////////////////
// Drag and Drop
////////////////////////////////////////////////////////////////

  /**
   * If rows are selected, then call startDrag with 
   * a MarkTransferable.
   */
  public void mouseDragStarted(BMouseEvent event)
  {         
    // get selection                       
    int[] rows = getSelection().getRows();                
    if (rows.length == 0) return;

    // if click not in selection, then don't start drag
    int row = getRowAt(event.getY());
    boolean found = false;
    for(int i=0; i<rows.length; ++i)
      if (row == rows[i]) { found = true; break; }
    if (!found) return;    

    // start drag
    Mark mark = getSelectedFilesAsMark();
    SimpleDragRenderer dragRenderer = new SimpleDragRenderer(mark);
    dragRenderer.font = Theme.table().getCellFont();
    startDrag(event, TransferEnvelope.make(mark), dragRenderer);
  }
  
  /**
   * The entire table is a a drop into the container.
   */
  public void dragEnter(TransferContext cx)
  {
    if (!enableEdits) return;
    dropActive = true;
    repaint();
  }
  
  /**
   * The entire table is a a drop into the container.
   */
  public int dragOver(TransferContext cx)
  {
    if (!enableEdits) return 0;
    int mask = ACTION_COPY | ACTION_MOVE;
    return mask;
  }

  /**
   * Drag exit.
   */
  public void dragExit(TransferContext cx)
  {
    dropActive = false;
    repaint();
  }
  
  /**
   * Handle a drop using insertDynamicProperties().
   */
  public CommandArtifact drop(TransferContext cx)
    throws Exception
  {
    dropActive = false;
    repaint();
    if (!enableEdits) return null;
    return insertTransferData(cx);      
  }

////////////////////////////////////////////////////////////////
// Selection
////////////////////////////////////////////////////////////////

  class Selection extends TableSelection
  {                                     
    public void updateTable()
    {
      super.updateTable();
      updateTransferCommands();
    }
  }

////////////////////////////////////////////////////////////////
// HeaderRenderer
////////////////////////////////////////////////////////////////

  class HeaderRenderer extends TableHeaderRenderer
  {
    public BBrush getForeground(Header header)
    {
      if (dropActive)
        return Theme.widget().getDropOkForeground();
      else
        return super.getForeground(header);
    }
  
    public BBrush getBackground(Header header)
    {
      if (dropActive)
        return Theme.widget().getDropOkBackground();
      else
        return super.getBackground(header);
    }
  }

////////////////////////////////////////////////////////////////
// CellRenderer
////////////////////////////////////////////////////////////////

  class CellRenderer extends TableCellRenderer
  {
    public BBrush getForeground(Cell cell)
    {
      try
      {
        BIFile file = rows[cell.row].file;
        if (file.isPendingMove()) return cutFg;
      }
      catch(IndexOutOfBoundsException e)
      {
      }
      return super.getForeground(cell);
    }
  }

////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////

  class Controller extends TableController
  {  
    /**
     * if there is only one row selected, hyperlink to it
     */
    protected void handleEnter(BKeyEvent event)
    {
      event.consume();

      int[] selRows = this.getSelection().getRows();
      if (selRows.length == 1) 
        hyperlink(rows[selRows[0]].file.getNavOrd(), null);
    }
  
    protected void cellDoubleClicked(BMouseEvent event, int row, int column)
    {
      hyperlink(rows[row].file.getNavOrd(), event);
    }

    public int getTextSearchColumn()
    {
      return 0;
    }

    private void hyperlink(BOrd ord, BMouseEvent event)
    {
      BWbShell shell = getWbShell();
      if (ord != null && shell != null)
      {                   
        HyperlinkInfo info;     
        if (event == null) 
          info = new HyperlinkInfo(ord);
        else
          info = new HyperlinkInfo(ord, event);
        shell.hyperlink(info);
      }
    }
    
    protected BMenu makePopup(TableSubject subject)
    {   
      return makeDirPopup(BDirTable.this, dir, subject, enableEdits);
    }
    
    /*
      // if multiple selection, then only return merged menu
      if (subject.size() > 1)
        return NavMenuUtil.makeMenu(getTable(), subject);
      
      BTable table = getTable();
      BMenu menu = new BMenu();
      BIFile active = (BIFile)subject.getActive();
      
      // if over a row, then provide a view menu
      if (active != null)
      {
        BMenu views = NavMenuUtil.makeViewsMenu(table, (BObject)active, active.getAbsoluteOrd());
        menu.add("views", new BSubMenuItem(views));
      }
      
      if (!enableEdits)
      {
        menu.add("copy", new CopyCommand(table));
      }
      else
      {
        BDirectory d = (BDirectory)dir;
        
        BMenu newMenu = BFileMenuAgent.makeNewMenu(table, d);
        if (newMenu != null)
        {
          menu.add("new", new BSubMenuItem(newMenu));
          menu.add("sep0", new BSeparator());
        }
        
        menu.add("copy", new CopyCommand(table));
        menu.add("cut", new CutCommand(table));
        menu.add("paste", new PasteCommand(table));
        menu.add("duplicate", new DuplicateCommand(table));
        menu.add("copyFrom", new CopyFromCommand(table));
        menu.add("sep1", new BSeparator());
        menu.add("delete", new DeleteCommand(table));
        menu.add("rename", new RenameCommand(table));
      }       
      
      return menu;      
    }        
    */
  }                  
  
  public static BMenu makeDirPopup(BTransferWidget owner, BIDirectory dir, Subject subject, boolean enableEdits)
  {
    BMenu menu = NavMenuUtil.makeMenu(owner, subject);

    // if multiple selection, then only return merged menu
    if (subject.size() > 1)
    {
      return menu;
    }

    BDirectory d = (BDirectory) dir;

    BMenu newMenu = null;
    Command copyFromCommand = null;

    if (enableEdits)
    {
      newMenu = BFileMenuAgent.makeNewMenu(owner, d);

      if (owner instanceof BDirTable)
      {
        copyFromCommand = ((BDirTable) owner).makeCopyFromCommand();
      }
    }

    if (menu == null)
    {
      menu = new BMenu();

      BIFile active = (BIFile)subject.getActive();

      // if over a row, then provide a view menu
      if (active != null)
      {
        BMenu views = NavMenuUtil.makeViewsMenu(owner, (BObject)active, active.getAbsoluteOrd());
        menu.add("views", new BSubMenuItem(views));
      }

      if (!enableEdits)
      {
        menu.add("copy", new CopyCommand(owner));
      }
      else
      {
        if (newMenu != null)
        {
          menu.add("new", new BSubMenuItem(newMenu));
          menu.add("sep0", new BSeparator());
        }

        menu.add("copy", new CopyCommand(owner));
        menu.add("cut", new CutCommand(owner));
        menu.add("paste", new PasteCommand(owner));
        menu.add("duplicate", new DuplicateCommand(owner));
        if (copyFromCommand != null)
        {
          menu.add("copyFrom", copyFromCommand);
        }
        menu.add("sep1", new BSeparator());
        menu.add("delete", new DeleteCommand(owner));
        menu.add("rename", new RenameCommand(owner));
      }
    }
    else
    {
      // The nav menu for this item is being used. We need to add any "New" menu or "Copy From"
      // menu item for the containing directory.
      supplementPopupMenu(menu, newMenu, copyFromCommand);
    }

    return menu;      
  }

  public static void supplementPopupMenu(BMenu menu, BMenu newMenu, Command copyFromCommand)
  {
    Property newMenuProperty = null;
    Property copyFromProperty = null;
    String newMenuTitle = null;

    if (newMenu != null)
    {
      // This will place it at the bottom of the list, we'll move it in a sec.
      newMenuProperty = menu.add("new", new BSubMenuItem(newMenu));
      newMenuTitle = newMenu.getText();
    }

    if (copyFromCommand != null)
    {
      // This will place it at the bottom of the list, we'll move it in a sec.
      copyFromProperty = menu.add("copyFrom",  new BActionMenuItem(copyFromCommand));
    }

    // Try to move the "New" menu near the top, but after "View".
    // Try to move the "Copy From" menu item after the "Duplicate" menu item.
    Property[] originalMenuItems = menu.getDynamicPropertiesArray();
    ArrayList<Property> reorderedMenuItems = new ArrayList<Property>();
    for (int i = 0, menuCount = originalMenuItems.length; reorderedMenuItems.size() < menuCount; i++)
    {
      Property menuProperty = originalMenuItems[i];

      if (newMenuProperty != null &&
        (reorderedMenuItems.size() == (menuCount - 1) || !(menu.get(menuProperty) instanceof BSubMenuItem)))
      {
        // This is the first item that is not a sub-menu. Put the "New" sub-menu first.
        reorderedMenuItems.add(newMenuProperty);
        newMenuProperty = null;
      }

      if (reorderedMenuItems.size() < menuCount)
      {
        boolean redundantMenuProperty = false;
        if (menuProperty != newMenuProperty && newMenuTitle != null)
        {
          // NCCB-45504 - Make sure this isn't an alternate "new" menu. We only want the one that we've created for
          // the parent directory.
          BValue menuItem = menu.get(menuProperty);
          if (menuItem instanceof BSubMenuItem)
          {
            redundantMenuProperty = newMenuTitle.equals(((BSubMenuItem) menuItem).getMenu().getText());
          }
        }

        if (redundantMenuProperty)
        {
          menu.remove(menuProperty);
          menuCount--;
        }
        else
        {
          // Copy over the next menu item.
          reorderedMenuItems.add(menuProperty);
          if (menuProperty == newMenuProperty)
          {
            newMenuProperty = null;
          }
          if (menuProperty == copyFromProperty)
          {
            copyFromProperty = null;
          }
        }
      }

      if (reorderedMenuItems.size() < menuCount && copyFromProperty != null &&
        ("duplicate".equals(menuProperty.getName()) || reorderedMenuItems.size() == (menuCount - 1)))
      {
        // We just copied the "duplicate" menu item (or we're at the end). Add "copyFrom" here.
        reorderedMenuItems.add(copyFromProperty);
        copyFromProperty = null;
      }
    }

    menu.reorder(reorderedMenuItems.toArray(new Property[0]));
  }

////////////////////////////////////////////////////////////////
// Commands
////////////////////////////////////////////////////////////////

  Command makeCopyFromCommand() { return new CopyFromCommand(this); }
  
  class CopyFromCommand extends Command
  {
    public CopyFromCommand(BTransferWidget owner)
    {
      super(owner, UiLexicon.bajaui().module, "commands.copyFrom");
    }
    
    public CommandArtifact doInvoke()
      throws Exception
    {
      BTransferWidget owner = (BTransferWidget)getOwner();
      BNiagaraWbShell shell = (BNiagaraWbShell)getWbShell();
      if (shell.fileChooser == null)
        shell.fileChooser = BFileChooser.makeOpen(shell);
      
      BOrd ord = shell.fileChooser.show();
      try
      {
        if (ord != null)
        {
          copyFrom = (BIFile)ord.get();
          setCopyEnabled(true);
          owner.doCopy();
          owner.doPaste();
        }
      }
      finally 
      { 
        copyFrom = null; 
        updateTransferCommands();
      }
      return null;
    }
  }  

////////////////////////////////////////////////////////////////
// Model
////////////////////////////////////////////////////////////////

  class Model extends TableModel
  {
    public int getRowCount() { return rows.length; }
  
    public int getColumnCount() { return 4; }
    
    public String getColumnName(int col) 
    { 
      switch(col)
      {
        case COL_NAME:     return LEX_NAME;
        case COL_TYPE:     return LEX_TYPE;
        case COL_SIZE:     return LEX_SIZE;
        case COL_MODIFIED: return LEX_MODIFIED;
        default: return "?";
      }
    }
    
    public BHalign getColumnAlignment(int col)
    {
      if (col == COL_SIZE) return BHalign.right;
      else return BHalign.left;
    }

    public Object getSubject(int row) 
    {
      return rows[row].file;
    }
  
    public Object getValueAt(int row, int col) 
    { 
      switch(col)
      {
        case COL_NAME:     return rows[row].name;
        case COL_TYPE:     return rows[row].type;
        case COL_SIZE:     return rows[row].sizeString;
        case COL_MODIFIED: return rows[row].modifiedString;
        default: return "?";
      }
    }

    public boolean isColumnSortable(int col) { return true; }
    
    public void sortByColumn(int col, boolean ascending)
    {
      // sort file spaces themselves by the 
      // order they are listed
      if (col == COL_NAME && dir instanceof BFileSpace)
      {
        rows = origRowOrder.clone();
      }
      else
      {
        Object[] keys = new Object[rows.length];
        for(int i=0; i<keys.length; ++i) keys[i] = rows[i].getSortKey(col);
        SortUtil.sort(keys, rows, ascending);
      }
    }
  
    public BImage getRowIcon(int row) { return rows[row].getIcon(); }
  }

////////////////////////////////////////////////////////////////
// Row
////////////////////////////////////////////////////////////////  

  static class Row
  {
    Row(BIFile file, Context context)
    {
      try
      {
        this.file           = file;
        this.icon           = icon(file.getNavIcon());
        this.name           = file.getNavDisplayName(null);
        this.type           = file.asObject().getType().getTypeName();
        this.size           = file.getSize();
        this.sizeString     = size(size);
        this.modified       = file.getLastModified();
        this.modifiedString = time(modified, context);
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }

    public BImage getIcon()
    {
      if (file.isPendingMove())
        return icon.getDisabledImage();
      else 
        return icon;
    }
    
    public Object getSortKey(int col)
    {
      switch(col)
      {
        case COL_NAME:     return new NameKey(this);
        case COL_TYPE:     return type;
        case COL_SIZE:     return Long.valueOf(size);
        case COL_MODIFIED: return modified == null ? BAbsTime.NULL : modified;
        default: throw new IllegalStateException();
      }
    }
    
    BIFile file;
    BImage icon;
    String name;
    String type;
    String sizeString;
    String modifiedString;
    long size;
    BAbsTime modified;
  }
  
  /**
   * NameKey takes into account directories and case insensitive names.
   */
  static class NameKey implements Comparable<NameKey>
  {
    NameKey(Row row) { this.row = row; }

    @Override
    public int compareTo(NameKey obj)
    {
      Row a = row;
      Row b = obj.row;
      if (a.file.isDirectory())
      {
        if (!b.file.isDirectory()) return -1;
        else return a.name.compareToIgnoreCase(b.name);
      }
      else
      {
        if (b.file.isDirectory()) return 1;
        else return a.name.compareToIgnoreCase(b.name);
      }
    }
    Row row;
  }

////////////////////////////////////////////////////////////////
// String Utils
////////////////////////////////////////////////////////////////

  /**
   * Get icon.
   */
  public static BImage icon(BIcon i)
  {
    BImage icon = BImage.make(i);
    if (icon == null)
      return defaultIcon;
    return icon;
  }
  static final BImage defaultIcon = BImage.make("module://icons/x16/file.png");

  /**
   * Get a string for file size.
   */
  public static String size(long size)
  {
    if (size < 0) return "";
    if (size < 1024) return "1 KB";
    double d = size / 1024;
    return sizeFormat.format(d);
  }
  static DecimalFormat sizeFormat = new DecimalFormat("#,##0.# KB");
  
  /**
   * Get a string for time.
   */  
  public static String time(BAbsTime time, Context context)
  {
    if (time == null || time.isNull()) return "";
    return time.toString(context);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final BBrush cutFg = BColor.make(100,100,100).toBrush();      

  static final int COL_NAME     = 0;
  static final int COL_TYPE     = 1;
  static final int COL_SIZE     = 2;
  static final int COL_MODIFIED = 3;
  
  final UiLexicon lex = UiLexicon.bajaui();
  final String LEX_NAME     = lex.getText("name");
  final String LEX_TYPE     = lex.getText("type");
  final String LEX_SIZE     = lex.getText("size");
  final String LEX_MODIFIED = lex.getText("modified");

  boolean enableEdits = false;
  BIDirectory dir;
  Context context;
  boolean isSpace;
  BOrd dirOrd;
  boolean dropActive;
  Row[] rows = new Row[0];
  Row[] origRowOrder;
  
  BIFile copyFrom = null;  
}
