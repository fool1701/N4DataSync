/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import javax.baja.gx.BImage;
import javax.baja.gx.BInsets;
import javax.baja.job.BJob;
import javax.baja.naming.BOrd;
import javax.baja.sys.BComponent;
import javax.baja.ui.BBorder;
import javax.baja.ui.BWidget;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.util.BTitlePane;
import javax.baja.workbench.component.table.ComponentTableModel;
import com.tridium.ui.theme.Theme;
import com.tridium.workbench.job.BJobBar;

/**
 * MgrLearn manages the logical model and visualization of the
 * discovery data as well as the add and match processing.
 *
 * @author    Brian Frank
 * @creation  10 Jan 04
 * @version   $Revision: 21$ $Date: 7/30/10 10:29:29 AM EDT$
 * @since     Baja 1.0
 */
public abstract class MgrLearn 
  extends MgrSupport
{           
                                                                              
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor. 
   */
  public MgrLearn(BAbstractManager manager)
  {                 
    super(manager); 
  }

  /**
   * Init is called once from <code>BAbstractManager.init()</code>
   * Must call <code>super.init()</code> if overridden.
   */
  public void init()
  {            
    updateColumns();
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the TreeTable used to display the discovery data.
   */
  public final BLearnTable getTable()
  {                                             
    return table;
  }                           

  /**
   * Get the widget created via makeJobBar().
   */
  public final BWidget getJobBar()
  {
    return jobBar;
  }

  /**
   * Get the columns to display for the discovery table.  These
   * columns are initialized by the makeColumns() method. 
   */
  public final MgrColumn[] getColumns()
  {                           
    // initialize firt time thru the columns
    if (cols == null)
    {
    }                                                  
    return cols.clone();
  }
  
  /**
   * This method is called when the discovery table
   * should be updated against the logical model.
   */
  public void updateTable()
  {                 
    table.getSelection().deselectAll();
    if(hasDynamicColumns())
    {
      updateColumns();
      table.updateColumns();
    }
    table.treeTableModel.updateTreeTable(true);
  }

  /**
   * Initialize the columns for the discovery table
   */
  void updateColumns()
  {
    cols = makeColumns().clone();
    for(int i=0; i<cols.length; ++i)
      cols[i].init(manager);    
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
   * Update the root discovery objects and call <code>updateTable()</code>.
   */
  public final void updateRoots(Object[] roots)
  {
    this.roots = roots.clone();
    updateTable();
  }                                              
  
////////////////////////////////////////////////////////////////
// Construction
////////////////////////////////////////////////////////////////

  /**
   * Make the pane used to display the discovery data.
   * The default implementation uses makeTableTitle(), 
   * makeTable(), and makeJobBar().
   */
  public BWidget makePane()
  {                                                               
    String title = makeTableTitle();
    this.table = makeTable();
    this.jobBar = makeJobBar();
    
    BEdgePane pane = new BEdgePane();
    pane.setCenter(BTitlePane.makePane(title, table));
    if (jobBar != null)
    {  
      if (jobBar instanceof BJobBar) 
      {
        manager.attach((BJobBar)jobBar);          
        BBorderPane jobPane = new BBorderPane(jobBar, BBorder.none, BInsets.make(0,5,0,5));
        jobPane.setFill(Theme.widget().getWindowBackground());
        jobPane = new BBorderPane(jobPane, BBorder.none, BInsets.DEFAULT);
        pane.setTop(new BBorderPane(jobPane, BInsets.make(0, 0, 5, 0)));
      }              
      else
      {
        pane.setTop(new BBorderPane(jobBar, 0, 0, 5, 0));
      }
    }
    return pane;
  }  

  /**
   * Get the title for the discovery table.
   */
  public String makeTableTitle()
  {             
    return manager.lexDiscoveryObjects;
  }

  /**
   * This method is called once to initalize the discovery table.
   */
  protected BLearnTable makeTable()
  {                                             
    return new BLearnTable(this);
  }                           

  /**
   * This is a hook used to initialize the discovery JobBar 
   * for displaying progress of the discovery job.  If null 
   * is returned then no JobBar is included. 
   */
  protected BWidget makeJobBar()
  {             
    return new BJobBar();
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  
  /**
   * Return if the discovery table's columns should be 
   * re-initialized when the discovery data is updated.
   * @since Niagara 3.4
   */
  protected boolean hasDynamicColumns()
  {
    return false;
  }
  
  /**
   * This is method is called to initialize the 
   * columns used for the discovery table.  If 
   * hasDynamicColumns() returns false, than makeColumns()
   * is only called once; otherwise it is called each time
   * the learn table is updated.
   */
  protected abstract MgrColumn[] makeColumns();
    
  /**
   * Return if the specified depth is expandable.  If the
   * discovery model supports expansion, this method must
   * be overridden to return true for applicable levels.
   * Default returns false.
   */
  public boolean isDepthExpandable(int depth)
  {
    return false;
  }                      
  
  /**
   * Return if the discovery object should be rendered
   * as group in the TreeTable.
   */
  public boolean isGroup(Object discovery)
  {                                           
    return false;
  }
  
  /**
   * Return if the specified discovery object has children.  This 
   * method is only applicable if <code>isDepthExpandable()</code>
   * returns true for the given object's depth.  Default returns true.
   */
  public boolean hasChildren(Object discovery)
  {                
    return true;
  }
  
  /**
   * If <code>isDepthExpandable()</code> and <code>hasChildren()</code> 
   * return true for the specified object then this method is the
   * hook to get the expanded children.  Return an empty array if
   * no children available.
   */
  public Object[] getChildren(Object discovery)
  {
    return new Object[0];
  }
  
  /**
   * Get the icon for the specified discovery object.
   */
  public BImage getIcon(Object discovery)
  {                                                  
    return null;
  }               
  
  /**
   * If the specified discovery object is already mapped into
   * the station database as an existing component then return
   * it, otherwise return null.  Subclasses should override
   * <code>isExisting(Object, BComponent)</code>. 
   */
  public BComponent getExisting(Object discovery)
  {                 
    ComponentTableModel model = getManager().getModel().getTable().getComponentModel();
    for(int i=0; i<model.getRowCount(); ++i)
    {
      BComponent comp = model.getComponentAt(i);
      if (isExisting(discovery, comp)) return comp;
    }
    return null;
  }            
  
  /**
   * Return if the specified component is an existing representation
   * of the discovery object which has already been mapped into the
   * station database.  The default implementation always returns false.
   */
  public boolean isExisting(Object discovery, BComponent component)
  {
    return false;
  }

////////////////////////////////////////////////////////////////
// Jobs
////////////////////////////////////////////////////////////////
    
  /**
   * Return the job currently mapped via <code>setJob()</code>.
   */
  public BJob getJob()
  {  
    return job;
  }

  /**
   * Learnable managers can provide a baja:Job which is
   * used to manage the discovery process.
   */
  public void setJob(BJob job)
  {             
    this.job = job;
    this.jobComplete = false;
    if (jobBar instanceof BJobBar)   
    { 
      getManager().registerForComponentEvents(job);
      ((BJobBar)jobBar).load(job);
    } 
    
    if (job != null && job.getJobState().isComplete() && !jobComplete) 
    {
      jobComplete = true;

      // We have suppressed subscriptions to learned objects (ie. descendant 
      // components of the learn job) in BMgrTable.handleComponentEvent() up
      // until this point.  Now it is safe to subscribe to everything on the
      // learn job at once, so do it.
      if ((getManager() != null) && (getManager().isRegisteredForComponentEvents(job)))
        getManager().registerForComponentEvents(job, Integer.MAX_VALUE);
        
      jobComplete(job);
    }
  }                   
  
  /**
   * Lookup a job by ord, and then call <code>setJob()</code>.
   */
  public void setJob(BOrd jobOrd)
    throws Exception
  {                              
    BComponent base = (BComponent)getManager().getCurrentValue();
    base.getComponentSpace().sync();
    BJob job = (BJob)jobOrd.get(base);
    setJob(job);
  }                  
  
  /**
   * This callback is automatically invoked when the 
   * current job set via <code>setJob()</code> completes.
   */
  public void jobComplete(BJob job)
  {
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
  public boolean isMatchable(Object discovery, BComponent database)
  {                         
    try
    {
      MgrTypeInfo[] types = toTypes(discovery);
      for(int i=0; i<types.length; ++i)
      {
        if (types[i].isMatchable(database))
        {
          return true;
        }
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
   * Given a discovery row object, return a list of the types 
   * which may be used to model it as a BComponent in the 
   * station database.  This method is used by the add command.
   * The type at index 0 in the array should be the type which
   * provides the best mapping.  Return an empty array if the
   * discovery cannot be mapped.
   */
  public abstract MgrTypeInfo[] toTypes(Object discovery)
    throws Exception;
  
  /**
   * Map the configuration of discovery object to the specified 
   * MgrEditRow.  Configuration changes should be made to the row,
   * not the component (so that the changes aren't applied until
   * the user commits).  
   * <p>
   * This method is used by the add and match commands.  For the 
   * match command the row maps to the component already in the 
   * database.  For the add command the component is not created 
   * until commit time.
   */
  public abstract void toRow(Object discovery, MgrEditRow row)
    throws Exception;
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BLearnTable table;
  BWidget jobBar;
  BJob job;
  MgrColumn[] cols;
  Object[] roots = new Object[0];  
  boolean jobComplete;
}                    


