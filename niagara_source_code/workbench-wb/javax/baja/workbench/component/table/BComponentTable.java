/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.component.table;

import java.util.ArrayList;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.Mark;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.table.BTable;
import javax.baja.ui.table.TableModel;
import javax.baja.ui.table.WrapperTableModel;
import javax.baja.ui.transfer.SimpleDragRenderer;
import javax.baja.ui.transfer.TransferContext;
import javax.baja.ui.transfer.TransferEnvelope;
import javax.baja.workbench.view.BWbComponentView;

import com.tridium.ui.theme.Theme;
import com.tridium.workbench.transfer.TransferUtil;
import com.tridium.workbench.util.WbViewEventWorker;

/**
 * BComponentTable is an extension to BTable that is designed to 
 * model a table of BComponents.  BComponentTable is best used
 * bound to a BPlugin which is used to keep the table model in
 * sync with component events:
 * <pre>
 * ComponentTableModel.Column[] columns = 
 *    {
 *      new ComponentTableModel.DisplayNameColumn(),
 *      new ComponentTableModel.PropertyColumn(x),
 *      new ComponentTableModel.PropertyColumn(y)
 *    });
 * BComponentTable table = new BComponentTable(columns);
 * plugin.bind(table);
 * table.load(container, cx);
 * </pre>
 *
 * @author    Brian Frank       
 * @creation  21 Mar 02
 * @version   $Revision: 22$ $Date: 11/2/07 2:38:41 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BComponentTable
  extends BTable
  implements BWbComponentView.Attachable
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.component.table.BComponentTable(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BComponentTable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Create a default instance of BComponentTable.
   */
  public BComponentTable()
  {
    this(new ComponentTableModel(null));
  }
  
  /**
   * Create with specified columns for model.
   */
  public BComponentTable(ComponentTableModel.Column[] columns)
  {
    this(new ComponentTableModel(columns));
  }
  
  /**
   * Create a BComponentTable with the specified model instance.
   */
  public BComponentTable(ComponentTableModel model)
  {
    super(model);
    setController(new ComponentTableController());
    setHeaderRenderer(new ComponentTableHeaderRenderer());
    setCellRenderer(new ComponentTableCellRenderer());
    setSelection(new ComponentTableSelection());
    
    setCutEnabled(true);
    setCopyEnabled(true);
    setPasteEnabled(true);
    setPasteSpecialEnabled(true);
    setDuplicateEnabled(true);
    setDeleteEnabled(true);
    setRenameEnabled(true);
    // By default, turn off alternating color rows since component tables use color to indicate status
    setColorRows(false);
  }                               
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Trap model sets.
   */
  public void setModel(TableModel m)
  {
    super.setModel(m);
    while(m instanceof WrapperTableModel)
      m = ((WrapperTableModel)m).getRootModel();
    this.model = (ComponentTableModel)m;
  }
    
  /**
   * Get the ComponentTableModel for this table.
   */
  public final ComponentTableModel getComponentModel()
  { 
    return model;
  }

////////////////////////////////////////////////////////////////
// BPlugin.Attachabled
////////////////////////////////////////////////////////////////
  
  public void attached(BWbComponentView view) 
  {
    this.view = view;
  }

  public void detached(BWbComponentView view) 
  {
    this.view = null;
  }
  
  public void handleComponentEvent(BComponentEvent event)
  {
    if (container == null) return;

    ComponentTableModel model = getComponentModel();
    int id = event.getId();
    Object containerHandle = container.getHandle();
    Object eventHandle = event.getSourceComponent().getHandle();
    String slot = event.getSlotName();

    // handle an event on the container
    if (containerHandle.equals(eventHandle))
    {
      switch(id)
      {
        case BComponentEvent.PROPERTY_ADDED:   
          container.loadSlots();
          BObject obj = container.get(slot);
          if (obj != null && obj.isComponent())
          {
            BComponent comp = obj.asComponent();
            if (!include(comp)) return;
            
            WbViewEventWorker.getInstance().registerForComponentEventsLater(view, comp, Math.max(loadDepth-1, 0));            
            model.addRow(comp);
            getSelection().deselectAll();
          }
          break;
        case BComponentEvent.PROPERTY_REMOVED: 
          if (model.removeRow(slot))
            getSelection().deselectAll();
          break;
        case BComponentEvent.PROPERTY_RENAMED: 
          String oldName = event.getValue().toString();
          model.renameRow(oldName, slot);
          break;        
        case BComponentEvent.PROPERTIES_REORDERED:
          handleReorder(); 
          break;
      }
    }
    
    repaint();
  }
  
  private void handleReorder()
  {
    ComponentTableModel model = getComponentModel();
    int[] indices = new int[model.getRowCount()];
    
    int n = 0;
    SlotCursor<Property> c = container.getProperties();
    while(c.nextComponent())
    {
      int index = model.getRowByName(c.property().getName());
      if (index != -1) indices[n++] = index;
    }
    
    model.reorderRows(indices);
  }

