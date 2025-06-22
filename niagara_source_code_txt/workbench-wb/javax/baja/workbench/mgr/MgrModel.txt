/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.naming.SlotPath;
import javax.baja.nre.util.Array;
import javax.baja.space.Mark;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Tag;
import javax.baja.ui.BWidget;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.util.BTitlePane;
import javax.baja.workbench.mgr.MgrColumn.TagColumn;

import com.tridium.sys.tag.ComponentTags;

/**
 * MgrModel manages the logical model of the BComponents
 * in the station database (as opposed to the discovery
 * model managed by MgrLearn).
 *
 * @author    Brian Frank on 12 Jan 04
 * @version   $Revision: 24$ $Date: 7/30/10 11:10:22 AM EDT$
 * @since     Baja 1.0
 */
public class MgrModel
  extends MgrSupport
{           
                                                                              
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor. 
   */
  public MgrModel(BAbstractManager manager)
  {                  
    super(manager);
  }

  /**
   * Init is called once from {@code BAbstractManager.init()}.
   * Must call {@code super.init()} if overridden.
   */
  public void init()
  {                            
    this.cols = makeColumns().clone();
    columnsModified();
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the database table used to display the components in the 
   * database.  This table is initialized via the makeTable() method.
   */
  public final BMgrTable getTable()
  {
    return table;
  }            

  /**
   * Get the columns to display for the database table.  These
   * columns are initialized from the makeColumns() method. 
   */
  public final MgrColumn[] getColumns()
  {                                    
    return cols.clone();
  }    
  
  /**
   * Set the columns to display for the database table.  These
   * columns replace the values initialized from the makeColumns() 
   * method.
   * 
   * @since Niagara 3.6
   */
  public final void setColumns(MgrColumn[] cols)
  {                                    
    this.cols = cols;
  }      
   
  /**
   * Get the number of columns. 
   * 
   * @since Niagara 3.6
   */
  public final int getColumnCount()
  {                                    
    return cols.length;
  }                          
    
  
  /**
   * Get the column at the specified index.
   */
  public final MgrColumn getColumn(int index)
  {                                        
    return cols[index];
  }

  /**
   * Get the columns which are editable using the EditMgr APIs. 
   * This list the subset of {@code getColumns()} which
   * has the {@code MgrColumn.EDITABLE} flag set.
   */
  public final MgrColumn[] getEditableColumns()
  {                        
    return editable.clone();
  }                    
  
  /**
   * Must be called if the model's columns are modified in order to 
   * initialize the new columns and updated the MgrTable.
   * 
   * @since Niagara 3.6
   */  
  public final void columnsModified()
  {
    ArrayList<MgrColumn> editable = new ArrayList<>();
    for (MgrColumn col : cols)
    {
      col.init(manager);
      if (col.isEditable()) { editable.add(col); }
    }                                         
    this.editable = editable.toArray(new MgrColumn[0]);
    
    BMgrTable table = getTable();
    if(table!=null)
      table.columnsModified();
  }
  
  public MgrColumn[] getEditableTagColumns(String namespace)
  {
    Map<String, TagColumn> tagCols = new HashMap<>();
    for (BComponent selComp : getTable().getSelectedComponents())
    {
      // only consider direct tags
      for (Tag tag : new ComponentTags(selComp).filter(t -> t.getId().getDictionary().equals(namespace)))
      {
        // Exclude direct tags that are marked readonly or hidden
        Property tagProp = selComp.getProperty(SlotPath.escape(tag.getId().getQName()));
        if (tagProp == null || Flags.isReadonly(selComp, tagProp) ||
            Flags.isHidden(selComp, tagProp))
        {
          continue;
        }

        int colFlags = MgrColumn.EDITABLE;
        TagColumn tagCol = new TagColumn(tag, colFlags);
        // TODO: should we be using qname here?
        if (!tagCols.containsKey(tag.getId().getName()))
        {
          tagCols.put(tag.getId().getName(), tagCol);
        }
      }
    }
    MgrColumn[] array = new MgrColumn[tagCols.size()];
    tagCols.values().toArray(array);
    return array;
  }

////////////////////////////////////////////////////////////////
// Construction
////////////////////////////////////////////////////////////////
              
  /**
   * Make the pane used to display the database components.  The
   * default implementation uses makeTable() and makeTableTitle().
   */
  public BWidget makePane()
  {  
    table = makeTable();
    manager.attach(table);                   
    manager.setTransferWidget(table);
    
    return BTitlePane.makePane(makeTableTitle(), table);
  }

  /**
   * Make the table to use for the display of components 
   * in the database.
   */
  protected BMgrTable makeTable()
  {             
    return new BMgrTable(this);
  }

  /**
   * Get the title to use for the database table.
   */
  protected String makeTableTitle()
  {             
    return manager.lexDatabaseObjects;
  }

  protected void initTable()
  {
    table = makeTable();
    manager.attach(table);
    manager.setTransferWidget(table);
  }
    
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Override this method if you need the ability
   * to add more than 100 objects at a time.
   *
   * @since Niagara 4.2
   * @return the maximum number of new objects to add at once
   */
  public int getMaxNewInstances()
  {
    return 100;
  }

  /**
   * Get the list of types supported by the new operation.  The
   * first entry in the list should be the default type.
   * <pre>                                     
   *    return MgrTypeInfo.makeArray(BFooDevice.TYPE);
   * </pre>
   */
  public MgrTypeInfo[] getNewTypes()
  {
    return null;
  }

  /**
   * Get the base type supported by the new operation.
   */
  public Type getBaseNewType()
  {
    return null;
  }

  /**
   * Given a BComponent, compute what its MgrTypeInfo is; this is
   * basically the reverse of newInstance().
   */
  public MgrTypeInfo toType(BComponent c)
  {                
    return MgrTypeInfo.make(c);
  }
  
  /**
   * Given a MgrTypeInfo create a new BComponent instance.  This is
   * used by the new and add commands to initialize components from
   * a user selected type.
   */
  public BComponent newInstance(MgrTypeInfo type)
    throws Exception
  {                 
    return type.newInstance();
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
    // build a Mark out of the rows                            
    String[] names = new String[rows.length];
    BComponent[] values = new BComponent[rows.length];
    for(int i=0; i<rows.length; ++i)
    {
      names[i]  = rows[i].getName();
      values[i] = rows[i].getTarget();
    }
    Mark mark = new Mark(values, names);
    
    // add to the database - eventually we might be
    // adding to multiple containers, in which case
    // we need to break the problem up into multiple
    // Marks and separate operations
    BComponent container = rows[0].getEdit().getAddContainer();
    mark.moveTo(container, null); 
    return null;
  }
    
  /**
   * This method is called once to initialize the columns.  
   * If you wish your manager to support MixIns, use the 
   * {@code appendMixInColumns} method.
   */
  protected MgrColumn[] makeColumns()
  {           
    return new MgrColumn[] { new MgrColumn.Name(), new MgrColumn.Type() };
  }                  
  
  /**
   * Given an original list of columns, append a MgrColumn.MixIn for 
   * each MixIn registered on the specified parent type - usually this
   * the primary type returned by {@code getNewTypes()}.
   */
  public MgrColumn[] appendMixInColumns(MgrColumn[] cols, Type mixInParentType)
  {                                  
    try
    { 
      // get enabled mix ins, and figure out which one's 
      // apply to the specified parent type
      Type[] mixIns = manager.target.getComponentSpace().getEnabledMixIns();
      Array<MgrColumn> result = new Array<>(cols);
      for (Type mixIn : mixIns)
      {
        // check if mixin is an agent on the parent type
        if (Sys.getRegistry().isAgent(mixIn.getTypeInfo(), mixInParentType.getTypeInfo()))
        {
          AgentList mixinAgents = Sys.getRegistry().getAgents(mixIn.getTypeInfo());
          mixinAgents = mixinAgents.filter(AgentFilter.is(BMixinMgrAgent.TYPE));
          mixinAgents = mixinAgents.filter(new AgentFilter()
          {
            // filter out all but concrete agents
            public boolean include(AgentInfo agent)
            {
              return ((!agent.getAgentType().isInterface()) &&
                (!agent.getAgentType().isAbstract()));
            }
          });

          // add the columns provided by each registered BMixinColumns agent
          for (int j = 0; j < mixinAgents.size(); j++)
          {
            BMixinMgrAgent mixInCols = (BMixinMgrAgent) mixinAgents.get(j)
              .getInstance();
            if (mixInCols.requireExactTypeMatch())
            {
              boolean isSpecific = Sys.getRegistry().isSpecificAgent(
                mixInCols.getType().getTypeInfo(), mixIn.getTypeInfo());
              if (isSpecific)
              {
                result.addAll(mixInCols.getColumns());
              }
            }
            else
            {
              result.addAll(mixInCols.getColumns());
            }
          }
        }
      }   
      return result.trim();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return cols;
  }

 /**
   * Call {@code getTable().reload()}.
   */                        
  public void load(BComponent target)
  {                                 
    super.load(target);
    table.reload();
  }

  /**
   * Get the list of types to include in this manager.
   */
  public Type[] getIncludeTypes()
  {
    return new Type[] { BComponent.TYPE };
  }        
  
  /**
   * The {@code accept()} method is used to provide an
   * additional level of filtering for the components displayed 
   * by this manager.  This method is NOT a replacement for 
   * {@code getIncludeTypes()}, but augments it.
   */
  public boolean accept(BComponent component)
  {            
    return true;
  }
  
  /**
   * Get the depth used to subscribe for component events
   * by this manager.  A depth of 1 means to subscribe to
   * components right under the target container. A depth
   * of 2 means to subscribe to child components and grandchild
   * components under the container.  Depth must never be
   * zero - that would be only the container itself.  If you
   * find yourself using a depth above 3 you probably need
   * to rethink your component structure.  The default is one.
   */
  public int getSubscribeDepth() 
  {       
    return 1;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  MgrColumn[] cols;
  MgrColumn[] editable;     
  BMgrTable table;
}                    


