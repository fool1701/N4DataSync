/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import javax.baja.gx.BImage;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.table.TableSelection;
import javax.baja.ui.util.BTitlePane;
import javax.baja.workbench.component.table.ComponentTableModel;

/**
 * MgrTemplate manages the logical model and visualization of the
 * Template Directory as well as the match processing.
 *
 * @author    Andy Saunders
 * @creation  16 Dec 13
 * @since     Baja 4.0
 */
public abstract class MgrTemplate
  extends MgrSupport
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public MgrTemplate(BAbstractManager manager)
  {
    super(manager);
  }

  /**
   * Init is called once from <code>BAbstractManager.init()</code>
   * Must call <code>super.init()</code> if overridden.
   */
  public void init()
  {
    this.cols = makeColumns();
    updateColumns();
  }

  public abstract void updateTemplateTable(Type addType, Object[] learnSelObj);

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the TreeTable used to display the template data.
   */
  public final BTemplateTable getTable()
  {
    return table;
  }

  /**
   * Get the columns to display for the template table.  These
   * columns are initialized by the makeColumns() method.
   */
  public final MgrColumn[] getColumns()
  {
    return cols;//.clone();

  }

  /**
   * This method is called when the template table
   * should be updated against the logical model.
   */
  public void updateTable()
  {
    if(hasDynamicColumns())
    {
      updateColumns();
      table.updateColumns();
    }
    table.treeTableModel.updateTreeTable(true);
    table.getSelection().deselectAll();
  }

  /**
   * Initialize the columns
   */
  void updateColumns()
  {
    for(int i=0; i<cols.length; ++i)
      cols[i].init(getManager());
  }


  /**
   * Get the current number of root objects.
   */
  public final int getRootCount()
  {
    return roots.length;
  }

  /**
   * Get root object at the specified index.
   */
  public final Object getRoot(int index)
  {
    return roots[index];
  }

  /**
   * Get a copy of the current root objects.
   */
  public final Object[] getRoots()
  {
    return roots.clone();
  }

  /**
   * Update the root template objects and call <code>updateTable()</code>.
   */
  public final void updateRoots(Object[] roots)
  {
    this.roots = roots.clone();
    updateTable();
  }

  /*
   * Callback when a row is selected in the learn pane.
   */
  public void learnSelected()
  {
    MgrLearn learn = getManager().getLearn();
    if(learn == null)   return;

    BLearnTable learnTable = learn.getTable();
    TableSelection learnSelection = learnTable.getSelection();

    Object[] learnSelObj = learnTable.getSelectedObjects();
    if(learnSelObj == null) return;

    try
    {
      updateTemplateTable(null, learnSelObj);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

//    ((BTaggableFolderManager)getManager()).selectMatchingTag( learnSelection.getRow());
  }


////////////////////////////////////////////////////////////////
// Construction
////////////////////////////////////////////////////////////////

  /**
   * Make the pane used to display the template data.
   * The default implementation uses makeTableTitle(),
   * makeTable(), and makeJobBar().
   */
  public BWidget makePane()
  {
    String title = makeTableTitle();
    this.table = makeTable();

    BEdgePane pane = new BEdgePane();
    pane.setCenter(BTitlePane.makePane(title, table));

    return pane;
  }

  /**
   * Get the title for the template table.
   */
  public String makeTableTitle()
  {
    return manager.lexTemplateObjects;
  }

  /**
   * This method is called once to initalize the template table.
   */
  protected BTemplateTable makeTable()
  {
    return new BTemplateTable(this);
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Return if the template table's columns should be
   * re-initialized when the template data is updated.
   */
  protected boolean hasDynamicColumns()
  {
    return false;
  }

  /**
   * This is method is called to initialize the
   * columns used for the template table.  If
   * hasDynamicColumns() returns false, than makeColumns()
   * is only called once; otherwise it is called each time
   * the mgrTemplate table is updated.
   */
  protected abstract MgrColumn[] makeColumns();

  /**
   * Return if the specified depth is expandable.  If the
   * template model supports expansion, this method must
   * be overridden to return true for applicable levels.
   * Default returns false.
   */
  public boolean isDepthExpandable(int depth)
  {
    return true;
  }

  /**
   * Return if the template object should be rendered
   * as group in the TreeTable.
   */
  public boolean isGroup(Object template)
  {
    return false;
  }

  /**
   * Return if the specified template object has children.  This
   * method is only applicable if <code>isDepthExpandable()</code>
   * returns true for the given object's depth.  Default returns true.
   */
  public boolean hasChildren(Object template)
  {
    if(template instanceof BComponent)
    {
      return ((BComponent)template).getChildComponents().length > 0;
    }
    return false;
  }

  /**
   * If <code>isDepthExpandable()</code> and <code>hasChildren()</code>
   * return true for the specified object then this method is the
   * hook to get the expanded children.  Return an empty array if
   * no children available.
   */
  public Object[] getChildren(Object template)
  {
    return ((BComponent)template).getChildComponents();
  }

  /**
   * Get the icon for the specified template object.
   */
  public BImage getIcon(Object template)
  {
    return BImage.make(templateIcon);
  }

  /**
   * If the specified template object is already mapped into
   * the station database as an existing component then return
   * it, otherwise return null.  Subclasses should override
   * <code>isExisting(Object, BComponent)</code>.
   */
  public BComponent getExisting(Object template)
  {
    ComponentTableModel model = getManager().getModel().getTable().getComponentModel();
    for(int i=0; i<model.getRowCount(); ++i)
    {
      BComponent comp = model.getComponentAt(i);
      if (isExisting(template, comp)) return comp;
    }
    return null;
  }

  /**
   * Return if the specified component is an existing representation
   * of the template object which has already been mapped into the
   * station database.  The default implementation always returns false.
   */
  public boolean isExisting(Object template, BComponent component)
  {
    return ! isTaggable(template, component);
  }

////////////////////////////////////////////////////////////////
// Match and Add
////////////////////////////////////////////////////////////////

  /**
   * Return if the specified discovered object and the existing
   * component can be matched.  If so then this combination must
   * be supported by <code>toRow()</code>.  The default implementation
   * returns true if any of the MgrTypeInfos from <code>toTypes()</code>
   * return true for <code>isMatchable(database)</code>.
   */
  public boolean isTaggable(Object template, BComponent database)
  {
    try
    {
      MgrTypeInfo[] types = toTypes(template);
      for(int i=0; i<types.length; ++i)
        if (types[i].isMatchable(database)) return true;
      return false;
    }
    catch(Exception e)
    {
      System.out.println(e);
      return false;
    }
  }

  /**
   * Given a template row object, return a list of the types
   * which may be used to model it as a BComponent in the
   * station database.  This method is used by the tag command.
   * The type at index 0 in the array should be the type which
   * provides the best mapping.  Return an empty array if the
   * template cannot be mapped.
   */
  public MgrTypeInfo[] toTypes(Object template)
    throws Exception
  {
    System.out.println("MgrTemplate.toTypes invoked ?????????????");
    return MgrTypeInfo.makeArray(BComponent.TYPE);
  }

  /**
   * Map the configuration of template object to the specified
   * MgrEditRow.  Configuration changes should be made to the row,
   * not the component (so that the changes aren't applied until
   * the user commits).
   * <p>
   * This method is used by the add and match commands.  For the
   * match command the row maps to the component already in the
   * database.  For the add command the component is not created
   * until commit time.
   */
  public void toRow(Object template, MgrEditRow row)
    throws Exception
  {

  }

  protected static final BIcon templateIcon = BIcon.make("module://icons/x16/files/ntpl.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BTemplateTable table;
  MgrColumn[] cols;
  Object[] roots = new Object[0];
  boolean jobComplete;
}