////////////////////////////////////////////////////////////////
// Loading
////////////////////////////////////////////////////////////////  

  /**
   * Get the container component which is the parent
   * of the components displayed in the table's rows.
   */
  public BComponent getContainer()
  {
    return container;
  }

  /**
   * Set the container component.
   */
  public void setContainer(BComponent container)
  {
    this.container = container;
  }                     
  
  /**
   * Get the context passed to the load method.
   */
  public Context getCurrentContext()
  {
    return context;
  }

  /**
   * Convenience for <code>load(container, new Class[] { BComponent.class }, 1, cx)</code>.
   */
  public final void load(BComponent container, Context cx)
  {
    load(container, new Class<?>[] {BComponent.class}, cx);
  }

  /**
   * Convenience for <code>load(container, new Class[] { cls }, 1, cx)</code>.
   */
  public final void load(BComponent container, Class<?> cls, Context cx)
  {
    load(container, new Class<?>[] { cls }, 1, cx);
  }
  
  /**
   * Convenience for <code>load(container, classes, 1, cx)</code>.
   */
  public final void load(BComponent container, Class<?>[] classes, Context cx)
  {
    load(container, classes, 1, cx);
  }
  
  /**
   * Convenience for <code>load(container, new Class[] { cls }, depth, cx}</code>.
   */
  public final void load(BComponent container, Class<?> cls, int depth, Context cx)
  {
    load(container, new Class<?>[] { cls }, depth, cx);
  }
  
  /**
   * This automatically populates the model with the components
   * under the container which are instances of one of the
   * specified classes.  This table must be bound to a BPlugin using 
   * BPlugin.bind() before calling this method.  This method
   * automatically loads and subscribes to each component 
   * displayed as a row using the bound BPlugin.  Component events
   * are automatically processed to keep the model in sync. 
   * <p>
   * If depth is one only the direct children of container are
   * loaded.  If depth is 2, then both the children and grandchildren
   * of container are loaded and subscribed (only the direct children
   * are actually used to update the table though).
   */
  public void load(BComponent container, Class<?>[] classes, int depth, Context cx)
  {
    if (view == null) 
      throw new IllegalStateException("Use BWbComponentEvent.addComponentEventListener() before calling load()");

    setContainer(container);
    this.loadClasses = classes;
    this.loadDepth = depth;   
    this.context = cx;
    view.registerForComponentEvents(container, depth);
    
    ArrayList<BComponent> toAdd = new ArrayList<>();
    
    container.loadSlots();
    BComponent[] kids = container.getChildComponents();
    for(int i=0; i<kids.length; ++i) 
    {
      BComponent kid = kids[i];
      if (Flags.isHidden(container, kid.getPropertyInParent())) continue;
      if (!include(kid)) continue;      
      kid.loadSlots();
      toAdd.add(kid);
    }
    
    BComponent[] rows = toAdd.toArray(new BComponent[toAdd.size()]);
    ComponentTableModel model = getComponentModel();
    model.setRows(rows); // Does the same thing as removeAllRows -> addRows
  }

  /**
   * Reload the table.  This loads the table using the same parameters
   * used in the last call to load().  It also clears all subscriptions
   * made in any previous calls to load().
   */  
  public void reload()
  {
    if (view == null)
      throw new IllegalStateException("Must call load before calling reload.");
    getSelection().deselectAll();
    view.unregisterForAllComponentEvents();      
    load(container, loadClasses, loadDepth, context);
  }

////////////////////////////////////////////////////////////////
// Selection
////////////////////////////////////////////////////////////////  
  
  /**
   * Get the component at the specified row index.
   */
  public BComponent getComponentAt(int row)
  {                                               
    return getComponentModel().getComponentAt(row);
  }

  /**
   * Get the selected row's BComponent or null if 
   * no selection.
   */
  public BComponent getSelectedComponent()
  {
    int row = getSelection().getRow();
    if (row == -1) return null;
    return getComponentModel().getComponentAt(row);
  }

  /**
   * Get the selected rows' BComponents or return an empty 
   * array if no selection.
   */
  public BComponent[] getSelectedComponents()
  {
    int[] rows = getSelection().getRows();
    BComponent[] components = new BComponent[rows.length];
    for(int i=0; i<rows.length; ++i)
      components[i] = getComponentModel().getComponentAt(rows[i]);
    return components;
  }

  /**
   * Get the selected components are a transfer Mark
   * or if nothing is selected return null.
   */
  public Mark getSelectedComponentsAsMark()
  {
    BComponent[] c = getSelectedComponents();
    if (c.length == 0) return null;
    return new Mark(c);
  }
  
  /**
   * Select a component by it's name.
   */
  public void selectByName(String name)
  {
    selectByName(new String[] { name });
  }

  /**
   * Select multiple components by name.
   */
  public void selectByName(String[] names)
  {
    ComponentTableModel model = getComponentModel();
    int[] rows = model.getRowsByName(names);
    getSelection().select(rows);
    if (rows.length > 0)
      ensureRowIsVisible(rows[rows.length-1]);
  }

