/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.baja.gx.BInsets;
import javax.baja.gx.IRectGeom;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BLink;
import javax.baja.sys.BValue;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBorder;
import javax.baja.ui.BButton;
import javax.baja.ui.BDialog;
import javax.baja.ui.BLabel;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.enums.BValign;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BWidgetEvent;
import javax.baja.ui.event.BWindowEvent;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.pane.BScrollPane;
import javax.baja.ui.pane.BTabbedPane;
import javax.baja.ui.table.BTable;
import javax.baja.ui.table.TableController;
import javax.baja.ui.table.TableSelection;
import javax.baja.ui.util.UiLexicon;
import javax.baja.util.Lexicon;
import javax.baja.workbench.BWbEditor;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.ui.UiEnv;
import com.tridium.ui.theme.Theme;

/**
 * BMgrEditPane is used to prompt the user to make
 * changes to an MgrEdit.
 *
 * @author    Brian Frank
 * @creation  17 Dec 03
 * @version   $Revision: 43$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraAction(
  name = "editorModified",
  parameterType = "BWidgetEvent",
  defaultValue = "new BWidgetEvent()"
)
public class BMgrEditDialog
  extends BDialog
{                           
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.mgr.BMgrEditDialog(2296388700)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "editorModified"

  /**
   * Slot for the {@code editorModified} action.
   * @see #editorModified(BWidgetEvent parameter)
   */
  public static final Action editorModified = newAction(0, new BWidgetEvent(), null);

  /**
   * Invoke the {@code editorModified} action.
   * @see #editorModified
   */
  public void editorModified(BWidgetEvent parameter) { invoke(editorModified, parameter, null); }

  //endregion Action "editorModified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMgrEditDialog.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
    
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Framework use only
   */
  public BMgrEditDialog()
  {
    throw new IllegalStateException();
  }  
  
  public BMgrEditDialog(MgrEdit edit, boolean selectAll)
    throws Exception
  {
    this(edit, selectAll, edit.new TagTableCellRenderer());
  }
  
  public BMgrEditDialog(MgrEdit edit, boolean selectAll, MgrEdit.TagTableCellRenderer cellRenderer)
    throws Exception
  {                    
    super(edit.getManager(), edit.getTitle(), true);
    this.manager = edit.manager;                     
    this.edit = edit;

    // setup table
    table = new BTable(edit, new Controller());
    table.setSelection(new Selection());
    table.setCellRenderer(cellRenderer);

    // setup input pane
    makeInputPane();

    // setup actions
    ok = new Ok(this);
    cancel = new Cancel(this);
    BGridPane actions = new BGridPane();
    actions.setColumnAlign(BHalign.fill);
    actions.setUniformColumnWidth(true);
    actions.add(null, new BButton(ok));
    actions.add(null, new BButton(cancel));
    
    // put it together
    BTabbedPane tabbedPane = new BTabbedPane();
    BEdgePane pane = new BEdgePane();
    pane.setTop(new BBorderPane(table, BBorder.inset, BInsets.make(5, 5, 5, 5)));

    BScrollPane inputScrollPane = new BScrollPane( inputPane );
    tabbedPane.addPane("Properties", new BBorderPane(inputScrollPane,BBorder.inset, BInsets.make(0, 5, 5, 5)));

    tabbedPane.setShowSingleTab(false);
    pane.setCenter(tabbedPane);

    BScrollPane scrollPane = new BScrollPane( pane );
    scrollPane.setViewportBackground( Theme.widget().getControlBackground() );

    BEdgePane content = new BEdgePane();
    content.setCenter( new BBorderPane( scrollPane ) );
    content.setBottom(new BBorderPane(actions, 0, 10, 10, 10));

    setContent(content);    
    
    setupKeyEvents();
    
    if (selectAll)
      table.getSelection().selectAll();

  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the associated BAbstractManager.
   */
  public BAbstractManager getManager()
  {
    return manager;
  }
  
  /**
   * Get the associated MgrEdit.
   */
  public MgrEdit getEdit()
  {
    return edit;
  }            
  
  /**
   * Get the result as BDialog.OK or BDialog.CANCEL.
   */
  public int getResult()
  {          
    return result;
  }

  /**
   * Get the currently selected rows as an array of MgrEditRows.
   */
  public MgrEditRow[] getSelectedRows()
  {  
    int[] sel = table.getSelection().getRows();
    MgrEditRow[] rows = new MgrEditRow[sel.length];
    for(int i=0; i<rows.length; ++i)
      rows[i] = edit.getRow(sel[i]);
    return rows;
  }

////////////////////////////////////////////////////////////////
// Input Pane
////////////////////////////////////////////////////////////////
    
  /**
   * Make the input pane which is used to edit one or more
   * rows.  The pane is a grid of label/editors each modeled
   * using a ColumnInput class.
   */
  private void makeInputPane()
    throws Exception
  {                                    
    // create initial editors using first row
    MgrEditRow[] rows = new MgrEditRow[] { edit.getRow(0) };
    
    // create the input entries
    MgrColumn[] cols = edit.getColumns();
    inputs = new ColumnInput[cols.length];
    for(int i=0; i<inputs.length; ++i)
      inputs[i] = new ColumnInput(cols[i], i);
    
    // build a grid pane of label/editors
    inputPane = new BGridPane(2);
    inputPane.setColumnGap(10);
    inputPane.setHalign(BHalign.left);
    inputPane.setValign(BValign.top);
    for(int i=0; i<inputs.length; ++i)
    {               
      ColumnInput input = inputs[i];
      input.installLabel();
      try{input.installEditor(cols[i].toEditor(rows, i, null), rows);}
      catch(Exception e)
      { 
        input.installEditor(null, rows);
      }
    }                     
  }        

  /**
   * This method syncs the input pane with the MgrEdit
   * table based on the current selection.  Sync first 
   * saves the current changes back to the old selection, 
   * then loads the input pane with the new selection.
   *
   * @return true for success, fail on failure
   */
  public boolean syncInputPane()
  {
    MgrEditRow[] newSelection = getSelectedRows();
    removeDuplicates(newSelection);
    // save old selection    
    try
    {                    
      saveInputPane(oldSelection);
    }
    catch(Throwable e)
    {                            
      e.printStackTrace();
      if (isShowing()) BDialog.error(this, BDialog.TITLE_ERROR, "Cannot save to table", e);
      return false;
    }                                         
    
    // update input pane with new selection
    try
    {
      loadInputPane(newSelection);
    }
    catch(Throwable e)
    {
      e.printStackTrace();
      if (isShowing()) BDialog.error(this, "Cannot load from table", e);
      return false;
    }                                         
    oldSelection = newSelection;        
    table.repaint();
    return true;
  }

  /*
   * Remove duplicate items from oldSelection using the given array
   */
  private void removeDuplicates(MgrEditRow[] toRemove)
  {
    if(oldSelection != null)
    {
      ArrayList<MgrEditRow> returnList = new ArrayList<>();
      List<MgrEditRow> toRemoveList = Arrays.asList(toRemove);
      for(MgrEditRow row : oldSelection)
      {
        if(!toRemoveList.contains(row))
        {
          returnList.add(row);
        }
      }
      //Check for case where the oldSelection is the same as the new selection
      //This occurs when the returnList size is 0 and the oldSelection length is
      //The size of the new selection
      if(returnList.size() != 0 || oldSelection.length != toRemove.length)
      {
        oldSelection = returnList.toArray(new MgrEditRow[returnList.size()]);
      }
    }
  }
  
  /**
   * Load the input pane with the selected rows.
   */
  protected void loadInputPane(MgrEditRow[] selection)
    throws Exception
  {
    for(ColumnInput input : inputs)
    {
      input.load(selection);
    }
  }                                     

  /**
   * Save the input pane with the selected rows.
   */
  protected void saveInputPane(MgrEditRow[] selection)
    throws Exception
  {                      
    if (selection == null || selection.length == 0) return;
    for(ColumnInput input : inputs)
    {
      input.save(selection);
    }
    
    //before we allow the user to move on, we should allow the MgrEdit a chance to validate
    //what has been entered
    edit.validate(selection);
  }

  private void copy(BComponent from, BComponent to)
  {
    for (Property prop : from.getProperties())
    {
      int flags = from.getFlags(prop);
      if( (flags & (Flags.HIDDEN + Flags.READONLY)) != 0 )
        continue;
      String propName = prop.getName();
      if(to.get(propName).equivalent(from.get(prop)))
        continue;
      to.set(propName, from.get(prop).newCopy(true));
    }
    ;
  }
  /**
   * Implementation for editorModified action.
   */
  public void doEditorModified(BWidgetEvent event)
  {                                  
    String name = event.getWidget().getName();
    int index = Integer.parseInt(name.substring("editor".length()));
    inputs[index].modified(getSelectedRows());
  }               
  
  /**
   * When the type column is changed, that potentially
   * changes all the other columns, so reload them all,
   * except for the type column itself which would put us
   * into a infinite loop.
   */
  void typeChanged()
    throws Exception
  {                                            
    MgrEditRow[] selection = getSelectedRows();
    for(ColumnInput input : inputs)
    {
      if(input.col instanceof MgrColumn.Type) continue;
      input.load(selection);
    }
  }                                     
                       
////////////////////////////////////////////////////////////////
// Check Names
////////////////////////////////////////////////////////////////
  
  /**
   * Return true if all configured names are all unique.  If not
   * then display an error dialog, and return false.
   */
  public boolean checkNames()
  {                        
    MgrEditRow[] rows = edit.getRows();
    for(int i=0; i<rows.length; ++i)
    {
      String name = rows[i].getName();
      if (name != null && !edit.isNameUnique(rows[i], name))
      {                          
        String msg = lex.getText("mgr.duplicateName", new Object[] { name });
        BDialog.error(this, msg);
        return false;
      }
    }                
    return true;
  }
  
////////////////////////////////////////////////////////////////
// ColumnInput
////////////////////////////////////////////////////////////////

  class ColumnInput
  {     
    ColumnInput(MgrColumn col, int colIndex) 
    { 
      this.col = col;           
      this.colIndex = colIndex;        
      this.labelKey = "label"+colIndex;
      this.editorKey = "editor"+colIndex;
      this.label = new BLabel(col.getIcon(), col.getDisplayName());
      label.setFont(Theme.widget().getBoldText());                      
    }            

    void installLabel()
    {
      inputPane.add(labelKey, label);                 
    }
    
    void installEditor(BWbEditor editor, MgrEditRow[] selectedRows)
    {              
      // save editor       
      BWbEditor oldEditor = this.editor;
      this.editor = editor;

      // reset label
      if (editor == null || !col.isCellValid(selectedRows[0]))
      {                             
        label.setImage(col.getIcon().getDisabledImage());
        //label.setForeground(BColor.gray.toBrush());
        label.setEnabled(false); //let theme gray it out
        if(editor != null)
          editor.setEnabled(false);
      }
      else
      {
        label.setImage(col.getIcon());
        //label.setForeground(BColor.black.toBrush());
        label.setEnabled(true); //let theme paint it enabled
        if(editor != null) editor.setEnabled(true);
      }                                 
      label.repaint();
      
      // get widget to display
      BWidget widget;
      if (editor != null)
      {
        widget = editor;
        editor.setReadonly(edit.isReadonly(selectedRows, col));
      }
      else if (oldEditor != null)
      {
        widget = oldEditor;
        oldEditor.setReadonly(true);
      }
      else if( !col.isCellValid(selectedRows[0]) )
        widget = new BLabel("");
      else
      {
        widget = new BLabel(lexCannotEdit);
      }
      
      // setup link
      if (linkFromEditor != null) remove(linkFromEditor);
      if (editor != null) linkTo(editor, BWbEditor.pluginModified, editorModified);
      
      // add or set
      Property editorProp = inputPane.getProperty(editorKey);
      if (editorProp == null)
        inputPane.add(editorKey, widget);
      else if (inputPane.get(editorProp) != widget)
        inputPane.set(editorProp, widget);
    }                
    
    void load(MgrEditRow[] selection)
      throws Exception
    {                
      if (selection == null || selection.length == 0) 
        installEditor(null, new MgrEditRow[0]);
      else    
      {
        try{installEditor(col.toEditor(selection, colIndex, editor), selection);}
        catch(Exception e)
        {
          installEditor(null, selection);
        }
      }
    }

    void save(MgrEditRow[] selection)
      throws Exception
    {               
      if (editor != null && editor.isModified())
      {
        col.fromEditor(selection, colIndex, editor);      
        if (col instanceof MgrColumn.Type)
          typeChanged();
      }
    }
    
    void modified(MgrEditRow[] selection)
    {                                   
      BLabel label = (BLabel)inputPane.get(labelKey);
      
      // try to save
      try
      { 
        if (selection != null && selection.length > 0)
        {
          save(selection);
          label.setImage(col.getIcon());
          repaint();  
          return;
        }
      }
      catch(Exception e)
      {
      }             
      
      // otherwise highlight the label for dirty
      label.setImage(col.getIcon().getHighlightedImage());
      label.repaint();
    }                  
        
    MgrColumn col;    
    int colIndex;     
    String labelKey;
    String editorKey;
    BLabel label;
    BWbEditor editor;   
    BLink linkFromEditor;
  }
                                
////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  public void computePreferredSize()
  { 
    // compute exact size                                
    super.computePreferredSize();     
    
    // make a little bigger for table
    double pw = getPreferredWidth() + 20;
    double ph = getPreferredHeight() + 20;    
    
    // but always make it a bit smaller than screen
    IRectGeom screen = UiEnv.get().getScreenBounds(this);
    double sw = screen.width() - 50;
    double sh = screen.height() - 50;
    if (pw > sw) pw = sw;
    if (ph > sh) ph = sh;

    if(pw > MAX_DIALOG_WIDTH) pw = MAX_DIALOG_WIDTH;
    if(ph > MAX_DIALOG_HEIGHT) ph = MAX_DIALOG_HEIGHT;
    setPreferredSize(pw, ph);
  }                   
  
  public void handleEnter()
  {       
    syncInputPane();
  }
  
////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////

  class Controller extends TableController
  {          
  }

////////////////////////////////////////////////////////////////
// Selection
////////////////////////////////////////////////////////////////

  class Selection extends TableSelection
  {
    @Override
    public void updateTable()
    {                        
      super.updateTable();   
      int[] curSel = getRows();
      if (hasSelectionChanged(curSel, oldSel))
      {
        int[] saveOldSel = oldSel.clone();
        oldSel = curSel;
        if(!syncInputPane())
        {
          oldSel = saveOldSel;
          select(saveOldSel);
        }
      }
    }            
    
    int[] oldSel = new int[0];
  }                     
  
  static boolean hasSelectionChanged(int[] a, int[] b)
  {
    if (a.length != b.length) return true;
    for(int i=0; i<a.length; ++i)
      if (a[i] != b[i]) return true;
    return false;
  }

////////////////////////////////////////////////////////////////
// Key Eventing Hack
////////////////////////////////////////////////////////////////
  
  /**
   * NOTE: this is a very bad hack to deal with keyboard 
   * eventing until the bajaui key event APIs are enhanced.
   */
  private void setupKeyEvents()
  {              
    com.tridium.ui.awt.AwtShellManager manager = (com.tridium.ui.awt.AwtShellManager)widgetSupport(null);
    manager.addKeyListener(new KeyListener());
  }                                           
  
  class KeyListener implements java.awt.event.KeyListener
  {
    public void keyPressed(java.awt.event.KeyEvent event)
    {                       
      if (getCurrentFocus() == table) return;
      if (event.isConsumed()) return;      
      int code = event.getKeyCode();
      switch(code)
      {
        case BKeyEvent.VK_UP:
        case BKeyEvent.VK_DOWN:
        case BKeyEvent.VK_PAGE_UP:
        case BKeyEvent.VK_PAGE_DOWN:             
          updateTableSelection(event, code);
          break;
        case BKeyEvent.VK_ENTER:
          if(getSelectedRows().length > 1)  // If multiple rows are selected leave selection alone.
            break;
          if (event.isControlDown()) ok.invoke();
          else updateTableSelection(event, BKeyEvent.VK_DOWN); // enter is same as hitting down
          break;
      }
    }
    
    public void keyReleased(java.awt.event.KeyEvent event) {}
    public void keyTyped(java.awt.event.KeyEvent event) {}
  }               
  
  void updateTableSelection(java.awt.event.KeyEvent event, int code)
  {
    BKeyEvent e = new BKeyEvent(BKeyEvent.KEY_PRESSED, table, event.getModifiersEx(), code, event.getKeyChar());
    table.getController().keyPressed(e);     
    BWidget focus = getCurrentFocus();
    if (focus instanceof BTextField)
      ((BTextField)focus).getSelection().selectAll();
  }
  
////////////////////////////////////////////////////////////////
// Ok
////////////////////////////////////////////////////////////////

  class Ok extends Command
  {
    Ok(BWidget owner) { super(owner, UiLexicon.bajaui().getText("dialog.ok")); }
    
    public CommandArtifact doInvoke() 
    { 
      if (!syncInputPane()) return null;
      if (!checkNames()) return null;
      try
      {
        edit.validate(edit.getRows());
      }
      catch(Exception e)
      {
        e.printStackTrace();
        if (isShowing())
        {
          BDialog.error(BMgrEditDialog.this, BDialog.TITLE_ERROR, "Cannot save all rows to table", e);
        }
        //displayError("Cannot save all rows to table", e);
        return null;
      }
      result = BDialog.OK;
      close();
      return null;
    }
  }

////////////////////////////////////////////////////////////////
// Cancel
////////////////////////////////////////////////////////////////

  class Cancel extends Command
  {
    Cancel(BWidget owner) { super(owner, UiLexicon.bajaui().getText("dialog.cancel")); }
    
    public CommandArtifact doInvoke() 
    { 
      result = BDialog.CANCEL;
      close();
      return null;
    }
  }

  public void windowClosing(BWindowEvent event)
  {
    super.windowClosing(event);
    cancel.invoke();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

//  static BImage objectIcon = BImage.make("module://icons/x16/object.png");
//  static BImage disabledIcon = objectIcon.getDisabledImage();
//  static BImage highlightedIcon = objectIcon.getHighlightedImage();

  public static int MAX_DIALOG_WIDTH = 750;
  public static final int MAX_DIALOG_HEIGHT = 750;


  Lexicon lex = Lexicon.make(BMgrEditDialog.class);
  String lexCannotEdit = lex.getText("mgr.cannotEdit");

  BAbstractManager manager;
  Command ok, cancel;
  MgrEdit edit;
  BTable table;
  BGridPane inputPane;
  ColumnInput[] inputs;
  MgrEditRow[] oldSelection;    
  int result;

}