////////////////////////////////////////////////////////////////
// Transfer
////////////////////////////////////////////////////////////////

  /** 
   * Return a transfer envelope for currently selected components.
   */
  public TransferEnvelope getTransferData() 
    throws Exception 
  { 
    Mark mark = getSelectedComponentsAsMark();
    if (mark == null) return null;
    return TransferEnvelope.make(mark);
  }
    
  /** 
   * 
   * Call insertDynamicProperties using the container
   * component from model.  Note that no matter whether
   * a row is selected or not the insert always occurs
   * into the container component.
   */
  public CommandArtifact insertTransferData(TransferContext cx) 
    throws Exception
  { 
    return TransferUtil.insert(this, cx, container, null);
  }

  /** 
   * Do nothing, developer should forward component events
   * to model to keep table in sync.  Return null.
   */
  public CommandArtifact removeTransferData(TransferContext cx)
    throws Exception
  { 
    return null;
  }

  /**
   * Delete selected components.
   */
  public CommandArtifact doDelete()
    throws Exception
  {
    if (container == null) return null;
    
    BComponent[] selection = getSelectedComponents();
    if (selection.length == 0) return null;
    
    Mark mark = new Mark(selection);
    return TransferUtil.delete(this, mark);
  }

  /**
   * Rename selected components.
   */
  public CommandArtifact doRename()
    throws Exception
  {
    if (container == null) return null;
    
    BComponent[] selection = getSelectedComponents();
    if (selection.length == 0) return null;
    
    Mark mark = new Mark(selection);
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
    int[] rows = getSelection().getRows();                
    BComponent[] components = getSelectedComponents();
    if (components.length == 0) return;
    
    // if click not in selection, then don't start drag
    int row = getRowAt(event.getY());
    boolean found = false;
    for(int i=0; i<rows.length; ++i)
      if (row == rows[i]) { found = true; break; }
    if (!found) return;    
      
    SimpleDragRenderer dragRenderer = new SimpleDragRenderer(components);
    dragRenderer.font = Theme.table().getCellFont();
    startDrag(event, TransferEnvelope.make(new Mark(components)), dragRenderer);
  }
  
  /**
   * The entire table is a a drop into the container.
   */
  public void dragEnter(TransferContext cx)
  {
    if (container == null) return;
    dropActive = true;
    repaint();
  }
  
  /**
   * The entire table is a a drop into the container.
   */
  public int dragOver(TransferContext cx)
  {
    if (container == null) return 0;
    int mask = ACTION_COPY | ACTION_MOVE;
    return mask;
  }

  /**
   * Drag exit.
   */
  public void dragExit(TransferContext cx)
  {
    if (container == null) return;
    dropActive = false;
    repaint();
  }
  
  /**
   * Handle a drop using insertDynamicProperties().
   */
  public CommandArtifact drop(TransferContext cx)
    throws Exception
  {
    if (container == null) return null;
    dropActive = false;
    repaint();
    return insertTransferData(cx);      
  }

///////////////////////////////////////////////////////////
// Filter
///////////////////////////////////////////////////////////

  /**
   * Should the specified component be included as a row in
   * this table?  By default, the just makes sure that
   * the object is an instance of one of the classes specified
   * in the most recent call to load().  Subclasses can override
   * to provide additional filtering.
   */
  protected boolean include(BComponent c)
  {
    for (int i = 0; i < loadClasses.length; i++)
      if (loadClasses[i].isInstance(c))
        return true;

    return false;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  BComponent container;
  BWbComponentView view;
  Class<?>[] loadClasses = new Class<?>[] { BComponent.class };
  int loadDepth = 0;
  Context context;

  /**
   * Modifies the table's appearance when an item is dragged over.
   * Over-riding methods may require the subclasser to change this field.
   */
  protected boolean dropActive;
  
  ComponentTableModel model;  
} 
